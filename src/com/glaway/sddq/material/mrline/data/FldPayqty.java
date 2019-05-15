package com.glaway.sddq.material.mrline.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

public class FldPayqty extends JpoField {
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
			String MRTYPE=this.getJpo().getParent().getString("MRTYPE");
			String appname=this.getJpo().getAppName();
			String itemnum=this.getJpo().getString("itemnum");
			String mrnum=this.getJpo().getString("mrnum");
			if(appname!=null){
				if(!MRTYPE.equalsIgnoreCase("")){
						if(MRTYPE.equalsIgnoreCase("零星")){
							IJpoSet mrlinetransferset=MroServer.getMroServer().getSysJpoSet("mrlinetransfer");
							mrlinetransferset.setUserWhere("mrnum='"+mrnum+"' and itemnum='"+itemnum+"' and transtype in ('"+ItemUtil.TRANSTYPE_XDJH+"','"+ItemUtil.TRANSTYPE_NBXT+"','"+ItemUtil.TRANSTYPE_ZXKDB+"','"+ItemUtil.TRANSTYPE_XCDB+"')");
							mrlinetransferset.reset();
							if(!mrlinetransferset.isEmpty()){
								total = mrlinetransferset.sum("TRANSFERQTY");
							}
						}
						if(MRTYPE.equalsIgnoreCase("项目")){
							total=this.getJpo().getDouble("endqty");
						}
					}	
				}
			}
		return total;
	}
}
