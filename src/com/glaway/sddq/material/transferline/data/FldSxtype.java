package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <装箱单选择修造类别字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-9-4]
 * @since [产品/模块版本]
 */
public class FldSxtype extends JpoField {
	/**
	 * 触发控制项目编号和工作令号字段必填只读
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String sxtype = this.getValue();
		if (sxtype.equalsIgnoreCase("GAIZ")) {
			this.getJpo().setValue("PROJECTNUM", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_READONLY,
					true);
			this.getJpo().setFieldFlag("transworkordernum",
					GWConstant.S_READONLY, false);
			this.getJpo().setFieldFlag("transworkordernum",
					GWConstant.S_REQUIRED, true);
		} else {
			this.getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_READONLY,
					false);
			this.getJpo().setFieldFlag("transworkordernum",
					GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("transworkordernum", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("transworkordernum",
					GWConstant.S_READONLY, true);
		}
	}

}
