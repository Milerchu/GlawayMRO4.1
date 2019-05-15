package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨单 行未接收数量字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldWjsqty extends JpoField {
	/**
	 * 初始化未接收数量
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		double ORDERQTY = this.getJpo().getDouble("ORDERQTY");
		double yjsqty = this.getJpo().getDouble("yjsqty");
		double wjsqty = ORDERQTY - yjsqty;
		this.getJpo().setValue("wjsqty", wjsqty,
				GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
	}

}
