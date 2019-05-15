package com.glaway.sddq.service.valiplan.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 验证计划-计划编制人角色类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月24日]
 * @since [产品/模块版本]
 */
public class VPEditor implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curJpo, String param)
			throws MroException {
		String persons = "";
		// 车型大类
		String jdc = curJpo.getString("VALIREQUEST.JDC");
		if (StringUtil.isStrEmpty(jdc)) {
			throw new MroException("", "无车型大类，无法匹配编制人角色！");
		}
		// 人员组
		String group = "";
		if ("机车".equals(jdc)) {
			group = "100207";
		} else if ("动车".equals(jdc)) {
			group = "100208";
		} else {// 城轨
			group = "100209";
		}
		persons = WorkorderUtil.getPersonsFromPersonGroup(group);
		if (StringUtil.isStrEmpty(persons)) {
			throw new MroException("", "人员组中无角色！");
		}
		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curJpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();

		return personSet;
	}

}
