package com.glaway.sddq.material.invtrans.common;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨管理行接收记录表增加公共类>
 * 
 * @author public2795
 * @version [版本号, 2018-9-10]
 * @since [产品/模块版本]
 */
public class TransferlinetradeCommon {
	private static TransferlinetradeCommon wfCtrl = null;;

	private TransferlinetradeCommon() {
	}

	public synchronized static TransferlinetradeCommon getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new TransferlinetradeCommon();
		}
		return wfCtrl;
	}

	/**
	 * 
	 * <装箱单记录接收记录方法>
	 * 
	 * @param transferline
	 * @param jsqty
	 *            [参数说明]
	 * 
	 */
	public static void add_trade(IJpo transferline, double jsqty) {
		try {
			String orgid = transferline.getString("orgid");
			String siteid = transferline.getString("siteid");
			String transferlineid = transferline.getString("transferlineid");
			String SXTYPE = transferline.getString("SXTYPE");
			String ISSUESTOREROOM = transferline.getString("ISSUESTOREROOM");
			String RECEIVESTOREROOM = transferline
					.getString("RECEIVESTOREROOM");
			String itemnum = transferline.getString("itemnum");
			String transfernum = transferline.getString("transfernum");
			String sqn = transferline.getString("sqn");
			String lotnum = transferline.getString("lotnum");
			String itempotype = "";
			IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",
					MroServer.getMroServer().getSystemUserServer());
			itemset.setUserWhere("itemnum='" + itemnum + "'");

			IJpoSet transferlinetradeset = MroServer.getMroServer().getJpoSet(
					"transferlinetrade",
					MroServer.getMroServer().getSystemUserServer());
			transferlinetradeset.setUserWhere("transferlineid='"+transferlineid+"'");
			if(transferlinetradeset.isEmpty()){
				IJpo transferlinetrade = transferlinetradeset.addJpo();
				transferlinetrade.setValue("transferlineid", transferlineid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferlinetrade.setValue("siteid", siteid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferlinetrade.setValue("orgid", orgid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferlinetrade.setValue("jsqty", jsqty,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferlinetrade.setValue("SXTYPE", SXTYPE,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferlinetrade.setValue("ISSUESTOREROOM", ISSUESTOREROOM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferlinetrade.setValue("RECEIVESTOREROOM", RECEIVESTOREROOM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferlinetrade.setValue("itemnum", itemnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferlinetrade.setValue("transfernum", transfernum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferlinetrade.setValue("sqn", sqn,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferlinetrade.setValue("lotnum", lotnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if (!itemset.isEmpty()) {
					itempotype = itemset.getJpo(0).getString("itempotype");
					transferlinetrade.setValue("itempotype", itempotype,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (itempotype.equalsIgnoreCase("三菱插件")) {
						String FAILURECONS = transferline.getString("FAILURECONS");
						if (!FAILURECONS.isEmpty()) {
							if (FAILURECONS.equalsIgnoreCase("安监")) {
								transferlinetrade.setValue("itempotype", "三菱安监插件",
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}
					}
				}
				transferlinetradeset.save();
			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
