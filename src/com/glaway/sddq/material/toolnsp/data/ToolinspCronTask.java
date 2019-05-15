package com.glaway.sddq.material.toolnsp.data;

import java.text.SimpleDateFormat;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;

/**
 * 
 * <工具校检单定时任务类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */

public class ToolinspCronTask extends BaseStatefulJob {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void execute() throws MroException {
		// TODO Auto-generated method stub
		// System.out.println("已经进入定时任务方法！！！");
		java.util.Date curDate = MroServer.getMroServer().getDate();
		SimpleDateFormat datefmt = new SimpleDateFormat("yyyy/MM");

		String format = datefmt.format(curDate);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		// 1.查询当月下有多少设备工具
		IJpoSet deviceinfoSet = MroServer.getMroServer().getJpoSet(
				"DEVICEINFO", MroServer.getMroServer().getSystemUserServer());
		deviceinfoSet.setUserWhere("to_char(CHECKDATE,'yyyy/mm')='" + format
				+ "' and identify=0");
		deviceinfoSet.reset();
		if (deviceinfoSet.count() > 0) {
			String replace = format.replace("/", "");
			String num = "GJ-SJ-" + replace + "0001";
			// 1.查詢出工具設備信息，插入當月主記錄
			IJpoSet toolinspSet = MroServer.getMroServer().getSysJpoSet(
					"TOOLINSP");
			toolinspSet.setUserWhere("TOOLINSPNUM='" + num + "'");
			toolinspSet.reset();
			if (toolinspSet.count() == 0) {
				IJpo addJpo = toolinspSet.addJpo();
				addJpo.setValue("TOOLINSPNUM", num);
				addJpo.setValue("INSPDATE", format);
				toolinspSet.save();
				// 2.查詢出設備信息插入主數據下子表中
				for (int i = 0; i < deviceinfoSet.count(); i++) {
					String checkdate = deviceinfoSet.getJpo(i).getString(
							"CHECKDATE");
					String itemnum = deviceinfoSet.getJpo(i).getString(
							"ITEMNUM");
					IJpoSet toolcheckSet = MroServer.getMroServer().getJpoSet(
							"TOOLCHECK",
							MroServer.getMroServer().getSystemUserServer());
					// IJpoSet toolcheckSet =
					// MroServer.getMroServer().getSysJpoSet("TOOLCHECK");
					IJpo addJpoCheck = toolcheckSet.addJpo();
					addJpoCheck.setValue("ITEMNUM", itemnum);
					addJpoCheck.setValue("PLANCHECKDATE", checkdate);
					addJpoCheck.setValue("TOOLINSPNUM", num);
					// addJpoCheck.setValue("SITEID", "ELEC");
					// addJpoCheck.setValue("ORGID", "CRRC");
					toolcheckSet.save();
					deviceinfoSet.getJpo(i).setValue("IDENTIFY", 1);
				}
				deviceinfoSet.save();
			} else {
				for (int i = 0; i < deviceinfoSet.count(); i++) {
					String checkdate = deviceinfoSet.getJpo(i).getString(
							"CHECKDATE");
					String itemnum = deviceinfoSet.getJpo(i).getString(
							"ITEMNUM");
					IJpoSet toolcheckSet = MroServer.getMroServer()
							.getSysJpoSet("TOOLCHECK");
					IJpo addJpoCheck = toolcheckSet.addJpo();
					addJpoCheck.setValue("ITEMNUM", itemnum);
					addJpoCheck.setValue("PLANCHECKDATE", checkdate);
					addJpoCheck.setValue("TOOLINSPNUM", num);
					toolcheckSet.save();
					deviceinfoSet.getJpo(i).setValue("IDENTIFY", 1);
				}
				deviceinfoSet.save();
			}
			// toolinspSet.setUserWhere("TOOLINSPNUM='"+num+"'");

			/*
			 * //zzx add String replace = format.replace("/", ""); String
			 * num=""; String bs="0";
			 * 
			 * //zzx add end
			 * 
			 * 
			 * 
			 * for (int i = 0; i <deviceinfoSet.count(); i++) { //2.已查询到所需设备工具
			 * 
			 * num=replace+bs+i;
			 * 
			 * String checkdate =
			 * deviceinfoSet.getJpo(i).getString("CHECKDATE"); String itemnum =
			 * deviceinfoSet.getJpo(i).getString("ITEMNUM");
			 * 
			 * System.out.println(checkdate); //3.根据所查到的结果往送检单中插入 IJpoSet
			 * toolinspSet = MroServer.getMroServer().getSysJpoSet("TOOLINSP");
			 * // IJpoSet toolinspSet =
			 * MroServer.getMroServer().getJpoSet("TOOLINSP",
			 * MroServer.getMroServer().getSystemUserServer()); IJpo addJpo =
			 * toolinspSet.addJpo(); //addJpo.setValue("INSPDATE", checkdate);
			 * addJpo.setValue("INSPDATE", curDate); addJpo.setValue("SITEID",
			 * "ELEC"); addJpo.setValue("ORGID", "CRRC");
			 * addJpo.setValue("TOOLINSPNUM",num); toolinspSet.save(); IJpoSet
			 * toolcheckSet =
			 * MroServer.getMroServer().getSysJpoSet("TOOLCHECK"); IJpo
			 * addJpoCheck = toolcheckSet.addJpo();
			 * addJpoCheck.setValue("ITEMNUM",itemnum);
			 * addJpoCheck.setValue("PLANCHECKDATE",checkdate);
			 * addJpoCheck.setValue("TOOLINSPNUM",num);
			 * addJpoCheck.setValue("SITEID", "ELEC");
			 * addJpoCheck.setValue("ORGID", "CRRC"); toolcheckSet.save();
			 * deviceinfoSet.getJpo(i).setValue("IDENTIFY", 1); }
			 * deviceinfoSet.save();
			 */

		}
	}
}
