package com.glaway.sddq.service.failurelib.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <是否存在故障备品字段类>
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-4-24]
 * @since [MRO4.1/模块版本]
 */
public class FldIsexistspare extends JpoField {
	/**
	 * 唯一序列
	 */
	private static final long serialVersionUID = -5994581427863941552L;

	@Override
	public void action() throws MroException {
		// 设置故障备品权限
		String flag = getInputMroType().getStringValue();
		String[] spareAttr = { "SPAREPRODUCTNUM", "SPAREPRODUCTCODE",
				"SPAREFAILUREDESC" };

		if ("是".equals(flag)) {
			getJpo().setFieldFlag(spareAttr, GWConstant.S_READONLY, false);
		} else {
			getJpo().setFieldFlag(spareAttr, GWConstant.S_READONLY, true);
		}

		super.action();
	}
}
