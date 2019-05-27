package com.glaway.sddq.material.invblance.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <库存管理批次信息发出在途字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class FldFcztqty extends JpoField {
	/**
	 * 计算发出在途数量
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String lotnum = this.getJpo().getString("lotnum");
		String itemnum = this.getJpo().getString("itemnum");
		String STOREROOM = this.getJpo().getString("STOREROOM");
		if (!lotnum.equalsIgnoreCase("")) {
			IJpoSet transferlineset = MroServer.getMroServer().getJpoSet(
					"transferline",
					MroServer.getMroServer().getSystemUserServer());
			transferlineset
					.setUserWhere("lotnum='"
							+ lotnum
							+ "' and itemnum='"
							+ itemnum
							+ "' and transfernum in (select transfernum from transfer where status='在途' and ISSUESTOREROOM='"
							+ STOREROOM + "' and type in ('SX','ZXD'))");
			transferlineset.reset();
			if (transferlineset.count() > 0) {
				double fcztqty = 0;
				fcztqty = transferlineset.sum("ORDERQTY");
				this.getJpo().setValue("fcztqty", fcztqty,
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			}
		}
	}

}
