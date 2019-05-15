package com.glaway.sddq.service.convertloc.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

public class FldProjNum extends JpoField {

	/**
	 *  项目编号字段类映射
	 */
	private static final long serialVersionUID = 2422791127935967337L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
			super.init();
		 	String[] thisAttrs = {"ProjNum"};
	        String[] srcAttrs = {"WORKORDERNUM"};
	        setLookupMap(thisAttrs, srcAttrs);
	}
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String projnum = this.getJpo().getString("ProjNum");
		if(projnum!=null){
			IJpoSet proset = MroServer.getMroServer().getJpoSet("projectinfo", MroServer.getMroServer().getSystemUserServer());
			proset.setUserWhere("WORKORDERNUM='"+projnum+"'");
			proset.reset();
			String location = proset.getJpo().getString("location");
			if(location!=null){
				this.getJpo().setValue("location",location);
			}
		}
	}
}
