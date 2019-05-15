package com.glaway.sddq.config.sbom.bean;

import io.netty.util.internal.StringUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis2.transport.http.util.URIEncoderDecoder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.controller.AppBean;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.control.Button;
import com.glaway.mro.page.control.Tab;
import com.glaway.mro.page.control.TreeNode;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * SBOM构型管理
 * 
 * @author hyhe
 */
public class SbomAppBean extends AppBean {

	Map<String, JSONObject> dataMap = new HashMap<String, JSONObject>();
	Set<JSONObject> noItemnumSet = null;// 存放暂时找不到item的Map
	JSONArray I_ITEM_ARRAY = null;// 存放I类耗损件数据
	JSONArray II_ITEM_ARRAY = null;// 存放II类耗损件数据
	Map<String, String> sqnItemnumMap = null;// 存放产品序列号与itemnum的MAP,可以通过产品序列号快速找到对应的itemnum
	Map<String, JSONObject> sqnItemMap = null;// 存放产品序列号与对应数据的MAP,可以通过产品序列号快速找到对应的ITEM
	Map<String, String> assetnumMap = null;// 存放aseetnum与parent的Map
	JSONArray replaceChild = null;// 物料替换关系数据
	ArrayList<String> sqlList = null;
	IJpoSet roatJposet = null;
	IJpoSet lotJposet = null;
	IJpoSet altitemJpoSet = null;
	@Override
	public void afterTabChange(Tab currTab) throws IOException, MroException {
		if (currTab == currTab.getTabGroup().getTabByNum(2)) {
			if (this.getJpo() != null) {
				Button b = ((Button) this.getPage().getControlByXmlId(
						"13753201645612"));
				if (b != null) {
					if (("可用").equals(this.getJpo().getString("STATUS"))
							|| ("作废").equals(this.getJpo().getString("STATUS"))) {
						b.setDisable(true);
					} else {
						b.setDisable(false);
					}
					b.loadData();
				}
			}
		}
		super.afterTabChange(currTab);
		if (currTab == currTab.getTabGroup().getFirstTab()) {
			DataBean databean = this.getDataBean("1461565595904");
			if (databean != null) {
				TreeBean sbomTreeBean = (TreeBean) databean;
				sbomTreeBean.clickNode(sbomTreeBean.getCurrNode());
			}
		}
	}

	/**
	 * 导出数据
	 * 
	 * @return [参数说明]
	 * @throws IOException
	 * @throws MroException
	 */
	public int EXPORT() throws IOException, MroException {

		HttpServletRequest request = getMroSession().getRequest();
		String path = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";

		// 查询导出字段
		IJpoSet assetcsSet = MroServer.getMroServer().getSysJpoSet(
				"IMPATTRIBUTE");
		assetcsSet.setUserWhere("ifacename='PRODUCTSBOM'");
		assetcsSet.setOrderBy("to_number(excelcolnum)");
		assetcsSet.reset();

		String[] attributes = new String[assetcsSet.count()];
		String[] titles = new String[assetcsSet.count()];

		for (int i = 0; i < assetcsSet.count(); i++) {
			IJpo csjpo = assetcsSet.getJpo(i);
			attributes[i] = csjpo.getString("ATTRIBUTENAME");
			titles[i] = getAttrTitle("ASSETTMP", attributes[i]);
		}

		// 导出当前操作的JPO 所有节点数据
		IJpoSet impSet = MroServer.getMroServer().getSysJpoSet("ASSETTMP");
		String ancestor = this.getJpo().getString("ASSETTMPNUM");
		impSet.setUserWhere("ancestor = '" + ancestor + "'");
		impSet.setOrderBy("assettmplevel,assettmpid");
		impSet.reset();
		HttpSession session = request.getSession();
		session.setAttribute("TABLESET", impSet/* this.getJpoSet() */);
		session.setAttribute("ATTRIBUTES", attributes);
		session.setAttribute("TITLES", titles);
		session.setAttribute("APPNAME", "PSBOM");
		path += "downloadattachments";
		openurl(path);

		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	/**
	 * 获取字段标题
	 * 
	 * @param objectname
	 *            表名
	 * @param attributename
	 *            字段名称
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 */
	public String getAttrTitle(String objectname, String attributename)
			throws MroException {
		IJpoSet fieldSet = MroServer.getMroServer().getSysJpoSet("SYS_FIELD");
		fieldSet.setUserWhere("objectname = '" + objectname
				+ "' and fieldname = '" + attributename + "'");
		fieldSet.reset();
		return fieldSet.getJpo(0).getString("TITLE");
	}

	/**
	 * 根据物料编码获取MBOM信息
	 * 
	 * @return
	 * @throws AppException
	 * @throws MroException
	 *             [参数说明]
	 */
	public void GETPLMEBOM() throws MroException {
		super.checkSave();
		assetnumMap = new HashMap<String, String>();// 存放aseetnum与parent的Map
		replaceChild = new JSONArray();// 存放可替换物料数据
		if(this.getJpo() != null){
		String itemnum = this.getJpo().getString("itemnum");
		// String itemnum = "TE6229000000";
		if ("1".equals(this.getJpo().getString("ISPLM"))) {
			throw new AppException("assettmp", "isplm");
		} else {
			// Connection con = DBManager.getDBManager().getConnection();
			// 调用接口，输出itemnum，接收返回的数据
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
				Call call = (org.apache.axis.client.Call) service.createCall();
				call.setUsername(user);
				call.setPassword(pwd);
				call.setTargetEndpointAddress(new java.net.URL(url));
				call.addParameter("arg1", XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter("arg2", XMLType.XSD_STRING, ParameterMode.IN);
				call.setOperationName(methodName);
				call.setReturnType(XMLType.XSD_STRING);
				String dataxml = (String) call.invoke(new Object[] { itemnum,
						factory });
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "输出物料编码:" + itemnum);
				num = IFUtil.addIfHistory(IFUtil.MBOM, dataxml,
						IFUtil.TYPE_INPUT);
				if (StringUtil.isNullOrEmpty(dataxml)) {
					IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
							IFUtil.FLAG_YES, "获取数据为空");// 增加输出记录
					IFUtil.addDataHistory(this.getString("ancestor"), "获取数据为空",
							"1");
					this.SAVE();
					throw new AppException("assettmp", "noPlmdata");
				}
				Document doc;
				doc = DocumentHelper.parseText(dataxml);
				Element root = doc.getRootElement();
				if (root.element("ERRINFO") != null
						&& !StringUtil.isNullOrEmpty(root
								.elementText("ERRINFO"))) {
					IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
							IFUtil.FLAG_YES, root.elementText("ERRINFO"));// 增加输出记录
					IFUtil.addDataHistory(this.getString("ancestor"),
							root.elementText("ERRINFO"), "1");
					this.SAVE();
					throw new AppException("assettmp", "errinfo",
							new String[] { root.elementText("ERRINFO") });
				} 
//				else if(root.element("ERRINFO") != null
//						&& StringUtil.isNullOrEmpty(root
//						.elementText("ERRINFO"))){
//					IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
//							IFUtil.FLAG_YES, "获取数据没有节点");// 增加输出记录
//					IFUtil.addDataHistory(this.getString("ancestor"),
//							"获取数据没有节点", "1");
//					this.SAVE();
//					throw new AppException("assettmp", "errinfo",
//							new String[] { "获取数据没有节点" });
//				}
				else{
					listRootChildNodes(root);
					dealJsonData();
					StringBuffer message = new StringBuffer();
					if (noItemnumSet != null && noItemnumSet.size() > 0) {// 输出处理的信息
						System.out.println("在MRO系统没有找到对应的物料编码:"
								+ noItemnumSet.size());
						message.append("在MRO系统没有找到对应的物料编码共"
								+ noItemnumSet.size() + "种:");
						Iterator<JSONObject> it = noItemnumSet.iterator();
						while (it.hasNext()) {
							JSONObject rdata = it.next();
							String noitemnum = rdata.getString("itemnum");
							message.append(noitemnum + ",");
						}
					}
					IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
							IFUtil.FLAG_YES, "在MRO系统没有找到对应的物料编码共"
									+ noItemnumSet.size() + "种");// 增加输出记录
					if (StringUtil.isNullOrEmpty(message.toString())) {
						IFUtil.addDataHistory(this.getString("ancestor"),
								"在MRO系统没有找到对应的物料编码共" + noItemnumSet.size()
										+ "种", "1");
					} else {
						IFUtil.addDataHistory(this.getString("ancestor"),
								message.toString(), "1");
					}
					this.getJpo().setValue("ISPLM", "1",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					this.SAVE();
					// if(sqlList != null && sqlList.size() >0){
					// DBManager.getDBManager().executeBatch(con, sqlList);
					// }
				}
			} catch (ServiceException e) {
				IFUtil.addDataHistory(this.getString("ancestor"),
						e.getMessage(), "1");
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, e.getMessage());
				deleJpoSet();
				throw new AppException("assettmp", "backerror");
			} catch (MalformedURLException e) {
				IFUtil.addDataHistory(this.getString("ancestor"),
						e.getMessage(), "1");
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, e.getMessage());
				deleJpoSet();
				throw new AppException("assettmp", "backerror");
			} catch (IOException e) {
				IFUtil.addDataHistory(this.getString("ancestor"),
						e.getMessage(), "1");
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, e.getMessage());
				deleJpoSet();
				throw new AppException("assettmp", "backerror");
			} catch (DocumentException e) {
				IFUtil.addDataHistory(this.getString("ancestor"),
						e.getMessage(), "1");
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, e.getMessage());
				deleJpoSet();
				throw new AppException("assettmp", "backerror");
			} catch (IndexOutOfBoundsException e1) {
				IFUtil.addDataHistory(this.getString("ancestor"),
						"数组越界", "1");
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, "数组越界");
				deleJpoSet();
				e1.printStackTrace();
				throw new AppException("assettmp", "backerror");
			}
		}
		}else{
			throw new AppException("assettmp", "backerror");
		}
	}

	public void deleJpoSet() throws MroException {
		if(roatJposet != null){
			roatJposet.reset();
		}
		if(lotJposet != null){
			lotJposet.reset();
		}
		if(altitemJpoSet != null){
			altitemJpoSet.reset();
		}
	}

	/**
	 * 处理获取的JSON数据
	 * 
	 * @param allData
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 */
	public void dealJsonData() throws MroException {

		noItemnumSet = new HashSet<JSONObject>();// 存放暂时找不到item的Map
		I_ITEM_ARRAY = new JSONArray();// 存放I类耗损件数据
		II_ITEM_ARRAY = new JSONArray();// 存放II类耗损件数据

		IJpo assetJpo = this.getAppBean().getJpo();
		String assetitemnum = assetJpo.getString("itemnum");
		String ancestor = assetJpo.getString("ancestor");
		JSONObject jsonObject = dataMap.get(assetitemnum);// 找到所需物料，然后遍历循环
		assetnumMap.put(ancestor, "");

		if (jsonObject != null) {
			assetJpo.setValue("MBOMVERSION", jsonObject.getString("version"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			assetJpo.setValue("REMARK", jsonObject.getString("memo"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			roatJposet = assetJpo.getJpoSet("$systemChildren",
					"ASSETTMP", "1=2");
			lotJposet = assetJpo.getJpoSet("$assetmodelpart",
					"ASSETMODELPART", "1=2");
			addRoatChildrenJpo(roatJposet, lotJposet, jsonObject, ancestor,
					ancestor);
			// 处理物料关系的数据
			if (replaceChild != null && replaceChild.size() > 0) {
				 altitemJpoSet = assetJpo.getJpoSet("$sys_altitem",
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
			// sqlList = new ArrayList<String>();
			// String deleteSql =
			// "delete from assettmptree where assettmpnum in (select assettmpnum from assettmptree where ancestor = '"+ancestor+"')";
			// sqlList.add(deleteSql);
			// //遍历treeMap,生成assettree表数据
			// Iterator iter = assetnumMap.entrySet().iterator();
			// while(iter.hasNext()){
			// Entry entry = (Entry) iter.next();
			// String assetnum = (String) entry.getKey();
			// String parent = (String) entry.getValue();
			// getInsertAncestorSql("assettmptree",sqlList,assetnumMap,assetnum,assetnum,ancestor,0);
			// }
		}
	}

	/**
	 * 增加子级节点
	 * 
	 * @param jsonObject
	 * @param ancestor
	 * @param parent
	 *            [参数说明]
	 * @throws MroException
	 */
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
								}else{
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

	/**
	 * 创建批次号件数据
	 * 
	 * @param lotJposet
	 * @param jsonObject
	 * @param parent
	 *            [参数说明]
	 * @throws MroException
	 */
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
			jpo.setValue("APPLY", "0", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
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

	@SuppressWarnings("unchecked")
	public void listRootChildNodes(Element root) throws MroException {
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

	/**
	 * 拼装成创建assettree表的sql语句
	 * 
	 * @param tbname
	 * @param sqlList
	 * @param ancestorMap
	 * @param assetnum
	 * @param parent
	 * @param assetancestor
	 * @param i
	 * @return [参数说明]
	 */
	public ArrayList<String> getInsertAncestorSql(String tbname,
			ArrayList<String> sqlList, Map<String, String> ancestorMap,
			String assetnum, String parent, String assetancestor, int i) {
		if (ancestorMap.containsKey(parent)) {
			String ancestor = (String) ancestorMap.get(parent);
			String insertSql = "insert into " + tbname.toUpperCase() + "(";
			insertSql = insertSql
					+ "ASSETTMPTREEID,ASSETTMPNUM,ANCESTOR,SITEID,ORGID,HIERARCHYLEVELS)";
			insertSql = insertSql + " values (ASSETTMPTREESEQ.NEXTVAL,'"
					+ assetnum + "','" + parent + "','ELEC','CRRC'," + i + ")";
			sqlList.add(insertSql);
			if (ancestor != null) {
				sqlList = getInsertAncestorSql(tbname, sqlList, ancestorMap,
						assetnum, ancestor, assetancestor, i + 1);
			}
		} else {
			if ((parent.equals(assetancestor))) {
				String insertSql = "insert into " + tbname.toUpperCase() + "(";
				insertSql = insertSql
						+ "ASSETTMPTREEID,ASSETTMPNUM,ANCESTOR,SITEID,ORGID,HIERARCHYLEVELS)";
				insertSql = insertSql + " values (ASSETTMPTREESEQ.NEXTVAL,'"
						+ assetnum + "','" + parent + "','ELEC','CRRC'," + i
						+ ")";
				sqlList.add(insertSql);
			}
		}
		return sqlList;
	}

	/**
	 * @Description 添加节点
	 * @return
	 * @throws MroException
	 */
	public int ADDPRO() throws MroException {

		// 判断状态，变更状态时，可以添加节点
		if (!SddqConstant.ASSET_MODEL_STATUS_BG.equals(this.getJpo().getString(
				"STATUS"))) {
			throw new AppException("assettmp", "addNode");
		}

		// 判断节点
		DataBean databean = this.getDataBean("1461565595904");
		if (databean != null) {
			TreeBean sbomTreeBean = (TreeBean) databean;
			TreeNode treenode = sbomTreeBean.getCurrNode();
			if (treenode != null) {
				return GWConstant.ACCESS_SAMEMETHOD;
			} else {
				throw new AppException("asset", "selectNoAsset");
			}
		} else {
			throw new AppException("asset", "selectNoAsset");
		}
	}

	@Override
	public int INSERT() throws IOException, MroException {
		ShowDjLabel();
		return super.INSERT();
	}

	@Override
	public int NEXT() throws IOException, MroException {
		ShowDjLabel();
		return super.NEXT();
	}

	@Override
	public int PREVIOUS() throws IOException, MroException {
		ShowDjLabel();
		return super.PREVIOUS();
	}

	/**
	 * 
	 * 在向前向后切换时，显示隐藏
	 * 
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void ShowDjLabel() throws IOException {
		if (this.getPage().getControlByXmlId("13753201644523") != null) {
			this.getPage().getControlByXmlId("13753201644523").show();
		}
		if (this.getPage().getControlByXmlId("1467202192536") != null) {
			this.getPage().getControlByXmlId("1467202192536").hide();
		}
	}

}
