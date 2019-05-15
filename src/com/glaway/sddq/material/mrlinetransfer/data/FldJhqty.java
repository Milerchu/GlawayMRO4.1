package com.glaway.sddq.material.mrlinetransfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <配件申请计划数量字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldJhqty extends JpoField {
	/**
	 * 判断计划数量不能少于发货数量
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String transferqtystr = this.getJpo().getString("transferqty");
		if (!transferqtystr.isEmpty()) {
			double jhqty = this.getDoubleValue();
			double transferqty = this.getJpo().getDouble("transferqty");
			if (jhqty < transferqty) {
				throw new MroException("计划数量不能少于发货数量");
			}
		}
	}

}
