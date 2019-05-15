package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 检修工单任务项JpoSet
 * 
 * @author  bchen
 * @version  [版本号, 2018-1-31]
 * @since  [产品/模块版本]
 */
public class JxTaskItemSet extends JpoSet implements IJpoSet
{
    private static final long serialVersionUID = 1L;
    
    public JxTaskItem getJpoInstance()
    {
        return new JxTaskItem();
    }
}
