package com.glaway.sddq.service.failureord.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 故障工单-判断是否有上下车监听
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月8日]
 * @since [产品/模块版本]
 */
public class FailureActionNewListener implements ExecutionListener {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 3426907540140548453L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		// IJpo curJpo = (IJpo) execution.getVariable("curJpo");
		// if (curJpo == null) {

		IJpo curJpo = WfControlUtil.getJpoByProInstId(execution
				.getProcessInstanceId());
		if (curJpo == null) {
			curJpo = WfControlUtil.getJpoByUid(execution);
		}

		// }

		// 有上下车则会给库管员审核
		if (curJpo != null) {
			IJpoSet failturlibset = curJpo.getJpoSet("FAILURELIB");
			IJpoSet exchangerecordset = failturlibset.getJpo().getJpoSet(
					"EXCHANGERECORD");
			IJpoSet jxtasklosspartset = curJpo.getJpoSet("JXTASKLOSSPART");
			if (exchangerecordset.count(GWConstant.P_COUNT_AFTERSAVE) > 0
					|| jxtasklosspartset.count(GWConstant.P_COUNT_AFTERSAVE) > 0) {
				execution.setVariable("approved", "1");

			} else {
				execution.setVariable("approved", "2");
			}

		}

	}

}
