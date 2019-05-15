package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <出库仓位字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldoutBinnum extends JpoField {
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
	 * 过滤仓位信息
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("locbin");
		String ISSUESTOREROOM = this.getJpo().getString("ISSUESTOREROOM");
		String listSql = "";

		listSql = "location='" + ISSUESTOREROOM + "'";

		setListWhere(listSql);
		return super.getList();
	}
}
