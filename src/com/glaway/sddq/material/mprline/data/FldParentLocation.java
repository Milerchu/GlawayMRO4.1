package com.glaway.sddq.material.mprline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;
/**
 * 
 * <入库单借出库房字段绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-3-6]
 * @since  [产品/模块版本]
 */
public class FldParentLocation extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -4635929287683405950L;

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
	 * 过滤库房数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		String APPLICANTBY=this.getJpo().getParent().getString("APPLICANTBY");
		setListObject("LOCATIONS");
		String listSql = "";
		listSql = listSql
				+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
				+ ItemUtil.ERPLOC_1020 + "') and LOCATIONTYPE='"+ItemUtil.LOCATIONTYPE_CG+"' and locsite in (select deptnum from PERSONDEPTMAP where personid='"+APPLICANTBY+"')";
		if (!listSql.equals("")) {
			listSql=listSql+"and status='正常'";
			setListWhere(listSql);
		}

		return super.getList();
	}

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		this.getJpo().setValue("PARENTITEMNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("PARENTSQN", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("PARENTSQNASSETNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("ITEMNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("SQN", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("PARENTASSETNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("LOTNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("QTY", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
	}
	
}
