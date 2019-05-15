package com.glaway.sddq.material.materreq.data;

import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 配件申請-项目角色售后计划经理定制类
 * 
 * @author zzx
 * @version [版本号, 2018年8月17日]
 * @since [产品/模块版本]
 */
public class MrShPlanEnginer implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String role)
			throws MroException {
		// 1.获取当前项目编号
		String where = "";
		String personids = "";
		// String mrtype = curjpo.getString("MRTYPE");
		// IJpoSet mrlineset = curjpo.getJpoSet("MRLINE");
		// if (mrtype.equals("零星")) {
		// for (int i = 0; i < mrlineset.count(); i++) {
		// String project = mrlineset.getJpo(i).getString("PROJECT");
		//
		// Vector<String> personidvec = WorkorderUtil.getProjectRole(
		// project, role);
		// for (int j = 0; j < personidvec.size(); j++) {
		// String personid = personidvec.get(j);
		//
		// personids = personids + "'" + personid + "',";
		//
		// }
		// }
		// } else {
		// String project = curjpo.getString("PROJECT");
		//
		// Vector<String> personidvec = WorkorderUtil.getProjectRole(project,
		// role);
		// for (int i = 0; i < personidvec.size(); i++) {
		// String personid = personidvec.get(i);
		//
		// personids = personids + "'" + personid + "',";
		//
		// }
		// }
		String project = curjpo.getString("PROJECT");

		Vector<String> personidvec = WorkorderUtil
				.getProjectRole(project, role);
		for (int i = 0; i < personidvec.size(); i++) {
			String personid = personidvec.get(i);

			personids = personids + "'" + personid + "',";

		}
		if (!StringUtils.isEmpty(personids)) {
			personids = personids.substring(0, personids.length() - 1);
		}

		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());

		personSet.setUserWhere("personid in (" + personids + ")");

		return personSet;
	}

}
