/*
 * 文 件 名:  WfHistory.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  工作流历史记录JPO
 * 修 改 人:  hyhe
 * 修改时间:  2016-7-11
 */
package com.glaway.mro.app.system.workflow.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 工作流历史记录JPO
 * 
 * @author  hyhe
 * @version  [版本号, 2016-7-11]
 * @since  [产品/模块版本]
 */
public class WfHistory extends Jpo implements IJpo
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        super.init();
        if (!this.getString("TASKID").isEmpty())
        {
            this.setValue("STARTTIME", this.getString("ACT_HI_TASKINST.START_TIME_"), GWConstant.P_NOUPDATE);
            this.setValue("ENDTIME", this.getString("ACT_HI_TASKINST.END_TIME_"), GWConstant.P_NOUPDATE);
        }
        else
        {
            this.setValue("STARTTIME", this.getString("ASSIGNTIME"), GWConstant.P_NOUPDATE);
        }
    }
}
