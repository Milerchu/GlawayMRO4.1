package com.glaway.sddq.service.transorder.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 改造工单-TOCLZ处理中操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月11日]
 * @since [产品/模块版本]
 */
public class ToCLZ implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {
		// TODO Auto-generated method stub
		if (jpo != null) {

			// jpo.setValue("status", "处理中",
			// GWConstant.P_NOVALIDATION_AND_NOACTION);

			if (jpo.getBoolean("isfirst")) {// 首台改造工单
				IJpoSet planset = MroServer.getMroServer().getSysJpoSet(
						"transplan",
						"transplannum='" + jpo.getString("plannum") + "'");
				if (!planset.isEmpty()) {
					IJpo plan = planset.getJpo(0);
					if (plan.getDate("STARTDATE") == null) {
						// 设置启动时间
						plan.setValue("STARTDATE", MroServer.getMroServer()
								.getDate());
					}
					planset.save();
				}
			}

			String ordernum = jpo.getString("ORDERNUM");

			IJpoSet orderSet = MroServer.getMroServer().getSysJpoSet(
					"WORKORDER", "ordernum='" + ordernum + "'");
			if (!orderSet.isEmpty()) {
				orderSet.getJpo(0).setValue("status", "处理中",
						GWConstant.P_NOVALIDATION_AND_NOACTION);
				orderSet.save();
			}

			// jpo.getJpoSet().save();
		}
	}

}
