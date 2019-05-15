package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

public class FldStoreloc extends JpoField{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3508147563042424859L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String[] thisAttrs={"STORELOC"};
		String[] srcAttrs={"LOCATION"};
		setLookupMap(thisAttrs, srcAttrs);
	}
}
