package com.glaway.sddq.service.valiplan.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

public class ValiProductInfoBean extends DataBean {

	@Override
	public int addrow() throws MroException, IOException {
		// 当前操作工单jpo
		IJpo workorder = getDataBean("1533607363636").getJpo();
		String carnum = workorder.getString("carnum");
		if (carnum.isEmpty()) {
			throw new MroException("valiplan", "nocarnum");
		}
		return super.addrow();
	}

	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {

		super.addEditRowCallBackOk();

		// 设置工单时间开始时间
		IJpo vailpro = getJpo();
		String startdate = vailpro.getString("STARTDATE");
		DataBean parentDataBean = this.getParent();
		if (parentDataBean != null) {
			Date actstartdate = parentDataBean.getDate("ACTSTARTDATE");
			if (actstartdate == null) {
				parentDataBean.setValue("ACTSTARTDATE", startdate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			if (StringUtil.isStrEmpty(parentDataBean.getString("REPORTER"))) {
				// 设置提报人
				parentDataBean.setValue("REPORTER", getJpo().getUserInfo()
						.getLoginID(), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}
	}
}
