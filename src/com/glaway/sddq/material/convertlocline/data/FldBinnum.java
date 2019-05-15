package com.glaway.sddq.material.convertlocline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <调拨转库单接收窗口仓位字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class FldBinnum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "binnum" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 仓位信息过滤
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("locbin");
		String location = this.getJpo().getParent().getString("location");
		String listSql = "";
		listSql = listSql + "location='" + location + "'";
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
