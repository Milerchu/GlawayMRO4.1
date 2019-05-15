package com.glaway.sddq.service.failureord.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 故障工单- 上下车操作定制类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-13]
 * @since [产品/模块版本]
 */
public class UpDownAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		if (jpo != null) {

			// 工单编号
			String ordernum = jpo.getString("ordernum");

			/* 上下车操作（主要针对不创建三包订单的） */
			// 耗损件上下车操作
			// 耗损件记录子表
			IJpoSet consumeSet = MroServer.getMroServer().getSysJpoSet(
					"JXTASKLOSSPART", "jxtasknum='" + ordernum + "'");
			if (consumeSet != null && consumeSet.count() > 0) {
				WorkorderUtil.consumeUpDown(consumeSet, jpo);
			}
			// 周转件上下车记录子表
			IJpoSet exchangeSet = MroServer.getMroServer().getSysJpoSet(
					"EXCHANGERECORD", "failureordernum='" + ordernum + "'");
			if (exchangeSet != null && exchangeSet.count() > 0) {
				WorkorderUtil.swapHistory(exchangeSet, jpo);
			}

			/* 串换件操作 */
			// 串换件记录子表
			IJpoSet swapSet = MroServer.getMroServer().getSysJpoSet("CHASSET",
					"ORDERNUM='" + ordernum + "'");
			if (swapSet != null && swapSet.count() > 0) {
				// 执行串换件操作
				WorkorderUtil.swapParts(swapSet);
			}
		}

	}

}
