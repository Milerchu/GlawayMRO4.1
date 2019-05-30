package com.glaway.sddq.tools.mobiletools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.material.transfer.data.Transfer;
import com.glaway.sddq.tools.HttpRequestHelper;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.mobiletools.mobileModel.MobileColumn;
import com.glaway.sddq.tools.mobiletools.mobileModel.MobileColumn.fieldType;

/**
 * 
 * 虚拟字段处理类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月10日]
 * @since [产品/模块版本]
 */
public class MobileField {

	private enum modifyStatus {
		READONLY, EDITABLE, REQUIRED
	}

	private static JSONObject transferMap;
	private static JSONObject transferLineMap;

	static {
		transferMap = initTransferMap();
		transferLineMap = initTransferLineMap();
	}

	public MobileResult INVENTORYMGR(String json) throws MroException {
		MobileResult result = new MobileResult();

		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("inventoryMgr");
		// 物料编码
		String itemNum = data.getString("itemNum");
		// 库存类型
		String erpLoc = data.getString("erpLoc");
		// 应用程序名称
		String appId = obj.getString("appId");
		if (itemNum == null || itemNum.isEmpty()) {
			throw new MroException("必填字段：物料编码 为空，请检查");
		}
		if (erpLoc == null || erpLoc.isEmpty()) {
			throw new MroException("必填字段：所属ERP工厂 为空，请检查");
		}
		// 返回数据集
		JSONObject res = new JSONObject();

		// 根据物料编码获取物料数据
		IJpoSet itemSet = MroServer.getMroServer().getSysJpoSet("SYS_ITEM");
		itemSet.setQueryWhere("itemNum='" + itemNum + "'");
		itemSet.reset();
		itemSet.setAppName(appId);
		if (itemSet.count() < 1) {
			throw new MroException("无法根据物料编码:" + itemNum + "获取" + erpLoc
					+ "库存数据");
		}

		IJpoSet inveSet = null;
		IJpo itemJpo = null;
		if ("1020".equals(erpLoc)) {
			itemSet.setAppName("INVENTORY");
			itemJpo = itemSet.getJpo();
			inveSet = itemJpo.getJpoSet("INVENTORY");
		} else if ("1030".equals(erpLoc)) {
			itemSet.setAppName("INV1030");
			itemJpo = itemSet.getJpo();
			inveSet = itemJpo.getJpoSet("INV1030");
		} else if ("改造物料库".equals(erpLoc)) {
			itemSet.setAppName("GZINV");
			itemJpo = itemSet.getJpo();
			inveSet = itemJpo.getJpoSet("GZINV");
		} else if ("其他待处理库".equals(erpLoc)) {
			itemSet.setAppName("QTINV");
			itemJpo = itemSet.getJpo();
			inveSet = itemJpo.getJpoSet("QTINV");
		}
		res.put("curbalTotal", itemJpo.getDouble("CURBALTOTAL"));

		if (inveSet != null) {
			JSONArray inveArr = new JSONArray();
			for (int i = 0; i < inveSet.count(); i++) {
				inveArr.add(getInvetory(inveSet.getJpo(i)));
			}
			res.put("invetory", inveArr);
		}
		/* copy完成 */

		result.setData(res);
		return result;
	}

	/**
	 * 
	 * 库房4个虚拟字段
	 * 
	 * @param json
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private JSONObject getInvetory(IJpo inventoryJpo) throws MroException {
		// 返回数据集
		JSONObject res = new JSONObject();
		res.put("location", inventoryJpo.getString("LOCATION"));
		res.put("jsztQty", inventoryJpo.getDouble("JSZTQTY"));
		res.put("fcztQty", inventoryJpo.getDouble("FCZTQTY"));
		res.put("kyQty", inventoryJpo.getDouble("KYQTY"));
		res.put("curbalTotal", inventoryJpo.getDouble("CURBALTOTAL"));

		return res;
	}

	public MobileResult ZXTRANSFER(String json) throws MroException {
		// 返回数据集
		MobileResult result = new MobileResult();
		JSONArray res = new JSONArray();
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("zxTransfer");
		// 装箱单编号
		String transfernum = data.getString("transferNum");
		IJpoSet transferSet = MroServer.getMroServer().getSysJpoSet("transfer",
				"transferNum='" + transfernum + "'");
		if (transferSet.count() > 0) {
			IJpoSet transferLineSet = transferSet.getJpo().getJpoSet(
					"transferline");
			// 装箱单行的每一行的未接受数量&装箱单行编号
			for (int i = 0; i < transferLineSet.count(); i++) {
				IJpo transferLine = transferLineSet.getJpo(i);
				JSONObject subObj = new JSONObject();
				subObj.put("transferLineNum",
						transferLine.getString("transferLineNum"));
				subObj.put("wjsQty", transferLine.getString("wjsQty"));
				res.add(subObj);
			}
		} else {
			throw new MroException("装箱单:" + transfernum + "对应装箱单行记录为空，请检查！");
		}

		result.setData(res);
		return result;
	}

	public MobileResult CONVERTLOC(String json) throws MroException {
		// 返回数据集
		MobileResult result = new MobileResult();
		JSONArray res = new JSONArray();

		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("convertLoc");
		// 装箱单编号
		String convertLocNum = data.getString("convertLocNum");
		IJpoSet convertSet = MroServer.getMroServer().getSysJpoSet(
				"CONVERTLOC", "convertLocNum='" + convertLocNum + "'");
		if (convertSet.count() > 0) {
			IJpoSet convertLineSet = convertSet.getJpo().getJpoSet(
					"CONVERTLOCNUMIX");
			// 调拨转库单行的每一行的未接受数量&调拨转库单行id
			for (int i = 0; i < convertLineSet.count(); i++) {
				IJpo convertLine = convertLineSet.getJpo(i);
				JSONObject subObj = new JSONObject();
				subObj.put("convertLocLineId",
						convertLine.getString("convertLocLineId"));
				subObj.put("wjsQty", convertLine.getString("wjsQty"));
				res.add(subObj);
			}
		} else {
			throw new MroException("调拨转库单:" + convertLocNum
					+ "对应调拨转库单行记录为空，请检查！");
		}

		result.setData(res);
		return result;
	}

	public MobileResult MATERREQ(String json) throws MroException {
		// 返回数据集
		MobileResult result = new MobileResult();
		JSONArray res = new JSONArray();

		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("materReq");
		// 配件申请单编号
		String mrNum = data.getString("mrNum");
		IJpoSet mrSet = MroServer.getMroServer().getSysJpoSet("MR",
				"MRNUM='" + mrNum + "'");
		mrSet.setAppName("MATERREQ");
		if (mrSet.count() > 0) {
			IJpoSet mrLineSet = mrSet.getJpo().getJpoSet("MRLINE");
			// 配件申请单行的每一行
			for (int i = 0; i < mrLineSet.count(); i++) {
				IJpo mrLine = mrLineSet.getJpo(i);
				JSONObject subObj = new JSONObject();
				subObj.put("mrLineId", mrLine.getString("mrLineId"));
				subObj.put("mrLineNum", mrLine.getString("mrLineNum"));
				subObj.put("mrLineId", mrLine.getString("mrLineId"));
				subObj.put("dcQty", mrLine.getString("dcQty"));
				subObj.put("dchwQty", mrLine.getString("dchwQty"));
				subObj.put("jcQty", mrLine.getString("jcQty"));
				subObj.put("jchwQty", mrLine.getString("jchwQty"));
				subObj.put("cgQty", mrLine.getString("cgQty"));
				subObj.put("cghwQty", mrLine.getString("cghwQty"));
				subObj.put("payQty", mrLine.getString("payQty"));
				res.add(subObj);
			}
		} else {
			throw new MroException("配件申请单:" + mrNum + "对应配件申请单行记录为空，请检查！");
		}

		result.setData(res);
		return result;
	}

	public MobileResult TRANSORDER(String json) throws MroException {
		// 返回数据集
		MobileResult result = new MobileResult();
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("transOrder");

		String orderNum = data.getString("orderNum");
		IJpoSet thisOrderSet = MroServer.getMroServer().getSysJpoSet(
				"workorder", "orderNum='" + orderNum + "'");
		if (thisOrderSet.isEmpty()) {
			throw new MroException("无法根据工单编号：" + orderNum + "查询到对应改造工单");
		}
		IJpo order = thisOrderSet.getJpo(0);
		String plannum = order.getString("plannum");
		String whichstation = order.getString("whichstation");
		IJpoSet distSet = MroServer.getMroServer().getSysJpoSet(
				"TRANSDIST",
				"TRANSPLANNUM='" + plannum + "' and station='" + whichstation
						+ "'");
		if (distSet.isEmpty()) {
			throw new MroException("无法根据工单编号：" + orderNum + "查询到对应改造范围记录");
		}
		IJpoSet orderSet = distSet.getJpo(0).getJpoSet("TRANSORDER");
		// 无是首台车的，或该工单是首台车工单，可编辑首台车、首台车改造情况
		result.setData(true);
		orderSet.setQueryWhere("ISFIRST='" + 1 + "'");
		orderSet.reset();
		if (!orderSet.isEmpty()) {
			if (!(orderSet.getJpo(0).getId() == order.getId())) {
				result.setData(false);
			}
		}
		return result;
	}

	/**
	 * 装箱单 各字段可编辑情况
	 * 
	 * @param json
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public MobileResult ZXTRANSFERMODIFY(String json) throws MroException {
		// 返回数据集
		MobileResult result = new MobileResult();
		JSONObject obj = new JSONObject(true);
		obj = JSONObject.parseObject(json, Feature.OrderedField);
		String loginid = obj.getString("userId");
		JSONObject ret = new JSONObject(true);
		JSONArray transfeRetArr = new JSONArray();
		JSONObject transfeData = new JSONObject();
		JSONObject data = obj.getJSONObject("data");
		JSONObject transferObj = new JSONObject(true);
		transferObj = data.getJSONObject("transfer");
		String transferNum = transferObj.containsKey("transferNum") ? transferObj
				.getString("transferNum") : "";
		IJpoSet transferSet = MroServer.getMroServer().getSysJpoSet("transfer");
		transferSet.setAppName("ZXTRANSFER");
		transferSet.getUserServer().getUserInfo().setLoginID(loginid);
		transferSet.getUserServer().getUserInfo().setLoginUserName(loginid);
		Transfer transfer;
		if (StringUtil.isStrEmpty(transferNum)) {
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultOrg("CRRC");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultSite("ELEC");
			transfer = (Transfer) transferSet.addJpo();
			transfer.setValue("STATUS", "未处理");
			transfer.setValue("TYPE", "ZXD");
			transfer.setValue("CREATEDATE", MroServer.getMroServer().getDate());
			transfer.setValue("CREATEBY", loginid, GWConstant.P_NOVALIDATION);
		} else {
			transferSet.setQueryWhere("transferNum='" + transferNum + "'");
			transferSet.reset();
			transfer = (Transfer) transferSet.getJpo();
		}
		// 强制赋值还原当前Jpo数据情况
		for (Entry<String, Object> attr : transferObj.entrySet()) {
			transfer.setValue(attr.getKey(), attr.getValue().toString(),
					GWConstant.P_NOVALIDATION);
		}
		// add by ZHUHAO ,190530 start
		transferSet.save();

		transferSet.setQueryWhere("transferNum='" + transferNum + "'");
		transferSet.reset();
		transfer = (Transfer) transferSet.getJpo();
		// end

		setTransferLookUp(transfer);
		// oneAttr是装箱单一个字段，要使用驼峰命名，与前端保持一致
		for (String oneAttr : transferMap.keySet()) {
			transfeRetArr.add(getColumnStatus(transfer,
					(MobileColumn) transferMap.get(oneAttr)));
			transfeData.put(oneAttr, transfer.getString(oneAttr));
		}
		ret.put("transfer", transfeRetArr);
		ret.put("transferData", transfeData);

		if (data.containsKey("transferLine")) {
			JSONArray transfeLineRetArr = new JSONArray();
			JSONObject lineData = data.getJSONObject("transferLine");
			String transferLineNum = lineData.containsKey("transferLineNum") ? lineData
					.getString("transferLineNum") : "";
			IJpoSet transferLineSet = transfer.getJpoSet("transferLine");
			IJpo transferLine;
			if (StringUtil.isStrEmpty(transferLineNum)) {
				transferLine = transferLineSet.addJpo();
				transferLine.setValue("TRANSFERNUM",
						transfer.getString("TRANSFERNUM"),
						GWConstant.P_NOVALIDATION);
				transferLine.setValue("RECEIVESTOREROOM",
						transfer.getString("RECEIVESTOREROOM"),
						GWConstant.P_NOVALIDATION);
				transferLine.setValue("RECEIVEADDRESS",
						transfer.getString("RECEIVEADDRESS"),
						GWConstant.P_NOVALIDATION);
				transferLine.setValue("ISSUESTOREROOM",
						transfer.getString("ISSUESTOREROOM"),
						GWConstant.P_NOVALIDATION);
				transferLine.setValue("ISSUEADDRESS",
						transfer.getString("ISSUEADDRESS"),
						GWConstant.P_NOVALIDATION);
				transferLine.setValue("PROJECTNUM",
						transfer.getString("PROJECTNUM"),
						GWConstant.P_NOVALIDATION);
				transferLine.setValue("SXTYPE", transfer.getString("SXTYPE"),
						GWConstant.P_NOVALIDATION);
			} else {
				transferLineSet.setQueryWhere("transferLineNum='"
						+ transferLineNum + "'");
				transferLine = transferLineSet.getJpo();
			}
			for (Entry<String, Object> attr : lineData.entrySet()) {
				transferLine.setValue(attr.getKey(),
						attr.getValue().toString(), GWConstant.P_NOVALIDATION);
			}
			setTransferLineLookUp(transferLine);
			// oneAttr是装箱单一个字段，要使用驼峰命名，与前端保持一致
			for (String oneAttr : transferLineMap.keySet()) {
				JSONObject colObj = getColumnStatus(transferLine,
						(MobileColumn) transferLineMap.get(oneAttr));
				transfeLineRetArr.add(colObj);
			}
			ret.put("transfeLine", transfeLineRetArr);
			JSONObject transferLineData = new JSONObject();
			transferLineData.put("taskNum", transferLine.getString("taskNum"));
			transferLineData.put("issueStoreRoom",
					transferLine.getString("issueStoreRoom"));
			transferLineData.put("status", transferLine.getString("status"));
			transferLineData.put("wjsQty", transferLine.getString("wjsQty"));
			transferLineData.put("receiveStoreRoom",
					transferLine.getString("receiveStoreRoom"));
			ret.put("transferLineData", transferLineData);
		}

		// 装箱单行是否可创建
		boolean isCreateLine = true;
		String CREATEBY = transfer.getString("CREATEBY");
		String TRANSFERMOVETYPE = transfer.getString("TRANSFERMOVETYPE");
		String SXTYPE = transfer.getString("SXTYPE");
		String ISSUESTOREROOM = transfer.getString("ISSUESTOREROOM");
		String RECEIVESTOREROOM = transfer.getString("RECEIVESTOREROOM");
		String status = transfer.getString("status");
		String ISMR = transfer.getString("ISMR");
		if (ISMR.equalsIgnoreCase("是")) {
			isCreateLine = false;
		}
		if (!TRANSFERMOVETYPE.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
			if (RECEIVESTOREROOM.isEmpty()) {
				isCreateLine = false;
			}
		} else {
			// if (SXTYPE.isEmpty()) {
			// isCreateLine = false;
			// } else {
			if (RECEIVESTOREROOM.isEmpty()) {
				isCreateLine = false;
			}
			// }
		}
		if (!TRANSFERMOVETYPE.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
			if (ISSUESTOREROOM.isEmpty()) {
				isCreateLine = false;
			}
		}
		if (!status.equalsIgnoreCase("未处理")
				&& !status.equalsIgnoreCase("申请人修改")) {
			isCreateLine = false;
		} else {
			if (!loginid.equalsIgnoreCase(CREATEBY)) {
				isCreateLine = false;
			}
		}
		ret.put("isCreateLine", isCreateLine);
		result.setData(ret);
		return result;
	}

	private static JSONObject initTransferLineMap() {
		JSONObject attrMap = new JSONObject(true);
		MobileColumn transferLineNum = new MobileColumn("transferLineNum",
				fieldType.TEXT, "", new String[] {}, "装箱单行编号");
		transferLineNum.setEnableEdit(false);
		MobileColumn issueStoreRoom = new MobileColumn("issueStoreRoom",
				fieldType.SELECT, "description", new String[] { "location",
						"description", "erpLoc", "storeRoomLevel",
						"locationType" }, "发出库房编号");
		MobileColumn issueStoreRoom_description = new MobileColumn(
				"issueStoreRoom.description", fieldType.TEXT, "",
				new String[] {}, "发出库房名称");
		issueStoreRoom_description.setEnableEdit(false);
		MobileColumn issueAddress = new MobileColumn("issueAddress",
				fieldType.SELECT, "address", new String[] { "address" }, "发出地址");
		MobileColumn taskNum = new MobileColumn("taskNum", fieldType.SELECT,
				"orderNum",
				new String[] { "orderNum", "projectInfo.projectName", "carNum",
						"models.modelDesc" }, "故障工单编号");
		MobileColumn itemNum = new MobileColumn("itemNum", fieldType.SELECT,
				"itemNum", new String[] { "itemNum", "item.description",
						"item.lotType", "curbal", "locQty", "zxdQty",
						"faultQty", "testingQty", "disposeQty" }, "物料编码");
		MobileColumn item_description = new MobileColumn("item.description",
				fieldType.TEXT, "", new String[] {}, "物料描述");
		item_description.setEnableEdit(false);
		MobileColumn sqn = new MobileColumn("sqn", fieldType.SELECT, "sqn",
				new String[] { "sqn", "itemNum", "item.description" }, "序列号");
		MobileColumn lotNum = new MobileColumn("lotNum", fieldType.SELECT,
				"lotNum", new String[] { "lotNum", "physcntQty", "kyQty" },
				"批次号");
		MobileColumn outBinNum = new MobileColumn("outBinNum",
				fieldType.SELECT, "binNum", new String[] { "binNum",
						"shelvesType", "description" }, "出库仓位");
		MobileColumn inBinNum = new MobileColumn("inBinNum", fieldType.SELECT,
				"binNum",
				new String[] { "binNum", "shelvesType", "description" }, "入库仓位");
		MobileColumn orderQty = new MobileColumn("orderQty", fieldType.TEXT,
				"", new String[] {}, "调拨数量");
		MobileColumn item_orderUnit_description = new MobileColumn(
				"item.orderUnit.description", fieldType.TEXT, "",
				new String[] {}, "基本计量单位");
		MobileColumn failureDesc = new MobileColumn("failureDesc",
				fieldType.TEXTAREA, "", new String[] {}, "故障描述");
		MobileColumn receiveStoreRoom = new MobileColumn("receiveStoreRoom",
				fieldType.TEXT, "", new String[] {}, "接收库房编号");
		receiveStoreRoom.setEnableEdit(false);
		MobileColumn receiveStoreRoom_description = new MobileColumn(
				"receiveStoreRoom.description", fieldType.TEXT, "",
				new String[] {}, "接收库房名称");
		receiveStoreRoom_description.setEnableEdit(false);
		MobileColumn receiveAddress = new MobileColumn("receiveAddress",
				fieldType.TEXT, "", new String[] {}, "接收地址");
		receiveAddress.setEnableEdit(false);
		MobileColumn isJsfx = new MobileColumn("isJsfx", fieldType.SWITCH, "",
				new String[] { "value" }, "是否转技术分析");
		MobileColumn sxType = new MobileColumn("sxType", fieldType.SELECT,
				"value", new String[] { "value", "description" }, "修造类别");
		sxType.setEnableEdit(false);
		MobileColumn sxType_description = new MobileColumn(
				"sxType.description", fieldType.TEXT, "value", new String[] {},
				"修造类别描述");
		sxType_description.setEnableEdit(false);
		MobileColumn model = new MobileColumn("model", fieldType.SELECT,
				"modelDesc", new String[] { "modelNum", "modelDesc",
						"productLine", "modelType", "modelCode" }, "车型");
		MobileColumn model_modelDesc = new MobileColumn("models.modelDesc",
				fieldType.TEXT, "modelDesc", new String[] {}, "车型描述");
		model_modelDesc.setEnableEdit(false);
		MobileColumn projectNum = new MobileColumn("projectNum",
				fieldType.SELECT, "projectNum", new String[] { "projectNum",
						"projectName" }, "项目编号");
		MobileColumn projectInfo_projectName = new MobileColumn(
				"projectInfo.projectName", fieldType.TEXT, "projectNum",
				new String[] {}, "项目名称");
		projectInfo_projectName.setEnableEdit(false);
		MobileColumn projectInfo_workOrderNum = new MobileColumn(
				"projectInfo.workOrderNum", fieldType.TEXT, "",
				new String[] {}, "工作令号");
		projectInfo_workOrderNum.setEnableEdit(false);
		MobileColumn repairProcess = new MobileColumn("repairProcess",
				fieldType.TEXT, "", new String[] {}, "返修次数");
		MobileColumn dealType = new MobileColumn("dealType", fieldType.SELECT,
				"", new String[] { "value" }, "处理方案");
		MobileColumn failureCons = new MobileColumn("failureCons",
				fieldType.TEXT, "", new String[] {}, "故障后果");
		failureCons.setEnableEdit(false);
		MobileColumn returnType = new MobileColumn("returnType",
				fieldType.SELECT, "", new String[] { "value" }, "驳回原因");
		MobileColumn memo = new MobileColumn("memo", fieldType.TEXTAREA, "",
				new String[] {}, "备注");

		attrMap.put("transferLineNum", transferLineNum);
		attrMap.put("issueStoreRoom", issueStoreRoom);
		attrMap.put("issueStoreRoom.description", issueStoreRoom_description);
		attrMap.put("issueAddress", issueAddress);
		attrMap.put("taskNum", taskNum);
		attrMap.put("itemNum", itemNum);
		attrMap.put("item.description", item_description);
		attrMap.put("sqn", sqn);
		attrMap.put("lotNum", lotNum);
		attrMap.put("outBinNum", outBinNum);
		attrMap.put("inBinNum", inBinNum);
		attrMap.put("orderQty", orderQty);
		attrMap.put("item.orderUnit.description", item_orderUnit_description);
		attrMap.put("failureDesc", failureDesc);
		attrMap.put("receiveStoreRoom", receiveStoreRoom);
		attrMap.put("receiveStoreRoom.description",
				receiveStoreRoom_description);
		attrMap.put("receiveAddress", receiveAddress);
		attrMap.put("isJsfx", isJsfx);
		attrMap.put("sxType", sxType);
		attrMap.put("sxType.description", sxType_description);
		attrMap.put("model", model);
		attrMap.put("model.modelDesc", model_modelDesc);
		attrMap.put("projectNum", projectNum);
		attrMap.put("projectInfo.projectName", projectInfo_projectName);
		attrMap.put("projectInfo.workOrderNum", projectInfo_workOrderNum);
		attrMap.put("repairProcess", repairProcess);
		attrMap.put("dealType", dealType);
		attrMap.put("failureCons", failureCons);
		attrMap.put("returnType", returnType);
		attrMap.put("memo", memo);
		return attrMap;
	}

	/**
	 * 根据jpo、字段名获取该字段可编辑状态、当前值公用方法
	 * 
	 * @param jpo
	 * @param col
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private JSONObject getColumnStatus(IJpo jpo, MobileColumn col)
			throws MroException {
		JSONObject sub = new JSONObject(true);
		JpoField thisField = jpo.getField(col.getAttribute());
		sub.put("attribute", col.getAttribute());
		sub.put("value", thisField.getValue());
		sub.put("type", col.getType());
		sub.put("description", col.getDescription());
		// 可编辑状态
		if (thisField.isReadonly()) {
			sub.put("status", modifyStatus.READONLY);
		} else if (!col.isEnableEdit()) {
			sub.put("status", modifyStatus.READONLY);
		} else if (thisField.isRequired()) {
			sub.put("status", modifyStatus.REQUIRED);
		} else {
			sub.put("status", modifyStatus.EDITABLE);
		}
		return sub;
	}

	private static JSONObject initTransferMap() {
		JSONObject attrMap = new JSONObject(true);// true表示有序
		MobileColumn transferNum = new MobileColumn("transferNum",
				fieldType.TEXT, "value", new String[] {}, "装箱单编号");
		transferNum.setEnableEdit(false);
		MobileColumn transferMoveType = new MobileColumn("transferMoveType",
				fieldType.SELECT, "value", new String[] { "value",
						"description" }, "移动类型");
		MobileColumn isFxfy = new MobileColumn("isFxfy", fieldType.SWITCH, "",
				new String[] { "value", "description" }, "是否返修发运");
		MobileColumn isMr = new MobileColumn("isMr", fieldType.SWITCH, "",
				new String[] { "value", "description" }, "关联配件申请");
		MobileColumn mrNum = new MobileColumn("mrNum", fieldType.SELECT,
				"mrNum", new String[] { "mrNum", "mrType", "receiveStoreRoom",
						"receiveStoreRoom.description" }, "配件申请单号");
		MobileColumn sxType = new MobileColumn("sxType", fieldType.SELECT,
				"value", new String[] { "value", "description" }, "修造类别");
		MobileColumn sxType_description = new MobileColumn(
				"sxType.description", fieldType.TEXT, "value", new String[] {},
				"修造类别描述");
		sxType_description.setEnableEdit(false);
		MobileColumn projectNum = new MobileColumn("projectNum",
				fieldType.SELECT, "projectNum", new String[] { "projectNum",
						"projectName" }, "项目编号");
		MobileColumn project_projectName = new MobileColumn(
				"project.projectName", fieldType.TEXT, "projectNum",
				new String[] {}, "项目名称");
		project_projectName.setEnableEdit(false);
		MobileColumn project_workOrderNum = new MobileColumn(
				"project.workOrderNum", fieldType.SELECT, "", new String[] {},
				"工作令号");
		MobileColumn createBy_displayName = new MobileColumn(
				"createBy.displayName", fieldType.TEXT, "", new String[] {},
				"制单人姓名");
		createBy_displayName.setEnableEdit(false);
		MobileColumn createDate = new MobileColumn("createDate",
				fieldType.DATETIME, "", new String[] { "" }, "制单时间");
		createDate.setEnableEdit(false);
		MobileColumn sendTime = new MobileColumn("sendTime", fieldType.TEXT,
				"", new String[] {}, "发出日期");
		sendTime.setEnableEdit(false);
		MobileColumn status = new MobileColumn("status", fieldType.TEXT, "",
				new String[] {}, "状态");
		status.setEnableEdit(false);
		MobileColumn memo = new MobileColumn("memo", fieldType.TEXTAREA, "",
				new String[] {}, "备注");
		MobileColumn issueStoreRoom = new MobileColumn("issueStoreRoom",
				fieldType.SELECT, "description", new String[] { "location",
						"description", "erpLoc", "storeRoomLevel",
						"locationType" }, "发出库房编号");
		MobileColumn issueStoreRoom_description = new MobileColumn(
				"issueStoreRoom.description", fieldType.TEXT, "",
				new String[] {}, "发出库房名称");
		issueStoreRoom_description.setEnableEdit(false);
		MobileColumn issueAddress = new MobileColumn("issueAddress",
				fieldType.SELECT, "address", new String[] { "address" }, "发出地址");
		MobileColumn packBy = new MobileColumn("packBy", fieldType.SELECT,
				"displayName", new String[] { "personId", "displayName",
						"post.postName", "dept.description" }, "装箱人ID");
		MobileColumn packBy_displayName = new MobileColumn(
				"packBy.displayName", fieldType.TEXT, "", new String[] {},
				"装箱人姓名");
		packBy_displayName.setEnableEdit(false);
		MobileColumn checkBy = new MobileColumn("checkBy", fieldType.SELECT,
				"displayName", new String[] { "personId", "displayName",
						"post.postName", "dept.description" }, "核对人ID");
		MobileColumn checkBy_displayName = new MobileColumn(
				"checkBy.displayName", fieldType.TEXT, "", new String[] {},
				"核对人姓名");
		checkBy_displayName.setEnableEdit(false);
		MobileColumn sendBy = new MobileColumn("sendBy", fieldType.SELECT,
				"displayName", new String[] { "personId", "displayName",
						"post.postName", "dept.description" }, "发运人ID");
		MobileColumn sendBy_displayName = new MobileColumn(
				"sendBy.displayName", fieldType.TEXT, "", new String[] {},
				"发运人姓名");
		sendBy_displayName.setEnableEdit(false);
		MobileColumn sendBy_primaryPhone = new MobileColumn(
				"sendBy.primaryPhone", fieldType.TEXT, "", new String[] {},
				"发运人联系电话");
		sendBy_primaryPhone.setEnableEdit(false);
		MobileColumn packDate = new MobileColumn("packDate",
				fieldType.DATETIME, "", new String[] {}, "装箱日期");
		MobileColumn courierCompany = new MobileColumn("courierCompany",
				fieldType.SELECT, "value", new String[] { "value" }, "快运公司");
		MobileColumn courierNum = new MobileColumn("courierNum",
				fieldType.TEXT, "", new String[] { "" }, "运单编号");
		MobileColumn receiveStoreRoom = new MobileColumn("receiveStoreRoom",
				fieldType.SELECT, "description", new String[] { "location",
						"description", "erpLoc", "storeRoomLevel",
						"locationType" }, "接收库房编号");
		MobileColumn receiveStoreRoom_description = new MobileColumn(
				"receiveStoreRoom.description", fieldType.TEXT, "",
				new String[] {}, "接收库房名称");
		receiveStoreRoom_description.setEnableEdit(false);
		MobileColumn receiveAddress = new MobileColumn("receiveAddress",
				fieldType.SELECT, "address", new String[] {}, "接收地址");
		MobileColumn receiveBy = new MobileColumn("receiveBy",
				fieldType.SELECT, "displayName", new String[] { "personId",
						"displayName", "post.postName", "dept.description" },
				"收货人ID");
		MobileColumn receiveBy_displayName = new MobileColumn(
				"receiveBy.displayName", fieldType.TEXT, "", new String[] {},
				"收货人名称");
		receiveBy_displayName.setEnableEdit(false);
		MobileColumn receiveBy_primaryPhone = new MobileColumn(
				"receiveBy.primaryPhone", fieldType.TEXT, "", new String[] {},
				"收货人联系电话");
		receiveBy_primaryPhone.setEnableEdit(false);
		MobileColumn openBy = new MobileColumn("openBy", fieldType.SELECT,
				"displayName", new String[] { "personId", "displayName",
						"post.postName", "dept.description" }, "接收开箱人ID");
		MobileColumn openBy_displayName = new MobileColumn(
				"openBy.displayName", fieldType.TEXT, "", new String[] {},
				"接收开箱人名称");
		openBy_displayName.setEnableEdit(false);
		MobileColumn receiveDate = new MobileColumn("receiveDate",
				fieldType.DATETIME, "", new String[] {}, "接收日期");
		MobileColumn receiveCheckBy = new MobileColumn("receiveCheckBy",
				fieldType.SELECT, "displayName", new String[] { "personId",
						"displayName", "post.postName", "dept.description" },
				"接收核对人ID");
		MobileColumn receiveCheckBy_displayName = new MobileColumn(
				"receiveCheckBy.displayName", fieldType.TEXT, "",
				new String[] {}, "接收核对人名称");
		receiveCheckBy_displayName.setEnableEdit(false);

		attrMap.put("transferNum", transferNum);
		attrMap.put("transferMoveType", transferMoveType);
		attrMap.put("isFxfy", isFxfy);
		attrMap.put("isMr", isMr);
		attrMap.put("mrNum", mrNum);
		attrMap.put("sxType", sxType);
		attrMap.put("sxType.description", sxType_description);
		attrMap.put("projectNum", projectNum);
		attrMap.put("project.projectName", project_projectName);
		attrMap.put("project.workOrderNum", project_workOrderNum);
		attrMap.put("createBy.displayName", createBy_displayName);
		attrMap.put("createDate", createDate);
		attrMap.put("sendTime", sendTime);
		attrMap.put("status", status);
		attrMap.put("memo", memo);
		attrMap.put("issueStoreRoom", issueStoreRoom);
		attrMap.put("issueStoreRoom.description", issueStoreRoom_description);
		attrMap.put("issueAddress", issueAddress);
		attrMap.put("packBy", packBy);
		attrMap.put("packBy.displayName", packBy_displayName);
		attrMap.put("checkBy", checkBy);
		attrMap.put("checkBy.displayName", checkBy_displayName);
		attrMap.put("sendBy", sendBy);
		attrMap.put("sendBy.displayName", sendBy_displayName);
		attrMap.put("sendBy.primaryPhone", sendBy_primaryPhone);
		attrMap.put("packDate", packDate);
		attrMap.put("courierCompany", courierCompany);
		attrMap.put("courierNum", courierNum);
		attrMap.put("receiveStoreRoom", receiveStoreRoom);
		attrMap.put("receiveStoreRoom.description",
				receiveStoreRoom_description);
		attrMap.put("receiveAddress", receiveAddress);
		attrMap.put("receiveBy", receiveBy);
		attrMap.put("receiveBy.displayName", receiveBy_displayName);
		attrMap.put("receiveBy.primaryPhone", receiveBy_primaryPhone);
		attrMap.put("openBy", openBy);
		attrMap.put("openBy.displayName", openBy_displayName);
		attrMap.put("receiveDate", receiveDate);
		attrMap.put("receiveCheckBy", receiveCheckBy);
		attrMap.put("receiveCheckBy.displayName", receiveCheckBy_displayName);

		return attrMap;
	}

	/**
	 * 装箱单 getList列表查询
	 * 
	 * @param json
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public MobileResult ZXTRANSFERLIST(String json) throws MroException {
		// 返回数据集
		MobileResult result = new MobileResult();
		JSONObject obj = JSONObject.parseObject(json, Feature.OrderedField);
		JSONObject data = obj.getJSONObject("data");
		// 请求参数
		String loginid = obj.getString("userId");
		String searchValue = data.getString("searchValue");
		String searchType = data.getString("searchType");
		String searchTable = data.getString("searchTable");
		String pageSizeStr = obj.getString("pageSize");
		int pageSize = Integer.parseInt(pageSizeStr);
		String pageNumStr = obj.getString("pageNum");
		int pageNum = Integer.parseInt(pageNumStr);
		JpoField thisField;
		IJpo transfer = null;
		IJpoSet transferSet = null;

		if (data.containsKey("transfer")) {
			JSONObject transferObj = data.getJSONObject("transfer");
			String transferNum = transferObj.containsKey("transferNum") ? transferObj
					.getString("transferNum") : "";
			transferSet = MroServer.getMroServer().getSysJpoSet("transfer");
			transferSet.setAppName("ZXTRANSFER");
			transferSet.getUserServer().getUserInfo().setLoginID(loginid);
			transferSet.getUserServer().getUserInfo().setLoginUserName(loginid);
			if (StringUtil.isStrEmpty(transferNum)) {
				MroServer.getMroServer().getSystemUserServer().getUserInfo()
						.setDefaultOrg("CRRC");
				MroServer.getMroServer().getSystemUserServer().getUserInfo()
						.setDefaultSite("ELEC");
				transfer = transferSet.addJpo();
				transfer.setValue("STATUS", "未处理");
				transfer.setValue("TYPE", "ZXD");
				transfer.setValue("CREATEDATE", MroServer.getMroServer()
						.getDate());
				transfer.setValue("CREATEBY", loginid,
						GWConstant.P_NOVALIDATION);
			} else {
				transferSet.setQueryWhere("transferNum='" + transferNum + "'");
				transferSet.reset();
				transfer = transferSet.getJpo();
			}
			for (Entry<String, Object> attr : transferObj.entrySet()) {
				transfer.setValue(attr.getKey(), attr.getValue().toString(),
						GWConstant.P_NOVALIDATION);
			}
			setTransferLookUp(transfer);
			thisField = transfer.getField(searchType);
		} else {
			JSONObject sub = new JSONObject();
			sub.put("status", modifyStatus.READONLY);
			sub.put("msg", "请求数据不完整");
			JSONObject ret = new JSONObject();
			ret.put("info", sub);
			result.setData(ret);
			return result;
		}

		if (data.containsKey("transferLine")
				&& "transferLine".equalsIgnoreCase(searchTable)) {
			JSONArray transfeLineRetArr = new JSONArray();
			JSONObject lineData = data.getJSONObject("transferLine");
			String transferLineNum = lineData.containsKey("transferLineNum") ? lineData
					.getString("transferLineNum") : "";
			IJpoSet transferLineSet = transfer.getJpoSet("transferLine");
			IJpo transferLine;
			if (StringUtil.isStrEmpty(transferLineNum)) {
				transferLine = transferLineSet.addJpo();
				transferLine.setValue("TRANSFERNUM",
						transfer.getString("TRANSFERNUM"),
						GWConstant.P_NOVALIDATION);
				transferLine.setValue("RECEIVESTOREROOM",
						transfer.getString("RECEIVESTOREROOM"),
						GWConstant.P_NOVALIDATION);
				transferLine.setValue("RECEIVEADDRESS",
						transfer.getString("RECEIVEADDRESS"),
						GWConstant.P_NOVALIDATION);
				transferLine.setValue("ISSUESTOREROOM",
						transfer.getString("ISSUESTOREROOM"),
						GWConstant.P_NOVALIDATION);
				transferLine.setValue("ISSUEADDRESS",
						transfer.getString("ISSUEADDRESS"),
						GWConstant.P_NOVALIDATION);
				transferLine.setValue("PROJECTNUM",
						transfer.getString("PROJECTNUM"),
						GWConstant.P_NOVALIDATION);
				transferLine.setValue("SXTYPE", transfer.getString("SXTYPE"),
						GWConstant.P_NOVALIDATION);
			} else {
				transferLineSet.setQueryWhere("transferLineNum='"
						+ transferLineNum + "'");
				transferLine = transferLineSet.getJpo();
				JSONObject numObj = getColumnStatus(transferLine,
						(MobileColumn) transferLineMap.get("transferLineNum"));
				numObj.put("status", modifyStatus.READONLY);
				transfeLineRetArr.add(numObj);
			}
			for (Entry<String, Object> attr : lineData.entrySet()) {
				transferLine.setValue(attr.getKey(),
						attr.getValue().toString(), GWConstant.P_NOVALIDATION);
			}
			setTransferLineLookUp(transferLine);
			thisField = transferLine.getField(searchType);
		}
		// 根据查询的表获取查询字段
		MobileColumn searchCol;
		if ("transferLine".equalsIgnoreCase(searchTable)) {
			searchCol = (MobileColumn) transferLineMap.get(searchType);
		} else {
			searchCol = (MobileColumn) transferMap.get(searchType);
		}

		// 返回数据中字段信息部分
		JSONObject sub = new JSONObject();
		sub.put("attribute", searchType);
		// 可编辑状态
		if (thisField.isReadonly()) {
			sub.put("status", modifyStatus.READONLY);
			sub.put("msg", "不可编辑该字段");
			JSONObject ret = new JSONObject();
			ret.put("info", sub);
			result.setData(ret);
			return result;
		} else if (thisField.isRequired()) {
			sub.put("status", modifyStatus.REQUIRED);
		} else {
			sub.put("status", modifyStatus.EDITABLE);
		}

		IJpoSet listSet = null;
		JSONArray listArr = new JSONArray();
		try {
			listSet = thisField.getList();
		} catch (MroException e) {
			sub.put("msg", e.getMessage());
			JSONObject ret = new JSONObject();
			ret.put("info", sub);
			result.setData(ret);
			return result;
		}
		sub.put("value", thisField.getValue());
		String valueCol = thisField.getLookupMap()
				.get(thisField.getFieldName());
		if (StringUtil.isStrEmpty(valueCol)) {
			valueCol = "";
		}

		// 该方法直接拼出like条件
		listSet.addAppFilter(searchCol.getSearchColumn(), searchValue);
		listSet.reset();
		sub.put("count", listSet.count());
		JSONObject ret = new JSONObject(true);
		ret.put("info", sub);

		// 分页
		if (pageSize != 0 && pageNum != 0) {
			try {
				// 请求地址
				String requierUrl = IFUtil.getIfServiceInfo("mobile.url");
				// 请求参数
				StringBuffer sb = new StringBuffer();
				sb.append("from " + listSet.getName());
				sb.append(" where "
						+ (StringUtil.isStrNotEmpty(listSet.getUserWhere()) ? listSet
								.getUserWhere() : "1=1"));
				sb.append(" and "
						+ (StringUtil.isStrNotEmpty(listSet.getQueryWhere()) ? listSet
								.getQueryWhere() : "1=1"));

				JSONObject requestObj = new JSONObject();
				requestObj.put("userId", loginid);
				requestObj.put("pageNum", pageNumStr);
				requestObj.put("pageSize", pageSizeStr);
				JSONObject sqlObj = new JSONObject();
				sqlObj.put("sql", URLEncoder.encode(sb.toString(), "utf-8"));// 可能有百分号，需要转码
				requestObj.put("data", sqlObj);
				String afterPage = HttpRequestHelper.sendGet(
						requierUrl,
						"methodName=CommonQuery&parameter="
								+ requestObj.toString(), "utf-8");
				if (StringUtil.isStrNotEmpty(afterPage)) {
					JSONObject pageObj = JSONObject.parseObject(afterPage);
					if ("200".equals(pageObj.getString("code"))) {
						// 列表数组
						JSONArray pagedList = pageObj.getJSONArray("data");
						// 重组列表
						for (int i = 0; i < pagedList.size(); i++) {
							JSONObject oneRow = pagedList.getJSONObject(i);
							JSONObject oneLookObj = new JSONObject();
							// for(String
							// oneLookKey:searchCol.getLookColumns()){
							// String oneLookValue =
							// oneRow.getString(oneLookKey.toUpperCase());//
							// 返回的json中key全部是大写
							// oneLookObj.put(oneLookKey, oneLookValue);
							// }
							String[] cols = searchCol.getLookColumns();
							if (cols.length > 1) {
								if (null == oneRow.getString(cols[1]
										.toUpperCase())) {
									thisField.setValue(oneRow.getString(cols[0]
											.toUpperCase()));
									oneLookObj.put(
											"description",
											thisField.getJpo().getString(
													cols[1].toUpperCase()));
								} else {
									oneLookObj.put("description", oneRow
											.getString(cols[1].toUpperCase()));
								}
							} else {
								oneLookObj.put("description", "");
							}
							oneLookObj.put("value",
									oneRow.getString(cols[0].toUpperCase()));
							// if(!oneLookObj.containsKey("value")||StringUtil.isStrEmpty(oneLookObj.getString("value"))){
							// oneLookObj.put("value",
							// oneRow.getString(valueCol.toUpperCase()));
							// }else{
							// oneLookObj.put("value","");
							// }
							listArr.add(oneLookObj);
						}
					} else {
						sub.put("msg", "分页请求失败：" + pageObj.getString("msg"));
						ret.put("info", sub);
						result.setData(ret);
						return result;
					}

				}
			} catch (UnsupportedEncodingException e) {
				throw new MroException("分页请求失败：" + e.getMessage());
			} catch (IOException e) {
				throw new MroException("分页请求失败：" + e.getMessage());
			}
		}
		ret.put("list", listArr);
		result.setData(ret);
		return result;
	}

	/**
	 * 装箱单的字段类init中有lookup的 <功能描述>
	 * 
	 * @param transfer
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private void setTransferLookUp(IJpo transfer) throws MroException {
		transfer.setValue("RECEIVEPHONE",
				transfer.getString("receiveStoreRoom.RECEIVEPHONE"),
				GWConstant.P_NOVALIDATION);
	}

	/**
	 * 装箱单行的字段类init中有lookup的 <功能描述>
	 * 
	 * @param transfer
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private void setTransferLineLookUp(IJpo transfer) throws MroException {
		String ordernum = transfer.getString("tasknum");
		if (StringUtil.isStrNotEmpty(ordernum)) {
			IJpo order = MroServer.getMroServer()
					.getSysJpoSet("workorder", "ordernum='" + ordernum + "'")
					.getJpo();
			transfer.setValue("QMSNUM", order.getString("QMS_NUM"));
		}
	}
}
