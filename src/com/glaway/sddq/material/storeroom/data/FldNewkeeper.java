package com.glaway.sddq.material.storeroom.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 库房变更库管员字段类
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-1]
 * @since [GlawayMro4.0/库房管理]
 */
public class FldNewkeeper extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		// String [] attr = {"RECEIVEBY","KEEPER"};

		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "personid" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤库管员数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		// 在库房管理应用程序中变更库管员时过滤当前库管员
//		if ("STOREROOM".equalsIgnoreCase(getJpo().getJpoSet().getAppName())) {
//			if (getField("KEEPER").getValue().isEmpty()) {
//				this.setListWhere("DEPARTMENT in (select deptnum from sys_dept where parent='01060000')");
//			} else {
//				this.setListWhere("PERSONID!='"
//						+ getField("KEEPER").getValue()
//						+ "' and DEPARTMENT in (select deptnum from sys_dept where parent='01060000')");
//			}
//		}
		//肖林宝2019年1月21日修改，变更库管员可选择所有人员
		if ("STOREROOM".equalsIgnoreCase(getJpo().getJpoSet().getAppName())) {
		if (!getField("KEEPER").getValue().isEmpty()) {
			this.setListWhere("PERSONID!='"+getField("KEEPER").getValue()+"'");
		}
	}
		return super.getList();
	}
}
