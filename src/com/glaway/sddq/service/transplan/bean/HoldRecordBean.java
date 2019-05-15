package com.glaway.sddq.service.transplan.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 改造计划-挂起dialog DataBean
 * 
 * @author zhuhao
 * @version [版本号, 2018年10月22日]
 * @since [产品/模块版本]
 */
public class HoldRecordBean extends DataBean {

	@Override
	public int dialogok() throws IOException, MroException {

		IJpo plan = this.getAppBean().getJpo();

		plan.setValue("holdreason", getJpo().getString("HOLDRECORDREASON"));

		plan.setValue("STATUS", "挂起");

		// 关联工单挂起
		IJpoSet transDistSet = plan.getJpoSet("TRANSDIST");
		if (!transDistSet.isEmpty()) {
			for (int i = 0; i < transDistSet.count(); i++) {

				IJpo transDist = transDistSet.getJpo(i);
				IJpoSet orderSet = transDist.getJpoSet("TRANSORDER");
				if (!orderSet.isEmpty()) {

					for (int j = 0; j < orderSet.count(); j++) {

						IJpo order = orderSet.getJpo(j);

						order.setValue("status", "挂起",
								GWConstant.P_NOVALIDATION_AND_NOACTION);

					}

				}

			}

		}

		PageControl ctrl = getPage().getControlByXmlId("1540350223458");
		if (ctrl != null) {
			ctrl.show();// 显示挂起原因文本框
		}

		// 关闭对话框
		this.dialogclose();
		this.page.getAppBean().SAVE();

		return 1;
	}
}
