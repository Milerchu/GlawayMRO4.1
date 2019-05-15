package com.glaway.sddq.material.location.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

public class FLdQty extends JpoField {
	@Override
	public void init() throws MroException {
		super.init();
		if (getJpo() != null && !StringUtil.isNullOrEmpty(this.getJpo().getAppName())) {
		String itemnum=this.getJpo().getParent().getString("itemnum");
		String location=this.getJpo().getString("location");
		IJpoSet inventoryset=MroServer.getMroServer().getSysJpoSet("sys_inventory");
    	inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
    	if(!inventoryset.isEmpty()){
    		double qty=inventoryset.getJpo(0).getDouble("curbal");
    		this.getJpo().setValue("qty", qty,GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
    	}
    	
		}
	}
}
