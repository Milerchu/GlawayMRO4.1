package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.material.invtrans.common.InventoryQtyCommon;

public class ZxdAllStatusAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		// TODO Auto-generated method stub
		if (jpo != null) {
			jpo.setValue("status", "在途",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			String type = jpo.getString("type");
			IJpoSet transferlineset = jpo.getJpoSet("TRANSFERLINE");

			// 取是否发运的值
			String issue = jpo.getString("issue");
			if (type.equalsIgnoreCase("ZXD")) {
				if (transferlineset != null && transferlineset.count() > 0) {
					// 取装箱单行中transferline的记录中的assetnum值
					if (issue.equalsIgnoreCase("是")) {
						for (int i = 0; i < transferlineset.count(); i++) {
							IJpo transferline=transferlineset.getJpo(i);
							String assetnum = transferlineset.getJpo(i)
									.getString("assetnum");
							IJpoSet assetjposet = transferlineset.getJpo(i)
									.getJpoSet("ASSET");
								if (StringUtil.isStrNotEmpty(assetnum)) {
									// 取asset
									assetjposet
											.getJpo(0)
											.setValue(
													"STATUS",
													"在途",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							mrsendqty(transferline,jpo);
							inventoryqty(transferline);
						}
					}

				}
			}
			jpo.getJpoSet().save();
		}
	}
	public void mrsendqty(IJpo transferline,IJpo transfer) throws MroException {
		String mrnum=transfer.getString("mrnum");
		if(!mrnum.isEmpty()){
			String MRTYPE = transfer.getJpoSet("mr").getJpo(0).getString("MRTYPE");
			if (MRTYPE.equalsIgnoreCase("零星")) {
				String mrlinetransferid = transferline.getString("mrlinetransid");
				double orderqty=transferline.getDouble("orderqty");
				IJpoSet mrlinetransferset = MroServer.getMroServer().getJpoSet("mrlinetransfer",MroServer.getMroServer().getSystemUserServer());
				mrlinetransferset.setUserWhere("mrlinetransferid='"+mrlinetransferid+"'");
				if(!mrlinetransferset.isEmpty()){
					double sendqty=mrlinetransferset.getJpo(0).getDouble("sendqty");
					double zxqty=mrlinetransferset.getJpo(0).getDouble("zxqty");
					double newsendqty=sendqty+orderqty;
					double newzxqty=zxqty+orderqty;
					mrlinetransferset.getJpo(0).setValue("sendqty", newsendqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mrlinetransferset.getJpo(0).setValue("zxqty", newzxqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mrlinetransferset.save();
				}
			}
			if (MRTYPE.equalsIgnoreCase("项目")) {
				String mrlineid = transferline.getString("mrlineid");
				double orderqty=transferline.getDouble("orderqty");
				IJpoSet mrlineset = MroServer.getMroServer().getJpoSet("mrline",MroServer.getMroServer().getSystemUserServer());
				mrlineset.setUserWhere("mrlineid='"+mrlineid+"'");
				if(!mrlineset.isEmpty()){
					double sendqty=mrlineset.getJpo(0).getDouble("sendqty");
					double newsendqty=sendqty+orderqty;
					double zxqty=mrlineset.getJpo(0).getDouble("zxqty");
					double newzxqty=zxqty+orderqty;
					mrlineset.getJpo(0).setValue("sendqty", newsendqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mrlineset.getJpo(0).setValue("zxqty", newzxqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mrlineset.save();
				}
			}
		}
	}
	public void inventoryqty(IJpo transferline) throws MroException {
		String itemnum=transferline.getString("itemnum");
		String issuestoreroom=transferline.getString("issuestoreroom");//发出库房
		String receivestoreroom=transferline.getString("receivestoreroom");//接收库房
		double qty=transferline.getDouble("orderqty");
		InventoryQtyCommon.ZXDBHSCSQZYQTY(itemnum, issuestoreroom);
		InventoryQtyCommon.ZXDBHSCFCZTQTY(itemnum, issuestoreroom,qty);
		InventoryQtyCommon.ZXDBHSCJSZTQTY(itemnum, receivestoreroom,qty);
		InventoryQtyCommon.ZXDBHSCKYQTY(itemnum, issuestoreroom);
	}
}
