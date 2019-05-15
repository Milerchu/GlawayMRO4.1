package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 工单 状态字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年6月25日]
 * @since [产品/模块版本]
 */
public class FldStatus extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 8365442311618570411L;

	@Override
	public void action() throws MroException {

		super.action();
		// 当前输入值
		String status = this.getInputMroType().asString();
		IJpo wo = this.getJpo();
		if ("关闭".equals(status)) {
			// 耗损件上下车
			IJpoSet consumeSet = MroServer.getMroServer().getSysJpoSet(
					"JXTASKLOSSPART",
					"jxtasknum='" + wo.getString("ordernum") + "'");
			if (consumeSet != null && consumeSet.count() > 0) {
				if (!"故障".equals(wo.getString("TYPE"))) {
					// 非故障工单
					WorkorderUtil.consumeUpDown(consumeSet, wo);
				}

			}
			if ("故障".equals(wo.getString("type"))) {
				// 清除任务执行人数据
				wo.setValueNull("ACTTASKPERSON");
			}

		}

	}

}
