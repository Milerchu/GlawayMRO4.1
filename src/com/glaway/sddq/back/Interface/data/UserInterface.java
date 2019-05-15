package com.glaway.sddq.back.Interface.data;

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
 * 接口管理Jpo
 * 
 * @author bchen
 * @version [版本号, 2018-2-8]
 * @since [产品/模块版本]
 */
public class UserInterface extends StatusJpo implements IStatusJpo,
		FixedLoggers {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void changeStatus(String newstatus, String memo) throws MroException {
		if (!newstatus.equals("")) {
			String oldstatus = getString("status");
			setValue("status", newstatus);
			IJpoSet ahstatusset = getJpoSet("USERINTERFACESTATUSHISTORY");
			IJpo ahstatusnew = ahstatusset.addJpo();
			ahstatusnew.setValue("ifnum", getString("ifnum"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			ahstatusnew.setValue("siteid", getString("siteid"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			ahstatusnew.setValue("orgid", getString("orgid"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			ahstatusnew.setValue("STATUS", oldstatus,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			ahstatusnew.setValue("newstatus", newstatus,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			ahstatusnew.setValue("REMARK", memo,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			ahstatusnew.setFlag(GWConstant.S_READONLY, true);
		} else {
			throw new AppException("userinterface", "cannotnull");
		}
	}

	@Override
	public void delete(long flag) throws MroException {
		super.delete(flag);
		if (this.getJpoSet("USERINTERFACESTATUSHISTORY") != null) {
			this.getJpoSet("USERINTERFACESTATUSHISTORY").deleteAll(flag);
		}
	}

	@Override
	public void undelete() throws MroException {
		super.undelete();
		if (this.getJpoSet("USERINTERFACESTATUSHISTORY") != null) {
			this.getJpoSet("USERINTERFACESTATUSHISTORY").undeleteAll();
		}
	}

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String status = this.getString("status");
		String[] str = { "ifnum", "ifname", "DESCRIPTION", "IFSYSTEM",
				"FREQUENCY", "IFDATATYPE", "FROMTO", "IFURL", "IFMETHOD",
				"IFTYPE", "SYSUSER", "TEL", "MEMO" };
		if (!this.isNew()) {
			if (status.equals("草稿")) {
				this.setFieldFlag(str, 7L, false);

			} else {
				this.setFieldFlag(str, 7L, true);

			}

		}
	}


}
