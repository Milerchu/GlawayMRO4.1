package com.glaway.sddq.base.revisit.data;

import java.util.Date;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

public class ReturnVisit extends Jpo implements IJpo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void changestatus(String newstatus, Date date, String memo,String oldstatus)
			throws MroException {
		if (!newstatus.equals("")) {
			setValue("docstatus", newstatus);
			setValue("statusdate", date, 11L);
			IJpoSet ahstatusset = getJpoSet("RETURNVISITSTATUSHISTORY");
			IJpo ahstatusnew = ahstatusset.addJpo();
			ahstatusnew.setValue("RECORDNUM", getString("RECORDNUM"), 11L);
			ahstatusnew.setValue("siteid", getString("siteid"), 11L);
			ahstatusnew.setValue("orgid", getString("orgid"), 11L);
			ahstatusnew.setValue("changedate", date, 11L);
			ahstatusnew.setValue("docstatus", newstatus, 11L);
			ahstatusnew.setValue("oldstatus", oldstatus, 11L);
			ahstatusnew.setValue("memo", memo, 11L);
			ahstatusnew.setValue("changeby", getUserInfo().getPersonId(), 11L);
			ahstatusnew.setFlag(7L, true);
		} else {
			throw new AppException("custinfo", "cannotnull");
		}
	}

	@Override
	public void init() throws MroException {
		super.init();
		String[] names = { "RECORDNUM", "DESCRIPTION", "RETVISITTIME",
				"CUSTNUM", "RETVISITPERSON", "CUSTSATISFACTION",
				"PROBLEMLEVEL", "CUSTFEEDBACK", "RETVISITUSER", "CREATEDATE",
				"DOCSTATUS", "STATUSDATE" };
		String status = this.getString("DOCSTATUS");
		if ("已审核".equalsIgnoreCase(status)) {
			this.setFieldFlag(names, GWConstant.S_READONLY, false);
		}
	}

}
