package com.glaway.sddq.service.servplan.data;

import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;

public class ServicePlanSet extends JpoSet
{
    private static final long serialVersionUID = -3629593439454618770L;
    
    @Override
    public Jpo getJpoInstance()
    {
        return new ServicePlan();
    }
}
