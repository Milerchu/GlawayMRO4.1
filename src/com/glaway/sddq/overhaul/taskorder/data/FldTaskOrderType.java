package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 耗损件记录类型字段类
 * 
 * @author  bchen
 * @version  [版本号, 2018-2-2]
 * @since  [产品/模块版本]
 */
public class FldTaskOrderType extends JpoField
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void action()
        throws MroException
    {
        super.action();
        String type = this.getJpo().getString("type");
        if (type != null && type.equals("下车"))
        {
            this.getJpo().setValue("FLAG", "报废", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        }
        else
        {
            this.getField("FLAG").setValueNull(GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
        }
    }
    
}
