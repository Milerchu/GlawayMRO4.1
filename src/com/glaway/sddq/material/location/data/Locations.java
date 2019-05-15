/*
 * 文 件 名:  Locations.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yyang
 * 修改时间:  2016-5-9
 */
package com.glaway.sddq.material.location.data;

import java.util.HashSet;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * <功能描述>
 * @author  yyang
 * @version  [版本号, 2016-5-9]
 * @since  [产品/模块版本]
 */
public class Locations extends Jpo
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -8037848761461687388L;
    
    /**
     * @throws MroException
     */
    @Override
    public void add()
        throws MroException
    {
        super.add();
        setValue("type", "操作");
        setValue("LOCLEVEL", 0);//默认新建为0级数据
        setValue("STOREROOMGRADE", "二级");//默认手动新建为二级库房
        IJpoSet siteSet = getJpoSet("SITE");
        if (!siteSet.isEmpty())
        {
            IJpo siteMbo = siteSet.getJpo(0);
            IJpo billtoShiptoBilling = siteMbo.getJpoSet("BILLTODEFAULT").getJpo(0);
            IJpo billtoShiptoShipping = siteMbo.getJpoSet("SHIPTODEFAULT").getJpo(0);
            if (billtoShiptoBilling != null)
            {
                setValue("billtoaddresscode",
                    billtoShiptoBilling.getString("addresscode"),
                    GWConstant.P_NOVALIDATION_AND_NOACTION);
                setValue("billtolaborcode",
                    billtoShiptoBilling.getString("billtocontact"),
                    GWConstant.P_NOVALIDATION_AND_NOACTION);
            }
            if (billtoShiptoShipping != null)
            {
                setValue("shiptoaddresscode",
                    billtoShiptoShipping.getString("addresscode"),
                    GWConstant.P_NOVALIDATION_AND_NOACTION);
                setValue("shiptolaborcode",
                    billtoShiptoShipping.getString("shiptocontact"),
                    GWConstant.P_NOVALIDATION_AND_NOACTION);
            }
        }
        setValue("CHANGEBY", this.getUserInfo().getLoginID());
        setValue("CHANGEDATE", MroServer.getMroServer().getDate());
        setValue("STATUS", "正常");
        setValue("STATUSDATE", MroServer.getMroServer().getDate());
        setValue("isdefault", false, 11L);
        setValue("disabled", false, 11L);
    }
    
    /**
     * 初始化：设置parent 和  children 字段
     * @throws MroException
     */
    @Override
    public void init()
        throws MroException
    {
        super.init();

        IJpoSet parentSet = this.getJpoSet("LOCHIERARCHY_PARENTS");
        if (!parentSet.isEmpty())
        {
            this.setValue("parent", parentSet.getJpo(0).getString("parent"), GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
        }
        IJpoSet childrenSet = this.getJpoSet("LOCHIERARCHY_CHILDREN");
        this.setValue("children", !childrenSet.isEmpty(), GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
      //初始化如果是一级库房，库房等级和父级库房为只读
        if(this.getString("STOREROOMGRADE").equalsIgnoreCase("一级")){
        	this.setFieldFlag("STOREROOMGRADE",7l,true);
        	this.setFieldFlag("STOREROOMPARENT",7l,true);
        }
         // ERP对应库房只读 不可修改--by yangy
        if(this.getBoolean("ISSAPSTORE")){
        	this.setFlag(GWConstant.S_READONLY, true);
        }
        //不是新建状态下，库房不可编辑
        if(!this.isNew()){
        	String KEEPER=this.getString("KEEPER");
        	String personid=this.getUserInfo().getLoginID();
        	personid=personid.toUpperCase();
        	IJpoSet GROUPUSER=MroServer.getMroServer().getSysJpoSet("SYS_GROUPUSER");//判断登陆人是不是在系统管理员组
        	GROUPUSER.setUserWhere("groupname='SDDQDEFAULT' and userid='"+personid+"'");
        	String[] readonlyfiled={"DESCRIPTION","ERPLOC","STOREROOMLEVEL","LOCATIONTYPE","LOCSITE","KEEPER","ADDRESS","JXORFW","ISLOCBIN","REMARK"};
//        	if(this.getString("status").equalsIgnoreCase("正常")){
        		if(personid.equalsIgnoreCase(KEEPER)){
        			this.setFieldFlag(readonlyfiled, GWConstant.S_READONLY, true);
        		}else{
        			if(!GROUPUSER.isEmpty()){
        				this.setFieldFlag(readonlyfiled, GWConstant.S_READONLY, true);
        			}else{
        				this.setFlag(GWConstant.S_READONLY, true);
        			}
        			
        		}       		
//        	}
        }
    }
    
    /**
     * @throws MroException
     */
    @Override
    public void beforeSave()
        throws MroException
    {
        if (toBeUpdated())
        {
        	String appname=this.getAppName();
        	if(null!=appname){
            	if(!appname.equalsIgnoreCase("MATERREQ")){
           		 setValue("CHANGEBY", this.getUserInfo().getLoginID());
                    setValue("CHANGEDATE", MroServer.getMroServer().getDate());
                    IJpoSet ansSet = getJpoSet("LOCANCESTOR");
                    if (!ansSet.isEmpty())
                    {
                        setValue("LOCLEVEL", ansSet.count() - 1);
                    }
           	}
        	}

           
        }
    }

    /**
     * @throws MroException
     */
    @Override
    public void delete()
        throws MroException
    {
        canDelete();
        try
        {
            this.getJpoSet("LOCANCESTOR").deleteAll();
            this.getJpoSet("LOCHIERARCHY").deleteAll();
            this.getJpoSet("LOCSTATUS").deleteAll();
            this.getJpoSet("DOCLINKS").deleteAll();
            super.delete();
        }
        catch (MroException e)
        {
            undelete();
            throw e;
        }
    }
    
    /**
     * 判断能否删除位置
     * <功能描述> [参数说明]
     * @throws MroException 
     */
    private void canDelete()
        throws MroException
    {
        IJpoSet childrenSet = this.getJpoSet("LOCHIERARCHY_CHILDREN");
        IJpoSet childrenlocationSet = this.getJpoSet("CHILDREN");
        IJpoSet itemSet = this.getJpoSet("INVENTORY");
        if (!childrenSet.isEmpty())
        {
            String[] p = {this.getString("location")};
            throw new MroException("locations", "deleteLocation", p);
        }
        if (!childrenlocationSet.isEmpty())
        {
            String[] p = {this.getString("location")};
            throw new MroException("locations", "havechildren", p);
        }
        if (!itemSet.isEmpty())
        {
            String[] p = {this.getString("location")};
            throw new MroException("locations", "haveitem", p);
        }
        
        if (this.getBoolean("ISSAPSTORE"))
        {
            String[] p = {this.getString("location")};
            throw new MroException("locations", "issapstore", p);
        }
    }
    
    /**
     * 清除当前位置的父级关系，更新子级和所有子级的 祖代关系
     * @throws MroException [参数说明]
     */
    public void clearParent()
        throws MroException
    {
        changeParent(null);
    }
    
    /**
     * 撤销对当前记录的父级更改，撤销对子级和所有子级的 祖代关系的影响
     * @throws MroException [参数说明]
     */
    public void unclearParent()
        throws MroException
    {
        IJpoSet locancestorSet = getJpoSet("LOCANCESTOR");//所有祖先关系。
        locancestorSet.reset();
        IJpoSet locancestornoselfSet = getJpoSet("LOCANCESTORNOSELF");//排除自己跟自己的所有祖先关系。
        locancestornoselfSet.reset();
        IJpoSet successorLocSet = getJpoSet("LOCATION_CHILDREN"); //不包括自己的所有后代 位置
        successorLocSet.reset();
    }
    
    /**
     * 修改父级操作： 
     * 1、变更父级属性 
     * 2、变更祖代关系表
     * @param parentLoc [参数说明]
     * @throws MroException 
     */
    public void changeParent(IJpo newparentLoc)
        throws MroException
    {
        HashSet<String> oldAncestors = new HashSet<String>(); //原 祖先 列表
        HashSet<String> newAncestors = new HashSet<String>();
        
        IJpoSet locancestorSet = getJpoSet("LOCANCESTOR");//所有祖先关系。
        locancestorSet.reset();
        IJpoSet locancestornoselfSet = getJpoSet("LOCANCESTORNOSELF");//排除自己跟自己的所有祖先关系。
        locancestornoselfSet.reset();
        // 1 记录修改之前的所有 祖先并删除
        for (int i = 0; i < locancestornoselfSet.count(); i++)
        {
            IJpo locancestornoself = locancestornoselfSet.getJpo(i);
            if (locancestornoself != null)
            {
                oldAncestors.add(locancestornoself.getString("ANCESTOR"));
            }
        }
        //  删除原有祖先关系
        locancestornoselfSet.deleteAll();
        
        // 2 更新 新父级带来的祖代关系（祖先）,若心父级为空 则表示要断开层级关系
        if (newparentLoc != null)
        {
            IJpoSet plocancestorSet = newparentLoc.getJpoSet("LOCANCESTOR");
            for (int i = 0; i < plocancestorSet.count(); i++)
            {
                IJpo panc = plocancestorSet.getJpo(i);
                IJpo locancestor = locancestorSet.addJpo();
                locancestor.setValue("ANCESTOR", panc.getString("ANCESTOR"));
                locancestor.setValue("LOCATION", this.getString("location"));
                locancestor.setValue("ORGID", this.getString("ORGID"));
                locancestor.setValue("SITEID", this.getString("SITEID"));
                newAncestors.add(panc.getString("ANCESTOR"));
            }
        }
        
        // 3 读取当前位置的所有后代位置
        IJpoSet successorLocSet = getJpoSet("LOCATION_CHILDREN"); //不包括自己的所有后代 位置
        
        // 4 遍历所有后代位置，逐个取出后代所有的祖代关系。
        //    删除 后代还保留的原祖先关系
        //    新增 后代因新增父级带来的祖代关系
        updateSuccessor(successorLocSet, oldAncestors, newAncestors);
        //locancestornoselfSet.save();
    }
    
    /**
     * 遍历原子代列表，逐个取出子代所有的祖代关系。
     * 删除 后代还保留的原祖先关系
     * 新增 后代因新增父级带来的祖代关系
     * @param oldsuccessorVec 当前位置的所有不包括自己的子代 位置数组
     * @param oldAncestorVec  当前位置的所有需要删除的祖代位置数组 同样需要再子代关系数据中删除
     * @param newAncestorVec  当前位置父级变更后新增的祖代位置数组 需要再子代关系数据中新增
     * @throws MroException 
     */
    private void updateSuccessor(IJpoSet successorLocSet, HashSet<String> oldAncestorVec, HashSet<String> newAncestorVec)
        throws MroException
    {
        for (int i = 0; i < successorLocSet.count(); i++)
        {
            IJpo successorLoc = successorLocSet.getJpo(i);
            if (successorLoc != null)
            {
                IJpoSet locancestorSet = successorLoc.getJpoSet("LOCANCESTOR");//所有祖先关系。
                for (int n = 0; n < locancestorSet.count(); n++)
                {
                    IJpo locancestor = locancestorSet.getJpo(n);
                    String ancestor = locancestor.getString("ANCESTOR");
                    if (oldAncestorVec.contains(ancestor))
                        locancestor.delete();
                }
                for (String newancestor : newAncestorVec)
                {
                    IJpo newlocancestor = locancestorSet.addJpo();
                    newlocancestor.setValue("ANCESTOR", newancestor);
                    newlocancestor.setValue("LOCATION", successorLoc.getString("location"));
                    newlocancestor.setValue("ORGID", successorLoc.getString("ORGID"));
                    newlocancestor.setValue("SITEID", successorLoc.getString("SITEID"));
                }
            }
        }
    }
    
    @Override
    public boolean toBeUpdated() {
    	// TODO Auto-generated method stub
    	String appname= this.getAppName();
    	if(null!=appname){
    		if(appname.equalsIgnoreCase("MATERREQ")){
        		return false;
        	}
    	} 	
    	return super.toBeUpdated();
    }
    
    @Override
    public boolean isModified(String arg0) throws MroException {
    	// TODO Auto-generated method stub
    	String appname= this.getAppName();
    	if(null!=appname){
    		if(appname.equalsIgnoreCase("MATERREQ")){
        		return false;
        	}
    	} 
    	return super.isModified(arg0);
    }
}
