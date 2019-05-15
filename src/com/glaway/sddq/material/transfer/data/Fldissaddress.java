package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <发出库房地址选择>
 * 
 * @author public2795
 * @version [版本号, 2018-8-24]
 * @since [产品/模块版本]
 */
public class Fldissaddress extends JpoField {
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
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "address" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤库房地址
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("locaddress");
		String ISSUESTOREROOM = this.getJpo().getString("ISSUESTOREROOM");
		String listSql = "";
		listSql = listSql + "location='" + ISSUESTOREROOM + "'";

		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
