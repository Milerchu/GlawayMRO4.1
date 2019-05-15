package com.glaway.mro.app.system.workflow.util;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.TaskFormDataImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.glaway.mro.app.system.role.data.Role;
import com.glaway.mro.app.system.workflow.data.WfInfo;
import com.glaway.mro.app.system.workflow.data.WfInfoSet;
import com.glaway.mro.app.system.workflow.data.WfVersion;
import com.glaway.mro.app.system.workflow.data.WfVersionSet;
import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 工作流操作类
 * 
 * @author hyhe
 * @version [版本号, 2016-4-19]
 * @since [产品/模块版本]
 */
public final class WfControlUtil {
	private static WfControlUtil wfCtrl = null;;

	private WfControlUtil() {
	}

	public synchronized static WfControlUtil getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new WfControlUtil();
		}
		return wfCtrl;
	}

	/**
	 * 
	 * 初始化工作流签名选项(SYS_SIGOPTION)
	 * 
	 * @param appBean
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void initSigOption(AppBean appBean) throws MroException {
		addSigOption(appBean, WFConstant.STARTWF);
		addSigOption(appBean, WFConstant.STOPWF);
		addSigOption(appBean, WFConstant.HISTORYWF);
		addSigOption(appBean, WFConstant.ASSIGNWF);
		addSigOption(appBean, WFConstant.VIEWWF);
	}

	/**
	 * 
	 * 初始化工作流选择操作菜单
	 * 
	 * @param appBean
	 * @throws RemoteException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void initMenuOption(AppBean appBean) throws MroException {

		addMenuOption(appBean, WFConstant.STARTWF, 10, null);
		addMenuOption(appBean, WFConstant.STOPWF, 20, null);
		addMenuOption(appBean, WFConstant.HISTORYWF, 30, null);
		addMenuOption(appBean, WFConstant.ASSIGNWF, 40, null);
		addMenuOption(appBean, WFConstant.VIEWWF, 50, null);
	}

	/**
	 * 
	 * 初始化工作流默认签名选项
	 * 
	 * @param appBean
	 * @param sigName
	 * @param sigDescKey
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void addSigOption(AppBean appBean, String sigName)
			throws MroException {

		IJpoSet addSigOptionSet = appBean.getJpo().getExistingJpoSet(
				"$AppSupportAdd");
		if (addSigOptionSet == null) {
			addSigOptionSet = appBean.getJpo().getJpoSet("$AppSupportAdd",
					"SYS_SIGOPTION", "1=2");
		}

		IJpo addSigOption = addSigOptionSet.addJpo();
		addSigOption.setValue("APP", appBean.getJpo().getString("APP"));
		addSigOption.setValue("OPTIONNAME", sigName);
		addSigOption.setValue("ESIGENABLED", 0);
		addSigOption.setValue("VISIBLE", 1);
		addSigOption.setValue("LANGCODE", "ZH");
		addSigOption.setValue("DESCRIPTION", getSigDescKey(sigName));
	}

	/**
	 * 从同义词域中获取描述
	 * 
	 * @param sigName
	 * @return [参数说明]
	 * 
	 */
	private static String getSigDescKey(String sigName) {
		if (sigName != null) {
			return MroServer.getMroServer().getSysDomainCache()
					.getExternalValue("WORKFLOW", sigName);
		} else {
			return "";
		}

	}

	/**
	 * 
	 * 初始化工作流选择操作菜单
	 * 
	 * @param appBean
	 * @param sigName
	 * @param preferredSpot
	 * @param imageSource
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void addMenuOption(AppBean appBean, String sigName,
			int preferredSpot, String imageSource) throws MroException {
		int spot = preferredSpot;
		IJpoSet checkMenuSet = appBean
				.getJpo()
				.getJpoSet(
						"$" + sigName + "MO",
						"SYS_MENU",
						"moduleapp = :app and menutype = 'APPMENU' and elementtype = 'OPTION' and keyvalue ='"
								+ StringUtil.getSafeSqlStr(sigName) + "'");
		if (checkMenuSet.isEmpty()) {
			IJpo header = includeHeader(appBean);
			if (!header.toBeAdded()) {
				String sql = "moduleapp = :app and position ="
						+ header.getInt("POSITION") + " and subposition = "
						+ spot + " and menutype = 'APPMENU' ";
				IJpoSet checkSubSet = MroServer.getMroServer().getJpoSet(
						"SYS_MENU", appBean.getMroSession().getUserServer());
				checkSubSet.setUserWhere(sql);
				// 判断是否存在相同位置的选项
				while (!checkSubSet.isEmpty()) {
					spot++;
					sql = "moduleapp = :app and position ="
							+ header.getInt("POSITION") + " and subposition = "
							+ spot + " and menutype = 'APPMENU' ";
					checkSubSet.setUserWhere(sql);
					checkSubSet.reset();
				}
			}

			IJpoSet addSubMenu = appBean.getJpo().getExistingJpoSet(
					"$addSubMenu");
			if (addSubMenu == null) {
				addSubMenu = appBean.getJpo().getJpoSet("$addSubMenu",
						"SYS_MENU", "1=2");
			}
			IJpo subMenu = addSubMenu.addJpo();
			subMenu.setValue("MENUTYPE", "APPMENU");
			subMenu.setValue("MODULEAPP", appBean.getJpo().getString("APP"));
			subMenu.setValue("POSITION", header.getInt("POSITION"));
			subMenu.setValue("SUBPOSITION", spot);
			subMenu.setValue("ELEMENTTYPE", "OPTION");
			subMenu.setValue("KEYVALUE", sigName);
			subMenu.setValue("VISIBLE", 1);
			subMenu.setValue("TABDISPLAY", "主要");
			if (imageSource != null) {
				subMenu.setValue("IMAGE", imageSource);
			}
		}
	}

	/**
	 * 
	 * 设置菜单项头
	 * 
	 * @param appBean
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private static IJpo includeHeader(AppBean appBean) throws MroException {
		String headerTitle = MroServer.getMroServer().getSysDomainCache()
				.getExternalValue("WORKFLOW", "headerTitle");
		IJpoSet headerSubMenuSet = appBean.getJpo().getExistingJpoSet(
				"$FINDHEADER");
		if (headerSubMenuSet == null) {
			headerSubMenuSet = appBean.getJpo().getJpoSet(
					"$FINDHEADER",
					"SYS_MENU",
					"moduleapp = :app and elementtype = 'HEADER' and headerdescription = '"
							+ StringUtil.getSafeSqlStr(headerTitle) + "'");
		}
		if (headerSubMenuSet.isEmpty()) {
			// 如果没有生成一级菜单，则生成该菜单，并将该菜单排序到最上层
			IJpoSet findMenuPos = appBean.getJpo().getJpoSet("$findMenuPos",
					"SYS_MENU", "menutype = 'APPMENU' and moduleapp = :app");
			findMenuPos.setOrderBy("position desc");
			findMenuPos.reset();
			int menuPosition = 10;
			if (!findMenuPos.isEmpty()) {
				menuPosition = (int) findMenuPos.min("POSITION");
				if (menuPosition > 10) {
					menuPosition = 10;
				} else if (menuPosition == 1) {
					for (int index = 0; index < findMenuPos.count(); index++) {
						IJpo menu = findMenuPos.getJpo(index);
						menu.setValue("POSITION", menu.getInt("POSITION") + 10);
					}
					findMenuPos.save();
					headerSubMenuSet.reset();
				} else {
					menuPosition--;
				}
			}
			IJpo headerWF = headerSubMenuSet.addJpo();
			headerWF.setValue("MENUTYPE", "APPMENU");
			headerWF.setValue("MODULEAPP", appBean.getJpo().getString("APP"));
			headerWF.setValue("POSITION", menuPosition);
			headerWF.setValue("SUBPOSITION", 0);
			headerWF.setValue("HEADERDESCRIPTION", headerTitle);
			headerWF.setValue("ELEMENTTYPE", "HEADER");
			headerWF.setValue("VISIBLE", 1);
			headerWF.setValue("TABDISPLAY", "主要");
			headerWF.setValue("KEYVALUE", "AM" + menuPosition);
			return headerWF;
		} else {
			IJpo headerWF = headerSubMenuSet.getJpo(0);
			return headerWF;
		}
	}

	/**
	 * 
	 * 初始化工作流默认权限设置，需安全管理员配置之后方可使用
	 * 
	 * @param appBean
	 * @throws RemoteException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void initAppAuth(AppBean appBean) throws MroException {

		addAppAuth(appBean, WFConstant.STARTWF);
		addAppAuth(appBean, WFConstant.STOPWF);
		addAppAuth(appBean, WFConstant.HISTORYWF);
		addAppAuth(appBean, WFConstant.ASSIGNWF);
		addAppAuth(appBean, WFConstant.VIEWWF);

	}

	/**
	 * 
	 * 初始化工作流默认权限设置，需安全管理员配置之后方可使用
	 * 
	 * @param appBean
	 * @param sigName
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void addAppAuth(AppBean appBean, String sigName)
			throws MroException {

		IJpoSet readGroups = appBean.getJpo().getJpoSet("$READGROUPS",
				"SYS_APPAUTH", "app = :app and optionname = 'READ'");

		IJpoSet addAuth = appBean.getJpo().getExistingJpoSet("$addAuthForWF");
		if (addAuth == null) {
			addAuth = appBean.getJpo().getJpoSet("$addAuthForWF",
					"SYS_APPAUTH", "1=2");
		}
		for (int index = 0; index < readGroups.count(); index++) {
			IJpo group = readGroups.getJpo(index);
			IJpoSet jposet = appBean.getJpo().getJpoSet(
					"$ALREADYIN",
					"SYS_APPAUTH",
					"app = :app and optionname = '"
							+ StringUtil.getSafeSqlStr(sigName)
							+ "' and groupname = '"
							+ StringUtil.getSafeSqlStr(group
									.getString("GROUPNAME")) + "'");
			if (jposet.isEmpty()) {
				IJpo newAuth = addAuth.addJpo();
				newAuth.setValue("GROUPNAME", group.getString("GROUPNAME"));
				newAuth.setValue("APP", appBean.getJpo().getString("APP"));
				newAuth.setValue("OPTIONNAME", sigName);
			}
		}
	}

	/**
	 * 
	 * 获取关联关系-actAppInfo表
	 * 
	 * @param appBean
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static IJpo getActAppInfo(AppBean appBean) throws MroException {
		String uidValue = String.valueOf(appBean.getUniqueIdValue());
		String AppName = appBean.getAppName();
		if (appBean.getJpo() != null) {
			IJpoSet actAppInfoSet = appBean.getJpo().getJpoSet(
					"$actAppInfo",
					"ACT_APP_INFO",
					"APP = '" + StringUtil.getSafeSqlStr(AppName)
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
	 * @param appBean
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static String hasTaskAuth(AppBean appBean) throws MroException {
		IJpo actAppInfo = getActAppInfo(appBean);
		if (actAppInfo != null
				&& actAppInfo.getString("STATUS").equals(WFConstant.ACTIVI)) {
			ProcessEngine processEngine = ProcessEngines
					.getDefaultProcessEngine();
			TaskService taskService = processEngine.getTaskService();
			String processInstanceId = actAppInfo.getString("PROCINSTID");
			// 根据当前人的ID查询,当前用户是否有任务
			String personId = appBean.getUserInfo().getPersonId();
			List<Task> tasks = taskService.createTaskQuery()
					.processInstanceId(processInstanceId)
					.taskAssignee(personId).active().orderByTaskId().desc()
					.list();
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
	 * 获取当前记录正在运行的流程定义
	 * 
	 * @param appBean
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public static IJpo getActivitiWfVersion(AppBean appBean)
			throws MroException {
		if (appBean == null) {
			return null;
		}
		WfInfoSet wfinfoSet = (WfInfoSet) appBean
				.getJpo()
				.getJpoSet(
						"$wfInfo",
						"SYS_WFINFO",
						"APP ='"
								+ StringUtil.getSafeSqlStr(appBean.getAppName())
								+ "'");
		// 获取最新版本的且已激活的流程
		if (!wfinfoSet.isEmpty()) {
			WfInfo wfinfo = (WfInfo) wfinfoSet.getJpo(0);
			WfVersionSet wfVersionSet = (WfVersionSet) wfinfo
					.getJpoSet(
							"$wfversion",
							"SYS_WFVERSION",
							"WFINFONUM =:WFINFONUM and STATUS = '"
									+ StringUtil
											.getSafeSqlStr(WFConstant.ACTIVI)
									+ "'");
			WfVersion wfVersion = (WfVersion) wfVersionSet.getJpo(0);
			return wfVersion;
		}
		return null;
	}

	/**
	 * 获取某个应用关联的工作流
	 * 
	 * @param appName
	 * @return
	 * @throws MroException
	 */
	public static IJpo getActivitiWf(String appName) throws MroException {

		IJpoSet wfinfoSet = MroServer.getMroServer().getSysJpoSet("SYS_WFINFO",
				"APP ='" + StringUtil.getSafeSqlStr(appName) + "'");
		// 获取最新版本的且已激活的流程
		if (!wfinfoSet.isEmpty()) {
			WfInfo wfinfo = (WfInfo) wfinfoSet.getJpo(0);
			WfVersionSet wfVersionSet = (WfVersionSet) wfinfo
					.getJpoSet(
							"$wfversion",
							"SYS_WFVERSION",
							"WFINFONUM =:WFINFONUM and STATUS = '"
									+ StringUtil
											.getSafeSqlStr(WFConstant.ACTIVI)
									+ "'");
			WfVersion wfVersion = (WfVersion) wfVersionSet.getJpo(0);
			return wfVersion;
		}
		return null;
	}

	/**
	 * 
	 * 启动流程后,记录到中间关系表
	 * 
	 * @param appBean
	 *            appBean
	 * @param procDefinitionId
	 *            流程定义ID
	 * @param processInstance
	 *            流程实例
	 * @param uid
	 *            业务唯一主键ID
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public static void addActAppInfo(AppBean appBean, String procDefinitionId,
			ProcessInstance processInstance, String uid) throws Exception {
		String appName = appBean.getAppName();
		// 获取表名
		String tableName = appBean.getJpoSet().getName();
		String procInstId = processInstance.getId();
		IJpoSet actAppInfoSet = appBean.getPage().getJpoSet("ACT_APP_INFO",
				"1=2");
		IJpo actAppInfo = actAppInfoSet.addJpo();
		actAppInfo.setValue("APP", appName);
		actAppInfo.setValue("BUSINESSKEY", uid);
		actAppInfo.setValue("PROCINSTID", procInstId);
		actAppInfo.setValue("PROCDEFINID", procDefinitionId);
		actAppInfo.setValue("STATUS", WFConstant.ACTIVI);
		actAppInfo.setValue("TABLENAME", tableName);
		actAppInfo.setValue("CREATEDATE", MroServer.getMroServer().getDate());
		actAppInfoSet.save();
	}

	/**
	 * 在一个应用中启动另一个应用中的工作流
	 * 
	 * @param appName
	 * @param tableName
	 * @param procDefinitionId
	 * @param processInstance
	 * @param uid
	 * @throws Exception
	 */
	public static void addActAppInfo(String appName, String tableName,
			String procDefinitionId, ProcessInstance processInstance, String uid)
			throws Exception {
		// 获取表名
		String procInstId = processInstance.getId();
		IJpoSet actAppInfoSet = MroServer.getMroServer().getSysJpoSet(
				"ACT_APP_INFO", "1=2");
		IJpo actAppInfo = actAppInfoSet.addJpo();
		actAppInfo.setValue("APP", appName);
		actAppInfo.setValue("BUSINESSKEY", uid);
		actAppInfo.setValue("PROCINSTID", procInstId);
		actAppInfo.setValue("PROCDEFINID", procDefinitionId);
		actAppInfo.setValue("STATUS", WFConstant.ACTIVI);
		actAppInfo.setValue("TABLENAME", tableName);
		actAppInfo.setValue("CREATEDATE", MroServer.getMroServer().getDate());
		actAppInfoSet.save();
	}

	/**
	 * 
	 * 流程启动、结束时记录历史
	 * 
	 * @param processInstanceId
	 * @param memo
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void addHistoryLog(String processInstanceId, String personId,
			String memo) throws IOException, MroException {

		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ACT_HI_ASSIGN",
				"1=2");
		IJpo jpo = jposet.addJpo();
		jpo.setValue("ASSIGNEE", personId,
				GWConstant.P_NOVALIDATION_AND_NOACTION);// 流程启动人
		jpo.setValue("OLDUSERID", "", GWConstant.P_NOVALIDATION_AND_NOACTION);
		jpo.setValue("MEMO", memo, GWConstant.P_NOVALIDATION_AND_NOACTION);
		jpo.setValue("PROCINSTID", processInstanceId,
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		jpo.setValue("ASSIGNTIME", MroServer.getMroServer().getDate(),
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		jpo.setValue("TYPE", "0", GWConstant.P_NOVALIDATION_AND_NOACTION);
		jposet.save();
		jposet.destroy();

	}

	/**
	 * 
	 * 根据配置的组，来获取组中的用户,当角色类型为数据集时，默认只选第一条数据
	 * 
	 * @param groupName
	 *            角色字符串
	 * @return 用户组ID
	 * @throws MroException
	 *             自定义异常
	 * 
	 */
	public static List<String> getUserListbyGroup(IJpo appJpo, String groupName)
			throws MroException {
		List<String> userList = new ArrayList<String>();
		if (StringUtil.isStrNotEmpty(groupName)) {
			String[] roles = groupName.split(",");

			for (int index = 0; index < roles.length; index++) {
				String roleId = roles[index];
				IJpoSet roleJpoSet = MroServer.getMroServer()
						.getSysJpoSet(
								"SYS_ROLE",
								"MAXROLE='"
										+ StringUtil.getSafeSqlStr(roleId
												.toUpperCase()) + "'");
				if (roleJpoSet != null && !roleJpoSet.isEmpty()) {
					Role roleJpo = (Role) roleJpoSet.getJpo(0);
					roleJpo.getPersonByRole(appJpo, userList);
				} else {
					throw new AppException("role", "isNotExist",
							new String[] { roleId });
				}
			}
		}
		return userList;
	}

	/**
	 * 
	 * 获取用户任务表单数据
	 * 
	 * @param taskId
	 * @return [参数说明]
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> findTaskForm(String taskId) {

		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		FormService formService = processEngine.getFormService();
		Map<String, Object> result = new HashMap<String, Object>();
		TaskFormDataImpl taskFormData = (TaskFormDataImpl) formService
				.getTaskFormData(taskId);
		// 设置task为null，否则输出json的时候会报错
		taskFormData.setTask(null);

		result.put("taskFormData", taskFormData);
		/*
		 * 读取enum类型数据，用于下拉框
		 */
		List<FormProperty> formProperties = taskFormData.getFormProperties();
		for (FormProperty formProperty : formProperties) {
			if (formProperty.getType() != null) {
				Map<String, String> values = (Map<String, String>) formProperty
						.getType().getInformation("values");
				if (values != null) {
					// for (Entry<String, String> enumEntry : values.entrySet())
					// {
					// System.out.println("配置enum属性:" + enumEntry.getKey() + ":"
					// + enumEntry.getValue());
					// }
					result.put(formProperty.getId(), values);
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * 获取当前正在执行工作流的jpo(流程已经启动)
	 * 
	 * @param proInstId
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public static IJpo getJpoByProInstId(String proInstId) throws MroException {
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ACT_APP_INFO",
				"PROCINSTID='" + StringUtil.getSafeSqlStr(proInstId) + "'");
		if (jposet != null && !jposet.isEmpty()) {
			IJpo actHiAssign = jposet.getJpo(0);
			String app = actHiAssign.getString("APP");
			IJpoSet sysAppJposet = MroServer.getMroServer().getSysJpoSet(
					"SYS_APP", "APP='" + StringUtil.getSafeSqlStr(app) + "'");
			if (!sysAppJposet.isEmpty()) {
				String tableName = sysAppJposet.getJpo(0).getString(
						"MAINTBNAME");
				String primaryKeyColName = tableName + "ID";
				String businessKey = actHiAssign.getString("BUSINESSKEY");
				IJpoSet appJposet = MroServer.getMroServer().getSysJpoSet(
						tableName,
						primaryKeyColName + "='"
								+ StringUtil.getSafeSqlStr(businessKey) + "'");
				if (!appJposet.isEmpty()) {
					return appJposet.getJpo(0);
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * 获取当前正在执行工作流的jpo(流程尚未启动)
	 * 
	 * @param execution
	 * @return
	 * @throws MroException
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public static IJpo getJpoByUid(DelegateExecution execution)
			throws MroException {
		String app = (String) execution.getVariable("APP");
		String uid = (String) execution.getVariable("BUSINESSKEY");
		IJpoSet sysAppJposet = MroServer.getMroServer().getSysJpoSet("SYS_APP",
				"APP='" + StringUtil.getSafeSqlStr(app) + "'");
		if (!sysAppJposet.isEmpty()) {
			String tableName = sysAppJposet.getJpo(0).getString("MAINTBNAME");
			String primaryKeyColName = tableName + "ID";
			IJpoSet appJposet = MroServer.getMroServer().getSysJpoSet(
					tableName,
					primaryKeyColName + "='" + StringUtil.getSafeSqlStr(uid)
							+ "'");
			if (!appJposet.isEmpty()) {
				return appJposet.getJpo(0);
			}
		}
		return null;
	}

	/**
	 * 流程结束后，删除流程垃圾数据
	 * 
	 * @param processInstanceId
	 *            [参数说明]
	 * @throws Exception
	 * 
	 */
	public static void delActAppInfo(String processInstanceId)
			throws MroException {
		IJpoSet sysAppJposet = MroServer.getMroServer().getSysJpoSet(
				"ACT_APP_INFO",
				"PROCINSTID='" + StringUtil.getSafeSqlStr(processInstanceId)
						+ "'");
		sysAppJposet.deleteAll();
		sysAppJposet.save();
		sysAppJposet.destroy();
	}

	public static void updateActAppInfo(String processInstanceId, String status)
			throws Exception {
		IJpoSet sysAppJposet = MroServer.getMroServer().getSysJpoSet(
				"ACT_APP_INFO",
				"PROCINSTID='" + StringUtil.getSafeSqlStr(processInstanceId)
						+ "'");
		IJpo jpo = sysAppJposet.getJpo(0);
		if (jpo != null) {
			jpo.setValue("STATUS", status,
					GWConstant.P_NOVALIDATION_AND_NOACTION);
		}
		sysAppJposet.save();
		sysAppJposet.destroy();
	}

	/**
	 * 更新备注
	 * 
	 * @param processInstanceId
	 * @param string
	 * @param userId
	 *            [参数说明]
	 * @throws Exception
	 * 
	 */
	public static void updateHistoryLog(String processInstanceId,
			String taskId, String memo, String userId) throws Exception {
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet(
				"act_hi_assign",
				"PROCINSTID='" + StringUtil.getSafeSqlStr(processInstanceId)
						+ "' AND TASKID = '" + StringUtil.getSafeSqlStr(taskId)
						+ "' and ASSIGNEE = '"
						+ StringUtil.getSafeSqlStr(userId) + "'");
		IJpo jpo = jposet.getJpo(0);
		jpo.setValue("MEMO", memo);
		jposet.save();
		jposet.destroy();
	}

	/**
	 * 
	 * 判断当前登录人是否是工作流的当前的执行人
	 * 
	 * @param jpo
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public static boolean isCurUser(IJpo jpo) throws MroException {
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
						if (jpo.getUserInfo().getUserName()
								.equals(task.getString("ASSIGNEE_"))) {
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

	/**
	 * 
	 * 启动工作流
	 * 
	 * @param jpo
	 *            主JPO
	 * @param appName
	 *            应用名称
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public static void startwf(IJpo jpo, String appName) throws MroException {

		if (!hasActiveWf(jpo)) {// 判断是否已经启动工作流

			long id = jpo.getId();
			String jponame = jpo.getName();

			IJpo wfversion = WfControlUtil.getActivitiWf(appName);
			if (wfversion == null) {
				throw new AppException("wfinfo", "noActivitiWf");
			}
			String procDefinitionId = wfversion.getString("PROCDEFINITIONID");
			ProcessEngine processEngine = ProcessEngines
					.getDefaultProcessEngine();
			RuntimeService runtimeService = processEngine.getRuntimeService();
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
						.startProcessInstanceById(procDefinitionId, uidValue,
								formProperties);
				// 添加到关系表
				WfControlUtil.addActAppInfo(appName, jponame, procDefinitionId,
						processInstance, uidValue);
				// showOperInfo("workflow", "startSuccess");
			} catch (Exception e) {
				// if (e instanceof AppException) {
				// throw e;
				// } else if (e instanceof PvmException) {
				// String msg = e.getMessage();
				// if (msg.indexOf(":") != -1)
				// {
				// throw new AppException("信息", msg.substring(msg.indexOf(":") +
				// 1));
				// }
				// else
				// {
				// throw new AppException("信息", msg);
				// }
				// } else {
				// EXCEPTIONLOGGER.error(e);
				// }
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 判断当前jpo是否有活动的工作流
	 * 
	 * @param jpo
	 * @return true 有工作流， false 无工作流
	 * @throws MroException
	 * 
	 */
	public static boolean hasActiveWf(IJpo jpo) throws MroException {

		String tableName = jpo.getName().toUpperCase();

		IJpoSet taskSet = MroServer.getMroServer()
				.getSysJpoSet(
						"ACT_APP_INFO",
						" tablename='" + tableName + "' and businesskey="
								+ jpo.getId());
		if (taskSet != null && !taskSet.isEmpty()) {
			if (taskSet.getJpo(0) == null) {
				return false;
			} else {
				return true;
			}

		} else {
			return false;
		}

	}
}
