package com.glaway.sddq.service.transplan.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;

/**
 * 
 * 改造计划-计划编制角色类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月11日]
 * @since [产品/模块版本]
 */
public class TpEditor implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo jpo, String arg1) throws MroException {
		String persons = "";
		persons = "'" + jpo.getString("transnotice.appperson") + "'";
		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				jpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();

		return personSet;
	}

}
