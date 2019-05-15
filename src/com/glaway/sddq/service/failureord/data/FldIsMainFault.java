package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 故障工单-上下车记录-是否主故障件字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年7月5日]
 * @since [产品/模块版本]
 */
public class FldIsMainFault extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 8192052051495773590L;

	@Override
	public void action() throws MroException {
		// 当前输入值
		int input = this.getInputMroType().asInt();
		if (input == 1) {
			// 上下车记录表
			IJpoSet exchangeSet = getJpo().getJpoSet();
			if (!exchangeSet.isEmpty()) {
				// 判断是否已经有主故障件
				for (int index = 0; index < exchangeSet.count(GWConstant.P_COUNT_DATABASE); index++) {
					IJpo ex = exchangeSet.getJpo(index);
					// 其他故障件都设为从属故障件
					ex.setValue("ISMAINFAULT", 0);
					/*
					 * ex.getField("ISMAINFAULT").getPreviousMroType()
					 * .getStringValue();
					 */

				}
			}
			this.setValue(1,GWConstant.P_NOACTION);
		}
		super.action();
	}

}
