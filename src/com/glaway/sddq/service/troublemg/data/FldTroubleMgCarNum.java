package com.glaway.sddq.service.troublemg.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 项目问题跟踪表车号字段类
 */
public class FldTroubleMgCarNum extends JpoField {

	private static final long serialVersionUID = 4555402388957747289L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String[] thisAttrs = { "CARNUM" };
		String[] srcAttrs = { "CARNO" };
		setLookupMap(thisAttrs, srcAttrs);
	}
}
