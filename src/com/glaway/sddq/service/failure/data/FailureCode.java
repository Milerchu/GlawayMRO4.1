package com.glaway.sddq.service.failure.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

public class FailureCode extends Jpo implements FailureCodeRemote
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -3142848449575745688L;
    
    @Override
    public void init()
        throws MroException
    {
        if (!toBeAdded())
        {
            setFieldFlag("FAILURECODE", GWConstant.S_READONLY, true);
        }
        super.init();
    }
    
    /**
     * 
     * 状态变更，记录到状态历史表
     * @param newstatus 新状态
     * @param reason 变更原因
     * @throws MroException [参数说明]
     *
     */
    public void changestatus(String newstatus, String reason)
        throws MroException
    {
        if ("关闭".equals(getString("status")))
        {
            throw new MroException("troublemg", "cannotchangestatus");
        }
        if (!newstatus.equals(""))
        {
            String oldstatus = getString("status");
            //给当前jpo赋值
            setValue("STATUS", newstatus);
            
            IJpoSet statusset = getJpoSet("FAILURECODESTATUSHISTORY");
            IJpo statusnew = statusset.addJpo();
            statusnew.setValue("failurecode", getString("failurecode"), 11L);
            statusnew.setValue("siteid", "ELEC", 11L);
            statusnew.setValue("orgid", "CRRC", 11L);
            statusnew.setValue("changedate", MroServer.getMroServer().getDate(), 11L);
            statusnew.setValue("status", oldstatus, 11L);
            statusnew.setValue("newstatus", newstatus, 11L);
            statusnew.setValue("reason", reason, 11L);
            statusnew.setValue("changeby", getUserInfo().getPersonId(), 11L);
            statusnew.setFlag(GWConstant.S_READONLY, true);
        }
        else
        {
            throw new AppException("custinfo", "cannotnull");
        }
    }
    
}
