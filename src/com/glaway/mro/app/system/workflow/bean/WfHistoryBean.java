package com.glaway.mro.app.system.workflow.bean;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * @ClassName: WfHistoryBean
 * @Description: 工作流历史记录bean-对应ACT_HI_ASSIGN表
 * @author hyhe
 * @date Mar 23, 2016 2:30:56 PM
 */
public class WfHistoryBean extends DataBean
{
    @Override
    public void initJpoSet()
        throws MroException
    {
        super.initJpoSet();
        try
        {
            IJpo actAppInfo = WfControlUtil.getActAppInfo(getAppBean());
            if (actAppInfo != null)
            {
                String processInstanceId = actAppInfo.getString("PROCINSTID");
                this.jpoSet.setUserWhere("PROCINSTID ='" + StringUtil.getSafeSqlStr(processInstanceId) + "'");
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
