package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * <计划编号字段类>
 * 
 * @author  hzhu
 * @version  [MRO4.0, 2018-5-5]
 * @since  [MRO4.0/模块版本]
 */
public class FldPlannum extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -4307740551791800481L;
    
    @Override
    public void init()
        throws MroException
    {
        String appname = getJpo().getAppName();
        if ("SERVORDER".equalsIgnoreCase(appname) || "FAILUREORD".equalsIgnoreCase(appname))
        {//服务工单、故障工单
            setLookupMap(new String[] {"PLANNUM"}, new String[] {"SERVPLANNUM"});
        }
        else
        {//改造工单、验证工单
            setLookupMap(new String[] {"PLANNUM", "NOTICENUM"}, new String[] {"TRANSPLANNUM", "TRANSNOTICENUM"});
        }
        
    }
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        //应用名
        String appname = getJpo().getAppName();
        String tableName = "";
        String where = " 1 = 1 ";
        if ("SERVORDER".equalsIgnoreCase(appname) || "FAILUREORD".equalsIgnoreCase(appname))
        {//服务、故障工单
            tableName = "SERVPLAN";
            where += " and status = '已发布' ";
        }
        else if ("TRANSORDER".equalsIgnoreCase(appname))
        {//改造工单
            tableName = "TRANSPLAN";
            where += " and status in('执行中','已接收') and plantype<>'验证' ";
        }
        else
        {//验证工单
            tableName = "TRANSPLAN";
            where += " and status in('执行中','已接收') and plantype='验证'";
        }
        
        IJpoSet returnSet = MroServer.getMroServer().getJpoSet(tableName, getUserServer());
        returnSet.setUserWhere(where);
        returnSet.reset();
        
        return returnSet;
    }
    
    @Override
    public void action()
        throws MroException
    {
        if ("TRANSORDER".equalsIgnoreCase(getJpo().getAppName()))
        {
            String plannum = this.getInputMroType().asString();
            String models =
                MroServer.getMroServer()
                    .getSysJpoSet("TRANSPLAN", "TRANSPLANNUM='" + plannum + "'")
                    .getJpo()
                    .getString("TRANSMODELS");
            this.getJpo().setValue("MODELS", models);
        }
        super.action();
    }
}
