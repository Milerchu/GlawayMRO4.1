package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <服务工程师字段类>
 * 
 * @author hzhu
 * @version [MRO4.0, 2018-5-5]
 * @since [MRO4.0/模块版本]
 */
public class FldServEngineer extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -725175736193015327L;

	@Override
	public void init() throws MroException {

		String[] srcAttrs = { "PERSONID" };
		String[] tarAttrs = { "SERVENGINEER" };
		this.setLookupMap(tarAttrs, srcAttrs);

		super.init();
	}

	@Override
	public IJpoSet getList() throws MroException {
		IJpoSet returnSet = super.getList();

		String type = getJpo().getString("type");
		if ("故障".equals(type) || "服务".equals(type)) {// 故障/服务工单

			// 根据办事处过滤现场处理人
			// String office = getJpo().getString("WHICHOFFICE");
			String where = "";
			where = " department in(select deptnum from sys_dept where parent in('01060000','01062400')) ";// 售后服务中心

			returnSet.setUserWhere(where);
			returnSet.reset();
		}

		return returnSet;
	}

	@Override
	public void action() throws MroException {

		String appName = getJpo().getAppName();

		// if ("TRANSORDER".equalsIgnoreCase(appName)) {
		// if (isValueChanged()) {
		// // 将所属站点置空
		// getJpo().setValueNull("WHICHSTATION");
		// }
		// }

		// 设置所属办事处
		// if ("FAILUREORD".equalsIgnoreCase(getJpo().getAppName())) {
		// getJpo().setFieldFlag("WHICHOFFICE", GWConstant.S_READONLY, false);
		// getJpo().setValue("WHICHOFFICE",
		// getJpo().getString("SERVENGINEER.DEPARTMENT"));
		// }

		/*
		 * IJpo jpo = this.getJpo(); if ("重新派工".equals(jpo.getString("status")))
		 * {
		 * 
		 * String oldEng = jpo.getString("SERVENGINEER"); if
		 * (oldEng.equalsIgnoreCase(this.getMroType().asString())) { throw new
		 * MroException("", "所选工程师和原工程师一致，请重新选择！"); } }
		 */
		super.action();
	}
}
