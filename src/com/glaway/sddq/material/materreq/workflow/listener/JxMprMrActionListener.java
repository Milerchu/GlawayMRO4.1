package com.glaway.sddq.material.materreq.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.jpo.IJpo;

/**
 * 
 * <检修领料单流程判断控制类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class JxMprMrActionListener implements ExecutionListener {
	/**
	 * 判断流程走向
	 * 
	 * @param execution
	 * @throws Exception
	 */
	@Override
	public void notify(DelegateExecution execution) throws Exception {

		IJpo curJpo = WfControlUtil.getJpoByProInstId(execution
				.getProcessInstanceId());
		if (curJpo == null) {
			curJpo = WfControlUtil.getJpoByUid(execution);
		}
		if (curJpo != null) {
			String type = curJpo.getString("TYPE");
			if ("领料".equals(type)) {

				execution.setVariable("approved", "1");
			} else {
				execution.setVariable("approved", "2");
			}
		}

	}

}