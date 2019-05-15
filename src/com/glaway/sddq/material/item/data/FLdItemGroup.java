package com.glaway.sddq.material.item.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <物料管理物料组字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FLdItemGroup extends JpoField {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1739683938386907485L;

	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "itemgroupnum" };
		setLookupMap(thisAttrs, srcAttrs);

	}

	/**
	 * 过滤物料组数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("itemgroup");
		String listSql = "";
		listSql = " 1=1 ";
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
