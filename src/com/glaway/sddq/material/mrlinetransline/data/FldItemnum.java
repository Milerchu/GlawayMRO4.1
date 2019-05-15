package com.glaway.sddq.material.mrlinetransline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <配件申请办事处主任处置物料料字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-28]
 * @since [产品/模块版本]
 */
public class FldItemnum extends JpoField {

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
		String[] srcAttrs = { "itemnum" };
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
		String listSql = "";
		String mrnum = this.getJpo().getParent().getString("mrnum");
		setListObject("sys_item");
		listSql = "itemnum in (select itemnum from mrline where mrnum='"
				+ mrnum + "')";

		setListWhere(listSql);

		return super.getList();
	}

	/**
	 * 触发赋值locationqty
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String itemnum = this.getValue();
		IJpoSet mrlineset = this.getJpo().getParent().getJpoSet("mrline");
		mrlineset.setQueryWhere("itemnum='" + itemnum + "'");
		if (!mrlineset.isEmpty()) {
			double locationqty = mrlineset.getJpo(0).getDouble("locationqty");
			this.getJpo().setValue("locationqty", locationqty,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}

}
