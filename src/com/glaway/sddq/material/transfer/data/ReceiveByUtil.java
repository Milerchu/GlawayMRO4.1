package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * <装箱单流程接收人角色类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class ReceiveByUtil implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String param)
			throws MroException {
		String persons = "";
		// 获取接收人
		String receiveby = curjpo.getString("receiveby");
		if (receiveby.isEmpty()) {
			persons = WorkorderUtil.getPersonsFromPersonGroup("RECEIVE");
		} else {
			persons = "'" + receiveby + "'";
		}
		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();
		return personSet;

	}

}
