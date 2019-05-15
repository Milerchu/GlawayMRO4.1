package com.glaway.sddq.material.transfer.workflow.listener;

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
 * <装箱单流程控制类，全部接收不能驳回>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class ZxdReceiveNotBhActionListener implements ExecutionListener {
	private Expression actions;

	@Override
	public void notify(DelegateExecution execution) throws MroException {
		// TODO Auto-generated method stub
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
				}

				if (curJpo != null) {
					// 取装箱单编号
					String transfernum = curJpo.getString("TRANSFERNUM");
					boolean flag = false;
					// 取装箱单行表Jposet
					IJpoSet transferlineJposet = MroServer.getMroServer()
							.getSysJpoSet("TRANSFERLINE",
									"TRANSFERNUM='" + transfernum + "'");
					transferlineJposet.reset();

					if (transferlineJposet != null
							&& transferlineJposet.count() > 0) {
						// 取装箱单行中的状态值
						for (int i = 0; i < transferlineJposet.count(); i++) {
							String status = transferlineJposet.getJpo(i)
									.getString("STATUS");
							if (!status.equals("已接收")) {
								flag = true;
							}
						}
						if (!flag) {
							throw new MroException("装箱单行已全部接收，不能驳回");
						}

						IJpoSet actionJposet = MroServer
								.getMroServer()
								.getSysJpoSet(
										"SYS_ACTION",
										"ACTION='"
												+ StringUtil
														.getSafeSqlStr(actionid)
												+ "'");
						if (!actionJposet.isEmpty()) {
							Action action = (Action) actionJposet.getJpo(0);
							action.executeAction(curJpo);
						}
					}
				}
			}

		}

	}

}
