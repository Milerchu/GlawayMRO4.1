package com.glaway.sddq.service.transorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

public class FldPreassetnum extends JpoField{

	/**
	 * 改造产品信息表中，改造前序列号字段类
	 */
	private static final long serialVersionUID = -165341809329244548L;

	
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String[] thisAttrs={"PREASSETNUM"};
		String[] srcAttrs={"ASSETNUM"};
		setLookupMap(thisAttrs, srcAttrs);
	}
}
