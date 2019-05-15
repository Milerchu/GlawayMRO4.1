package com.glaway.sddq.material.invblance.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <库存批次列表可用数量计算>
 * 
 * @author public2795
 * @version [版本号, 2018-7-31]
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
		String itemnum = this.getJpo().getString("itemnum");
		String STOREROOM = this.getJpo().getString("STOREROOM");
		double qty = 0;
		IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",
				MroServer.getMroServer().getSystemUserServer());
		itemset.setQueryWhere("itemnum='" + itemnum + "'");
		itemset.reset();
		if (!itemset.isEmpty()) {
			String type = ItemUtil.getItemInfo(itemnum);
			if (ItemUtil.SQN_ITEM.equals(type)) {
				IJpoSet assetJpoSet = this.getJpo().getJpoSet("ASSET");
				if (assetJpoSet.isEmpty()) {
					qty = this.getJpo().getDouble("PHYSCNTQTY");
				} else {
					qty = assetJpoSet.count();
				}

			} else {
				qty = this.getJpo().getDouble("PHYSCNTQTY");
			}
			if (!lotnum.equalsIgnoreCase("")) {
				IJpoSet transferlineset = MroServer.getMroServer().getJpoSet(
						"transferline",
						MroServer.getMroServer().getSystemUserServer());
				transferlineset
						.setQueryWhere("lotnum='"
								+ lotnum
								+ "' and itemnum='"
								+ itemnum
								+ "' and transfernum in (select transfernum from transfer where status='在途' and ISSUESTOREROOM='"
								+ STOREROOM + "' and type in ('SX','ZXD'))");
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
}
