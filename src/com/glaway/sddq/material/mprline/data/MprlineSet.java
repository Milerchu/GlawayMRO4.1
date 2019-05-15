package com.glaway.sddq.material.mprline.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * <领料单行表JPoSet>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class MprlineSet extends JpoSet {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	public Mprline getJpoInstance() {
		return new Mprline();
	}

}
