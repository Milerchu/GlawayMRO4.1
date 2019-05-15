package com.glaway.sddq.service.valiplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 验证计划-状态字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月29日]
 * @since [产品/模块版本]
 */
public class FldStatus extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();

	}

	@Override
	public void init() throws MroException {
		super.init();
		if ("验证".equals(getJpo().getString("plantype"))) {// 验证计划
			// 设置计划审核人为当前工作流操作人
			// String status = getJpo().getString("status");
			// String planEditor = getJpo().getString("PLANEDITOR");
			// if ("待审核".equals(status) && StringUtil.isStrEmpty(planEditor)) {
			// getJpo().setValue("PLANEDITOR",
			// getJpo().getUserInfo().getUserName(),
			// GWConstant.P_NOVALIDATION);
			// getJpo().setFieldFlag("PLANEDITOR", GWConstant.S_READONLY, true);
			//
			// }
		}
	}

}
