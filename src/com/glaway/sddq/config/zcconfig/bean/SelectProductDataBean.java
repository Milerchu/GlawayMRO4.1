package com.glaway.sddq.config.zcconfig.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.page.control.TreeNode;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * @ClassName SelectProductDataBean
 * @Description 选择产品
 * @author public2175
 * @Date 2018-8-10 上午11:21:34
 * @version 1.0.0
 */
public class SelectProductDataBean extends DataBean {

    @Override
    public int execute() throws MroException, IOException {
        
        DataBean databean = this.getDataBean("1375344856389");
        if (databean != null) {
            TreeBean sbomTreeBean = (TreeBean) databean;
            TreeNode treenode = sbomTreeBean.getCurrNode();
            if(treenode != null){
                String sqn = this.getJpo().getString("sqn");
                String assetnum = this.getJpo().getString("assetnum");
                System.out.println(sqn);
                
                //判断节点是不是顶层节点，如果是顶层节点，则弹出不可添加节点以及选择产品的提示信息
                String assetlevel = treenode.getJpo().getString("assetlevel");
                if("ASSET".equals(assetlevel)){
                    throw new AppException("asset", "selectAsset");
                }
                super.checkSave();
                //根据产品序列号去查结构信息
                //获取ASSET表中选中部件以及子部件,没有ANCESTOR
                MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultOrg("CRRC");
                MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultSite("ELEC");
                IJpoSet installMboset = MroServer.getMroServer().getSysJpoSet("ASSET");
                installMboset.setUserWhere("ancestor ='" + assetnum
                    + "'");
                installMboset.reset();
                //装机目标部件，可以装到除顶层节点外的任一节点上
                String toAncestor = treenode.getJpo().getString("ancestor");
                String toParent = treenode.getJpo().getString("assetnum");
                if (installMboset != null && installMboset.count() > 0)
                {
                    for (int index = 0; index < installMboset.count(); index++)
                    {
                        if (installMboset.getJpo(index).getString("ASSETNUM").equals(assetnum))
                        {
                            installMboset.getJpo(index).setValue("parent",
                                toParent,
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("assetlevel",
                                "SYSTEM",
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            
                            installMboset.getJpo(index).setValue("LINENUM",
                                this.getJpo().getString("LINENUM"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("RNUM",
                                this.getJpo().getString("RNUM"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("CONFIGNUM",
                                this.getJpo().getString("CONFIGNUM"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("LOC",
                                this.getJpo().getString("LOC"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("LOCDESC",
                                    this.getJpo().getString("LOCDESC"),
                                    GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("SOFTNAME",
                                this.getJpo().getString("SOFTNAME"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("SOFTVERSION",
                                this.getJpo().getString("SOFTVERSION"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("SOFTUPDATE",
                                this.getJpo().getDate("SOFTUPDATE"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("STATUS",
                                this.getJpo().getString("STATUS"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        }
                        installMboset.getJpo(index).setValue("ANCESTOR",
                            toAncestor,
                            GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        installMboset.getJpo(index).setValue("type", "2", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        installMboset.getJpo(index).setValue("LOCATION", "", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        installMboset.getJpo(index).setValue("STATUS", SddqConstant.UP_CAR_STATUS, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    }
                    //              toParentAsset.setValue("children", 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                }
                installMboset.save();
                //重新加载节点的子层节点
                sbomTreeBean.reloadNodeWithoutReset();
                showAssetNode();
            }else{
                throw new AppException("asset", "selectNoAsset");
            }
        }else{
            throw new AppException("asset", "selectNoAsset");
        }
        return super.execute();
    }
    
    /**
     * 
     * @Description 显示顶层节点
     * @throws IOException
     */
    public void showAssetNode() throws IOException{
        
        PageControl ctrl = null;
        ctrl = getPage().getControlByXmlId("13753448564042");//顶层节点的基本信息
        if (ctrl != null)
            ctrl.show();
        ctrl = getPage().getControlByXmlId("1526365463241");//车厢节点的基本信息
        if (ctrl != null)
            ctrl.hide();
        ctrl = getPage().getControlByXmlId("1467204293642");//子级节点的基本信息
        if (ctrl != null)
            ctrl.hide();
        ctrl = getPage().getControlByXmlId("13753386188542");//产品列表
        if (ctrl != null)
            ctrl.hide();
        ctrl = getPage().getControlByXmlId("1527590193314");//产品列表
        if (ctrl != null)
            ctrl.hide();
        ctrl = getPage().getControlByXmlId("1526289204161");//添加车厢
        if (ctrl != null)
            ctrl.show();
    }
}
