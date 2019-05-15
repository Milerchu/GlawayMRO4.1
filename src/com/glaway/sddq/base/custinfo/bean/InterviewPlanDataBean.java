package com.glaway.sddq.base.custinfo.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;

public class InterviewPlanDataBean extends DataBean {
	
	@Override
	public void initialize() throws MroException {
		// TODO Auto-generated method stub
		System.out.println("123");
		super.initialize();
	}
	
	@Override
	public int addrow() throws MroException, IOException {
		// TODO Auto-generated method stub
		getJpo().getString("INTERVPNUM");
		return super.addrow();
	}
}
