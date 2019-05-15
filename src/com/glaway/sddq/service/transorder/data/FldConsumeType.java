package com.glaway.sddq.service.transorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 改造耗损件CONSUMETYPE类字段
 * 
 * @author bchen
 * @version [版本号, 2018-3-12]
 * @since [产品/模块版本]
 */
public class FldConsumeType extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -6021505284700018077L;

	@Override
	public void action() throws MroException {
		super.action();
		String type = this.getJpo().getString("CONSUMETYPE");
		if (type != null && type.equals("下车")) {
			this.getJpo().setValue("CONSUMEMARK", "报废", GWConstant.S_READONLY);
		} else {
			this.getField("CONSUMEMARK").setValueNull(
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}

}
