package com.glaway.mro.app.system.workflow.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.form.DefaultTaskFormHandler;
import org.activiti.engine.impl.form.FormPropertyHandler;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;

import com.glaway.mro.app.system.workflow.util.WFConstant;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.jpo.IJpo;

/**
 * 
 * 工作流任务分配监听器
 * 
 * @author hyhe
 * @version [MRO4.0, 2016-5-31]
 * @since [MRO4.0/工作流设计器]
 */
public class MroActivitiUserAssignListener implements ExecutionListener {
	private static final long serialVersionUID = 1L;

	private Expression approvedCounter;

	private Expression rejectedCounter;

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		ExecutionEntity executionTemp = (ExecutionEntity) execution;
		TransitionImpl eventSource = (TransitionImpl) executionTemp
				.getEventSource();
		// 获取指向的节点
		ActivityImpl activityImpl = eventSource.getDestination();

		// 获取指向的任务节点
		TaskDefinition destTaskDefinition = (TaskDefinition) activityImpl
				.getProperties().get("taskDefinition");
		// 获取表单配置
		List<FormPropertyHandler> formPropertys = ((DefaultTaskFormHandler) destTaskDefinition
				.getTaskFormHandler()).getFormPropertyHandlers();

		IJpo curJpo = WfControlUtil.getJpoByProInstId(execution
				.getProcessInstanceId());
		if (curJpo == null) {
			curJpo = WfControlUtil.getJpoByUid(execution);
		}
		if (curJpo != null) {
			// 获取下一个任务节点
			for (FormPropertyHandler property : formPropertys) {
				if (property.getId().equals(WFConstant.GROUPS)) {
					String groupName = property.getName();
					// 通过组名来获取组中用户列表
					List<String> users = WfControlUtil.getUserListbyGroup(
							curJpo, groupName);
					if (users.size() > 0) {
						execution.setVariable(WFConstant.USERS, users);
					} else {
						throw new AppException("wfinfo", "userisnull");
					}
					if (approvedCounter != null) {
						execution.setVariable(WFConstant.APPROVEDCOUNTER,
								approvedCounter.getExpressionText());
					}
					if (rejectedCounter != null) {
						execution.setVariable(WFConstant.REJECTEDCOUNTER,
								rejectedCounter.getExpressionText());
					}
				}
			}
		}
	}
}
