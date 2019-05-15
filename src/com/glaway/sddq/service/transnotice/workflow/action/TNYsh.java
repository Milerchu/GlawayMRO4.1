package com.glaway.sddq.service.transnotice.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造通知单完成审核操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月10日]
 * @since [产品/模块版本]
 */
public class TNYsh implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {
		if (jpo != null) {
			try {
				// 创建改造计划
				WorkorderUtil.createTransPlan(jpo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 变更状态
			jpo.setValue("status", "已审核");
			jpo.getJpoSet().save();
		}

	}

}
