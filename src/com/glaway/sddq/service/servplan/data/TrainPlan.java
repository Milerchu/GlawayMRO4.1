package com.glaway.sddq.service.servplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 培训计划jpo
 * 
 * @author  ygao
 * @version  [版本号, 2017-11-7]
 * @since  [产品/模块版本]
 */
public class TrainPlan extends StatusJpo implements IStatusJpo, FixedLoggers
{
    private static final long serialVersionUID = 1L;
    
    
    @Override
    public void init()
        throws MroException
    {
        super.init();
        if(getString("STATUS").equals("已完成")){
            this.setFieldFlag("ACTCOMPLETEDATE", GWConstant.S_READONLY, true);
            this.setFieldFlag("CONFIRMOR", GWConstant.S_READONLY, true);
        }
    }
}
