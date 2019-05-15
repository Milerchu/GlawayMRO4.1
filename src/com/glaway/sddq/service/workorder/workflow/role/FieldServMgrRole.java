package com.glaway.sddq.service.workorder.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;

/**
 * 
 * 服务工单-工作流-现场服务经理-角色定制类
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-5-24]
 * @since [MRO4.1/模块版本]
 */
public class FieldServMgrRole implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String parameter)
			throws MroException {
		// 站点负责人
		String owner = curjpo.getString("WHICHSTATION.OWNER");
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in ('" + owner + "')");
		personSet.reset();

		return personSet;
	}

}
