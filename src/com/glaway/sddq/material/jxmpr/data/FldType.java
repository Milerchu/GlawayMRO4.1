package com.glaway.sddq.material.jxmpr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 交易类型字段类
 * 
 * @author zzx
 * @version [版本号, 2018-6-8]
 * @since [物资/检修领料单]
 */
public class FldType extends JpoField {
	private static final long serialVersionUID = 1L;

	/**
	 * 根据交易类型设置计划字段的值以及只读控制
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String type = this.getJpo().getString("TYPE");
		if (this.getJpo().getAppName().equals("JXMPR")) {
			if (type.equals("退料")) {
				this.getField("PLAN").setValue("Y",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getField("plan").setFlag(GWConstant.S_READONLY, true);
			} else {
				this.getField("PLAN").setValue("N",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getField("plan").setFlag(GWConstant.S_READONLY, false);
			}
		}
	}

}
