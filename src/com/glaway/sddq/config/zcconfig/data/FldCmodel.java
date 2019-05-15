package com.glaway.sddq.config.zcconfig.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 选择车型字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-4-24]
 * @since  [产品/模块版本]
 */
public class FldCmodel extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();
		if(this.isValueChanged()){
			this.getJpo().setValueNull("RepairProcess", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValueNull("carno", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}
}
