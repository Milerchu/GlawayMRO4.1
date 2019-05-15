package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.Jpo;

/**
 * 
 * 检修车辆列表jpo
 * 
 * @author  bchen
 * @version  [版本号, 2018-1-24]
 * @since  [产品/模块版本]
 */
public class RepairPlanscope extends Jpo implements IJpo
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void delete(long flag)
        throws MroException
    {
        super.delete(flag);
        if (this.getJpoSet("SINGLEOVERHAUL") != null)
        {
            this.getJpoSet("SINGLEOVERHAUL").deleteAll();
        }
    }
    
    @Override
    public void undelete()
        throws MroException
    {
        super.undelete();
        if (this.getJpoSet("SINGLEOVERHAUL") != null)
        {
            this.getJpoSet("SINGLEOVERHAUL").undeleteAll();
        }
    }
}
