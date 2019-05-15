package com.glaway.sddq.service.transplan.bean;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造计划-改造车辆分布-分配人员Dialog 表格databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月12日]
 * @since [产品/模块版本]
 */
public class DistPersonBean extends DataBean {
	@Override
	public void initJpoSet() throws MroException {
		super.initJpoSet();
		IJpoSet jposet = getJpoSet();
		String where = "STATUS = '活动' and personid not in"
				+ "(select personid from sys_user where userid in "
				+ "(select userid from sys_groupuser, sys_group where "
				+ "sys_groupuser.groupname = sys_group.groupname and sys_group.groupname "
				+ "in('GWADMIN','MROAUDADMIN','MROSECADMIN','MROSYSADMIN','MOBILEUSER')))";
		// 当前工单
		IJpo wo = this.getDataBean("1536286533933").getJpo();
		IJpoSet personSet = wo.getJpoSet("JXTASKEXECPERSON");
		String selected = WorkorderUtil.filterSelected(personSet, wo.getId(),
				"PERSONNUM");
		if (StringUtil.isStrNotEmpty(selected)) {
			where += " and personid not in (" + selected + ")";
		}

		/**
		 * 过滤显示当前办事处及站点人员
		 */
		IJpo rangeJpo = getDataBean("1520318024916").getJpo();
		String office = rangeJpo.getString("WHICHOFFICE");
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
