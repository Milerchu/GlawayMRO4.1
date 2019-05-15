package com.glaway.sddq.material.invtrans.common;

import java.util.Date;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <服务，检修 工单出入库记录公共方法>
 * 
 * @author public2795
 * @version [版本号, 2018-8-9]
 * @since [产品/模块版本]
 */
public final class CommonAddInvtrans {

	private static CommonAddInvtrans wfCtrl = null;;

	private CommonAddInvtrans() {
	}

	public synchronized static CommonAddInvtrans getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new CommonAddInvtrans();
		}
		return wfCtrl;
	}

	/**
	 * 
	 * <出库记录调用方法>
	 * 
	 * @param sqn
	 *            序列号
	 * @param lotnum
	 *            批次号
	 * @param qty
	 *            数量
	 * @param location
	 *            库房编码
	 * @param transdate
	 *            交易日期
	 * @param tasknum
	 *            工单编号
	 * @param itemnum
	 *            物料编码
	 * @param assetnum
	 *            序列号唯一资产编号
	 * 
	 */
	public static void OUTINVTRANS(String sqn, String lotnum, double qty,
			String location, Date transdate, String tasknum, String itemnum,
			String assetnum) { // 出库交易记录
		try {

			IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
					"INVTRANS", MroServer.getMroServer().getSystemUserServer());
			IJpo INVTRANS = INVTRANSset.addJpo();
			INVTRANS.setValue("TRANSTYPE", "出库",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
			INVTRANS.setValue("SQN", sqn,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
			INVTRANS.setValue("LOTNUM", lotnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号
			INVTRANS.setValue("QTY", qty,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
			INVTRANS.setValue("STOREROOM", location,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
			INVTRANS.setValue("TRANSDATE", transdate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
			INVTRANS.setValue("TASKNUM", tasknum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --工单编号
			INVTRANS.setValue("ITEMNUM", itemnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
			INVTRANS.setValue("assetnum", assetnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编
			INVTRANSset.save();
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <入库记录调用方法>
	 * 
	 * @param sqn
	 *            序列号
	 * @param lotnum
	 *            批次号
	 * @param qty
	 *            数量
	 * @param location
	 *            库房编码
	 * @param transdate
	 *            交易日期
	 * @param tasknum
	 *            工单编号
	 * @param itemnum
	 *            物料编码
	 * @param assetnum
	 *            序列号唯一资产编号
	 * 
	 */
	public static void ININVTRANS(String sqn, String lotnum, double qty,
			String location, Date transdate, String tasknum, String itemnum,
			String assetnum) { // 入库交易记录
		try {
			IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
					"INVTRANS", MroServer.getMroServer().getSystemUserServer());
			IJpo INVTRANS = INVTRANSset.addJpo();
			INVTRANS.setValue("TRANSTYPE", "入库",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
			INVTRANS.setValue("SQN", sqn,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
			INVTRANS.setValue("LOTNUM", lotnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号
			INVTRANS.setValue("QTY", qty,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
			INVTRANS.setValue("STOREROOM", location,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
			INVTRANS.setValue("TRANSDATE", transdate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
			INVTRANS.setValue("TASKNUM", tasknum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --工单编号
			INVTRANS.setValue("ITEMNUM", itemnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
			INVTRANS.setValue("assetnum", assetnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
			INVTRANSset.save();
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
