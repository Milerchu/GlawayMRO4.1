package com.glaway.sddq.service.valiorder.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 验证工单 现场处理人 角色类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月10日]
 * @since [产品/模块版本]
 */
public class VoDealPersonRole implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo jpo, String arg1) throws MroException {
		String persons = "";
		// 现场处理人子表
		IJpoSet dealSet = jpo.getJpoSet("JXTASKEXECPERSON");
		if (!dealSet.isEmpty()) {
			for (int index = 0; index < dealSet.count(); index++) {
				IJpo dealperson = dealSet.getJpo(index);
				persons += "'" + dealperson.getString("PERSONNUM") + "',";
			}
			if (StringUtil.isStrNotEmpty(persons)) {
				// 去除末尾逗号
				persons = persons.substring(0, persons.length() - 1);
			}

		} else {
			throw new MroException("valiorder", "nodealperson");
		}

		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				jpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();
		return personSet;
	}
}
