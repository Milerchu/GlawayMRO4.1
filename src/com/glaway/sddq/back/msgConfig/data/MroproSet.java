package com.glaway.sddq.back.msgConfig.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 系统问题记录JpoSet
 * 
 * @author  chenbin
 * @version  [版本号, 2018-7-27]
 * @since  [产品/模块版本]
 */
public class MroproSet extends JpoSet implements IJpoSet {

	  private static final long serialVersionUID = 1L;
	    
	    public Mropro getJpoInstance()
	    {
	        return new Mropro();
	    }
}
