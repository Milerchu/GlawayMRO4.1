package com.glaway.sddq.service.servplan.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 培训计划jpoSet
 * 
 * @author   ygao
 * @version  [版本号, 2017-11-7]
 * @since  [产品/模块版本]
 */
public class TrainPlanSet extends JpoSet implements IJpoSet
{
    private static final long serialVersionUID = 1L;
    
    public TrainPlan getJpoInstance()
    {
        return new TrainPlan();
    }
}
