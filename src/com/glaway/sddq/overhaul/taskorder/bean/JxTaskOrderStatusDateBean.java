package com.glaway.sddq.overhaul.taskorder.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.overhaul.taskorder.data.JxTaskItem;

/**
 * 
 * 检修工单任务项状态变更类
 * 
 * @author  bchen
 * @version  [版本号, 2018-2-2]
 * @since  [产品/模块版本]
 */
public class JxTaskOrderStatusDateBean extends DataBean
{
    
    @Override
    public int execute()
        throws MroException, IOException
    {
        if (this.getJpo() != null)
        {
            JxTaskItem ct = (JxTaskItem)this.getJpo().getParent();
            ct.changeStatus(getString("status"), getString("memo"));
        }
        this.getAppBean().SAVE();
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
}
