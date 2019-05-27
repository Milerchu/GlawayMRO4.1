package com.glaway.sddq.material.transferline.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.material.invtrans.common.InventoryQtyCommon;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;
/**
 * 
 * <缴库单变更序列号弹出框绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-2-22]
 * @since  [产品/模块版本]
 */
public class JkdChangeSqnDataBean extends DataBean {
	/**
	 * 确认接收按钮方法
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int execute() throws MroException, IOException {
		if (this.getJpo() != null) {
			IJpo parent = this.getJpo().getParent();
			double passqty = this.getJpo().getDouble("passqty");
			double failqty = this.getJpo().getDouble("failqty");
			double JSQTY = this.getJpo().getDouble("JSQTY");
			double newqty = passqty + failqty;
			String STATUS = parent.getString("STATUS");
			String itemnum=parent.getString("itemnum");
			String issuestoreroom=parent.getString("issuestoreroom");
			String receivestoreroom=parent.getString("receivestoreroom");
			String transferlinenum = parent.getString("transferlinenum");
			String sxdtransfernum = parent.getParent().getString("SENDNUM");
			IJpoSet sxdtransferset = MroServer.getMroServer().getJpoSet(
					"transfer", MroServer.getMroServer().getSystemUserServer());
			sxdtransferset.setUserWhere("transfernum='" + sxdtransfernum + "'");
			sxdtransferset.reset();
			IJpoSet sxdtransferlineset = MroServer.getMroServer().getJpoSet(
					"transferline",
					MroServer.getMroServer().getSystemUserServer());
			sxdtransferlineset.setUserWhere("transfernum='" + sxdtransfernum
					+ "' and transferlinenum='" + transferlinenum + "'");
			sxdtransferlineset.reset();
			if (STATUS.equalsIgnoreCase("未接收")) {
				String newassetnum=parent.getString("NEWASSETNUM");
				if(newassetnum.isEmpty()){
					ADDASSET();
				}
				String SENDNUM = this.getParent().getParent()
						.getString("SENDNUM");
				if (SENDNUM.equalsIgnoreCase("")) {
					double statuswjsqty = this.getJpo().getDouble("wjsqty");
					double statusjsqty = this.getJpo().getDouble("jsqty");
					String transfernum = parent.getString("transfernum");
					String transferlineid = parent.getString("transferlineid");
					parent.setValue("yjsqty", JSQTY,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.setValue("status", "已接收",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.setValue("jkdlineid", transferlineid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (statuswjsqty == statusjsqty) {
						IJpoSet transferlineset = MroServer.getMroServer()
								.getJpoSet(
										"transferline",
										MroServer.getMroServer()
												.getSystemUserServer());
						transferlineset.setUserWhere("transfernum='"
								+ transfernum
								+ "' and status!='已接收' and transferlineid!='"
								+ transferlineid + "'");
						if (transferlineset.isEmpty()) {
							parent.getParent().setValue("status", "已接收",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					}
					// 调用出库方法
					OUTSTOREROOM();
					// 调用出库记录方法
					OUTINVTRANS();
					// 调用入库方法
					INSTOREROOM();
					// 调用入库记录方法
					ININVTRANS();
					InventoryQtyCommon.SQZYQTY(itemnum, issuestoreroom);
					InventoryQtyCommon.FCZTQTY(itemnum, issuestoreroom);
					InventoryQtyCommon.JSZTQTY(itemnum, issuestoreroom);
					InventoryQtyCommon.KYQTY(itemnum, issuestoreroom);
					
					InventoryQtyCommon.SQZYQTY(itemnum, receivestoreroom);
					InventoryQtyCommon.FCZTQTY(itemnum, receivestoreroom);
					InventoryQtyCommon.JSZTQTY(itemnum, receivestoreroom);
					InventoryQtyCommon.KYQTY(itemnum, receivestoreroom);
				} else {
					parent.setValue("yjsqty", newqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.setValue("status", "已接收",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					if (!sxdtransferlineset.isEmpty()) {
						double sxpassqty = sxdtransferlineset.getJpo(0)
								.getDouble("passqty");
						double sxfailqty = sxdtransferlineset.getJpo(0)
								.getDouble("failqty");
						double orderqty = sxdtransferlineset.getJpo(0)
								.getDouble("ORDERQTY");
						double newsxpassqty = sxpassqty + passqty;
						double newsxfailqty = sxfailqty + failqty;
						double neworderqty = newsxpassqty + newsxfailqty;
						sxdtransferlineset.getJpo(0).setValue("passqty",
								newsxpassqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						sxdtransferlineset.getJpo(0).setValue("failqty",
								newsxfailqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						if (orderqty == neworderqty) {
							sxdtransferlineset.getJpo(0).setValue("status",
									"全部缴库",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						} else {
							sxdtransferlineset.getJpo(0).setValue("status",
									"部分缴库",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
						sxdtransferlineset.save();
					}
					IJpoSet newsxdtransferlineset = MroServer.getMroServer()
							.getJpoSet(
									"transferline",
									MroServer.getMroServer()
											.getSystemUserServer());
					newsxdtransferlineset.setUserWhere("transfernum='"
							+ sxdtransfernum + "' and status!='全部缴库'");
					newsxdtransferlineset.reset();
					if (newsxdtransferlineset.count() == 0) {
						sxdtransferset.getJpo(0).setValue("status", "全部缴库",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						parent.getParent().setValue("status", "全部缴库",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					} else {
						sxdtransferset.getJpo(0).setValue("status", "部分缴库",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						parent.getParent().setValue("status", "部分缴库",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					sxdtransferset.save();
					// 调用出库方法
					OUTSTOREROOM();
					// 调用出库记录方法
					OUTINVTRANS();
					// 调用入库方法
					INSTOREROOM();
					// 调用入库记录方法
					ININVTRANS();
					InventoryQtyCommon.SQZYQTY(itemnum, issuestoreroom);
					InventoryQtyCommon.FCZTQTY(itemnum, issuestoreroom);
					InventoryQtyCommon.JSZTQTY(itemnum, issuestoreroom);
					InventoryQtyCommon.KYQTY(itemnum, issuestoreroom);
					
					InventoryQtyCommon.SQZYQTY(itemnum, receivestoreroom);
					InventoryQtyCommon.FCZTQTY(itemnum, receivestoreroom);
					InventoryQtyCommon.JSZTQTY(itemnum, receivestoreroom);
					InventoryQtyCommon.KYQTY(itemnum, receivestoreroom);
					String DEALTYPE = parent.getString("DEALTYPE");
					if (DEALTYPE.equalsIgnoreCase("返回") && passqty > 0) {// 通知返修发运人
						// 调用通知返修发运人方法
						CALLFXFY();
					}
					if (!DEALTYPE.equalsIgnoreCase("返回") && passqty > 0) {// 通知返修发运人
						// 调用通知方向发运人关联配件申请发货方法
						PJSQCALLFXFY();
					}
				}
			} else {
				throw new MroException("jkdtransfer", "noreceive");
			}
		}
		this.getAppBean().SAVE();
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	/**
	 * 
	 * 
	 * <出库方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void OUTSTOREROOM() throws MroException {
		IJpo parent = this.getJpo().getParent();
		double passqty = this.getJpo().getDouble("passqty");/* 合格数量 */
		double failqty = this.getJpo().getDouble("failqty");/* 报废数量 */
		double JSQTY = this.getJpo().getDouble("JSQTY");/* 接收数量 */
		String itemnum = parent.getString("itemnum");
		String Sqn = parent.getString("sqn");
		String lotnum = parent.getString("lotnum");
		String assetnum = parent.getString("assetnum");
		String RECEIVESTOREROOM = parent.getString("ISSUESTOREROOM");/* 缴库单接收库房 */
		String ISSUESTOREROOM = parent.getString("RECEIVESTOREROOM");/* 缴库单发出库房 */
		if (passqty > 0) {
			if (!Sqn.isEmpty()) {
				IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
						MroServer.getMroServer().getSystemUserServer());// --对应的周转件集合
				assetset.setUserWhere("assetnum='" + assetnum + "'");
				assetset.reset();
				if (!assetset.isEmpty()) {// --判断对应周转件的存在
					IJpoSet outinventoryset = MroServer.getMroServer()
							.getJpoSet(
									"sys_inventory",
									MroServer.getMroServer()
											.getSystemUserServer());// --调拨出库库存的集合
					outinventoryset.setUserWhere("itemnum='" + itemnum
							+ "' and location='" + ISSUESTOREROOM + "'");
					outinventoryset.reset();
					if (!outinventoryset.isEmpty()) {
						IJpo outinventory = outinventoryset.getJpo(0);
						double CURBAL = outinventory.getDouble("CURBAL");
						double newCURBAL = CURBAL - passqty;
						double locqty = outinventory.getDouble("locqty");
						double newlocqty = locqty - passqty;
						outinventory.setValue("CURBAL", newCURBAL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						outinventory.setValue("locqty", newlocqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						outinventoryset.save();
						assetset.getJpo(0).setValue("location", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.getJpo(0).setValue("status", "返修序列号返回变更",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.save();
					}
				}
			}
			if (!lotnum.isEmpty()) {
				IJpoSet out_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨出库库存的集合
				out_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + ISSUESTOREROOM + "'");
				out_inventoryset.reset();
				if (!out_inventoryset.isEmpty()) {
					IJpo out_inventory = out_inventoryset.getJpo(0);
					double CURBAL = out_inventory.getDouble("CURBAL");
					double newCURBAL = CURBAL - passqty;
					double locqty = out_inventory.getDouble("locqty");
					double newlocqty = locqty - passqty;
					if (!lotnum.equalsIgnoreCase("")) {
						IJpoSet invblanceset = MroServer.getMroServer()
								.getJpoSet(
										"invblance",
										MroServer.getMroServer()
												.getSystemUserServer());// --调拨出库批次集合
						invblanceset.setUserWhere("itemnum='" + itemnum
								+ "' and storeroom='" + ISSUESTOREROOM
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty - passqty;
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
			}
			if (Sqn.isEmpty() && lotnum.isEmpty()) {
				IJpoSet out_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨出库库存的集合
				out_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + ISSUESTOREROOM + "'");
				out_inventoryset.reset();
				if (!out_inventoryset.isEmpty()) {
					IJpo out_inventory = out_inventoryset.getJpo(0);
					double CURBAL = out_inventory.getDouble("CURBAL");
					double newcurbal = CURBAL - passqty;
					double locqty = out_inventory.getDouble("locqty");
					double newlocqty = locqty - passqty;
					out_inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					out_inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					out_inventoryset.save();
				}
			}
		}
		if (failqty > 0) {
			if (!Sqn.isEmpty()) {
				IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
						MroServer.getMroServer().getSystemUserServer());// --对应的周转件集合
				assetset.setUserWhere("assetnum='" + assetnum + "'");
				assetset.reset();
				if (!assetset.isEmpty()) {// --判断对应周转件的存在
					IJpoSet outinventoryset = MroServer.getMroServer()
							.getJpoSet(
									"sys_inventory",
									MroServer.getMroServer()
											.getSystemUserServer());// --调拨出库库存的集合
					outinventoryset.setUserWhere("itemnum='" + itemnum
							+ "' and location='" + ISSUESTOREROOM + "'");
					outinventoryset.reset();
					if (!outinventoryset.isEmpty()) {
						IJpo outinventory = outinventoryset.getJpo(0);
						double CURBAL = outinventory.getDouble("CURBAL");
						double newCURBAL = CURBAL - failqty;
						double locqty = outinventory.getDouble("locqty");
						double newlocqty = locqty - failqty;
						outinventory.setValue("locqty", newlocqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						outinventory.setValue("CURBAL", newCURBAL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						outinventoryset.save();
						assetset.getJpo(0).setValue("location", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.getJpo(0).setValue("status", "返修序列号返回变更",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.save();
					}
				}
			}
			if (!lotnum.isEmpty()) {
				IJpoSet out_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨出库库存的集合
				out_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + ISSUESTOREROOM + "'");
				out_inventoryset.reset();
				if (!out_inventoryset.isEmpty()) {
					IJpo out_inventory = out_inventoryset.getJpo(0);
					double CURBAL = out_inventory.getDouble("CURBAL");
					double newCURBAL = CURBAL - failqty;
					double locqty = out_inventory.getDouble("locqty");
					double newlocqty = locqty - failqty;
					if (!lotnum.equalsIgnoreCase("")) {
						IJpoSet invblanceset = MroServer.getMroServer()
								.getJpoSet(
										"invblance",
										MroServer.getMroServer()
												.getSystemUserServer());// --调拨出库批次集合
						invblanceset.setUserWhere("itemnum='" + itemnum
								+ "' and storeroom='" + ISSUESTOREROOM
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty - failqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
					}
					out_inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					out_inventory.setValue("CURBAL", newCURBAL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					out_inventoryset.save();
				}
			}
			if (Sqn.isEmpty() && lotnum.isEmpty()) {
				IJpoSet out_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨出库库存的集合
				out_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + ISSUESTOREROOM + "'");
				out_inventoryset.reset();
				if (!out_inventoryset.isEmpty()) {
					IJpo out_inventory = out_inventoryset.getJpo(0);
					double CURBAL = out_inventory.getDouble("CURBAL");
					double newcurbal = CURBAL - failqty;
					double locqty = out_inventory.getDouble("locqty");
					double newlocqty = locqty - failqty;
					out_inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					out_inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					out_inventoryset.save();
				}
			}
		}
		if (failqty == 0 && passqty == 0) {
			String ISSUESTOREROOM1 = this.getParent().getString(
					"receivestoreroom");
			if (!Sqn.isEmpty()) {
				IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
						MroServer.getMroServer().getSystemUserServer());// --对应的周转件集合
				assetset.setUserWhere("assetnum='" + assetnum + "'");
				assetset.reset();
				if (!assetset.isEmpty()) {// --判断对应周转件的存在
					IJpoSet outinventoryset = MroServer.getMroServer()
							.getJpoSet(
									"sys_inventory",
									MroServer.getMroServer()
											.getSystemUserServer());// --调拨出库库存的集合
					outinventoryset.setUserWhere("itemnum='" + itemnum
							+ "' and location='" + ISSUESTOREROOM1 + "'");
					outinventoryset.reset();
					if (!outinventoryset.isEmpty()) {
						IJpo outinventory = outinventoryset.getJpo(0);
						double CURBAL = outinventory.getDouble("CURBAL");
						double newCURBAL = CURBAL - JSQTY;
						double locqty = outinventory.getDouble("locqty");
						double newlocqty = locqty - JSQTY;
						outinventory.setValue("CURBAL", newCURBAL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						outinventory.setValue("locqty", newlocqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						outinventoryset.save();
						assetset.getJpo(0).setValue("location", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.getJpo(0).setValue("status", "返修序列号返回变更",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.save();
					}
				}
			}
			if (!lotnum.isEmpty()) {
				IJpoSet out_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨出库库存的集合
				out_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + ISSUESTOREROOM1 + "'");
				out_inventoryset.reset();
				if (!out_inventoryset.isEmpty()) {
					IJpo out_inventory = out_inventoryset.getJpo(0);
					double CURBAL = out_inventory.getDouble("CURBAL");
					double newCURBAL = CURBAL - JSQTY;
					double locqty = out_inventory.getDouble("locqty");
					double newlocqty = locqty - JSQTY;
					if (!lotnum.equalsIgnoreCase("")) {
						IJpoSet invblanceset = MroServer.getMroServer()
								.getJpoSet(
										"invblance",
										MroServer.getMroServer()
												.getSystemUserServer());// --调拨出库批次集合
						invblanceset.setUserWhere("itemnum='" + itemnum
								+ "' and storeroom='" + ISSUESTOREROOM1
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty - JSQTY;
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
			}
			if (Sqn.isEmpty() && lotnum.isEmpty()) {
				IJpoSet out_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨出库库存的集合
				out_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + ISSUESTOREROOM1 + "'");
				out_inventoryset.reset();
				if (!out_inventoryset.isEmpty()) {
					IJpo out_inventory = out_inventoryset.getJpo(0);
					double CURBAL = out_inventory.getDouble("CURBAL");
					double newcurbal = CURBAL - JSQTY;
					double locqty = out_inventory.getDouble("locqty");
					double newlocqty = locqty - JSQTY;
					out_inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					out_inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					out_inventoryset.save();
				}
			}
		}
	}

	/**
	 * 
	 * <出库记录方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void OUTINVTRANS() throws MroException {
		try {
			IJpo parent = this.getJpo().getParent();
			double passqty = this.getJpo().getDouble("passqty");/* 合格数量 */
			double failqty = this.getJpo().getDouble("failqty");/* 报废数量 */
			double JSQTY = this.getJpo().getDouble("JSQTY");/* 接收数量 */
			String RECEIVESTOREROOM = parent.getString("ISSUESTOREROOM");/* 缴库单接收库房 */
			String ISSUESTOREROOM = parent.getString("RECEIVESTOREROOM");/* 缴库单发出库房 */
			if (passqty > 0) {
				IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
						"INVTRANS",
						MroServer.getMroServer().getSystemUserServer());
				String sqn = parent.getString("sqn");// --产品序列号
				String lotnum = parent.getString("lotnum");// --批次号
				String itemnum = parent.getString("itemnum");// --物料编码
				String assetnum = parent.getString("assetnum");// --资产编号
				String transfernum = parent.getString("transfernum");// --调拨单编号
				Date outdate = MroServer.getMroServer().getDate();// --出库时间
				String siteid = parent.getString("siteid");// --地点
				String orgid = parent.getString("orgid");// --组织

				IJpo INVTRANS_OUT = INVTRANSset.addJpo(); // 出库记录
				INVTRANS_OUT.setValue("TRANSTYPE", "调拨出库",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
				INVTRANS_OUT.setValue("SQN", sqn,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
				INVTRANS_OUT.setValue("LOTNUM", lotnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号
				INVTRANS_OUT.setValue("QTY", passqty,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
				INVTRANS_OUT.setValue("STOREROOM", ISSUESTOREROOM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --库房
				INVTRANS_OUT.setValue("TRANSDATE", outdate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
				INVTRANS_OUT.setValue("TRANSFERNUM", transfernum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --调拨单编号
				INVTRANS_OUT.setValue("ITEMNUM", itemnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
				INVTRANS_OUT.setValue("assetnum", assetnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
				INVTRANS_OUT.setValue("siteid", siteid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
				INVTRANS_OUT.setValue("orgid", orgid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
				INVTRANSset.save();
			}
			if (failqty > 0) {
				IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
						"INVTRANS",
						MroServer.getMroServer().getSystemUserServer());
				String sqn = parent.getString("sqn");// --产品序列号
				String lotnum = parent.getString("lotnum");// --批次号
				String itemnum = parent.getString("itemnum");// --物料编码
				String assetnum = parent.getString("assetnum");// --资产编号
				String transfernum = parent.getString("transfernum");// --调拨单编号
				Date outdate = MroServer.getMroServer().getDate();// --出库时间
				String siteid = parent.getString("siteid");// --地点
				String orgid = parent.getString("orgid");// --组织

				IJpo INVTRANS_OUT = INVTRANSset.addJpo(); // 出库记录
				INVTRANS_OUT.setValue("TRANSTYPE", "调拨出库",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
				INVTRANS_OUT.setValue("SQN", sqn,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
				INVTRANS_OUT.setValue("LOTNUM", lotnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号
				INVTRANS_OUT.setValue("QTY", failqty,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
				INVTRANS_OUT.setValue("STOREROOM", ISSUESTOREROOM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --库房
				INVTRANS_OUT.setValue("TRANSDATE", outdate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
				INVTRANS_OUT.setValue("TRANSFERNUM", transfernum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --调拨单编号
				INVTRANS_OUT.setValue("ITEMNUM", itemnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
				INVTRANS_OUT.setValue("assetnum", assetnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
				INVTRANS_OUT.setValue("siteid", siteid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
				INVTRANS_OUT.setValue("orgid", orgid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
				INVTRANSset.save();
			}
			if (failqty == 0 && passqty == 0) {
				IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
						"INVTRANS",
						MroServer.getMroServer().getSystemUserServer());
				String sqn = parent.getString("sqn");// --产品序列号
				String lotnum = parent.getString("lotnum");// --批次号
				String itemnum = parent.getString("itemnum");// --物料编码
				String assetnum = parent.getString("assetnum");// --资产编号
				String transfernum = parent.getString("transfernum");// --调拨单编号
				Date outdate = MroServer.getMroServer().getDate();// --出库时间
				String siteid = parent.getString("siteid");// --地点
				String orgid = parent.getString("orgid");// --组织

				IJpo INVTRANS_OUT = INVTRANSset.addJpo(); // 出库记录
				INVTRANS_OUT.setValue("TRANSTYPE", "调拨出库",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
				INVTRANS_OUT.setValue("SQN", sqn,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
				INVTRANS_OUT.setValue("LOTNUM", lotnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号
				INVTRANS_OUT.setValue("QTY", JSQTY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
				INVTRANS_OUT.setValue("STOREROOM",
						this.getParent().getString("receivestoreroom"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --库房
				INVTRANS_OUT.setValue("TRANSDATE", outdate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
				INVTRANS_OUT.setValue("TRANSFERNUM", transfernum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --调拨单编号
				INVTRANS_OUT.setValue("ITEMNUM", itemnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
				INVTRANS_OUT.setValue("assetnum", assetnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
				INVTRANS_OUT.setValue("siteid", siteid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
				INVTRANS_OUT.setValue("orgid", orgid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
				INVTRANSset.save();
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
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void INSTOREROOM() throws MroException {
		IJpo parent = this.getJpo().getParent();
		double passqty = this.getJpo().getDouble("passqty");/* 合格数量 */
		double failqty = this.getJpo().getDouble("failqty");/* 报废数量 */
		double JSQTY = this.getJpo().getDouble("JSQTY");/* 接收数量 */
		String itemnum = parent.getString("itemnum");
		String Sqn = parent.getString("sqn");
		String lotnum = parent.getString("lotnum");
		String assetnum = parent.getString("assetnum");
		String siteid = parent.getString("siteid");// --地点
		String orgid = parent.getString("orgid");// --组织
		java.util.Date INSTOREDATE = MroServer.getMroServer().getDate();
		String RECEIVESTOREROOM = parent.getString("ISSUESTOREROOM");/* 缴库单接收库房 */
		String ISSUESTOREROOM = parent.getString("RECEIVESTOREROOM");/* 缴库单发出库房 */

		if (passqty > 0) {
			if (!Sqn.isEmpty()) {
					IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
							"sys_inventory",
							MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
					inventoryset.setUserWhere("itemnum='" + itemnum
							+ "' and location='" + RECEIVESTOREROOM + "'");
					inventoryset.reset();

					if (!inventoryset.isEmpty()) {// --判断入库库存集合不为空
						IJpo inventory = inventoryset.getJpo(0);
						double CURBAL = inventory.getDouble("CURBAL");
						double newCURBAL = CURBAL + passqty;
						double locqty = inventory.getDouble("locqty");
						double newlocqty = locqty + passqty;

						inventory.setValue("CURBAL", newCURBAL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("locqty", newlocqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventoryset.save();
					}

					if (inventoryset.isEmpty()) {// --判断入库库存集合为空
						IJpo inventory = inventoryset.addJpo();

						inventory.setValue("CURBAL", passqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("locqty", passqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("itemnum", itemnum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("location", RECEIVESTOREROOM,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("siteid", siteid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("orgid", orgid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventoryset.save();

					}
			}
			if (!lotnum.isEmpty()) {
				IJpoSet in_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
				in_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + RECEIVESTOREROOM + "'");
				in_inventoryset.reset();
				if (!in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.getJpo(0);
					double CURBAL = in_inventory.getDouble("CURBAL");
					double newCURBAL = CURBAL + passqty;
					double locqty = in_inventory.getDouble("locqty");
					double newlocqty = locqty + passqty;
					if (!lotnum.equalsIgnoreCase("")) {
						IJpoSet invblanceset = MroServer.getMroServer()
								.getJpoSet(
										"invblance",
										MroServer.getMroServer()
												.getSystemUserServer());// --调拨入库批次集合
						invblanceset.setUserWhere("itemnum='" + itemnum
								+ "' and storeroom='" + RECEIVESTOREROOM
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty + passqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", passqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", RECEIVESTOREROOM,
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
					in_inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
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
						invblanceset.setUserWhere("itemnum='" + itemnum
								+ "' and storeroom='" + RECEIVESTOREROOM
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty + passqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", passqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", RECEIVESTOREROOM,
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
					in_inventory.setValue("CURBAL", passqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("locqty", passqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("location", RECEIVESTOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventoryset.save();
				}
			}
			if (Sqn.isEmpty() && lotnum.isEmpty()) {
				IJpoSet in_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
				in_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + RECEIVESTOREROOM + "'");
				in_inventoryset.reset();

				if (!in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.getJpo(0);
					double CURBAL = in_inventory.getDouble("CURBAL");
					double newcurbal = CURBAL + passqty;
					double locqty = in_inventory.getDouble("locqty");
					double newlocqty = locqty + passqty;
					in_inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventoryset.save();
				}
				if (in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.addJpo();
					in_inventory.setValue("CURBAL", passqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("locqty", passqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("location", RECEIVESTOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventoryset.save();
				}
			}
		}
		if (failqty > 0) {
			if (!Sqn.isEmpty()) {
					IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
							"sys_inventory",
							MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
					inventoryset.setUserWhere("itemnum='" + itemnum
							+ "' and location='" + RECEIVESTOREROOM + "'");
					inventoryset.reset();

					if (!inventoryset.isEmpty()) {// --判断入库库存集合不为空
						IJpo inventory = inventoryset.getJpo(0);
						double CURBAL = inventory.getDouble("CURBAL");
						double DISPOSEQTY = inventory.getDouble("DISPOSEQTY");
						double newCURBAL = CURBAL + failqty;
						double newDISPOSEQTY = DISPOSEQTY + failqty;
						double locqty = inventory.getDouble("locqty");
						double newlocqty = locqty + failqty;

						inventory.setValue("CURBAL", newCURBAL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("locqty", newlocqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//						inventory.setValue("DISPOSEQTY", newDISPOSEQTY,
//								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventoryset.save();
					}

					if (inventoryset.isEmpty()) {// --判断入库库存集合为空
						IJpo inventory = inventoryset.addJpo();

						inventory.setValue("CURBAL", passqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("locqty", passqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//						inventory.setValue("DISPOSEQTY", passqty,
//								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("itemnum", itemnum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("location", RECEIVESTOREROOM,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("siteid", siteid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("orgid", orgid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
			}
			if (!lotnum.isEmpty()) {
				IJpoSet in_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
				in_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + RECEIVESTOREROOM + "'");
				in_inventoryset.reset();
				if (!in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.getJpo(0);
					double CURBAL = in_inventory.getDouble("CURBAL");
					double newCURBAL = CURBAL + failqty;
					double DISPOSEQTY = in_inventory.getDouble("DISPOSEQTY");
					double newDISPOSEQTY = DISPOSEQTY + failqty;
					double locqty = in_inventory.getDouble("locqty");
					double newlocqty = locqty + failqty;
					if (!lotnum.equalsIgnoreCase("")) {
						IJpoSet invblanceset = MroServer.getMroServer()
								.getJpoSet(
										"invblance",
										MroServer.getMroServer()
												.getSystemUserServer());// --调拨入库批次集合
						invblanceset.setUserWhere("itemnum='" + itemnum
								+ "' and storeroom='" + RECEIVESTOREROOM
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty + failqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", failqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", RECEIVESTOREROOM,
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
					in_inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//					in_inventory.setValue("DISPOSEQTY", newDISPOSEQTY,
//							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
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
						invblanceset.setUserWhere("itemnum='" + itemnum
								+ "' and storeroom='" + RECEIVESTOREROOM
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty + failqty;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", failqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", RECEIVESTOREROOM,
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
					in_inventory.setValue("CURBAL", failqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("locqty", failqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//					in_inventory.setValue("DISPOSEQTY", failqty,
//							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("location", RECEIVESTOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventoryset.save();
				}
			}
			if (Sqn.isEmpty() && lotnum.isEmpty()) {
				IJpoSet in_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
				in_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + RECEIVESTOREROOM + "'");
				in_inventoryset.reset();

				if (!in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.getJpo(0);
					double CURBAL = in_inventory.getDouble("CURBAL");
					double newcurbal = CURBAL + failqty;
					double DISPOSEQTY = in_inventory.getDouble("DISPOSEQTY");
					double newDISPOSEQTY = DISPOSEQTY + failqty;
					double locqty = in_inventory.getDouble("locqty");
					double newlocqty = locqty + failqty;
					in_inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//					in_inventory.setValue("DISPOSEQTY", newDISPOSEQTY,
//							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventoryset.save();
				}
				if (in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.addJpo();
					in_inventory.setValue("CURBAL", failqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("locqty", failqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//					in_inventory.setValue("DISPOSEQTY", failqty,
//							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("location", RECEIVESTOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventoryset.save();
				}
			}
		}
		if (failqty == 0 && passqty == 0) {
			String RECEIVESTOREROOM1 = this.getParent().getString(
					"ISSUESTOREROOM");
			if (!Sqn.isEmpty()) {
					IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
							"sys_inventory",
							MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
					inventoryset.setUserWhere("itemnum='" + itemnum
							+ "' and location='" + RECEIVESTOREROOM1 + "'");
					inventoryset.reset();

					if (!inventoryset.isEmpty()) {// --判断入库库存集合不为空
						IJpo inventory = inventoryset.getJpo(0);
						double CURBAL = inventory.getDouble("CURBAL");
						double newCURBAL = CURBAL + JSQTY;
						double locqty = inventory.getDouble("locqty");
						double newlocqty = locqty + JSQTY;

						inventory.setValue("CURBAL", newCURBAL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("locqty", newlocqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventoryset.save();
					}

					if (inventoryset.isEmpty()) {// --判断入库库存集合为空
						IJpo inventory = inventoryset.addJpo();

						inventory.setValue("CURBAL", JSQTY,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("locqty", JSQTY,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("itemnum", itemnum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("location", RECEIVESTOREROOM1,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("siteid", siteid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("orgid", orgid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
			}
			if (!lotnum.isEmpty()) {
				IJpoSet in_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
				in_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + RECEIVESTOREROOM1 + "'");
				in_inventoryset.reset();
				if (!in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.getJpo(0);
					double CURBAL = in_inventory.getDouble("CURBAL");
					double newCURBAL = CURBAL + JSQTY;
					double locqty = in_inventory.getDouble("locqty");
					double newlocqty = locqty + JSQTY;
					if (!lotnum.equalsIgnoreCase("")) {
						IJpoSet invblanceset = MroServer.getMroServer()
								.getJpoSet(
										"invblance",
										MroServer.getMroServer()
												.getSystemUserServer());// --调拨入库批次集合
						invblanceset.setUserWhere("itemnum='" + itemnum
								+ "' and storeroom='" + RECEIVESTOREROOM1
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty + JSQTY;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", JSQTY,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", RECEIVESTOREROOM1,
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
					in_inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
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
						invblanceset.setUserWhere("itemnum='" + itemnum
								+ "' and storeroom='" + RECEIVESTOREROOM1
								+ "' and lotnum='" + lotnum + "'");
						invblanceset.reset();
						if (!invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.getJpo(0);
							double physcntqty = invblance
									.getDouble("physcntqty");
							double newphyscntqty = physcntqty + JSQTY;
							invblance.setValue("physcntqty", newphyscntqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblanceset.save();
						}
						if (invblanceset.isEmpty()) {
							IJpo invblance = invblanceset.addJpo();
							invblance.setValue("lotnum", lotnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("physcntqty", JSQTY,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("itemnum", itemnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							invblance.setValue("storeroom", RECEIVESTOREROOM1,
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
					in_inventory.setValue("CURBAL", JSQTY,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("locqty", JSQTY,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("location", RECEIVESTOREROOM1,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventoryset.save();
				}
			}
			if (Sqn.isEmpty() && lotnum.isEmpty()) {
				IJpoSet in_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
				in_inventoryset.setUserWhere("itemnum='" + itemnum
						+ "' and location='" + RECEIVESTOREROOM1 + "'");
				in_inventoryset.reset();

				if (!in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.getJpo(0);
					double CURBAL = in_inventory.getDouble("CURBAL");
					double newcurbal = CURBAL + JSQTY;
					double locqty = in_inventory.getDouble("locqty");
					double newlocqty = locqty + JSQTY;
					in_inventory.setValue("CURBAL", newcurbal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("locqty", newlocqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventoryset.save();
				}
				if (in_inventoryset.isEmpty()) {
					IJpo in_inventory = in_inventoryset.addJpo();
					in_inventory.setValue("CURBAL", JSQTY,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("locqty", JSQTY,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("location", RECEIVESTOREROOM1,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventory.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					in_inventoryset.save();
				}
			}
		}
	}

	/**
	 * 
	 * <入库记录方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void ININVTRANS() throws MroException {
		try {
			IJpo parent = this.getJpo().getParent();
			
			double passqty = this.getJpo().getDouble("passqty");/* 合格数量 */
			double failqty = this.getJpo().getDouble("failqty");/* 报废数量 */
			double JSQTY = this.getJpo().getDouble("JSQTY");/* 报废数量 */
			String RECEIVESTOREROOM = parent.getString("ISSUESTOREROOM");/* 缴库单接收库房 */
			String ISSUESTOREROOM = parent.getString("RECEIVESTOREROOM");/* 缴库单发出库房 */

			if (passqty > 0) {
				IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
						"INVTRANS",
						MroServer.getMroServer().getSystemUserServer());
				String sqn = parent.getString("newsqn");// --产品序列号
				String lotnum = parent.getString("lotnum");// --批次号
				String itemnum = parent.getString("itemnum");// --物料编码
				String assetnum = parent.getString("newassetnum");// --资产编号
				String transfernum = parent.getString("transfernum");// --调拨单编号
				Date indate = MroServer.getMroServer().getDate();// --入库时间
				String siteid = parent.getString("siteid");// --地点
				String orgid = parent.getString("orgid");// --组织

				IJpo INVTRANS_IN = INVTRANSset.addJpo(); // 入库记录
				INVTRANS_IN.setValue("TRANSTYPE", "调拨入库",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
				INVTRANS_IN.setValue("SQN", sqn,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
				INVTRANS_IN.setValue("LOTNUM", lotnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号
				INVTRANS_IN.setValue("QTY", passqty,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
				INVTRANS_IN.setValue("STOREROOM", RECEIVESTOREROOM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
				INVTRANS_IN.setValue("TRANSDATE", indate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
				INVTRANS_IN.setValue("TRANSFERNUM", transfernum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --调拨单编号
				INVTRANS_IN.setValue("ITEMNUM", itemnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
				INVTRANS_IN.setValue("assetnum", assetnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
				INVTRANS_IN.setValue("siteid", siteid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
				INVTRANS_IN.setValue("orgid", orgid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
				INVTRANSset.save();
			}
			if (failqty > 0) {
				IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
						"INVTRANS",
						MroServer.getMroServer().getSystemUserServer());
				String sqn = parent.getString("newsqn");// --产品序列号
				String lotnum = parent.getString("lotnum");// --批次号
				String itemnum = parent.getString("itemnum");// --物料编码
				String assetnum = parent.getString("newassetnum");// --资产编号
				String transfernum = parent.getString("transfernum");// --调拨单编号
				Date indate = MroServer.getMroServer().getDate();// --入库时间
				String siteid = parent.getString("siteid");// --地点
				String orgid = parent.getString("orgid");// --组织

				IJpo INVTRANS_IN = INVTRANSset.addJpo(); // 入库记录
				INVTRANS_IN.setValue("TRANSTYPE", "调拨入库",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
				INVTRANS_IN.setValue("SQN", sqn,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
				INVTRANS_IN.setValue("LOTNUM", lotnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号
				INVTRANS_IN.setValue("QTY", failqty,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
				INVTRANS_IN.setValue("STOREROOM", RECEIVESTOREROOM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
				INVTRANS_IN.setValue("TRANSDATE", indate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
				INVTRANS_IN.setValue("TRANSFERNUM", transfernum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --调拨单编号
				INVTRANS_IN.setValue("ITEMNUM", itemnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
				INVTRANS_IN.setValue("assetnum", assetnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
				INVTRANS_IN.setValue("siteid", siteid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
				INVTRANS_IN.setValue("orgid", orgid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
				INVTRANSset.save();
			}
			if (failqty == 0 && passqty == 0) {
				IJpoSet INVTRANSset = MroServer.getMroServer().getJpoSet(
						"INVTRANS",
						MroServer.getMroServer().getSystemUserServer());
				String sqn = parent.getString("newsqn");// --产品序列号
				String lotnum = parent.getString("lotnum");// --批次号
				String itemnum = parent.getString("itemnum");// --物料编码
				String assetnum = parent.getString("newassetnum");// --资产编号
				String transfernum = parent.getString("transfernum");// --调拨单编号
				Date indate = MroServer.getMroServer().getDate();// --入库时间
				String siteid = parent.getString("siteid");// --地点
				String orgid = parent.getString("orgid");// --组织

				IJpo INVTRANS_IN = INVTRANSset.addJpo(); // 入库记录
				INVTRANS_IN.setValue("TRANSTYPE", "调拨入库",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易类型
				INVTRANS_IN.setValue("SQN", sqn,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --产品序列号
				INVTRANS_IN.setValue("LOTNUM", lotnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --批次号
				INVTRANS_IN.setValue("QTY", JSQTY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --数量
				INVTRANS_IN.setValue("STOREROOM",
						this.getParent().getString("ISSUESTOREROOM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT); // --库房
				INVTRANS_IN.setValue("TRANSDATE", indate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --交易时间
				INVTRANS_IN.setValue("TRANSFERNUM", transfernum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --调拨单编号
				INVTRANS_IN.setValue("ITEMNUM", itemnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --物料编码
				INVTRANS_IN.setValue("assetnum", assetnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --资产编号
				INVTRANS_IN.setValue("siteid", siteid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --地点
				INVTRANS_IN.setValue("orgid", orgid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// --组织
				INVTRANSset.save();
			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <通知返修发运人方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void CALLFXFY() throws MroException {
		IJpo parent = this.getJpo().getParent();
		String siteid = parent.getString("siteid");
		String orgid = parent.getString("orgid");
		IJpoSet MSGMANAGEset = MroServer.getMroServer().getJpoSet("MSGMANAGE",
				MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		Date createdate = MroServer.getMroServer().getDate();
		String transferid = parent.getParent().getString("transferid");
		String transferlineid = parent.getString("transferlineid");
		IJpoSet persongroupset = MroServer.getMroServer().getJpoSet(
				"SYS_PERSONGROUPTEAM",
				MroServer.getMroServer().getSystemUserServer());
		persongroupset.setUserWhere("persongroup='FXHFY'");
		persongroupset.reset();
		String personid = persongroupset.getJpo(0).getString("RESPPARTYGROUP");
		String transfernum = parent.getString("transfernum");
		String itemnum = parent.getString("itemnum");
		String sqn = parent.getString("newsqn");
		String zxdlineid = parent.getString("zxdlineid");
		IJpoSet zxdset = MroServer.getMroServer().getJpoSet(
				"transferlinetrade",
				MroServer.getMroServer().getSystemUserServer());
		zxdset.setUserWhere("transferlinetradeid='" + zxdlineid + "'");
		zxdset.reset();
		String zxdissuesttoreroom = zxdset.getJpo(0)
				.getString("issuestoreroom");
		IJpoSet location = MroServer.getMroServer().getJpoSet("locations",
				MroServer.getMroServer().getSystemUserServer());
		location.setUserWhere("location='" + zxdissuesttoreroom + "'");
		location.reset();
		String receivestoreroom = location.getJpo(0).getJpoSet("CGLOCATION")
				.getJpo().getString("location");
		String receiveaddress = location.getJpo(0).getJpoSet("CGLOCATION")
				.getJpo().getJpoSet("ISADDRESS").getJpo().getString("ADDRESS");
		String description = location.getJpo(0).getJpoSet("CGLOCATION")
				.getJpo().getString("description");
		IJpo MSGMANAGE = MSGMANAGEset.addJpo();
		MSGMANAGE.setValue("app", "JKTRANSFER",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("appid", transferid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		if (sqn.isEmpty()) {
			MSGMANAGE.setValue("content", "缴库单编号为:'" + transfernum
					+ "' ,物料编码为:'" + itemnum + "',已经接收，且合格。需要返修发运到现场，接收库房为:'"
					+ description + "',库房编号为:'" + receivestoreroom
					+ "',接收地址为:'" + receiveaddress + "'。请创建装箱单进行发运！",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

		} else {
			MSGMANAGE.setValue("content", "缴库单编号为:'" + transfernum
					+ "' ,物料编码为:'" + itemnum + "',序列号为:'" + sqn
					+ "'已经接收，且合格。需要返修发运到现场，接收库房为:'" + description + "',库房编号为:'"
					+ receivestoreroom + "',接收地址为:'" + receiveaddress
					+ "'。请创建装箱单进行发运！", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
		MSGMANAGE.setValue("createdate", createdate,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("msgnum", transferlineid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("receiver", personid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("orgid", orgid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("siteid", siteid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("status", "新增",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("subject", "返修返货通知",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

		MSGMANAGEset.save();
	}

	/**
	 * 
	 * <通知返修发运人关联配件申请发货方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void PJSQCALLFXFY() throws MroException {
		IJpo parent = this.getJpo().getParent();
		String siteid = parent.getString("siteid");
		String orgid = parent.getString("orgid");
		IJpoSet MSGMANAGEset = MroServer.getMroServer().getJpoSet("MSGMANAGE",
				MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		Date createdate = MroServer.getMroServer().getDate();
		String transferid = parent.getParent().getString("transferid");
		String transferlineid = parent.getString("transferlineid");
		IJpoSet persongroupset = MroServer.getMroServer().getJpoSet(
				"SYS_PERSONGROUPTEAM",
				MroServer.getMroServer().getSystemUserServer());
		persongroupset.setUserWhere("persongroup='FXHFY'");
		persongroupset.reset();
		String personid = persongroupset.getJpo(0).getString("RESPPARTYGROUP");
		String transfernum = parent.getString("transfernum");
		String itemnum = parent.getString("itemnum");
		String sqn = parent.getString("newsqn");
		IJpoSet mrlinetransferset = MroServer.getMroServer().getJpoSet(
				"mrlinetransfer",
				MroServer.getMroServer().getSystemUserServer());
		mrlinetransferset.setUserWhere("itemnum='" + itemnum
				+ "' and transtype='返修后发运' and transferqty>zxqty");
		mrlinetransferset.setOrderBy("mrlinetransferid asc");
		mrlinetransferset.reset();
		if (!mrlinetransferset.isEmpty()) {
			String mrnum = mrlinetransferset.getJpo(0).getString("mrnum");
			IJpo MSGMANAGE = MSGMANAGEset.addJpo();
			MSGMANAGE.setValue("app", "JKTRANSFER",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			MSGMANAGE.setValue("appid", transferid,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			if (sqn.isEmpty()) {
				MSGMANAGE.setValue("content", "配件申请编号为:'" + mrnum
						+ "',要求返修后发运的物料编码为:'" + itemnum
						+ "'，已经接收，且合格。请创建装箱单进行发运！",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

			} else {
				MSGMANAGE.setValue("content", "配件申请编号为:'" + mrnum
						+ "',要求返修后发运的物料编码为:'" + itemnum
						+ "'，序列号为:'"+sqn+"',已经接收，且合格。请创建装箱单进行发运！",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			MSGMANAGE.setValue("createdate", createdate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			MSGMANAGE.setValue("msgnum", transferlineid,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			MSGMANAGE.setValue("receiver", personid,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			MSGMANAGE.setValue("orgid", orgid,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			MSGMANAGE.setValue("siteid", siteid,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			MSGMANAGE.setValue("status", "新增",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			MSGMANAGE.setValue("subject", "配件申请返修后发货通知",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

			MSGMANAGEset.save();
		}

	}
	/**
	 * 
	 * <序列号新增方法>
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void ADDASSET() throws MroException {
//		 IJpoSet jpoSet = this.getJpo().getJpoSet("$asset", "ASSET", "1=2");
		IJpoSet jpoSet = MroServer.getMroServer().getJpoSet("asset",
				MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
		.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
		.setDefaultSite("ELEC");
		String PROJNUM = this.getParent().getJpo().getString("PROJNUM");
		java.util.Date INSTOREDATE = MroServer.getMroServer().getDate();
		String newSQN =  this.getParent().getJpo().getString("NEWSQN").toUpperCase();
		String ITEMNUM = this.getParent().getJpo().getString("ITEMNUM");
		double passqty = this.getJpo().getDouble("passqty");
		double failqty = this.getJpo().getDouble("failqty");
		String RECEIVESTOREROOM = this.getParent().getJpo().getString("ISSUESTOREROOM");
		IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",
				MroServer.getMroServer().getSystemUserServer());
		itemset.setUserWhere("itemnum='" + ITEMNUM + "'");
		String type = ItemUtil.getItemInfo(ITEMNUM);
		String commomtype="入库";
		if (ItemUtil.SQN_ITEM.equals(type)) {
			IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
					MroServer.getMroServer().getSystemUserServer());
			assetset.setUserWhere("itemnum='"
					+ ITEMNUM
					+ "' and sqn='"
					+ newSQN
					+ "' and assetlevel='ASSET' and type!='2' and location is null");
			if (assetset.isEmpty()) {
				IJpo jpo = jpoSet.addJpo();
				jpo.setValue("assetlevel", "ASSET",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("itemnum", ITEMNUM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("sqn", newSQN,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("LOCATION", RECEIVESTOREROOM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("type", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("ancestor", jpo.getString("assetnum"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if(passqty>0){
					jpo.setValue("status", "可用",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(failqty>0){
					jpo.setValue("status", "待处理",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				jpo.setValue("INSTOREDATE", INSTOREDATE,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("PROJNUM", PROJNUM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("isnew", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("iserp", "0",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("MSGFLAG", "返修序列号变更",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("FROMSOURCE", SddqConstant.FROMSOURCE_XP,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//				CommomCarItemLife.INOROURLOCATION(jpo, commomtype);//调用一物一档入库计算方法
				this.getParent().setValue("newassetnum", jpo.getString("assetnum"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpoSet.save();
			} else {
				IJpo asset = assetset.getJpo(0);
				asset.setValue("LOCATION", RECEIVESTOREROOM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("type", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if(passqty>0){
					asset.setValue("status", "可用",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(failqty>0){
					asset.setValue("status", "待处理",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				asset.setValue("INSTOREDATE", INSTOREDATE,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//				CommomCarItemLife.INOROURLOCATION(asset, commomtype);//调用一物一档入库计算方法
				this.getParent().setValue("newassetnum", asset.getString("assetnum"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				assetset.save();
			}
		}
	}
}
