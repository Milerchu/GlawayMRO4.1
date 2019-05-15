package com.glaway.sddq.service.valiorder.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.proxy.ActionCustomClass;

/**
 * 
 * <验证工单 VOCLOSE定制类>
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-6-1]
 * @since [MRO4.1/模块版本]
 */
public class VoCloseAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		if (jpo != null) {
			IJpoSet rangeSet = jpo.getJpoSet("VALIPRORANGE");
			if (rangeSet != null && rangeSet.count() > 0) {
				for (int index = 0; index < rangeSet.count(); index++) {
					IJpo range = rangeSet.getJpo(index);
					// 工单jposet
					IJpoSet orderSet = range.getJpoSet("VALIORDER");
					if (orderSet != null && orderSet.count() > 0) {
						for (int j = 0; j < orderSet.count(); j++) {
							IJpo valiorder = orderSet.getJpo(j);
							valiorder.setValue("status", "关闭");
						}
					}
				}
			}

			// jpo.setValue("status", "关闭");
			jpo.getJpoSet().save();

			// 更新验证计划中数量
			// WorkorderUtil.updatePlanData(office, plannum);
		}

	}
}
