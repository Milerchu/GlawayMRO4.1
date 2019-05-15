package com.glaway.sddq.material.materreq.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
/**
 * 
 * <配件申请选择服务主管处置匹配项弹出框DataBean>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-8]
 * @since  [产品/模块版本]
 */
public class SelectMrlineTransferDataBean extends DataBean {
	/**
	 * 过滤可选择的数据
	 * @throws MroException
	 */
	@Override
	public void initialize() throws MroException {
		// TODO Auto-generated method stub
		String mrnum=this.page.getAppBean().getJpo().getString("mrnum");
		String where = "";
		IJpoSet MRLINETRANSLINESET =MroServer.getMroServer().getJpoSet("MRLINETRANSFER", MroServer.getMroServer().getSystemUserServer());
		MRLINETRANSLINESET.setUserWhere("selecttype='匹配' and mrnum='"+mrnum+"' and datatype='2'");
		MRLINETRANSLINESET.reset();
		if(MRLINETRANSLINESET.isEmpty()){
			where = " mrnum='"+mrnum+"' and TRANSTYPE='计划经理协调'";
		}else{
			String itemnums = null;
			IJpoSet mrlinetransferset=this.getJpoSet();
			mrlinetransferset.setUserWhere("mrnum='"+mrnum+"'and datatype='1' and TRANSTYPE='计划经理协调'");
			mrlinetransferset.reset();
			for(int i=0;i<mrlinetransferset.count();i++){
				IJpo mrlinetransfer=mrlinetransferset.getJpo(i);
				String itemnum=mrlinetransfer.getString("itemnum");
				double transferqty=mrlinetransfer.getDouble("transferqty");
				IJpoSet thisMRLINETRANSLINESET=MroServer.getMroServer().getJpoSet("MRLINETRANSFER", MroServer.getMroServer().getSystemUserServer());
				thisMRLINETRANSLINESET.setUserWhere("selecttype='匹配' and itemnum='"+itemnum+"' and mrnum='"+mrnum+"' and datatype='2'");
				thisMRLINETRANSLINESET.reset();
				if(!thisMRLINETRANSLINESET.isEmpty()){
					double sumjhjlqty=0.00;
					for(int j=0;j<thisMRLINETRANSLINESET.count();j++){
						String transtype=thisMRLINETRANSLINESET.getJpo(j).getString("transtype");
						if(transtype.equalsIgnoreCase("下达计划")){
							sumjhjlqty+=thisMRLINETRANSLINESET.getJpo(j).getDouble("JHQTY");
						}else{
							sumjhjlqty+=thisMRLINETRANSLINESET.getJpo(j).getDouble("transferqty");
						}
					}
					if(sumjhjlqty<transferqty){
						if (itemnums == null) {
				               itemnums = "'" + itemnum + "'";
				           } else {
				               itemnums = itemnums + ",'" + itemnum + "'";
				           }
					}
				}else{
					if (itemnums == null) {
			               itemnums = "'" + itemnum + "'";
			           } else {
			               itemnums = itemnums + ",'" + itemnum + "'";
			           }
				}
			}
			if (itemnums != null) {
		           where = " mrnum='"+mrnum+"' and itemnum in(" + itemnums + ") and datatype='1' and TRANSTYPE='计划经理协调'";
		       }else{
		    	   where = " mrnum='"+mrnum+"' and itemnum is null";
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
	public int dialogok() throws IOException, MroException {
		// TODO Auto-generated method stub
		DataBean mrlinetransferdataBean = this.page.getAppBean().getDataBean("jhjlcz");
		IJpoSet mrlinetransferSet=mrlinetransferdataBean.getJpoSet();
		String mrnum = this.page.getAppBean().getJpo().getString("MRNUM");
		String MODEL = this.page.getAppBean().getJpo().getString("MODEL");
		String PROJECT = this.page.getAppBean().getJpo().getString("PROJECT");
		String receivestoreroom = this.page.getAppBean().getJpo().getString("receivestoreroom");
		double sumqty=0.00;
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty())
        {
			for (int i = 0; i < list.size(); i++){		
				IJpo mrlinetransferline = list.get(i);
				String itemnum=mrlinetransferline.getString("itemnum");
				String memo=mrlinetransferline.getString("memo");
				String issoft=mrlinetransferline.getString("issoft");
				double lineqty=mrlinetransferline.getDouble("transferqty");
				IJpoSet thisMRLINETRANSLINESET=MroServer.getMroServer().getJpoSet("MRLINETRANSFER", MroServer.getMroServer().getSystemUserServer());
				thisMRLINETRANSLINESET.setUserWhere("selecttype='匹配' and itemnum='"+itemnum+"' and mrnum='"+mrnum+"' and datatype='2'");
				thisMRLINETRANSLINESET.reset();
				if(!thisMRLINETRANSLINESET.isEmpty()){
					for(int j=0;j<thisMRLINETRANSLINESET.count();j++){
						String transtype=thisMRLINETRANSLINESET.getJpo(j).getString("transtype");
						if(transtype.equalsIgnoreCase("下达计划")){
							sumqty+=thisMRLINETRANSLINESET.getJpo(j).getDouble("JHQTY");
						}else{
							sumqty+=thisMRLINETRANSLINESET.getJpo(j).getDouble("transferqty");
						}
					}
				}
			
				IJpo mrlinetransfer = mrlinetransferSet.addJpo();	
				mrlinetransfer.setValue("ITEMNUM", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrlinetransfer.setValue("mrnum", mrnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrlinetransfer.setValue("MODEL", MODEL,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrlinetransfer.setValue("PROJECT", PROJECT,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrlinetransfer.setValue("REMARK", memo,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrlinetransfer.setValue("issoft", issoft,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if(issoft.equalsIgnoreCase("1")){
					mrlinetransfer.setValue("TRANSTYPE", "中心库调拨",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mrlinetransfer.setValue("TRANSFERQTY", lineqty-sumqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				mrlinetransfer.setValue("selecttype", "匹配",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrlinetransfer.setValue("datatype", "2",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrlinetransfer.setValue("receivestoreroom", receivestoreroom,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrlinetransfer.setValue("OVERQTY", sumqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrlinetransfer.setValue("NOQTY", lineqty-sumqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
        }
		mrlinetransferdataBean.reloadPage();
		this.getAppBean().SAVE();
		return super.dialogok();
	}
}
