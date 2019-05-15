package com.glaway.sddq.material.invblance.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <批次表数量字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2018-7-31]
 * @since [产品/模块版本]
 */
public class FLdPhyscntqty extends JpoField {
	/**
	 * 判断输入数量是否大于库存数量减现有批次数量
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		double CURBAL = this.getJpo().getParent().getDouble("CURBAL");
		IJpoSet thisjposet = this.getJpo().getParent().getJpoSet("invblance");
		double sumPHYSCNTQTY = thisjposet.sum("PHYSCNTQTY");
		double newPHYSCNTQTY = CURBAL - sumPHYSCNTQTY;
		if (newPHYSCNTQTY < 0) {
			throw new MroException("invblance", "qty");
		}
	}

}
