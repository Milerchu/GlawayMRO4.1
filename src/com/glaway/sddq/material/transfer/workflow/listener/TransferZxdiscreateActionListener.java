package com.glaway.sddq.material.transfer.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;

/**
 * 
 * <装箱单流程判断是否自动生成装箱单流程控制类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class TransferZxdiscreateActionListener implements ExecutionListener {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取操作，并为当前JPO执行操作
	 * 
	 * @param arg0
	 * @throws Exception
	 */
	@Override
	public void notify(DelegateExecution execution) throws MroException {
		IJpo curJpo = (IJpo) execution.getVariable("curJpo");
		// if (curJpo == null) {
		curJpo = WfControlUtil.getJpoByProInstId(execution
				.getProcessInstanceId());
		if (curJpo == null) {
			curJpo = WfControlUtil.getJpoByUid(execution);
		}
		// }
		if (curJpo != null) {
			String orderType = curJpo.getString("TRANSFERMOVETYPE");
			if ("中心到中心".equals(orderType)) {
				execution.setVariable("approved", "1");
			} else {
				execution.setVariable("approved", "2");
			}
		}
	}
}
