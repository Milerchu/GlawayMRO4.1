package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 备料清单列表计划数量字段类
 * 
 * @author  chenbin
 * @version  [版本号, 2018-9-3]
 * @since  [产品/模块版本]
 */
public class FldStockPlanNum  extends JpoField {

	@Override
	public void action() throws MroException {
		super.action();
		int planamount = getIntValue();
		int amounts=getJpo().getInt("AMOUNT");
		int totalamount =amounts*planamount;
		getField("TOTALAMOUNT").setValue( totalamount, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
	}
}
