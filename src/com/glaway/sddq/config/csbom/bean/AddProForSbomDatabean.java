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

/**
 * 
 * @ClassName AddProForSbomDatabean
 * @Description 添加节点DataBean
 * @author public2175
 * @Date 2018-8-15 下午3:06:59
 * @version 1.0.0
 */
public class AddProForSbomDatabean extends DataBean {
    
    @Override
    public int execute() throws MroException, IOException {
        
        if("SBOMCONFIG".equals(this.getAppName())){
            
            DataBean databean = this.getDataBean("1461565595904");
            if (databean != null) {
                TreeBean sbomTreeBean = (TreeBean) databean;
                TreeNode treenode = sbomTreeBean.getCurrNode();
                if(treenode != null){
                    super.checkSave();
                    //装机目标部件，可以装到除顶层节点外的任一节点上
                    String toAncestor = treenode.getJpo().getString("ancestor");
                    String toParent = treenode.getJpo().getString("assettmpnum");
                    String itemnum = this.getJpo().getString("itemnum");//物料编码
                    String description = this.getJpo().getString("item.description");
                    String loc =  this.getJpo().getString("loc");//位置号
                    String locdesc =  this.getJpo().getString("locdesc");//功能位置描述
                    String rnum = this.getJpo().getString("rnum");//位号
                    String linenum = this.getJpo().getString("linenum");//行号
//                    String confignum = this.getJpo().getString("confignum");//配置序号
//                    String softversion = this.getJpo().getString("softversion");//现场版本
                    
                    MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultOrg("CRRC");
                    MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultSite("ELEC");
                    IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETTMP", "1=2");
                    
                    IJpo jpo = jposet.addJpo();
                    jpo.setValue("itemnum", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("description", description,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("loc", loc,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("locdesc", locdesc,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("rnum", rnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("linenum", linenum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//                    jpo.setValue("confignum", confignum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//                    jpo.setValue("softversion", softversion,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("ancestor", toAncestor,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("parent", toParent,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("assettmplevel", "SYSTEM",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jposet.save();
                    //重新加载节点的子层节点
                    return super.getAppBean().SAVE();
                }else{
                    throw new AppException("asset", "selectNoAsset");
                }
            }else{
                throw new AppException("asset", "selectNoAsset");
            } 
            
        }else if("ZCSBOM".equals(this.getAppName())){
            DataBean databean = this.getDataBean("1375344856389");
            if (databean != null) {
                TreeBean sbomTreeBean = (TreeBean) databean;
                TreeNode treenode = sbomTreeBean.getCurrNode();
                if(treenode != null){
                    //装机目标部件，可以装到除顶层节点外的任一节点上
                    String toAncestor = treenode.getJpo().getString("ancestor");
                    String toParent = treenode.getJpo().getString("assetcsnum");
                    String itemnum = this.getJpo().getString("itemnum");//物料编码
                    String description = this.getJpo().getString("item.description");
                    String loc =  this.getJpo().getString("loc");//位置号
                    String locdesc =  this.getJpo().getString("locdesc");//功能位置描述
                    String rnum = this.getJpo().getString("rnum");//位号
                    String linenum = this.getJpo().getString("linenum");//行号
//                    String confignum = this.getJpo().getString("confignum");//配置序号
//                    String softversion = this.getJpo().getString("softversion");//现场版本
                    
                    MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultOrg("CRRC");
                    MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultSite("ELEC");
                    IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETCS", "1=2");
                    
                    IJpo jpo = jposet.addJpo();
                    jpo.setValue("itemnum", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("description", description,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("loc", loc,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("locdesc", locdesc,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("rnum", rnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("linenum", linenum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//                    jpo.setValue("confignum", confignum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//                    jpo.setValue("softversion", softversion,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("ancestor", toAncestor,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("parent", toParent,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("assetcslevel", "SYSTEM",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jposet.save();
                    //重新加载节点的子层节点
                    return super.getAppBean().SAVE();
                }else{
                    throw new AppException("asset", "selectNoAsset");
                }
            }else{
                throw new AppException("asset", "selectNoAsset");
            }
        }
        return super.execute();
    }
}
