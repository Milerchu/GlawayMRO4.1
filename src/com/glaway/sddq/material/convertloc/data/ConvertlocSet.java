package com.glaway.sddq.material.convertloc.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * <调拨转库单JPoSet>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class ConvertlocSet extends JpoSet {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	public Convertloc getJpoInstance() {
		return new Convertloc();
	}
}
