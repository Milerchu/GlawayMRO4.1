package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.Jpo;

public class Transplanloc extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void add() throws MroException {
		// TODO Auto-generated method stub
		IJpo onwer = this.getParent();
		if (onwer != null) {
			this.setValue("TRANSPLANNUM", onwer.getString("TRANSPLANNUM"),11L);
			this.setValue("ITEMNUM", onwer.getString("PRODUCTCODE"),11L);
		}

		super.add();
	}

}
