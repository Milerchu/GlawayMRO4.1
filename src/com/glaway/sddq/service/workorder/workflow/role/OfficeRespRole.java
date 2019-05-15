package com.glaway.sddq.service.workorder.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;

/**
 * 
 * 工单-所属办事处责任人 角色定制类
 * 
 * @author  hzhu
 * @version  [MRO4.1, 2018-5-25]
 * @since  [MRO4.1/模块版本]
 */
public class OfficeRespRole implements RoleCustomClass
{
    
    @Override
    public IJpoSet executeCustomRole(IJpo curjpo, String parameter)
        throws MroException
    {
        IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON", curjpo.getUserServer());
        personSet.setUserWhere("personid='" + curjpo.getString("WHICHOFFICE.OWNER") + "'");
        personSet.reset();
        
        return personSet;
    }
    
}
