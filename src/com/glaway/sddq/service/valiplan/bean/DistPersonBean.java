package com.glaway.sddq.service.valiplan.bean;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 验证计划-验证车辆分布-分配人员Dialog 表格databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月9日]
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
		IJpo wo = this.getDataBean("1533607363636").getJpo();
		IJpoSet personSet = wo.getJpoSet("JXTASKEXECPERSON");
		String selected = WorkorderUtil.filterSelected(personSet, wo.getId(),
				"PERSONNUM");
		if (StringUtil.isStrNotEmpty(selected)) {
			where += " and personid not in (" + selected + ")";
		}

		/**
		 * 过滤显示当前办事处及站点人员
		 */
		IJpo rangeJpo = getDataBean("main_tab2").getJpo();
		String office = rangeJpo.getString("office");
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
