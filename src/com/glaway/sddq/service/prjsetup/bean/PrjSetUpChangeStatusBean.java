package com.glaway.sddq.service.prjsetup.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.service.prjsetup.data.ProjectInfo;

/**
 * 
 * 项目信息变更状态databean
 * 
 * @author ygao
 * @version [版本号, 2017-11-7]
 * @since [产品/模块版本]
 */
public class PrjSetUpChangeStatusBean extends DataBean {
	@Override
	public int execute() throws MroException {
		IJpo mr = getAppBean().getJpo();
		// 列表数据为空时
		if (mr == null) {
			return GWConstant.NOACCESS_SAMEMETHOD;
		}
		ProjectInfo ct = (ProjectInfo) mr;
		checkSave();

		ct.changestatus(getString("status"), getString("memo"));
		try {
			if (getAppBean().getJpo().getString("STATUS").equals("已立项")) {
				getAppBean().getJpo().setFieldFlag("PROJECTNAME",
						GWConstant.S_READONLY, true);
			}
			getAppBean().SAVE();
			// this.reloadPage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}
}
