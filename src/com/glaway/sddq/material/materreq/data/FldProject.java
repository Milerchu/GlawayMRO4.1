package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <配件申请项目选择字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-13]
 * @since [产品/模块版本]
 */
public class FldProject extends JpoField {
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
		String[] srcAttrs = { "PROJECTNUM" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤项目
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("PROJECTINFO");
		String listSql = "";
		listSql = listSql + "STATUS='已立项'";
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
