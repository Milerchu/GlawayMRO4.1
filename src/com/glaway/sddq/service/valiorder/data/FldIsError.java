package com.glaway.sddq.service.valiorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 验证工单 是否异常iserror字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月6日]
 * @since [产品/模块版本]
 */
public class FldIsError extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 5452902357954957589L;

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		boolean input = getInputMroType().asBoolean();
		if (input) {
			getJpo().setValue("issuccess", 0);
		} else {
			getJpo().setValue("issuccess", 1);
		}

		String iserror = getInputMroType().asString();
		String issuccess = this.getJpo().getString("ISSUCCESS");

			if (iserror.equals("1")) {
				this.getJpo().setFieldFlag("VALIRESULT", GWConstant.S_REQUIRED,
						true);
			} else if (issuccess.equals("1")) {

				this.getJpo().setFieldFlag("VALIRESULT", GWConstant.S_REQUIRED,
						false);
			this.getJpo().getField("VALIRESULT").clearError();
			}



		super.action();
	}

}
