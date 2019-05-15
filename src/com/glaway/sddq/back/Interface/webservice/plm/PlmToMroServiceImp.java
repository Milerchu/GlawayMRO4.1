package com.glaway.sddq.back.Interface.webservice.plm;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.app.system.role.data.Role;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.WorkorderUtil;

@WebService(endpointInterface = "com.glaway.sddq.back.Interface.webservice.plm.PlmToMroService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class PlmToMroServiceImp implements PlmToMroService {

	/**
	 * MBOM变更数据传递
	 * 
	 * @param jsonData
	 * @return
	 */
	@Override
	public String toMroMbomUpdateData(String jsonData) {

		System.out.println("jsonData:" + jsonData);
		String num = "";
		try {
			num = IFUtil.addIfHistory(IFUtil.MBOM_U, jsonData,
					IFUtil.TYPE_INPUT);
			if (jsonData != null) {

				JSONObject json = JSONObject.parseObject(jsonData);
				if (json != null) {
					MroServer.getMroServer().getSystemUserServer()
							.getUserInfo().setDefaultOrg("CRRC");
					MroServer.getMroServer().getSystemUserServer()
							.getUserInfo().setDefaultSite("ELEC");

					IJpoSet sbomUpdateJpoSet = MroServer.getMroServer()
							.getSysJpoSet("SBOMUPDATE", "1=2");
					// 新增变更记录
					IJpo sbomupdateJpo = sbomUpdateJpoSet.addJpo();

					sbomupdateJpo
							.setValue("subject", json.getString("subject"));
					sbomupdateJpo
							.setValue("content", json.getString("content"));
					sbomupdateJpo.setValue("link", json.getString("link"));
					sbomupdateJpo.setValue("changeNumber",
							json.getString("changeNumber"));

					List<String> userList = new ArrayList<String>();
					String rolenum = "SBOMUSER";
					IJpoSet roleSet = MroServer.getMroServer().getSysJpoSet(
							"sys_role", "maxrole='" + rolenum + "'");
					Role roleJpo = (Role) roleSet.getJpo(0);
					roleJpo.getPersonByRole(roleJpo, userList);
					IJpoSet msgJposet = MroServer.getMroServer().getSysJpoSet(
							"MSGMANAGE", "1=2");
					long id = sbomupdateJpo.getId();
					if (userList != null && userList.size() > 0) {
						for (int i = 0; i < userList.size(); i++) {
							IJpo msgjpo = msgJposet.addJpo();
							msgjpo.setValue("appid", id);
							msgjpo.setValue("app", "SBOMUPDATE");
							msgjpo.setValue("msgnum", "");
							msgjpo.setValue("subject",
									json.getString("subject"));
							msgjpo.setValue("content",
									json.getString("content"));
							msgjpo.setValue("receiver", userList.get(i));
						}
					}
					sbomUpdateJpoSet.save();
					msgJposet.save();
					IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
							IFUtil.FLAG_YES, "");
				}
			}
		} catch (Exception e) {
			try {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, e.getMessage());
			} catch (MroException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	public String toMroSoftValirebill(String softXml) {
		String msg = "true";
		if (softXml != null) {

			String num = "";

			try {
				num = IFUtil.addIfHistory("PLM_MRO_SOFT", softXml,
						IFUtil.TYPE_INPUT);

				IJpoSet valirequestset = MroServer.getMroServer().getSysJpoSet(
						"VALIREQUEST");

				MroServer.getMroServer().getSystemUserServer().getUserInfo()
						.setDefaultOrg("CRRC");
				MroServer.getMroServer().getSystemUserServer().getUserInfo()
						.setDefaultSite("ELEC");
				Document doc = DocumentHelper.parseText(softXml);
				Element root = doc.getRootElement();

				String plmtovailrenum = root.valueOf("number");// PLM申请编号
				String appdept = root.valueOf("sqdw");// 申请单位
				String appperson = root.valueOf("sqry");// 申请人
				String workordernum = root.valueOf("gzlh");// 工作令号
				String valisoft = root.valueOf("dyzmc");// 待验证软名称
				String snumber = root.valueOf("rjbh");// 软件编号
				String installpage = root.valueOf("azbmc");// 安装包名称
				String download = root.valueOf("download");// 验证软件包
				String appdate = root.valueOf("sqsj");// 申请时间
				String transtype = root.valueOf("yzxz");// 验证性质
				String valireason = root.valueOf("yzsqyy");// 验证原因描述
				String valicontext = root.valueOf("yznr");// 验证内容描述
				String plantouse = root.valueOf("dqyzpbczy");// 到期是否装用
				String perverisonstatus = root.valueOf("sybbclzt");// 上一版处理状态
				String perverisonmemo = root.valueOf("sybbsjclyj");// 上一版本处理意见
				String emergentstep = root.valueOf("ycqkyjclqk");// 异常情况应急处理措施
				String valiattention = root.valueOf("yzzysx");// 验证注意事项
				String specialreq = root.valueOf("sfcztp");// 是否存在特批
				String backlogsoft = root.valueOf("kghfrjbb");// 可供恢复软件版本
				String projectname = root.valueOf("xmmc");// 项目名称

				valirequestset.setUserWhere("VALIREQUESTNUM like '"
						+ plmtovailrenum + "%'");
				valirequestset.reset();

				IJpo valirequestupdatesetJpo = null;
				// zzx add
				if (valirequestset.count() > 0) {// 已经创建了申请单
					valirequestset.setOrderBy("valirequestid desc");
					valirequestset.reset();
					IJpo valireq = valirequestset.getJpo(0);
					String valiNum = valireq.getString("VALIREQUESTNUM");
					if (valiNum.indexOf("-") > -1) {// 已经有子序号

						int snum = Integer.parseInt(valiNum.substring(valiNum
								.indexOf("-") + 1)) + 1;// 获取序号
						plmtovailrenum = plmtovailrenum + "-" + snum;

					} else {
						plmtovailrenum = plmtovailrenum + "-1";
					}
				}
				valirequestupdatesetJpo = valirequestset.addJpo();

				String apppersonone = URLDecoder.decode(appperson, "gb2312");

				String apppersononenew = URLDecoder.decode(appperson, "gb2312")
						.substring(apppersonone.indexOf("(") + 1,
								apppersonone.indexOf(")"));

				valirequestupdatesetJpo.setValue("APPPERSON", apppersononenew,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 申请人

				valirequestupdatesetJpo.setValue("VALIREQUESTNUM",
						plmtovailrenum);// 编号
				valirequestupdatesetJpo.setValue("STATUS", "已审核");// 状态

				valirequestupdatesetJpo.setValue("APPSERVERDEPART",
						URLDecoder.decode(appdept, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 申请单位
				valirequestupdatesetJpo.setValue("VALIREASON",
						URLDecoder.decode(valireason, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 验证原因描述
				valirequestupdatesetJpo.setValue("WORKORDERNUM",
						URLDecoder.decode(workordernum, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 工作令号

				valirequestupdatesetJpo.setValue("INSTALLPAGE",
						URLDecoder.decode(installpage, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 安装包名称

				valirequestupdatesetJpo.setValue("SOFTLINK",
						URLDecoder.decode(download, "gb2312"));// 验证软件包
				valirequestupdatesetJpo.setValue("APPDATE",
						URLDecoder.decode(appdate, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 申请时间

				valirequestupdatesetJpo.setValue("VALISOFT",
						URLDecoder.decode(valisoft, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 待验证软件名称
				valirequestupdatesetJpo.setValue("SNUMBER", snumber);// 软件编号

				valirequestupdatesetJpo.setValue("TRANSTYPE",
						URLDecoder.decode(transtype, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 验证性质

				valirequestupdatesetJpo.setValue("VALICONTEXT",
						URLDecoder.decode(valicontext, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 验证内容描述

				valirequestupdatesetJpo.setValue("VALIATTENTION",
						URLDecoder.decode(valiattention, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 验证注意事项

				String plantousenew = URLDecoder.decode(plantouse, "gb2312");
				if (plantousenew.equals("是")) {
					valirequestupdatesetJpo.setValue("PLANTOUSE", "1",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 到期验证品是否装用
				} else {
					valirequestupdatesetJpo.setValue("PLANTOUSE", "0",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 到期验证品是否装用
				}

				valirequestupdatesetJpo.setValue("PERVERISONSTATUS",
						URLDecoder.decode(perverisonstatus, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 上一版处理状态

				valirequestupdatesetJpo.setValue("PERVERISONMEMO",
						URLDecoder.decode(perverisonmemo, "gb2312"));// 上一版处理意见

				valirequestupdatesetJpo.setValue("EMERGENTSTEP",
						URLDecoder.decode(emergentstep, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 异常情况应急处理措施

				String specialreqnew = URLDecoder.decode(specialreq, "gb2312");
				if (specialreqnew.equals("是")) {
					valirequestupdatesetJpo.setValue("SPECIALREQ", "1",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 是否存在特批
				} else {
					valirequestupdatesetJpo.setValue("SPECIALREQ", "0",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 是否存在特批
				}
				valirequestupdatesetJpo.setValue("BACKLOGSOFT",
						URLDecoder.decode(backlogsoft, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 可供恢复版本

				valirequestupdatesetJpo.setValue("PROJECTNAME",
						URLDecoder.decode(projectname, "gb2312"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 公司项目名称

				// 先删除原先已经存在的数据
				/*
				 * IJpoSet valiprorangeTmp =
				 * valirequestset.getJpo().getJpoSet("VALIPRORANGE");
				 * valiprorangeTmp.deleteAll();
				 */
				// 验证范围jposet
				IJpoSet valiprorangenewset = valirequestupdatesetJpo.getJpoSet(
						"$PLMVALIPRORANGE", "PLMVALIPRORANGE", "1=2");

				List<Element> yzxgsmList = root.elements("yzxgsm");
				for (Element yzxgsm : yzxgsmList) {

					String ownercustomer = yzxgsm.valueOf("ljs");// 客户名称
					String plmtransmodels = yzxgsm.valueOf("sjjcbh");// 涉及车型
					String plmtransmodelsname = yzxgsm.valueOf("sjjc");// 车型项目
					String productcode = yzxgsm.valueOf("cpmcxh");// 产品名称
					String partcode = yzxgsm.valueOf("dbbh");// 单板编号
					String chipdesc = yzxgsm.valueOf("xpxh");// 芯片型号
					String chiploc = yzxgsm.valueOf("xpwz");// 芯片位置
					String valicountStr = URLDecoder.decode(
							yzxgsm.valueOf("yzsl"), "gb2312").trim();// 验证数量str
					String regEx = "[^0-9]";
					Matcher m = Pattern.compile(regEx).matcher(valicountStr);
					String valicount = m.replaceAll("").trim();// 验证数量

					String valicountUnit = String.valueOf(valicountStr
							.charAt(valicountStr.length() - 1));// 验证数量单位

					String period = yzxgsm.valueOf("yzzq");// 验证周期
					String periodunit = yzxgsm.valueOf("yzzqdw");// 验证周期单位
					String remark = yzxgsm.valueOf("bz");// 备注
					// 验证范围子表增加数据
					IJpo valiprorangenewsetJpo = valiprorangenewset.addJpo();
					valiprorangenewsetJpo.setValue("VALIREQUESTNUM",
							plmtovailrenum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 申请单编号
					valiprorangenewsetJpo.setValue("OWNERCUSTOMER",
							URLDecoder.decode(ownercustomer, "gb2312"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 机务段
					valiprorangenewsetJpo.setValue("MODELS",
							URLDecoder.decode(plmtransmodels, "gb2312"));// 涉及车型
					valiprorangenewsetJpo.setValue("MODELPROJECT",
							URLDecoder.decode(plmtransmodelsname, "gb2312"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 车型项目

					valiprorangenewsetJpo.setValue("PRODUCTCODENAMENUM",
							URLDecoder.decode(productcode, "gb2312"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 产品名称
					valiprorangenewsetJpo.setValue("VALICOUNT", valicount,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 验证数量
					valiprorangenewsetJpo.setValue("VALICOUNTUNIT",
							valicountUnit,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 验证数量单位
					valiprorangenewsetJpo.setValue("PARTCODE",
							URLDecoder.decode(partcode, "gb2312"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 单板编号
					valiprorangenewsetJpo.setValue("CHIPDESC",
							URLDecoder.decode(chipdesc, "gb2312"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 芯片型号
					valiprorangenewsetJpo.setValue("CHIPLOC",
							URLDecoder.decode(chiploc, "gb2312"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 芯片位置
					valiprorangenewsetJpo.setValue("PERIOD",
							URLDecoder.decode(period, "gb2312"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 验证周期
					valiprorangenewsetJpo.setValue("PERIODUNIT",
							URLDecoder.decode(periodunit, "gb2312"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 验证周期单位
					valiprorangenewsetJpo.setValue("REMARK",
							URLDecoder.decode(remark, "gb2312"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 备注

				}
				valirequestset.save();

				// 创建改造计划
				IJpoSet vrSet = MroServer.getMroServer().getSysJpoSet(
						"VALIREQUEST",
						"valirequestnum='" + plmtovailrenum + "'");
				WorkorderUtil.createValiPlan(vrSet.getJpo(0));

				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "");

			} catch (Exception e) {
				e.printStackTrace();
				// 处理失败
				try {
					IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_YES, e.getMessage());
					String retruntmp = e.getMessage();
					msg = retruntmp;
				} catch (MroException e1) {
					e1.printStackTrace();

				}

			}

		}
		return msg;

	}

	@Override
	public String toMroSoft(String itemnum, String softinfoxml) {

		try {

			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultOrg("CRRC");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultSite("ELEC");
			// 获取软件信息
			IJpoSet softJposet = MroServer.getMroServer().getSysJpoSet(
					"SOFTCONFIG");
			IJpo soft = softJposet.addJpo();
			String softconfignum = soft.getString("SOFTCONFIGNUM");

			// 单板直接父项列表
			IJpoSet softitemJposet = MroServer.getMroServer().getSysJpoSet(
					"SOFTITEM");
			IJpo softitem = softitemJposet.addJpo();
			String softitemnum = softitem.getString("SOFTITEMNUM");
			softitem.setValue("SOFTCONFIGNUM", softconfignum);

			// 单板信息列表
			IJpoSet softitemxpJposet = MroServer.getMroServer().getSysJpoSet(
					"SOFTITEMXP");
			IJpo softitemxp = softitemxpJposet.addJpo();
			String softitemxpnum = softitemxp.getString("SOFTITEMXPNUM");
			softitemxp.setValue("SOFTCONFIGNUM", softconfignum);
			softitemxp.setValue("softitemnum", softitemnum);
			// 履历表软件表
			IJpoSet softwarerecordrjJposet = MroServer.getMroServer()
					.getSysJpoSet("SOFTWARERECORDRJ");
			IJpo softwarerecordrjJpo = softwarerecordrjJposet.addJpo();
			softwarerecordrjJpo.setValue("softitemxpnum", softitemxpnum);
			softwarerecordrjJpo.setValue("SOFTCONFIGNUM", softconfignum);
			softwarerecordrjJpo.setValue("softitemnum", softitemnum);

		} catch (MroException e) {
			e.printStackTrace();
		}

		return null;
	}
}
