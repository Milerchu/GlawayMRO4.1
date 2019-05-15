package com.glaway.sddq.material.transferline.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.material.invtrans.common.TransLineInvtranscommon;
import com.glaway.sddq.material.invtrans.common.TransLineStoroomCommon;
import com.glaway.sddq.material.invtrans.common.TransferlinetradeCommon;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * <改造装箱单接收窗口DataBean>
 * 
 * @author 20167088
 * @version [版本号, 2018-8-3]
 * @since [产品/模块版本]
 */
public class GzTransferlineStatusDataBean extends DataBean {
	/**
	 * 确认接收方法
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int execute() throws MroException, IOException {
		if (this.getJpo() != null) {
			IJpo parent = this.getJpo().getParent();
			double statuswjsqty = this.getJpo().getDouble("wjsqty");
			double statusjsqty = this.getJpo().getDouble("jsqty");
			double YJSQTY = parent.getDouble("YJSQTY");
			double newyjsqty = YJSQTY + statusjsqty;
			String inbinnum = this.getJpo().getString("inbinnum");
			String itemnum = parent.getString("itemnum");
			String assetnum = parent.getString("assetnum");
			String sqn = parent.getString("sqn");
			String LOCATION = parent.getString("receivestroeroom");/* 接收库房 */

			String transfernum = parent.getString("transfernum");
			String transferlineid = parent.getString("transferlineid");
			if (statusjsqty > statuswjsqty) {
				throw new MroException("transferline", "qty");
			} else {
				parent.setValue("inbinnum", inbinnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				// 调用公共出库方法
				TransLineStoroomCommon.out_storoom(parent, statusjsqty);
				// 调用公共出库记录方法
				TransLineInvtranscommon.out_invtrans(parent, statusjsqty);
				// 调用公共入库方法
				TransLineStoroomCommon.in_storoom(parent, statusjsqty);
				// 调用公共入库记录方法
				TransLineInvtranscommon.in_invtrans(parent, statusjsqty);
				// 调用增加序列号方法
				ADDASSET(itemnum, sqn, LOCATION, assetnum);
				parent.setValue("YJSQTY", newyjsqty,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if (statuswjsqty == statusjsqty) {
					parent.setValue("status", "已接收",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					IJpoSet transferlineset = MroServer.getMroServer()
							.getJpoSet(
									"transferline",
									MroServer.getMroServer()
											.getSystemUserServer());
					transferlineset.setQueryWhere("transfernum='" + transfernum
							+ "' and status!='已接收' and transferlineid!='"
							+ transferlineid + "'");
					if (transferlineset.isEmpty()) {
						parent.getParent().setValue("status", "已接收",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				} else {
					parent.setValue("status", "部分接收",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
			// 调用增加装箱单接收记录方法
			TransferlinetradeCommon.add_trade(parent, statusjsqty);
		}
		this.getAppBean().SAVE();
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	/**
	 * 
	 * <新增序列号方法>
	 * 
	 * @param itemnum
	 * @param sqn
	 * @param LOCATION
	 * @param assetnum
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void ADDASSET(String itemnum, String sqn, String LOCATION,
			String assetnum) throws MroException {

		IJpoSet jpoSet = this.getJpo().getJpoSet("$asset", "ASSET", "1=2");
		java.util.Date INSTOREDATE = MroServer.getMroServer().getDate();
		IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",
				MroServer.getMroServer().getSystemUserServer());
		itemset.setQueryWhere("itemnum='" + itemnum + "'");
		String type = ItemUtil.getItemInfo(itemnum);
		if (ItemUtil.SQN_ITEM.equals(type)) {
			IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
					MroServer.getMroServer().getSystemUserServer());
			assetset.setQueryWhere("itemnum='" + itemnum + "' and sqn='" + sqn
					+ "' and assetnum='" + assetnum + "'");
			if (assetset.isEmpty()) {
				IJpo asset = jpoSet.addJpo();
				asset.setValue("itemnum", itemnum);
				asset.setValue("sqn", sqn);
				asset.setValue("location", LOCATION);
				asset.setValue("INSTOREDATE", MroServer.getMroServer()
						.getDate());
				asset.setValue("secretleveldes", "非密");
				asset.setValue("status", "可用",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("isnew", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("iserp", "0",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("type", "1");
				asset.setValue("ANCESTOR", asset.getString("assetnum"));
				asset.setValue("INSTOREDATE", INSTOREDATE,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("MSGFLAG", SddqConstant.MSG_INFO_NOSQN,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("FROMSOURCE", SddqConstant.FROMSOURCE_XP,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

			} else {
				IJpo asset = assetset.getJpo(0);
				asset.setValue("LOCATION", LOCATION,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("type", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("status", "可用",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				assetset.save();
			}
		}
	}

	/**
	 * 
	 * <功能描述>
	 * 
	 * @param transfernum
	 *            装箱单号
	 * @param itemnum
	 *            物料编码
	 * @param cloc
	 *            出库库房
	 * @param rloc
	 *            接收库房
	 * @param qty
	 *            数量
	 * @param jpo
	 *            Jpo对象
	 * @param xlh
	 *            是否序列号
	 * @param sqn
	 *            asstenum
	 * @param pc
	 *            是否批次
	 * @param pch
	 *            批次号
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void recive(String transfernum, String itemnum, String cloc,
			String rloc, double qty, IJpo jpo, boolean xlh, String sqn,
			boolean pc, String pch) throws MroException {

		try {// IJpo item = itemSet.getJpo(0);
			String PROJNUM = jpo.getParent().getJpoSet("PROJECT").getJpo()
					.getString("WORKORDERNUM");
			java.util.Date INSTOREDATE = MroServer.getMroServer().getDate();
			IJpoSet inventorySet = jpo.getJpoSet("$SYS_INVENTORY",
					"SYS_INVENTORY", "itemnum='" + itemnum
							+ "' and LOCATION = '" + cloc + "'");// 出库的
			if (inventorySet.isEmpty()) {
				IJpo inventor = inventorySet.addJpo();
				inventor.setValue("itemnum", itemnum, 2L);
				inventor.setValue("LOCATION", cloc, 2L);
				inventor.setValue("CURBAL", qty, 2L);// 余量
			} else {
				IJpo inventor = inventorySet.getJpo(0);
				inventor.setValue("CURBAL", inventor.getDouble("CURBAL") - qty,
						2L);// 出库库存表
			}
			IJpoSet InvtransSet = jpo.getJpoSet("$invtrans", "INVTRANS", "1=2");// 出入库交易表
			IJpo Invtrans = InvtransSet.addJpo();// 出库记录
			Invtrans.setValue("itemnum", itemnum, 2L);
			Invtrans.setValue("TRANSTYPE", "出库", 2L);
			Invtrans.setValue("TRANSFERNUM", transfernum, 2L);// 装箱单号
			Invtrans.setValue("QTY", -qty, 2L);
			Invtrans.setValue("SQN", sqn, 2L);
			Invtrans.setValue("LOTNUM", pch, 2L);
			Invtrans.setValue("STOREROOM", cloc, 2L);
			Invtrans.setValue("TRANSDATE", MroServer.getMroServer().getDate(),
					2L);
			/**
			 * 入库的
			 */
			IJpoSet inventorysSet = jpo.getJpoSet("$SYS_INVENTORY0",
					"SYS_INVENTORY", "itemnum='" + itemnum
							+ "' and LOCATION = '" + rloc + // 入库
							"'");//
			if (inventorysSet.isEmpty()) {
				IJpo inventors = inventorysSet.addJpo();
				inventors.setValue("itemnum", itemnum, 2L);
				inventors.setValue("LOCATION", rloc, 2L);
				inventors.setValue("CURBAL", qty, 2L);// 余量
			} else {
				IJpo inventors = inventorysSet.getJpo(0);
				inventors.setValue("CURBAL", inventors.getDouble("CURBAL")
						+ qty, 2L);// 入库
			}
			IJpo Invtranss = InvtransSet.addJpo();// 入库记录
			Invtranss.setValue("itemnum", itemnum, 2L);
			Invtranss.setValue("TRANSTYPE", "入库", 2L);
			Invtranss.setValue("QTY", qty, 2L);
			Invtranss.setValue("SQN", sqn, 2L);
			Invtranss.setValue("LOTNUM", pch, 2L);

			Invtranss.setValue("TRANSFERNUM", transfernum, 2L);
			Invtranss.setValue("STOREROOM", rloc, 2L);
			Invtranss.setValue("TRANSDATE", MroServer.getMroServer().getDate(),
					2L);
			if (xlh != false) {// 序列号判断
				IJpoSet assetSet = jpo.getJpoSet("$ASSET", "asset",
						"assetnum='" + sqn + "'");//
				if (assetSet.isEmpty()) {
					IJpo asset = assetSet.addJpo();
					asset.setValue("itemnum", itemnum);
					asset.setValue("sqn", sqn);
					asset.setValue("location", rloc);
					asset.setValue("INSTOREDATE", MroServer.getMroServer()
							.getDate());
					asset.setValue("secretleveldes", "非密");
					asset.setValue("status", "可用",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					asset.setValue("isnew", "1",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					asset.setValue("type", "1");
					asset.setValue("ANCESTOR", asset.getString("assetnum"));
					asset.setValue("INSTOREDATE", INSTOREDATE,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					asset.setValue("PROJNUM", PROJNUM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					//
				} else {
					IJpo asset = assetSet.getJpo(0);
					asset.setValue("location", rloc);
					asset.setValue("status", "可用",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

				}
			}

			if (pc != false) {// INVBLANCE
				IJpoSet invblanceSet = jpo.getJpoSet("$INVBLANCE", "INVBLANCE",
						"LOTNUM='" + pch + "'");//
				IJpo invblance = null;
				if (invblanceSet.isEmpty()) {
					invblance = invblanceSet.addJpo();
					invblance.setValue("LOTNUM", pch);// 批次号
					invblance.setValue("ITEMNUM", itemnum);// 物料编码
					invblance.setValue("RECEIVEDATE", MroServer.getMroServer()
							.getDate());// 入库时间
					invblance.setValue("STOREROOM", rloc);// 接收库房
					invblance.setValue("PHYSCNTQTY", qty, 56L);// 接收数量
				} else {
					invblance = invblanceSet.getJpo(0);
					invblance.setValue("PHYSCNTQTY",
							invblance.getDouble("PHYSCNTQTY") + qty);// 接收数量
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
