package com.glaway.sddq.overhaul.plan.bean;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
/**
 * 
 * 单车检修部件列表DataBean
 * 
 * @author  chenbin
 * @version  [版本号, 2018-8-3]
 * @since  [产品/模块版本]
 */
public class SingleOverhaulDataBean extends DataBean {

	@Override
	public synchronized void delete() throws MroException {
        IJpoSet repairplansSet = MroServer.getMroServer().getSysJpoSet("REPAIRPLANS");
        IJpo repairplans =repairplansSet.getJpo();
        
        String status =repairplans.getString("status");
		if(!status.equals("新增")){
			throw new AppException("singleoverhaul", "notdelete");
		}		
	}	
}
