package com.glaway.sddq.material.locbinitem.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;

public class LocbinItemDataBean extends DataBean {
	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {
		// TODO Auto-generated method stub
		int bzqty=this.getJpo().getParent().getInt("bzqty");
		IJpoSet locbinitemset=this.getJpo().getParent().getJpoSet("locbinitem");
		if(!locbinitemset.isEmpty()){
			int sumfpqty=0;
			for(int i=0;i<locbinitemset.count();i++){
				int fpqty=locbinitemset.getJpo(i).getInt("fpqty");
				sumfpqty=sumfpqty+fpqty;
			}
			int newnobinqty=bzqty-sumfpqty;
			if(newnobinqty<0){
			throw new MroException("locbinitem", "qty");
		}else{
			this.getJpo().getParent().setValue("nobinqty", newnobinqty);
			this.getAppBean().reloadAllTab();
			super.addEditRowCallBackOk();
		}
		}
		 
	}
	@Override
	public int addrow() throws MroException, IOException {
		// TODO Auto-generated method stub
		DataBean locbinbean= this.page.getAppBean().getDataBean("nolotandnoroa");
		if(!locbinbean.isEmpty()){
			int nobinqty=locbinbean.getJpo(0).getParent().getInt("nobinqty");
			if(nobinqty==0){
				throw new MroException("locbinitem", "nobin");
			}
            
		}
		return super.addrow();
	
	}
	@Override
	public int editrow() throws MroException, IOException {
		// TODO Auto-generated method stub
		int fpqty=this.getJpo().getInt("fpqty");
		int nobinqty=this.getJpo().getParent().getInt("nobinqty");
		if(fpqty==0){
			this.getJpo().setValue("fpqty", nobinqty,GWConstant.P_NOACTION);
		}
		return super.editrow();
		
		 
	}
	
}
