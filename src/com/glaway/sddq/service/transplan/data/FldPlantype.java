package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <改造/验证计划 计划类型字段类>
 * 
 * @author  hzhu
 * @version  [MRO4.1, 2018-5-16]
 * @since  [MRO4.1/模块版本]
 */
public class FldPlantype extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -7569004307933327191L;
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        String appname = getJpo().getAppName();
        IJpoSet domainSet = null;
        if (StringUtil.isStrNotEmpty(appname))
        {
            if (appname.equalsIgnoreCase("transplan"))
            {//改造计划
            
                domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN", "domainid='TRANSPLANTYPE'");
                domainSet.reset();
            }
            else
            {//验证计划
                domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN", "domainid='VALIPLANTYPE'");
                domainSet.reset();
            }
        }
        
        return domainSet;
    }
}
