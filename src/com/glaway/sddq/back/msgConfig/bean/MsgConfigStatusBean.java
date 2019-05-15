package com.glaway.sddq.back.msgConfig.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.back.msgConfig.data.MsgConfig;

/**
 * 
 * 消息配置状态变更类
 * 
 * @author  bchen
 * @version  [版本号, 2018-2-8]
 * @since  [产品/模块版本]
 */
public class MsgConfigStatusBean extends DataBean
{
    @Override
    public int execute()
        throws MroException
    {
        IJpo mr = getAppBean().getJpo();
        if (mr == null)
        {
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
        checkSave();
        MsgConfig ct = (MsgConfig)mr;
        String oldStatus = mr.getString("status");
        String newStatus = this.getString("status");
        if (oldStatus.equals(newStatus))
        {
            throw new AppException("msgconfig", "isStatus");
        }
        if (oldStatus.equals("已激活"))
        {
            if (newStatus.equals("草稿"))
            {
                IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("MSGMANAGE");
                jposet.setUserWhere("MSGNUM='" + mr.getString("MSGNUM") + "'");
                jposet.reset();
                if (jposet != null && jposet.count() > 0)
                {
                    throw new AppException("msgmanage", "isForSche");
                }
                else
                {
                    return GWConstant.ACCESS_SAMEMETHOD;
                }
            }
        }
        ct.changeStatus(getString("status"), getString("memo"));
        try
        {
            getAppBean().SAVE();
            this.reloadPage();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
}
