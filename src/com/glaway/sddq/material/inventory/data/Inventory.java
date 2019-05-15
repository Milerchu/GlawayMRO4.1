package com.glaway.sddq.material.inventory.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <库存表JPO>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class Inventory extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 59754576740756780L;

	/**
	 * 初始化判断是否新建设置只读
	 */
	@Override
	public void init() throws MroException {
		super.init();
		if (!isNew()) {
			this.setFieldFlag("ITEMNUM", GWConstant.S_READONLY, true);
			this.setFieldFlag("LOCATION", GWConstant.S_READONLY, true);
		}
	}

}
