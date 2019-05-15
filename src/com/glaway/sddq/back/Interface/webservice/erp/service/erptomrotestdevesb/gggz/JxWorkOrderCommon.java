package com.glaway.sddq.back.Interface.webservice.erp.service.erptomrotestdevesb.gggz;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient3.HttpTransportPropertiesImpl.Authenticator;

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
import com.glaway.sddq.tools.IFUtil;

public class JxWorkOrderCommon {
	/**
	 * 
	 * <功能描述>
	 * 
	 * @param movetype1
	 *            移动类型
	 * @param werks1
	 *            工厂
	 * @param budat1
	 *            点收日期
	 * @param person1
	 *            点收人
	 * @param itemnum
	 *            物资编码
	 * @param stock
	 *            库房
	 * @param orderid
	 *            工作令号
	 * @param amount
	 *            数量
	 * @param jldw
	 *            计量单位
	 * @param interface 接口名称
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public  String[] jxworkorder(String movetype1, String werks1, String budat1, String person1, String itemnum, String stock, String orderid, double amount, String jldw) throws Exception {

		String amount1=String.valueOf(amount);
		ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub service = new ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub();
		Authenticator auth = new Authenticator();
        String user = IFUtil.getIfServiceInfo("erp.user");
        String pwd = IFUtil.getIfServiceInfo("erp.pwd");
        auth.setUsername(user);
        auth.setPassword(pwd);
		service._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
		ZtfunWmsBasisFunction param = new ZtfunWmsBasisFunction();
		Char3 movetype = new Char3();
		Char10 person = new Char10();
		Char4 werks = new Char4();
		Date budat = new Date();
		Char18 Material = new Char18();
		Char4 Plant = new Char4();
		Char4 StgeLoc = new Char4();
		Char10 Batch = new Char10();
		Char3 MoveType = new Char3();
		Char1 StckType = new Char1();
		Char1 SpecStock = new Char1();
		Char10 Vendor = new Char10();
		Char10 Customer = new Char10();
		Char10 SalesOrd = new Char10();
		Numeric6 SOrdItem = new Numeric6();
		Numeric4 SchedLine = new Numeric4();
		Char10 ValType = new Char10();
		Quantum133 EntryQnt = new Quantum133();
		Unit3 EntryUom = new Unit3();
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
		Char10 Costcenter = new Char10();
		Char12 Orderid = new Char12();
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

		Material.setChar18(itemnum);// 物料号
		StgeLoc.setChar4(stock);// 库存地点
		MoveStloc.setChar4("");//
		EntryUom.setUnit3(jldw);// 单位
		Orderid.setChar12(orderid);// 订单号
		EntryQnt.setQuantum133(new BigDecimal(amount1));// 数量
		Costcenter.setChar10("");// 成本中心
		Batch.setChar10("");// 批次号

		Plant.setChar4("");
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

		Bapi2017GmItemCreate tableobject = new Bapi2017GmItemCreate();
		TableOfBapi2017GmItemCreate table = new TableOfBapi2017GmItemCreate();

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
		tableobject.setSerialnoAutoNumberassignment(SerialnoAutoNumberassignment);
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
		movetype.setChar3(movetype1);
		person.setChar10(person1);
		werks.setChar4(werks1);
		budat.setDate(budat1);
		param.setMoveType(movetype);
		param.setPerson(person);
		param.setWerks(werks);
		param.setWmBudat(budat);
		param.setTGvitem(table);
		ZtfunWmsBasisFunctionResponse res = service.ztfunWmsBasisFunction(param);
		TableOfBapi2017GmItemCreate retable = res.getTGvitem();
		Bapi2017GmItemCreate[] zppz = retable.getItem();
		if (zppz != null && zppz.length > 0) {
			for (int index = 0; index < zppz.length; index++) {
				String Itemnum = zppz[index].getMaterial().toString();// 父项物料编码

			}
		}
		String MATERIALDOCUMENT = res.getMaterialdocument().toString();// 凭证
		String MATDOCUMENTYEAR = res.getMatdocumentyear().toString();// 年度
		String message = res.getMessage().toString();
		String retu = res.getReturn().toString();

		String[] str = new String[4];
		str[0] = retu;
		str[1] = message;
		str[2] = MATDOCUMENTYEAR;
		str[3] = MATERIALDOCUMENT;

		return str;
	}
	
}