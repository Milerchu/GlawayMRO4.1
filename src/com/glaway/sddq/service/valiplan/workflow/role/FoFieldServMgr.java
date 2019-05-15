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
 * 故障工单-现场服务经理角色定制类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月10日]
 * @since [产品/模块版本]
 */
public class FoFieldServMgr implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo jpo, String role) throws MroException {
		// 项目编号
		String projectnum = jpo.getString("PROJECTNUM");
		if (StringUtil.isStrEmpty(projectnum)) {

			throw new MroException("workorder", "noproject");

		}
		Vector<String> personVector = WorkorderUtil.getProjectRole(projectnum,
				role);
		String persons = "";
		// 人员列表
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
				jpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();
		return personSet;
	}

}
