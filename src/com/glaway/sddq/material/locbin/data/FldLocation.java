/*
 * 文 件 名:  FldLocationsItemNum.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yyang
 * 修改时间:  2016-5-13
 */
package com.glaway.sddq.material.locbin.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <仓位表库房编码字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldLocation extends JpoField {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6714808953825812433L;

	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "location" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 获取可选择列表数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("locations");
		IJpo parent = this.getJpo().getParent();
		String listSql = "";
		if (parent != null) {
			listSql = " location <> '" + parent.getString("location")
					+ "' and STOREROOMPARENT='" + parent.getString("location")
					+ "' ";
		}
		IJpoSet thisSet = this.getJpo().getJpoSet();
		String locations = null;
		if (!thisSet.isEmpty()) {
			for (int i = 0; i < thisSet.count(); i++) {
				String location = thisSet.getJpo(i).getString(
						this.getFieldName());
				if (!"".equals(location)) {
					if (locations == null)
						locations = "'" + StringUtil.getSafeSqlStr(location)
								+ "'";
					else
						locations = locations + ",'"
								+ StringUtil.getSafeSqlStr(location) + "'";
				}
			}
		}
		if (locations != null) {
			listSql = listSql + "and  location not in (" + locations + ")";
		}

		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
