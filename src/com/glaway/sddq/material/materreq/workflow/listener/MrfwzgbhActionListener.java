package com.glaway.sddq.material.materreq.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;

import com.glaway.mro.app.system.action.data.Action;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <配件申请流程服务主管驳回判断>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class MrfwzgbhActionListener implements ExecutionListener {
	private Expression actions;

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		IJpo curJpo = WfControlUtil.getJpoByProInstId(execution
				.getProcessInstanceId());
		if (curJpo == null) {
			curJpo = WfControlUtil.getJpoByUid(execution);
		}

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
				}
				IJpoSet HMRLINETRANSLINESET = curJpo
						.getJpoSet("MRLINETRANSLINE");
				if (!HMRLINETRANSLINESET.isEmpty()) {
					throw new MroException("服务主管已分配物料不能驳回，如需要驳回，请删除分配的信息再驳回");
				}
				IJpoSet actionJposet = MroServer.getMroServer().getSysJpoSet(
						"SYS_ACTION",
						"ACTION='" + StringUtil.getSafeSqlStr(actionid) + "'");
				if (!actionJposet.isEmpty()) {
					Action action = (Action) actionJposet.getJpo(0);
					action.executeAction(curJpo);
				}
				// }
			}
		}
	}

}
