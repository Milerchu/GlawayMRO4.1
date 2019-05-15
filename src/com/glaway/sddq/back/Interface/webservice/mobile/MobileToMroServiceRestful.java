package com.glaway.sddq.back.Interface.webservice.mobile;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.WorkorderUtil;
import com.glaway.sddq.tools.mobiletools.MobileResult;
import com.glaway.sddq.tools.mobiletools.MobileUploadFile;
import com.glaway.sddq.tools.mobiletools.MobileUtil;

@Path("/app")
public class MobileToMroServiceRestful {

	/**
	 * 
	 * 微信小程序公共入口
	 * 
	 * @param methodName
	 * @param parameter
	 * @return [参数说明]
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@POST
	@Path("/wechat")
	// @Consumes({MediaType.TEXT_PLAIN,MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON,MediaType.MEDIA_TYPE_WILDCARD})
	@Produces("application/json;charset=utf-8")
	public String wechat(@FormParam("methodName") String methodName,
			@FormParam("parameter") String parameter)
			throws UnsupportedEncodingException {

		MobileResult result = new MobileResult();
		if ("toAddMroData".equals(methodName)) {
			result = toAddMroData(parameter);
		} else if ("toUpdateMroData".equals(methodName)) {
			result = toUpdateMroData(parameter);
		} else if ("toDeleteMroData".equals(methodName)) {
			result = toDeleteMroData(parameter);
		} else if ("startMroWF".equals(methodName)) {
			result = startMroWF(parameter);
		} else if ("getAuth".equals(methodName)) {
			result = getAuthorization(parameter);
		} else if ("event".equals(methodName)) {
			result = moblieEvent(parameter);
		} else if ("field".equals(methodName)) {
			result = moblieField(parameter);
		} else if ("scan".equals(methodName)) {
			result = scanCode(parameter);
		} else if ("custom".equals(methodName)) {
			result = customDeal(parameter);
		} else {
			result.setCode("400");
			result.setMsg("无法根据请求参数：" + methodName + "获取请求方法");
		}
		// return new String(result.toString().getBytes(),"utf-8");
		return result.toString();
	}

	/**
	 * 返回客户数据和电话号码 <功能描述>
	 * 
	 * @param mobileJson
	 * @return [参数说明]
	 * 
	 */
	private MobileResult customDeal(String mobileJson) {
		MobileResult result = new MobileResult();
		JSONObject data = JSONObject.parseObject(mobileJson);
		String customId = data.getString("customId");
		String method = data.getString("method");
		result = invokeCustom(method, mobileJson, customId);
		return result;
	}

	/**
	 * 
	 * 客户小程序处理
	 * 
	 * @param method
	 *            方法名
	 * @param mobileJson
	 *            请求数据
	 * @param customId
	 *            客户电话号码
	 * @return [参数说明]
	 * 
	 */
	private MobileResult invokeCustom(String method, String mobileJson,
			String customId) {
		MobileResult result = new MobileResult();

		try {
			Class<?> clz = Class
					.forName("com.glaway.sddq.tools.mobiletools.MobileCustom");
			Method me = clz.getMethod(method, String.class, String.class);
			Object invokeResult = me.invoke(clz.newInstance(), mobileJson,
					customId);
			if (invokeResult != null && invokeResult instanceof MobileResult) {
				result = (MobileResult) invokeResult;
			}
		} catch (ClassNotFoundException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		} catch (SecurityException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		} catch (NoSuchMethodException e) {
			result.setMsg("无法根据方法名" + method + "获取对应方法");
			result.setCode("404");
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		} catch (IllegalAccessException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		}
		// InvocationTargetException中是反射调用方法抛出的异常
		catch (InvocationTargetException e) {
			Throwable t = e.getCause();
			t.printStackTrace();
			if (t instanceof MroException) {
				result.setMsg("请求被拒绝：" + t.getMessage());
				result.setCode("403");
			} else {
				result.setMsg("请求失败：" + t.getMessage());
				result.setCode("500");
			}
		} catch (InstantiationException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		}
		return result;
	}

	@POST
	@Path("/wechatupload")
	@Produces(MediaType.TEXT_PLAIN)
	public String wechatBasic64(@FormParam("parameter") String parameter) {

		MobileResult result = new MobileResult();

		result = MobileUploadFile.uploadToMroDisk(parameter);

		return result.toString();
	}

	private MobileResult scanCode(String mobileJson) {
		MobileResult result = new MobileResult();
		JSONObject data = JSONObject.parseObject(mobileJson);
		String appId = data.getString("appId").toUpperCase();
		String userId = data.getString("userId");
		result = invoke("SCAN", appId, mobileJson, userId);
		return result;
	}

	public MobileResult startMroWF(String mobileJson) {
		MobileResult result = new MobileResult();
		JSONObject obj = JSONObject.parseObject(mobileJson);
		String appid = obj.getString("appId");
		String userId = obj.getString("userId").toUpperCase();
		String action = obj.getString("action");
		IJpo jpo = null;
		String ifnum = "";
		try {
			ifnum = IFUtil.addIfHistory("MOBILE_STARTWF", mobileJson,
					IFUtil.TYPE_INPUT);
			if ("SERVORDER".equalsIgnoreCase(appid)
					|| "FAILUREORD".equalsIgnoreCase(appid)
					|| "TRANSORDER".equalsIgnoreCase(appid)
					|| "VALIORDER".equalsIgnoreCase(appid)) {// 服务、故障、改造、验证工单
				String ordernum = obj.getString("uniqueNum");

				// 查询具体jpo
				IJpoSet woSet = MroServer.getMroServer().getSysJpoSet(
						"WORKORDER");
				woSet.setQueryWhere("ORDERNUM='" + ordernum + "'");
				woSet.reset();
				if (!woSet.isEmpty()) {
					jpo = woSet.getJpo(0);
				}
			} else if ("ZXTRANSFER".equalsIgnoreCase(appid)) {// 装箱单
				String transfernum = obj.getString("uniqueNum");
				// 查询具体jpo
				IJpoSet transferSet = MroServer.getMroServer().getSysJpoSet(
						"TRANSFER");
				transferSet.setQueryWhere("transfernum='" + transfernum + "'");
				transferSet.reset();
				transferSet.reset();
				if (!transferSet.isEmpty()) {
					jpo = transferSet.getJpo(0);
				}
			} else if ("MATERREQ".equalsIgnoreCase(appid)) {// 配件申请
				String mrNum = obj.getString("uniqueNum");
				// 查询具体jpo
				IJpoSet mrSet = MroServer.getMroServer().getSysJpoSet("MR");
				mrSet.setQueryWhere("mrNum='" + mrNum + "'");
				mrSet.reset();
				mrSet.reset();
				if (!mrSet.isEmpty()) {
					jpo = mrSet.getJpo(0);
				}
			} else {
				result.setCode("400");
				result.setMsg("无法根据应用程序:" + appid + "获取对应表");
				IFUtil.updateIfHistory(ifnum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, result.getMsg());
				return result;
			}
			if (jpo == null) {
				result.setCode("400");
				result.setMsg("无法根据编号:" + obj.getString("uniqueNum") + "获取对应记录");
				IFUtil.updateIfHistory(ifnum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, result.getMsg());
				return result;
			}
			// 启动工作流
			result = MobileUtil.startWF(jpo, appid, userId, action, "移动端工作流备忘");

			if ("200".equals(result.getCode())) {// 操作成功
				IFUtil.updateIfHistory(ifnum, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, result.getMsg());
			} else {// 操作失败
				IFUtil.updateIfHistory(ifnum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, result.getMsg());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private MobileResult toAddMroData(String mobileJson) {

		// 日志编码
		String logNum = "";
		MobileResult result = new MobileResult();
		try {
			logNum = IFUtil.addIfHistory("MOBILE_TOADDMRODATA", mobileJson,
					IFUtil.TYPE_INPUT);

			JSONObject data = JSONObject.parseObject(mobileJson);
			String appId = data.getString("appId").toUpperCase();
			String userId = data.getString("userId");
			result = invoke("ADD", appId, mobileJson, userId);
			if ("200".equals(result.getCode())) {// 成功
				IFUtil.updateIfHistory(logNum, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "新建成功，返回信息：" + result.toString());
			} else {// 失败
				IFUtil.updateIfHistory(logNum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "新建失败，错误信息：" + result.getMsg());
			}

		} catch (MroException e) {
			e.printStackTrace();
			try {
				IFUtil.updateIfHistory(logNum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "新建失败，错误信息：" + e.getMessage());
			} catch (MroException e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}

	private MobileResult toDeleteMroData(String mobileJson) {
		// 日志编码
		String logNum = "";
		MobileResult result = new MobileResult();
		JSONObject data = JSONObject.parseObject(mobileJson);
		String userId = data.getString("userId");
		try {
			// 记录日志
			logNum = IFUtil.addIfHistory("MOBILE_TODELETEMRODATA", mobileJson,
					IFUtil.TYPE_INPUT);

			String tableName = data.getString("tableName");
			String id = data.getString("uniqueId");
			IJpoSet delSet = MroServer.getMroServer().getSysJpoSet(tableName,
					tableName + "ID='" + id + "'");
			if (delSet.count() > 0) {
				IJpo del = delSet.getJpo();
				// ConsumeDataBean
				if ("jxtasklosspart".equalsIgnoreCase(tableName)) {
					if ("故障".equals(del.getString("workordertype"))
							|| "改造".equals(del.getString("workordertype"))) {
						int amount = del.getInt("AMOUNT");// 上车数量
						String assetpartnum = del.getString("DOWNPARTNUM");
						String itemnum = del.getString("itemnum");// 上车件物料编码
						String uploc = del.getString("uploc");// 上车件库房
						String lotnum = del.getString("lotnum");// 上车件批次号

						// 操作锁定数量
						WorkorderUtil.operateQty(assetpartnum, amount, itemnum,
								uploc, lotnum, "-");
					}
				} else if ("transferline".equalsIgnoreCase(tableName)) {
					IJpoSet transferSet = MroServer.getMroServer()
							.getSysJpoSet(
									"transfer",
									"TRANSFERNUM='"
											+ del.getString("TRANSFERNUM")
											+ "'");
					IJpo transfer = transferSet.getJpo();
					String CREATEBY = transfer.getString("CREATEBY");
					String status = transfer.getString("status");
					if (!userId.equalsIgnoreCase(CREATEBY)) {
						throw new MroException("transferline", "nodelete");
					} else if (!status.equalsIgnoreCase("未处理")
							&& !status.equalsIgnoreCase("申请人修改")
							&& !status.equalsIgnoreCase("驳回")) {
						throw new MroException("transferline", "statusnodelete");
					}
				}
				del.delete();
				delSet.save();

				IFUtil.updateIfHistory(logNum, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "删除成功，返回信息：" + result.toString());

			} else {
				result.setCode("400");
				result.setMsg("无法根据唯一ID: " + id + " 获取表：" + tableName + " 的数据");

				IFUtil.updateIfHistory(logNum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "删除失败，错误信息：" + result.getMsg());
			}
		} catch (MroException e) {
			result.setCode("400");
			result.setMsg(e.getMessage());

			try {
				IFUtil.updateIfHistory(logNum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "删除失败，错误信息：" + e.getMessage());
			} catch (MroException e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 
	 * 根据人员id获取移动端权限列表
	 * 
	 * @param personid
	 * @return [参数说明]
	 * 
	 */
	public MobileResult getAuthorization(String parameter) {
		// 输入日志编码
		String inputLogNum = "";
		String outputLogNum = "";
		// 记录请求日志
		try {
			inputLogNum = IFUtil.addIfHistory("MOBILE_GETAUTH", parameter,
					IFUtil.TYPE_INPUT);

			IFUtil.updateIfHistory(inputLogNum, IFUtil.STATUS_SUCCESS,
					IFUtil.FLAG_YES, "请求成功");
		} catch (MroException e1) {
			e1.printStackTrace();
			try {
				IFUtil.updateIfHistory(inputLogNum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "请求失败，错误信息：" + e1.getMessage());
			} catch (MroException e) {
				e.printStackTrace();
			}
		}

		MobileResult result = new MobileResult();
		JSONObject data = JSONObject.parseObject(parameter);
		String personid = data.getString("userId");
		try {
			result.setCode("200");
			JSONObject returnData = MobileUtil.backappid(personid);
			result.setData(returnData);
			outputLogNum = IFUtil.addIfHistory("MOBILE_GETAUTH",
					returnData.toString(), IFUtil.TYPE_OUTPUT);
			IFUtil.updateIfHistory(outputLogNum, IFUtil.STATUS_SUCCESS,
					IFUtil.FLAG_YES, "权限获取成功");

		} catch (MroException e) {
			result.setCode("400");
			result.setMsg(e.getMessage());
			try {
				IFUtil.updateIfHistory(outputLogNum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "权限获取失败，错误信息：" + e.getMessage());
			} catch (MroException e1) {
				e1.printStackTrace();
			}
		}

		return result;
	}

	public MobileResult toUpdateMroData(String mobileJson) {

		// 日志编码
		String logNum = "";
		MobileResult result = new MobileResult();

		try {
			logNum = IFUtil.addIfHistory("MOBILE_TOUPDATEMRODATA", mobileJson,
					IFUtil.TYPE_INPUT);

			JSONObject data = JSONObject.parseObject(mobileJson);
			String appId = data.getString("appId").toUpperCase();
			String userId = data.getString("userId");
			result = invoke("UPDATE", appId, mobileJson, userId);

			if ("200".equals(result.getCode())) {// 成功
				IFUtil.updateIfHistory(logNum, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "数据更新成功，返回信息:" + result.toString());
			} else {// 失败
				IFUtil.updateIfHistory(logNum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "数据更新失败，错误信息：" + result.getMsg());
			}
		} catch (MroException e) {
			e.printStackTrace();
			try {
				IFUtil.updateIfHistory(logNum, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "数据更新失败，错误信息：" + e.getMessage());
			} catch (MroException e1) {
				e1.printStackTrace();
			}
		}
		return result;

	}

	public MobileResult moblieEvent(String mobileJson) {
		JSONObject data = JSONObject.parseObject(mobileJson);
		String appId = data.getString("appId").toUpperCase();
		String userId = data.getString("userId");
		MobileResult result = invokeEvent(appId, appId, mobileJson, userId);
		return result;
	}

	public MobileResult moblieField(String mobileJson) {
		JSONObject data = JSONObject.parseObject(mobileJson);
		String appId = data.getString("appId").toUpperCase();
		String userId = data.getString("userId");
		MobileResult result = invokeField(appId, mobileJson, userId);
		return result;
	}

	/**
	 * 
	 * 调用MobileUtil类中对应方法
	 * 
	 * @param action
	 * @param tableName
	 * @param data
	 * @return [参数说明]
	 * 
	 */
	private MobileResult invoke(String action, String appName, String data,
			String loginid) {
		MobileResult result = new MobileResult();

		try {
			Class<?> clz = Class
					.forName("com.glaway.sddq.tools.mobiletools.MobileUtil");
			Method me = clz.getMethod(action + appName, String.class,
					String.class);
			Object invokeResult = me.invoke(clz.newInstance(), loginid, data);
			if (invokeResult != null && invokeResult instanceof MobileResult) {
				result = (MobileResult) invokeResult;
			}
		} catch (ClassNotFoundException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		} catch (SecurityException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		} catch (NoSuchMethodException e) {
			result.setMsg("无法根据应用程序" + appName + "获取对应" + action + "方法");
			result.setCode("404");
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		} catch (IllegalAccessException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		}
		// InvocationTargetException中是反射调用方法抛出的异常
		catch (InvocationTargetException e) {
			Throwable t = e.getCause();
			t.printStackTrace();
			if (t instanceof MroException) {
				result.setMsg("请求被拒绝：" + t.getMessage());
				result.setCode("403");
			} else {
				result.setMsg("请求失败：" + t.getMessage());
				result.setCode("500");
			}
		} catch (InstantiationException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		}
		return result;
	}

	/**
	 * 反射抛出的异常 <功能描述>
	 * 
	 * @param func
	 * @param data
	 * @param loginid
	 * @return [参数说明]
	 * 
	 */
	private MobileResult invokeEvent(String action, String tableName,
			String data, String loginid) {
		MobileResult result = new MobileResult();

		try {
			Class<?> clz = Class
					.forName("com.glaway.sddq.tools.mobiletools.MobileButton");
			Method me = clz.getMethod(tableName, String.class);
			result = (MobileResult) me.invoke(clz.newInstance(), data);
		} catch (ClassNotFoundException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		} catch (SecurityException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		} catch (NoSuchMethodException e) {
			result.setMsg("无法获取" + tableName + "的对应方法");
			result.setCode("404");
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		} catch (IllegalAccessException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		}
		// InvocationTargetException中是反射调用方法抛出的异常
		catch (InvocationTargetException e) {
			Throwable t = e.getCause();
			t.printStackTrace();
			if (t instanceof MroException) {
				result.setMsg("请求被拒绝：" + t.getMessage());
				result.setCode("403");
			} else {
				result.setMsg("请求失败：" + t.getMessage());
				result.setCode("500");
			}
		} catch (InstantiationException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		}
		return result;
	}

	/**
	 * 反射抛出的异常 <功能描述>
	 * 
	 * @param func
	 * @param data
	 * @param loginid
	 * @return [参数说明]
	 * 
	 */
	private MobileResult invokeField(String func, String data, String loginid) {
		MobileResult result = new MobileResult();

		try {
			Class<?> clz = Class
					.forName("com.glaway.sddq.tools.mobiletools.MobileField");
			Method me = clz.getMethod(func, String.class);
			result = (MobileResult) me.invoke(clz.newInstance(), data);
		} catch (ClassNotFoundException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		} catch (SecurityException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		} catch (NoSuchMethodException e) {
			result.setMsg("无法根据应用程序" + func + "获取对应虚拟字段字段");
			result.setCode("404");
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		} catch (IllegalAccessException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		}
		// InvocationTargetException中是反射调用方法抛出的异常
		catch (InvocationTargetException e) {
			Throwable t = e.getCause();
			t.printStackTrace();
			if (t instanceof MroException) {
				result.setMsg("请求被拒绝：" + t.getMessage());
				result.setCode("403");
			} else {
				result.setMsg("请求失败：" + t.getMessage());
				result.setCode("500");
			}
		} catch (InstantiationException e) {
			result.setMsg(e.getMessage());
			result.setCode("500");
		}
		return result;
	}
}