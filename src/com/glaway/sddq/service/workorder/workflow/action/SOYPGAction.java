package com.glaway.sddq.service.workorder.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;

/**
 * 
 * <服务工单-SOYPG操作定制类>
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-5-24]
 * @since [MRO4.1/模块版本]
 */
public class SOYPGAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		if (jpo != null) {
			jpo.setValue("status", "已派工");
			jpo.setValue("DISPATCHTIME", MroServer.getMroServer().getDate());
			jpo.getJpoSet().save();
		}

	}

}
