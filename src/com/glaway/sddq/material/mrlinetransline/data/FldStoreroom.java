package com.glaway.sddq.material.mrlinetransline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <计划经理处置表物料编码字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldStoreroom extends JpoField {

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
		String[] srcAttrs = { "LOCATION" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 获取库房数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("locations");
		String receiveerploc = this.getJpo().getJpoSet("receivestoreroom")
				.getJpo().getString("erploc");
		String listSql = "";
		listSql = listSql + "ERPLOC='" + receiveerploc
				+ "' AND STOREROOMGRADE='二级'";
		if (!listSql.equals("")) {
			listSql=listSql+"and status='正常'";
			setListWhere(listSql);
		}
		IJpoSet j = super.getList();
		return j;
	}

	/**
	 * 触发赋值库管员
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String keeper = this.getJpo().getJpoSet("STOREROOM").getJpo(0)
				.getString("keeper");
		this.getJpo().setValue("keeper", keeper,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
	}

}
