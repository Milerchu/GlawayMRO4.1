package com.glaway.sddq.config.zcconfig.bean;

import io.netty.util.internal.StringUtil;

import java.io.IOException;
import java.util.Map;

import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.page.control.TreeNode;

/**
 * 初始配置treeBean
 * 
 * @author hyhe
 * @version [版本号, 2018-4-25]
 * @since [产品/模块版本]
 */
public class AssetSsTreeBean extends TreeBean {

    @Override
    public int clickNode(TreeNode treeNode) throws MroException, IOException {
        PageControl ctrl = null;
        if (treeNode != null
                && ("ASSET".equals(treeNode.getJpo().getString("ASSETLEVEL")) || StringUtil.isNullOrEmpty(treeNode
                        .getJpo().getString("ASSETLEVEL")))) {
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
            // ctrl = getPage().getControlByXmlId("13753386188706");
            // if (ctrl != null)
            // ctrl.show();
        } else {
            // 结构
            ctrl = getPage().getControlByXmlId("1467204293642");
            if (ctrl != null)
                ctrl.show();
            ctrl = getPage().getControlByXmlId("13753448564042");
            if (ctrl != null)
                ctrl.hide();
            ctrl = getPage().getControlByXmlId("1535795246915");
            if (ctrl != null)
                ctrl.hide();
            // 选择产品按钮
            // ctrl = getPage().getControlByXmlId("13753386188706");
            // if (ctrl != null)
            // ctrl.hide();
        }
        refreshPage();
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
}
