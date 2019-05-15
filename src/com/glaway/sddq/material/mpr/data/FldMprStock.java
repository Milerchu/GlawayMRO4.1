package com.glaway.sddq.material.mpr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 交车工单库房字段类
 * 
 * @author zzx
 * @version [版本号, 2018-7-25]
 * @since [产品/模块版本]
 */
public class FldMprStock extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -4635929287683405950L;

	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "LOCATION" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤库房数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("LOCATIONS");
		String listSql = "";
		listSql = listSql + "erploc='" + ItemUtil.ERPLOC_1030
				+ "' and ISSAPSTORE='0'";
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
