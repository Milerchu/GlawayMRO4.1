package com.glaway.sddq.material.convertlocline.bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient3.HttpTransportPropertiesImpl.Authenticator;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
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
import com.glaway.sddq.material.invtrans.common.ConvertlocCommonInventory;
import com.glaway.sddq.material.invtrans.common.InventoryQtyCommon;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * <调拨转库单接收窗口DataBean类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class ConvertloclineStatusDataBean extends DataBean {
	/**
	 * 初始化接收窗口判断仓位、序列号、批次号必填与只读.
	 * 
	 * @throws MroException
	 */
	@Override
	public void initialize() throws MroException {
		// TODO Auto-generated method stub
		super.initialize();
		IJpo parent = this.getJpo().getParent();
		String itemnum = parent.getString("itemnum");
		String type = ItemUtil.getItemInfo(itemnum);
		boolean islocbin = parent.getJpoSet("LOCATIONIX").getJpo()
				.getBoolean("islocbin");
		if (islocbin) {
			this.getJpo().setFieldFlag("binnum", GWConstant.S_READONLY, false);
			this.getJpo().setFieldFlag("binnum", GWConstant.S_REQUIRED, true);
			if (ItemUtil.SQN_ITEM.equals(type)) {
				this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY, false);
				this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("lotnum", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setFieldFlag("lotnum", GWConstant.S_READONLY,
						true);
			} else {
				if (ItemUtil.LOT_I_ITEM.equals(type)) {
					this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED,
							false);
					this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY,
							true);
					this.getJpo().setFieldFlag("lotnum", GWConstant.S_READONLY,
							false);
					this.getJpo().setFieldFlag("lotnum", GWConstant.S_REQUIRED,
							true);
				} else {
					this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED,
							false);
					this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY,
							true);
					this.getJpo().setFieldFlag("lotnum", GWConstant.S_REQUIRED,
							false);
					this.getJpo().setFieldFlag("lotnum", GWConstant.S_READONLY,
							true);
				}
			}

		} else {
			this.getJpo().setFieldFlag("binnum", GWConstant.S_REQUIRED, false);
			this.getJpo().setFieldFlag("binnum", GWConstant.S_READONLY, true);
			if (ItemUtil.SQN_ITEM.equals(type)) {
				this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY, false);
				this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("lotnum", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setFieldFlag("lotnum", GWConstant.S_READONLY,
						true);
			} else {
				if (ItemUtil.LOT_I_ITEM.equals(type)) {
					this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED,
							false);
					this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY,
							true);
					this.getJpo().setFieldFlag("lotnum", GWConstant.S_READONLY,
							false);
					this.getJpo().setFieldFlag("lotnum", GWConstant.S_REQUIRED,
							true);
				} else {
					this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED,
							false);
					this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY,
							true);
					this.getJpo().setFieldFlag("lotnum", GWConstant.S_REQUIRED,
							false);
					this.getJpo().setFieldFlag("lotnum", GWConstant.S_READONLY,
							true);
				}
			}
		}
	}

	/**
	 * 调拨转库单接收窗口确认接收按钮方法判断是否能接收
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
			String lotnum = this.getJpo().getString("lotnum");
			String location = parent.getString("location");
			String binnum = this.getJpo().getString("binnum");
			String sqn = this.getJpo().getString("sqn");
			String ERPLOC = parent.getJpoSet("LOCATIONIX").getJpo()
					.getString("erploc");
			String convertlocnum = parent.getString("convertlocnum");
			String convertloclineid = parent.getString("convertloclineid");
			String ITEMNUM = parent.getString("ITEMNUM");
			// ////////////////////////////////////////////////////////////////
			String itemnum = parent.getString("itemnum");
			String status = parent.getString("status");
			String type = ItemUtil.getItemInfo(itemnum);
			if (status.equalsIgnoreCase("未接收")) {
				boolean islocbin = parent.getJpoSet("LOCATIONIX").getJpo()
						.getBoolean("islocbin");
				if (islocbin) {
					if (binnum.isEmpty()) {
						throw new MroException("该库房为仓位管理，请填写仓位信息");
					} else {
						if (ItemUtil.SQN_ITEM.equals(type)) {
							if (sqn.isEmpty()) {
								throw new MroException("该物料为序列号管理，请填写序列号");
							} else {
								IJpoSet assetset = MroServer.getMroServer()
										.getJpoSet(
												"asset",
												MroServer.getMroServer()
														.getSystemUserServer());
								assetset.setUserWhere("itemnum='" + itemnum
										+ "' and sqn='" + sqn + "'");
								if (assetset.isEmpty()) {
									// 调用物料接收方法
									TOERP(parent, convertlocnum,
											convertloclineid, ERPLOC, lotnum,
											location, itemnum, binnum, sqn,
											statusjsqty, newyjsqty,
											statuswjsqty);
								} else {
									String assettype=assetset.getJpo(0).getString("type");
									String assetlocation=assetset.getJpo(0).getString("location");
									if(assettype.equalsIgnoreCase("0")&&assetlocation.isEmpty()){
										// 调用物料接收方法
										TOERP(parent, convertlocnum,
												convertloclineid, ERPLOC, lotnum,
												location, itemnum, binnum, sqn,
												statusjsqty, newyjsqty,
												statuswjsqty);
									}else{
										if(assettype.equalsIgnoreCase("1")&&assetlocation.equalsIgnoreCase(location)){
											IJpoSet convertloclineset = MroServer.getMroServer()
													.getJpoSet(
															"convertlocline",
															MroServer.getMroServer()
																	.getSystemUserServer());
											convertloclineset.setUserWhere("convertlocnum='"+convertlocnum+"' and itemnum='"+itemnum+"' and sqn='"+sqn+"'");
											if(convertloclineset.isEmpty()){
												// 调用物料接收方法
												TOERP(parent, convertlocnum, convertloclineid,
														ERPLOC, lotnum, location, itemnum,
														binnum, sqn, statusjsqty, newyjsqty,
														statuswjsqty);
											}else{
												throw new MroException("该物料序列号在本交货单重复，请检查！");
											}
										}else{
											throw new MroException("该物料序列号重复，请检查！");
										}
									}
									
								}

							}
						} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
							if (lotnum.isEmpty()) {
								throw new MroException("该物料为批次号管理，请填写批次号");
							} else {
								// 调用物料接收方法
								TOERP(parent, convertlocnum, convertloclineid,
										ERPLOC, lotnum, location, itemnum,
										binnum, sqn, statusjsqty, newyjsqty,
										statuswjsqty);
							}
						} else {
							// 调用物料接收方法
							TOERP(parent, convertlocnum, convertloclineid,
									ERPLOC, lotnum, location, itemnum, binnum,
									sqn, statusjsqty, newyjsqty, statuswjsqty);
						}
					}
				} else {
					if (ItemUtil.SQN_ITEM.equals(type)) {
						if (sqn.isEmpty()) {
							throw new MroException("该物料为序列号管理，请填写序列号");
						} else {
							IJpoSet assetset = MroServer.getMroServer()
									.getJpoSet(
											"asset",
											MroServer.getMroServer()
													.getSystemUserServer());
							assetset.setUserWhere("itemnum='" + itemnum
									+ "' and sqn='" + sqn + "'");
							if (assetset.isEmpty()) {
								// 调用物料接收方法
								TOERP(parent, convertlocnum, convertloclineid,
										ERPLOC, lotnum, location, itemnum,
										binnum, sqn, statusjsqty, newyjsqty,
										statuswjsqty);
							} else {
								String assettype=assetset.getJpo(0).getString("type");
								String assetlocation=assetset.getJpo(0).getString("location");
								if(assettype.equalsIgnoreCase("0")&&assetlocation.isEmpty()){
									// 调用物料接收方法
									TOERP(parent, convertlocnum,
											convertloclineid, ERPLOC, lotnum,
											location, itemnum, binnum, sqn,
											statusjsqty, newyjsqty,
											statuswjsqty);
								}else{
									if(assettype.equalsIgnoreCase("1")&&assetlocation.equalsIgnoreCase(location)){
										IJpoSet convertloclineset = MroServer.getMroServer()
												.getJpoSet(
														"convertlocline",
														MroServer.getMroServer()
																.getSystemUserServer());
										convertloclineset.setUserWhere("convertlocnum='"+convertlocnum+"' and itemnum='"+itemnum+"' and sqn='"+sqn+"'");
										if(convertloclineset.isEmpty()){
											// 调用物料接收方法
											TOERP(parent, convertlocnum, convertloclineid,
													ERPLOC, lotnum, location, itemnum,
													binnum, sqn, statusjsqty, newyjsqty,
													statuswjsqty);
										}else{
											throw new MroException("该物料序列号在本交货单重复，请检查！");
										}
									}else{
										throw new MroException("该物料序列号重复，请检查！");
									}
								}

							}
						}
					} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
						if (lotnum.isEmpty()) {
							throw new MroException("该物料为批次号管理，请填写批次号");
						} else {
							// 调用物料接收方法
							TOERP(parent, convertlocnum, convertloclineid,
									ERPLOC, lotnum, location, itemnum, binnum,
									sqn, statusjsqty, newyjsqty, statuswjsqty);
						}
					} else {
						// 调用物料接收方法
						TOERP(parent, convertlocnum, convertloclineid, ERPLOC,
								lotnum, location, itemnum, binnum, sqn,
								statusjsqty, newyjsqty, statuswjsqty);
					}
				}
			} else {
				throw new MroException("该物料已接收");
			}
		}
		// this.getAppBean().SAVE();
		// return GWConstant.NOACCESS_SAMEMETHOD;
		return super.execute();
	}

	/**
	 * 
	 * <物料接收方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void TOERP(IJpo parent, String convertlocnum,
			String convertloclineid, String ERPLOC, String lotnum,
			String location, String itemnum, String binnum, String sqn,
			double statusjsqty, double newyjsqty, double statuswjsqty)
			throws MroException {
		if (ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1020)
				|| ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1030)) {
			// 调用序列号新增方法
						ADDASSET();
			String retu = MroToErp();// 调用ERP接口 接口方法中返回的retu的值；
			if (retu.equalsIgnoreCase("S")) {

				parent.setValue("YJSQTY", newyjsqty,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if (statuswjsqty == statusjsqty) {
					parent.setValue("lotnum", lotnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.setValue("binnum", binnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.setValue("sqn", sqn,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.setValue("status", "已接收",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					IJpoSet convertloclineset = MroServer.getMroServer()
							.getJpoSet(
									"convertlocline",
									MroServer.getMroServer()
											.getSystemUserServer());
					convertloclineset.setQueryWhere("convertlocnum='"
							+ convertlocnum
							+ "' and status!='已接收' and convertloclineid!='"
							+ convertloclineid + "'");
					if (convertloclineset.isEmpty()) {
						parent.getParent().setValue("status", "已接收");
					}
				} else {
					parent.setValue("lotnum", lotnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.setValue("binnum", binnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.setValue("sqn", sqn,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.setValue("status", "部分接收",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				// 调用物料入库公共方法
				ConvertlocCommonInventory.ININVENTORY(lotnum, statusjsqty,
						location, itemnum, binnum, sqn);
//				// 调用序列号新增方法
//				ADDASSET();
				inventoryqty(itemnum,location);
			} else {
				throw new MroException("错误", "交货单点收接口错误:" + retu);
			}
		} else {
			// 调用序列号新增方法
			ADDASSET();
			parent.setValue("YJSQTY", newyjsqty,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			if (statuswjsqty == statusjsqty) {
				parent.setValue("lotnum", lotnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				parent.setValue("binnum", binnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				parent.setValue("sqn", sqn,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				parent.setValue("status", "已接收",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				IJpoSet convertloclineset = MroServer.getMroServer().getJpoSet(
						"convertlocline",
						MroServer.getMroServer().getSystemUserServer());
				convertloclineset.setQueryWhere("convertlocnum='"
						+ convertlocnum
						+ "' and status!='已接收' and convertloclineid!='"
						+ convertloclineid + "'");
				if (convertloclineset.isEmpty()) {
					parent.setValue("lotnum", lotnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.setValue("binnum", binnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.setValue("sqn", sqn,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					parent.getParent().setValue("status", "已接收");
				}
			} else {
				parent.setValue("lotnum", lotnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				parent.setValue("binnum", binnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				parent.setValue("sqn", sqn,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				parent.setValue("status", "部分接收",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			// 调用物料入库公共方法
			ConvertlocCommonInventory.ININVENTORY(lotnum, statusjsqty,
					location, itemnum, binnum, sqn);
//			// 调用序列号新增方法
//			ADDASSET();
			inventoryqty(itemnum,location);
		}
	}

	/**
	 * 
	 * <序列号新增方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void ADDASSET() throws MroException {
		// IJpoSet jpoSet = this.getJpo().getJpoSet("$asset", "ASSET", "1=2");
		IJpoSet jpoSet = MroServer.getMroServer().getJpoSet("asset",
				MroServer.getMroServer().getSystemUserServer());
		String PROJNUM = this.getParent().getJpo().getString("PROJNUM");
		java.util.Date INSTOREDATE = MroServer.getMroServer().getDate();
		String binnum = this.getJpo().getString("binnum");
		String SQN = this.getJpo().getString("SQN").toUpperCase();
		String ITEMNUM = this.getParent().getJpo().getString("ITEMNUM");
		String LOCATION = this.getParent().getJpo().getString("LOCATION");
		IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",
				MroServer.getMroServer().getSystemUserServer());
		itemset.setQueryWhere("itemnum='" + ITEMNUM + "'");
		String type = ItemUtil.getItemInfo(ITEMNUM);
		String commomtype ="入库";
		if (ItemUtil.SQN_ITEM.equals(type)) {
			IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
					MroServer.getMroServer().getSystemUserServer());
			assetset.setQueryWhere("itemnum='"+ ITEMNUM+ "' and sqn='"+ SQN+ "' and assetlevel='ASSET' and type!='2' and location is null " +
					"or itemnum='"+ ITEMNUM+ "' and sqn='"+ SQN+ "' and assetlevel='ASSET' and type!='2' and location='"+LOCATION+"'");
			if (assetset.isEmpty()) {
				IJpo jpo = jpoSet.addJpo();
				jpo.setValue("assetlevel", "ASSET",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("itemnum", ITEMNUM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("sqn", SQN,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("LOCATION", LOCATION,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("binnum", binnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("type", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("ancestor", jpo.getString("assetnum"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("status", "可用",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("INSTOREDATE", INSTOREDATE,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("PROJNUM", PROJNUM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("isnew", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("iserp", "0",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("MSGFLAG", SddqConstant.MSG_INFO_NOSQN,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("FROMSOURCE", SddqConstant.FROMSOURCE_XP,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.getParent()
						.setValue("assetnum", jpo.getString("assetnum"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//				CommomCarItemLife.INOROURLOCATION(jpo, commomtype);
				jpoSet.save();
			} else {
				IJpo asset = assetset.getJpo(0);
				asset.setValue("LOCATION", LOCATION,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("type", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("status", "可用",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				asset.setValue("INSTOREDATE", INSTOREDATE,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.getParent()
						.setValue("assetnum", asset.getString("assetnum"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//				CommomCarItemLife.INOROURLOCATION(asset, commomtype);
				assetset.save();
			}
		}

	}

	/**
	 * 
	 * <调用ERP过账接口>
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public String MroToErp() throws MroException {// 传递接口、
		// String retu="S";
		// return retu;
		String statusjsqty = this.getJpo().getString("jsqty");
		String CONVERTLOCNUM = this.page.getAppBean().getJpo()
				.getString("CONVERTLOCNUM");
		String num = "";
		String WERKS = this.getJpo().getParent().getString("WERKS");// 工厂
		String mroperson = this.getJpo().getUserServer().getUserInfo()
				.getLoginID();
		mroperson = mroperson.toUpperCase();
		java.util.Date mrodate = this.getJpo().getUserServer().getDate();
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
			Char18 Material = new Char18();// 输入数据--物料编码
			Char4 StgeLoc = new Char4();// 输入数据--库存地点
			Char10 Batch = new Char10();// 输入数据--批次号
			Quantum133 EntryQnt = new Quantum133();// 输入数据--数量
			Unit3 EntryUom = new Unit3();// 输入数据--计量单位
			Char10 Costcenter = new Char10();// 输入数据--成本中心
			Char12 Orderid = new Char12();// 输入数据--生产订单号
			Numeric10 ReservNo = new Numeric10();// 输入数据--预留号
			Numeric4 ResItem = new Numeric4();// 输入数据--预留行号
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

			movetype.setChar3("101");// 参数--移动类型101

			person.setChar10(mroperson);// 参数--点收入
			werks.setChar4(WERKS);// 参数--工厂
			budat.setDate(CREATEDATES);// 参数--过账日期
			TableOfBapi2017GmItemCreate table = new TableOfBapi2017GmItemCreate();
			JSONArray jArray = new JSONArray();
			String itemnum = this.getJpo().getParent().getString("itemnum");// 物料编码
			String unit = this.getJpo().getParent().getString("ORDERUNIT");// 计量单位
			String lotnum = this.getJpo().getString("lotnum");
			String inlocation = this.getJpo().getParent()
					.getJpoSet("LOCATIONIX").getJpo()
					.getString("STOREROOMPARENT");

			String POSNR = this.getJpo().getParent().getString("POSNR");
			String VGBEL = this.getJpo().getParent().getString("VGBEL");
			String VGPOS = this.getJpo().getParent().getString("VGPOS");
			JSONObject rdata = new JSONObject();
			Bapi2017GmItemCreate tableobject = new Bapi2017GmItemCreate();
			PoNumber.setChar10(VGBEL);// 输入数据--采购订单号
			PoItem.setNumeric5(VGPOS);// 输入数据--采购订单行号
			Material.setChar18(itemnum);// 输入数据--物料编码
			Plant.setChar4("");
			StgeLoc.setChar4(inlocation);// 输入数据--库存地点
			MoveStloc.setChar4("");// 输入数据--接收库存地点
			EntryQnt.setQuantum133(new BigDecimal(statusjsqty));// 输入数据--数量
			EntryUom.setUnit3(unit);// 输入数据--计量单位
			Batch.setChar10("1");// 输入数据--批次号
			DelivNumbToSearch.setChar10(CONVERTLOCNUM);// 输入数据--交货单号
			DelivItemToSearch.setNumeric6(POSNR);// 输入数据--交货单行号
			RefDoc.setChar10(CONVERTLOCNUM);// 输入数据--参考凭证的凭证号
			RefDocIt.setNumeric4("");// 输入数据--参考凭证项目
			MvtInd.setChar1("B");// 输入数据--移动标识
			DelivNumb.setChar10("");
			DelivItem.setNumeric6("");
			Costcenter.setChar10("");
			Orderid.setChar12("");
			ReservNo.setNumeric10("");
			ResItem.setNumeric4("");
			MoveReas.setNumeric4("");
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
			ResType.setChar1("");
			Withdrawn.setChar1("");
			MoveMat.setChar18("");
			MovePlant.setChar4("");
			MoveBatch.setChar10("");
			MoveValType.setChar10("");

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
			rdata.put("StgeLoc", inlocation);
			rdata.put("EntryQnt", statusjsqty);
			rdata.put("EntryUom", unit);
			rdata.put("Batch", lotnum);
			rdata.put("MoveStloc", inlocation);

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

			// }
			param.setMoveType(movetype);
			param.setPerson(person);
			param.setWerks(werks);
			param.setWmBudat(budat);
			param.setTGvitem(table);

			num = IFUtil.addIfHistory(IFUtil.ERP_MRO_TOEROCKIF_101,
					jArray.toString(), IFUtil.TYPE_OUTPUT);// 增加输出记录
			ZtfunWmsBasisFunctionResponse res = service
					.ztfunWmsBasisFunction(param);
			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"交货单号：" + CONVERTLOCNUM + ";传递ERP" + 1 + "条;");
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
			num = IFUtil.addIfHistory(IFUtil.ERP_MRO_TOEROCKIF_101,
					returnjArray.toString(), IFUtil.TYPE_INPUT);// 增加输出记录

			// TableOfBapi2017GmItemCreate retable=res.getTGvitem();

			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"交货单号：" + CONVERTLOCNUM + ";接收ERP回传;");

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
	 * <调拨转库单接收后计算库存数量>
	 * @param mprlineset
	 * @throws MroException [参数说明]
	 *
	 */
	public void inventoryqty(String itemnum,String location) throws MroException {// 出库
					InventoryQtyCommon.SQZYQTY(itemnum, location);
					InventoryQtyCommon.FCZTQTY(itemnum, location);
					InventoryQtyCommon.JSZTQTY(itemnum, location);
					InventoryQtyCommon.KYQTY(itemnum, location);
	}
}
