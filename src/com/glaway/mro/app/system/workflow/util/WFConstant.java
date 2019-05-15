package com.glaway.mro.app.system.workflow.util;

/**
 * 
 * 定义工作流常量
 * 
 * @author hyhe
 * @version [MRO4.0, 2016-5-12]
 * @since [MRO4.0/工作流]
 */
public interface WFConstant {

	public static final String EDIT = "0"; // 编辑状态中

	public static final String ENABLE = "1"; // 已部署或已启用

	public static final String DISABLE = "2"; // 禁用

	public static final String ACTIVI = "3"; // 已激活

	public static final String DISACTIVI = "4"; // 取消激活

	public static final String STOP = "5"; // 已停止

	public static final String NONE = "6";// 流程已经结束

	public static final String AUTO = "Y"; // 设置为自动启动

	public static final String DISAUTO = "N"; // 设置为不自动启动

	public static final String STARTWF = "ROUTEWF"; // 启动工作流

	public static final String STOPWF = "STOPWF"; // 停止工作流

	public static final String HISTORYWF = "HISTORYWF"; // 工作流历史记录

	public static final String ASSIGNWF = "ASSIGNWF"; // 工作流任务分配

	public static final String VIEWWF = "VIEWWF"; // 查看工作流图

	// 审批意见
	public static final String APPROVED = "approved";

	// 统计审批的数目
	public static final String APPROVEDCOUNTER = "approvedCounter";

	// 驳回审批的数目
	public static final String REJECTEDCOUNTER = "rejectedCounter";

	public static final String DEAL = "办理";

	public static final String CLAIM = "签收";

	public static final String USERS = "userlist";

	public static final String GROUPS = "usergroup";

	public static final String ACTIVITISTART = "流程启动";

	public static final String ACTIVITIEND = "流程结束";

	public static final String ACTIVITISTOP = "流程已经停止";

}
