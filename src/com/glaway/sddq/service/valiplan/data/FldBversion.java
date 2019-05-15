package com.glaway.sddq.service.valiplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 验证产品范围-验证前软件版本字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月15日]
 * @since [产品/模块版本]
 */
public class FldBversion extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {

		super.action();
		// 软件表新增数据
		ValiProRange vpr = (ValiProRange) getJpo();
		vpr.addSoftversion(getInputMroType().asString());

	}

}
