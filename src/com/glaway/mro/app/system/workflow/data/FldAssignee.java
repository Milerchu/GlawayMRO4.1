/*
 * 文 件 名:  FldAssignee.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  工作流任务重新分配字段类
 * 修 改 人:  hyhe
 * 修改时间:  2016-5-20
 */
package com.glaway.mro.app.system.workflow.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 工作流任务重新分配字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2016-5-20]
 * @since  [产品/模块版本]
 */
public class FldAssignee extends JpoField
{
    
    /**
     * 默认序列化ID
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        super.init();
        this.setLookupMap(new String[] {"ASSIGNEE"}, new String[] {"PERSONID"});
    }
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        //过滤掉三员管理用户以及GWADMIN用户
        //        String where =
        //            "STATUS ='活动' and personid not in(select personid from sys_user where userid in "
        //                + " (select userid from sys_groupuser, sys_group where sys_groupuser.groupname = sys_group.groupname "
        //                + " and sys_group.groupname in('GWADMIN','MROAUDADMIN','MROSECADMIN','MROSYSADMIN')))";
        IJpoSet personJpoSet = this.getUserServer().getJpoSet("SYS_PERSON", "STATUS ='活动'");
        personJpoSet.reset();
        
        return personJpoSet;
    }
}
