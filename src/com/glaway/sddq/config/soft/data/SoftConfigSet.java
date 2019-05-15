package com.glaway.sddq.config.soft.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;
/**
 * 
 * 软件配置JpoSet
 * 
 * @author  public2175
 * @version  [版本号, 2018-6-7]
 * @since  [产品/模块版本]
 */
public class SoftConfigSet extends JpoSet implements IJpoSet{

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;
	
	public SoftConfig getJpoInstance()
    {
        return new SoftConfig();
    }
	
}
