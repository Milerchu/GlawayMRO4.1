package com.glaway.sddq.service.valiplan.bean;

import java.io.IOException;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.control.ToolbarAction;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 验证计划 AppBean
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月16日]
 * @since [产品/模块版本]
 */
public class ValiPlanAppBean extends AppBean {

	/**
	 * 判断验证计划状态，非草稿不可删除
	 */
	// 报表实现
	public int REPORT1() throws MroException, IOException {
		if (getCurrEventCtrl() != null
				&& getCurrEventCtrl() instanceof ToolbarAction) {
			ToolbarAction tool = (ToolbarAction) getCurrEventCtrl();
			String reportNum = tool.getUrl();// url为报表编号
			if (StringUtil.isStrEmpty(reportNum)) {
				throw new MroException("report", "no_report");
			} else {
				IJpoSet set = MroServer.getMroServer().getSysJpoSet(
						"SYS_REPORT", "reportnum='" + reportNum + "'");
				set.reset();
				if (set.isEmpty()) {
					throw new MroException("report", "reportnum");
				}
			}
			IJpoSet reports = getPage()
					.getJpoSet(
							"SYS_REPORT",
							"reportnum in(select x0.reportnum from sys_reportgrp x0,sys_groupuser x1 where x0.groupname=x1.groupname and x1.userid='"
									+ getUserInfo().getUserName()
									+ "')and reportnum='"
									+ reportNum
									+ "'and app is not null");
			if (reports.isEmpty()) {
				throw new MroException("report", "report_noautic");
			}
			IJpo rptJpo = reports.getJpo();
			String url = "";
			String rptLink = rptJpo.getString("RPTLINK");
			String inputName = "";
			if (StringUtil.isEqual(
					MroServer.getMroServer().getSysProp("mro.report.engine")
							.trim(), "FR")) {
				String flag = "";
				if (rptLink.length() - 3 > 0) {
					flag = rptLink.substring(rptLink.length() - 3);
					if (StringUtil.isEqual("frm", flag)) {
						inputName = "formlet";
					} else {
						inputName = "reportlet";
					}
				} else
					inputName = "formlet";
			} else {
				inputName = "_report";
			}
			long id = -1;
			if (rptJpo.getBoolean("ISBILL"))// 单据类报表需额外传递ID参数
			{
				String idColName = StringUtil.toLowerCase(getJpoSet()
						.getIdColName());
				if (getJpo() != null && !getJpo().isZombie()) {
					id = getJpo().getId();
				}
			}
			String userName = getUserInfo().getUserName();
			url = inputName + "," + rptLink + "," + userName + ","
					+ String.valueOf(id);
			getMroSession().returnReponse("", "linkReport", url);
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	@Override
	public int DELETE() throws MroException, IOException {
		String status = getString("STATUS");
		String creater = getString("CREATER");
		if (status.equals("草稿") && creater.equals(getUserInfo().getUserName())) {
			return super.DELETE();
		} else {
			throw new MroException("非草稿状态或非创建人无法删除");
		}
	}

	@Override
	public int ROUTEWF() throws Exception {
		IJpo plan = this.getAppBean().getJpo();
		if (WfControlUtil.isCurUser(plan)) {// 工作流权限

			String status = plan.getString("status");
			if ("草稿".equals(status)) {
				String planEditor = plan.getString("planeditor");
				if (StringUtil.isStrEmpty(planEditor)) {
					plan.setValue("planeditor", plan.getUserInfo()
							.getPersonId());// 设置计划编制人为当前操作人
				}
			}
		}
		return super.ROUTEWF();
	}

}
