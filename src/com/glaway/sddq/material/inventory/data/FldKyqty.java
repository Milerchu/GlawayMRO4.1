package com.glaway.sddq.material.inventory.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <库存表可用数量字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class FldKyqty extends JpoField {
	/**
	 * 计算可用数量
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
					|| this.getJpo().getAppName().equalsIgnoreCase("STOREROOM") || this.getJpo().getAppName().equalsIgnoreCase("JXJHD")) {
				String itemnum = this.getJpo().getString("itemnum");
				String location = this.getJpo().getString("location");
				double FCZTQTY = 0;// 发出在途数量
				IJpoSet transferlineset = MroServer.getMroServer().getJpoSet(
						"transferline",
						MroServer.getMroServer().getSystemUserServer());
				transferlineset
						.setUserWhere("itemnum='"
								+ itemnum
								+ "' and ISSUESTOREROOM='"
								+ location
								+ "'  and transfernum in (select transfernum from transfer where status='在途' and type in ('SX','ZXD'))");
				transferlineset.reset();
				if (transferlineset.count() > 0) {
					FCZTQTY = transferlineset.sum("ORDERQTY");
				}
				double curbal = this.getJpo().getDouble("curbal");// 库存总数
				double sqzyqty = this.getJpo().getDouble("sqzyqty");// 申请占用数量
				double DISPOSEQTY = this.getJpo().getDouble("DISPOSEQTY");// 待处理数量
				double FAULTQTY = this.getJpo().getDouble("FAULTQTY");// 故障数量
				double TESTINGQTY = this.getJpo().getDouble("TESTINGQTY");// 待检测数量

				double kyqty = curbal - FCZTQTY - sqzyqty - DISPOSEQTY
						- FAULTQTY - TESTINGQTY;
				this.getJpo().setValue("kyqty", kyqty,
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			}
		}
	}
}
