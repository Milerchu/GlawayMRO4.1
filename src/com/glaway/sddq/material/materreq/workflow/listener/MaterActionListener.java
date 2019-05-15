package com.glaway.sddq.material.materreq.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.jpo.IJpo;

/**
 * 
 * <配件申请流程根据金额判断走向>
 * 
 * @author public2795
 * @version [版本号, 2018-10-16]
 * @since [产品/模块版本]
 */
public class MaterActionListener implements ExecutionListener {
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
			String TOTALCOST = curJpo.getString("TOTALCOST");
			if (TOTALCOST.equalsIgnoreCase("大于等于一百万")) {

				execution.setVariable("approved", "1");
			} else if (TOTALCOST.equalsIgnoreCase("大于等于五十万小于一百万")) {
				execution.setVariable("approved", "1");
			} else {
				execution.setVariable("approved", "2");
			}
		}
	}

}
