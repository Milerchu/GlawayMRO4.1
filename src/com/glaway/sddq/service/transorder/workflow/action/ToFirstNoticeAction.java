package com.glaway.sddq.service.transorder.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造工单-首台验证通知操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月20日]
 * @since [产品/模块版本]
 */
public class ToFirstNoticeAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {

		if (jpo != null) {

			String[] receivers = {
					jpo.getString("transplan.TRANSNOTICE.PRODLINEMGR"),
					jpo.getString("transplan.TRANSNOTICE.TECHMGR"),
					jpo.getString("transplan.TRANSNOTICE.QUALITYENG") };// 改造通知单审核人

			String subject = "首台改造通知";
			String content = jpo.getString("ordernum") + "改造工单正在进行首台改造，请知晓！";
			// 发送系统内消息
			WorkorderUtil.sendMsg("TRANSORDER", jpo.getId(), receivers,
					subject, content);

		}

	}

}
