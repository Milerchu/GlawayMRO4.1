package com.glaway.sddq.material.transfer.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.control.Tab;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.material.invtrans.common.CommonAddNewInventory;
import com.glaway.sddq.tools.EmailUtil;
import com.glaway.sddq.tools.ItemUtil;
/**
 * 
 * <改造装箱单AppBean>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-8]
 * @since  [产品/模块版本]
 */
public class GzZxTransferAppBean extends AppBean {
	@Override
	public void setCurrentRow(int rowNum)
			throws MroException
	{
		super.setCurrentRow(rowNum);
		// 切换记录刷新界面
		refreshPage();
	}

	@Override
	public void afterTabChange(Tab currTab)
			throws IOException, MroException
	{
		// 标签页切换时刷新界面
		refreshPage();
		super.afterTabChange(currTab);
	}
/**
 * 赋值后刷新页面
 * @param attribute
 * @throws MroException
 */
	@Override
	protected void afterSetValue(String attribute)
			throws MroException
	{
		super.afterSetValue(attribute);
		refreshPage();
	}

	/**
	 * 
	 * 根据TRANSFERTYPE字段的值刷新界面
	 * 
	 * @throws MroException
	 * 
	 */
	public void refreshPage()
			throws MroException
	{
	}
/**
 * 发送流程前判断装箱单行是否为空，数量是否为空为0
 * @return
 * @throws Exception
 */
	@Override
	public int ROUTEWF() throws Exception {
		// TODO Auto-generated method stub
		IJpo transferJpo = getJpo();
		IJpoSet transferlineset=transferJpo.getJpoSet("transferline");
		transferlineset.reset();
		if(transferlineset.isEmpty()){
			throw new MroException("transfer", "notransferline");
		}else{
			for(int i=0;i<transferlineset.count();i++){
				String ORDERQTY=transferlineset.getJpo(i).getString("ORDERQTY");
				if(ORDERQTY.equalsIgnoreCase("")||ORDERQTY.equalsIgnoreCase("0.00")){
					throw new MroException("transferline", "noqty");
				}
			}
		}
		return super.ROUTEWF();
	}
/**
 * 发出按钮，判断是否可以发运，设置状态值
 * <功能描述>
 * @throws MroException
 * @throws IOException [参数说明]
 *
 */
	public void ISSUE() throws MroException, IOException {
		IJpo transferJpo = getJpo();
		String status = transferJpo.getString("status");
		if (status.equalsIgnoreCase("未处理")) {
			IJpoSet transferlineset=transferJpo.getJpoSet("transferline");
    		if(transferlineset.count()==0){
    			throw new MroException("transferline", "statusissue");
    		}else{
				transferJpo.setValue("status", "在途");
				//调用接收在途增加库房数据方法
				CommonAddNewInventory.addinventory(transferlineset);
				//调用变更序列号状态方法
				UPDATESQNSTATUS(transferlineset);
				// zzx add start 9.26调用邮件通知方法
				EmailUtil.tranfersengptx(transferJpo);
				// zzx add end 9.236
    			this.getAppBean().SAVE();
    		}

		} else {
			throw new MroException("transferline", "noissue");
		}
	}


	/**
	 * 
	 * @return删除校验
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int DELETE() throws MroException, IOException {
		if (this.getAppName().equals("GZTRANSFER")
				&& this.getAppBean().getJpo().getString("status").equals("已接收"))
			throw new MroException("transferline", "delete");

		return super.DELETE();
	}
	/**
	 * 
	 * <变更序列号件状态>
	 * @param transferlineset
	 * @throws MroException [参数说明]
	 *
	 */
	public void UPDATESQNSTATUS(IJpoSet transferlineset) throws MroException {

		for(int i=0;i<transferlineset.count();i++){
			IJpo transferline=transferlineset.getJpo(i);
			String itemnum=transferline.getString("itemnum");
			String sqn=transferline.getString("sqn");
			String assetnum = transferline.getString("assetnum");
			String ISSUESTOREROOM=transferline.getParent().getString("ISSUESTOREROOM");
			if(!sqn.isEmpty()){
				if(!assetnum.isEmpty()){
					IJpoSet assetset=MroServer.getMroServer().getJpoSet("asset", MroServer.getMroServer().getSystemUserServer());
					assetset.setUserWhere("itemnum='"+itemnum+"' and sqn='"+sqn+"' and assetnum='"+assetnum+"' and location='"+ISSUESTOREROOM+"'");
					assetset.reset();
					if(!assetset.isEmpty()){
						assetset.getJpo(0).setValue("status", "在途",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.save();
					}
				}else{
					IJpoSet assetset=MroServer.getMroServer().getJpoSet("asset", MroServer.getMroServer().getSystemUserServer());
					assetset.setUserWhere("itemnum='"+itemnum+"' and sqn='"+sqn+"' and location='"+ISSUESTOREROOM+"'");
					assetset.reset();
					if(!assetset.isEmpty()){
						assetset.getJpo(0).setValue("status", "在途",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.save();
					}
				}
			}
		}
	}
}
