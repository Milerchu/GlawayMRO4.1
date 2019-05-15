package com.glaway.sddq.material.inventory.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * <库存表JPOSET>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class InventorySet extends JpoSet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 42415563064965294L;

	@Override
	public Inventory getJpoInstance() {
		return new Inventory();
	}

}
