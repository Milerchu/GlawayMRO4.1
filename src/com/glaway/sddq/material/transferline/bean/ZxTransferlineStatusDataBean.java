package com.glaway.sddq.material.transferline.bean;

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
import com.glaway.sddq.material.invtrans.common.TransLineInvtranscommon;
import com.glaway.sddq.material.invtrans.common.TransLineStoroomCommon;
import com.glaway.sddq.material.invtrans.common.TransferlinetradeCommon;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * <装箱单接收弹出框DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class ZxTransferlineStatusDataBean extends DataBean {
	/**
	 * 弹出时判断仓位是否必填
	 * 
	 * @throws MroException
	 */
	@Override
	public void initialize() throws MroException {
		// TODO Auto-generated method stub
		super.initialize();
		boolean islocbin = this.page.getAppBean().getJpo()
				.getJpoSet("RECEIVESTOREROOM").getJpo().getBoolean("islocbin");
		if (islocbin) {
			this.getJpo()
					.setFieldFlag("inbinnum", GWConstant.S_READONLY, false);
			this.getJpo().setFieldFlag("inbinnum", GWConstant.S_REQUIRED, true);
		} else {
			this.getJpo()
					.setFieldFlag("inbinnum", GWConstant.S_REQUIRED, false);
			this.getJpo().setFieldFlag("inbinnum", GWConstant.S_READONLY, true);
		}
	}

	/**
	 * 确认接收按钮方法，判断数量是否符合接收条件，调用可以接收方法
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int execute() throws MroException, IOException {
		if (this.getJpo() != null) {
			IJpo parent = this.getJpo().getParent();
			if(parent!=null){
				double statuswjsqty = this.getJpo().getDouble("wjsqty");
				double statusjsqty = this.getJpo().getDouble("jsqty");
				String status = parent.getString("status");
				String jponame = parent.getName();
				String jpoid = parent.getString("transferlineid");
				IJpoSet erpifaceset = MroServer.getMroServer().getJpoSet(
						"erpiface", MroServer.getMroServer().getSystemUserServer());
				erpifaceset.setUserWhere("jponame='" + jponame + "' and jpoid='"
						+ jpoid + "'");
				erpifaceset.reset();
				if (erpifaceset.isEmpty()) {
					if (status.equalsIgnoreCase("未接收")||status.equalsIgnoreCase("部分接收")) {
						if (statusjsqty > statuswjsqty) {
							throw new MroException("transferline", "qty");
						} else if (statusjsqty == 0) {
							throw new MroException("transferline", "zerio");
						} else {
							// 调用可以接收方法
							TOERP();
						}
					}
				} else {
					throw new MroException("数据或网络异常导致暂时不能接收！请联系管理员先查询是否调用接口成功，如果成功请管理员后台进行将此条数据变更为已接收，如果没有，请管理员去“关账期间异常接收程序进行处理”");
				}
			}else{
				throw new MroException("数据异常");
			}
		}
//		this.getAppBean().SAVE();

		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	/**
	 * 
	 * <可以接收方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * @throws IOException 
	 * 
	 */
	public void TOERP() throws MroException, IOException {
		IJpo parent = this.getJpo().getParent();
		double statuswjsqty = this.getJpo().getDouble("wjsqty");
		double statusjsqty = this.getJpo().getDouble("jsqty");
		double YJSQTY = parent.getDouble("YJSQTY");
		double newyjsqty = YJSQTY + statusjsqty;
		String inbinnum = this.getJpo().getString("inbinnum");
		String ISSUESTOREROOM_parent = this.getJpo().getParent()
				.getJpoSet("ISSUESTOREROOM").getJpo()
				.getString("STOREROOMPARENT");// 发出库房父级
		String RECEIVESTOREROOM_parent = this.getJpo().getParent()
				.getJpoSet("RECEIVESTOREROOM").getJpo()
				.getString("STOREROOMPARENT");// 接收库房父级
		String ERPLOC = this.getJpo().getParent().getJpoSet("ISSUESTOREROOM")
				.getJpo().getString("erploc");
		String transfernum = parent.getString("transfernum");
		String iskhitem = parent.getString("iskhitem");//是否客户料返修自动生成
		String transferlineid = parent.getString("transferlineid");
		if (ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1020)
				|| ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1030)) {
			if (ISSUESTOREROOM_parent.equalsIgnoreCase(RECEIVESTOREROOM_parent)) {
				String jponame = this.getParent().getJpo().getName();
				String jpoid = this.getParent().getString("transferlineid");
				IJpoSet erpifaceset = MroServer.getMroServer().getJpoSet("erpiface",
						MroServer.getMroServer().getSystemUserServer());
				erpifaceset.setUserWhere("jponame='" + jponame + "' and jpoid='"
						+ jpoid + "'");
				erpifaceset.reset();
				if (erpifaceset.isEmpty()) {
					IJpo erpiface = erpifaceset.addJpo();
					erpiface.setValue("jponame", jponame,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					erpiface.setValue("jpoid", jpoid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					erpifaceset.save();
				}
				parent.setValue("inbinnum", inbinnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
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
					transferlineset.setUserWhere("transfernum='" + transfernum
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
				// 调用公共出库方法
				TransLineStoroomCommon.out_storoom(parent, statusjsqty);
				// 调用公共出库记录方法
				TransLineInvtranscommon.out_invtrans(parent, statusjsqty);
				// 调用公共入库方法
				TransLineStoroomCommon.in_storoom(parent, statusjsqty);
				// 调用公共入库记录方法
				TransLineInvtranscommon.in_invtrans(parent, statusjsqty);
				// 调用修改相关配件申请接收数量方法
				ADDENDPAYQTY();
				// 调用增加装箱单接收记录方法
				TransferlinetradeCommon.add_trade(parent, statusjsqty);
				this.getAppBean().SAVE();
				String status=parent.getString("status");
				if(status.equalsIgnoreCase("未接收")){
					throw new MroException("库存已变动，但由于网络原因状态为变更，请联系管理员后台进行将此条数据变更为已接收");
				}
			} else {
				// 调用ERP接口方法
				String retu = MroToErp();// 接口方法中返回的retu的值；
				if (retu.equalsIgnoreCase("S")) {
					System.out.println("跟踪装箱单----------------------------------------------------1");
					parent.setValue("inbinnum", inbinnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					System.out.println("跟踪装箱单----------------------------------------------------2");
					parent.setValue("YJSQTY", newyjsqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					System.out.println("跟踪装箱单----------------------------------------------------3");
					if (statuswjsqty == statusjsqty) {
						parent.setValue("status", "已接收",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						System.out.println("跟踪装箱单----------------------------------------------------4");
						IJpoSet transferlineset = MroServer.getMroServer()
								.getJpoSet(
										"transferline",
										MroServer.getMroServer()
												.getSystemUserServer());
						transferlineset.setUserWhere("transfernum='"
								+ transfernum
								+ "' and status!='已接收' and transferlineid!='"
								+ transferlineid + "'");
						System.out.println("跟踪装箱单----------------------------------------------------5");
						if (transferlineset.isEmpty()) {
							System.out.println("跟踪装箱单----------------------------------------------------6");
							parent.getParent().setValue("status", "已接收",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							System.out.println("跟踪装箱单----------------------------------------------------7");
						}
					} else {
						System.out.println("跟踪装箱单----------------------------------------------------8");
						parent.setValue("status", "部分接收",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						System.out.println("跟踪装箱单----------------------------------------------------9");
					}
					System.out.println("跟踪装箱单----------------------------------------------------10");
					// 调用公共出库方法
					TransLineStoroomCommon.out_storoom(parent, statusjsqty);
					System.out.println("跟踪装箱单----------------------------------------------------11");
					// 调用公共出库记录方法
					TransLineInvtranscommon.out_invtrans(parent, statusjsqty);
					System.out.println("跟踪装箱单----------------------------------------------------12");
					// 调用公共入库方法
					TransLineStoroomCommon.in_storoom(parent, statusjsqty);
					System.out.println("跟踪装箱单----------------------------------------------------13");
					// 调用公共入库记录方法
					TransLineInvtranscommon.in_invtrans(parent, statusjsqty);
					System.out.println("跟踪装箱单----------------------------------------------------14");
					// 调用修改相关配件申请接收数量方法
					ADDENDPAYQTY();
					System.out.println("跟踪装箱单----------------------------------------------------15");
					// 调用增加装箱单接收记录方法
					TransferlinetradeCommon.add_trade(parent, statusjsqty);
					System.out.println("跟踪装箱单----------------------------------------------------16");
					this.getAppBean().SAVE();
					System.out.println("跟踪装箱单----------------------------------------------------17");
					String status=parent.getString("status");
					System.out.println("跟踪装箱单----------------------------------------------------18");
					if(status.equalsIgnoreCase("未接收")){
						System.out.println("跟踪装箱单----------------------------------------------------19");
						throw new MroException("接口已过账，但由于网络原因状态为变更，请联系管理员后台进行将此条数据变更为已接收");
					}
				} else {
					throw new MroException("错误", "装箱单接口错误:" + retu);
				}
			}
		} else {
			String jponame = this.getParent().getJpo().getName();
			String jpoid = this.getParent().getString("transferlineid");
			IJpoSet erpifaceset = MroServer.getMroServer().getJpoSet("erpiface",
					MroServer.getMroServer().getSystemUserServer());
			erpifaceset.setUserWhere("jponame='" + jponame + "' and jpoid='"
					+ jpoid + "'");
			erpifaceset.reset();
			if (erpifaceset.isEmpty()) {
				IJpo erpiface = erpifaceset.addJpo();
				erpiface.setValue("jponame", jponame,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				erpiface.setValue("jpoid", jpoid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				erpifaceset.save();
			}
			parent.setValue("inbinnum", inbinnum,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			parent.setValue("YJSQTY", newyjsqty,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			if (statuswjsqty == statusjsqty) {
				parent.setValue("status", "已接收",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				IJpoSet transferlineset = MroServer.getMroServer().getJpoSet(
						"transferline",
						MroServer.getMroServer().getSystemUserServer());
				transferlineset.setUserWhere("transfernum='" + transfernum
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
			// 调用公共出库方法
			TransLineStoroomCommon.out_storoom(parent, statusjsqty);
			// 调用公共出库记录方法
			TransLineInvtranscommon.out_invtrans(parent, statusjsqty);
			// 调用公共入库方法
			TransLineStoroomCommon.in_storoom(parent, statusjsqty);
			// 调用公共入库记录方法
			TransLineInvtranscommon.in_invtrans(parent, statusjsqty);
			// 调用修改相关配件申请接收数量方法
			ADDENDPAYQTY();
			// 调用增加装箱单接收记录方法
			TransferlinetradeCommon.add_trade(parent, statusjsqty);
			if(!iskhitem.isEmpty()){
				if(iskhitem.equalsIgnoreCase("是")){
					CREATETKITEMREQ();//自动生成退库单
				}
			}
			this.getAppBean().SAVE();
			String status=parent.getString("status");
			if(status.equalsIgnoreCase("未接收")){
				throw new MroException("库存已变动，但由于网络原因状态为变更，请联系管理员后台进行将此条数据变更为已接收");
			}
		}
	}

	/**
	 * 
	 * <ERP接口方法>
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public String MroToErp() throws MroException {// 传递接口
	// String retu="S";
	// return retu;
		String jponame = this.getParent().getJpo().getName();
		String jpoid = this.getParent().getString("transferlineid");
		IJpoSet erpifaceset = MroServer.getMroServer().getJpoSet("erpiface",
				MroServer.getMroServer().getSystemUserServer());
		erpifaceset.setUserWhere("jponame='" + jponame + "' and jpoid='"
				+ jpoid + "'");
		erpifaceset.reset();
		if (erpifaceset.isEmpty()) {
			IJpo erpiface = erpifaceset.addJpo();
			erpiface.setValue("jponame", jponame,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			erpiface.setValue("jpoid", jpoid,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			erpifaceset.save();
		}
		String transfernum = this.page.getAppBean().getJpo()
				.getString("transfernum");
		String statusjsqty = this.getJpo().getString("jsqty");
		String num = "";
		IJpo transferJpo = this.page.getAppBean().getJpo();
		String ERPLOC = this.getJpo().getParent().getJpoSet("ISSUESTOREROOM")
				.getJpo().getString("erploc");
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

			movetype.setChar3("311");// 参数--移动类型311

			person.setChar10(mroperson);// 参数--点收入
			werks.setChar4(ERPLOC);// 参数--工厂
			budat.setDate(CREATEDATES);// 参数--过账日期
			// IJpoSet TRANSFERLINEset =
			// this.getJpo().getJpoSet("TRANSFERLINE");
			TableOfBapi2017GmItemCreate table = new TableOfBapi2017GmItemCreate();
			JSONArray jArray = new JSONArray();
			// for (int i = 0; i < TRANSFERLINEset.count(); i++) {
			// IJpo TRANSFERLINE = TRANSFERLINEset.getJpo(i);
			String itemnum = this.getJpo().getParent().getString("itemnum");// 物料编码
			String unit = this.getJpo().getParent().getJpoSet("item").getJpo()
					.getString("ORDERUNIT");// 计量单位
			// String lotnum=this.getJpo().getString("lotnum");
			String lotnum = "1";
			String outlocation = this.getJpo().getParent()
					.getJpoSet("ISSUESTOREROOM").getJpo()
					.getString("STOREROOMPARENT");
			String inlocation = this.getJpo().getParent()
					.getJpoSet("RECEIVESTOREROOM").getJpo()
					.getString("STOREROOMPARENT");

			JSONObject rdata = new JSONObject();
			Bapi2017GmItemCreate tableobject = new Bapi2017GmItemCreate();
			Material.setChar18(itemnum);// 输入数据--物料编码
			Plant.setChar4("1010");
			StgeLoc.setChar4(outlocation);// 输入数据--发出库存地点
			MoveStloc.setChar4(inlocation);// 输入数据--接收库存地点
			EntryQnt.setQuantum133(new BigDecimal(statusjsqty));// 输入数据--数量
			EntryUom.setUnit3(unit);
			Batch.setChar10(lotnum);
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
			ResType.setChar1("");
			Withdrawn.setChar1("");
			MoveMat.setChar18("");
			MovePlant.setChar4("");
			MoveBatch.setChar10("");
			MoveValType.setChar10("");
			MvtInd.setChar1("");
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
			rdata.put("StgeLoc", outlocation);
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

			num = IFUtil.addIfHistory(IFUtil.MRO_ERP_ZXD, "装箱单号：" + transfernum
					+ ";传递ERP" + 1 + "条;传递数据为：" + jArray.toString() + "",
					IFUtil.TYPE_OUTPUT);// 增加输出记录
			ZtfunWmsBasisFunctionResponse res = service
					.ztfunWmsBasisFunction(param);
			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"装箱单号：" + transfernum + ";传递ERP" + 1 + "条;");
			IFUtil.updatedescriptionIfHistory(num, jpoid);//调拨单行ID写入接口记录表
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
			num = IFUtil.addIfHistory(IFUtil.MRO_ERP_ZXD,
					returnjArray.toString(), IFUtil.TYPE_INPUT);// 增加输出记录

			// TableOfBapi2017GmItemCreate retable=res.getTGvitem();

			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"装箱单号：" + transfernum + ";接收ERP回传;");
			IFUtil.updatedescriptionIfHistory(num, jpoid);//调拨单行ID写入接口记录表

			return retu;
		} catch (Exception e) {
			IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
					e.getMessage());
			IFUtil.updatedescriptionIfHistory(num, jpoid);//调拨单行ID写入接口记录表
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * <变更相关配件申请接收数量方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void ADDENDPAYQTY() throws MroException {
		String mrnum = this.page.getAppBean().getJpo().getString("mrnum");
		String TRANSFERMOVETYPE = this.page.getAppBean().getJpo()
				.getString("TRANSFERMOVETYPE");
		if (TRANSFERMOVETYPE.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)
				|| TRANSFERMOVETYPE
						.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)) {
			if (!mrnum.equalsIgnoreCase("")) {
				IJpo parent = this.getJpo().getParent();
				String itemnum = parent.getString("itemnum");
				double ORDERQTY = parent.getDouble("ORDERQTY");// 装箱数量
				IJpoSet mrlineset = MroServer.getMroServer().getJpoSet(
						"mrline",
						MroServer.getMroServer().getSystemUserServer());
				mrlineset.setUserWhere("mrnum='" + mrnum + "' and itemnum='"
						+ itemnum + "'");
				if (!mrlineset.isEmpty()) {
					double endpayqty = mrlineset.getJpo(0).getDouble(
							"endpayqty");// 配件申请已调拨数量
					double newqty = endpayqty + ORDERQTY;
					mrlineset.getJpo(0).setValue("endpayqty", newqty);
					mrlineset.save();
				}
			}
		}

	}
	/**
	 * 
	 * <自动生成退库单>
	 * @throws MroException [参数说明]
	 *
	 */
	public void CREATETKITEMREQ() throws MroException {
		IJpo parent=this.getJpo().getParent();
		String location=parent.getString("receivestoreroom");
		String rkmprlineid=parent.getString("rkmprlineid");
		String loginid = this.getJpo().getUserServer().getUserInfo().getLoginID();
		loginid = loginid.toUpperCase();
		String orgid = parent.getString("orgid");
		String siteid = parent.getString("siteid");
		java.util.Date createdate = this.getJpo().getUserServer().getDate();
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
		.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
		.setDefaultSite("ELEC");
		IJpoSet mprset = MroServer.getMroServer().getJpoSet("mpr",MroServer.getMroServer().getSystemUserServer());
		IJpo mpr = mprset.addJpo();
		String mprnum = mpr.getString("mprnum");
		String mprid = mpr.getString("mprid");
		IJpoSet deptset = MroServer.getMroServer().getJpoSet(
				"SYS_DEPT",
				MroServer.getMroServer().getSystemUserServer());
		deptset.setUserWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
				+ loginid + "')");
		deptset.reset();
		if (!deptset.isEmpty()) {
			String deptnum = deptset.getJpo(0).getString("deptnum");
			String retrun = WorkorderUtil
					.getAbbreviationByDeptnum(deptnum);
			if (retrun.equalsIgnoreCase("PJGL")) {
				mprnum = "ZX-" + "RK" + "-" + mprnum;
			} else {
				mprnum = retrun + "-" + "RK" + "-" + mprnum;
			}
		} else {
			mprnum = "ZX-" + "RK" + "-" + mprnum;
		}
		mpr.setValue("mprnum", mprnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		mpr.setValue("MEMO", "客户料入库上下车返修后合格退库",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		mpr.setValue("APPLICANTDATE", createdate,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		mpr.setValue("MPRSTOREROOM", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		mpr.setValue("APPLICANTBY", loginid,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		mpr.setValue("mprtype", "TK",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		mpr.setValue("siteid", siteid,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		mpr.setValue("orgid", orgid,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		mpr.setValue("status", "草稿",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		IJpoSet locationset = MroServer.getMroServer().getJpoSet("locations",MroServer.getMroServer().getSystemUserServer());
		locationset.setUserWhere("location='"+location+"'");
		if(!locationset.isEmpty()){
			String KEEPER=locationset.getJpo(0).getString("KEEPER");
			if(!KEEPER.isEmpty()){
				mpr.setValue("RECIVEBY", KEEPER,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);	
			}
		}
		mprset.save();
		IJpoSet rkmprlineset = MroServer.getMroServer().getJpoSet("mprline",MroServer.getMroServer().getSystemUserServer());
		rkmprlineset.setUserWhere("mprlineid='"+rkmprlineid+"'");
		if(!rkmprlineset.isEmpty()){
			IJpoSet tkmprlineset = MroServer.getMroServer().getJpoSet("mprline",MroServer.getMroServer().getSystemUserServer());
			IJpo tkmprline = tkmprlineset.addJpo();
			tkmprline.setValue("mprnum", mprnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			
			tkmprline.setValue("rkmprlineid", rkmprlineid,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			tkmprline.setValue("ISSUESTOREROOM", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			tkmprline.setValue("SITEID", siteid);
			tkmprline.setValue("ORGID", orgid);
			tkmprline.setValue("qty", parent.getDouble("yjsqty"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			tkmprline.setValue("status", "未退库",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			tkmprline.setValue("PARENTLOCATION", rkmprlineset.getJpo(0).getString("PARENTLOCATION"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			tkmprline.setValue("PARENTITEMNUM", rkmprlineset.getJpo(0).getString("PARENTITEMNUM"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			tkmprline.setValue("PARENTSQN", rkmprlineset.getJpo(0).getString("PARENTSQN"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			tkmprline.setValue("PARENTSQNASSETNUM", rkmprlineset.getJpo(0).getString("PARENTSQNASSETNUM"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			tkmprline.setValue("PARENTASSETNUM", rkmprlineset.getJpo(0).getString("PARENTASSETNUM"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			
			tkmprline.setValue("itemnum", parent.getString("itemnum"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			tkmprline.setValue("sqn", parent.getString("sqn"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			tkmprline.setValue("assetnum", parent.getString("assetnum"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			tkmprline.setValue("lotnum", parent.getString("lotnum"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			tkmprlineset.save();
			CALLPERSON(mprnum,mprid,loginid);
		}		
	}
	/**
	 * 
	 * <发送消息功能>
	 * @throws MroException [参数说明]
	 *
	 */
	public void CALLPERSON(String mprnum,String mprid,String loginid) throws MroException {
		IJpo parent = this.getJpo().getParent();
		String siteid = parent.getString("siteid");
		String orgid = parent.getString("orgid");
		IJpoSet MSGMANAGEset = MroServer.getMroServer().getJpoSet("MSGMANAGE",
				MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		java.util.Date createdate = this.getJpo().getUserServer().getDate();
		IJpo MSGMANAGE = MSGMANAGEset.addJpo();
		MSGMANAGE.setValue("app", "TKITEMREQ",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("appid", mprid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			MSGMANAGE.setValue("content","客户料返修合格现场接收，已生成退库单，单号为："+mprnum+",请根据单号前往退库单程序进行退库操作", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("createdate", createdate,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("msgnum", mprid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("receiver", loginid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("orgid", orgid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("siteid", siteid,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("status", "新增",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		MSGMANAGE.setValue("subject", "客户料返修退库通知",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

		MSGMANAGEset.save();
	}
}
