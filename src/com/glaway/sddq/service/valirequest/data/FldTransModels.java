package com.glaway.sddq.service.valirequest.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 验证申请范围表涉及车型字段类
 */
public class FldTransModels extends JpoField {

	private static final long serialVersionUID = -7546270361100891007L;

	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { "TRANSMODELS" };
		String[] srcAttrs = { "MODELNUM" };
		setLookupMap(thisAttrs, srcAttrs);
	}

}
