package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 工单仓位字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-19]
 * @since [产品/模块版本]
 */
public class FldBinnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 5469002096370653230L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "binnum" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	@Override
	public IJpoSet getList() throws MroException {
		setListObject("locbin");
		String listSql = "";
		listSql = " location='" + this.getJpo().getString("location") + "' ";
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}
		return super.getList();
	}

}
