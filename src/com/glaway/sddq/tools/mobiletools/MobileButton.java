package com.glaway.sddq.tools.mobiletools;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.material.invtrans.common.CommonAddNewInventory;
import com.glaway.sddq.material.invtrans.common.Commondeleteotherqty;
import com.glaway.sddq.material.invtrans.common.TransLineInvtranscommon;
import com.glaway.sddq.material.invtrans.common.TransLineStoroomCommon;
import com.glaway.sddq.material.invtrans.common.TransferlinetradeCommon;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.WorkorderUtil;
import com.glaway.sddq.tools.mobiletools.mobileInterface.IfConvertLoc;
import com.glaway.sddq.tools.mobiletools.mobileInterface.IfZxTransfer;

/**
 * 
 * 移动端按钮类
 * 
 * @author public2176
 * @version [版本号, 2018-8-3]
 * @since [产品/模块版本]
 */
public class MobileButton {

	public MobileResult SERVORDER(String json) throws MroException {
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("servOrder");
		// 将改变的数据返回前端
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 工单编号
		if (!data.containsKey("orderNum")) {
			throw new MroException("必填字段:工单编号 为空，请检查！");
		}
		String ordernum = data.getString("orderNum");
		IJpoSet workorderSet = MroServer.getMroServer().getJpoSet("WORKORDER",
				MroServer.getMroServer().getSystemUserServer());
		// 设置应用名称
		workorderSet.setAppName("SERVORDER");
		workorderSet.setQueryWhere("ordernum='" + ordernum + "' and type='服务'");
		workorderSet.reset();
		if (workorderSet.count() < 1) {
			throw new MroException("无法根据编号:" + ordernum + "获取服务工单");
		}
		IJpo workorder = workorderSet.getJpo();

		if (StringUtil.isStrEmpty(MobileUtil.hasTaskAuth(workorder,
				"SERVORDER", obj.getString("userId")))) {
			throw new MroException("当前用户无法修改此工单");
		}

		if ("ARRIVAL".equals(data.getString("action"))) {

			if (workorder.getString("status").equals("处理中")) {

				if (workorder.getDate("ARRIVETIME") != null) {
					throw new MroException("", "已经到达现场!");
				}
				Date date = MroServer.getMroServer().getDate();
				workorder.setValue("ARRIVETIME", date,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				workorderSet.save();
				ret.put("arriveTime", sdf.format(date));
				result.setData(ret);
			} else {
				// 当前状态无法操作
				throw new MroException("servorder", "statusnoaction");
			}
		} else if ("STARTWORK".equals(data.getString("action"))) {
			if (workorder.getString("status").equals("处理中")) {
				if (workorder.getDate("OPERATETIME") != null) {
					throw new MroException("", "已经开始作业!");
				}
				if (workorder.getDate("ARRIVETIME") == null) {
					// 未进行到达现场操作
					throw new MroException("workorder", "arrivefirst");
				}
				Date date = MroServer.getMroServer().getDate();
				workorder.setValue("OPERATETIME", date,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				workorderSet.save();
				ret.put("operateTime", sdf.format(date));
				result.setData(ret);
			} else {
				throw new MroException("servorder", "statusnoaction");
			}
		} else {
			throw new MroException("", "无法根据action:" + data.getString("action")
					+ "获取对应服务响应");
		}
		return result;
	}

	public MobileResult FAILUREORD(String json) throws MroException {
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("failureOrd");
		// 将改变的数据返回前端
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 工单编号
		if (!data.containsKey("orderNum")) {
			throw new MroException("必填字段:工单编号 为空，请检查！");
		}
		String ordernum = data.getString("orderNum");
		IJpoSet workorderSet = MroServer.getMroServer().getJpoSet("WORKORDER",
				MroServer.getMroServer().getSystemUserServer());
		// 设置应用名称
		workorderSet.setAppName("FAILUREORD");
		workorderSet.setQueryWhere("ordernum='" + ordernum + "' and type='故障'");
		workorderSet.reset();
		if (workorderSet.count() < 1) {
			throw new MroException("无法根据编号:" + ordernum + "获取故障工单");
		}
		IJpo workorder = workorderSet.getJpo();
		String status = workorder.getString("status");
		// 判断当前登录人是否是工作流执行人
		if (StringUtil.isStrEmpty(MobileUtil.hasTaskAuth(workorder,
				"FAILUREORD", obj.getString("userId")))) {
			throw new MroException("workorder", "notwfcuruser");
		}
		// 到达现场
		if ("ARRIVAL".equals(data.getString("action"))) {
			if (status.equals("处理中")) {
				if (workorder.getDate("ARRIVETIME") != null) {
					throw new MroException("", "已经到达现场!");
				}
				Date date = MroServer.getMroServer().getDate();
				workorder.setValue("ARRIVETIME", date,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				workorderSet.save();
				ret.put("ARRIVETIME", sdf.format(date));
				result.setData(ret);
			} else {
				// 当前状态无法操作
				throw new MroException("servorder", "statusnoaction");
			}
		} else if ("STARTWORK".equals(data.getString("action"))) {
			// 开始工作
			if (status.equals("处理中")) {
				if (workorder.getDate("OPERATETIME") != null) {
					throw new MroException("", "已经开始作业!");
				}
				if (workorder.getDate("ARRIVETIME") == null) {
					// 未进行到达现场操作
					throw new MroException("workorder", "arrivefirst");
				}
				Date date = MroServer.getMroServer().getDate();
				workorder.setValue("OPERATETIME", date,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				workorderSet.save();
				ret.put("OPERATETIME", sdf.format(date));
				result.setData(ret);
			} else if ("挂起".equals(status)) {
				unHoldOrder(workorder);
				ret.put("STATUS", "处理中");
				result.setData(ret);
			} else {
				throw new MroException("", "当前状态无法操作!");
			}
		} else if ("HOLD".equals(data.getString("action"))) {
			if ("处理中".equals(status)) {
				if (workorder.getDate("OPERATETIME") == null) {
					throw new MroException("", "开始作业后才能挂起工单！");
				}
				// 增加挂起表记录
				holdOrder(workorder, data.getString("userId"));
				ret.put("STATUS", "挂起");
				result.setData(ret);
			} else if ("挂起".equals(status)) {
				// 故障工单中，解除挂起由开始作业按钮完成
				throw new MroException("", "当前工单已经挂起！");
			} else {
				throw new MroException("", "当前状态无法操作！");
			}
		} else if ("FINISHPROCESS".equals(data.getString("action"))) {
			if (status.equals("处理中")) {
				if (workorder.getDate("OPERATETIME") == null) {
					throw new MroException("", "还未开始作业!");
				}
				if (workorder.getDate("COMPLETETIME") != null) {
					throw new MroException("", "已经处理完成!");
				}
				Date operadate = MroServer.getMroServer().getDate();
				workorder.setValue("COMPLETETIME", operadate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			} else {
				throw new MroException("当前状态无法操作！");
			}
		} else if ("SBYWXX".equals(data.getString("action"))) {// 三包业务信息按钮
			// 设置当前操作人
			String userId = obj.getString("userId");

			String[] statuses = { "专职库管员审核", "技术主管审核", "审计审核" };
			if (!StringUtil.isHaveStr(statuses, status)) {
				throw new MroException("servorder", "statusnoaction");
			}
			if (!MobileUtil.isCurUser(workorder, userId)) {
				throw new MroException("非工作流执行人无法操作！");
			}
			String retn = WorkorderUtil.tomsg(workorder);
			if (!"S".equals(retn)) {
				throw new MroException("三包订单接口访问出错", retn);
			}

		} else {
			throw new MroException("", "无法根据action:" + data.getString("action")
					+ "获取对应服务响应");
		}
		workorderSet.save();
		return result;
	}

	public MobileResult TRANSORDER(String json) throws MroException {
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("transOrder");
		// 将改变的数据返回前端
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		// 工单编号
		if (!data.containsKey("orderNum")) {
			throw new MroException("必填字段:工单编号 为空，请检查！");
		}
		String ordernum = data.getString("orderNum");
		IJpoSet workorderSet = MroServer.getMroServer().getJpoSet("WORKORDER",
				MroServer.getMroServer().getSystemUserServer());
		// 设置应用名称
		workorderSet.setAppName("VALIORDER");
		workorderSet.setQueryWhere("ordernum='" + ordernum + "' and type='改造'");
		workorderSet.reset();
		if (workorderSet.count() < 1) {
			throw new MroException("无法根据编号:" + ordernum + "获取改造工单");
		}
		IJpo workorder = workorderSet.getJpo();

		if ("HOLD".equals(data.getString("action"))) {
			if (!"处理中".equals(workorder.getString("status"))) {
				if ("挂起".equals(workorder.getString("STATUS"))) {
					unHoldOrder(workorder);
					ret.put("STATUS", "处理中");
				} else {
					throw new MroException("", "当前状态无法操作！");
				}
			} else {
				// 增加挂起表记录
				holdOrder(workorder, data.getString("userId"));
				ret.put("STATUS", "挂起");
			}
			result.setData(ret);
		} else {
			throw new MroException("", "无法根据action:" + data.getString("action")
					+ "获取对应服务响应");
		}
		workorderSet.save();
		return result;
	}

	public MobileResult VALIORDER(String json) throws MroException {
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("valiOrder");
		// 将改变的数据返回前端
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		// 工单编号
		if (!data.containsKey("orderNum")) {
			throw new MroException("必填字段:工单编号 为空，请检查！");
		}
		String ordernum = data.getString("orderNum");
		IJpoSet workorderSet = MroServer.getMroServer().getJpoSet("WORKORDER",
				MroServer.getMroServer().getSystemUserServer());
		// 设置应用名称
		workorderSet.setAppName("VALIORDER");
		workorderSet.setQueryWhere("ordernum='" + ordernum + "' and type='验证'");
		workorderSet.reset();
		if (workorderSet.count() < 1) {
			throw new MroException("无法根据编号:" + ordernum + "获取验证工单");
		}
		IJpo workorder = workorderSet.getJpo();

		if ("HOLD".equals(data.getString("action"))) {
			if (!"处理中".equals(workorder.getString("status"))) {
				if ("挂起".equals(workorder.getString("STATUS"))) {
					unHoldOrder(workorder);
					ret.put("STATUS", "处理中");
				} else {
					throw new MroException("", "当前状态无法操作！");
				}
			} else {
				// 增加挂起表记录
				holdOrder(workorder, data.getString("userId"));
				ret.put("STATUS", "挂起");
			}
			result.setData(ret);
		} else {
			throw new MroException("", "无法根据action:" + data.getString("action")
					+ "获取对应服务响应");
		}
		workorderSet.save();
		return result;
	}

	public MobileResult ZXTRANSFER(String json) throws MroException {
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("transfer");
		String loginid = obj.getString("userId");
		// 将改变的数据返回前端
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();

		// 装箱单编号
		String transfernum = data.getString("transferNum");
		if (transfernum == null || transfernum.isEmpty()) {
			throw new MroException("必填字段：装箱单编号 为空，请检查");
		}

		// 根据装箱单编号transfernum获取相关字段数据
		IJpoSet transferSet = MroServer.getMroServer().getJpoSet("TRANSFER",
				MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setPersonId(loginid);
		transferSet.setQueryWhere("transfernum='" + transfernum
				+ "' and type='ZXD'");
		transferSet.setAppName("ZXTRANSFER");
		transferSet.reset();

		if (transferSet.count(GWConstant.P_COUNT_DATABASE) < 1) {
			throw new MroException("无法根据编号:" + transfernum + "获取装箱单");
		}
		IJpo transferJpo = transferSet.getJpo();
		transferJpo.getUserServer().getUserInfo().setLoginID(loginid);
		transferJpo.getUserServer().getUserInfo().setUserName(loginid);

		if ("ISSUE".equals(data.getString("action"))) {
			// ZxTransferAppBean中的发货ISSUE方法
			String status = transferJpo.getString("status");
			String issue = transferJpo.getString("issue");// 是否发出标记

			if (WfControlUtil.hasActiveWf(transferJpo)
					&& !WfControlUtil.isCurUser(transferJpo)) { /* 当前登陆人不是流程审批人 */
				throw new MroException("transferline", "noissueby");
			}

			JSONObject transferRet = new JSONObject();
			if (issue.equalsIgnoreCase("否")) {
				String TRANSFERMOVETYPE = transferJpo
						.getString("TRANSFERMOVETYPE");
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
							IJpoSet sqntransferlineset = transferJpo
									.getJpoSet("transferline");
							sqntransferlineset
									.setUserWhere("itemnum in (select itemnum from sys_item where ISTURNOVERERP=1) and sqn is null");
							sqntransferlineset.reset();
							if (!sqntransferlineset.isEmpty()) {
								throw new MroException("transfer", "sqn");
							} else {
								// TransStoroomCommon.out_storoom(transferlineset);//
								// 调用公共方法物料出库
								// TransInvtranscommon.out_invtrans(transferlineset);//
								// 调用公共方法物料出库库存交易记录
								IfZxTransfer.UPDATESQNSTATUS(transferlineset);
								transferJpo.setValue("status", "在途",
										GWConstant.P_NOVALIDATION);
								transferJpo.setValue("issue", "是",
										GWConstant.P_NOVALIDATION);
								transferRet.put("status", "在途");
								transferRet.put("issue", "是");
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
								transferRet.put("sendTime", format);
								IfZxTransfer.ADDMRQTY(transferJpo);
								transferSet.save();
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
							IJpoSet sqntransferlineset = transferJpo
									.getJpoSet("transferline");
							sqntransferlineset
									.setUserWhere("itemnum in (select itemnum from sys_item where ISTURNOVERERP=1) and sqn is null");
							sqntransferlineset.reset();
							if (!sqntransferlineset.isEmpty()) {
								throw new MroException("transfer", "sqn");
							} else {
								// TransStoroomCommon.out_storoom(transferlineset);//
								// 调用公共方法物料出库
								// TransInvtranscommon.out_invtrans(transferlineset);//
								// 调用公共方法物料出库库存交易记录
								IfZxTransfer.UPDATESQNSTATUS(transferlineset);
								transferJpo.setValue("status", "在途",
										GWConstant.P_NOVALIDATION);
								transferJpo.setValue("issue", "是",
										GWConstant.P_NOVALIDATION);
								transferRet.put("status", "在途");
								transferRet.put("issue", "是");
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
								transferRet.put("sendTime", format);
								IfZxTransfer.ADDMRQTY(transferJpo);
								transferSet.save();
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
						if (loginid.equalsIgnoreCase(sendby)) {
							IJpoSet transferlineset = transferJpo
									.getJpoSet("transferline");
							if (transferlineset.count() == 0) {
								throw new MroException("transferline",
										"statusissue");
							} else {
								IJpoSet sqntransferlineset = transferJpo
										.getJpoSet("transferline");
								sqntransferlineset
										.setUserWhere("itemnum in (select itemnum from sys_item where ISTURNOVERERP=1) and sqn is null");
								sqntransferlineset.reset();
								if (!sqntransferlineset.isEmpty()) {
									throw new MroException("transfer", "sqn");
								} else {
									// TransStoroomCommon.out_storoom(transferlineset);//
									// 调用公共方法物料出库
									// TransInvtranscommon.out_invtrans(transferlineset);//
									// 调用公共方法物料出库库存交易记录
									IfZxTransfer
											.UPDATESQNSTATUS(transferlineset);
									transferJpo.setValue("status", "在途",
											GWConstant.P_NOVALIDATION);
									transferJpo.setValue("issue", "是",
											GWConstant.P_NOVALIDATION);
									transferRet.put("status", "在途");
									transferRet.put("issue", "是");
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
									transferRet.put("sendTime", format);
									IfZxTransfer.ADDMRQTY(transferJpo);
									transferSet.save();
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
						if (loginid.equalsIgnoreCase(sendby)) {
							IJpoSet transferlineset = transferJpo
									.getJpoSet("transferline");
							if (transferlineset.count() == 0) {
								throw new MroException("transferline",
										"statusissue");
							} else {
								IJpoSet sqntransferlineset = transferJpo
										.getJpoSet("transferline");
								sqntransferlineset
										.setUserWhere("itemnum in (select itemnum from sys_item where ISTURNOVERERP=1) and sqn is null");
								sqntransferlineset.reset();
								if (!sqntransferlineset.isEmpty()) {
									throw new MroException("transfer", "sqn");
								} else {
									// TransStoroomCommon.out_storoom(transferlineset);//
									// 调用公共方法物料出库
									// TransInvtranscommon.out_invtrans(transferlineset);//
									// 调用公共方法物料出库库存交易记录
									IfZxTransfer
											.UPDATESQNSTATUS(transferlineset);
									transferJpo.setValue("status", "在途");
									transferJpo.setValue("issue", "是");
									ret.put("status", "在途");
									ret.put("issue", "是");
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
									IfZxTransfer.ADDMRQTY(transferJpo);
									transferSet.save();
								}

							}
						} else {
							throw new MroException("transferline", "sendby");
						}
					}
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
						if (loginid.equalsIgnoreCase(sendby)) {
							IJpoSet transferlineset = transferJpo
									.getJpoSet("transferline");
							if (transferlineset.count() == 0) {
								throw new MroException("transferline",
										"statusissue");
							} else {
								IJpoSet sqntransferlineset = transferJpo
										.getJpoSet("transferline");
								sqntransferlineset
										.setUserWhere("itemnum in (select itemnum from sys_item where ISTURNOVERERP=1) and sqn is null");
								sqntransferlineset.reset();
								if (!sqntransferlineset.isEmpty()) {
									throw new MroException("transfer", "sqn");
								} else {
									// TransStoroomCommon.out_storoom(transferlineset);//
									// 调用公共方法物料出库
									// TransInvtranscommon.out_invtrans(transferlineset);//
									// 调用公共方法物料出库库存交易记录
									IfZxTransfer
											.UPDATESQNSTATUS(transferlineset);
									transferJpo.setValue("status", "在途");
									transferJpo.setValue("issue", "是");
									ret.put("status", "在途");
									ret.put("issue", "是");
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
									ret.put("sendTime", format);

									IfZxTransfer.ADDMRQTY(transferJpo);
									transferSet.save();
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
				ret.put("transfer", transferRet);
			} else {
				throw new MroException("transfer", "issue");
			}
		} else if ("RECEIVELINE".equals(data.getString("action"))) {
			// 接收单行用字段
			String RECEIVEBY = transferJpo.getString("RECEIVEBY");// 现场库管员
			String OPENBY = transferJpo.getString("OPENBY");
			String RECEIVEDATE = transferJpo.getString("RECEIVEDATE");
			String RECEIVECHECKBY = transferJpo.getString("RECEIVECHECKBY");

			// 装箱单行编号
			String transferlinenum = data.getString("transferLineNum");
			// 接收数量
			Double receiveCount = Double.parseDouble(data
					.getString("receiveCount"));
			if (!(receiveCount > 0)) {
				throw new MroException("请设置正确的接收数量！");
			}
			boolean islocbin = transferJpo.getJpoSet("RECEIVESTOREROOM")
					.getJpo().getBoolean("islocbin");

			// 入库仓位--库房管理时
			String inbinnum = "";
			if (islocbin) {
				inbinnum = data.getString("inBinNum");
			}

			IJpoSet transferlineset = transferJpo.getJpoSet("transferline");
			transferlineset.setQueryWhere("transferlinenum='" + transferlinenum
					+ "'");
			transferlineset.reset();
			if (transferlineset.count() < 1) {
				throw new MroException("无法根据编号:" + transferlinenum + " 获取装箱单行");
			}
			IJpo transferLine = transferlineset.getJpo();
			String status = transferLine.getString("status");

			// 逻辑来自ZxdTransferLineDataBean.status()--SVN：2995
			if (!WfControlUtil.isCurUser(transferJpo)) { /* 当前登陆人不是流程审批人 */
				throw new MroException("transferline", "noreceiveby");
			}
			if (loginid.equalsIgnoreCase(RECEIVEBY)) {
				if (status.equalsIgnoreCase("已接收")) {
					throw new MroException("transferline", "receive");
				}
				if (!transferJpo.getString("status").equalsIgnoreCase("在途")) {
					throw new MroException("transferline", "statusreceive");
				} else if (transferJpo.getString("status").equalsIgnoreCase(
						"在途")) {
					if (OPENBY.equalsIgnoreCase("")
							|| RECEIVEDATE.equalsIgnoreCase("")
							|| RECEIVECHECKBY.equalsIgnoreCase("")) {
						throw new MroException("transferline",
								"norecrivemessage");
					}
				}

				// 逻辑来自ZxTransferlineStatusDataBean.execute()--SVN:2425
				String ISSUESTOREROOM_parent = transferLine
						.getJpoSet("ISSUESTOREROOM").getJpo()
						.getString("STOREROOMPARENT");// 发出库房父级
				String RECEIVESTOREROOM_parent = transferLine
						.getJpoSet("RECEIVESTOREROOM").getJpo()
						.getString("STOREROOMPARENT");// 接收库房父级
				String ERPLOC = transferLine.getJpoSet("ISSUESTOREROOM")
						.getJpo().getString("erploc");

				double statuswjsqty = transferLine.getDouble("wjsqty");
				double YJSQTY = transferLine.getDouble("YJSQTY");
				double newyjsqty = YJSQTY + receiveCount;
				if (receiveCount > statuswjsqty) {
					throw new MroException("transferline", "qty");
				} else if (receiveCount == 0) {
					throw new MroException("transferline", "zerio");
				} else {
					String transferlineid = transferLine
							.getString("transferlineid");
					if (ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1020)
							|| ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1030)) {
						if (ISSUESTOREROOM_parent
								.equalsIgnoreCase(RECEIVESTOREROOM_parent)) {
							transferLine.setValue("inbinnum", inbinnum,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							TransLineStoroomCommon.out_storoom(transferJpo,
									receiveCount);
							TransLineInvtranscommon.out_invtrans(transferJpo,
									receiveCount);
							TransLineStoroomCommon.in_storoom(transferJpo,
									receiveCount);
							TransLineInvtranscommon.in_invtrans(transferJpo,
									receiveCount);
							transferLine.setValue("YJSQTY", newyjsqty,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							if (statuswjsqty == receiveCount) {
								transferLine
										.setValue(
												"status",
												"已接收",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								IJpoSet templineset = MroServer.getMroServer()
										.getJpoSet(
												"transferline",
												MroServer.getMroServer()
														.getSystemUserServer());
								templineset
										.setQueryWhere("transfernum='"
												+ transfernum
												+ "' and status!='已接收' and transferlineid!='"
												+ transferlineid + "'");
								if (templineset.isEmpty()) {
									transferLine
											.getParent()
											.setValue(
													"status",
													"已接收",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							} else {
								transferLine
										.setValue(
												"status",
												"部分接收",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						} else {
							String retu = IfZxTransfer.MroToErp(transferLine,
									String.valueOf(receiveCount), loginid);// 接口方法中返回的retu的值；
							if (retu.equalsIgnoreCase("S")) {
								transferLine
										.setValue(
												"inbinnum",
												inbinnum,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								TransLineStoroomCommon.out_storoom(transferJpo,
										receiveCount);
								TransLineInvtranscommon.out_invtrans(
										transferJpo, receiveCount);
								TransLineStoroomCommon.in_storoom(transferJpo,
										receiveCount);
								TransLineInvtranscommon.in_invtrans(
										transferJpo, receiveCount);
								transferLine
										.setValue(
												"YJSQTY",
												newyjsqty,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								if (statuswjsqty == receiveCount) {
									transferLine
											.setValue(
													"status",
													"已接收",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									IJpoSet templineset = MroServer
											.getMroServer()
											.getJpoSet(
													"transferline",
													MroServer
															.getMroServer()
															.getSystemUserServer());
									templineset
											.setQueryWhere("transfernum='"
													+ transfernum
													+ "' and status!='已接收' and transferlineid!='"
													+ transferlineid + "'");
									if (templineset.isEmpty()) {
										transferLine
												.getParent()
												.setValue(
														"status",
														"已接收",
														GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								} else {
									transferLine
											.setValue(
													"status",
													"部分接收",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							} else {
								throw new MroException("错误", "装箱单接口错误:" + retu);
							}
						}
					} else {
						transferLine.setValue("inbinnum", inbinnum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						TransLineStoroomCommon.out_storoom(transferJpo,
								receiveCount);
						TransLineInvtranscommon.out_invtrans(transferJpo,
								receiveCount);
						TransLineStoroomCommon.in_storoom(transferJpo,
								receiveCount);
						TransLineInvtranscommon.in_invtrans(transferJpo,
								receiveCount);
						transferLine.setValue("YJSQTY", newyjsqty,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						if (statuswjsqty == receiveCount) {
							transferLine.setValue("status", "已接收",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							IJpoSet templineset = MroServer.getMroServer()
									.getJpoSet(
											"transferline",
											MroServer.getMroServer()
													.getSystemUserServer());
							templineset
									.setQueryWhere("transfernum='"
											+ transfernum
											+ "' and status!='已接收' and transferlineid!='"
											+ transferlineid + "'");
							if (templineset.isEmpty()) {
								transferLine
										.getParent()
										.setValue(
												"status",
												"已接收",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						} else {
							transferLine.setValue("status", "部分接收",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					}
					IfZxTransfer.ADDENDPAYQTY(transferLine);
					TransferlinetradeCommon.add_trade(transferLine,
							receiveCount);
					/* 逻辑copy完成 */

					JSONObject lineObj = new JSONObject();
					lineObj.put("yjsQty", String.valueOf(newyjsqty));
					transferLine.getField("WJSQTY").init();
					lineObj.put("wjsQty",
							String.valueOf(transferLine.getDouble("WJSQTY")));
					lineObj.put("status", transferLine.getString("status"));
					lineObj.put("inBinNum", inbinnum);
					JSONObject transferObj = new JSONObject();
					transferObj.put("status", transferJpo.getString("status"));

					ret.put("transfer", lineObj);
					ret.put("transferLine", transferObj);
				}
				transferSet.save();
			} else {
				throw new MroException("transferline", "endbyreceive");
			}
		} else {
			throw new MroException("", "无法根据action:" + data.getString("action")
					+ "获取对应服务响应");
		}
		result.setData(ret);
		return result;
	}

	public MobileResult SXTRANSFER(String json) throws MroException {
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("transfer");
		String loginid = obj.getString("userId");
		// 将改变的数据返回前端
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 送修单编号
		String transfernum = data.getString("transferNum");
		if (transfernum == null || transfernum.isEmpty()) {
			throw new MroException("必填字段：送修单编号 为空，请检查");
		}

		// 根据送修单编号transfernum获取相关字段数据
		IJpoSet transferSet = MroServer.getMroServer().getJpoSet("TRANSFER",
				MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setPersonId(loginid);
		transferSet.setQueryWhere("transfernum='" + transfernum
				+ "' and type='SX'");
		transferSet.setAppName("SXTRANSFER");
		transferSet.reset();
		if (transferSet.count() < 1) {
			throw new MroException("无法根据编号:" + transfernum + "获取送修单");
		}
		IJpo transferJpo = transferSet.getJpo();
		String CREATEBY = transferJpo.getString("CREATEBY");// 制单人
		String status = transferJpo.getString("status");

		// SxTransferAppBean
		if ("ISSUE".equals(data.getString("action"))) {
			if (loginid.equalsIgnoreCase(CREATEBY)) {
				if (status.equalsIgnoreCase("未处理")) {
					IJpoSet transferlineset = transferJpo
							.getJpoSet("transferline");
					if (transferlineset.count() == 0) {
						throw new MroException("transferline", "statusissue");
					} else {
						// TransStoroomCommon.out_storoom(transferlineset);//
						// 调用公共方法物料出库
						// TransInvtranscommon.out_invtrans(transferlineset);//
						// 调用公共方法物料出库库存交易记录
						IfZxTransfer.UPDATESQNSTATUS(transferlineset);
						CommonAddNewInventory.addinventory(transferlineset);
						sxTransferUpdateSqnStatus(transferlineset);
						Commondeleteotherqty.sxdeleteqty(transferlineset);
						transferJpo.setValue("status", "在途");
						ret.put("status", "在途");
						transferSet.save();
					}
				} else {
					throw new MroException("transferline", "noissue");
				}
			} else {
				throw new MroException("transferline", "ISSUE");
			}
		} else {
			throw new MroException("", "无法根据action:" + data.getString("action")
					+ "获取对应服务响应");
		}
		result.setData(ret);
		return result;
	}

	public MobileResult CONVERTLOC(String json) throws MroException {
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		String loginid = obj.getString("userId");
		JSONObject data = obj.getJSONObject("convertLoc");
		// 将改变的数据返回前端
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		// 转库单编号
		String convertLocNum = data.getString("convertLocNum");
		if (convertLocNum == null || convertLocNum.isEmpty()) {
			throw new MroException("必填字段：调拨转库单编号 为空，请检查");
		}

		// 根据转库单编号convertLocNum获取相关字段数据
		IJpoSet convertLocSet = MroServer.getMroServer().getJpoSet(
				"CONVERTLOC", MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		convertLocSet.setQueryWhere("convertLocNum='" + convertLocNum + "'");
		convertLocSet.reset();
		if (convertLocSet.count() < 1) {
			throw new MroException("无法根据编号:" + convertLocNum + "获取调拨转库单");
		}
		IJpo convertJpo = convertLocSet.getJpo();

		if ("RECEIVELINE".equals(data.getString("action"))) {
			String convertLocLineId = data.getString("convertLocLineId");
			// 接收数量
			Double receiveCount = Double.parseDouble(data
					.getString("receiveCount"));
			IJpoSet convertLineSet = convertJpo.getJpoSet("CONVERTLOCNUMIX");
			convertLineSet.setQueryWhere("convertLocLineId='"
					+ convertLocLineId + "'");
			convertLineSet.reset();
			if (convertLineSet.isEmpty()) {
				throw new MroException("无法根据ID：" + convertLocLineId
						+ "获取调拨转库单:" + convertLocNum + "的单行数据");
			}
			IJpo convertLine = convertLineSet.getJpo();

			String itemnum = convertLine.getString("itemnum");
			String status = convertLine.getString("status");
			String type = ItemUtil.getItemInfo(itemnum);
			boolean islocbin = convertLine.getJpoSet("LOCATIONIX").getJpo()
					.getBoolean("islocbin");

			JSONObject lineObj = new JSONObject();

			// ConvertloclineDatabean判断逻辑
			if (status.equalsIgnoreCase("已接收")) {
				throw new MroException("transferline", "receive");
			} else {
				// ConvertloclineStatusDataBean中填写校验逻辑
				// 仓位管理
				if (islocbin) {
					if (data.containsKey("binNum")
							&& !data.getString("binNum").isEmpty()) {
						if (ItemUtil.SQN_ITEM.equals(type)) {
							if (!data.containsKey("sqn")
									|| data.getString("sqn").isEmpty()) {
								throw new MroException("该物料为序列号追溯件,请填写序列号！");
							} else if (data.containsKey("lotNum")
									&& !data.getString("lotNum").isEmpty()) {
								throw new MroException("该物料为序列号追溯件,请勿填写批次号！");
							} else {
								lineObj = IfConvertLoc.receiveConvertLine(
										convertLine, data.getString("sqn"), "",
										data.getString("binNum"), receiveCount,
										loginid);
							}
						} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
							if (!data.containsKey("lotNum")
									|| data.getString("lotNum").isEmpty()) {
								throw new MroException("该物料为I类件,请填写批次号！");
							} else if (data.containsKey("sqn")
									&& !data.getString("sqn").isEmpty()) {
								throw new MroException("该物料为I类件,请勿填写序列号！");
							} else {
								lineObj = IfConvertLoc.receiveConvertLine(
										convertLine, "",
										data.getString("lotNum"),
										data.getString("binNum"), receiveCount,
										loginid);
							}
						} else {
							if (data.containsKey("lotNum")
									&& !data.getString("lotNum").isEmpty()) {
								throw new MroException("该物料为II类件,请勿填写批次号！");
							} else if (data.containsKey("sqn")
									&& !data.getString("sqn").isEmpty()) {
								throw new MroException("该物料为II类件,请勿填写序列号！");
							} else {
								lineObj = IfConvertLoc.receiveConvertLine(
										convertLine, "", "",
										data.getString("binNum"), receiveCount,
										loginid);
							}
						}
					} else {
						throw new MroException("该库房为仓位管理库房，请填写仓位！");
					}
				} else {
					// 非仓位管理
					if (data.containsKey("binNum")
							&& !data.getString("binNum").isEmpty()) {
						throw new MroException("该库房为非仓位管理库房，请勿填写仓位！");
					} else {
						if (ItemUtil.SQN_ITEM.equals(type)) {
							if (!data.containsKey("sqn")
									|| data.getString("sqn").isEmpty()) {
								throw new MroException("该物料为序列号追溯件,请填写序列号！");
							} else if (data.containsKey("lotNum")
									&& !data.getString("lotNum").isEmpty()) {
								throw new MroException("该物料为序列号追溯件,请勿填写批次号！");
							} else {
								lineObj = IfConvertLoc.receiveConvertLine(
										convertLine, data.getString("sqn"), "",
										"", receiveCount, loginid);
							}
						} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
							if (!data.containsKey("lotNum")
									|| data.getString("lotNum").isEmpty()) {
								throw new MroException("该物料为I类件,请填写批次号！");
							} else if (data.containsKey("sqn")
									&& !data.getString("sqn").isEmpty()) {
								throw new MroException("该物料为I类件,请勿填写序列号！");
							} else {
								lineObj = IfConvertLoc.receiveConvertLine(
										convertLine, "",
										data.getString("lotNum"), "",
										receiveCount, loginid);
							}
						} else {
							if (data.containsKey("lotNum")
									&& !data.getString("lotNum").isEmpty()) {
								throw new MroException("该物料为II类件,请勿填写批次号！");
							} else if (data.containsKey("sqn")
									&& !data.getString("sqn").isEmpty()) {
								throw new MroException("该物料为II类件,请勿填写序列号！");
							} else {
								lineObj = IfConvertLoc.receiveConvertLine(
										convertLine, "", "", "", receiveCount,
										loginid);
							}
						}
					}
				}
			}
			ret.put("convertLocLine", lineObj);
			convertLineSet.save();
		} else {
			throw new MroException("", "无法根据action:" + data.getString("action")
					+ "获取对应服务响应");
		}
		result.setData(ret);
		return result;
	}

	/**
	 * 
	 * 工单挂起
	 * 
	 * @param order
	 * @param uersId
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private void holdOrder(IJpo order, String uersId) throws MroException {
		/* 挂起记录表添加数据 */
		IJpoSet holdrecordset = MroServer.getMroServer().getJpoSet(
				"HOLDRECORD", MroServer.getMroServer().getSystemUserServer());
		IJpo holdrecord = holdrecordset.addJpo();
		holdrecord.setValue("HOLDPERSON", uersId);
		holdrecord.setValue("STARTTIME", MroServer.getMroServer().getDate());
		holdrecord.setValue("HOLDREASON", "移动端挂起");
		holdrecord.setValue("FAILUREORDERNUM", order.getString("ORDERNUM"));
		holdrecord.setValue("SITEID", order.getString("SITEID"));
		holdrecord.setValue("ORGID", order.getString("ORGID"));
		order.setValue("STATUS", "挂起");

		holdrecordset.save();
	}

	/**
	 * 
	 * 解除工单挂起
	 * 
	 * @param order
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private void unHoldOrder(IJpo order) throws MroException {
		// 获取挂起记录表
		IJpoSet hrSet = order.getJpoSet("HOLDRECORD");
		// 取结束时间未填的
		hrSet.setUserWhere("endtime is null");
		hrSet.reset();

		IJpo hr = hrSet.getJpo(0);
		// 设置结束时间为当前时间
		hr.setValue("endtime", MroServer.getMroServer().getDate());

		// 设置改造工单状态为 处理中
		order.setValue("status", "处理中");

	}

	private static void sxTransferUpdateSqnStatus(IJpoSet transferlineset)
			throws MroException {
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
}
