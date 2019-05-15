package com.glaway.sddq.material.invtrans.common;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨管理库存增加无库存接收在途数据公共类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class CommonAddNewInventory {
	private static CommonAddNewInventory wfCtrl = null;;

	private CommonAddNewInventory() {
	}

	public synchronized static CommonAddNewInventory getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new CommonAddNewInventory();
		}
		return wfCtrl;
	}

	/**
	 * 
	 * <增加库存数据>
	 * 
	 * @param transferlineset
	 *            [参数说明]
	 * 
	 */
	public static void addinventory(IJpoSet transferlineset) {
		try {
			if (!transferlineset.isEmpty()) {
				for (int i = 0; i < transferlineset.count(); i++) {
					IJpo transferline = transferlineset.getJpo(i);
					String itemnum = transferline.getString("itemnum");
					String siteid = transferline.getString("siteid");
					String orgid = transferline.getString("orgid");
					String receivestoreroom = transferline
							.getString("receivestoreroom");
					IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
							"sys_inventory",
							MroServer.getMroServer().getSystemUserServer());
					inventoryset.setUserWhere("itemnum='" + itemnum
							+ "' and location='" + receivestoreroom + "'");
					inventoryset.reset();
					if (inventoryset.isEmpty()) {
						IJpo inventory = inventoryset.addJpo();
						inventory.setValue("CURBAL", 0,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("locqty", 0,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("itemnum", itemnum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("location", receivestoreroom,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("siteid", siteid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("orgid", orgid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventoryset.save();
					}
				}
			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
