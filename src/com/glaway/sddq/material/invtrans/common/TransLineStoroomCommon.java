package com.glaway.sddq.material.invtrans.common;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <调拨单行点收库存变动公共类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-8]
 * @since [产品/模块版本]
 */
public class TransLineStoroomCommon {
	private static TransLineStoroomCommon wfCtrl = null;;

	private TransLineStoroomCommon() {
	}

	public synchronized static TransLineStoroomCommon getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new TransLineStoroomCommon();
		}
		return wfCtrl;
	}

	/**
	 * 
	 * <出库方法>
	 * 
	 * @param transferline
	 * @param dsqty
	 *            [参数说明]
	 * 
	 */
	public static void out_storoom(IJpo transferline, double dsqty) { // 调拨库存变化-出库
		try {
			String transfernum = transferline.getString("transfernum");/* 调拨单号 */
			IJpoSet transferset = MroServer.getMroServer().getJpoSet(
					"transfer", MroServer.getMroServer().getSystemUserServer());// --对应的周转件集合
			transferset.setQueryWhere("transfernum='" + transfernum + "'");
			transferset.reset();
			String transfertype = "";/* 调拨单类型 */

			String itemnum = transferline.getString("itemnum");// --物料编码
			String lotnum = transferline.getString("lotnum");// --批次号
			String issuestoreroom = transferline.getString("issuestoreroom");// --发出库房
			String receivestoreroom = transferline
					.getString("receivestoreroom");// --接收库房
			String outbinnum = transferline.getString("outbinnum");// --出库仓位
			String siteid = transferline.getString("siteid");// --地点
			String orgid = transferline.getString("orgid");// --组织
			String type = ItemUtil.getItemInfo(itemnum);
			String assetnum = transferline.getString("assetnum");// --资产编号

			if (ItemUtil.SQN_ITEM.equals(type)) {// --判断如果是周转件
				IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
						MroServer.getMroServer().getSystemUserServer());// --对应的周转件集合
				assetset.setQueryWhere("assetnum='" + assetnum + "'");
				assetset.reset();
				if (!assetset.isEmpty()) {// --判断对应周转件的存在
					IJpoSet outinventoryset = MroServer.getMroServer()
							.getJpoSet(
									"sys_inventory",
									MroServer.getMroServer()
											.getSystemUserServer());// --调拨出库库存的集合
					outinventoryset.setQueryWhere("itemnum='" + itemnum
							+ "' and location='" + issuestoreroom + "'");
					outinventoryset.reset();
					if (!outinventoryset.isEmpty()) {
						IJpo outinventory = outinventoryset.getJpo(0);
						double CURBAL = outinventory.getDouble("CURBAL");
						double newCURBAL = CURBAL - dsqty;
						double locqty = outinventory.getDouble("locqty");
						double newlocqty = locqty - dsqty;
						if (!outbinnum.equalsIgnoreCase("")) {// --判断出库仓位不为空
							IJpoSet outlocbinitemset = MroServer.getMroServer()
									.getJpoSet(
											"locbinitem",
											MroServer.getMroServer()
													.getSystemUserServer());// --调拨出库仓位集合
							outlocbinitemset.setQueryWhere("itemnum='"
									+ itemnum + "' and location='"
									+ issuestoreroom + "' and binnum='"
									+ outbinnum + "'");
							outlocbinitemset.reset();
							if (!outlocbinitemset.isEmpty()) {// --判断出库仓位集合不为空
								IJpo outlocbinitem = outlocbinitemset.getJpo(0);
								double qty = outlocbinitem.getDouble("qty");// --调拨出库对应仓位上放置物料的数量
								double newqty = qty - dsqty;// --出库后，仓位上的数量
								outlocbinitem
										.setValue(
												"qty",
												newqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								outlocbinitemset.save();
							}
						}
						outinventory.setValue("CURBAL", newCURBAL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						if (!transferset.isEmpty()) {
							transfertype = transferset.getJpo(0).getString(
									"type");
							if (transfertype.equalsIgnoreCase("GZZXD")) {
								outinventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfertype.equalsIgnoreCase("SX")) {
								String sxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (sxtype.equalsIgnoreCase("YXX")) {
									outinventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							}
							if (transfertype.equalsIgnoreCase("ZXD")) {
								String transfermovetype = transferset.getJpo(0)
										.getString("transfermovetype");
								if (transfermovetype.equalsIgnoreCase("现场到现场")) {
									outinventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
								if (transfermovetype.equalsIgnoreCase("中心到现场")) {
									outinventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
								if (transfermovetype.equalsIgnoreCase("现场到中心")) {
									String sxtype = transferset.getJpo(0)
											.getString("sxtype");
									if (sxtype.equalsIgnoreCase("YXX")) {
										outinventory
												.setValue(
														"locqty",
														newlocqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								}
								if (transfermovetype.equalsIgnoreCase("中心到中心")) {
									String sxtype = transferset.getJpo(0)
											.getString("sxtype");
									if (!sxtype.isEmpty()) {
										if (sxtype.equalsIgnoreCase("YXX")) {
											outinventory
													.setValue(
															"locqty",
															newlocqty,
															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										}
										if (sxtype.equalsIgnoreCase("GZ")) {
											outinventory
													.setValue(
															"locqty",
															newlocqty,
															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										}
									} else {
										outinventory
												.setValue(
														"locqty",
														newlocqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								}

							}
						}
						outinventoryset.save();
						assetset.getJpo(0).setValue("location", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.save();
					}
				}
			} else if (ItemUtil.LOT_I_ITEM.equals(type)) {// --判断如果是批次件
				IJpoSet out_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨出库库存的集合
				out_inventoryset.setQueryWhere("itemnum='" + itemnum
						+ "' and location='" + issuestoreroom + "'");
				out_inventoryset.reset();
				if (!out_inventoryset.isEmpty()) {
					IJpo out_inventory = out_inventoryset.getJpo(0);
					double CURBAL = out_inventory.getDouble("CURBAL");
					double newCURBAL = CURBAL - dsqty;
					double locqty = out_inventory.getDouble("locqty");
					double newlocqty = locqty - dsqty;
					if (!lotnum.equalsIgnoreCase("")) {
						IJpoSet invblanceset = MroServer.getMroServer()
								.getJpoSet(
										"invblance",
										MroServer.getMroServer()
												.getSystemUserServer());// --调拨出库批次集合
						invblanceset.setQueryWhere("itemnum='" + itemnum
								+ "' and storeroom='" + issuestoreroom
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty - dsqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
					}
					out_inventory.setValue("CURBAL", newCURBAL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (!transferset.isEmpty()) {
						transfertype = transferset.getJpo(0).getString("type");
						if (transfertype.equalsIgnoreCase("GZZXD")) {
							out_inventory.setValue("locqty", newlocqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
						if (transfertype.equalsIgnoreCase("SX")) {
							String sxtype = transferset.getJpo(0).getString(
									"sxtype");
							if (sxtype.equalsIgnoreCase("YXX")) {
								out_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}
						if (transfertype.equalsIgnoreCase("ZXD")) {
							String transfermovetype = transferset.getJpo(0)
									.getString("transfermovetype");
							if (transfermovetype.equalsIgnoreCase("现场到现场")) {
								out_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfermovetype.equalsIgnoreCase("中心到现场")) {
								out_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfermovetype.equalsIgnoreCase("现场到中心")) {
								String sxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (sxtype.equalsIgnoreCase("YXX")) {
									out_inventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							}
							if (transfermovetype.equalsIgnoreCase("中心到中心")) {
								String sxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (!sxtype.isEmpty()) {
									if (sxtype.equalsIgnoreCase("YXX")) {
										out_inventory
												.setValue(
														"locqty",
														newlocqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
									if (sxtype.equalsIgnoreCase("GZ")) {
										out_inventory
												.setValue(
														"locqty",
														newlocqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								} else {
									out_inventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							}

						}
					}
					out_inventoryset.save();
				}
			} else {// --判断即不是周转件也不是批次件
				IJpoSet out_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨出库库存的集合
				out_inventoryset.setQueryWhere("itemnum='" + itemnum
						+ "' and location='" + issuestoreroom + "'");
				out_inventoryset.reset();
				if (!out_inventoryset.isEmpty()) {
					IJpo out_inventory = out_inventoryset.getJpo(0);
					double CURBAL = out_inventory.getDouble("CURBAL");
					double newcurbal = CURBAL - dsqty;
					double locqty = out_inventory.getDouble("locqty");
					double newlocqty = locqty - dsqty;
					out_inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (!transferset.isEmpty()) {
						transfertype = transferset.getJpo(0).getString("type");
						if (transfertype.equalsIgnoreCase("GZZXD")) {
							out_inventory.setValue("locqty", newlocqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
						if (transfertype.equalsIgnoreCase("SX")) {
							String sxtype = transferset.getJpo(0).getString(
									"sxtype");
							if (sxtype.equalsIgnoreCase("YXX")) {
								out_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}
						if (transfertype.equalsIgnoreCase("ZXD")) {
							String transfermovetype = transferset.getJpo(0)
									.getString("transfermovetype");
							if (transfermovetype.equalsIgnoreCase("现场到现场")) {
								out_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfermovetype.equalsIgnoreCase("中心到现场")) {
								out_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfermovetype.equalsIgnoreCase("现场到中心")) {
								String sxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (sxtype.equalsIgnoreCase("YXX")) {
									out_inventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							}
							if (transfermovetype.equalsIgnoreCase("中心到中心")) {
								String sxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (!sxtype.isEmpty()) {
									if (sxtype.equalsIgnoreCase("YXX")) {
										out_inventory
												.setValue(
														"locqty",
														newlocqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
									if (sxtype.equalsIgnoreCase("GZ")) {
										out_inventory
												.setValue(
														"locqty",
														newlocqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								} else {
									out_inventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							}

						}
					}
					out_inventoryset.save();
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
	 * @param transferline
	 * @param djqty
	 *            [参数说明]
	 * 
	 */
	public static void in_storoom(IJpo transferline, double djqty) { // 调拨库存变化-入库
		try {

			java.util.Date INSTOREDATE = MroServer.getMroServer().getDate();
			String itemnum = transferline.getString("itemnum");// --物料编码
			String lotnum = transferline.getString("lotnum");// --批次号
			String transfernum = transferline.getString("transfernum");//
			String sxtype = transferline.getString("sxtype");
			String receivestoreroom = transferline
					.getString("receivestoreroom");
			String LOCATIONTYPE = transferline.getJpoSet("receivestoreroom")
					.getJpo().getString("LOCATIONTYPE");// --库房类型
			String siteid = transferline.getString("siteid");// --地点
			String orgid = transferline.getString("orgid");// --组织
			String type = ItemUtil.getItemInfo(itemnum);
			String assetnum = transferline.getString("assetnum");// --资产编号
			// double orderqty=transferline.getDouble("orderqty");//--调拨数量

			IJpoSet transferset = MroServer.getMroServer().getJpoSet(
					"transfer", MroServer.getMroServer().getSystemUserServer());// --对应的周转件集合
			transferset.setQueryWhere("transfernum='" + transfernum + "'");
			transferset.reset();
			String transfertype = "";/* 调拨单类型 */

			if (ItemUtil.SQN_ITEM.equals(type)) {// --判断如果是周转件
				IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
						MroServer.getMroServer().getSystemUserServer());// --对应的周转件集合
				assetset.setQueryWhere("assetnum='" + assetnum + "'");
				assetset.reset();
				if (!assetset.isEmpty()) {// --判断对应周转件的存在
					IJpo asset=assetset.getJpo(0);
					String commomtype="入库";
					IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
							"sys_inventory",
							MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
					inventoryset.setQueryWhere("itemnum='" + itemnum
							+ "' and location='" + receivestoreroom + "'");
					inventoryset.reset();

					if (!inventoryset.isEmpty()) {// --判断入库库存集合不为空
						IJpo inventory = inventoryset.getJpo(0);
						double CURBAL = inventory.getDouble("CURBAL");
						double newCURBAL = CURBAL + djqty;
						double locqty = inventory.getDouble("locqty");
						double newlocqty = locqty + djqty;
						inventory.setValue("CURBAL", newCURBAL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						if (!transferset.isEmpty()) {
							transfertype = transferset.getJpo(0).getString(
									"type");
							if (transfertype.equalsIgnoreCase("GZZXD")) {
								inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfertype.equalsIgnoreCase("SX")) {
								String newsxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (newsxtype.equalsIgnoreCase("YXX")) {
									inventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							}
							if (transfertype.equalsIgnoreCase("ZXD")) {
								String transfermovetype = transferset.getJpo(0)
										.getString("transfermovetype");
								if (transfermovetype.equalsIgnoreCase("现场到现场")) {
									inventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
								if (transfermovetype.equalsIgnoreCase("中心到现场")) {
									inventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
								if (transfermovetype.equalsIgnoreCase("现场到中心")) {
									String newsxtype = transferset.getJpo(0)
											.getString("sxtype");
									if (newsxtype.equalsIgnoreCase("YXX")) {
										inventory
												.setValue(
														"locqty",
														newlocqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
//									if (receivestoreroom
//											.equalsIgnoreCase("Y1087")
//											|| receivestoreroom
//													.equalsIgnoreCase("QT1080")) {
//										if (!sxtype.equalsIgnoreCase("")) {
//											if (sxtype.equalsIgnoreCase("GZ")) {
//												double faultqty = inventory
//														.getDouble("faultqty");
//												double newfaultqty = faultqty
//														+ djqty;
//												inventory
//														.setValue(
//																"faultqty",
//																newfaultqty,
//																GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//											} else if (sxtype
//													.equalsIgnoreCase("GAIZ")) {
//												double remouldqty = inventory
//														.getDouble("remouldqty");
//												double newremouldqty = remouldqty
//														+ djqty;
//												inventory
//														.setValue(
//																"remouldqty",
//																newremouldqty,
//																GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//											} else if (sxtype
//													.equalsIgnoreCase("YXX")) {
//												double testingqty = inventory
//														.getDouble("testingqty");
//												double newtestingqty = testingqty
//														+ djqty;
//												inventory
//														.setValue(
//																"testingqty",
//																newtestingqty,
//																GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//											}
//										}
//									}
								}
								if (transfermovetype.equalsIgnoreCase("中心到中心")) {
									String newsxtype = transferset.getJpo(0)
											.getString("sxtype");
									if (!newsxtype.isEmpty()) {
										if (sxtype.equalsIgnoreCase("YXX")) {
											inventory
													.setValue(
															"locqty",
															newlocqty,
															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										}
									} else {
										inventory
												.setValue(
														"locqty",
														newlocqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
//									if (receivestoreroom
//											.equalsIgnoreCase("Y1087")
//											|| receivestoreroom
//													.equalsIgnoreCase("QT1080")) {
//										if (!sxtype.equalsIgnoreCase("")) {
//											if (sxtype.equalsIgnoreCase("GZ")) {
//												double faultqty = inventory
//														.getDouble("faultqty");
//												double newfaultqty = faultqty
//														+ djqty;
//												inventory
//														.setValue(
//																"faultqty",
//																newfaultqty,
//																GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//											} else if (sxtype
//													.equalsIgnoreCase("GAIZ")) {
//												double remouldqty = inventory
//														.getDouble("remouldqty");
//												double newremouldqty = remouldqty
//														+ djqty;
//												inventory
//														.setValue(
//																"remouldqty",
//																newremouldqty,
//																GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//											} else if (sxtype
//													.equalsIgnoreCase("YXX")) {
//												double testingqty = inventory
//														.getDouble("testingqty");
//												double newtestingqty = testingqty
//														+ djqty;
//												inventory
//														.setValue(
//																"testingqty",
//																newtestingqty,
//																GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//											}
//										}
//									}
								}

							}
						}
						inventoryset.save();
						assetset.getJpo(0).setValue("location",
								receivestoreroom,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.getJpo(0).setValue("INSTOREDATE", INSTOREDATE,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//						CommomCarItemLife.INOROURLOCATION(asset, commomtype);//调用一物一档入库计算方法
						// if(!sxtype.equalsIgnoreCase("")){
						// if(sxtype.equalsIgnoreCase("GZ")){
						// assetset.getJpo(0).setValue("status",
						// "故障",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// }else if(sxtype.equalsIgnoreCase("GAIZ")){
						// assetset.getJpo(0).setValue("status",
						// "改造",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// }else if(sxtype.equalsIgnoreCase("YXX")){
						// assetset.getJpo(0).setValue("status",
						// "待检测",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// }
						// }else{
						// assetset.getJpo(0).setValue("status",
						// "可用",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// }
						if (LOCATIONTYPE.equalsIgnoreCase("常规")) {
							if (receivestoreroom.equalsIgnoreCase("Y1710")) {// 再利用库
								assetset.getJpo(0)
										.setValue(
												"status",
												"再利用",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							} else {
								assetset.getJpo(0)
										.setValue(
												"status",
												"可用",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}
						if (LOCATIONTYPE.equalsIgnoreCase("维修")) {
							if (receivestoreroom.equalsIgnoreCase("Y1090")) {// 宁波维修子库
								assetset.getJpo(0)
										.setValue(
												"status",
												"故障",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (receivestoreroom.equalsIgnoreCase("Y1081")) {// 待处理库
								assetset.getJpo(0)
										.setValue(
												"status",
												"待处理",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (receivestoreroom.equalsIgnoreCase("Y1711")) {// 进口维修库
								assetset.getJpo(0)
										.setValue(
												"status",
												"故障",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (receivestoreroom.equalsIgnoreCase("Y1712")) {// 三菱维修库
								assetset.getJpo(0)
										.setValue(
												"status",
												"故障",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (receivestoreroom.equalsIgnoreCase("Y1087")) {// 中心维修库
								if (!sxtype.equalsIgnoreCase("")) {
									if (sxtype.equalsIgnoreCase("GZ")) {
										assetset.getJpo(0)
												.setValue(
														"status",
														"故障",
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									} else if (sxtype.equalsIgnoreCase("GAIZ")) {
										assetset.getJpo(0)
												.setValue(
														"status",
														"改造",
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									} else if (sxtype.equalsIgnoreCase("YXX")) {
										assetset.getJpo(0)
												.setValue(
														"status",
														"待检测",
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								}
							}
						}
						assetset.save();
					}

					if (inventoryset.isEmpty()) {// --判断入库库存集合为空
						IJpo inventory = inventoryset.addJpo();

						inventory.setValue("CURBAL", djqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						if (!transferset.isEmpty()) {
							transfertype = transferset.getJpo(0).getString(
									"type");
							if (transfertype.equalsIgnoreCase("GZZXD")) {
								inventory
										.setValue(
												"locqty",
												djqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfertype.equalsIgnoreCase("SX")) {
								String newsxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (newsxtype.equalsIgnoreCase("YXX")) {
									inventory
											.setValue(
													"locqty",
													djqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							}
							if (transfertype.equalsIgnoreCase("ZXD")) {
								String transfermovetype = transferset.getJpo(0)
										.getString("transfermovetype");
								if (transfermovetype.equalsIgnoreCase("现场到现场")) {
									inventory
											.setValue(
													"locqty",
													djqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
								if (transfermovetype.equalsIgnoreCase("中心到现场")) {
									inventory
											.setValue(
													"locqty",
													djqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
								if (transfermovetype.equalsIgnoreCase("现场到中心")) {
									String newsxtype = transferset.getJpo(0)
											.getString("sxtype");
									if (newsxtype.equalsIgnoreCase("YXX")) {
										inventory
												.setValue(
														"locqty",
														djqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
//									if (receivestoreroom
//											.equalsIgnoreCase("Y1087")
//											|| receivestoreroom
//													.equalsIgnoreCase("QT1080")) {
//										if (!sxtype.equalsIgnoreCase("")) {
//											if (sxtype.equalsIgnoreCase("GZ")) {
//												inventory
//														.setValue(
//																"faultqty",
//																djqty,
//																GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//											} else if (sxtype
//													.equalsIgnoreCase("GAIZ")) {
//												inventory
//														.setValue(
//																"remouldqty",
//																djqty,
//																GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//											} else if (sxtype
//													.equalsIgnoreCase("YXX")) {
//												inventory
//														.setValue(
//																"testingqty",
//																djqty,
//																GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//											}
//										}
//									}
								}
								if (transfermovetype.equalsIgnoreCase("中心到中心")) {
									String newsxtype = transferset.getJpo(0)
											.getString("sxtype");
									if (!newsxtype.isEmpty()) {
										if (sxtype.equalsIgnoreCase("YXX")) {
											inventory
													.setValue(
															"locqty",
															djqty,
															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										}
									} else {
										inventory
												.setValue(
														"locqty",
														djqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
//									if (receivestoreroom
//											.equalsIgnoreCase("Y1087")
//											|| receivestoreroom
//													.equalsIgnoreCase("QT1080")) {
//										if (!sxtype.equalsIgnoreCase("")) {
//											if (sxtype.equalsIgnoreCase("GZ")) {
//												inventory
//														.setValue(
//																"faultqty",
//																djqty,
//																GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//											} else if (sxtype
//													.equalsIgnoreCase("GAIZ")) {
//												inventory
//														.setValue(
//																"remouldqty",
//																djqty,
//																GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//											} else if (sxtype
//													.equalsIgnoreCase("YXX")) {
//												inventory
//														.setValue(
//																"testingqty",
//																djqty,
//																GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//											}
//										}
//									}
								}

							}
						}
						inventory.setValue("itemnum", itemnum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("location", receivestoreroom,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("siteid", siteid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("orgid", orgid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

						assetset.getJpo(0).setValue("location",
								receivestoreroom,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.getJpo(0).setValue("INSTOREDATE", INSTOREDATE,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//						CommomCarItemLife.INOROURLOCATION(asset, commomtype);//调用一物一档入库计算方法
						// if(!sxtype.equalsIgnoreCase("")){
						// if(sxtype.equalsIgnoreCase("GZ")){
						// assetset.getJpo(0).setValue("status",
						// "故障",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// }else if(sxtype.equalsIgnoreCase("GAIZ")){
						// assetset.getJpo(0).setValue("status",
						// "改造",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// }else if(sxtype.equalsIgnoreCase("YXX")){
						// assetset.getJpo(0).setValue("status",
						// "待检测",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// }
						// }else{
						// assetset.getJpo(0).setValue("status",
						// "可用",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// }
						if (LOCATIONTYPE.equalsIgnoreCase("常规")) {
							if (receivestoreroom.equalsIgnoreCase("Y1710")) {// 再利用库
								assetset.getJpo(0)
										.setValue(
												"status",
												"再利用",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							} else {
								assetset.getJpo(0)
										.setValue(
												"status",
												"可用",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}
						if (LOCATIONTYPE.equalsIgnoreCase("维修")) {
							if (receivestoreroom.equalsIgnoreCase("Y1090")) {// 宁波维修子库
								assetset.getJpo(0)
										.setValue(
												"status",
												"故障",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (receivestoreroom.equalsIgnoreCase("Y1081")) {// 待处理库
								assetset.getJpo(0)
										.setValue(
												"status",
												"待处理",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (receivestoreroom.equalsIgnoreCase("Y1711")) {// 进口维修库
								assetset.getJpo(0)
										.setValue(
												"status",
												"故障",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (receivestoreroom.equalsIgnoreCase("Y1712")) {// 三菱维修库
								assetset.getJpo(0)
										.setValue(
												"status",
												"故障",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (receivestoreroom.equalsIgnoreCase("Y1087")) {// 中心维修库
								if (!sxtype.equalsIgnoreCase("")) {
									if (sxtype.equalsIgnoreCase("GZ")) {
										assetset.getJpo(0)
												.setValue(
														"status",
														"故障",
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									} else if (sxtype.equalsIgnoreCase("GAIZ")) {
										assetset.getJpo(0)
												.setValue(
														"status",
														"改造",
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									} else if (sxtype.equalsIgnoreCase("YXX")) {
										assetset.getJpo(0)
												.setValue(
														"status",
														"待检测",
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								}
							}
						}
						inventoryset.save();
						assetset.save();
					}
				}
			} else if (ItemUtil.LOT_I_ITEM.equals(type)) {// --判断如果是批次件
				IJpoSet in_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
				in_inventoryset.setQueryWhere("itemnum='" + itemnum
						+ "' and location='" + receivestoreroom + "'");
				in_inventoryset.reset();
				if (!in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.getJpo(0);
					double CURBAL = in_inventory.getDouble("CURBAL");
					double newCURBAL = CURBAL + djqty;
					double locqty = in_inventory.getDouble("locqty");
					double newlocqty = locqty + djqty;
					if (!lotnum.equalsIgnoreCase("")) {
						IJpoSet invblanceset = MroServer.getMroServer()
								.getJpoSet(
										"invblance",
										MroServer.getMroServer()
												.getSystemUserServer());// --调拨入库批次集合
						invblanceset.setQueryWhere("itemnum='" + itemnum
								+ "' and storeroom='" + receivestoreroom
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty + djqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", djqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", receivestoreroom,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("siteid", siteid,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("orgid", orgid,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
					}

					in_inventory.setValue("CURBAL", newCURBAL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (!transferset.isEmpty()) {
						transfertype = transferset.getJpo(0).getString("type");
						if (transfertype.equalsIgnoreCase("GZZXD")) {
							in_inventory.setValue("locqty", newlocqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
						if (transfertype.equalsIgnoreCase("SX")) {
							String newsxtype = transferset.getJpo(0).getString(
									"sxtype");
							if (newsxtype.equalsIgnoreCase("YXX")) {
								in_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}
						if (transfertype.equalsIgnoreCase("ZXD")) {
							String transfermovetype = transferset.getJpo(0)
									.getString("transfermovetype");
							if (transfermovetype.equalsIgnoreCase("现场到现场")) {
								in_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfermovetype.equalsIgnoreCase("中心到现场")) {
								in_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfermovetype.equalsIgnoreCase("现场到中心")) {
								String newsxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (newsxtype.equalsIgnoreCase("YXX")) {
									in_inventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
//								if (receivestoreroom.equalsIgnoreCase("Y1087")
//										|| receivestoreroom
//												.equalsIgnoreCase("QT1080")) {
//									if (!sxtype.equalsIgnoreCase("")) {
//										if (sxtype.equalsIgnoreCase("GZ")) {
//											double faultqty = in_inventory
//													.getDouble("faultqty");
//											double newfaultqty = faultqty
//													+ djqty;
//											in_inventory
//													.setValue(
//															"faultqty",
//															newfaultqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("GAIZ")) {
//											double remouldqty = in_inventory
//													.getDouble("remouldqty");
//											double newremouldqty = remouldqty
//													+ djqty;
//											in_inventory
//													.setValue(
//															"remouldqty",
//															newremouldqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("YXX")) {
//											double testingqty = in_inventory
//													.getDouble("testingqty");
//											double newtestingqty = testingqty
//													+ djqty;
//											in_inventory
//													.setValue(
//															"testingqty",
//															newtestingqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										}
//									}
//								}
							}
							if (transfermovetype.equalsIgnoreCase("中心到中心")) {
								String newsxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (!newsxtype.isEmpty()) {
									if (sxtype.equalsIgnoreCase("YXX")) {
										in_inventory
												.setValue(
														"locqty",
														newlocqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								} else {
									in_inventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
//								if (receivestoreroom.equalsIgnoreCase("Y1087")
//										|| receivestoreroom
//												.equalsIgnoreCase("QT1080")) {
//									if (!sxtype.equalsIgnoreCase("")) {
//										if (sxtype.equalsIgnoreCase("GZ")) {
//											double faultqty = in_inventory
//													.getDouble("faultqty");
//											double newfaultqty = faultqty
//													+ djqty;
//											in_inventory
//													.setValue(
//															"faultqty",
//															newfaultqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("GAIZ")) {
//											double remouldqty = in_inventory
//													.getDouble("remouldqty");
//											double newremouldqty = remouldqty
//													+ djqty;
//											in_inventory
//													.setValue(
//															"remouldqty",
//															newremouldqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("YXX")) {
//											double testingqty = in_inventory
//													.getDouble("testingqty");
//											double newtestingqty = testingqty
//													+ djqty;
//											in_inventory
//													.setValue(
//															"testingqty",
//															newtestingqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										}
//									}
//								}
							}

						}
					}
					in_inventoryset.save();
				}
				if (in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.addJpo();

					if (!lotnum.equalsIgnoreCase("")) {
						IJpoSet invblanceset = MroServer.getMroServer()
								.getJpoSet(
										"invblance",
										MroServer.getMroServer()
												.getSystemUserServer());// --调拨入库批次集合
						invblanceset.setQueryWhere("itemnum='" + itemnum
								+ "' and storeroom='" + receivestoreroom
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty + djqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", djqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", receivestoreroom,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("siteid", siteid,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("orgid", orgid,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
					}
					in_inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("CURBAL", djqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (!transferset.isEmpty()) {
						transfertype = transferset.getJpo(0).getString("type");
						if (transfertype.equalsIgnoreCase("GZZXD")) {
							in_inventory.setValue("locqty", djqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
						if (transfertype.equalsIgnoreCase("SX")) {
							String newsxtype = transferset.getJpo(0).getString(
									"sxtype");
							if (newsxtype.equalsIgnoreCase("YXX")) {
								in_inventory
										.setValue(
												"locqty",
												djqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}
						if (transfertype.equalsIgnoreCase("ZXD")) {
							String transfermovetype = transferset.getJpo(0)
									.getString("transfermovetype");
							if (transfermovetype.equalsIgnoreCase("现场到现场")) {
								in_inventory
										.setValue(
												"locqty",
												djqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfermovetype.equalsIgnoreCase("中心到现场")) {
								in_inventory
										.setValue(
												"locqty",
												djqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfermovetype.equalsIgnoreCase("现场到中心")) {
								String newsxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (newsxtype.equalsIgnoreCase("YXX")) {
									in_inventory
											.setValue(
													"locqty",
													djqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
//								if (receivestoreroom.equalsIgnoreCase("Y1087")
//										|| receivestoreroom
//												.equalsIgnoreCase("QT1080")) {
//									if (!sxtype.equalsIgnoreCase("")) {
//										if (sxtype.equalsIgnoreCase("GZ")) {
//											in_inventory
//													.setValue(
//															"faultqty",
//															djqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("GAIZ")) {
//											in_inventory
//													.setValue(
//															"remouldqty",
//															djqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("YXX")) {
//											in_inventory
//													.setValue(
//															"testingqty",
//															djqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										}
//									}
//								}
							}
							if (transfermovetype.equalsIgnoreCase("中心到中心")) {
								String newsxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (!newsxtype.isEmpty()) {
									if (sxtype.equalsIgnoreCase("YXX")) {
										in_inventory
												.setValue(
														"locqty",
														djqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								} else {
									in_inventory
											.setValue(
													"locqty",
													djqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
//								if (receivestoreroom.equalsIgnoreCase("Y1087")
//										|| receivestoreroom
//												.equalsIgnoreCase("QT1080")) {
//									if (!sxtype.equalsIgnoreCase("")) {
//										if (sxtype.equalsIgnoreCase("GZ")) {
//											in_inventory
//													.setValue(
//															"faultqty",
//															djqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("GAIZ")) {
//											in_inventory
//													.setValue(
//															"remouldqty",
//															djqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("YXX")) {
//											in_inventory
//													.setValue(
//															"testingqty",
//															djqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										}
//									}
//								}
							}
						}
					}
					in_inventory.setValue("location", receivestoreroom,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventoryset.save();
				}
			} else {// --判断即不是周转件也不是批次件

				IJpoSet in_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
				in_inventoryset.setQueryWhere("itemnum='" + itemnum
						+ "' and location='" + receivestoreroom + "'");
				in_inventoryset.reset();

				if (!in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.getJpo(0);
					double CURBAL = in_inventory.getDouble("CURBAL");
					double newcurbal = CURBAL + djqty;
					double locqty = in_inventory.getDouble("locqty");
					double newlocqty = locqty + djqty;
					in_inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (!transferset.isEmpty()) {
						transfertype = transferset.getJpo(0).getString("type");
						if (transfertype.equalsIgnoreCase("GZZXD")) {
							in_inventory.setValue("locqty", newlocqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
						if (transfertype.equalsIgnoreCase("SX")) {
							String newsxtype = transferset.getJpo(0).getString(
									"sxtype");
							if (newsxtype.equalsIgnoreCase("YXX")) {
								in_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}
						if (transfertype.equalsIgnoreCase("ZXD")) {
							String transfermovetype = transferset.getJpo(0)
									.getString("transfermovetype");
							if (transfermovetype.equalsIgnoreCase("现场到现场")) {
								in_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfermovetype.equalsIgnoreCase("中心到现场")) {
								in_inventory
										.setValue(
												"locqty",
												newlocqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfermovetype.equalsIgnoreCase("现场到中心")) {
								String newsxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (newsxtype.equalsIgnoreCase("YXX")) {
									in_inventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
//								if (receivestoreroom.equalsIgnoreCase("Y1087")
//										|| receivestoreroom
//												.equalsIgnoreCase("QT1080")) {
//									if (!sxtype.equalsIgnoreCase("")) {
//										if (sxtype.equalsIgnoreCase("GZ")) {
//											double faultqty = in_inventory
//													.getDouble("faultqty");
//											double newfaultqty = faultqty
//													+ djqty;
//											in_inventory
//													.setValue(
//															"faultqty",
//															newfaultqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("GAIZ")) {
//											double remouldqty = in_inventory
//													.getDouble("remouldqty");
//											double newremouldqty = remouldqty
//													+ djqty;
//											in_inventory
//													.setValue(
//															"remouldqty",
//															newremouldqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("YXX")) {
//											double testingqty = in_inventory
//													.getDouble("testingqty");
//											double newtestingqty = testingqty
//													+ djqty;
//											in_inventory
//													.setValue(
//															"testingqty",
//															newtestingqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										}
//									}
//								}
							}
							if (transfermovetype.equalsIgnoreCase("中心到中心")) {
								String newsxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (!newsxtype.isEmpty()) {
									if (sxtype.equalsIgnoreCase("YXX")) {
										in_inventory
												.setValue(
														"locqty",
														newlocqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								} else {
									in_inventory
											.setValue(
													"locqty",
													newlocqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
//								if (receivestoreroom.equalsIgnoreCase("Y1087")
//										|| receivestoreroom
//												.equalsIgnoreCase("QT1080")) {
//									if (!sxtype.equalsIgnoreCase("")) {
//										if (sxtype.equalsIgnoreCase("GZ")) {
//											double faultqty = in_inventory
//													.getDouble("faultqty");
//											double newfaultqty = faultqty
//													+ djqty;
//											in_inventory
//													.setValue(
//															"faultqty",
//															newfaultqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("GAIZ")) {
//											double remouldqty = in_inventory
//													.getDouble("remouldqty");
//											double newremouldqty = remouldqty
//													+ djqty;
//											in_inventory
//													.setValue(
//															"remouldqty",
//															newremouldqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("YXX")) {
//											double testingqty = in_inventory
//													.getDouble("testingqty");
//											double newtestingqty = testingqty
//													+ djqty;
//											in_inventory
//													.setValue(
//															"testingqty",
//															newtestingqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										}
//									}
//								}
							}

						}
					}
					in_inventoryset.save();
				}
				if (in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.addJpo();
					in_inventory.setValue("CURBAL", djqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (!transferset.isEmpty()) {
						transfertype = transferset.getJpo(0).getString("type");
						if (transfertype.equalsIgnoreCase("GZZXD")) {
							in_inventory.setValue("locqty", djqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
						if (transfertype.equalsIgnoreCase("SX")) {
							String newsxtype = transferset.getJpo(0).getString(
									"sxtype");
							if (newsxtype.equalsIgnoreCase("YXX")) {
								in_inventory
										.setValue(
												"locqty",
												djqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}
						if (transfertype.equalsIgnoreCase("ZXD")) {
							String transfermovetype = transferset.getJpo(0)
									.getString("transfermovetype");
							if (transfermovetype.equalsIgnoreCase("现场到现场")) {
								in_inventory
										.setValue(
												"locqty",
												djqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfermovetype.equalsIgnoreCase("中心到现场")) {
								in_inventory
										.setValue(
												"locqty",
												djqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							if (transfermovetype.equalsIgnoreCase("现场到中心")) {
								String newsxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (newsxtype.equalsIgnoreCase("YXX")) {
									in_inventory
											.setValue(
													"locqty",
													djqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
//								if (receivestoreroom.equalsIgnoreCase("Y1087")
//										|| receivestoreroom
//												.equalsIgnoreCase("QT1080")) {
//									if (!sxtype.equalsIgnoreCase("")) {
//										if (sxtype.equalsIgnoreCase("GZ")) {
//											in_inventory
//													.setValue(
//															"faultqty",
//															djqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("GAIZ")) {
//											in_inventory
//													.setValue(
//															"remouldqty",
//															djqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("YXX")) {
//											in_inventory
//													.setValue(
//															"testingqty",
//															djqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										}
//									}
//								}
							}
							if (transfermovetype.equalsIgnoreCase("中心到中心")) {
								String newsxtype = transferset.getJpo(0)
										.getString("sxtype");
								if (!newsxtype.isEmpty()) {
									if (sxtype.equalsIgnoreCase("YXX")) {
										in_inventory
												.setValue(
														"locqty",
														djqty,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								} else {
									in_inventory
											.setValue(
													"locqty",
													djqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
//								if (receivestoreroom.equalsIgnoreCase("Y1087")
//										|| receivestoreroom
//												.equalsIgnoreCase("QT1080")) {
//									if (!sxtype.equalsIgnoreCase("")) {
//										if (sxtype.equalsIgnoreCase("GZ")) {
//											in_inventory
//													.setValue(
//															"faultqty",
//															djqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("GAIZ")) {
//											in_inventory
//													.setValue(
//															"remouldqty",
//															djqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										} else if (sxtype
//												.equalsIgnoreCase("YXX")) {
//											in_inventory
//													.setValue(
//															"testingqty",
//															djqty,
//															GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//										}
//									}
//								}
							}
						}
					}
					in_inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("location", receivestoreroom,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventoryset.save();
				}
			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
