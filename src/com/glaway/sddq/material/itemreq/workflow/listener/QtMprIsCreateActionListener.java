package com.glaway.sddq.material.itemreq.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.jpo.IJpo;

/**
 * 
 * <QT领料单流程控制类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class QtMprIsCreateActionListener implements ExecutionListener {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		IJpo curJpo = (IJpo) execution.getVariable("curJpo");
		if (curJpo == null) {
			curJpo = WfControlUtil.getJpoByProInstId(execution
					.getProcessInstanceId());
			if (curJpo == null) {
				curJpo = WfControlUtil.getJpoByUid(execution);
			}
		}
		if (curJpo != null) {
			String iscreate = curJpo.getString("ISCREATE");
			if ("是".equals(iscreate)) {
				execution.setVariable("approved", "1");

			} else {
				execution.setVariable("approved", "2");
			}
		}
	}

}
