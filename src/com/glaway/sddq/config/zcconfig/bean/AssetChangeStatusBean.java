/*
 * 文 件 名:  AssetChangeStatusBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  实时台帐变更状态
 * 修 改 人:  hyhe
 * 修改时间:  2016-5-10
 */
package com.glaway.sddq.config.zcconfig.bean;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.config.zcconfig.data.Asset;

/**
 * 实时台帐变更状态
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-5-10]
 * @since  [MRO4.0/实时台帐]
 */
public class AssetChangeStatusBean extends DataBean
{
    
    @Override
    public int execute()
        throws MroException, IOException
    {
        super.checkSave();
        String newStatus = this.getString("newstatus");
        String oldStatus = this.getString("status");
        if (newStatus != null && oldStatus != null && oldStatus.equals(newStatus))
        {
            throw new MroException("assettmp", "statusnovalid");
        }
        if (!StringUtils.isEmpty(newStatus))
        {
            Asset asset = (Asset)this.getAppBean().getJpo();
            asset.changeStatus(asset.getString("status"),
                newStatus,
                getString("NP_STATUSMEMO"),
                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            this.getAppBean().getJpo().setValue("status", newStatus);
        }
        else
        {
            throw new MroException("assettmp", "statusnovalid");
        }
//        this.getAppBean().SAVE();
        return super.execute();
    }
}
