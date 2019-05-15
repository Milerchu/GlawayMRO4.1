package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 服务工单-servordertype字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-12]
 * @since [产品/模块版本]
 */
public class FldServorderType extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -2573178344692498188L;

	@Override
	public void action() throws MroException {
		// 当前输入值
		String servordertype = getInputMroType().asString();
		if (isValueChanged()) {// 字段值变更后清除部分已填字段的值

			String[] attrs = { "CARNUM", "MODELS", "REPAIRPROCESS", "ASSETNUM",
					"MODELPROJECT", "PROJECTNUM", "SERVCOMPANY", "WHICHOFFICE",
					"ORDERNUM", "SERVCOMCONTACTOR", "SERVENGINEER",
					"UPDATETIME", "RUNKILOMETRE", "REPAIRAFTERKILOMETER",
					"OVERHAULER", "OWNERCUSTOMER" };// 需要清空的字段
			for (String attr : attrs) {

				if (!SddqConstant.SO_TYPE_HWDC.equals(getInputMroType()
						.asString())) {
					// getJpo().setFieldFlag("CARNUM", GWConstant.S_READONLY,
					// false);

				}
				getJpo().setValueNull(attr, GWConstant.P_NOVALIDATION);

			}

		}
		if (servordertype.equals(SddqConstant.SO_TYPE_HWDC)) {

			getJpo().setFieldFlag("CARNUM", GWConstant.S_REQUIRED, false);
			String sotype = getJpo().getString("SERVORDERTYPE");
			if (StringUtil.isStrNotEmpty(sotype)) {

				if (!SddqConstant.SO_TYPE_HWDC.equals(sotype)) {
					// 清空已有数据
					// getJpo().setValueNull("CARNUM");
					// getJpo().setValueNull("MODELS");
					// getJpo().setValueNull("REPAIRPROCESS");
					// getJpo().setValueNull("ASSETNUM");
					// getJpo().setValueNull("MODELPROJECT");

				}
				// getJpo().setFieldFlag("CARNUM", GWConstant.S_READONLY, true);
				getJpo().getField("CARNUM").clearError();
			}

		} else if (SddqConstant.SO_TYPE_TSJC.equals(servordertype)) {

			// getJpo().setFieldFlag("CARNUM", GWConstant.S_READONLY, false);
			getJpo().setFieldFlag("CARNUM", GWConstant.S_REQUIRED, true);

		} else {

			getJpo().setFieldFlag("CARNUM", GWConstant.S_REQUIRED, false);
			// getJpo().setFieldFlag("CARNUM", GWConstant.S_READONLY, false);
			getJpo().getField("CARNUM").clearError();

		}

		super.action();
	}
}
