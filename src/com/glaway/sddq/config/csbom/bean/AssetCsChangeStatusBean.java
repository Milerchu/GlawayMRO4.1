/*
 * 文 件 名:  AssetCsChangeStatusBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  出所台帐改变状态
 * 修 改 人:  hyhe
 * 修改时间:  2016-5-4
 */
package com.glaway.sddq.config.csbom.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.config.csbom.data.AssetCs;

/**
 * 整车SBOM 变更状态
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-5-4]
 * @since  [MRO4.0/出所台帐]
 */
public class AssetCsChangeStatusBean extends DataBean
{
    @Override
    public int execute()
        throws MroException, IOException
    {
        super.checkSave();
        AssetCs assetcs = (AssetCs)this.getAppBean().getJpo();
        String newStatus = getString("NEWSTATUS");
        if (StringUtil.isStrEmpty(newStatus) || newStatus.equals(this.getAppBean().getJpo().getString("STATUS")))
        {
            throw new MroException("assettmp", "statusnovalid");
        }
        assetcs.changeStatus(assetcs.getString("status"),
            newStatus,
            getString("NP_STATUSMEMO"),
            GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        if(newStatus.equals("变更")){
            String version = getNewVersion(assetcs.getString("VERSION"));
            assetcs.setValue("version", version,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        }
        this.getAppBean().getJpo().setValue("STATUS", newStatus, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
      
        this.getAppBean().SAVE();
        //变更状态之后,会选中顶层节点,但是右侧的列表没有刷新
        DataBean databean = this.getDataBean("1375344856389");
    	if(databean != null){
    		TreeBean sbomTreeBean = (TreeBean)databean ;
        	sbomTreeBean.clickNode(sbomTreeBean.getCurrNode());
    	}
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    public static String getNewVersion(String oldVersion){
    	String version = oldVersion.substring(3);
    	String pre = oldVersion.substring(0,3);
    	int ver = Integer.valueOf(version)+1;
    	System.out.println(ver);
    	return pre+ver;
    }
}
