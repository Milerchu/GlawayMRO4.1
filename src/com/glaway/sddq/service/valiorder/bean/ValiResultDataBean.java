package com.glaway.sddq.service.valiorder.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 验证计划-验证工单-反馈记录databean
 * 
 * @author chenbin
 * @version [版本号, 2018-9-29]
 * @since [产品/模块版本]
 */
public class ValiResultDataBean extends DataBean {

	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {

		super.addEditRowCallBackOk();
		IJpo curJpo = this.getJpo();// 当前jpo
		String ordernum = getParent().getString("ORDERNUM");// 验证工单编号
		IJpoSet valifeedbackSet = MroServer.getMroServer().getSysJpoSet(
				"VALIFEEDBACK");
		valifeedbackSet.setQueryWhere("ordernum='" + ordernum + "'");
		valifeedbackSet.setOrderBy("FEEDBACKTIME desc");
		valifeedbackSet.reset();
		if (valifeedbackSet.count() > 0) {
			// 根据反馈时间，设置最新的验证情况为验证结果
			IJpo fbJpo = valifeedbackSet.getJpo(0);
			String valicituation = "";
			Date feedbackTime = fbJpo.getDate("feedbacktime");
			Date curDate = curJpo.getDate("feedbacktime");

			if (feedbackTime.after(curDate)) {

				valicituation = fbJpo.getString("VALICITUATION");

			} else {

				valicituation = curJpo.getString("VALICITUATION");

			}

			getParent().setValue("VALIRESULT", valicituation,
					GWConstant.P_NOVALIDATION);// 设置验证工单验证结果
		}

	}
}
