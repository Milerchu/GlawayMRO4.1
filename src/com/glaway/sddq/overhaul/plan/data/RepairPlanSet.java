package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;
/**
 * 
 * 检修计划JpoSet
 * 
 * @author  hyhe
 * @version  [版本号, 2017-10-19]
 * @since  [产品/模块版本]
 */
public class RepairPlanSet extends JpoSet implements IJpoSet{
    
   private static final long serialVersionUID = 1L;
   
   public RepairPlan getJpoInstance()
   {
       return new RepairPlan();
   }
}
