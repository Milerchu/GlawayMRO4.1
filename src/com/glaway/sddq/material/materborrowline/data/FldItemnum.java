package com.glaway.sddq.material.materborrowline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;
/**
 * 
 * <配件借用行物料编码字段绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-24]
 * @since  [产品/模块版本]
 */
public class FldItemnum extends JpoField {

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
		String[] srcAttrs = { "itemnum"};
		setLookupMap(thisAttrs, srcAttrs);
	}
	/**
	 * 过滤库房物料数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("SYS_INVENTORY");
		String BORROWSTOREROOM=this.getJpo().getParent().getString("BORROWSTOREROOM");//借出库房
		String listSql = "LOCATION='"+BORROWSTOREROOM+"'";
		
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
	/**
	 * 触发方法
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String itemnum=this.getValue();//物料编码
		String type = ItemUtil.getItemInfo(itemnum);// --关联取物料是否批次号,序列号管理属性
		if (ItemUtil.SQN_ITEM.equals(type)) {//判断物料如果是序列号，设置序列号必填，批次号只读，数量为1且只读
			this.getJpo().setFieldFlag("SEQNUM", GWConstant.S_READONLY, false);
			this.getJpo().setValue("SEQNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("SEQNUM", GWConstant.S_REQUIRED, true);
			
			this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("LOTNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_READONLY, true);
			
			this.getJpo().setValue("QTY", 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}else if (ItemUtil.LOT_I_ITEM.equals(type)) {//判断物料如果是批次号,设置批次号必填，序列号只读，数量为1且只读
			this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_READONLY, false);
			this.getJpo().setValue("LOTNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_REQUIRED, true);
			
			this.getJpo().setFieldFlag("SEQNUM", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("SEQNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("SEQNUM", GWConstant.S_READONLY, true);
			this.getJpo().setValue("assetnum", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			
			this.getJpo().setValue("QTY", 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}else{//判断物料即不是是批次号也不是序列号，设置批次号和序列号只读，数量为1且只读
			this.getJpo().setFieldFlag("SEQNUM", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("SEQNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("SEQNUM", GWConstant.S_READONLY, true);
			this.getJpo().setValue("assetnum", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			
			this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("LOTNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("LOTNUM", GWConstant.S_READONLY, true);
			
			this.getJpo().setValue("QTY", 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}
	
}
