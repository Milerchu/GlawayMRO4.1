package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 改造计划-物料计划-未分批数量distedqty字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年7月25日]
 * @since [产品/模块版本]
 */
public class FldDistedQty extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 6671645093381124524L;

	@Override
	public void action() throws MroException {
		// 当前输入值
		float inputVal = getInputMroType().asFloat();
		if (getJpo() != null) {
			// 变更未分配数量
			getJpo().setValue("UNDISTQTY",
					getJpo().getFloat("MATERIALCOUNT") - inputVal);
		}
		super.action();
	}

}
