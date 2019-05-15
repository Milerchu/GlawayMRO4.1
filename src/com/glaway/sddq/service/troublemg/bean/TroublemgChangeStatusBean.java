package com.glaway.sddq.service.troublemg.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.service.troublemg.data.Troublemg;

/**
 * 
 * 项目问题跟踪状态变更dialog databean
 * 
 * @author  hzhu
 * @version  [GlawayMro4.0, 2018-1-25]
 * @since  [GlawayMro4.0/模块版本]
 */
public class TroublemgChangeStatusBean extends DataBean
{
    @Override
    public int execute()
        throws MroException
    {
        IJpo mr = getAppBean().getJpo();
        //列表数据为空时
        if (mr == null)
        {
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
        Troublemg tm = (Troublemg)mr;
        Date curDate = MroServer.getMroServer().getDate();
        checkSave();
        
        tm.changestatus(getString("status"), curDate, getString("memo"));
        try
        {
            getAppBean().SAVE();
            this.reloadPage();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 1;
    }
}
