package com.glaway.sddq.service.transplan.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造计划/验证计划-办事处主任 定制类
 * 
 * @author zhuhao
 * @version [版本号, 2018年6月24日]
 * @since [产品/模块版本]
 */
public class OfficeResp implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String arg1)
			throws MroException {
		String persons = "";
		if ("验证".equals(curjpo.getString("plantype"))) {// 验证计划
			IJpoSet rangeSet = curjpo.getJpoSet("VALIPRORANGE");// 验证产品范围
			if (rangeSet != null && rangeSet.count() > 0) {
				for (int index = 0; index < rangeSet.count(); index++) {
					IJpo range = rangeSet.getJpo(index);
					persons += "'"
							+ WorkorderUtil.getOfficeDirectorByOfficenum(range
									.getString("office")) + "',";
				}
				if (StringUtil.isStrNotEmpty(persons)) {
					persons = persons.substring(0, persons.length() - 1);
				}
			}
		} else {// 改造
				// 改造分布
			IJpoSet tdSet = curjpo.getJpoSet("TRANSDIST");

			if (!tdSet.isEmpty()) {
				for (int index = 0; index < tdSet.count(); index++) {
					IJpo td = tdSet.getJpo(index);
					persons += "'" + td.getString("RESPONSIBLE") + "',";
				}
				persons = persons.substring(0, persons.length() - 1);
			}
		}

		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();

		return personSet;
	}

}
