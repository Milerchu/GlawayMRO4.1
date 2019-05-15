package com.glaway.sddq.service.failureord.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 专职库管员定制类
 * 
 * @author zhuhao
 * @version [版本号, 2018年11月12日]
 * @since [产品/模块版本]
 */
public class FullTimeKeeper implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo jpo, String arg1) throws MroException {

		String persons = "";
		String whichoffice = jpo.getString("whichoffice");// 所属办事处

		String fullTimeKeeperGrp = "";// 专职库管员人员组

		if ("01061500".equals(whichoffice)) {// 兰州办事处

			fullTimeKeeperGrp = "LZZZKGY";

		} else if ("01061800".equals(whichoffice)) {// 株洲办事处

			fullTimeKeeperGrp = "ZZZZKGY";

		} else if ("01061900".equals(whichoffice)) {// 广州办事处

			fullTimeKeeperGrp = "GZZZKGY";

		} else if ("01062100".equals(whichoffice)) {// 沈阳办事处

			fullTimeKeeperGrp = "SYZZKGY";

		} else if ("01062400".equals(whichoffice)) {// 青岛检修分公司

			fullTimeKeeperGrp = "QDZZKGY";

		} else if ("01062700".equals(whichoffice)) {// 美洲办事处

			fullTimeKeeperGrp = "MZZZKGY";

		} else if ("01062500".equals(whichoffice)) {// 东南亚办事处

			fullTimeKeeperGrp = "DNYZZKGY";

		} else if ("01061600".equals(whichoffice)) {// 西安办事处

			fullTimeKeeperGrp = "XAZZKGY";

		} else if ("01061700".equals(whichoffice)) {// 重庆办事处

			fullTimeKeeperGrp = "CQZZKGY";

		} else if ("01062000".equals(whichoffice)) {// 上海办事处

			fullTimeKeeperGrp = "SHZZKGY";

		} else if ("01062300".equals(whichoffice)) {// 武汉办事处

			fullTimeKeeperGrp = "WHZZKGY";

		} else if ("01062600".equals(whichoffice)) {// 非洲办事处

			fullTimeKeeperGrp = "FZZZKGY";

		} else if ("01061400".equals(whichoffice)) {// 北京办事处

			fullTimeKeeperGrp = "BJZZKGY";

		}

		persons = WorkorderUtil.getPersonsFromPersonGroup(fullTimeKeeperGrp);// 人员组人员

		// 返回人员列表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				jpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();
		return personSet;

	}

}
