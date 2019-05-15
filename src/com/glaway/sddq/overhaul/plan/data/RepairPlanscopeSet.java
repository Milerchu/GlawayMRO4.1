package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

public class RepairPlanscopeSet extends JpoSet implements IJpoSet
{
    /**
     * 注释内容
     */
    
    private static final long serialVersionUID = 1L;
    
    public RepairPlanscope getJpoInstance()
    {
        return new RepairPlanscope();
    }
}
