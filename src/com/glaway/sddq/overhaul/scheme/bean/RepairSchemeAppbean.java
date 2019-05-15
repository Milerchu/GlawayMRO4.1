package com.glaway.sddq.overhaul.scheme.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 检修方案AppBean
 * 
 * @author  hyhe
 * @version  [版本号, 2017-10-18]
 * @since  [产品/模块版本]
 */
public class RepairSchemeAppbean extends AppBean
{
    
    /**
     * 变更状态前判断
     */
    public int STATUS()
        throws MroException, IOException
    {
        IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("REPAIRPLANS");
        jposet.setUserWhere("SCHEMENUM='" + this.getString("SCHEMENUM") + "'");
        if (jposet != null && jposet.count() > 0)
        {
            throw new AppException("REPAIRPLANS", "isForSche");
        }
        else
        {
            return GWConstant.ACCESS_SAMEMETHOD;
        }
        
    }
}
