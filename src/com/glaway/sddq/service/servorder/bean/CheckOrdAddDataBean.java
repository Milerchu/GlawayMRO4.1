package com.glaway.sddq.service.servorder.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 服务工单-检查单databean
 * 
 * @author ygao
 * @version [版本号, 2017-12-10]
 * @since [产品/模块版本]
 */
public class CheckOrdAddDataBean extends DataBean {

	@Override
	public int addrow() throws MroException, IOException {
		String status = this.getAppBean().getJpo().getString("STATUS");
		if ("处理中".equals(status) || "审核驳回".equals(status)) {
			super.addrow();
			String carnum = this.getAppBean().getJpo().getString("CARNUM");
			String projectnum = this.getAppBean().getJpo()
					.getString("PROJECTNUM");
			String cmodel = this.getAppBean().getJpo().getString("MODELS");
			IJpo checkord = getJpo();
			checkord.setValue("MODELS", cmodel);
			checkord.setValue("CARNUM", carnum);
			checkord.setValue("PROJECTNAME", projectnum);
			return GWConstant.ACCESS_SAMEMETHOD;
		} else {
			throw new MroException("", "当前状态无法操作!");
		}
	}
}
