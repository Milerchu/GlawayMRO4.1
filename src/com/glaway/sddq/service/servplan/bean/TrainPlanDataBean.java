package com.glaway.sddq.service.servplan.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <培训计划绑定类>
 * 
 * @author  ygao
 * @version  [版本号, 2017-10-19]
 * @since  [产品/模块版本]
 */
public class TrainPlanDataBean extends DataBean
{
    
    public int confirminfo()
        throws MroException, IOException
    {
        //获取项目成员列表
        IJpoSet prjmemberSet = this.getParent().getJpo().getJpoSet("PROJECTTEAMEMBER");
        if (!prjmemberSet.isEmpty())
        {
            prjmemberSet.setUserWhere("projectrole='售后培训经理'");
            prjmemberSet.reset();
            
            //判断当前登录人是不是服务计划中的培训经理角色
            String trainmangr = prjmemberSet.getJpo().getString("personid");
            if (getJpo().getUserInfo().getLoginID().equalsIgnoreCase(trainmangr))
            {
                getJpo().setValue("ACTCOMPLETEDATE", MroServer.getMroServer().getDate());
                getJpo().setValue("CONFIRMOR", this.getUserInfo().getUserName());
                getJpo().setValue("STATUS", "已完成");
                this.reloadPage();
            }
            else
            {
                throw new MroException("", "当前用户无权操作！");
            }
        }
        
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
}
