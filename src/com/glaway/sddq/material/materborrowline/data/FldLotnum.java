package com.glaway.sddq.material.materborrowline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * <配件借用行批次号字段绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-24]
 * @since  [产品/模块版本]
 */
public class FldLotnum extends JpoField {
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
		String[] thisAttrs = { this.getFieldName()};
		String[] srcAttrs = { "lotnum"};
		setLookupMap(thisAttrs, srcAttrs);
	}
	/**
	 * 过滤批次号数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("invblance");
		String BORROWSTOREROOM=this.getJpo().getParent().getString("BORROWSTOREROOM");//借出库房
		String itemnum=this.getJpo().getString("itemnum");//借出的物料编码
		String listSql = "itemnum='"+itemnum+"' and storeroom='"+BORROWSTOREROOM+"'";
		
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}
		return super.getList();
	}
}
