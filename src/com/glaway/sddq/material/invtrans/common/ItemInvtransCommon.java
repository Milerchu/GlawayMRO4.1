package com.glaway.sddq.material.invtrans.common;

import java.util.Date;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <领料单出入库记录公共类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-7]
 * @since [产品/模块版本]
 */
public class ItemInvtransCommon {
	private static ItemInvtransCommon wfCtrl = null;;

	private ItemInvtransCommon() {
	}

	public synchronized static ItemInvtransCommon getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new ItemInvtransCommon();
		}
		return wfCtrl;
	}

	public static void mprin(IJpoSet mprlineset) { // 入库
		try {
			IJpo mprJpo = mprlineset.getParent();
			String type = mprJpo.getString("type");
			String location = "";
			if (mprJpo.getString("mprtype").equalsIgnoreCase("GZ")) {
				if (type.equalsIgnoreCase("领料")) {
					location = mprJpo.getJpoSet("MPRSTOREROOM").getJpo()
							.getJpoSet("gzlocation").getJpo(0)
							.getString("location");
				}
				if (type.equalsIgnoreCase("退料")) {
					location = mprJpo.getJpoSet("MPRSTOREROOM").getJpo()
							.getJpoSet("location1020").getJpo(0)
							.getString("location");
				}
			}
			if (mprJpo.getString("mprtype").equalsIgnoreCase("JX")) {
				location = mprJpo.getString("MPRSTOREROOM");
			}

			IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
					"INVTRANS", MroServer.getMroServer().getSystemUserServer());
			for (int i = 0; i < mprlineset.count(); i++) {
				IJpo mprline = mprlineset.getJpo(i);

				Date transdate = MroServer.getMroServer().getDate();// --交易时间
				String itemnum = mprline.getString("itemnum");// --物料编码
				String mprnum = mprline.getString("mprnum");// --领料单编号
				double qty = mprline.getDouble("qty");// --数量
				String siteid = mprline.getString("siteid");// --地点
				String orgid = mprline.getString("orgid");// --组织

				String itemtype = ItemUtil.getItemInfo(itemnum);
				if (ItemUtil.SQN_ITEM.equals(itemtype)) {
					IJpoSet mustchangemprset = mprline
							.getJpoSet("mustchangempr");
					if (!mustchangemprset.isEmpty()) {
						for (int j = 0; j < mustchangemprset.count(); j++) {
							IJpo mustchangempr = mustchangemprset.getJpo(j);
							double amount = mustchangempr.getDouble("amount");// --数量
							String assetnum = mustchangempr
									.getString("assetnum");// --资产编号
							String sqn = mustchangempr.getString("sqn");// --资产编号
							IJpo INVTRANS = INVTRANSset.addJpo();
							INVTRANS.setValue("TRANSTYPE", "入库",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
							INVTRANS.setValue("SQN", sqn,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
							INVTRANS.setValue("QTY", amount,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
							INVTRANS.setValue("STOREROOM", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
							INVTRANS.setValue("TRANSDATE", transdate,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
							INVTRANS.setValue("ITEMNUM", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
							INVTRANS.setValue("assetnum", assetnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
							INVTRANS.setValue("mprnum", mprnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --领料单编号
							INVTRANS.setValue("siteid", siteid,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
							INVTRANS.setValue("orgid", orgid,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
						}
					}
				} else if (ItemUtil.LOT_I_ITEM.equals(itemtype)) {
					IJpoSet mustchangemprset = mprline
							.getJpoSet("mustchangempr");
					if (!mustchangemprset.isEmpty()) {
						for (int j = 0; j < mustchangemprset.count(); j++) {
							IJpo mustchangempr = mustchangemprset.getJpo(j);
							double amount = mustchangempr.getDouble("amount");// --数量
							String lotnum = mustchangempr.getString("lotnum");// --资产编号
							IJpo INVTRANS = INVTRANSset.addJpo();
							INVTRANS.setValue("TRANSTYPE", "入库",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
							INVTRANS.setValue("LOTNUM", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --次号
							INVTRANS.setValue("QTY", amount,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
							INVTRANS.setValue("STOREROOM", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
							INVTRANS.setValue("TRANSDATE", transdate,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
							INVTRANS.setValue("ITEMNUM", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
							INVTRANS.setValue("mprnum", mprnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --领料单编号
							INVTRANS.setValue("siteid", siteid,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
							INVTRANS.setValue("orgid", orgid,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
						}
					}
				} else {
					IJpo INVTRANS = INVTRANSset.addJpo();
					INVTRANS.setValue("TRANSTYPE", "入库",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
					INVTRANS.setValue("QTY", qty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
					INVTRANS.setValue("STOREROOM", location,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
					INVTRANS.setValue("TRANSDATE", transdate,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
					INVTRANS.setValue("ITEMNUM", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
					INVTRANS.setValue("mprnum", mprnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --领料单编号
					INVTRANS.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
					INVTRANS.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
				}

			}
			INVTRANSset.save();
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void mprout(IJpoSet mprlineset) { // 领料单工作流调用出库
		try {
			IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
					"INVTRANS", MroServer.getMroServer().getSystemUserServer());
			for (int i = 0; i < mprlineset.count(); i++) {
				IJpo mprline = mprlineset.getJpo(i);
				Date transdate = MroServer.getMroServer().getDate();// --交易时间
				double qty = mprline.getDouble("qty");// --数量
				String location = mprline.getParent().getString("MPRSTOREROOM");// --库房
				String itemnum = mprline.getString("itemnum");// --物料编码
				String mprnum = mprline.getString("mprnum");// --领料单编号
				String siteid = mprline.getString("siteid");// --地点
				String orgid = mprline.getString("orgid");// --组织

				String type = ItemUtil.getItemInfo(itemnum);
				if (ItemUtil.SQN_ITEM.equals(type)) {
					IJpoSet mustchangemprset = mprline
							.getJpoSet("mustchangempr");
					if (!mustchangemprset.isEmpty()) {
						for (int j = 0; j < mustchangemprset.count(); j++) {
							IJpo mustchangempr = mustchangemprset.getJpo(j);
							double amount = mustchangempr.getDouble("amount");// --数量
							String assetnum = mustchangempr
									.getString("assetnum");// --资产编号
							String sqn = mustchangempr.getString("sqn");// --资产编号
							IJpo INVTRANS = INVTRANSset.addJpo();
							INVTRANS.setValue("TRANSTYPE", "出库",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
							INVTRANS.setValue("SQN", sqn,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
							INVTRANS.setValue("QTY", amount,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
							INVTRANS.setValue("STOREROOM", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
							INVTRANS.setValue("TRANSDATE", transdate,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
							INVTRANS.setValue("ITEMNUM", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
							INVTRANS.setValue("assetnum", assetnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
							INVTRANS.setValue("mprnum", mprnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --领料单编号
							INVTRANS.setValue("siteid", siteid,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
							INVTRANS.setValue("orgid", orgid,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
						}
					}
				} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
					IJpoSet mustchangemprset = mprline
							.getJpoSet("mustchangempr");
					if (!mustchangemprset.isEmpty()) {
						for (int j = 0; j < mustchangemprset.count(); j++) {
							IJpo mustchangempr = mustchangemprset.getJpo(j);
							double amount = mustchangempr.getDouble("amount");// --数量
							String lotnum = mustchangempr.getString("lotnum");// --资产编号
							IJpo INVTRANS = INVTRANSset.addJpo();
							INVTRANS.setValue("TRANSTYPE", "出库",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
							INVTRANS.setValue("LOTNUM", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --次号
							INVTRANS.setValue("QTY", amount,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
							INVTRANS.setValue("STOREROOM", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
							INVTRANS.setValue("TRANSDATE", transdate,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
							INVTRANS.setValue("ITEMNUM", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
							INVTRANS.setValue("mprnum", mprnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --领料单编号
							INVTRANS.setValue("siteid", siteid,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
							INVTRANS.setValue("orgid", orgid,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
						}
					}
				} else {
					IJpo INVTRANS = INVTRANSset.addJpo();
					INVTRANS.setValue("TRANSTYPE", "出库",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
					INVTRANS.setValue("QTY", qty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
					INVTRANS.setValue("STOREROOM", location,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
					INVTRANS.setValue("TRANSDATE", transdate,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
					INVTRANS.setValue("ITEMNUM", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
					INVTRANS.setValue("mprnum", mprnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --领料单编号
					INVTRANS.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
					INVTRANS.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
				}

			}
			INVTRANSset.save();
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void reqmprout(IJpoSet mprlineset) { // 出库
		try {
			IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
					"INVTRANS", MroServer.getMroServer().getSystemUserServer());
			for (int i = 0; i < mprlineset.count(); i++) {
				IJpo mprline = mprlineset.getJpo(i);
				Date transdate = MroServer.getMroServer().getDate();// --交易时间
				double qty = mprline.getDouble("qty");// --数量
				String location = mprline.getParent().getString("MPRSTOREROOM");// --库房
				String itemnum = mprline.getString("itemnum");// --物料编码
				String mprnum = mprline.getString("mprnum");// --领料单编号
				String siteid = mprline.getString("siteid");// --地点
				String orgid = mprline.getString("orgid");// --组织
				String assetnum = mprline.getString("assetnum");// --资产编号
				String sqn = mprline.getString("sqn");// --序列号
				String lotnum = mprline.getString("lotnum");// --批次号

				IJpo INVTRANS = INVTRANSset.addJpo();
				INVTRANS.setValue("TRANSTYPE", "出库",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
				INVTRANS.setValue("QTY", qty,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
				INVTRANS.setValue("STOREROOM", location,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
				INVTRANS.setValue("TRANSDATE", transdate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
				INVTRANS.setValue("ITEMNUM", itemnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
				INVTRANS.setValue("mprnum", mprnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --领料单编号
				INVTRANS.setValue("siteid", siteid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
				INVTRANS.setValue("orgid", orgid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
				INVTRANS.setValue("assetnum", assetnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
				INVTRANS.setValue("sqn", sqn,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --序列号
				INVTRANS.setValue("lotnum", lotnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号

			}
			INVTRANSset.save();
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void reqmprin(IJpoSet mprlineset) { // 入库
		try {
			IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
					"INVTRANS", MroServer.getMroServer().getSystemUserServer());
			for (int i = 0; i < mprlineset.count(); i++) {
				IJpo mprline = mprlineset.getJpo(i);
				Date transdate = MroServer.getMroServer().getDate();// --交易时间
				double qty = mprline.getDouble("qty");// --数量
				String location = mprline.getParent().getString("instoreroom");// --库房
				String itemnum = mprline.getString("itemnum");// --物料编码
				String mprnum = mprline.getString("mprnum");// --领料单编号
				String assetnum = mprline.getString("assetnum");// --资产编号
				String sqn = mprline.getString("sqn");// --序列号
				String lotnum = mprline.getString("lotnum");// --批次号
				String siteid = mprline.getString("siteid");// --地点
				String orgid = mprline.getString("orgid");// --组织

				IJpo INVTRANS = INVTRANSset.addJpo();
				INVTRANS.setValue("TRANSTYPE", "入库",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
				INVTRANS.setValue("QTY", qty,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
				INVTRANS.setValue("STOREROOM", location,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
				INVTRANS.setValue("TRANSDATE", transdate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
				INVTRANS.setValue("ITEMNUM", itemnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
				INVTRANS.setValue("mprnum", mprnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --领料单编号
				INVTRANS.setValue("assetnum", assetnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
				INVTRANS.setValue("sqn", sqn,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --序列号
				INVTRANS.setValue("lotnum", lotnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号
				INVTRANS.setValue("siteid", siteid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
				INVTRANS.setValue("orgid", orgid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织

			}
			INVTRANSset.save();
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
