package com.glaway.sddq.service.transorder.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 是否首台判断监听
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月19日]
 * @since [产品/模块版本]
 */
public class FirstTrainActionListener implements ExecutionListener {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

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

		if (curJpo != null) {
			boolean isFirst = MroServer
					.getMroServer()
					.getSysJpoSet("workorder",
							"ordernum='" + curJpo.getString("ordernum") + "'")
					.getJpo(0).getBoolean("isfirst");
			if (isFirst) {// 首台车

				execution.setVariable("approved", "2");

			}

		}

	}

}
