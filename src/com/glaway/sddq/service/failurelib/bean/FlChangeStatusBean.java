package com.glaway.sddq.service.failurelib.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.service.failurelib.data.Failurelib;

/**
 * 
 * 故障管理状态变更 bean类
 * 
 * @author  hzhu
 * @version  [GlawayMro4.0, 2017-2-10]
 * @since  [GlawayMro4.0/系统]
 */
public class FlChangeStatusBean extends DataBean
{
    public int execute()
        throws MroException, IOException
    {
        //新状态
        String newStatus = getString("status");
        String memo = getString("memo");
        
        IJpo mr = this.parent.getJpo();
        Failurelib fl = (Failurelib)mr;
        Date curDate = MroServer.getMroServer().getDate();
        fl.changeStatus(fl.getString("STATUS"), newStatus, curDate, memo);
        
        this.getAppBean().save();
        this.getAppBean().reloadPage();
        
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
}
