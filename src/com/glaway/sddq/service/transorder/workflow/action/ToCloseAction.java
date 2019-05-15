package com.glaway.sddq.service.transorder.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 改造工单-toclose关闭操作定制类
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-5-31]
 * @since [MRO4.1/模块版本]
 */
public class ToCloseAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		if (jpo != null) {

			// 修改改造分布中的剩余数量
			IJpoSet distSet = jpo.getJpoSet("TRANSDIST");
			if (!distSet.isEmpty()) {
				IJpo dist = distSet.getJpo(0);
				int surplus = dist.getInt("SURPLUS");
				dist.setValue("SURPLUS", (surplus - 1) > 0 ? (surplus - 1) : 0,
						GWConstant.P_NOVALIDATION);
			}

			jpo.setValue("status", "关闭");
			jpo.getJpoSet().save();

			// 更新改造计划中改造分布中的改造剩余数量
			// WorkorderUtil.updatePlanData(office, plannum);

		}

	}
}
