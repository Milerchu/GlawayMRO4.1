package com.glaway.sddq.service.transplan.workflow.role;

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
 * 改造计划-项目成员角色
 * 
 * @author zhuhao
 * @version [版本号, 2018年6月24日]
 * @since [产品/模块版本]
 */
public class ProjectRole implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String role)
			throws MroException {
		String persons = "";
		if ("验证".equals(curjpo.getString("PLANTYPE"))) {// 验证计划
			// 获取项目角色
			String projectnum = curjpo.getString("TRANSPRJNUM");
			Vector<String> personVector = WorkorderUtil.getProjectRole(
					projectnum, role);

			for (int i = 0; i < personVector.size(); i++) {
				persons += "'" + personVector.get(i) + "',";
			}

		} else {// 改造计划

			// 改造车辆分布
			IJpoSet transrangeSet = curjpo.getJpoSet("TRANSDIST");
			// transrangeSet.setQueryWhere("TRANSPLANNUM='"
			// + curjpo.getString("TRANSPLANNUM") + "'");
			// transrangeSet.reset();

			if (!transrangeSet.isEmpty()) {
				for (int index = 0; index < transrangeSet.count(); index++) {
					IJpo ts = transrangeSet.getJpo(index);
					// 获取项目角色
					String projectnum = ts.getString("PROJECTNUM");
					Vector<String> personVector = WorkorderUtil.getProjectRole(
							projectnum, role);
					for (int j = 0; j < personVector.size(); j++) {
						persons += "'" + personVector.get(j) + "',";
					}
				}
			}

		}

		if (StringUtil.isStrNotEmpty(persons)) {
			// 去除逗号
			persons = persons.substring(0, persons.length() - 1);
		}
		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();

		return personSet;
	}

}
