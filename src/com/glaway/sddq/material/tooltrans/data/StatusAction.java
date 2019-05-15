package com.glaway.sddq.material.tooltrans.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;

/**
 * 
 * <工具交接流程操作类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class StatusAction implements ActionCustomClass {
	/**
	 * 设置状态值，库管员值
	 * 
	 * @param jpo
	 * @param parameter
	 * @throws MroException
	 */
	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		// TODO Auto-generated method stub
		jpo.setValue("STATUS", "已接收");

		String newkeeper = jpo.getString("NEWKEEPER");
		String oldkeeper = jpo.getString("OLDKEEPER");

		// IJpoSet devicinfo =jpo.getJpoSet("DEVICEINFO");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		IJpoSet devicinfo = MroServer.getMroServer().getJpoSet("DEVICEINFO",
				MroServer.getMroServer().getSystemUserServer());
		devicinfo.setUserWhere("KEEPER='" + oldkeeper + "'");
		devicinfo.reset();

		for (int j = 0; j < devicinfo.count(); j++) {
			IJpo jpo1 = devicinfo.getJpo(j);

			jpo1.setValue("KEEPER", newkeeper);
		}
		devicinfo.save();

		jpo.getJpoSet().save();
	}

}
