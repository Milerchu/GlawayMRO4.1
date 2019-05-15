package com.glaway.sddq.material.mrlinetransline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <办事处主任处置-处置方式字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-28]
 * @since [产品/模块版本]
 */
public class FldTranstype extends JpoField {
	/**
	 * 触发赋值，控制字段制度比他
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String transtype = this.getValue();
		if (transtype.equalsIgnoreCase(ItemUtil.TRANSTYPE_NBXT)) {// 处置方式为内部协调
			this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_READONLY,
					false);
			this.getJpo().setFieldFlag("TRANSFERQTY", GWConstant.S_READONLY,
					false);
			this.getJpo().setValue("STOREROOM", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValue("TRANSFERQTY", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo()
					.setFieldFlag("STOREROOM", GWConstant.S_REQUIRED, true);
			this.getJpo().setFieldFlag("TRANSFERQTY", GWConstant.S_REQUIRED,
					true);
		}
		if (transtype.equalsIgnoreCase(ItemUtil.TRANSTYPE_JHJLXT)) {// 处置方式为计划经理协调
			this.getJpo().setFieldFlag("STOREROOM", GWConstant.S_REQUIRED,
					false);
			this.getJpo().setFieldFlag("TRANSFERQTY", GWConstant.S_REQUIRED,
					false);
			this.getJpo().setValue("STOREROOM", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValue("TRANSFERQTY", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo()
					.setFieldFlag("STOREROOM", GWConstant.S_READONLY, true);
			this.getJpo().setFieldFlag("TRANSFERQTY", GWConstant.S_REQUIRED,
					true);
		}
	}

}
