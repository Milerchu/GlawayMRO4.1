package com.glaway.sddq.overhaul.jobbook.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

public class JobTaskRecord extends Jpo implements IJpo {

private static final long serialVersionUID = 1L;
	
	
	public void jcopyTask(IJpo jobtastrecord, IJpo newjp) throws MroException {
		
		String jpnum = newjp.getString("JPNUM");
		String[] attrs = { "CHECKITEM", "PROJECTSTEPS","SEQ","DESCRIPTION"};
		setValue(jobtastrecord, attrs, attrs, GWConstant.P_NOVALIDATION);
		setValue("JPNUM", jpnum, GWConstant.P_NOVALIDATION);		
	}
}
