package com.glaway.sddq.service.valiplan.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 验证计划-设置计划编制人操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月24日]
 * @since [产品/模块版本]
 */
public class VpSetEditor implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {
		// 项目编号
		String projectnum = jpo.getString("TRANSPRJNUM");
		if (StringUtil.isStrEmpty(projectnum)) {
			throw new MroException("valiplan", "noprjnum");
		}
		IJpoSet rangeSet = jpo.getJpoSet("VALIPRORANGE");
		boolean flag = false;
		for (int index = 0; index < rangeSet.count(); index++) {
			IJpo range = rangeSet.getJpo(index);
			if (StringUtil.isStrEmpty(range.getString("PRODUCTCODE"))
					|| StringUtil.isStrEmpty(range.getString("OWNERCUSTOMER"))
					|| StringUtil.isStrEmpty(range.getString("TRANSMODELS"))
					|| StringUtil.isStrEmpty(range.getString("BVERSION"))) {
				flag = true;
				break;
			}
		}
		if (flag) {
			throw new MroException("valiplan", "noproductcode");
		}
		// 设置计划编制人为当前操作人
		// jpo.setValue("planeditor", jpo.getUserInfo().getPersonId());
		jpo.getJpoSet().save();

	}

}
