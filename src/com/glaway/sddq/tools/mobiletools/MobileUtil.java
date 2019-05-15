package com.glaway.sddq.tools.mobiletools;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.axis.utils.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.glaway.mro.app.system.workflow.util.WFConstant;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.material.transfer.data.TransferSet;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * <功能描述> 移动app工具类
 * 
 * @author 20167088
 * @version [版本号, 2018-7-7]
 * @since [产品/模块版本]
 */
public class MobileUtil {
	/**
	 * 
	 * <功能描述> 根据安全组返回其对应的appid
	 * 
	 * @param groupname
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static JSONObject backappid(String personid) throws MroException {
		Map<String, Object> authMap = new LinkedHashMap<String, Object>();
		Set<String> fwlist = new TreeSet<String>();
		Set<String> jxlist = new TreeSet<String>();
		Set<String> wzlist = new TreeSet<String>();
		Set<String> jclist = new TreeSet<String>();
		String[] fwApp = { "SERVORDER", "FAILUREORD", "TRANSORDER", "VALIORDER" };
		String[] jxApp = { "JXTASKORDER", "JCTASKORDER" };
		String[] wzApp = { "MATERREQ", "INVENTORYMGR", "STOREROOM",
				"ZXTRANSFER", "SXTRANSFER", "CONVERTLOC" };
		String[] jcApp = { "PROJECTINFO", "MODEL", "CUSTINFOMG", "ZCCONFIG",
				"PERSONINFO" };

		IJpoSet mobileauzSet = MroServer.getMroServer().getSysJpoSet(
				"mobileauz");
		mobileauzSet
				.setQueryWhere("groupname in (select groupname from SYS_GROUPUSER where userid = '"
						+ personid.toUpperCase() + "') and type='微信小程序'");
		mobileauzSet.reset();
		if (!mobileauzSet.isEmpty()) {
			for (int i = 0; i < mobileauzSet.count(); i++) {
				IJpo mobileauz = mobileauzSet.getJpo(i);
				String appid = mobileauz.getString("appid");
				// 应用分组
				if (StringUtil.isHaveStr(fwApp, appid)) {
					fwlist.add(appid);
				} else if (StringUtil.isHaveStr(jxApp, appid)) {
					jxlist.add(appid);
				} else if (StringUtil.isHaveStr(wzApp, appid)) {
					wzlist.add(appid);
				} else if (StringUtil.isHaveStr(jcApp, appid)) {
					jclist.add(appid);
				}

			}
			authMap.put("FW", fwlist);
			authMap.put("JX", jxlist);
			authMap.put("WZ", wzlist);
			authMap.put("JC", jclist);
		} else {
			throw new MroException("", "用户ID错误！");
		}
		return new JSONObject(authMap);
		// return array.toString();
	}

	/**
	 * 
	 * 添加服务工单
	 * 
	 * @param loginid
	 * @param json
	 * @throws ParseException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public MobileResult ADDSERVORDER(String loginid, String json)
			throws MroException {
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("servOrder");
		// 工单类型
		String type = "服务";

		// 服务工单类型
		String servordertype = data.getString("servOrderType");
		if (servordertype == null || StringUtil.isStrEmpty(servordertype)) {
			throw new MroException("必填字段:服务工单类型 为空，请检查！");
		}
		// 车号
		String carnum = data.containsKey("carNum") ? data.getString("carNum")
				: "";
		// 车型
		String models = data.containsKey("cmodel") ? data.getString("cmodel")
				: "";
		if ("货物点收".equals(servordertype)) {
			if (StringUtil.isStrNotEmpty(carnum)
					|| (StringUtil.isStrNotEmpty(models))) {
				throw new MroException("当前工单类型不可填写车型车号！");
			}
		} else if ("调试交车".equals(servordertype)) {
			if (StringUtil.isStrEmpty(carnum)) {
				throw new MroException("当前工单类型必填字段:车号 为空，请检查！");
			}
			if (StringUtil.isStrEmpty(models)) {
				throw new MroException("当前工单类型必填字段:车型 为空，请检查！");
			}
		}
		// 来电时间
		String calltime = data.getString("callTime");
		// 项目编号
		String projectNum = data.getString("projectNum");
		if (projectNum == null || StringUtil.isStrEmpty(projectNum)) {
			throw new MroException("必填字段:项目编号 为空，请检查！");
		}
		// 服务单位
		String servcompany = data.getString("servCompany");
		if (servcompany == null || StringUtil.isStrEmpty(servcompany)) {
			throw new MroException("必填字段:服务单位 为空，请检查！");
		}
		// 服务单位联系人
		String servcomcontactor = data.getString("servComContactor");
		// 服务工程师
		String servEngineer = data.getString("servEngineer");
		// 安全管理要求
		String managementreq = data.getString("managementReq");
		if (managementreq == null || StringUtil.isStrEmpty(managementreq)) {
			throw new MroException("必填字段:安全管理要求 为空，请检查！");
		}
		// 派工理由
		String dispatchreason = data.getString("dispatchReason");
		if (dispatchreason == null || StringUtil.isStrEmpty(dispatchreason)) {
			throw new MroException("必填字段:派工理由 为空，请检查！");
		}

		// 配置主表获取相关字段数据
		IJpoSet workorderSet = MroServer.getMroServer().getJpoSet("workorder",
				MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setLoginID(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setUserName(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		// 设置应用名称
		workorderSet.setAppName("SERVORDER");

		IJpo workorder = workorderSet.addJpo();
		workorder.getUserInfo().setLoginID(loginid);
		workorder.setValue("ISMOBILECREATE", 1);
		workorder.setValue("type", type);
		if (StringUtil.isStrNotEmpty(servordertype)) {
			workorder.setValue("servordertype", servordertype);
		}
		if (StringUtil.isStrNotEmpty(carnum)
				&& StringUtil.isStrNotEmpty(models)) {
			workorder.setValue("carnum", carnum);
			workorder.setValue("models", models);

		}
		// 车型车号带入的数据
		IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
				"ASSET",
				"carno='" + workorder.getString("carnum") + "' and cmodel='"
						+ workorder.getString("models") + "'");
		if (workorder.getField("carnum").isValueChanged()
				&& !assetSet.isEmpty()) {
			workorder.setValue("ASSETNUM",
					assetSet.getJpo().getString("ASSETNUM"));
			workorder.setValue("PROJECTNUM",
					assetSet.getJpo().getString("PROJECTNUM"));
			workorder.setValue("REPAIRPROCESS",
					assetSet.getJpo().getString("REPAIRPROCESS"));
			workorder.setValue("OWNERCUSTOMER",
					assetSet.getJpo().getString("OWNERCUSTOMER"));
			workorder.setValue("MODELPROJECT",
					workorder.getString("MODELS.MODELCODE"));
		}

		workorder.setValue("calltime", calltime);
		workorder.setValue("projectNum", projectNum);
		workorder.setValue("servcompany", servcompany);
		workorder.setValue("servcomcontactor", servcomcontactor);
		workorder.setValue("servengineer", servEngineer);
		workorder.setValue("managementreq", managementreq);
		workorder.setValue("dispatchreason", dispatchreason);

		workorder.setValue("reporter", loginid);
		workorder.setValue("creater", loginid);

		if (data.containsKey("attachments")) {
			JSONArray attachemnts = data.getJSONArray("attachments");
			for (Object atta : attachemnts) {
				saveAttachment(workorder, atta.toString(), loginid);
			}
		}

		ret.put("type", type);
		ret.put("servOrderType", servordertype);
		ret.put("carNum", carnum);
		ret.put("cmodel", models);
		ret.put("callTime", calltime);
		ret.put("projectNum", projectNum);
		ret.put("servCompany", servcompany);
		ret.put("servComContactor", servcomcontactor);
		ret.put("servEngineer", servEngineer);
		ret.put("managementReq", managementreq);
		ret.put("dispatchReason", dispatchreason);
		ret.put("assetNum", workorder.getString("ASSETNUM"));
		ret.put("projectNum", workorder.getString("PROJECTNUM"));
		ret.put("repairProcess", workorder.getString("REPAIRPROCESS"));
		ret.put("ownerCustomer", workorder.getString("OWNERCUSTOMER"));
		ret.put("modelProject", workorder.getString("MODELPROJECT"));
		ret.put("reporter", loginid);
		ret.put("creater", loginid);

		String orderNum = workorder.getString("orderNum");
		workorderSet.save();
		ret.put("orderNum", orderNum);
		result.setData(ret);
		return result;
	}

	/**
	 * 
	 * 编辑服务工单
	 * 
	 * @param jsonData
	 * @return
	 * @throws MroException
	 * 
	 */
	public MobileResult UPDATESERVORDER(String loginid, String jsonData)
			throws MroException {
		MobileResult result = new MobileResult();
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(jsonData);
		JSONObject data = obj.getJSONObject("servOrder");
		JSONObject ret = new JSONObject();
		// 工单编号
		if (!data.containsKey("orderNum")) {
			throw new MroException("必填字段:工单编号 为空，请检查！");
		}
		String ordernum = data.getString("orderNum");

		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setLoginID(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setUserName(loginid);
		IJpoSet workorderSet = MroServer.getMroServer().getSysJpoSet(
				"WORKORDER");

		// 设置应用名称
		workorderSet.setAppName("SERVORDER");
		workorderSet.setQueryWhere("ordernum='" + ordernum + "'");
		workorderSet.reset();
		if (workorderSet.count() > 0) {
			IJpo workorder = workorderSet.getJpo();
			String status = workorder.getString("status");
			// 草稿
			if (SddqConstant.WO_STATUS_CG.equals(status)) {
				if (data.containsKey("servOrderType")) {
					workorder.setValue("servordertype",
							data.getString("servOrderType"));
					ret.put("servOrderType",
							workorder.getString("servOrderType"));
				}

				if (!"货物点收".equals(workorder.getString("servOrderType"))) {
					if (data.containsKey("carNum")) {
						workorder.setValue("carnum", data.getString("carNum"));
						ret.put("carNum", workorder.getString("carNum"));
					}
					if (data.containsKey("cmodel")) {
						workorder.setValue("models", data.getString("cmodel"));
						ret.put("cmodel", workorder.getString("models"));
					}
				}

				IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
						"ASSET",
						"carno='" + workorder.getString("carnum")
								+ "' and cmodel='"
								+ workorder.getString("models") + "'");
				if (workorder.getField("carnum").isValueChanged()
						&& !assetSet.isEmpty()) {
					workorder.setValue("ASSETNUM",
							assetSet.getJpo().getString("ASSETNUM"));
					workorder.setValue("PROJECTNUM", assetSet.getJpo()
							.getString("PROJECTNUM"));
					workorder.setValue("REPAIRPROCESS", assetSet.getJpo()
							.getString("REPAIRPROCESS"));
					workorder.setValue("OWNERCUSTOMER", assetSet.getJpo()
							.getString("OWNERCUSTOMER"));
					workorder.setValue("MODELPROJECT",
							workorder.getString("MODELS.MODELCODE"));
					ret.put("assetNum", workorder.getString("ASSETNUM"));
					ret.put("projectNum", workorder.getString("PROJECTNUM"));
					ret.put("repairProcess",
							workorder.getString("REPAIRPROCESS"));
					ret.put("ownerCustomer",
							workorder.getString("OWNERCUSTOMER"));
					ret.put("modelProject", workorder.getString("MODELPROJECT"));
				}

				if (data.containsKey("callTime")) {
					workorder.setValue("calltime", data.getString("callTime"));
					ret.put("callTime", workorder.getString("callTime"));
				}
				if (data.containsKey("projectNum")) {
					workorder.setValue("projectNum",
							data.getString("projectNum"));
					ret.put("projectNum", workorder.getString("projectNum"));
				}
				if (data.containsKey("servCompany")) {
					workorder.setValue("servcompany",
							data.getString("servCompany"));
					ret.put("servCompany", workorder.getString("servCompany"));
				}
				if (data.containsKey("servComContactor")) {
					workorder.setValue("servcomcontactor",
							data.getString("servComContactor"));
					ret.put("servComContactor",
							workorder.getString("servComContactor"));
				}
				if (data.containsKey("servEngineer")) {
					workorder.setValue("servEngineer",
							data.getString("servEngineer"));
					ret.put("servEngineer", workorder.getString("servEngineer"));
				}
				if (data.containsKey("managementReq")) {
					workorder.setValue("managementreq",
							data.getString("managementReq"));
					ret.put("managementReq",
							workorder.getString("managementReq"));
				}
				if (data.containsKey("dispatchReason")) {
					workorder.setValue("dispatchreason",
							data.getString("dispatchReason"));
					ret.put("dispatchReason",
							workorder.getString("dispatchReason"));
				}
			} else if (SddqConstant.WO_STATUS_CXPG.equals(status)) {
				if (StringUtil.isStrEmpty(hasTaskAuth(workorder, "SERVORDER",
						loginid))) {
					throw new MroException("当前用户无法修改此工单");
				}
				// 重新派工
				if (data.containsKey("servEngineer")) {
					workorder.setValue("servEngineer",
							data.getString("servEngineer"));
					ret.put("servEngineer", workorder.getString("servEngineer"));
				}
				if (data.containsKey("dispatchReason")) {
					workorder.setValue("dispatchreason",
							data.getString("dispatchReason"));
					ret.put("dispatchReason",
							workorder.getString("dispatchReason"));
				}
			} else if (SddqConstant.WO_STATUS_CLZ.equals(status)) {
				if (StringUtil.isStrEmpty(hasTaskAuth(workorder, "SERVORDER",
						loginid))) {
					throw new MroException("当前用户无法修改此工单");
				}
				// 处理中
				if (data.containsKey("carNum")) {
					workorder.setValue("carnum", data.getString("carNum"),
							GWConstant.P_NOVALIDATION);
					ret.put("carNum", workorder.getString("carnum"));
				}
				if (data.containsKey("cmodel")) {
					workorder.setValue("models", data.getString("cmodel"),
							GWConstant.P_NOVALIDATION);
					ret.put("cmodel", workorder.getString("models"));
				}

				// 车型改变车型项目
				if (workorder.getField("models").isValueChanged()) {
					workorder.setValue("MODELPROJECT",
							workorder.getString("MODELS.MODELCODE"),
							GWConstant.P_NOVALIDATION);
					ret.put("modelProject", workorder.getString("modelProject"));
				}
				// 车号修改
				IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
						"ASSET",
						"carno='" + workorder.getString("carnum")
								+ "' and cmodel='"
								+ workorder.getString("models") + "'");
				if (workorder.getField("carnum").isValueChanged()
						&& !assetSet.isEmpty()) {
					workorder.setValue("ASSETNUM",
							assetSet.getJpo().getString("ASSETNUM"));
					workorder.setValue("PROJECTNUM", assetSet.getJpo()
							.getString("PROJECTNUM"));
					workorder.setValue("REPAIRPROCESS", assetSet.getJpo()
							.getString("REPAIRPROCESS"));
					workorder.setValue("OWNERCUSTOMER", assetSet.getJpo()
							.getString("OWNERCUSTOMER"));
					workorder.setValue("MODELPROJECT",
							workorder.getString("MODELS.MODELCODE"));
					ret.put("assetNum", workorder.getString("ASSETNUM"));
					ret.put("projectNum", workorder.getString("PROJECTNUM"));
					ret.put("repairProcess",
							workorder.getString("REPAIRPROCESS"));
					ret.put("ownerCustomer",
							workorder.getString("OWNERCUSTOMER"));
					ret.put("modelProject", workorder.getString("MODELPROJECT"));
				}

				if (data.containsKey("remark")) {
					workorder.setValue("remark", data.getString("remark"),
							GWConstant.P_NOVALIDATION);
					ret.put("remark", workorder.getString("remark"));
				}

				// 到达现场和开始作业不为空时
				if (workorder.getDate("ARRIVETIME") != null
						&& workorder.getDate("OPERATETIME") != null) {

					// 工作汇报必填
					if (workorder.getString("remark") == null
							|| StringUtil.isStrEmpty(workorder
									.getString("remark"))) {
						throw new MroException("必填字段:工作汇报 为空，请检查！");
					}

				}
			} else if (SddqConstant.WO_STATUS_SHBH.equals(status)) {
				if (StringUtil.isStrEmpty(hasTaskAuth(workorder, "SERVORDER",
						loginid))) {
					throw new MroException("当前用户无法修改此工单");
				}
				// 审核驳回
				if (data.containsKey("remark")) {
					workorder.setValue("remark", data.getString("remark"),
							GWConstant.P_NOVALIDATION);
					ret.put("remark", workorder.getString("remark"));
				}
				// 工作汇报必填
				if (workorder.getString("remark") == null
						|| StringUtil.isStrEmpty(workorder.getString("remark"))) {
					throw new MroException("必填字段:工作汇报 为空，请检查！");
				}
			} else {
				throw new MroException("服务工单当前状态无法编辑");
			}

			// 必填
			if (workorder.getString("servordertype") == null
					|| StringUtil.isStrEmpty(workorder
							.getString("servordertype"))) {
				throw new MroException("必填字段:服务工单类型 为空，请检查！");
			}
			if (SddqConstant.SO_TYPE_HWDC.equals(workorder
					.getString("servordertype"))) {
				if (!StringUtil.isStrEmpty(workorder.getString("carnum"))
						|| !StringUtil
								.isStrEmpty(workorder.getString("models"))) {
					throw new MroException("当前工单类型不可填写车型车号！");
				}
			} else if (SddqConstant.SO_TYPE_TSJC.equals(workorder
					.getString("servordertype"))) {
				if (StringUtil.isStrEmpty(workorder.getString("carnum"))) {
					throw new MroException("当前工单类型必填字段:车号 为空，请检查！");
				}
				if (StringUtil.isStrEmpty(workorder.getString("models"))) {
					throw new MroException("当前工单类型必填字段:车型 为空，请检查！");
				}
			}
			if (workorder.getString("projectnum") == null
					|| StringUtil.isStrEmpty(workorder.getString("projectnum"))) {
				throw new MroException("必填字段:项目编号 为空，请检查！");
			}
			if (workorder.getString("servcompany") == null
					|| StringUtil
							.isStrEmpty(workorder.getString("servcompany"))) {
				throw new MroException("必填字段:服务单位 为空，请检查！");
			}
			if (workorder.getString("managementreq") == null
					|| StringUtil.isStrEmpty(workorder
							.getString("managementreq"))) {
				throw new MroException("必填字段:安全管理要求 为空，请检查！");
			}
			if (workorder.getString("dispatchreason") == null
					|| StringUtil.isStrEmpty(workorder
							.getString("dispatchreason"))) {
				throw new MroException("必填字段:派工理由 为空，请检查！");
			}

			if (data.containsKey("attachments")) {
				JSONArray attachemnts = data.getJSONArray("attachments");
				for (Object atta : attachemnts) {
					saveAttachment(workorder, atta.toString(), loginid);
				}
			}

			result.setData(ret);
			workorderSet.save();
		} else {
			throw new MroException("工单:" + ordernum + "不存在！修改失败!");
		}
		return result;
	}

	/**
	 * 
	 * 启动工作流
	 * 
	 * @param jpo
	 *            当前操作jpo
	 * @param appName
	 *            应用名称
	 * @param loginId
	 *            当前登录人
	 * @param actionId
	 *            工作流审批意见
	 * @param memo
	 *            工作流审批备忘
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public static MobileResult startWF(IJpo jpo, String appName,
			String loginId, String actionId, String memo) {

		MobileResult rslt = new MobileResult();
		try {
			// 改造工单特殊处理
			if ("TRANSORDER".equals(appName)) {
				transOrderWf(jpo, loginId);
			} else if ("FAILUREORD".equals(appName)) {
				failureOrdWf(jpo, loginId);
			} else if ("MATERREQ".equals(appName)) {
				materReqWf(jpo, loginId);
			} else if ("ZXTRANSFER".equals(appName)) {
				zxTransferWf(jpo, loginId);
			}

			if (hasStartWf(jpo, appName)) {// 判断工作流是否已经启动
				String taskId = hasTaskAuth(jpo, appName, loginId);
				if (StringUtil.isStrNotEmpty(taskId)) {
					// 发送工作流
					MobileResult rs2 = routeWf(jpo, appName, loginId, taskId,
							actionId, memo);
					rslt.setMsg(rs2.getMsg());
					rslt.setCode(rs2.getCode());
				} else {
					rslt.setCode("400");
					rslt.setMsg("操作失败，当前流程中该用户没有任务。");
					return rslt;
				}
			} else {// 未启动工作流
				if (jpo != null) {
					long id = jpo.getId();
					String jponame = jpo.getName();

					// 获取已激活的流程版本
					IJpo wfversion = WfControlUtil.getActivitiWf(appName);
					if (wfversion == null) {
						rslt.setCode("400");
						rslt.setMsg("操作失败，没有“已激活”的流程版本，无法启动工作流。");
						return rslt;
					}
					String procDefinitionId = wfversion
							.getString("PROCDEFINITIONID");
					ProcessEngine processEngine = ProcessEngines
							.getDefaultProcessEngine();
					RuntimeService runtimeService = processEngine
							.getRuntimeService();
					IdentityService identityService = processEngine
							.getIdentityService();

					Map<String, Object> formProperties = new HashMap<String, Object>(); // 在该map中可设置自定义的字段参数
					String uidValue = String.valueOf(id);

					formProperties.put("APP", appName);
					formProperties.put("BUSINESSKEY", uidValue);
					formProperties.put("curJpo", jpo);
					// 启动工作流
					identityService.setAuthenticatedUserId("GWADMIN");
					try {
						ProcessInstance processInstance = runtimeService
								.startProcessInstanceById(procDefinitionId,
										uidValue, formProperties);
						// 添加到关系表
						WfControlUtil.addActAppInfo(appName, jponame,
								procDefinitionId, processInstance, uidValue);
						// showOperInfo("workflow", "startSuccess");
					} catch (Exception e) {
						/*
						 * if (e instanceof AppException) { throw e; } else {
						 * FixedLoggers.EXCEPTIONLOGGER.error(e); }
						 */
						rslt.setCode("400");
						rslt.setMsg(e.getMessage());
						return rslt;
					}
					rslt.setMsg("操作成功，工作流已启动！");
				} else {
					rslt.setCode("400");
					rslt.setMsg("工作流启动失败！");
				}
			}
		} catch (Exception e) {
			rslt.setCode("400");
			rslt.setMsg(e.getMessage());
		}

		return rslt;

	}

	/**
	 * 
	 * 配件申请工作流特殊处理
	 * 
	 * @param mr
	 * @param loginId
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private static void materReqWf(IJpo mr, String loginId) throws MroException {
		if (mr != null) {
			IJpoSet Mrlineset = mr.getJpoSet("mrline");
			if (Mrlineset.isEmpty()) {
				throw new MroException("mr", "nomrline");
			}
		}
	}

	/**
	 * 装箱单工作流特殊处理
	 * 
	 * @param transfer
	 * @param loginId
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private static void zxTransferWf(IJpo transfer, String loginId)
			throws MroException {
		boolean nullFlag = false;
		String status = transfer.getString("status");
		String issue = transfer.getString("issue");// 是否发出标记
		String TRANSFERMOVETYPE = transfer.getString("TRANSFERMOVETYPE");
		IJpoSet transferlineset = transfer.getJpoSet("transferline");
		if (!transferlineset.isEmpty()) {
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
	}

	/**
	 * 
	 * 故障工单工作流特殊处理
	 * 
	 * @param order
	 * @param loginId
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private static void failureOrdWf(IJpo order, String loginId)
			throws MroException {
		String status = order.getString("status");
		String dataStatus = order.getString("FAILURELIB.FAULTDATAREC");// 故障数据包状态
		String productline = order.getString("MODELS.PRODUCTLINE");// 车型大类
		String nodataReason = order.getString("FAILURELIB.NODATAREASON");// 故障数据原因

		IJpoSet losspartSet = order.getJpoSet("JXTASKLOSSPART");
		IJpoSet exchangeSet = order.getJpoSet("FAILURELIB").getJpo()
				.getJpoSet("EXCHANGERECORD");

		if (StringUtil.isNull(status) || status.equals("关闭")) {
			throw new MroException("servorder", "statusnowf");
		}

		if (SddqConstant.WO_STATUS_CLZ.equals(status)
				|| SddqConstant.WO_STATUS_KGYBH.equals(status)) {
			if (order.getDate("COMPLETETIME") == null)// 处理完成时间为空
			{
				throw new MroException("", "处理完成后才能发送工作流！");
			}

			String dealMethod = order.getJpoSet("FAILURELIB").getJpo()
					.getString("DEALMETHOD");

			if (SddqConstant.FAIL_DEALMETHOD_HJCL.equals(dealMethod)) {// 故障处理方式为换件处理

				if (exchangeSet.count(GWConstant.P_COUNT_AFTERSAVE) < 1
						&& losspartSet.count(GWConstant.P_COUNT_AFTERSAVE) < 1) {

					throw new MroException("failureord", "noexchange");// 无上下车件无法提交
				}
			} else if (SddqConstant.FAIL_DEALMETHOD_YJXF.equals(dealMethod)
					|| SddqConstant.FAIL_DEALMETHOD_FWCQ.equals(dealMethod)
					|| SddqConstant.FAIL_DEALMETHOD_GXRJ.equals(dealMethod)
					|| SddqConstant.FAIL_DEALMETHOD_RJWT.equals(dealMethod)
					|| SddqConstant.FAIL_DEALMETHOD_JKSY.equals(dealMethod)) {

				if (exchangeSet.count(GWConstant.P_COUNT_AFTERSAVE) > 0
						&& losspartSet.count(GWConstant.P_COUNT_AFTERSAVE) > 0) {

					throw new MroException("当前处理方式无需上下车，请删除记录后再进行操作！");// 无上下车件无法提交

				}

			}

			String faultconseq = order.getString("FAILURELIB.FAULTCONSEQ");
			if ((!"上传成功".equals(dataStatus))
					&& (WorkorderUtil.isImpFault(faultconseq))) {// 高级故障需要有数据包才能发生工作流
				throw new MroException("WORKORDER", "DOCLINKS2");
			}

			if ("机车".equals(productline)) {// 机车类需判断是否有关键物料
				if (WorkorderUtil.hasImpItem(exchangeSet, "ITEM")
						|| WorkorderUtil.hasImpItem(losspartSet, "DOWNITEMNUM")) {
					if (!"上传成功".equals(dataStatus)
							&& StringUtil.isStrEmpty(nodataReason)) {
						throw new MroException("WORKORDER", "DOCLINKS2");
					}
				}
			}

			if ("否".equals(order.getString("ISREADSAFEREQ"))) {// 未阅读安全管理要求不可发送工作流
				throw new MroException("failureord", "notreadsecreq");
			}

		} else if (SddqConstant.WO_STATUS_KGYBH.equals(status)
				|| SddqConstant.WO_STATUS_SHBH.equals(status)
				|| SddqConstant.WO_STATUS_JSZGBH.equals(status)) {

			String faultconseq = order.getString("FAILURELIB.FAULTCONSEQ");
			if ((!"上传成功".equals(dataStatus))
					&& (WorkorderUtil.isImpFault(faultconseq))) {// 高级故障需要有数据包才能发生工作流
				throw new MroException("WORKORDER", "DOCLINKS2");
			}

			if ("机车".equals(productline)) {// 机车类需判断是否有关键物料
				if (WorkorderUtil.hasImpItem(exchangeSet, "ITEM")
						|| WorkorderUtil.hasImpItem(losspartSet, "DOWNITEMNUM")) {
					if (!"上传成功".equals(dataStatus)
							&& StringUtil.isStrEmpty(nodataReason)) {
						throw new MroException("WORKORDER", "DOCLINKS2");
					}
				}
			}

			if ("否".equals(order.getString("ISREADSAFEREQ"))) {// 未阅读安全管理要求不可发送工作流
				throw new MroException("failureord", "notreadsecreq");
			}

			if (SddqConstant.FAIL_DEALMETHOD_HJCL.equals(order
					.getJpoSet("FAILURELIB").getJpo().getString("DEALMETHOD"))) {// 故障处理方式为换件处理

				if (exchangeSet.count(GWConstant.P_COUNT_AFTERSAVE) < 1
						&& losspartSet.count(GWConstant.P_COUNT_AFTERSAVE) < 1) {

					throw new MroException("failureord", "noexchange");// 无上下车件无法提交
				}
			}
		}

		if (SddqConstant.WO_STATUS_GQ.equals(status)) {// 挂起时无法发送工作流
			throw new MroException("", "当前状态无法发送工作流！");
		}

	}

	/**
	 * 
	 * 改造工单工作流特殊处理 loginid无法在工作流类中获取，只能在appbean中获取 移动端增加了TransorderAppBean相应逻辑
	 * 
	 * @param order
	 * @param loginId
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private static void transOrderWf(IJpo order, String loginId)
			throws MroException {
		String status = order.getString("status");
		if ("挂起".equals(status) || "草稿".equals(status)) {
			throw new MroException("service", "cannotoperate");
		}
		if (order != null) {
			IJpoSet exchangerecordset = order.getJpoSet("EXCHANGERECORD");
			if (SddqConstant.WO_STATUS_CLZ.equals(status)) {
				if (exchangerecordset.isEmpty()) {
					throw new MroException("transorder", "exchangerecord");
				}
			}
			// 设置提报人
			if (StringUtil.isStrEmpty(order.getString("reporter"))) {
				order.setValue("reporter", loginId,
						GWConstant.P_NOVALIDATION_AND_NOACTION);
			}
		}
	}

	/**
	 * 
	 * 获取关联关系-actAppInfo表
	 * 
	 * @param jpo
	 * @param appName
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * @author zhuhao
	 * 
	 */
	public static IJpo getActAppInfo(IJpo jpo, String appName)
			throws MroException {

		String uidValue = String.valueOf(jpo.getId());
		if (jpo != null) {
			IJpoSet actAppInfoSet = jpo.getJpoSet(
					"$actAppInfo",
					"ACT_APP_INFO",
					"APP = '" + StringUtil.getSafeSqlStr(appName)
							+ "' and BUSINESSKEY = '"
							+ StringUtil.getSafeSqlStr(uidValue) + "'");
			if (actAppInfoSet != null && !actAppInfoSet.isEmpty()) {
				return actAppInfoSet.getJpo(0);
			}
		}
		return null;
	}

	/**
	 * 
	 * 判断当前用户是否拥有任务
	 * 
	 * @param jpo
	 * @param appName
	 * @param loginId
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * @author zhuhao
	 * 
	 */
	public static String hasTaskAuth(IJpo jpo, String appName, String loginId)
			throws MroException {
		IJpo actAppInfo = getActAppInfo(jpo, appName);
		if (actAppInfo != null
				&& actAppInfo.getString("STATUS").equals(WFConstant.ACTIVI)) {
			ProcessEngine processEngine = ProcessEngines
					.getDefaultProcessEngine();
			TaskService taskService = processEngine.getTaskService();
			String processInstanceId = actAppInfo.getString("PROCINSTID");
			// 根据当前人的ID查询,当前用户是否有任务
			List<Task> tasks = taskService.createTaskQuery()
					.processInstanceId(processInstanceId)
					.taskAssignee(loginId.toUpperCase()).active()
					.orderByTaskId().desc().list();
			if (tasks != null && tasks.size() > 0) {
				return tasks.get(0).getId();
			} else {
				return "";
			}
		} else {
			if (actAppInfo != null) {
				if (actAppInfo.getString("STATUS").equals(WFConstant.STOP)) {
					throw new AppException("workflow", "isStop");
				}
			}
		}
		return "";
	}

	/**
	 * 
	 * 是否已启动工作流
	 * 
	 * @param jpo
	 * @param appName
	 * @return true 已启动，false 未启动
	 * 
	 */
	public static boolean hasStartWf(IJpo jpo, String appName) {

		try {
			IJpo wfjpo = getActAppInfo(jpo, appName);
			if (wfjpo == null) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * 发送工作流
	 * 
	 * @param jpo
	 *            操作jpo
	 * @param appName
	 *            应用名称
	 * @param loginId
	 *            当前登录用户id
	 * @param taskId
	 *            任务id
	 * @param actionId
	 *            审批选择
	 * @param memo
	 *            审批备忘
	 * @return [参数说明]
	 * 
	 */
	private static MobileResult routeWf(IJpo jpo, String appName,
			String loginId, String taskId, String actionId, String memo) {
		MobileResult result = new MobileResult();

		try {
			ProcessEngine processEngine = ProcessEngines
					.getDefaultProcessEngine();
			FormService formService = processEngine.getFormService();
			IdentityService identityService = processEngine
					.getIdentityService();
			Map<String, String> formProperties = new HashMap<String, String>();
			formProperties.put("memo", memo);
			formProperties.put(WFConstant.APPROVED, actionId);

			identityService.setAuthenticatedUserId(loginId);
			formService.submitTaskFormData(taskId, formProperties);

			IJpo actAppInfo = getActAppInfo(jpo, appName);
			if (actAppInfo != null) {
				String processInstanceId = actAppInfo.getString("PROCINSTID");
				WfControlUtil.updateHistoryLog(processInstanceId, taskId, memo,
						loginId);
			}

			// 工作流发送成功
			result.setMsg("工作流发送成功！");

		} catch (Exception e) {
			result.setCode("400");
			result.setMsg(e.getMessage());
		}

		return result;
	}

	/**
	 * 
	 * 添加客户联系人
	 * 
	 * @param loginid
	 * @param json
	 *            [参数说明]
	 * @throws MroException
	 * 
	 */
	public void ADDCUSTINFOMG(String loginid, String json) throws MroException {
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("customerContactor");

		String custnum = data.getString("custNum");
		if (custnum == null || custnum.isEmpty()) {
			throw new MroException("必填字段:客户编号 为空，请检查！");
		}
		String name = data.getString("name");
		String telephone = data.getString("telephone");
		String gender = data.getString("gender");
		String nationality = data.getString("nationality");
		String position = data.getString("position");
		String entrydate = data.getString("entryDate");
		String hobby = data.getString("hobby");
		String custpoint = data.getString("custPoint");
		String birthday = data.getString("birthday");
		String emailaddress = data.getString("emailAddress");
		String remark = data.getString("remark");

		// 获取客户表数据
		IJpoSet custSet = MroServer.getMroServer().getJpoSet("CUSTINFO",
				MroServer.getMroServer().getSystemUserServer());
		custSet.setQueryWhere("CUSTNUM='" + custnum + "'");
		custSet.reset();
		if (custSet.count() < 1) {
			throw new MroException("无法根据客户编号" + custnum + "找到对应客户");
		}

		// 关联关系获取联系人表
		IJpoSet customerContactorSet = custSet.getJpo().getJpoSet(
				"CUSTOMERCONTACTOR");
		IJpo customerContactor = customerContactorSet.addJpo();
		customerContactor.setValue("custnum", custnum);

		if (name != null && !name.isEmpty())
			customerContactor.setValue("name", name);
		if (telephone != null && !telephone.isEmpty())
			customerContactor.setValue("telephone", telephone);
		if (gender != null && !gender.isEmpty())
			customerContactor.setValue("gender", gender);
		if (nationality != null && !nationality.isEmpty())
			customerContactor.setValue("nationality", nationality);
		if (position != null && !position.isEmpty())
			customerContactor.setValue("position", position);
		if (entrydate != null && !entrydate.isEmpty())
			customerContactor.setValue("entryDate", entrydate);
		if (hobby != null && !hobby.isEmpty())
			customerContactor.setValue("hobby", hobby);
		if (custpoint != null && !custpoint.isEmpty())
			customerContactor.setValue("custPoint", custpoint);
		if (birthday != null && !birthday.isEmpty())
			customerContactor.setValue("birthday", birthday);
		if (emailaddress != null && !emailaddress.isEmpty())
			customerContactor.setValue("emailAddress", emailaddress);
		if (remark != null && !remark.isEmpty())
			customerContactor.setValue("remark", remark);

		if (customerContactor.getString("name") == null
				|| customerContactor.getString("name").isEmpty()) {
			throw new MroException("必填字段:姓名 为空，请检查！");
		}
		if (customerContactor.getString("telephone") == null
				|| customerContactor.getString("telephone").isEmpty()) {
			throw new MroException("必填字段:联系电话 为空，请检查！");
		}
		customerContactorSet.save();
	}

	/**
	 * 
	 * 更新客户联系人
	 * 
	 * @param loginid
	 * @param json
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public void UPDATECUSTINFOMG(String loginid, String json) throws Exception {
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("customerContactor");

		String custcontactnum = data.getString("custContactNum");
		if (custcontactnum == null || custcontactnum.isEmpty()) {
			throw new MroException("必填字段:联系人编号 为空，请检查！");
		}
		String custnum = data.getString("custNum");
		if (custnum == null || custnum.isEmpty()) {
			throw new MroException("必填字段:客户编号 为空，请检查！");
		}
		String name = data.getString("name");
		String telephone = data.getString("telephone");
		String gender = data.getString("gender");
		String nationality = data.getString("nationality");
		String position = data.getString("position");
		String entrydate = data.getString("entryDate");
		String hobby = data.getString("hobby");
		String custpoint = data.getString("custPoint");
		String birthday = data.getString("birthday");
		String emailaddress = data.getString("emailAddress");
		String remark = data.getString("remark");

		// 获取客户表数据
		IJpoSet custSet = MroServer.getMroServer().getJpoSet("CUSTINFO",
				MroServer.getMroServer().getSystemUserServer());
		custSet.setQueryWhere("CUSTNUM='" + custnum + "'");
		custSet.reset();
		if (custSet.count() < 1) {
			throw new MroException("无法根据客户编号" + custnum + "找到对应客户");
		}

		// 关联关系获取联系人表
		IJpoSet customerContactorSet = custSet.getJpo().getJpoSet(
				"CUSTOMERCONTACTOR");
		customerContactorSet.setQueryWhere("custcontactnum='" + custcontactnum
				+ "'");
		customerContactorSet.reset();
		if (customerContactorSet.count() < 1) {
			throw new MroException("无法根据人员编号" + custcontactnum + "找到对应客户联系人");
		}
		IJpo customerContactor = customerContactorSet.getJpo();

		if (name != null && !name.isEmpty())
			customerContactor.setValue("name", name);
		if (telephone != null && !telephone.isEmpty())
			customerContactor.setValue("telephone", telephone);
		if (gender != null && !gender.isEmpty())
			customerContactor.setValue("gender", gender);
		if (nationality != null && !nationality.isEmpty())
			customerContactor.setValue("nationality", nationality);
		if (position != null && !position.isEmpty())
			customerContactor.setValue("position", position);
		if (entrydate != null && !entrydate.isEmpty())
			customerContactor.setValue("entrydate", entrydate);
		if (hobby != null && !hobby.isEmpty())
			customerContactor.setValue("hobby", hobby);
		if (custpoint != null && !custpoint.isEmpty())
			customerContactor.setValue("custpoint", custpoint);
		if (birthday != null && !birthday.isEmpty())
			customerContactor.setValue("birthday", birthday);
		if (emailaddress != null && !emailaddress.isEmpty())
			customerContactor.setValue("emailaddress", emailaddress);
		if (remark != null && !remark.isEmpty())
			customerContactor.setValue("remark", remark);

		if (customerContactor.getString("name") == null
				|| customerContactor.getString("name").isEmpty()) {
			throw new MroException("必填字段:姓名 为空，请检查！");
		}
		if (customerContactor.getString("telephone") == null
				|| customerContactor.getString("telephone").isEmpty()) {
			throw new MroException("必填字段:联系电话 为空，请检查！");
		}
		customerContactorSet.save();
	}

	/**
	 * 
	 * 增加装箱单
	 * 
	 * @param loginid
	 * @param json
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public MobileResult ADDZXTRANSFER(String loginid, String json)
			throws Exception {
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		JSONObject transferRet = new JSONObject();
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json, Feature.OrderedField);
		JSONObject data = obj.getJSONObject("transfer");

		// 可编辑字段
		List<String> tmpList = Arrays.asList("transferMoveType", "memo",
				"packBy", "checkBy", "packDate", "receiveStoreRoom",
				"receiveAddress");
		LinkedList<String> attrList = new LinkedList<String>(tmpList);

		// 必填
		LinkedHashMap<String, String> requiredAttrs = new LinkedHashMap<String, String>();
		requiredAttrs.put("transferMoveType", "移动类型");
		requiredAttrs.put("packBy", "装箱人");
		requiredAttrs.put("checkBy", "核对人");
		requiredAttrs.put("packDate", "装箱日期");
		requiredAttrs.put("receiveStoreRoom", "接收库房");
		requiredAttrs.put("receiveAddress", "接收地址");

		// 移动类型
		String transferMoveType = data.getString("transferMoveType");
		if ("中心到现场".equals(transferMoveType)) {
			attrList.add("sendBy");
			attrList.add("isMr");
			attrList.add("isFxfy");
			requiredAttrs.put("sendBy", "发运人");
			requiredAttrs.put("isMr", "是否关联配件申请");
			requiredAttrs.put("isFxfy", "是否返修发运");
			if ("是".equals(data.getString("isMr"))) {
				attrList.add("mrNum");
				requiredAttrs.put("mrNum", "配件申请单号");
			}
		} else if ("中心到中心".equals(transferMoveType)) {
			attrList.add("issueStoreRoom");
			attrList.add("issueAddress");
			requiredAttrs.put("issueStoreRoom", "发出库房");
			requiredAttrs.put("issueAddress", "发出地址");
		} else if ("现场到中心".equals(transferMoveType)) {
			attrList.add("sxType");
			attrList.add("issueStoreRoom");
			attrList.add("issueAddress");
			attrList.add("sendBy");
			requiredAttrs.put("sxType", "修造类别");
			requiredAttrs.put("issueStoreRoom", "发出库房");
			requiredAttrs.put("issueAddress", "发出地址");
			requiredAttrs.put("sendBy", "发运人");
		} else {// 现场到现场
			attrList.add("issueStoreRoom");
			attrList.add("issueAddress");
			attrList.add("sendBy");
			requiredAttrs.put("issueStoreRoom", "发出库房");
			requiredAttrs.put("issueAddress", "发出地址");
			requiredAttrs.put("sendBy", "发运人");
		}

		// 配置主表获取相关字段数据
		TransferSet transferSet = (TransferSet) MroServer.getMroServer()
				.getJpoSet("TRANSFER",
						MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setLoginID(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setUserName(loginid);
		transferSet.setAppName("ZXTRANSFER");

		IJpo transfer = transferSet.addJpo();
		String transfernum = transfer.getString("transfernum");
		transfer.getUserServer().getUserInfo().setLoginID(loginid);

		// 装箱单类型description
		transfer.setValue("STATUS", "未处理");
		transfer.setValue("TYPE", "ZXD");
		transfer.setValue("CREATEDATE", MroServer.getMroServer().getDate());
		transfer.setValue("CREATEBY", loginid, GWConstant.P_NOVALIDATION);
		transferRet.put("status", "未处理");
		transferRet.put("type", "ZXD");
		transferRet.put("createDate", transfer.getString("CREATEDATE"));
		transferRet.put("createBy", loginid);

		// 赋值
		if (data.containsKey("transferMoveType")) {
			transfer.setValue("transferMoveType",
					data.getString("transferMoveType"));
			data.remove("transferMoveType");
		}
		if (data.containsKey("issueStoreRoom")) {
			transfer.setValue("issueStoreRoom",
					data.getString("issueStoreRoom"));
			data.remove("issueStoreRoom");
		}
		if (data.containsKey("sxType")) {
			transfer.setValue("sxType", data.getString("sxType"));
			data.remove("sxType");
		}
		for (Entry<String, Object> attr : data.entrySet()) {
			String changeValue = attr.getValue().toString();
			if (!transfer.getString(attr.getKey()).equals(changeValue)) {
				transfer.setValue(attr.getKey(), changeValue,
						GWConstant.P_NOVALIDATION);
				transferRet.put(attr.getKey(), changeValue);
			}
		}

		// lookup
		// 接收库房
		if (!transfer.getString("RECEIVESTOREROOM").isEmpty()) {
			transfer.setValue("RECEIVEBY",
					transfer.getString("RECEIVESTOREROOM.KEEPER"));
			transfer.setValue("RECEIVEPHONE",
					transfer.getString("RECEIVEBY.PRIMARYPHONE"));
			transferRet.put("receiveBy",
					transfer.getString("RECEIVESTOREROOM.KEEPER"));
			transferRet.put("receivePhone",
					transfer.getString("RECEIVEBY.PRIMARYPHONE"));
		}

		if (transfer.getString("TRANSFERMOVETYPE").equalsIgnoreCase("中心到中心")) {
			String LOCATIONTYPE = transfer.getJpoSet("ISSUESTOREROOM").getJpo()
					.getString("LOCATIONTYPE");
			if (LOCATIONTYPE.equalsIgnoreCase("维修")) {
				requiredAttrs.remove("sxType");
				requiredAttrs.remove("receiveStoreRoom");
				requiredAttrs.remove("receiveAddress");
				requiredAttrs.remove("receiveBy");
			}
		}
		// 必填校验
		for (String required : requiredAttrs.keySet()) {
			if (transfer.getString(required) == null
					|| transfer.getString(required).isEmpty()) {
				throw new MroException("必填字段:" + requiredAttrs.get(required)
						+ " 为空，请检查！");
			}
		}
		transfer.setValue("transfernum", transfernum);
		transferRet.put("transferNum", transfernum);
		transferSet.save();

		if (obj.containsKey("transferLine")) {
			transferSet.setQueryWhere("transfernum='" + transfernum + "'");
			transferSet.reset();
			transfer = transferSet.getJpo();
			JSONArray lines = obj.getJSONArray("transferLine");
			JSONArray retLines = new JSONArray();
			for (int i = 0; i < lines.size(); i++) {
				MobileResult lineResult = MODIFYZXTRANSFERLINE(transfer,
						loginid, lines.getString(i));
				retLines.add(lineResult.getData());
			}
			transferSet.save();
			ret.put("transferLine", retLines);
		}

		if (obj.containsKey("attachments")) {
			transferSet.setQueryWhere("transfernum='" + transfernum + "'");
			transferSet.reset();
			JSONArray attachemnts = data.getJSONArray("attachments");
			for (Object atta : attachemnts) {
				saveAttachment(transfer, atta.toString(), loginid);
			}
		}
		ret.put("transfer", transferRet);
		result.setData(ret);
		return result;
	}

	/**
	 * 
	 * 更新装箱单
	 * 
	 * @param loginid
	 * @param json
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public MobileResult UPDATEZXTRANSFER(String loginid, String json)
			throws Exception {
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		JSONObject transferRet = new JSONObject();
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json, Feature.OrderedField);
		JSONObject data = obj.getJSONObject("transfer");

		String transfernum = data.getString("transferNum");
		if (transfernum == null || transfernum.isEmpty()) {
			throw new MroException("必填字段：装箱单编号 为空，请检查");
		}

		// 根据装箱单编号transfernum获取相关字段数据
		TransferSet transferSet = (TransferSet) MroServer.getMroServer()
				.getJpoSet("TRANSFER",
						MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setLoginID(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setUserName(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		transferSet.setQueryWhere("transfernum='" + transfernum + "'");
		transferSet.reset();

		if (transferSet.count() < 1) {
			throw new MroException("无法根据装箱单编号:" + transfernum + "获取记录");
		}
		IJpo transfer = transferSet.getJpo();
		transfer.getUserServer().getUserInfo().setLoginID(loginid);

		if (!loginid.equalsIgnoreCase(transfer.getString("CREATEBY"))
				&& StringUtil.isStrEmpty(hasTaskAuth(transfer, "ZXTRANSFER",
						loginid))) {
			throw new MroException("当前用户无法修改该装箱单");
		}
		String status = transfer.getString("status");
		transferRet.put("transferNum", transfernum);
		// 移动类型
		String transferMoveType = data.containsKey("transferMoveType") ? data
				.getString("transferMoveType") : transfer
				.getString("transferMoveType");
		LinkedHashMap<String, String> requiredAttrs = new LinkedHashMap<String, String>();
		List<String> attrList = new LinkedList<String>();
		if ("未处理".equals(status)) {
			// 可编辑字段
			List<String> tmpList = Arrays.asList("transferMoveType", "memo",
					"packBy", "checkBy", "packDate", "receiveStoreRoom",
					"receiveAddress");
			attrList = new LinkedList<String>(tmpList);

			// 必填
			requiredAttrs.put("transferMoveType", "移动类型");
			requiredAttrs.put("packBy", "装箱人");
			requiredAttrs.put("checkBy", "核对人");
			requiredAttrs.put("packDate", "装箱日期");
			requiredAttrs.put("receiveStoreRoom", "接收库房");
			requiredAttrs.put("receiveAddress", "接收地址");

			if ("中心到现场".equals(transferMoveType)) {
				attrList.add("sendBy");
				attrList.add("isMr");
				attrList.add("isFxfy");
				requiredAttrs.put("sendBy", "发运人");
				requiredAttrs.put("isMr", "是否关联配件申请");
				requiredAttrs.put("isFxfy", "是否返修发运");
				if ("是".equals(data.getString("isMr"))) {
					attrList.add("mrNum");
					requiredAttrs.put("mrNum", "配件申请单号");
				}
			} else if ("中心到中心".equals(transferMoveType)) {
				attrList.add("issueStoreRoom");
				attrList.add("issueAddress");
				requiredAttrs.put("issueStoreRoom", "发出库房");
				requiredAttrs.put("issueAddress", "发出地址");
			} else if ("现场到中心".equals(transferMoveType)) {
				attrList.add("sxType");
				attrList.add("issueStoreRoom");
				attrList.add("issueAddress");
				attrList.add("sendBy");
				requiredAttrs.put("sxType", "修造类别");
				requiredAttrs.put("issueStoreRoom", "发出库房");
				requiredAttrs.put("issueAddress", "发出地址");
				requiredAttrs.put("sendBy", "发运人");
			} else {// 现场到现场
				attrList.add("issueStoreRoom");
				attrList.add("issueAddress");
				attrList.add("sendBy");
				requiredAttrs.put("issueStoreRoom", "发出库房");
				requiredAttrs.put("issueAddress", "发出地址");
				requiredAttrs.put("sendBy", "发运人");
			}

		} else if ("发运人审核".equals(status)) {
			attrList.add("memo");
			attrList.add("packDate");
			attrList.add("courierNum");
			requiredAttrs.put("courierNum", "运单编号");

			if (!"中心到中心".equals(transferMoveType)) {
				attrList.add("courierCompany");
				requiredAttrs.put("courierCompany", "快运公司");
			}
		} else if ("在途".equals(status)) {
			attrList.add("memo");
			attrList.add("openBy");
			attrList.add("receiveDate");
			attrList.add("receiveCheckBy");
			requiredAttrs.put("openBy", "接收开箱人");
			requiredAttrs.put("receiveDate", "接收日期");
			requiredAttrs.put("receiveCheckBy", "接收核对人");
		} else {
			attrList.add("memo");
		}

		// 赋值
		if (data.containsKey("transferMoveType")) {
			transfer.setValue("transferMoveType",
					data.getString("transferMoveType"),
					GWConstant.P_NOVALIDATION);
			data.remove("transferMoveType");
		}
		if (data.containsKey("issueStoreRoom")) {
			transfer.setValue("issueStoreRoom",
					data.getString("issueStoreRoom"), GWConstant.P_NOVALIDATION);
			data.remove("issueStoreRoom");
		}
		if (data.containsKey("sxType")) {
			transfer.setValue("sxType", data.getString("sxType"),
					GWConstant.P_NOVALIDATION);
			data.remove("sxType");
		}
		for (Entry<String, Object> attr : data.entrySet()) {
			String changeValue = attr.getValue().toString();
			if (!transfer.getString(attr.getKey()).equals(changeValue)) {
				transfer.setValue(attr.getKey(), changeValue,
						GWConstant.P_NOVALIDATION);
				transferRet.put(attr.getKey(), changeValue);
			}
		}

		// lookup
		// 接收库房
		if (!transfer.getString("RECEIVESTOREROOM").isEmpty()
				&& transfer.getField("RECEIVESTOREROOM").isValueChanged()) {
			transfer.setValue("RECEIVEBY",
					transfer.getString("RECEIVESTOREROOM.KEEPER"),
					GWConstant.P_NOVALIDATION);
			transfer.setValue("RECEIVEPHONE",
					transfer.getString("RECEIVESTOREROOM.PRIMARYPHONE"),
					GWConstant.P_NOVALIDATION);
			transferRet.put("receiveBy",
					transfer.getString("RECEIVESTOREROOM.KEEPER"));
			transferRet.put("receivePhone",
					transfer.getString("RECEIVESTOREROOM.PRIMARYPHONE"));
		}
		if (transfer.getString("TRANSFERMOVETYPE").equalsIgnoreCase("中心到中心")) {
			String LOCATIONTYPE = transfer.getJpoSet("ISSUESTOREROOM").getJpo()
					.getString("LOCATIONTYPE");
			if (LOCATIONTYPE.equalsIgnoreCase("维修")) {
				requiredAttrs.remove("sxType");
				requiredAttrs.remove("receiveStoreRoom");
				requiredAttrs.remove("receiveAddress");
				requiredAttrs.remove("receiveBy");
			}
		}
		// 必填校验
		for (String required : requiredAttrs.keySet()) {
			if (transfer.getString(required) == null
					|| transfer.getString(required).isEmpty()) {
				throw new MroException("必填字段:" + requiredAttrs.get(required)
						+ " 为空，请检查！");
			}
		}

		transferSet.save();

		if (obj.containsKey("transferLine")) {
			transferSet.setQueryWhere("transfernum='" + transfernum + "'");
			transferSet.reset();
			transfer = transferSet.getJpo();
			JSONArray lines = obj.getJSONArray("transferLine");
			JSONArray retLines = new JSONArray();
			for (int i = 0; i < lines.size(); i++) {
				MobileResult lineResult = MODIFYZXTRANSFERLINE(transfer,
						loginid, lines.getString(i));
				retLines.add(lineResult.getData());
			}
			transferSet.save();
			ret.put("transferLine", retLines);
		}

		if (obj.containsKey("attachments")) {
			transferSet.setQueryWhere("transfernum='" + transfernum + "'");
			transferSet.reset();
			JSONArray attachemnts = data.getJSONArray("attachments");
			for (Object atta : attachemnts) {
				saveAttachment(transfer, atta.toString(), loginid);
			}
		}

		ret.put("transfer", transferRet);
		result.setData(ret);
		return result;
	}

	/**
	 * 
	 * 编辑装箱单行
	 * 
	 * @param jpo
	 * @param loginid
	 * @param json
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public MobileResult MODIFYZXTRANSFERLINE(IJpo jpo, String loginid,
			String json) throws MroException {
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		// 将json字符串转换成json对象
		JSONObject data = JSONObject.parseObject(json);

		IJpo transferline;
		IJpoSet transferLineSet = jpo.getJpoSet("TRANSFERLINE");
		transferLineSet.setAppName("ZXTRANSFER");
		// 根据transferLineNum有无判断新增或修改
		if (data.containsKey("transferLineNum")) {
			// 装箱单行编号
			String transferlinenum = data.getString("transferLineNum");
			if (transferlinenum == null || transferlinenum.isEmpty()) {
				throw new MroException("必填字段：装箱单行编号 为空，请检查");
			}
			transferLineSet.setQueryWhere("transferlinenum='" + transferlinenum
					+ "'");
			transferLineSet.reset();
			if (transferLineSet.count() < 1) {
				throw new MroException("无法根据装箱单行编号:" + transferlinenum
						+ " 获取装箱单行记录");
			}
			transferline = transferLineSet.getJpo();
			ret.put("transferLineNum", transferlinenum);
		} else {
			// ZxdTransferLineDataBean.addrow()中判断是否可添加行的逻辑
			String CREATEBY = jpo.getString("CREATEBY");
			String TRANSFERMOVETYPE = jpo.getString("TRANSFERMOVETYPE");
			String SXTYPE = jpo.getString("SXTYPE");
			String ISSUESTOREROOM = jpo.getString("ISSUESTOREROOM");
			String RECEIVESTOREROOM = jpo.getString("RECEIVESTOREROOM");
			String status = jpo.getString("status");
			String ISMR = jpo.getString("ISMR");
			if (ISMR.equalsIgnoreCase("是")) {
				throw new MroException("transferline", "notaddrow");
			}
			if (!TRANSFERMOVETYPE
					.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
				if (RECEIVESTOREROOM.isEmpty()) {
					throw new MroException("transferline", "norecstoreroom");
				}
			} else {
				if (SXTYPE.isEmpty()) {
					throw new MroException("请在PC端使用选择缴库单按钮添加装箱单行");
				} else {
					if (RECEIVESTOREROOM.isEmpty()) {
						throw new MroException("transferline", "norecstoreroom");
					}
				}
			}

			if (!TRANSFERMOVETYPE
					.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
				if (ISSUESTOREROOM.isEmpty()) {
					throw new MroException("transferline", "nostoreroom");
				}

			}
			if (!status.equalsIgnoreCase("未处理")
					&& !status.equalsIgnoreCase("申请人修改")) {
				throw new MroException("transferline", "notaddrow");
			} else {
				if (!loginid.equalsIgnoreCase(CREATEBY)) {
					throw new MroException("transferline", "notaddrow");
				}
			}

			// 新增单行
			transferline = transferLineSet.addJpo();
			transferline.setValue("transfernum", jpo.getString("transfernum"),
					GWConstant.P_NOVALIDATION);
			transferline.setValue("SXTYPE", jpo.getString("SXTYPE"),
					GWConstant.P_NOVALIDATION);
			transferline.setValue("TRANSWORKORDERNUM",
					jpo.getString("TRANSWORKORDERNUM"),
					GWConstant.P_NOVALIDATION);
			transferline.setValue("receiveStoreRoom",
					jpo.getString("receiveStoreRoom"),
					GWConstant.P_NOVALIDATION);
			transferline.setValue("issueStoreRoom",
					jpo.getString("issueStoreRoom"), GWConstant.P_NOVALIDATION);
			transferline.setValue("issueAddress",
					jpo.getString("issueAddress"), GWConstant.P_NOVALIDATION);
			transferline.setValue("projectNum", jpo.getString("projectNum"),
					GWConstant.P_NOVALIDATION);
			ret.put("transferNum", jpo.getString("transfernum"));
			ret.put("sxType", jpo.getString("sxType"));
			ret.put("transWorkOrderNum", jpo.getString("transWorkOrderNum"));
			ret.put("receiveStoreRoom", jpo.getString("receiveStoreRoom"));
			ret.put("issueStoreRoom", jpo.getString("issueStoreRoom"));
			ret.put("issueAddress", jpo.getString("issueAddress"));
			ret.put("projectNum", jpo.getString("projectNum"));
		}

		// 装箱单的状态
		String status = jpo.getString("status");
		// 物料编码，请求中的数据或原有数据
		String itemnum = data.containsKey("itemNum") ? data
				.getString("itemNum") : transferline.getString("itemNum");
		// 接收库房
		String receiveStoreRoom = transferline.getString("receiveStoreRoom");
		// 发出库房
		String issueStoreRoom = transferline.getString("issueStoreRoom");
		// 移动类型
		String transferMoveType = jpo.getString("transferMoveType");

		// 可编辑字段
		LinkedList<String> attrList = new LinkedList<String>();
		attrList.add("returnType");
		attrList.add("memo");
		// 必填
		LinkedHashMap<String, String> requiredAttrs = new LinkedHashMap<String, String>();

		if ("未处理".equals(status)) {
			attrList.add("itemNum");
			attrList.add("orderQty");
			attrList.add("isJsfx");
			attrList.add("model");
			attrList.add("projectNum");
			attrList.add("dealType");
			attrList.add("repairProcess");
			attrList.add("failureDesc");
			attrList.add("outBinNum");
			requiredAttrs.put("itemNum", "物料编码");
			requiredAttrs.put("orderQty", "调拨数量");

			// 序列号、批次号
			if (StringUtil.isHaveStr(new String[] { "中心到现场", "中心到中心" },
					transferMoveType)) {
				if (isItem(itemnum, "ISTURNOVERERP")) {
					attrList.add("sqn");
				}
				if (isItem(itemnum, "ISLOTERP")) {
					attrList.add("lotNum");
				}
			} else {
				if (isItem(itemnum, "ISTURNOVERERP")) {
					attrList.add("sqn");
					requiredAttrs.put("sqn", "序列号");
				}
				if (isItem(itemnum, "ISLOTERP")) {
					attrList.add("lotNum");
					requiredAttrs.put("lotNum", "批次号");
				}
			}
			// 故障工单编号
			if ("GZ".equals(data.getString("sxType"))) {
				attrList.add("taskNum");
				requiredAttrs.put("taskNum", "故障工单编号");
			}
			if ("中心到现场".equals(transferMoveType)) {
				attrList.add("issueStoreRoom");
				attrList.add("issueAddress");
				requiredAttrs.put("issueStoreRoom", "发出库房");
				requiredAttrs.put("issueAddress", "发出地址");
			}
		} else if ("在途".equals(status)) {
			if (isLoc(issueStoreRoom, "ISLOCBIN")) {
				attrList.add("inBinNum");
				requiredAttrs.put("inBinNum", "入库仓位");
			}
		} else if ("申请人修改".equals(status)) {
			attrList.add("itemNum");
			attrList.add("orderQty");
			requiredAttrs.put("itemNum", "物料编码");
			requiredAttrs.put("orderQty", "调拨数量");
			// 序列号、批次号
			if (StringUtil.isHaveStr(new String[] { "中心到现场", "中心到中心" },
					transferMoveType)) {
				if (isItem(itemnum, "ISTURNOVERERP")) {
					attrList.add("sqn");
				}
				if (isItem(itemnum, "ISLOTERP")) {
					attrList.add("lotNum");
				}
			} else {
				if (isItem(itemnum, "ISTURNOVERERP")) {
					attrList.add("sqn");
					requiredAttrs.put("sqn", "序列号");
				}
				if (isItem(itemnum, "ISLOTERP")) {
					attrList.add("lotNum");
					requiredAttrs.put("lotNum", "批次号");
				}
			}

			if (isLoc(receiveStoreRoom, "ISLOCBIN")) {
				attrList.add("outBinNum");
				requiredAttrs.put("outBinNum", "出库仓位");
			}
		}

		// 赋值
		for (String attr : attrList) {
			if (data.containsKey(attr)) {
				transferline.setValue(attr, data.getString(attr),
						GWConstant.P_NOVALIDATION);
				ret.put(attr, data.getString(attr));
			}
		}

		// 必填校验
		for (String required : requiredAttrs.keySet()) {
			if (transferline.getString(required) == null
					|| transferline.getString(required).isEmpty()) {
				throw new MroException("必填字段:" + requiredAttrs.get(required)
						+ " 为空，请检查！");
			}
		}

		result.setData(ret);
		return result;
	}

	/**
	 * 
	 * 编辑验证工单
	 * 
	 * @param loginid
	 * @param json
	 * @throws Exception
	 * 
	 */
	public MobileResult UPDATEVALIORDER(String loginid, String json)
			throws Exception {
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		JSONObject orderRet = new JSONObject();
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("valiOrder");

		if (!data.containsKey("orderNum")) {
			throw new MroException("必须字段工单编号为空！");
		}
		String ordernum = data.getString("orderNum");

		IJpoSet orderSet = MroServer.getMroServer().getJpoSet("WORKORDER",
				MroServer.getMroServer().getSystemUserServer());
		orderSet.setAppName("VALIORDER");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setLoginID(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setUserName(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		orderSet.setQueryWhere("ordernum='" + ordernum + "'");
		orderSet.reset();
		if (orderSet.count() < 1) {
			throw new MroException("工单:" + ordernum + "不存在！修改失败!");
		}
		IJpo validateOrder = orderSet.getJpo();
		// 人员权限验证
		IJpoSet execPersonSet = validateOrder.getJpoSet("JXTASKEXECPERSON");
		execPersonSet.setQueryWhere("PERSONNUM='" + loginid + "'");
		execPersonSet.reset();
		if (execPersonSet.isEmpty()) {
			throw new MroException("当前用户无法修改此工单");
		}
		String status = validateOrder.getString("status");

		// 草稿
		if (SddqConstant.WO_STATUS_CG.equals(status)) {
			// 车号
			if (data.containsKey("carNum")) {
				validateOrder.setValue("carnum", data.getString("carNum"));
				if (validateOrder.getField("carnum").isValueChanged()) {
					IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
							"ASSET",
							"carno='" + validateOrder.getString("carnum")
									+ "' and cmodel='"
									+ validateOrder.getString("models") + "'");
					if (!assetSet.isEmpty()) {
						validateOrder.setValue("ASSETNUM", assetSet.getJpo()
								.getString("ASSETNUM"));
						validateOrder.setValue("PROJECTNUM", assetSet.getJpo()
								.getString("PROJECTNUM"));
						validateOrder.setValue("REPAIRPROCESS", assetSet
								.getJpo().getString("REPAIRPROCESS"));
						validateOrder.setValue("MODELPROJECT",
								validateOrder.getString("MODELS.MODELCODE"));

						orderRet.put("assetNum",
								assetSet.getJpo().getString("ASSETNUM"));
						orderRet.put("projectNum",
								assetSet.getJpo().getString("PROJECTNUM"));
						orderRet.put("repairProcess", assetSet.getJpo()
								.getString("REPAIRPROCESS"));
						orderRet.put("modelProject",
								validateOrder.getString("MODELS.MODELCODE"));
					}
				}
				orderRet.put("carnum", data.getString("carNum"));
			}
			// 检查员
			if (data.containsKey("checkPerson"))
				validateOrder.setValue("checkPerson",
						data.getString("checkPerson"));
		} else if (SddqConstant.WO_STATUS_CLZ.equals(status)) {
			// 车号
			if (data.containsKey("carNum")) {
				validateOrder.setValue("carnum", data.getString("carNum"));
				if (validateOrder.getField("carnum").isValueChanged()) {
					IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
							"ASSET",
							"carno='" + validateOrder.getString("carnum")
									+ "' and cmodel='"
									+ validateOrder.getString("models") + "'");
					if (!assetSet.isEmpty()) {
						validateOrder.setValue("ASSETNUM", assetSet.getJpo()
								.getString("ASSETNUM"));
						validateOrder.setValue("PROJECTNUM", assetSet.getJpo()
								.getString("PROJECTNUM"));
						validateOrder.setValue("REPAIRPROCESS", assetSet
								.getJpo().getString("REPAIRPROCESS"));
						validateOrder.setValue("MODELPROJECT",
								validateOrder.getString("MODELS.MODELCODE"));

						orderRet.put("assetNum",
								assetSet.getJpo().getString("ASSETNUM"));
						orderRet.put("projectNum",
								assetSet.getJpo().getString("PROJECTNUM"));
						orderRet.put("repairProcess", assetSet.getJpo()
								.getString("REPAIRPROCESS"));
						orderRet.put("modelProject",
								validateOrder.getString("MODELS.MODELCODE"));
					}
				}
				orderRet.put("carnum", data.getString("carNum"));
			}
			// 检查员
			if (data.containsKey("checkPerson")) {
				validateOrder.setValue("checkPerson",
						data.getString("checkPerson"));
				orderRet.put("checkPerson", data.getString("checkPerson"));
			}
			// 实际完成时间
			if (data.containsKey("actCompleteDate")) {
				validateOrder.setValue("ACTCOMPLETEDATE",
						data.getString("actCompleteDate"));
				orderRet.put("actCompleteDate",
						data.getString("actCompleteDate"));
			}
			// 验证结果--临时使用
			if (data.containsKey("valiResult")) {
				validateOrder.setValue("valiResult",
						data.getString("valiResult"));
				orderRet.put("valiResult", data.getString("valiResult"));
			}
			// 是否异常
			if (data.containsKey("isError")) {
				if ("是".equals(data.getString("isError"))) {
					validateOrder.setValue("ISERROR", 1);
				} else {
					validateOrder.setValue("ISERROR", 0);
				}
				orderRet.put("isError", data.getString("isError"));
			}

			if (validateOrder.getString("carNum") == null
					|| validateOrder.getString("carNum").isEmpty()) {
				throw new MroException("必填字段:车号 为空，请检查！");
			}
			if (validateOrder.getString("checkPerson") == null
					|| validateOrder.getString("checkPerson").isEmpty()) {
				throw new MroException("必填字段:检查员 为空，请检查！");
			}
		} else {
			throw new MroException("验证工单当前状态无法编辑");
		}
		orderSet.save();
		ret.put("valiOrder", orderRet);

		// 工单保存后再次获取，给子表使用
		orderSet.setQueryWhere("ordernum='" + ordernum + "'");
		orderSet.reset();
		if (!orderSet.isEmpty()) {
			validateOrder = orderSet.getJpo();
		}
		if (obj.containsKey("updownRcd")) {
			if (SddqConstant.WO_STATUS_CLZ.equals(status)) {
				JSONArray updownArr = obj.getJSONArray("updownRcd");
				JSONArray retArr = new JSONArray();
				for (int i = 0; i < updownArr.size(); i++) {
					MobileResult exResult = MODIFYEXCHANGERECORD(validateOrder,
							loginid, updownArr.getJSONObject(i).toJSONString());
					retArr.add(exResult.getData());
				}
				ret.put("updownRcd", retArr);
			} else {
				throw new MroException("验证产品信息修改失败，当前状态无法修改");
			}
		}
		if (obj.containsKey("valiFeedBack")) {
			if (SddqConstant.WO_STATUS_CLZ.equals(status)) {
				JSONObject feedbackObj = modifyValiFeedback(validateOrder, obj
						.getJSONObject("valiFeedBack").toJSONString());
				ret.put("valiFeedBack", feedbackObj);
			} else {
				throw new MroException("验证工单反馈信息修改失败，当前状态无法修改");
			}
		}
		if (obj.containsKey("attachments")) {
			JSONArray attachemnts = data.getJSONArray("attachments");
			for (Object atta : attachemnts) {
				saveAttachment(validateOrder, atta.toString(), loginid);
			}
		}
		orderSet.save();
		result.setData(ret);
		return result;
	}

	/**
	 * 
	 * 修改上下车记录
	 * 
	 * @param loginid
	 * @param json
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public MobileResult MODIFYEXCHANGERECORD(IJpo orderjpo, String loginid,
			String json) throws Exception {
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		// 将json字符串转换成json对象
		JSONObject data = JSONObject.parseObject(json);
		String status = orderjpo.getString("STATUS");

		String ordernum = orderjpo.getString("ordernum");
		String taskType = orderjpo.getString("type");
		String appName = orderjpo.getAppName();

		if ("FAILUREORD".equals(appName)
				&& orderjpo.getJpoSet("FAILURELIB").count() < 1) {
			throw new MroException("", "该故障工单对应故障信息为空，请检查！");
		}
		IJpoSet exchangeSet = orderjpo.getJpoSet("EXCHANGERECORD");
		if ("FAILUREORD".equals(appName)) {
			exchangeSet = orderjpo.getJpoSet("failurelib").getJpo()
					.getJpoSet("exchangerecord");
		}
		exchangeSet.setAppName(appName);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setLoginID(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");

		IJpo exchange = null;
		// 包含exchangeRecordId时为修改
		if (data.containsKey("exchangeRecordId")) {
			String exchangeRecordId = data.getString("exchangeRecordId");
			exchangeSet.setQueryWhere("exchangeRecordId='" + exchangeRecordId
					+ "'");
			exchangeSet.reset();
			if (exchangeSet.count() > 0) {
				exchange = exchangeSet.getJpo();
			} else {
				throw new MroException("", "无法根据唯一ID:" + exchangeRecordId
						+ "查找到上下车记录，请检查！");
			}
		} else {
			exchangeSet.setFlag(GWConstant.S_READONLY, false);
			exchange = exchangeSet.addJpo();
			exchange.setValue("tasktype", orderjpo.getString("type"));
			exchange.setValue("creatby", loginid);
			exchange.setValue("taskordernum", ordernum);
			// 故障工单中还要FAILUREORDERNUM，与上下车表关联
			if ("FAILUREORD".equals(appName)) {
				exchange.setValue("FAILUREORDERNUM", ordernum);
				ret.put("failureOrderNum", ordernum);
			}

			ret.put("taskType", orderjpo.getString("type"));
			ret.put("creatBy", loginid);
			ret.put("taskOrderNum", ordernum);

			// FAILURENUM--仅故障工单中与故障信息关联
			if (orderjpo.getJpoSet("FAILURELIB").count() > 0) {
				exchange.setValue("FAILURENUM",
						orderjpo.getString("FAILURELIB.FAILURENUM"));
				ret.put("failureNum", exchange.getString("FAILURENUM"));
			}
			exchange.setValue("cmodel", orderjpo.getString("models"));
			exchange.setValue("carno", orderjpo.getString("carnum"));

			ret.put("cmodel", orderjpo.getString("models"));
			ret.put("carNum", orderjpo.getString("carnum"));
		}
		ret.put("exchangeRecordNum", exchange.getString("exchangeRecordNum"));

		/* 下车部分 */
		// 验证前产品序列号&唯一编号（下车件）
		if (data.containsKey("sqn") && data.containsKey("assetNum")) {
			exchange.setValue("sqn", data.getString("sqn"),
					GWConstant.P_NOVALIDATION);
			exchange.setValue("assetnum", data.getString("assetNum"));
			ret.put("sqn", data.getString("sqn"));
			ret.put("assetNum", data.getString("assetNum"));

			IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet("ASSET",
					"assetNum='" + data.getString("assetNum") + "'");
			if (exchange.getField("sqn").isValueChanged()
					&& !assetSet.isEmpty()) {
				exchange.setValue("LOTNUM",
						assetSet.getJpo().getString("LOTNUM"));
				exchange.setValue("ITEMNUM",
						assetSet.getJpo().getString("ITEMNUM"));
				exchange.setValue("FAULTPOSITION",
						assetSet.getJpo().getString("LOC"));
				ret.put("lotNum", exchange.getString("LOTNUM"));
				ret.put("itemNum", exchange.getString("ITEMNUM"));
				ret.put("faultPosition", exchange.getString("FAULTPOSITION"));
			}

		}

		if ("故障".equals(taskType)) {

			// 仅处理中、库管员驳回、技术主管审核的故障工单，可以添加或修改上下车
			String[] statuss = { SddqConstant.WO_STATUS_CLZ,
					SddqConstant.WO_STATUS_KGYBH, SddqConstant.WO_STATUS_JSZGSH };
			if (!StringUtil.isHaveStr(statuss, status)) {
				throw new MroException("当前工单状态无法修改上下车记录信息!");
			}

			// 可编辑字段
			List<String> tmpList = Arrays.asList("sqn", "newSqn", "assetNum",
					"newAssetNum", "faultPosition", "downReason",
					"isMainFault", "dealMode", "isAppNotice", "isTechaAnalyze",
					"isCustItem", "isAgreeStay", "remark");
			ArrayList<String> attrList = new ArrayList<String>(tmpList);

			// 必填
			HashMap<String, String> requiredAttrs = new HashMap<String, String>();
			requiredAttrs.put("sqn", "下车件序列号");
			requiredAttrs.put("dealMode", "故障品处置方式");
			requiredAttrs.put("faultPosition", "故障品位置号");
			requiredAttrs.put("downReason", "下车原因");
			requiredAttrs.put("newSqn", "上车件序列号");

			// 根据故障后果对是否邮件通报和是否技术分析字段勾选
			// 获取故障后果的值
			String[] allAttrs = { "机破", "D类事故", "C类及以上事故", "下线", "清客", "救援",
					"安监" };
			String faultconseq = orderjpo.getJpoSet("FAILURELIB").getJpo()
					.getString("FAULTCONSEQ");
			if (!faultconseq.isEmpty()) {
				if (StringUtil.isHaveStr(allAttrs, faultconseq)) {
					exchange.setValue("ISAPPNOTICE", "1");
					exchange.setValue("ISTECHAANALYZE", "1");
					attrList.remove("isAppNotice");
					attrList.remove("isTechaAnalyze");
				}
			}

			for (String attr : attrList) {
				if (data.containsKey(attr)) {
					if (attr.startsWith("is")) {
						if ("是".equals(data.getString(attr))) {
							exchange.setValue(attr, 1);
						} else {
							exchange.setValue(attr, 0);
						}
					}
					if (StringUtil.isHaveStr(new String[] { "sqn", "newSqn" },
							attr)) {
						exchange.setValue(attr, data.getString(attr),
								GWConstant.P_NOVALIDATION);
					} else {
						exchange.setValue(attr, data.getString(attr));
					}

					// 变更字段有lookup的
					if ("assetNum".equals(attr)
							&& exchange.getField("assetNum").isValueChanged()) {
						IJpoSet assetSet = MroServer.getMroServer()
								.getSysJpoSet(
										"ASSET",
										"assetNum='"
												+ exchange
														.getString("assetNum")
												+ "'");
						exchange.setValue("ITEMNUM", assetSet.getJpo()
								.getString("ITEMNUM"));
						exchange.setValue("LOTNUM", assetSet.getJpo()
								.getString("LOTNUM"));
						exchange.setValue("FAULTPOSITION", assetSet.getJpo()
								.getString("LOC"));
						exchange.setValue("SOFTVERSION", assetSet.getJpo()
								.getString("SOFTVERSION"));
					}
					if ("newAssetNum".equals(attr)
							&& exchange.getField("NEWassetNum")
									.isValueChanged()) {
						IJpoSet assetSet = MroServer
								.getMroServer()
								.getSysJpoSet(
										"ASSET",
										"assetNum='"
												+ exchange
														.getString("NEWassetNum")
												+ "'");
						exchange.setValue("NEWITEMNUM", assetSet.getJpo()
								.getString("ITEMNUM"));
						exchange.setValue("NEWLOC", assetSet.getJpo()
								.getString("LOCATION"));
						exchange.setValue("NEWBINNUM", assetSet.getJpo()
								.getString("BINNUM"));
						exchange.setValue("NEWLOTNUM", assetSet.getJpo()
								.getString("LOTNUM"));
					}
				}
			}

			if (exchange.getBoolean("ISMAINFAULT")) {
				IJpo fl = orderjpo.getJpoSet("failurelib").getJpo();
				if (fl != null) {
					// 故障件信息
					fl.setValue("FAULTCOMPONENTNUM", exchange.getString("sqn"));
					fl.setValue("ASSETNUM", exchange.getString("ASSETNUM"));
					fl.setValue("PRODUCTCODE", exchange.getString("ITEMNUM"));
					fl.setValue("FAULTCOMPONENTNAME",
							exchange.getString("ITEM.DESCRIPTION"));
					// 更换件信息
					fl.setValue("CHANGEASSETNUM", exchange.getString("newsqn"),
							GWConstant.P_NOVALIDATION);
					fl.setValue("NEWASSETNUM",
							exchange.getString("NEWASSETNUM"));
					fl.setValue("CHANGEPRODUCTCODE",
							exchange.getString("NEWITEMNUM"));
					fl.setValue("CHANGEASSETNAME",
							exchange.getString("NEWITEM.DESCRIPTION"));

					// 设备简称设必填
					fl.setFieldFlag("PRODUCTNICKNAME", GWConstant.S_REQUIRED,
							true);

					// zzx add 8.23
					String assetnums = exchange.getString("ASSETNUM");

					IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
							"ASSET", "assetnum='" + assetnums + "'");
					if (assetSet != null && assetSet.count() > 0) {
						String loc = assetSet.getJpo().getString("LOC");
						fl.setValue("FAULTPOSITION", loc,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					// zzx add end

				}
			}

			for (String required : requiredAttrs.keySet()) {
				if (exchange.getString(required) == null
						|| StringUtil.isStrEmpty(exchange.getString(required))) {
					throw new MroException("必填字段:"
							+ requiredAttrs.get(required) + " 为空，请检查！");
				}
			}
		} else if ("改造".equals(taskType)) {
			if (!SddqConstant.WO_STATUS_CLZ.equals(status)) {
				throw new MroException("改造工单当前状态无法修改");
			}
			// 可编辑字段
			List<String> tmpList = Arrays.asList("sqn", "assetNum", "newSqn",
					"newAssetNum", "softVersion", "newSoftVersion", "lotNum",
					"newLotNum", "remark", "isAddPart", "parentSqn",
					"parentItemNum", "parentAssetNum");
			ArrayList<String> attrList = new ArrayList<String>(tmpList);

			for (String attr : attrList) {
				if (data.containsKey(attr)) {
					if (StringUtil.isHaveStr(new String[] { "sqn", "newSqn" },
							attr)) {
						exchange.setValue(attr, data.getString(attr),
								GWConstant.P_NOVALIDATION);
					} else if ("isAddPart".equalsIgnoreCase(attr)) {
						exchange.setValue(attr, data.getString(attr),
								GWConstant.P_NOVALIDATION_AND_NOACTION);
					} else {
						exchange.setValue(attr, data.getString(attr));
					}

					ret.put(attr, exchange.getString(attr));

					// 变更字段有lookup的
					if ("assetNum".equals(attr)
							&& exchange.getField("assetNum").isValueChanged()) {
						IJpoSet assetSet = MroServer.getMroServer()
								.getSysJpoSet(
										"ASSET",
										"assetNum='"
												+ exchange
														.getString("assetNum")
												+ "'");
						exchange.setValue("ITEMNUM", assetSet.getJpo()
								.getString("ITEMNUM"));
						exchange.setValue("LOTNUM", assetSet.getJpo()
								.getString("LOTNUM"));
						exchange.setValue("FAULTPOSITION", assetSet.getJpo()
								.getString("LOC"));
						if (!data.containsKey("softVersion")
								&& StringUtil.isStrEmpty(exchange
										.getString("SOFTVERSION"))) {
							exchange.setValue("SOFTVERSION", assetSet.getJpo()
									.getString("SOFTVERSION"));
							ret.put("softVersion",
									exchange.getString("softVersion"));
						}
						ret.put("itemNum", exchange.getString("ITEMNUM"));
						ret.put("lotNum", exchange.getString("LOTNUM"));
						ret.put("faultPosition",
								exchange.getString("FAULTPOSITION"));
					}
					if ("newAssetNum".equals(attr)
							&& exchange.getField("NEWassetNum")
									.isValueChanged()) {
						IJpoSet assetSet = MroServer
								.getMroServer()
								.getSysJpoSet(
										"ASSET",
										"assetNum='"
												+ exchange
														.getString("NEWassetNum")
												+ "'");
						exchange.setValue("NEWITEMNUM", assetSet.getJpo()
								.getString("ITEMNUM"));
						ret.put("newItemNum", exchange.getString("NEWITEMNUM"));
						exchange.setValue("NEWLOC", assetSet.getJpo()
								.getString("LOCATION"));
						ret.put("newLoc", exchange.getString("NEWLOC"));
						exchange.setValue("NEWBINNUM", assetSet.getJpo()
								.getString("BINNUM"));
						ret.put("newBinNum", exchange.getString("NEWBINNUM"));
						exchange.setValue("NEWLOTNUM", assetSet.getJpo()
								.getString("LOTNUM"));
						ret.put("newLotNum", exchange.getString("NEWLOTNUM"));
					}
				}
			}
			if (StringUtil.isStrEmpty(exchange.getString("sqn"))
					&& (!exchange.getBoolean("isAddPart"))) {
				throw new MroException("必填信息:改造前产品序列号 为空，请检查！");
			}
			if (StringUtil.isStrEmpty(exchange.getString("newsqn"))
					&& exchange.getBoolean("isAddPart")) {
				throw new MroException("必填信息:改造后产品序列号 为空，请检查！");
			}
		} else if ("验证".equals(taskType)) {

			if (data.containsKey("softVersion")) {
				exchange.setValue("SOFTVERSION", data.getString("softVersion"));
				ret.put("softVersion", exchange.getString("softVersion"));
			}
			if (data.containsKey("startDate")) {
				if (StringUtil.isStrEmpty(data.getString("startDate"))) {
					exchange.setValueNull("STARTDATE");
				} else {
					exchange.setValue("STARTDATE", data.getString("startDate"));
				}
				ret.put("startDate", exchange.getString("startDate"));
			}
			if (data.containsKey("newSoftVersion")) {
				exchange.setValue("NEWSOFTVERSION",
						data.getString("newSoftVersion"));
				ret.put("newSoftVersion", exchange.getString("newSoftVersion"));
			}
			if (data.containsKey("endDate")) {
				if (StringUtil.isStrEmpty(data.getString("endDate"))) {
					exchange.setValueNull("endDate");
				} else {
					exchange.setValue("endDate", data.getString("endDate"));
				}
				exchange.setValue("ENDDATE", data.getString("endDate"));
				ret.put("endDate", exchange.getString("endDate"));
			}
			if (data.containsKey("remark")) {
				exchange.setValue("REMARK", data.getString("remark"));
				ret.put("remark", exchange.getString("remark"));
			}

			if (exchange.getString("sqn") == null
					|| StringUtil.isStrEmpty(exchange.getString("sqn"))) {
				throw new MroException("必填信息:验证前产品序列号 为空，请检查！");
			}

			// ValiProductInfoBean中部分逻辑，验证产品信息向工单信息赋值
			String startdate = exchange.getString("STARTDATE");
			if (orderjpo != null) {
				Date actstartdate = orderjpo.getDate("ACTSTARTDATE");
				if (actstartdate == null) {
					orderjpo.setValue("ACTSTARTDATE", startdate,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if (StringUtil.isStrEmpty(orderjpo.getString("REPORTER"))) {
					// 设置提报人
					orderjpo.setValue("REPORTER", loginid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
		}

		result.setData(ret);
		return result;
	}

	/**
	 * 
	 * 修改耗损件记录
	 * 
	 * @param jpo
	 *            父级Joo
	 * @param loginid
	 * @param json
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public MobileResult MODIFYJXTASKLOSSPART(IJpo jpo, String loginid,
			String json) throws Exception {
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		// 将json字符串转换成json对象
		JSONObject data = JSONObject.parseObject(json);
		// 工单类型
		String tasktype = jpo.getString("type");
		String status = jpo.getString("status");

		// 仅处理中的故障工单，可以添加或修改耗损件信息
		String[] havingStr = new String[] { SddqConstant.WO_STATUS_CLZ,
				SddqConstant.WO_STATUS_JSZGBH, SddqConstant.WO_STATUS_KGYBH,
				SddqConstant.WO_STATUS_SHBH, SddqConstant.WO_STATUS_JSZGSH };
		if (!StringUtil.isHaveStr(havingStr, (jpo.getString("STATUS")))) {
			throw new MroException("当前工单状态无法修改耗损件信息!");
		}

		// 工单表工单编号，关联用
		String jxtasknum = jpo.getString("ordernum");

		IJpoSet losspartSet = jpo.getJpoSet("JXTASKLOSSPART");
		losspartSet.setAppName(jpo.getAppName());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");

		IJpo losspart = null;
		// ID
		if (data.containsKey("jxTaskLossPartId")) {
			String jxtasklosspartid = data.getString("jxTaskLossPartId");
			losspartSet.setQueryWhere("jxtasklosspartid='" + jxtasklosspartid
					+ "'");
			losspartSet.reset();
			if (losspartSet.count() > 0) {
				losspart = losspartSet.getJpo();
			} else {
				throw new MroException("", "无法根据编号:" + jxtasklosspartid
						+ "查找到耗损件记录，请检查！");
			}
		} else {
			losspart = losspartSet.addJpo();
			losspart.setValue("creator", loginid);
			losspart.setValue("createdate", MroServer.getMroServer().getDate());
			losspart.setValue("workordertype", tasktype);
			losspart.setValue("jxtasknum", jxtasknum);

			ret.put("creator", loginid);
			ret.put("createdate", MroServer.getMroServer().getDate());
			ret.put("workordertype", tasktype);
			ret.put("jxtasknum", jxtasknum);
		}

		if ("故障".equals(tasktype)) {
			// 可编辑字段
			List<String> tmpList = Arrays.asList("downItemNum", "isCustItem",
					"isNotice", "isTechaAnalyze", "itemNum", "amount", "upLoc",
					"inventory", "dealMode", "isAgreeStay", "remark");
			ArrayList<String> attrList = new ArrayList<String>(tmpList);

			// 必填
			HashMap<String, String> requiredAttrs = new HashMap<String, String>();
			requiredAttrs.put("downItemNum", "下车件物料编码");
			requiredAttrs.put("dealMode", " 故障品处置方式");
			requiredAttrs.put("itemNum", "上车件物料编码");
			requiredAttrs.put("amount", "上车件上车数量");

			if (SddqConstant.WO_STATUS_CLZ.equals(status)
					|| SddqConstant.WO_STATUS_KGYBH.equals(status)) {
				// 根据故障后果对是否邮件通报和是否技术分析字段勾选
				// 获取故障后果的值
				String[] allAttrs = { "机破", "D类事故", "C类及以上事故", "下线", "清客",
						"救援", "安监" };
				String faultconseq = jpo.getJpoSet("FAILURELIB").getJpo()
						.getString("FAULTCONSEQ");
				if (StringUtil.isStrNotEmpty(faultconseq)) {
					if (StringUtil.isHaveStr(allAttrs, faultconseq)) {
						losspart.setValue("ISNOTICE", "1");
						losspart.setValue("ISTECHAANALYZE", "1");
						attrList.remove("isNotice");
						attrList.remove("isTechaAnalyze");
					}
				}

				for (String attr : attrList) {
					if (data.containsKey(attr)) {
						if (StringUtil.isHaveStr(new String[] { "sqn",
								"newSqn", "downItemNum", "itemNum", "upLoc",
								"inventory" }, attr)) {
							losspart.setValue(attr, data.getString(attr),
									GWConstant.P_NOVALIDATION);
						} else if ("amount".equals(attr)) {
							losspart.setValue(attr, data.getString(attr),
									GWConstant.P_NOVALIDATION_AND_NOACTION);
						} else {
							losspart.setValue(attr, data.getString(attr));
						}
						ret.put(attr, data.getString(attr));
					}
				}

				for (String required : requiredAttrs.keySet()) {
					if (losspart.getString(required) == null
							|| StringUtil.isStrEmpty(losspart
									.getString(required))) {
						throw new MroException("必填字段:"
								+ requiredAttrs.get(required) + " 为空，请检查！");
					}
				}

			} else if (SddqConstant.WO_STATUS_JSZGSH.equals(status)) {
				// 给现场留用观察意见赋值
				String[] attrList2 = { "isAgreeStay", "remark" };
				for (String attr : attrList2) {
					if (data.containsKey(attr)) {

						losspart.setValue(attr, data.getString(attr),
								GWConstant.P_NOVALIDATION);

						ret.put(attr, data.getString(attr));
					}
				}

				if (StringUtil.isStrEmpty(losspart.getString("isAgreeStay"))) {
					throw new MroException("必填字段:"
							+ requiredAttrs.get("isAgreeStay") + " 为空，请检查！");
				}

			}

			else {
				throw new MroException("工单当前状态无法修改耗损件记录");
			}
		} else if ("改造".equals(tasktype)) {
			if (data.containsKey("downItemNum")) {
				losspart.setValue("downItemNum", data.getString("downItemNum"));
				ret.put("downItemNum", data.getString("downItemNum"));
			}
			if (data.containsKey("itemNum")) {
				losspart.setValue("itemNum", data.getString("itemNum"),
						GWConstant.P_NOVALIDATION);
				ret.put("itemNum", data.getString("itemNum"));
			}
			if (data.containsKey("amount")) {
				losspart.setValue("amount", data.getString("amount"),
						GWConstant.P_NOVALIDATION_AND_NOACTION);
				ret.put("amount", data.getString("amount"));
			}

			if (losspart.getString("downitemnum") == null
					|| StringUtil.isStrEmpty(losspart.getString("downitemnum"))) {
				throw new MroException("必填字段:耗损件下车物料编码 为空，请检查！");
			}
			if (losspart.getString("amount") == null
					|| StringUtil.isStrEmpty(losspart.getString("amount"))) {
				throw new MroException("必填字段:耗损件上车数量 为空，请检查！");
			}
		}
		// 下车item的lookup
		if (data.containsKey("downItemNum")) {
			if (data.containsKey("downAssetNum")) {
				// 下车件物料编码相关
				IJpo assetpart = losspart.getJpoSet("$ASSETPART", "ASSETPART",
						"assetnum='" + data.getString("downAssetNum") + "'")
						.getJpo();
				losspart.setValue("DOWNASSETNUM",
						assetpart.getString("ASSETNUM"));
				losspart.setValue("downsqn", assetpart.getString("ASSET.SQN"));
				losspart.setValue("DOWNAMOUNT", assetpart.getString("QTY"));
				losspart.setValue("DOWNLOTNUM", assetpart.getString("LOTNUM"));
				losspart.setValue("DOWNPARTNUM",
						assetpart.getString("ASSETPARTNUM"));
			} else {
				throw new MroException("必填字段:下车件库存ID 为空，请检查！");
			}

		}
		// 上车item的lookup
		if (data.containsKey("itemNum")) {
			if (data.containsKey("assetId")
					&& StringUtil.isStrNotEmpty(data.getString("assetId"))) {
				// FldTaskOrderSwapSqn中设置的lookup
				// 上车件物料编码相关
				if (ItemUtil.LOT_I_ITEM.equals(ItemUtil.getItemInfo(losspart
						.getString("itemnum")))) {
					IJpo invblance = losspart.getJpoSet("$INVBLANCE",
							"INVBLANCE",
							"INVBLANCEID='" + data.getString("assetId") + "'")
							.getJpo();
					losspart.setValue("INVENTORY",
							invblance.getString("LOTQTY"));
					losspart.setValue("UPLOC", invblance.getString("STOREROOM"));
					losspart.setValue("LOTNUM", invblance.getString("LOTNUM"));
				} else {
					IJpo invetory = losspart.getJpoSet(
							"$SYS_INVENTORY",
							"SYS_INVENTORY",
							"SYS_INVENTORYID='" + data.getString("assetId")
									+ "'").getJpo();
					losspart.setValue("INVENTORY", invetory.getString("CURBAL"));
					losspart.setValue("UPLOC", invetory.getString("LOCATION"));
				}
			} else if (!data.getString("itemNum").equals(
					losspart.getString("itemNum"))) {
				throw new MroException("必填字段:上车件库存ID 为空，请检查！");
			}
		}
		if ("故障".equals(tasktype) || "改造".equals(tasktype)) {
			int amount = losspart.getInt("AMOUNT");// 上车数量
			String assetpartnum = losspart.getString("DOWNPARTNUM");
			String itemnum = losspart.getString("itemnum");// 上车件物料编码
			String uploc = losspart.getString("uploc");// 上车件库房
			String lotnum = losspart.getString("lotnum");// 上车件批次号
			// 操作锁定数量
			WorkorderUtil.operateQty(assetpartnum, amount, itemnum, uploc,
					lotnum, "+");
		}
		result.setData(ret);
		return result;
	}

	/**
	 * 
	 * 增加故障工单
	 * 
	 * @param loginid
	 * @param json
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public MobileResult ADDFAILUREORD(String loginid, String json)
			throws Exception {
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		JSONObject failRet = new JSONObject();
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("failureOrd");

		// 车号
		String carnum = data.getString("carNum");
		// 车型
		String models = data.getString("cmodel");
		// 客户来电时间
		String calltime = data.getString("callTime");
		// 项目编号
		String projectnum = data.getString("projectNum");
		// 服务单位
		String servcompany = data.getString("servCompany");
		// 服务单位联系人
		String servcomcontactor = data.getString("servComContactor");
		// 联系人备用电话
		String backTel = data.getString("backTel");
		// 所属站点
		String whichStation = data.getString("whichStation");
		// 服务工程师
		String servEngineer = data.getString("servEngineer");
		// 安全管理要求
		String managementreq = data.getString("managementReq");
		// 派工理由
		String dispatchreason = data.getString("dispatchReason");

		IJpoSet orderSet = MroServer.getMroServer().getJpoSet("WORKORDER",
				MroServer.getMroServer().getSystemUserServer());
		orderSet.setAppName("FAILUREORD");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setLoginID(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setUserName(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		IJpo failureOrder = orderSet.addJpo();

		failureOrder.setValue("type", "故障");
		failureOrder.setValue("ISMOBILECREATE", 1);
		failureOrder.setValue("reporter", loginid);
		failureOrder.setValue("creater", loginid);

		failureOrder.setValue("carnum", carnum);
		failureOrder.setValue("models", models);

		IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
				"asset",
				"carno='" + failureOrder.getString("carnum") + "' and cmodel='"
						+ failureOrder.getString("models") + "'");
		if (!assetSet.isEmpty()) {
			failureOrder.setValue("carnum", assetSet.getJpo()
					.getString("carno"));
			failureOrder.setValue("models",
					assetSet.getJpo().getString("cmodel"));
			failureOrder.setValue("assetnum",
					assetSet.getJpo().getString("assetnum"),
					GWConstant.P_NOACTION);

			failureOrder.setValue("repairprocess",
					failureOrder.getString("ASSET.REPAIRPROCESS"));
			failureOrder.setValue("OWNERCUSTOMER",
					failureOrder.getString("ASSET.OWNERCUSTOMER"));
			failureOrder.setValue("modelproject",
					failureOrder.getString("ASSET.MODELS.MODELDESC"));
			failureOrder.setValue("runKilometre",
					failureOrder.getString("ASSET.runKilometre"));

			failRet.put("assetNum", failureOrder.getString("assetNum"));
			failRet.put("repairProcess",
					failureOrder.getString("repairProcess"));
			failRet.put("projectNum", failureOrder.getString("projectNum"));
			failRet.put("ownerCustomer",
					failureOrder.getString("ownerCustomer"));
			failureOrder.setValue("runKilometre",
					failureOrder.getString("ASSET.runKilometre"));
		} else {
			throw new MroException("无法根据车型:" + failureOrder.getString("carnum")
					+ " 车号:" + failureOrder.getString("models")
					+ " 获取车辆信息，请检查！");
		}

		failureOrder.setValue("calltime", calltime);
		failureOrder.setValue("projectnum", projectnum);
		failureOrder.setValue("servcompany", servcompany);
		failureOrder.setValue("servcomcontactor", servcomcontactor);
		failureOrder.setValue("backTel", backTel);
		failureOrder.setValue("servengineer", servEngineer);
		failureOrder.setValue("whichStation", whichStation);
		failureOrder.setValue("managementreq", managementreq);
		failureOrder.setValue("dispatchreason", dispatchreason);

		failRet.put("type", "故障");
		failRet.put("reporter", loginid);
		failRet.put("creater", loginid);

		failRet.put("carNum", carnum);
		failRet.put("cmodel", models);
		failRet.put("callTime", calltime);
		failRet.put("projectNum", projectnum);
		failRet.put("servCompany", servcompany);
		failRet.put("servComContactor", servcomcontactor);
		failRet.put("backTel", backTel);
		failRet.put("servEngineer", servEngineer);
		failRet.put("whichStation", whichStation);
		failRet.put("managementReq", managementreq);
		failRet.put("dispatchReason", dispatchreason);

		// 检查必填
		if (failureOrder.getString("carnum") == null
				|| StringUtil.isStrEmpty(failureOrder.getString("carnum"))) {
			throw new MroException("故障工单必填字段:车号 为空，请检查！");
		}
		if (failureOrder.getString("models") == null
				|| StringUtil.isStrEmpty(failureOrder.getString("models"))) {
			throw new MroException("故障工单必填字段:车型 为空，请检查！");
		}
		if (failureOrder.getString("calltime") == null
				|| StringUtil.isStrEmpty(failureOrder.getString("calltime"))) {
			throw new MroException("故障工单必填字段:客户来电时间 为空，请检查！");
		}
		if (failureOrder.getString("projectnum") == null
				|| StringUtil.isStrEmpty(failureOrder.getString("projectnum"))) {
			throw new MroException("故障工单必填字段:项目编号 为空，请检查！");
		}
		if (failureOrder.getString("servcompany") == null
				|| StringUtil.isStrEmpty(failureOrder.getString("servcompany"))) {
			throw new MroException("故障工单必填字段:服务单位 为空，请检查！");
		}
		if (failureOrder.getString("whichStation") == null
				|| StringUtil
						.isStrEmpty(failureOrder.getString("whichStation"))) {
			throw new MroException("故障工单必填字段:所属站点 为空，请检查！");
		}
		if (failureOrder.getString("servengineer") == null
				|| StringUtil
						.isStrEmpty(failureOrder.getString("servengineer"))) {
			throw new MroException("故障工单必填字段:现场处理人 为空，请检查！");
		}
		if (failureOrder.getString("managementreq") == null
				|| StringUtil.isStrEmpty(failureOrder
						.getString("managementreq"))) {
			throw new MroException("故障工单必填字段:安全管理要求 为空，请检查！");
		}
		if (failureOrder.getString("dispatchreason") == null
				|| StringUtil.isStrEmpty(failureOrder
						.getString("dispatchreason"))) {
			throw new MroException("故障工单必填字段:派工理由 为空，请检查！");
		}

		// lookups带来的字段
		failureOrder.setValue("whichoffice",
				failureOrder.getString("SERVENGINEER.DEPARTMENT"));
		failRet.put("whichOffice", failureOrder.getString("whichOffice"));

		String ordernum = failureOrder.getString("ordernum");
		failRet.put("orderNum", ordernum);

		if (data.containsKey("attachments")) {
			JSONArray attachemnts = data.getJSONArray("attachments");
			for (Object atta : attachemnts) {
				saveAttachment(failureOrder, atta.toString(), loginid);
			}
		}

		orderSet.save();
		ret.put("failureOrd", failRet);
		result.setData(ret);
		return result;
	}

	/**
	 * 更新故障工单
	 * 
	 * @param loginid
	 * @param json
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public MobileResult UPDATEFAILUREORD(String loginid, String json)
			throws Exception {
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		JSONObject orderRet = new JSONObject();
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("failureOrd");

		if (!data.containsKey("orderNum")) {
			throw new MroException("必填字段: 工单编号 为空！");
		}
		String ordernum = data.getString("orderNum");

		IJpoSet orderSet = MroServer.getMroServer().getJpoSet("WORKORDER",
				MroServer.getMroServer().getSystemUserServer());
		orderSet.setAppName("FAILUREORD");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setLoginID(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setUserName(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");

		orderSet.setQueryWhere("ordernum='" + ordernum + "'");
		orderSet.reset();
		if (!orderSet.isEmpty()) {
			IJpo failureOrder = orderSet.getJpo();
			String status = failureOrder.getString("status");
			String erpStatus = data.getString("erpStatus");// 三包接口状态
			// 草稿
			if (SddqConstant.WO_STATUS_CG.equals(status)) {
				// 车号&车型
				if (data.containsKey("carNum") && data.containsKey("cmodel")) {
					IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
							"asset",
							"carno='" + data.getString("carNum")
									+ "' and cmodel='"
									+ data.getString("cmodel") + "'");
					if (!assetSet.isEmpty()) {
						failureOrder.setValue("carnum", assetSet.getJpo()
								.getString("carno"), GWConstant.P_NOVALIDATION);
						failureOrder
								.setValue("models", assetSet.getJpo()
										.getString("cmodel"),
										GWConstant.P_NOVALIDATION);
						failureOrder.setValue("assetnum", assetSet.getJpo()
								.getString("assetnum"), GWConstant.P_NOACTION);

						failureOrder.setValue("assetnum",
								failureOrder.getString("ASSET.ASSETNUM"),
								GWConstant.P_NOVALIDATION);
						orderRet.put("carNum", failureOrder.getString("carnum"));
						orderRet.put("cmodel", failureOrder.getString("models"));
					} else {
						throw new MroException("无法根据车型:"
								+ failureOrder.getString("carnum") + " 车号:"
								+ failureOrder.getString("models")
								+ " 获取车辆信息，请检查！");
					}
					failureOrder.setValue("repairprocess",
							failureOrder.getString("ASSET.REPAIRPROCESS"),
							GWConstant.P_NOVALIDATION);
					failureOrder.setValue("modelproject",
							failureOrder.getString("ASSET.MODELS.MODELDESC"),
							GWConstant.P_NOVALIDATION);
					failureOrder.setValue("runKilometre",
							failureOrder.getString("ASSET.runKilometre"),
							GWConstant.P_NOVALIDATION);
					failureOrder.setValue("repairAfterKilometer", failureOrder
							.getString("ASSET.REPAIRAFTERKILOMETER"),
							GWConstant.P_NOVALIDATION);
					failureOrder.setValue("ownerCustomer",
							failureOrder.getString("ASSET.OWNERCUSTOMER"),
							GWConstant.P_NOVALIDATION);
					orderRet.put("assetNum", failureOrder.getString("assetNum"));
					orderRet.put("repairProcess",
							failureOrder.getString("repairprocess"));
					orderRet.put("modelProject",
							failureOrder.getString("modelproject"));
					orderRet.put("projectNnum",
							failureOrder.getString("projectnum"));
					orderRet.put("runKilometre",
							failureOrder.getString("runKilometre"));
					orderRet.put("repairAfterKilometer",
							failureOrder.getString("repairAfterKilometer"));
					orderRet.put("ownerCustomer",
							failureOrder.getString("ownerCustomer"));
				}
				// 客户来电时间
				if (data.containsKey("callTime")) {
					failureOrder.setValue("calltime",
							data.getString("callTime"),
							GWConstant.P_NOVALIDATION);
					orderRet.put("callTime", failureOrder.getString("callTime"));
				}
				// 项目编号
				if (data.containsKey("projectNum")) {
					failureOrder.setValue("projectNum",
							data.getString("projectNum"),
							GWConstant.P_NOVALIDATION);
					orderRet.put("projectNum",
							failureOrder.getString("projectNum"));
				}
				// 服务单位
				if (data.containsKey("servCompany")) {
					failureOrder.setValue("servcompany",
							data.getString("servCompany"),
							GWConstant.P_NOVALIDATION);
					orderRet.put("servCompany",
							failureOrder.getString("servCompany"));
				}
				// 服务单位联系人
				if (data.containsKey("servComContactor")) {
					failureOrder.setValue("servcomcontactor",
							data.getString("servComContactor"));
					orderRet.put("servComContactor",
							failureOrder.getString("servComContactor"));
				}
				// 备用电话
				if (data.containsKey("backTel")) {
					failureOrder.setValue("backTel", data.getString("backTel"),
							GWConstant.P_NOVALIDATION);
					orderRet.put("backTel", failureOrder.getString("backTel"));
				}
				// 所属站点
				if (data.containsKey("whichStation")) {
					failureOrder.setValue("whichStation",
							data.getString("whichStation"),
							GWConstant.P_NOVALIDATION);
					orderRet.put("whichStation",
							failureOrder.getString("whichStation"));
				}
				// 现场处理人
				if (data.containsKey("servEngineer")) {
					failureOrder.setValue("servEngineer",
							data.getString("servEngineer"),
							GWConstant.P_NOVALIDATION);
					orderRet.put("servEngineer",
							failureOrder.getString("servEngineer"));
				}
				// 安全管理要求
				if (data.containsKey("managementReq")) {
					failureOrder.setValue("managementreq",
							data.getString("managementReq"),
							GWConstant.P_NOVALIDATION);
					orderRet.put("managementReq",
							failureOrder.getString("managementReq"));
				}
				// 派工理由
				if (data.containsKey("dispatchReason")) {
					failureOrder.setValue("dispatchreason",
							data.getString("dispatchReason"),
							GWConstant.P_NOVALIDATION);
					orderRet.put("dispatchReason",
							failureOrder.getString("dispatchReason"));
				}
			} else if (SddqConstant.WO_STATUS_CXPG.equals(status)) {// 重新派工
				// 现场处理人
				if (data.containsKey("servEngineer")) {
					failureOrder.setValue("servEngineer",
							data.getString("servEngineer"),
							GWConstant.P_NOVALIDATION);
					orderRet.put("servEngineer",
							failureOrder.getString("servEngineer"));
				}
				// 派工理由
				if (data.containsKey("dispatchReason")) {
					failureOrder.setValue("dispatchreason",
							data.getString("dispatchReason"),
							GWConstant.P_NOVALIDATION);
					orderRet.put("dispatchReason",
							failureOrder.getString("dispatchReason"));
				}
			} else if (SddqConstant.WO_STATUS_CLZ.equals(status)) {// 处理中

				// 车号&车型
				if (data.containsKey("carNum") && data.containsKey("cmodel")) {
					failureOrder.setValue("carnum", data.getString("carNum"),
							GWConstant.P_NOVALIDATION);
					failureOrder.setValue("models", data.getString("cmodel"),
							GWConstant.P_NOVALIDATION);

					orderRet.put("carNum", failureOrder.getString("carnum"));
					orderRet.put("cmodel", failureOrder.getString("models"));

					failureOrder.setValue("assetnum",
							failureOrder.getString("ASSET.ASSETNUM"),
							GWConstant.P_NOVALIDATION);
					failureOrder.setValue("repairprocess",
							failureOrder.getString("ASSET.REPAIRPROCESS"),
							GWConstant.P_NOVALIDATION);
					failureOrder.setValue("modelproject",
							failureOrder.getString("ASSET.MODELS.MODELDESC"),
							GWConstant.P_NOVALIDATION);
					failureOrder.setValue("runKilometre",
							failureOrder.getString("ASSET.runKilometre"),
							GWConstant.P_NOVALIDATION);
					failureOrder.setValue("repairAfterKilometer", failureOrder
							.getString("ASSET.REPAIRAFTERKILOMETER"),
							GWConstant.P_NOVALIDATION);
					failureOrder.setValue("ownerCustomer",
							failureOrder.getString("ASSET.OWNERCUSTOMER"),
							GWConstant.P_NOVALIDATION);
					orderRet.put("assetNum", failureOrder.getString("assetNum"));
					orderRet.put("repairProcess",
							failureOrder.getString("repairprocess"));
					orderRet.put("modelProject",
							failureOrder.getString("modelproject"));
					orderRet.put("projectNnum",
							failureOrder.getString("projectnum"));
					orderRet.put("runKilometre",
							failureOrder.getString("runKilometre"));
					orderRet.put("repairAfterKilometer",
							failureOrder.getString("repairAfterKilometer"));
					orderRet.put("ownerCustomer",
							failureOrder.getString("ownerCustomer"));
				}

				// add start by Zhuhao-20190103
				// 处理非换件处理故障件字段
				String[] faultAttrs = { "faultComponentSqn", "faultCompLotNum",
						"faultCompItemNum", "faultCompAssetNum",
						"faultCompDealMode", "isMainFaultComp", "isNotice",
						"isTechAnalyze" };
				for (String faultAttr : faultAttrs) {
					if (data.containsKey(faultAttr)
							&& StringUtil.isStrNotEmpty(data
									.getString(faultAttr))) {

						failureOrder.setValue(faultAttr,
								data.getString(faultAttr),
								GWConstant.P_NOVALIDATION);
						orderRet.put(faultAttr,
								failureOrder.getString(faultAttr));

					}
				}
				// add end

				// 处理中可以编辑子表
				if (obj.containsKey("failureLib")) {
					JSONObject failureLibObj = MODIFYFAILURELIB(failureOrder,
							loginid, obj.getString("failureLib"));
					ret.put("failureLib", failureLibObj);
				}
				if (StringUtil.isStrEmpty(erpStatus)) {// 三包接口状态为空才能编辑子表

					if (obj.containsKey("updownRcd")) {
						JSONArray updownArr = obj.getJSONArray("updownRcd");
						JSONArray retArr = new JSONArray();
						for (int i = 0; i < updownArr.size(); i++) {
							MobileResult exResult = MODIFYEXCHANGERECORD(
									failureOrder, loginid, updownArr
											.getJSONObject(i).toJSONString());
							retArr.add(exResult.getData());
						}
						ret.put("updownRcd", retArr);
					}
					if (obj.containsKey("losspartRcd")) {
						JSONArray losspartArr = obj.getJSONArray("losspartRcd");
						JSONArray retArr = new JSONArray();
						for (int i = 0; i < losspartArr.size(); i++) {
							MobileResult lossResult = MODIFYJXTASKLOSSPART(
									failureOrder, loginid, losspartArr
											.getJSONObject(i).toJSONString());
							retArr.add(lossResult.getData());
						}
						ret.put("updownRcd", retArr);
					}
				} else {
					throw new MroException("已经获取三包信息无法修改上下车记录！");
				}
			} else if (SddqConstant.WO_STATUS_JSZGBH.equals(status)) {// 技术主管驳回
				if (obj.containsKey("failureLib")) {
					JSONObject failureLibObj = MODIFYFAILURELIB(failureOrder,
							loginid, obj.getString("failureLib"));
					ret.put("failureLib", failureLibObj);
				}
			}

			// 检查必填
			if (failureOrder.getString("carnum") == null
					|| StringUtil.isStrEmpty(failureOrder.getString("carnum"))) {
				throw new MroException("故障工单必填字段:车号 为空，请检查！");
			}
			if (failureOrder.getString("models") == null
					|| StringUtil.isStrEmpty(failureOrder.getString("models"))) {
				throw new MroException("故障工单必填字段:车型 为空，请检查！");
			}
			if (failureOrder.getString("callTime") == null
					|| StringUtil
							.isStrEmpty(failureOrder.getString("callTime"))) {
				throw new MroException("故障工单必填字段:客户来电时间 为空，请检查！");
			}
			if (failureOrder.getString("projectNum") == null
					|| StringUtil.isStrEmpty(failureOrder
							.getString("projectNum"))) {
				throw new MroException("故障工单必填字段:项目编号 为空，请检查！");
			}
			if (failureOrder.getString("servcompany") == null
					|| StringUtil.isStrEmpty(failureOrder
							.getString("servcompany"))) {
				throw new MroException("故障工单必填字段:服务单位 为空，请检查！");
			}
			if (failureOrder.getString("whichStation") == null
					|| StringUtil.isStrEmpty(failureOrder
							.getString("whichStation"))) {
				throw new MroException("故障工单必填字段:所属站点 为空，请检查！");
			}
			if (failureOrder.getString("servengineer") == null
					|| StringUtil.isStrEmpty(failureOrder
							.getString("servengineer"))) {
				throw new MroException("故障工单必填字段:现场处理人 为空，请检查！");
			}
			if (failureOrder.getString("managementreq") == null
					|| StringUtil.isStrEmpty(failureOrder
							.getString("managementreq"))) {
				throw new MroException("故障工单必填字段:安全管理要求 为空，请检查！");
			}
			if (failureOrder.getString("dispatchreason") == null
					|| StringUtil.isStrEmpty(failureOrder
							.getString("dispatchreason"))) {
				throw new MroException("故障工单必填字段:派工理由 为空，请检查！");
			}
			if (SddqConstant.WO_STATUS_CLZ.equals(status)) {
				if (failureOrder.getDate("OPERATETIME") != null
						&& failureOrder.getDate("COMPLETETIME") == null) {
					if (failureOrder.getString("isReadSafeReq") == null
							|| StringUtil.isStrEmpty(failureOrder
									.getString("isReadSafeReq"))) {
						throw new MroException("故障工单必填字段:是否已阅读安全管理要求 为空，请检查！");
					}
				}
			}

			if (data.containsKey("attachments")) {
				JSONArray attachemnts = data.getJSONArray("attachments");
				for (Object atta : attachemnts) {
					saveAttachment(failureOrder, atta.toString(), loginid);
				}
			}
			ret.put("failureOrd", orderRet);
			orderSet.save();

			// 工单保存后再次获取
			orderSet.setQueryWhere("ordernum='" + ordernum + "'");
			orderSet.reset();
			if (!orderSet.isEmpty()) {
				failureOrder = orderSet.getJpo();
			}

			// 修改故障信息中的数据
			String carnum = failureOrder.getString("CARNUM");
			String cmodel = failureOrder.getString("MODELS");
			String modelprj = failureOrder.getString("MODELPROJECT");
			String modeltype = failureOrder.getString("MODELS.PRODUCTLINE");
			String repairprocess = failureOrder.getString("REPAIRPROCESS");
			IJpoSet assetset = MroServer.getMroServer().getJpoSet("ASSET",
					MroServer.getMroServer().getSystemUserServer());
			assetset.setQueryWhere("ASSETLEVEL = 'ASSET' AND CARNO = '"
					+ carnum + "' and cmodel = '" + cmodel + "'");
			IJpoSet failureSet = MroServer.getMroServer().getJpoSet(
					"failurelib",
					MroServer.getMroServer().getSystemUserServer());
			failureSet.setQueryWhere("failurenum='"
					+ failureOrder.getString("failurenum") + "'");
			failureSet.reset();
			if (failureSet.count() > 0) {
				JSONObject failureLibObj = ret.getJSONObject("failureLib");
				IJpo failure = failureSet.getJpo();
				failure.setValue("CARMODELS", cmodel, GWConstant.P_NOVALIDATION);
				failure.setValue("CARNUM", carnum, GWConstant.P_NOVALIDATION);
				failure.setValue("MODELPROJECT", modelprj,
						GWConstant.P_NOVALIDATION);
				failure.setValue("MODELTYPE", modeltype,
						GWConstant.P_NOVALIDATION);
				failure.setValue("REPAIRPROCESS", repairprocess,
						GWConstant.P_NOVALIDATION);
				failureLibObj.put("carNum", carnum);
				failureLibObj.put("modelType", modeltype);
				failureLibObj.put("repairProcess", repairprocess);
				failureLibObj.put("cmodel", cmodel);
				if (!assetset.isEmpty()) {
					if (assetset.getJpo().getString("RUNKILOMETRE") != null) {
						failure.setValue("RUNMILEAGE", assetset.getJpo()
								.getString("RUNKILOMETRE"),
								GWConstant.P_NOVALIDATION);
						failureLibObj.put("runMileage",
								failure.getString("runMileage"));
					}
				}
				ret.put("failureLib", failureLibObj);
				failureSet.save();
			}
			/* 赋值结束 */

			if (obj.containsKey("attachments")) {
				JSONArray attachemnts = data.getJSONArray("attachments");
				for (Object atta : attachemnts) {
					saveAttachment(failureOrder, atta.toString(), loginid);
				}
			}
		} else {
			throw new MroException("工单:" + ordernum + "不存在！修改失败!");
		}
		result.setData(ret);
		return result;
	}

	/**
	 * 验证工单-反馈记录子表 <功能描述>
	 * 
	 * @param valiOrder
	 * @param json
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private JSONObject modifyValiFeedback(IJpo valiOrder, String json)
			throws MroException {
		// 将json字符串转换成json对象
		JSONObject data = JSONObject.parseObject(json);
		JSONObject ret = new JSONObject();
		String ordernum = valiOrder.getString("ordernum");
		IJpoSet feedbackSet = valiOrder.getJpoSet("VALIFEEDBACK");
		if (data.containsKey("valiFeedBackId")) {
			feedbackSet.setQueryWhere("valiFeedBackId='"
					+ data.getString("valiFeedBackId") + "'");
			feedbackSet.reset();
			if (feedbackSet.isEmpty()) {
				throw new MroException("无法根据ID："
						+ data.getString("valiFeedBackId") + "获取验证产品反馈信息");
			}
			IJpo feedback = feedbackSet.getJpo();
			if (data.containsKey("valicituation")) {
				feedback.setValue("valicituation",
						data.getString("valicituation"));
				ret.put("valicituation", data.getString("valicituation"));
			}
			if (data.containsKey("feedbackPerson")) {
				feedback.setValue("feedbackPerson",
						data.getString("feedbackPerson"));
				ret.put("feedbackPerson", data.getString("feedbackPerson"));
			}
			if (data.containsKey("feedbackTime")) {
				feedback.setValue("feedbackTime",
						data.getString("feedbackTime"));
				ret.put("feedbackTime", data.getString("feedbackTime"));
			}
		} else {
			IJpo feedback = feedbackSet.addJpo();
			feedback.setValue("ordernum", ordernum);
			if (data.containsKey("valicituation")) {
				feedback.setValue("valicituation",
						data.getString("valicituation"));
			}
			if (data.containsKey("feedbackPerson")) {
				feedback.setValue("feedbackPerson",
						data.getString("feedbackPerson"));
			}
			if (data.containsKey("feedbackTime")) {
				feedback.setValue("feedbackTime",
						data.getString("feedbackTime"));
			}
			// 默认为当前登录人、当前系统时间
			if (StringUtil.isStrEmpty(data.getString("feedbackPerson"))) {
				feedback.setValue("feedbackPerson", MroServer.getMroServer()
						.getSystemUserServer().getUserInfo().getLoginID());
			}
			if (StringUtil.isStrEmpty(data.getString("feedbackTime"))) {
				feedback.setValue("feedbackTime", MroServer.getMroServer()
						.getDate());
			}
			ret.put("valicituation", feedback.getString("valicituation"));
			ret.put("feedbackPerson", feedback.getString("feedbackPerson"));
			ret.put("feedbackTime", feedback.getString("feedbackTime"));
		}

		return ret;
	}

	/**
	 * /**
	 * 
	 * 编辑故障信息
	 * 
	 * @param order
	 *            父级Jop
	 * @param loginid
	 *            用户ID
	 * @param json
	 *            故障数据
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public JSONObject MODIFYFAILURELIB(IJpo order, String loginid, String json)
			throws Exception {
		// 将json字符串转换成json对象
		JSONObject data = JSONObject.parseObject(json);
		String status = order.getString("STATUS");
		JSONObject ret = new JSONObject();
		// 车型大类
		String productline = order.getString("MODELS.PRODUCTLINE");

		IJpoSet failureSet = order.getJpoSet("FAILURELIB");
		failureSet.setAppName("FAILUREORD");
		IJpo failure = failureSet.getJpo();

		String faultconseq = failure.getString("FAULTCONSEQ");

		// 是否已阅读安全管理要求
		if (order.getDate("OPERATETIME") != null) {
			if (data.containsKey("isReadSafeReq"))
				order.setValue("isReadSafeReq", data.getString("isReadSafeReq"));
		}

		// 可编辑字段
		List<String> tmpList = Arrays.asList("carSectionNum",
				"productNickName", "failureCode", "faultDesc", "preReasonAlys",
				"dealMeasure", "faultTime", "findProcess", "runningMode",
				"dealMethod", "faultQualit", "faultConseq", "faultLocation",
				"gdqj", "roadType", "failWeather", "noDataReason");
		ArrayList<String> attrList = new ArrayList<String>(tmpList);
		if (!"城轨".equals(productline) && !"动车".equals(productline)) {// 非城轨动车牵引吨位可编辑
			attrList.add("qyfzdw");
		}

		// 必填
		HashMap<String, String> requiredAttrs = new HashMap<String, String>();
		requiredAttrs.put("carSectionNum", "车厢号");
		requiredAttrs.put("failureCode", "故障品代码");
		requiredAttrs.put("faultTime", "故障发生时间");
		requiredAttrs.put("findProcess", "发生阶段");
		requiredAttrs.put("dealMethod", "处理方式");
		requiredAttrs.put("runningMode", "运行模式");
		requiredAttrs.put("faultQualit", "客户定责");
		requiredAttrs.put("faultConseq", "故障后果");
		requiredAttrs.put("analysisRepNeed", "客户是否需要分析报告");
		requiredAttrs.put("qyfzdw", "牵引吨位");
		requiredAttrs.put("roadType", "路况");
		requiredAttrs.put("failWeather", "天气");
		requiredAttrs.put("faultDesc", "故障现象");
		requiredAttrs.put("preReasonAlys", "初步分析");
		requiredAttrs.put("dealMeasure", "处理措施");

		String[] allAttrsnew = { "机破", "D类事故", "C类及以上事故", "下线", "清客", "救援",
				"安监" };
		if (StringUtil.isHaveStr(allAttrsnew, faultconseq)) {
			requiredAttrs.remove("analysisRepNeed");
			attrList.remove("analysisRepNeed");
		}
		if (SddqConstant.WO_STATUS_CLZ.equals(status)
				|| SddqConstant.WO_STATUS_KGYBH.equals(status)
				|| SddqConstant.WO_STATUS_SHBH.equals(status)
				|| SddqConstant.WO_STATUS_JSZGBH.equals(status)) {
			for (String attr : attrList) {
				if (data.containsKey(attr)) {
					failure.setValue(attr, data.getString(attr));
					ret.put(attr, failure.getString(attr));
				}
			}
			if (order.getDate("OPERATETIME") != null) {// 开始作业后必填字段
				if (failure.getString("CARSECTIONNUM") == null
						|| StringUtil.isStrEmpty(failure
								.getString("CARSECTIONNUM"))) {
					throw new MroException("必填字段:车厢号 为空，请检查！");
				}
				if (failure.getString("FAILURECODE") == null
						|| StringUtil.isStrEmpty(failure
								.getString("FAILURECODE"))) {
					throw new MroException("必填字段:故障品代码 为空，请检查！");
				}
			}
			if (order.getDate("COMPLETETIME") != null) {
				for (String required : requiredAttrs.keySet()) {
					if (failure.getString(required) == null
							|| StringUtil.isStrEmpty(failure
									.getString(required))) {
						throw new MroException("必填字段:"
								+ requiredAttrs.get(required) + " 为空，请检查！");
					}
				}
			}
		} else {

			throw new MroException("", "当前工单状态无法修改故障信息!");
		}

		return ret;
	}

	/**
	 * 编辑改造工单
	 * 
	 * @param loginid
	 * @param json
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public MobileResult UPDATETRANSORDER(String loginid, String json)
			throws Exception {
		MobileResult result = new MobileResult();
		JSONObject ret = new JSONObject();
		JSONObject orderRet = new JSONObject();
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("transOrder");

		if (!data.containsKey("orderNum")) {
			throw new MroException("必须字段:工单编号 为空！");
		}
		String ordernum = data.getString("orderNum");

		IJpoSet workorderSet = MroServer.getMroServer().getJpoSet("WORKORDER",
				MroServer.getMroServer().getSystemUserServer());
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setLoginID(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setUserName(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		// 设置应用名称
		workorderSet.setAppName("TRANSORDER");
		workorderSet.setQueryWhere("ordernum='" + ordernum + "'");
		workorderSet.reset();
		if (!workorderSet.isEmpty()) {
			IJpo transOrder = workorderSet.getJpo();
			String oldCarNum = transOrder.getString("carnum");
			String status = transOrder.getString("status");
			if (StringUtil.isStrEmpty(hasTaskAuth(transOrder, "TRANSORDER",
					loginid))) {
				throw new MroException("当前用户无法修改此工单");
			}
			if (SddqConstant.WO_STATUS_CLZ.equals(status)) {

				// 改造计划创建的工单无法修改车号
				if (transOrder.getBoolean("ISPLANCRAET")) {
					if (data.containsKey("carNum")) {
						if (!data.getString("carNum").equalsIgnoreCase(
								oldCarNum)) {// 车号发生变更
							throw new MroException("该改造工单为改造计划创建，无法修改车号！");
						}
					}
				}
				// 车号
				if (data.containsKey("carNum") && data.containsKey("assetNum")) {
					transOrder.setValue("carnum", data.getString("carNum"),
							GWConstant.P_NOVALIDATION);
					transOrder.setValue("assetNum", data.getString("assetNum"));

					transOrder.setValue("repairProcess",
							transOrder.getString("ASSET.REPAIRPROCESS"));
					transOrder.setValue("modelProject",
							transOrder.getString("ASSET.MODELS.MODELDESC"));
					transOrder.setValue("projectNum",
							transOrder.getString("ASSET.PROJECTNUM"));
				}
				String transPlanNum = transOrder.getString("planNum");
				IJpoSet transPlanSet = MroServer.getMroServer().getSysJpoSet(
						"TRANSPLAN", "TRANSPLANNUM='" + transPlanNum + "'");
				if (transPlanSet.count() < 1) {
					throw new MroException("改造工单：" + ordernum
							+ "对应改造计划不存在，请检查！");
				}
				IJpoSet distOrderSet = transPlanSet.getJpo(0).getJpoSet(
						"TRANSDIST");
				// 首台车与首台车状况的可编辑标识
				boolean isFirstFlag = false;
				for (int i = 0; i < distOrderSet.count(); i++) {
					if (transOrder.getId() == distOrderSet.getJpo(i).getId()) {
						continue;
					}
					if (distOrderSet.getJpo(i).getBoolean("isFirst")) {
						isFirstFlag = true;
						break;
					}
				}
				if (!isFirstFlag) {
					if (data.containsKey("isFirst")) {
						transOrder.setValue("isFirst",
								data.getString("isFirst"));
						orderRet.put("isFirst", transOrder.getString("isFirst"));
					}
					// 是首台车，首台车改造情况必填
					if (transOrder.getBoolean("isFirst")) {
						if (data.containsKey("firstCondition")) {
							transOrder.setValue("firstCondition",
									data.getString("firstCondition"));
							orderRet.put("firstCondition",
									transOrder.getString("firstCondition"));
						}
						if (StringUtil.isStrEmpty(transOrder
								.getString("firstCondition"))) {
							throw new MroException("改造工单首台车改造情况 必填，请检查");
						}
					}
				}
				// 实际开始时间
				if (data.containsKey("actStartDate")) {
					transOrder.setValue("actStartDate",
							data.getString("actStartDate"));
					orderRet.put("actStartDate",
							transOrder.getString("actStartDate"));
				}
				// 实际完成时间
				if (data.containsKey("actCompleteDate")) {
					transOrder.setValue("actCompleteDate",
							data.getString("actCompleteDate"));
					orderRet.put("actCompleteDate",
							transOrder.getString("actCompleteDate"));
				}
				// 实施工程师
				if (data.containsKey("engineers")) {
					transOrder.setValue("engineers",
							data.getString("engineers"));
					orderRet.put("engineers", transOrder.getString("engineers"));
				}
				// 检查员
				if (data.containsKey("checkPersons")) {
					transOrder.setValue("checkPersons",
							data.getString("checkPersons"));
					orderRet.put("checkPersons",
							transOrder.getString("checkPersons"));
				}
				// 备注
				if (data.containsKey("remark")) {
					transOrder.setValue("remark", data.getString("remark"));
					orderRet.put("remark", transOrder.getString("remark"));
				}

				// lookup中字段
				transOrder.setValue("repairprocess",
						transOrder.getString("ASSET.REPAIRPROCESS"));
				transOrder.setValue("modelproject",
						transOrder.getString("ASSET.MODELS.MODELDESC"));
				transOrder.setValue("projectnum",
						transOrder.getString("ASSET.PROJECTNUM"));

				// 检查必填
				if (transOrder.getField("carnum").isValueChanged()) {
					if (transOrder.getString("carnum") == null
							|| StringUtil.isStrEmpty(transOrder
									.getString("carnum"))) {
						throw new MroException("必填字段:车号 为空，请检查！");
					}
				}
				if (transOrder.getField("CHECKPERSONS").isValueChanged()) {
					if (transOrder.getString("CHECKPERSONS") == null
							|| StringUtil.isStrEmpty(transOrder
									.getString("CHECKPERSONS"))) {
						throw new MroException("必填字段:检查员 为空，请检查！");
					}
				}
				workorderSet.save();

				// 工单保存后再次获取，给子表使用
				workorderSet.setQueryWhere("ordernum='" + ordernum + "'");
				workorderSet.reset();
				if (!workorderSet.isEmpty()) {
					transOrder = workorderSet.getJpo();
				}

				if (obj.containsKey("updownRcd")) {
					JSONArray updownArr = obj.getJSONArray("updownRcd");
					JSONArray retArr = new JSONArray();
					for (int i = 0; i < updownArr.size(); i++) {
						MobileResult exResult = MODIFYEXCHANGERECORD(
								transOrder, loginid, updownArr.getJSONObject(i)
										.toJSONString());
						retArr.add(exResult.getData());
					}
					ret.put("updownRcd", retArr);
				}
				if (obj.containsKey("losspartRcd")) {
					JSONArray losspartArr = obj.getJSONArray("losspartRcd");
					JSONArray retArr = new JSONArray();
					for (int i = 0; i < losspartArr.size(); i++) {
						MobileResult lossResult = MODIFYJXTASKLOSSPART(
								transOrder, loginid,
								losspartArr.getJSONObject(i).toJSONString());
						retArr.add(lossResult.getData());
					}
					ret.put("updownRcd", retArr);
				}
				if (obj.containsKey("attachments")) {
					JSONArray attachemnts = data.getJSONArray("attachments");
					for (Object atta : attachemnts) {
						saveAttachment(transOrder, atta.toString(), loginid);
					}
				}
				workorderSet.save();
			} else {
				throw new MroException("当前状态无法修改此改造工单");
			}
		} else {
			throw new MroException("工单:" + ordernum + "不存在！修改失败!");
		}
		result.setData(ret);
		return result;
	}

	/**
	 * 
	 * 故障工单扫码
	 * 
	 * @param loginid
	 * @param json
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public MobileResult SCANFAILUREORD(String loginid, String json)
			throws MroException, SQLException {
		MobileResult result = new MobileResult();
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("failureOrd");
		// 获取故障工单以及上下车子表
		String ordernum = data.getString("orderNum");
		IJpoSet orderSet = MroServer.getMroServer().getSysJpoSet("workorder",
				"ordernum='" + ordernum + "' and type='故障'");
		if (orderSet.count() < 1) {
			throw new MroException("工单:" + ordernum + "不存在！查询失败!");
		}
		IJpo order = orderSet.getJpo();
		IJpoSet exchangeSet = order.getJpoSet("exchangerecord");

		// 获取该条上下车记录
		IJpo currJpo = null;
		if (data.containsKey("exchangeRecordId")) {
			String exchangeRecordId = data.getString("exchangeRecordId");
			IJpoSet currSet = MroServer.getMroServer().getSysJpoSet(
					"exchangeRecord",
					"exchangeRecordId='" + exchangeRecordId + "'");
			if (currSet.count() < 1) {
				throw new MroException("编号为:" + exchangeRecordId
						+ "的上下车记录不存在！查询失败!");
			}
			currJpo = currSet.getJpo();
		} else {
			currJpo = exchangeSet.getJpoInstance();
		}

		// 上车扫码或下车扫码
		String scanCol = data.getString("scanCol");
		String scanVal = data.getString("scanVal");
		if ("DOWN".equals(scanCol)) {
			// 下车件的getlist
			String assetnumnew = WorkorderUtil.serversxitemnum(exchangeSet,
					currJpo);
			String assetnum = order.getString("assetnum");
			String listwhere = "sqn in (select sqn from asset start with assetnum='"
					+ assetnum
					+ "' connect by parent = PRIOR assetnum)  and type='2' ";
			if (StringUtil.isStrNotEmpty(assetnumnew)) {
				listwhere += " and assetnum not in(" + assetnumnew + ")";
			}

			// 序列号模糊，精确查询批次号、物料编码
			String assetwhere = " and (sqn like '%" + scanVal
					+ "%' or lotnum='" + scanVal + "' or itemnum='" + scanVal
					+ "')";
			IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet("asset",
					listwhere + assetwhere);
			JSONArray arr = new JSONArray();
			for (int i = 0; i < assetSet.count(); i++) {
				JSONObject subobj = new JSONObject();
				subobj.put("sqn", assetSet.getJpo(i).getString("SQN"));
				subobj.put("lotNum", assetSet.getJpo(i).getString("LOTNUM"));
				subobj.put("itemNum", assetSet.getJpo(i).getString("ITEMNUM"));
				subobj.put("assetNum", assetSet.getJpo(i).getString("ASSETNUM"));
				subobj.put("faultPosition", assetSet.getJpo(i).getString("LOC"));
				subobj.put("softVersion",
						assetSet.getJpo(i).getString("SOFTVERSION"));
				subobj.put("itemDesc",
						assetSet.getJpo(i).getString("ITEM.DESCRIPTION"));
				arr.add(subobj);
			}
			result.setData(arr);
		} else if ("UP".equals(scanCol)) {// 上车件扫码
			// 下车物料编码
			String itemNum = data.getString("itemNum");
			if (!StringUtils.isEmpty(itemNum)) {
				// 可替换物料
				String itemnums = ItemUtil.getAltItemnums(itemNum);
				String locationWhere = "";
				// 是否使用客户料
				String isCustItem = data.getString("isCustItem");
				// 工单所属站点
				String locsite = order.getString("WHICHSTATION");

				// 重复物料
				String assetnumnew = WorkorderUtil.serverupitemnum(exchangeSet,
						currJpo);

				if ("1".equals(isCustItem)) {// 使用客户料

					locationWhere = "erploc='" + ItemUtil.ERPLOC_QTDCL
							+ "' and STOREROOMLEVEL in('"
							+ ItemUtil.STOREROOMLEVEL_XCK + "','"
							+ ItemUtil.STOREROOMLEVEL_XCZDK
							+ "') and locationtype='"
							+ ItemUtil.LOCATIONTYPE_CG + "' and jxorfw='"
							+ ItemUtil.JXORFW_FW + "' and locsite ='" + locsite
							+ "'";

				} else {
					locationWhere = "erploc='" + ItemUtil.ERPLOC_1020
							+ "' and  STOREROOMLEVEL in('"
							+ ItemUtil.STOREROOMLEVEL_XCZDK + "','"
							+ ItemUtil.STOREROOMLEVEL_XCK
							+ "') and locationtype='"
							+ ItemUtil.LOCATIONTYPE_CG + "' and jxorfw='"
							+ ItemUtil.JXORFW_FW + "' and locsite ='" + locsite
							+ "'";
				}
				String sql = "itemnum in("
						+ itemnums
						+ ") and type in( '1','3') and location in (select location from locations where "
						+ locationWhere + " )";
				if (StringUtil.isStrNotEmpty(assetnumnew)) {
					sql += " and assetnum not in(" + assetnumnew + ")";
				}

				// 序列号模糊，精确查询批次号、物料编码
				String assetwhere = " and (sqn like '%" + scanVal
						+ "%' or lotnum='" + scanVal + "' or itemnum='"
						+ scanVal + "')";
				IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
						"asset", sql + assetwhere);
				JSONArray arr = new JSONArray();
				for (int i = 0; i < assetSet.count(); i++) {
					JSONObject subobj = new JSONObject();
					subobj.put("newSqn", assetSet.getJpo(i).getString("SQN"));
					subobj.put("newLotNum",
							assetSet.getJpo(i).getString("LOTNUM"));
					subobj.put("newItemNum",
							assetSet.getJpo(i).getString("ITEMNUM"));
					subobj.put("newAssetNum",
							assetSet.getJpo(i).getString("ASSETNUM"));
					subobj.put("newLoc",
							assetSet.getJpo(i).getString("LOCATION"));
					subobj.put("newSoftVersion",
							assetSet.getJpo(i).getString("SOFTVERSION"));
					subobj.put("newBinNum",
							assetSet.getJpo(i).getString("BINNUM"));
					subobj.put("NewItemDesc",
							assetSet.getJpo(i).getString("ITEM.DESCRIPTION"));
					arr.add(subobj);
				}
				result.setData(arr);
			}
		} else {
			result.setCode("400");
			result.setData("参数有误，请检查！");
		}
		return result;
	}

	/**
	 * 
	 * 改造工单扫码
	 * 
	 * @param loginid
	 * @param json
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public MobileResult SCANTRANSORDER(String loginid, String json)
			throws Exception {
		MobileResult result = new MobileResult();
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("transOrder");
		// 获取故障工单以及上下车子表
		String ordernum = data.getString("orderNum");
		IJpoSet orderSet = MroServer.getMroServer().getSysJpoSet("workorder",
				"ordernum='" + ordernum + "' and type='改造'");
		if (orderSet.count() < 1) {
			throw new MroException("工单:" + ordernum + "不存在！查询失败!");
		}
		IJpo order = orderSet.getJpo();
		IJpoSet exchangeSet = order.getJpoSet("exchangerecord");

		// 获取该条上下车记录
		IJpo currJpo = null;
		if (data.containsKey("exchangeRecordId")) {
			String exchangeRecordId = data.getString("exchangeRecordId");
			IJpoSet currSet = MroServer.getMroServer().getSysJpoSet(
					"exchangeRecord",
					"exchangeRecordId='" + exchangeRecordId + "'");
			if (currSet.count() < 1) {
				throw new MroException("编号为:" + exchangeRecordId
						+ "的上下车记录不存在！查询失败!");
			}
			currJpo = currSet.getJpo();
		} else {
			currJpo = exchangeSet.getJpoInstance();
		}

		// 上车扫码或下车扫码
		String scanCol = data.getString("scanCol");
		String scanVal = data.getString("scanVal");
		if ("DOWN".equals(scanCol)) {
			// 下车件
			// FldTaskOrderSwapSqn中getlist方法，改造工单部分
			String noticenum = order.getString("NOTICENUM");
			IJpoSet transcontentSet = MroServer.getMroServer().getSysJpoSet(
					"TRANSCONTENT");
			transcontentSet.setQueryWhere("transnoticenum='" + noticenum + "'");
			String listwhere = "";
			if (!transcontentSet.isEmpty()) {
				String previousprdnum = transcontentSet.getJpo().getString(
						"PREVIOUSPRDNUM");

				String assetnumnew = WorkorderUtil.serversxitemnum(exchangeSet,
						currJpo);

				// 上下车记录
				String assetnum = order.getString("assetnum");
				listwhere = "sqn in (select sqn from asset start with assetnum='"
						+ assetnum
						+ "' connect by parent = PRIOR assetnum)  and type='2' and itemnum='"
						+ previousprdnum + "'";
				if (StringUtil.isStrNotEmpty(assetnumnew)) {
					listwhere += " and assetnum not in(" + assetnumnew + ")";
				}
			} else {
				listwhere = "1=2";
			}

			// 序列号模糊，精确查询批次号、物料编码
			String assetwhere = " and (sqn like '%" + scanVal
					+ "%' or lotnum='" + scanVal + "' or itemnum='" + scanVal
					+ "')";
			IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet("asset",
					listwhere + assetwhere);
			JSONArray arr = new JSONArray();
			for (int i = 0; i < assetSet.count(); i++) {
				JSONObject subobj = new JSONObject();
				subobj.put("sqn", assetSet.getJpo(i).getString("SQN"));
				subobj.put("lotNum", assetSet.getJpo(i).getString("LOTNUM"));
				subobj.put("itemNum", assetSet.getJpo(i).getString("ITEMNUM"));
				subobj.put("assetNum", assetSet.getJpo(i).getString("ASSETNUM"));
				subobj.put("faultPosition", assetSet.getJpo(i).getString("LOC"));
				subobj.put("softVersion",
						assetSet.getJpo(i).getString("SOFTVERSION"));
				subobj.put("itemDesc",
						assetSet.getJpo(i).getString("ITEM.DESCRIPTION"));
				arr.add(subobj);
			}
			result.setData(arr);
		} else if ("UP".equals(scanCol)) {// 上车件扫码
			// FldTaskOrderSwapNewSqn的getlist

			// 下车物料编码
			String itemNum = data.getString("itemNum");
			String noticenum = order.getString("NOTICENUM");

			if (!StringUtils.isEmpty(itemNum)) {
				IJpoSet transContentSet = MroServer.getMroServer()
						.getSysJpoSet(
								"TRANSCONTENT",
								"transnoticenum='" + noticenum
										+ "' and PREVIOUSPRDNUM='" + itemNum
										+ "'");// 改造内容set
				String sql = "1=1";
				if (!transContentSet.isEmpty()) {

					IJpo content = transContentSet.getJpo(0);
					String itemnums = "'" + content.getString("NEWSPRDNUM")
							+ "'";// 改造后物料编码
					String assetnumnew = WorkorderUtil.serverupitemnum(
							exchangeSet, currJpo);
					// 工单所属站点
					String locsite = order.getString("WHICHSTATION");

					String locationWhere = "erploc='" + ItemUtil.ERPLOC_QTGZ
							+ "' and  STOREROOMLEVEL in('"
							+ ItemUtil.STOREROOMLEVEL_XCK + "','"
							+ ItemUtil.STOREROOMLEVEL_XCZDK + "') and jxorfw='"
							+ ItemUtil.JXORFW_FW + "' and locationtype='"
							+ ItemUtil.LOCATIONTYPE_CG + "' and locsite ='"
							+ locsite + "'";

					sql = "itemnum in("
							+ itemnums
							+ ") and type in( '1','3') and location in (select location from locations where "
							+ locationWhere + " )";
					if (StringUtil.isStrNotEmpty(assetnumnew)) {
						sql += " and assetnum not in(" + assetnumnew + ")";
					}
				} else {
					sql = "1=2";
				}

				// 序列号模糊，精确查询批次号、物料编码
				String assetwhere = " and (sqn like '%" + scanVal
						+ "%' or lotnum='" + scanVal + "' or itemnum='"
						+ scanVal + "')";
				IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
						"asset", sql + assetwhere);
				JSONArray arr = new JSONArray();
				for (int i = 0; i < assetSet.count(); i++) {
					JSONObject subobj = new JSONObject();
					subobj.put("newSqn", assetSet.getJpo(i).getString("SQN"));
					subobj.put("newLotNum",
							assetSet.getJpo(i).getString("LOTNUM"));
					subobj.put("newItemNum",
							assetSet.getJpo(i).getString("ITEMNUM"));
					subobj.put("newAssetNum",
							assetSet.getJpo(i).getString("ASSETNUM"));
					subobj.put("newLoc",
							assetSet.getJpo(i).getString("LOCATION"));
					subobj.put("newSoftVersion",
							assetSet.getJpo(i).getString("SOFTVERSION"));
					subobj.put("newBinNum",
							assetSet.getJpo(i).getString("BINNUM"));
					subobj.put("newItemDesc",
							assetSet.getJpo(i).getString("ITEM.DESCRIPTION"));
					arr.add(subobj);
				}
				result.setData(arr);
			}
		} else {
			result.setCode("400");
			result.setData("参数有误，请检查！");
		}
		return result;
	}

	/**
	 * 
	 * 装箱单扫码
	 * 
	 * @param loginid
	 * @param json
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public MobileResult SCANZXTRANSFER(String loginid, String json)
			throws Exception {
		MobileResult result = new MobileResult();
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("zxTransfer");

		String transfernum = data.getString("transferNum");
		String scanVal = data.getString("scanVal");
		// 扫码录入或扫码接收
		String scanCol = data.getString("scanCol");
		String issueStoreRoom = data.getString("issueStoreRoom");
		JSONArray returnArr = new JSONArray();
		if ("INPUT".equals(scanCol)) {
			// 有故障工单编号时
			if (!StringUtil.isStrEmpty(data.getString("taskNum"))) {
				// 从工单上下车记录中选择物料
				String listSql = " (itemnum,sqn)  in (select itemnum,sqn from EXCHANGERECORD where TASKORDERNUM='"
						+ data.getString("taskNum")
						+ "') and (itemnum='"
						+ scanVal + "' or sqn like '%" + scanVal + "%')";
				IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
						"ASSET", listSql);
				for (int i = 0; i < assetSet.count(); i++) {
					JSONObject oneObj = new JSONObject();
					oneObj.put("assetNum",
							assetSet.getJpo(i).getString("assetNum"));
					oneObj.put("itemNum",
							assetSet.getJpo(i).getString("itemnum"));
					oneObj.put("sqn", assetSet.getJpo(i).getString("sqn"));
					oneObj.put("description",
							assetSet.getJpo(i).getString("ITEM.DESCRIPTION"));
					returnArr.add(oneObj);
				}
			} else {
				// 无故障编号时，需要发出库房
				// 从库房库存中选择物料
				String inveSql = "itemnum in (select itemnum from SYS_INVENTORY where LOCATION='"
						+ issueStoreRoom
						+ "' and curbal>0) and (itemnum='"
						+ scanVal + "' or sqn like '%" + scanVal + "%')";
				String listSql = " location='" + issueStoreRoom + "'";
				IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
						"ASSET", listSql + " and " + inveSql);

				for (int i = 0; i < assetSet.count(); i++) {
					JSONObject oneObj = new JSONObject();
					oneObj.put("assetNum",
							assetSet.getJpo(i).getString("assetNum"));
					oneObj.put("itemNum",
							assetSet.getJpo(i).getString("itemnum"));
					oneObj.put("sqn", assetSet.getJpo(i).getString("sqn"));
					oneObj.put("description",
							assetSet.getJpo(i).getString("ITEM.DESCRIPTION"));
					returnArr.add(oneObj);
				}
			}
			// 库存余量查询批次信息
			String invSql = " itemnum in (select itemnum from sys_item where isloterp=1) and storeroom ='"
					+ issueStoreRoom
					+ "' and (itemnum='"
					+ scanVal
					+ "' or lotnum='" + scanVal + "')";
			IJpoSet invSet = MroServer.getMroServer().getSysJpoSet("invblance",
					invSql);

			for (int i = 0; i < invSet.count(); i++) {
				JSONObject oneObj = new JSONObject();
				oneObj.put("itemNum", invSet.getJpo(i).getString("itemnum"));
				oneObj.put("lotNum", invSet.getJpo(i).getString("lotNum"));
				oneObj.put("actLotQty", invSet.getJpo(i)
						.getString("PHYSCNTQTY"));
				oneObj.put("canUseQty", invSet.getJpo(i).getString("KYQTY"));
				returnArr.add(oneObj);
			}
			result.setData(returnArr);
		} else if ("RECEIVE".equals(scanCol)) {
			IJpoSet transferSet = MroServer.getMroServer().getSysJpoSet(
					"transfer", "transfernum='" + transfernum + "'");
			if (!transferSet.isEmpty()) {
				// 从装箱单所属装箱单行中查询物料编码、批次号、序列号
				IJpoSet transferLineSet = MroServer.getMroServer()
						.getSysJpoSet(
								"TRANSFERLINE",
								"TRANSFERNUM='" + transfernum
										+ "' and (itemnum='" + scanVal
										+ "' or sqn like '%" + scanVal
										+ "%' or lotnum='" + scanVal + "')");

				for (int i = 0; i < transferLineSet.count(); i++) {
					JSONObject oneObj = new JSONObject();
					oneObj.put("transferLineId", transferLineSet.getJpo(i)
							.getString("TRANSFERLINEID"));
					oneObj.put("transferLineNum", transferLineSet.getJpo(i)
							.getString("TRANSFERLINENUM"));
					oneObj.put("itemNum",
							transferLineSet.getJpo(i).getString("itemnum"));
					oneObj.put("sqn", transferLineSet.getJpo(i)
							.getString("sqn"));
					oneObj.put("status",
							transferLineSet.getJpo(i).getString("status"));
					oneObj.put("lotNum",
							transferLineSet.getJpo(i).getString("lotnum"));
					oneObj.put("description", transferLineSet.getJpo(i)
							.getString("ITEM.DESCRIPTION"));
					returnArr.add(oneObj);
				}
				result.setData(returnArr);
			} else {
				throw new MroException("装箱单:" + transfernum + " 不存在，请检查！");
			}
		} else {
			throw new MroException("参数有误，请检查！");
		}
		return result;
	}

	/**
	 * 
	 * 调拨转库单扫码
	 * 
	 * @param loginid
	 * @param json
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public MobileResult SCANCONVERTLOC(String loginid, String json)
			throws Exception {
		MobileResult result = new MobileResult();
		// 将json字符串转换成json对象
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("convertLoc");

		String convertLocNum = data.getString("convertLocNum");
		String scanVal = data.getString("scanVal");
		// 扫码录入或扫码接收--调拨转库单只有接收
		String scanCol = data.getString("scanCol");
		JSONArray returnArr = new JSONArray();

		if ("RECEIVE".equals(scanCol)) {
			IJpoSet convertlocSet = MroServer.getMroServer().getSysJpoSet(
					"convertloc", "convertLocNum='" + convertLocNum + "'");
			if (!convertlocSet.isEmpty()) {
				// 从调拨转库单所属装箱单行中查询物料编码、批次号、序列号
				IJpoSet convertlocLineSet = MroServer.getMroServer()
						.getSysJpoSet(
								"CONVERTLOCLINE",
								"CONVERTLOCNUM='" + convertLocNum
										+ "' and (itemnum='" + scanVal
										+ "' or sqn like '%" + scanVal
										+ "%' or lotnum='" + scanVal + "')");

				for (int i = 0; i < convertlocLineSet.count(); i++) {
					JSONObject oneObj = new JSONObject();
					oneObj.put("convertLocLineId", convertlocLineSet.getJpo(i)
							.getString("convertLocLineID"));
					oneObj.put("convertLocLineNum", convertlocLineSet.getJpo(i)
							.getString("convertlocLineNUM"));
					oneObj.put("itemNum", convertlocLineSet.getJpo(i)
							.getString("itemnum"));
					oneObj.put("itemDesc", convertlocLineSet.getJpo(i)
							.getString("itemDesc"));
					oneObj.put("sqn",
							convertlocLineSet.getJpo(i).getString("sqn"));
					oneObj.put("lotNum",
							convertlocLineSet.getJpo(i).getString("lotnum"));
					oneObj.put("description", convertlocLineSet.getJpo(i)
							.getString("ITEMDESC"));
					oneObj.put("orderUnit", convertlocLineSet.getJpo(i)
							.getString("ORDERUNIT"));
					oneObj.put("projNum", convertlocLineSet.getJpo(i)
							.getString("projNum"));
					oneObj.put("location", convertlocLineSet.getJpo(i)
							.getString("location"));
					oneObj.put("locationDesc", convertlocLineSet.getJpo(i)
							.getString("LOCATIONIX.DESCRIPTION"));
					oneObj.put("binNum",
							convertlocLineSet.getJpo(i).getString("binNum"));
					oneObj.put("binDesc", convertlocLineSet.getJpo(i)
							.getString("BINNUMIX.DESCRIPTION"));
					oneObj.put("vgbel",
							convertlocLineSet.getJpo(i).getString("vgbel"));
					oneObj.put("pstyv",
							convertlocLineSet.getJpo(i).getString("pstyv"));
					oneObj.put("werks",
							convertlocLineSet.getJpo(i).getString("werks"));
					oneObj.put("status",
							convertlocLineSet.getJpo(i).getString("status"));
					oneObj.put("yjsQty",
							convertlocLineSet.getJpo(i).getString("yjsQty"));
					oneObj.put("bwart",
							convertlocLineSet.getJpo(i).getString("bwart"));
					oneObj.put("qty",
							convertlocLineSet.getJpo(i).getString("qty"));
					oneObj.put("posNr",
							convertlocLineSet.getJpo(i).getString("posNr"));
					oneObj.put("vgpos",
							convertlocLineSet.getJpo(i).getString("vgpos"));
					oneObj.put("lfDat",
							convertlocLineSet.getJpo(i).getString("lfDat"));
					if (convertlocLineSet.getJpo(i).getBoolean("islocbin")) {
						oneObj.put("isLocbin", 1);
					} else {
						oneObj.put("isLocbin", 0);
					}
					if (isItem(
							convertlocLineSet.getJpo(i).getString("itemnum"),
							"ISTURNOVERERP")) {
						oneObj.put("isTurnOverErp", 1);
					} else {
						oneObj.put("isTurnOverErp", 0);
					}
					if (isItem(
							convertlocLineSet.getJpo(i).getString("itemnum"),
							"ISLOTERP")) {
						oneObj.put("isLotErp", 1);
					} else {
						oneObj.put("isLotErp", 0);
					}
					returnArr.add(oneObj);
				}
				result.setData(returnArr);
			} else {
				throw new MroException("调拨转库单:" + convertLocNum + " 不存在，请检查！");
			}
		} else {
			throw new MroException("参数有误，请检查！");
		}

		return result;
	}

	// 判断是否周转件等

	/**
	 * 
	 * 物料属性
	 * 
	 * @param itemnum
	 * @param isCol
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static boolean isItem(String itemnum, String isCol)
			throws MroException {
		String sqlwhere = "" + isCol + "='1' and itemnum='" + itemnum + "'";
		IJpoSet set = MroServer.getMroServer().getSysJpoSet("sys_item",
				sqlwhere);
		if (set.count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否仓位管理等 <功能描述>
	 * 
	 * @param loc
	 * @param isCol
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static boolean isLoc(String loc, String isCol) throws MroException {
		String sqlwhere = "" + isCol + "='1' and location='" + loc + "'";
		IJpoSet set = MroServer.getMroServer().getSysJpoSet("locations",
				sqlwhere);
		if (set.count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	private String getItemDescription(String itemNum) throws MroException {
		IJpoSet set = MroServer.getMroServer().getSysJpoSet("sys_item",
				"itemnum='" + itemNum + "'");
		if (set.count() > 0) {
			return set.getJpo().getString("DESCRIPTION");
		} else {
			return "";
		}

	}

	private void saveAttachment(IJpo jpo, String uniqueId, String loginid)
			throws MroException {
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setLoginID(loginid);
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setUserName(loginid);
		// 唯一id在这个表中
		IJpoSet docInfoSet = MroServer.getMroServer().getSysJpoSet(
				"SYS_DOCINFO", "DOCUMENT='" + uniqueId + "'");
		String infoId = "";
		if (!docInfoSet.isEmpty()) {
			IJpo docInfo = docInfoSet.getJpo();
			docInfo.setValue("changeby", loginid, GWConstant.P_NOVALIDATION);
			docInfo.setValue("createby", loginid, GWConstant.P_NOVALIDATION);
			infoId = String.valueOf(docInfo.getId());
			docInfoSet.save();
		}

		IJpoSet docSet = MroServer.getMroServer().getSysJpoSet("SYS_DOCLINKS",
				"DOCUMENT='" + uniqueId + "'");
		if (!docSet.isEmpty()) {
			IJpo doc = docSet.getJpo();
			doc.setValue("ownertable", jpo.getName(), GWConstant.P_NOVALIDATION);
			doc.setValue("ownerid", jpo.getId(), GWConstant.P_NOVALIDATION);
			doc.setValue("docinfoid", infoId, GWConstant.P_NOVALIDATION);
			doc.setValue("changeby", loginid, GWConstant.P_NOVALIDATION);
			doc.setValue("createby", loginid, GWConstant.P_NOVALIDATION);
			docSet.save();
		}
	}

	/**
	 * 
	 * 判断当前登录人是否是工作流的当前的执行人
	 * 
	 * @param jpo
	 * @parem loginId 当前登录用户id
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public static boolean isCurUser(IJpo jpo, String loginId)
			throws MroException {
		boolean flag = false;
		if (jpo != null) {
			IJpoSet jposet = MroServer.getMroServer().getSysJpoSet(
					"act_app_info",
					"BUSINESSKEY = '" + jpo.getId() + "' and APP='"
							+ jpo.getAppName() + "'");
			if (!jposet.isEmpty()) {
				IJpoSet taskJpoSet = MroServer.getMroServer().getSysJpoSet(
						"act_ru_task",
						"PROC_INST_ID_ = '"
								+ jposet.getJpo(0).getString("PROCINSTID")
								+ "'");
				if (!taskJpoSet.isEmpty()) {
					for (int index = 0; index < taskJpoSet.count(); index++) {
						IJpo task = taskJpoSet.getJpo(index);
						if (loginId.equals(task.getString("ASSIGNEE_"))) {
							flag = true;
							break;
						}
					}
					return flag;
				} else {
					return flag;
				}
			} else {
				return flag;
			}
		} else {
			return flag;
		}
	}

}
