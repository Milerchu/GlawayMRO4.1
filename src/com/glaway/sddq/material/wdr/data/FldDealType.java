package com.glaway.sddq.material.wdr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <处置管理-处置方式字段类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-3-18]
 * @since  [产品/模块版本]
 */
public class FldDealType extends JpoField {

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		this.getJpo().setValue("location", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
	}

}
