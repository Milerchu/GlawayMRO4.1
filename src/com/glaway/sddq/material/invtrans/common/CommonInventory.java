package com.glaway.sddq.material.invtrans.common;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <服务，检修 工单出入库公共方法>
 * 
 * @author public2795
 * @version [版本号, 2018-8-9]
 * @since [产品/模块版本]
 */
public class CommonInventory {
	private static CommonInventory wfCtrl = null;;

	private CommonInventory() {
	}

	public synchronized static CommonInventory getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new CommonInventory();
		}
		return wfCtrl;
	}

	/**
	 * 
	 * <出库方法>
	 * 
	 * @param lotnum
	 *            批次号
	 * @param qty
	 *            数量
	 * @param location
	 *            库房编码
	 * @param itemnum
	 *            物料编码
	 * @param assetnum
	 *            序列号唯一资产编码
	 * @param tasknum
	 *            工单编号
	 * 
	 */
	public static void OUTINVENTORY(String lotnum, double qty, String location,
			String itemnum, String assetnum, String tasknum) { // 出库
		try {

			String sqn = "";
			java.util.Date transdate = MroServer.getMroServer().getDate();
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultOrg("CRRC");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultSite("ELEC");
			IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",
					MroServer.getMroServer().getSystemUserServer());
			itemset.setUserWhere("itemnum='" + itemnum + "'");

			String type = ItemUtil.getItemInfo(itemnum);
			if (ItemUtil.SQN_ITEM.equals(type)) {// --判断是周转件
				IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
						MroServer.getMroServer().getSystemUserServer());// --对应的周转件集合
				assetset.setUserWhere("assetnum='" + assetnum + "'");
				assetset.reset();
				if (!assetset.isEmpty()) {
					IJpo asset = assetset.getJpo(0);
					sqn = asset.getString("sqn");
					IJpoSet out_inventoryset = MroServer.getMroServer()
							.getJpoSet(
									"sys_inventory",
									MroServer.getMroServer()
											.getSystemUserServer());// --对应的出库库存集合
					out_inventoryset.setUserWhere("itemnum='" + itemnum
							+ "' and location='" + location + "'");
					out_inventoryset.reset();
					if (!out_inventoryset.isEmpty()) {
						IJpo out_inventory = out_inventoryset.getJpo(0);
						double CURBAL = out_inventory.getDouble("CURBAL");
						double newCURBAL = CURBAL - qty;
						double locqty = out_inventory.getDouble("locqty");
						double newlocqty = locqty - qty;
						out_inventory.setValue("CURBAL", newCURBAL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						out_inventory.setValue("locqty", newlocqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						out_inventoryset.save();
					}
					asset.setValue("location", "",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					assetset.save();
				}
			} else if (ItemUtil.LOT_I_ITEM.equals(type)) {// --判断是批次件
				IJpoSet out_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --对应的出库库存集合
				out_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + location + "'");
				out_inventoryset.reset();
				if (!out_inventoryset.isEmpty()) {
					IJpo out_inventory = out_inventoryset.getJpo(0);
					double CURBAL = out_inventory.getDouble("CURBAL");
					double newCURBAL = CURBAL - qty;
					double locqty = out_inventory.getDouble("locqty");
					double newlocqty = locqty - qty;
					if (!lotnum.equalsIgnoreCase("")) {
						IJpoSet invblanceset = MroServer.getMroServer()
								.getJpoSet(
										"invblance",
										MroServer.getMroServer()
												.getSystemUserServer());// --对应的周转件集合
						invblanceset.setUserWhere("itemnum='" + itemnum
								+ "' and storeroom='" + location
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty - qty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
					}
					out_inventory.setValue("CURBAL", newCURBAL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					out_inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					out_inventoryset.save();
				}
			} else {// --判断即不是周转件也不是批次件
				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨出库库存的集合
				inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + location + "'");
				inventoryset.reset();
				if (!inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.getJpo(0);
					double CURBAL = inventory.getDouble("CURBAL");
					double newcurbal = CURBAL - qty;
					double locqty = inventory.getDouble("locqty");
					double newlocqty = locqty - qty;
					inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();
				}
			}
			// 调用出库记录公用方法
			CommonAddInvtrans.OUTINVTRANS(sqn, lotnum, qty, location,
					transdate, tasknum, itemnum, assetnum);
			InventoryQtyCommon.SQZYQTY(itemnum, location);
			InventoryQtyCommon.FCZTQTY(itemnum, location);
			InventoryQtyCommon.JSZTQTY(itemnum, location);
			InventoryQtyCommon.KYQTY(itemnum, location);
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <入库方法>
	 * 
	 * @param lotnum
	 *            批次号
	 * @param qty
	 *            数量
	 * @param location
	 *            库房编码
	 * @param itemnum
	 *            物料编码
	 * @param assetnum
	 *            序列号唯一资产编码
	 * @param tasknum
	 *            工单编号
	 * 
	 */
	public static void ININVENTORY(String lotnum, double qty, String location,
			String itemnum, String assetnum, String tasknum) { // 入库
		try {
			String sqn = "";
			java.util.Date transdate = MroServer.getMroServer().getDate();
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultOrg("CRRC");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultSite("ELEC");
			IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",
					MroServer.getMroServer().getSystemUserServer());
			itemset.setUserWhere("itemnum='" + itemnum + "'");
			String type = ItemUtil.getItemInfo(itemnum);
			if (ItemUtil.SQN_ITEM.equals(type)) {// --判断是周转件
				IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
						MroServer.getMroServer().getSystemUserServer());// --对应的周转件集合
				assetset.setUserWhere("assetnum='" + assetnum + "'");
				assetset.reset();
				if (!assetset.isEmpty()) {
					IJpo asset = assetset.getJpo(0);
					sqn = asset.getString("sqn");
					String commomtype ="入库";
					IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
							"sys_inventory",
							MroServer.getMroServer().getSystemUserServer());// --入库库存的集合
					inventoryset.setUserWhere("itemnum='" + itemnum
							+ "' and location='" + location + "'");
					inventoryset.reset();
					if (!inventoryset.isEmpty()) {
						IJpo inventory = inventoryset.getJpo(0);
						double CURBAL = inventory.getDouble("CURBAL");
						double newCURBAL = CURBAL + qty;
						double faultqty = inventory.getDouble("faultqty");
						double newfaultqty = faultqty + qty;
						// inventory.setValue("faultqty",
						// newfaultqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("CURBAL", newCURBAL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

						inventoryset.save();
						asset.setValue("location", location,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//						CommomCarItemLife.INOROURLOCATION(asset, commomtype);// 一物一档自动计算
						assetset.save();
					}
					if (inventoryset.isEmpty()) {
						IJpo inventory = inventoryset.addJpo();
						inventory.setValue("itemnum", itemnum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// inventory.setValue("faultqty",
						// qty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("CURBAL", qty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("location", location,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventoryset.save();

						asset.setValue("location", location,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						asset.setValue("INSTOREDATE", transdate,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//						CommomCarItemLife.INOROURLOCATION(asset, commomtype);// 一物一档自动计算
						assetset.save();
					}
				}
			} else if (ItemUtil.LOT_I_ITEM.equals(type)) {// --判断是批次件
				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --入库库存的集合
				inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + location + "'");
				inventoryset.reset();
				if (!inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.getJpo(0);
					double CURBAL = inventory.getDouble("CURBAL");
					double newCURBAL = CURBAL + qty;
					double faultqty = inventory.getDouble("faultqty");
					double newfaultqty = faultqty + qty;

					if (!lotnum.equalsIgnoreCase("")) {
						IJpoSet invblanceset = MroServer.getMroServer()
								.getJpoSet(
										"invblance",
										MroServer.getMroServer()
												.getSystemUserServer());// --对应的周转件集合
						invblanceset.setUserWhere("itemnum='" + itemnum
								+ "' and storeroom='" + location
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty + qty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();

						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", qty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
					}
					inventory.setValue("CURBAL", newCURBAL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// inventory.setValue("faultqty",
					// newfaultqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();
				}
				if (inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.addJpo();
					if (!lotnum.equalsIgnoreCase("")) {
						IJpoSet invblanceset = MroServer.getMroServer()
								.getJpoSet(
										"invblance",
										MroServer.getMroServer()
												.getSystemUserServer());// --对应的批次件集合
						invblanceset.setUserWhere("itemnum='" + itemnum
								+ "' and storeroom='" + location
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty + qty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();

						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", qty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
					}
					inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("CURBAL", qty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// inventory.setValue("faultqty",
					// qty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("location", location,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();
				}
			} else {// --判断即不是周转件也不是批次件
				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --入库库存的集合
				inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + location + "'");
				inventoryset.reset();
				if (!inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.getJpo(0);
					double CURBAL = inventory.getDouble("CURBAL");
					double newcurbal = CURBAL + qty;
					double faultqty = inventory.getDouble("faultqty");
					double newfaultqty = faultqty + qty;
					// inventory.setValue("faultqty",
					// newfaultqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();
				}
				if (inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.addJpo();
					inventory.setValue("CURBAL", qty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// inventory.setValue("faultqty",
					// qty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("location", location,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();
				}
			}
			// 调用入库记录公共方法
			CommonAddInvtrans.ININVTRANS(sqn, lotnum, qty, location, transdate,
					tasknum, itemnum, assetnum);
			InventoryQtyCommon.SQZYQTY(itemnum, location);
			InventoryQtyCommon.FCZTQTY(itemnum, location);
			InventoryQtyCommon.JSZTQTY(itemnum, location);
			InventoryQtyCommon.KYQTY(itemnum, location);
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
