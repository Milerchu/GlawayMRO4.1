package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 耗损件上下车-下车件父级唯一序列号字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年12月4日]
 * @since [产品/模块版本]
 */
public class FldLossDownAssetnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();
		if (getJpo() != null) {

			String orderType = getJpo().getString("workordertype");// 工单类型

			if ("故障".equals(orderType)) {// 故障工单
				// 获取故障件上级部件
				WorkorderUtil.getSuperiorAsset(getJpo(), "LOT", getJpo()
						.getString("DOWNITEMNUM"),
						getInputMroType().asString(),
						getJpo().getString("JXTASKLOSSPARTID"), getJpo()
								.getString("DOWNLOTNUM"));
			}
		}
	}

}
