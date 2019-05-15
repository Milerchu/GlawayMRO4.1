
package com.glaway.sddq.service.convertloc.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

public class FldBinNum extends JpoField {
	
	@Override
	public IJpoSet getList() throws MroException {
		String location = this.getJpo().getString("LOCATION");
//		IJpoSet set=super.getList();
//		if(!location.equals("")){
//			set.setUserWhere("LOCATION='"+location+"'");
//			set.reset();
//		}
		IJpoSet domainSet = null;
        domainSet = getUserServer().getJpoSet("LOCBIN", "LOCATION='"+location+"'");
        domainSet.reset();
		return domainSet;
	}
}
