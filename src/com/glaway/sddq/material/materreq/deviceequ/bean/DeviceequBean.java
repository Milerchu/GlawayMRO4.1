package com.glaway.sddq.material.materreq.deviceequ.bean;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.app.system.appdesigner.bean.DesignerAppBean;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;

/**
 * 
 * <工具设备管理交接变更记录DataBean>
 * 
 * @author zxx
 * @version [版本号, 2018-6-6]
 * @since [产品/模块版本]
 */
public class DeviceequBean extends DataBean {

	public void buildJpoSet() throws MroException {

		super.buildJpoSet();

		if (!(this.page.getAppBean() instanceof DesignerAppBean)) {
			if (getAppBean().getJpo() != null
					&& !StringUtil.isNullOrEmpty(getAppBean().getJpo()
							.getString("KEEPER"))) {
				jpoSet.setUserWhere("itemnum IN (SELECT d.itemnum from Tooltrans t, Deviceinfo d WHERE t.newkeeper='"
						+ getAppBean().getJpo().getString("KEEPER")
						+ "' AND t.status='已接收')");
				jpoSet.reset();
			}
		}
	}
}
