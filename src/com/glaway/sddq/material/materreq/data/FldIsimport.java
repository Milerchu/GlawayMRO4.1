package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <配件申请是否紧急字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldIsimport extends JpoField {
	/**
	 * 触发设置紧急原因必填控制
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String thisvalue = this.getValue();
		if (thisvalue.equalsIgnoreCase("是")) {
			this.getJpo().setFieldFlag("description", GWConstant.S_REQUIRED,
					true);
		}
		if (thisvalue.equalsIgnoreCase("否")) {
			this.getJpo().setValue("description", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("description", GWConstant.S_REQUIRED,
					false);
		}
	}

}
