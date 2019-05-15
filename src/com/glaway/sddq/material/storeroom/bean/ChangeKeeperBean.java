package com.glaway.sddq.material.storeroom.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 库房库管员变更DataBean
 * 
 * @author  qhsong
 * @version [GlawayMro4.0, 2017-11-1]
 * @since   [GlawayMro4.0/库房]
 */
public class ChangeKeeperBean extends DataBean
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
        try
        {
            String newkeeper = mr.getString("NEWKEEPER");
            String keeper = mr.getString("KEEPER");
            //排除库管员相同的情况
            if (StringUtil.isNotEqual(keeper, newkeeper))
            {
                addKeeperHistory(mr);
                mr.setValue("KEEPER", newkeeper, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                getAppBean().SAVE();
                this.reloadPage();
            }
            else
            {
                throw new AppException("storeroom", "samekeeper");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * 
     * 添加库管员变更历史
     * @param mr 库房的jpo
     * @throws MroException
     *
     */
    public void addKeeperHistory(IJpo mr)
        throws MroException
    {
        IJpoSet khset = mr.getJpoSet("LOCKEEPERHISTORY");
        IJpo khjpo = khset.addJpo();
        khjpo.setValue("LOCATION", mr.getString("LOCATION"), 11L);
        khjpo.setValue("NEWKEEPER", mr.getString("NEWKEEPER"), 11L);
        khjpo.setValue("OLDKEEPER", mr.getString("KEEPER"), 11L);
        khjpo.setValue("MEMO", mr.getString("MEMO"), 11L);
    }
}
