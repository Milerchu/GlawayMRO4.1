package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 检修工单JpoSet
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-24]
 * @since  [产品/模块版本]
 */
public class JxTaskOrderSet extends JpoSet implements IJpoSet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public JxTaskOrder getJpoInstance()
    {
        return new JxTaskOrder();
    }
    
}
