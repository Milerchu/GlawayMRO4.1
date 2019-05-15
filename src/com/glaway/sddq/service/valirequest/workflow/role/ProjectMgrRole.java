package com.glaway.sddq.service.valirequest.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 验证申请单 项目经理 角色定制类
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-5-28]
 * @since [MRO4.1/模块版本]
 */
public class ProjectMgrRole implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String parameter)
			throws MroException {
		// 项目编号
		String prjnum = curjpo.getString("PROJECTNUM");
		String persons = WorkorderUtil.getPrjManager(prjnum);

		// 如果为空，则取申请人personid
		if (StringUtil.isStrEmpty(persons)) {
			persons = curjpo.getString("APPPERSON");
		}

		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in ('" + persons + "')");
		personSet.reset();

		return personSet;

	}

}
