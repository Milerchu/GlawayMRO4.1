package com.glaway.sddq.service.valirequest.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 验证产品范围表中产品物料编码 字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-19]
 * @since [产品/模块版本]
 */
public class FldProductCode extends JpoField {

	private static final long serialVersionUID = -4830027598434612576L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String[] thisAttrs = { "PRODUCTCODE", "PRODUCTDESC", "SPECIFICATION" };
		String[] srcAttrs = { "ITEMNUM", "DESCRIPTION", "SPECIFICATION" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	@Override
	public IJpoSet getList() throws MroException {

		if (getJpo().getParent() != null) {
			// 验证内容 set
			IJpoSet valiContentSet = this.getJpo().getParent()
					.getJpoSet("VALICONTENT");
			if (!valiContentSet.isEmpty()) {
				String itemnums = "";
				for (int index = 0; index < valiContentSet.count(); index++) {
					IJpo valicontent = valiContentSet.getJpo(index);
					itemnums += "'" + valicontent.getString("PREVIOUSPRDNUM")
							+ "',";
				}
				// 去除末尾逗号
				itemnums = itemnums.substring(0, itemnums.length() - 1);
				// 根据验证内容过滤物料编码
				setListWhere("itemnum in (" + itemnums + ")");
			}

		}
		return super.getList();
	}
}
