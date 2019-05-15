package com.glaway.sddq.material.transfer.bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient3.HttpTransportPropertiesImpl.Authenticator;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
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
import com.glaway.sddq.material.invtrans.common.CommonAddNewInventory;
import com.glaway.sddq.material.invtrans.common.InventoryQtyCommon;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <装箱单AppBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class ZxTransferAppBean extends AppBean {
	/**
	 * 装箱单保存设置关联的配件申请装箱数量，发货数量，已接收数量
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 */
	@Override
	public int SAVE() throws IOException, MroException {
		// TODO Auto-generated method stub
		super.SAVE();
		IJpo transferJpo = getJpo();
		String mrnum = transferJpo.getString("mrnum");
		String status = transferJpo.getString("status");
		if (!mrnum.isEmpty()) {
			double sumzxdqty = 0.00;
			String MRTYPE = transferJpo.getJpoSet("mr").getJpo(0)
					.getString("MRTYPE");
			if (MRTYPE.equalsIgnoreCase("零星")) {
				IJpoSet mrlinetransferset = MroServer.getMroServer().getJpoSet(
						"mrlinetransfer",
						MroServer.getMroServer().getSystemUserServer());
				mrlinetransferset.setUserWhere("mrnum='" + mrnum
						+ "' and transtype!='计划经理协调'");
				mrlinetransferset.reset();
				for (int i = 0; i < mrlinetransferset.count(); i++) {
					IJpo mrlinetransfer = mrlinetransferset.getJpo(i);
					String mrlinetransferid = mrlinetransfer
							.getString("mrlinetransferid");
					IJpoSet transferlineset = MroServer.getMroServer()
							.getJpoSet(
									"transferline",
									MroServer.getMroServer()
											.getSystemUserServer());
					transferlineset.setUserWhere("mrlinetransid='"
									+ mrlinetransferid
									+ "' and transfernum in (select transfernum from transfer where type='ZXD' and status!='驳回')");
					transferlineset.reset();
					if (!transferlineset.isEmpty()) {
						sumzxdqty = transferlineset.sum("orderqty");
						mrlinetransfer.setValue("zxqty", sumzxdqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					} else {
						mrlinetransfer.setValue("zxqty", 0,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					IJpoSet fctransferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
					fctransferlineset
							.setUserWhere("mrlinetransid='"
									+ mrlinetransferid
									+ "'  and transfernum in (select transfernum from transfer where type='ZXD' and issue='是' and status not in ('驳回','发运人修改','未处理','申请人修改'))");
					fctransferlineset.reset();
					if (!fctransferlineset.isEmpty()) {
						double  sumsendqty= fctransferlineset.sum("orderqty");
						mrlinetransfer.setValue("sendqty", sumsendqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}else{
						mrlinetransfer.setValue("sendqty", 0,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					IJpoSet rectransferlineset = MroServer.getMroServer()
							.getJpoSet(
									"transferline",
									MroServer.getMroServer()
											.getSystemUserServer());
					rectransferlineset.setUserWhere("mrlinetransid='"
							+ mrlinetransferid + "' and status='已接收'");
					rectransferlineset.reset();
					if (!rectransferlineset.isEmpty()) {
						double mrreceiveqty = 0.00;
						double sumreceiveqty = rectransferlineset.sum("yjsqty");
						mrlinetransfer.setValue("receiveqty", sumreceiveqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						String transtype = mrlinetransfer
								.getString("transtype");
						if (transtype.equalsIgnoreCase("下达计划")) {
							mrreceiveqty = mrlinetransfer.getDouble("jhqty");
						} else {
							mrreceiveqty = mrlinetransfer
									.getDouble("TRANSFERQTY");
						}
						if (mrreceiveqty == sumreceiveqty) {
							mrlinetransfer.setValue("status", "全部接收",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						} else {
							mrlinetransfer.setValue("status", "部分接收",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					} else {
						mrlinetransfer.setValue("receiveqty", 0,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}
				mrlinetransferset.save();
				IJpoSet mrset = MroServer.getMroServer().getJpoSet("mr",
						MroServer.getMroServer().getSystemUserServer());
				mrset.setUserWhere("mrnum='" + mrnum + "'");
				mrset.reset();
				if (!mrset.isEmpty()) {
					IJpoSet newmrlinetransferset = mrset.getJpo().getJpoSet(
							"ALLMRLINETRANSFER");
					newmrlinetransferset
							.setUserWhere("transtype not in ('计划经理协调','退回申请人') and status!='全部接收'");
					newmrlinetransferset.reset();
					if (newmrlinetransferset.isEmpty()) {
						mrset.getJpo(0).setValue("status", "关闭",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					mrset.save();
				}
			}
			if (MRTYPE.equalsIgnoreCase("项目")) {
				IJpoSet mrlineset = MroServer.getMroServer().getJpoSet(
						"mrline",
						MroServer.getMroServer().getSystemUserServer());
				mrlineset.setUserWhere("mrnum='" + mrnum + "'");
				mrlineset.reset();
				for (int i = 0; i < mrlineset.count(); i++) {
					IJpo mrline = mrlineset.getJpo(i);
					String mrlineid = mrline.getString("mrlineid");
					IJpoSet transferlineset = MroServer.getMroServer()
							.getJpoSet(
									"transferline",
									MroServer.getMroServer()
											.getSystemUserServer());
					transferlineset
							.setUserWhere("mrlineid='"
									+ mrlineid
									+ "' and transfernum in (select transfernum from transfer where type='ZXD' and status!='驳回')");
					transferlineset.reset();
					if (!transferlineset.isEmpty()) {
						sumzxdqty = transferlineset.sum("orderqty");
						mrline.setValue("zxqty", sumzxdqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// if (status.equalsIgnoreCase("在途")) {
//							mrline.setValue("sendqty", sumzxdqty,
//									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//						}
					} else {
						mrline.setValue("zxqty", 0,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					IJpoSet fctransferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
					fctransferlineset
							.setUserWhere("mrlineid='"
									+ mrlineid
									+ "'   and transfernum in (select transfernum from transfer where type='ZXD'  and issue='是' and status not in ('驳回','发运人修改','未处理','申请人修改'))");
					fctransferlineset.reset();
					if (!fctransferlineset.isEmpty()) {
						double  sumsendqty= fctransferlineset.sum("orderqty");
						mrline.setValue("sendqty", sumsendqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}else{
						mrline.setValue("sendqty", 0,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					IJpoSet rectransferlineset = MroServer.getMroServer()
							.getJpoSet(
									"transferline",
									MroServer.getMroServer()
											.getSystemUserServer());
					rectransferlineset.setUserWhere("mrlineid='" + mrlineid
							+ "' and status='已接收'");
					rectransferlineset.reset();
					if (!rectransferlineset.isEmpty()) {
						double sumreceiveqty = rectransferlineset.sum("yjsqty");
						mrline.setValue("receiveqty", sumreceiveqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						double lineqty = mrline.getDouble("ENDQTY");
						if (lineqty == sumreceiveqty) {
							mrline.setValue("status", "全部接收",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						} else {
							mrline.setValue("status", "部分接收",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					} else {
						mrline.setValue("receiveqty", 0,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}
				mrlineset.save();
				IJpoSet mrset = MroServer.getMroServer().getJpoSet("mr",
						MroServer.getMroServer().getSystemUserServer());
				mrset.setUserWhere("mrnum='" + mrnum + "'");
				mrset.reset();
				if (!mrset.isEmpty()) {
					IJpoSet newmrlineset = mrset.getJpo().getJpoSet("mrline");
					newmrlineset.setUserWhere("status!='全部接收'");
					newmrlineset.reset();
					if (newmrlineset.isEmpty()) {
						mrset.getJpo(0).setValue("status", "关闭",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					mrset.save();
				}
			}
		}
		 if(status.equalsIgnoreCase("在途")||status.equalsIgnoreCase("已接收")||status.equalsIgnoreCase("部分接收")){
			String transfernum = transferJpo.getString("transfernum");
			IJpoSet transferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
			transferlineset.setUserWhere("transfernum='"+transfernum+"'");
			transferlineset.reset();
			if(!transferlineset.isEmpty()){
				for(int i=0;i<transferlineset.count();i++){
					IJpo transferline=transferlineset.getJpo(i);
					String itemnum=transferline.getString("itemnum");
				String issuestoreroom = transferline
						.getString("issuestoreroom");// 发出库房
				String receivestoreroom = transferline
						.getString("receivestoreroom");// 接收库房
					InventoryQtyCommon.SQZYQTY(itemnum, issuestoreroom);
					InventoryQtyCommon.FCZTQTY(itemnum, issuestoreroom);
					InventoryQtyCommon.JSZTQTY(itemnum, issuestoreroom);
					InventoryQtyCommon.KYQTY(itemnum, issuestoreroom);
					
					InventoryQtyCommon.SQZYQTY(itemnum, receivestoreroom);
					InventoryQtyCommon.FCZTQTY(itemnum, receivestoreroom);
					InventoryQtyCommon.JSZTQTY(itemnum, receivestoreroom);
					InventoryQtyCommon.KYQTY(itemnum, receivestoreroom);
				}
			}
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	/**
	 * 发送工作流判断行是否为空，数量是否为空、为零。判断序列号是否缺少assetnum
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public int ROUTEWF() throws Exception {
		// TODO Auto-generated method stub
		boolean nullFlag = false;
		IJpo transferJpo = getJpo();
		String status = transferJpo.getString("status");
		String issue = transferJpo.getString("issue");// 是否发出标记
		String SXTYPE=transferJpo.getString("SXTYPE");
		String TRANSFERMOVETYPE = transferJpo.getString("TRANSFERMOVETYPE");
		IJpoSet transferlineset = transferJpo.getJpoSet("transferline");
		if (!transferlineset.isEmpty()) {
			for (int i = 0; i < transferlineset.count(); i++) {
				IJpo transferline = transferlineset.getJpo(i);
				String itemnum = transferline.getString("itemnum");
				String issuestoreroom = transferline.getString("issuestoreroom");
				//----------------
				double orderqty=transferline.getDouble("orderqty");
				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet("sys_inventory", MroServer.getMroServer().getSystemUserServer());
				inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+issuestoreroom+"'");
				String mrnum=transferJpo.getString("mrnum");
				double kyqty =0;
				if(mrnum.isEmpty()){
					kyqty = inventoryset.getJpo(0).getDouble("kyqty");// 可用数量	
				}else{
					kyqty = inventoryset.getJpo(0).getDouble("sqzyqty");// 可用数量
				}
				if(kyqty==0){
					throw new MroException("物料编码:'" + itemnum + "',库存可用数量为0");
				}else if(kyqty>0){
					IJpoSet jkdtransferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
					jkdtransferlineset.setUserWhere("itemnum='"+ itemnum+ "' and receivestoreroom='"+ issuestoreroom+ "' and status='未接收' and transfernum in (select transfernum from transfer where status='未处理' and type='JKD')");
					jkdtransferlineset.reset();
					
					IJpoSet zstransferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
					zstransferlineset.setUserWhere("itemnum='"+ itemnum+ "' and ISSUESTOREROOM='"+ issuestoreroom+ "' and status='未接收' and transfernum in (select transfernum from transfer where status='未处理' and type in ('SX','ZXD'))");
					zstransferlineset.reset();
					if(jkdtransferlineset.isEmpty()){
						if(zstransferlineset.isEmpty()){
							double newqty=kyqty-orderqty;
							if(newqty<0){
								throw new MroException("物料编码:'" + itemnum + "',数量大于库存可用数量");
							}
						}else{
							double sumtransferorderqty=zstransferlineset.sum("orderqty");
							double newqty=kyqty-(orderqty+sumtransferorderqty);
							if(newqty<0){
								throw new MroException("物料编码:'" + itemnum + "',累计发货数量大于库存可用数量");
							}
						}
					}else{
						double sumjkdtransferorderqty=jkdtransferlineset.sum("orderqty");
						if(zstransferlineset.isEmpty()){
							double newqty=kyqty-(orderqty+sumjkdtransferorderqty);
							if(newqty<0){
								throw new MroException("物料编码:'" + itemnum + "',累计发货数量大于库存可用数量");
							}
						}else{
							double sumtransferorderqty=zstransferlineset.sum("orderqty");
							double newqty=kyqty-(orderqty+sumtransferorderqty+sumjkdtransferorderqty);
							if(newqty<0){
								throw new MroException("物料编码:'" + itemnum + "',累计发货数量大于库存可用数量");
							}
						}
					}
				}
				//-------------
				String sqn = transferline.getString("sqn");
				String assetnum = transferline.getString("assetnum");
				String lotnum = transferline.getString("lotnum");
				boolean issqn = transferline.getJpoSet("item").getJpo(0)
						.getBoolean("ISTURNOVERERP");
				boolean islot = transferline.getJpoSet("item").getJpo(0)
						.getBoolean("ISLOTERP");
				if (issqn) {
					if (sqn.isEmpty()) {
						throw new MroException("行:'"
								+ transferline.getString("TRANSFERLINENUM")
								+ "',物料编码:'" + itemnum + "',为序列号管理请添加序列号");
					} else {
						if (assetnum.isEmpty()) {
							throw new MroException("行:'"
									+ transferline.getString("TRANSFERLINENUM")
									+ "',物料编码:'" + itemnum + "',缺失资产编号,请管理员处理");
						}
					}
				} else if (islot && lotnum.isEmpty()) {
					throw new MroException("行:'"
							+ transferline.getString("TRANSFERLINENUM")
							+ "',物料编码:'" + itemnum + "',为批次管理请添加批次号");
				}
			}
			for (int index = 0; index < transferlineset.count(); index++) {
				String qty = transferlineset.getJpo(index)
						.getString("orderqty");
				if (StringUtil.isStrEmpty(qty)) {
					nullFlag = true;
					break;
				}
			}
			if (nullFlag) {
				throw new AppException("transferline", "qtyisnull");
			}
			if (!TRANSFERMOVETYPE
					.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
				if (status.equalsIgnoreCase("发运人审核")
						&& issue.equalsIgnoreCase("否")) {
					throw new MroException("transfer", "wfissued");
				}
			} else {
				if (status.equalsIgnoreCase("未处理")
						&& issue.equalsIgnoreCase("否")) {
					throw new MroException("transfer", "wfissued");
				}
			}
		} else {
			throw new MroException("transfer", "notransferline");
		}
		if(!WfControlUtil.hasActiveWf(transferJpo)){
			java.util.Date newdate = MroServer.getMroServer().getDate();
			transferJpo.setValue("CREATEDATE",newdate, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
		if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到中心")
				&& status.equalsIgnoreCase("驳回")
				&& SXTYPE.equalsIgnoreCase("GZ")) {
			ADDZXDTRANSFERTOQMSREPAIRINFO();
		}
		return super.ROUTEWF();
	}

	/**
	 * 
	 * <判断是否能发运，并调用改变序列号状态方法，调用接收在途新增数据方法，调用变更缴库单提示消息方法>
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void ISSUE() throws MroException, IOException {
		this.getAppBean().SAVE();
		IJpo transferJpo = getJpo();
		String transfernum = transferJpo.getString("transfernum");
		String transfermovetype = transferJpo.getString("transfermovetype");
		String sxtype=transferJpo.getString("sxtype");
		//getZxTaskNum(transfernum, transfermovetype,sxtype);
		// 有工作流
		if (WfControlUtil.hasActiveWf(transferJpo)
				&& !WfControlUtil.isCurUser(transferJpo)) { /* 当前登陆人不是流程审批人 */
			throw new MroException("transferline", "noissueby");
		} else {
			//-----------
			IJpoSet ttransferlineset = transferJpo.getJpoSet("transferline");
			if (!ttransferlineset.isEmpty()) {
				double orderqty=ttransferlineset.getJpo(0).getDouble("orderqty");
				String issuestoreroom=ttransferlineset.getJpo(0).getString("issuestoreroom");
				String itemnum=ttransferlineset.getJpo(0).getString("itemnum");
				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet("sys_inventory", MroServer.getMroServer().getSystemUserServer());
				inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+issuestoreroom+"'");
				String mrnum=transferJpo.getString("mrnum");
				double kyqty =0;
				if(mrnum.isEmpty()){
					kyqty = inventoryset.getJpo(0).getDouble("kyqty");// 可用数量	
				}else{
					kyqty = inventoryset.getJpo(0).getDouble("sqzyqty");// 可用数量
				}
				if(kyqty==0){
					throw new MroException("物料编码:'" + itemnum + "',库存可用数量为0");
				}else if(kyqty>0){
					IJpoSet jkdtransferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
					jkdtransferlineset.setUserWhere("itemnum='"+ itemnum+ "' and receivestoreroom='"+ issuestoreroom+ "' and status='未接收' and transfernum in (select transfernum from transfer where status='未处理' and type='JKD')");
					jkdtransferlineset.reset();
					
					IJpoSet zstransferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
					zstransferlineset.setUserWhere("itemnum='"+ itemnum+ "' and ISSUESTOREROOM='"+ issuestoreroom+ "' and status='未接收' and transfernum in (select transfernum from transfer where status='未处理' and type in ('SX','ZXD'))");
					zstransferlineset.reset();
					if(jkdtransferlineset.isEmpty()){
						if(zstransferlineset.isEmpty()){
							double newqty=kyqty-orderqty;
							if(newqty<0){
								throw new MroException("物料编码:'" + itemnum + "',数量大于库存可用数量");
							}
						}else{
							double sumtransferorderqty=zstransferlineset.sum("orderqty");
							double newqty=kyqty-(orderqty+sumtransferorderqty);
							if(newqty<0){
								throw new MroException("物料编码:'" + itemnum + "',累计发货数量大于库存可用数量");
							}
						}
					}else{
						double sumjkdtransferorderqty=jkdtransferlineset.sum("orderqty");
						if(zstransferlineset.isEmpty()){
							double newqty=kyqty-(orderqty+sumjkdtransferorderqty);
							if(newqty<0){
								throw new MroException("物料编码:'" + itemnum + "',累计发货数量大于库存可用数量");
							}
						}else{
							double sumtransferorderqty=zstransferlineset.sum("orderqty");
							double newqty=kyqty-(orderqty+sumtransferorderqty+sumjkdtransferorderqty);
							if(newqty<0){
								throw new MroException("物料编码:'" + itemnum + "',累计发货数量大于库存可用数量");
							}
						}
					}
				}
			}
			//----------
			String status = transferJpo.getString("status");
			String issue = transferJpo.getString("issue");// 是否发出标记
			if (issue.equalsIgnoreCase("否")) {
				String TRANSFERMOVETYPE = transferJpo
						.getString("TRANSFERMOVETYPE");
				String personid = this.page.getAppBean().getUserInfo()
						.getLoginID();
				String sendby = transferJpo.getString("sendby");// 制单人
				if (status.equalsIgnoreCase("未处理")) {
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
						throw new MroException("transferline", "noissue");
					}
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
						IJpoSet transferlineset = transferJpo
								.getJpoSet("transferline");
						if (transferlineset.count() == 0) {
							throw new MroException("transferline",
									"statusissue");
						} else {
							IJpoSet sqntransferlineset = MroServer
									.getMroServer().getJpoSet(
											"transferline",
											MroServer.getMroServer()
													.getSystemUserServer());
							sqntransferlineset
									.setUserWhere("transfernum='"
											+ transfernum
											+ "' and itemnum in (select itemnum from sys_item where ISTURNOVERERP=1) and sqn is null");
							sqntransferlineset.reset();

							if (!sqntransferlineset.isEmpty()) {
								this.getAppBean().SAVE();
								throw new MroException("transfer", "sqn");
							} else {
								// 调用变更序列号状态方法
								UPDATESQNSTATUS(transferlineset);
								// 调用接收在途增加数据方法
								CommonAddNewInventory
										.addinventory(transferlineset);
								transferJpo.setValue("status", "在途");
								transferJpo.setValue("issue", "是");
								java.util.Date newdate = MroServer
										.getMroServer().getDate();
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
										"yyyy/MM/dd HH:mm:ss");
								String format = simpleDateFormat
										.format(newdate);

								transferJpo
										.setValue(
												"SENDTIME",
												format,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								// 调用变更缴库单提示消息关闭方法
								CHANGEMESSAGESTATUS();
								
								this.getAppBean().SAVE();
								showMsgbox("提示", "装箱单已成功发运!");
								return;
							}
						}
					}
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)) {
						throw new MroException("transferline", "noissue");
					}
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
						throw new MroException("transferline", "noissue");
					}
				}
				if (status.equalsIgnoreCase("申请人修改")) {
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
						throw new MroException("transferline", "noissue");
					}
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
						IJpoSet transferlineset = transferJpo
								.getJpoSet("transferline");
						if (transferlineset.count() == 0) {
							throw new MroException("transferline",
									"statusissue");
						} else {
							IJpoSet sqntransferlineset = MroServer
									.getMroServer().getJpoSet(
											"transferline",
											MroServer.getMroServer()
													.getSystemUserServer());
							sqntransferlineset
									.setUserWhere("transfernum='"
											+ transfernum
											+ "' and itemnum in (select itemnum from sys_item where ISTURNOVERERP=1) and sqn is null");
							sqntransferlineset.reset();
							if (!sqntransferlineset.isEmpty()) {
								this.getAppBean().SAVE();
								throw new MroException("transfer", "sqn");
							} else {
								// 调用变更序列号状态方法
								UPDATESQNSTATUS(transferlineset);
								transferJpo.setValue("status", "在途");
								transferJpo.setValue("issue", "是");
								// 调用接收在途增加数据方法
								CommonAddNewInventory
										.addinventory(transferlineset);
								java.util.Date newdate = MroServer
										.getMroServer().getDate();
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
										"yyyy/MM/dd HH:mm:ss");
								String format = simpleDateFormat
										.format(newdate);

								transferJpo
										.setValue(
												"SENDTIME",
												format,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								// 调用变更缴库单提示消息关闭方法
								CHANGEMESSAGESTATUS();
								this.getAppBean().SAVE();
								showMsgbox("提示", "装箱单已成功发运!");
								return;
							}
						}
					}
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)) {
						throw new MroException("transferline", "noissue");
					}
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
						throw new MroException("transferline", "noissue");
					}
				}
				if (status.equalsIgnoreCase("发运人审核")) {
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
						if (personid.equalsIgnoreCase(sendby)) {
							IJpoSet transferlineset = transferJpo
									.getJpoSet("transferline");
							if (transferlineset.count() == 0) {
								throw new MroException("transferline",
										"statusissue");
							} else {
								IJpoSet sqntransferlineset = MroServer
										.getMroServer().getJpoSet(
												"transferline",
												MroServer.getMroServer()
														.getSystemUserServer());
								sqntransferlineset
										.setUserWhere("transfernum='"
												+ transfernum
												+ "' and itemnum in (select itemnum from sys_item where ISTURNOVERERP=1) and sqn is null");
								sqntransferlineset.reset();
								if (!sqntransferlineset.isEmpty()) {
									this.getAppBean().SAVE();
									throw new MroException("transfer", "sqn");
								} else {
									// 调用变更序列号状态方法
									UPDATESQNSTATUS(transferlineset);
									// 调用接收在途增加数据方法
									CommonAddNewInventory
											.addinventory(transferlineset);
									transferJpo.setValue("status", "在途");
									transferJpo.setValue("issue", "是");
									java.util.Date newdate = MroServer
											.getMroServer().getDate();
									SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
											"yyyy/MM/dd HH:mm:ss");
									String format = simpleDateFormat
											.format(newdate);

									transferJpo
											.setValue(
													"SENDTIME",
													format,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									// 调用变更缴库单提示消息关闭方法
									CHANGEMESSAGESTATUS();
									this.getAppBean().SAVE();
									showMsgbox("提示", "装箱单已成功发运!");
									return;
								}

							}
						} else {
							throw new MroException("transferline", "sendby");
						}
					}
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
						throw new MroException("transferline", "sendby");
					}
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)) {
						if (personid.equalsIgnoreCase(sendby)) {
							IJpoSet transferlineset = transferJpo
									.getJpoSet("transferline");
							if (transferlineset.count() == 0) {
								throw new MroException("transferline",
										"statusissue");
							} else {
								IJpoSet sqntransferlineset = MroServer
										.getMroServer().getJpoSet(
												"transferline",
												MroServer.getMroServer()
														.getSystemUserServer());
								sqntransferlineset
										.setUserWhere("transfernum='"
												+ transfernum
												+ "' and itemnum in (select itemnum from sys_item where ISTURNOVERERP=1) and sqn is null");
								sqntransferlineset.reset();
								if (!sqntransferlineset.isEmpty()) {
									this.getAppBean().SAVE();
									throw new MroException("transfer", "sqn");
								} else {
									// 调用变更序列号状态方法
									UPDATESQNSTATUS(transferlineset);
									// 调用接收在途增加数据方法
									CommonAddNewInventory
											.addinventory(transferlineset);
									transferJpo.setValue("status", "在途");
									transferJpo.setValue("issue", "是");
									java.util.Date newdate = MroServer
											.getMroServer().getDate();
									SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
											"yyyy/MM/dd HH:mm:ss");
									String format = simpleDateFormat
											.format(newdate);

									transferJpo
											.setValue(
													"SENDTIME",
													format,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									// 调用变更缴库单提示消息关闭方法
									CHANGEMESSAGESTATUS();
									this.getAppBean().SAVE();
									showMsgbox("提示", "装箱单已成功发运!");
									return;
								}

							}
						} else {
							throw new MroException("transferline", "sendby");
						}
					}
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
						if (personid.equalsIgnoreCase(sendby)) {
							IJpoSet transferlineset = transferJpo
									.getJpoSet("transferline");
							if (transferlineset.count() == 0) {
								throw new MroException("transferline",
										"statusissue");
							} else {
								IJpoSet sqntransferlineset = MroServer
										.getMroServer().getJpoSet(
												"transferline",
												MroServer.getMroServer()
														.getSystemUserServer());
								sqntransferlineset
										.setUserWhere("transfernum='"
												+ transfernum
												+ "' and itemnum in (select itemnum from sys_item where ISTURNOVERERP=1) and sqn is null");
								sqntransferlineset.reset();
								if (!sqntransferlineset.isEmpty()) {
									this.getAppBean().SAVE();
									throw new MroException("transfer", "sqn");
								} else {
									// 调用变更序列号状态方法
									UPDATESQNSTATUS(transferlineset);
									// 调用接收在途增加数据方法
									CommonAddNewInventory
											.addinventory(transferlineset);
									transferJpo.setValue("status", "在途");
									transferJpo.setValue("issue", "是");
									java.util.Date newdate = MroServer
											.getMroServer().getDate();
									SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
											"yyyy/MM/dd HH:mm:ss");
									String format = simpleDateFormat
											.format(newdate);

									transferJpo
											.setValue(
													"SENDTIME",
													format,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									// 调用将装箱单号返回到故障工单QMS信息方法
									ADDZXDTRANSFERTOQMSREPAIRINFO();
									// 调用变更缴库单提示消息关闭方法
									CHANGEMESSAGESTATUS();
//									getZxTaskNum(transfernum, transfermovetype,sxtype);
									this.getAppBean().SAVE();
									showMsgbox("提示", "装箱单已成功发运!");
									return;
								}

							}
						} else {
							throw new MroException("transferline", "sendby");
						}
					}
				}
				if (status.equalsIgnoreCase("在途")) {
					throw new MroException("transferline", "noissue");
				}
				if (status.equalsIgnoreCase("部分接收")) {
					throw new MroException("transferline", "noissue");
				}
				if (status.equalsIgnoreCase("已接收")) {
					throw new MroException("transferline", "noissue");
				}
				if (status.equalsIgnoreCase("结束")) {
					throw new MroException("transferline", "noissue");
				}
				if (status.equalsIgnoreCase("驳回")) {
					throw new MroException("transferline", "noissue");
				}

			} else {
				throw new MroException("transfer", "issue");
			}
		}

	}

	// 获取装箱单行故障工单号并去重
//		private void getZxTaskNum(String transfernum, String transfermovetype,String sxtype)
//				throws MroException {
//			// TODO Auto-generated method stub
//			Set<String> tasknumset = new HashSet<String>();
//			Vector<String> personVector = new Vector<String>();		
//		if (!transfermovetype.equals("现场到中心")) {
//				return;
//			} else if(transfermovetype.equals("现场到中心")&&!sxtype.equals("GZ")){
//				return;
//			}else{
//				IJpoSet jpoSet = MroServer.getMroServer().getSysJpoSet(
//						"TRANSFERLINE", "TRANSFERNUM='" + transfernum + "'");
//				for (int i = 0; i < jpoSet.count(); i++) {
//					IJpo transferline = jpoSet.getJpo(i);
//					String tasknum = transferline.getString("TASKNUM");
//					personVector.add(tasknum);
//				}
//				for (int t = 0; t < personVector.size(); t++) {
//					tasknumset.add(personVector.get(t));
//				}
//				String[] object = tasknumset.toArray(new String[tasknumset.size()]);
//				for(int j=0;j<object.length;j++){
//					String gztasknum = object[j];
//					Email.Email(gztasknum);
//				}
//			}
//		}

	/**
	 * 
	 * <变更序列号状态方法>
	 * 
	 * @param transferlineset
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void UPDATESQNSTATUS(IJpoSet transferlineset) throws MroException {

		for (int i = 0; i < transferlineset.count(); i++) {
			IJpo transferline = transferlineset.getJpo(i);
			String itemnum = transferline.getString("itemnum");
			String sqn = transferline.getString("sqn");
			String assetnum = transferline.getString("assetnum");
			String ISSUESTOREROOM = transferline.getString("ISSUESTOREROOM");
			if (!sqn.isEmpty()) {
				if (!assetnum.isEmpty()) {
					IJpoSet assetset = MroServer.getMroServer().getJpoSet(
							"asset",
							MroServer.getMroServer().getSystemUserServer());
					assetset.setUserWhere("itemnum='" + itemnum + "' and sqn='"
							+ sqn + "' and assetnum='" + assetnum
							+ "' and location='" + ISSUESTOREROOM + "'");
					assetset.reset();
					if (!assetset.isEmpty()) {
						assetset.getJpo(0).setValue("status", "在途",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.save();
					}
				} else {
					IJpoSet assetset = MroServer.getMroServer().getJpoSet(
							"asset",
							MroServer.getMroServer().getSystemUserServer());
					assetset.setUserWhere("itemnum='" + itemnum + "' and sqn='"
							+ sqn + "' and location='" + ISSUESTOREROOM + "'");
					assetset.reset();
					if (!assetset.isEmpty()) {
						assetset.getJpo(0).setValue("status", "在途",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						assetset.save();
					}
				}
			}
		}
	}

	/**
	 * 
	 * <调用ERP接口方法>
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public String MroToErp() throws MroException {// 传递接口
		// String retu="S";
		// return retu;
		String num = "";
		IJpo transferJpo = getJpo();
		String ERPLOC = transferJpo.getJpoSet("ISSUESTOREROOM").getJpo()
				.getString("erploc");
		String mroperson = this.getJpo().getUserServer().getUserInfo()
				.getLoginID();
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

			movetype.setChar3("311");// 参数--移动类型311

			person.setChar10(mroperson);// 参数--点收入
			werks.setChar4(ERPLOC);// 参数--工厂
			budat.setDate(CREATEDATES);// 参数--过账日期
			IJpoSet TRANSFERLINEset = this.getJpo().getJpoSet("TRANSFERLINE");
			TableOfBapi2017GmItemCreate table = new TableOfBapi2017GmItemCreate();
			JSONArray jArray = new JSONArray();
			for (int i = 0; i < TRANSFERLINEset.count(); i++) {
				IJpo TRANSFERLINE = TRANSFERLINEset.getJpo(i);
				String itemnum = TRANSFERLINE.getString("itemnum");// 物料编码
				String ORDERQTY = TRANSFERLINE.getString("ORDERQTY");// --数量
				String unit = TRANSFERLINE.getJpoSet("item").getJpo()
						.getString("ORDERUNIT");// 计量单位
				String lotnum = TRANSFERLINE.getString("lotnum");
				String outlocation = TRANSFERLINE.getParent()
						.getJpoSet("ISSUESTOREROOM").getJpo()
						.getString("STOREROOMPARENT");
				String inlocation = TRANSFERLINE.getParent()
						.getJpoSet("RECEIVESTOREROOM").getJpo()
						.getString("STOREROOMPARENT");

				JSONObject rdata = new JSONObject();
				Bapi2017GmItemCreate tableobject = new Bapi2017GmItemCreate();

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

				Material.setChar18(itemnum);// 输入数据--物料编码
				Plant.setChar4("1010");
				StgeLoc.setChar4(outlocation);// 输入数据--发出库存地点
				MoveStloc.setChar4(inlocation);// 输入数据--接收库存地点
				EntryQnt.setQuantum133(new BigDecimal(ORDERQTY));// 输入数据--数量
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
				rdata.put("EntryQnt", ORDERQTY);
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

			}
			param.setMoveType(movetype);
			param.setPerson(person);
			param.setWerks(werks);
			param.setWmBudat(budat);
			param.setTGvitem(table);

			num = IFUtil.addIfHistory(IFUtil.MRO_ERP_ZXD, jArray.toString(),
					IFUtil.TYPE_OUTPUT);// 增加输出记录
			ZtfunWmsBasisFunctionResponse res = service
					.ztfunWmsBasisFunction(param);
			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"装箱单号：" + this.getJpo().getString("transfernum") + ";传递ERP"
							+ TRANSFERLINEset.count() + "条;");
			String message = res.getMessage().toString();
			String retu = res.getReturn().toString();
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
					"装箱单号：" + this.getJpo().getString("transfernum")
							+ ";接收ERP回传;");

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
	 * <回写装箱单号到故障工单QMS信息方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void ADDZXDTRANSFERTOQMSREPAIRINFO() throws MroException {
		IJpo transferJpo = getJpo();
		String TRANSFERMOVETYPE = transferJpo.getString("TRANSFERMOVETYPE");
		String transfernum = transferJpo.getString("transfernum");
		if (TRANSFERMOVETYPE.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
			String SXTYPE = transferJpo.getString("SXTYPE");
			if (SXTYPE.equalsIgnoreCase("GZ")) {
				IJpoSet transferlineset = MroServer.getMroServer().getJpoSet(
						"transferline",
						MroServer.getMroServer().getSystemUserServer());
				transferlineset.setUserWhere("transfernum='" + transfernum
						+ "'");
				transferlineset.reset();
				if (!transferlineset.isEmpty()) {
					for (int i = 0; i < transferlineset.count(); i++) {
						IJpo transferline = transferlineset.getJpo(i);
						String transferlinenum = transferline
								.getString("transferlinenum");
						String tasknum = transferline.getString("tasknum");
						String SCALELINENUM = transferline
								.getString("SCALELINENUM");
						String QMSNUM = transferline.getString("QMSNUM");
						IJpoSet QMSREPAIRINFOset = MroServer.getMroServer()
								.getJpoSet(
										"QMSREPAIRINFO",
										MroServer.getMroServer()
												.getSystemUserServer());
						QMSREPAIRINFOset.setUserWhere("wordernum='" + tasknum
								+ "' and rowno='" + SCALELINENUM
								+ "' and qmsrepairnum='" + QMSNUM + "'");
						QMSREPAIRINFOset.reset();
						if (!QMSREPAIRINFOset.isEmpty()) {
							IJpo QMSREPAIRINFO = QMSREPAIRINFOset.getJpo(0);
							QMSREPAIRINFO.setValue("zxdtransfernum",
									transfernum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							QMSREPAIRINFO.setValue("zxdtransferlinenum",
									transferlinenum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
						QMSREPAIRINFOset.save();
					}
				}

			}
		}

	}

	/**
	 * 
	 * <变更缴库单通知状态方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void CHANGEMESSAGESTATUS() throws MroException {
		IJpo transferJpo = getJpo();
		IJpoSet transferlineset = transferJpo.getJpoSet("transferline");
		transferlineset.setUserWhere("jkdlineid is not null");
		if (!transferlineset.isEmpty()) {
			for (int i = 0; i < transferlineset.count(); i++) {
				IJpo transferline = transferlineset.getJpo(i);
				String transferlineid = transferline.getString("JKDLINEID");
				IJpoSet messageset = MroServer.getMroServer().getJpoSet(
						"MSGMANAGE",
						MroServer.getMroServer().getSystemUserServer());
				messageset.setUserWhere("app='JKTRANSFER' and msgnum='"
						+ transferlineid + "'");
				messageset.reset();
				if (!messageset.isEmpty()) {
					messageset.getJpo(0).setValue("status", "已阅",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					messageset.save();
				}
			}
		}
	}
}
