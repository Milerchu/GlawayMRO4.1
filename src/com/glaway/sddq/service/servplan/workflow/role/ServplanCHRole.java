package com.glaway.sddq.service.servplan.workflow.role;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.RoleCustomClass;

/**
 * 
 * 服务计划编制角色类
 * 
 * @author  hzhu
 * @version  [MRO4.0, 2018-1-25]
 * @since  [MRO4.0/服务计划]
 */
public class ServplanCHRole implements RoleCustomClass
{
    
    @Override
    public IJpoSet executeCustomRole(IJpo curjpo, String parameter)
        throws MroException
    {
        //获取项目团队管理成员名单
        IJpoSet prjtTeamMemberSet = curjpo.getJpoSet("PROJECTTEAMEMBER");
        String persons = "";
        //拼接人员id字符串
        for (int i = 0; i < prjtTeamMemberSet.count(); i++)
        {
            IJpo teamMember = prjtTeamMemberSet.getJpo(i);
            persons += "'" + teamMember.getString("PERSONID") + "',";
        }
        //去除末尾的","
        persons = persons.substring(0, persons.length() - 1);
        
        //返回人员表
        IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON", curjpo.getUserServer());
        personSet.setUserWhere("personid in (" + persons + ")");
        personSet.reset();
        
        return personSet;
    }
    
}
