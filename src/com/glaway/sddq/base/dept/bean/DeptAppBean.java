package com.glaway.sddq.base.dept.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.control.TreeNode;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.base.dept.data.Dept;

/**
 * 
 * <功能描述> 部门管理的主应用程序
 * 
 * @author jxiang
 * @version [MRO4.0, 2016-4-19]
 * @since [MRO4.0/模块版本]
 */
public class DeptAppBean extends AppBean {

	@Override
	public int INSERT() throws IOException, MroException {
		super.INSERT();
		AppBean db = getAppBean();
		IJpo jpo = db.getJpo();
		jpo.setValue("TYPE", "0级部门");
		jpo.setValue("DEPTLEVEL", "0");
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	@Override
	public int delAndSaveRow() throws MroException, IOException {
		DeptTreeBean tree = (DeptTreeBean) this.getDataBean("depttree");
		TreeNode treeNode = tree.getCurrNode();
		IJpo thisJpo = treeNode.getJpo();

		if (thisJpo instanceof Dept) {
			thisJpo.delete();
			getAppBean().save();
			showOperInfo("system", "deleterecord", null);
			// tree.reloadNode();
			gotoTab(0);
		}
		return GWConstant.ACCESS_SAMEMETHOD;
	}

	/**
	 * 
	 * <功能描述> 判断当前部门是否能删除
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void canDelete(String deptNum) throws MroException {
		String subSql = "parent='" + StringUtil.getSafeSqlStr(deptNum)
				+ "' and siteid='"
				+ StringUtil.getSafeSqlStr(getString("siteid")) + "'";
		IJpoSet children = this.getJpo().getJpoSet("$CHILDREN", "SYS_DEPT",
				subSql);
		if (!children.isEmpty()) {
			String[] p = { "该部门存在子级部门，不可删除。" };
			throw new MroException("dept", "validate", p);
		}

		// 添加用户关联校验
		if (!this
				.getJpo()
				.getJpoSet(
						"$sys_person",
						"sys_person",
						"department='" + StringUtil.getSafeSqlStr(deptNum)
								+ "'").isEmpty()) {
			String[] p = { "该部门存在用户，不可删除。" };
			throw new MroException("dept", "validate", p);
		}

		// 添加工种关联校验
		if (!this
				.getJpo()
				.getJpoSet("$SYS_DEPTCRAFT", "SYS_DEPTCRAFT",
						"deptnum='" + StringUtil.getSafeSqlStr(deptNum) + "'")
				.isEmpty()) {
			String[] p = { "该部门存在工种，不可删除。" };
			throw new MroException("dept", "validate", p);
		}

	}

}
