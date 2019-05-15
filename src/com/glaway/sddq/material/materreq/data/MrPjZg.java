package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;

/**
 * 
 * 配件申请-项目角色配件主管定制类
 * 
 * @author zzx
 * @version [版本号, 2018年8月17日]
 * @since [产品/模块版本]
 */
public class MrPjZg implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String param)
			throws MroException {
		String persons = "";

		IJpoSet postSet = MroServer.getMroServer().getJpoSet("post",
				curjpo.getUserServer());
		postSet.setUserWhere("POSTNUM ='010608000010'");
		postSet.reset();
		if (postSet != null && postSet.count() > 0) {
			IJpoSet personSet = postSet.getJpo().getJpoSet("PERSON");
			personSet.setUserWhere("1=1");
			personSet.reset();

			return personSet;
		} else {
			throw new MroException("MRJHZG", "MRSQJZG");
		}
	}

}
