package com.glaway.sddq.service.transnotice.workflow.role;

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
 * 改造通知单-售后质量经理定制类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月10日]
 * @since [产品/模块版本]
 */
public class TNQAMgr implements RoleCustomClass {
	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String role)
			throws MroException {
		// 改造产品范围
		IJpoSet transrangeSet = curjpo.getJpoSet("TRANSRANGE");
		String persons = "";
		if (!transrangeSet.isEmpty()) {

			for (int index = 0; index < transrangeSet.count(); index++) {
				IJpo transrange = transrangeSet.getJpo(index);
				// 项目编号
				String prjnum = transrange.getString("projectnum");
				Vector<String> personVect = WorkorderUtil.getProjectRole(
						prjnum, role);
				for (int j = 0; j < personVect.size(); j++) {
					persons += "'" + personVect.get(j) + "',";
				}

			}
			persons = persons.substring(0, persons.length() - 1);
		}
		// 如果为空，则取申请人personid
		if (StringUtil.isStrEmpty(persons)) {
			persons = "'" + curjpo.getString("APPPERSON") + "'";
		}

		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();

		return personSet;
	}
}
