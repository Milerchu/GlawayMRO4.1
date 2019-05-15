package com.glaway.sddq.service.valiorder.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 验证工单AppBean
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月15日]
 * @since [产品/模块版本]
 */
public class ValiOrderAppBean extends AppBean {
	/**
	 * 
	 * 验证工单挂起
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public int HANGUP() throws IOException, MroException {
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

		return 0;
	}

	/**
	 * 工单删除
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
			throw new MroException("item", "cannotdeleteactive");
		}

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

		/*
		 * Workorder wo = (Workorder) getAppBean().getJpo(); IJpoSet exSet =
		 * wo.getJpoSet("EXCHANGERECORD"); wo.swapHistory(exSet);
		 */
		// WorkorderUtil.consumeUpDown(consumeSet, wo);
		return 0;
	}
}
