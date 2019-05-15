package com.glaway.sddq.service.transnotice.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造通知单AppBean
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月8日]
 * @since [产品/模块版本]
 */
public class TransNoticeAppBean extends AppBean {

	@Override
	public int DELETE() throws MroException, IOException {
		// TODO Auto-generated method stub
		String creater = getString("APPPERSON");
		String status = getString("STATUS");
		if (creater.equals(this.getUserInfo().getUserName())
				&& status.equals("草稿")) {
			return super.DELETE();
		} else {
			throw new MroException("非创建人或非草稿状态无法删除");
		}
	}

	@Override
	public int ROUTEWF() throws Exception {
		IJpoSet rangeSet = getJpo().getJpoSet("TRANSRANGE");
		if (rangeSet.isEmpty()) {
			throw new MroException("改造范围为空，无法发送工作流！");
		}

		return super.ROUTEWF();
	}

	/**
	 * 
	 * 创建改造计划
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public int CREATEPLAN() throws Exception {
		this.SAVE();
		if (getJpo().getJpoSet("TRANSCONTENT").count(
				GWConstant.P_COUNT_AFTERSAVE) < 1
				|| getJpo().getJpoSet("TRANSRANGE").count(
						GWConstant.P_COUNT_AFTERSAVE) < 1) {
			throw new MroException("请填写完所有改造信息再创建计划！");

		}
		WorkorderUtil.createTransPlan(getJpo());
		getJpo().setValue("status", "已审核");
		this.SAVE();
		return 1;
	}
}
