package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

public class FldResponsible extends JpoField {

	/**
	 * 改造计划应用中，改造车辆分布表负责人字段类
	 */
	private static final long serialVersionUID = -6095350365904195294L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String[] thisAttrs = { "RESPONSIBLE" };
		String[] srcAttrs = { "PERSONID" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	@Override
	public IJpoSet getList() throws MroException {
		// TODO Auto-generated method stub
		return super.getList();
	}

}
