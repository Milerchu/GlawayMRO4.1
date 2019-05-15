package com.glaway.sddq.material.materborrow.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.jpo.IJpo;
/**
 * 
 * <配件借用审批流程控制类，根据审批人判断不同走向>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class BorrowActionListener implements ExecutionListener {
	@Override
	public void notify(DelegateExecution execution) throws Exception {

		IJpo curJpo = WfControlUtil.getJpoByProInstId(execution
				.getProcessInstanceId());
		if (curJpo == null) {
			curJpo = WfControlUtil.getJpoByUid(execution);
		}
		if (curJpo != null) {
			String actionperson = curJpo.getString("actionperson");
			if ("仓储物流主管".equals(actionperson)) {
				execution.setVariable("approved", "1");
			} else {
				execution.setVariable("approved", "2");
			}
		}

	}
}
