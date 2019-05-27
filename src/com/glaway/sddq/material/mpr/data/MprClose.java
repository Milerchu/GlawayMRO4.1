package com.glaway.sddq.material.mpr.data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient3.HttpTransportPropertiesImpl.Authenticator;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;
/*正式接口调用*/
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Bapi2017GmItemCreate;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char1;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char10;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char12;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char14;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char15;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char16;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char18;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char2;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char20;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char24;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char25;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char3;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char32;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char4;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char40;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char50;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char6;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Char8;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Date;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Decimal234;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Decimal30;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Numeric10;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Numeric2;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Numeric3;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Numeric4;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Numeric5;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Numeric6;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Quantum133;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.TableOfBapi2017GmItemCreate;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Unit3;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunction;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunctionResponse;
/*正式接口调用*/
import com.glaway.sddq.material.invtrans.common.InventoryQtyCommon;
import com.glaway.sddq.material.invtrans.common.ItemInvtransCommon;
import com.glaway.sddq.tools.IFUtil;

/**
 * 
 * <领料单流程关闭操作类>
 * 
 * @author public2795
 * @version [版本号, 2018-10-13]
 * @since [产品/模块版本]
 */
public class MprClose implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		// TODO Auto-generated method stub
		if (jpo != null) {
			IJpoSet mprlineset = jpo.getJpoSet("mprline");
			// 调用ERP接口
			String retu = MroToErp(jpo);// 接口方法中返回的retu的值；
			if (retu.equalsIgnoreCase("S")) {
				// 调用出库方法
				Mprout(jpo);
				// 调用公共出库记录方法
				ItemInvtransCommon.reqmprout(mprlineset);
				jpo.setValue("status", "关闭",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				inventoryqty(jpo,mprlineset);

			} else {
				throw new MroException("错误", "领料单接口错误:" + retu);
			}
			jpo.getJpoSet().save();
		}
	}

	/**
	 * 
	 * <ERP接口方法>
	 * 
	 * @param jpo
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public String MroToErp(IJpo jpo) throws MroException {// 传递接口
	// String retu="S";
	// return retu;
		String num = "";
		String type = jpo.getString("type");
		String mrowerks = jpo.getJpoSet("MPRSTOREROOM").getJpo()
				.getString("ERPLOC");
		String mroperson = jpo.getUserServer().getUserInfo().getLoginID();
		mroperson = mroperson.toUpperCase();
		java.util.Date mrodate = jpo.getUserServer().getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 2018-07-24修改
		String CREATEDATES = sdf.format(mrodate);
		try {
			String user = IFUtil.getIfServiceInfo("erp.user");
			String pwd = IFUtil.getIfServiceInfo("erp.pwd");
			ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub service = new ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub();
			// 认证代码 start
			Authenticator auth = new Authenticator();
			auth.setUsername(user);
			auth.setPassword(pwd);
			service._getServiceClient().getOptions()
					.setProperty(HTTPConstants.AUTHENTICATE, auth);
			// 认证代码 end
			ZtfunWmsBasisFunction param = new ZtfunWmsBasisFunction();
			Char3 movetype = new Char3();// 参数--移动类型
			Char10 person = new Char10();// 参数--点收人
			Char4 werks = new Char4();// 参数--工厂
			Date budat = new Date();// 参数--过账日期

			movetype.setChar3("201");// 参数--移动类型201

			person.setChar10(mroperson);// 参数--点收入
			werks.setChar4(mrowerks);// 参数--工厂
			budat.setDate(CREATEDATES);// 参数--过账日期
			IJpoSet mprlineset = jpo.getJpoSet("mprline");
			TableOfBapi2017GmItemCreate table = new TableOfBapi2017GmItemCreate();
			JSONArray jArray = new JSONArray();
			for (int i = 0; i < mprlineset.count(); i++) {
				IJpo mprline = mprlineset.getJpo(i);
				String itemnum = mprline.getString("itemnum");// 物料编码

				String qty = mprline.getString("qty");// --数量
				String WORKORDERNUM = jpo.getString("WORKORDERNUM");// 工作令号
				String unit = mprline.getJpoSet("item").getJpo()
						.getString("ORDERUNIT");// 计量单位
				String COSTCENTER = jpo.getString("COSTCENTER");// 成本中心号
				String lotnum = "";
				String location = "";
				location = jpo.getJpoSet("MPRSTOREROOM").getJpo()
						.getString("STOREROOMPARENT");
				JSONObject rdata = new JSONObject();
				Bapi2017GmItemCreate tableobject = new Bapi2017GmItemCreate();

				Char18 Material = new Char18();// 输入数据--物料编码
				Char4 StgeLoc = new Char4();// 输入数据--库存地点
				Char10 Batch = new Char10();// 输入数据--批次号
				Quantum133 EntryQnt = new Quantum133();// 输入数据--数量
				Unit3 EntryUom = new Unit3();// 输入数据--计量单位
				Char10 Costcenter = new Char10();// 输入数据--成本中心
				Char12 Orderid = new Char12();// 输入数据--工作令号
				Char4 Plant = new Char4();
				Char3 MoveType = new Char3();
				Char1 StckType = new Char1();
				Char1 SpecStock = new Char1();
				Char10 Vendor = new Char10();
				Char10 Customer = new Char10();
				Char10 SalesOrd = new Char10();
				Numeric6 SOrdItem = new Numeric6();
				Numeric4 SchedLine = new Numeric4();
				Char10 ValType = new Char10();
				Char3 EntryUomIso = new Char3();
				Quantum133 PoPrQnt = new Quantum133();
				Unit3 OrderprUn = new Unit3();
				Char3 OrderprUnIso = new Char3();
				Char10 PoNumber = new Char10();
				Numeric5 PoItem = new Numeric5();
				Char2 Shipping = new Char2();
				Char2 CompShip = new Char2();
				Char1 NoMoreGr = new Char1();
				Char50 ItemText = new Char50();
				Char12 GrRcpt = new Char12();
				Char25 UnloadPt = new Char25();
				Numeric4 OrderItno = new Numeric4();
				Char2 CalcMotive = new Char2();
				Char12 AssetNo = new Char12();
				Char4 SubNumber = new Char4();
				Numeric10 ReservNo = new Numeric10();
				Numeric4 ResItem = new Numeric4();
				Char1 ResType = new Char1();
				Char1 Withdrawn = new Char1();
				Char18 MoveMat = new Char18();
				Char4 MovePlant = new Char4();
				Char4 MoveStloc = new Char4();
				Char10 MoveBatch = new Char10();
				Char10 MoveValType = new Char10();
				Char1 MvtInd = new Char1();
				Numeric4 MoveReas = new Numeric4();
				Char8 RlEstKey = new Char8();
				Date RefDate = new Date();
				Char12 CostObj = new Char12();
				Numeric10 ProfitSegmNo = new Numeric10();
				Char10 ProfitCtr = new Char10();
				Char24 WbsElem = new Char24();
				Char12 Network = new Char12();
				Char4 Activity = new Char4();
				Char10 PartAcct = new Char10();
				Decimal234 AmountLc = new Decimal234();
				Decimal234 AmountSv = new Decimal234();
				Numeric4 RefDocYr = new Numeric4();
				Char10 RefDoc = new Char10();
				Numeric4 RefDocIt = new Numeric4();
				Date Expirydate = new Date();
				Date ProdDate = new Date();
				Char10 Fund = new Char10();
				Char16 FundsCtr = new Char16();
				Char14 CmmtItem = new Char14();
				Char10 ValSalesOrd = new Char10();
				Numeric6 ValSOrdItem = new Numeric6();
				Char24 ValWbsElem = new Char24();
				Char10 GlAccount = new Char10();
				Char1 IndProposeQuanx = new Char1();
				Char1 Xstob = new Char1();
				Char18 EanUpc = new Char18();
				Char10 DelivNumbToSearch = new Char10();
				Numeric6 DelivItemToSearch = new Numeric6();
				Char1 SerialnoAutoNumberassignment = new Char1();
				Char15 Vendrbatch = new Char15();
				Char3 StgeType = new Char3();
				Char10 StgeBin = new Char10();
				Decimal30 SuPlStck1 = new Decimal30();
				Quantum133 StUnQtyy1 = new Quantum133();
				Char3 StUnQtyy1Iso = new Char3();
				Char3 Unittype1 = new Char3();
				Decimal30 SuPlStck2 = new Decimal30();
				Quantum133 StUnQtyy2 = new Quantum133();
				Char3 StUnQtyy2Iso = new Char3();
				Char3 Unittype2 = new Char3();
				Char3 StgeTypePc = new Char3();
				Char10 StgeBinPc = new Char10();
				Char1 NoPstChgnt = new Char1();
				Char10 GrNumber = new Char10();
				Char3 StgeTypeSt = new Char3();
				Char10 StgeBinSt = new Char10();
				Char10 MatdocTrCancel = new Char10();
				Numeric4 MatitemTrCancel = new Numeric4();
				Numeric4 MatyearTrCancel = new Numeric4();
				Char1 NoTransferReq = new Char1();
				Char12 CoBusproc = new Char12();
				Char6 Acttype = new Char6();
				Char10 SupplVend = new Char10();
				Char40 MaterialExternal = new Char40();
				Char32 MaterialGuid = new Char32();
				Char10 MaterialVersion = new Char10();
				Char40 MoveMatExternal = new Char40();
				Char32 MoveMatGuid = new Char32();
				Char10 MoveMatVersion = new Char10();
				Char4 FuncArea = new Char4();
				Char4 TrPartBa = new Char4();
				Char4 ParCompco = new Char4();
				Char10 DelivNumb = new Char10();
				Numeric6 DelivItem = new Numeric6();
				Numeric3 NbSlips = new Numeric3();
				Char1 NbSlipsx = new Char1();
				Char1 GrRcptx = new Char1();
				Char1 UnloadPtx = new Char1();
				Char1 SpecMvmt = new Char1();
				Char20 GrantNbr = new Char20();
				Char24 CmmtItemLong = new Char24();
				Char16 FuncAreaLong = new Char16();
				Numeric6 LineId = new Numeric6();
				Numeric6 ParentId = new Numeric6();
				Numeric2 LineDepth = new Numeric2();
				Quantum133 Quantity = new Quantum133();
				Unit3 BaseUom = new Unit3();
				Char40 Longnum = new Char40();

				Material.setChar18(itemnum);// 输入数据--物料编码
				Plant.setChar4("1010");
				StgeLoc.setChar4(location);// 输入数据--库存地点
				EntryQnt.setQuantum133(new BigDecimal(qty));// 输入数据--数量
				EntryUom.setUnit3(unit);// 输入数据--计量单位
				Batch.setChar10("1");// 输入数据--批次
				Costcenter.setChar10(COSTCENTER);// 输入数据--成本中心
				Orderid.setChar12(WORKORDERNUM);// 输入数据--工作令号
				MoveType.setChar3("");
				StckType.setChar1("");
				SpecStock.setChar1("");
				Vendor.setChar10("");
				Customer.setChar10("");
				SalesOrd.setChar10("");
				SOrdItem.setNumeric6("");
				SchedLine.setNumeric4("");
				ValType.setChar10("");
				EntryUomIso.setChar3("");
				PoPrQnt.setQuantum133(new BigDecimal(0.000));
				OrderprUn.setUnit3("");
				OrderprUnIso.setChar3("");
				PoNumber.setChar10("");
				PoItem.setNumeric5("");
				Shipping.setChar2("");
				CompShip.setChar2("");
				NoMoreGr.setChar1("");
				ItemText.setChar50("");
				GrRcpt.setChar12("");
				UnloadPt.setChar25("");
				OrderItno.setNumeric4("");
				CalcMotive.setChar2("");
				AssetNo.setChar12("");
				SubNumber.setChar4("");
				ReservNo.setNumeric10("");
				ResItem.setNumeric4("");
				ResType.setChar1("");
				Withdrawn.setChar1("");
				MoveMat.setChar18("");
				MovePlant.setChar4("");
				MoveStloc.setChar4("");
				MoveBatch.setChar10("");
				MoveValType.setChar10("");
				MvtInd.setChar1("");
				MoveReas.setNumeric4("");
				RlEstKey.setChar8("");
				RefDate.setDate("0000-00-00");
				CostObj.setChar12("");
				ProfitSegmNo.setNumeric10("");
				ProfitCtr.setChar10("");
				WbsElem.setChar24("");
				Network.setChar12("");
				Activity.setChar4("");
				PartAcct.setChar10("");
				AmountLc.setDecimal234(new BigDecimal(0.000));
				AmountSv.setDecimal234(new BigDecimal(0.000));
				RefDocYr.setNumeric4("");
				RefDoc.setChar10("");
				RefDocIt.setNumeric4("");
				Expirydate.setDate("0000-00-00");
				ProdDate.setDate("0000-00-00");
				Fund.setChar10("");
				FundsCtr.setChar16("");
				CmmtItem.setChar14("");
				ValSalesOrd.setChar10("");
				ValSOrdItem.setNumeric6("");
				ValWbsElem.setChar24("");
				GlAccount.setChar10("");
				IndProposeQuanx.setChar1("");
				Xstob.setChar1("");
				EanUpc.setChar18("");
				DelivNumbToSearch.setChar10("");
				DelivItemToSearch.setNumeric6("");
				SerialnoAutoNumberassignment.setChar1("");
				Vendrbatch.setChar15("");
				StgeType.setChar3("");
				StgeBin.setChar10("");
				SuPlStck1.setDecimal30(new BigDecimal(0.000));
				StUnQtyy1.setQuantum133(new BigDecimal(0.000));
				StUnQtyy1Iso.setChar3("");
				Unittype1.setChar3("");
				SuPlStck2.setDecimal30(new BigDecimal(0.000));
				StUnQtyy2.setQuantum133(new BigDecimal(0.000));
				StUnQtyy2Iso.setChar3("");
				Unittype2.setChar3("");
				StgeTypePc.setChar3("");
				StgeBinPc.setChar10("");
				NoPstChgnt.setChar1("");
				GrNumber.setChar10("");
				StgeTypeSt.setChar3("");
				StgeBinSt.setChar10("");
				MatdocTrCancel.setChar10("");
				MatitemTrCancel.setNumeric4("");
				MatyearTrCancel.setNumeric4("");
				NoTransferReq.setChar1("");
				CoBusproc.setChar12("");
				Acttype.setChar6("");
				SupplVend.setChar10("");
				MaterialExternal.setChar40("");
				MaterialGuid.setChar32("");
				MaterialVersion.setChar10("");
				MoveMatExternal.setChar40("");
				MoveMatGuid.setChar32("");
				MoveMatVersion.setChar10("");
				FuncArea.setChar4("");
				TrPartBa.setChar4("");
				ParCompco.setChar4("");
				DelivNumb.setChar10("");
				DelivItem.setNumeric6("");
				NbSlips.setNumeric3("");
				NbSlipsx.setChar1("");
				GrRcptx.setChar1("");
				UnloadPtx.setChar1("");
				SpecMvmt.setChar1("");
				GrantNbr.setChar20("");
				CmmtItemLong.setChar24("");
				FuncAreaLong.setChar16("");
				LineId.setNumeric6("");
				ParentId.setNumeric6("");
				LineDepth.setNumeric2("");
				Quantity.setQuantum133(new BigDecimal(0.000));
				BaseUom.setUnit3("");
				Longnum.setChar40("");

				rdata.put("itemnum", itemnum);
				rdata.put("StgeLoc", location);
				rdata.put("EntryQnt", qty);
				rdata.put("EntryUom", unit);
				rdata.put("Batch", lotnum);
				rdata.put("Costcenter", COSTCENTER);
				rdata.put("Orderid", WORKORDERNUM);

				jArray.put(rdata);
				tableobject.setMaterial(Material);
				tableobject.setPlant(Plant);
				tableobject.setStgeLoc(StgeLoc);
				tableobject.setBatch(Batch);
				tableobject.setMoveType(MoveType);
				tableobject.setStckType(StckType);
				tableobject.setSpecStock(SpecStock);
				tableobject.setVendor(Vendor);
				tableobject.setCustomer(Customer);
				tableobject.setSalesOrd(SalesOrd);
				tableobject.setSOrdItem(SOrdItem);
				tableobject.setSchedLine(SchedLine);
				tableobject.setValType(ValType);
				tableobject.setEntryQnt(EntryQnt);
				tableobject.setEntryUom(EntryUom);
				tableobject.setEntryUomIso(EntryUomIso);
				tableobject.setPoPrQnt(PoPrQnt);
				tableobject.setOrderprUn(OrderprUn);
				tableobject.setOrderprUnIso(OrderprUnIso);
				tableobject.setPoNumber(PoNumber);
				tableobject.setPoItem(PoItem);
				tableobject.setShipping(Shipping);
				tableobject.setCompShip(CompShip);
				tableobject.setNoMoreGr(NoMoreGr);
				tableobject.setItemText(ItemText);
				tableobject.setGrRcpt(GrRcpt);
				tableobject.setUnloadPt(UnloadPt);
				tableobject.setCostcenter(Costcenter);
				tableobject.setOrderid(Orderid);
				tableobject.setOrderItno(OrderItno);
				tableobject.setCalcMotive(CalcMotive);
				tableobject.setAssetNo(AssetNo);
				tableobject.setSubNumber(SubNumber);
				tableobject.setReservNo(ReservNo);
				tableobject.setResItem(ResItem);
				tableobject.setResType(ResType);
				tableobject.setWithdrawn(Withdrawn);
				tableobject.setMoveMat(MoveMat);
				tableobject.setMovePlant(MovePlant);
				tableobject.setMoveStloc(MoveStloc);
				tableobject.setMoveBatch(MoveBatch);
				tableobject.setMoveValType(MoveValType);
				tableobject.setMvtInd(MvtInd);
				tableobject.setMoveReas(MoveReas);
				tableobject.setRlEstKey(RlEstKey);
				tableobject.setRefDate(RefDate);
				tableobject.setCostObj(CostObj);
				tableobject.setProfitSegmNo(ProfitSegmNo);
				tableobject.setProfitCtr(ProfitCtr);
				tableobject.setWbsElem(WbsElem);
				tableobject.setNetwork(Network);
				tableobject.setActivity(Activity);
				tableobject.setPartAcct(PartAcct);
				tableobject.setAmountLc(AmountLc);
				tableobject.setAmountSv(AmountSv);
				tableobject.setRefDocYr(RefDocYr);
				tableobject.setRefDoc(RefDoc);
				tableobject.setRefDocIt(RefDocIt);
				tableobject.setExpirydate(Expirydate);
				tableobject.setProdDate(ProdDate);
				tableobject.setFund(Fund);
				tableobject.setFundsCtr(FundsCtr);
				tableobject.setCmmtItem(CmmtItem);
				tableobject.setValSalesOrd(ValSalesOrd);
				tableobject.setValSOrdItem(ValSOrdItem);
				tableobject.setValWbsElem(ValWbsElem);
				tableobject.setGlAccount(GlAccount);
				tableobject.setIndProposeQuanx(IndProposeQuanx);
				tableobject.setXstob(Xstob);
				tableobject.setEanUpc(EanUpc);
				tableobject.setDelivNumbToSearch(DelivNumbToSearch);
				tableobject.setDelivItemToSearch(DelivItemToSearch);
				tableobject
						.setSerialnoAutoNumberassignment(SerialnoAutoNumberassignment);
				tableobject.setVendrbatch(Vendrbatch);
				tableobject.setStgeType(StgeType);
				tableobject.setStgeBin(StgeBin);
				tableobject.setSuPlStck1(SuPlStck1);
				tableobject.setStUnQtyy1(StUnQtyy1);
				tableobject.setStUnQtyy1Iso(StUnQtyy1Iso);
				tableobject.setUnittype1(Unittype1);
				tableobject.setSuPlStck2(SuPlStck2);
				tableobject.setStUnQtyy2(StUnQtyy2);
				tableobject.setStUnQtyy2Iso(StUnQtyy2Iso);
				tableobject.setUnittype2(Unittype2);
				tableobject.setStgeTypePc(StgeTypePc);
				tableobject.setStgeBinPc(StgeBinPc);
				tableobject.setNoPstChgnt(NoPstChgnt);
				tableobject.setGrNumber(GrNumber);
				tableobject.setStgeTypeSt(StgeTypeSt);
				tableobject.setStgeBinSt(StgeBinSt);
				tableobject.setMatdocTrCancel(MatdocTrCancel);
				tableobject.setMatitemTrCancel(MatitemTrCancel);
				tableobject.setMatyearTrCancel(MatyearTrCancel);
				tableobject.setNoTransferReq(NoTransferReq);
				tableobject.setCoBusproc(CoBusproc);
				tableobject.setActtype(Acttype);
				tableobject.setSupplVend(SupplVend);
				tableobject.setMaterialExternal(MaterialExternal);
				tableobject.setMaterialGuid(MaterialGuid);
				tableobject.setMaterialVersion(MaterialVersion);
				tableobject.setMoveMatExternal(MoveMatExternal);
				tableobject.setMoveMatGuid(MoveMatGuid);
				tableobject.setMoveMatVersion(MoveMatVersion);
				tableobject.setFuncArea(FuncArea);
				tableobject.setTrPartBa(TrPartBa);
				tableobject.setParCompco(ParCompco);
				tableobject.setDelivNumb(DelivNumb);
				tableobject.setDelivItem(DelivItem);
				tableobject.setNbSlips(NbSlips);
				tableobject.setNbSlipsx(NbSlipsx);
				tableobject.setGrRcptx(GrRcptx);
				tableobject.setUnloadPtx(UnloadPtx);
				tableobject.setSpecMvmt(SpecMvmt);
				tableobject.setGrantNbr(GrantNbr);
				tableobject.setCmmtItemLong(CmmtItemLong);
				tableobject.setFuncAreaLong(FuncAreaLong);
				tableobject.setLineId(LineId);
				tableobject.setParentId(ParentId);
				tableobject.setLineDepth(LineDepth);
				tableobject.setQuantity(Quantity);
				tableobject.setBaseUom(BaseUom);
				tableobject.setLongnum(Longnum);
				table.addItem(tableobject);
			}
			param.setMoveType(movetype);
			param.setPerson(person);
			param.setWerks(werks);
			param.setWmBudat(budat);
			param.setTGvitem(table);

			num = IFUtil.addIfHistory(IFUtil.ERP_MRO_TOEROCKIF_201,
					jArray.toString(), IFUtil.TYPE_OUTPUT);// 增加输出记录
			ZtfunWmsBasisFunctionResponse res = service
					.ztfunWmsBasisFunction(param);
			IFUtil.updateIfHistory(
					num,
					IFUtil.STATUS_SUCCESS,
					IFUtil.FLAG_YES,
					"领料单号：" + jpo.getString("mprnum") + ";传递ERP"
							+ mprlineset.count() + "条;");
			String message = res.getMessage().toString();
			String retu = res.getReturn().toString();
			if (retu.equalsIgnoreCase("E")) {
				retu = message;
			}
			String MATERIALDOCUMENT = res.getMaterialdocument().toString();
			String MATDOCUMENTYEAR = res.getMatdocumentyear().toString();
			JSONArray returnjArray = new JSONArray();
			JSONObject returnrdata = new JSONObject();
			returnrdata.put("message", message);
			returnrdata.put("retu", retu);
			returnrdata.put("MATERIALDOCUMENT", MATERIALDOCUMENT);
			returnrdata.put("MATDOCUMENTYEAR", MATDOCUMENTYEAR);
			returnjArray.put(returnrdata);
			num = IFUtil.addIfHistory(IFUtil.ERP_MRO_TOEROCKIF_201,
					returnjArray.toString(), IFUtil.TYPE_INPUT);// 增加输出记录

			// TableOfBapi2017GmItemCreate retable=res.getTGvitem();

			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"领料单号：" + jpo.getString("mprnum") + ";接收ERP回传;");

			return retu;
		} catch (Exception e) {
			IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
					e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * <出口方法>
	 * 
	 * @param mprJpo
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void Mprout(IJpo mprJpo) throws MroException {// 出库
		try {
			// IJpo mprJpo = jpo;
			IJpoSet mprlineset = mprJpo.getJpoSet("mprline");
			String MPRSTOREROOM = mprJpo.getString("MPRSTOREROOM");
			IJpoSet locationsset = MroServer.getMroServer()
					.getJpoSet("locations",
							MroServer.getMroServer().getSystemUserServer());
			locationsset.setUserWhere("location='" + MPRSTOREROOM + "'");
			boolean ISLOCBIN = locationsset.getJpo(0).getBoolean("ISLOCBIN");
			if (!mprlineset.isEmpty()) {
				for (int i = 0; i < mprlineset.count(); i++) {
					IJpo mprline = mprlineset.getJpo(i);
					double qty = mprline.getDouble("qty");// --数量
					String location = mprline.getParent().getString(
							"MPRSTOREROOM");// --库房
					String itemnum = mprline.getString("itemnum");// --物料编码
					String binnum = mprline.getString("binnum");// --仓位

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
						double invlocqty = inventory.getDouble("locqty");
						double newinvlocqty = invlocqty - qty;
						if (ISLOCBIN) {
							if (!binnum.equalsIgnoreCase("")) {
								IJpoSet locbinitemset = MroServer
										.getMroServer().getJpoSet(
												"locbinitem",
												MroServer.getMroServer()
														.getSystemUserServer());// --璋冩嫧鍑哄簱浠撲綅闆嗗悎
								locbinitemset.setUserWhere("itemnum='"
										+ itemnum + "' and location='"
										+ location + "' and binnum='" + binnum
										+ "'");
								locbinitemset.reset();
								if (!locbinitemset.isEmpty()) {
									IJpo locbinitem = locbinitemset.getJpo(0);
									double locqty = locbinitem.getDouble("qty");
									double newqty = locqty - qty;
									locbinitem
											.setValue(
													"qty",
													newqty,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									locbinitemset.save();
								}
							}
						}
						inventory.setValue("CURBAL", newcurbal,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventory.setValue("locqty", newinvlocqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						inventoryset.save();
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
	 * <领料单确认领料计算库存数量>
	 * @param mprlineset
	 * @throws MroException [参数说明]
	 *
	 */
	public void inventoryqty(IJpo mpr,IJpoSet mprlineset) throws MroException {// 出库
		if(mpr!=null){
			String MPRSTOREROOM=mpr.getString("MPRSTOREROOM");
			if(!mprlineset.isEmpty()){
				for(int i=0;i<mprlineset.count();i++){
					IJpo mprline=mprlineset.getJpo(i);
					String itemnum=mprline.getString("itemnum");
					InventoryQtyCommon.SQZYQTY(itemnum, MPRSTOREROOM);
					InventoryQtyCommon.FCZTQTY(itemnum, MPRSTOREROOM);
					InventoryQtyCommon.JSZTQTY(itemnum, MPRSTOREROOM);
					InventoryQtyCommon.KYQTY(itemnum, MPRSTOREROOM);
				}
			}
		}
	}
}
