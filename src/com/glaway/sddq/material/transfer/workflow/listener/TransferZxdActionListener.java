package com.glaway.sddq.material.transfer.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;

/**
 * 
 * <装箱单流程控制类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class TransferZxdActionListener implements ExecutionListener {
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
		if (curJpo == null) {
			curJpo = WfControlUtil.getJpoByProInstId(execution
					.getProcessInstanceId());
			if (curJpo == null) {
				curJpo = WfControlUtil.getJpoByUid(execution);
			}
		}
		if (curJpo != null) {
			String orderType = curJpo.getString("TRANSFERMOVETYPE");
			String iscreate = curJpo.getString("ISCREATE");
			String status = curJpo.getString("STATUS");
			String RECEIVESTOREROOM = curJpo.getString("RECEIVESTOREROOM");
			if ("否".equals(iscreate)) {
				if ("中心到中心".equals(orderType)) {
					if(RECEIVESTOREROOM.equalsIgnoreCase("Y1090")||RECEIVESTOREROOM.equalsIgnoreCase("QT1083")){
						execution.setVariable("approved", "2");	
					}else{
						execution.setVariable("approved", "1");
					}
				} else if (!orderType.equals("中心到中心")) {
					execution.setVariable("approved", "2");
				}
			} else {
				execution.setVariable("approved", "3");
			}

			if ("申请人修改".equals(status)) {
				if ("中心到中心".equals(orderType)) {
					execution.setVariable("approved", "1");
				} else if (!orderType.equals("中心到中心")) {
					execution.setVariable("approved", "2");
				}
			}
		}
	}
}