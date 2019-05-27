package com.glaway.sddq.material.invtrans.common;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <调拨单库存变动公共类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-8]
 * @since [产品/模块版本]
 */
public class TransStoroomCommon {
	private static TransStoroomCommon wfCtrl = null;;

	private TransStoroomCommon() {
	}

	public synchronized static TransStoroomCommon getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new TransStoroomCommon();
		}
		return wfCtrl;
	}

	/**
	 * 
	 * <出库方法>
	 * 
	 * @param transferlineset
	 *            [参数说明]
	 * 
	 */
	public static void out_storoom(IJpoSet transferlineset) { // 调拨库存变化-出库
		try {
			if (!transferlineset.isEmpty()) {// --判断调拨单行不为空
				for (int i = 0; i < transferlineset.count(); i++) {
					IJpo transferline = transferlineset.getJpo(i);
					String transfernum = transferline.getString("transfernum");// --调拨单号
					IJpoSet transferset = MroServer.getMroServer().getJpoSet(
							"transfer",
							MroServer.getMroServer().getSystemUserServer());// --对应的周转件集合
					transferset.setUserWhere("transfernum='" + transfernum
							+ "'");
					transferset.reset();
					String sxtype = "";
					if (!transferset.isEmpty()) {
						sxtype = transferset.getJpo(0).getString("sxtype");
					}

					String itemnum = transferline.getString("itemnum");// --物料编码
					String lotnum = transferline.getString("lotnum");// --批次号
					String issuestoreroom = transferline.getParent().getString(
							"issuestoreroom");// --发出库房
					String receivestoreroom = transferline.getParent()
							.getString("receivestoreroom");// --接收库房
					String siteid = transferline.getString("siteid");// --地点
					String orgid = transferline.getString("orgid");// --组织
					String outbinnum = transferline.getString("outbinnum");// --出库仓位
					String type = ItemUtil.getItemInfo(itemnum);// --关联取物料是否批次号管理属性
					String assetnum = transferline.getString("assetnum");// --资产编号
					double orderqty = transferline.getDouble("orderqty");// --调拨数量

					if (ItemUtil.SQN_ITEM.equals(type)) {// --判断如果是周转件
						IJpoSet assetset = MroServer.getMroServer().getJpoSet(
								"asset",
								MroServer.getMroServer().getSystemUserServer());// --对应的周转件集合
						assetset.setUserWhere("assetnum='" + assetnum + "'");
						assetset.reset();
						if (!assetset.isEmpty()) {// --判断对应周转件的存在
							IJpoSet outinventoryset = MroServer.getMroServer()
									.getJpoSet(
											"sys_inventory",
											MroServer.getMroServer()
													.getSystemUserServer());// --调拨出库库存的集合
							outinventoryset
									.setUserWhere("itemnum='" + itemnum
											+ "' and location='"
											+ issuestoreroom + "'");
							outinventoryset.reset();
							if (!outinventoryset.isEmpty()) {
								IJpo outinventory = outinventoryset.getJpo(0);
								double CURBAL = outinventory
										.getDouble("CURBAL");
								double newCURBAL = CURBAL - orderqty;
								double locqty = outinventory
										.getDouble("locqty");
								double newlocqty = locqty - orderqty;
								if (!outbinnum.equalsIgnoreCase("")) {// --判断出库仓位不为空
									IJpoSet outlocbinitemset = MroServer
											.getMroServer()
											.getJpoSet(
													"locbinitem",
													MroServer
															.getMroServer()
															.getSystemUserServer());// --调拨出库仓位集合
									outlocbinitemset.setUserWhere("itemnum='"
											+ itemnum + "' and location='"
											+ issuestoreroom + "' and binnum='"
											+ outbinnum + "'");
									outlocbinitemset.reset();
									if (!outlocbinitemset.isEmpty()) {// --判断出库仓位集合不为空
										IJpo outlocbinitem = outlocbinitemset
												.getJpo(0);
										double qty = outlocbinitem
												.getDouble("qty");// --调拨出库对应仓位上放置物料的数量
										double newqty = qty - orderqty;// --出库后，仓位上的数量
										outlocbinitem
												.setValue(
														"qty",
														newqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										outlocbinitemset.save();
									}
								}
//								if (!sxtype.equalsIgnoreCase("")) {
//									if (sxtype.equalsIgnoreCase("GZ")) {
//										double faultqty = outinventory
//												.getDouble("faultqty");
//										double newfaultqty = faultqty
//												- orderqty;
//										outinventory
//												.setValue(
//														"faultqty",
//														newfaultqty,
//														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//									} else if (sxtype.equalsIgnoreCase("GAIZ")) {
//										double remouldqty = outinventory
//												.getDouble("remouldqty");
//										double newremouldqty = remouldqty
//												- orderqty;
//										outinventory
//												.setValue(
//														"remouldqty",
//														newremouldqty,
//														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//									} else if (sxtype.equalsIgnoreCase("YXX")) {
//										double testingqty = outinventory
//												.getDouble("testingqty");
//										double newtestingqty = testingqty
//												- orderqty;
//										outinventory
//												.setValue(
//														"testingqty",
//														newtestingqty,
//														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//									}
//								}
								outinventory
										.setValue(
												"CURBAL",
												newCURBAL,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								outinventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								outinventoryset.save();
								assetset.getJpo(0)
										.setValue(
												"location",
												"",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

								assetset.save();
							}
						}
					} else if (ItemUtil.LOT_I_ITEM.equals(type)) {// --判断如果是批次件
						IJpoSet out_inventoryset = MroServer.getMroServer()
								.getJpoSet(
										"sys_inventory",
										MroServer.getMroServer()
												.getSystemUserServer());// --调拨出库库存的集合
						out_inventoryset.setUserWhere("itemnum='" + itemnum
								+ "' and location='" + issuestoreroom + "'");
						out_inventoryset.reset();
						if (!out_inventoryset.isEmpty()) {
							IJpo out_inventory = out_inventoryset.getJpo(0);
							double CURBAL = out_inventory.getDouble("CURBAL");
							double newCURBAL = CURBAL - orderqty;
							double locqty = out_inventory.getDouble("locqty");
							double newlocqty = locqty - orderqty;
							if (!lotnum.equalsIgnoreCase("")) {
								IJpoSet invblanceset = MroServer.getMroServer()
										.getJpoSet(
												"invblance",
												MroServer.getMroServer()
														.getSystemUserServer());// --调拨出库批次集合
								invblanceset.setUserWhere("itemnum='"
										+ itemnum + "' and storeroom='"
										+ issuestoreroom + "' and lotnum='"
										+ lotnum + "'");
								invblanceset.reset();
								if (!invblanceset.isEmpty()) {
									IJpo invblance = invblanceset.getJpo(0);
									double physcntqty = invblance
											.getDouble("physcntqty");
									double newphyscntqty = physcntqty
											- orderqty;
									invblance
											.setValue(
													"physcntqty",
													newphyscntqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									invblanceset.save();
								}
							}
//							if (!sxtype.equalsIgnoreCase("")) {
//								if (sxtype.equalsIgnoreCase("GZ")) {
//									double faultqty = out_inventory
//											.getDouble("faultqty");
//									double newfaultqty = faultqty - orderqty;
//									out_inventory
//											.setValue(
//													"faultqty",
//													newfaultqty,
//													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//								} else if (sxtype.equalsIgnoreCase("GAIZ")) {
//									double remouldqty = out_inventory
//											.getDouble("remouldqty");
//									double newremouldqty = remouldqty
//											- orderqty;
//									out_inventory
//											.setValue(
//													"remouldqty",
//													newremouldqty,
//													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//								} else if (sxtype.equalsIgnoreCase("YXX")) {
//									double testingqty = out_inventory
//											.getDouble("testingqty");
//									double newtestingqty = testingqty
//											- orderqty;
//									out_inventory
//											.setValue(
//													"testingqty",
//													newtestingqty,
//													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//								}
//							}
							out_inventory.setValue("locqty", newlocqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							out_inventory.setValue("CURBAL", newCURBAL,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							out_inventoryset.save();
						}
					} else {// --判断即不是周转件也不是批次件
						IJpoSet out_inventoryset = MroServer.getMroServer()
								.getJpoSet(
										"sys_inventory",
										MroServer.getMroServer()
												.getSystemUserServer());// --调拨出库库存的集合
						out_inventoryset.setUserWhere("itemnum='" + itemnum
								+ "' and location='" + issuestoreroom + "'");
						out_inventoryset.reset();
						if (!out_inventoryset.isEmpty()) {
							IJpo out_inventory = out_inventoryset.getJpo(0);
							double CURBAL = out_inventory.getDouble("CURBAL");
							double newcurbal = CURBAL - orderqty;
							double locqty = out_inventory.getDouble("locqty");
							double newlocqty = locqty - orderqty;
//							if (!sxtype.equalsIgnoreCase("")) {
//								if (sxtype.equalsIgnoreCase("GZ")) {
//									double faultqty = out_inventory
//											.getDouble("faultqty");
//									double newfaultqty = faultqty - orderqty;
//									out_inventory
//											.setValue(
//													"faultqty",
//													newfaultqty,
//													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//								} else if (sxtype.equalsIgnoreCase("GAIZ")) {
//									double remouldqty = out_inventory
//											.getDouble("remouldqty");
//									double newremouldqty = remouldqty
//											- orderqty;
//									out_inventory
//											.setValue(
//													"remouldqty",
//													newremouldqty,
//													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//								} else if (sxtype.equalsIgnoreCase("YXX")) {
//									double testingqty = out_inventory
//											.getDouble("testingqty");
//									double newtestingqty = testingqty
//											- orderqty;
//									out_inventory
//											.setValue(
//													"testingqty",
//													newtestingqty,
//													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//								}
//							}
							out_inventory.setValue("locqty", newlocqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							out_inventory.setValue("CURBAL", newcurbal,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							out_inventoryset.save();
						}
					}
				}
			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <入库方法>
	 * 
	 * @param transferlineset
	 *            [参数说明]
	 * 
	 */
	public static void in_storoom(IJpoSet transferlineset) { // 调拨库存变化-入库
		try {
			if (!transferlineset.isEmpty()) {// --判断调拨单行不为空
				java.util.Date INSTOREDATE = MroServer.getMroServer().getDate();
				IJpoSet notransferlineset = transferlineset;
				notransferlineset.setUserWhere("status!='已接收'");
				if (!notransferlineset.isEmpty()) {
					for (int i = 0; i < notransferlineset.count(); i++) {
						IJpo transferline = notransferlineset.getJpo(i);
						String itemnum = transferline.getString("itemnum");// --物料编码
						String lotnum = transferline.getString("lotnum");// --批次号
						String transfernum = transferline
								.getString("transfernum");
						String receivestoreroom = "";
						IJpoSet transferset = MroServer.getMroServer()
								.getJpoSet(
										"transfer",
										MroServer.getMroServer()
												.getSystemUserServer());// --对应的周转件集合
						transferset.setUserWhere("transfernum='" + transfernum
								+ "'");
						String sxtype = "";
						if (!transferset.isEmpty()) {
							receivestoreroom = transferset.getJpo(0).getString(
									"receivestoreroom");// --接收库房
							sxtype = transferset.getJpo(0).getString("sxtype");
						}
						String siteid = transferline.getString("siteid");// --地点
						String orgid = transferline.getString("orgid");// --组织

						String itemtype = ItemUtil.getItemInfo(itemnum);

						String assetnum = transferline.getString("assetnum");// --资产编号
						double orderqty = transferline.getDouble("orderqty");// --调拨数量
						double YJSQTY = transferline.getDouble("YJSQTY");// --已接收数量
						double newqty = orderqty - YJSQTY;

						if (itemtype.equalsIgnoreCase(ItemUtil.SQN_ITEM)) {// --判断如果是周转件
							IJpoSet assetset = MroServer.getMroServer()
									.getJpoSet(
											"asset",
											MroServer.getMroServer()
													.getSystemUserServer());// --对应的周转件集合
							assetset.setUserWhere("assetnum='" + assetnum
									+ "'");
							assetset.reset();
							if (!assetset.isEmpty()) {// --判断对应周转件的存在
								IJpo asset=assetset.getJpo(0);
								String commomtype="入库";
								IJpoSet inventoryset = MroServer.getMroServer()
										.getJpoSet(
												"sys_inventory",
												MroServer.getMroServer()
														.getSystemUserServer());// --调拨入库库存的集合
								inventoryset.setUserWhere("itemnum='"
										+ itemnum + "' and location='"
										+ receivestoreroom + "'");
								inventoryset.reset();

								if (!inventoryset.isEmpty()) {// --判断入库库存集合不为空
									IJpo inventory = inventoryset.getJpo(0);
									double CURBAL = inventory
											.getDouble("CURBAL");
									double newCURBAL = CURBAL + newqty;
									double locqty = inventory
											.getDouble("locqty");
									double newlocqty = locqty + newqty;
									inventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									inventory
											.setValue(
													"CURBAL",
													newCURBAL,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									inventoryset.save();
									assetset.getJpo(0)
											.setValue(
													"location",
													receivestoreroom,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									assetset.getJpo(0)
											.setValue(
													"INSTOREDATE",
													INSTOREDATE,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									assetset.getJpo(0)
											.setValue(
													"status",
													"可用",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//									CommomCarItemLife.INOROURLOCATION(asset, commomtype);//调用一物一档入库计算方法
									assetset.save();
								}

								if (inventoryset.isEmpty()) {// --判断入库库存集合为空
									IJpo inventory = inventoryset.addJpo();
									inventory
											.setValue(
													"CURBAL",
													newqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									inventory
											.setValue(
													"locqty",
													newqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									inventory
											.setValue(
													"itemnum",
													itemnum,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									inventory
											.setValue(
													"location",
													receivestoreroom,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									inventory
											.setValue(
													"siteid",
													siteid,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									inventory
											.setValue(
													"orgid",
													orgid,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									assetset.getJpo(0)
											.setValue(
													"location",
													receivestoreroom,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									assetset.getJpo(0)
											.setValue(
													"INSTOREDATE",
													INSTOREDATE,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									assetset.getJpo(0)
											.setValue(
													"status",
													"可用",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//									CommomCarItemLife.INOROURLOCATION(asset, commomtype);//调用一物一档入库计算方法

									inventoryset.save();
									assetset.save();
								}
							}
						} else if (itemtype
								.equalsIgnoreCase(ItemUtil.LOT_I_ITEM)) {// --判断如果是批次件
							IJpoSet in_inventoryset = MroServer.getMroServer()
									.getJpoSet(
											"sys_inventory",
											MroServer.getMroServer()
													.getSystemUserServer());// --调拨入库库存的集合
							in_inventoryset.setUserWhere("itemnum='" + itemnum
									+ "' and location='" + receivestoreroom
									+ "'");
							in_inventoryset.reset();
							if (!in_inventoryset.isEmpty()) {
								IJpo in_inventory = in_inventoryset.getJpo(0);
								double CURBAL = in_inventory
										.getDouble("CURBAL");
								double newCURBAL = CURBAL + newqty;
								if (!lotnum.equalsIgnoreCase("")) {
									IJpoSet invblanceset = MroServer
											.getMroServer()
											.getJpoSet(
													"invblance",
													MroServer
															.getMroServer()
															.getSystemUserServer());// --调拨入库批次集合
									invblanceset.setUserWhere("itemnum='"
											+ itemnum + "' and storeroom='"
											+ receivestoreroom
											+ "' and lotnum='" + lotnum + "'");
									invblanceset.reset();
									if (!invblanceset.isEmpty()) {
										IJpo invblance = invblanceset.getJpo(0);
										double physcntqty = invblance
												.getDouble("physcntqty");
										double newphyscntqty = physcntqty
												+ newqty;
										invblance
												.setValue(
														"physcntqty",
														newphyscntqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblanceset.save();
									}
									if (invblanceset.isEmpty()) {
										IJpo invblance = invblanceset.addJpo();
										invblance
												.setValue(
														"lotnum",
														lotnum,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblance
												.setValue(
														"physcntqty",
														newqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblance
												.setValue(
														"itemnum",
														itemnum,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblance
												.setValue(
														"storeroom",
														receivestoreroom,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblance
												.setValue(
														"siteid",
														siteid,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblance
												.setValue(
														"orgid",
														orgid,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblanceset.save();
									}
								}
								double locqty = in_inventory
										.getDouble("locqty");
								double newlocqty = locqty + newqty;
								in_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventory
										.setValue(
												"CURBAL",
												newCURBAL,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventoryset.save();
							}
							if (in_inventoryset.isEmpty()) {
								IJpo in_inventory = in_inventoryset.addJpo();

								if (!lotnum.equalsIgnoreCase("")) {
									IJpoSet invblanceset = MroServer
											.getMroServer()
											.getJpoSet(
													"invblance",
													MroServer
															.getMroServer()
															.getSystemUserServer());// --调拨入库批次集合
									invblanceset.setUserWhere("itemnum='"
											+ itemnum + "' and storeroom='"
											+ receivestoreroom
											+ "' and lotnum='" + lotnum + "'");
									invblanceset.reset();
									if (!invblanceset.isEmpty()) {
										IJpo invblance = invblanceset.getJpo(0);
										double physcntqty = invblance
												.getDouble("physcntqty");
										double newphyscntqty = physcntqty
												+ newqty;
										invblance
												.setValue(
														"physcntqty",
														newphyscntqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblanceset.save();
									}
									if (invblanceset.isEmpty()) {
										IJpo invblance = invblanceset.addJpo();
										invblance
												.setValue(
														"lotnum",
														lotnum,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblance
												.setValue(
														"physcntqty",
														newqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblance
												.setValue(
														"itemnum",
														itemnum,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblance
												.setValue(
														"storeroom",
														receivestoreroom,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblance
												.setValue(
														"siteid",
														siteid,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblance
												.setValue(
														"orgid",
														orgid,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										invblanceset.save();
									}
								}

								in_inventory
										.setValue(
												"itemnum",
												itemnum,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventory
										.setValue(
												"CURBAL",
												newqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventory
										.setValue(
												"locqty",
												newqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventory
										.setValue(
												"location",
												receivestoreroom,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventory
										.setValue(
												"siteid",
												siteid,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventory
										.setValue(
												"orgid",
												orgid,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventoryset.save();
							}
						} else {// --判断即不是周转件也不是批次件

							IJpoSet in_inventoryset = MroServer.getMroServer()
									.getJpoSet(
											"sys_inventory",
											MroServer.getMroServer()
													.getSystemUserServer());// --调拨入库库存的集合
							in_inventoryset.setUserWhere("itemnum='" + itemnum
									+ "' and location='" + receivestoreroom
									+ "'");
							in_inventoryset.reset();

							if (!in_inventoryset.isEmpty()) {
								IJpo in_inventory = in_inventoryset.getJpo(0);
								double CURBAL = in_inventory
										.getDouble("CURBAL");
								double newcurbal = CURBAL + newqty;
								double locqty = in_inventory
										.getDouble("locqty");
								double newlocqty = locqty + newqty;
								in_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventory
										.setValue(
												"CURBAL",
												newcurbal,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventoryset.save();
							}
							if (in_inventoryset.isEmpty()) {
								IJpo in_inventory = in_inventoryset.addJpo();
								in_inventory
										.setValue(
												"CURBAL",
												newqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventory
										.setValue(
												"locqty",
												newqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventory
										.setValue(
												"itemnum",
												itemnum,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventory
										.setValue(
												"location",
												receivestoreroom,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventory
										.setValue(
												"siteid",
												siteid,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventory
										.setValue(
												"orgid",
												orgid,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								in_inventoryset.save();
							}
						}
					}
				}

			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
