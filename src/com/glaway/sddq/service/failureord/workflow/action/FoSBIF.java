package com.glaway.sddq.service.failureord.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 故障工单-三包业务订单接口触发操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月1日]
 * @since [产品/模块版本]
 */
public class FoSBIF implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {
		// 调用三包业务订单接口
		if (jpo != null) {
			if ((!jpo.getJpoSet("failurelib").getJpo()
					.getJpoSet("EXCHANGERECORD").isEmpty())
					|| (!jpo.getJpoSet("JXTASKLOSSPART").isEmpty())) {

				String ret = WorkorderUtil.tomsg(jpo);
				if (!"S".equals(ret)) {
					throw new MroException("错误", "三包订单接口访问出错:" + ret);
				}
				jpo.getJpoSet().save();
			}
		}

	}

}
