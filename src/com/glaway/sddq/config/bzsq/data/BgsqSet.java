package com.glaway.sddq.config.bzsq.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;
/**
 * 
 * 配置变更申请JpoSet
 * 
 * @author  hyhe
 * @version  [版本号, 2018-11-29]
 * @since  [产品/模块版本]
 */
public class BgsqSet extends JpoSet implements IJpoSet{
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;
	
	public Bgsq getJpoInstance()
    {
        return new Bgsq();
    }
}
