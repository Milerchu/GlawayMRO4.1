package com.glaway.sddq.material.mpr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <改造转服务领料单改造计划编号字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldTransplannum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "transplannum" };
		setLookupMap(thisAttrs, srcAttrs);

	}

	/**
	 * 获取改造计划信息
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("transplan");
		String listSql = "";
		listSql = "STATUS not in ('草稿','关闭') and plantype!='验证'";
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}
		return super.getList();
	}
}
