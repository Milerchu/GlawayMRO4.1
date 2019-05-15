package com.glaway.sddq.base.person.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.service.failurelib.data.Failurelib;

/**
 * 用户状态变更操作
 * @author jxiang
 *
 */
public class PersonChangeStatusBean extends DataBean
{
    
    @Override
    public int execute()
        throws MroException, IOException
    {
        
        IJpo appjpo = getAppBean().getJpo();
        if (appjpo == null)
        {
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
        checkSave();
        String showstatus = getString("showstatus");
        String oldStatus = getString("status");
        
      
        
        String personid = getString("personid");
        
               
       
        //暂去掉人员和用户之间变更状态的相互制约，具体项目具体对待
        if ("不活动".equals(showstatus))
        {
            IJpoSet userJpoSet =
                getJpo().getUserServer().getJpoSet("SYS_USER", "personid='" + StringUtil.getSafeSqlStr(personid) + "'");
            if (!userJpoSet.isEmpty())
            {
                IJpo userJpo = userJpoSet.getJpo(0);
                String status = userJpo.getString("STATUS");
                String innerStatus = userJpo.getInnerValue("USERSTATUS", status);
                if ("ACTIVE".equals(innerStatus) || "LOCKED".equals(innerStatus))
                {
                    String userId = userJpo.getString("USERID");
                    String[] param = {personid, userId};
                    throw new MroException("person", "personchangestatus", param);
                }
            }
        }
        
        if (null != showstatus && !"".equals(showstatus))
        {
            java.util.Date curDate = MroServer.getMroServer().getDate();
            
            ////构建状态历史变更对象 
            IJpoSet hisJpoSet = appjpo.getJpoSet("PERSONSTATUS");
            IJpo hisJpo = hisJpoSet.addJpo(GWConstant.P_NOVALIDATION_AND_NOACTION);
            String memo = "用户状态从" + oldStatus + "变更为" + showstatus;
            
            appjpo.setValue("status", showstatus, GWConstant.P_NOVALIDATION_AND_NOACTION);
            appjpo.setValue("STATUSDATE", curDate);
            appjpo.setValue("NP_STATUSMEMO", memo);
            
            hisJpo.setValue("changeby", appjpo.getUserInfo().getPersonId(), GWConstant.P_NOVALIDATION_AND_NOACTION);
            hisJpo.setValue("changedate", curDate, GWConstant.P_NOVALIDATION_AND_NOACTION);
            hisJpo.setValue("memo", memo);
            hisJpo.setValue("personid", personid, GWConstant.P_NOVALIDATION_AND_NOACTION);
            hisJpo.setValue("status", showstatus, GWConstant.P_NOVALIDATION_AND_NOACTION);
            hisJpo.setValue("oldstatus", oldStatus, GWConstant.P_NOVALIDATION_AND_NOACTION);
            getAppBean().SAVE();
            reloadPage();
        }
        else
        {
            throw new MroException("PersonChangeStatus", "statusnotnull");
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * @return
     * @throws IOException
     * @throws MroException
     */
    @Override
    public int dialogcancel()
        throws IOException, MroException
    {
        IJpo appjpo = getAppBean().getJpo();
        if (appjpo != null && appjpo.getField("SHOWSTATUS") != null)
        {
            appjpo.getField("SHOWSTATUS").clearError();
        }
        return super.dialogcancel();
    }
}
