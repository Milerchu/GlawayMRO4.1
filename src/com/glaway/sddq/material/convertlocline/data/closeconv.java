package com.glaway.sddq.material.convertlocline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨转库单流程节点操作类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class closeconv implements ActionCustomClass {
	/**
	 * 流程通过设置单据状态值
	 * 
	 * @param jpo
	 * @param parameter
	 * @throws MroException
	 */
	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		// TODO Auto-generated method stub
		if (jpo != null) {
			jpo.setValue("status", "已关闭",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

			jpo.getJpoSet().save();
		}
	}

}
