package com.glaway.sddq.service.transnotice.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造通知单-技术方案审核角色类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月10日]
 * @since [产品/模块版本]
 */
public class TNTechScheme implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String arg1)
			throws MroException {

		String persons = "";
		String personGroup = "";
		// 改造产品范围
		IJpoSet transrangeSet = curjpo.getJpoSet("TRANSRANGE");
		if (!transrangeSet.isEmpty()) {
			// 取第一个范围jpo
			IJpo transrange = transrangeSet.getJpo(0);
			// 车型
			String models = transrange.getString("TRANSMODELS");
			// 根据车型首字母判断机动城
			switch (models.charAt(0)) {
			case 'J':
				personGroup = "TNTECH1";
				break;
			case 'D':
				personGroup = "TNTECH2";
				break;
			case 'C':
				personGroup = "TNTECH3";
				break;
			}
			persons = WorkorderUtil.getPersonsFromPersonGroup(personGroup);

		}

		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();

		return personSet;
	}

}
