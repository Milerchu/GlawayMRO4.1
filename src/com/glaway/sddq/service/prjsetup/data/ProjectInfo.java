package com.glaway.sddq.service.prjsetup.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 项目信息jpo
 * 
 * @author ygao
 * @version [版本号, 2017-11-7]
 * @since [产品/模块版本]
 */
public class ProjectInfo extends StatusJpo implements IStatusJpo, FixedLoggers {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 变更状态
	 * 
	 * @param newstatus
	 * @param memo
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void changestatus(String newstatus, String memo) throws MroException {
		if (!newstatus.equals("")) {
			String oldstatus = getString("STATUS");
			setValue("STATUS", newstatus);
			IJpoSet ahstatusset = getJpoSet("PRJINFOSTATUSHISTORY");
			IJpo ahstatusnew = ahstatusset.addJpo();
			ahstatusnew.setValue("PROJECTNUM", getString("PROJECTNUM"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			ahstatusnew.setValue("siteid", getString("siteid"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			ahstatusnew.setValue("orgid", getString("orgid"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			ahstatusnew.setValue("STATUS", oldstatus,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			ahstatusnew.setValue("newstatus", newstatus,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			ahstatusnew.setValue("REMARK", memo, 11L);
			ahstatusnew.setFlag(7L, true);
		} else {
			throw new AppException("custinfo", "cannotnull");
		}

	}

	@Override
	public void init() throws MroException {
		super.init();
		if (getString("STATUS").equals("已立项")) {
			// setFlag(GWConstant.S_READONLY, true);
			setFieldFlag(new String[] { "PROJECTNAME", "PRJMANAGER",
					"WORKORDERNUM", "CUSTNUM", "STARTDATE", "COMPLETEDATE",
					"PRJBUDGET", "BASICINFO", "BACKGROUNDINFO", "PROJECTRANGE",
					"SERVSTRATEGY", "MILESTONEEXPECTED", "MAJORRISKASMENT",
					"PRJEXPECTED", "COMMUNICATIONPLAN", "GOAL", "PRJCATEGORY",
					"MANAGERTEL", "COSTCOLLECDEPT", "ISPDTPROJECT" },
					GWConstant.S_READONLY, true);
			// getJpoSet("DOCLINKS").setFlag(GWConstant.S_READONLY, true);
		}
		// 任何时候都能修改干系人登记册
		// getJpoSet("RELATEDPEOPLEREG").setFlag(GWConstant.S_READONLY, false);

	}
}
