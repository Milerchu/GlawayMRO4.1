package com.glaway.sddq.material.mpr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

public class FldRktype extends JpoField {

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String rktype=this.getValue();
		if(rktype.equalsIgnoreCase("客户委托修")){
			this.getJpo().setFieldFlag("sxtype", GWConstant.S_REQUIRED,true);
		}else{
			this.getJpo().setValue("sxtype", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("sxtype", GWConstant.S_REQUIRED,false);
		}
	}

}
