package com.glaway.sddq.service.chasset.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 串换记录-串换类型chtype字段类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月25日]
 * @since [产品/模块版本]
 */
public class FldChType extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 9063763956047033378L;

	@Override
	public void action() throws MroException {

		super.action();
		// 当前输入值
		String chType = this.getInputMroType().asString();

		if (StringUtil.isStrNotEmpty(chType)) {

			if (SddqConstant.SWAP_SAMETRAIN.equals(chType)) {// 同车互换

				// 将原车相关字段值赋值到串换车上
				getJpo().setValue("AFTERCARNO",
						getJpo().getString("BEFORECARNO"));
				getJpo().setValue("NEWTRAINASSETNUM",
						getJpo().getString("OLDTRAINASSETNUM"));
				// 设置字段只读
				getJpo().setFieldFlag("AFTERCARNO", GWConstant.S_READONLY, true);
				// 串换车号设置必填取消
				getJpo().setFieldFlag("AFTERCARNO", GWConstant.S_REQUIRED,
						false);

			} else {// 两车互换

				// 只读取消
				getJpo().setFieldFlag("AFTERCARNO", GWConstant.S_READONLY,
						false);
				// 串换车号设置必填
				getJpo().setFieldFlag("AFTERCARNO", GWConstant.S_REQUIRED, true);

			}
			// 设置物料编码必填
			String[] requiredAttrs = { "BFEBATCHNUM", "AFTERSQN",
					"AFTBATCHNUM", "BEFORESQN" };
			getJpo().setFieldFlag(requiredAttrs, GWConstant.S_REQUIRED, true);
		}

	}

}
