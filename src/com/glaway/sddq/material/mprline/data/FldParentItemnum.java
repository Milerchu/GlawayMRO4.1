package com.glaway.sddq.material.mprline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;
/**
 * 
 * <入库单主体物料编码绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-3-6]
 * @since  [产品/模块版本]
 */
public class FldParentItemnum extends JpoField {

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
		String[] srcAttrs = { "itemnum" };
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
		String parentlocation=this.getJpo().getString("parentlocation");
		setListObject("SYS_ITEM");
		String listSql = "";
		listSql = listSql
				+ "itemnum in (select itemnum from sys_inventory where location='"+parentlocation+"' and curbal>0) and ISTURNOVERERP='1'";
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		this.getJpo().setValue("PARENTSQN", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("PARENTSQNASSETNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("ITEMNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("SQN", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("PARENTASSETNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("LOTNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("QTY", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
	}
}
