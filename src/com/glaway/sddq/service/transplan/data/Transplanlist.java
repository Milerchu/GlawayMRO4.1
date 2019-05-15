package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;

public class Transplanlist extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void add() throws MroException {
		this.setValue("reportby", getUserInfo().getPersonId(), 11L);
		this.setValue("reportdate", MroServer.getMroServer().getDate(), 11L);
		super.add();
	}

	@Override
	public void init() throws MroException {
		if (!this.isNew()) {
			String status = this.getString("status");
			if (status.equals("未分配")) {
				this.setFlag(7L, false);
			} else {
				this.setFlag(7L, true);
			}
			this.getJpoSet("$TRANSPLANLISTLINE", "TRANSPLANLISTLINE", "TRANSPLANLISTNUM=:TRANSPLANLISTNUM and status='已分配'").setFlag(7L, true);

		}

		super.init();
	}


}
