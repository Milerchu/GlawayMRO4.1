package com.glaway.sddq.service.servorder.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 服务工单-技术主管角色类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月10日]
 * @since [产品/模块版本]
 */
public class SoTechMgr implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String param)
			throws MroException {
		String persons = "";
		// 办事处
		String whichoffice = curjpo.getString("whichoffice");
		// mdm组织id
		String mdm_deptid = curjpo.getString("whichoffice.MDM_DEPTID");

		// 获取该岗位下人员
		persons = WorkorderUtil.getPersonByPost(whichoffice, param);

		// 如果办事处编号查不到人员，则通过mdmid查询
		if (StringUtil.isStrEmpty(persons)) {
			persons = WorkorderUtil.getPersonByPost(mdm_deptid, param);
		}

		if (StringUtil.isStrEmpty(persons)) {
			throw new MroException("", "当前办事处无" + param + "角色！");
		}

		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();
		return personSet;
	}

}
