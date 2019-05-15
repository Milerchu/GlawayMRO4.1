package com.glaway.sddq.base.revisit.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.base.revisit.data.ReturnVisit;

public class ReturnVisitChangStatus extends DataBean {
	@Override
	public int execute() throws MroException {
		IJpo mr = getAppBean().getJpo();
		// 列表数据为空时
		if (mr == null) {
			return GWConstant.NOACCESS_SAMEMETHOD;
		}
		checkSave();
		
		//String oldStatusNew = getAppBean().getJpo().getString("status");
		
		ReturnVisit ct = (ReturnVisit) mr;
		Date curDate = MroServer.getMroServer().getDate();
		String oldStatus = mr.getString("DOCSTATUS");
		String newStatus = this.getString("status");
		if (oldStatus.equals(newStatus)) {
			throw new AppException("returnvisitstatus", "isStatus");
		}
		//ct.changestatus(getString("status"), curDate, getString("memo"),oldStatusNew);
		ct.changestatus(getString("status"), curDate, getString("memo"),oldStatus);
		
		try {
			getAppBean().SAVE();
			this.reloadPage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}
}
