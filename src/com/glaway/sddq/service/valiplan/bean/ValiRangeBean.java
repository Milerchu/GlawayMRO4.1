package com.glaway.sddq.service.valiplan.bean;

import java.io.IOException;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 验证计划-验证产品范围 databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月23日]
 * @since [产品/模块版本]
 */
public class ValiRangeBean extends DataBean {

	@Override
	public int addrow() throws MroException, IOException {
		String status = this.page.getAppBean().getJpo().getString("status");
		if (!"草稿".equals(status)) {// 限制只能在草稿状态下新建
			throw new MroException("valiplan", "cannotoperate");
		}
		// 根据工作流判断权限
		try {
			if (!WfControlUtil.isCurUser(this.page.getAppBean().getJpo())) {
				this.showMsgbox("错误", "您无权操作！");
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.addrow();
	}

	/**
	 * 
	 * 批量创建验证工单
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public int createvaliord() throws MroException {

		String status = this.page.getAppBean().getJpo().getString("status");
		if (!("执行中".equals(status))) {
			throw new MroException("", "当前状态无法创建工单！");
		}

		IJpo valiplan = this.page.getAppBean().getJpo();
		IJpo valirange = this.getJpo();
		boolean iscreate = valirange.getBoolean("ISCREATEORDER");// 是否已创建工单
		if (iscreate) {// 已经创建过工单
			throw new MroException("valiplan", "outofvaliqty");
		}

		if (!valiplan
				.getUserInfo()
				.getLoginID()
				.equalsIgnoreCase(
						WorkorderUtil.getOfficeDirectorByOfficenum(valirange
								.getString("OFFICE")))) {// 当前登录人非办事处责任人
			throw new MroException("valiplan", "notthisoffice");
		}

		int valicount = valirange.getInt("valicount");// 验证数量
		// 计划编号
		String transplannum = valiplan.getString("TRANSPLANNUM");
		// String userid = valirange.getUserInfo().getLoginID();

		// 根据验证数量批量创建工单
		for (int index = 0; index < valicount; index++) {
			IJpoSet workOrderSet = MroServer.getMroServer()
					.getJpoSet("WORKORDER",
							MroServer.getMroServer().getSystemUserServer());
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultOrg("CRRC");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultSite("ELEC");
			IJpo jpo = workOrderSet.addJpo();
			jpo.setValue("TYPE", "验证");
			jpo.setValue("SITEID", "ELEC");
			jpo.setValue("ORGID", "CRRC");
			jpo.setValue("PLANNUM", transplannum,
					GWConstant.P_NOVALIDATION_AND_NOACTION);
			jpo.setValue("NOTICENUM", valiplan.getString("transnoticenum"));
			// jpo.setValue("REPORTER", userid);
			jpo.setValue("WHICHOFFICE", valirange.getString("OFFICE"));
			jpo.setValue("WHICHSTATION", valirange.getString("OWNERCUSTOMER"));
			jpo.setValue("MODELS", valirange.getString("TRANSMODELS"));
			jpo.setValue("MODELPROJECT",
					valirange.getString("MODELS.MODELDESC"));
			workOrderSet.save();
		}

		// 更新是否创建工单标记
		valirange.setValue("ISCREATEORDER", 1);

		// 更新计划启动时间
		// this.page.getAppBean().getJpo()
		// .setValue("STARTDATE", MroServer.getMroServer().getDate());

		this.showMsgbox("提示", "成功创建" + valicount + "个工单！");
		try {
			this.page.getAppBean().SAVE();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	/**
	 * 
	 * 分配人员按钮
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public int distribute() throws MroException, IOException {

		String director = WorkorderUtil.getOfficeDirectorByOfficenum(getJpo()
				.getString("OFFICE"));
		if (!getJpo().getUserInfo().getPersonId().equalsIgnoreCase(director)) {
			throw new MroException("非本办事处主任无权操作！");
		}

		String status = this.page.getAppBean().getJpo().getString("status");
		if (!("执行中".equals(status))) {
			throw new MroException("", "当前状态无法操作！");
		}

		if (!getJpo().getBoolean("iscreateorder")) {// 是否创建了工单
			throw new MroException("valiplan", "noorder");
		}
		// 显示分配人员对话框
		loadDialog("distribute");

		// 设置验证计划实际开始时间
		IJpo plan = getAppBean().getJpo();
		if (plan.getDate("REALSTARTTIME") == null) {

			plan.setValue("REALSTARTTIME", MroServer.getMroServer().getDate());

		}
		return 1;
	}

	@Override
	public synchronized void delete() throws MroException {
		// String status = this.page.getAppBean().getJpo().getString("status");
		String status = this.getJpo().getString("status");
		if (!"草稿".equals(status)) {// 限制只能在草稿状态下可删除
			throw new MroException("valiplan", "cannotoperate");
		}
		try {
			// 根据工作流判断权限
			if (!WfControlUtil.isCurUser(this.page.getAppBean().getJpo())) {
				this.showMsgbox("错误", "您无权操作！");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// if (getJpo().getBoolean("iscreateorder")) {
		// // 已经创建工单的范围不能删除
		// throw new MroException("valiplan", "cannotdeletedist");
		// }
		super.delete();
	}
}
