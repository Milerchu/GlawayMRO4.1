package com.glaway.sddq.tools;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient3.HttpTransportPropertiesImpl.Authenticator;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.Char10;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.Char12;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.Char18;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.Char2;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.Char3;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.Char35;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.Char4;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.Quantum153;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.TableOfZsdZsywItems;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.ZsdZsywHeader;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.ZsdZsywItems;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.ZtfunSdZsyw;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywdd.ComZzsErpZTFUN_SD_ZSYW_WEBStub.ZtfunSdZsywResponse;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjhd.ComZzsErpZTFUN_SD_ZSYWJH_WEBStub;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjhd.ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.Numeric6;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjhd.ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.Quantum133;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjhd.ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.TableOfZsywjhItem;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjhd.ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.ZsywjhHeader;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjhd.ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.ZsywjhItem;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjhd.ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.ZtfunSdZsywjh;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjhd.ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.ZtfunSdZsywjhResponse;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.TableOfZsdZjhjpItems;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.ZsdZjhjpHeader;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.ZtfunSdZjhjp;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.ZtfunSdZjhjpResponse;
import com.glaway.sddq.back.Interface.webservice.qms.sclient.ComZzsQmsDqXcgzMappingServiceStub;
import com.glaway.sddq.back.Interface.webservice.qms.sclient.ComZzsQmsDqXcgzMappingServiceStub.GetDataFormMRO;
import com.glaway.sddq.back.Interface.webservice.qms.sclient.ComZzsQmsDqXcgzMappingServiceStub.GetDataFormMROResponse;
import com.glaway.sddq.material.invtrans.common.CommonInventory;

/**
 * 
 * 工单 工具类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-20]
 * @since [产品/模块版本]
 */
public class WorkorderUtil {

	/**
	 * 
	 * 耗损件上下车操作
	 * 
	 * @param consumeSet
	 *            耗损件记录jposet
	 * @param workorder
	 *            工单jpo
	 * @throws MroException
	 * 
	 */
	public static void consumeUpDown(IJpoSet consumeSet, IJpo workorder)
			throws MroException {
		// 遍历耗损件记录表
		for (int index = 0; index < consumeSet.count(); index++) {
			IJpo consume = consumeSet.getJpo(index);

			if (consume.getBoolean("ISDONE")) {// 判断是否已经完成上下车
				continue;
			}
			// 下车件物料编码
			String downItemnum = consume.getString("DOWNITEMNUM");
			String downPartnum = consume.getString("DOWNPARTNUM");
			// 下车件父级序列号
			String downassetnum = consume.getString("DOWNASSETNUM");
			// 下车件批次号
			String downLotnum = consume.getString("DOWNLOTNUM") == null ? ""
					: consume.getString("DOWNLOTNUM");
			// 下车件仓位
			// String downBinnum = consume.getString("DOWNBINNUM");
			// 入库库房
			String underLoc = consume.getString("UNDERLOC");
			String downAssetnum = consume.getString("DOWNASSETNUM");

			// 上车件物料编码
			String upItemnum = consume.getString("ITEMNUM");
			// 上车件批次号
			String upLotnum = consume.getString("LOTNUM") == null ? ""
					: consume.getString("LOTNUM");
			// 上车件仓位
			// String upBinnum = consume.getString("UPBINNUM");
			// 出库库房
			String upLoc = consume.getString("UPLOC");
			// 操作数量
			int actQty = consume.getInt("AMOUNT");

			/**
			 * edit by Zhuhao,2018/10/27
			 */
			/* 下车操作 */
			// 耗损件子表
			IJpoSet assetpartSet = MroServer.getMroServer().getSysJpoSet(
					"ASSETPART", "assetpartnum='" + downPartnum + "'");

			if (!assetpartSet.isEmpty()) {
				IJpo assetpart = assetpartSet.getJpo(0);
				// 车上数量
				int qty = assetpart.getInt("QTY");
				// 此次操作后剩余数量
				int remainQty = qty - actQty;
				// 操作后数量不足0，则删除该记录,否则变更车上数量
				if (qty <= 0) {
					remainQty = 0;
				}
				assetpart.setValue("QTY", remainQty);
				assetpart.setValue("LOCKQTY", 1);// 重置锁定数量

				assetpartSet.save();

			}

			/* 上车操作 */
			if (StringUtil.isStrNotEmpty(upItemnum)) {

				if (downItemnum.equalsIgnoreCase(upItemnum)) {// 上下车物料编码一致

					IJpoSet apartSet2 = MroServer.getMroServer().getSysJpoSet(
							"ASSETPART");
					MroServer.getMroServer().getSystemUserServer()
							.getUserInfo().setDefaultOrg("CRRC");
					MroServer.getMroServer().getSystemUserServer()
							.getUserInfo().setDefaultSite("ELEC");
					if (downLotnum.equalsIgnoreCase(upLotnum)) {// 上下车批次号一致

						// 更新车上数量
						apartSet2.setUserWhere("assetpartnum='" + downPartnum
								+ "'");
						apartSet2.reset();
						if (apartSet2 != null && apartSet2.count() > 0) {
							IJpo apart = apartSet2.getJpo(0);
							apart.setValue("QTY", apart.getInt("QTY") + actQty);
						}

					} else {// 上下车批次号不一致

						// 新增耗损件
						IJpo newAssetpart = apartSet2.addJpo();
						newAssetpart.setValue("ASSETNUM", downassetnum);
						newAssetpart.setValue("ITEMNUM", upItemnum);
						newAssetpart.setValue("ORGID", "CRRC");
						newAssetpart.setValue("SITEID", "ELEC");
						newAssetpart.setValue("LOTNUM", upLotnum);
						newAssetpart.setValue("QTY", actQty);

					}
					apartSet2.save();

				} else {// 上下车物料编码不一致

					// 新增耗损件
					IJpoSet assetpartSet2 = MroServer.getMroServer()
							.getSysJpoSet("ASSETPART");
					MroServer.getMroServer().getSystemUserServer()
							.getUserInfo().setDefaultOrg("CRRC");
					MroServer.getMroServer().getSystemUserServer()
							.getUserInfo().setDefaultSite("ELEC");
					IJpo newAssetpart = assetpartSet2.addJpo();
					newAssetpart.setValue("ASSETNUM", downassetnum);
					newAssetpart.setValue("ITEMNUM", upItemnum);
					newAssetpart.setValue("ORGID", "CRRC");
					newAssetpart.setValue("SITEID", "ELEC");
					newAssetpart.setValue("LOTNUM", upLotnum);
					newAssetpart.setValue("QTY", actQty);

					assetpartSet2.save();

				}

				/**
				 * 将库存中锁定数量清零
				 */
				IJpoSet inventorySet = null;
				if (ItemUtil.LOT_I_ITEM.equals(ItemUtil.getItemInfo(upItemnum))) {// 批次号件

					inventorySet = MroServer.getMroServer().getSysJpoSet(
							"INVBLANCE",
							" itemnum='" + upItemnum + "' and lotnum='"
									+ upLotnum + "' and storeroom = '" + upLoc
									+ "'");

				} else {

					inventorySet = MroServer.getMroServer().getSysJpoSet(
							"SYS_INVENTORY",
							" itemnum='" + upItemnum + "' and location = '"
									+ upLoc + "'");
				}
				if (inventorySet != null && inventorySet.count() > 0) {
					IJpo jpo = inventorySet.getJpo(0);
					jpo.setValue("orderaplyqty", 0, GWConstant.P_NOVALIDATION);
					inventorySet.save();
				}
			}

			/* 删除车上数量为0的耗损件 */
			IJpoSet assetPartDelSet = MroServer.getMroServer().getSysJpoSet(
					"ASSETPART",
					"assetpartnum='" + downPartnum + "' and qty <= 0 ");
			if (assetPartDelSet != null && assetPartDelSet.count() > 0) {
				assetPartDelSet.deleteAll();
				assetPartDelSet.save();
			}

			/* 出入库操作 */
			if ("WORKORDER".equalsIgnoreCase(workorder.getName())) {// 服务模块工单
				// 判断是否已经进行出入库操作
				if (!consume.getBoolean("ISINPUT")) {
					// 下车件入库
					CommonInventory.ININVENTORY(downLotnum, actQty, underLoc,
							downItemnum, downAssetnum,
							workorder.getString("ordernum"));
					// 设置入库标志
					consume.setValue("ISINPUT", 1,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}

				if (StringUtil.isStrNotEmpty(upItemnum)) {// 存在上车件
					// 判断是否已经进行出入库操作
					if (!consume.getBoolean("ISOUTPUT")) {
						// 上车件出库
						CommonInventory.OUTINVENTORY(upLotnum, actQty, upLoc,
								upItemnum, "", workorder.getString("ordernum"));
						// 设置出库标志
						consume.setValue("ISOUTPUT", 1,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}
				/*
				 * / 下车件退料 createMPR("退料", workorder.getString("TYPE"),
				 * workorder.getString("ordernum"),
				 * workorder.getString("projectnum"),
				 * consume.getString("underloc"), downItemnum, "",
				 * consume.getInt("AMOUNT"), downLotnum, downBinnum,
				 * consume.getInt("INVENTORY")); // 上车件领料 createMPR("领料",
				 * workorder.getString("TYPE"), workorder.getString("ordernum"),
				 * workorder.getString("projectnum"),
				 * consume.getString("uploc"), upItemnum, "",
				 * consume.getInt("AMOUNT"), upLotnum, upBinnum,
				 * consume.getInt("INVENTORY"));
				 */
			} else {
				if (!consume.getBoolean("ISINPUT")) {
					// 下车件入库
					CommonInventory.ININVENTORY(downLotnum, actQty, underLoc,
							downItemnum, downAssetnum,
							workorder.getString("JXTASKNUM"));
					consume.setValue("ISINPUT", 1,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
			// 上车件出库
			/*
			 * CommonInventory.OUTINVENTORY(upLotnum, actQty, upLoc, upItemnum,
			 * "", workorder.getString("JXTASKNUM"));
			 */
			/*
			 * else { // 检修工单 createMPR("退料", "检修",
			 * workorder.getString("JXTASKNUM"),
			 * workorder.getString("projectnum"), consume.getString("underloc"),
			 * downItemnum, "", consume.getInt("AMOUNT"), downLotnum,
			 * downBinnum, consume.getInt("INVENTORY"));
			 * 
			 * createMPR("领料", "检修", workorder.getString("JXTASKNUM"),
			 * workorder.getString("projectnum"), consume.getString("uploc"),
			 * upItemnum, "", consume.getInt("AMOUNT"), upLotnum, upBinnum,
			 * consume.getInt("INVENTORY")); }
			 */

			// 设置完成标志
			consume.setValue("ISDONE", 1,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}// end of consume
		consumeSet.save();
	}

	/**
	 * 
	 * 创建领料单
	 * 
	 * @param mprType
	 *            领料单类型 type
	 * @param woType
	 *            工单类型 gdtype
	 * @param ordernum
	 *            工单编号 TASKNUM
	 * @param projectnum
	 *            项目编号 projectnum
	 * @param location
	 *            库房编号 MPRSTOREROOM
	 * @param itemnum
	 *            物料编码 itemnum
	 * @param sqn
	 *            产品序列号SQN
	 * @param qty
	 *            领用数量 qty
	 * @param lotnum
	 *            批次号 lotnum
	 * @param binnum
	 *            仓位 binnum
	 * @param curbal
	 *            仓位余量 CURBAL
	 */
	public static void createMPR(String mprType, String woType,
			String ordernum, String projectnum, String location,
			String itemnum, String sqn, int qty, String lotnum, String binnum,
			int curbal) throws MroException {

		// 先查询是否已有领料单
		// 领料单jposet
		IJpoSet mprSet1 = MroServer.getMroServer().getJpoSet("MPR",
				MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		mprSet1.setUserWhere("type='" + mprType + "' and gdtype='" + woType
				+ "' and tasknum='" + ordernum + "'");
		mprSet1.reset();

		IJpoSet mprSet2 = MroServer.getMroServer().getJpoSet("MPR",
				MroServer.getMroServer().getSystemUserServer());

		// 领料单编号
		String mprnum = "";
		if (mprSet1.isEmpty()) {// 没有领料单
			IJpo mpr = mprSet2.addJpo();
			// 当前登录用户id
			String loginid = mpr.getUserInfo().getLoginID();
			mprnum = mpr.getString("mprnum");
			mpr.setValue("TYPE", mprType);
			mpr.setValue("DESCRIPTION", woType + "工单" + ordernum + mprType);
			mpr.setValue("APPLICANTBY", loginid);
			mpr.setValue("MPRTYPE", "工单领料",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			mpr.setValue("GDTYPE", woType,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			mpr.setValue("ORGID", "CRRC");
			mpr.setValue("SITEID", "ELEC");
			if (StringUtil.isStrNotEmpty(ordernum)) {
				mpr.setValue("TASKNUM", ordernum);
			}
			mpr.setValue("PROJECTNUM", projectnum);
			if (StringUtil.isStrNotEmpty(location)) {
				mpr.setValue("MPRSTOREROOM", location);
			}
			mpr.setValue("status", "草稿");
		} else {
			mprnum = mprSet1.getJpo(0).getString("mprnum");
		}

		// 领料单行jposet
		IJpoSet mprlineSet = MroServer.getMroServer().getJpoSet("MPRLINE",
				MroServer.getMroServer().getSystemUserServer());
		IJpo mprline = mprlineSet.addJpo();
		mprline.setValue("MPRNUM", mprnum);
		mprline.setValue("ITEMNUM", itemnum,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		if (StringUtil.isStrNotEmpty(sqn)) {
			mprline.setValue("SQN", sqn);
		}
		mprline.setValue("CURBAL", curbal);
		mprline.setValue("QTY", qty);
		mprline.setValue("ISSUESTOREROOM", location);
		if (StringUtil.isStrNotEmpty(lotnum)) {
			mprline.setValue("lotnum", lotnum);
		}
		if (StringUtil.isStrNotEmpty(binnum)) {
			mprline.setValue("binnum", binnum);
		}
		mprline.setValue("ORGID", "CRRC");
		mprline.setValue("SITEID", "ELEC");

		mprlineSet.save();
		mprSet2.save();

	}

	/**
	 * 
	 * 周转件上下车操作
	 * 
	 * @param exchangeSet
	 *            上下车表jposet [参数说明]
	 * @param workorder
	 *            工单jpo
	 * @throws MroException
	 * 
	 */
	public static void swapHistory(IJpoSet exchangeSet, IJpo workorder)
			throws MroException {

		// 工单类型
		String orderType = workorder.getString("type");
		String ordernum = workorder.getString("ordernum");
		for (int i = 0; i < exchangeSet.count(); i++) {

			IJpo exchange = exchangeSet.getJpo(i);
			String isdo = exchange.getString("isdo");// 是否已经上下车
			if ("1".equals(isdo)) {// 进行过上下车则跳过
				continue;
			}
			// 下车件的信息
			String assetnum = exchange.getString("ASSETNUM");
			String ancestor = exchange.getString("ASSET.ANCESTOR");
			String parent = exchange.getString("ASSET.PARENT");
			String location = exchange.getString("LOCATION");
			// String sqn = exchange.getString("SQN");
			String itemnum = exchange.getString("ITEMNUM");
			// String confignum = exchange.getString("ASSET.CONFIGNUM");
			String softversion = exchange.getString("ASSET.SOFTVERSION");
			String lotnum = exchange.getString("LOTNUM");
			// 上车件的信息
			String newassetnum = exchange.getString("NEWASSETNUM");
			// String newsqn = exchange.getString("NEWSQN");
			String newitemnum = exchange.getString("NEWITEMNUM");
			// String newconfignum = exchange.getString("NEWCONFIGNUM");
			String newsoftversion = exchange.getString("NEWSOFTVERSION");
			String newloc = exchange.getString("NEWLOC");
			String newlotnum = exchange.getString("NEWLOTNUM");
			String faultposition = exchange.getString("FAULTPOSITION");
			// 故障品处置方式
			String dealMode = exchange.getString("DEALMODE");

			// 验证工单软件验证，更新配置信息，不做上下车操作
			if ("验证".equalsIgnoreCase(orderType)) {

				if (StringUtil.isStrEmpty(softversion)
						|| (!softversion.equalsIgnoreCase(newsoftversion))) {// 软件版本有变更
					IJpoSet downAsset = MroServer.getMroServer().getSysJpoSet(
							"ASSET", "assetnum='" + assetnum + "'");
					if (!downAsset.isEmpty()) {
						IJpo down = downAsset.getJpo();
						down.setValue("SOFTVERSION", newsoftversion);
						downAsset.save();

						exchange.setValue("ISDO", "1",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 设置完成标志
						continue;
					}

				}

			}
			// 改造性质
			String transType = workorder.getString("TRANSPLAN.TRANSTYPE");
			if ("改造".equals(orderType)
					&& ("软件改造".equals(transType) || "质量普查".equals(transType))) {// 非硬件改造不进行上下车

				if ("软件改造".equals(transType)) {
					IJpoSet downAsset = MroServer.getMroServer().getSysJpoSet(
							"ASSET", "assetnum='" + assetnum + "'");
					if (downAsset != null && downAsset.count() > 0) {
						IJpo down = downAsset.getJpo();
						down.setValue("SOFTVERSION", newsoftversion,
								GWConstant.P_NOVALIDATION);
						downAsset.save();
					}
				}
				exchange.setValue("ISDO", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 设置完成标志
				continue;
			}
			// 出入库操作
			if (StringUtil.isStrNotEmpty(newitemnum)) {// 有上车件
				// 出库重复判断
				if (!exchange.getBoolean("ISOUTPUT")) {
					// 上车件出库
					CommonInventory.OUTINVENTORY(newlotnum, 1, newloc,
							newitemnum, newassetnum, ordernum);
					// 设置出库标记
					exchange.setValue("ISOUTPUT", 1,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					// 重置锁定标记
					IJpoSet upAssetSet = exchange.getJpoSet("NEWASSET");
					if (upAssetSet != null && upAssetSet.count() > 0) {
						IJpo upAsset = upAssetSet.getJpo(0);
						upAsset.setValue("islocked", 0,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}

			}
			// 入库重复判断
			if (!exchange.getBoolean("ISINPUT")) {
				// 下车件入库
				CommonInventory.ININVENTORY(lotnum, 1, location, itemnum,
						assetnum, ordernum);
				// 设置入库标记
				exchange.setValue("ISINPUT", 1,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

				// 重置锁定标记
				IJpoSet downAssetSet = exchange.getJpoSet("ASSET");
				if (downAssetSet != null && downAssetSet.count() > 0) {
					IJpo downAsset = downAssetSet.getJpo(0);
					downAsset.setValue("islocked", 0,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}

			// 获取ASSET表中待拆卸的选中部件以及子部件
			IJpoSet removeMboset = MroServer.getMroServer().getJpoSet("ASSET",
					workorder.getUserServer());
			removeMboset
					.setUserWhere("ANCESTOR='"
							+ ancestor
							+ "' and assetnum in (select assetnum from asset  start with assetnum ='"
							+ assetnum
							+ "' connect by parent = PRIOR assetnum)");
			removeMboset.reset();

			// 保留结构，更新Asset表的parent以及ancestor
			if (!removeMboset.isEmpty()) {
				for (int index = 0; index < removeMboset.count(); index++) {
					// // ----------------xlb2019-4-15修改一物一档计算方法-----------//
					// IJpo asset = removeMboset.getJpo(index);
					// String downcartype = "下车";
					// String inlocationtype = "入库";
					// String thiscarancestor = workorder
					// .getString("ASSETNUM");// 整车assetnum
					// CommomCarItemLife.UPORDOWNCAR(asset, downcartype,
					// thiscarancestor);
					// CommomCarItemLife
					// .INOROURLOCATION(asset, inlocationtype);
					// // ----------------xlb2019-4-15修改一物一档计算方法-----------//
					if (removeMboset.getJpo(index).getString("ASSETNUM")
							.equals(assetnum)) {
						removeMboset.getJpo(index).setValue("parent", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						removeMboset.getJpo(index).setValue("ASSETLEVEL",
								"ASSET",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					removeMboset.getJpo(index).setValue("ANCESTOR", assetnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					removeMboset.getJpo(index).setValue("LOCATION", location,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// 检修的下车件默认要放到待处理物资库
					removeMboset.getJpo(index).setValue("TYPE", "3",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					String downtype = workorder.getString("type") + "下车";
					// 只有返修时才修改该字段的值
					if (!SddqConstant.FAIL_DEALMODE_RETENTION.equals(dealMode)) {
						removeMboset.getJpo(index).setValue("DOWNTYPE",
								downtype,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}

					// 设置下车件状态
					if ("故障".equals(orderType)) {
						// 返修时将状态设为故障
						if (!SddqConstant.FAIL_DEALMODE_RETENTION
								.equals(dealMode)) {
							removeMboset.getJpo(index).setValue("status", "故障",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						} else {// 不返修则将状态设为可用
							removeMboset.getJpo(index).setValue("status", "可用",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					} else if ("改造".equals(orderType)) {
						removeMboset.getJpo(index).setValue("status", "改造下车",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}
				removeMboset.save();

			}

			if (StringUtil.isStrNotEmpty(newitemnum)) {// 有上车件
				// 上车件处理

				IJpoSet installMboset = MroServer.getMroServer().getJpoSet(
						"ASSET", workorder.getUserServer());
				installMboset
						.setUserWhere("assetnum in (select assetnum from asset  start with assetnum ='"
								+ newassetnum
								+ "' connect by parent = PRIOR assetnum)");
				installMboset.reset();

				// 给从库存中取出的部件及子部件设置ANCESTOR和parent
				if (!installMboset.isEmpty()) {
					for (int index = 0; index < installMboset.count(); index++) {

						if (installMboset.getJpo(index).getString("ASSETNUM")
								.equals(newassetnum)) {
							installMboset.getJpo(index).setValue("parent",
									parent,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							installMboset.getJpo(index).setValue("ASSETLEVEL",
									"SYSTEM",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
						installMboset.getJpo(index).setValue("ANCESTOR",
								ancestor,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// installMboset.getJpo(index).setValue("LOCATION", "",
						// GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// installMboset.getJpo(index).setValue("BINNUM", "",
						// GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						installMboset.getJpo(index).setValue("TYPE", "2",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						installMboset.getJpo(index).setValue("ISNEW", "0",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 上车后将是否新品字段改成否
						installMboset.getJpo(index).setValue("RNUM",
								faultposition,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// //
						// ----------------xlb2019-4-15修改一物一档计算方法-----------//
						// IJpo asset = installMboset.getJpo(index);
						// String upcartype = "上车";
						// CommomCarItemLife.UPORDOWNCAR(asset, upcartype,
						// ancestor);
						// //
						// ----------------xlb2019-4-15修改一物一档计算方法-----------//

					}
					installMboset.save();
				}

			}
			exchange.setValue("ISDO", "1",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}// end of exchangeSet
		exchangeSet.save();
	}

	/**
	 * 
	 * 变更计划中的改造/验证数量
	 * 
	 * @param whichoffice
	 *            所属办事处
	 * @param plannum
	 *            计划编号
	 * @param models
	 *            车型编号
	 * 
	 */
	public static void updatePlanData(String whichoffice, String models,
			String plannum) throws MroException {
		// 改造、验证分布set
		IJpoSet transdistSet = MroServer.getMroServer().getJpoSet("TRANSDIST",
				MroServer.getMroServer().getSystemUserServer());
		transdistSet.setQueryWhere("whichoffice='" + whichoffice
				+ "' and transplannum='" + plannum + "'");
		transdistSet.reset();
		if (!transdistSet.isEmpty()) {
			IJpo transdist = transdistSet.getJpo();
			// 剩余改造数量
			int surplus = transdist.getInt("surplus");
			if (surplus > 0) {
				transdist.setValue("surplus", surplus - 1);
				transdistSet.save();
			}
		}
	}

	/**
	 * 
	 * 获取项目角色人员
	 * 
	 * @param projectnum
	 *            项目编号
	 * @param role
	 *            项目角色
	 * @return personid
	 * 
	 */
	public static Vector<String> getProjectRole(String projectnum, String role)
			throws MroException {
		// 项目成员set
		// yangyi 修改 获取项目角色返回多个值
		Vector<String> personidVec = new Vector<String>();
		IJpoSet prjTeamSet = MroServer.getMroServer().getJpoSet(
				"PROJECTTEAMEMBER",
				MroServer.getMroServer().getSystemUserServer());
		prjTeamSet.setQueryWhere("projectnum='" + projectnum
				+ "' and PROJECTROLE='" + role + "'");
		prjTeamSet.reset();
		String personid = "";
		if (!prjTeamSet.isEmpty()) {
			for (int i = 0; i < prjTeamSet.count(); i++) {
				IJpo prjTeam = prjTeamSet.getJpo(i);
				personid = prjTeam.getString("PERSONID");
				personidVec.add(personid);
			}
		}
		return personidVec;
	}

	/**
	 * 
	 * 获取项目经理personid 项目信息中的项目经理有可能是公司及项目经理 如果需要获取售后服务团队中的项目经理，需按照项目角色获取
	 * 
	 * @param projectnum
	 *            项目编号
	 * @return [参数说明]
	 * 
	 */
	public static String getPrjManager(String projectnum) throws MroException {

		IJpoSet projectSet = MroServer.getMroServer().getJpoSet("PROJECTINFO",
				MroServer.getMroServer().getSystemUserServer());
		projectSet.setQueryWhere("projectnum='" + projectnum + "'");
		projectSet.reset();

		String projectMgr = "";
		if (!projectSet.isEmpty()) {

			projectMgr = projectSet.getJpo().getString("PRJMANAGER");

		}

		return projectMgr;

	}

	/**
	 * 
	 * 获取办事处主任personid
	 * 
	 * @param personid
	 * @return [参数说明]
	 * 
	 */
	public static String getOfficeDirectorByPerson(String personid)
			throws MroException {
		// 人员表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				MroServer.getMroServer().getSystemUserServer());
		personSet.setQueryWhere("personid='" + personid + "'");
		personSet.reset();
		// 部门编号
		String deptnum = personSet.getJpo().getString("department");

		// 部门表
		IJpoSet deptSet = MroServer.getMroServer().getJpoSet("SYS_DEPT",
				MroServer.getMroServer().getSystemUserServer());
		deptSet.setQueryWhere("deptnum='" + deptnum + "'");
		deptSet.reset();

		String officer = "";
		if (!deptSet.isEmpty()) {
			officer = deptSet.getJpo().getString("OWNER");
		}

		return officer;

	}

	/**
	 * 
	 * 获取办事处责任人（办事处主任）
	 * 
	 * @param office
	 *            办事处部门编号
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static String getOfficeDirectorByOfficenum(String office)
			throws MroException {
		// 岗位
		String post = "";
		// 青岛取检修服务部生产服务主管
		if ("01062400".equals(office) || "01062403".equals(office)) {
			post = "生产服务主管";
			office = "01062403";
		} else {
			post = "办事处主任";
		}
		String officer = getPersonByPost(office, post);
		if (officer.indexOf(",") != -1) {// 存在多个办事处主任
			throw new MroException("该办事处存在多个办事处主任，请检查！");
		}
		officer = officer.replaceAll("'", "");// 去除单引号

		return officer;
	}

	/**
	 * 
	 * 根据人员id获取部门iD
	 * 
	 * @param personid
	 *            人员ID
	 * @return 部门ID
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static String getDeptByPerson(String personid) throws MroException {

		// 人员表
		IJpoSet personSet = MroServer.getMroServer().getJpoSet("SYS_PERSON",
				MroServer.getMroServer().getSystemUserServer());
		personSet.setQueryWhere("personid='" + personid.toUpperCase() + "'");
		personSet.reset();

		return personSet.getJpo().getString("department");

	}

	/**
	 * 
	 * 根据部门和岗位查询对应人员
	 * 
	 * @param deptnum
	 *            部门编号
	 * @param postname
	 *            岗位名称
	 * @return [参数说明]
	 * @throws MroException
	 * 
	 */
	public static String getPersonByPost(String deptnum, String postname)
			throws MroException {
		String person = "";
		String mdmid = "";// mdm部门编号

		IJpoSet deptSet = MroServer.getMroServer().getSysJpoSet("SYS_DEPT",
				"DEPTNUM='" + deptnum + "'");
		if (deptSet != null && deptSet.count() > 0) {
			mdmid = deptSet.getJpo(0).getString("MDM_DEPTID");
		}
		// 岗位表
		IJpoSet postSet = MroServer.getMroServer().getJpoSet("POST",
				MroServer.getMroServer().getSystemUserServer());
		postSet.setUserWhere("detpnum='" + deptnum + "' and postname like '%"
				+ postname + "%'");
		postSet.reset();

		if (postSet.isEmpty()) {// 根据mdmid查询
			postSet.setUserWhere("detpnum='" + mdmid + "' and postname like '%"
					+ postname + "%'");
			postSet.reset();
		}

		if (!postSet.isEmpty()) {
			IJpo post = postSet.getJpo(0);
			// 岗位人员表
			IJpoSet personSet = post.getJpoSet("person");
			if (!personSet.isEmpty()) {
				for (int index = 0; index < personSet.count(); index++) {
					person += "'"
							+ personSet.getJpo(index).getString("personid")
							+ "',";
				}
				// 去除末尾逗号
				person = person.substring(0, person.length() - 1);
			}

		} else {
			throw new MroException("该岗位无人员或无此岗位，请检查！");
		}

		return person;
	}

	/**
	 * 
	 * 生成工单编号
	 * 
	 * @param whichoffice
	 *            办事处
	 * @param appName
	 *            应用名称
	 * @return 工单编号
	 * @throws MroException
	 * 
	 */
	public static String generateOrdernum(String whichoffice, String appName,
			String odType) throws MroException {
		String ordernum = "";
		String type = "";
		// 办事处缩写
		String officeAbbr = getAbbreviationByDeptnum(whichoffice);
		// 当前年份
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String autokeyName = "";// 自动编号名称
		// 应用名称
		// 工单类型
		if (StringUtil.isStrNotEmpty(appName)) {

			if ("SERVORDER".equalsIgnoreCase(appName)) {// 服务工单
				type = "FW";
				autokeyName = "SORDERNUM";
			} else if ("FAILUREORD".equalsIgnoreCase(appName)) {// 故障工单
				type = "GZ";
				autokeyName = "FORDERNUM";
			} else if ("TRANSORDER".equalsIgnoreCase(appName)
					|| "TRANSPLAN".equalsIgnoreCase(appName)) {// 改造工单
				type = "GAIZAO";
				autokeyName = "TORDERNUM";
			} else {// 验证工单
				type = "YZ";
				autokeyName = "VORDERNUM";
			}

		} else {

			if ("故障".equals(odType)) {
				type = "GZ";
				autokeyName = "FORDERNUM";
			} else if ("服务".equals(odType)) {
				type = "FW";
				autokeyName = "SORDERNUM";
			} else if ("改造".equals(odType)) {
				type = "GAIZAO";
				autokeyName = "TORDERNUM";
			} else {
				type = "YZ";
				autokeyName = "VORDERNUM";
			}

		}

		// 流水号
		int serialnum = 0;
		while (true) {

			// 生成流水号
			serialnum = getSysAutoKey(autokeyName, "CRRC", "ELEC");
			// 判断流水号是否重复
			if (!isOrderNumRepeat(type, year, serialnum)) {
				// 不重复则跳出循环
				break;
			}

		}
		ordernum = officeAbbr + "-" + type + "-" + year + "-" + serialnum;
		return ordernum;
	}

	/**
	 * 
	 * 判断当年的工单流水号是否重复
	 * 
	 * @param type
	 *            工单类型
	 * @param year
	 *            年份
	 * @param serialNum
	 *            流水号
	 * @return true 重复，false 不重复
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private static boolean isOrderNumRepeat(String type, int year, int serialNum)
			throws MroException {

		// 工单表
		IJpoSet workOrderSet = MroServer.getMroServer().getSysJpoSet(
				"WORKORDER");
		workOrderSet.setQueryWhere(" ordernum like '%-" + type + "-" + year
				+ "-" + serialNum + "'");
		workOrderSet.reset();

		if (workOrderSet != null && workOrderSet.count() > 0) {
			// 存在相同流水号工单，返回true
			return true;
		}

		return false;
	}

	/**
	 * 
	 * 根据办事处编号，获取对应的缩写
	 * 
	 * @param deptnum
	 *            办事处编号
	 * @return [参数说明]缩写
	 * @throws MroException
	 * 
	 */
	public static String getAbbreviationByDeptnum(String deptnum)
			throws MroException {
		String abbr = "ZX";
		IJpoSet deptSet = MroServer.getMroServer().getSysJpoSet("SYS_DEPT");
		deptSet.setUserWhere(" deptnum ='" + deptnum + "' ");
		deptSet.reset();
		if (!deptSet.isEmpty()) {
			IJpo dept = deptSet.getJpo();
			if (null != dept) {
				abbr = dept.getString("SHORTNAME");
			}
		}
		return abbr;
	}

	/**
	 * 
	 * 根据办事处和当前年份查出工单流水号
	 * 
	 * @param orderType
	 *            工单类型
	 * @param deptnum
	 *            办事处编号
	 * @param year
	 *            当前年
	 * @return [参数说明]
	 * 
	 */
	private static int getLineNum(String orderType, String deptnum, int year) {
		int serialnum = 1000;
		try {
			// 当前办事处、当前工单类型 当年
			IJpoSet woSet = MroServer.getMroServer().getSysJpoSet("WORKORDER");
			woSet.setUserWhere("type='" + orderType + "' and whichoffice='"
					+ deptnum + "' and extract(year from createdate)=" + year);
			woSet.setOrderBy("ordernum desc");
			woSet.reset();
			if (!woSet.isEmpty()) {
				IJpo wo = woSet.getJpo(0);
				String oldnum = wo.getString("ordernum");
				// 取工单编号最后序号
				serialnum = Integer.parseInt(oldnum.substring(oldnum
						.lastIndexOf("-") + 1));
			}
		} catch (MroException e) {
			e.printStackTrace();
		}

		return serialnum + 1;
	}

	/**
	 * 
	 * 服务模块产品下车物料不可重复选择公共方法
	 * 
	 * @author zzx
	 * @version [版本号, 2018-8-20]
	 * @since [产品/模块版本]
	 */
	public static String serversxitemnum(IJpoSet exchangerecordSet,
			IJpo serverJpo) throws MroException {
		String assetnumnew = "";
		// IJpoSet exchangerecordSet = getJpo().getJpoSet();
		if (exchangerecordSet != null && exchangerecordSet.count() > 0) {
			for (int i = 0; i < exchangerecordSet.count(); i++) {
				if (exchangerecordSet.getJpo(i) != serverJpo) {
					String assetnums = exchangerecordSet.getJpo(i).getString(
							"ASSETNUM");
					assetnumnew += "'" + assetnums + "',";
				}

			}
			if (StringUtil.isStrNotEmpty(assetnumnew)) {
				assetnumnew = assetnumnew
						.substring(0, assetnumnew.length() - 1);
			}

		}
		// 上下车记录
		return assetnumnew;
	}

	/**
	 * 
	 * 服务模块产品上车件物料不可重复选择公共方法
	 * 
	 * @author zzx
	 * @version [版本号, 2018-8-20]
	 * @since [产品/模块版本]
	 */
	public static String serverupitemnum(IJpoSet jpoSet, IJpo curJpo)
			throws MroException {
		// IJpoSet exchangerecordSet = getJpo().getJpoSet();
		String assetnumnew = "";
		if (jpoSet != null && jpoSet.count() > 0) {
			for (int i = 0; i < jpoSet.count(); i++) {
				// if(curJpo.getId() jpoSet.getJpo(i).getId())
				if (jpoSet.getJpo(i) != curJpo) {
					String assetnums = jpoSet.getJpo(i)
							.getString("NEWASSETNUM");
					assetnumnew += "'" + assetnums + "',";
				}

			}
			if (StringUtil.isStrNotEmpty(assetnumnew)) {
				assetnumnew = assetnumnew
						.substring(0, assetnumnew.length() - 1);
			}

		}

		return assetnumnew;
	}

	/**
	 * 
	 * 过滤出已经选择的字段值
	 * 
	 * @param jpoSet
	 * @param uid
	 *            当前jpo 唯一id
	 * @param colName
	 *            字段名称
	 * @return 已经选择的值
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static String filterSelected(IJpoSet jpoSet, long uid, String colName)
			throws MroException {
		String selectedStr = "";
		if (jpoSet != null && jpoSet.count() > 0) {
			for (int index = 0; index < jpoSet.count(); index++) {
				IJpo jpo = jpoSet.getJpo(index);
				if (jpo.getId() == uid) {// 跳过当前新增的jpo
					continue;
				}
				selectedStr += "'" + jpo.getString(colName) + "',";
			}
			if (StringUtil.isStrNotEmpty(selectedStr)) {
				// 去除末尾的逗号
				selectedStr = selectedStr
						.substring(0, selectedStr.length() - 1);
			}
		}
		return selectedStr;
	}

	/**
	 * 
	 * 创建验证计划
	 * 
	 * @param valirequest
	 *            验证申请单jpo [参数说明]
	 * @author zhuhao
	 * @throws Exception
	 */
	public static void createValiPlan(IJpo valirequest) throws Exception {
		if (!valirequest.getJpoSet("VALIPLAN").isEmpty()) {// 已经生成过验证计划
			throw new MroException("valirebill", "valiplanexsits");
		}
		// 验证计划jposet
		IJpoSet planSet = MroServer.getMroServer().getSysJpoSet("TRANSPLAN");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		IJpo plan = planSet.addJpo();
		plan.setValue("TRANSTYPE", "软件验证");
		plan.setValue("TRANSPLANNAME", generateValiPlanName(valirequest));
		plan.setValue("PLANTYPE", "验证");
		plan.setValue("TRANSNOTICENUM", valirequest.getString("VALIREQUESTNUM"));
		plan.setValue("WORKORDERNUM", valirequest.getString("WORKORDERNUM"));
		String plannum = plan.getString("TRANSPLANNUM");// 计划编号
		// PLM验证产品范围子表
		IJpoSet plmRangeSet = valirequest.getJpoSet("PLMVALIPRORANGE");
		if (plmRangeSet != null && plmRangeSet.count() > 0) {

			// 验证产品范围子表新增
			IJpoSet valiprorangeSet = plan.getJpoSet("$VALIPRORANGE",
					"VALIPRORANGE", "1=2");
			for (int index = 0; index < plmRangeSet.count(); index++) {
				// PLM验证产品范围
				IJpo plmRange = plmRangeSet.getJpo(index);
				// 验证产品范围
				IJpo valiRange = valiprorangeSet.addJpo();
				valiRange.setValue("valirequestnum",
						valirequest.getString("valirequestnum"));// 申请单编号
				valiRange.setValue("PLANNUM", plan.getString("TRANSPLANNUM"),
						GWConstant.P_NOVALIDATION);// 计划编号
				valiRange.setValue("PLMOWNERCUSTOMER",
						plmRange.getString("OWNERCUSTOMER"));// PLM机务段

				valiRange.setValue("PLMTRANSMODELS",
						plmRange.getString("MODELS"));// plm涉及车型
				valiRange.setValue("TRANSMODELS", plmRange.getString("MODELS"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 涉及车型
				valiRange.setValue("PLMTRANSMODELSNAME",
						plmRange.getString("MODELPROJECT"));// plm车型项目
				valiRange.setValue("PRODUCTCODENAMENUM",
						plmRange.getString("PRODUCTCODENAMENUM"));// 产品名称
				valiRange
						.setValue("VALICOUNT", plmRange.getString("VALICOUNT"));// 验证数量
				valiRange.setValue("VALICOUNTUNIT",
						plmRange.getString("VALICOUNTUNIT"));// 验证数量单位

				valiRange.setValue("PARTCODE", plmRange.getString("PARTCODE"),
						GWConstant.P_NOVALIDATION);// 单板物料编码
				valiRange.setValue("CHIPDESC", plmRange.getString("CHIPDESC"));// 芯片型号
				valiRange.setValue("CHIPLOC", plmRange.getString("CHIPLOC"));// 芯片位置
				valiRange.setValue("PERIOD", plmRange.getString("PERIOD"));// 验证周期
				valiRange.setValue("PERIODUNIT",
						plmRange.getString("PERIODUNIT"));// 验证周期单位
				valiRange.setValue("REMARK", plmRange.getString("REMARK"));// 备注

				if (index == 0) {// 根据第一条验证产品范围设置计划的验证周期

					plan.setValue("period", plmRange.getString("PERIOD"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					plan.setValue("periodunit",
							plmRange.getString("PERIODUNIT"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

				}

			}
		}
		planSet.save();
		// 启动计划工作流
		IJpoSet valiplanSet = MroServer.getMroServer().getSysJpoSet(
				"TRANSPLAN", "TRANSPLANNUM='" + plannum + "'");
		if (valiplanSet != null && valiplanSet.count() > 0) {

			IJpo valiplan = valiplanSet.getJpo(0);
			WfControlUtil.startwf(valiplan, "VALIPLAN");
		}
	}

	/**
	 * 
	 * 生成验证计划名称
	 * 
	 * @param valiRequest
	 *            验证申请单jpo
	 * @return [参数说明]
	 * @author zhuhao
	 * @throws MroException
	 */
	public static String generateValiPlanName(IJpo valiRequest)
			throws MroException {
		String planName = "";
		// 验证日期
		Date valiDate = valiRequest.getDate("APPDATE");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		String valiDateStr = formatter.format(valiDate);

		// 第一条验证范围jpo
		IJpo valiRange = valiRequest.getJpoSet("PLMVALIPRORANGE").getJpo(0);
		String ownercustomer = valiRange.getString("OWNERCUSTOMER");// 机务段
		String model = valiRange.getString("MODELPROJECT");// 车型项目
		String jdc = valiRequest.getString("JDC");// 车型大类
		String product = valiRange.getString("PARTITEM.DESCRIPTION");// 单板物料描述

		planName = valiDateStr + ownercustomer + model + jdc + product + "验证计划";

		return planName;
	}

	/**
	 * 
	 * 获取人员组中人员
	 * 
	 * @param group
	 *            人员组id
	 * @return 人员id拼接，如：'20110712','20136658'
	 * @author zhuhao
	 */
	public static String getPersonsFromPersonGroup(String group)
			throws MroException {
		String persons = "";
		IJpoSet personGroupSet = MroServer.getMroServer().getSysJpoSet(
				"SYS_PERSONGROUPTEAM", "persongroup='" + group + "'");
		if (personGroupSet != null && personGroupSet.count() > 0) {
			for (int index = 0; index < personGroupSet.count(); index++) {
				IJpo groupTeam = personGroupSet.getJpo(index);
				persons += "'" + groupTeam.getString("respparty") + "',";
			}
			if (StringUtil.isStrNotEmpty(persons)) {
				// 去除末尾逗号
				persons = persons.substring(0, persons.length() - 1);
			}
		}

		return persons;
	}

	/**
	 * 
	 * 故障工单发送数据到QMS
	 * 
	 * @param workorder
	 *            故障工单Jpo
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void sendToQMS(IJpo workorder) throws MroException {

		String result = "";
		String num = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		String failurenum = workorder.getString("FAILURELIB.FAILURENUM");// 故障编号

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("data");

		// 检修厂家
		String repaircustname = workorder.getString("OVERHAULER.CUSTNAME");
		root.addElement("jxcj").addText(repaircustname);
		// 故障附件(地址)
		String attachmentUrl = workorder
				.getString("FAULTDIAGNOSE.SOURCEFILENAME");
		root.addElement("attachment").addText(attachmentUrl);
		// 报告人员
		String reporter = workorder.getString("REPORTER.DISPLAYNAME") + "("
				+ workorder.getString("REPORTER") + ")";
		root.addElement("bgry").addText(reporter);
		// 报告单位
		String reportdept = workorder.getString("REPORTER.DEPT.DESCRIPTION");
		root.addElement("bgdw").addText(reportdept);
		// 报告单位主管领导--质量分管领导角色
		IJpoSet roleSet = MroServer.getMroServer().getSysJpoSet("SYS_ROLE",
				"MAXROLE='QUALITYLEADER'");
		String groupid = roleSet.getJpo().getString("value");
		IJpoSet groupSet = MroServer.getMroServer().getSysJpoSet(
				"SYS_PERSONGROUPTEAM", "PERSONGROUP='" + groupid + "'");
		String personid = groupSet.getJpo().getString("RESPPARTYGROUP");
		String personname = groupSet.getJpo().getString(
				"RESPPARTY_PERSONS.DISPLAYNAME");
		String reportleader = personname + "(" + personid + ")";
		root.addElement("bgdwzgld").addText(reportleader);
		// 报告单位质量部部长--01060200为“中车时代电气售后服务中心质量安全部”
		IJpoSet quailtySet = MroServer.getMroServer().getSysJpoSet("SYS_DEPT",
				"DEPTNUM='01060200'");
		IJpo quailty = quailtySet.getJpo();
		String reportquailty = quailty.getString("OWNER.DISPLAYNAME") + "("
				+ quailty.getString("OWNER") + ")";
		root.addElement("bgdwzlbbz").addText(reportquailty);
		// 报告时间
		root.addElement("bgsj").addText(
				sdf.format(MroServer.getMroServer().getDate()));
		// 修后运行时间--天
		Integer aftRunTime = workorder.getInt("AFTREPAIRRUNTIME");
		root.addElement("xhyxsj").addText(aftRunTime.toString());
		// 修后走行公里
		String repairkilometer = workorder.getString("REPAIRAFTERKILOMETER");
		if (!repairkilometer.isEmpty()) {
			root.addElement("xhzxgl").addText(repairkilometer);
		}

		// TMS包含数据，无数据会向TMS请求数据
		// 供电区间
		String gdqj = workorder.getString("FAILURELIB.GDQJ");
		// 路况
		String roadtype = workorder.getString("FAILURELIB.ROADTYPE");
		// 天气
		String failweather = workorder.getString("FAILURELIB.FAILWEATHER");
		// 牵引负载吨位
		String qyfzdw = workorder.getString("FAILURELIB.QYFZDW");
		// 发生地点
		String faultlocation = workorder.getString("FAILURELIB.FAULTLOCATION");
		// 车次
		String trainline = workorder.getString("FAILURELIB.TMSDATA.TRAINLINE");
		// 工况
		String traincondition = workorder
				.getString("FAILURELIB.TMSDATA.TRAINCONDITION");

		if (gdqj.isEmpty() || roadtype.isEmpty() || failweather.isEmpty()
				|| qyfzdw.isEmpty() || faultlocation.isEmpty()) {
			String tmsStatus = workorder.getString("FAILURELIB.TMSDATA.STATUS");
			if (tmsStatus.isEmpty()) {
				getTMSData(workorder);
			}
			gdqj = workorder.getString("FAILURELIB.TMSDATA.GDQJ");
			roadtype = workorder.getString("FAILURELIB.TMSDATA.ROADCONDITION");
			failweather = workorder.getString("FAILURELIB.TMSDATA.WEATHER");
			qyfzdw = workorder.getString("FAILURELIB.TMSDATA.WEIGHT");
			faultlocation = workorder
					.getString("FAILURELIB.TMSDATA.FAILURELOC");
		}

		root.addElement("gdqj").addText(gdqj);
		root.addElement("lk").addText(roadtype);
		root.addElement("tq").addText(failweather);
		root.addElement("qyfzdw").addText(qyfzdw);
		root.addElement("fsdd").addText(faultlocation);
		root.addElement("cc").addText(trainline);
		root.addElement("gk").addText(traincondition);
		/* TMS END */

		// 故障发生时间
		Date faulttimeDate = workorder.getDate("FAILURELIB.FAULTTIME");
		String faulttime = "";
		if (faulttimeDate != null) {
			faulttime = sdf.format(faulttimeDate);
		}
		root.addElement("gzfssj").addText(faulttime);

		// 运行模式
		String runningmode = workorder.getString("FAILURELIB.RUNNINGMODE");
		root.addElement("yxms").addText(runningmode);

		// 处理方式
		String dealmethod = workorder.getString("FAILURELIB.DEALMETHOD");
		root.addElement("clfs").addText(dealmethod);

		// 故障现象
		String faultdesc = workorder.getString("FAILURELIB.FAULTDESC");
		faultdesc = faultdesc.replaceAll("%(?![0-9a-fA-F]{2})", "%25");// 对百分号特殊处理
		root.addElement("gzxx").addText(faultdesc);

		// 发生阶段
		String findprocess = workorder.getString("FAILURELIB.FINDPROCESS");
		root.addElement("fsjd").addText(findprocess);

		// 故障后果
		String faultconseq = workorder.getString("FAILURELIB.FAULTCONSEQ");
		root.addElement("gzhg").addText(faultconseq);

		// 故障定性(客户定责)
		String faultqualit = workorder.getString("FAILURELIB.FAULTQUALIT");
		root.addElement("gzdx").addText(faultqualit);

		// 处理步骤(处理措施)
		String dealmeasure = workorder.getString("FAILURELIB.DEALMEASURE");
		dealmeasure = dealmeasure.replaceAll("%(?![0-9a-fA-F]{2})", "%25");// 对百分号特殊处理
		root.addElement("clbz").addText(dealmeasure);
		// 运行公里数(累计走行公里)
		String runmileage = workorder.getString("RUNKILOMETRE");
		root.addElement("yxgls").addText(runmileage);

		// 故障品返回原因
		String repairreason = workorder.getString("FAILURELIB.REPAIRREASON");
		root.addElement("gzpfhyy").addText(repairreason);

		// 故障名称
		String failuredesc = workorder.getString("FAILURELIB.FAILUREDESC");
		if (hasIllegalFileNameChar(failuredesc)) {
			throw new MroException("发送失败，故障名称中包含非法字符，请检查！");
		}
		failuredesc = failuredesc.replaceAll("%(?![0-9a-fA-F]{2})", "%25");// 对百分号特殊处理
		root.addElement("gzmc").addText(failuredesc);

		// 故障代码
		String failurecode = workorder.getString("FAILURELIB.FAILURECODE");
		root.addElement("gzdm").addText(failurecode);

		// 初步分析原因(初步分析)
		String prereasonalys = workorder.getString("FAILURELIB.PREREASONALYS");
		prereasonalys = prereasonalys.replaceAll("%(?![0-9a-fA-F]{2})", "%25");// 对百分号特殊处理
		root.addElement("cbyyfx").addText(prereasonalys);

		// 故障数据下载(故障数据上传状态)
		String faultdatarec = workorder.getString("FAILURELIB.FAULTDATAREC");
		root.addElement("gzsjxz").addText(faultdatarec);

		// 车厢号
		String carsectionnum = workorder.getString("FAILURELIB.CARSECTIONNUM");
		root.addElement("cxh").addText(carsectionnum);

		// 项目编号
		String projectnum = workorder.getString("PROJECTNUM");
		root.addElement("xmh").addText(projectnum);

		// 客户是否需要分析报告
		String analysisrepneed = workorder
				.getString("FAILURELIB.ANALYSISREPNEED");
		root.addElement("fsxfxbg").addText(analysisrepneed);

		// 工单表
		String failureordernum = workorder.getString("ORDERNUM");

		// 故障工单10+3个字段

		// 检修日期
		Date crdateDate = workorder.getDate("UPDATETIME");
		String crdate = "";
		if (crdateDate != null) {
			crdate = sdf.format(crdateDate);
		}
		root.addElement("jxrq").addText(crdate);

		// 检修修程(修程修次)
		String repairprocess = workorder.getString("REPAIRPROCESS.DESCRIPTION");
		root.addElement("jxxc").addText(repairprocess);

		// 产品类别(车型/产品类别)
		String modeltype = workorder
				.getString("MODELS.MODELTYPENEW.DESCRIPTION");
		root.addElement("cplb").addText(modeltype);

		// 到达现场时间
		String arrivetime = workorder.getString("ARRIVETIME");
		root.addElement("ddxcsj").addText(arrivetime);

		// 作业开始时间
		String operatetime = workorder.getString("OPERATETIME");
		root.addElement("zykssj").addText(operatetime);

		// 处理完成时间
		String completetime = workorder.getString("COMPLETETIME");
		root.addElement("clwcsj").addText(completetime);

		// 现场处理人
		String servengineer = workorder.getString("SERVENGINEER.DISPLAYNAME");
		String servengineerId = workorder.getString("SERVENGINEER");
		root.addElement("xcclr").addText(
				servengineer + "(" + servengineerId + ")");

		// 装车车型
		String models = workorder.getString("MODELS.MODELCODE");
		root.addElement("zccx").addText(models);

		// 车型大类 /*add by zhuhao,2019-02-20*/
		String cxdl = workorder.getString("MODELS.PRODUCTLINE");
		root.addElement("cxdl").addText(cxdl);

		// 装车车号
		String carnum = workorder.getString("CARNUM");
		root.addElement("zcch").addText(carnum);

		// 配属用户
		String custname = workorder.getString("OWNERCUSTOMER.CUSTNAME");
		root.addElement("psyh").addText(custname);

		// 现场处理人联系方式
		String primaryphone = workorder.getString("SERVENGINEER.PRIMARYPHONE");
		root.addElement("xcclrlxfs").addText(primaryphone);

		// 车型项目
		String modelproject = workorder.getString("MODELPROJECT");
		if (!modelproject.isEmpty()) {
			root.addElement("cxxm").addText(modelproject);
		}

		// TMS表
		IJpoSet tmsSet = MroServer.getMroServer().getJpoSet("TMSDATA",
				MroServer.getMroServer().getSystemUserServer());
		tmsSet.setQueryWhere("FAILURENUM='" + failurenum + "'");
		tmsSet.reset();

		if (tmsSet.count() > 0) {
			// TMS 10个字段

			// 供电区间
			String gdqj2 = tmsSet.getJpo().getString("GDQJ");
			if (!gdqj2.isEmpty()) {
				root.addElement("gdqj").addText(gdqj2);
			}

			// 海拔
			String elevation = tmsSet.getJpo().getString("ELEVATION");
			root.addElement("hb").addText(elevation);

			// 路况
			String roadcondition = tmsSet.getJpo().getString("ROADCONDITION");
			root.addElement("lk").addText(roadcondition);

			// 天气
			String weather = tmsSet.getJpo().getString("WEATHER");
			root.addElement("tq").addText(weather);

			// 速度
			String trainspped = tmsSet.getJpo().getString("TRAINSPPED");
			root.addElement("sd").addText(trainspped);

			// 担当
			String assume = tmsSet.getJpo().getString("ASSUME");
			root.addElement("dd").addText(assume);

			// 牵引负载吨位
			String tonnage = tmsSet.getJpo().getString("TONNAGE");
			root.addElement("qyfzdw").addText(tonnage);

			// 工况
			String otpcondition = tmsSet.getJpo().getString("TRAINCONDITION");
			root.addElement("gk").addText(otpcondition);

			// 温度
			String temperature = tmsSet.getJpo().getString("TEMPERATURE");
			root.addElement("wd").addText(temperature);

			// 发生地点
			String failureloc = tmsSet.getJpo().getString("FAILURELOC");
			root.addElement("fsdd").addText(failureloc);
		}

		/* 新增故障件xml元素 */
		String[] elements = { "gzpthwzbm", "gzpmc", "gzpxh", "ghpxh", "ghpth",
				"ghpmc", "gzpwz", "gzprjbbpzh", "gzpczfs", "QMS_ID", "scdw",
				"ssyjlbj", "yjlbjxlh", "yjlbjbh", "ssejlbj", "ejlbjxlh",
				"ejlbjbh", "sssjlbj", "sjlbjxlh", "sjlbjbh", "ssfjlbj",
				"fjlbjxlh", "fjlbjbh", "sswjlbj", "wjlbjxlh", "wjlbjbh" };
		for (String elementStr : elements) {
			root.addElement(elementStr);
		}

		// 周转件上下车表
		IJpoSet exchangerecordSet = workorder.getJpoSet("FAILURELIB").getJpo()
				.getJpoSet("EXCHANGERECORD");
		// 耗损件上下车表
		IJpoSet consumeSet = workorder.getJpoSet("JXTASKLOSSPART");

		int maxRownum = (int) exchangerecordSet.max("ROWNO");// 周转件最大行号
		if (consumeSet != null && consumeSet.count() > 0) {
			if (maxRownum == 0 || maxRownum < (int) consumeSet.max("ROWNO")) {
				maxRownum = (int) consumeSet.max("ROWNO");// 耗损件最大行号
			}

		}
		int rownum = maxRownum == 0 ? 10 : (maxRownum + 20);// 行号
		String qmsNums = "";
		if (exchangerecordSet != null
				&& exchangerecordSet.count(GWConstant.P_COUNT_AFTERSAVE) > 0) {

			for (int i = 0; i < exchangerecordSet.count(); i++) {

				IJpo exJpo = exchangerecordSet.getJpo(i);

				if (exJpo.getBoolean("ISSENDTOQMS")) {// 跳过已经发送过数据的记录
					continue;
				}

				if (exJpo.getInt("ROWNO") == 0) {
					exJpo.setValue("ROWNO", rownum, GWConstant.P_NOVALIDATION);
				}

				// 故障品图号/物资编码
				root.element("gzpthwzbm").setText(exJpo.getString("ITEMNUM"));

				// 故障品名称
				String gzpmc = exJpo.getString("ITEM.DESCRIPTION");
				gzpmc = gzpmc.replaceAll("%(?![0-9a-fA-F]{2})", "%25");// 对百分号特殊处理
				root.element("gzpmc").setText(gzpmc);

				// 故障品序号
				root.element("gzpxh").setText(exJpo.getString("SQN"));

				// 更换品序号
				root.element("ghpxh").setText(exJpo.getString("NEWSQN"));

				// 更换品图号/物资编码
				root.element("ghpth").setText(exJpo.getString("NEWITEMNUM"));

				// 更换品名称
				String ghpmc = exJpo.getString("NEWITEM.DESCRIPTION");
				ghpmc = ghpmc.replaceAll("%(?![0-9a-fA-F]{2})", "%25");// 对百分号特殊处理
				root.element("ghpmc").setText(ghpmc);

				// 故障品位置(故障品位置号)
				root.element("gzpwz").setText(
						getSafeXmlString(exJpo.getString("FAULTPOSITION")));

				// 故障品软件版本
				root.element("gzprjbbpzh").setText(
						exJpo.getString("ASSET.SOFTVERSION"));

				// 故障品处置方式
				root.element("gzpczfs").setText(exJpo.getString("DEALMODE"));

				// 生产单位 add by ZH,2018/09/27
				String producter = exJpo.getString("ITEM.ITEMPOTYPE");
				if (StringUtil.isStrNotEmpty(producter)) {
					if ("自制件".equals(producter)) {
						producter = "本单位自制";
					} else if ("外购件".equals(producter)) {
						producter = "外购";
					} else if ("外协件".equals(producter)) {
						producter = "外协";
					} else if ("三菱插件".equals(producter)) {
						producter = "三菱件";
					}

					root.element("scdw").setText(producter);
				}

				// QMS_ID——使用 故障工单编号-上下车唯一id
				root.element("QMS_ID").setText(
						failureordernum + "-"
								+ exJpo.getString("exchangerecordid"));
				// 故障件上级
				IJpoSet superiorSet = exJpo.getJpoSet("SUPERIORASSET");
				if (superiorSet != null
						&& superiorSet.count(GWConstant.P_COUNT_AFTERSAVE) > 0) {
					for (int idx = 0; idx < superiorSet.count(); idx++) {
						IJpo superior = superiorSet.getJpo(idx);
						String itemnum = superior.getString("ITEMNUM");// 物料编码
						String sqnorbatch = superior.getString("SQNORBATCHNUM");// 序列号
						String faultName = superior
								.getString("SYS_ITEM.DESCRIPTION");// 物料描述
						faultName = faultName.replaceAll("%(?![0-9a-fA-F]{2})",
								"%25");// 对百分号特殊处理
						int level = superior.getInt("partlevel");// 级别

						switch (level) {
						case 1:
							root.element("yjlbjbh").setText(itemnum);
							root.element("yjlbjxlh").setText(sqnorbatch);
							root.element("ssyjlbj").setText(faultName);
							break;
						case 2:
							root.element("ejlbjbh").setText(itemnum);
							root.element("ejlbjxlh").setText(sqnorbatch);
							root.element("ssejlbj").setText(faultName);
							break;
						case 3:
							root.element("sjlbjbh").setText(itemnum);
							root.element("sjlbjxlh").setText(sqnorbatch);
							root.element("sssjlbj").setText(faultName);
							break;
						case 4:
							root.element("fjlbjbh").setText(itemnum);
							root.element("fjlbjxlh").setText(sqnorbatch);
							root.element("ssfjlbj").setText(faultName);
							break;
						case 5:
							root.element("wjlbjbh").setText(itemnum);
							root.element("wjlbjxlh").setText(sqnorbatch);
							root.element("sswjlbj").setText(faultName);
							break;
						}
					}
				}

				// 必填校验
				Map<String, String> checkers = QMSFieldCheck();
				List<Element> list = root.elements();
				for (Element e : list) {
					if (checkers.containsKey(e.getName())) {
						if (e.getText() == null || e.getText().isEmpty()) {
							throw new MroException("", "发送失败，QMS必填字段:["
									+ checkers.get(e.getName()) + "]为空");
						}
						checkers.remove(e.getName());
					}
				}
				if (!checkers.isEmpty()) {
					throw new MroException("", "发送失败，QMS必填字段:["
							+ checkers.values() + "]为空");
				}
				try {
					num = IFUtil.addIfHistory("QMS_MRO_DATA", doc.asXML(),
							IFUtil.TYPE_OUTPUT);// 接口管理中间表
					ComZzsQmsDqXcgzMappingServiceStub service = new ComZzsQmsDqXcgzMappingServiceStub();
					// 认证代码 start
					Authenticator auth = new Authenticator();
					String user = IFUtil.getIfServiceInfo("qms.user");
					String pwd = IFUtil.getIfServiceInfo("qms.pwd");
					auth.setUsername(user);
					auth.setPassword(pwd);
					service._getServiceClient().getOptions()
							.setProperty(HTTPConstants.AUTHENTICATE, auth);
					service._getServiceClient().getOptions()
							.setTimeOutInMilliSeconds(600000L);
					GetDataFormMRO fetDataFormMRO = new GetDataFormMRO();
					fetDataFormMRO.setIn0(StringEscapeUtils.unescapeXml(doc
							.asXML()));
					GetDataFormMROResponse res = service
							.getDataFormMRO(fetDataFormMRO);

					result = res.getOut();
					if (StringUtil.isStrEmpty(result)) {
						throw new MroException("接口返回为空！");
					}

				} catch (Exception e) {
					IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO, e.getMessage());
					e.printStackTrace();
					throw new MroException(e.getMessage());
				}
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_NO, failureordernum + "-" + exJpo.getId()
								+ "数据发送成功");

				String returnnum = IFUtil.addIfHistory("QMS_MRO_DATA", result,
						IFUtil.TYPE_INPUT);
				try {
					Document resdoc = DocumentHelper.parseText(result);
					Element resroot = resdoc.getRootElement();
					// 请求返回状态
					if (resroot.element("state").getTextTrim().equals("false")) {
						IFUtil.updateIfHistory(returnnum,
								IFUtil.STATUS_FAILURE, IFUtil.FLAG_NO, resroot
										.element("mesg").getTextTrim());
						throw new MroException("", resroot.element("mesg")
								.getTextTrim());
					} else {
						IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
								IFUtil.FLAG_YES,
								failureordernum + "-" + exJpo.getId()
										+ "数据发送成功");
						try {
							// qms表单编号
							String bdbh = resroot.element("bdbh").getTextTrim();
							qmsNums += bdbh + ",";

							IJpoSet exSet = MroServer
									.getMroServer()
									.getSysJpoSet(
											"EXCHANGERECORD",
											"exchangerecordnum='"
													+ exJpo.getString("exchangerecordnum")
													+ "'");
							exSet.getJpo(0).setValue("QMS_NUM", bdbh);
							exSet.getJpo(0).setValue("ISSENDTOQMS", 1);// 设置已经发送QMS标记
							if (exSet.getJpo(0).getInt("ROWNO") == 0) {
								exSet.getJpo(0).setValue("ROWNO", rownum);
							}
							exSet.save();

							IJpoSet qmsInfoSet = MroServer.getMroServer()
									.getSysJpoSet("QMSREPAIRINFO");
							IJpo qmsInfo = qmsInfoSet.addJpo();
							qmsInfo.setValue("WORDERNUM",
									workorder.getString("ordernum"));
							qmsInfo.setValue("QMSREPAIRNUM",
									resroot.element("bdbh").getTextTrim(),
									GWConstant.P_NOVALIDATION);// qms表单编号
							qmsInfo.setValue("ROWNO", exJpo.getString("ROWNO"),
									GWConstant.P_NOVALIDATION);// 下车件行号
							qmsInfo.setValue("ITEMNUM",
									exJpo.getString("ITEMNUM"));// 下车件物料编码
							qmsInfo.setValue("REPAIRMENTSN",
									exJpo.getString("SQN"));// 下车件序列号
							qmsInfoSet.save();

						} catch (Exception e) {
							IFUtil.updateIfHistory(returnnum,
									IFUtil.STATUS_FAILURE, IFUtil.FLAG_NO,
									e.getMessage());
						}
						IFUtil.updateIfHistory(returnnum,
								IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
								failureordernum + "-" + exJpo.getId()
										+ resroot.element("mesg").getTextTrim());
					}
				} catch (DocumentException e) {
					IFUtil.updateIfHistory(returnnum, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO, e.getMessage());
					e.printStackTrace();
				}

				rownum += 20;
				// 清空故障件信息
				for (String elmt : elements) {
					root.element(elmt).setText("");
				}
			}// end of exchangerecordSet circle
		}

		/* 耗损件记录表 */
		// 清空故障件信息
		for (String elmt : elements) {
			root.element(elmt).setText("");
		}

		if (consumeSet != null
				&& consumeSet.count(GWConstant.P_COUNT_AFTERSAVE) > 0) {

			for (int j = 0; j < consumeSet.count(); j++) {

				IJpo consume = consumeSet.getJpo(j);

				if (consume.getBoolean("ISSENDTOQMS")) {
					continue;
				}

				if (consume.getInt("ROWNO") == 0) {
					consume.setValue("ROWNO", rownum, GWConstant.P_NOVALIDATION);
				}

				// 故障品图号/物资编码
				root.element("gzpthwzbm").setText(
						consume.getString("DOWNITEMNUM"));

				// 故障品名称
				String gzpmc = consume.getString("DOWNITEMNUM.DESCRIPTION");
				gzpmc = gzpmc.replaceAll("%(?![0-9a-fA-F]{2})", "%25");// 对百分号特殊处理
				root.element("gzpmc").setText(gzpmc);

				// 故障品序号/批次号
				String batchnum = StringUtil.isStrEmpty(consume
						.getString("DOWNLOTNUM")) ? "NA" : consume
						.getString("DOWNLOTNUM");
				root.element("gzpxh").setText(batchnum);

				// 更换品序号
				String newbatchnum = StringUtil.isStrEmpty(consume
						.getString("LOTNUM")) ? "NA" : consume
						.getString("LOTNUM");
				root.element("ghpxh").setText(newbatchnum);

				// 更换品图号/物资编码
				root.element("ghpth").setText(consume.getString("ITEMNUM"));

				// 更换品名称
				String ghpmc = consume.getString("ITEM.DESCRIPTION");
				ghpmc = ghpmc.replaceAll("%(?![0-9a-fA-F]{2})", "%25");// 对百分号特殊处理
				root.element("ghpmc").setText(ghpmc);

				// 生产单位 add by ZH,2018/09/27
				String producter = consume.getString("DOWNITEMNUM.ITEMPOTYPE");
				if (StringUtil.isStrNotEmpty(producter)) {
					if ("自制件".equals(producter)) {
						producter = "本单位自制";
					} else if ("外购件".equals(producter)) {
						producter = "外购";
					} else if ("外协件".equals(producter)) {
						producter = "外协";
					} else if ("三菱插件".equals(producter)) {
						producter = "三菱件";
					}

					root.element("scdw").setText(producter);
				}

				// 故障品位置(故障品父级位置号)
				root.element("gzpwz").setText(
						StringUtil.isStrEmpty(consume
								.getString("DOWNASSET.RNUM")) ? "NA" : consume
								.getString("DOWNASSET.RNUM"));

				// 故障品软件版本
				/*
				 * root.element("gzprjbbpzh").setText(
				 * consume.getString("ASSET.SOFTVERSION"));
				 */

				// 故障品处置方式
				root.element("gzpczfs").setText(consume.getString("DEALMODE"));

				// QMS_ID——使用 故障工单编号-上下车唯一id
				root.element("QMS_ID").setText(
						failureordernum + "-"
								+ consume.getString("jxtasklosspartid"));

				// 故障件上级
				IJpoSet superiorSet = consume.getJpoSet("SUPERIORASSET");
				if (superiorSet != null
						&& superiorSet.count(GWConstant.P_COUNT_AFTERSAVE) > 0) {
					for (int idx = 0; idx < superiorSet.count(); idx++) {
						IJpo superior = superiorSet.getJpo(idx);
						String itemnum = superior.getString("ITEMNUM");// 物料编码
						String sqnorbatch = superior.getString("SQNORBATCHNUM");// 序列号
						String faultName = superior
								.getString("SYS_ITEM.DESCRIPTION");// 物料描述
						faultName = faultName.replaceAll("%(?![0-9a-fA-F]{2})",
								"%25");// 对百分号特殊处理
						int level = superior.getInt("partlevel");// 级别

						switch (level) {
						case 1:
							root.element("yjlbjbh").setText(itemnum);
							root.element("yjlbjxlh").setText(sqnorbatch);
							root.element("ssyjlbj").setText(faultName);
							break;
						case 2:
							root.element("ejlbjbh").setText(itemnum);
							root.element("ejlbjxlh").setText(sqnorbatch);
							root.element("ssejlbj").setText(faultName);
							break;
						case 3:
							root.element("sjlbjbh").setText(itemnum);
							root.element("sjlbjxlh").setText(sqnorbatch);
							root.element("sssjlbj").setText(faultName);
							break;
						case 4:
							root.element("fjlbjbh").setText(itemnum);
							root.element("fjlbjxlh").setText(sqnorbatch);
							root.element("ssfjlbj").setText(faultName);
							break;
						case 5:
							root.element("wjlbjbh").setText(itemnum);
							root.element("wjlbjxlh").setText(sqnorbatch);
							root.element("sswjlbj").setText(faultName);
							break;
						}
					}
				}

				// 必填校验
				Map<String, String> checkers2 = QMSFieldCheck();

				List<Element> list = root.elements();
				for (Element e : list) {
					if (checkers2.containsKey(e.getName())) {
						if (e.getText() == null || e.getText().isEmpty()) {
							throw new MroException("", "发送失败，QMS必填字段:["
									+ checkers2.get(e.getName()) + "]为空");
						}
						checkers2.remove(e.getName());
					}
				}
				if (!checkers2.isEmpty()) {
					throw new MroException("", "发送失败，QMS必填字段:["
							+ checkers2.values() + "]为空");
				}
				try {
					num = IFUtil.addIfHistory("QMS_MRO_DATA", doc.asXML(),
							IFUtil.TYPE_OUTPUT);// 接口管理中间表

					ComZzsQmsDqXcgzMappingServiceStub service = new ComZzsQmsDqXcgzMappingServiceStub();
					// 认证代码 start
					Authenticator auth = new Authenticator();
					String user = IFUtil.getIfServiceInfo("qms.user");
					String pwd = IFUtil.getIfServiceInfo("qms.pwd");
					auth.setUsername(user);
					auth.setPassword(pwd);
					service._getServiceClient().getOptions()
							.setProperty(HTTPConstants.AUTHENTICATE, auth);
					service._getServiceClient().getOptions()
							.setTimeOutInMilliSeconds(600000L);

					GetDataFormMRO fetDataFormMRO = new GetDataFormMRO();
					fetDataFormMRO.setIn0(StringEscapeUtils.unescapeXml(doc
							.asXML()));
					GetDataFormMROResponse res = service
							.getDataFormMRO(fetDataFormMRO);

					result = res.getOut();
				} catch (Exception e) {
					IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO, e.getMessage());
					e.printStackTrace();
					throw new MroException(e.getMessage());
				}
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_NO, failureordernum + "-" + consume.getId()
								+ "数据发送成功");

				String returnnum = IFUtil.addIfHistory("QMS_MRO_DATA", result,
						IFUtil.TYPE_INPUT);
				try {
					Document resdoc = DocumentHelper.parseText(result);
					Element resroot = resdoc.getRootElement();
					// 请求返回状态
					if (resroot.element("state").getTextTrim().equals("false")) {
						IFUtil.updateIfHistory(returnnum,
								IFUtil.STATUS_FAILURE, IFUtil.FLAG_NO, resroot
										.element("mesg").getTextTrim());
						throw new MroException("", resroot.element("mesg")
								.getTextTrim());
					} else {
						IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
								IFUtil.FLAG_YES, failureordernum + "-"
										+ consume.getId() + "数据发送成功");
						try {
							String bdbh = resroot.element("bdbh").getTextTrim();
							qmsNums += bdbh + ",";

							IJpoSet consumeSet2 = MroServer.getMroServer()
									.getSysJpoSet(
											"JXTASKLOSSPART",
											"JXTASKLOSSPARTID="
													+ consume.getId());
							consumeSet2.getJpo(0).setValue("QMSNUM", bdbh,
									GWConstant.P_NOVALIDATION);
							consumeSet2.getJpo(0).setValue("ISSENDTOQMS", 1);// 设置发送成功标记
							if (consumeSet2.getJpo(0).getInt("ROWNO") == 0) {
								consumeSet2.getJpo(0).setValue("ROWNO", rownum);
							}
							consumeSet2.save();

							IJpoSet qmsInfoSet = MroServer.getMroServer()
									.getSysJpoSet("QMSREPAIRINFO");
							IJpo qmsInfo = qmsInfoSet.addJpo();
							qmsInfo.setValue("WORDERNUM",
									workorder.getString("ordernum"));// 工单编号
							qmsInfo.setValue("QMSREPAIRNUM",
									resroot.element("bdbh").getTextTrim(),
									GWConstant.P_NOVALIDATION);// qms表单编号
							qmsInfo.setValue("ROWNO",
									consume.getString("ROWNO"),
									GWConstant.P_NOVALIDATION);// 下车件行号
							qmsInfo.setValue("ITEMNUM",
									consume.getString("DOWNITEMNUM"));// 下车件物料编码
							qmsInfo.setValue("REPAIRMENTSN",
									consume.getString("ASSETPART.LOTNUM"));// 下车件序列号/批次号
							qmsInfoSet.save();

						} catch (Exception e) {
							IFUtil.updateIfHistory(returnnum,
									IFUtil.STATUS_FAILURE, IFUtil.FLAG_NO,
									e.getMessage());
						}
						IFUtil.updateIfHistory(returnnum,
								IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
								failureordernum + "-" + consume.getId()
										+ resroot.element("mesg").getTextTrim());
					}
				} catch (DocumentException e) {
					IFUtil.updateIfHistory(returnnum, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO, e.getMessage());
					e.printStackTrace();
				}
				rownum += 20;
				// 清空故障件信息
				for (String elmt : elements) {
					root.element(elmt).setText("");
				}
			}// end of consumeSet

		}
		if (StringUtil.isStrNotEmpty(qmsNums)) {
			qmsNums = qmsNums.substring(0, qmsNums.length() - 1);
		} else {// 无故障件工单
			if (StringUtil.isStrEmpty(workorder.getString("QMS_NUM"))) {
				// 故障品图号/物资编码
				root.element("gzpthwzbm").setText(
						workorder.getString("FAULTCOMPITEMNUM"));

				// 故障品名称
				String gzpmc = workorder
						.getString("FAULTCOMPITEMNUM.DESCRIPTION");
				gzpmc = gzpmc.replaceAll("%(?![0-9a-fA-F]{2})", "%25");// 对百分号特殊处理
				root.element("gzpmc").setText(gzpmc);

				// 故障品序号/批次号
				String sqnorbatch = StringUtil.isStrEmpty(workorder
						.getString("FAULTCOMPONENTSQN")) ? workorder
						.getString("FAULTCOMPLOTNUM") : workorder
						.getString("FAULTCOMPONENTSQN");
				root.element("gzpxh").setText(sqnorbatch);

				// 生产单位
				String producter = workorder
						.getString("FAULTCOMPITEMNUM.ITEMPOTYPE");
				if (StringUtil.isStrNotEmpty(producter)) {
					if ("自制件".equals(producter)) {
						producter = "本单位自制";
					} else if ("外购件".equals(producter)) {
						producter = "外购";
					} else if ("外协件".equals(producter)) {
						producter = "外协";
					} else if ("三菱插件".equals(producter)) {
						producter = "三菱件";
					}

					root.element("scdw").setText(producter);
				}

				// 故障品位置(故障品位置号)
				root.element("gzpwz").setText(
						StringUtil.isStrEmpty(workorder
								.getString("FAULTCOMPASSET.RNUM")) ? "NA"
								: workorder.getString("FAULTCOMPASSET.RNUM"));

				// 故障品处置方式
				root.element("gzpczfs").setText(
						workorder.getString("FAULTCOMPDEALMODE"));

				// QMS_ID——使用 故障工单编号
				root.element("QMS_ID").setText(failureordernum);

				// 故障件上级
				IJpoSet superiorSet = workorder.getJpoSet("SUPERIORASSET");
				if (superiorSet != null
						&& superiorSet.count(GWConstant.P_COUNT_AFTERSAVE) > 0) {
					for (int idx = 0; idx < superiorSet.count(); idx++) {
						IJpo superior = superiorSet.getJpo(idx);
						String itemnum = superior.getString("ITEMNUM");// 物料编码
						String sqnorbatchnum = superior
								.getString("SQNORBATCHNUM");// 序列号
						String faultName = superior
								.getString("SYS_ITEM.DESCRIPTION");// 物料描述
						faultName = faultName.replaceAll("%(?![0-9a-fA-F]{2})",
								"%25");// 对百分号特殊处理
						int level = superior.getInt("partlevel");// 级别

						switch (level) {
						case 1:
							root.element("yjlbjbh").setText(itemnum);
							root.element("yjlbjxlh").setText(sqnorbatchnum);
							root.element("ssyjlbj").setText(faultName);
							break;
						case 2:
							root.element("ejlbjbh").setText(itemnum);
							root.element("ejlbjxlh").setText(sqnorbatchnum);
							root.element("ssejlbj").setText(faultName);
							break;
						case 3:
							root.element("sjlbjbh").setText(itemnum);
							root.element("sjlbjxlh").setText(sqnorbatchnum);
							root.element("sssjlbj").setText(faultName);
							break;
						case 4:
							root.element("fjlbjbh").setText(itemnum);
							root.element("fjlbjxlh").setText(sqnorbatchnum);
							root.element("ssfjlbj").setText(faultName);
							break;
						case 5:
							root.element("wjlbjbh").setText(itemnum);
							root.element("wjlbjxlh").setText(sqnorbatchnum);
							root.element("sswjlbj").setText(faultName);
							break;
						}
					}
				}

				// 必填校验
				Map<String, String> checkers3 = QMSFieldCheck();
				List<Element> list = root.elements();
				for (Element e : list) {
					if (checkers3.containsKey(e.getName())) {
						if (e.getText() == null || e.getText().isEmpty()) {
							throw new MroException("", "发送失败，QMS必填字段:["
									+ checkers3.get(e.getName()) + "]为空");
						}
						checkers3.remove(e.getName());
					}
				}
				if (!checkers3.isEmpty()) {
					throw new MroException("", "发送失败，QMS必填字段:["
							+ checkers3.values() + "]为空");
				}
				try {
					num = IFUtil.addIfHistory("QMS_MRO_DATA", doc.asXML(),
							IFUtil.TYPE_OUTPUT);// 接口管理中间表

					ComZzsQmsDqXcgzMappingServiceStub service = new ComZzsQmsDqXcgzMappingServiceStub();
					// 认证代码 start
					Authenticator auth = new Authenticator();
					String user = IFUtil.getIfServiceInfo("qms.user");
					String pwd = IFUtil.getIfServiceInfo("qms.pwd");
					auth.setUsername(user);
					auth.setPassword(pwd);
					service._getServiceClient().getOptions()
							.setProperty(HTTPConstants.AUTHENTICATE, auth);
					service._getServiceClient().getOptions()
							.setTimeOutInMilliSeconds(600000L);

					GetDataFormMRO fetDataFormMRO = new GetDataFormMRO();
					fetDataFormMRO.setIn0(StringEscapeUtils.unescapeXml(doc
							.asXML()));
					GetDataFormMROResponse res = service
							.getDataFormMRO(fetDataFormMRO);

					result = res.getOut();
				} catch (Exception e) {
					IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO, e.getMessage());
					e.printStackTrace();
					throw new MroException(e.getMessage());
				}
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_NO, failureordernum + "数据发送成功");

				String returnnum = IFUtil.addIfHistory("QMS_MRO_DATA", result,
						IFUtil.TYPE_INPUT);
				try {
					Document resdoc = DocumentHelper.parseText(result);
					Element resroot = resdoc.getRootElement();
					// 请求返回状态
					if (resroot.element("state").getTextTrim().equals("false")) {
						IFUtil.updateIfHistory(returnnum,
								IFUtil.STATUS_FAILURE, IFUtil.FLAG_NO, resroot
										.element("mesg").getTextTrim());
						throw new MroException("", resroot.element("mesg")
								.getTextTrim());
					} else {// 成功
						// 输出
						IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
								IFUtil.FLAG_YES, failureordernum + "数据发送成功");
						// 输入
						IFUtil.updateIfHistory(returnnum,
								IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
								failureordernum
										+ resroot.element("mesg").getTextTrim());

						qmsNums += resroot.element("bdbh").getTextTrim() + ",";
					}
				} catch (DocumentException e) {
					IFUtil.updateIfHistory(returnnum, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO, e.getMessage());
					e.printStackTrace();
				}

			}
		}
		if (StringUtil.isStrNotEmpty(qmsNums) && qmsNums.endsWith(",")) {// 去除末尾逗号
			qmsNums = qmsNums.substring(0, qmsNums.length() - 1);
		}
		if (StringUtil.isStrNotEmpty(qmsNums)) {
			// 设置工单中QMS编号
			workorder.setValue("QMS_NUM", qmsNums, GWConstant.P_NOVALIDATION);
		}
	}

	/**
	 * 
	 * 故障工单-获取TMS数据
	 * 
	 * @param workorder
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void getTMSData(IJpo workorder) throws MroException {

		String loginnum = "";
		String datanum = "";
		String returnDatanum = "";
		// 工单编号
		String orderNum = workorder.getString("ORDERNUM");
		try {

			String loginUrl = IFUtil.getIfServiceInfo("bigdata.loginurl");
			Map<String, Object> loginparam = new HashMap<String, Object>();
			loginparam.put("username",
					IFUtil.getIfServiceInfo("bigdata.loginusername"));
			loginparam.put("password",
					IFUtil.getIfServiceInfo("bigdata.loginpassword"));
			IJpoSet failureSet = workorder.getJpoSet("FAILURELIB");
			// 故障记录
			IJpo failure = null;
			if (failureSet != null && failureSet.count() > 0) {
				failure = failureSet.getJpo(0);
			}

			// 车型 车号 故障时间
			String carnum = workorder.getString("CARNUM");
			String models = getTrainTypeCode(workorder
					.getString("MODELPROJECT"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss");
			Date faultDate = workorder.getDate("FAILURELIB.FAULTTIME");
			String faulttime = "";
			if (faultDate != null) {
				faulttime = sdf.format(faultDate);
			}
			// AB节
			String train_ab = "255";
			String carsectionnum = workorder
					.getString("FAILURELIB.CARSECTIONNUM");
			if (carsectionnum.equals("A")) {
				train_ab = "1";
			} else if (carsectionnum.equals("B")) {
				train_ab = "2";
			} else if (carsectionnum.equals("C")) {
				train_ab = "3";
			}

			Map<String, Object> dataparam = new HashMap<String, Object>();
			// 测试数据
			// dataparam.put("train_type", "2");
			// dataparam.put("train_num", "0100");
			// dataparam.put("train_ab", "255");
			// dataparam.put("f_time", "170816204051");

			dataparam.put("train_type", models);
			dataparam.put("train_num", carnum);
			dataparam.put("train_ab", train_ab);
			dataparam.put("f_time", faulttime);

			IJpoSet tmsdataset = MroServer.getMroServer().getJpoSet("TMSDATA",
					MroServer.getMroServer().getSystemUserServer());
			tmsdataset.setQueryWhere("FAILURENUM='"
					+ workorder.getString("FAILURELIB.FAILURENUM") + "'");
			tmsdataset.reset();
			IJpo tmsJpo;
			if (tmsdataset.count() > 0) {
				tmsJpo = tmsdataset.getJpo();
			} else {
				tmsJpo = tmsdataset.addJpo();
			}

			tmsJpo.setValue("CARNUM", carnum, GWConstant.P_NOVALIDATION);
			tmsJpo.setValue("MODELS", workorder.getString("MODELPROJECT"),
					GWConstant.P_NOVALIDATION);
			tmsJpo.setValue("ABCFLAG",
					workorder.getString("FAILURELIB.CARSECTIONNUM"),
					GWConstant.P_NOVALIDATION);
			tmsJpo.setValue("FAILURENUM",
					workorder.getString("FAILURELIB.FAILURENUM"),
					GWConstant.P_NOVALIDATION);
			tmsJpo.setValue("FAILURELOC",
					workorder.getString("FAILURELIB.FAULTLOCATION"),
					GWConstant.P_NOVALIDATION);
			tmsJpo.setValue("WEATHER",
					workorder.getString("FAILURELIB.FAILWEATHER"),
					GWConstant.P_NOVALIDATION);
			tmsJpo.setValue("ORGID", "CRRC", GWConstant.P_NOVALIDATION);
			tmsJpo.setValue("SITEID", "ELEC", GWConstant.P_NOVALIDATION);

			// 中间表处理

			loginnum = IFUtil.addIfHistory("BIGDATA_LOGIN",
					JSON.toJSONString(loginparam), IFUtil.TYPE_OUTPUT);

			// 车型编号不存在对应代码
			if (models.isEmpty()) {
				tmsJpo.setValue("STATUS", "无数据", GWConstant.P_NOVALIDATION);
				datanum = IFUtil.addIfHistory("BIGDATA_GETTMS",
						JSON.toJSONString(dataparam), IFUtil.TYPE_OUTPUT);
				IFUtil.updateIfHistory(datanum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "工单" + orderNum
								+ ":无数据，车型代号对照表同义词域中车型编号不存在对应代码");

				tmsdataset.save();
				return;
			}

			// 平台登录
			String logininfo = "";
			try {
				logininfo = HttpRequestHelper.sendPost(loginUrl,
						JSON.toJSONString(loginparam), "UTF-8");
			} catch (IOException e) {
				// 登录请求中失败
				IFUtil.updateIfHistory(loginnum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, e.getMessage());
				tmsJpo.setValue("STATUS", "连接失败", GWConstant.P_NOVALIDATION);
				e.printStackTrace();
			}
			JSONObject loginObject = JSONObject.parseObject(logininfo);

			// 登录状态判断
			if (loginObject.getString("status").equals("0")) {
				IFUtil.updateIfHistory(loginnum, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "");

				String requierUrl = IFUtil.getIfServiceInfo("bigdata.dataurl");
				Map<String, Object> requireparam = new HashMap<String, Object>();
				// 用户标识
				requireparam.put("userID", loginObject.getString("result"));
				// 请求参数
				requireparam.put("dataParameter", dataparam);

				String responseinfo = "";
				datanum = IFUtil.addIfHistory("BIGDATA_GETTMS",
						JSON.toJSONString(requireparam), IFUtil.TYPE_OUTPUT);
				try {
					// POST发送获取数据请求
					responseinfo = HttpRequestHelper.sendPost(requierUrl,
							JSON.toJSONString(requireparam), "UTF-8");
				} catch (IOException e) {
					// 数据请求中失败
					IFUtil.updateIfHistory(
							datanum,
							IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO,
							"工单" + orderNum + "数据请求失败:"
									+ JSON.toJSONString(dataparam) + " "
									+ e.getMessage());
				}
				if (responseinfo.isEmpty()) {
					// 接口访问无返回
					tmsJpo.setValue("STATUS", "连接失败");
					IFUtil.updateIfHistory(datanum, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO, "工单" + orderNum
									+ "请求无返回结果，请检查接口连通状况");
				}
				JSONObject responseObj = JSONObject.parseObject(responseinfo);
				if (responseObj != null && !responseObj.isEmpty()) {
					// 无数据
					if (responseObj.getString("stateCode").equals("21")) {
						tmsJpo.setValue("STATUS", "无数据",
								GWConstant.P_NOVALIDATION);
						IFUtil.updateIfHistory(datanum, IFUtil.STATUS_SUCCESS,
								IFUtil.FLAG_YES, "工单" + orderNum + "无数据");
					}
					// 查询成功
					else if (responseObj.getString("stateCode").equals("0")) {
						tmsJpo.setValue("STATUS", "有效",
								GWConstant.P_NOVALIDATION);
						// 数据体
						JSONObject dataObj = responseObj
								.getJSONObject("dataParameter");

						// 记录接口日志
						IFUtil.updateIfHistory(datanum, IFUtil.STATUS_SUCCESS,
								IFUtil.FLAG_YES, "工单" + orderNum + "成功");

						returnDatanum = IFUtil.addIfHistory("BIGDATA_GETTMS",
								dataObj.toString(), IFUtil.TYPE_INPUT);

						/* 特殊处理字段 */
						// A/B/C车标示
						String abc_id = dataObj.getString("abc_id");
						if (abc_id.equals("255")) {
							tmsJpo.setValue("ABCFLAG", "非重联车",
									GWConstant.P_NOVALIDATION);
						} else if (abc_id.endsWith("1")) {
							tmsJpo.setValue("ABCFLAG", "A车",
									GWConstant.P_NOVALIDATION);
						} else if (abc_id.endsWith("2")) {
							tmsJpo.setValue("ABCFLAG", "B车",
									GWConstant.P_NOVALIDATION);
						} else if (abc_id.endsWith("3")) {
							tmsJpo.setValue("ABCFLAG", "C车",
									GWConstant.P_NOVALIDATION);
						}

						// 监控状态
						if (dataObj.getInteger("monitor_status") != null) {

							String monitor_status = Integer
									.toBinaryString(dataObj
											.getInteger("monitor_status"));
							if (monitor_status.contains("1")) {
								tmsJpo.setValue("MONITORSTATUS", "有效",
										GWConstant.P_NOVALIDATION);
							} else {
								tmsJpo.setValue("MONITORSTATUS", "无效",
										GWConstant.P_NOVALIDATION);
							}
						}

						// 机车信号
						if (StringUtil.isStrNotEmpty(dataObj
								.getString("train_signal"))) {

							String[] train_signal = toBinaryList(dataObj
									.getString("train_signal").trim());
							if (train_signal[5].equals("1")) {
								// 1xxx--08--黄2
								tmsJpo.setValue("SIGNAL", "黄2",
										GWConstant.P_NOVALIDATION);
							} else {
								if (train_signal[6].equals("1")) {
									if (train_signal[7].equals("1")) {
										if (train_signal[8].equals("1")) {
											// 0111--07--绿黄
											tmsJpo.setValue("SIGNAL", "绿黄",
													GWConstant.P_NOVALIDATION);
										} else {
											// 0110--06--白
											tmsJpo.setValue("SIGNAL", "白",
													GWConstant.P_NOVALIDATION);
										}
									} else {
										if (train_signal[8].equals("1")) {
											// 0101--05--红
											tmsJpo.setValue("SIGNAL", "红",
													GWConstant.P_NOVALIDATION);
										} else {
											// 0100--04--红黄
											tmsJpo.setValue("SIGNAL", "红黄",
													GWConstant.P_NOVALIDATION);
										}
									}
								} else {
									if (train_signal[7].equals("1")) {
										if (train_signal[8].equals("1")) {
											// 0011--03--双黄
											tmsJpo.setValue("SIGNAL", "双黄",
													GWConstant.P_NOVALIDATION);
										} else {
											// 0010--02--黄
											tmsJpo.setValue("SIGNAL", "黄",
													GWConstant.P_NOVALIDATION);
										}
									} else {
										if (train_signal[8].equals("1")) {
											// 0001--01--绿
											tmsJpo.setValue("SIGNAL", "绿",
													GWConstant.P_NOVALIDATION);
										} else {
											// 0000--00--无灯
											tmsJpo.setValue("SIGNAL", "无灯",
													GWConstant.P_NOVALIDATION);
										}
									}
								}
							}
						}
						// 最高气温
						String max_temperature = dataObj
								.getString("max_temperature");
						// 最低气温
						String min_temperature = dataObj
								.getString("min_temperature");
						String temperature = "";
						if (!max_temperature.isEmpty()
								&& !min_temperature.isEmpty()) {
							temperature = min_temperature + "~"
									+ max_temperature;
						} else if (!max_temperature.isEmpty()
								|| !min_temperature.isEmpty()) {
							temperature = min_temperature.isEmpty() ? max_temperature
									: min_temperature;
						}
						tmsJpo.setValue("WEATHER", temperature,
								GWConstant.P_NOVALIDATION);

						// 定速状态
						if (StringUtil.isStrNotEmpty(dataObj
								.getString("lim_speed_s"))) {

							String[] lim_speed_s = toBinaryList(dataObj
									.getString("lim_speed_s"));
							if (lim_speed_s[7].equals("1")) {
								tmsJpo.setValue("SPEEDPERMIT", temperature,
										GWConstant.P_NOVALIDATION);
							}
							if (lim_speed_s[6].equals("1")) {
								tmsJpo.setValue("SPEEDACTIVATE", temperature,
										GWConstant.P_NOVALIDATION);
							}
						}

						// 本/补、客/货 ==担当
						if (StringUtil.isStrNotEmpty(dataObj
								.getString("train_fun"))) {

							String[] train_fun = toBinaryList(dataObj
									.getString("train_fun"));
							StringBuffer bbkh = new StringBuffer();
							if (train_fun[8].equals("0")) {
								bbkh.append("货运");
							} else {
								bbkh.append("客运");
							}
							if (train_fun[7].equals("0")) {
								bbkh.append("本务");
							} else {
								bbkh.append("补机");
							}
							tmsJpo.setValue("BBKH", bbkh.toString(),
									GWConstant.P_NOVALIDATION);
							tmsJpo.setValue("ASSUME", bbkh.toString(),
									GWConstant.P_NOVALIDATION);
						}
						// VCM状态
						if (StringUtil.isStrNotEmpty(dataObj
								.getString("vcm_status"))) {

							String[] vcm_status = toBinaryList(dataObj
									.getString("vcm_status"));
							// VCM1状态
							String vcm1status = "";
							if (vcm_status[8].equals("1")) {
								vcm1status = "主";
							} else if (vcm_status[7].equals("1")) {
								vcm1status = "从";
							} else if (vcm_status[6].equals("1")) {
								vcm1status = "断开";
							} else {
								vcm1status = "未知";
							}
							tmsJpo.setValue("VCM1STATUS", vcm1status,
									GWConstant.P_NOVALIDATION);

							// VCM2状态
							String vcm2status = "";
							if (vcm_status[5].equals("1")) {
								vcm2status = "主";
							} else if (vcm_status[4].equals("1")) {
								vcm2status = "从";
							} else if (vcm_status[3].equals("1")) {
								vcm2status = "断开";
							} else {
								vcm2status = "未知";
							}
							tmsJpo.setValue("VCM2STATUS", vcm2status,
									GWConstant.P_NOVALIDATION);
						}
						// 机车工况
						if (StringUtil.isStrNotEmpty(dataObj
								.getString("train_op_mode"))) {

							String[] train_op_mode = toBinaryList(dataObj
									.getString("train_op_mode"));
							if (train_op_mode[8].equals("1")) {
								tmsJpo.setValue("TRAINCONDITION", "零位",
										GWConstant.P_NOVALIDATION);
							} else if (train_op_mode[7].equals("1")) {
								tmsJpo.setValue("TRAINCONDITION", "向后",
										GWConstant.P_NOVALIDATION);
							} else if (train_op_mode[6].equals("1")) {
								tmsJpo.setValue("TRAINCONDITION", "向前",
										GWConstant.P_NOVALIDATION);
							} else if (train_op_mode[5].equals("1")) {
								tmsJpo.setValue("TRAINCONDITION", "制动",
										GWConstant.P_NOVALIDATION);
							}
						}
						/* 以下是直接入库的字段 */
						// 辅助变流器1输出频率实际值
						String ccu1_real = dataObj.getString("ccu1_real");
						tmsJpo.setValue("AUX1OUTPUTACTFREQ", ccu1_real,
								GWConstant.P_NOVALIDATION);
						// 辅助变流器1输出频率设定值
						String ccu1_set = dataObj.getString("ccu1_set");
						tmsJpo.setValue("AUX1OUTPUTSETTINGFREQ", ccu1_set,
								GWConstant.P_NOVALIDATION);
						// 辅助变流器2输出频率实际值
						String ccu2_real = dataObj.getString("ccu2_real");
						tmsJpo.setValue("AUX2OUTPUTACTFREQ", ccu2_real,
								GWConstant.P_NOVALIDATION);
						// 辅助变流器2输出频率设定值
						String ccu2_set = dataObj.getString("ccu2_set");
						tmsJpo.setValue("AUX2OUTPUTSETTINGFREQ", ccu2_set,
								GWConstant.P_NOVALIDATION);
						// 环温1
						String en_temp1 = dataObj.getString("en_temp1");
						tmsJpo.setValue("CIRCTEMP1", en_temp1,
								GWConstant.P_NOVALIDATION);
						// 环温2
						String en_temp2 = dataObj.getString("en_temp2");
						tmsJpo.setValue("CIRCTEMP2", en_temp2,
								GWConstant.P_NOVALIDATION);
						// 主控ERM软件版本
						String erm_soft_ver = dataObj.getString("erm_soft_ver");
						tmsJpo.setValue("MCERMSOFTWAREVERSION", erm_soft_ver,
								GWConstant.P_NOVALIDATION);
						// 主控GWM软件版本
						String gwm_soft_ver = dataObj.getString("gwm_soft_ver");
						tmsJpo.setValue("MCGWMSOFTWAREVERSION", gwm_soft_ver,
								GWConstant.P_NOVALIDATION);
						// IDU1软件版本
						String idu1_soft_ver = dataObj
								.getString("idu1_soft_ver");
						tmsJpo.setValue("IDU1SOFTWAREVERSION", idu1_soft_ver,
								GWConstant.P_NOVALIDATION);
						// IDU2软件版本
						String idu2_soft_ver = dataObj
								.getString("idu2_soft_ver");
						tmsJpo.setValue("IDU2SOFTWAREVERSION", idu2_soft_ver,
								GWConstant.P_NOVALIDATION);
						// 计长
						String ji_chang = dataObj.getString("ji_chang");
						tmsJpo.setValue("MEASURELENGTH", ji_chang,
								GWConstant.P_NOVALIDATION);
						// 公里标
						String kilometer = dataObj.getString("kilometer");
						tmsJpo.setValue("KILOMETERSFLAG", kilometer,
								GWConstant.P_NOVALIDATION);
						// 辆数
						String liang_shu = dataObj.getString("liang_shu");
						tmsJpo.setValue("VEHICLESQTY", liang_shu,
								GWConstant.P_NOVALIDATION);
						// 监控时间
						String monitor_time = dataObj.getString("monitor_time");
						if ((!"000000000000".equals(monitor_time))
								&& StringUtil.isStrNotEmpty(monitor_time)) {
							tmsJpo.setValue("MONITORTIME",
									sdf.parse(monitor_time),
									GWConstant.P_NOVALIDATION);
						}
						// 运行里程
						Float odometer = dataObj.getFloat("odometer");
						if (odometer != null) {
							tmsJpo.setValue("RUNLENGTH", odometer,
									GWConstant.P_NOVALIDATION);
						}
						// 实际交路号
						String real_cross = dataObj.getString("real_cross");
						tmsJpo.setValue("ACTTRFCROADNUM", real_cross,
								GWConstant.P_NOVALIDATION);
						// CCU实际速度
						String real_speed = dataObj.getString("real_speed");
						tmsJpo.setValue("CCUSPEED", real_speed,
								GWConstant.P_NOVALIDATION);
						// TCU1固件版本
						String tcu1_firm_ver = dataObj
								.getString("tcu1_firm_ver");
						tmsJpo.setValue("TCUFIRMWAREVERSION", tcu1_firm_ver,
								GWConstant.P_NOVALIDATION);
						// TCU1硬件版本
						String tcu1_hard_ver = dataObj
								.getString("tcu1_hard_ver");
						tmsJpo.setValue("TCUHARDWAREVERSION", tcu1_hard_ver,
								GWConstant.P_NOVALIDATION);
						// TCU1软件版本
						String tcu1_soft_ver = dataObj
								.getString("tcu1_soft_ver");
						tmsJpo.setValue("TCUSOFTWAREVERSION", tcu1_soft_ver,
								GWConstant.P_NOVALIDATION);
						// TCU1实时时钟
						String tcu1_time = dataObj.getString("tcu1_time");
						if ((!"000000000000".equals(tcu1_time))
								&& StringUtil.isStrNotEmpty(tcu1_time)) {
							tmsJpo.setValue("TCU1TIME", sdf.parse(tcu1_time),
									GWConstant.P_NOVALIDATION);
						}
						// TCU1第一轴轮径设定值（mm）
						String tcu1_whee_set1 = dataObj
								.getString("tcu1_whee_set1");
						tmsJpo.setValue("FIRSTDIAMSETVAL", tcu1_whee_set1,
								GWConstant.P_NOVALIDATION);
						// TCU1第二轴轮径设定值（mm）
						String tcu1_whee_set2 = dataObj
								.getString("tcu1_whee_set2");
						tmsJpo.setValue("SECONDDIAMSETVAL", tcu1_whee_set2,
								GWConstant.P_NOVALIDATION);
						// TCU1第三轴轮径设定值（mm）
						String tcu1_whee_set3 = dataObj
								.getString("tcu1_whee_set3");
						tmsJpo.setValue("THIRDDIAMSETVAL", tcu1_whee_set3,
								GWConstant.P_NOVALIDATION);
						// TCU1原边回馈电流（A）
						String tcu1_yb_cu = dataObj.getString("tcu1_yb_cu");
						tmsJpo.setValue("FEEDBACKCURRENT", tcu1_yb_cu,
								GWConstant.P_NOVALIDATION);
						// TCU2固件版本
						String tcu2_firm_ver = dataObj
								.getString("tcu2_firm_ver");
						tmsJpo.setValue("TCU2FIRMWAREVERSION", tcu2_firm_ver,
								GWConstant.P_NOVALIDATION);
						// TCU2硬件版本
						String tcu2_hard_ver = dataObj
								.getString("tcu2_hard_ver");
						tmsJpo.setValue("TCU2HARDWAREVERSION", tcu2_hard_ver,
								GWConstant.P_NOVALIDATION);
						// TCU2软件版本
						String tcu2_soft_ver = dataObj
								.getString("tcu2_soft_ver");
						tmsJpo.setValue("TCU2SOFTWAREVERSION", tcu2_soft_ver,
								GWConstant.P_NOVALIDATION);
						// TCU2实时时钟
						String tcu2_time = dataObj.getString("tcu2_time");
						if ((!"000000000000".equals(tcu2_time))
								&& StringUtil.isStrNotEmpty(tcu2_time)) {
							tmsJpo.setValue("TCU2TIME", sdf.parse(tcu2_time),
									GWConstant.P_NOVALIDATION);
						}
						// TCU2第一轴轮径设定值（mm）
						String tcu2_whee_set1 = dataObj
								.getString("tcu2_whee_set1");
						tmsJpo.setValue("FIRSTDIAMSETVAL2", tcu2_whee_set1,
								GWConstant.P_NOVALIDATION);
						// TCU2第二轴轮径设定值（mm）
						String tcu2_whee_set2 = dataObj
								.getString("tcu2_whee_set2");
						tmsJpo.setValue("SECONDDIAMSETVAL2", tcu2_whee_set2,
								GWConstant.P_NOVALIDATION);
						// TCU2第三轴轮径设定值（mm）
						String tcu2_whee_set3 = dataObj
								.getString("tcu2_whee_set3");
						tmsJpo.setValue("THIRDDIAMSETVAL2", tcu2_whee_set3,
								GWConstant.P_NOVALIDATION);
						// TCU2原边回馈电流（A）
						String tcu2_yb_cu = dataObj.getString("tcu2_yb_cu");
						tmsJpo.setValue("FEEDBACKCURRENT2", tcu2_yb_cu,
								GWConstant.P_NOVALIDATION);
						// 总重==牵引吨位
						String total_weight = dataObj.getString("total_weight");
						tmsJpo.setValue("WEIGHT", total_weight,
								GWConstant.P_NOVALIDATION);
						tmsJpo.setValue("TONNAGE", total_weight,
								GWConstant.P_NOVALIDATION);
						// 车次标识符
						String train_id = dataObj.getString("train_id");
						tmsJpo.setValue("TRAINLINE", train_id,
								GWConstant.P_NOVALIDATION);
						// 机车当前级位
						String train_level = dataObj.getString("train_level");
						tmsJpo.setValue("TRAINLEVEL", train_level,
								GWConstant.P_NOVALIDATION);
						// 车号
						String train_num = dataObj.getString("train_num");
						tmsJpo.setValue("CARNUM", train_num,
								GWConstant.P_NOVALIDATION);
						// 机车实际速度
						String train_real_speed = dataObj
								.getString("train_real_speed");
						tmsJpo.setValue("TRAINSPPED", train_real_speed,
								GWConstant.P_NOVALIDATION);
						// 机车设定速度
						String train_set_speed = dataObj
								.getString("train_set_speed");
						tmsJpo.setValue("DEFAULTSPEED", train_set_speed,
								GWConstant.P_NOVALIDATION);
						// 车型
						String train_type = dataObj.getString("train_type");
						tmsJpo.setValue("MODELS", train_type,
								GWConstant.P_NOVALIDATION);
						// 列车管压力（kPa）
						String tube_press = dataObj.getString("tube_press");
						tmsJpo.setValue("PIPEPRESSURE", tube_press,
								GWConstant.P_NOVALIDATION);
						// 主控VCM1软件版本
						String vcm1_soft_ver = dataObj
								.getString("vcm1_soft_ver");
						tmsJpo.setValue("MCVCM1SOFTWAREVERSION", vcm1_soft_ver,
								GWConstant.P_NOVALIDATION);
						// 主控VCM2软件版本
						String vcm2_soft_ver = dataObj
								.getString("vcm2_soft_ver");
						tmsJpo.setValue("MCVCM2SOFTWAREVERSION", vcm2_soft_ver,
								GWConstant.P_NOVALIDATION);
						// 天气情况
						String weather = dataObj.getString("weather");
						if (StringUtil.isStrNotEmpty(weather)) {
							tmsJpo.setValue("WEATHER", weather,
									GWConstant.P_NOVALIDATION);
						}
						// CCU时间
						String date_time = dataObj.getString("date_time");
						if ((!"000000000000".equals(date_time))
								&& StringUtil.isStrNotEmpty(date_time)) {
							tmsJpo.setValue("CCUTIME", sdf.parse(date_time),
									GWConstant.P_NOVALIDATION);
						}
						/* 海拔、供电区间、路况接口无返回 */

						/* 向父表FAILURELIB回填 */
						/* 供电区间、发生地点、路况接口未返回无法回填 */
						if (failure != null) {
							// 纬度的数据部
							String latitude = dataObj.getString("latitude");
							failure.setValue("LATITUDE", latitude,
									GWConstant.P_NOVALIDATION);
							// 经度的数据部
							String longitude = dataObj.getString("longitude");
							failure.setValue("LONGITUDE", longitude,
									GWConstant.P_NOVALIDATION);
							// 牵引负载吨位
							if (StringUtil.isStrNotEmpty(total_weight)) {
								failure.setValue("QYFZDW", total_weight,
										GWConstant.P_NOVALIDATION);
							}
							// 天气
							if (StringUtil.isStrNotEmpty(weather)) {
								failure.setValue("FAILWEATHER", weather,
										GWConstant.P_NOVALIDATION);
							}
						}

						/* 暂时无用 */
						// 风向--------!
						// String wind_direction =
						// dataObj.getString("wind_direction");
						// 风力--------!
						// String wind_power = dataObj.getString("wind_power");

						IFUtil.updateIfHistory(returnDatanum,
								IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "工单"
										+ orderNum + "接收数据成功！");

					} else {
						// 获取数据失败
						tmsJpo.setValue("STATUS", "连接失败");
						IFUtil.updateIfHistory(
								datanum,
								IFUtil.STATUS_FAILURE,
								IFUtil.FLAG_NO,
								"工单" + orderNum + "请求参数："
										+ JSON.toJSONString(requireparam)
										+ "\n返回结果："
										+ responseObj.toJSONString());
					}
				}
			} else {
				// 登录失败
				tmsJpo.setValue("STATUS", "连接失败");
				IFUtil.updateIfHistory(loginnum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, logininfo);
			}

			tmsdataset.save();
		} catch (MroException e) {
			e.printStackTrace();
			IFUtil.updateIfHistory(returnDatanum, IFUtil.STATUS_FAILURE,
					IFUtil.FLAG_NO,
					"工单" + orderNum + "数据写入失败,错误信息：" + e.getMessage());
		} catch (ParseException e1) {
			e1.printStackTrace();
			IFUtil.updateIfHistory(returnDatanum, IFUtil.STATUS_FAILURE,
					IFUtil.FLAG_NO,
					"工单" + orderNum + "数据写入失败,错误信息：" + e1.getMessage());
		}
	}

	/**
	 * 
	 * 根据车型获取车型代码
	 * 
	 * @param trainName
	 *            车型
	 * 
	 * @return 车型代码
	 * 
	 */
	private static String getTrainTypeCode(String trainName) {
		if (trainName.isEmpty()) {
			return "";
		}
		String traincode = MroServer.getMroServer().getSysDomainCache()
				.getInnerValue("TRAINCODE", trainName);
		if (traincode == null) {
			return "";
		}
		return traincode;
	}

	/**
	 * 
	 * 数字转为二进制存入数组
	 * 
	 * @param num
	 *            数字
	 * @return 8位二进制的数组
	 * 
	 */
	private static String[] toBinaryList(String num) {
		String text = Integer.toBinaryString(Integer.parseInt(num));
		text = "00000000" + text;
		text = text.substring(text.length() - 8, text.length());
		String[] bin = text.split("");
		return bin;
	}

	/**
	 * 
	 * 根据工单数据创建三包订单，返回三包订单号
	 * 
	 * @param jpo
	 *            故障工单
	 * @return
	 * @throws RemoteException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static String[] msg1(IJpo jpo) throws RemoteException, MroException {
		// 工单编号
		String orderNum = jpo.getString("ordrenum");
		// 判断是否已经生成过三包订单
		if (StringUtil.isStrNotEmpty(isSendToERP(orderNum))) {
			String[] msg = new String[2];
			msg[0] = isSendToERP(orderNum);
			msg[1] = "成功";
			return msg;
		}
		String lognum = "";

		ComZzsErpZTFUN_SD_ZSYW_WEBStub service = new ComZzsErpZTFUN_SD_ZSYW_WEBStub();
		Authenticator auth = new Authenticator();
		String user = IFUtil.getIfServiceInfo("erp.user");
		String pwd = IFUtil.getIfServiceInfo("erp.pwd");
		auth.setUsername(user);
		auth.setPassword(pwd);
		service._getServiceClient().getOptions()
				.setProperty(HTTPConstants.AUTHENTICATE, auth);
		ZsdZsywHeader params = new ZsdZsywHeader();// 表头

		Char4 AUART = new Char4();
		AUART.setChar4("ZSYW");
		params.setAuart(AUART);// 订单类型--固定值

		Char4 VKORG = new Char4();
		VKORG.setChar4("1040");// 销售组织--固定值
		params.setVkorg(VKORG);

		Char2 VTWEG = new Char2();// 分销渠道--固定值
		VTWEG.setChar2("10");
		params.setVtweg(VTWEG);

		Char2 SPART = new Char2();// 产品组--固定值
		SPART.setChar2("10");
		params.setSpart(SPART);

		String VKBUR10 = jpo.getString("SALES");// 销售方
		if (StringUtil.isStrEmpty(VKBUR10)) {
			throw new MroException("必填字段：销售部门 为空，请检查！");
		}
		Char4 VKBUR = new Char4();
		VKBUR.setChar4(VKBUR10);
		params.setVkbur(VKBUR);

		String KUNNR10 = jpo.getString("SERVCOMPANY");// 客户
		if (StringUtil.isStrEmpty(KUNNR10)) {
			throw new MroException("必填字段：客户代码 为空，请检查！");
		}
		Char10 KUNNR = new Char10();
		// KUNNR.setChar10("100416");
		KUNNR.setChar10(KUNNR10);
		params.setKunnr(KUNNR);

		Char10 KUNNR1 = new Char10();
		// KUNNR1.setChar10("100416");
		KUNNR1.setChar10(KUNNR10);
		params.setKunnr2(KUNNR1);

		String BSTKD1 = jpo.getString("ORDERNUM");// 工单号
		if (StringUtil.isStrEmpty(BSTKD1)) {
			throw new MroException("必填字段：工单号 为空，请检查！");
		}
		Char35 BSTKD = new Char35();
		BSTKD.setChar35(BSTKD1);
		params.setBstkd(BSTKD);

		String ERNAM1 = jpo.getString("REPORTER.DISPLAYNAME");// 创建人
		Char12 ERNAM = new Char12();
		ERNAM.setChar12(ERNAM1);
		params.setErnam(ERNAM);

		Date AUDAT1 = MroServer.getMroServer().getDate();// 单据日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String AUDATstr = sdf.format(AUDAT1);

		ComZzsErpZTFUN_SD_ZSYW_WEBStub.Date AUDAT = new ComZzsErpZTFUN_SD_ZSYW_WEBStub.Date();
		AUDAT.setDate(AUDATstr);
		params.setAudat(AUDAT);

		TableOfZsdZsywItems tableofzsdzsywitems = new TableOfZsdZsywItems();// table
		IJpoSet exchangeSet = jpo.getJpoSet("EXCHANGERECORD");
		int rowno = 10;
		if (!exchangeSet.isEmpty()) {
			for (int i = 0; i < exchangeSet.count(); i++) {
				IJpo exchangerecord = exchangeSet.getJpo(i);

				// 当上下车使用客户料时不创建三包订单
				if (exchangerecord.getBoolean("ISCUSTITEM")) {
					continue;
				}
				// 现场留用观察不用创建三包订单
				if (SddqConstant.FAIL_DEALMODE_RETENTION.equals(exchangerecord
						.getString("dealmode"))) {
					continue;
				}
				// 设置行号
				exchangerecord.setValue("rowno", rowno,
						GWConstant.P_NOVALIDATION);
				rowno += 20;

				// 循环2次，一上一下
				for (int j = 0; j < 2; j++) {
					ZsdZsywItems zsdzsywitems = new ZsdZsywItems();// 行

					double KWMENG1 = 1.000;// 数量
					Quantum153 KWMENG = new Quantum153();
					BigDecimal lmnga1 = new BigDecimal(KWMENG1);
					KWMENG.setQuantum153(lmnga1);
					zsdzsywitems.setKwmeng(KWMENG);

					// 项目工作令号--内部项目号
					String AUFNR1 = jpo.getString("PROJECTINFO.WORKORDERNUM");
					if (StringUtil.isStrEmpty(AUFNR1)) {
						throw new MroException("必填字段： 项目工作令号 为空，请检查！");
					}
					Char12 AUFNR = new Char12();
					// AUFNR.setChar12("P12000000206");
					AUFNR.setChar12(AUFNR1);
					zsdzsywitems.setAufnr(AUFNR);

					zsdzsywitems.setEtdat(AUDAT);// 单据日期

					// 交货工厂--固定值
					Char4 WERKS = new Char4();
					WERKS.setChar4("1020");
					zsdzsywitems.setWerks(WERKS);
					// 上车
					if (j == 0) {
						String MABNR1 = exchangerecord.getString("NEWITEMNUM");// 物料号
						if (StringUtil.isStrEmpty(MABNR1)) {
							throw new MroException("必填字段：上下车记录上车件物料编码 为空，请检查！");
						}
						Char18 MABNR = new Char18();
						// MABNR.setChar18("W200000113");
						MABNR.setChar18(MABNR1);
						zsdzsywitems.setMatnr(MABNR);

						// 项目类别--zywt-上车;zywr-下车
						Char4 PSTYV = new Char4();
						PSTYV.setChar4("ZYWT");
						zsdzsywitems.setPstyv(PSTYV);

						String LGORT1 = exchangerecord
								.getString("NEWLOC.STOREROOMPARENT");// 库房--需要用中心库的
						if (exchangerecord.isNull("NEWLOC.STOREROOMPARENT")) {
							LGORT1 = jpo.getString("NEWLOC");
						}
						if (StringUtil.isStrEmpty(LGORT1)) {
							throw new MroException("必填字段：上下车上车件库房 为空，请检查！");
						}
						Char4 LGORT = new Char4();
						// LGORT.setChar4("Y586");
						LGORT.setChar4(LGORT1);
						zsdzsywitems.setLgort(LGORT);

						// Char1 PRGBZ = new Char1();// 日期格式--固定值
						// PRGBZ.setChar1("D");

						String WLZ1 = exchangerecord.getString("NEWWLZ1");
						Char3 MVGR1 = new Char3();
						MVGR1.setChar3(WLZ1);
						zsdzsywitems.setMvgr1(MVGR1);// 物料组1

						String WLZ4 = exchangerecord.getString("NEWWLZ4");
						Char3 MVGR4 = new Char3();
						MVGR4.setChar3(WLZ4);
						zsdzsywitems.setMvgr4(MVGR4);// 物料组4
					} else {
						// 下车
						String MABNR1 = exchangerecord.getString("ITEMNUM");// 物料号
						if (StringUtil.isStrEmpty(MABNR1)) {
							throw new MroException("必填字段：上下车下车件物料编码 为空，请检查！");
						}
						Char18 MABNR = new Char18();
						// MABNR.setChar18("W200000113");
						MABNR.setChar18(MABNR1);
						zsdzsywitems.setMatnr(MABNR);

						// 项目类别--zywt-上车;zywr-下车
						Char4 PSTYV = new Char4();
						PSTYV.setChar4("ZYWR");
						zsdzsywitems.setPstyv(PSTYV);

						String LGORT1 = exchangerecord
								.getString("LOCATION.STOREROOMPARENT");// 库房--需要用中心库的
						if (exchangerecord.isNull("LOCATION.STOREROOMPARENT")) {
							LGORT1 = jpo.getString("LOCATION");
						}
						if (StringUtil.isStrEmpty(LGORT1)) {
							throw new MroException("必填字段：上下车记录下车件库房 为空，请检查！");
						}

						Char4 LGORT = new Char4();
						// LGORT.setChar4("Y586");
						LGORT.setChar4(LGORT1);
						zsdzsywitems.setLgort(LGORT);

						String WLZ1 = exchangerecord.getString("WLZ1");
						Char3 MVGR1 = new Char3();
						MVGR1.setChar3(WLZ1);
						zsdzsywitems.setMvgr1(MVGR1);// 物料组1

						String WLZ4 = exchangerecord.getString("WLZ4");
						Char3 MVGR4 = new Char3();
						MVGR4.setChar3(WLZ4);
						zsdzsywitems.setMvgr4(MVGR4);// 物料组4
					}
					tableofzsdzsywitems.addItem(zsdzsywitems);
				}
			}// end of exchangeSet
		}

		// 耗损件也需要生成三包订单，但不是必须
		IJpoSet losspartSet = jpo.getJpoSet("JXTASKLOSSPART");
		if (!losspartSet.isEmpty()) {
			for (int i = 0; i < losspartSet.count(); i++) {
				IJpo losspart = losspartSet.getJpo(i);
				// 使用客户料不创建三包
				if (losspart.getBoolean("ISCUSTITEM")) {
					continue;
				}
				// 现场留用观察不用创建三包订单
				if (SddqConstant.FAIL_DEALMODE_RETENTION.equals(losspart
						.getString("dealmode"))) {
					continue;
				}
				// 设置行号
				losspart.setValue("rowno", rowno, GWConstant.P_NOVALIDATION);
				rowno += 20;

				// 循环2次，一上一下
				for (int j = 0; j < 2; j++) {
					ZsdZsywItems zsdzsywitems = new ZsdZsywItems();// 行

					double KWMENG1 = losspart.getDouble("AMOUNT");// 上车数量
					if (KWMENG1 == 0.0) {
						throw new MroException("必填字段： 耗损件上车数量 为空，请检查！");
					}
					Quantum153 KWMENG = new Quantum153();
					BigDecimal lmnga1 = new BigDecimal(KWMENG1);
					KWMENG.setQuantum153(lmnga1);
					zsdzsywitems.setKwmeng(KWMENG);

					// 项目工作令号--内部项目号
					String AUFNR1 = jpo.getString("PROJECTINFO.WORKORDERNUM");
					if (StringUtil.isStrEmpty(AUFNR1)) {
						throw new MroException("必填字段： 项目工作令号 为空，请检查！");
					}
					Char12 AUFNR = new Char12();
					AUFNR.setChar12(AUFNR1);
					zsdzsywitems.setAufnr(AUFNR);

					zsdzsywitems.setEtdat(AUDAT);// 单据日期

					// 交货工厂--固定值
					Char4 WERKS = new Char4();
					WERKS.setChar4("1020");
					zsdzsywitems.setWerks(WERKS);
					// 上车
					if (j == 0) {
						String MABNR1 = losspart.getString("ITEMNUM");// 物料号
						if (StringUtil.isStrEmpty(MABNR1)) {
							throw new MroException("必填字段：耗损件上车物料编码 为空，请检查！");
						}
						Char18 MABNR = new Char18();
						MABNR.setChar18(MABNR1);
						zsdzsywitems.setMatnr(MABNR);

						// 项目类别--zywt-上车;zywr-下车
						Char4 PSTYV = new Char4();
						PSTYV.setChar4("ZYWT");
						zsdzsywitems.setPstyv(PSTYV);

						String LGORT1 = losspart
								.getString("UPLOC.STOREROOMPARENT");// 库房--需要用中心库的
						if (losspart.isNull("UPLOC.STOREROOMPARENT")) {
							LGORT1 = jpo.getString("UPLOC");
						}
						if (StringUtil.isStrEmpty(LGORT1)) {
							throw new MroException("必填字段：耗损件上车库房 为空，请检查！");
						}
						Char4 LGORT = new Char4();
						LGORT.setChar4(LGORT1);
						zsdzsywitems.setLgort(LGORT);

						// Char1 PRGBZ = new Char1();// 日期格式--固定值
						// PRGBZ.setChar1("D");

						String WLZ1 = losspart.getString("NEWWLZ1");
						Char3 MVGR1 = new Char3();
						MVGR1.setChar3(WLZ1);
						zsdzsywitems.setMvgr1(MVGR1);// 物料组1

						String WLZ4 = losspart.getString("NEWWLZ4");
						Char3 MVGR4 = new Char3();
						MVGR4.setChar3(WLZ4);
						zsdzsywitems.setMvgr4(MVGR4);// 物料组4
					} else {
						// 下车
						String MABNR1 = losspart.getString("DOWNITEMNUM");// 物料号
						if (StringUtil.isStrEmpty(MABNR1)) {
							throw new MroException("必填字段：耗损件下车物料编码 为空，请检查！");
						}
						Char18 MABNR = new Char18();
						MABNR.setChar18(MABNR1);
						zsdzsywitems.setMatnr(MABNR);

						// 项目类别--zywt-上车;zywr-下车
						Char4 PSTYV = new Char4();
						PSTYV.setChar4("ZYWR");
						zsdzsywitems.setPstyv(PSTYV);

						String LGORT1 = losspart
								.getString("UNDERLOC.STOREROOMPARENT");// 库房--需要用中心库的
						if (losspart.isNull("UNDERLOC.STOREROOMPARENT")) {
							LGORT1 = jpo.getString("UNDERLOC");
						}
						if (StringUtil.isStrEmpty(LGORT1)) {
							throw new MroException("必填字段：耗损件下车库房 为空，请检查！");
						}

						Char4 LGORT = new Char4();
						LGORT.setChar4(LGORT1);
						zsdzsywitems.setLgort(LGORT);

						String WLZ1 = losspart.getString("WLZ1");
						Char3 MVGR1 = new Char3();
						MVGR1.setChar3(WLZ1);
						zsdzsywitems.setMvgr1(MVGR1);// 物料组1

						String WLZ4 = losspart.getString("WLZ4");
						Char3 MVGR4 = new Char3();
						MVGR4.setChar3(WLZ4);
						zsdzsywitems.setMvgr4(MVGR4);// 物料组4
					}
					tableofzsdzsywitems.addItem(zsdzsywitems);
				}
			}// end of losspartSet
		}

		// 检测是否需要调用创建三包接口
		if (tableofzsdzsywitems.getItem() == null
				|| tableofzsdzsywitems.getItem().length < 1) {
			String[] msg = new String[2];
			msg[0] = "不需创建三包订单";
			msg[1] = "SPECIAL";
			return msg;
		}

		ZtfunSdZsyw ztfunsdzsyw = new ZtfunSdZsyw();
		ztfunsdzsyw.setHeader(params);
		ztfunsdzsyw.setIItems(tableofzsdzsywitems);
		String[] msg = new String[2];
		try {
			lognum = IFUtil.addIfHistory("MRO_ERP_ZSYW",
					JSON.toJSONString(ztfunsdzsyw), IFUtil.TYPE_OUTPUT);// 接口管理中间表
			ZtfunSdZsywResponse response = service.ztfunSdZsyw(ztfunsdzsyw);
			String Vbeln = response.getVbeln().toString();
			msg[0] = Vbeln;// 三包订单编号
			msg[1] = response.getRmesg().toString();
			if (response.getRcode().toString().equals("E")) {
				msg[0] = response.getRmesg().toString();
				msg[1] = "失败";
				IFUtil.updateIfHistory(lognum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, response.getRmesg().toString());
			} else {
				if (StringUtil.isStrNotEmpty(Vbeln)) {

					msg[1] = "成功";
					IFUtil.updateIfHistory(lognum, IFUtil.STATUS_SUCCESS,
							IFUtil.FLAG_YES, "三包订单号：" + Vbeln + ", "
									+ response.getRmesg().toString());

				} else {

					msg[0] = response.getRmesg().toString();
					msg[1] = "失败";
					IFUtil.updateIfHistory(lognum, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO, response.getRmesg().toString());

				}
			}

		} catch (Exception e) {
			IFUtil.updateIfHistory(lognum, IFUtil.STATUS_FAILURE,
					IFUtil.FLAG_NO, e.getMessage());
			msg[0] = e.getMessage();
			msg[1] = "失败";
		}
		return msg;
	}

	/**
	 * 
	 * 创建ERP交货单并过账
	 * 
	 * @param vbeln
	 *            装运点
	 * @param zyd
	 *            三包业务订单号
	 * @return
	 * @throws RemoteException
	 *             [参数说明]
	 * @throws MroException
	 * 
	 */
	public static String[] msg2(IJpo order, String vbeln, String zyd)
			throws RemoteException, MroException {
		String lognum = "";

		String[] msg = new String[2];
		ComZzsErpZTFUN_SD_ZSYWJH_WEBStub service = new ComZzsErpZTFUN_SD_ZSYWJH_WEBStub();
		Authenticator auth = new Authenticator();
		String user = IFUtil.getIfServiceInfo("erp.user");
		String pwd = IFUtil.getIfServiceInfo("erp.pwd");
		auth.setUsername(user);
		auth.setPassword(pwd);
		service._getServiceClient().getOptions()
				.setProperty(HTTPConstants.AUTHENTICATE, auth);
		service._getServiceClient().getOptions()
				.setTimeOutInMilliSeconds(15 * 1000);

		ZsywjhHeader zsywjhheader = new ZsywjhHeader();

		// 三包业务订单号
		ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.Char10 VBELN = new ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.Char10();
		VBELN.setChar10(vbeln);
		zsywjhheader.setVbeln(VBELN);

		// 装运点
		ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.Char4 VSTEL = new ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.Char4();
		VSTEL.setChar4(zyd);
		zsywjhheader.setVstel(VSTEL);

		// 按照文档，创建者、凭证日期不需填写
		ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.Date Bldat = new ComZzsErpZTFUN_SD_ZSYWJH_WEBStub.Date();
		Date date = MroServer.getMroServer().getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String AUDATs = sdf.format(date);
		Bldat.setDate(AUDATs);
		zsywjhheader.setBldat(Bldat);

		TableOfZsywjhItem tableofzsywjhitem = new TableOfZsywjhItem();

		IJpoSet exchengeSet = order.getJpoSet("exchangerecord");
		// int rownum = 10;
		for (int i = 0; i < exchengeSet.count(); i++) {

			if (exchengeSet.getJpo(i).getBoolean("ISCUSTITEM")) {
				continue;
			}
			// 现场留用观察不用创建三包订单
			if (SddqConstant.FAIL_DEALMODE_RETENTION.equals(exchengeSet.getJpo(
					i).getString("dealmode"))) {
				continue;
			}

			ZsywjhItem zsywjhitem = new ZsywjhItem();

			// 行号
			int rownum = exchengeSet.getJpo(i).getInt("rowno");
			Numeric6 Posnr = new Numeric6();
			Posnr.setNumeric6("" + rownum);
			zsywjhitem.setPosnr(Posnr);
			// 设置MRO中行号
			// exchengeSet.getJpo(i).setValue("rowno", rownum,
			// GWConstant.P_NOVALIDATION_AND_NOACTION);
			// 交货数量
			Quantum133 Lfimg = new Quantum133();
			Lfimg.setQuantum133(new BigDecimal(1.00));
			zsywjhitem.setLfimg(Lfimg);

			// 一上一下,add两次
			tableofzsywjhitem.addItem(zsywjhitem);

			ZsywjhItem zsywjhitem2 = new ZsywjhItem();
			Numeric6 Posnr2 = new Numeric6();
			rownum += 10;
			Posnr2.setNumeric6("" + rownum);
			zsywjhitem2.setPosnr(Posnr2);
			// 交货数量
			Quantum133 Lfimg2 = new Quantum133();
			Lfimg2.setQuantum133(new BigDecimal(1.00));
			zsywjhitem2.setLfimg(Lfimg2);
			tableofzsywjhitem.addItem(zsywjhitem2);

			// rownum += 10;
		}

		// 耗损件记录
		IJpoSet losspartSet = order.getJpoSet("JXTASKLOSSPART");
		for (int i = 0; i < losspartSet.count(); i++) {

			if (losspartSet.getJpo(i).getBoolean("ISCUSTITEM")) {
				continue;
			}
			// 现场留用观察不用创建三包订单
			if (SddqConstant.FAIL_DEALMODE_RETENTION.equals(losspartSet.getJpo(
					i).getString("dealmode"))) {
				continue;
			}
			ZsywjhItem zsywjhitem = new ZsywjhItem();
			// 行号
			int rownum = losspartSet.getJpo(i).getInt("rowno");
			Numeric6 Posnr = new Numeric6();
			Posnr.setNumeric6("" + rownum);
			zsywjhitem.setPosnr(Posnr);
			// 设置MRO中行号
			// losspartSet.getJpo(i).setValue("rowno", rownum,
			// GWConstant.P_NOVALIDATION_AND_NOACTION);
			// 交货数量
			Quantum133 Lfimg = new Quantum133();
			Lfimg.setQuantum133(new BigDecimal(1.00));
			zsywjhitem.setLfimg(Lfimg);

			// 一上一下,add两次
			tableofzsywjhitem.addItem(zsywjhitem);

			ZsywjhItem zsywjhitem2 = new ZsywjhItem();
			Numeric6 Posnr2 = new Numeric6();
			rownum += 10;
			Posnr2.setNumeric6("" + rownum);
			zsywjhitem2.setPosnr(Posnr2);
			// 交货数量
			Quantum133 Lfimg2 = new Quantum133();
			Lfimg2.setQuantum133(new BigDecimal(1.00));
			zsywjhitem2.setLfimg(Lfimg2);

			tableofzsywjhitem.addItem(zsywjhitem2);

			// rownum += 10;
		}

		ZtfunSdZsywjh ztfunsdzsywjh = new ZtfunSdZsywjh();
		ztfunsdzsywjh.setHeader(zsywjhheader);
		ztfunsdzsywjh.setTItems(tableofzsywjhitem);

		lognum = IFUtil.addIfHistory("MRO_ERP_ZSYWJH",
				JSON.toJSONString(ztfunsdzsywjh), IFUtil.TYPE_OUTPUT);// 接口管理中间表

		try {

			ZtfunSdZsywjhResponse Response = service
					.ztfunSdZsywjh(ztfunsdzsywjh);
			msg[0] = Response.getVbeln().toString();// 交货单号
			msg[1] = Response.getRmesg().toString();// 返回信息
			if (Response.getRcode().toString().equals("E")) {

				IFUtil.updateIfHistory(lognum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, Response.getRmesg().toString());
				msg[0] = Response.getRmesg().toString();
				msg[1] = "失败";

			} else {

				if (StringUtil.isStrNotEmpty(msg[0])) {

					IFUtil.updateIfHistory(lognum, IFUtil.STATUS_SUCCESS,
							IFUtil.FLAG_YES, "交货单号：" + msg[0] + ", "
									+ Response.getRmesg().toString());
					msg[1] = "成功";

				} else {

					IFUtil.updateIfHistory(lognum, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO, Response.getRmesg().toString());
					msg[0] = Response.getRmesg().toString();
					msg[1] = "失败";

				}

			}

		} catch (Exception e) {

			IFUtil.updateIfHistory(lognum, IFUtil.STATUS_FAILURE,
					IFUtil.FLAG_NO, e.getMessage());
			msg[0] = e.getMessage();
			msg[1] = "失败";

		}

		return msg;
	}

	/**
	 * 
	 * 交货单拣配过账
	 * 
	 * @param vbelns
	 *            交货单号
	 * @param date
	 *            过账日期
	 * @return
	 * @throws RemoteException
	 *             [参数说明]
	 * @throws MroException
	 * 
	 */
	public static String[] msg3(String vbelns, String date)
			throws RemoteException, MroException {
		String lognum = "";

		ComZzsErpZTFUN_SD_ZJHJP_WEBStub service = new ComZzsErpZTFUN_SD_ZJHJP_WEBStub();
		Authenticator auth = new Authenticator();
		String user = IFUtil.getIfServiceInfo("erp.user");
		String pwd = IFUtil.getIfServiceInfo("erp.pwd");
		auth.setUsername(user);
		auth.setPassword(pwd);
		service._getServiceClient().getOptions()
				.setProperty(HTTPConstants.AUTHENTICATE, auth);

		ZsdZjhjpHeader zsdzjhjpheader = new ZsdZjhjpHeader();
		com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.Char10 VBELN = new com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.Char10();
		VBELN.setChar10(vbelns);
		ComZzsErpZTFUN_SD_ZJHJP_WEBStub.Date Bldat = new ComZzsErpZTFUN_SD_ZJHJP_WEBStub.Date();
		Bldat.setDate(date);
		zsdzjhjpheader.setVbeln(VBELN);
		zsdzjhjpheader.setBldat(Bldat);

		// 按照文档，此表无需填写数据
		TableOfZsdZjhjpItems tableofzsdzjhjpitems = new TableOfZsdZjhjpItems();

		ZtfunSdZjhjp ztfunsdzjhjp = new ZtfunSdZjhjp();
		ztfunsdzjhjp.setHeader(zsdzjhjpheader);
		ztfunsdzjhjp.setTItems(tableofzsdzjhjpitems);

		String[] msg = new String[2];
		try {

			lognum = IFUtil.addIfHistory("MRO_ERP_ZJHJP",
					JSON.toJSONString(ztfunsdzjhjp), IFUtil.TYPE_OUTPUT);// 接口管理中间表
			ZtfunSdZjhjpResponse Response = service.ztfunSdZjhjp(ztfunsdzjhjp);

			msg[0] = Response.getRmesg().toString();
			if (Response.getRcode().toString().equals("E")) {

				IFUtil.updateIfHistory(lognum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, msg[0]);
				msg[1] = "失败";

			} else {

				if (msg[0].startsWith("过账成功")) {

					IFUtil.updateIfHistory(lognum, IFUtil.STATUS_SUCCESS,
							IFUtil.FLAG_YES, msg[0]);
					msg[1] = "成功";

				} else {

					IFUtil.updateIfHistory(lognum, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO, msg[0]);
					msg[1] = "失败";

				}

			}

		} catch (Exception e) {

			IFUtil.updateIfHistory(lognum, IFUtil.STATUS_FAILURE,
					IFUtil.FLAG_NO, e.getMessage());
			msg[0] = e.getMessage();
			msg[1] = "失败";

		}
		return msg;
	}

	/**
	 * 创建三包定单接口入口方法 <功能描述>
	 * 
	 * @param jpo
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static String tomsg(IJpo jpo) throws MroException {
		if (jpo == null)
			return "";
		String ret = "E";
		String erpstatus = jpo.getString("erpstatus");// 三包总状态
		String vbelnstatus = jpo.getString("vbelnstatus");// 第一次状态
		String vbelnsstatus = jpo.getString("vbelnsstatus");// 第二次状态
		String messagestatus = jpo.getString("messagestatus");// 第三次状态
		String vbeln = jpo.getString("VBELN");// 交货订单号
		String vbelns = jpo.getString("VBELNS");// 交货号
		String message = jpo.getString("message");// 反馈
		// 当前日期，给交货单拣配过账接口使用
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = sdf.format(MroServer.getMroServer().getDate());
		try {
			if (erpstatus.equals("成功")) {// 成功的不进行任何操作
				return "S"/* "已获取三包业务订单信息，不可重复获取" */;
			} else if (StringUtil.isStrEmpty(erpstatus)
					|| erpstatus.equals("失败")) {// 总状态失败，三包中交货单失败，从第一个接口开始
				if (StringUtil.isStrEmpty(vbelnstatus)
						|| vbelnstatus.equals("失败")) {// 第一个接口失败状态，重新开始
					// 调用第一个接口
					String[] msg1 = msg1(jpo);
					vbelnstatus = msg1[1];
					// 特殊情况：上下车为全部使用客户料，无需创建三包订单，部分客供料已排出
					if (vbelnstatus.equals("SPECIAL")) {
						return "S";
					}

					if (vbelnstatus.equals("成功")) {// 第一个接口成功
						vbeln = msg1[0];
						jpo.setValue("vbeln", vbeln, GWConstant.P_NOVALIDATION);
						jpo.setValue("vbelnstatus", vbelnstatus,
								GWConstant.P_NOVALIDATION);

						// 开始调用第二个接口
						String[] msg2 = msg2(jpo, vbeln, "1020");
						vbelnsstatus = msg2[1];
						if (vbelnsstatus.equals("成功")) {// 第二个接口成功
							vbelns = msg2[0];
							jpo.setValue("vbelns", vbelns,
									GWConstant.P_NOVALIDATION);
							jpo.setValue("vbelnsstatus", vbelnsstatus,
									GWConstant.P_NOVALIDATION);
							// 开始调用第三个接口
							String[] msg3 = msg3(vbelns, currDate);
							messagestatus = msg3[1];
							if (messagestatus.equals("成功")) {// 第三个接口成功

								if (msg3[0].indexOf("：") > -1) {
									message = msg3[0].substring(msg3[0]
											.indexOf("：") + 1);
								} else {
									message = msg3[0];
								}
								ret = "S";
								jpo.setValue("message", message,
										GWConstant.P_NOVALIDATION);
								jpo.setValue("messagestatus", messagestatus,
										GWConstant.P_NOVALIDATION);
								jpo.setValue("erpstatus", "成功",
										GWConstant.P_NOVALIDATION);
							} else {// 第三个接口失败
								message = msg3[0];
								ret = message;
								jpo.setValue("messagestatus", messagestatus,
										GWConstant.P_NOVALIDATION);
								jpo.setValue("erpstatus", "失败",
										GWConstant.P_NOVALIDATION);
							}
						} else {// 第二次失败
							ret = msg2[0];
							jpo.setValue("vbelnsstatus", "失败",
									GWConstant.P_NOVALIDATION);
							jpo.setValue("erpstatus", "失败",
									GWConstant.P_NOVALIDATION);
						}
					} else {// 第一个接口失败
						ret = msg1[0];
						jpo.setValue("vbelnstatus", "失败",
								GWConstant.P_NOVALIDATION);
						jpo.setValue("erpstatus", "失败",
								GWConstant.P_NOVALIDATION);
					}
				} else {// 第一个接口--交货单成功，从第二个开始

					if (vbelnsstatus.isEmpty() || vbelnsstatus.equals("失败")) {
						// 第二个接口-订货单失败，从第二个开始
						String[] msg2 = msg2(jpo, vbeln, "1020");
						vbelnsstatus = msg2[1];
						if (vbelnsstatus.equals("成功")) {// 第二个接口成功
							vbelns = msg2[0];
							jpo.setValue("vbelns", vbelns,
									GWConstant.P_NOVALIDATION);
							jpo.setValue("vbelnsstatus", vbelnsstatus,
									GWConstant.P_NOVALIDATION);
							// 开始第三个接口
							String[] msg3 = msg3(vbelns, currDate);
							messagestatus = msg3[1];
							if (messagestatus.equals("成功")) {// 第三个接口成功
								if (msg3[0].indexOf("：") > -1) {
									message = msg3[0].substring(msg3[0]
											.indexOf("：") + 1);
								} else {
									message = msg3[0];
								}
								ret = "S";
								jpo.setValue("message", message,
										GWConstant.P_NOVALIDATION);
								jpo.setValue("messagestatus", messagestatus,
										GWConstant.P_NOVALIDATION);
								jpo.setValue("erpstatus", "成功",
										GWConstant.P_NOVALIDATION);
							} else {// 第三个接口失败
								message = msg3[0];
								ret = message;
								jpo.setValue("messagestatus", messagestatus,
										GWConstant.P_NOVALIDATION);
								jpo.setValue("erpstatus", "失败",
										GWConstant.P_NOVALIDATION);
							}
						} else {// 第二次失败
							ret = msg2[0];
							jpo.setValue("vbelnsstatus", "失败",
									GWConstant.P_NOVALIDATION);
							jpo.setValue("erpstatus", "失败",
									GWConstant.P_NOVALIDATION);
						}
					} else if (vbelnsstatus.equals("成功")) {
						// 第二个接口-订货单成功，从第三个接口开始
						// 判断第三个接口状态，是否重新调用
						if (!messagestatus.equals("成功")) {
							String[] msg3 = msg3(vbelns, currDate);
							messagestatus = msg3[1];
							if (messagestatus.equals("成功")) {// 第三个接口成功
								if (msg3[0].indexOf("：") > -1) {
									message = msg3[0].substring(msg3[0]
											.indexOf("：") + 1);
								} else {
									message = msg3[0];
								}
								jpo.setValue("message", message,
										GWConstant.P_NOVALIDATION);
								jpo.setValue("messagestatus", messagestatus,
										GWConstant.P_NOVALIDATION);
								jpo.setValue("erpstatus", "成功",
										GWConstant.P_NOVALIDATION);
								ret = "S";
							} else {// 第三个接口失败
								message = msg3[0];
								ret = message;
								jpo.setValue("messagestatus", messagestatus,
										GWConstant.P_NOVALIDATION);
								jpo.setValue("erpstatus", "失败",
										GWConstant.P_NOVALIDATION);
							}
						}
					}
				}
			}
		} catch (RemoteException e) {

			ret = e.getMessage();
			jpo.setValue("erpstatus", "失败", GWConstant.P_NOVALIDATION);

		}
		return ret;
	}

	/**
	 * 
	 * 发送MRO系统消息
	 * 
	 * @param appName
	 *            应用名称
	 * @param uid
	 *            应用唯一ID
	 * @param receivers
	 *            接收人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容 [参数说明]
	 * 
	 */
	public static void sendMsg(String appName, long uid, String[] receivers,
			String subject, String content) throws MroException {

		IJpoSet msgSet = MroServer.getMroServer().getSysJpoSet("MSGMANAGE");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");

		for (String receiver : receivers) {
			IJpo msg = msgSet.addJpo();
			msg.setValue("MSGNUM", uid);// 消息配置编号
			msg.setValue("APPID", uid);// 记录ID
			msg.setValue("APP", appName);// 关联应用
			msg.setValue("RECEIVER", receiver);// 接收人
			msg.setValue("SUBJECT", subject);// 主题
			msg.setValue("CONTENT", content);// 内容
		}
		msgSet.save();

	}

	public static void sendMsg(String appName, long uid, Set<String> receivers,
			String subject, String content) throws MroException {
		String[] receiversArray = new String[receivers.size()];
		receivers.toArray(receiversArray);
		sendMsg(appName, uid, receiversArray, subject, content);
	}

	/**
	 * 
	 * 根据机务段获取所属办事处的主任
	 * 
	 * @param station
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static List<String> getOfficeDirectorByStation(String station)
			throws MroException {
		// 部门表
		IJpoSet deptSet = MroServer.getMroServer().getJpoSet("SYS_DEPT",
				MroServer.getMroServer().getSystemUserServer());
		deptSet.setQueryWhere("deptnum='" + station + "'");
		deptSet.reset();

		List<String> officer = new ArrayList<String>();

		if (deptSet != null && deptSet.count() > 0) {
			// 办事处部门编号
			String deptnum = deptSet.getJpo(0).getString("parent");
			officer = getPersonsByPost(deptnum, "办事处主任");
		}
		if (officer.size() == 0) {
			IJpoSet deptSet1 = MroServer.getMroServer().getSysJpoSet(
					"SYS_DEPT",
					"deptnum='" + deptSet.getJpo(0).getString("parent") + "'");
			String mdmid = deptSet1.getJpo(0).getString("MDM_DEPTID");
			officer = getPersonsByPost(mdmid, "办事处主任");
		}
		return officer;
	}

	public static List<String> getPersonsByPost(String deptnum, String postname)
			throws MroException {

		List<String> persons = new ArrayList<String>();

		// 岗位表
		IJpoSet postSet = MroServer.getMroServer().getJpoSet("POST",
				MroServer.getMroServer().getSystemUserServer());
		postSet.setUserWhere("detpnum='" + deptnum + "' and postname like '%"
				+ postname + "%'");
		postSet.reset();
		if (!postSet.isEmpty()) {
			IJpo post = postSet.getJpo(0);
			// 岗位人员表
			IJpoSet personSet = post.getJpoSet("person");
			if (!personSet.isEmpty()) {
				for (int index = 0; index < personSet.count(); index++) {
					persons.add(personSet.getJpo(index).getString("personid"));
				}
			}
		}

		return persons;

	}

	/**
	 * 
	 * 创建改造计划
	 * 
	 * @param transnotice
	 *            改造通知单jpo
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public static void createTransPlan(IJpo transnotice) throws Exception {
		if (!transnotice.getJpoSet("TRANSPLAN").isEmpty()) {
			throw new MroException("transnotice", "transplanexsits");
		}
		IJpoSet planSet = MroServer.getMroServer().getSysJpoSet("TRANSPLAN");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		IJpo plan = planSet.addJpo();
		plan.setValue("TRANSTYPE", transnotice.getString("TRANSKIND"),
				GWConstant.P_NOVALIDATION);
		plan.setValue("TRANSPLANNAME", generateTransPlanName(transnotice));
		plan.setValue("PLANTYPE", "改造");
		plan.setValue("ENDDATE", transnotice.getString("COMPLETEDATE"));
		plan.setValue("TRANSNOTICENUM",
				transnotice.getString("TRANSNOTICENUM"),
				GWConstant.P_NOVALIDATION);
		plan.setValue("WORKORDERNUM",
				transnotice.getString("TRANSWORKORDERNUM"));
		String plannum = plan.getString("TRANSPLANNUM");// 计划编号

		IJpoSet distSet = plan.getJpoSet("TRANSDIST");// 改造车辆分布
		IJpoSet rangeSet = transnotice.getJpoSet("TRANSRANGE");// 改成产品范围
		for (int index = 0; index < rangeSet.count(); index++) {
			IJpo range = rangeSet.getJpo(index);
			IJpo dist = distSet.addJpo();

			String station = range.getString("IMPDEPT");
			dist.setValue("TRANSPLANNUM", plannum);
			if (StringUtil.isStrNotEmpty(station)) {
				dist.setValue("station", station, GWConstant.P_NOVALIDATION);
			}
			dist.setValue("PROJECTNUM", range.getString("PROJECTNUM"));
			dist.setValue("TRANSMODELS", range.getString("TRANSMODELS"));
			dist.setValue("KINDLOC", range.getString("KINDLOC"),
					GWConstant.P_NOVALIDATION);
			dist.setValue("TRANSCOUNT", range.getString("TRANSCOUNT"));
			dist.setValue("unit", range.getString("unit"));
		}

		IJpoSet materiallistset = transnotice.getJpoSet("MATERIALLIST");// 改造通知单-物料计划
		IJpoSet transmaterialplanset = plan.getJpoSet("TRANSMATERIALPLAN");// 改造计划-物料计划

		for (int i = 0; i < materiallistset.count(); i++) {

			IJpo transmaterialplan = transmaterialplanset.addJpo();
			IJpo materiallist = materiallistset.getJpo(i);

			transmaterialplan.setValue("TRANSPLANNUM", plannum);
			transmaterialplan.setValue("PRODUCTCODE",
					materiallist.getString("PRODUCTCODE"),
					GWConstant.P_NOVALIDATION);
			transmaterialplan.setValue("MATERIALCOUNT",
					materiallist.getFloat("MATERIALCOUNT"));
			transmaterialplan.setValue("REMARK",
					materiallist.getString("REMARK"));

		}
		planSet.save();

		// 启动计划工作流
		IJpo transplan = MroServer.getMroServer()
				.getSysJpoSet("TRANSPLAN", "transplannum='" + plannum + "'")
				.getJpo(0);
		WfControlUtil.startwf(transplan, "TRANSPLAN");
	}

	/**
	 * 
	 * 生成改造计划名称
	 * 
	 * @param transnotice
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static String generateTransPlanName(IJpo transnotice)
			throws MroException {
		String planName = "";
		// 申请日期
		Date appDate = transnotice.getDate("APPDATE");
		String transDateStr = new SimpleDateFormat("yyyy年MM月dd日")
				.format(appDate);

		// 第一条改造范围jpo
		IJpo transRange = transnotice.getJpoSet("TRANSRANGE").getJpo(0);
		String ownercustomer = transRange.getString("IMPDEPT.DESCRIPTION");// 机务段
		String model = transRange.getString("MODELS.MODELCODE");// 车型
		String jdc = getModelType(transRange.getString("TRANSMODELS"));// 车型大类
		IJpo content = transnotice.getJpoSet("TRANSCONTENT").getJpo(0);
		String product = content.getString("PREVIOUSPRDNUM.DESCRIPTION");// 物料描述

		planName = transDateStr + ownercustomer + model + jdc + product
				+ "改造计划";

		return planName;
	}

	/**
	 * 
	 * 根据车型编号获取车型大类
	 * 
	 * @param models
	 *            车型编号
	 * @return [参数说明]
	 * 
	 */
	public static String getModelType(String models) {
		String type = "";
		switch (models.charAt(0)) {
		case 'J':
			type = "机车";
			break;
		case 'D':
			type = "动车";
			break;
		case 'C':
			type = "城轨";
			break;
		}
		return type;
	}

	/**
	 * 
	 * 判断是否是高级故障
	 * 
	 * @param faultConsequence
	 *            故障后果
	 * @return [参数说明]
	 * 
	 */
	public static boolean isImpFault(String faultConsequence) {

		String[] faultConSeqs = { "机破", "D类事故", "C类及以上事故", "下线", "清客", "救援",
				"安监" };

		return StringUtil.isHaveStr(faultConSeqs, faultConsequence);
	}

	/**
	 * 
	 * QMS接口必填字段校验
	 * 
	 * @return [参数说明]
	 * 
	 */
	private static Map<String, String> QMSFieldCheck() {

		// 必填校验--可改同义词域
		Map<String, String> checkers = new HashMap<String, String>();
		checkers.put("xmh", "项目号");
		checkers.put("gzfssj", "故障发生时间");
		checkers.put("fsxfxbg", "客户是否需要分析报告");
		checkers.put("ddxcsj", "到达现场时间");
		checkers.put("zykssj", "作业开始时间");
		checkers.put("clwcsj", "处理完成时间");
		checkers.put("yxms", "运行模式");
		checkers.put("clfs", "处理方式");
		checkers.put("gzxx", "故障现象");
		checkers.put("fsjd", "发生阶段");
		checkers.put("gzhg", "故障后果");
		checkers.put("bgry", "报告人员/工号");
		checkers.put("gzdx", "客户定责");
		checkers.put("xcclr", "现场处理人");
		checkers.put("bgdw", "报告单位");
		checkers.put("bgdwzlbbz", "报告单位质量部部长");
		checkers.put("bgdwzgld", "报告单位主管领导");
		checkers.put("bgsj", "报告时间");
		checkers.put("clbz", "处理措施");
		checkers.put("zccx", "车型");
		checkers.put("zcch", "车号");
		checkers.put("psyh", "配属用户");
		checkers.put("jxxc", "修程修次");
		checkers.put("gzpmc", "故障品名称");
		checkers.put("gzpxh", "故障品序号/批次号");
		checkers.put("gzmc", "故障名称");
		checkers.put("QMS_ID", "故障工单号");
		checkers.put("cbyyfx", "初步分析");
		checkers.put("cplb", "车型/产品类别");
		checkers.put("xcclrlxfs", "现场处理人联系方式");
		checkers.put("gzpwz", "故障品位置号");
		checkers.put("cxxm", "车型项目");
		checkers.put("cxh", "车厢号");
		checkers.put("scdw", "生产单位");

		return checkers;
	}

	/**
	 * 
	 * 获取系统级自动编号当前的值
	 * 
	 * @param autokeyName
	 *            自动编号名称
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static int getSysAutoKey(String autokeyName) throws MroException {

		return getSysAutoKey(autokeyName, null, null);

	}

	/**
	 * 
	 * 获取组织级自动编号当前的值
	 * 
	 * @param autokeyName
	 *            自动编号名称
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static int getSysAutoKey(String autokeyName, String orgId)
			throws MroException {

		return getSysAutoKey(autokeyName, orgId, null);

	}

	/**
	 * 
	 * 获取地点级自动编号当前的值
	 * 
	 * @param autokeyName
	 *            自动编号名称
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static int getSysAutoKey(String autokeyName, String orgId,
			String siteId) throws MroException {
		int key = 0;
		String where = "";
		if (StringUtil.isStrEmpty(siteId)) {

			if (StringUtil.isStrEmpty(orgId)) {

				where = " and orgid is null and siteid is null ";

			} else {

				where = " and siteid is null and orgid='" + orgId.toUpperCase()
						+ "' ";

			}

		} else {

			where = " and siteid ='" + siteId.toUpperCase() + "' and orgid='"
					+ orgId.toUpperCase() + "' ";

		}
		IJpoSet autokeySet = MroServer.getMroServer().getSysJpoSet(
				"sys_autokey",
				"autokeyname='" + autokeyName.toUpperCase() + "' " + where);
		if (autokeySet != null && autokeySet.count() > 0) {

			int seed = autokeySet.getJpo(0).getInt("seed") + 1;
			key = seed;

			autokeySet.getJpo(0).setValue("seed", seed);
			autokeySet.save();

		} else {
			throw new MroException("自动编号错误！");
		}

		return key;
	}

	/**
	 * 
	 * 操作配置中耗损件数量
	 * 
	 * @param qty
	 *            操作数量
	 * @param operator
	 *            运算符
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void operateQty(String assetpartnum, int qty, String itemnum,
			String uploc, String lotnum, String operator) throws MroException {

		/**
		 * 操作下车件
		 */
		IJpoSet apartSet = MroServer.getMroServer().getSysJpoSet("ASSETPART",
				"ASSETPARTNUM='" + assetpartnum + "'");// 配置中耗损件表
		if (apartSet != null && apartSet.count() > 0) {

			IJpo apart = apartSet.getJpo(0);

			int oldQty = apart.getInt("lockqty");// 原锁定数量
			int newQty = 0;// 操作后数量
			if ("+".equals(operator)) {// 增加

				newQty = oldQty + qty;

			} else if ("-".equals(operator)) {// 减少

				newQty = (oldQty - qty) > 1 ? (oldQty - qty) : 1;

			}
			apart.setValue("lockqty", newQty, GWConstant.P_NOVALIDATION);
			apartSet.save();
		}

		/**
		 * 操作上车件
		 */
		IJpoSet inventorySet = null;
		if (ItemUtil.LOT_I_ITEM.equals(ItemUtil.getItemInfo(itemnum))) {// 批次号件

			inventorySet = MroServer.getMroServer().getSysJpoSet(
					"INVBLANCE",
					" itemnum='" + itemnum + "' and lotnum='" + lotnum
							+ "' and storeroom = '" + uploc + "'");

		} else {

			inventorySet = MroServer.getMroServer()
					.getSysJpoSet(
							"SYS_INVENTORY",
							" itemnum='" + itemnum + "' and location = '"
									+ uploc + "'");
		}
		if (inventorySet != null && inventorySet.count() > 0) {
			IJpo jpo = inventorySet.getJpo(0);
			double oldQty = jpo.getDouble("orderaplyqty");// 原申请数量
			double newQty = 0.0;
			if ("+".equals(operator)) {
				newQty = oldQty + qty;
			} else if ("-".equals(operator)) {
				newQty = (oldQty - qty) > 0 ? (oldQty - qty) : 0;
			}
			jpo.setValue("orderaplyqty", newQty, GWConstant.P_NOVALIDATION);
			inventorySet.save();
		}
	}

	/**
	 * 
	 * 根据库房编号去找部门
	 * 
	 * @param location
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static String getDeptnumByLoc(String location) throws MroException {
		String deptnum = "";
		if (StringUtils.isNotEmpty(location)) {
			IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("LOCATIONS",
					"LOCATION='" + location + "'");
			if (jposet != null && jposet.count() > 0) {
				String personid = jposet.getJpo(0).getString("KEEPER");
				deptnum = getDeptByPerson(personid);
			}
		}
		return deptnum;
	}

	/**
	 * 
	 * 获取故障件父级部件
	 * 
	 * @param jpo
	 *            工单or上下车记录jpo
	 * @param type
	 *            类型 (WO、SQN、LOT)
	 * @param itemnum
	 *            故障件物料编码
	 * @param assetnum
	 *            故障件assetnum or 故障件父级assetnum
	 * @param relatednum
	 *            关联编号
	 * @param sqnorbatch
	 *            序列号or批次号
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void getSuperiorAsset(IJpo jpo, String type, String itemnum,
			String assetnum, String relatednum, String sqnorbatch)
			throws MroException {
		// 获取故障件上级
		IJpoSet superiorSet = jpo
				.getJpoSet("$superior", "SUPERIORASSET", "1=2");
		// 故障件assetNum
		String newAssetnum = assetnum;
		// 故障件set
		IJpoSet faultSet = MroServer.getMroServer().getSysJpoSet("ASSET");

		// 获取故障件层级
		int level = 0;
		while (true) {
			faultSet.setUserWhere("assetnum='" + newAssetnum + "'");
			faultSet.reset();
			IJpo faultpart = faultSet.getJpo(0);

			if (faultpart != null) {

				// 父级jpo
				IJpo parent = faultpart.getJpoSet("parent").getJpo(0);
				if (parent == null) {
					break;
				}

				String assetlevel = parent.getString("ASSETLEVEL");

				if ("CAR".equalsIgnoreCase(assetlevel)) {// 循环到车厢级别停止
					break;
				}
				if (StringUtil.isStrEmpty(parent.getString("parent"))) {// 顶层节点
					break;
				}
				newAssetnum = parent.getString("assetnum");
				level++;
			}

		}
		// 重置为故障件assetnum
		newAssetnum = assetnum;
		// 非序列号件
		if (!ItemUtil.SQN_ITEM.equalsIgnoreCase(ItemUtil.getItemInfo(itemnum))) {
			faultSet.setUserWhere("assetnum='" + newAssetnum + "'");
			faultSet.reset();
			IJpo faultpart = faultSet.getJpo(0);

			if (faultpart != null) {

				IJpo superior = superiorSet.addJpo();
				superior.setValue("type", type);
				superior.setValue("relatednum", relatednum);
				superior.setValue("partlevel", level + 1);
				if ("CAR".equalsIgnoreCase(faultpart.getString("assetlevel"))) {// 如果直接挂在车厢上，则填自己

					superior.setValue("assetnum", newAssetnum);
					superior.setValue("itemnum", itemnum);
					superior.setValue("sqnorbatchnum", sqnorbatch);

				} else {

					// 设置非序列号件直接父级
					superior.setValue("assetnum", newAssetnum);
					superior.setValue("itemnum", faultpart.getString("ITEMNUM"));
					superior.setValue("sqnorbatchnum",
							faultpart.getString("SQN"));

				}
			}
		}

		// 如果直接挂在车厢上则记录本身为1级部件
		if (level == 0
				&& ItemUtil.SQN_ITEM.equalsIgnoreCase(ItemUtil
						.getItemInfo(itemnum))) {

			IJpo superior = superiorSet.addJpo();
			superior.setValue("assetnum", newAssetnum);
			superior.setValue("type", type);
			superior.setValue("itemnum", itemnum);
			superior.setValue("sqnorbatchnum", sqnorbatch);
			superior.setValue("relatednum", relatednum);
			superior.setValue("partlevel", level + 1);

		}

		// 循环添加上级部件
		while (level > 0) {

			faultSet.setUserWhere("assetnum='" + newAssetnum + "'");
			faultSet.reset();
			IJpo faultpart = faultSet.getJpo(0);

			// 父级jpo
			IJpo parent = faultpart.getJpoSet("parent").getJpo(0);

			if (parent != null) {

				IJpo superior = superiorSet.addJpo();
				superior.setValue("assetnum", parent.getString("ASSETNUM"));
				superior.setValue("type", type);
				superior.setValue("itemnum", parent.getString("ITEMNUM"));
				superior.setValue("sqnorbatchnum", parent.getString("SQN"));
				superior.setValue("relatednum", relatednum);
				superior.setValue("partlevel", level);

				newAssetnum = parent.getString("assetnum");
				level--;

			}

		}
		faultSet.destroy();

	}

	/**
	 * 
	 * 判断是否有关键物料
	 * 
	 * @param jposet
	 *            数据表
	 * @param relationShip
	 *            与sys_item关联关系
	 * @return true:有关键物料，false:无关键物料
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static boolean hasImpItem(IJpoSet jposet, String relationShip)
			throws MroException {

		boolean flag = false;
		if (jposet != null && jposet.count() > 0) {

			for (int idx = 0; idx < jposet.count(); idx++) {
				IJpo jpo = jposet.getJpo(idx);
				if (jpo.getBoolean(relationShip + ".IMPORTANT")) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	/**
	 * 
	 * 串换件
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void swapParts(IJpoSet swapSet) throws MroException {

		for (int idx = 0; idx < swapSet.count(); idx++) {
			IJpo swap = swapSet.getJpo(idx);
			// 跳过已经进行过串换的记录
			if (swap.getBoolean("isdone")) {
				continue;
			}
			// 原整车assetnum
			String bfAncestor = swap.getString("OLDTRAINASSETNUM");
			// 原件assetnum
			String bfAssetnum = swap.getString("BEFOREASSETNUM");
			// 新整车assetnum
			String afAncestor = swap.getString("NEWTRAINASSETNUM");
			// 串换件assetnum
			String afAssetnum = swap.getString("CHASSETNUM");
			// 原件父级
			String oldParent = swap.getString("BEFOREPARENT");
			// 串换件父级
			String newParent = swap.getString("CHPARENT");
			// 原件位置号
			String oldLoc = swap.getString("BFASSETNUM.LOC");
			// 串换件位置号
			String newLoc = swap.getString("CHASSETNUM.LOC");
			// 原物料编码
			String bfItemnum = swap.getString("BEFOREITEMNUM");
			// 原批次号
			String bfBatchnum = swap.getString("BFEBATCHNUM");
			// 串换物料编码
			String afItemnum = swap.getString("AFTERITEMNUM");
			// 串换批次号
			String afBatchnum = swap.getString("AFTBATCHNUM");

			/* 序列号件处理 */
			if (ItemUtil.SQN_ITEM.equals(ItemUtil.getItemInfo(bfItemnum))) {

				// 获取ASSET表中待拆卸的选中部件以及子部件
				IJpoSet removeMboset = MroServer.getMroServer().getSysJpoSet(
						"ASSET");
				removeMboset
						.setUserWhere("ANCESTOR='"
								+ bfAncestor
								+ "' and assetnum in (select assetnum from asset start with assetnum ='"
								+ bfAssetnum
								+ "' connect by parent = PRIOR assetnum)");
				removeMboset.reset();

				// 保留结构，更新Asset表的parent以及ancestor
				if (removeMboset != null && removeMboset.count() > 0) {
					for (int index = 0; index < removeMboset.count(); index++) {

						// 原件
						if (removeMboset.getJpo(index).getString("ASSETNUM")
								.equals(bfAssetnum)) {
							// 变更原件的PARENT
							removeMboset.getJpo(index).setValue("PARENT",
									newParent,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							// 变更原件的LOC
							removeMboset.getJpo(index).setValue("LOC", newLoc,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
						// 变更原件及子级的ancestor
						removeMboset.getJpo(index).setValue("ANCESTOR",
								afAncestor,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					}// end of removeMboset circle
					removeMboset.save();

				}

				// 串换件处理
				IJpoSet installMboset = MroServer.getMroServer().getSysJpoSet(
						"ASSET");
				installMboset
						.setUserWhere("ANCESTOR='"
								+ afAncestor
								+ "' and assetnum in (select assetnum from asset start with assetnum ='"
								+ afAssetnum
								+ "' connect by parent = PRIOR assetnum)");
				installMboset.reset();

				// 变更串换件及子部件的ANCESTOR和parent
				if (installMboset != null && installMboset.count() > 0) {

					for (int index = 0; index < installMboset.count(); index++) {

						if (installMboset.getJpo(index).getString("ASSETNUM")
								.equals(afAssetnum)) {
							// 设置串换件父级
							installMboset.getJpo(index).setValue("PARENT",
									oldParent,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							// 设置串换件新位置号
							installMboset.getJpo(index).setValue("LOC", oldLoc,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
						// 设置串换件及子级的ancestor
						installMboset.getJpo(index).setValue("ANCESTOR",
								bfAncestor,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					}// end of installMboset circle
					installMboset.save();
				}

			} else {

				/* 非序列号件处理 */
				// 串换物料编码相同
				if (bfItemnum.equalsIgnoreCase(afItemnum)) {

					// 防止空指针
					bfBatchnum = bfBatchnum != null ? bfBatchnum : "";
					// 批次号不一致
					if (!bfBatchnum.equalsIgnoreCase(afBatchnum)) {
						// 拆除原件
						IJpoSet removePartSet = MroServer.getMroServer()
								.getSysJpoSet("ASSETPART",
										"assetpartnum='" + bfAssetnum + "'");
						if (removePartSet != null && removePartSet.count() > 0) {
							IJpo removePart = removePartSet.getJpo(0);
							removePart.setValue("qty",
									removePart.getInt("QTY") - 1);// 变更剩余数量
							if (removePart.getInt("LOCKQTY") > 1) {
								removePart.setValue("LOCKQTY",
										removePart.getInt("LOCKQTY") - 1);// 重置锁定数量
							}

							IJpoSet apartSet = MroServer.getMroServer()
									.getSysJpoSet("ASSETPART");
							MroServer.getMroServer().getSystemUserServer()
									.getUserInfo().setDefaultOrg("CRRC");
							MroServer.getMroServer().getSystemUserServer()
									.getUserInfo().setDefaultSite("ELEC");
							apartSet.setUserWhere("assetnum='" + oldParent
									+ "' and itemnum='" + afItemnum
									+ "' and lotnum='" + afBatchnum + "'");
							apartSet.reset();

							if (apartSet != null && apartSet.count() > 0) {// 已存在相同批次件，变更数量即可
								IJpo apart = apartSet.getJpo(0);
								apart.setValue("qty", apart.getInt("qty") + 1);
							} else {// 不存在相同的物料，新增记录
								IJpo newAssetpart = apartSet.addJpo();
								newAssetpart.setValue("ASSETNUM", oldParent);
								newAssetpart.setValue("ITEMNUM", afItemnum);
								newAssetpart.setValue("ORGID", "CRRC");
								newAssetpart.setValue("SITEID", "ELEC");
								newAssetpart.setValue("LOTNUM", afBatchnum);
								newAssetpart.setValue("PARENTITEMNUM",
										removePart.getString("PARENTITEMNUM"));
								newAssetpart.setValue("PARENTSQN",
										removePart.getString("PARENTSQN"));
								newAssetpart.setValue("RNUM",
										removePart.getString("RNUM"));
								newAssetpart.setValue("QTY", 1);

							}

							apartSet.save();
							// 删除车上数量为0的批次件
							if (removePart.getInt("QTY") <= 0) {
								removePart.delete();
							}
							removePartSet.save();

						}

						// 处理串换件
						IJpoSet instPartSet = MroServer.getMroServer()
								.getSysJpoSet("ASSETPART",
										"assetpartnum='" + afAssetnum + "'");
						if (instPartSet != null && instPartSet.count() > 0) {
							IJpo installPart = instPartSet.getJpo(0);
							installPart.setValue("qty",
									installPart.getInt("QTY") - 1);// 变更剩余数量
							if (installPart.getInt("LOCKQTY") > 1) {
								installPart.setValue("LOCKQTY",
										installPart.getInt("LOCKQTY") - 1);// 重置锁定数量
							}

							IJpoSet apartSet = MroServer.getMroServer()
									.getSysJpoSet("ASSETPART");
							MroServer.getMroServer().getSystemUserServer()
									.getUserInfo().setDefaultOrg("CRRC");
							MroServer.getMroServer().getSystemUserServer()
									.getUserInfo().setDefaultSite("ELEC");
							apartSet.setUserWhere("assetnum='" + newParent
									+ "' and itemnum='" + bfItemnum
									+ "' and lotnum='" + bfBatchnum + "'");
							apartSet.reset();

							if (apartSet != null && apartSet.count() > 0) {// 已存在相同批次件，变更数量即可
								IJpo apart = apartSet.getJpo(0);
								apart.setValue("qty", apart.getInt("qty") + 1);
							} else {// 不存在相同的物料，新增记录
								IJpo newAssetpart = apartSet.addJpo();
								newAssetpart.setValue("ASSETNUM", newParent);
								newAssetpart.setValue("ITEMNUM", bfItemnum);
								newAssetpart.setValue("ORGID", "CRRC");
								newAssetpart.setValue("SITEID", "ELEC");
								newAssetpart.setValue("LOTNUM", bfBatchnum);
								newAssetpart.setValue("PARENTITEMNUM",
										installPart.getString("PARENTITEMNUM"));
								newAssetpart.setValue("PARENTSQN",
										installPart.getString("PARENTSQN"));
								newAssetpart.setValue("RNUM",
										installPart.getString("RNUM"));
								newAssetpart.setValue("QTY", 1);

							}
							apartSet.save();
							// 删除车上数量为0的批次件
							if (installPart.getInt("QTY") <= 0) {
								installPart.delete();
							}
							instPartSet.save();
						}

					}
				} else {// 串换物料编码不一致

					// 拆除原件
					IJpoSet removePartSet = MroServer.getMroServer()
							.getSysJpoSet("ASSETPART",
									"assetpartnum='" + bfAssetnum + "'");
					if (removePartSet != null && removePartSet.count() > 0) {
						IJpo removePart = removePartSet.getJpo(0);
						removePart
								.setValue("qty", removePart.getInt("QTY") - 1);// 变更剩余数量
						if (removePart.getInt("LOCKQTY") > 1) {
							removePart.setValue("LOCKQTY",
									removePart.getInt("LOCKQTY") - 1);// 重置锁定数量
						}

						IJpoSet apartSet = MroServer.getMroServer()
								.getSysJpoSet("ASSETPART");
						MroServer.getMroServer().getSystemUserServer()
								.getUserInfo().setDefaultOrg("CRRC");
						MroServer.getMroServer().getSystemUserServer()
								.getUserInfo().setDefaultSite("ELEC");
						apartSet.setUserWhere("assetnum='" + oldParent
								+ "' and itemnum='" + afItemnum
								+ "' and lotnum='" + afBatchnum + "'");
						apartSet.reset();

						if (apartSet != null && apartSet.count() > 0) {// 已存在相同批次件，变更数量即可
							IJpo apart = apartSet.getJpo(0);
							apart.setValue("qty", apart.getInt("qty") + 1);
						} else {// 不存在相同的物料，新增记录
							IJpo newAssetpart = apartSet.addJpo();
							newAssetpart.setValue("ASSETNUM", oldParent);
							newAssetpart.setValue("ITEMNUM", afItemnum);
							newAssetpart.setValue("ORGID", "CRRC");
							newAssetpart.setValue("SITEID", "ELEC");
							newAssetpart.setValue("LOTNUM", afBatchnum);
							newAssetpart.setValue("PARENTITEMNUM",
									removePart.getString("PARENTITEMNUM"));
							newAssetpart.setValue("PARENTSQN",
									removePart.getString("PARENTSQN"));
							newAssetpart.setValue("RNUM",
									removePart.getString("RNUM"));
							newAssetpart.setValue("QTY", 1);

						}

						apartSet.save();
						// 删除车上数量为0的批次件
						if (removePart.getInt("QTY") <= 0) {
							removePart.delete();
						}
						removePartSet.save();

					}

					// 处理串换件
					IJpoSet instPartSet = MroServer.getMroServer()
							.getSysJpoSet("ASSETPART",
									"assetpartnum='" + afAssetnum + "'");
					if (instPartSet != null && instPartSet.count() > 0) {
						IJpo installPart = instPartSet.getJpo(0);
						installPart.setValue("qty",
								installPart.getInt("QTY") - 1);// 变更剩余数量
						if (installPart.getInt("LOCKQTY") > 1) {
							installPart.setValue("LOCKQTY",
									installPart.getInt("LOCKQTY") - 1);// 重置锁定数量
						}

						IJpoSet apartSet = MroServer.getMroServer()
								.getSysJpoSet("ASSETPART");
						MroServer.getMroServer().getSystemUserServer()
								.getUserInfo().setDefaultOrg("CRRC");
						MroServer.getMroServer().getSystemUserServer()
								.getUserInfo().setDefaultSite("ELEC");
						apartSet.setUserWhere("assetnum='" + newParent
								+ "' and itemnum='" + bfItemnum
								+ "' and lotnum='" + bfBatchnum + "'");
						apartSet.reset();

						if (apartSet != null && apartSet.count() > 0) {// 已存在相同批次件，变更数量即可
							IJpo apart = apartSet.getJpo(0);
							apart.setValue("qty", apart.getInt("qty") + 1);
						} else {// 不存在相同的物料，新增记录
							IJpo newAssetpart = apartSet.addJpo();
							newAssetpart.setValue("ASSETNUM", newParent);
							newAssetpart.setValue("ITEMNUM", bfItemnum);
							newAssetpart.setValue("ORGID", "CRRC");
							newAssetpart.setValue("SITEID", "ELEC");
							newAssetpart.setValue("LOTNUM", bfBatchnum);
							newAssetpart.setValue("PARENTITEMNUM",
									installPart.getString("PARENTITEMNUM"));
							newAssetpart.setValue("PARENTSQN",
									installPart.getString("PARENTSQN"));
							newAssetpart.setValue("RNUM",
									installPart.getString("RNUM"));
							newAssetpart.setValue("QTY", 1);

						}
						apartSet.save();
						// 删除车上数量为0的批次件
						if (installPart.getInt("QTY") <= 0) {
							installPart.delete();
						}
						instPartSet.save();
					}

				}

			}
			// 设置已经完成串换标记
			swap.setValue("ISDONE", 1, GWConstant.P_NOVALIDATION);
		}
		swapSet.save();
	}

	/**
	 * 
	 * 获取xml合法字符串
	 * 
	 * @param oldStr
	 *            原字符串
	 * @return [参数说明]
	 * 
	 */
	public static String getSafeXmlString(String oldStr) {
		String newStr = "";
		if (StringUtil.isStrNotEmpty(oldStr)) {
			newStr = oldStr.replaceAll("&", "&amp;");
			newStr = newStr.replaceAll("<", "&lt;");
			newStr = newStr.replaceAll(">", "&gt;");
			newStr = newStr.replaceAll("%(?![0-9a-fA-F]{2})", "%25");// 处理百分号
		}
		return newStr;
	}

	/**
	 * 
	 * 判断是否存在文件名非法字符
	 * 
	 * @param str
	 *            原字符串
	 * @return true存在，false不存在
	 * 
	 */
	public static boolean hasIllegalFileNameChar(String str) {
		String[] illegalChars = { "\\", "/", ":", "*", "?", "\"", "<", ">", "|" };
		return StringUtil.isHaveStr(illegalChars, str);
	}

	/**
	 * 
	 * 判断用户是否在管理员权限组
	 * 
	 * @param loginId
	 * @return [参数说明]
	 * 
	 */
	public static boolean isInAdminGroup(String loginId) throws MroException {

		IJpoSet groupSet = MroServer.getMroServer().getSysJpoSet(
				"SYS_GROUPUSER", "groupname='SDDQDEFAULT'");
		if (groupSet != null && groupSet.count() > 0) {
			for (int idx = 0; idx < groupSet.count(); idx++) {
				String userId = groupSet.getJpo(idx).getString("USERID");
				if (loginId.equalsIgnoreCase(userId)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 判断是否已经调用三包订单接口并生成三包订单号
	 * 
	 * @param orderNum
	 *            故障工单编号
	 * @return 三包订单号，为空则未生成
	 * @throws MroException
	 */
	public static String isSendToERP(String orderNum) throws MroException {

		String sbNum = "";

		IJpoSet ifaceHisSet = MroServer.getMroServer().getSysJpoSet(
				"INTERFACEHISTORY",
				"IFNUM='MRO_ERP_ZSYW' and " + "STATUS='成功' and CONTENT like '%"
						+ orderNum + "%'");
		if (ifaceHisSet != null && ifaceHisSet.count() > 0) {
			// 设置排序，取最近一条记录
			ifaceHisSet.setOrderBy("UPDATETIME desc");
			ifaceHisSet.reset();
			IJpo ifaceHis = ifaceHisSet.getJpo(0);
			// 返回信息
			String msg = ifaceHis.getString("ERRORMSG");
			// 截取三包订单号
			sbNum = msg.substring(6, 16);

		}

		return sbNum;
	}

	/**
	 * 判断是否进行过出入库操作
	 * 
	 * @param type
	 *            出库 or 入库
	 * @param location
	 *            库房
	 * @param itemNum
	 *            物料编码
	 * @param orderNum
	 *            工单编号
	 * @return 是否已经进行过出入库操作
	 * @throws MroException
	 */
	public static boolean isStorageAction(String type, String location,
			String itemNum, String orderNum) throws MroException {
		boolean flag = false;
		// 出入库记录
		IJpoSet transRcdSet = MroServer.getMroServer().getSysJpoSet(
				"INVTRANS",
				"itemnum='" + itemNum + "' and storeroom='" + location
						+ "' and transtype='" + type + "' and tasknum='"
						+ orderNum + "'");

		if (transRcdSet != null && transRcdSet.count() > 0) {
			flag = true;
		}

		return flag;
	}

}
