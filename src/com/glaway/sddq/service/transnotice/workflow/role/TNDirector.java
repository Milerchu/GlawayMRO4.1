package com.glaway.sddq.service.transnotice.workflow.role;

import java.util.ArrayList;
import java.util.List;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造通知单-办事处主任角色类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月11日]
 * @since [产品/模块版本]
 */
public class TNDirector implements RoleCustomClass {

	@Override
	public IJpoSet executeCustomRole(IJpo curjpo, String arg1)
			throws MroException {

		List<String> directors = new ArrayList<String>();
		// 改造范围子表
		IJpoSet rangeSet = curjpo.getJpoSet("TRANSRANGE");
		if (rangeSet != null && rangeSet.count() > 0) {
			for (int index = 0; index < rangeSet.count(); index++) {
				IJpo range = rangeSet.getJpo(index);
				String deptnum = range.getString("IMPDEPT");
				directors.addAll(WorkorderUtil
						.getOfficeDirectorByStation(deptnum));// 添加所有产品范围的办事处主任
			}
		}

		String persons = "";
		for (String director : directors) {
			persons += "'" + director + "',";
		}
		if (StringUtil.isStrNotEmpty(persons)) {
			persons = persons.substring(0, persons.length() - 1);
		}

		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				curjpo.getUserServer());
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();

		return personSet;
	}

}
