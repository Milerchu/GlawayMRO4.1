package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <装箱单是否返修发运字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldIsFxfy extends JpoField {
	/**
	 * 根据字段值触发控制发出库房字段的必填只读
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		String isfxfy = this.getValue();
		if (isfxfy.equalsIgnoreCase("否")) {
			this.getJpo().setFieldFlag("ISSUESTOREROOM", GWConstant.S_REQUIRED,
					false);
			this.getJpo().setValue("ISSUESTOREROOM", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("ISSUESTOREROOM", GWConstant.S_READONLY,
					true);

			this.getJpo().setFieldFlag("ISSUEADDRESS", GWConstant.S_REQUIRED,
					false);
			this.getJpo().setValue("ISSUEADDRESS", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("ISSUEADDRESS", GWConstant.S_READONLY,
					true);
		}
		if (isfxfy.equalsIgnoreCase("是")) {
			this.getJpo().setFieldFlag("ISSUESTOREROOM", GWConstant.S_READONLY,
					false);
			this.getJpo().setValue("ISSUESTOREROOM", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("ISSUESTOREROOM", GWConstant.S_REQUIRED,
					true);

			this.getJpo().setFieldFlag("ISSUEADDRESS", GWConstant.S_READONLY,
					false);
			this.getJpo().setValue("ISSUEADDRESS", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("ISSUEADDRESS", GWConstant.S_REQUIRED,
					true);
		}
		super.action();

	}

}
