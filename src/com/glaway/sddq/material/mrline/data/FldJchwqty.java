package com.glaway.sddq.material.mrline.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

public class FldJchwqty extends JpoField {
	@Override
	public void init() throws MroException {
		super.init();
		if (getJpo() != null && !StringUtil.isNullOrEmpty(this.getJpo().getAppName())) {
			setValue(getSumCurbaltotal() + "",
					GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
		}
	}
	private double getSumCurbaltotal() throws MroException {
		double total = 0;
		if (getJpo() != null) {
			String appname=this.getJpo().getAppName();
			if(appname!=null){
				String itemnum=this.getJpo().getString("itemnum");
				IJpoSet inventoryset=MroServer.getMroServer().getJpoSet("sys_inventory", MroServer.getMroServer().getSystemUserServer());
				inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='Y1713'");//动车服务库
				if(!inventoryset.isEmpty()){
					total = inventoryset.sum("CURBAL");
				}	
				}
			}
		return total;
	}
}
