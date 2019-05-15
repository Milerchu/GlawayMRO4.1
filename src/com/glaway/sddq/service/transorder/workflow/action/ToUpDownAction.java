package com.glaway.sddq.service.transorder.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造工单-上下车action定制类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-15]
 * @since [产品/模块版本]
 */
public class ToUpDownAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {
		// 序列号件上下车操作
		String ordernum = jpo.getString("ordernum");
		IJpoSet exchangeSet = MroServer.getMroServer().getSysJpoSet(
				"EXCHANGERECORD", "TASKORDERNUM='" + ordernum + "'");
		if (!exchangeSet.isEmpty()) {
			WorkorderUtil.swapHistory(exchangeSet, jpo);
		}
		// 非序列号件上下车操作
		IJpoSet consumeSet = MroServer.getMroServer().getSysJpoSet(
				"JXTASKLOSSPART", "JXTASKNUM='" + ordernum + "'");
		if (!consumeSet.isEmpty()) {
			WorkorderUtil.consumeUpDown(consumeSet, jpo);
		}

	}

}
