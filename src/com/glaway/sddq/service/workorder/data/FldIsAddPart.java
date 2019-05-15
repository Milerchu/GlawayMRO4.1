package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 改造工单-上下车记录 是否加装isaddpart字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年12月21日]
 * @since [产品/模块版本]
 */
public class FldIsAddPart extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {

		super.action();
		if ("改造".equals(getJpo().getString("TASKTYPE"))) {

			boolean isAddpart = getInputMroType().asBoolean();
			String[] bfTransAttrs = { "SQN", "LOTNUM", "SOFTVERSION" };// 改造前相关字段
			String[] needClear1 = { "SQN", "ITEMNUM", "LOTNUM", "ASSETNUM",
					"FAULTPOSITION", "SOFTVERSION", "LOCATION" };// 切换模式需清除字段
			String[] needClear2 = { "NEWSQN", "NEWASSETNUM", "NEWITEMNUM",
					"NEWLOC", "NEWBINNUM", "NEWLOTNUM", "NEWSOFTVERSION",
					"NEWLOC", "PARENTITEMNUM", "PARENTASSETNUM", "PARENTSQN" };// 切换模式需清除字段
			if (isAddpart) {// 加装

				getJpo().setFieldFlag("SQN", GWConstant.S_REQUIRED, false);
				getJpo().getField("SQN").clearError();
				getJpo().setFieldFlag("NEWSQN", GWConstant.S_REQUIRED, true);
				getJpo().setFieldFlag("PARENTSQN", GWConstant.S_READONLY, false);
				getJpo().setFieldFlag("PARENTSQN", GWConstant.S_REQUIRED, true);
				getJpo().setFieldFlag(bfTransAttrs, GWConstant.S_READONLY, true);
				for (String attr : needClear1) {
					if (StringUtil.isStrNotEmpty(getJpo().getString(attr))) {
						getJpo().setValueNull(attr, GWConstant.P_NOVALIDATION);// 请空数据
					}

				}

			} else {// 非加装

				getJpo().setFieldFlag("PARENTSQN", GWConstant.S_REQUIRED, false);
				getJpo().getField("PARENTSQN").clearError();
				getJpo().setFieldFlag("PARENTSQN", GWConstant.S_READONLY, true);
				getJpo().setFieldFlag(bfTransAttrs, GWConstant.S_READONLY,
						false);
				getJpo().setFieldFlag("SQN", GWConstant.S_REQUIRED, true);
				getJpo().setFieldFlag("NEWSQN", GWConstant.S_REQUIRED, false);
				getJpo().getField("NEWSQN").clearError();
				for (String attr : needClear2) {
					if (StringUtil.isStrNotEmpty(getJpo().getString(attr))) {
						getJpo().setValueNull(attr, GWConstant.P_NOVALIDATION);// 请空数据
					}

				}

			}
		}

	}

}
