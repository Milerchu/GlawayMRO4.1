package com.glaway.sddq.config.bzsq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

public class FldItemnum extends JpoField {
	
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		if(this.isValueChanged()){
			this.getField("NEWITEMNUM").setValue(this.getValue(),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}
	
}
