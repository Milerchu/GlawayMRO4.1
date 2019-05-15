package com.glaway.sddq.service.transplan.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造计划-办事处关闭计划操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年10月23日]
 * @since [产品/模块版本]
 */
public class TpOfficeCloseAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {

		IJpoSet transDistSet = jpo.getJpoSet("TRANSDIST");
		for (int i = 0; i < transDistSet.count(); i++) {

			IJpo transDist = transDistSet.getJpo(i);
			String resp = WorkorderUtil.getOfficeDirectorByOfficenum(transDist
					.getString("WHICHOFFICE"));// 办事处主任
			if (jpo.getString("wfacter").equalsIgnoreCase(resp)) {// 判断当前办事处的工单
				IJpoSet orderSet = transDist.getJpoSet("TRANSORDER");
				for (int j = 0; j < orderSet.count(); j++) {

					IJpo order = orderSet.getJpo(j);
					String orderStatus = order.getString("status");
					if (!SddqConstant.WO_STATUS_GB.equals(orderStatus)) {// 存在未关闭的工单不能关闭计划
						throw new MroException("transplan", "cannotclose");
					}

				}
			}

		}

	}

}
