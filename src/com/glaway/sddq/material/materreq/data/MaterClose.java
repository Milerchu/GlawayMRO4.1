package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <配件申请关闭定制类>
 * 
 * @author zzx
 * @version [MRO4.1, 2018-9-4]
 * @since [MRO4.1/模块版本]
 */
public class MaterClose implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		// TODO Auto-generated method stub
		if (jpo != null) {
			jpo.setValue("status", "结束", GWConstant.P_NOVALIDATION);

			jpo.getJpoSet().save();
		}
	}
}
