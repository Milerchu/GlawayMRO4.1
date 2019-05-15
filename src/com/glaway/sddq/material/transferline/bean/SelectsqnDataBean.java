package com.glaway.sddq.material.transferline.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
/**
 * 
 * <送修单选择周转件功能类>
 * 
 * @author  public2795
 * @version  [版本号, 2018-9-4]
 * @since  [产品/模块版本]
 */
public class SelectsqnDataBean extends DataBean {
/**	
 * 过滤周转件信息
 * @throws MroException
 */
	public void initialize()
	        throws MroException
	    {
	 String ISSUESTOREROOM=this.getAppBean().getJpo().getString("ISSUESTOREROOM");
		String sxtype=this.getAppBean().getJpo().getString("sxtype");
		   String where = "";
		   if(sxtype.equalsIgnoreCase("GZ")){
			   where="location='"+ISSUESTOREROOM+"' and status='故障'"; 
		   }
		   else if(sxtype.equalsIgnoreCase("GAIZ")){
			   where="location='"+ISSUESTOREROOM+"' and status='改造'"; 
		   }
		   else if(sxtype.equalsIgnoreCase("YXX")){
			   where="location='"+ISSUESTOREROOM+"' and status='待检测'"; 
		   }else{
			   where="location='"+ISSUESTOREROOM+"'"; 
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
	public int dialogok() throws IOException, MroException {
	DataBean transferlineBean = this.page.getAppBean().getDataBean("othertable");
	IJpoSet ransferlineSet=transferlineBean.getJpoSet();
	IJpo transfer=this.getAppBean().getJpo();
	String transfernum =transfer.getString("transfernum");
	String orgid =transfer.getString("orgid");
	String siteid =transfer.getString("siteid");
	String RECEIVESTOREROOM =transfer.getString("RECEIVESTOREROOM");
	String PROJECTNUM=transfer.getString("PROJECTNUM");
	List<IJpo> list=getJpoSet().getSelections();
	if(!list.isEmpty()){
		for (IJpo iJpo : list) {
			String itemnum=iJpo.getString("itemnum");	
			String sqn=iJpo.getString("sqn");
			String sxtype=transfer.getString("sxtype");
			
			IJpo transferline=ransferlineSet.addJpo();
			transferline.setValue("transfernum", transfernum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			transferline.setValue("orgid", orgid,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			transferline.setValue("siteid", siteid,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			transferline.setValue("RECEIVESTOREROOM", RECEIVESTOREROOM,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			transferline.setValue("itemnum", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			transferline.setValue("sqn", sqn);
			transferline.setValue("sxtype", sxtype,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			transferline.setValue("ORDERQTY", 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			transferline.setValue("PROJECTNUM", PROJECTNUM,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			
			
			
		}
		
	}

	transferlineBean.reloadPage();
	return super.dialogok();
}
}
