package com.glaway.sddq.material.jxmpr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 检修领料单-办事处主任定制类
 * 
 * @author zzx
 * @version [版本号, 2018年8月17日]
 * @since [产品/模块版本]
 */
public class JxMprBansczr implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String param)
			throws MroException {
		String persons = "";
		// 获取部门编号
		String deptnum = curjpo.getString("APPLICANTBY.DEPARTMENT");
		// mdm组织id
		String mdm_deptid = curjpo.getString("APPLICANTBY.DEPT.MDM_DEPTID");

		// 青岛分公司、广州检修基地
		if ("01062400".equals(deptnum)
			|| "01062403".equals(deptnum)) {// 青岛

			deptnum = "01062403";// 检修服务部
			mdm_deptid = "75140109304";
			param = "检修服务部部长";

		} else if ("01062800".equals(deptnum)) {// 广州检修基地

			param = "生产计划主管";

		} else if ("01062700".equals(deptnum)
				|| "01062600".equals(deptnum)
				|| "01062500".equals(deptnum)) {// 美洲、非洲、东南亚办事处取办事处主任审核

			param = "办事处主任";

		} else if ("01061900".equals(deptnum)) {// 广州办事处

			// 固定服务主管角色为罗建盛
			IJpoSet personSet = MroServer.getMroServer().getSysJpoSet(
					"SYS_PERSON", "personid ='19995003'");
			return personSet;

		}else if ("01062700".equals(deptnum)) {// 美洲办事处

			IJpoSet personSet = MroServer.getMroServer().getSysJpoSet(
					"SYS_PERSON", "personid ='20035015'");// 张超
			return personSet;

		}
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
