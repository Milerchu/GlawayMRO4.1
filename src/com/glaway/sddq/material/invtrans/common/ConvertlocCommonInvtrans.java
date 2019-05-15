package com.glaway.sddq.material.invtrans.common;

import java.util.Date;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨转库单，入库单入库记录公共类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class ConvertlocCommonInvtrans {

	private static ConvertlocCommonInvtrans wfCtrl = null;;

	private ConvertlocCommonInvtrans() {
	}

	public synchronized static ConvertlocCommonInvtrans getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new ConvertlocCommonInvtrans();
		}
		return wfCtrl;
	}

	/**
	 * 
	 * <入库记录方法>
	 * 
	 * @param lotnum
	 *            批次号
	 * @param statusjsqty
	 *            接收数量
	 * @param location
	 *            库房编码
	 * @param itemnum
	 *            物料编码
	 * @param binnum
	 *            仓位编码
	 * @param sqn
	 *            序列号
	 * @param transdate
	 *            交易时间
	 * 
	 */
	public static void ININVTRANS(String lotnum, double statusjsqty,
			String location, String itemnum, String binnum, String sqn,
			Date transdate) { // 入库交易记录
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
			INVTRANS.setValue("QTY", statusjsqty,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
			INVTRANS.setValue("STOREROOM", location,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
			INVTRANS.setValue("TRANSDATE", transdate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
			INVTRANS.setValue("ITEMNUM", itemnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
			INVTRANS.setValue("binnum", binnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
			INVTRANSset.save();
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * <退库单出库记录>
	 * @param lotnum
	 * @param statusjsqty
	 * @param location
	 * @param itemnum
	 * @param binnum
	 * @param sqn
	 * @param transdate [参数说明]
	 *
	 */
	public static void OUTINVTRANS(String lotnum, double statusjsqty,
			String location, String itemnum, String binnum, String sqn,
			Date transdate) { // 入库交易记录
		try {
			IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
					"INVTRANS", MroServer.getMroServer().getSystemUserServer());
			IJpo INVTRANS = INVTRANSset.addJpo();
			INVTRANS.setValue("TRANSTYPE", "退库",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
			INVTRANS.setValue("SQN", sqn,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
			INVTRANS.setValue("LOTNUM", lotnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号
			INVTRANS.setValue("QTY", statusjsqty,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
			INVTRANS.setValue("STOREROOM", location,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
			INVTRANS.setValue("TRANSDATE", transdate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
			INVTRANS.setValue("ITEMNUM", itemnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
			INVTRANS.setValue("binnum", binnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
			INVTRANSset.save();
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
