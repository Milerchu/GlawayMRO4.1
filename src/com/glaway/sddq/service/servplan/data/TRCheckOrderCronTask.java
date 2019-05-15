package com.glaway.sddq.service.servplan.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.pvm.PvmException;
import org.activiti.engine.runtime.ProcessInstance;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * TR检查单定时生成任务
 * 
 * @author ygao
 * @version [版本号, 2017-11-8]
 * @since [产品/模块版本]
 */
public class TRCheckOrderCronTask extends BaseStatefulJob {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Override
	public void execute() throws MroException {
		IJpoSet servplanSet = MroServer.getMroServer().getJpoSet("servplan",
				MroServer.getMroServer().getSystemUserServer());
		IJpoSet milestoneSet = MroServer.getMroServer().getJpoSet("MILESTONE",
				MroServer.getMroServer().getSystemUserServer());
		servplanSet.setQueryWhere("STATUS='已发布'");
		servplanSet.reset();
		String[] data = null;
		Date sysdate = MroServer.getMroServer().getDate();
		Connection con = MroServer.getMroServer().getCronTaskUserServer()
				.getConnection();
		ResultSet rs = null;
		Statement stm = null;
		if (!servplanSet.isEmpty()) {
			try {
				stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				IJpo servPlanjpo = null;
				int i = 0;
				while ((servPlanjpo = servplanSet.getJpo(i)) != null) {
					String servPlannum = servPlanjpo.getString("servplannum");
					// milestoneSet.setQueryWhere("servplannum = '"+servPlannum+"'");
					// 查询出该服务计划下每个阶段里程碑最后一个任务。
					// milestoneSet.setOrderBy("milestonetime desc");
					// milestoneSet.reset();

					String sql = "SELECT t.stagenum from MILESTONE t WHERE t.servplannum = '"
							+ servPlannum + "' GROUP BY t.stagenum";
					rs = stm.executeQuery(sql);
					if (rs != null) {
						int rowcount = 0;
						while (rs.next()) {
							rowcount++;
						}
						data = new String[rowcount];
						rs.beforeFirst();
						int k = 0;
						while (rs.next()) {
							data[k] = rs.getString("stagenum");
							k++;
						}
					}

					for (int k = 0; k < data.length; k++) {
						String stagenum = data[k];
						milestoneSet.setQueryWhere("servplannum = '"
								+ servPlannum + "' AND stagenum = '" + stagenum
								+ "' and status<>'关闭'");
						milestoneSet.setOrderBy("milestonetime desc");
						milestoneSet.reset();
						if (!milestoneSet.isEmpty()) {
							IJpo milestone = milestoneSet.getJpo();
							Date milestonetime = milestone
									.getDate("MILESTONETIME");
							// 根据得到的jpo创建TR检查单
							// 当该里程碑的状态为未生成TR检查单并且它的里程碑事件距离当前日期小于等于一个月才能生成TR检查单
							if (milestonetime != null) {

								if (!"1".equals(milestone
										.getString("CHECKORDERFLAG"))
										&& 30 >= daysBetween(sysdate,
												milestonetime)) {
									generateTRCheckOrder(milestone,
											milestoneSet);
								}
							}
						}
					}
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (stm != null) {
					try {
						stm.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
						FixedLoggers.EXCEPTIONLOGGER.error(e);
					}
				}
			}
		}
	}

	/**
	 * 
	 * 生成TR检查单信息
	 * 
	 * @param milestone
	 * @param milestoneSet
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private void generateTRCheckOrder(IJpo milestone, IJpoSet milestoneSet)
			throws MroException {

		MroServer.getMroServer().getCronTaskUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getCronTaskUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		IJpoSet trcheckorderset = MroServer.getMroServer().getJpoSet(
				"TRCHECKORDER",
				MroServer.getMroServer().getCronTaskUserServer()); // 获取TR检查单
		IJpo trcheckorder = trcheckorderset.addJpo();
		trcheckorder.setValue("CHECKRESPROLE", milestone.getString("RESPROLE"));
		trcheckorder.setValue("CHECKDATE", MroServer.getMroServer().getDate());
		trcheckorder.setValue("CHECKCONTENT",
				milestone.getString("MILESTONENAME"));
		trcheckorder
				.setValue("SERVPLANNUM", milestone.getString("SERVPLANNUM"));
		trcheckorder.setValue("STAGENUM", milestone.getString("STAGENUM"));
		long uid = trcheckorder.getId();
		// 将该阶段下的里程碑与产生的TR检查单建立关联关系
		for (int i = 0; i < milestoneSet.count(); i++) {
			IJpo mjpo = milestoneSet.getJpo(i);
			mjpo.setValue("TRCHECKORDERNUM",
					trcheckorder.getString("TRCHECKORDERNUM"));
		}
		// 生成检查单后，将该里程碑状态设置为已生成TR检查单，防止重复生成TR检查单
		milestone.setValue("CHECKORDERFLAG", "Y",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		milestoneSet.save();
		trcheckorderset.save();
		try {
			// 启动工作流
			startwf(trcheckorder, uid);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static int daysBetween(Date sysdate, Date milestonetime)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sysdate = sdf.parse(sdf.format(sysdate));
		milestonetime = sdf.parse(sdf.format(milestonetime));
		Calendar cal = Calendar.getInstance();
		cal.setTime(sysdate);
		long time1 = cal.getTimeInMillis();

		cal.setTime(milestonetime);
		long time2 = cal.getTimeInMillis();

		long daysbtw = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(daysbtw));

	}

	/**
	 * 
	 * 启动TR检查单工作流
	 * 
	 * @param jpo
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	private void startwf(IJpo jpo, long id) throws Exception {
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("TRCHECKORDER");
		jposet.setUserWhere("TRCHECKORDERID=" + id);
		jposet.reset();
		IJpo curjpo = jposet.getJpo(0);

		IJpo wfversion = WfControlUtil.getActivitiWf("TRCHECKORD");
		if (wfversion == null) {
			throw new AppException("wfinfo", "noActivitiWf");
		}
		String procDefinitionId = wfversion.getString("PROCDEFINITIONID");
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		IdentityService identityService = processEngine.getIdentityService();

		Map<String, Object> formProperties = new HashMap<String, Object>(); // 在该map中可设置自定义的字段参数
		String uidValue = String.valueOf(id);
		formProperties.put("APP", "TRCHECKORD");
		formProperties.put("BUSINESSKEY", uidValue);
		formProperties.put("curJpo", curjpo);
		// 启动工作流
		identityService.setAuthenticatedUserId("GWADMIN");
		try {
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceById(procDefinitionId, uidValue,
							formProperties);
			// 添加到关系表
			WfControlUtil.addActAppInfo("TRCHECKORD", "TRCHECKORDER",
					procDefinitionId, processInstance, uidValue);
			// showOperInfo("workflow", "startSuccess");
		} catch (Exception e) {
			if (e instanceof AppException) {
				throw e;
			} else if (e instanceof PvmException) {
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
			} else {
				EXCEPTIONLOGGER.error(e);
			}
		}
	}
}
