package com.glaway.sddq.material.jxmpr.data;

import java.util.Vector;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 服务转改造领料单-售后计划经理定制类
 * 
 * @author zzx
 * @version [版本号, 2018年8月17日]
 * @since [产品/模块版本]
 */
public class FwChangMprShpanner implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String role)
			throws MroException {
		// 取改造计划编号
		String personids = "";
		String transplannum = curjpo.getString("TRANSPLANNUM");
		IJpoSet transplanset = MroServer.getMroServer().getJpoSet("TRANSPLAN",
				curjpo.getUserServer());
		transplanset.setUserWhere("TRANSPLANNUM='" + transplannum + "'");
		transplanset.reset();
		// 根据关联关系取改造车辆里的数据
		IJpoSet transrangeset = transplanset.getJpo().getJpoSet("TRANSDIST");
		for (int i = 0; i < transrangeset.count(); i++) {
			String projectnum = transrangeset.getJpo(i).getString("PROJECTNUM");

			Vector<String> personidvec = WorkorderUtil.getProjectRole(
					projectnum,
					role);
			for (int j = 0; j < personidvec.size(); j++) {
				String personid = personidvec.get(j);

				personids = personids + "'" + personid + "',";

			}
		}

		personids = personids.substring(0, personids.length() - 1);
		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());

		personSet.setUserWhere("personid in (" + personids + ")");

		return personSet;
	}

}
