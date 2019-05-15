/*
 * 文 件 名:  AssetModelChangeStatus.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  装备型号管理 变更状态
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-26
 */
package com.glaway.sddq.config.sbom.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.control.Button;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.config.sbom.data.AssetModel;

/**
 * 装备型号管理(ASSETTMP) 变更状态
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-4-26]
 * @since  [MRO4.0/装备型号管理]
 */
public class AssetModelChangeStatus extends DataBean
{
    @Override
    public int execute()
        throws MroException, IOException
    {
        super.checkSave();
        AssetModel assetModel = (AssetModel)this.getAppBean().getJpo();
        if (assetModel == null)
        {
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
        String newStatus = assetModel.getString("NEWSTATUS");
        if (StringUtil.isStrEmpty(newStatus) || newStatus.equals(this.getAppBean().getJpo().getString("Status")))
        {
            throw new MroException("assettmp", "statusnovalid");
        }
        
        String versionuser = this.getUserInfo().getPersonId();
        this.getAppBean().getJpo().setValue("VERSIONUSER", versionuser, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        this.getAppBean().getJpo().setValue("VERSIONDATE",
            MroServer.getMroServer().getDate(),
            GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        
        assetModel.changeStatus(assetModel.getString("status"),
            newStatus,
            getString("NP_STATUSMEMO"),
            GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        
        if(newStatus.equals("变更")){
            String version = getNewVersion(assetModel.getString("VERSION"));
            assetModel.setValue("version", version,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        }
        
        //设置新建行按钮
        Button b = ((Button)this.getPage().getControlByXmlId("13753201645612"));
        if (b != null)
        {
            if (newStatus.equals("可用"))
            {
               b.setDisable(true);
            }
            else
            {
                b.setDisable(false);
            }
            b.loadData();
        }
        
        this.getAppBean().getJpo().setValue("STATUS", newStatus, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        this.getAppBean().SAVE();
        
        DataBean databean = this.getDataBean("1461565595904");
    	if(databean != null){
    		TreeBean sbomTreeBean = (TreeBean)databean ;
        	sbomTreeBean.clickNode(sbomTreeBean.getCurrNode());
    	}
        
        return GWConstant.NOACCESS_SAMEMETHOD;
    }

    @Override
    public int dialogcancel()
        throws IOException, MroException
    {
        getAppBean().setUidBefAddEdit();
        getAppBean().resetAndFixPos();
        dialogCloseNoRefresh();
        return 1;
    }
    
    /**
     * 
     * 变更时获取小版本
     * @param oldVersion
     * @return [参数说明]
     *
     */
    public static String getNewVersion(String oldVersion){
    	String version = oldVersion.substring(3);
    	String pre = oldVersion.substring(0,3);
    	int ver = Integer.valueOf(version)+1;
    	System.out.println(ver);
    	return pre+ver;
    }
    
    public static String getNextMaxVersion(String itemnunm) throws MroException{
    	
    	IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETTMP", "version=(select max(version) from ASSET where itemnum='"+itemnunm+"' and assettmplevel='ASSET') and itemnum='"+itemnunm+"' and assettmplevel='ASSET'");
    	if(jposet != null && jposet.count() > 0){
    		String version = jposet.getJpo(0).getString("version");
    		if(!StringUtil.isStrEmpty(version)){
    			String curVersion = String.valueOf(version);
            	String pre = curVersion.substring(1,2);
            	int ver = Integer.valueOf(pre)+1;
            	return "V"+ver+".0";
    		}else{
    			return "V1.0";
    		}
    	}else{
    		return "V1.0";
    	}
    }
    
    public  static void main(String[] args){
    	System.out.print(getNewVersion("V1.0"));
    }
}
