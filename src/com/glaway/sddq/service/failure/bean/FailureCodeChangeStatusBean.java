package com.glaway.sddq.service.failure.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.service.failure.data.FailureCode;

/**
 * 
 * 故障字典状态变更databean
 * 
 * @author  hzhu
 * @version  [MRO4.1, 2018-5-18]
 * @since  [MRO4.1/故障字典]
 */
public class FailureCodeChangeStatusBean extends DataBean
{
    @Override
    public int execute()
        throws MroException, IOException
    {
        IJpo mr = getAppBean().getJpo();
        //列表数据为空时
        if (mr == null)
        {
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
        FailureCode ct = (FailureCode)mr;
        checkSave();
        
        ct.changestatus(getString("status"), getString("reason"));
        try
        {
            getAppBean().SAVE();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
}
