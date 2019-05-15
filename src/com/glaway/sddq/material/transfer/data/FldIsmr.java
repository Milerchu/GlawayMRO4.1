package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <装箱单是否根据配件申请装箱字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-9-5]
 * @since [产品/模块版本]
 */
public class FldIsmr extends JpoField {
	/**
	 * 根据字段值控制配件申请编号字段只读必填
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String ismr = this.getValue();
		if (ismr.equalsIgnoreCase("是")) {
			this.getJpo().setFieldFlag("mrnum", GWConstant.S_READONLY, false);
			this.getJpo().setValue("mrnum", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("mrnum", GWConstant.S_REQUIRED, true);
		}
		if (ismr.equalsIgnoreCase("否")) {
			this.getJpo().setFieldFlag("mrnum", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("mrnum", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("mrnum", GWConstant.S_READONLY, true);
		}
		this.getJpo().setFieldFlag("RECEIVESTOREROOM", GWConstant.S_READONLY,
				false);
		this.getJpo().setValue("RECEIVESTOREROOM", "",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setFieldFlag("RECEIVESTOREROOM", GWConstant.S_REQUIRED,
				true);
		this.getJpo().setFieldFlag("RECEIVEADDRESS", GWConstant.S_READONLY,
				false);
		this.getJpo().setValue("RECEIVEADDRESS", "",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setFieldFlag("RECEIVEADDRESS", GWConstant.S_REQUIRED,
				true);
	}

}
