package com.glaway.mro.app.system.workflow.bean;

import com.glaway.mro.app.system.workflow.util.WFConstant;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * @ClassName: WfTaskBean
 * @Description: 查看工作流任务分配bean-对应act_ru_task表
 * @author hyhe
 * @date Mar 23, 2016 2:30:43 PM
 */
public class WfTaskBean extends DataBean
{
    @Override
    public void initJpoSet()
        throws MroException
    {
        super.initJpoSet();
        try
        {
            IJpo actAppInfo = WfControlUtil.getActAppInfo(getAppBean());
            if (actAppInfo != null && !actAppInfo.getString("STATUS").equals(WFConstant.STOP))
            {
                String processInstanceId = actAppInfo.getString("PROCINSTID");
                this.jpoSet.setUserWhere("PROC_INST_ID_ ='" + StringUtil.getSafeSqlStr(processInstanceId) + "'");
            }
            else
            {
                this.jpoSet.setUserWhere("1 = 2");
            }
            this.jpoSet.reset();
        }
        catch (Exception e)
        {
            EXCEPTIONLOGGER.error(e);
        }
    }
}
