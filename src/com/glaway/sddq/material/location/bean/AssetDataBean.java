package com.glaway.sddq.material.location.bean;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 
 * <库房管理变更序列号DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class AssetDataBean extends DataBean {
	/**
	 * 
	 * <判断是否能修改序列号>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void changesqn() throws MroException {
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String KEEPER = this.page.getAppBean().getJpo().getString("KEEPER");
		IJpoSet GROUPUSER = MroServer.getMroServer().getSysJpoSet(
				"SYS_GROUPUSER");// 判断登陆人是不是在系统管理员组
		GROUPUSER.setUserWhere("groupname='SDDQDEFAULT' and userid='"
				+ personid + "'");
		if (!personid.equalsIgnoreCase(KEEPER)) {
			if (GROUPUSER.isEmpty()) {
				throw new MroException("asset", "nokeepetandadmin");
			} else {
				String sqn = this.getJpo().getString("sqn");
				if (!sqn.startsWith("LS")) {
					throw new MroException("asset", "nochanged");
				}
			}
		} else {
			String sqn = this.getJpo().getString("sqn");
			if (!sqn.startsWith("LS")) {
				throw new MroException("asset", "nochanged");
			}
		}

	}
}
