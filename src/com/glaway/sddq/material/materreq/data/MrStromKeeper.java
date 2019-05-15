package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;

/**
 * 
 * 配件申请-项目角色库管员定制类
 * 
 * @author zzx
 * @version [版本号, 2018年8月17日]
 * @since [产品/模块版本]
 */
public class MrStromKeeper implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String arg1)
			throws MroException {

		// 1.获取配件申请行的Jposet
		String keepers = "";
		IJpoSet mrlineset = curjpo.getJpoSet("MRLINE");
		for (int i = 0; i < mrlineset.count(); i++) {
			IJpoSet mrlinetransferset = mrlineset.getJpo(i).getJpoSet(
					"MRLINETRANSFER");
			for (int j = 0; j < mrlinetransferset.count(); j++) {
				String keeper = mrlinetransferset.getJpo(j).getString(
"KEEPER");
				keepers += "'" + keeper + "',";

			}
		}
		keepers = keepers.substring(0, keepers.length() - 1);
		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());

		personSet.setUserWhere("personid in (" + keepers + ")");
		return personSet;
	}
}