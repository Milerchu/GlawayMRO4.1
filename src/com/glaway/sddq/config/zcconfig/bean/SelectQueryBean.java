package com.glaway.sddq.config.zcconfig.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringUtil;

public class SelectQueryBean extends DataBean {

	@Override
	public int execute() throws MroException, IOException {
		// TODO Auto-generated method stub

		String sqn = this.getString("SQN");
		String itemnum = this.getString("ITEMNUM");
		// System.out.println(sqn + " ......");
		IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet("ASSET");
		String meg = "";
		if (StringUtil.isStrEmpty(sqn)) {
			throw new MroException(" 请输入产品序列号");
		} else {
			if (StringUtil.isStrEmpty(itemnum)) {
				assetSet.setQueryWhere(" SQN='" + sqn + "'");
				assetSet.reset();
			} else {

				assetSet.setQueryWhere("SQN='" + sqn + "' and itemnum='"
						+ itemnum + "'");
				assetSet.reset();
			}
		}
		if (!assetSet.isEmpty()) {
			MroException[] warnings = new MroException[assetSet.count()];
			for (int i = 0; i < assetSet.count(); i++) {
				IJpo asset = assetSet.getJpo(i);
				String type = asset.getString("TYPE");
				String cpitemnum=asset.getString("ITEMNUM");
				String location = asset.getString("LOCATION");
				// type = ‘0’ , MES传递的数据
				// type = ‘1’, 尚未上过车的库存产品
				// type = ‘2’, 车上的配置产品
				// type = ‘3’, 下车的库存产品
				if ("0".equals(type)) {
					// MES 初始数据 上级 ？
					// 产品（序列号）在初始配置中，上级产品为图号： 序列号：
					IJpoSet parentassetSet = MroServer.getMroServer()
							.getSysJpoSet("ASSET");
					parentassetSet.setQueryWhere(" assetnum ='"
							+ asset.getString("PARENT") + "' ");
					if (parentassetSet.isEmpty()) {
						// 产品（序列号）在初始配置中，无上级产品
						meg = "产品(" + sqn + ")在初始配置中，无上级";
					} else {
						// 产品（序列号）在初始配置中，有上级产品
						IJpo passet = parentassetSet.getJpo(0);
						IJpoSet parentassetset = MroServer.getMroServer()
								.getSysJpoSet("ASSET");
						parentassetset.setQueryWhere(" assetnum ='"
								+ passet.getString("PARENT") + "' ");
						if (parentassetset.isEmpty()) {
							// 只有一个上级产品
							meg = "产品(" + sqn + ")在初始配置中，上级产品为图号("
									+ passet.getString("ITEMNUM") + ")序列号("
									+ passet.getString("SQN") + ").";
						} else {
							IJpo pasSet = parentassetset.getJpo(0);
							meg = "产品(" + sqn + ")在初始配置产品图号为("
									+ pasSet.getString("ITEMNUM") + ")序列号为("
									+ pasSet.getString("SQN") + ")，上级产品为图号("
									+ passet.getString("ITEMNUM") + ")序列号("
									+ passet.getString("SQN") + "下).";
						}
					}
				} else if ("1".equals(type)) {
					// 尚未上过车的库存产品
					// 产品（序列号）在：库房中
					IJpoSet parentassetSet = MroServer.getMroServer()
							.getSysJpoSet("ASSET");
					parentassetSet.setQueryWhere(" assetnum ='"
							+ asset.getString("PARENT") + "' ");
					if (parentassetSet.isEmpty() && location.isEmpty()) {
						// 产品（序列号）在初始配置中，无上级产品
						meg = "产品(" + sqn + ")在初始配置中，无上级";
					} else if (parentassetSet.isEmpty() && !location.isEmpty()) {
						meg = "产品(" + sqn + ")在" + location + "库房中";
					} else {
						// 产品（序列号）在初始配置中，有上级产品
						IJpo passet = parentassetSet.getJpo(0);
						IJpoSet parentassetset = MroServer.getMroServer()
								.getSysJpoSet("ASSET");
						parentassetset.setQueryWhere(" assetnum ='"
								+ passet.getString("PARENT") + "' ");
						if (parentassetset.isEmpty()) {
							// 只有一个上级产品
							meg = "产品(" + sqn + ")在初始配置中，上级产品为图号("
									+ passet.getString("ITEMNUM") + ")序列号("
									+ passet.getString("SQN") + "下).";
						} else {
							IJpo pasSet = parentassetset.getJpo(0);
							meg = "产品(" + sqn + ")在初始配置产品图号为("
									+ pasSet.getString("ITEMNUM") + ")序列号为("
									+ pasSet.getString("SQN") + "，上级产品为图号("
									+ passet.getString("ITEMNUM") + ")序列号："
									+ passet.getString("SQN") + "下).";
						}
					}
				} else if ("2".equals(type)) {
					IJpoSet parentassetSet = MroServer.getMroServer()
							.getSysJpoSet("ASSET");
					parentassetSet.setQueryWhere(" assetnum ='"
							+ asset.getString("PARENT") + "' ");
					parentassetSet.reset();

					if (!parentassetSet.isEmpty()) {
						// 产品（序列号）在装车配置中
						IJpo passet = parentassetSet.getJpo(0);
						IJpoSet parentassetset = MroServer.getMroServer()
								.getSysJpoSet("ASSET");
						parentassetset.setQueryWhere(" assetnum ='"
								+ passet.getString("PARENT") + "' ");
						parentassetset.reset();
						// 第一层上级
						String assetlevel = parentassetSet.getJpo().getString(
								"ASSETLEVEL");
						String parentitemnum = parentassetSet.getJpo()
								.getString("ITEM.DESCRIPTION");
						if (!parentassetset.isEmpty()
								&& "SYSTEM".equals(assetlevel)) {
							// 产品（序列号）在装车配置中，有一个上级产品
							// IJpo passset = parentassetset.getJpo(0);
							IJpoSet parentSet = MroServer.getMroServer()
									.getSysJpoSet("ASSET");
							parentSet.setQueryWhere(" assetnum ='"
									+ parentassetSet.getJpo().getString(
											"PARENT") + "' ");
							parentSet.reset();
							String level = parentassetset.getJpo().getString(
									"ASSETLEVEL");
							String parentitem = parentassetset.getJpo()
									.getString("ITEM.DESCRIPTION");
							if (!parentSet.isEmpty() && "SYSTEM".equals(level)) {
								// 产品（序列号）在装车配置中，有二个上级产品
								// IJpo paset = parentSet.getJpo(0);
								IJpoSet parentset = MroServer.getMroServer()
										.getSysJpoSet("ASSET");
								parentset.setQueryWhere(" assetnum ='"
										+ parentassetset.getJpo().getString(
												"PARENT") + "' ");
								parentset.reset();
								String Level = parentset.getJpo().getString(
										"ASSETLEVEL");
								String Parentitem = parentset.getJpo()
										.getString("ITEM.DESCRIPTION");
								if (!parentset.isEmpty()
										&& "SYSTEM".equals(Level)) {
									IJpo towasset = parentset.getJpo(0);
									IJpoSet towparentset = MroServer
											.getMroServer().getSysJpoSet(
													"ASSET");
									towparentset.setQueryWhere(" assetnum ='"
											+ towasset.getString("PARENT")
											+ "' ");
									towparentset.reset();
									String towLevel = towparentset.getJpo()
											.getString("ASSETLEVEL");
									String towarentitem = towparentset.getJpo()
											.getString("ITEM.DESCRIPTION");
									if (!towparentset.isEmpty()
											&& "SYSTEM".equals(towLevel)) {
										IJpo threeasset = towparentset
												.getJpo(0);
										IJpoSet threeparentset = MroServer
												.getMroServer().getSysJpoSet(
														"ASSET");
										threeparentset
												.setQueryWhere(" assetnum ='"
														+ threeasset
																.getString("PARENT")
														+ "' ");
										threeparentset.reset();
										String threeLevel = threeparentset
												.getJpo().getString(
														"ASSETLEVEL");
										String threearentitem = threeparentset
												.getJpo().getString(
														"ITEM.DESCRIPTION");
										if (!threeparentset.isEmpty()
												&& "SYSTEM".equals(threeLevel)) {
											IJpo fourasset = threeparentset
													.getJpo(0);
											IJpoSet fourparentset = MroServer
													.getMroServer()
													.getSysJpoSet("ASSET");
											fourparentset
													.setQueryWhere(" assetnum ='"
															+ fourasset
																	.getString("PARENT")
															+ "' ");
											fourparentset.reset();
											String fourLevel = fourparentset
													.getJpo().getString(
															"ASSETLEVEL");
											String fourarentitem = fourparentset
													.getJpo().getString(
															"ITEM.DESCRIPTION");
										} else if (!threeparentset.isEmpty()
												&& "CAR".equals(threeLevel)) {
											String carriagenum = threeparentset
													.getJpo().getString(
															"CARRIAGENUM");
											String carparent = threeparentset
													.getJpo().getString(
															"PARENT");
											IJpoSet tparentset = MroServer
													.getMroServer()
													.getSysJpoSet("ASSET");
											tparentset
													.setQueryWhere(" assetnum ='"
															+ carparent + "' ");
											tparentset.reset();
											if (tparentset.isEmpty()) {
												meg = "装车配置中该产品(" + sqn
														+ ")所在的车无法找到....,物料编码("+cpitemnum+").";
											}else{
												String tLevel = tparentset.getJpo()
														.getString("ASSETLEVEL");
												if (!tparentset.isEmpty()
														&& "ASSET".equals(tLevel)) {
													String cmodel = tparentset
															.getJpo().getString(
																	"CMODEL");
													String carno = tparentset
															.getJpo().getString(
																	"CARNO");
													meg = "产品(" + sqn
															+ ")在装车配置车型编号为("
															+ cmodel + "),车号("
															+ carno + ")," + "车厢("
															+ carriagenum + "),("
															+ towarentitem + ","
															+ Parentitem + ","
															+ parentitem + ","
															+ parentitemnum + "下).";
													;
												}
											}										
										}
									} else if (!towparentset.isEmpty()
											&& "CAR".equals(towLevel)) {
										String carriagenum = towparentset
												.getJpo().getString(
														"CARRIAGENUM");
										String carparent = towparentset
												.getJpo().getString("PARENT");
										IJpoSet tparentset = MroServer
												.getMroServer().getSysJpoSet(
														"ASSET");
										tparentset.setQueryWhere(" assetnum ='"
												+ carparent + "' ");
										tparentset.reset();
										if (tparentset.isEmpty()) {
											meg = "装车配置中该产品(" + sqn
													+ ")所在的车无法找到....,物料编码("+cpitemnum+").";
										} else {
											String tLevel = tparentset.getJpo()
													.getString("ASSETLEVEL");
											if (!tparentset.isEmpty()
													&& "ASSET".equals(tLevel)) {
												String cmodel = tparentset
														.getJpo().getString(
																"CMODEL");
												String carno = tparentset
														.getJpo().getString(
																"CARNO");
												meg = "产品(" + sqn
														+ ")在装车配置车型编号为("
														+ cmodel + "),车号("
														+ carno + "),车厢("
														+ carriagenum + "),("
														+ Parentitem + ","
														+ parentitem + ","
														+ parentitemnum + "下).";
											}
										}
									}
								} else if (!parentset.isEmpty()
										&& "CAR".equals(Level)) {
									String carriagenum = parentset.getJpo()
											.getString("CARRIAGENUM");
									String carparent = parentset.getJpo()
											.getString("PARENT");
									IJpoSet tparentset = MroServer
											.getMroServer().getSysJpoSet(
													"ASSET");
									tparentset.setQueryWhere(" assetnum ='"
											+ carparent + "' ");
									tparentset.reset();
									if (tparentset.isEmpty()) {
										meg = "装车配置中该产品(" + sqn
												+ ")所在的车无法找到....,物料编码("+cpitemnum+").";
									} else {
										String tLevel = tparentset.getJpo()
												.getString("ASSETLEVEL");
										if (!tparentset.isEmpty()
												&& "ASSET".equals(tLevel)) {
											String cmodel = tparentset.getJpo()
													.getString("CMODEL");
											String carno = tparentset.getJpo()
													.getString("CARNO");
											meg = "产品(" + sqn + ")在装车配置车型编号为("
													+ cmodel + "),车号(" + carno
													+ "),车厢(" + carriagenum
													+ "),(" + parentitem + ","
													+ parentitemnum + "下).";
										}
									}

								}
							} else if (!parentSet.isEmpty()
									&& "CAR".equals(level)) {
								String carriagenum = parentassetset.getJpo()
										.getString("CARRIAGENUM");
								String carparent = parentassetset.getJpo()
										.getString("PARENT");
								IJpoSet parentset = MroServer.getMroServer()
										.getSysJpoSet("ASSET");
								parentset.setQueryWhere(" assetnum ='"
										+ carparent + "' ");
								parentset.reset();
								if (parentset.isEmpty()) {
									meg = "装车配置中该产品(" + sqn + ")所在的车无法找到....,物料编码("+cpitemnum+").";
								} else {
									String Level = parentset.getJpo()
											.getString("ASSETLEVEL");
									if (!parentset.isEmpty()
											&& "ASSET".equals(Level)) {
										String cmodel = parentset.getJpo()
												.getString("CMODEL");
										String carno = parentset.getJpo()
												.getString("CARNO");
										meg = "产品(" + sqn + ")在装车配置车型编号为("
												+ cmodel + "),车号(" + carno
												+ "),车厢(" + carriagenum + "),("
												+ parentitemnum + "下).";
									}
								}
							}
						} else if (!parentassetset.isEmpty()
								|| "CAR".equals(assetlevel)) {
							String carriagenum = parentassetSet.getJpo()
									.getString("CARRIAGENUM");
							String carparent = parentassetSet.getJpo()
									.getString("PARENT");
							IJpoSet parentset = MroServer.getMroServer()
									.getSysJpoSet("ASSET");
							parentset.setQueryWhere(" assetnum ='" + carparent
									+ "' ");
							parentset.reset();
							if (parentset.isEmpty()) {
								meg = "装车配置中该产品(" + sqn + ")所在的车无法找到....,物料编码("+cpitemnum+").";
							} else {
								String Level = parentset.getJpo().getString(
										"ASSETLEVEL");
								if (!parentset.isEmpty()
										&& "ASSET".equals(Level)) {
									String cmodel = parentset.getJpo()
											.getString("CMODEL");
									String carno = parentset.getJpo()
											.getString("CARNO");
									meg = "产品(" + sqn + ")在装车配置车型编号为(" + cmodel
											+ "),车号(" + carno + "),车厢("
											+ carriagenum + "下).";
								}
							}
						}
					}
				} else if ("3".equals(type)) {
					// 下车的库存产品
					IJpoSet parentassetSet = MroServer.getMroServer()
							.getSysJpoSet("ASSET");
					parentassetSet.setQueryWhere(" assetnum ='"
							+ asset.getString("PARENT") + "' ");
					if (parentassetSet.isEmpty()) {
						// 产品（序列号）在初始配置中，无上级产品
						meg = "产品（" + sqn + "）在车下配置中，无上级";
					} else {
						// 产品（序列号）在初始配置中，无上级产品
						IJpo passet = parentassetSet.getJpo(0);
						IJpoSet parentassetset = MroServer.getMroServer()
								.getSysJpoSet("ASSET");
						parentassetset.setQueryWhere(" assetnum ='"
								+ passet.getString("PARENT") + "' ");
						if (parentassetset.isEmpty()) {
							// 只有一个上级产品
							meg = "产品(" + sqn + ")在车下配置中，上级产品为图号("
									+ passet.getString("ITEMNUM") + ")序列号("
									+ passet.getString("SQN") + "下).";
						} else {
							IJpo pasSet = parentassetset.getJpo(0);

							meg = "产品(" + sqn + ")在车下配置产品图号为("
									+ pasSet.getString("ITEMNUM") + ")序列号为("
									+ pasSet.getString("SQN") + ")，上级产品为图号("
									+ passet.getString("ITEMNUM") + ")序列号("
									+ passet.getString("SQN") + "下).";
						}
					}
				}
				MroException e = new MroException(meg);
				warnings[i] = e;
			}
			showWarnings(warnings);
			this.dialogclose();
		} else {
			throw new MroException("未找到....");
		}

		return super.execute();
	}
}
