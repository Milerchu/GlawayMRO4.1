package com.glaway.sddq.material.transferline.bean;

import groovy.ui.SystemOutputInterceptor;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.cache.SysObjectCache;
import com.glaway.mro.system.cache.SysOrgSiteCache;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
/**
 * 
 * <送修单选择装箱单行功能类>
 * 
 * @author  public2795
 * @version  [版本号, 2018-8-21]
 * @since  [产品/模块版本]
 */
public class SelectzxdlineDataBean extends DataBean {
/**
 * 过滤装箱单信息	
 * @throws MroException
 */
	 public void initialize()
		        throws MroException
		    {
		  
		 	String ISSUESTOREROOM=this.getAppBean().getJpo().getString("ISSUESTOREROOM");
			String sxtype=this.getAppBean().getJpo().getString("sxtype");
			IJpoSet transferlineset=this.getAppBean().getJpo().getJpoSet("transferline");
			String where = "";
			String Idstr="";
			if(transferlineset == null || transferlineset.count() == 0){
				
				where="RECEIVESTOREROOM='"+ISSUESTOREROOM+"' and sxtype='"+sxtype+"' and not exists(select zxdlineid from transferline where zxdlineid is not null and zxdlineid=transferlinetradeid)";
			}
			if(transferlineset != null && transferlineset.count() >0){
				for(int i=0;i<transferlineset.count();i++){
					String zxdlineid = transferlineset.getJpo(i).getString("zxdlineid");
	                if (!"".equals(zxdlineid))
	                {
	                    if (StringUtil.isStrEmpty(Idstr))
	                    	Idstr = "'" + StringUtil.getSafeSqlStr(zxdlineid) + "'";
	                    else
	                    	Idstr = Idstr+ ",'" + StringUtil.getSafeSqlStr(zxdlineid) + "'";
	                    
	                }
				}
				if (Idstr != null)
		        {
					String where1="RECEIVESTOREROOM='"+ISSUESTOREROOM+"' and sxtype='"+sxtype+"' and not exists(select zxdlineid from transferline where zxdlineid is not null and zxdlineid=transferlinetradeid)";
					where = where1 + "and  transferlinetradeid not in (" + Idstr + ")";
		        }
			}
		      if (!StringUtil.isStrEmpty(where)) {
		           this.getJpoSet().setUserWhere(where);
		       }
		      super.initialize();
		    }
	 
/**
 * 赋值选择的数据
 * @return
 * @throws IOException
 * @throws MroException
 */
	@Override
	public int execute() throws IOException, MroException {
		DataBean transferlineBean = this.page.getAppBean().getDataBean("othertable");
		IJpoSet ransferlineSet=this.getAppBean().getJpo().getJpoSet("transferline");
		IJpo transfer=this.getAppBean().getJpo();
		String transfernum =transfer.getString("transfernum");
		String orgid =transfer.getString("orgid");
		String siteid =transfer.getString("siteid");
		String RECEIVESTOREROOM =transfer.getString("RECEIVESTOREROOM");
		String ISSUESTOREROOM =transfer.getString("ISSUESTOREROOM");
		List<IJpo> list=getJpoSet().getSelections();
		if(!list.isEmpty()){
			for (IJpo iJpo : list) {
				String transferlilneid=iJpo.getString("transferlineid");
				IJpoSet transferlineset=MroServer.getMroServer().getJpoSet("transferline", MroServer.getMroServer().getSystemUserServer());
				transferlineset.setUserWhere("transferlineid='"+transferlilneid+"'");
				transferlineset.reset();
				if(!transferlineset.isEmpty()){
				String TRANSWORKORDERNUM=transferlineset.getJpo(0).getString("TRANSWORKORDERNUM");
				String TRANSNOTICENUM="";
				if(!TRANSWORKORDERNUM.equalsIgnoreCase("")){
					TRANSNOTICENUM=transferlineset.getJpo(0).getJpoSet("TRANSWORKORDERNUM").getJpo().getString("TRANSNOTICENUM");
				}
				
		
				
				String itemnum=transferlineset.getJpo(0).getString("itemnum");	
				String sqn=transferlineset.getJpo(0).getString("sqn");
				String lotnum=transferlineset.getJpo(0).getString("lotnum");
				String FAILUREDESC=transferlineset.getJpo(0).getString("FAILUREDESC");
				String FAILURECONS=transferlineset.getJpo(0).getString("FAILURECONS");
				String ISJSFX=transferlineset.getJpo(0).getString("ISJSFX");	
				String sxtype=transferlineset.getJpo(0).getString("sxtype");
				String MODEL=transferlineset.getJpo(0).getString("MODEL");
				String PROJECTNUM=transferlineset.getJpo(0).getString("PROJECTNUM");
				String ISAPPNOTICE=transferlineset.getJpo(0).getString("ISAPPNOTICE");
				String DEALTYPE=transferlineset.getJpo(0).getString("DEALTYPE");
				String tasknum=transferlineset.getJpo(0).getString("tasknum");
				String assetnum=transferlineset.getJpo(0).getString("assetnum");
				String qmsnum=transferlineset.getJpo(0).getString("qmsnum");
				String IMPORTLEVEL=transferlineset.getJpo(0).getString("IMPORTLEVEL");
				
				String SCALELINENUM=transferlineset.getJpo(0).getString("SCALELINENUM");
				Date zcdate=transferlineset.getJpo(0).getDate("zcdate");
				double ORDERQTY=iJpo.getDouble("jsqty");
				String transferlinetradeid=iJpo.getString("transferlinetradeid");
				String itempotype=iJpo.getString("itempotype");
				
				IJpo transferline=ransferlineSet.addJpo();
				transferline.setValue("transfernum", transfernum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("orgid", orgid,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("siteid", siteid,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("RECEIVESTOREROOM", RECEIVESTOREROOM,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("ISSUESTOREROOM", ISSUESTOREROOM,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("itemnum", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("sqn", sqn,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("lotnum", lotnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("FAILUREDESC", FAILUREDESC,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("FAILURECONS", FAILURECONS,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("ISJSFX", ISJSFX);
				transferline.setValue("sxtype", sxtype,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("MODEL", MODEL,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("PROJECTNUM", PROJECTNUM,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("DEALTYPE", DEALTYPE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("ORDERQTY", ORDERQTY,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("zxdlineid", transferlinetradeid,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("TRANSWORKORDERNUM", TRANSWORKORDERNUM,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("TRANSNOTICENUM", TRANSNOTICENUM,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("ISAPPNOTICE", ISAPPNOTICE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("tasknum", tasknum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("PRODUCTTYPE", itempotype,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("assetnum", assetnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("zcdate", zcdate,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("qmsnum", qmsnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("SCALELINENUM", SCALELINENUM,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("IMPORTLEVEL", IMPORTLEVEL,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				
				transferline.setValue("PLANREPAURDATE", transfer.getDate("PLANREPAURDATE"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if(!tasknum.isEmpty()){
					IJpoSet workorderset=MroServer.getMroServer().getJpoSet("workorder", MroServer.getMroServer().getSystemUserServer());
					workorderset.setUserWhere("ORDERNUM='"+tasknum+"'");
					workorderset.reset();
					if(!workorderset.isEmpty()){
						transferline.setValue("PROJNUM", workorderset.getJpo(0).getString("MODELPROJECT"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("SCALENUM", workorderset.getJpo(0).getString("VBELN"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("CUSTNUM", workorderset.getJpo(0).getString("SERVCOMPANY"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("CUSTNUMNAME", workorderset.getJpo(0).getJpoSet("SERVCOMPANY").getJpo(0).getString("CUSTNAME"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}		
			}
				
				
			}
			
		}
		transferlineBean.reloadPage();
		this.page.getAppBean().SAVE();
		return super.execute();
	}
}
