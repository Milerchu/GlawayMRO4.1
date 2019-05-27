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
import com.glaway.sddq.material.invtrans.common.InventoryQtyCommon;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;
/**
 * 
 * <缴库单变更图号弹出框绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-2-22]
 * @since  [产品/模块版本]
 */
public class JkdChangeItemnumDataBean extends DataBean {
	/**
	 * 确认接收按钮方法
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int execute() throws MroException, IOException {
		// TODO Auto-generated method stub
		if (this.getJpo() != null) {
			IJpo parent = this.getJpo().getParent();
			String newitemnum = parent.getString("newitemnum");
			IJpoSet itemset = MroServer.getMroServer().getJpoSet(
					"sys_item", MroServer.getMroServer().getSystemUserServer());
			itemset.setUserWhere("itemnum='" + newitemnum + "'");
			if(itemset.isEmpty()){
				throw new MroException("物料编码在系统中不存在，请先补充物料编码:"+newitemnum+",到系统");
			}else{
				String status = parent.getString("status");
				double statuswjsqty = this.getJpo().getDouble("wjsqty");
				double statusjsqty = this.getJpo().getDouble("jsqty");
				String jponame = this.getParent().getJpo().getName();
				String jpoid = this.getParent().getString("transferlineid");
				IJpoSet erpifaceset = MroServer.getMroServer().getJpoSet(
						"erpiface", MroServer.getMroServer().getSystemUserServer());
				erpifaceset.setUserWhere("jponame='" + jponame + "' and jpoid='"
						+ jpoid + "'");
				erpifaceset.reset();
				if (erpifaceset.isEmpty()) {
					if (status.equalsIgnoreCase("未接收")) {
						if (statusjsqty > statuswjsqty) {
							throw new MroException("transferline", "qty");
						} else if (statusjsqty == 0) {
							throw new MroException("transferline", "zerio");
						} else {
							// 调用可以接收方法
							TOERP();
						}
					}else {
						throw new MroException("jkdtransfer", "noreceive");
					}
				} else {
					throw new MroException("数据或网络异常导致暂时不能接收！请联系管理员去“关账期间异常接收程序进行处理”");
				}
			}
		}
		this.getAppBean().SAVE();
		return GWConstant.NOACCESS_SAMEMETHOD;
	}
	/**
	 * 
	 * <可以接收方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void TOERP() throws MroException {
		IJpo parent = this.getJpo().getParent();
		double passqty = this.getJpo().getDouble("passqty");
		double failqty = this.getJpo().getDouble("failqty");
		double JSQTY = this.getJpo().getDouble("JSQTY");
		double newqty = passqty + failqty;
		String STATUS = parent.getString("STATUS");
		String itemnum=parent.getString("itemnum");
		String issuestoreroom=parent.getString("issuestoreroom");
		String receivestoreroom=parent.getString("receivestoreroom");
		String sqn = parent.getString("sqn");
		String newitemnum = parent.getString("newitemnum");
		String newsqn = parent.getString("newsqn");
		String newassetnum=parent.getString("NEWASSETNUM");
		String SENDNUM = this.getParent().getParent().getString("SENDNUM");
		String RECEIVESTOREROOM = parent.getString("ISSUESTOREROOM");
		String ERPLOC = this.getJpo().getParent().getJpoSet("ISSUESTOREROOM")
				.getJpo().getString("erploc");
		String receivedate = parent.getString("RECEIVEDATE");// 接收日期
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
		if(ERPLOC.equalsIgnoreCase("1020")){
			if(newassetnum.isEmpty()){
				ADDASSET();
			}
			String retu = MroToErp();// 接口方法中返回的retu的值；
			if (retu.equalsIgnoreCase("S")) {
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
					InventoryQtyCommon.SQZYQTY(newitemnum, issuestoreroom);
					InventoryQtyCommon.FCZTQTY(newitemnum, issuestoreroom);
					InventoryQtyCommon.JSZTQTY(newitemnum, issuestoreroom);
					InventoryQtyCommon.KYQTY(newitemnum, issuestoreroom);
					
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
			}else{
				throw new MroException("错误", "缴库单接口错误:" + retu);
			}
		}else{
			if(newassetnum.isEmpty()){
				ADDASSET();
			}
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
				InventoryQtyCommon.SQZYQTY(newitemnum, issuestoreroom);
				InventoryQtyCommon.FCZTQTY(newitemnum, issuestoreroom);
				InventoryQtyCommon.JSZTQTY(newitemnum, issuestoreroom);
				InventoryQtyCommon.KYQTY(newitemnum, issuestoreroom);
				
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
				InventoryQtyCommon.SQZYQTY(newitemnum, issuestoreroom);
				InventoryQtyCommon.FCZTQTY(newitemnum, issuestoreroom);
				InventoryQtyCommon.JSZTQTY(newitemnum, issuestoreroom);
				InventoryQtyCommon.KYQTY(newitemnum, issuestoreroom);
				
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
		}
	}
	public String MroToErp() throws MroException {// 传递接口
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
//	 String retu="S";
//	 return retu;
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

			movetype.setChar3("309");// 参数--移动类型311

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
			String newitemnum = this.getJpo().getParent().getString("newitemnum");// 物料编码
			String newlotnum = this.getJpo().getParent().getString("newlotnum");// 物料编码
			String unit = this.getJpo().getParent().getJpoSet("item").getJpo()
					.getString("ORDERUNIT");// 计量单位
			// String lotnum=this.getJpo().getString("lotnum");
			String lotnum = "1";
			String inlocation = this.getJpo().getParent()
					.getJpoSet("ISSUESTOREROOM").getJpo()
					.getString("STOREROOMPARENT");
			String outlocation= this.getJpo().getParent()
					.getJpoSet("RECEIVESTOREROOM").getJpo()
					.getString("STOREROOMPARENT");

			JSONObject rdata = new JSONObject();
			Bapi2017GmItemCreate tableobject = new Bapi2017GmItemCreate();
			
			Material.setChar18(itemnum);//输入数据--发出物料编码
			StgeLoc.setChar4(outlocation);//输入数据--发出库存地点
			Batch.setChar10(lotnum);//输入数据--批次号
			EntryQnt.setQuantum133(new BigDecimal(statusjsqty));//输入数据--数量
			EntryUom.setUnit3(unit);//输入数据--计量单位
			MoveMat.setChar18(newitemnum);//输入数据--接收物料编码
			MoveStloc.setChar4(inlocation);//输入数据--接收库存地点
			MoveBatch.setChar10(newlotnum);//输入数据--接收批次号
			
			Plant.setChar4("1010");
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
			MovePlant.setChar4("");
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
			
			rdata.put("Material", itemnum);
			rdata.put("StgeLoc", outlocation);
			rdata.put("Batch", lotnum);
			rdata.put("EntryQnt", statusjsqty);
			rdata.put("EntryUom", unit);
			rdata.put("MoveMat", newitemnum);
			rdata.put("MoveStloc", inlocation);
			rdata.put("MoveBatch", newlotnum);

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

			num = IFUtil.addIfHistory(IFUtil.MRO_ERP_JKD, "缴库单号：" + transfernum
					+ ";传递ERP" + 1 + "条;传递数据为：" + jArray.toString() + "",
					IFUtil.TYPE_OUTPUT);// 增加输出记录
			ZtfunWmsBasisFunctionResponse res = service
					.ztfunWmsBasisFunction(param);
			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"缴库单号：" + transfernum + ";传递ERP" + 1 + "条;");
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
			num = IFUtil.addIfHistory(IFUtil.MRO_ERP_JKD,
					returnjArray.toString(), IFUtil.TYPE_INPUT);// 增加输出记录

			// TableOfBapi2017GmItemCreate retable=res.getTGvitem();

			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"缴库单号：" + transfernum + ";接收ERP回传;");
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
				java.util.Date outdate = MroServer.getMroServer().getDate();// --出库时间
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
				java.util.Date outdate = MroServer.getMroServer().getDate();// --出库时间
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
				java.util.Date outdate = MroServer.getMroServer().getDate();// --出库时间
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
		String NEWITEMNUM = parent.getString("NEWITEMNUM");
		String newSqn = parent.getString("newSqn");
		String lotnum = parent.getString("lotnum");
		String newassetnum = parent.getString("newassetnum");
		String siteid = parent.getString("siteid");// --地点
		String orgid = parent.getString("orgid");// --组织
		java.util.Date INSTOREDATE = MroServer.getMroServer().getDate();
		String RECEIVESTOREROOM = parent.getString("ISSUESTOREROOM");/* 缴库单接收库房 */
		String ISSUESTOREROOM = parent.getString("RECEIVESTOREROOM");/* 缴库单发出库房 */

		if (passqty > 0) {
			if (!newSqn.isEmpty()) {
					IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
							"sys_inventory",
							MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
					inventoryset.setUserWhere("itemnum='" + NEWITEMNUM
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
						inventory.setValue("itemnum", NEWITEMNUM,
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
				in_inventoryset.setUserWhere("itemnum='" + NEWITEMNUM
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
						invblanceset.setUserWhere("itemnum='" + NEWITEMNUM
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
							invblance.setValue("itemnum", NEWITEMNUM,
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
						invblanceset.setUserWhere("itemnum='" + NEWITEMNUM
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
							invblance.setValue("itemnum", NEWITEMNUM,
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
					in_inventory.setValue("itemnum", NEWITEMNUM,
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
			if (newSqn.isEmpty() && lotnum.isEmpty()) {
				IJpoSet in_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
				in_inventoryset.setUserWhere("itemnum='" + NEWITEMNUM
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
					in_inventory.setValue("itemnum", NEWITEMNUM,
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
			if (!newSqn.isEmpty()) {
					IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
							"sys_inventory",
							MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
					inventoryset.setUserWhere("itemnum='" + NEWITEMNUM
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
						inventory.setValue("itemnum", NEWITEMNUM,
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
				in_inventoryset.setUserWhere("itemnum='" + NEWITEMNUM
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
						invblanceset.setUserWhere("itemnum='" + NEWITEMNUM
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
							invblance.setValue("itemnum", NEWITEMNUM,
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
						invblanceset.setUserWhere("itemnum='" + NEWITEMNUM
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
							invblance.setValue("itemnum", NEWITEMNUM,
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
					in_inventory.setValue("itemnum", NEWITEMNUM,
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
			if (newSqn.isEmpty() && lotnum.isEmpty()) {
				IJpoSet in_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
				in_inventoryset.setUserWhere("itemnum='" + NEWITEMNUM
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
					in_inventory.setValue("itemnum", NEWITEMNUM,
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
			if (!newSqn.isEmpty()) {
					IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
							"sys_inventory",
							MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
					inventoryset.setUserWhere("itemnum='" + NEWITEMNUM
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
						inventory.setValue("itemnum", NEWITEMNUM,
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
				in_inventoryset.setUserWhere("itemnum='" + NEWITEMNUM
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
						invblanceset.setUserWhere("itemnum='" + NEWITEMNUM
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
							invblance.setValue("itemnum", NEWITEMNUM,
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
						invblanceset.setUserWhere("itemnum='" + NEWITEMNUM
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
							invblance.setValue("itemnum", NEWITEMNUM,
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
					in_inventory.setValue("itemnum", NEWITEMNUM,
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
			if (newSqn.isEmpty() && lotnum.isEmpty()) {
				IJpoSet in_inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());// --调拨入库库存的集合
				in_inventoryset.setUserWhere("itemnum='" + NEWITEMNUM
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
					in_inventory.setValue("itemnum", NEWITEMNUM,
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
				String itemnum = parent.getString("NEWITEMNUM");// --物料编码
				String assetnum = parent.getString("newassetnum");// --资产编号
				String transfernum = parent.getString("transfernum");// --调拨单编号
				java.util.Date indate = MroServer.getMroServer().getDate();// --入库时间
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
				String itemnum = parent.getString("NEWITEMNUM");// --物料编码
				String assetnum = parent.getString("newassetnum");// --资产编号
				String transfernum = parent.getString("transfernum");// --调拨单编号
				java.util.Date indate = MroServer.getMroServer().getDate();// --入库时间
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
				String itemnum = parent.getString("NEWITEMNUM");// --物料编码
				String assetnum = parent.getString("newassetnum");// --资产编号
				String transfernum = parent.getString("transfernum");// --调拨单编号
				java.util.Date indate = MroServer.getMroServer().getDate();// --入库时间
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
		java.util.Date createdate = MroServer.getMroServer().getDate();
		String transferid = parent.getParent().getString("transferid");
		String transferlineid = parent.getString("transferlineid");
		IJpoSet persongroupset = MroServer.getMroServer().getJpoSet(
				"SYS_PERSONGROUPTEAM",
				MroServer.getMroServer().getSystemUserServer());
		persongroupset.setUserWhere("persongroup='FXHFY'");
		persongroupset.reset();
		String personid = persongroupset.getJpo(0).getString("RESPPARTYGROUP");
		String transfernum = parent.getString("transfernum");
		String newitemnum = parent.getString("newitemnum");
		String itemnum = parent.getString("itemnum");
		String sqn = parent.getString("sqn");
		String newsqn = parent.getString("newsqn");
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
			if(newsqn.isEmpty()){
				MSGMANAGE.setValue("content", "缴库单编号为:'" + transfernum
						+ "' ,原物料编码为:'" + itemnum + "',现已变更为:'"+newitemnum+"',已经接收，且合格。需要返修发运到现场，接收库房为:'"
						+ description + "',库房编号为:'" + receivestoreroom
						+ "',接收地址为:'" + receiveaddress + "'。请创建装箱单进行发运！",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}else{
				MSGMANAGE.setValue("content", "缴库单编号为:'" + transfernum
						+ "' ,原物料编码为:'" + itemnum + "',现已变更为:'"+newitemnum+"',且序列号为:'"+newsqn+"',已经接收，且合格。需要返修发运到现场，接收库房为:'"
						+ description + "',库房编号为:'" + receivestoreroom
						+ "',接收地址为:'" + receiveaddress + "'。请创建装箱单进行发运！",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}

		} else {
			if(newsqn.isEmpty()){
				MSGMANAGE.setValue("content", "缴库单编号为:'" + transfernum
						+ "' ,原物料编码为:'" + itemnum + "',现已变更为:'"+newitemnum+"',序列号为:'" + sqn
						+ "'已经接收，且合格。需要返修发运到现场，接收库房为:'" + description + "',库房编号为:'"
						+ receivestoreroom + "',接收地址为:'" + receiveaddress
						+ "'。请创建装箱单进行发运！", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}else{
				if(sqn.equalsIgnoreCase(newsqn)){
					MSGMANAGE.setValue("content", "缴库单编号为:'" + transfernum
							+ "' ,原物料编码为:'" + itemnum + "',现已变更为:'"+newitemnum+"',序列号为:'" + sqn
							+ "'已经接收，且合格。需要返修发运到现场，接收库房为:'" + description + "',库房编号为:'"
							+ receivestoreroom + "',接收地址为:'" + receiveaddress
							+ "'。请创建装箱单进行发运！", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}else{
					MSGMANAGE.setValue("content", "缴库单编号为:'" + transfernum
							+ "' ,原物料编码为:'" + itemnum + "',现已变更为:'"+newitemnum+"'且原序列号为:'" + sqn
							+ "',现已变更为:'"+newsqn+"',已经接收，且合格。需要返修发运到现场，接收库房为:'" + description + "',库房编号为:'"
							+ receivestoreroom + "',接收地址为:'" + receiveaddress
							+ "'。请创建装箱单进行发运！", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
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
		java.util.Date createdate = MroServer.getMroServer().getDate();
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
		String newitemnum = parent.getString("newitemnum");
		String sqn = parent.getString("sqn");
		String newsqn = parent.getString("newsqn");
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
				if(newsqn.isEmpty()){
					MSGMANAGE.setValue("content", "配件申请编号为:'" + mrnum
							+ "',要求返修后发运的物料编码为:'" + itemnum
							+ "'，返修后已变更为:'"+newitemnum+"',已经接收，且合格。请创建装箱单进行发运！",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}else{
					MSGMANAGE.setValue("content", "配件申请编号为:'" + mrnum
							+ "',要求返修后发运的物料编码为:'" + itemnum
							+ "'，返修后已变更为:'"+newitemnum+"',且序列号为:'"+newsqn+"',已经接收，且合格。请创建装箱单进行发运！",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			} else {
				if(newsqn.isEmpty()){
					MSGMANAGE.setValue("content", "配件申请编号为:'" + mrnum
							+ "',要求返修后发运的物料编码为:'" + itemnum
							+ "'，返修后已变更为:'"+newitemnum+"',序列号为:'"+sqn+"',已经接收，且合格。请创建装箱单进行发运！",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);	
				}else{
					if(sqn.equalsIgnoreCase(newsqn)){
						MSGMANAGE.setValue("content", "配件申请编号为:'" + mrnum
								+ "',要求返修后发运的物料编码为:'" + itemnum
								+ "'，返修后已变更为:'"+newitemnum+"',序列号为:'"+sqn+"',已经接收，且合格。请创建装箱单进行发运！",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}else{
						MSGMANAGE.setValue("content", "配件申请编号为:'" + mrnum
								+ "',要求返修后发运的物料编码为:'" + itemnum
								+ "'，返修后已变更为:'"+newitemnum+"',且序列号为:'"+newsqn+"',已经接收，且合格。请创建装箱单进行发运！",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}
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
		String NEWITEMNUM = this.getParent().getJpo().getString("NEWITEMNUM");
		double passqty = this.getJpo().getDouble("passqty");
		double failqty = this.getJpo().getDouble("failqty");
		String RECEIVESTOREROOM = this.getParent().getJpo().getString("ISSUESTOREROOM");
		IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",
				MroServer.getMroServer().getSystemUserServer());
		itemset.setUserWhere("itemnum='" + NEWITEMNUM + "'");
		String type = ItemUtil.getItemInfo(NEWITEMNUM);
		String commomtype="入库";
		if (ItemUtil.SQN_ITEM.equals(type)) {
			IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
					MroServer.getMroServer().getSystemUserServer());
			assetset.setUserWhere("itemnum='"
					+ NEWITEMNUM
					+ "' and sqn='"
					+ newSQN
					+ "' and assetlevel='ASSET' and type!='2' and location is null");
			if (assetset.isEmpty()) {
				IJpo jpo = jpoSet.addJpo();
				jpo.setValue("assetlevel", "ASSET",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jpo.setValue("itemnum", NEWITEMNUM,
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
