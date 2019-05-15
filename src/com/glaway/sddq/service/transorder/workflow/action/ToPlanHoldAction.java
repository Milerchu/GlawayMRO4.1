package com.glaway.sddq.service.transorder.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造工单-计划挂起操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月20日]
 * @since [产品/模块版本]
 */
public class ToPlanHoldAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {

		if (jpo != null) {

			String[] receivers = {
					jpo.getString("transplan.TRANSNOTICE.PRODLINEMGR"),
					jpo.getString("transplan.TRANSNOTICE.TECHMGR"),
					jpo.getString("transplan.TRANSNOTICE.QUALITYENG") };// 改造通知单审核人

			String subject = "改造工单异常";
			String content = "改造工单：" + jpo.getString("ordernum")
					+ " 改造发生异常，请注意";

			// 计划状态挂起
			IJpo plan = jpo.getJpoSet("TRANSPLAN").getJpo();
			plan.setValue("status", "挂起",
					GWConstant.P_NOVALIDATION_AND_NOACTION);

			// 发送系统内消息
			WorkorderUtil.sendMsg("TRANSORDER", jpo.getId(), receivers,
					subject, content);

			jpo.getJpoSet().save();

		}

	}

}
