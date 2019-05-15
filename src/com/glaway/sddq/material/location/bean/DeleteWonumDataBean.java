/*
 * 文 件 名:  StoreroomAppBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yyang
 * 修改时间:  2016-5-16
 */
package com.glaway.sddq.material.location.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <库房管理删除工作令号弹出框DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class DeleteWonumDataBean extends DataBean {
	/**
	 * 过滤相关工作令号数据
	 * 
	 * @throws MroException
	 */
	@Override
	public void buildJpoSet() throws MroException {
		super.buildJpoSet();
		IJpo locationjpo = this.getAppBean().getJpo();
		String location = locationjpo.getString("location");
		IJpoSet jposet = getJpoSet();
		jposet.setQueryWhere("location ='" + location + "'");
		jposet.reset();
	}

	/**
	 * 确认按钮删除选定工作令号
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 */
	@Override
	public int dialogok() throws IOException, MroException {
		DataBean projectinfoBean = this.page.getAppBean().getDataBean(
				"1535033292230");

		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (IJpo iJpo : list) {
				String projectnum = iJpo.getString("PROJECTNUM");
				IJpoSet projectinfoSet = MroServer.getMroServer().getJpoSet(
						"PROJECTINFO",
						MroServer.getMroServer().getSystemUserServer());
				projectinfoSet.setQueryWhere("PROJECTNUM='" + projectnum + "'");
				projectinfoSet.reset();
				projectinfoSet.getJpo().setValue("location", "",
						GWConstant.P_NOACTION);
				projectinfoSet.save();
			}
		}

		projectinfoBean.resetAndReload();

		return super.dialogok();
	}
}
