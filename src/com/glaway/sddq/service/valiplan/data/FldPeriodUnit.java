package com.glaway.sddq.service.valiplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 验证计划-验证周期单位字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月24日]
 * @since [产品/模块版本]
 */
public class FldPeriodUnit extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 3435406803611633008L;

	@Override
	public void validate() throws MroException {

		super.validate();
		// 校验单位是否合法
		// 合法的单位值
		String[] legalUnits = { "日", "天", "周", "月", "年" };
		// 当前输入值
		String input = getInputMroType().asString();
		if (!StringUtil.isHaveStr(legalUnits, input)) {// 判断是否在合法值范围内，不合法则抛异常
			throw new MroException("valiplan", "illegalUnit");
		}
	}

	@Override
	public void action() throws MroException {
		super.action();
		// 调用启动日期的action方法
		this.getJpo().getField("STARTDATE").action();

	}
}
