package com.glaway.sddq.overhaul.jobbook.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 选择检修对象
 * 
 * @author public2175
 * @version [版本号, 2019-1-10]
 * @since [产品/模块版本]
 */
public class SelectItemDataBean extends DataBean {

	/**
	 * 初始化数据
	 * 
	 * @throws MroException
	 */
	@Override
	public void initialize() throws MroException {
		
		super.initialize();
		IJpoSet jposet = getJpoSet();
		jposet.setQueryWhere("ASSETTMPLEVEL='ASSET' and STATUS = '可用'");
		jposet.reset();

	}

	@Override
	public int dialogok() throws IOException, MroException {

		DataBean jobscopeBean = this.page.getAppBean().getDataBean(
				"1544005533746");
		IJpoSet jobscopeSet = jobscopeBean.getJpoSet();

		String jpnum = jobscopeSet.getParent().getString("JPNUM");

		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IJpo lotnumjpo = list.get(i);
				IJpo jobscope = jobscopeSet.addJpo();
				jobscope.setValue("ITEMNUM", lotnumjpo.getString("ITEMNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				jobscope.setValue("SITEID", this.page.getAppBean().getJpo()
						.getString("SITEID"));
				jobscope.setValue("ORGID", this.page.getAppBean().getJpo()
						.getString("ORGID"));
				jobscope.setValue("JPNUM", jpnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}
		jobscopeBean.reloadPage();
		return super.dialogok();
	}

}
