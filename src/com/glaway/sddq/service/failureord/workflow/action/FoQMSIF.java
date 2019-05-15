package com.glaway.sddq.service.failureord.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 故障工单-QMS接口触发操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月1日]
 * @since [产品/模块版本]
 */
public class FoQMSIF implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {
		if (jpo != null) {

			// 调用qms接口
			WorkorderUtil.sendToQMS(jpo);
			jpo.getJpoSet().save();
		}

	}

}
