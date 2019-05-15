package com.glaway.sddq.config.zcconfig.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.page.control.TreeNode;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * @ClassName AddProductDataBean
 * @Description 新增节点
 * @author public2175
 * @Date 2018-8-10 上午11:23:44
 * @version 1.0.0
 */
public class AddProductDataBean extends DataBean {
    
    @Override
    public int execute() throws MroException, IOException {
        
        DataBean databean = this.getDataBean("1375344856389");
        if (databean != null) {
            TreeBean sbomTreeBean = (TreeBean) databean;
            TreeNode treenode = sbomTreeBean.getCurrNode();
            if(treenode != null){
                //判断节点是不是顶层节点，如果是顶层节点，则弹出不可添加节点以及选择产品的提示信息
                String assetlevel = treenode.getJpo().getString("assetlevel");
                if("ASSET".equals(assetlevel)){
                    throw new AppException("asset", "selectAsset");
                }
                super.checkSave();
                //装机目标部件，可以装到除顶层节点外的任一节点上
                String toAncestor = treenode.getJpo().getString("ancestor");
                String toParent = treenode.getJpo().getString("assetnum");
                
                String sqn = this.getJpo().getString("newsqn");//产品序列号
                String itemnum = this.getJpo().getString("itemnum");//物料编码
                String description = this.getJpo().getString("item.description");
                String loc =  this.getJpo().getString("loc");//位置号
                String locdesc =  this.getJpo().getString("locdesc");//功能位置描述
                String rnum = this.getJpo().getString("rnum");//位号
                String linenum = this.getJpo().getString("linenum");//行号
                String confignum = this.getJpo().getString("confignum");//配置序号
                String softversion = this.getJpo().getString("softversion");//现场版本
                
                MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultOrg("CRRC");
                MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultSite("ELEC");
                IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET", "1=2");
                
                IJpo jpo = jposet.addJpo();
                jpo.setValue("type", "2",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("sqn", sqn,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("itemnum", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("description", description,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("loc", loc,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("locdesc", locdesc,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("rnum", rnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("linenum", linenum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("confignum", confignum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("softversion", softversion,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("ancestor", toAncestor,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("parent", toParent,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("assetlevel", "SYSTEM",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("iserp", "1",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jposet.save();
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
