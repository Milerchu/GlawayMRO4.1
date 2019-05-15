package com.glaway.sddq.material.inventory.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <配件申请中申请占用数量字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-9-3]
 * @since [产品/模块版本]
 */
public class FldSqzyqty extends JpoField {
	/**
	 * 计算申请占用数量
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();
		if (getJpo() != null
				&& !StringUtil.isNullOrEmpty(this.getJpo().getAppName())) {
			if (this.getJpo().getAppName().equalsIgnoreCase("GZINV")
					|| this.getJpo().getAppName().equalsIgnoreCase("INV1030")
					|| this.getJpo().getAppName().equalsIgnoreCase("INVENTORY")
					|| this.getJpo().getAppName().equalsIgnoreCase("QTINV")
					|| this.getJpo().getAppName().equalsIgnoreCase("STOREROOM") || this.getJpo().getAppName().equalsIgnoreCase("JXJHD")) {
				// 调用计算数量方法
				setValue(getSumCurbaltotal() + "",
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			}
		}
	}

	/**
	 * 
	 * <计算数量方法>
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private double getSumCurbaltotal() throws MroException {
		double total = 0;
		if (getJpo() != null) {
			String location = this.getJpo().getString("location");
			String itemnum = this.getJpo().getString("itemnum");
			IJpoSet mrlinetransferset = MroServer.getMroServer().getSysJpoSet(
					"mrlinetransfer");
			mrlinetransferset.setUserWhere("STOREROOM='" + location
					+ "' and itemnum='" + itemnum
					+ "' and transtype not in ('计划经理协调','退回申请人','下达计划','返修后发运','计划交付后发货')");
			mrlinetransferset.reset();
			if (!mrlinetransferset.isEmpty()) {
				/* 配件申请中所有申请已发出的数量 */
				double sumSENDQTY = mrlinetransferset.sum("SENDQTY");
				/* 配件申请中所有申请的数量 */
				double sumsqqty = 0.00;
				for (int i = 0; i < mrlinetransferset.count(); i++) {
					IJpo mrlinetransfer = mrlinetransferset.getJpo(i);
					sumsqqty += mrlinetransfer.getDouble("transferqty");
				}
				total = sumsqqty - sumSENDQTY;
			}
		}
		return total;
	}

}
