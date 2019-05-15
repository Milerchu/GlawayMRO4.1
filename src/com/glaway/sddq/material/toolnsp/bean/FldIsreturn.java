package com.glaway.sddq.material.toolnsp.bean;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <工具送检表是否返回字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldIsreturn extends JpoField {
	private static final long serialVersionUID = 1L;

	/**
	 * 触发控制字段只读
	 * 
	 * @throws MroException
	 */
	public void action() throws MroException {

		int isreturn = this.getJpo().getInt("ISRETURN");

		if (isreturn == 1) {
			this.getJpo().setFieldFlag("ACTCHECKDATE", GWConstant.S_READONLY,
					false);
			this.getJpo().setFieldFlag("CHECKRESULT", GWConstant.S_READONLY,
					false);
			this.getJpo().setFieldFlag("STATUS", GWConstant.S_READONLY, false);
		} else {
			this.getJpo().setFieldFlag("ACTCHECKDATE", GWConstant.S_READONLY,
					true);
			this.getJpo().setFieldFlag("CHECKRESULT", GWConstant.S_READONLY,
					true);
			this.getJpo().setFieldFlag("STATUS", GWConstant.S_READONLY, true);
		}

	}
}
