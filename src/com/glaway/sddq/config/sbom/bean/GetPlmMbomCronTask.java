package com.glaway.sddq.config.sbom.bean;

import io.netty.util.internal.StringUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis2.transport.http.util.URIEncoderDecoder;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 获取产品SBOM结构
 * 
 * @author public2175
 * @version [版本号, 2018-9-26]
 * @since [产品/模块版本]
 */
public class GetPlmMbomCronTask extends BaseStatefulJob {

	Map<String, JSONObject> dataMap = new HashMap<String, JSONObject>();
	Set<JSONObject> noItemnumSet = null;// 存放暂时找不到item的Map
	JSONArray I_ITEM_ARRAY = null;// 存放I类耗损件数据
	JSONArray II_ITEM_ARRAY = null;// 存放II类耗损件数据
	Map<String, String> sqnItemnumMap = null;// 存放产品序列号与itemnum的MAP,可以通过产品序列号快速找到对应的itemnum
	Map<String, JSONObject> sqnItemMap = null;// 存放产品序列号与对应数据的MAP,可以通过产品序列号快速找到对应的ITEM
	Map<String, String> assetnumMap = null;// 存放aseetnum与parent的Map
	JSONArray replaceChild = null;// 物料替换关系数据
	ArrayList<String> sqlList = null;

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void execute() throws MroException {

		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		// 1、首先去ASSETTMP表中去获取没有获取过出厂配置的数据
		IJpoSet assetTmpJpoSet = MroServer.getMroServer().getSysJpoSet(
				"ASSETTMP");
		assetTmpJpoSet.setQueryWhere("assettmplevel='ASSET' and isplm = '0'");
		assetTmpJpoSet.reset();
		try {
			if (assetTmpJpoSet != null && assetTmpJpoSet.count() > 0) {
				System.out.println("获取PLM MBOM定时任务开始:");
				for (int i = 0; i < assetTmpJpoSet.count(); i++) {
					IJpo assetTmp = assetTmpJpoSet.getJpo(i);
					if (assetTmp != null) {
						String itemnum = assetTmp.getString("itemnum");
						if (!StringUtil.isNullOrEmpty(itemnum)) {
							System.out.println("获取PLM MBOM开始:"+itemnum);
							getPlmMbomByJpo(assetTmp);
							System.out.println("获取PLM MBOM结束:"+itemnum);
						}
					}
				}
				System.out.println("获取PLM MBOM定时任务结束:");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getPlmMbomByJpo(IJpo assetTmpJpo) throws MroException {

		IJpoSet myassetJpoSet = MroServer.getMroServer().getSysJpoSet(
				"ASSETTMP");
		myassetJpoSet.setQueryWhere("assettmpnum='"
				+ assetTmpJpo.getString("assettmpnum")
				+ "' and assettmplevel='ASSET' and isplm = '0'");
		myassetJpoSet.reset();
		if (myassetJpoSet != null && myassetJpoSet.count() > 0) {

			IJpo assetTmp = myassetJpoSet.getJpo(0);
			assetnumMap = new HashMap<String, String>();// 存放aseetnum与parent的Map
			replaceChild = new JSONArray();// 存放可替换物料数据
				String itemnum = assetTmp.getString("itemnum");
				String num = IFUtil.addIfHistory(IFUtil.MBOM, itemnum,
						IFUtil.TYPE_OUTPUT);// 增加输出记录
				// 调用PLM的接口
				String user = IFUtil.getIfServiceInfo("plm.user");
				String pwd = IFUtil.getIfServiceInfo("plm.pwd");
				String url = IFUtil.getIfServiceInfo("plm.url");
				String methodName = IFUtil.getIfServiceInfo("plm.method");
				String factory = IFUtil.getIfServiceInfo("plm.factory");
				try {
					Service service = new Service();
					Call call = (org.apache.axis.client.Call) service
							.createCall();
					call.setUsername(user);
					call.setPassword(pwd);
					call.setTargetEndpointAddress(new java.net.URL(url));
					call.addParameter("arg1", XMLType.XSD_STRING,
							ParameterMode.IN);
					call.addParameter("arg2", XMLType.XSD_STRING,
							ParameterMode.IN);
					call.setOperationName(methodName);
					call.setReturnType(XMLType.XSD_STRING);
					String dataxml = (String) call.invoke(new Object[] {
							itemnum, factory });
					IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
							IFUtil.FLAG_YES, "输出物料编码:" + itemnum);
					num = IFUtil.addIfHistory(IFUtil.MBOM, dataxml,
							IFUtil.TYPE_INPUT);
					if (StringUtil.isNullOrEmpty(dataxml)) {
						IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
								IFUtil.FLAG_YES, "获取数据为空");// 增加输出记录
						IFUtil.addDataHistory(assetTmp.getString("ancestor"),
								"获取数据为空", "1");
					} else {
						Document doc = DocumentHelper.parseText(dataxml);
						Element root = doc.getRootElement();
						if (root.element("ERRINFO") != null
								&& !StringUtil.isNullOrEmpty(root
										.elementText("ERRINFO"))) {
							IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
									IFUtil.FLAG_YES,
									root.elementText("ERRINFO"));// 增加输出记录
							IFUtil.addDataHistory(assetTmp.getString("ancestor"),
									root.elementText("ERRINFO"), "1");
						}else {
							listRootChildNodes(root);
							dealJsonData(assetTmp);
							StringBuffer message = new StringBuffer();
							if (noItemnumSet != null && noItemnumSet.size() > 0) {// 输出处理的信息
								System.out.println("在MRO系统没有找到对应的物料编码:"
										+ noItemnumSet.size());
								message.append("在MRO系统没有找到对应的物料编码共"
										+ noItemnumSet.size() + "种:");
								Iterator<JSONObject> it = noItemnumSet
										.iterator();
								while (it.hasNext()) {
									JSONObject rdata = it.next();
									String noitemnum = rdata
											.getString("itemnum");
									message.append(noitemnum + ",");
								}
							}
							IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
									IFUtil.FLAG_YES, "在MRO系统没有找到对应的物料编码共"
											+ noItemnumSet.size() + "种");// 增加输出记录
							if (StringUtil.isNullOrEmpty(message.toString())) {
								IFUtil.addDataHistory(
										assetTmp.getString("ancestor"),
										"在MRO系统没有找到对应的物料编码共"
												+ noItemnumSet.size() + "种",
										"1");
							} else {
								IFUtil.addDataHistory(
										assetTmp.getString("ancestor"),
										message.toString(), "1");
							}
							assetTmp.setValue("ISPLM", "1",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							myassetJpoSet.save();
						}
					}
				} catch (Exception e) {
					IFUtil.addDataHistory(assetTmp.getString("ancestor"),
							e.getMessage(), "1");
					IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_YES, e.getMessage());
					e.printStackTrace();
				}
//				catch (MalformedURLException e) {
//					IFUtil.addDataHistory(assetTmp.getString("ancestor"),
//							e.getMessage(), "1");
//					IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
//							IFUtil.FLAG_YES, e.getMessage());
//					e.printStackTrace();
//				} catch (IOException e) {
//					IFUtil.addDataHistory(assetTmp.getString("ancestor"),
//							e.getMessage(), "1");
//					IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
//							IFUtil.FLAG_YES, e.getMessage());
//					e.printStackTrace();
//				} catch (DocumentException e) {
//					IFUtil.addDataHistory(assetTmp.getString("ancestor"),
//							e.getMessage(), "1");
//					IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
//							IFUtil.FLAG_YES, e.getMessage());
//					e.printStackTrace();
//				}catch (IndexOutOfBoundsException e1) {
//					IFUtil.addDataHistory(assetTmp.getString("ancestor"),
//							"数组越界", "1");
//					IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
//							IFUtil.FLAG_YES, "数组越界");
//				}
		}
	}

	private void dealJsonData(IJpo assetJpo) throws MroException {

		noItemnumSet = new HashSet<JSONObject>();// 存放暂时找不到item的Map
		I_ITEM_ARRAY = new JSONArray();// 存放I类耗损件数据
		II_ITEM_ARRAY = new JSONArray();// 存放II类耗损件数据

		String assetitemnum = assetJpo.getString("itemnum");
		String ancestor = assetJpo.getString("ancestor");
		JSONObject jsonObject = dataMap.get(assetitemnum);// 找到所需物料，然后遍历循环
		assetnumMap.put(ancestor, "");

		if (jsonObject != null) {
			assetJpo.setValue("MBOMVERSION", jsonObject.getString("version"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			assetJpo.setValue("REMARK", jsonObject.getString("memo"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			IJpoSet roatJposet = assetJpo.getJpoSet("$systemChildren",
					"ASSETTMP", "1=2");
			IJpoSet lotJposet = assetJpo.getJpoSet("$assetmodelpart",
					"ASSETMODELPART", "1=2");
			addRoatChildrenJpo(roatJposet, lotJposet, jsonObject, ancestor,
					ancestor);
			// 处理物料关系的数据
			if (replaceChild != null && replaceChild.size() > 0) {
				IJpoSet altitemJpoSet = assetJpo.getJpoSet("$sys_altitem",
						"sys_altitem", "1=2");
				for (int index = 0; index < replaceChild.size(); index++) {
					// 增加更新机制，需判断在系统中是否已经存在altitemnum,parent
					String itemnum = replaceChild.getJSONObject(index)
							.getString("itemnum");
					String replace = replaceChild.getJSONObject(index)
							.getString("replace");
					String parent = replaceChild.getJSONObject(index)
							.getString("parent");
					String priority = replaceChild.getJSONObject(index)
							.getString("priority");
					// 判断系统中是否已经存在该替换关系
					if (!ItemUtil.isExistAltItem(replaceChild
							.getJSONObject(index))) {
						IJpo jpo = altitemJpoSet.addJpo();
						jpo.setValue("altitemnum", itemnum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("replace", replace);
						jpo.setValue("parent", parent,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("priority", priority);
					}
				}
			}
		}
	}

	private void addRoatChildrenJpo(IJpoSet roatJposet, IJpoSet lotJposet,
			JSONObject jsonObject, String ancestor, String parent)
			throws MroException {
		JSONArray childrens = jsonObject.getJSONArray("children");
		if (childrens != null && childrens.size() > 0) {
			for (int index = 0; index < childrens.size(); index++) {
				JSONObject children = childrens.getJSONObject(index);
				String itemnum = children.getString("itemnum");
				// 判断子级节点的物料属性
				String type = ItemUtil.getItemInfo(itemnum);
				if (ItemUtil.NO_ITEM.equals(type)) {
					noItemnumSet.add(children);// 忽略该数据
				} else if (ItemUtil.SQN_ITEM.equals(type)) {
					if (StringUtils.equals("EA",
							children.getString("orderunit"))
							&& children.containsKey("amount")) {
						if (IFUtil.isInteger(children.getString("amount"))
								&& children.getIntValue("amount") > 0) {
							for (int m = 0; m < children.getIntValue("amount"); m++) {
								IJpo jpo = roatJposet.addJpo();
								String assetnum = jpo.getString("assettmpnum");
								String desc = children.getString("description");
								jpo.setValue("ancestor", ancestor,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("parent", parent,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("description", desc,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("assettmplevel", "SYSTEM",
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("linenum",
										children.getString("rownum"),
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("itemnum", itemnum,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//
								jpo.setValue("ISPLM", "1",
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								if (!StringUtil.isNullOrEmpty(children
										.getString("linenums"))) {
									ArrayList<String> linenums = (ArrayList<String>) children
											.get("linenums");
									jpo.setValue(
											"RNUM",
											linenums.get(m),
											GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									if (StringUtil.isNullOrEmpty(linenums
											.get(m))) {
										jpo.setValue(
												"LOCDESC",
												desc,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//
									} else {
										jpo.setValue(
												"LOCDESC",
												desc,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//
									}
								} else {
									jpo.setValue(
											"LOCDESC",
											desc,
											GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//
								}
								assetnumMap.put(assetnum, parent);
								if (dataMap.containsKey(itemnum)) {
									addRoatChildrenJpo(roatJposet, lotJposet,
											dataMap.get(itemnum), ancestor,
											assetnum);
								}
							}
						}
					} else {
						IJpo jpo = roatJposet.addJpo();
						String assetnum = jpo.getString("assettmpnum");
						String desc = children.getString("description");
						jpo.setValue("ancestor", ancestor,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("parent", parent,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("description", desc,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("assettmplevel", "SYSTEM",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("itemnum", itemnum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//
						jpo.setValue("ISPLM", "1",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetnumMap.put(assetnum, parent);
						if (dataMap.containsKey(itemnum)) {
							addRoatChildrenJpo(roatJposet, lotJposet,
									dataMap.get(itemnum), ancestor, assetnum);
						}
					}
				} else {
					addLotJposet(lotJposet, children, parent);
				}
			}
		}
	}

	private void addLotJposet(IJpoSet lotJposet, JSONObject jsonObject,
			String parent) throws MroException {

		String itemnum = jsonObject.getString("itemnum");
		String type = ItemUtil.getItemInfo(itemnum);
		if (ItemUtil.NO_ITEM.equals(type)) {
			noItemnumSet.add(jsonObject);// 记住该数据，后期进行遍历循环处理
		}
		if (!StringUtil.isNullOrEmpty(jsonObject.getString("linenums"))) {
			if (IFUtil.isInteger(jsonObject.getString("amount"))
					&& jsonObject.getIntValue("amount") > 0) {
				for (int m = 0; m < jsonObject.getIntValue("amount"); m++) {
					IJpo jpo = lotJposet.addJpo();
					jpo.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					jpo.setValue("description",
							jsonObject.getString("description"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					jpo.setValue("qty", 1,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					jpo.setValue("assetnum", parent,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					jpo.setValue("datatype", "1",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					jpo.setValue("linenum", jsonObject.getString("rownum"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					jpo.setValue("APPLY", "0",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (!StringUtil.isNullOrEmpty(jsonObject
							.getString("linenums"))) {
						ArrayList<String> linenums = (ArrayList<String>) jsonObject
								.get("linenums");
						jpo.setValue("WNUM", linenums.get(m),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					if (dataMap.containsKey(itemnum)) {
						JSONArray childrens = dataMap.get(itemnum)
								.getJSONArray("children");
						if (childrens != null && childrens.size() > 0) {
							for (int i = 0; i < childrens.size(); i++) {
								addLotJposet(lotJposet,
										childrens.getJSONObject(i), parent);
							}
						}
					}
				}
			}
		} else {
			IJpo jpo = lotJposet.addJpo();
			jpo.setValue("itemnum", itemnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			jpo.setValue("description", jsonObject.getString("description"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			jpo.setValue("qty", jsonObject.getString("amount"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			jpo.setValue("assetnum", parent,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			jpo.setValue("datatype", "1",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			jpo.setValue("linenum", jsonObject.getString("rownum"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			jpo.setValue("APPLY", "0",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			if (dataMap.containsKey(itemnum)) {
				JSONArray childrens = dataMap.get(itemnum).getJSONArray(
						"children");
				if (childrens != null && childrens.size() > 0) {
					for (int i = 0; i < childrens.size(); i++) {
						addLotJposet(lotJposet, childrens.getJSONObject(i),
								parent);
					}
				}
			}
		}
	}

	private void listRootChildNodes(Element root) {
		// 根节点下第一层节点
		List<Element> childElemnets = root.elements("PNUMBER");
		try {
			for (Element pitem : childElemnets) {

				JSONObject item = new JSONObject();
				String itemnum = pitem.attributeValue("FNUMBER");
				Element element = pitem.element("ZIZHIJIANXINXI");

				item.put("itemnum", itemnum);// 物料编码
				item.put("description", URIEncoderDecoder.decode(element
						.elementText("DESCRIPTION")));// 物料描述
				item.put("specification", element.elementText("SPECIFICATION"));
				item.put("orderunit", element.elementText("ORDERUNIT"));
				item.put("version", element.elementText("VERSION"));
				item.put("type",
						URIEncoderDecoder.decode(element.elementText("TYPE")));
				item.put("memo", element.elementText("MEMO"));
				item.put("changeunittype",
						element.elementText("CHANGEUNITTYPE"));// SRU/LRU

				List<Element> childElements = pitem.elements("SNUMBER");
				JSONArray childrens = new JSONArray();
				for (Element sitem : childElements) {
					JSONObject s = new JSONObject();
					s.put("itemnum", sitem.attributeValue("ZNUMBER"));
					s.put("description", URIEncoderDecoder.decode(sitem
							.element("ZIZHIJIANXINXI").elementText(
									"DESCRIPTION")));
					s.put("amount", sitem.elementText("AMOUNT"));
					s.put("orderunit", sitem.element("ZIZHIJIANXINXI")
							.elementText("ORDERUNIT"));
					s.put("rownum", sitem.elementText("ROWNUM"));
					s.put("parent", sitem.elementText("PARENT"));
					s.put("memo", sitem.elementText("MEMO"));
					s.put("changeunittype", sitem.element("ZIZHIJIANXINXI")
							.elementText("CHANGEUNITTYPE"));// SRU/LRU
					s.put("version", sitem.element("ZIZHIJIANXINXI")
							.elementText("VERSION"));
					s.put("type",
							sitem.element("ZIZHIJIANXINXI").elementText("TYPE"));
					s.put("replace", sitem.elementText("REPLACE"));
					s.put("priority", sitem.elementText("PRIORITY"));
					if (sitem.element("OCCURRENCES") != null) {
						List<Element> occurrences = sitem
								.element("OCCURRENCES").elements("OCCURRENCE");
						if (occurrences != null && occurrences.size() > 0) {
							ArrayList<String> linenums = new ArrayList<String>();
							for (int n = 0; n < occurrences.size(); n++) {
								linenums.add(occurrences.get(n).elementText(
										"EBORT"));
							}
							s.put("linenums", linenums);
						}
					}
					if (!StringUtil.isNullOrEmpty(sitem.elementText("REPLACE"))) {
						replaceChild.add(s);
					}
					if (StringUtil.isNullOrEmpty(sitem.elementText("REPLACE"))
							|| sitem.elementText("PRIORITY").equals("1")) {
						childrens.add(s);
					}
				}
				item.put("children", childrens);
				dataMap.put(itemnum, item);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
