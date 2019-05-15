package com.glaway.sddq.material.convertloc.data;

import java.util.Date;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨转库单状态字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-9-6]
 * @since [产品/模块版本]
 */
public class FldStatus extends JpoField {
	/**
	 * 调拨转库单状态变为已接收通知配件专员已交货
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		IJpo CONVERTLOC = this.getJpo();
		String status = this.getValue();
		String CONVERTLOCID = CONVERTLOC.getString("CONVERTLOCID");
		String CONVERTLOCNUM = CONVERTLOC.getString("CONVERTLOCNUM");
		String orgid = CONVERTLOC.getString("orgid");
		String siteid = CONVERTLOC.getString("siteid");
		String personid = "";
		IJpoSet SYS_PERSONGROUPTEAM = MroServer.getMroServer().getJpoSet("SYS_PERSONGROUPTEAM",MroServer.getMroServer().getSystemUserServer());
		SYS_PERSONGROUPTEAM.setUserWhere("persongroup='PJZY1020'");
		SYS_PERSONGROUPTEAM.reset();
		if (!SYS_PERSONGROUPTEAM.isEmpty()) {
			personid=SYS_PERSONGROUPTEAM.getJpo(0).getString("RESPPARTYGROUP");
		}

		if (status.equalsIgnoreCase("已接收")) {
			IJpoSet mrset = MroServer.getMroServer().getJpoSet("mr",
					MroServer.getMroServer().getSystemUserServer());
			mrset.setUserWhere("SAPPONUM in (select VGBEL from CONVERTLOCLINE where werks='1020' and CONVERTLOCNUM='"
					+ CONVERTLOCNUM + "')");
			mrset.reset();
			if (!mrset.isEmpty()) {
				for (int i = 0; i < mrset.count(); i++) {
					IJpo mr = mrset.getJpo(i);
					String mrnum = mr.getString("mrnum");
					ADDMESG(CONVERTLOCID, CONVERTLOCNUM, orgid, siteid,
							personid, mrnum);// 调用生成通知方法
				}
			}
			IJpoSet mrlinetransferset = MroServer.getMroServer().getJpoSet(
					"mrlinetransfer",
					MroServer.getMroServer().getSystemUserServer());
			mrlinetransferset
					.setUserWhere("SAPPONUM in (select VGBEL from CONVERTLOCLINE where werks='1020' and CONVERTLOCNUM='"
							+ CONVERTLOCNUM + "')");
			mrlinetransferset.reset();
			if (!mrlinetransferset.isEmpty()) {
				for (int i = 0; i < mrlinetransferset.count(); i++) {
					IJpo mrlinetransfer = mrlinetransferset.getJpo(i);
					String mrnum = mrlinetransfer.getString("mrnum");
					ADDMESG(CONVERTLOCID, CONVERTLOCNUM, orgid, siteid,
							personid, mrnum);// 调用生成通知方法
				}
			}
		}
	}

	/**
	 * 
	 * <生成系统通知>
	 * 
	 */
	public static void ADDMESG(String CONVERTLOCID, String CONVERTLOCNUM,
			String orgid, String siteid, String personid, String mrnum)
			throws MroException {
		IJpoSet MSGMANAGEset = MroServer.getMroServer().getJpoSet("MSGMANAGE",
				MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		Date createdate = MroServer.getMroServer().getDate();

		IJpo MSGMANAGE = MSGMANAGEset.addJpo();
		MSGMANAGE.setValue("app", "CONVERTLOC",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("appid", CONVERTLOCID,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("content", "编号为：'" + mrnum
				+ "'的配件申请中计划物料已经到货。请到编号为'" + CONVERTLOCNUM + "'的交货单中查看！",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("createdate", createdate,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("msgnum", CONVERTLOCID,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("receiver", personid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("orgid", orgid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("siteid", siteid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("status", "新增",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("subject", "配件申计划到货通知",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

		MSGMANAGEset.save();
	}
}
