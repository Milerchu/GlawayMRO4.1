package com.glaway.sddq.material.transfer.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.back.Interface.webservice.srm.MrotoSrmSxdServiceImpl;
import com.glaway.sddq.material.invtrans.common.CommonAddNewInventory;
import com.glaway.sddq.material.invtrans.common.TransInvtranscommon;
import com.glaway.sddq.material.invtrans.common.TransStoroomCommon;

/**
 * 
 * <送修单AppBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class SxTransferAppBean extends AppBean {
	/**
	 * 
	 * <发运按钮功能，判断是否能发运，发运成功赋值status,调用改变序列号状态方法，新增接收在途数据方法>
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void ISSUE() throws MroException, IOException {
		this.getAppBean().SAVE();
		IJpo transferJpo = getJpo();
		String status = transferJpo.getString("status");
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String CREATEBY = transferJpo.getString("CREATEBY");// 制单人
		if (personid.equalsIgnoreCase(CREATEBY)) {
			if (status.equalsIgnoreCase("未处理")) {
				IJpoSet transferlineset = transferJpo.getJpoSet("transferline");
				if (transferlineset.count() == 0) {
					throw new MroException("transferline", "statusissue");
				} else {
					transferJpo.setValue("status", "在途");
					// 调用接收在途新增数据方法
					CommonAddNewInventory.addinventory(transferlineset);
					// 调用修改序列号状态方法
					UPDATESQNSTATUS(transferlineset);
					this.getAppBean().SAVE();
				}

			} else {
				throw new MroException("transferline", "noissue");
			}
		} else {
			throw new MroException("transferline", "ISSUE");
		}

	}

	/**
	 * MRO送修单同步SRM(同步送修单按钮)
	 * 
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void RECEVICESXD() throws MroException, IOException {
		IJpo transferJpo = getJpo();

		MrotoSrmSxdServiceImpl.toSrmSxd(transferJpo);
	}

	/**
	 * 
	 * <接收方法>
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void RECEIVE() throws MroException, IOException {
		IJpo transferJpo = getJpo();
		String status = transferJpo.getString("status");
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		java.util.Date RECIVEDATE = MroServer.getMroServer().getDate();
		String endby = transferJpo.getString("endby");// 现场库管员
		if (personid.equalsIgnoreCase(endby)) {
			if (status.equalsIgnoreCase("在途")) {
				IJpoSet transferlineset = transferJpo.getJpoSet("transferline");
				if (!transferlineset.isEmpty()) {
					TransStoroomCommon.in_storoom(transferlineset);// 调用公共方法物料入库
					TransInvtranscommon.in_invtrans(transferlineset);// 调用公共方法物料入库库存交易记录
					for (int i = 0; i < transferlineset.count(); i++) {
						IJpo transferline = transferlineset.getJpo(i);
						double ORDERQTY = transferline.getDouble("ORDERQTY");
						transferline.setValue("YJSQTY", ORDERQTY);
						transferline.setValue("status", "已接收");
					}

					transferJpo.setValue("status", "已接收");
					transferJpo.setValue("RECIVEBY", personid);
					transferJpo.setValue("RECIVEDATE", RECIVEDATE);
					this.getAppBean().SAVE();
				} else {
					throw new MroException("transferline", "noline");
				}

			} else {
				throw new MroException("transferline", "noreceive");
			}
		} else {
			throw new MroException("transferline", "endby");
		}

	}

	/**
	 * 
	 * <变更序列号状态方法>
	 * 
	 * @param transferlineset
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void UPDATESQNSTATUS(IJpoSet transferlineset) throws MroException {

		for (int i = 0; i < transferlineset.count(); i++) {
			IJpo transferline = transferlineset.getJpo(i);
			String itemnum = transferline.getString("itemnum");
			String sqn = transferline.getString("sqn");
			String assetnum = transferline.getString("assetnum");
			String ISSUESTOREROOM = transferline.getString("ISSUESTOREROOM");
			if (!sqn.isEmpty()) {
				if (!assetnum.isEmpty()) {
					IJpoSet assetset = MroServer.getMroServer().getJpoSet(
							"asset",
							MroServer.getMroServer().getSystemUserServer());
					assetset.setUserWhere("itemnum='" + itemnum + "' and sqn='"
							+ sqn + "' and assetnum='" + assetnum
							+ "' and location='" + ISSUESTOREROOM + "'");
					assetset.reset();
					if (!assetset.isEmpty()) {
						assetset.getJpo(0).setValue("status", "在途",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.save();
					}
				} else {
					IJpoSet assetset = MroServer.getMroServer().getJpoSet(
							"asset",
							MroServer.getMroServer().getSystemUserServer());
					assetset.setUserWhere("itemnum='" + itemnum + "' and sqn='"
							+ sqn + "' and location='" + ISSUESTOREROOM + "'");
					assetset.reset();
					if (!assetset.isEmpty()) {
						assetset.getJpo(0).setValue("status", "在途",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.save();
					}
				}
			}
		}
	}
}
