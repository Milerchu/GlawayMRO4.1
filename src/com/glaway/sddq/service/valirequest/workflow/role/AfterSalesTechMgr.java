package com.glaway.sddq.service.valirequest.workflow.role;

import java.util.Vector;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 验证申请单-售后质量经理角色定制类
 * 
 * @author zhuhao
 * @version [版本号, 2018年6月24日]
 * @since [产品/模块版本]
 */
public class AfterSalesTechMgr implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String arg1)
			throws MroException {
		// 项目编号
		String prjnum = curjpo.getString("projectnum");
		Vector<String> personVector = WorkorderUtil.getProjectRole(prjnum,
				"售后技术经理");
		String persons = "";
		for (int i = 0; i < personVector.size(); i++) {
			persons += "'" + personVector.get(i) + "',";
		}
		// 如果为空
		if (StringUtil.isStrEmpty(persons)) {
			throw new MroException("valiplan", "rolenull");
		}
		// 去除逗号
		persons = persons.substring(0, persons.length() - 1);
		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();

		return personSet;
	}

}
