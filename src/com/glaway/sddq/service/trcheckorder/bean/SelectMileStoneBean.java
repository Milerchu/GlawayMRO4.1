package com.glaway.sddq.service.trcheckorder.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 售后TR检查单 选择里程碑模板 databean
 * 
 * @author sjchen
 */
public class SelectMileStoneBean extends DataBean {

	@Override
	public void buildJpoSet() throws MroException {
		super.buildJpoSet();
		// 根据 检查阶段 字段过滤
		IJpo mbo = this.page.getAppBean().getDataBean("tab_checkdetail")
				.getParent().getJpo();
		String stagenum = mbo.getString("STEP");
		if (null != stagenum && !"".equals(stagenum)) {
			jpoSet.setUserWhere("step='" + stagenum + "'");
			jpoSet.reset();
		}
	}

	@Override
	public int dialogok() throws IOException, MroException {
		// 检查项明细databean
		DataBean msdataBean = this.page.getAppBean().getDataBean(
				"tab_checkdetail");

		IJpoSet milestoneSet = MroServer.getMroServer()
				.getJpoSet("TRCHECKDETAIL",
						MroServer.getMroServer().getSystemUserServer());// 获得检查单明细的表
		String trcheckordernum = this.page.getAppBean().getJpo()
				.getString("TRCHECKORDERNUM");

		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IJpo mbo = list.get(i);
				IJpo milestone = milestoneSet.addJpo();
				milestone.setValue("SEQUENCE", mbo.getString("SEQUENCE"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				milestone.setValue("CHECKTYPE", mbo.getString("CHECKTYPE"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				milestone.setValue("CHECKCLASS", mbo.getString("CHECKCLASS"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				milestone.setValue("CHECCONTEXT", mbo.getString("CHECCONTEXT"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

				milestone.setValue("TRCHECKORDERNUM", trcheckordernum);
				milestone.setValue("ORGID", "CRRC");
				milestone.setValue("SITEID", "ELEC");
			}
		}
		milestoneSet.save();
		msdataBean.resetAndReload();
		milestoneSet.destroy();
		return super.dialogok();
	}
}
