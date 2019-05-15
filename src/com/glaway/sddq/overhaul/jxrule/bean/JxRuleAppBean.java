package com.glaway.sddq.overhaul.jxrule.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 检修规程AppBean
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-11]
 * @since  [产品/模块版本]
 */
public class JxRuleAppBean extends AppBean
{

    @Override
    public int DELETE()
        throws MroException, IOException
    {
        //判断是否被引用
        IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("REPAIRSCHEME");
        jposet.setUserWhere("JXNUM='"+this.getJpo().getString("JXNUM")+"'");
        jposet.reset();
        if(jposet != null && jposet.count() > 0){
            throw new AppException("jxrule", "nodelete");
        }else{
            super.DELETE();
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
}
