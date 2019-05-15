package com.glaway.sddq.material.materreq.data;

import java.util.Date;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.EmailUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * <配件申请状态字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldStatus extends JpoField {
	/**
	 * 状态变更触发生成装箱单通知创建人
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		IJpo mr = this.getJpo();
		String MRTYPE = mr.getString("MRTYPE");
		if (MRTYPE.equalsIgnoreCase("零星")) {
			// 调用生成装箱单方法
			ADDTRANSFER();
			// 调用通知创建人方法
			CALLCREATEBY();
		}
	}

	/**
	 * 通知创建人 <功能描述>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void CALLCREATEBY() throws MroException {
		IJpo mr = this.getJpo();
		String mrnum = mr.getString("mrnum");
		String mrid = mr.getString("mrid");
		String orgid = mr.getString("orgid");
		String siteid = mr.getString("siteid");
		String CREATEBY = mr.getString("CREATEBY");
		// String appname=mr.getAppName();
		Date createdate = MroServer.getMroServer().getDate();

		IJpoSet mrlinetransferkeeperset = MroServer.getMroServer().getJpoSet(
				"MRLINETRANSFER",
				MroServer.getMroServer().getSystemUserServer());
		mrlinetransferkeeperset.setQueryWhere("mrnum='" + mrnum
				+ "' and TRANSTYPE='退回申请人' and calltrans='否'");
		if (!mrlinetransferkeeperset.isEmpty()) {
			IJpoSet MSGMANAGEset = MroServer.getMroServer()
					.getJpoSet("MSGMANAGE",
							MroServer.getMroServer().getSystemUserServer());
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultOrg("CRRC");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultSite("ELEC");
			for (int i = 0; i < mrlinetransferkeeperset.count(); i++) {
				String itemnum = mrlinetransferkeeperset.getJpo(i).getString(
						"itemnum");
				String REMARK = mrlinetransferkeeperset.getJpo(i).getString(
						"REMARK");
				IJpo MSGMANAGE = MSGMANAGEset.addJpo();
				MSGMANAGE.setValue("app", "MATERREQ",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				MSGMANAGE.setValue("appid", mrid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				MSGMANAGE.setValue("content", "编号为：'" + mrnum + "'的配件申请中编号为:"
						+ itemnum + "的物料被计划经理退回，退回原因为:" + REMARK + "！",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				MSGMANAGE.setValue("createdate", createdate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				MSGMANAGE.setValue("msgnum", mrid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				MSGMANAGE.setValue("receiver", CREATEBY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				MSGMANAGE.setValue("orgid", orgid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				MSGMANAGE.setValue("siteid", siteid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				MSGMANAGE.setValue("status", "新增",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				MSGMANAGE.setValue("subject", "配件申请申请物料退回申请人通知",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			MSGMANAGEset.save();
			IJpoSet mrlinetransferset = mr.getJpoSet("mrlinetransfer");
			mrlinetransferset
					.setQueryWhere("TRANSTYPE='退回申请人' and calltrans='否'");
			mrlinetransferset.reset();
			if (!mrlinetransferset.isEmpty()) {
				for (int j = 0; j < mrlinetransferset.count(); j++) {
					IJpo mrlinetransfer = mrlinetransferset.getJpo(j);
					mrlinetransfer.setValue("calltrans", "是",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

				}
			}
		}
	}

	/**
	 * 
	 * <创建装箱单>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void ADDTRANSFER() throws MroException {
		IJpo mr = this.getJpo();
		String mrnum = mr.getString("mrnum");
		String RECEIVESTOREROOM = mr.getString("RECEIVESTOREROOM");
		String RECEIVEADDRESS = mr.getString("RECEIVEADDRESS");
		String orgid = mr.getString("orgid");
		String siteid = mr.getString("siteid");
		Date createdate = MroServer.getMroServer().getDate();
		IJpoSet mrlinetransferkeeperset = MroServer.getMroServer().getJpoSet(
				"sys_person", MroServer.getMroServer().getSystemUserServer());
		mrlinetransferkeeperset
				.setQueryWhere("personid in (select keeper from mrlinetransfer where mrnum='"
						+ mrnum
						+ "' and TRANSTYPE in ('"
						+ ItemUtil.TRANSTYPE_XCDB
						+ "','"
						+ ItemUtil.TRANSTYPE_NBXT
						+ "') and ISCREATEZXTRANSFER='否')");
		if (!mrlinetransferkeeperset.isEmpty()) {
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultOrg("CRRC");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultSite("ELEC");
			for (int i = 0; i < mrlinetransferkeeperset.count(); i++) {
				IJpo mrlinetransferkeeper = mrlinetransferkeeperset.getJpo(i);
				String personid = mrlinetransferkeeper.getString("personid");
				IJpoSet storoommrlinetransferset = MroServer.getMroServer()
						.getJpoSet("mrlinetransfer",
								MroServer.getMroServer().getSystemUserServer());
				storoommrlinetransferset.setUserWhere("mrnum='" + mrnum
						+ "' and TRANSTYPE in ('" + ItemUtil.TRANSTYPE_XCDB
						+ "','" + ItemUtil.TRANSTYPE_NBXT + "') and keeper='"
						+ personid + "'");
				String STOREROOM = storoommrlinetransferset.getJpo(0)
						.getString("STOREROOM");

				IJpoSet transferset = MroServer.getMroServer().getJpoSet(
						"transfer",
						MroServer.getMroServer().getSystemUserServer());
				IJpo transfer = transferset.addJpo();
				transfer.setValue("TRANSFERMOVETYPE",
						ItemUtil.TRANSFERMOVETYPE_XTOX,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transfer.setValue("ISMR", "是",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transfer.setValue("mrnum", mrnum);
				transfer.setValue("CREATEBY", personid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transfer.setValue("CREATEDATE", createdate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 制单日期

				IJpoSet ISSUESTOREROOMlocationSet = this.getJpo().getJpoSet(
						"$LOCATIONS", "LOCATIONS",
						"LOCATION='" + STOREROOM + "'");
				if (!ISSUESTOREROOMlocationSet.isEmpty()) {
					IJpo ISSUESTOREROOMlocation = ISSUESTOREROOMlocationSet
							.getJpo(0);
					transfer.setValue("ISSUESTOREROOM", STOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transfer.setValue("KEEPER",
							ISSUESTOREROOMlocation.getString("KEEPER"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					IJpoSet locaddressSet = this.getJpo().getJpoSet(
							"$LOCADDRESS", "locaddress",
							"LOCATION='" + STOREROOM + "' and isaddress='是'");
					if (!locaddressSet.isEmpty()) {
						IJpo locaddress = locaddressSet.getJpo(0);
						transfer.setValue("ISSUEADDRESS",
								locaddress.getString("address"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}

				IJpoSet RECEIVESTOREROOMlocationSet = this.getJpo().getJpoSet(
						"$LOCATIONS", "LOCATIONS",
						"LOCATION='" + RECEIVESTOREROOM + "'");
				if (!RECEIVESTOREROOMlocationSet.isEmpty()) {
					IJpo RECEIVESTOREROOMlocation = RECEIVESTOREROOMlocationSet
							.getJpo(0);
					transfer.setValue("RECEIVESTOREROOM", RECEIVESTOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transfer.setValue("KEEPER",
							RECEIVESTOREROOMlocation.getString("KEEPER"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transfer.setValue("RECEIVEPHONE",
							RECEIVESTOREROOMlocation.getString("PRIMARYPHONE"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transfer.setValue("endby",
							RECEIVESTOREROOMlocation.getString("KEEPER"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transfer.setValue("RECEIVEBY",
							RECEIVESTOREROOMlocation.getString("KEEPER"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 装箱单接收人
					IJpoSet reclocaddressSet = this.getJpo().getJpoSet(
							"$LOCADDRESS",
							"locaddress",
							"LOCATION='" + RECEIVESTOREROOM
									+ "' and isaddress='是'");
					if (!reclocaddressSet.isEmpty()) {
						IJpo reclocaddress = reclocaddressSet.getJpo(0);
						transfer.setValue("RECEIVEADDRESS", RECEIVEADDRESS,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}
				transfer.setValue("PACKBY", personid);
				transfer.setValue("CHECKBY", personid);// 装箱人
				transfer.setValue("SENDBY", personid);// 发运人

				transfer.setValue("iscreate", "是");
				transfer.setValue("TYPE", "ZXD",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transfer.setValue("orgid", orgid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transfer.setValue("siteid", siteid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transfer.setValue("status", "未处理",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

				String transfernum = transfer.getString("TRANSFERNUM");
				String transferid = transfer.getString("transferid");
				// zzx add start
				String receiveby = transfer.getString("RECEIVEBY");// 收货人
				// Date createdatezxd = transfer.getDate("CREATEDATE");
				// zzx add ende
				if (!StringUtil.isStrEmpty(transfernum)
						&& transfernum.startsWith("D")) {
					transfernum = transfernum.substring(6);
				}
				IJpoSet deptset = MroServer.getMroServer().getJpoSet(
						"SYS_DEPT",
						MroServer.getMroServer().getSystemUserServer());
				deptset.setQueryWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
						+ personid + "')");
				deptset.reset();
				String deptnum = "";
				String mdmdeptid = "";
				if (!deptset.isEmpty()) {
					deptnum = deptset.getJpo(0).getString("deptnum");
					mdmdeptid = deptset.getJpo(0).getString("MDM_DEPTID");
					String retrun = WorkorderUtil
							.getAbbreviationByDeptnum(deptnum);
					if (retrun.equalsIgnoreCase("PJGL")) {
						transfernum = "ZX-" + "ZX" + transfernum;
					} else {
						transfernum = retrun + "-" + "ZX" + transfernum;
					}
				} else {
					transfernum = "ZX-" + "ZX" + transfernum;
				}

				transfer.setValue("transfernum", transfernum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferset.save();
				// 启动计划工作流
				IJpoSet zxdtransferset = MroServer.getMroServer().getSysJpoSet(
						"transfer", "transferid='" + transferid + "'");
				if (zxdtransferset != null && zxdtransferset.count() > 0) {

					IJpo zxdtransfer = zxdtransferset.getJpo(0);
					WfControlUtil.startwf(zxdtransfer, "ZXTRANSFER");
//					// zzx add start 9.26
//					EmailUtil.mrcreatetransfer(transfernum, receiveby);
//					// zzx add end 9.236
				}
				IJpoSet mrlinetransferset = MroServer.getMroServer().getJpoSet(
						"mrlinetransfer",
						MroServer.getMroServer().getSystemUserServer());
				mrlinetransferset.setUserWhere("mrnum='" + mrnum
						+ "' and TRANSTYPE in ('" + ItemUtil.TRANSTYPE_XCDB
						+ "','" + ItemUtil.TRANSTYPE_NBXT
						+ "') and ISCREATEZXTRANSFER='否' and keeper='"
						+ personid + "'");
				mrlinetransferset.reset();
				if (!mrlinetransferset.isEmpty()) {
					for (int j = 0; j < mrlinetransferset.count(); j++) {
						IJpo mrlinetransfer = mrlinetransferset.getJpo(j);
						mrlinetransfer.setValue("ISCREATEZXTRANSFER", "是",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						mrlinetransfer.setValue("TRANSFERNUM", transfernum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					}
					mrlinetransferset.save();
				}
			}

		}
	}
}
