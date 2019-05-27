package com.glaway.sddq.material.invtrans.common;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <调拨转库单，入库单入库公共方法>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class ConvertlocCommonInventory {
	private static ConvertlocCommonInventory wfCtrl = null;;

	private ConvertlocCommonInventory() {
	}

	public synchronized static ConvertlocCommonInventory getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new ConvertlocCommonInventory();
		}
		return wfCtrl;
	}

	/**
	 * 
	 * <调拨转库单入库方法>
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
	 *            仓位编号
	 * @param sqn
	 *            序列号
	 * 
	 */
	public static void ININVENTORY(String lotnum, double statusjsqty,
			String location, String itemnum, String binnum, String sqn) { // 入库
		try {
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

				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --入库库存的集合
				inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + location + "'");
				inventoryset.reset();
				if (!inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.getJpo(0);
					double CURBAL = inventory.getDouble("CURBAL");
					double locqty = inventory.getDouble("locqty");/* 非工单数量 */
					double newCURBAL = CURBAL + statusjsqty;
					double newlocqty = locqty + statusjsqty;
					if (!binnum.equalsIgnoreCase("")) {
						IJpoSet locbinitemset = MroServer.getMroServer()
								.getJpoSet(
										"locbinitem",
										MroServer.getMroServer()
												.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
						locbinitemset.setUserWhere("itemnum='" + itemnum
								+ "' and location='" + location
								+ "' and binnum='" + binnum + "'");
						locbinitemset.reset();
						if (!locbinitemset.isEmpty()) {
							IJpo locbinitem = locbinitemset.getJpo(0);
							double qty = locbinitem.getDouble("qty");
							double newqty = qty + statusjsqty;
							locbinitem.setValue("qty", newqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						} else {
							IJpo locbinitem = locbinitemset.addJpo();
							locbinitem.setValue("binnum", binnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("location", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("qty", statusjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						}
					}
					inventory.setValue("CURBAL", newCURBAL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();

				}
				if (inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.addJpo();
					inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("CURBAL", statusjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", statusjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("location", location,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (!binnum.equalsIgnoreCase("")) {
						IJpoSet locbinitemset = MroServer.getMroServer()
								.getJpoSet(
										"locbinitem",
										MroServer.getMroServer()
												.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
						locbinitemset.setUserWhere("itemnum='" + itemnum
								+ "' and location='" + location
								+ "' and binnum='" + binnum + "'");
						locbinitemset.reset();
						if (!locbinitemset.isEmpty()) {
							IJpo locbinitem = locbinitemset.getJpo(0);
							double qty = locbinitem.getDouble("qty");
							double newqty = qty + statusjsqty;
							locbinitem.setValue("qty", newqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						} else {
							IJpo locbinitem = locbinitemset.addJpo();
							locbinitem.setValue("binnum", binnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("location", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("qty", statusjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						}
					}
					inventoryset.save();

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
					double locqty = inventory.getDouble("locqty");
					double newCURBAL = CURBAL + statusjsqty;
					double newlocqty = locqty + statusjsqty;
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
							double newphyscntqty = physcntqty + statusjsqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							if (!binnum.equalsIgnoreCase("")) {
								IJpoSet locbinitemset = MroServer
										.getMroServer().getJpoSet(
												"locbinitem",
												MroServer.getMroServer()
														.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
								locbinitemset.setUserWhere("itemnum='"
										+ itemnum + "' and location='"
										+ location + "' and binnum='" + binnum
										+ "' and lotnum='" + lotnum + "'");
								locbinitemset.reset();
								if (!locbinitemset.isEmpty()) {
									IJpo locbinitem = locbinitemset.getJpo(0);
									double qty = locbinitem.getDouble("qty");
									double newqty = qty + statusjsqty;
									locbinitem
											.setValue(
													"qty",
													newqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								} else {
									IJpo locbinitem = locbinitemset.addJpo();
									locbinitem
											.setValue(
													"binnum",
													binnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"location",
													location,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"itemnum",
													itemnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"qty",
													statusjsqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"lotnum",
													lotnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								}
							}
							invblanceset.save();

						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", statusjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							if (!binnum.equalsIgnoreCase("")) {
								IJpoSet locbinitemset = MroServer
										.getMroServer().getJpoSet(
												"locbinitem",
												MroServer.getMroServer()
														.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
								locbinitemset.setUserWhere("itemnum='"
										+ itemnum + "' and location='"
										+ location + "' and binnum='" + binnum
										+ "' and lotnum='" + lotnum + "'");
								locbinitemset.reset();
								if (!locbinitemset.isEmpty()) {
									IJpo locbinitem = locbinitemset.getJpo(0);
									double qty = locbinitem.getDouble("qty");
									double newqty = qty + statusjsqty;
									locbinitem
											.setValue(
													"qty",
													newqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								} else {
									IJpo locbinitem = locbinitemset.addJpo();
									locbinitem
											.setValue(
													"binnum",
													binnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"location",
													location,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"itemnum",
													itemnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"qty",
													statusjsqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"lotnum",
													lotnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								}
							}
							invblanceset.save();
						}
					}
					inventory.setValue("CURBAL", newCURBAL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
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
							double newphyscntqty = physcntqty + statusjsqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							if (!binnum.equalsIgnoreCase("")) {
								IJpoSet locbinitemset = MroServer
										.getMroServer().getJpoSet(
												"locbinitem",
												MroServer.getMroServer()
														.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
								locbinitemset.setUserWhere("itemnum='"
										+ itemnum + "' and location='"
										+ location + "' and binnum='" + binnum
										+ "' and lotnum='" + lotnum + "'");
								locbinitemset.reset();
								if (!locbinitemset.isEmpty()) {
									IJpo locbinitem = locbinitemset.getJpo(0);
									double qty = locbinitem.getDouble("qty");
									double newqty = qty + statusjsqty;
									locbinitem
											.setValue(
													"qty",
													newqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								} else {
									IJpo locbinitem = locbinitemset.addJpo();
									locbinitem
											.setValue(
													"binnum",
													binnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"location",
													location,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"itemnum",
													itemnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"qty",
													statusjsqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"lotnum",
													lotnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								}
							}
							invblanceset.save();

						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", statusjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							if (!binnum.equalsIgnoreCase("")) {
								IJpoSet locbinitemset = MroServer
										.getMroServer().getJpoSet(
												"locbinitem",
												MroServer.getMroServer()
														.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
								locbinitemset.setUserWhere("itemnum='"
										+ itemnum + "' and location='"
										+ location + "' and binnum='" + binnum
										+ "' and lotnum='" + lotnum + "'");
								locbinitemset.reset();
								if (!locbinitemset.isEmpty()) {
									IJpo locbinitem = locbinitemset.getJpo(0);
									double qty = locbinitem.getDouble("qty");
									double newqty = qty + statusjsqty;
									locbinitem
											.setValue(
													"qty",
													newqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								} else {
									IJpo locbinitem = locbinitemset.addJpo();
									locbinitem
											.setValue(
													"binnum",
													binnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"location",
													location,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"itemnum",
													itemnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"qty",
													statusjsqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"lotnum",
													lotnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								}
							}
							invblanceset.save();
						}
					}
					inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("CURBAL", statusjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", statusjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
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
					double locqty = inventory.getDouble("locqty");
					double newcurbal = CURBAL + statusjsqty;
					double newlocqty = locqty + statusjsqty;
					if (!binnum.equalsIgnoreCase("")) {
						IJpoSet locbinitemset = MroServer.getMroServer()
								.getJpoSet(
										"locbinitem",
										MroServer.getMroServer()
												.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
						locbinitemset.setUserWhere("itemnum='" + itemnum
								+ "' and location='" + location
								+ "' and binnum='" + binnum + "' and lotnum='"
								+ lotnum + "'");
						locbinitemset.reset();
						if (!locbinitemset.isEmpty()) {
							IJpo locbinitem = locbinitemset.getJpo(0);
							double qty = locbinitem.getDouble("qty");
							double newqty = qty + statusjsqty;
							locbinitem.setValue("qty", newqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						} else {
							IJpo locbinitem = locbinitemset.addJpo();
							locbinitem.setValue("binnum", binnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("location", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("qty", statusjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						}
					}
					inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();
				}
				if (inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.addJpo();
					inventory.setValue("CURBAL", statusjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", statusjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("location", location,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (!binnum.equalsIgnoreCase("")) {
						IJpoSet locbinitemset = MroServer.getMroServer()
								.getJpoSet(
										"locbinitem",
										MroServer.getMroServer()
												.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
						locbinitemset.setUserWhere("itemnum='" + itemnum
								+ "' and location='" + location
								+ "' and binnum='" + binnum + "' and lotnum='"
								+ lotnum + "'");
						locbinitemset.reset();
						if (!locbinitemset.isEmpty()) {
							IJpo locbinitem = locbinitemset.getJpo(0);
							double qty = locbinitem.getDouble("qty");
							double newqty = qty + statusjsqty;
							locbinitem.setValue("qty", newqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						} else {
							IJpo locbinitem = locbinitemset.addJpo();
							locbinitem.setValue("binnum", binnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("location", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("qty", statusjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						}
					}
					inventoryset.save();
				}
			}
			// 调用入库记录公共类
			ConvertlocCommonInvtrans.ININVTRANS(lotnum, statusjsqty, location,
					itemnum, binnum, sqn, transdate);
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <入库单入库方法>
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
	 *            仓位编号
	 * @param sqn
	 *            序列号
	 * @param sxtype
	 *            修造类别
	 * 
	 */
	public static void MPRININVENTORY(String lotnum, double statusjsqty,
			String location, String itemnum, String binnum, String sqn,
			String sxtype) { // 入库
		try {
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

				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --入库库存的集合
				inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + location + "'");
				inventoryset.reset();
				if (!inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.getJpo(0);
					double CURBAL = inventory.getDouble("CURBAL");
					double locqty = inventory.getDouble("locqty");/* 非工单数量 */
					double newCURBAL = CURBAL + statusjsqty;
					double newlocqty = locqty + statusjsqty;

					if (!binnum.equalsIgnoreCase("")) {
						IJpoSet locbinitemset = MroServer.getMroServer()
								.getJpoSet(
										"locbinitem",
										MroServer.getMroServer()
												.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
						locbinitemset.setUserWhere("itemnum='" + itemnum
								+ "' and location='" + location
								+ "' and binnum='" + binnum + "'");
						locbinitemset.reset();
						if (!locbinitemset.isEmpty()) {
							IJpo locbinitem = locbinitemset.getJpo(0);
							double qty = locbinitem.getDouble("qty");
							double newqty = qty + statusjsqty;
							locbinitem.setValue("qty", newqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						} else {
							IJpo locbinitem = locbinitemset.addJpo();
							locbinitem.setValue("binnum", binnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("location", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("qty", statusjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						}
					}
					inventory.setValue("CURBAL", newCURBAL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// if(!sxtype.isEmpty()){
					// if(sxtype.equalsIgnoreCase("GZ")){
					// double faultqty=inventory.getDouble("faultqty");
					// double newfaultqty=faultqty+statusjsqty;
					// inventory.setValue("faultqty",
					// newfaultqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// }
					// if(sxtype.equalsIgnoreCase("YXX")){
					// double testingqty=inventory.getDouble("testingqty");
					// double newtestingqty=testingqty+statusjsqty;
					// inventory.setValue("testingqty",
					// newtestingqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// }
					// }
					inventoryset.save();

				}
				if (inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.addJpo();
					inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("CURBAL", statusjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", statusjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// if(!sxtype.isEmpty()){
					// if(sxtype.equalsIgnoreCase("GZ")){
					// inventory.setValue("faultqty",
					// statusjsqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// }
					// if(sxtype.equalsIgnoreCase("YXX")){
					// inventory.setValue("testingqty",
					// statusjsqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// }
					// }
					inventory.setValue("location", location,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (!binnum.equalsIgnoreCase("")) {
						IJpoSet locbinitemset = MroServer.getMroServer()
								.getJpoSet(
										"locbinitem",
										MroServer.getMroServer()
												.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
						locbinitemset.setUserWhere("itemnum='" + itemnum
								+ "' and location='" + location
								+ "' and binnum='" + binnum + "'");
						locbinitemset.reset();
						if (!locbinitemset.isEmpty()) {
							IJpo locbinitem = locbinitemset.getJpo(0);
							double qty = locbinitem.getDouble("qty");
							double newqty = qty + statusjsqty;
							locbinitem.setValue("qty", newqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						} else {
							IJpo locbinitem = locbinitemset.addJpo();
							locbinitem.setValue("binnum", binnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("location", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("qty", statusjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						}
					}
					inventoryset.save();

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
					double locqty = inventory.getDouble("locqty");
					double newCURBAL = CURBAL + statusjsqty;
					double newlocqty = locqty + statusjsqty;
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
							double newphyscntqty = physcntqty + statusjsqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							if (!binnum.equalsIgnoreCase("")) {
								IJpoSet locbinitemset = MroServer
										.getMroServer().getJpoSet(
												"locbinitem",
												MroServer.getMroServer()
														.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
								locbinitemset.setUserWhere("itemnum='"
										+ itemnum + "' and location='"
										+ location + "' and binnum='" + binnum
										+ "' and lotnum='" + lotnum + "'");
								locbinitemset.reset();
								if (!locbinitemset.isEmpty()) {
									IJpo locbinitem = locbinitemset.getJpo(0);
									double qty = locbinitem.getDouble("qty");
									double newqty = qty + statusjsqty;
									locbinitem
											.setValue(
													"qty",
													newqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								} else {
									IJpo locbinitem = locbinitemset.addJpo();
									locbinitem
											.setValue(
													"binnum",
													binnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"location",
													location,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"itemnum",
													itemnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"qty",
													statusjsqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"lotnum",
													lotnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								}
							}
							invblanceset.save();

						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", statusjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							if (!binnum.equalsIgnoreCase("")) {
								IJpoSet locbinitemset = MroServer
										.getMroServer().getJpoSet(
												"locbinitem",
												MroServer.getMroServer()
														.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
								locbinitemset.setUserWhere("itemnum='"
										+ itemnum + "' and location='"
										+ location + "' and binnum='" + binnum
										+ "' and lotnum='" + lotnum + "'");
								locbinitemset.reset();
								if (!locbinitemset.isEmpty()) {
									IJpo locbinitem = locbinitemset.getJpo(0);
									double qty = locbinitem.getDouble("qty");
									double newqty = qty + statusjsqty;
									locbinitem
											.setValue(
													"qty",
													newqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								} else {
									IJpo locbinitem = locbinitemset.addJpo();
									locbinitem
											.setValue(
													"binnum",
													binnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"location",
													location,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"itemnum",
													itemnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"qty",
													statusjsqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"lotnum",
													lotnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								}
							}
							invblanceset.save();
						}
					}
					inventory.setValue("CURBAL", newCURBAL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// if(!sxtype.isEmpty()){
					// if(sxtype.equalsIgnoreCase("GZ")){
					// double faultqty=inventory.getDouble("faultqty");
					// double newfaultqty=faultqty+statusjsqty;
					// inventory.setValue("faultqty",
					// newfaultqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// }
					// if(sxtype.equalsIgnoreCase("YXX")){
					// double testingqty=inventory.getDouble("testingqty");
					// double newtestingqty=testingqty+statusjsqty;
					// inventory.setValue("testingqty",
					// newtestingqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// }
					// }
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
							double newphyscntqty = physcntqty + statusjsqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							if (!binnum.equalsIgnoreCase("")) {
								IJpoSet locbinitemset = MroServer
										.getMroServer().getJpoSet(
												"locbinitem",
												MroServer.getMroServer()
														.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
								locbinitemset.setUserWhere("itemnum='"
										+ itemnum + "' and location='"
										+ location + "' and binnum='" + binnum
										+ "' and lotnum='" + lotnum + "'");
								locbinitemset.reset();
								if (!locbinitemset.isEmpty()) {
									IJpo locbinitem = locbinitemset.getJpo(0);
									double qty = locbinitem.getDouble("qty");
									double newqty = qty + statusjsqty;
									locbinitem
											.setValue(
													"qty",
													newqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								} else {
									IJpo locbinitem = locbinitemset.addJpo();
									locbinitem
											.setValue(
													"binnum",
													binnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"location",
													location,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"itemnum",
													itemnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"qty",
													statusjsqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"lotnum",
													lotnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								}
							}
							invblanceset.save();

						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", statusjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							if (!binnum.equalsIgnoreCase("")) {
								IJpoSet locbinitemset = MroServer
										.getMroServer().getJpoSet(
												"locbinitem",
												MroServer.getMroServer()
														.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
								locbinitemset.setUserWhere("itemnum='"
										+ itemnum + "' and location='"
										+ location + "' and binnum='" + binnum
										+ "' and lotnum='" + lotnum + "'");
								locbinitemset.reset();
								if (!locbinitemset.isEmpty()) {
									IJpo locbinitem = locbinitemset.getJpo(0);
									double qty = locbinitem.getDouble("qty");
									double newqty = qty + statusjsqty;
									locbinitem
											.setValue(
													"qty",
													newqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								} else {
									IJpo locbinitem = locbinitemset.addJpo();
									locbinitem
											.setValue(
													"binnum",
													binnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"location",
													location,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"itemnum",
													itemnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"qty",
													statusjsqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitem
											.setValue(
													"lotnum",
													lotnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								}
							}
							invblanceset.save();
						}
					}
					inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("CURBAL", statusjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", statusjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("location", location,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// if(!sxtype.isEmpty()){
					// if(sxtype.equalsIgnoreCase("GZ")){
					// inventory.setValue("faultqty",
					// statusjsqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// }
					// if(sxtype.equalsIgnoreCase("YXX")){
					// inventory.setValue("testingqty",
					// statusjsqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// }
					// }
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
					double locqty = inventory.getDouble("locqty");
					double newcurbal = CURBAL + statusjsqty;
					double newlocqty = locqty + statusjsqty;
					if (!binnum.equalsIgnoreCase("")) {
						IJpoSet locbinitemset = MroServer.getMroServer()
								.getJpoSet(
										"locbinitem",
										MroServer.getMroServer()
												.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
						locbinitemset.setUserWhere("itemnum='" + itemnum
								+ "' and location='" + location
								+ "' and binnum='" + binnum + "' and lotnum='"
								+ lotnum + "'");
						locbinitemset.reset();
						if (!locbinitemset.isEmpty()) {
							IJpo locbinitem = locbinitemset.getJpo(0);
							double qty = locbinitem.getDouble("qty");
							double newqty = qty + statusjsqty;
							locbinitem.setValue("qty", newqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						} else {
							IJpo locbinitem = locbinitemset.addJpo();
							locbinitem.setValue("binnum", binnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("location", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("qty", statusjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						}
					}
					inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// if(!sxtype.isEmpty()){
					// if(sxtype.equalsIgnoreCase("GZ")){
					// double faultqty=inventory.getDouble("faultqty");
					// double newfaultqty=faultqty+statusjsqty;
					// inventory.setValue("faultqty",
					// newfaultqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// }
					// if(sxtype.equalsIgnoreCase("YXX")){
					// double testingqty=inventory.getDouble("testingqty");
					// double newtestingqty=testingqty+statusjsqty;
					// inventory.setValue("testingqty",
					// newtestingqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// }
					// }
					inventoryset.save();
				}
				if (inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.addJpo();
					inventory.setValue("CURBAL", statusjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", statusjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("location", location,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// if(!sxtype.isEmpty()){
					// if(sxtype.equalsIgnoreCase("GZ")){
					// inventory.setValue("faultqty",
					// statusjsqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// }
					// if(sxtype.equalsIgnoreCase("YXX")){
					// inventory.setValue("testingqty",
					// statusjsqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// }
					// }
					if (!binnum.equalsIgnoreCase("")) {
						IJpoSet locbinitemset = MroServer.getMroServer()
								.getJpoSet(
										"locbinitem",
										MroServer.getMroServer()
												.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
						locbinitemset.setUserWhere("itemnum='" + itemnum
								+ "' and location='" + location
								+ "' and binnum='" + binnum + "' and lotnum='"
								+ lotnum + "'");
						locbinitemset.reset();
						if (!locbinitemset.isEmpty()) {
							IJpo locbinitem = locbinitemset.getJpo(0);
							double qty = locbinitem.getDouble("qty");
							double newqty = qty + statusjsqty;
							locbinitem.setValue("qty", newqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						} else {
							IJpo locbinitem = locbinitemset.addJpo();
							locbinitem.setValue("binnum", binnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("location", location,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitem.setValue("qty", statusjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						}
					}
					inventoryset.save();
				}
			}
			// 调用入库单入库记录公共类
			ConvertlocCommonInvtrans.ININVTRANS(lotnum, statusjsqty, location,
					itemnum, binnum, sqn, transdate);
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <退库单出口功能>
	 * 
	 * @param lotnum
	 * @param statusjsqty
	 * @param location
	 * @param itemnum
	 * @param binnum
	 * @param sqn
	 * @param sxtype
	 *            [参数说明]
	 * 
	 */
	public static void MPROUTINVENTORY(String lotnum, double statusjsqty,
			String location, String itemnum, String binnum, String sqn,
			String sxtype) { // 出库库
		try {
			java.util.Date transdate = MroServer.getMroServer().getDate();
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultOrg("CRRC");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultSite("ELEC");
			// IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",
			// MroServer.getMroServer().getSystemUserServer());
			// itemset.setUserWhere("itemnum='" + itemnum + "'");
			String type = ItemUtil.getItemInfo(itemnum);
			if (ItemUtil.SQN_ITEM.equals(type)) {// --判断是周转件

				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --出库库存的集合
				inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + location + "'");
				inventoryset.reset();
				if (!inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.getJpo(0);
					double CURBAL = inventory.getDouble("CURBAL");
					double locqty = inventory.getDouble("locqty");/* 非工单数量 */
					double newCURBAL = CURBAL - statusjsqty;
					double newlocqty = locqty - statusjsqty;

					if (!binnum.equalsIgnoreCase("")) {
						IJpoSet locbinitemset = MroServer.getMroServer()
								.getJpoSet(
										"locbinitem",
										MroServer.getMroServer()
												.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
						locbinitemset.setUserWhere("itemnum='" + itemnum
								+ "' and location='" + location
								+ "' and binnum='" + binnum + "'");
						locbinitemset.reset();
						if (!locbinitemset.isEmpty()) {
							IJpo locbinitem = locbinitemset.getJpo(0);
							double qty = locbinitem.getDouble("qty");
							double newqty = qty - statusjsqty;
							locbinitem.setValue("qty", newqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						} 
					}
					inventory.setValue("CURBAL", newCURBAL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();

				}

			} else if (ItemUtil.LOT_I_ITEM.equals(type)) {// --判断是批次件
				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --退库库存的集合
				inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + location + "'");
				inventoryset.reset();
				if (!inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.getJpo(0);
					double CURBAL = inventory.getDouble("CURBAL");
					double locqty = inventory.getDouble("locqty");
					double newCURBAL = CURBAL - statusjsqty;
					double newlocqty = locqty - statusjsqty;
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
							double newphyscntqty = physcntqty - statusjsqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							if (!binnum.equalsIgnoreCase("")) {
								IJpoSet locbinitemset = MroServer
										.getMroServer().getJpoSet(
												"locbinitem",
												MroServer.getMroServer()
														.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
								locbinitemset.setUserWhere("itemnum='"
										+ itemnum + "' and location='"
										+ location + "' and binnum='" + binnum
										+ "' and lotnum='" + lotnum + "'");
								locbinitemset.reset();
								if (!locbinitemset.isEmpty()) {
									IJpo locbinitem = locbinitemset.getJpo(0);
									double qty = locbinitem.getDouble("qty");
									double newqty = qty - statusjsqty;
									locbinitem
											.setValue(
													"qty",
													newqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								} 
							}
							invblanceset.save();

						}
					}
					inventory.setValue("CURBAL", newCURBAL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();
				}

			} else {// --判断即不是周转件也不是批次件
				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --退库库存的集合
				inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + location + "'");
				inventoryset.reset();
				if (!inventoryset.isEmpty()) {
					IJpo inventory = inventoryset.getJpo(0);
					double CURBAL = inventory.getDouble("CURBAL");
					double locqty = inventory.getDouble("locqty");
					double newcurbal = CURBAL - statusjsqty;
					double newlocqty = locqty - statusjsqty;
					if (!binnum.equalsIgnoreCase("")) {
						IJpoSet locbinitemset = MroServer.getMroServer()
								.getJpoSet(
										"locbinitem",
										MroServer.getMroServer()
												.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
						locbinitemset.setUserWhere("itemnum='" + itemnum
								+ "' and location='" + location
								+ "' and binnum='" + binnum + "' and lotnum='"
								+ lotnum + "'");
						locbinitemset.reset();
						if (!locbinitemset.isEmpty()) {
							IJpo locbinitem = locbinitemset.getJpo(0);
							double qty = locbinitem.getDouble("qty");
							double newqty = qty - statusjsqty;
							locbinitem.setValue("qty", newqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							locbinitemset.save();
						}
					}
					inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					inventoryset.save();
				}
			}
			// 调用入库单入库记录公共类
			ConvertlocCommonInvtrans.OUTINVTRANS(lotnum, statusjsqty, location,
					itemnum, binnum, sqn, transdate);
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
