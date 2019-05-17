package com.glaway.sddq.service.transplan.bean;

import java.io.IOException;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 改造计划 AppBean
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月26日]
 * @since [产品/模块版本]
 */
public class TransplanAppBean extends AppBean {

	/**
	 * 判断改造计划状态，非草稿不可删除
	 */
	@Override
	public int DELETE() throws MroException, IOException {
		String status = getString("STATUS");
		String creater = getString("CREATER");
		if (status.equals("草稿") && creater.equals(getUserInfo().getUserName())) {
			return super.DELETE();
		} else {
			throw new MroException("非草稿状态或非创建人无法删除!");
		}
	}

	@Override
	public int ROUTEWF() throws Exception {
		IJpo plan = this.getAppBean().getJpo();
		String status = plan.getString("status");
		// 取当前登录人
		String loginid = plan.getUserInfo().getPersonId();
		plan.setValue("wfacter", loginid, GWConstant.P_NOVALIDATION);// 设置工作流当前操作人
		if ("草稿".equals(status)) {
			if (plan.getJpoSet("TRANSDIST").count(GWConstant.P_COUNT_AFTERSAVE) < 1) {
				throw new AppException("transplan", "notransdist");
			}
			//设置计划编制人
			this.getJpo().setValue("PLANEDITOR", loginid);

			/*必填校验*/
			//改造分布set
			IJpoSet transDistSet = getJpo().getJpoSet("TRANSDIST");
			if(transDistSet != null && transDistSet.count() > 0){
				for (int i = 0; i < transDistSet.count(); i++) {
					IJpo transDist = transDistSet.getJpo(i);
					//机务段或改造数量未填写
					if( StringUtil.isStrEmpty(transDist.getString("STATION")) ||
							transDist.getFloat("TRANSCOUNT") == 0.0f){

						throw new MroException("改造车辆分布信息不完整，无法发送工作流！");

					}

				}
			}

		} else if ("挂起".equals(status)) {
			throw new AppException("service", "cannotoperate");
		} else if ("执行中".equals(status)) {
			boolean isJobUndone = false;
			IJpoSet transDistSet = MroServer.getMroServer().getSysJpoSet("TRANSDIST","transplannum='"+
					plan.getString("TRANSPLANNUM")+"' and responsible='"+loginid+"'");
			if(transDistSet!=null && transDistSet.count()>0){
				for (int i = 0; i < transDistSet.count(); i++) {
					IJpo transDist = transDistSet.getJpo(i);
					if(!transDist.getBoolean("ISCREATEWO")){
						isJobUndone = true;
						break;
					}
					//改造工单
					IJpoSet orderSet = transDist.getJpoSet("TRANSORDER");
					if(orderSet != null && orderSet.count() > 0) {

						for (int j = 0; j < orderSet.count(); j++) {
							if(orderSet.getJpo(j).getJpoSet("JXTASKEXECPERSON").isEmpty()) {
								//现场处理人子表为空
								isJobUndone = true;
								break;
							}
						}

					}

				}
				if(isJobUndone) {
					throw new MroException("任务未分配完无法发送工作流！");
				}
			}

		}

		return super.ROUTEWF();
	}

	/**
	 * 
	 * 挂起
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * @throws IOException
	 * 
	 */
	public int HOLD() throws MroException, IOException {

		IJpo transplan = getAppBean().getJpo();
		String status = transplan.getString("status");
		if (!StringUtil.isHaveStr(new String[] { "执行中", "挂起" }, status)) {

			throw new AppException("service", "cannotoperate");

		}

		// 权限，只有改造通知单中的售后质量工程师才能操作
		String asQaEngineer = transplan.getString("TRANSNOTICE.QUALITYENG");// 售后质量工程师
		String loginId = transplan.getUserInfo().getPersonId();// 当前登录用户
		if (!loginId.equalsIgnoreCase(asQaEngineer)) {

			throw new AppException("transplan", "noauth");

		}

		if ("执行中".equals(status)) {

			this.page.loadDialog("SHOWHOLD", this.page.getCurrEventCtrl());

			return 1;

		} else {

			transplan.setValue("status", "执行中");
			// 关联工单挂起
			IJpoSet transDistSet = transplan.getJpoSet("TRANSDIST");
			if (!transDistSet.isEmpty()) {
				for (int i = 0; i < transDistSet.count(); i++) {

					IJpo transDist = transDistSet.getJpo(i);
					IJpoSet orderSet = transDist.getJpoSet("TRANSORDER");
					if (!orderSet.isEmpty()) {

						for (int j = 0; j < orderSet.count(); j++) {

							IJpo order = orderSet.getJpo(j);

							if (WfControlUtil.hasActiveWf(order)) {// 只变更未处理完成的工单的值
								// 将工单状态变为处理中
								order.setValue("status",
										SddqConstant.WO_STATUS_CLZ,
										GWConstant.P_NOVALIDATION_AND_NOACTION);

							}

						}

					}

				}
			}

			this.showMsgbox("提示", "成功取消挂起！");

			PageControl ctrl = getPage().getControlByXmlId("1540350223458");
			if (ctrl != null) {
				ctrl.hide();// 隐藏挂起原因文本框
			}
		}

		this.SAVE();

		return 1;
	}

	/**
	 * 
	 * 重新启动工单工作流
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * @throws IOException
	 * 
	 */
	public int RESTARTWF() throws MroException, IOException {

		// 计划jpo
		IJpo jpo = getJpo();
		String plannum = jpo.getString("transplannum");

		// 删除工作流信息 start
		IJpoSet jpoSet1 = MroServer
				.getMroServer()
				.getSysJpoSet(
						"ACT_HI_ASSIGN",
						"procinstid in(select procinstid from act_app_info t where app = 'TRANSORDER' and businesskey in(select workorderid from workorder where  plannum='"
								+ plannum + "'))");
		if (jpoSet1 != null && jpoSet1.count() > 0) {
			jpoSet1.deleteAll();
			jpoSet1.save();
		}
		IJpoSet jpoSet2 = MroServer
				.getMroServer()
				.getSysJpoSet(
						"act_app_info",
						"app = 'TRANSORDER' and businesskey in(select workorderid from workorder where  plannum='"
								+ plannum + "' )");
		if (jpoSet2 != null && jpoSet2.count() > 0) {
			jpoSet2.deleteAll();
			jpoSet2.save();
		}
		IJpoSet workorderSet = MroServer.getMroServer().getSysJpoSet(
				"workorder", "plannum='" + plannum + "' and status<>'草稿'");
		if (workorderSet != null && workorderSet.count() > 0) {
			for (int i = 0; i < workorderSet.count(); i++) {
				workorderSet.getJpo(i).setValue("status", "草稿");
			}
			workorderSet.save();
		}
		// 删除工作流信息 end

		IJpoSet transdistSet = jpo.getJpoSet("TRANSDIST");// 改造车辆分布表
		if (!transdistSet.isEmpty()) {

			for (int index = 0; index < transdistSet.count(); index++) {

				IJpo transdist = transdistSet.getJpo(index);
				IJpoSet orderSet = transdist.getJpoSet("TRANSORDER");// 工单子表
				if (!orderSet.isEmpty()) {

					for (int i = 0; i < orderSet.count(); i++) {

						IJpo order = orderSet.getJpo(i);
						IJpoSet dealSet = order.getJpoSet("JXTASKEXECPERSON");// 处理人子表
						if (dealSet.isEmpty()) {
							throw new MroException("valiorder", "nodealperson");// 无现场处理人
						}

						// 启动工单工作流
						WfControlUtil.startwf(order, "TRANSORDER");

					}

				}

			}
		}
		this.SAVE();

		return GWConstant.NOACCESS_SAMEMETHOD;
	}
}
