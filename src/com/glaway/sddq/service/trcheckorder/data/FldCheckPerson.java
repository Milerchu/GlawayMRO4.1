package com.glaway.sddq.service.trcheckorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 售后tr检查单明细表检察者字段类
 */
public class FldCheckPerson extends JpoField {

	private static final long serialVersionUID = -7778850882942664072L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String[] thisAttrs = { "CHECKPERSON" };
		String[] srcAttrs = { "PERSONID" };
		setLookupMap(thisAttrs, srcAttrs);
	}
}
