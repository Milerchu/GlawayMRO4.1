/*
 * 文 件 名:  AssetAppBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  实时台帐AppBean
 * 修 改 人:  hyhe
 * 修改时间:  2016-5-6
 */
package com.glaway.sddq.config.zcconfig.bean;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl.Authenticator;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.DBManager;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.page.control.Tab;
import com.glaway.mro.page.control.TreeNode;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
/*正式接口调用*/
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub.Char20;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub.TableOfZfracaszppz;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub.Zfracaszppz;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub.ZtfunFracasCppz;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub.ZtfunFracasCppzResponse;
import com.glaway.sddq.config.zcconfig.data.Asset;
/*正式接口调用*/
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 现场配置AppBean
 * 
 * @author hyhe
 * @version [MRO4.0, 2016-5-6]
 * @since [MRO4.0/模块版本]
 */
public class AssetAppBean extends AppBean {

	Map<String, String> parentMap = null;// 存放产品序列号与assetnum的Map
	Map<String, String> treeMap = null;// 存放aseetnum与parent的Map
	JSONArray noparentArray = null;// 存放暂时找不到parent的Map
	Set<JSONObject> noItemnumSet = null;// 存放暂时找不到item的Map
	JSONArray I_ITEM_ARRAY = null;// 存放I类耗损件数据
	JSONArray II_ITEM_ARRAY = null;// 存放II类耗损件数据
	Map<String, String> sqnItemnumMap = null;// 存放产品序列号与itemnum的MAP,可以通过产品序列号快速找到对应的itemnum
	Map<String, JSONObject> sqnItemMap = null;// 存放产品序列号与对应数据的MAP,可以通过产品序列号快速找到对应的ITEM

	@Override
	public void afterTabChange(Tab currTab) throws IOException, MroException {
		super.afterTabChange(currTab);
		if (currTab == currTab.getTabGroup().getFirstTab()) {
			DataBean databean = this.getDataBean("1375344856389");
			if (databean != null) {
				TreeBean sbomTreeBean = (TreeBean) databean;
				sbomTreeBean.setCurrJpo(sbomTreeBean.getCurrNode());
				sbomTreeBean.clickNode(sbomTreeBean.getCurrNode());
			}
		}
	}

	private void refreshPage() throws MroException {
		// if (!onListTab())
		// {
		// String description = "";
		// if (getJpo() != null)
		// {
		// IJpo jpo = getJpo();
		// if (!getJpo().isNull("DESCRIPTION"))
		// {
		// description = jpo.getString("DESCRIPTION");
		// description = description.length() > 30 ? description.substring(0,
		// 30) + "..." : description;
		// String desc[] = {description};
		// if (jpo.getParent() != null)
		// {
		//
		// description = getMessage("asset", "childrenasset", desc);
		// }
		// else
		// {
		// description = getMessage("asset", "assetchildren", desc);
		// }
		// }
		// PageControl tablectrl =
		// this.getPage().getControlByXmlId("13753386188542");
		// if (tablectrl != null)
		// {
		// tablectrl.setLabel(description);
		// }
		// }
		//
		// }
	}

	public int INSERT() throws MroException, IOException {
		ShowDjLabel();
		int i = super.INSERT();
		refreshPage();
		return i;
	}

	public void ShowDjLabel() throws IOException {
		PageControl ctrl = null;

		if (this.getAppName() != null && this.getAppName().equals("ZCCONFIG")) {
			// 结构
			ctrl = getPage().getControlByXmlId("13753448564042");// 顶层节点的基本信息
			if (ctrl != null)
				ctrl.show();
			ctrl = getPage().getControlByXmlId("1526365463241");// 车厢节点的基本信息
			if (ctrl != null)
				ctrl.hide();
			ctrl = getPage().getControlByXmlId("1467204293642");// 子级节点的基本信息
			if (ctrl != null)
				ctrl.hide();
			ctrl = getPage().getControlByXmlId("13753386188542");// 产品列表
			if (ctrl != null)
				ctrl.hide();
			ctrl = getPage().getControlByXmlId("1527590193314");// 产品列表
			if (ctrl != null)
				ctrl.hide();
			ctrl = getPage().getControlByXmlId("1526289204161");// 添加车厢
			if (ctrl != null)
				ctrl.show();
			ctrl = getPage().getControlByXmlId("1528545363690");// 子级耗损件列表
			if (ctrl != null)
				ctrl.hide();
		}

		if ("SSCONFIG".equals(this.getAppName())
				|| "CXCONFIG".equals(this.getAppName())) {
			// 结构
			ctrl = getPage().getControlByXmlId("13753448564042");
			if (ctrl != null)
				ctrl.show();
			ctrl = getPage().getControlByXmlId("1535795246915");
			if (ctrl != null)
				ctrl.show();
			ctrl = getPage().getControlByXmlId("1467204293642");
			if (ctrl != null)
				ctrl.hide();
			ctrl = getPage().getControlByXmlId("13753386188706");
			if (ctrl != null)
				ctrl.show();
		}
	}

	public int SAVE() throws MroException, IOException {
		int i = super.SAVE();
		refreshPage();
		if ("ZCCONFIG".equals(this.getAppName())) {
			getPzwzd();
			getYJPzwzd();
			i = super.SAVE();
		}
		ShowDjLabel();
		return i;
	}

	/**
	 * @Description 获取配置完整度
	 * @throws MroException
	 */
	public void getPzwzd() throws MroException {

		if (!StringUtil.isStrEmpty(this.getAppName())
				&& "ZCCONFIG".equals(this.getAppName())) {
			if (this.getJpo().getField("ASSETLEVEL").getValue().equals("ASSET")) {
				String ancestor = this.getString("assetnum");
				// 获取所有子集（不包括车厢节点，assetlevel = 'SYSTEM'）
				IJpoSet allJpoSet = MroServer.getMroServer()
						.getSysJpoSet(
								"ASSET",
								"ANCESTOR='" + ancestor
										+ "' and assetlevel = 'SYSTEM'");
				if (allJpoSet != null && allJpoSet.count() > 0) {
					// 获取序列号为空的节点
					IJpoSet sqnIsNullJpoSet = MroServer
							.getMroServer()
							.getSysJpoSet(
									"ASSET",
									"ANCESTOR='"
											+ ancestor
											+ "' and assetlevel = 'SYSTEM' and sqn is null");
					if (sqnIsNullJpoSet != null && sqnIsNullJpoSet.count() > 0) {
						int xncount = 0;
						// 如果存在序列号不为空的数据，则判断不为空的数据中有多少是虚拟件,排除掉
						for (int index = 0; index < sqnIsNullJpoSet.count(); index++) {
							IJpo jpo = sqnIsNullJpoSet.getJpo(index);
							if (ItemUtil.getItem(jpo.getString("itemnum")) != null
									&& ItemUtil.getItem(
											jpo.getString("itemnum"))
											.getBoolean("ISIV")) {
								xncount = xncount + 1;
							}
						}
						NumberFormat numformat = NumberFormat.getInstance();
						numformat.setMaximumFractionDigits(2);
						if (xncount > 0) {
							int allcount = allJpoSet.count() - xncount;
							int sqnisnullCount = sqnIsNullJpoSet.count()
									- xncount;
							if (sqnisnullCount > 0) {
								String count = numformat
										.format(((float) (allcount - sqnisnullCount) / (float) allcount) * 100);
								this.getJpo()
										.getField("PZWZD")
										.setValue(
												count,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								this.getJpo()
										.getField("PZWZ")
										.setValue(
												count + "%",
												GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
							} else {
								this.getJpo()
										.getField("PZWZD")
										.setValue(
												100,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								this.getJpo()
										.getField("PZWZ")
										.setValue(
												"100%",
												GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
							}
						} else {
							String count = numformat
									.format(((float) (allJpoSet.count() - sqnIsNullJpoSet
											.count()) / (float) allJpoSet
											.count()) * 100);
							this.getJpo()
									.getField("PZWZD")
									.setValue(
											count,
											GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.getJpo()
									.getField("PZWZ")
									.setValue(
											count + "%",
											GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
						}
					} else {
						this.getJpo()
								.getField("PZWZD")
								.setValue(100,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo()
								.getField("PZWZ")
								.setValue("100%",
										GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
					}
				} else {
					this.getJpo()
							.getField("PZWZD")
							.setValue(0,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					this.getJpo()
							.getField("PZWZ")
							.setValue("0%",
									GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
				}
			}
		}
	}

	/**
	 * 
	 * 计算一级部件配置完整度
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private void getYJPzwzd() throws MroException {

		if (this.getJpo().getField("ASSETLEVEL").getValue().equals("ASSET")) {
			String ancestor = this.getString("assetnum");
			// 获取所有子集（不包括车厢节点，assetlevel = 'SYSTEM'）
			// 获取车厢信息
			IJpoSet carJpoSet = MroServer.getMroServer().getSysJpoSet("ASSET",
					"ANCESTOR='" + ancestor + "' and assetlevel = 'CAR'");
			int yjCount = 0;
			int yjsqnisNullCount = 0;
			if (carJpoSet != null && carJpoSet.count() > 0) {
				for (int index = 0; index < carJpoSet.count(); index++) {
					// 根据车厢信息查找车厢下的子级部件，如果直接子级是虚拟件，则继续往下一层查找
					IJpo carJpo = carJpoSet.getJpo(index);
					String assetnum = carJpo.getString("ASSETNUM");
					IJpoSet sysJpoSet = MroServer.getMroServer().getSysJpoSet(
							"ASSET",
							"ANCESTOR='" + ancestor + "' and parent = '"
									+ assetnum + "'");

					if (sysJpoSet != null && sysJpoSet.count() > 0) {
						for (int j = 0; j < sysJpoSet.count(); j++) {
							// 判断该物料是否是虚拟件
							IJpo itemJpo = ItemUtil.getItem(sysJpoSet.getJpo(j)
									.getString("itemnum"));
							if (itemJpo != null && itemJpo.getBoolean("ISIV")) {
								IJpoSet sysXjJpoSet = MroServer
										.getMroServer()
										.getSysJpoSet(
												"ASSET",
												"ANCESTOR='"
														+ ancestor
														+ "' and parent = '"
														+ sysJpoSet
																.getJpo(j)
																.getString(
																		"ASSETNUM")
														+ "'");
								for (int i = 0; i < sysXjJpoSet.count(); i++) {
									yjCount = yjCount + 1;
									if (StringUtil.isStrEmpty(sysXjJpoSet
											.getJpo(i).getString("SQN"))) {
										yjsqnisNullCount = yjsqnisNullCount + 1;
									}
								}
							} else {
								yjCount = yjCount + 1;
								if (StringUtil.isStrEmpty(sysJpoSet.getJpo(j)
										.getString("SQN"))) {
									yjsqnisNullCount = yjsqnisNullCount + 1;
								}
							}
						}
					}
				}
				NumberFormat numformat = NumberFormat.getInstance();
				numformat.setMaximumFractionDigits(2);
				int sqnCount = yjCount - yjsqnisNullCount;
				if (sqnCount > 0 && yjCount > 0) {
					String count = numformat
							.format(((float) (sqnCount) / (float) yjCount) * 100);
					this.getJpo()
							.getField("YJPZWZD")
							.setValue(count,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				} else {
					this.getJpo()
							.getField("YJPZWZD")
							.setValue(0,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			} else {
				this.getJpo().getField("YJPZWZD")
						.setValue(0, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}
	}

	public int PREVIOUS() throws MroException, IOException {

		int i = super.PREVIOUS();
		ShowDjLabel();
		refreshPage();
		return i;
	}

	public int NEXT() throws MroException, IOException {

		int i = super.NEXT();
		ShowDjLabel();
		refreshPage();
		return i;
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
		assetcsSet.setUserWhere("ifacename='XZASSET'");
		assetcsSet.setOrderBy("to_number(excelcolnum)");
		assetcsSet.reset();

		String[] attributes = new String[assetcsSet.count()];
		String[] titles = new String[assetcsSet.count()];

		for (int i = 0; i < assetcsSet.count(); i++) {
			IJpo csjpo = assetcsSet.getJpo(i);
			attributes[i] = csjpo.getString("ATTRIBUTENAME");
			titles[i] = getAttrTitle("ASSET", attributes[i]);
		}
		/*
		 * String[] titles = {"序号", "父级序号", "车型", "描述", "制造企业", "物料编码", "行号",
		 * "位号", "位置号", "功能位置描述", "状态", "软件名称", "软件版本", "软件更新时间", "配置号", "顶级序号",
		 * "级别"};
		 */

		// 导出当前操作的JPO 所有节点数据
		IJpoSet impSet = MroServer.getMroServer().getSysJpoSet("ASSET");
		String ancestor = this.getJpo().getString("ASSETNUM");
		impSet.setUserWhere("ancestor = '" + ancestor + "'");
		impSet.setOrderBy("assetlevel,assetid");
		impSet.reset();
		HttpSession session = request.getSession();
		session.setAttribute("TABLESET", impSet/* this.getJpoSet() */);
		session.setAttribute("ATTRIBUTES", attributes);
		session.setAttribute("TITLES", titles);
		session.setAttribute("APPNAME", "ZCPZ");
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
	 * 通过产品序列号从ERP获取BBOM
	 * 
	 * @throws MroException
	 *             [参数说明]
	 */
	public void GETERPBBOM() throws MroException {
		super.checkSave();
		parentMap = new HashMap<String, String>();// 存放产品序列号与assetnum的Map
		treeMap = new HashMap<String, String>();// 存放aseetnum与parent的Map
		noparentArray = new JSONArray();// 存放暂时找不到parent的Map
		noItemnumSet = new HashSet<JSONObject>();// 存放暂时找不到item的Map
		I_ITEM_ARRAY = new JSONArray();// 存放I类耗损件数据
		II_ITEM_ARRAY = new JSONArray();// 存放II类耗损件数据
		sqnItemnumMap = new HashMap<String, String>();// 存放产品序列号与itemnum的MAP,可以通过产品序列号快速找到对应的itemnum
		sqnItemMap = new HashMap<String, JSONObject>();// 存放产品序列号与对应数据的MAP,可以通过产品序列号快速找到对应的ITEM

		// 测试数据：500081110C
		String ancestor = this.getString("ancestor");
		String locsqn = this.getJpo().getString("sqn");
		String assettype = this.getString("type");
		if (StringUtil.isStrEmpty(locsqn)) {
			throw new AppException("assettmp", "sqnisnull");
		}
		if ("1".equals(this.getJpo().getString("ISERP"))) {
			throw new AppException("asset", "iserp");
		}
		// String sqn = "500081110C";
		// System.out.print(locsqn);
		String num = "";
		try {
			ComZzsErpZTFUN_FRACAS_CPPZStub service = new ComZzsErpZTFUN_FRACAS_CPPZStub();
			// ZTFUN_FRACAS_CPPZStub service = new ZTFUN_FRACAS_CPPZStub();
			// 认证代码 start
			Authenticator auth = new Authenticator();
			String user = IFUtil.getIfServiceInfo("erp.user");
			String pwd = IFUtil.getIfServiceInfo("erp.pwd");
			auth.setUsername(user);
			auth.setPassword(pwd);
			service._getServiceClient().getOptions()
					.setProperty(HTTPConstants.AUTHENTICATE, auth);
			// 认证代码 end
			ZtfunFracasCppz params = new ZtfunFracasCppz();
			Char20 sqnparam = new Char20();
			String sqn = locsqn.toUpperCase();
			sqnparam.setChar20(sqn);
			TableOfZfracaszppz inputtable = new TableOfZfracaszppz();
			params.setTZfracaszppz(inputtable);
			params.setZbarn(sqnparam);
			num = IFUtil.addIfHistory(IFUtil.CCPZ, sqn, IFUtil.TYPE_OUTPUT);// 增加输出记录
			// service._getServiceClient().getOptions().setTimeOutInMilliSeconds(1);
			ZtfunFracasCppzResponse re = service.ztfunFracasCppz(params);
			TableOfZfracaszppz table = re.getTZfracaszppz();
			Zfracaszppz[] zppz = table.getItem();
			if (zppz != null && zppz.length > 0) {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "获取出厂配置的序列号为:" + sqn + ";ERP返回数据结果共"
								+ zppz.length + "条;");
				JSONObject jobject = new JSONObject();
				JSONArray jArray = new JSONArray();
				for (int index = 0; index < zppz.length; index++) {
					JSONObject rdata = new JSONObject();
					String parentItemnum = zppz[index].getMatnr().toString();// 父项物料编码
					String parentsqn = zppz[index].getZbarn().toString();// 父项序列号
					String itemnum = zppz[index].getZmatnr().toString();// 组件物料编码
					String itemsqn = zppz[index].getSbarn().toString();// 组件序列号,经确认,该字段目前不会为空
					String itemdesc = zppz[index].getMaktx().toString();// 组件物料描述
					String version = zppz[index].getVersion().toString();// 软件版本
					String zplace = zppz[index].getZplace().toString();// 位置号
					String zaufnr = zppz[index].getZaufnr().toString();// 生产订单号
					String zdate = zppz[index].getZdate().toString();// 导入日期
					String ztime = zppz[index].getZtime().toString();// 导入时间
					String lifnr = zppz[index].getLifnr().toString();// 供应商帐号
					String name = zppz[index].getName1().toString();// 供应商名称
					String zgys = zppz[index].getZgys().toString();// 供应商序列号
					String bname = zppz[index].getBname().toString();// 记录人信息
					String dispo = zppz[index].getDispo().toString();// MRP控制者
					String dsnam = zppz[index].getDsnam().toString();// 控制者名称
					String fevor = zppz[index].getFevor().toString();// 生产调度员
					String txt = zppz[index].getTxt().toString();// 生产调度员
					rdata.put("parentItemnum", parentItemnum);
					rdata.put("parentsqn", parentsqn);
					rdata.put("itemnum", itemnum);
					rdata.put("itemsqn", itemsqn);
					rdata.put("itemdesc", itemdesc);
					rdata.put("version", version);
					rdata.put("zplace", zplace);
					rdata.put("zaufnr", zaufnr);
					rdata.put("zdate", zdate);
					rdata.put("ztime", ztime);
					rdata.put("lifnr", lifnr);
					rdata.put("name", name);
					rdata.put("zgys", zgys);
					rdata.put("bname", bname);
					rdata.put("dispo", dispo);
					rdata.put("dsnam", dsnam);
					rdata.put("fevor", fevor);
					rdata.put("txt", txt);

					jArray.put(rdata);
					// System.out.println("父项物料编码 :"+parentItemnum+" 父项序列号:"+parentsqn+"  组件物料编码:"+itemnum+
					// " 组件序列号:"+itemsqn+" 组件物料描述:"+itemdesc+" 软件版本:"+version+" 位置号:"+zplace);
				}
				jobject.put("sqn", sqn);
				jobject.put("ancestor", ancestor);
				jobject.put("data", jArray);
				jobject.put("assettype", assettype);
				// 处理获取的配置信息数据
				dealErpData(jobject);
				throw new AppException("asset", "getccpzs");
			} else {
				this.getJpo().setValue("MSGFLAG",
						SddqConstant.MSG_INFO_NOCHILREN);
				this.getJpo().setValue("ISERP", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				try {
					this.SAVE();
					IFUtil.addDataHistory(this.getString("ancestor"),
							"获取出厂配置的序列号为:" + sqn + ";返回数据结果为空", "2");
					IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
							IFUtil.FLAG_YES, "获取出厂配置的序列号为:" + sqn + ";返回数据结果为空");
				} catch (IOException e) {
					IFUtil.addDataHistory(this.getString("ancestor"),
							e.getMessage(), "2");
					IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_YES, e.getMessage());
					e.printStackTrace();
				}
				throw new AppException("asset", "ccpzisnull");
			}
		} catch (AxisFault e) {
			IFUtil.addDataHistory(this.getString("ancestor"), e.getMessage(),
					"2");
			IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
					e.getMessage());
			e.printStackTrace();
			throw new AppException("assettmp", "backerror");
		} catch (RemoteException e) {
			IFUtil.addDataHistory(this.getString("ancestor"), e.getMessage(),
					"2");
			IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
					e.getMessage());
			e.printStackTrace();
			throw new AppException("assettmp", "backerror");
		} catch (JSONException e) {
			IFUtil.addDataHistory(this.getString("ancestor"), e.getMessage(),
					"2");
			IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
					e.getMessage());
			e.printStackTrace();
			throw new AppException("assettmp", "backerror");
		}
	}

	/**
	 * 处理从ERP获取的数据
	 * 
	 * @param data
	 * @throws Exception
	 * @throws MroException
	 *             [参数说明]
	 * @throws Exception
	 */
	public void dealErpData(JSONObject data) throws MroException {
		String inputnum = "";
		try {
			inputnum = IFUtil.addIfHistory(IFUtil.CCPZ, data.toString(),
					IFUtil.TYPE_INPUT);// 增加输入记录
			String sqn = data.getString("sqn");
			String ancestor = data.getString("ancestor");
			JSONArray treedata = data.getJSONArray("data");
			String assettype = data.getString("assettype");
			parentMap.put(sqn, ancestor);
			treeMap.put(ancestor, "");
			if (treedata.length() > 0) {
				// 处理生成每一条数据，新增每一条记录时，都要去记录父级的assetnum
				// 还需判断是否是I类批次件，如果是I类批次件，则需要则新增数据到ASSETPART表
				IJpoSet jpoSet = this.getJpo().getJpoSet("$systemChildren",
						"ASSET", "1=2");

				for (int i = 0; i < treedata.length(); i++) {

					JSONObject rdata = treedata.getJSONObject(i);
					String parentsqn = rdata.getString("parentsqn");
					String itemnum = rdata.getString("itemnum");
					String itemsqn = rdata.getString("itemsqn");
					String itemdesc = rdata.getString("itemdesc");
					String version = rdata.getString("version");
					String zplace = rdata.getString("zplace");

					// 根据物料编码去判断物料是否是批次件追溯的，如果是序列号追溯的，则需新增数据到ASSET表中，
					// 如果是批次件追溯的还需判断是否是I类件，如果是I类件，则新增数据到ASSETPART表
					String type = ItemUtil.getItemInfo(itemnum);
					if (ItemUtil.NO_ITEM.equals(type)) {
						noItemnumSet.add(rdata);// 忽略该数据
					} else if (ItemUtil.SQN_ITEM.equals(type)) {

						IJpo jpo = jpoSet.addJpo();
						String assetnum = jpo.getString("assetnum");
						jpo.setValue("type", assettype,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("ancestor", ancestor,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("sqn", itemsqn,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("rnum", zplace,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("softversion", version,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("description", itemdesc,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("assetlevel", "SYSTEM",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("itemnum", itemnum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//
						jpo.setValue("ISERP", "1",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						parentMap.put(itemsqn, assetnum);// 增一条记录，记住序列号与assetnum的关系，放到Map中
						// 根据父级序列号,如果获得的父级序列号对应的assetnum不存在或者为空,则暂时将该数据存放在noparentMap中，最后循环一遍后，要重新处理noparentMap中的数据
						if (StringUtils.isEmpty(parentMap.get(parentsqn))) {
							JSONObject noparentdata = new JSONObject();
							noparentdata.put("assetnum", assetnum);
							noparentdata.put("parentsqn", parentsqn);
							noparentArray.put(noparentdata);
						}
						if (parentsqn != null && sqn != null
								&& sqn.equals(parentsqn)) {
							jpo.setValue("parent", ancestor,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							treeMap.put(assetnum, ancestor);// 为新增数据到assettree表中做准备
						} else {
							jpo.setValue("parent", parentMap.get(parentsqn),
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							treeMap.put(assetnum, parentMap.get(parentsqn));// 为新增数据到assettree表中做准备
						}
						treeMap.put(assetnum, parentMap.get(parentsqn));// 为新增数据到assettree表中做准备

					} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
						I_ITEM_ARRAY.put(rdata);
						sqnItemnumMap.put(itemsqn, itemnum);
						sqnItemMap.put(itemsqn, rdata);
					} else {
						II_ITEM_ARRAY.put(rdata);
					}
				}
				// 循环结束之后，为第一次循环找不到父级的数据更新父级
				for (int j = 0; j < noparentArray.length(); j++) {
					JSONObject object = noparentArray.getJSONObject(j);
					IJpo jpo = jpoSet.getJpoByValue("assetnum",
							object.getString("assetnum"));
					String parent = parentMap
							.get(object.getString("parentsqn"));// 根据父级的产品序列号找到父级的assetnum
					jpo.setValue("parent", parent,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					treeMap.put(object.getString("assetnum"), parent);// 为新增数据到assettree表中做准备
				}

				// 循环结束之后，处理I类耗损件数据，这时候获取父级的数据才准确,如果是I类耗损件,则ERP传递过来的产品序列号字段存放的是批次号
				if (I_ITEM_ARRAY.length() > 0) {

					IJpoSet assetPartJpoSet = this.getJpo().getJpoSet(
							"$ASSETPART_I", "ASSETPART", "1=2");

					for (int m = 0; m < I_ITEM_ARRAY.length(); m++) {

						JSONObject object = I_ITEM_ARRAY.getJSONObject(m);
						String parentsqn = object.getString("parentsqn");
						String itemnum = object.getString("itemnum");
						String itemsqn = object.getString("itemsqn");
						String zplace = object.getString("zplace");
						String parentItemnum = object
								.getString("parentItemnum");
						// String itemdesc = object.getString("itemdesc");
						// String version = object.getString("version");
						// String zplace = object.getString("zplace");
						// 根据父级产品序列号去找批次号的父级
						String parentAssetnum = getParent(parentsqn);
						IJpo jpo = isExistAssetPart(itemnum, itemsqn,
								parentAssetnum);
						if (jpo != null) {
							double qty = jpo.getDouble("QTY");
							double newQty = qty + 1;
							jpo.setValue("QTY", newQty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						} else {
							IJpo assetpart = assetPartJpoSet.addJpo();
							assetpart.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							assetpart.setValue("lotnum", itemsqn,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							assetpart.setValue("assetnum", parentAssetnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							assetpart.setValue("rnum", zplace,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							assetpart.setValue("QTY", 1,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							assetpart.setValue("PARENTITEMNUM", parentItemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							assetpart.setValue("PARENTSQN", parentsqn,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					}
				}
				if (II_ITEM_ARRAY.length() > 0) {

					IJpoSet assetPartJpoSet = this.getJpo().getJpoSet(
							"$ASSETPART_II", "ASSETPART", "1=2");

					for (int m = 0; m < II_ITEM_ARRAY.length(); m++) {

						JSONObject object = II_ITEM_ARRAY.getJSONObject(m);
						String parentsqn = object.getString("parentsqn");
						String itemnum = object.getString("itemnum");
						String itemsqn = object.getString("itemsqn");
						String parentItemnum = object
								.getString("parentItemnum");
						String zplace = object.getString("zplace");
						// String itemdesc = object.getString("itemdesc");
						// String version = object.getString("version");
						// String zplace = object.getString("zplace");
						// 根据父级产品序列号去找批次号的父级
						String parentAssetnum = getParent(parentsqn);
						IJpo jpo = isExistAssetPart(itemnum, itemsqn,
								parentAssetnum);
						if (jpo != null) {
							double qty = jpo.getDouble("QTY");
							double newQty = qty + 1;
							jpo.setValue("QTY", newQty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						} else {
							IJpo assetpart = assetPartJpoSet.addJpo();
							assetpart.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							assetpart.setValue("lotnum", itemsqn,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							assetpart.setValue("assetnum", parentAssetnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							assetpart.setValue("rnum", zplace,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							assetpart.setValue("QTY", 1,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							assetpart.setValue("PARENTITEMNUM", parentItemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							assetpart.setValue("PARENTSQN", parentsqn,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					}
				}
				// this.SAVE();
				// ArrayList<String> sqlList = new ArrayList<String>();
				// String deleteSql =
				// "delete from assettree where assetnum in (select assetnum from asset where ancestor = '"
				// + ancestor + "')";
				// sqlList.add(deleteSql);
				// // 遍历treeMap,生成assettree表数据
				// Iterator iter = treeMap.entrySet().iterator();
				// while (iter.hasNext()) {
				// Entry entry = (Entry) iter.next();
				// String assetnum = (String) entry.getKey();
				// String parent = (String) entry.getValue();
				// getInsertAncestorSql("ASSETTREE", sqlList, treeMap, assetnum,
				// assetnum, ancestor, 0);
				// }
				StringBuffer message = new StringBuffer();

				if (noItemnumSet != null && noItemnumSet.size() > 0) {// 输出处理的信息
					System.out.println("在MRO系统没有找到对应的物料编码:"
							+ noItemnumSet.size());
					message.append("在MRO系统没有找到对应的物料编码共" + noItemnumSet.size()
							+ "种:");
					Iterator<JSONObject> it = noItemnumSet.iterator();
					while (it.hasNext()) {
						JSONObject rdata = it.next();
						String noitemnum = rdata.getString("itemnum");
						message.append(noitemnum + ",");
					}
				}
				if (II_ITEM_ARRAY.length() > 0) {// 输出处理的信息
					System.out.println("II类耗损件" + II_ITEM_ARRAY.length());
				}
				message.append("获取的出厂数据共" + treedata.length() + "条;序列号件:"
						+ jpoSet.count() + "条;I类耗损件:" + I_ITEM_ARRAY.length()
						+ "条;II类耗损件:" + II_ITEM_ARRAY.length() + "条;");
				this.getAppBean()
						.getJpo()
						.setValue("ISERP", "1",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				IFUtil.addDataHistory(ancestor, message.toString(), "2");
				this.SAVE();
				// Connection con = DBManager.getDBManager().getConnection();
				// DBManager.getDBManager().executeBatch(con, sqlList);
				IFUtil.updateIfHistory(inputnum, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, message.toString());
			}
		} catch (JSONException e) {
			IFUtil.addDataHistory(this.getString("ancestor"), e.getMessage(),
					"2");
			IFUtil.updateIfHistory(inputnum, IFUtil.STATUS_FAILURE,
					IFUtil.FLAG_YES, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			IFUtil.addDataHistory(this.getString("ancestor"), e.getMessage(),
					"2");
			IFUtil.updateIfHistory(inputnum, IFUtil.STATUS_FAILURE,
					IFUtil.FLAG_YES, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @Description 判断在某个父级节点下，某个物料编码、批次号的数据是否存在,如果不存在，则新增，如果存在，则更新数量+1
	 * @param itemnum
	 * @param lotnum
	 * @param parent
	 * @return
	 * @throws MroException
	 */
	public IJpo isExistAssetPart(String itemnum, String lotnum, String parent)
			throws MroException {

		IJpoSet assetPartJpoSet = MroServer.getMroServer().getSysJpoSet(
				"ASSETPART",
				"itemnum='" + itemnum + "' and lotnum='" + lotnum
						+ "' and assetnum='" + parent + "'");
		if (assetPartJpoSet != null && assetPartJpoSet.count() > 0) {
			return assetPartJpoSet.getJpo(0);
		}
		return null;
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
					+ "ASSETTREEID,ASSETNUM,ANCESTOR,SITEID,ORGID,HIERARCHYLEVELS)";
			insertSql = insertSql + " values (ASSETTREESEQ.NEXTVAL,'"
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
						+ "ASSETTREEID,ASSETNUM,ANCESTOR,SITEID,ORGID,HIERARCHYLEVELS)";
				insertSql = insertSql + " values (ASSETTREESEQ.NEXTVAL,'"
						+ assetnum + "','" + parent + "','ELEC','CRRC'," + i
						+ ")";
				sqlList.add(insertSql);
			}
		}
		return sqlList;
	}

	/**
	 * 获取批次号件的父级
	 * 
	 * @param parentsqn
	 * @return
	 * @throws JSONException
	 * @throws Exception
	 *             [参数说明]
	 */
	public String getParent(String parentsqn) throws JSONException {
		String parentItem = sqnItemnumMap.get(parentsqn);
		if (StringUtils.isNotEmpty(parentItem)) {
			JSONObject object = sqnItemMap.get(parentsqn);
			return getParent(object.getString("parentsqn"));
		} else {
			return parentMap.get(parentsqn);
		}
		// String parentAssetnum = parentMap.get(parentsqn);
	}

	/**
	 * @Description 获取整车SBOM的一级配置及虚拟节点（如：网络）的下级配置
	 * @throws MroException
	 */
	public void GETCSBOM() throws MroException {
		super.checkSave();
		String zcsbomnum = this.getJpo().getString("ZCSBOMNUM");
		String ancestor = this.getJpo().getString("ANCESTOR");
		if (StringUtil.isStrEmpty(zcsbomnum)) {
			throw new AppException("asset", "zcsbomisnull");
		}
		if (this.getBoolean("HASCS")) {
			throw new AppException("asset", "hascs");
		}
		try {
			// 首先找到车厢信息
			IJpoSet zcJpoSet = MroServer.getMroServer().getSysJpoSet("ASSETCS",
					"assetcslevel = 'CAR' and ANCESTOR = '" + zcsbomnum + "'");
			if (zcJpoSet != null && zcJpoSet.count() > 0) {
				IJpoSet assetJpoSet = this.getJpo().getJpoSet("$addAsset",
						"ASSET", "1=2");
				for (int index = 0; index < zcJpoSet.count(); index++) {
					IJpo csCarJpo = zcJpoSet.getJpo(index);
					String assetcsnum = csCarJpo.getString("ASSETCSNUM");
					String carriagenum = csCarJpo.getString("carriagenum");
					IJpo carJpo = assetJpoSet.addJpo();
					carJpo.setValue("carriagenum", carriagenum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					carJpo.setValue("ancestor", ancestor,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					carJpo.setValue("ASSETLEVEL", "CAR",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					carJpo.setValue("PARENT", ancestor,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					carJpo.setValue("TYPE", 2,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					carJpo.setValue("itemnum", SddqConstant.CAR_ITEMNUM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					String assetnum = carJpo.getString("assetnum");
					// 找到车厢下的一级节点，同时判断该节点是不是虚拟件，如果是虚拟件则继续找到该节点下的下一层节点
					addChildAsset(assetcsnum, assetJpoSet, ancestor, assetnum);
				}
				this.getJpo().setValue("HASCS", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.SAVE();
				throw new AppException("asset", "getcstrue");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description 新增子级节点
	 * @param parent
	 * @throws MroException
	 */
	public void addChildAsset(String parent, IJpoSet assetJpoSet,
			String ancestor, String assetnum) throws MroException {

		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETCS",
				"PARENT= '" + parent + "'");
		if (jposet != null && jposet.count() > 0) {
			for (int i = 0; i < jposet.count(); i++) {
				IJpo jpo = jposet.getJpo(i);
				String itemnum = jpo.getString("itemnum");
				IJpo sysJpo = assetJpoSet.addJpo();
				sysJpo.setValue("ancestor", ancestor,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				sysJpo.setValue("parent", assetnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				sysJpo.setValue("assetlevel", "SYSTEM",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				sysJpo.setValue("TYPE", 2,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				sysJpo.setValue("itemnum", itemnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				sysJpo.setValue("DESCRIPTION", jpo.getString("DESCRIPTION"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				sysJpo.setValue("LOC", jpo.getString("LOC"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				sysJpo.setValue("LOCDESC", jpo.getString("LOCDESC"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				sysJpo.setValue("LINENUM", jpo.getString("LINENUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				sysJpo.setValue("RNUM", jpo.getString("RNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				sysJpo.setValue("CONFIGNUM", jpo.getString("CONFIGNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				// 判断该物料是否是虚拟件
				IJpo itemJpo = ItemUtil.getItem(itemnum);
				if (itemJpo != null && itemJpo.getBoolean("ISIV")) {
					addChildAsset(jpo.getString("assetcsnum"), assetJpoSet,
							ancestor, sysJpo.getString("assetnum"));
				}
			}
		}
	}

	/**
	 * 
	 * @Description 选择产品
	 * @return
	 * @throws MroException
	 */
	public int SELECTPRO() throws MroException {

		DataBean databean = this.getDataBean("1375344856389");
		if (databean != null) {
			TreeBean sbomTreeBean = (TreeBean) databean;
			TreeNode treenode = sbomTreeBean.getCurrNode();
			if (treenode != null) {
				// 判断节点是不是顶层节点，如果是顶层节点，则弹出不可添加节点以及选择产品的提示信息
				String assetlevel = treenode.getJpo().getString("assetlevel");
				if ("ASSET".equals(assetlevel)) {
					throw new AppException("asset", "selectAsset");
				}
				return GWConstant.ACCESS_SAMEMETHOD;
			} else {
				throw new AppException("asset", "selectNoAsset");
			}
		} else {
			throw new AppException("asset", "selectNoAsset");
		}
	}

	/**
	 * 
	 * @Description 添加节点
	 * @return
	 * @throws MroException
	 */
	public int ADDPRO() throws MroException {

		if ("ZCCONFIG".equals(this.getAppName())) {
			DataBean databean = this.getDataBean("1375344856389");
			if (databean != null) {
				TreeBean sbomTreeBean = (TreeBean) databean;
				TreeNode treenode = sbomTreeBean.getCurrNode();
				if (treenode != null) {
					// 判断节点是不是顶层节点，如果是顶层节点，则弹出不可添加节点以及选择产品的提示信息
					String assetlevel = treenode.getJpo().getString(
							"assetlevel");
					if ("ASSET".equals(assetlevel)) {
						throw new AppException("asset", "selectAsset");
					}
					// 判断节点是不是顶层节点，如果是顶层节点，则弹出不可添加节点以及选择产品的提示信息
					return GWConstant.ACCESS_SAMEMETHOD;
				} else {
					throw new AppException("asset", "selectNoAsset");
				}
			} else {
				throw new AppException("asset", "selectNoAsset");
			}
		}
		if ("CXCONFIG".equals(this.getAppName())) {
			DataBean databean = this.getDataBean("1375344856389");
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
		if ("SSCONFIG".equals(this.getAppName())) {
			DataBean databean = this.getDataBean("1375344856389");
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
		return GWConstant.ACCESS_SAMEMETHOD;
	}

	/**
	 * 
	 * 获取产品SBOM
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * @throws IOException
	 * 
	 */
	public int GETCPSBOM() throws MroException, IOException {

		if ("CXCONFIG".equals(this.getAppName())) {
			DataBean databean = this.getDataBean("1375344856389");
			if (databean != null) {
				TreeBean sbomTreeBean = (TreeBean) databean;
				TreeNode treenode = sbomTreeBean.getCurrNode();
				if (treenode != null) {
					String itemnum = treenode.getJpo().getString("itemnum");// 物料编码
					String assetnum = treenode.getJpo().getString("assetnum");
					IJpoSet assetjposet = MroServer.getMroServer()
							.getSysJpoSet("ASSET");
					assetjposet.setUserWhere("PARENT ='" + assetnum + "'");
					assetjposet.reset();
					if (assetjposet != null && assetjposet.count() > 0) {
						throw new AppException("assettmp", "cantAddNode");
					}
					IJpoSet jposet = MroServer.getMroServer().getSysJpoSet(
							"ASSETTMP");
					jposet.setUserWhere("ITEMNUM ='" + itemnum
							+ "' and assettmplevel = 'ASSET' and STATUS='可用'");
					jposet.setOrderBy("version desc");
					jposet.reset();
					if (jposet != null && jposet.count() > 0) {
						IJpo assettmpJpo = jposet.getJpo(0);
						String installNum = assettmpJpo.getString("ancestor");
						String toAncestor = treenode.getJpo().getString(
								"ancestor");
						String toParent = treenode.getJpo().getString(
								"ASSETNUM");
						String rassettmpnum = getMaxnum(toAncestor, toParent);
						Connection conn = this.getMroSession().getUserServer()
								.getConnection();
						try {
							// String assetCsSql = "insert into asset cs "
							// +
							// "(ASSETNUM, PARENT,ANCESTOR,NUM,PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK,ASSETID, ASSETLEVEL, CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO, LANGCODE, LOCATION,MODEL,ORGID,SITEID,STATUS,STATUSDATE,TYPE,VERSION,SECRETLEVEL,SECRETLEVELDES) "
							// + "(select replace(ASSETTMPNUM,'"
							// + StringUtil.getSafeSqlStr(installNum)
							// + "','"
							// + StringUtil.getSafeSqlStr(rassettmpnum)
							// + "'),replace(PARENT,'"
							// + StringUtil.getSafeSqlStr(installNum)
							// + "','"
							// + StringUtil.getSafeSqlStr(rassettmpnum)
							// + "'),'"
							// + StringUtil.getSafeSqlStr(toAncestor)
							// + "',replace(NUM,'"
							// + StringUtil.getSafeSqlStr(installNum)
							// + "','"
							// + StringUtil.getSafeSqlStr(rassettmpnum)
							// +
							// "'),PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK, ASSETSEQ.NEXTVAL,'SYSTEM', CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO,'"
							// +
							// StringUtil.getSafeSqlStr(this.getUserInfo().getDefaultLang())
							// +
							// "', LOCATION, MODEL, ORGID,  SITEID, STATUS, STATUSDATE, TYPE, VERSION,0,'非密' from assettmp "
							// + " where  ANCESTOR='" +
							// StringUtil.getSafeSqlStr(installNum) +
							// "' and assettmpnum = '"
							// + StringUtil.getSafeSqlStr(installNum) + "')";

							String assetChildrenCsSql1 = "insert into ASSET cs "
									+ "(ASSETNUM, PARENT,ANCESTOR,NUM,PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK,ASSETID, ASSETLEVEL, CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO, LANGCODE, LOCATION,MODEL,ORGID,SITEID,STATUS,STATUSDATE,TYPE,VERSION,SECRETLEVEL,SECRETLEVELDES) "
									+ "(select concat('"
									+ StringUtil.getSafeSqlStr(rassettmpnum)
									+ ".',ASSETTMPNUM),'"
									+ StringUtil.getSafeSqlStr(toParent)
									+ "','"
									+ StringUtil.getSafeSqlStr(toAncestor)
									+ "',concat('"
									+ StringUtil.getSafeSqlStr(rassettmpnum)
									+ ".',NUM),PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK, ASSETSEQ.NEXTVAL,'SYSTEM', CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO,'"
									+ StringUtil.getSafeSqlStr(this
											.getUserInfo().getDefaultLang())
									+ "', LOCATION, MODEL, ORGID,  SITEID, STATUS, STATUSDATE, '3', VERSION,0,'非密' from assettmp "
									+ " where  ANCESTOR='"
									+ StringUtil.getSafeSqlStr(installNum)
									+ "' and parent = '"
									+ StringUtil.getSafeSqlStr(installNum)
									+ "')";

							String assetChildrenCsSql2 = "insert into ASSET cs "
									+ "(ASSETNUM, PARENT,ANCESTOR,NUM,PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK,ASSETID, ASSETLEVEL, CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO, LANGCODE, LOCATION,MODEL,ORGID,SITEID,STATUS,STATUSDATE,TYPE,VERSION,SECRETLEVEL,SECRETLEVELDES) "
									+ "(select concat('"
									+ StringUtil.getSafeSqlStr(rassettmpnum)
									+ ".',ASSETTMPNUM),"
									+ "concat('"
									+ StringUtil.getSafeSqlStr(rassettmpnum)
									+ ".',PARENT),'"
									+ StringUtil.getSafeSqlStr(toAncestor)
									+ "',concat('"
									+ StringUtil.getSafeSqlStr(rassettmpnum)
									+ ".',NUM),PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK, ASSETSEQ.NEXTVAL,'SYSTEM', CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO,'"
									+ StringUtil.getSafeSqlStr(this
											.getUserInfo().getDefaultLang())
									+ "', LOCATION, MODEL, ORGID,  SITEID, STATUS, STATUSDATE, '3', VERSION,0,'非密' from assettmp "
									+ " where  ANCESTOR='"
									+ StringUtil.getSafeSqlStr(installNum)
									+ "' and parent != '"
									+ StringUtil.getSafeSqlStr(installNum)
									+ "')";

							// 更新装机顶层节点的父级节点
							// String updatesql1 = "update ASSET set parent ='"
							// + toParent +
							// "',ASSETlevel='SYSTEM' where ASSETNUM='" +
							// rassettmpnum + "'";

							// 复制服务备件清单数据
							String insertAssetModelPartSql = "insert into ASSETPART t (ASSETPARTID,ASSETNUM,ITEMNUM,QTY,ORGID,SITEID,RNUM)(select ASSETPARTSEQ.NEXTVAL,(case when ASSETNUM ='"
									+ StringUtil.getSafeSqlStr(installNum)
									+ "' then '"
									+ StringUtil.getSafeSqlStr(rassettmpnum)
									+ "' else to_char(concat('"
									+ StringUtil.getSafeSqlStr(rassettmpnum)
									+ ".',ASSETNUM)) end),ITEMNUM,QTY,ORGID,SITEID,WNUM from ASSETMODELPART where assetnum in (select ASSETTMPNUM from assettmp where ancestor = '"
									+ installNum + "'))";

							DBManager.getDBManager().executeBatch(
									conn,
									new String[] { assetChildrenCsSql1,
											assetChildrenCsSql2,
											insertAssetModelPartSql });
						} catch (SQLException e) {
							System.out.println(e);
							throw new AppException("assettmp", "backerror");
						} finally {
							try {
								if (conn != null) {
									conn.close();
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					} else {
						throw new AppException("ASSET", "noSBOM");
					}
				} else {
					throw new AppException("asset", "selectNoAsset");
				}
			} else {
				throw new AppException("asset", "selectNoAsset");
			}
		}

		return super.getAppBean().SAVE();
	}

	public String getMaxnum(String ancestor, String parent) throws MroException {
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET");
		jposet.setUserWhere("parent = '" + parent + "' and ancestor = '"
				+ ancestor + "'");
		jposet.reset();
		int max = 0;
		for (int index = 0; index < jposet.count(); index++) {
			IJpo jpo = jposet.getJpo(index);
			String assettmpnum = jpo.getString("assetnum");
			if (assettmpnum.lastIndexOf(".") < 0) {
				if (max == 0) {
					max = 1;
				}
			} else {
				String num = assettmpnum
						.substring(assettmpnum.lastIndexOf(".") + 1);
				if (Integer.valueOf(num) + 1 > max) {
					max = Integer.valueOf(num) + 1;
				}
			}
		}
		return String.valueOf(parent + "." + max);
	}
	
	/**
	 * 
	 * 节点删除
	 * @return
	 * @throws MroException
	 * @throws IOException [参数说明]
	 *
	 */
	public int NODEDELETE() throws MroException, IOException {
		TreeNode treeNode = (TreeNode)this.getPage().getControlByXmlId("13753448563892");
		if(null!=treeNode){
			
			Asset treeJpo =(Asset)treeNode.getTreeBean().getCurrNode().getJpo();
			if(null!=treeJpo){
				deleteAssetAncestor(treeJpo);
			}
		}else{
			// 提示先选择节点
		}
		
		return SAVE();
		}

	/**
	 * 
	 * 删除配置及其结构信息
	 * @param asset
	 * @throws MroException [参数说明]
	 *
	 */
	private void deleteAssetAncestor(Asset asset) throws MroException {

		String assetnum = asset.getString("ASSETNUM");
		Connection conn = asset.getUserServer().getConnection();
		String delePartSql = "delete from ASSETPART where assetnum in (select assetnum from asset start with assetnum ='"
				+ assetnum + "' connect by parent = PRIOR assetnum)";
		String deleSql = "delete from ASSET where assetnum in (select assetnum from asset start with assetnum ='"
				+ assetnum + "' connect by parent = PRIOR assetnum)";
		try {
			DBManager.getDBManager().executeBatch(conn,
					new String[] { delePartSql, deleSql });
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
