package com.glaway.sddq.overhaul.jhd.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 检修交货单JpoSet
 * 
 * @author public2175
 * @version [版本号, 2019-1-10]
 * @since [产品/模块版本]
 */
public class JxJhdSet extends JpoSet implements IJpoSet {

	/**
	 * 默认序列化ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取返回的JxJhd实例
	 * 
	 * @return
	 */
	public JxJhd getJpoInstance() {
		return new JxJhd();
	}

}
