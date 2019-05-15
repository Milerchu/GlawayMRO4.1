/*
 * 文 件 名:  CounterSignCompleteListener.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  请假会签任务监听器，当会签任务完成时统计同意的数量
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-14
 */
package com.glaway.mro.app.system.workflow.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.glaway.mro.app.system.workflow.util.WFConstant;

/**
 * 请假会签任务监听器，当会签任务完成时统计同意的数量
 * 
 * @author hyhe
 * @version [版本号, 2016-4-14]
 * @since [产品/模块版本]
 */
public class CounterSignCompleteListener implements TaskListener {

	/**
	 * 默认序列化ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param arg0
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		String approved = (String) delegateTask
				.getVariable(WFConstant.APPROVED);
		if (approved.equals("1")) {
			int agreeCounter = Integer.valueOf(delegateTask.getVariable(
					WFConstant.APPROVEDCOUNTER).toString());
			delegateTask.setVariable(WFConstant.APPROVEDCOUNTER,
					agreeCounter + 1);
		} else {
			if (delegateTask.getVariable(WFConstant.REJECTEDCOUNTER) != null) {
				int agreeCounter = Integer.valueOf(delegateTask.getVariable(
						WFConstant.REJECTEDCOUNTER).toString());
				delegateTask.setVariable(WFConstant.REJECTEDCOUNTER,
						agreeCounter + 1);
			}
		}
	}
}
