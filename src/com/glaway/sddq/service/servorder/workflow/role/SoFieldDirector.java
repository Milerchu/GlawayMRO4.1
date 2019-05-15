package com.glaway.sddq.service.servorder.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 服务工单工作流- 现场服务主管 角色定制类
 * 
 * @author zhuhao
 * @version [版本号, 2018年6月24日]
 * @since [产品/模块版本]
 */
public class SoFieldDirector implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String arg1)
			throws MroException {
		// 办事处编号
		String whichoffice = curjpo.getString("whichoffice");
		String owner = "";
		if (StringUtil.isStrNotEmpty(whichoffice)) {
			// 办事处负责人
			owner = curjpo.getString("whichoffice.owner");
		} else {
			// 服务工程师
			owner = curjpo.getString("SERVENGINEER");
		}
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in ('" + owner + "')");
		personSet.reset();

		return personSet;
	}

}
