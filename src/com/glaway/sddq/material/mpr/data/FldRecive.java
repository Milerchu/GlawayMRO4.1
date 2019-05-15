package com.glaway.sddq.material.mpr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * <领料单表接收人字段绑定类>
 * 
 * @author 20167088
 * @version [版本号, 2018-8-5]
 * @since [产品/模块版本]
 */
public class FldRecive extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取接收人过滤数据并映射赋值
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("sys_person");
		String listSql = "1=1";

		if (!listSql.equals("")) {
			setListWhere(listSql);
			String[] thisAttrs = { this.getFieldName() };
			String[] srcAttrs = { "PERSONID" };
			setLookupMap(thisAttrs, srcAttrs);

		}

		return super.getList();
	}

}
