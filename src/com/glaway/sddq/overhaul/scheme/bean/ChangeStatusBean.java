package com.glaway.sddq.overhaul.scheme.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.overhaul.scheme.data.RepairScheme;
/**
 * 
 * 检修方案变更状态dataBean
 * 
 * @author  hyhe
 * @version  [版本号, 2017-10-18]
 * @since  [产品/模块版本]
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
        RepairScheme ct = (RepairScheme)mr;
        checkSave();
        
        ct.changestatus(getString("status"), getString("memo"));
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
