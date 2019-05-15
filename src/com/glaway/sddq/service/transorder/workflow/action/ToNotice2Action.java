package com.glaway.sddq.service.transorder.workflow.action;

import java.util.Vector;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造工单-公司级问题通知操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月27日]
 * @since [产品/模块版本]
 */
public class ToNotice2Action implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {
		// 通知售后技术经理
		if (jpo != null) {
			// 项目编号
			String prjnum = jpo.getString("projectnum");
			// 技术经理
			Vector<String> personVector = WorkorderUtil.getProjectRole(prjnum,
					"售后技术经理");
			String[] receivers = new String[personVector.size()];
			for (int i = 0; i < personVector.size(); i++) {
				receivers[i] = personVector.get(i);
			}

			String content = "改造工单：" + jpo.getString("ordernum")
					+ "首台改造出现异常无法继续进行！";

			WorkorderUtil.sendMsg("TRANSORDER", jpo.getId(), receivers,
					"首台改造异常", content);
		}

	}

}
