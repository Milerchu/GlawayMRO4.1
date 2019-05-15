package com.glaway.sddq.material.locaddress.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * <库房地址表JPoSet>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class LocaddressSet extends JpoSet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5837456078061613993L;

	/**
	 * @return
	 */
	@Override
	public Locaddress getJpoInstance() {
		return new Locaddress();
	}
}
