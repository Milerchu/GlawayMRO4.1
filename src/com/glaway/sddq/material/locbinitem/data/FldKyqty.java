package com.glaway.sddq.material.locbinitem.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <仓位物料表可用数量字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
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
		// TODO Auto-generated method stub
		super.init();
		String lotnum = this.getJpo().getString("lotnum");
		String binnum = this.getJpo().getString("binnum");
		String itemnum = this.getJpo().getString("itemnum");
		String location = this.getJpo().getString("location");
		double qty = this.getJpo().getDouble("qty");
		if (!lotnum.equalsIgnoreCase("")) {
			IJpoSet transferlineset = MroServer.getMroServer().getJpoSet(
					"transferline",
					MroServer.getMroServer().getSystemUserServer());
			transferlineset
					.setQueryWhere("lotnum='"
							+ lotnum
							+ "' and outbinnum='"
							+ binnum
							+ "' and itemnum='"
							+ itemnum
							+ "' and transfernum in (select transfernum from transfer where status='在途' and ISSUESTOREROOM='"
							+ location + "' and type in ('SX','ZXD'))");
			transferlineset.reset();
			if (transferlineset.count() > 0) {
				double fcztqty = 0;
				double kyqty = 0;
				fcztqty = transferlineset.sum("ORDERQTY");
				kyqty = qty - fcztqty;
				this.getJpo().setValue("kyqty", kyqty,
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			} else {
				double kyqty = 0;
				kyqty = qty;
				this.getJpo().setValue("kyqty", kyqty,
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			}

		} else {
			IJpoSet transferlineset = MroServer.getMroServer().getJpoSet(
					"transferline",
					MroServer.getMroServer().getSystemUserServer());
			transferlineset
					.setQueryWhere("outbinnum='"
							+ binnum
							+ "' and itemnum='"
							+ itemnum
							+ "' and transfernum in (select transfernum from transfer where status='在途' and ISSUESTOREROOM='"
							+ location + "' and type in ('SX','ZXD'))");
			transferlineset.reset();
			if (transferlineset.count() > 0) {
				double fcztqty = 0;
				double kyqty = 0;
				fcztqty = transferlineset.sum("ORDERQTY");
				kyqty = qty - fcztqty;
				this.getJpo().setValue("kyqty", kyqty,
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			} else {
				double kyqty = 0;
				kyqty = qty;
				this.getJpo().setValue("kyqty", kyqty,
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			}
		}
	}
}
