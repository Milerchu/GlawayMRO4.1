package com.glaway.sddq.material.worline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

public class FLdLotnum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName()};
		String[] srcAttrs = { "lotnum"};
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤批次信息
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("invblance");
		IJpo parent = this.getJpo().getParent();
		String location = parent.getString("location");
		String itemnum = this.getJpo().getString("itemnum");
		String listSql = "";
		listSql = "itemnum='" + itemnum + "' and storeroom='" + location
				+ "'";
		setListWhere(listSql);
		return super.getList();
	}
}
