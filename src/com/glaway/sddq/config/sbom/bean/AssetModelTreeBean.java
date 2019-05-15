/*
 * 文 件 名:  AssetModelTreeBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  装备型号管理结构树Bean
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-25
 */
package com.glaway.sddq.config.sbom.bean;

import java.io.IOException;
import java.util.Map;

import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.page.control.TreeNode;

/**
 * 装备型号管理结构树Bean
 * 
 * @author  hyhe
 * @version  [版本号, 2016-4-25]
 * @since  [产品/模块版本]
 */
public class AssetModelTreeBean extends TreeBean
{
    
    @Override
    public int clickNode(TreeNode treeNode)
        throws MroException, IOException
    {
        if (treeNode != null && ("ASSET").equals(treeNode.getJpo().getString("ASSETTMPLEVEL")))
        {
            this.getPage().getControlByXmlId("13753201644523").show();
            this.getPage().getControlByXmlId("1467202192536").hide();
        }
        else if(treeNode != null && "SYSTEM".equals(treeNode.getJpo().getString("ASSETTMPLEVEL")))
        {
            this.getPage().getControlByXmlId("1467202192536").show();
            this.getPage().getControlByXmlId("13753201644523").hide();
        }else{
        	this.getPage().getControlByXmlId("13753201644523").show();
            this.getPage().getControlByXmlId("1467202192536").hide();
        }
        return super.clickNode(treeNode);
    }
    
    private int getLevel(IJpo jpo)
    {
        IJpo rootJpo = getAppBean().getJpo();
        int level = 0;
        
        while (jpo != null && jpo.getParent() != null && jpo != rootJpo)
        {
            level = level + 1;
            jpo = jpo.getParent();
            getLevel(jpo);
        }
        return level;
    }
    
    @Override
    public String getNodeIcon(IJpo jpo, Map<String, String> node)
    {
        String icon = "";
        int count = getLevel(jpo);
        icon = "treenodeicon/tree_icon_" + count + ".png";
        return icon;
    }
    
	@Override
	public boolean collapseNode(TreeNode treeNode) throws MroException,
			IOException {
		if (getCurrNode() != treeNode)
        {
			clickNode(treeNode);
        }
		return super.collapseNode(treeNode);
	}

	@Override
	public boolean expandNode(TreeNode treeNode) throws MroException,
			IOException {
		if (getCurrNode() != treeNode)
        {
			clickNode(treeNode);
        }
		return super.expandNode(treeNode);
	}
    
}
