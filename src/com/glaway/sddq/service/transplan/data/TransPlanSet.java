package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 *改造计划JpoSet
 * 
 * @author  bchen
 * @version  [版本号, 2018-3-9]
 * @since  [产品/模块版本]
 */
public class TransPlanSet extends JpoSet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -5243891854916031039L;
    
    public TransPlan getJpoInstance()
    {
        return new TransPlan();
    }
}
