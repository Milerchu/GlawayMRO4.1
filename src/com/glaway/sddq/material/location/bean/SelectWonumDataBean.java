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
 * <库房管理选择工作令号弹出框DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class SelectWonumDataBean extends DataBean {
	/**
	 * 过滤工作令号数据
	 * 
	 * @throws MroException
	 */
	@Override
	public void buildJpoSet() throws MroException {
		super.buildJpoSet();
		IJpoSet jposet = getJpoSet();
		jposet.setUserWhere("location is null");
		jposet.reset();
	}

	/**
	 * 确认按钮赋值工作令号关联到当前库房
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 */
	@Override
	public int dialogok() throws IOException, MroException {
		DataBean projectinfoBean = this.page.getAppBean().getDataBean(
				"1535033292230");
		IJpo locationjpo = this.getAppBean().getJpo();
		String location = locationjpo.getString("location");

		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (IJpo iJpo : list) {
				String projectnum = iJpo.getString("PROJECTNUM");
				IJpoSet projectinfoSet = MroServer.getMroServer().getJpoSet(
						"PROJECTINFO",
						MroServer.getMroServer().getSystemUserServer());
				projectinfoSet.setUserWhere("PROJECTNUM='" + projectnum + "'");
				projectinfoSet.reset();
				projectinfoSet.getJpo().setValue("location", location,
						GWConstant.P_NOACTION);
				projectinfoSet.save();
			}
		}

		projectinfoBean.resetAndReload();

		return super.dialogok();
	}
}
