package com.glaway.sddq.material.convertloc.bean;

import java.io.IOException;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient3.HttpTransportPropertiesImpl.Authenticator;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
/*正式接口调用*/
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqjhd.ComZzsErpZTFUN_MRO_LIKP_WEBStub;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqjhd.ComZzsErpZTFUN_MRO_LIKP_WEBStub.Char10;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqjhd.ComZzsErpZTFUN_MRO_LIKP_WEBStub.TableOfZtfunMroLikp;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqjhd.ComZzsErpZTFUN_MRO_LIKP_WEBStub.ZtfunMroLikp;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqjhd.ComZzsErpZTFUN_MRO_LIKP_WEBStub.ZtfunMroLikpE;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqjhd.ComZzsErpZTFUN_MRO_LIKP_WEBStub.Date;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqjhd.ComZzsErpZTFUN_MRO_LIKP_WEBStub.ZtfunMroLikpResponse;
/*正式接口调用*/
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <调拨转库单APPBEAN类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class ConvertlocAppBean extends AppBean {
	/**
	 * 
	 * <获取交货单信息>
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void RECEIVEDATA() throws MroException, IOException {
		IJpo transferJpo = getJpo();
		String CONVERTLOCNUM = transferJpo.getString("CONVERTLOCNUM");
		String status = transferJpo.getString("status");
		boolean isiface = transferJpo.getBoolean("isiface");
		if (status.equalsIgnoreCase("未接收")) {
			if (isiface) {
				throw new MroException("transferline", "hasiface");
			} else {
				if (!CONVERTLOCNUM.equalsIgnoreCase("")) {
					ERPTOMRO();// 调用ERP接口方法
					this.getAppBean().SAVE();
				} else {
					throw new MroException("transferline", "noconvertlocnum");
				}
			}
		} else {
			throw new MroException("transferline", "canthq");
		}

	}

	/**
	 * 
	 * <根据交货单号获取ERP交货单信息>
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void ERPTOMRO() throws MroException {
		IJpo transferJpo = getJpo();
		String CONVERTLOCNUM = transferJpo.getString("CONVERTLOCNUM");
		String num = "";
		try {
			String user = IFUtil.getIfServiceInfo("erp.user");
			String pwd = IFUtil.getIfServiceInfo("erp.pwd");
			ComZzsErpZTFUN_MRO_LIKP_WEBStub service = new ComZzsErpZTFUN_MRO_LIKP_WEBStub();
			// 认证代码 start
			Authenticator auth = new Authenticator();
			auth.setUsername(user);
			auth.setPassword(pwd);
			service._getServiceClient().getOptions()
					.setProperty(HTTPConstants.AUTHENTICATE, auth);
			// 认证代码 end
			ZtfunMroLikpE param = new ZtfunMroLikpE();
			Date dathigh = new Date();
			dathigh.setDate("0000-00-00");
			param.setLDathigh(dathigh);

			Date ldatlow = new Date();
			ldatlow.setDate("0000-00-00");
			param.setLDatlow(ldatlow);

			Char10 lvbeln = new Char10();
			lvbeln.setChar10(CONVERTLOCNUM);
			param.setLVbeln(lvbeln);

			// Char4 Vkorg = new Char4();
			// Vkorg.setChar4("1000");
			// param.setLVkorg(Vkorg);

			TableOfZtfunMroLikp tablein = new TableOfZtfunMroLikp();
			param.setTOutput(tablein);
			num = IFUtil.addIfHistory(IFUtil.ERP_MRO_JHDIF, CONVERTLOCNUM,
					IFUtil.TYPE_OUTPUT);// 增加输出记录
			ZtfunMroLikpResponse ret = service.ztfunMroLikp(param);
			TableOfZtfunMroLikp table = ret.getTOutput();
			ZtfunMroLikp[] zppz = table.getItem();
			if (zppz != null && zppz.length > 0) {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "获取交货单号:" + CONVERTLOCNUM
								+ ";ERP返回数据结果共" + zppz.length + "条;");
				JSONObject jobject = new JSONObject();
				JSONArray jArray = new JSONArray();
				for (int index = 0; index < zppz.length; index++) {
					JSONObject rdata = new JSONObject();
					String LFDAT = zppz[index].getLfdat().toString();// 交货日期
					String VKORG = zppz[index].getVkorg().toString();// 销售组织
					String VBELN = zppz[index].getVbeln().toString();// 交货单号
					String POSNR = zppz[index].getPosnr().toString();// 交货单行号
					String PSTYV = zppz[index].getPstyv().toString();// 交货项目类别
					String MATNR = zppz[index].getMatnr().toString();// 物料号
					String MAKTX = zppz[index].getMaktx().toString();// 物料描述
					String LFIMG = zppz[index].getLfimg().toString();// 数量
					String VRKME = zppz[index].getVrkme().toString();// 单位
					String WERKS = zppz[index].getWerks().toString();// 工厂
					String LGORT = zppz[index].getLgort().toString();// 库存地点
					String BWART = zppz[index].getBwart().toString();// 移动类型
					String VGBEL = zppz[index].getVgbel().toString();// 工厂间转储单号
					String VGPOS = zppz[index].getVgpos().toString();// 转储单行
					String NAME1 = zppz[index].getName1().toString();// 收货单位
					String IHREZ = zppz[index].getIhrez().toString();// 工作令号
					String HOUSENUM1 = zppz[index].getHouseNum1().toString();// 门牌号（MRO库房代码）
					rdata.put("LFDAT", LFDAT);
					rdata.put("VKORG", VKORG);
					rdata.put("VBELN", VBELN);
					rdata.put("POSNR", POSNR);
					rdata.put("PSTYV", PSTYV);
					rdata.put("MATNR", MATNR);
					rdata.put("MAKTX", MAKTX);
					rdata.put("LFIMG", LFIMG);
					rdata.put("VRKME", VRKME);
					rdata.put("WERKS", WERKS);
					rdata.put("LGORT", LGORT);
					rdata.put("BWART", BWART);
					rdata.put("VGBEL", VGBEL);
					rdata.put("VGPOS", VGPOS);
					rdata.put("NAME1", NAME1);
					rdata.put("IHREZ", IHREZ);
					rdata.put("HOUSENUM1", HOUSENUM1);

					jArray.put(rdata);
				}
				jobject.put("data", jArray);
				MROADDJPO(jobject);// 调用写入交货单信息方法
				transferJpo.setValue("isiface", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			} else {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "获取交货单号:" + CONVERTLOCNUM
								+ ";返回数据结果为空");
			}
		} catch (Exception e) {
			IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
					e.getMessage());
			e.printStackTrace();
			throw new MroException(e.getMessage());
		}
	}

	/**
	 * 
	 * <往系统写入获取的ERP交货单信息>
	 * 
	 * @param data
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void MROADDJPO(JSONObject data) throws MroException {
		IJpo transferJpo = getJpo();
		String CONVERTLOCNUM = transferJpo.getString("CONVERTLOCNUM");
		String inputnum = "";
		try {
			inputnum = IFUtil.addIfHistory(IFUtil.ERP_MRO_JHDIF,
					data.toString(), IFUtil.TYPE_INPUT);// 增加输入记录
			JSONArray treedata = data.getJSONArray("data");
			if (treedata.length() > 0) {
				IJpoSet jpoSet = this.getJpo().getJpoSet("$systemChildren",
						"CONVERTLOCLINE", "1=2");

				for (int i = 0; i < treedata.length(); i++) {

					JSONObject rdata = treedata.getJSONObject(i);
					String MATNR = rdata.getString("MATNR");// 物料号
					String LFDAT = rdata.getString("LFDAT");// 交货日期
					String VKORG = rdata.getString("VKORG");// 销售组织
					String VBELN = rdata.getString("VBELN");// 交货单号
					String POSNR = rdata.getString("POSNR");// 交货单行号
					String PSTYV = rdata.getString("PSTYV");// 交货项目类别
					String MAKTX = rdata.getString("MAKTX");// 物料描述
					double LFIMG = rdata.getDouble("LFIMG");// 数量
					String VRKME = rdata.getString("VRKME");// 单位
					String WERKS = rdata.getString("WERKS");// 工厂
					String LGORT = rdata.getString("LGORT");// 库存地点
					String BWART = rdata.getString("BWART");// 移动类型
					String VGBEL = rdata.getString("VGBEL");// 工厂间转储单号
					String VGPOS = rdata.getString("VGPOS");// 转储单行
					String NAME1 = rdata.getString("NAME1");// 收货单位
					String IHREZ = rdata.getString("IHREZ");// 工作令号
					String HOUSENUM1 = rdata.getString("HOUSENUM1");// 门牌号（MRO库房代码）
					String itemtype = ItemUtil.getItemInfo(MATNR);
					if (ItemUtil.SQN_ITEM.equals(itemtype)) {
						if (LFIMG > 0) {
							int forcount = (int) LFIMG;
							for (int j = 0; j < forcount; j++) {
								IJpo jpo = jpoSet.addJpo();
								jpo.setValue("CONVERTLOCNUM", CONVERTLOCNUM,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("ITEMNUM", MATNR,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("QTY", 1,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("ORDERUNIT", VRKME,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("LFDAT", LFDAT,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("VKORG", VKORG,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("description", VBELN,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("POSNR", POSNR,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("PSTYV", PSTYV,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("ITEMDESC", MAKTX,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("WERKS", WERKS,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("BWART", BWART,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("VGBEL", VGBEL,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("VGPOS", VGPOS,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("NAME1", NAME1,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("PROJNUM", IHREZ,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								if (WERKS.equalsIgnoreCase("1020")) {
									if (!IHREZ.isEmpty()) {
										IJpoSet proset = MroServer
												.getMroServer()
												.getJpoSet(
														"projectinfo",
														MroServer
																.getMroServer()
																.getSystemUserServer());
										proset.setUserWhere("WORKORDERNUM='"
												+ IHREZ + "'");
										proset.reset();
										if (!proset.isEmpty()) {
											String location = proset.getJpo(0)
													.getString("location");
											if (location.isEmpty()) {
												throw new MroException("工作令号:'"
														+ IHREZ
														+ "',在系统中未关联库房，请维护！");
											} else {
												jpo.setValue(
														"LOCATION",
														location,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
											}

										} else {
											throw new MroException("工作令号:'"
													+ IHREZ + "',在系统中未找到，请维护");
										}

									} else {
										throw new MroException(
												"工厂为1020，请填写工作令号");
									}
								}
								if (WERKS.equalsIgnoreCase("1030")) {
									if (!HOUSENUM1.isEmpty()) {
										jpo.setValue(
												"LOCATION",
												HOUSENUM1,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									} else {
										throw new MroException(
												"工厂为1030，门牌号（MRO库房信息）为空，请在ERP填写！");
									}
								}
							}
						}
					} else if (ItemUtil.LOT_I_ITEM.equals(itemtype)) {
						if (LFIMG > 0) {
							int forcount = (int) LFIMG;
							for (int j = 0; j < forcount; j++) {
								IJpo jpo = jpoSet.addJpo();
								jpo.setValue("CONVERTLOCNUM", CONVERTLOCNUM,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("ITEMNUM", MATNR,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("QTY", 1,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("ORDERUNIT", VRKME,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("LFDAT", LFDAT,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("VKORG", VKORG,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("description", VBELN,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("POSNR", POSNR,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("PSTYV", PSTYV,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("ITEMDESC", MAKTX,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("WERKS", WERKS,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("BWART", BWART,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("VGBEL", VGBEL,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("VGPOS", VGPOS,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("NAME1", NAME1,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("PROJNUM", IHREZ,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								if (WERKS.equalsIgnoreCase("1020")) {
									if (!IHREZ.isEmpty()) {
										IJpoSet proset = MroServer
												.getMroServer()
												.getJpoSet(
														"projectinfo",
														MroServer
																.getMroServer()
																.getSystemUserServer());
										proset.setUserWhere("WORKORDERNUM='"
												+ IHREZ + "'");
										proset.reset();
										if (!proset.isEmpty()) {
											String location = proset.getJpo(0)
													.getString("location");
											if (location.isEmpty()) {
												throw new MroException("工作令号:'"
														+ IHREZ
														+ "',在系统中未关联库房，请维护！");
											} else {
												jpo.setValue(
														"LOCATION",
														location,
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
											}

										} else {
											throw new MroException("工作令号:'"
													+ IHREZ + "',在系统中未找到，请维护");
										}

									} else {
										throw new MroException(
												"工厂为1020，请填写工作令号");
									}
								}
								if (WERKS.equalsIgnoreCase("1030")) {
									if (!HOUSENUM1.isEmpty()) {
										jpo.setValue(
												"LOCATION",
												HOUSENUM1,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									} else {
										throw new MroException(
												"工厂为1030，门牌号（MRO库房信息）为空，请在ERP填写！");
									}
								}
							}
						}
					} else {
						IJpo jpo = jpoSet.addJpo();
						jpo.setValue("CONVERTLOCNUM", CONVERTLOCNUM,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("ITEMNUM", MATNR,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("QTY", LFIMG,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("ORDERUNIT", VRKME,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

						jpo.setValue("LFDAT", LFDAT,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("VKORG", VKORG,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("description", VBELN,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("POSNR", POSNR,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("PSTYV", PSTYV,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("ITEMDESC", MAKTX,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("WERKS", WERKS,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("BWART", BWART,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("VGBEL", VGBEL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("VGPOS", VGPOS,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("NAME1", NAME1,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						// jpo.setValue("LOCATION", LGORT,
						// GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						jpo.setValue("PROJNUM", IHREZ,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						if (WERKS.equalsIgnoreCase("1020")) {
							if (!IHREZ.isEmpty()) {
								IJpoSet proset = MroServer.getMroServer()
										.getJpoSet(
												"projectinfo",
												MroServer.getMroServer()
														.getSystemUserServer());
								proset.setUserWhere("WORKORDERNUM='" + IHREZ
										+ "'");
								proset.reset();
								if (!proset.isEmpty()) {
									String location = proset.getJpo(0)
											.getString("location");
									if (location.isEmpty()) {
										throw new MroException("工作令号:'" + IHREZ
												+ "',在系统中未关联库房，请维护！");
									} else {
										jpo.setValue(
												"LOCATION",
												location,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}

								} else {
									throw new MroException("工作令号:'" + IHREZ
											+ "',在系统中未找到，请维护");
								}

							} else {
								throw new MroException("工厂为1020，请填写工作令号");
							}
						}
						if (WERKS.equalsIgnoreCase("1030")) {
							if (!HOUSENUM1.isEmpty()) {
								jpo.setValue("LOCATION", HOUSENUM1,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							} else {
								throw new MroException(
										"工厂为1030，门牌号（MRO库房信息）为空，请在ERP填写！");
							}
						}
					}

				}
				IFUtil.updateIfHistory(inputnum, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "插入数据 " + treedata.length() + " 条");
			}

		} catch (Exception e) {
			IFUtil.updateIfHistory(inputnum, IFUtil.STATUS_FAILURE,
					IFUtil.FLAG_YES, e.getMessage());
			e.printStackTrace();
			throw new MroException(e.getMessage());
		}
	}
}
