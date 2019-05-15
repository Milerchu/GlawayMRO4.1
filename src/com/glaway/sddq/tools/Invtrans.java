package com.glaway.sddq.tools;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <功能描述> 出入库工具类
 * 
 * @author 20167088
 * @version [版本号, 2018-8-9]
 * @since [产品/模块版本]
 */
public class Invtrans {

	/**
	 * 
	 * <功能描述>
	 * 
	 * @param transfernum
	 *            调拨单
	 * @param workerorder
	 *            工单号
	 * @param itemnum
	 *            物料编码
	 * @param cloc
	 *            出库库房
	 * @param rloc
	 *            入库库房
	 * @param qty
	 *            数量
	 * @param jpo
	 * @param assetnum
	 * @param xlh
	 *            是否序列号
	 * @param sqn
	 *            序列号
	 * @param pc
	 *            是否批次
	 * @param pch
	 *            批次号
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void recive(String transfernum, String workerorder, String itemnum, String cloc, String rloc, double qty, IJpo jpo, String assetnum, boolean xlh, String sqn, boolean pc, String pch) throws MroException {
		try {// IJpo item = itemSet.getJpo(0);
			String PROJNUM = jpo.getParent().getJpoSet("PROJECT").getJpo().getString("WORKORDERNUM");
			java.util.Date INSTOREDATE=MroServer.getMroServer().getDate();
			IJpoSet InvtransSet = jpo.getJpoSet("$invtrans", "INVTRANS", "1=2");// 出入库交易表
			if (!cloc.isEmpty()) {
				IJpoSet inventorySet = jpo.getJpoSet("$SYS_INVENTORY",
						"SYS_INVENTORY", "itemnum='" + itemnum + "' and LOCATION = '" + cloc +
								"'");// 出库的
				if (inventorySet.isEmpty()) {
					IJpo inventor = inventorySet.addJpo();
					inventor.setValue("itemnum", itemnum, 2L);
					inventor.setValue("LOCATION", cloc, 2L);
					inventor.setValue("CURBAL", qty, 2L);// 余量
					inventor.setValue("locqty", qty, 2L);
				} else {
					IJpo inventor = inventorySet.getJpo(0);
					inventor.setValue("CURBAL", inventor.getDouble("CURBAL") - qty, 2L);// 出库库存表
					inventor.setValue("locqty", inventor.getDouble("locqty") - qty, 2L);
					System.out.println(inventor.getDouble("CURBAL"));
				}
				IJpo Invtrans = InvtransSet.addJpo();// 出库记录
				Invtrans.setValue("itemnum", itemnum, 2L);
				Invtrans.setValue("TRANSTYPE", "出库", 2L);
				Invtrans.setValue("TRANSFERNUM", transfernum, 2L);// 装箱单号
				Invtrans.setValue("QTY", qty, 2L);
				Invtrans.setValue("SQN", sqn, 2L);
				Invtrans.setValue("LOTNUM", pch, 2L);
				Invtrans.setValue("TASKNUM", workerorder, 2L);

				Invtrans.setValue("STOREROOM", cloc, 2L);
				Invtrans.setValue("TRANSDATE", MroServer.getMroServer().getDate(), 2L);
			}
			/**
			 * 入库的
			 */
			if (!rloc.isEmpty()) {
				IJpoSet inventorysSet = jpo.getJpoSet("$SYS_INVENTORY0",
						"SYS_INVENTORY", "itemnum='" + itemnum + "' and LOCATION = '" + rloc + // 入库
								"'");//
				if (inventorysSet.isEmpty()) {
					IJpo inventors = inventorysSet.addJpo();
					inventors.setValue("itemnum", itemnum, 2L);
					inventors.setValue("LOCATION", rloc, 2L);
					inventors.setValue("CURBAL", qty, 2L);// 余量
					inventors.setValue("locqty", qty, 2L);
				} else {
					IJpo inventors = inventorysSet.getJpo(0);
					inventors.setValue("CURBAL", inventors.getDouble("CURBAL") + qty, 2L);// 入库
					inventors.setValue("locqty", inventors.getDouble("locqty") + qty, 2L);
				}
				IJpo Invtranss = InvtransSet.addJpo();// 入库记录
				Invtranss.setValue("itemnum", itemnum, 2L);
				Invtranss.setValue("TRANSTYPE", "入库", 2L);
				Invtranss.setValue("QTY", qty, 2L);
				Invtranss.setValue("TASKNUM", workerorder, 2L);
				Invtranss.setValue("SQN", sqn, 2L);
				Invtranss.setValue("LOTNUM", pch, 2L);

				Invtranss.setValue("TRANSFERNUM", transfernum, 2L);
				Invtranss.setValue("STOREROOM", rloc, 2L);
				Invtranss.setValue("TRANSDATE", MroServer.getMroServer().getDate(), 2L);
				if (xlh != false) {// 序列号判断
					IJpoSet assetSet = jpo.getJpoSet("$ASSET", "asset", "assetnum='" + assetnum + "'");//
					if (assetSet.isEmpty()) {
						IJpo asset = assetSet.addJpo();
						asset.setValue("itemnum", itemnum);
						asset.setValue("sqn", sqn);
						asset.setValue("location", rloc);
						asset.setValue("INSTOREDATE", MroServer.getMroServer().getDate());
						asset.setValue("secretleveldes", "非密");
						asset.setValue("status", "可用", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						asset.setValue("isnew", "1", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						asset.setValue("iserp", "0", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						asset.setValue("type", "1");
						asset.setValue("ANCESTOR", asset.getString("assetnum"));
						asset.setValue("INSTOREDATE", INSTOREDATE, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						asset.setValue("PROJNUM", PROJNUM, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						asset.setValue("MSGFLAG", SddqConstant.MSG_INFO_NOSQN, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						asset.setValue("FROMSOURCE", SddqConstant.FROMSOURCE_XP, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

//						IJpoSet assettreeSet = asset.getJpoSet("ASSETANCESTOR");
//						IJpo assettree = assettreeSet.addJpo();
//						assettree.setValue("ANCESTOR", asset.getString("ANCESTOR"));
//						assettree.setValue("assetnum", asset.getString("assetnum"));
//						assettree.setValue("HIERARCHYLEVELS", 0);
					}
					else {
						IJpo asset = assetSet.getJpo(0);
						asset.setValue("location", rloc);
						asset.setValue("status", "可用", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					}
				}
			}

			if (pc != false) {// INVBLANCE
				IJpoSet invblanceSet = jpo.getJpoSet("$INVBLANCE", "INVBLANCE", "LOTNUM='" + pch + "'");//
				IJpo invblance = null;
				if (invblanceSet.isEmpty()) {
					invblance = invblanceSet.addJpo();
					invblance.setValue("LOTNUM", pch);// 批次号
					invblance.setValue("ITEMNUM", itemnum);// 物料编码
					invblance.setValue("RECEIVEDATE", MroServer.getMroServer().getDate());// 入库时间
					invblance.setValue("STOREROOM", rloc);// 接收库房
					invblance.setValue("PHYSCNTQTY", qty, 56L);// 接收数量
				} else {
					invblance = invblanceSet.getJpo(0);
					invblance.setValue("PHYSCNTQTY", invblance.getDouble("PHYSCNTQTY") + qty);// 接收数量
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// jpo.getThisJpoSet().save();
	}// binnum

	/**
	 * 
	 * <功能描述>
	 * 
	 * @param transfernum
	 *            调拨单
	 * @param workerorder
	 *            工单号
	 * @param itemnum
	 *            物料编码
	 * @param binnum
	 *            仓位
	 * @param cloc
	 *            出库库房
	 * @param rloc
	 *            入库库房
	 * @param qty
	 *            数量
	 * @param jpo
	 * @param assetnum
	 * @param xlh
	 *            是否序列号
	 * @param sqn
	 *            序列号
	 * @param pc
	 *            是否批次
	 * @param pch
	 *            批次号
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void recive(String transfernum, String workerorder, String itemnum, String binnum, String cloc, String rloc, double qty, IJpo jpo, String assetnum, boolean xlh, String sqn, boolean pc, String pch) throws MroException {
		try {// IJpo item = itemSet.getJpo(0);
			IJpoSet InvtransSet = jpo.getJpoSet("$invtrans", "INVTRANS", "1=2");// 出入库交易表
			if (!cloc.isEmpty()) {
				IJpoSet inventorySet = jpo.getJpoSet("$SYS_INVENTORY",
						"SYS_INVENTORY", "itemnum='" + itemnum + "' and LOCATION = '" + cloc +
								"'");// 出库的
				if (inventorySet.isEmpty()) {
					IJpo inventor = inventorySet.addJpo();
					inventor.setValue("itemnum", itemnum, 2L);
					inventor.setValue("LOCATION", cloc, 2L);
					inventor.setValue("CURBAL", qty, 2L);// 余量
				} else {
					IJpo inventor = inventorySet.getJpo(0);
					inventor.setValue("CURBAL", inventor.getDouble("CURBAL") - qty, 2L);// 出库库存表
					//System.out.println(inventor.getDouble("CURBAL"));
				}
				IJpo Invtrans = InvtransSet.addJpo();// 出库记录
				Invtrans.setValue("itemnum", itemnum, 2L);
				Invtrans.setValue("TRANSTYPE", "出库", 2L);
				Invtrans.setValue("TRANSFERNUM", transfernum, 2L);// 装箱单号
				Invtrans.setValue("QTY", qty, 2L);
				Invtrans.setValue("SQN", sqn, 2L);
				Invtrans.setValue("LOTNUM", pch, 2L);
				Invtrans.setValue("TASKNUM", workerorder, 2L);

				Invtrans.setValue("STOREROOM", cloc, 2L);
				Invtrans.setValue("TRANSDATE", MroServer.getMroServer().getDate(), 2L);
			}
			/**
			 * 入库的
			 */
			if (!rloc.isEmpty()) {
				IJpoSet inventorysSet = jpo.getJpoSet("$SYS_INVENTORY0",
						"SYS_INVENTORY", "itemnum='" + itemnum + "' and LOCATION = '" + rloc + // 入库
								"'");//
				if (inventorysSet.isEmpty()) {
					IJpo inventors = inventorysSet.addJpo();
					inventors.setValue("itemnum", itemnum, 2L);
					inventors.setValue("LOCATION", rloc, 2L);
					inventors.setValue("CURBAL", qty, 2L);// 余量
				} else {
					IJpo inventors = inventorysSet.getJpo(0);
					inventors.setValue("CURBAL", inventors.getDouble("CURBAL") + qty, 2L);// 入库
				}
				IJpo Invtranss = InvtransSet.addJpo();// 入库记录
				Invtranss.setValue("itemnum", itemnum, 2L);
				Invtranss.setValue("TRANSTYPE", "入库", 2L);
				Invtranss.setValue("QTY", qty, 2L);
				Invtranss.setValue("TASKNUM", workerorder, 2L);
				Invtranss.setValue("SQN", sqn, 2L);
				Invtranss.setValue("LOTNUM", pch, 2L);

				Invtranss.setValue("TRANSFERNUM", transfernum, 2L);
				Invtranss.setValue("STOREROOM", rloc, 2L);
				Invtranss.setValue("TRANSDATE", MroServer.getMroServer().getDate(), 2L);
				if (xlh != false) {// 序列号判断
					IJpoSet assetSet = jpo.getJpoSet("$ASSET", "asset", "assetnum='" + assetnum + "'");//
					if (assetSet.isEmpty()) {
						IJpo asset = assetSet.addJpo();
						asset.setValue("itemnum", itemnum);
						asset.setValue("sqn", sqn);
						asset.setValue("location", rloc);
						asset.setValue("INSTOREDATE", MroServer.getMroServer().getDate());
						asset.setValue("secretleveldes", "非密");
						asset.setValue("status", "可用", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						asset.setValue("isnew", "1", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						asset.setValue("type", "1");
						asset.setValue("ANCESTOR", asset.getString("assetnum"));

						IJpoSet assettreeSet = asset.getJpoSet("ASSETANCESTOR");
						IJpo assettree = assettreeSet.addJpo();
						assettree.setValue("ANCESTOR", asset.getString("ANCESTOR"));
						assettree.setValue("assetnum", asset.getString("assetnum"));
						assettree.setValue("HIERARCHYLEVELS", 0);
					}
					else {
						IJpo asset = assetSet.getJpo(0);
						asset.setValue("location", rloc);
						asset.setValue("status", "可用", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					}
				}
			}

			if (pc != false) {// INVBLANCE
				IJpoSet invblanceSet = jpo.getJpoSet("$INVBLANCE", "INVBLANCE", "LOTNUM='" + pch + "'");//
				IJpo invblance = null;
				if (invblanceSet.isEmpty()) {
					invblance = invblanceSet.addJpo();
					invblance.setValue("LOTNUM", pch);// 批次号
					invblance.setValue("ITEMNUM", itemnum);// 物料编码
					invblance.setValue("RECEIVEDATE", MroServer.getMroServer().getDate());// 入库时间
					invblance.setValue("STOREROOM", rloc);// 接收库房
					invblance.setValue("PHYSCNTQTY", qty, 56L);// 接收数量
				} else {
					invblance = invblanceSet.getJpo(0);
					invblance.setValue("PHYSCNTQTY", invblance.getDouble("PHYSCNTQTY") + qty);// 接收数量
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// jpo.getThisJpoSet().save();
	}// binnum

}
