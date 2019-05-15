package com.glaway.sddq.material.mrlinetransfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <计划经理协调-物料编码字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-28]
 * @since [产品/模块版本]
 */
public class FLdItemnum extends JpoField {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6714808953825812433L;

	/**
	 * 过滤物料数据并映射赋值
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "itemnum" };
		setLookupMap(thisAttrs, srcAttrs);
		String listSql = "";
		setListObject("sys_item");
		listSql = "1=1";
		setListWhere(listSql);

		return super.getList();
	}

	/**
	 * 触发赋值项目，车型字段
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String PROJECT = this.getJpo().getParent().getString("PROJECT");
		String MODEL = this.getJpo().getParent().getString("MODEL");
		this.getJpo().setValue("PROJECT", PROJECT,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("MODEL", MODEL,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
	}
}
