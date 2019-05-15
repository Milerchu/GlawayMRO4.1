package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <是否技术分析字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldIsjsfx extends JpoField {
	/**
	 * 触发是否紧急字段赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String isjsfx = this.getValue();
		if (isjsfx.equalsIgnoreCase("")) {
			this.getJpo().setValue("IMPORTLEVEL", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
		if (isjsfx.equalsIgnoreCase("是")) {
			this.getJpo().setValue("IMPORTLEVEL", "紧急",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
		if (isjsfx.equalsIgnoreCase("否")) {
			this.getJpo().setValue("IMPORTLEVEL", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}

}
