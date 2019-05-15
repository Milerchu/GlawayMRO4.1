package com.glaway.sddq.material.msgmanage.bean;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;

public class MsgmanageAppBean extends AppBean {
	@Override
	public void initializeApp() throws MroException {
		// TODO Auto-generated method stub
		super.initializeApp();
		String personid = this.getAppBean().getUserInfo().getLoginID();
		personid=personid.toUpperCase();
		this.getAppBean().getJpoSet().setUserWhere("receiver='"+personid+"' and MSGMANAGEID not in (select MSGMANAGEID FROM MSGMANAGE where isread='1')");
	}

}
