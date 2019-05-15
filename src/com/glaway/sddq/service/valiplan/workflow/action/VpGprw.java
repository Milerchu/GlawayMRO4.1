package com.glaway.sddq.service.valiplan.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 验证计划-改派任务操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月24日]
 * @since [产品/模块版本]
 */
public class VpGprw implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo curjpo, String arg1)
			throws MroException {
		// 计划编制人
		String editor = curjpo.getString("planeditor");
		if (StringUtil.isStrEmpty(editor)) {
			// 计划编制人为空，不能发送工作流
			throw new MroException("valiplan", "noeditor");
		}

	}

}
