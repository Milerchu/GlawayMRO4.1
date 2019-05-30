package com.glaway.sddq.service.failureord.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 故障工单-技术主管审核后监听
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月8日]
 * @since [产品/模块版本]
 */
public class FailureActionListener implements ExecutionListener {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		IJpo curJpo = (IJpo) execution.getVariable("curJpo");
		if (curJpo == null) {
			curJpo = WfControlUtil.getJpoByProInstId(execution
					.getProcessInstanceId());
			if (curJpo == null) {
				curJpo = WfControlUtil.getJpoByUid(execution);
			}

		}
		if (curJpo != null) {
			// 根据故障后果判断是否进行审计审核
			IJpoSet failureSet = MroServer.getMroServer().getSysJpoSet(
					"failurelib",
					"failureordernum='" + curJpo.getString("ordernum") + "'");
			String faultconseq = failureSet.getJpo(0).getString("FAULTCONSEQ");
			if ("1".equals(execution.getVariable("approved"))) {
				if (!WorkorderUtil.isImpFault(faultconseq)) {
					execution.setVariable("approved", "3");
				}
			}
		}
	}
}
