package com.glaway.sddq.material.inventory.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <库存表接收在途字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class FldJsztqty extends JpoField {
	/**
	 * 计算接收在途数量
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		if (getJpo() != null
				&& !StringUtil.isNullOrEmpty(this.getJpo().getAppName())) {
			if (this.getJpo().getAppName().equalsIgnoreCase("GZINV")
					|| this.getJpo().getAppName().equalsIgnoreCase("INV1030")
					|| this.getJpo().getAppName().equalsIgnoreCase("INVENTORY")
					|| this.getJpo().getAppName().equalsIgnoreCase("QTINV")
					|| this.getJpo().getAppName().equalsIgnoreCase("STOREROOM") || this.getJpo().getAppName().equalsIgnoreCase("JXJHD") ) {
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
			String itemnum = this.getJpo().getString("itemnum");
			String location = this.getJpo().getString("location");
			IJpoSet transferlineset = MroServer.getMroServer().getJpoSet(
					"transferline",
					MroServer.getMroServer().getSystemUserServer());
			transferlineset
					.setUserWhere("itemnum='"
							+ itemnum
							+ "' and RECEIVESTOREROOM='"
							+ location
							+ "' and transfernum in (select transfernum from transfer where status='在途' and type in ('SX','ZXD'))");
			transferlineset.reset();
			if (transferlineset.count() > 0) {
				total = transferlineset.sum("ORDERQTY");
			}
		}
		return total;
	}
}
