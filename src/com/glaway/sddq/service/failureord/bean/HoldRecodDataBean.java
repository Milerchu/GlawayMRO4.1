package com.glaway.sddq.service.failureord.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 工单挂起dialog databean
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月8日]
 * @since [产品/模块版本]
 */
public class HoldRecodDataBean extends DataBean {
	@Override
	public int dialogok() throws IOException, MroException {

		/* 挂起记录表添加数据 */
		IJpoSet holdrecordset = MroServer.getMroServer().getJpoSet(
				"HOLDRECORD", MroServer.getMroServer().getSystemUserServer());
		IJpo holdrecord = holdrecordset.addJpo();
		holdrecord.setValue("HOLDPERSON", this.getUserInfo().getUserName());
		holdrecord.setValue("STARTTIME", MroServer.getMroServer().getDate());
		holdrecord.setValue("HOLDREASON", getJpo()
				.getString("HOLDRECORDREASON"));
		holdrecord.setValue("FAILUREORDERNUM",
				getAppBean().getJpo().getString("ORDERNUM"));
		holdrecord
				.setValue("SITEID", getAppBean().getJpo().getString("SITEID"));
		holdrecord.setValue("ORGID", getAppBean().getJpo().getString("ORGID"));

		holdrecordset.save();

		// 设置工单状态
		getAppBean().getJpo().setValue("STATUS", "挂起");
		return super.dialogok();
	}
}
