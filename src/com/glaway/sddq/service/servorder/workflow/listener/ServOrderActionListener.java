package com.glaway.sddq.service.servorder.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;

import com.glaway.mro.app.system.action.data.Action;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 服务工单 工单审核action 定制listener
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月19日]
 * @since [产品/模块版本]
 */
public class ServOrderActionListener implements ExecutionListener {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 在工作流执行线上配置的操作
	 */
	private Expression actions;

	/**
	 * 获取操作，并为当前JPO执行操作
	 * 
	 * @param arg0
	 * @throws Exception
	 */
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		IJpo curJpo = (IJpo) execution.getVariable("curJpo");
		String actionText = actions.getExpressionText();
		if (StringUtil.isStrNotEmpty(actionText)) {
			String[] actionIds = actionText.split(",");
			for (int index = 0; index < actionIds.length; index++) {
				String actionid = actionIds[index];
				if (curJpo == null || curJpo.getJpoSet() == null) {
					curJpo = WfControlUtil.getJpoByProInstId(execution
							.getProcessInstanceId());
					if (curJpo == null) {
						curJpo = WfControlUtil.getJpoByUid(execution);

					}
					String orderType = curJpo.getString("SERVORDERTYPE");
					if ("现场技术支持".equals(orderType)) {
						// 现场技术支持类工单审核
						execution.setVariable("approved", 2);
					}
				}
				IJpoSet actionJposet = MroServer.getMroServer().getSysJpoSet(
						"SYS_ACTION",
						"ACTION='" + StringUtil.getSafeSqlStr(actionid) + "'");
				if (!actionJposet.isEmpty()) {
					Action action = (Action) actionJposet.getJpo(0);
					action.executeAction(curJpo);
				}
			}
		}
	}

}
