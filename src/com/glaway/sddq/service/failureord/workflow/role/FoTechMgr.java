package com.glaway.sddq.service.failureord.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 故障工单-办事处技术主管FOTECHMGR角色定制类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月1日]
 * @since [产品/模块版本]
 */
public class FoTechMgr implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String param)
			throws MroException {
		String persons = "";
		// 办事处
		String whichoffice = curjpo.getString("whichoffice");
		// mdm组织id
		String mdm_deptid = curjpo.getString("whichoffice.MDM_DEPTID");

		// 青岛分公司、广州检修基地
		if ("01062400".equals(whichoffice)) {// 青岛

			whichoffice = "01062403";// 检修服务部
			mdm_deptid = "75140109304";
			param = "检修服务部部长";

		} else if ("01062800".equals(whichoffice)) {// 广州检修基地

			param = "技术质量主管";

		} else if ("01062600".equals(whichoffice)
				|| "01062500".equals(whichoffice)) {// 非洲、东南亚办事处取办事处主任审核

			param = "办事处主任";

		} else if ("01062700".equals(whichoffice)) {// 美洲办事处指定角色

			IJpoSet personSet = MroServer.getMroServer().getSysJpoSet(
					"SYS_PERSON", "personid ='20035015'");// 张超
			return personSet;

		} else if ("01062000".equals(whichoffice)) {// 上海办事处
			IJpoSet personSet = MroServer.getMroServer().getSysJpoSet(
					"SYS_PERSON", "personid ='20148170'");// 夏政
			return personSet;
		}

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
