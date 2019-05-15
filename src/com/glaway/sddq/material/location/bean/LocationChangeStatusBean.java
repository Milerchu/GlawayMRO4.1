/*
 * 文 件 名:  LocationChangeStatusBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yyang
 * 修改时间:  2016-5-11
 */
package com.glaway.sddq.material.location.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 修改位置状态
 * @author  yyang
 * @version  [版本号, 2016-5-11]
 * @since  [产品/模块版本]
 */
public class LocationChangeStatusBean extends DataBean
{
    /**
     * 执行状态变更
     * @return
     * @throws MroException
     * @throws IOException
     */
    @Override
    public int execute()
        throws MroException, IOException
    {
        checkSave();
        IJpo appjpo = getAppBean().getJpo();
        if (appjpo == null)
        {
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
        String changestatus = getString("CHANGESTATUS");
        String memo = getString("memo");
        String status = appjpo.getString("status");
        if (StringUtil.isEqual(changestatus, status))
        {
            throw new MroException("companies", "SameStatus");
        }
        if (null != changestatus && !"".equals(changestatus))
        {
            java.util.Date curDate = MroServer.getMroServer().getDate();
            
            ////构建状态历史变更对象 
            IJpoSet hisJpoSet = appjpo.getJpoSet("$temp_locstatus", "LOCSTATUS", "1=0");
            IJpo hisJpo = hisJpoSet.addJpo();
            hisJpo.setValue("location", appjpo.getString("location"));
            hisJpo.setValue("changedate", curDate);
            hisJpo.setValue("status", changestatus);
            hisJpo.setValue("changeby", appjpo.getUserInfo().getLoginID());
            hisJpo.setValue("memo", memo);
            hisJpo.setValue("orgid", appjpo.getSiteOrg()[1]);
            hisJpo.setValue("siteid", appjpo.getSiteOrg()[0]);
            //            hisJpoSet.save();
            appjpo.setValue("status", changestatus, GWConstant.P_NOVALIDATION_AND_NOACTION);
            reloadPage();
        }
        else
        {
            throw new MroException("locations", "invalidstatus");
        }
        this.getAppBean().SAVE();
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * @return
     * @throws IOException
     * @throws MroException
     */
    @Override
    public int dialogcancel()
        throws IOException, MroException
    {
        IJpo appjpo = getAppBean().getJpo();
        if (appjpo != null && appjpo.getField("CHANGESTATUS") != null)
        {
            appjpo.getField("CHANGESTATUS").clearError();
        }
        return super.dialogcancel();
    }
}
