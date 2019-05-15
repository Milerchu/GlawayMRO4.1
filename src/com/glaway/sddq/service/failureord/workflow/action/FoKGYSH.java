package com.glaway.sddq.service.failureord.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 故障工单-库管员审核操作
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月1日]
 * @since [产品/模块版本]
 */
public class FoKGYSH implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {
		if (jpo != null) {

			jpo.setValue("status", "库管员审核",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

			jpo.getJpoSet().save();
		}

	}

}
