package com.glaway.sddq.material.location.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

public class FldOldSqn extends JpoField {
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String oldsqn=this.getJpo().getParent().getString("sqn");
		
		this.getJpo().setValue("oldsqn",oldsqn,GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
		 
	}
}
