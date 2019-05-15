package com.glaway.sddq.material.mrline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <是否刷程序字段类设置备注必填>
 * 
 * @author  public2795
 * @version  [版本号, 2018-10-13]
 * @since  [产品/模块版本]
 */
public class FldIssoft extends JpoField {

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		boolean issoft=this.getBooleanValue();
		if(issoft){
			this.getJpo().setValue("memo", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("memo", GWConstant.S_REQUIRED, true);
		}else{
			this.getJpo().setValue("memo", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("memo", GWConstant.S_REQUIRED, false);
		}
	}

}
