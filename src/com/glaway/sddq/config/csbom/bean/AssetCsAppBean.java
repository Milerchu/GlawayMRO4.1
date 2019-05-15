/*
 * 文 件 名:  AssetCsAppBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  出所台帐AppBean
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-27
 */
package com.glaway.sddq.config.csbom.bean;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.control.Button;
import com.glaway.mro.page.control.Tab;
import com.glaway.mro.page.control.TreeNode;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 整车SBOM AppBean
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-4-27]
 * @since  [MRO4.0/模块版本]
 */
public class AssetCsAppBean extends AppBean
{
	@Override
    public void afterTabChange(Tab currTab)
        throws IOException, MroException
    {
		if(currTab == currTab.getTabGroup().getTabByNum(2)){
			if (this.getJpo() != null)
	        {
	            Button b1 = ((Button)this.getPage().getControlByXmlId("13753386188706"));//选择产品按钮
	            Button b2 = ((Button)this.getPage().getControlByXmlId("152577054075731"));//添加节点
	            Button b3 = ((Button)this.getPage().getControlByXmlId("152574427900031"));//添加车厢按钮
	            if (("可用").equals(this.getJpo().getString("STATUS")) || ("作废").equals(this.getJpo().getString("STATUS")))
	            {
	            	if(b1 != null){
	            		b1.setDisable(true);
	            		b1.loadData();
	            	}
	            	if(b2 != null){
		            	b2.setDisable(true);
		            	b2.loadData();
	            	}
	            	if(b3 != null){
	            		b3.setDisable(true);
		            	b3.loadData();
	            	}
	            }
	            else
	            {
	            	if(b1 != null){
	            		b1.setDisable(false);
	            		b1.loadData();
	            	}
	            	if(b2 != null){
		            	b2.setDisable(false);
		            	b2.loadData();
	            	}
	            	if(b3 != null){
	            		b3.setDisable(false);
		            	b3.loadData();
	            	}
	            }
	        }
		}
        super.afterTabChange(currTab);
        if(currTab == currTab.getTabGroup().getFirstTab()){
        	DataBean databean = this.getDataBean("1375344856389");
        	if(databean != null){
        		TreeBean sbomTreeBean = (TreeBean)databean ;
            	sbomTreeBean.clickNode(sbomTreeBean.getCurrNode());
        	}
        }
    }
	
    @Override
    public int DELETE()
        throws MroException, IOException
    {
        if (this.getJpo() != null)
        {
            //删除状态历史
            IJpoSet assetCsStatusJpoSet = this.getJpo().getJpoSet("ASSETCSSTATUS");
            assetCsStatusJpoSet.deleteAll();
            return super.DELETE();
        }
        else
        {
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
    }
    
    /**
     * 
     * 点击变更状态前，判断必填项
     * @return
     * @throws MroException [参数说明]
     *
     */
    public int STATUS()
        throws MroException
    {
    	if(("草稿").equals(this.getJpo().getString("STATUS"))){
    		this.checkSave();
    	}
        return GWConstant.ACCESS_SAMEMETHOD;
    }
    
    /**
     * 
     * 导出数据
     * @return [参数说明]
     * @throws IOException 
     * @throws MroException 
     *
     */
    public int EXPORT()
        throws IOException, MroException
    {
        
        HttpServletRequest request = getMroSession().getRequest();
        String path =
            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";
        
        //查询导出字段
        IJpoSet assetcsSet = MroServer.getMroServer().getSysJpoSet("IMPATTRIBUTE");
        assetcsSet.setUserWhere("ifacename='CSBOMIMP'");
        assetcsSet.setOrderBy("to_number(excelcolnum)");
        assetcsSet.reset();
        
        String[] attributes = new String[assetcsSet.count()];
        String[] titles = new String[assetcsSet.count()];
        
        for (int i = 0; i < assetcsSet.count(); i++)
        {
            IJpo csjpo = assetcsSet.getJpo(i);
            attributes[i] = csjpo.getString("ATTRIBUTENAME");
            titles[i] = csjpo.getString("DESCRIPTION");//getAttrTitle("ASSETCS", attributes[i]);
        }
        /*String[] titles =
            {"序号", "父级序号", "车型", "描述", "制造企业", "物料编码", "行号", "位号", "位置号", "功能位置描述", "状态", "软件名称", "软件版本", "软件更新时间",
                "配置号", "顶级序号", "级别"};*/
        
        //导出当前操作的JPO 所有节点数据
        IJpoSet impSet = MroServer.getMroServer().getSysJpoSet("ASSETCS");
        String ancestor = this.getJpo().getString("ASSETCSNUM");
        impSet.setUserWhere("ancestor = '" + ancestor + "'");
        impSet.setOrderBy("assetcslevel,assetcsid");
        impSet.reset();
        
        HttpSession session = request.getSession();
        session.setAttribute("TABLESET", impSet/*this.getJpoSet()*/);
        session.setAttribute("ATTRIBUTES", attributes);
        session.setAttribute("TITLES", titles);
        session.setAttribute("APPNAME", "SBOM");
        path += "downloadattachments";
        openurl(path);
        
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    /**
     * 
     * 获取字段标题
     * @param objectname 表名
     * @param attributename 字段名称
     * @return
     * @throws MroException [参数说明]
     *
     */
    public String getAttrTitle(String objectname, String attributename)
        throws MroException
    {
        IJpoSet fieldSet = MroServer.getMroServer().getSysJpoSet("SYS_FIELD");
        fieldSet.setUserWhere("objectname = '" + objectname + "' and fieldname = '" + attributename + "'");
        fieldSet.reset();
        return fieldSet.getJpo(0).getString("TITLE");
    }
    
    @Override
    public int SAVE()
        throws IOException, MroException
    {
        int returnflag = super.SAVE();
        DataBean databean = this.getDataBean("1375344856389");
    	if(databean != null){
    		TreeBean sbomTreeBean = (TreeBean)databean ;
    		sbomTreeBean.setCurrJpo(sbomTreeBean.getCurrNode());
        	sbomTreeBean.clickNode(sbomTreeBean.getCurrNode());
    	}
//        if (this.getPage().getControlByXmlId("13753448564042") != null)
//        {
//            this.getPage().getControlByXmlId("13753448564042").show();
//        }
//        if (this.getPage().getControlByXmlId("1467204031612") != null)
//        {
//            this.getPage().getControlByXmlId("1467204031612").hide();
//        }
//        if (this.getPage().getControlByXmlId("13753386188706") != null)
//        {
//            this.getPage().getControlByXmlId("13753386188706").show();
//        }
        return returnflag;
    }
    
    /**
     * 
     * @Description 选择产品
     * @return 
     * @throws MroException
     */
    public int SELECTPRO() throws MroException {
        
        if (SddqConstant.ASSET_CS_STATUS_KY.equals(this.getString("STATUS"))
                || SddqConstant.ASSET_CS_STATUS_SD.equals(this.getString("STATUS"))) {
            throw new AppException("assetcs", "cannotAdd");
        }
        
        DataBean databean = this.getDataBean("1375344856389");
        if (databean != null) {
            TreeBean sbomTreeBean = (TreeBean) databean;
            TreeNode treenode = sbomTreeBean.getCurrNode();
            if(treenode != null){
                //判断节点是不是顶层节点，如果是顶层节点，则弹出不可添加节点以及选择产品的提示信息
                String assetlevel = treenode.getJpo().getString("assetcslevel");
                if("ASSET".equals(assetlevel)){
                    throw new AppException("asset", "selectAsset");
                }
                return GWConstant.ACCESS_SAMEMETHOD;
            }else{
                throw new AppException("asset", "selectNoAsset");
            }
        }else{
            throw new AppException("asset", "selectNoAsset");
        }
    }
    
    /**
     * 
     * @Description 添加节点
     * @return 
     * @throws MroException
     */
    public int ADDPRO() throws MroException {
        
        if (SddqConstant.ASSET_CS_STATUS_KY.equals(this.getString("STATUS"))
                || SddqConstant.ASSET_CS_STATUS_SD.equals(this.getString("STATUS"))) {
            throw new AppException("assetcs", "cannotAdd");
        }
        
        DataBean databean = this.getDataBean("1375344856389");
        if (databean != null) {
            TreeBean sbomTreeBean = (TreeBean) databean;
            TreeNode treenode = sbomTreeBean.getCurrNode();
            if(treenode != null){
                //判断节点是不是顶层节点，如果是顶层节点，则弹出不可添加节点以及选择产品的提示信息
                String assetlevel = treenode.getJpo().getString("assetcslevel");
                if("ASSET".equals(assetlevel)){
                    throw new AppException("asset", "selectAsset");
                }
                //判断节点是不是顶层节点，如果是顶层节点，则弹出不可添加节点以及选择产品的提示信息
                return GWConstant.ACCESS_SAMEMETHOD;
            }else{
                throw new AppException("asset", "selectNoAsset");
            }
        }else{
            throw new AppException("asset", "selectNoAsset");
        }
    }

    @Override
    public int INSERT() throws IOException, MroException {
        //保存一下，刷新结构树，解决选择不同的节点之后，点击新增的问题
        //this.getAppBean().SAVE();
    	ShowDjLabel();
        return super.INSERT();
    }
    
    @Override
	public int NEXT() throws IOException, MroException {
		ShowDjLabel();
		return super.NEXT();
	}

	private void ShowDjLabel() throws IOException {
		if(this.getPage().getControlByXmlId("13753448564042") != null ){
		    this.getPage().getControlByXmlId("13753448564042").show();
		}
		if(this.getPage().getControlByXmlId("1467204031612") != null){
	        this.getPage().getControlByXmlId("1467204031612").hide();
		}
		if( getPage().getControlByXmlId("1526365463241") != null){
	        getPage().getControlByXmlId("1526365463241").hide();// 车厢节点的基本信息
		}
		if(this.getPage().getControlByXmlId("1525744279000") != null){
	        this.getPage().getControlByXmlId("1525744279000").show();
		}
		if( this.getPage().getControlByXmlId("1525770540757") != null){
	        this.getPage().getControlByXmlId("1525770540757").hide();
		}
		if( this.getPage().getControlByXmlId("13722326580184") != null){
	        this.getPage().getControlByXmlId("13722326580184").hide();
		}
	}

	@Override
	public int PREVIOUS() throws IOException, MroException {
		ShowDjLabel();
		return super.PREVIOUS();
	}
    
    /**
     * 
     * @Description 新增车厢节点
     * @return
     * @throws MroException
     */
    public int ADDCX() throws MroException {
        
        if (SddqConstant.ASSET_CS_STATUS_KY.equals(this.getString("STATUS"))
                || SddqConstant.ASSET_CS_STATUS_SD.equals(this.getString("STATUS"))) {
            throw new AppException("assetcs", "cannotAdd");
        }
        
        DataBean databean = this.getDataBean("1375344856389");
        if (databean != null) {
            TreeBean sbomTreeBean = (TreeBean) databean;
            TreeNode treenode = sbomTreeBean.getCurrNode();
            if(treenode != null){
                //判断节点是不是顶层节点，如果是顶层节点，则弹出不可添加节点以及选择产品的提示信息
                String assetlevel = treenode.getJpo().getString("assetcslevel");
                if(!"ASSET".equals(assetlevel)){
                    throw new AppException("asset", "addcx");
                }
                //判断节点是不是顶层节点，如果是顶层节点，则弹出不可添加节点以及选择产品的提示信息
                return GWConstant.ACCESS_SAMEMETHOD;
            }else{
                throw new AppException("asset", "addcx");
            }
        }else{
            throw new AppException("asset", "addcx");
        }
    }
}
