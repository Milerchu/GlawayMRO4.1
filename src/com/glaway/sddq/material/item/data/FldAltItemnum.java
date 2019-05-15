/*
 * 文 件 名:  FldAltItemnum.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yyang
 * 修改时间:  2016-4-25
 */
package com.glaway.sddq.material.item.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * <可替换物料组替换物料绑定类>
 * 
 * @author yyang
 * @version [版本号, 2016-4-25]
 * @since [产品/模块版本]
 */
public class FldAltItemnum extends JpoField {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1739683938386907485L;

	/**
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "ITEMNUM" };
		setLookupMap(thisAttrs, srcAttrs);

	}

	/**
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		super.action();
		IJpo parent = this.getJpo().getParent();
		if (parent != null) {
			setListWhere(" itemnum <> '"
					+ StringUtil.getSafeSqlStr(parent.getString("itemnum"))
					+ "'  ");
			this.getJpo().setValue("ITEMNUM", parent.getString("itemnum"),
					GWConstant.P_NOVALIDATION);
		}

	}

	/**
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("SYS_ITEM");
		IJpo parent = this.getJpo().getParent();
		String listSql = "";
		if (parent != null) {
			listSql = " itemnum <> '" + parent.getString("itemnum") + "'  ";
		}
		IJpoSet thisSet = this.getJpo().getJpoSet();
		String itemnums = null;
		if (!thisSet.isEmpty()) {
			for (int i = 0; i < thisSet.count(); i++) {
				String itemnum = thisSet.getJpo(i).getString(
						this.getFieldName());
				if (!"".equals(itemnum)) {
					if (itemnums == null)
						itemnums = "'" + StringUtil.getSafeSqlStr(itemnum)
								+ "'";
					else
						itemnums = itemnums + ",'"
								+ StringUtil.getSafeSqlStr(itemnum) + "'";
				}
			}
		}
		if (itemnums != null) {
			listSql = listSql + "and  itemnum not in (" + itemnums + ")";
		}

		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
