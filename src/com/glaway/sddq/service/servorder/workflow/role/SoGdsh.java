package com.glaway.sddq.service.servorder.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 服务工单-工单审核角色
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月19日]
 * @since [产品/模块版本]
 */
public class SoGdsh implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo jpo, String param)
			throws MroException {
		String persons = "";
		// 服务工单类型
		String orderType = jpo.getString("SERVORDERTYPE");
		if (SddqConstant.SO_TYPE_JSZC.equals(orderType)) {
			// 技术支持类服务工单由服务工程师所属部门负责人审核
			String resp = jpo.getString("SERVENGINEER.DEPT.OWNER");
			if (StringUtil.isStrEmpty(resp)) {
				throw new MroException("", "当前服务工程师所属部门负责人为空或者所属部门为空！");
			}
			persons = "'" + resp + "'";
		} else {
			// 非现场技术支持类工单审核
			// 办事处
			String whichoffice = jpo.getString("whichoffice");
			// mdm组织id
			String mdm_deptid = jpo.getString("whichoffice.MDM_DEPTID");

			// 青岛分公司、广州检修基地
			if ("01062400".equals(whichoffice)) {// 青岛

				whichoffice = "01062403";// 检修服务部
				mdm_deptid = "75140109304";
				param = "检修服务部部长";

			} else if ("01062800".equals(whichoffice)) {// 广州检修基地

				param = "生产计划主管";

			} else if ("01062600".equals(whichoffice)
					|| "01062500".equals(whichoffice)) {// 非洲、东南亚办事处取办事处主任审核
				param = "办事处主任";

			} else if ("01061900".equals(whichoffice)) {// 广州办事处

				// 固定服务主管角色为罗建盛
				IJpoSet personSet = MroServer.getMroServer().getSysJpoSet(
						"SYS_PERSON", "personid ='19995003'");
				return personSet;

			} else if ("01062700".equals(whichoffice)) {// 美洲办事处

				IJpoSet personSet = MroServer.getMroServer().getSysJpoSet(
						"SYS_PERSON", "personid ='20035015'");// 张超
				return personSet;

			}

			// 获取该岗位下人员
			persons = WorkorderUtil.getPersonByPost(whichoffice, param);

			// 如果办事处编号查不到人员，则通过mdmid查询
			if (StringUtil.isStrEmpty(persons)) {
				persons = WorkorderUtil.getPersonByPost(mdm_deptid, param);
			}

			if (StringUtil.isStrEmpty(persons)) {
				throw new MroException("", "当前办事处无" + param + "！");
			}

		}

		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				jpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();
		return personSet;
	}

}
