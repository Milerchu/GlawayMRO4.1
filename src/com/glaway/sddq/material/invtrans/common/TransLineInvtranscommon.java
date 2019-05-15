package com.glaway.sddq.material.invtrans.common;

import java.util.Date;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨单行点收库存交易记录公共类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-8]
 * @since [产品/模块版本]
 */
public class TransLineInvtranscommon {
	private static TransLineInvtranscommon wfCtrl = null;;

	private TransLineInvtranscommon() {
	}

	public synchronized static TransLineInvtranscommon getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new TransLineInvtranscommon();
		}
		return wfCtrl;
	}

	/**
	 * 
	 * <入库记录方法>
	 * 
	 * @param transferline
	 * @param dsqty
	 *            [参数说明]
	 * 
	 */
	public static void in_invtrans(IJpo transferline, double dsqty) { // 调拨入库-库存交易记录
		try {
			IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
					"INVTRANS", MroServer.getMroServer().getSystemUserServer());
			String sqn = transferline.getString("sqn");// --产品序列号
			String lotnum = transferline.getString("lotnum");// --批次号
			String itemnum = transferline.getString("itemnum");// --物料编码
			String assetnum = transferline.getString("assetnum");// --资产编号
			String transfernum = transferline.getString("transfernum");// --调拨单编号
			String inbinnum = transferline.getString("inbinnum");// --入库仓位
			String tasknum = transferline.getString("tasknum");// --工单编号
			Date indate = MroServer.getMroServer().getDate();// --入库时间
			// double orderqty=transferline.getDouble("orderqty");//--调拨数量
			String receivestoreroom = transferline
					.getString("receivestoreroom");

			String siteid = transferline.getString("siteid");// --地点
			String orgid = transferline.getString("orgid");// --组织

			IJpo INVTRANS_IN = INVTRANSset.addJpo(); // 入库记录
			INVTRANS_IN.setValue("TRANSTYPE", "调拨入库",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
			INVTRANS_IN.setValue("SQN", sqn,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
			INVTRANS_IN.setValue("LOTNUM", lotnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号
			INVTRANS_IN.setValue("QTY", dsqty,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
			INVTRANS_IN.setValue("STOREROOM", receivestoreroom,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
			INVTRANS_IN.setValue("BINNUM", inbinnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --仓位
			INVTRANS_IN.setValue("TRANSDATE", indate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
			INVTRANS_IN.setValue("TASKNUM", tasknum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --工单编号
			INVTRANS_IN.setValue("TRANSFERNUM", transfernum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --调拨单编号
			INVTRANS_IN.setValue("ITEMNUM", itemnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
			INVTRANS_IN.setValue("assetnum", assetnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
			INVTRANS_IN.setValue("siteid", siteid,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
			INVTRANS_IN.setValue("orgid", orgid,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织

			INVTRANSset.save();

		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <出库记录方法>
	 * 
	 * @param transferline
	 * @param dsqty
	 *            [参数说明]
	 * 
	 */
	public static void out_invtrans(IJpo transferline, double dsqty) { // 调拨出库-库存交易记录
		try {
			IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
					"INVTRANS", MroServer.getMroServer().getSystemUserServer());

			String sqn = transferline.getString("sqn");// --产品序列号
			String lotnum = transferline.getString("lotnum");// --批次号
			String itemnum = transferline.getString("itemnum");// --物料编码
			String assetnum = transferline.getString("assetnum");// --资产编号
			String transfernum = transferline.getString("transfernum");// --调拨单编号
			String outbinnum = transferline.getString("outbinnum");// --出库仓位
			String tasknum = transferline.getString("tasknum");// --工单编号
			Date outdate = MroServer.getMroServer().getDate();// --出库时间
			String issuestoreroom = transferline.getString("issuestoreroom");// --发出库房
			String siteid = transferline.getString("siteid");// --地点
			String orgid = transferline.getString("orgid");// --组织

			IJpo INVTRANS_OUT = INVTRANSset.addJpo(); // 出库记录
			INVTRANS_OUT.setValue("TRANSTYPE", "调拨出库",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
			INVTRANS_OUT.setValue("SQN", sqn,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
			INVTRANS_OUT.setValue("LOTNUM", lotnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号
			INVTRANS_OUT.setValue("QTY", dsqty,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
			INVTRANS_OUT.setValue("STOREROOM", issuestoreroom,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --库房
			INVTRANS_OUT.setValue("BINNUM", outbinnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --仓位
			INVTRANS_OUT.setValue("TRANSDATE", outdate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
			INVTRANS_OUT.setValue("TASKNUM", tasknum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --工单编号
			INVTRANS_OUT.setValue("TRANSFERNUM", transfernum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --调拨单编号
			INVTRANS_OUT.setValue("ITEMNUM", itemnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
			INVTRANS_OUT.setValue("assetnum", assetnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
			INVTRANS_OUT.setValue("siteid", siteid,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
			INVTRANS_OUT.setValue("orgid", orgid,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织

			INVTRANSset.save();

		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
