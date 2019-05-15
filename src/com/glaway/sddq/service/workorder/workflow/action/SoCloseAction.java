package com.glaway.sddq.service.workorder.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 故障工单-soclose关闭操作定制类
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-5-24]
 * @since [MRO4.1/模块版本]
 */
public class SoCloseAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		if (jpo != null) {
			jpo.setValue("status", SddqConstant.WO_STATUS_GB,
					GWConstant.P_NOVALIDATION);
			// 设置完成时间
			if (jpo.getDate("CLOSETIME") == null) {
				jpo.setValue("CLOSETIME", MroServer.getMroServer().getDate(),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

			}
			jpo.getJpoSet().save();

		}

	}

}
