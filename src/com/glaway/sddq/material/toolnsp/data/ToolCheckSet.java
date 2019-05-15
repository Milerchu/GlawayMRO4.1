package com.glaway.sddq.material.toolnsp.data;

import com.glaway.mro.jpo.JpoSet;
/**
 * 
 * <工具送检JpoSet>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-8]
 * @since  [产品/模块版本]
 */
public class ToolCheckSet extends JpoSet{
	public ToolCheck getJpoInstance()
    {
        return new ToolCheck();
    }
}
