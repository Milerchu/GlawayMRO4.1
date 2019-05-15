package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 车型每月的预测/计划数量jposet
 * 
 * @author  bchen
 * @version  [版本号, 2018-5-9]
 * @since  [产品/模块版本]
 */
public class YearRepairPlansSet extends JpoSet implements IJpoSet
{
    private static final long serialVersionUID = 1L;
    
    public YearRepairPlans getJpoInstance()
    {
        return new YearRepairPlans();
    }
}
