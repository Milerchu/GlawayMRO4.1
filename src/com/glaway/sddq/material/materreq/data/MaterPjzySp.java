package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <配件申请，配件专员执行触发status字段类>
 * 
 * @author  public2795
 * @version  [版本号, 2018-10-25]
 * @since  [产品/模块版本]
 */
public class MaterPjzySp implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		// TODO Auto-generated method stub
		if (jpo != null) {
			jpo.setValue("status", "配件专员执行", GWConstant.P_NOVALIDATION);

			jpo.getJpoSet().save();
		}
	}
}
