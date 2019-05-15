package com.glaway.sddq.config.csbom.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.control.TreeNode;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * @ClassName AddCxDataBean
 * @Description 整车SBOM添加车厢DataBean
 * @author public2175
 * @Date 2018-8-23 下午9:55:00
 * @version 1.0.0
 */
public class AddCxDataBean extends DataBean {

    @Override
    public int execute() throws MroException, IOException {

        DataBean databean = this.getDataBean("1375344856389");
        if (databean != null) {
            super.checkSave();
            TreeBean sbomTreeBean = (TreeBean) databean;
            TreeNode treenode = sbomTreeBean.getCurrNode();
            if (treenode != null && treenode.getJpo() != null) {
                String assetlevel = treenode.getJpo().getString("assetcslevel");
                if ("ASSET".equals(assetlevel)) {
                    String toAncestor = treenode.getJpo().getString("ancestor");
                    String toParent = treenode.getJpo().getString("assetcsnum");
                    MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultOrg("CRRC");
                    MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultSite("ELEC");
                    IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETCS", "1=2");
                    IJpo jpo = jposet.addJpo();
                    jpo.setValue("itemnum", SddqConstant.CAR_ITEMNUM, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("ancestor", toAncestor, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("parent", toParent, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("assetcslevel", SddqConstant.CAR_ASSETLEVEL, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("carriagenum", this.getJpo().getString("CARRIAGENUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("remark", this.getJpo().getString("memo"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jposet.save();
                } else {
                    throw new AppException("asset", "noSelectAsset");
                }
            }
        }
        return super.getAppBean().SAVE();
    }
}
