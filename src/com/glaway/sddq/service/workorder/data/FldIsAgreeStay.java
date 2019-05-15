package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 上下车记录 是否同意留用观察字段类
 * 
 * @author zhuhao
 * @version [版本号, 2019年3月29日]
 * @since [产品/模块版本]
 */
public class FldIsAgreeStay extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();
		String isAgreeStary = this.getMroType().asString();
		if ("不同意".equals(isAgreeStary)) {// 不同意留用
			getJpo().setValue("DEALMODE",
					SddqConstant.FAIL_DEALMODE_BASEREPAIR,
					GWConstant.P_NOVALIDATION);

		} else {// 同意留用
			getJpo().setValue("DEALMODE", SddqConstant.FAIL_DEALMODE_RETENTION,
					GWConstant.P_NOVALIDATION);
		}

	}

}
