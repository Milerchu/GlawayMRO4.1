package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <合格数量字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldPassqty extends JpoField {
	/**
	 * 初始化合格数量
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		double passqty = this.getJpo().getParent().getDouble("passqty");
		this.getJpo().setValue("passqty", passqty,
				GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
	}
}
