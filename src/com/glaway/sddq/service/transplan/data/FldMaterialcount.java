package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 改造计划-物料计划-数量materialcount字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年7月25日]
 * @since [产品/模块版本]
 */
public class FldMaterialcount extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 8339634530456785457L;

	@Override
	public void action() throws MroException {
		// 设置未分配数量
		float inputvalue = getInputMroType().asFloat();
		getJpo().setValue("UNDISTQTY", inputvalue);
		super.action();
	}

}
