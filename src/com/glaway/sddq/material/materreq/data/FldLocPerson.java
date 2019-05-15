package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <配件申请配件专员字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldLocPerson extends JpoField {
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
		String[] srcAttrs = { "personid" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤配件专员数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("sys_person");
		String listSql = "";
		listSql = listSql
				+ "JOBCODE in (select POSTnum from POST where POSTNAME like '%配件专员%')";

		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
