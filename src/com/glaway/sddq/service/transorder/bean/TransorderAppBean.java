package com.glaway.sddq.service.transorder.bean;

import java.io.IOException;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造工单Appbean
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月9日]
 * @since [产品/模块版本]
 */
public class TransorderAppBean extends AppBean {
	/**
	 * 
	 * 改造工单挂起
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public int HANGUP() throws Exception {
		if (WfControlUtil.isCurUser(getJpo())) {

			if (!"处理中".equals(getJpo().getString("status"))) {
				if ("挂起".equals(getJpo().getString("STATUS"))) {
					// 获取挂起记录表
					IJpoSet hrSet = getJpo().getJpoSet("HOLDRECORD");
					// 取结束时间未填的
					hrSet.setUserWhere("endtime is null");
					hrSet.reset();

					IJpo hr = hrSet.getJpo(0);
					// 设置结束时间为当前时间
					hr.setValue("endtime", MroServer.getMroServer().getDate());

					// 设置改造工单状态为 处理中
					getJpo().setValue("status", "处理中");
					// this.reloadPage();
					this.SAVE();
					showMsgbox("提示信息", "取消挂起成功！");
					return 0;
				} else {
					throw new MroException("", "当前状态无法操作！");
				}
			}

			this.page.loadDialog("HANGUP", this.page.getCurrEventCtrl());
		} else {
			throw new AppException("错误", "当前用户无权操作！");
		}

		return 0;
	}

	/**
	 * 改造工单删除
	 * 
	 * @author sjchen
	 */
	@Override
	public int DELETE() throws MroException, IOException {
		String reporter = getString("REPORTER");
		String status = getString("STATUS");
		// 当状态为草稿，且提报人为当前用户时可以删除
		if (status.equals("草稿") && reporter.equals(getUserInfo().getUserName())) {
			return super.DELETE();
		} else {
			throw new MroException("非创建人或草稿状态无法删除工单！");
		}

	}

	@Override
	public int ROUTEWF() throws Exception {
		IJpo transorder = this.getJpo();
		String status = transorder.getString("status");
		if ("挂起".equals(status) || "草稿".equals(status)) {

			throw new AppException("service", "cannotoperate");

		}
		// 处理中且改造产品信息子表不能为空，才能发送工作流
		if (transorder != null) {

			IJpoSet exchangerecordset = transorder.getJpoSet("EXCHANGERECORD");
			IJpoSet lossPartSet = transorder.getJpoSet("JXTASKLOSSPART");
			if (SddqConstant.WO_STATUS_CLZ.equals(status)) {
				if (exchangerecordset.isEmpty() && lossPartSet.isEmpty()) {
					throw new MroException("transorder", "exchangerecord");
				}
			}

			// 设置提报人
			if (StringUtil.isStrEmpty(transorder.getString("reporter"))) {

				transorder.setValue("reporter", getUserInfo().getPersonId(),
						GWConstant.P_NOVALIDATION_AND_NOACTION);

			}
		}

		return super.ROUTEWF();
	}

	/**
	 * 
	 * 上下车功能测试
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public int TESTUPDOWN() throws MroException, IOException {

		IJpoSet woSet = MroServer.getMroServer().getJpoSet("WORKORDER",
				MroServer.getMroServer().getSystemUserServer());
		String ordernum = getJpo().getString("ORDERNUM");
		woSet.setUserWhere("type='改造' and ordernum='" + ordernum + "'");
		woSet.reset();
		IJpo wo = woSet.getJpo();
		IJpoSet consumeSet = wo.getJpoSet("JXTASKLOSSPART");
		WorkorderUtil.consumeUpDown(consumeSet, wo);
		return 0;
	}
}
