package com.glaway.sddq.material.materreq.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;

/**
 * 
 * <配件申请流程服务主管审批控制类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class MrMaterActionListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		IJpo curJpo = WfControlUtil.getJpoByProInstId(execution
				.getProcessInstanceId());
		if (curJpo == null) {
			curJpo = WfControlUtil.getJpoByUid(execution);
		}
		if (curJpo != null) {
			IJpoSet HMRLINETRANSLINESET = curJpo.getJpoSet("MRLINETRANSLINE");
			if (HMRLINETRANSLINESET.isEmpty()) {
				throw new MroException("服务主管未分配，不能通过，如需通过请分配物料");
			} else {
				IJpoSet MRLINETRANSLINESET = curJpo
						.getJpoSet("MRLINETRANSLINE");
				MRLINETRANSLINESET.setUserWhere("TRANSTYPE='计划经理协调'");
				MRLINETRANSLINESET.reset();
				if (MRLINETRANSLINESET.count() > 0) {
					execution.setVariable("approved", "2");
				} else {
					execution.setVariable("approved", "1");
				}
			}
		}
	}

}
