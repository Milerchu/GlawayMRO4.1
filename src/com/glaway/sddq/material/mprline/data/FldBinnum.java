package com.glaway.sddq.material.mprline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <领料单仓位字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-4]
 * @since [产品/模块版本]
 */
public class FldBinnum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "binnum" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤仓位数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("locbin");
		IJpo parent = this.getJpo().getParent();
		String MPRSTOREROOM = parent.getString("MPRSTOREROOM");
		String listSql = "";

		listSql = "location='" + MPRSTOREROOM + "'";

		setListWhere(listSql);
		return super.getList();
	}
}
