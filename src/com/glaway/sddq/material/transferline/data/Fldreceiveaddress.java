package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <调拨单行接收地库房址字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class Fldreceiveaddress extends JpoField {
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
	 * 过滤接收库房地址
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("locaddress");
		String RECEIVESTOREROOM = this.getJpo().getString("RECEIVESTOREROOM");
		String listSql = "";
		listSql = listSql + "location='" + RECEIVESTOREROOM + "'";

		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
