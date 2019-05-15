package com.glaway.sddq.material.materborrow.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <配件借用借出库房字段绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-23]
 * @since  [产品/模块版本]
 */
public class FldBorrowStoreroom extends JpoField {

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
		String[] srcAttrs = { "LOCATION"};
		setLookupMap(thisAttrs, srcAttrs);
	}
	/**
	 * 过滤库房数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("LOCATIONS");
		String listSql = "ERPLOC='1020' and STOREROOMLEVEL='中心库' and LOCATIONTYPE='常规' and STOREROOMGRADE='二级'";
		
		if (!listSql.equals("")) {
			listSql=listSql+"and status='正常'";
			setListWhere(listSql);
		}

		return super.getList();
	}
	/**
	 * 触发自动赋值给借入库房,默认为中心库-借用暂存库
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		this.getJpo().setValue("RETURNSTOREROOM", "JY1001",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
	}
	
}
