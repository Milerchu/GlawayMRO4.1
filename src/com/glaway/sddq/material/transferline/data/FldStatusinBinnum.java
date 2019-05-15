package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <装箱单接收入库仓位字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldStatusinBinnum extends JpoField {
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
	 * 过滤仓位信息
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("locbin");
		IJpo parent = this.getJpo().getParent();
		String RECEIVESTOREROOM = parent.getString("RECEIVESTOREROOM");
		String listSql = "";

		listSql = "location='" + RECEIVESTOREROOM + "'";

		setListWhere(listSql);
		return super.getList();
	}
}
