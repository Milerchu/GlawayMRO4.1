package com.glaway.sddq.overhaul.jobbook.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 车号多条选择
 * 
 * @author  chenbin
 * @version  [版本号, 2018-9-5]
 * @since  [产品/模块版本]
 */
public class CmodelDataBean extends DataBean {

	@Override
	public int dialogok() throws IOException, MroException {
		// TODO Auto-generated method stub
		DataBean jobscopeBean = this.page.getAppBean().getDataBean("main_jpop_table");
		IJpoSet jobscopeSet=jobscopeBean.getJpoSet();
		
		String jpnum =jobscopeSet.getParent().getString("JPNUM");
		
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty())
        {
			for (int i = 0; i < list.size(); i++){		
				IJpo lotnumjpo = list.get(i);
				IJpo jobscope = jobscopeSet.addJpo();	
				jobscope.setValue("CMODEL", lotnumjpo.getString("MODELNUM"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jobscope.setValue("SITEID", this.page.getAppBean().getJpo().getString("SITEID"));
				jobscope.setValue("ORGID", this.page.getAppBean().getJpo().getString("ORGID"));
				jobscope.setValue("JPNUM", jpnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
        }
		jobscopeBean.reloadPage();
		return super.dialogok();
	}	
	
}
 	