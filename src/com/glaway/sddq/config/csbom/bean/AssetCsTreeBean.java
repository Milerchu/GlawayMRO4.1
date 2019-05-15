/*
 * 文 件 名:  AssetCsTreeBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  整车SBOM 结构树Bean
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-28
 */
package com.glaway.sddq.config.csbom.bean;

import java.io.IOException;
import java.util.Map;

import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.page.control.TreeNode;

/**
 * 整车SBOM 结构树Bean
 * 
 * @author hyhe
 * @version [MRO4.0, 2016-4-28]
 * @since [MRO4.0/模块版本]
 */
public class AssetCsTreeBean extends TreeBean {

    @Override
    public int clickNode(TreeNode treeNode) throws MroException, IOException {
        if (treeNode != null && treeNode.getJpo() != null
                && ("ASSET").equals(treeNode.getJpo().getString("ASSETCSLEVEL"))) {
            this.getPage().getControlByXmlId("13753448564042").show();
            this.getPage().getControlByXmlId("1467204031612").hide();
            // this.getPage().getControlByXmlId("13753386188706").show();
            getPage().getControlByXmlId("1526365463241").hide();// 车厢节点的基本信息
            // this.getPage().getControlByXmlId("13753386188542").hide();
            this.getPage().getControlByXmlId("1525744279000").show();
            this.getPage().getControlByXmlId("1525770540757").hide();
            this.getPage().getControlByXmlId("13722326580184").hide();
        } else if (treeNode != null && treeNode.getJpo() != null
                && ("CAR").equals(treeNode.getJpo().getString("ASSETCSLEVEL"))) {
            this.getPage().getControlByXmlId("13753448564042").hide();
            getPage().getControlByXmlId("1526365463241").show();// 车厢节点的基本信息
            this.getPage().getControlByXmlId("1467204031612").hide();
            // this.getPage().getControlByXmlId("13753386188706").show();
            // this.getPage().getControlByXmlId("13753386188542").show();
            this.getPage().getControlByXmlId("1525744279000").hide();
            this.getPage().getControlByXmlId("1525770540757").show();
            this.getPage().getControlByXmlId("13722326580184").show();
        } else if (treeNode != null && treeNode.getJpo() != null
                && ("SYSTEM").equals(treeNode.getJpo().getString("ASSETCSLEVEL"))) {
            this.getPage().getControlByXmlId("1467204031612").show();
            this.getPage().getControlByXmlId("1526365463241").hide();
            this.getPage().getControlByXmlId("13753448564042").hide();
            // this.getPage().getControlByXmlId("13753386188706").hide();

            // this.getPage().getControlByXmlId("13753386188542").hide();
            this.getPage().getControlByXmlId("1525744279000").hide();
            this.getPage().getControlByXmlId("1525770540757").show();
            this.getPage().getControlByXmlId("13722326580184").show();
        } else {
            this.getPage().getControlByXmlId("13753448564042").show();
            this.getPage().getControlByXmlId("1526365463241").hide();
            this.getPage().getControlByXmlId("1467204031612").hide();
            // this.getPage().getControlByXmlId("13753386188706").show();

            // this.getPage().getControlByXmlId("13753386188542").hide();
            this.getPage().getControlByXmlId("1525744279000").show();
            this.getPage().getControlByXmlId("1525770540757").hide();
            this.getPage().getControlByXmlId("13722326580184").hide();
        }
        return super.clickNode(treeNode);
    }

    private int getLevel(IJpo jpo) {
        IJpo rootJpo = getAppBean().getJpo();
        int level = 0;

        while (jpo != null && jpo.getParent() != null && jpo != rootJpo) {
            level = level + 1;
            jpo = jpo.getParent();
            getLevel(jpo);
        }
        return level;
    }

    @Override
    public String getNodeIcon(IJpo jpo, Map<String, String> node) {
        String icon = "";
        int count = getLevel(jpo);
        icon = "treenodeicon/tree_icon_" + count + ".png";
        return icon;
    }

    @Override
    public boolean collapseNode(TreeNode treeNode) throws MroException, IOException {
        if (getCurrNode() != treeNode) {
            clickNode(treeNode);
        }
        return super.collapseNode(treeNode);
    }

    @Override
    public boolean expandNode(TreeNode treeNode) throws MroException, IOException {
        if (getCurrNode() != treeNode) {
            clickNode(treeNode);
        }
        return super.expandNode(treeNode);
    }
}
