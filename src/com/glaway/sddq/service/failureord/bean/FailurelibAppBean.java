package com.glaway.sddq.service.failureord.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.DateUtils;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 故障管理AppBean
 * 
 * @author hyhe
 * @version [版本号, 2017-12-13]
 * @since [产品/模块版本]
 */
public class FailurelibAppBean extends AppBean {
	@SuppressWarnings("unused")
	private static final String checkUserURL = "http://10.97.168.136:15390/bigdata/pub/login";

	private static final String dataRequestURL = "http://10.97.168.136:15390/bigdata/pub/dataRequest";

	public void BDSYNCINTERFACE() throws MroException, IOException {

		// FailurelibData.BDSYNCINTERFACE(this.getJpo());

		IJpoSet bdsyncifaceset = MroServer.getMroServer().getJpoSet(
				"BDSYNCINTERFACE",
				MroServer.getMroServer().getSystemUserServer());
		IJpo failurelib = getJpo();
		// String cmodel = failurelib.getString("MODELS");
		// String carno = failurelib.getString("CARNUM");
		// bdsyncifaceset.setQueryWhere("train_type = '" + cmodel +
		// "' and train_num = '" + carno + "'");
		bdsyncifaceset.reset();
		if (!bdsyncifaceset.isEmpty()) {
			IJpo bdsynciface = bdsyncifaceset.getJpo(0);
			failurelib.setValue("ENVIRONMENTTMP",
					bdsynciface.getString("EN_TEMP1"));
			failurelib
					.setValue("LONGITUDE", bdsynciface.getString("LONGITUDE"));
			failurelib.setValue("LATITUDE", bdsynciface.getString("LATITUDE"));
			failurelib.setValue("FAILSPEED",
					bdsynciface.getString("REAL_SPEED"));
			failurelib.setValue("THELEVEL",
					bdsynciface.getString("TRAIN_LEVEL"));
			failurelib.setValue("OTPCONDITION",
					bdsynciface.getString("TRAIN_OP_MODE"));
			failurelib
					.setValue("KILOMETER", bdsynciface.getString("KILOMETER"));
			failurelib.setValue("TOTAL_WEIGHT",
					bdsynciface.getString("TOTAL_WEIGHT"));
		}
		this.SAVE();
	}

	/**
	 * 
	 * 获取数据
	 * 
	 * @param userID
	 *            [参数说明]
	 * 
	 */
	public void dataRequest(String userID) throws MroException, IOException {
		System.out.println("准备获取数据:\n");
		try {
			URL targetDataUrl = new URL(dataRequestURL);
			HttpURLConnection datahttpConnection = null;
			try {
				datahttpConnection = (HttpURLConnection) targetDataUrl
						.openConnection();
				datahttpConnection.setDoOutput(true);
				datahttpConnection.setRequestMethod("POST");
				datahttpConnection.setRequestProperty("Content-Language",
						"UTF-8");
				datahttpConnection.setRequestProperty("Content-Type",
						"application/json");
				JSONObject parameter = new JSONObject();
				parameter.put("userID", userID);
				String cmodel = this.getJpo().getString("MODELS");
				if (cmodel.equals("HXD1D机车")) {
					cmodel = "2";
				}
				if (cmodel.equals("HXD1C机车")) {
					cmodel = "1";
				}
				String carno = this.getJpo().getString("CARNUM");
				Date faulttime = this.getJpo().getDate("FAULTTIME");
				String times = DateUtils.format(faulttime, "yyyyMMddHHmmss");
				JSONObject data = new JSONObject();
				data.put("train_type", cmodel);
				data.put("train_num", carno);
				data.put("f_time", times);
				parameter.put("dataParameter", data);
				System.out.println("传递的参数：\n");
				System.out.println(JSONUtils.toJSONString(parameter));
				// String input =
				// "{\"userID\":\""+userID+"\",\"dataParameter\":\"abc.1234\"}";
				OutputStream outputStream = datahttpConnection
						.getOutputStream();
				outputStream
						.write(JSONUtils.toJSONString(parameter).getBytes());
				outputStream.flush();

				if (datahttpConnection.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ datahttpConnection.getResponseCode());
				}

				BufferedReader responseBuffer = new BufferedReader(
						new InputStreamReader(
								(datahttpConnection.getInputStream())));

				String output = null;
				System.out.println("Output from Server:\n");
				while ((output = responseBuffer.readLine()) != null) {
					System.out.println(output);
					if (output != null) {
						JSONObject jsStr = JSONObject.parseObject(output);
						if (jsStr != null) {
							String stateCode = (String) jsStr.get("stateCode");
							String rsMsg = (String) jsStr.get("rsMsg");
							String dataParameter = (String) jsStr.get("rsMsg");
							System.out.println("stateCode:" + stateCode
									+ "\n rsMsg:" + rsMsg + "\n");
							if (stateCode != null && stateCode.equals("0")) {
								JSONObject gzdata = JSONObject
										.parseObject(dataParameter);
								String abc_id = (String) gzdata.get("abc_id");// -A/B/C车标示
								String date_time = (String) gzdata
										.get("date_time");// 日期时间
								String en_temp1 = (String) gzdata
										.get("en_temp1");// 环温1
								String kilometer = (String) gzdata
										.get("kilometer");// 公里标
								String latitude = (String) gzdata
										.get("latitude");// 度的整数部分
								String longitude = (String) gzdata
										.get("longitude");// 度的整数部分
								String odometer = (String) gzdata
										.get("odometer");// 运行里程
								String real_cross = (String) gzdata
										.get("real_cross");// 实际交路号
								String real_speed = (String) gzdata
										.get("real_speed");// 实速
								String tcu_time = (String) gzdata
										.get("tcu_time");// TCU实时时钟
								String time = (String) gzdata.get("time");// 时间
								String total_weight = (String) gzdata
										.get("total_weight");// 总重
								String train_fun = (String) gzdata
										.get("train_fun");// 本/补、客/货
								String train_id = (String) gzdata
										.get("train_id");// 车次标识符
								String train_level = (String) gzdata
										.get("train_level");// 机车当前级位
								String train_num = (String) gzdata
										.get("train_num");// 车号
								String train_op_mode = (String) gzdata
										.get("train_op_mode");// 机车工况
								String train_real_speed = (String) gzdata
										.get("train_real_speed");// 机车实际速度
								String train_set_speed = (String) gzdata
										.get("train_set_speed");// 机车设定速度
								String train_type = (String) gzdata
										.get("train_type");// 车型
								if (train_type != null
										&& train_type.equals("1")) {
									train_type = "HXD1C机车";
								}
								if (train_type != null
										&& train_type.equals("2")) {
									train_type = "HXD1D机车";
								}
								IJpoSet jposet = MroServer.getMroServer()
										.getSysJpoSet("BDSYNCINTERFACE", "1=2");
								IJpo jpo = jposet.addJpo();
								jpo.setValue("abc_id", abc_id,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("date_time", date_time,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("en_temp1", en_temp1,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("kilometer", kilometer,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("latitude", latitude,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("longitude", longitude,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("odometer", odometer,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("real_cross", real_cross,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("real_speed", real_speed,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("time", time,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("total_weight", total_weight,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("tcu_time", tcu_time,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("train_fun", train_fun,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("train_id", train_id,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("train_level", train_level,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("train_num", train_num,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("train_op_mode", train_op_mode,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("train_real_speed",
										train_real_speed,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("train_set_speed",
										train_set_speed,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("ORGID", "CRRC",
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("SITEID", "ELEC",
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("train_type", train_type,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

								jposet.save();
								this.showOperInfo("bigdata", "createSuccess");
							} else {
								throw new AppException("bigdata", "error");
							}
						}
						datahttpConnection.disconnect();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (datahttpConnection != null) {
					datahttpConnection.disconnect();
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
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
	 * 根据车型获取车型代码
	 * 
	 * @param 车型
	 *            MODELCODE
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
}
