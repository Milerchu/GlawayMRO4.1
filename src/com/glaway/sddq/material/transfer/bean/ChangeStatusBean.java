package com.glaway.sddq.material.transfer.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.material.transfer.data.Transfer;

/**
 * 
 * 调拨单变更状态dataBean
 * 
 * @author  qhsong
 * @version [GlawayMro4.0, 2017-11-8]
 * @since   [GlawayMro4.0/调拨单]
 */
public class ChangeStatusBean extends DataBean
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
        Transfer ct = (Transfer)mr;
        checkSave();
        
        if (ct != null)
        {
            ct.changestatus(getString("CHANGESTATUS"), getString("STATUSMEMO"));
        }
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
