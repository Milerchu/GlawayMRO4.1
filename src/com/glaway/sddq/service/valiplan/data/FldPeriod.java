package com.glaway.sddq.service.valiplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 验证计划 验证周期 period 字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年10月16日]
 * @since [产品/模块版本]
 */
public class FldPeriod extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();
		// 调用启动日期的action方法
		this.getJpo().getField("STARTDATE").action();

	}

}
