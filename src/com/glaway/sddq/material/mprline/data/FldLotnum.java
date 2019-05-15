package com.glaway.sddq.material.mprline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <领料单批次号字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-2]
 * @since [产品/模块版本]
 */
public class FldLotnum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName(), "curbal" };
		String[] srcAttrs = { "lotnum", "kyqty" };
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
		String MPRSTOREROOM = parent.getString("MPRSTOREROOM");
		String itemnum = this.getJpo().getString("itemnum");
		String listSql = "";
		listSql = "itemnum='" + itemnum + "' and storeroom='" + MPRSTOREROOM
				+ "'";
		setListWhere(listSql);
		return super.getList();
	}

	/**
	 * 批次号无校验
	 * 
	 * @throws MroException
	 */
	@Override
	protected void validateList() throws MroException {// 序列号，无校验 ---- 关
		// TODO Auto-generated method stub
	}
}
