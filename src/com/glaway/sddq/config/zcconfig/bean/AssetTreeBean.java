/*
 * 文 件 名:  AssetTreeBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  hyhe
 * 修改时间:  2016-5-6
 */
package com.glaway.sddq.config.zcconfig.bean;

import io.netty.util.internal.StringUtil;

import java.io.IOException;
import java.util.Map;

import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.page.control.TreeNode;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 实时台帐 结构树 Bean
 * 
 * @author hyhe
 * @version [MRO4.0, 2016-5-6]
 * @since [MRO4.0/实时台帐]
 */
public class AssetTreeBean extends TreeBean {

    @Override
    public int clickNode(TreeNode treeNode) throws MroException, IOException {
        PageControl ctrl = null;
        if ("SSCONFIG".equals(this.getAppName()) || "CXCONFIG".equals(this.getAppName())) {
            if (treeNode != null && treeNode.getJpo() != null && ("ASSET".equals(treeNode.getJpo().getString("ASSETLEVEL"))
                    || StringUtil.isNullOrEmpty(treeNode.getJpo().getString("ASSETLEVEL")))) {
                // 结构
                ctrl = getPage().getControlByXmlId("13753448564042");
                if (ctrl != null)
                    ctrl.show();
                ctrl = getPage().getControlByXmlId("1535795246915");
                if (ctrl != null)
                    ctrl.show();
                ctrl = getPage().getControlByXmlId("1467204293642");
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("13753386188706");
                if (ctrl != null)
                    ctrl.show();
            } else {
                // 结构
                ctrl = getPage().getControlByXmlId("1467204293642");
                if (ctrl != null)
                    ctrl.show();
                ctrl = getPage().getControlByXmlId("1535795246915");
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("13753448564042");
                if (ctrl != null)
                    ctrl.hide();
                // 选择产品按钮
                ctrl = getPage().getControlByXmlId("13753386188706");
                if (ctrl != null)
                    ctrl.hide();
            }
            refreshPage();
        }
        if (this.getAppName() != null && this.getAppName().equals("ZCCONFIG")) {
            if (treeNode != null && ("ASSET").equals(treeNode.getJpo().getString("ASSETLEVEL"))) {
                // 结构
                ctrl = getPage().getControlByXmlId("13753448564042");// 顶层节点的基本信息
                if (ctrl != null)
                    ctrl.show();
                ctrl = getPage().getControlByXmlId("1526365463241");// 车厢节点的基本信息
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("1467204293642");// 子级节点的基本信息
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("13753386188542");// 产品列表
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("1527590193314");// 产品列表
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("1526289204161");// 添加车厢
                if (ctrl != null)
                    ctrl.show();
                ctrl = getPage().getControlByXmlId("1528545363690");// 子级耗损件列表
                if (ctrl != null)
                    ctrl.hide();
            } else if (treeNode != null && "CAR".equals(treeNode.getJpo().getString("ASSETLEVEL"))) {
                ctrl = getPage().getControlByXmlId("13753448564042");// 顶层节点的基本信息
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("1526365463241");// 顶层节点的基本信息
                if (ctrl != null)
                    ctrl.show();
                ctrl = getPage().getControlByXmlId("1467204293642");// 子级节点的基本信息
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("13753386188542");// 产品列表
                if (ctrl != null)
                    ctrl.show();
                ctrl = getPage().getControlByXmlId("1527590193314");// 产品列表
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("1526289204161");// 添加车厢
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("1528545363690");// 子级耗损件列表
                if (ctrl != null)
                    ctrl.show();
            } else if (treeNode != null && "SYSTEM".equals(treeNode.getJpo().getString("ASSETLEVEL"))) {
                ctrl = getPage().getControlByXmlId("13753448564042");// 顶层节点的基本信息
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("1526365463241");// 顶层节点的基本信息
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("1467204293642");// 子级节点的基本信息
                if (ctrl != null)
                    ctrl.show();
                ctrl = getPage().getControlByXmlId("1528545363690");// 子级耗损件列表
                if (ctrl != null)
                    ctrl.show();
                // 判断选择的节点是不是网络节点，如果是网络节点，则显示可选择产品序列号的列表
                IJpo jpo = treeNode.getJpo();
                if (jpo != null && !StringUtil.isNullOrEmpty(jpo.getString("itemnum"))
                        && ItemUtil.getItem(jpo.getString("itemnum")) != null
                        && ItemUtil.getItem(jpo.getString("itemnum")).getBoolean("ISIV")) {
                    ctrl = getPage().getControlByXmlId("13753386188542");// 产品列表
                    if (ctrl != null)
                        ctrl.show();
                    ctrl = getPage().getControlByXmlId("1527590193314");// 产品列表
                    if (ctrl != null)
                        ctrl.hide();
                } else {
                    ctrl = getPage().getControlByXmlId("13753386188542");// 产品列表
                    if (ctrl != null)
                        ctrl.hide();
                    ctrl = getPage().getControlByXmlId("1527590193314");// 产品列表
                    if (ctrl != null)
                        ctrl.show();
                }
                ctrl = getPage().getControlByXmlId("1526289204161");// 添加车厢
                if (ctrl != null)
                    ctrl.hide();
            } else {
                ctrl = getPage().getControlByXmlId("13753448564042");// 顶层节点的基本信息
                if (ctrl != null)
                    ctrl.show();
                ctrl = getPage().getControlByXmlId("1526365463241");// 顶层节点的基本信息
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("1467204293642");// 子级节点的基本信息
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("13753386188542");// 产品列表
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("1527590193314");// 产品列表
                if (ctrl != null)
                    ctrl.hide();
                ctrl = getPage().getControlByXmlId("1526289204161");// 添加车厢
                if (ctrl != null)
                    ctrl.show();
                ctrl = getPage().getControlByXmlId("1528545363690");// 子级耗损件列表
                if (ctrl != null)
                    ctrl.hide();
            }
            // refreshPage();
        }
        return super.clickNode(treeNode);
    }

    private void refreshPage() throws MroException {
        // String description = "子级";
        // if (getJpo() != null)
        // {
        // IJpo jpo = getJpo();
        // if (!getJpo().isNull("ITEM.DESCRIPTION"))
        // {
        // description = jpo.getString("ITEM.DESCRIPTION");
        // description = description.length() > 30 ? description.substring(0, 30) + "..." : description;
        // description = "“" + description + "”的子部件";
        // }
        // PageControl tablectrl = this.getPage().getControlByXmlId("13753386188542");
        // if (tablectrl != null)
        // {
        // tablectrl.setLabel(description);
        // try
        // {
        // tablectrl.getDataBean().reloadSelfAndSubs();
        // }
        // catch (IOException e)
        // {
        // e.printStackTrace();
        // }
        // }
        // }
    }

    @Override
    public String getNodeStyle(IJpo jpo, Map<String, String> node) {
        String style = "";
        try {
            if (jpo != null && jpo.getString("ASSETLEVEL").equals("SYSTEM")
                    && StringUtil.isNullOrEmpty(jpo.getString("SQN"))) {
                if (ItemUtil.getItem(jpo.getString("itemnum")) != null
                        && !ItemUtil.getItem(jpo.getString("itemnum")).getBoolean("ISIV")) {
                    return style = "{\"color\":\"red\"}";
                }
            }
        } catch (MroException e) {
            e.printStackTrace();
        }
        return style;
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
        int count = getLevel(jpo);
        String icon = "treenodeicon/tree_icon_" + count + ".png";
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
