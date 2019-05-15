package com.glaway.sddq.service.trcheckorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 售后tr检查单明细表自查者字段类
 */
public class FldCheckOwner extends JpoField {

	private static final long serialVersionUID = -769002837974871102L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String[] thisAttrs = { "CHECKOWNER" };
		String[] srcAttrs = { "PERSONID" };
		setLookupMap(thisAttrs, srcAttrs);
	}
}
