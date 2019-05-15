package com.glaway.sddq.service.servplan.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;

/**
 * 
 * 服务计划审核角色类
 * 
 * @author  hzhu
 * @version  [MRO4.0, 2018-2-1]
 * @since  [MRO4.0/模块版本]
 */
public class ServplanAuditRole implements RoleCustomClass
{
    
    @Override
    public IJpoSet executeCustomRole(IJpo curjpo, String parameter)
        throws MroException
    {
        //获取项目经理id
        String managerid = curjpo.getString("PRJMANAGER");
        
        IJpoSet personSet = MroServer.getMroServer().getSysJpoSet("SYS_PERSON", "personid = '" + managerid + "'");
        
        return personSet;
    }
}
