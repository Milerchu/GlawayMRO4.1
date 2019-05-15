package com.glaway.sddq.material.mprline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <领料单数量字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldQty extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 校验领用数量是否大于库存数量
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		if (!this.getJpo().getParent().getString("mprtype").equals("JS")) {// 关

			// TODO Auto-generated method stub
			super.action();
			if (this.getJpo().getParent().getString("mprtype")
					.equalsIgnoreCase("JX")) {
				if (this.getJpo().getParent().getString("type")
						.equalsIgnoreCase("领料")) {
					double qty = this.getJpo().getDouble("qty");
					double CURBAL = this.getJpo().getDouble("CURBAL");
					if (qty - CURBAL > 0) {
						throw new MroException("itemreq", "qty");
					}
				}
			} else {
				double qty = this.getJpo().getDouble("qty");
				double CURBAL = this.getJpo().getDouble("CURBAL");
				if (qty - CURBAL > 0) {
					throw new MroException("itemreq", "qty");
				}
			}

		}
	}

}
