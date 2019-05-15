package com.glaway.sddq.service.userinfo.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <用户资料绑定类>
 * 
 * @author  ygao
 * @version  [版本号, 2017-10-19]
 * @since  [产品/模块版本]
 */
public class UserInfoDataBean extends DataBean
{
    public int confirminfo()
        throws MroException, IOException
    {
        getJpo().setValue("ACTCOMPLETEDATE", MroServer.getMroServer().getDate());
        getJpo().setValue("CONFIRMOR", this.getUserInfo().getUserName());
        getJpo().setValue("STATUS", "已完成");
        this.reloadPage();
        //        getAppBean().SAVE();
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
}
