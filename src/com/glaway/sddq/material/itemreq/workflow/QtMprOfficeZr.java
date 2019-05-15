package com.glaway.sddq.material.itemreq.workflow;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * QT领料单-办事处主任定制类
 * 
 * @author zzx
 * @version [版本号, 2019年3月14日]
 * @since [产品/模块版本]
 */
public class QtMprOfficeZr implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String param)
			throws MroException {
		String persons = "";
		// 获取部门编号
		String deptnum = curjpo.getString("APPLICANTBY.DEPARTMENT");
		// mdm组织id
		String mdm_deptid = curjpo.getString("APPLICANTBY.DEPT.MDM_DEPTID");

		// 获取该岗位下人员
		persons = WorkorderUtil.getPersonByPost(deptnum, param);

		// 如果办事处编号查不到人员，则通过mdmid查询
		if (StringUtil.isStrEmpty(persons)) {
			persons = WorkorderUtil.getPersonByPost(mdm_deptid, param);
		}

		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();
		return personSet;

	}

}
