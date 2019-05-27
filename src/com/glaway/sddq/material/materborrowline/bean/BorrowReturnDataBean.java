package com.glaway.sddq.material.materborrowline.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.material.invtrans.common.CommonInventory;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;
/**
 * 
 * <配件归还弹出框DATABEAN>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-26]
 * @since  [产品/模块版本]
 */
public class BorrowReturnDataBean extends DataBean {
	/**
	 * 弹出时判断相应字段只读必填
	 * 
	 * @throws MroException
	 */
	@Override
	public void initialize() throws MroException {
		// TODO Auto-generated method stub
		super.initialize();
		String newsqn=this.getJpo().getString("newsqn");//归还序列号
		String newlotnum=this.getJpo().getString("newlotnum");//归还批次号
		if(newsqn.isEmpty()){
			this.getJpo().setFieldFlag("newsqn", GWConstant.S_REQUIRED, false);
			this.getJpo().setFieldFlag("newsqn", GWConstant.S_READONLY, true);
		}else{
			this.getJpo().setFieldFlag("newsqn", GWConstant.S_READONLY, false);
			this.getJpo().setFieldFlag("newsqn", GWConstant.S_REQUIRED, true);
		}
		if(newlotnum.isEmpty()){
			this.getJpo().setFieldFlag("newlotnum", GWConstant.S_REQUIRED, false);
			this.getJpo().setFieldFlag("newlotnum", GWConstant.S_READONLY, true);
		}else{
			this.getJpo().setFieldFlag("newlotnum", GWConstant.S_READONLY, false);
			this.getJpo().setFieldFlag("newlotnum", GWConstant.S_REQUIRED, true);
		}
	}
	/**
	 * 确认归还按钮方法
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int execute() throws MroException, IOException {
		if (this.getJpo() != null) {
			String tasknum="";
			IJpo parent = this.getJpo().getParent();
			String BORROWSTOREROOM=this.page.getAppBean().getJpo().getString("BORROWSTOREROOM");//借出库房
			String RETURNSTOREROOM=this.page.getAppBean().getJpo().getString("RETURNSTOREROOM");//借入库房
			String olditemnum=this.getJpo().getString("olditemnum");//借用物料编码
			String oldsqn=this.getJpo().getString("oldsqn");//借用序列号
			String newsqn=this.getJpo().getString("newsqn");//归还序列号
			String oldlotnum=this.getJpo().getString("oldlotnum");//借用批次号
			String newitemnum=this.getJpo().getString("newitemnum");//归还物料编码
			String newlotnum=this.getJpo().getString("newlotnum");//归还批次号
			Double returnqty=this.getJpo().getDouble("returnqty");//归还数量
			//如果归还的物料编码和借用的物料编码相同
			if(olditemnum.equalsIgnoreCase(newitemnum)){
				//如果序列号不为空
				if(!oldsqn.isEmpty()){
					//如果借用的序列号和归还的序列号相同
					if(oldsqn.equalsIgnoreCase(newsqn)){
						CHANGESTATUS();
						String assetnum=parent.getString("assetnum");
						CommonInventory.OUTINVENTORY(oldlotnum, returnqty, RETURNSTOREROOM, olditemnum, assetnum, tasknum);
						CommonInventory.ININVENTORY(newlotnum, returnqty, BORROWSTOREROOM, newitemnum, assetnum, tasknum);
						
					}
					//如果借用的序列号和归还的序列号不相同
					else{
						CHANGESTATUS();
						String assetnum=parent.getString("assetnum");
						String newassetnum = ADDASSET();//获取新增的序列号的ASSETNUM
						CommonInventory.OUTINVENTORY(oldlotnum, returnqty, RETURNSTOREROOM, olditemnum, assetnum, tasknum);
						CommonInventory.ININVENTORY(newlotnum, returnqty, BORROWSTOREROOM, newitemnum, newassetnum, tasknum);
						
					}
				}
				//如果批次号不为空
				else if(!oldlotnum.isEmpty()){
					CHANGESTATUS();
					String assetnum=parent.getString("assetnum");
					CommonInventory.OUTINVENTORY(oldlotnum, returnqty, RETURNSTOREROOM, olditemnum, assetnum, tasknum);
					CommonInventory.ININVENTORY(newlotnum, returnqty, BORROWSTOREROOM, newitemnum, assetnum, tasknum);
					
				}
			}
			//如果归还的物料编码和借用的物料编码不相同-暂时无调用接口方法,未确定接口移动类型
			else{
				String assetnum=parent.getString("assetnum");
//				String retu = MroToErp();// 调用ERP接口 接口方法中返回的retu的值；
//				if (retu.equalsIgnoreCase("S")) {
				if(newsqn.isEmpty()){
					CHANGESTATUS();
					String newassetnum="";
					CommonInventory.OUTINVENTORY(oldlotnum, returnqty, RETURNSTOREROOM, olditemnum, assetnum, tasknum);
					CommonInventory.ININVENTORY(newlotnum, returnqty, BORROWSTOREROOM, newitemnum, newassetnum, tasknum);
					
				}else{
					CHANGESTATUS();
					String newassetnum = ADDASSET();//获取新增的序列号的ASSETNUM
					CommonInventory.OUTINVENTORY(oldlotnum, returnqty, RETURNSTOREROOM, olditemnum, assetnum, tasknum);
					CommonInventory.ININVENTORY(newlotnum, returnqty, BORROWSTOREROOM, newitemnum, newassetnum, tasknum);
					
				}
//				}
			}
		}
		this.getAppBean().SAVE();

		return GWConstant.NOACCESS_SAMEMETHOD;
	}
	/**
	 * 
	 * <公共变更状态方法>
	 * @throws MroException [参数说明]
	 *
	 */
	public void CHANGESTATUS() throws MroException {
		IJpo parent = this.getJpo().getParent();
		String newitemnum=this.getJpo().getString("newitemnum");//归还物料编码
		String newsqn=this.getJpo().getString("newsqn");//归还序列号
		String newlotnum=this.getJpo().getString("newlotnum");//归还批次号
		Double returnqty=this.getJpo().getDouble("returnqty");//归还数量
		Date returndate = MroServer.getMroServer().getDate();//当前日期为归还日期
		parent.setValue("newitemnum", newitemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		parent.setValue("newsqn", newsqn,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		parent.setValue("newlotnum", newlotnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		parent.setValue("returnqty", returnqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		parent.setValue("returndate", returndate,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		parent.setValue("status","已归还",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		String materborrowlineid=parent.getString("materborrowlineid");
		String mbnum=parent.getString("mbnum");
		IJpoSet materborrowlineset = MroServer.getMroServer().getJpoSet("materborrowline", MroServer.getMroServer().getSystemUserServer());
		materborrowlineset.setUserWhere("mbnum='"+mbnum+"' and materborrowlineid!='"+materborrowlineid+"' and status='未归还'");
		if(materborrowlineset.isEmpty()){
			parent.getParent().setValue("status", "全部归还",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}else{
			parent.getParent().setValue("status", "部分归还",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}
	
	/**
	 * 
	 * <序列号新增方法>
	 * @return 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public String ADDASSET() throws MroException {
		String newassetnum=null;
		IJpoSet newassetSet =MroServer.getMroServer().getJpoSet("asset",
				MroServer.getMroServer().getSystemUserServer());
		java.util.Date INSTOREDATE = MroServer.getMroServer().getDate();
		String SQN = this.getJpo().getString("newsqn").toUpperCase();
		String ITEMNUM = this.getJpo().getString("newitemnum");
			IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
					MroServer.getMroServer().getSystemUserServer());
			assetset.setUserWhere("itemnum='"
					+ ITEMNUM
					+ "' and sqn='"
					+ SQN
					+ "' and assetlevel='ASSET' and type!='2' and location is null");
			if (assetset.isEmpty()) {
				IJpo newasset = newassetSet.addJpo();
				
				newasset.setValue("assetlevel", "ASSET",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				newasset.setValue("itemnum", ITEMNUM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				newasset.setValue("sqn", SQN,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				newasset.setValue("type", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				newasset.setValue("ancestor", newasset.getString("assetnum"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				newasset.setValue("status", "可用",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				newasset.setValue("INSTOREDATE", INSTOREDATE,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				newasset.setValue("isnew", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				newasset.setValue("iserp", "0",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				newasset.setValue("MSGFLAG", SddqConstant.MSG_INFO_NOSQN,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				newasset.setValue("FROMSOURCE", SddqConstant.FROMSOURCE_XP,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				newassetSet.save();
				newassetnum=newasset.getString("assetnum");
			}
			return newassetnum;
	}
}
