package com.glaway.sddq.service.prjteammember.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.jpo.type.MroType;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 项目信息-选择项目人员databean
 * 
 * @author ygao
 * @version [版本号, 2017-10-19]
 * @since [产品/模块版本]
 */
public class FldPersonId extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();
		MroType value = getInputMroType();
		IJpo prjTeammemberjpo = getJpo();
		IJpoSet personset = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				MroServer.getMroServer().getSystemUserServer());
		personset.setQueryWhere("personid = '" + value.asString() + "'");
		personset.reset();
		if (!personset.isEmpty()) {
			IJpo person = personset.getJpo();
			prjTeammemberjpo.setValue("DISPLAYNAME",
					person.getString("DISPLAYNAME"));
			// 设置人员部门编号
			prjTeammemberjpo
					.setValue("DEPTNUM", person.getString("DEPARTMENT"));
		}

	}

}
