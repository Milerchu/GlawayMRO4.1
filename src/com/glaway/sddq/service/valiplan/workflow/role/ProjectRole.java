package com.glaway.sddq.service.valiplan.workflow.role;

import java.util.Vector;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 验证计划-项目角色类
 * 
 * @author zhuhao
 * @version [版本号, 2018年6月24日]
 * @since [产品/模块版本]
 */
public class ProjectRole implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String role)
			throws MroException {
		// 项目编号
		String projectnum = curjpo.getString("TRANSPRJNUM");
		Vector<String> personVector = WorkorderUtil.getProjectRole(projectnum,
				role);
		String persons = "";
		for (int i = 0; i < personVector.size(); i++) {
			persons += "'" + personVector.get(i) + "',";
		}
		if (StringUtil.isStrEmpty(persons)) {
			throw new MroException("valiplan", "rolenull");
		}
		// 去除逗号
		persons = persons.substring(0, persons.length() - 1);
		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();

		return personSet;
	}

}
