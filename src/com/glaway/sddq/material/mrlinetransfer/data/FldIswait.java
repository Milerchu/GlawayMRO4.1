package com.glaway.sddq.material.mrlinetransfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <配件申请是否待料字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldIswait extends JpoField {
	/**
	 * 根据是否待料情况设置倒料原因字段只读必填
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String iswait = this.getValue();
		if (iswait.equalsIgnoreCase("是")) {
			this.getJpo().setFieldFlag("waitdesc", GWConstant.S_REQUIRED, true);
		} else {

			this.getJpo()
					.setFieldFlag("waitdesc", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("waitdesc", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}

}
