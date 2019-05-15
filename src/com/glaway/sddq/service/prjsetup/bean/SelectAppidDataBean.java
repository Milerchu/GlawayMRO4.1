package com.glaway.sddq.service.prjsetup.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;

/**
 * 
 * 项目信息-选择客户DataBean
 * 
 * @author ygao
 * @version [版本号, 2017-10-18]
 * @since [产品/模块版本]
 */
public class SelectAppidDataBean extends DataBean {
	@Override
	public void initialize() throws MroException {
		super.initialize();
	}

	@Override
	public IJpoSet getJpoSet() {
		IJpoSet jpoSet = super.getJpoSet();
		String groupname;
		try {
			groupname = this.getAppBean().getJpo().getString("groupname");
			String sql = "appid not in (select appid from mobileauz where groupname = '"
					+ groupname + "' )";
			jpoSet.setQueryWhere(sql);
			jpoSet.setOrderBy("appid desc");
		} catch (MroException e) {
			e.printStackTrace();
		}
		return super.getJpoSet();
	}

	@Override
	public int dialogok() throws IOException, MroException {
		List<IJpo> vec = getJpoSet().getSelections();
		if (vec.size() > 0) {
			for (int i = 0; i < vec.size(); i++) {
				IJpo jpo = vec.get(i);
				IJpoSet jposet = this.getAppBean().getJpo()
						.getJpoSet("mobileauz");
				IJpo mobileauc = jposet.addJpo();
				String groupname = this.getAppBean().getJpo()
						.getString("groupname");
				mobileauc.setValue("groupname", groupname);
				mobileauc.setValue("appid", jpo.getString("appid"));
				mobileauc.setValue("DESCRIPTION", jpo.getString("DESCRIPTION"));
				mobileauc.setValue("type", jpo.getString("type"));
			}
		}
		this.getAppBean().SAVE();
		return super.dialogok();
	}
}
