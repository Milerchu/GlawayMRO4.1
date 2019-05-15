package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <批次号字段绑定类> 批次号选择
 * 
 * @author 20167088
 * @version [版本号, 2018-8-3]
 * @since [产品/模块版本]
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
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "lotnum" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤批次号信息
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("invblance");
		IJpo parent = this.getJpo().getParent();
		String type = parent.getString("type");
		String ISSUESTOREROOM = "";
		String listSql = "";
		if (type.equalsIgnoreCase("GZZXD")) {
			ISSUESTOREROOM = parent.getString("ISSUESTOREROOM");
			String itemnum = this.getJpo().getString("itemnum");

			listSql = "itemnum='" + itemnum + "' and storeroom='"
					+ ISSUESTOREROOM + "'";
		} else {
			ISSUESTOREROOM = this.getJpo().getString("ISSUESTOREROOM");
			String itemnum = this.getJpo().getString("itemnum");
			listSql = "itemnum='" + itemnum + "' and storeroom='"
					+ ISSUESTOREROOM + "'";
		}

		setListWhere(listSql);
		return super.getList();
	}

	@Override
	protected void validateList() throws MroException {
		// TODO Auto-generated method stub
	}

}
