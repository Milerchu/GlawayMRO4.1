package com.glaway.sddq.service.transorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 改造工单-是否首台车字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月11日]
 * @since [产品/模块版本]
 */
public class FldIsFirst extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {

		super.action();
		boolean inputValue = this.getInputMroType().asBoolean();
		IJpo wo = getJpo();// 工单jpo
		IJpoSet transDistSet = wo.getJpoSet("TRANSDIST");// 改造车辆分布

		if (inputValue) {

			// 首台车情况必填
			wo.setFieldFlag("FIRSTCONDITION", GWConstant.S_REQUIRED, true);

			if (!transDistSet.isEmpty()) {
				// 设置首台车标记
				IJpo transDist = transDistSet.getJpo(0);
				transDist.setValue("HASFIRSTTRAIN", 1,
						GWConstant.P_NOVALIDATION_AND_NOACTION);

			}

		} else {

			wo.getField("FIRSTCONDITION").clearError();
			wo.setFieldFlag("FIRSTCONDITION", GWConstant.S_REQUIRED, false);

			if (!transDistSet.isEmpty()) {

				IJpo transDist = transDistSet.getJpo(0);
				transDist.setValue("HASFIRSTTRAIN", 0,
						GWConstant.P_NOVALIDATION_AND_NOACTION);

			}

		}
	}

}
