package com.glaway.sddq.material.convertlocline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨转库单接收窗口未接收数量字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class FldStatusWjsqty extends JpoField {
	/**
	 * 初始化未接收数量
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		double QTY = this.getJpo().getParent().getDouble("QTY");
		double yjsqty = this.getJpo().getParent().getDouble("yjsqty");
		double wjsqty = QTY - yjsqty;
		this.getJpo().setValue("wjsqty", wjsqty,
				GWConstant.P_NOUPD_NOACTION_NOVALIDAT);

	}

}
