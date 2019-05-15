package com.glaway.sddq.material.storeroom.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 库房选择站点字段类
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-1]
 * @since [GlawayMro4.0/库房管理]
 */
public class FldLocsite extends JpoField {

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
		String[] thisAttrs = { this.getFieldName(), "ADDRESS" };
		String[] srcAttrs = { "DEPTNUM", "ADDR" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤站点数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("SYS_DEPT");
		String listSql = "";
		listSql = listSql + "HIERARCHY in ('5级组织','6级组织')";
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
