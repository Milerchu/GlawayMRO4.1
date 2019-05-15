package com.glaway.sddq.back.Interface.webservice;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl.Authenticator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.back.Interface.webservice.erp.jxjhd.ComZzsErpZTFUN_WMS_LIKPStub;
import com.glaway.sddq.back.Interface.webservice.erp.jxjhd.ComZzsErpZTFUN_WMS_LIKPStub.Char10;
import com.glaway.sddq.back.Interface.webservice.erp.jxjhd.ComZzsErpZTFUN_WMS_LIKPStub.Char4;
import com.glaway.sddq.back.Interface.webservice.erp.jxjhd.ComZzsErpZTFUN_WMS_LIKPStub.Date;
import com.glaway.sddq.back.Interface.webservice.erp.jxjhd.ComZzsErpZTFUN_WMS_LIKPStub.TableOfZtfunWmsLikp;
import com.glaway.sddq.back.Interface.webservice.erp.jxjhd.ComZzsErpZTFUN_WMS_LIKPStub.ZtfunWmsLikp;
import com.glaway.sddq.back.Interface.webservice.erp.jxjhd.ComZzsErpZTFUN_WMS_LIKPStub.ZtfunWmsLikpE;
import com.glaway.sddq.back.Interface.webservice.erp.jxjhd.ComZzsErpZTFUN_WMS_LIKPStub.ZtfunWmsLikpResponse;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.TableOfZsdZjhjpItems;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.ZsdZjhjpHeader;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.ZtfunSdZjhjp;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.ZtfunSdZjhjpResponse;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 接口工具类
 * 
 * @author public2175
 * @version [版本号, 2018-9-18]
 * @since [产品/模块版本]
 */
public class InterfaceUtil {

	/**
	 * 
	 * 根据QMS信息更新配置
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void GxPzForQms(String repairnum) throws MroException {

		if (StringUtil.isStrNotEmpty(repairnum)) {

			IJpoSet repairconfigSet = MroServer.getMroServer().getJpoSet(
					"QMSREPAIRCONFIG",
					MroServer.getMroServer().getSystemUserServer());
			repairconfigSet.setQueryWhere("qmsrepairnum ='" + repairnum + "'");
			repairconfigSet.reset();
			for (int index = 0; index < repairconfigSet.count(); index++) {
				IJpo jpo = repairconfigSet.getJpo(index);
				String itemnum = jpo.getString("faultitemnum");// 故障子项图号/物料编码
				String sqn = jpo.getString("faultitemsn");// 故障子项序列号
				String newsqn = jpo.getString("NEWITEMSN");// 更换件序列号
				if (!SddqConstant.GZPZ_STATUS_YCL.equals(jpo
						.getString("STATUS"))) {
					if (StringUtil.isStrNotEmpty(sqn)) {
						IJpoSet assetJpoSet = MroServer.getMroServer()
								.getSysJpoSet("ASSET");
						if (StringUtil.isStrNotEmpty(itemnum)) {
							assetJpoSet.setQueryWhere("sqn='" + sqn
									+ "' and itemnum ='" + itemnum + "'");
							assetJpoSet.reset();
							if (assetJpoSet != null && assetJpoSet.count() > 0) {
								IJpo asset = assetJpoSet.getJpo(0);
								if (StringUtil.isStrNotEmpty(newsqn)) {
									if (!ItemUtil.getAssetInfo(newsqn, itemnum)) {
										// 校验新的产品序列号是否存在
										asset.setValue(
												"sqn",
												newsqn,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue(
												"status",
												SddqConstant.GZPZ_STATUS_YCL,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue("ERRMSG", "");
									} else {
										jpo.setValue(
												"status",
												SddqConstant.GZPZ_STATUS_CLSB,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue("ERRMSG", "系统中已存在产品序列号："
												+ newsqn);
									}
								}
							}else{
								jpo.setValue(
										"status",
										SddqConstant.GZPZ_STATUS_CLSB,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("ERRMSG", "系统中不存在产品序列号为："
										+ sqn + " 物料编码为："+itemnum+"的产品");
							}
						} else {
							assetJpoSet.setQueryWhere("sqn='" + sqn + "'");
							assetJpoSet.reset();
							if (assetJpoSet != null && assetJpoSet.count() > 0) {
								IJpo asset = assetJpoSet.getJpo(0);
								if (StringUtil.isStrNotEmpty(newsqn)) {
									if (!ItemUtil.getAssetInfo(newsqn, "")) {
										// 校验新的产品序列号是否存在
										asset.setValue(
												"sqn",
												newsqn,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue(
												"status",
												SddqConstant.GZPZ_STATUS_YCL,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue("ERRMSG", "");
									} else {
										// 处理信息
										jpo.setValue(
												"status",
												SddqConstant.GZPZ_STATUS_CLSB,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue("ERRMSG", "系统中已存在产品序列号："
												+ newsqn);
									}
								}
							}
						}
						assetJpoSet.save();
					}
				}
			}
			repairconfigSet.save();
		}
	}
	
	
	/**
	 * 
	 * 获取ERP检修交货单
	 * 
	 * @param jxjhdnum
	 * @throws MroException
	 *             [参数说明]
	 * @throws RemoteException 
	 * @throws Exception
	 * 
	 */
	public static JSONArray getErpJxJhd(String jxjhdnum,String jxzj) throws MroException, RemoteException {

		ComZzsErpZTFUN_WMS_LIKPStub service = new ComZzsErpZTFUN_WMS_LIKPStub();

		Authenticator auth = new Authenticator();
		String user = IFUtil.getIfServiceInfo("erp.user");
		String pwd = IFUtil.getIfServiceInfo("erp.pwd");
		auth.setUsername(user);
		auth.setPassword(pwd);
		service._getServiceClient().getOptions()
				.setProperty(HTTPConstants.AUTHENTICATE, auth);

		ZtfunWmsLikpE ztfunWmsLikp0 = new ZtfunWmsLikpE();

		Date dathigh = new Date();
		dathigh.setDate("0000-00-00");
		ztfunWmsLikp0.setLDathigh(dathigh);

		Date ldatlow = new Date();
		ldatlow.setDate("0000-00-00");
		ztfunWmsLikp0.setLDatlow(ldatlow);

		Char10 lvbeln = new Char10();
		lvbeln.setChar10(jxjhdnum);
		ztfunWmsLikp0.setLVbeln(lvbeln);

		Char4 lvkorg = new Char4();
		lvkorg.setChar4(jxzj);
		ztfunWmsLikp0.setLVkorg(lvkorg);

		TableOfZtfunWmsLikp tablein = new TableOfZtfunWmsLikp();
		ztfunWmsLikp0.setTOutput(tablein);

		ZtfunWmsLikpResponse ret = service.ztfunWmsLikp(ztfunWmsLikp0);
		if (ret != null) {
			TableOfZtfunWmsLikp table = ret.getTOutput();
			if (table != null) {

				ZtfunWmsLikp[] zppz = table.getItem();
				if (zppz != null) {
					JSONArray jArray = new JSONArray();
					for (ZtfunWmsLikp row : table.getItem()) {
						JSONObject jobject = new JSONObject();
						jobject.put("LFDAT", row.getLfdat());
						jobject.put("VKORG", row.getVkorg().toString());// 销售组织

						jobject.put("JHNUM", row.getVbeln().toString());// 交货单号

						jobject.put("POSNR", row.getPosnr().toString());// 交货单行号

						jobject.put("PSTYV", row.getPstyv().toString());// 交货项目类别

						jobject.put("ERPITEMNUM", row.getMatnr().toString());// 物料编码
						jobject.put("ITEMNUM", ItemUtil.getItemnumFor400(row.getMatnr().toString()));// 物料编码
						

						jobject.put("MAKTX", row.getMaktx().toString());// 物料描述

						jobject.put("LFIMG", row.getLfimg());// 数量

						jobject.put("VRKME", row.getVrkme().toString());// 单位

						jobject.put("WERKS", row.getWerks().toString());// 工厂

						jobject.put("LGORT", row.getLgort().toString());// 库存地点

						jobject.put("BWART", row.getBwart().toString());// 移动类型

						jobject.put("VGBEL", row.getVgbel().toString());// 销售订单号

						jobject.put("VGPOS", row.getVgpos().toString());// 销售订单行

						jobject.put("SHDW", row.getName1().toString());// 收货单位

						jobject.put("REMARK", row.getRemarkHead().toString());// 备注

						jobject.put("ADDRESS", row.getAddress().toString());// 地址

						jArray.add(jobject);
					}
					return jArray;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * 调用ERP拣配过账接口
	 * @param jxjhdnum
	 * @return
	 * @throws MroException [参数说明]
	 * @throws RemoteException 
	 *
	 */
	public static JSONObject toErpJxJH(String jxjhdnum) throws MroException, RemoteException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = sdf.format(MroServer.getMroServer().getDate());
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
		VBELN.setChar10(jxjhdnum);
		com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.Date Bldat = new com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.Date();
		Bldat.setDate(currDate);
		zsdzjhjpheader.setVbeln(VBELN);
		zsdzjhjpheader.setBldat(Bldat);

		// 按照文档，此表无需填写数据
		TableOfZsdZjhjpItems tableofzsdzjhjpitems = new TableOfZsdZjhjpItems();

		ZtfunSdZjhjp ztfunsdzjhjp = new ZtfunSdZjhjp();
		ztfunsdzjhjp.setHeader(zsdzjhjpheader);
		ztfunsdzjhjp.setTItems(tableofzsdzjhjpitems);

		lognum = IFUtil.addIfHistory("MRO_ERP_ZJHJP",
				JSON.toJSONString(ztfunsdzjhjp), IFUtil.TYPE_OUTPUT);// 接口管理中间表
		ZtfunSdZjhjpResponse Response = service.ztfunSdZjhjp(ztfunsdzjhjp);
		JSONObject msg = new JSONObject();
		if (Response.getRcode().toString().equals("E")) {
			IFUtil.updateIfHistory(lognum, IFUtil.STATUS_FAILURE,
					IFUtil.FLAG_NO, Response.getRmesg().toString());
		} else {
			IFUtil.updateIfHistory(lognum, IFUtil.STATUS_SUCCESS,
					IFUtil.FLAG_YES, Response.getRmesg().toString());
		}
		msg.put("msg", Response.getRmesg().toString());
		msg.put("flag", Response.getRcode().toString());
		return msg;
	}
}
