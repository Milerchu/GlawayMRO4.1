package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <装箱单选择改造工作领号>
 * 
 * @author public2795
 * @version [版本号, 2018-9-4]
 * @since [产品/模块版本]
 */
public class FldTransworkordernum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "TRANSWORKORDERNUM" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤工作令号信息
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {

		setListObject("TRANSNOTICE");
		String listSql = "";
		listSql = "STATUS='已审核'";
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}
		return super.getList();
	}
}
