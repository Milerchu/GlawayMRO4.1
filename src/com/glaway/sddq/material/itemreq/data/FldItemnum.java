package com.glaway.sddq.material.itemreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 领料单选择物料编码字段类
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-9]
 * @since [GlawayMro4.0/领料单]
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

		if (this.getJpo().getParent().getString("mprtype").equals("JS")) {// ==入库单
			setListObject("sys_item");
			String rktype=this.getJpo().getParent().getString("rktype");
			String PARENTSQNASSETNUM=this.getJpo().getString("PARENTSQNASSETNUM");
			String PARENTITEMNUM=this.getJpo().getString("PARENTITEMNUM");
			if(rktype.isEmpty()){
				listSql = "1=1";
			}else{
				if(rktype.equalsIgnoreCase("拆借件入库")){
					listSql = "itemnum in (select itemnum from asset  start with assetnum='"+PARENTSQNASSETNUM+"' connect by parent = PRIOR assetnum) and itemnum!='"+PARENTITEMNUM+"'";
				}else{
					listSql = "1=1";
				}
			}
			
		} else if (this.getJpo().getParent().getString("mprtype").equals("TK")) {// ==退库单
			setListObject("sys_item");
			String MPRSTOREROOM=this.getJpo().getParent().getString("MPRSTOREROOM");
			String rkitemnum=this.getJpo().getJpoSet("RKMPRLINE").getJpo().getString("itemnum");//关联入库单的入库物料编码
			listSql = "itemnum in (select itemnum from sys_inventory where location='"+MPRSTOREROOM+"' and curbal>0) " +
					"and itemnum in (select ALTITEMNUM from SYS_ALTITEM where(sys_altitemid in(select s.sys_altitemid from sys_altitem s  left join (select * from sys_altitem) t on s.REPLACE = t.REPLACE and (s.parent = t.parent or (s.parent is null and t.parent is null)) where t.altitemnum ='"+rkitemnum+"')))";
			
		}else {
			setListObject("sys_inventory");
			IJpo parent = this.getJpo().getParent();
			String MPRSTOREROOM = parent.getString("MPRSTOREROOM");
			String mprtype = parent.getString("mprtype");
			String type = parent.getString("type");
			if (mprtype.equalsIgnoreCase("CB")) {// 领料单
				listSql = "itemnum IN (select itemnum from sys_item where ISTURNOVER=1) AND location='"
						+ MPRSTOREROOM + "' and CURBAL>0";
			} else if (mprtype.equalsIgnoreCase("JX")) {// 检修领料单
				if (type.equalsIgnoreCase("领料")) {
					listSql = "itemnum IN (select itemnum from sys_item where ISTURNOVER=1) AND location='"
							+ MPRSTOREROOM + "' and CURBAL>0";
				} else {// 检修领料单-退料
					listSql = "itemnum IN (select itemnum from sys_item where ISTURNOVER=1) AND location='"
							+ MPRSTOREROOM + "'";
				}

			} else {
				listSql = "location='" + MPRSTOREROOM + "' and CURBAL>0";
			}
		}
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
		String mprtype = this.getJpo().getParent().getString("mprtype");
		IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",
				MroServer.getMroServer().getSystemUserServer());
		itemset.setQueryWhere("itemnum='" + itemnum + "'");
		itemset.reset();
		String type = ItemUtil.getItemInfo(itemnum);
		if (ItemUtil.SQN_ITEM.equals(type)) {
			this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY, false);
			this.getJpo().setValue("sqn", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED, true);
			this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("LOTNUM", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_READONLY, true);
		} else {
			if (ItemUtil.LOT_I_ITEM.equals(type)) {
				this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("LOTNUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_REQUIRED,
						true);
				this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("sqn", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY, true);
				this.getJpo().setFieldFlag("QTY", GWConstant.S_READONLY, false);
				if(mprtype.equalsIgnoreCase("JS")){
					this.getJpo().setValue("QTY", 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}else{
					this.getJpo().setValue("QTY", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				
			} else {
				this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("sqn", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY, true);
				this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("LOTNUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_READONLY,
						true);
				this.getJpo().setFieldFlag("QTY", GWConstant.S_READONLY, false);
				if(mprtype.equalsIgnoreCase("JS")){
					this.getJpo().setValue("QTY", 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}else{
					this.getJpo().setValue("QTY", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
		}
	}
}
