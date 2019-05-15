package com.glaway.sddq.service.valiplan.bean;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 关联验证工单databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月15日]
 * @since [产品/模块版本]
 */
public class ValiOrderBean extends DataBean {

	@Override
	public void toggleEditRow() throws MroException, IOException {
		// 判断当前登录人是否是工单处理人
		String loginid = getJpo().getUserInfo().getLoginID();
		IJpoSet dealPersonSet = getJpo().getJpoSet("JXTASKEXECPERSON");
		if (!dealPersonSet.isEmpty()) {
			boolean flag = false;
			for (int index = 0; index < dealPersonSet.count(); index++) {
				String person = dealPersonSet.getJpo(index).getString(
						"PERSONNUM");
				if (loginid.equalsIgnoreCase(person)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				getJpo().setFlag(GWConstant.S_READONLY, true);
				// throw new MroException("非工单处理人无法操作！");
			} else {
				getJpo().setFlag(GWConstant.S_READONLY, false);
			}
		} else {
			throw new MroException("当前无法操作！");
		}

		super.toggleEditRow();
	}

	/**
	 * 
	 * 确认完工，关闭验证工单
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * @throws IOException
	 * 
	 */
	public int finishorder() throws MroException, IOException {

		/**
		 * 权限控制
		 */
		String planStatus = this.page.getAppBean().getJpo().getString("status");// 计划状态
		String orderStatus = getJpo().getString("status");// 工单状态
		if (!"执行中".equals(planStatus)) {
			throw new MroException("当前状态无法操作！");
		}
		if (SddqConstant.WO_STATUS_GB.equals(orderStatus)) {
			throw new MroException("工单已经关闭！");
		}

		// 判断当前登录人是否是工单处理人
		String loginid = getJpo().getUserInfo().getLoginID();
		IJpoSet dealPersonSet = getJpo().getJpoSet("JXTASKEXECPERSON");
		if (!dealPersonSet.isEmpty()) {
			boolean flag = false;
			for (int index = 0; index < dealPersonSet.count(); index++) {
				String person = dealPersonSet.getJpo(index).getString(
						"PERSONNUM");
				if (loginid.equalsIgnoreCase(person)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				throw new MroException("非工单处理人无法操作！");
			}
		} else {
			throw new MroException("当前无法操作！");
		}
		int exchangeCnt = getJpo().getJpoSet("EXCHANGERECORD").count(
				GWConstant.P_COUNT_AFTERSAVE);
		if (exchangeCnt < 1) {

			throw new MroException("valiplan", "noproductinfo");// 验证产品信息表未填写不可关闭工单

		}
		// 判断到期是否装用刷回原程序
		boolean isZY = getJpo().getBoolean("TRANSPLAN.VALIREQUEST.PLANTOUSE");// 到期是否装用
		if ((!isZY) && (exchangeCnt % 2 != 0)) {
			throw new MroException("到期装用，请刷回原程序再关闭工单！");
		}
		// 显示确认对话框
		showYesNoDialog("确认关闭工单？", "sureclose", "cancelclose");

		return 1;
	}

	/**
	 * 
	 * 确认关闭工单
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void sureclose() throws MroException, IOException {
		// 关闭当前工单
		this.getJpo().setValue("status", "关闭");

		// 设置计划实际完成时间
		IJpo plan = getAppBean().getJpo();
		IJpoSet rangeSet = plan.getJpoSet("VALIPRORANGE");
		boolean flag = false;// 是否存在未关闭工单
		for (int index = 0; index < rangeSet.count(); index++) {
			IJpo range = rangeSet.getJpo(index);
			IJpoSet orderSet = range.getJpoSet("VALIORDER");
			for (int j = 0; j < orderSet.count(); j++) {
				IJpo order = orderSet.getJpo(j);
				if (!"关闭".equals(order.getString("status"))) {
					flag = true;
					break;
				}
			}
		}
		if (!flag) {
			plan.setValue("REALENDTIME", MroServer.getMroServer().getDate());
		}
		this.getAppBean().SAVE();
	}

	public void cancelclose() {

	}

	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {

		super.addEditRowCallBackOk();
		if ((!"关闭".equals(getJpo().getString("status")))
				&& getJpo().getBoolean("ISERROR")) {// 工单异常

			showYesNoDialog("工单异常，是否通知相关人员？", "yesnotice", "nonotice");

		}
		// this.getAppBean().SAVE();

	}

	public void yesnotice() throws MroException {

		IJpo plan = getAppBean().getJpo();
		Set<String> psnSet = new HashSet<String>();// 消息接收人set
		psnSet.add(plan.getString("PLANEDITOR"));// 计划编制人

		String office = this.getParent().getString("office");
		List<String> plist = WorkorderUtil.getPersonsByPost(office, "办事处主任");// 获取办事处主任id
		for (String dir : plist) {
			psnSet.add(dir);
		}

		WorkorderUtil.sendMsg("VALIPLAN", plan.getId(), psnSet, "验证工单发生异常",
				"验证工单：" + getJpo().getString("ordernum") + "发生异常，请注意！");

	}

	public void nonotice() throws MroException {

	}
}
