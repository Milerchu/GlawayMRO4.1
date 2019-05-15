package com.glaway.sddq.material.worline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

public class FldItemnum extends JpoField {
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

			setListObject("sys_item");
			String location=this.getJpo().getParent().getString("location");
			listSql = "itemnum in (select itemnum from sys_inventory where location='"+location+"' and curbal>0)";
			
		setListWhere(listSql);

		return super.getList();
	}
	
	/**
	 * 根据物料属性设置字段只读必填
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String itemnum = this.getValue();
		String type = ItemUtil.getItemInfo(itemnum);
		if (ItemUtil.SQN_ITEM.equals(type)) {
			this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY, false);
			this.getJpo().setValue("sqn", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED, true);
			this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("LOTNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_READONLY, true);
			this.getJpo().setFieldFlag("SCRAPQTY", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("SCRAPQTY", 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("SCRAPQTY", GWConstant.S_READONLY, true);
		} else {
			if (ItemUtil.LOT_I_ITEM.equals(type)) {
				this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_READONLY,false);
				this.getJpo().setValue("LOTNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_REQUIRED,true);
				this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("sqn", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY, true);
				this.getJpo().setFieldFlag("SCRAPQTY", GWConstant.S_READONLY, false);
				this.getJpo().setValue("SCRAPQTY", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("SCRAPQTY", GWConstant.S_REQUIRED,true);
				
			} else {
				this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("sqn", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY, true);
				this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_REQUIRED,false);
				this.getJpo().setValue("LOTNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_READONLY,true);
				this.getJpo().setFieldFlag("SCRAPQTY", GWConstant.S_READONLY, false);
				this.getJpo().setValue("SCRAPQTY", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("SCRAPQTY", GWConstant.S_REQUIRED,true);
			}
		}
	}
}
