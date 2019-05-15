package com.glaway.sddq.service.transorder.bean;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 选择人员对话框table Bean
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月26日]
 * @since [产品/模块版本]
 */
public class SelectPersonsTableBean extends DataBean {

	@Override
	public void initJpoSet() throws MroException {

		super.initJpoSet();
		IJpoSet jposet = getJpoSet();
		String where = "STATUS = '活动' and personid not in"
				+ "(select personid from sys_user where userid in "
				+ "(select userid from sys_groupuser, sys_group where "
				+ "sys_groupuser.groupname = sys_group.groupname and sys_group.groupname "
				+ "in('GWADMIN','MROAUDADMIN','MROSECADMIN','MROSYSADMIN','MOBILEUSER')))";

		/**
		 * 过滤显示当前办事处及站点人员
		 */
		String office = getAppBean().getJpo().getString("WHICHOFFICE");
		if (StringUtil.isStrNotEmpty(office)) {
			where += " and  department='"
					+ office
					+ "' or department in (select deptnum from sys_dept where parent='"
					+ office + "')";
		}

		jposet.setQueryWhere(where);
		jposet.reset();
	}

}
