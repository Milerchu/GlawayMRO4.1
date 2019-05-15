package com.glaway.sddq.base.dept.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.StringUtil;

public class StationDataBean extends DataBean {

	@Override
	public void initialize() throws MroException {
		super.initialize();

		IJpoSet jposet = getJpoSet();

		DataBean treeDataBean = this.page.getAppBean().getDataBean("depttree");
		IJpo parentJpo=null;
		if (treeDataBean != null ) {
			parentJpo = treeDataBean.getJpo();
		}
		String where = "";
		if (parentJpo != null) {
			String hierarchy = parentJpo.getString("hierarchy");// 5级组织
			String parentnum = parentJpo.getString("parent");// 父级
			String deptnum = parentJpo.getString("DEPTNUM");// 部门编号
			if (StringUtil.isEqualIgnoreCase(hierarchy, "5级组织")
					|| StringUtil.isEqualIgnoreCase(hierarchy, "6级组织")
					|| StringUtil.isEqualIgnoreCase(hierarchy, "7级组织")) {
				// where = "DEPARTMENT ='" + deptnum + "' and STATUS = '活动'";
				where = "DEPARTMENT ='" + parentnum + "' and STATUS = '活动'";
			} else {
				where = " 1=2 ";
			}
		} else {
			where = " 1=2 ";
		}

		jposet.setQueryWhere(where);
		jposet.reset();
	}

	@Override
	public int dialogok() throws IOException, MroException {
		/*
		 * 2018-10-18 杨毅修改，一对多关系 ，数据存储到对应关系表 
		 * DataBean stationDataBean =
		 * this.page.getAppBean().getDataBean( "1534332820116"); DataBean
		 * treeDataBean = this.page.getAppBean().getDataBean("depttree"); if
		 * (treeDataBean != null && treeDataBean.getJpo() != null) { String
		 * deptnum = treeDataBean.getJpo().getString("DEPTNUM"); // 获取当前选择的人员
		 * List<IJpo> list = getJpoSet().getSelections(); if (!list.isEmpty()) {
		 * for (int i = 0; i < list.size(); i++) { IJpo personjpo = list.get(i);
		 * personjpo.setValue("STATION", deptnum); } } getJpoSet().save();
		 * stationDataBean.resetAndReload(); }
		 */
		
		DataBean stationDataBean = this.page.getAppBean().getDataBean(
				"1534332820116");
		DataBean treeDataBean = this.page.getAppBean().getDataBean("depttree");
		if (treeDataBean != null && treeDataBean.getJpo() != null) {
			String deptnum = treeDataBean.getJpo().getString("DEPTNUM");
			// 获取当前选择的人员
			List<IJpo> list = getJpoSet().getSelections();
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					IJpo personjpo = list.get(i);
					IJpo jpo = stationDataBean.getJpoSet().addJpo();
//					personjpo.setValue("STATION", deptnum);
					jpo.setValue("deptnum", deptnum);
					jpo.setValue("personid", personjpo.getString("PERSONID"));
				}
			}
//			stationDataBean.resetAndReload();
			stationDataBean.reloadPage();
		}
		return super.dialogok();
	}

}
