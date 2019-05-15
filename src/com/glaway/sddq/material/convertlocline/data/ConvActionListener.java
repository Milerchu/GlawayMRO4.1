package com.glaway.sddq.material.convertlocline.data;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;

/**
 * 
 * <调拨转库单流程控制类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class ConvActionListener implements ExecutionListener {
	/**
	 * 根据状态判断流程是否可继续
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
			String status = curJpo.getString("status");
			if (status.equalsIgnoreCase("未接收")) {
				throw new MroException("请先接收完成所有物料在完成工作流");
			}
		}
	}

}
