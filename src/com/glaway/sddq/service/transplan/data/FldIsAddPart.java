package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 改造计划-改造内容-是否加装字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年12月26日]
 * @since [产品/模块版本]
 */
public class FldIsAddPart extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();
		if ("TRANSPLAN".equalsIgnoreCase(getJpo().getAppName())) {
			boolean isaddpart = getInputMroType().asBoolean();
			if (isaddpart) {
				getJpo().setFieldFlag("TARGET", GWConstant.S_READONLY, false);
				getJpo().setFieldFlag("TARGET", GWConstant.S_REQUIRED, true);

			} else {
				getJpo().getField("TARGET").clearError();
				getJpo().setFieldFlag("TARGET", GWConstant.S_REQUIRED, false);
				getJpo().setFieldFlag("TARGET", GWConstant.S_READONLY, true);
			}
		}

	}

}
