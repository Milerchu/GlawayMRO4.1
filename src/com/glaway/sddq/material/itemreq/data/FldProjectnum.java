package com.glaway.sddq.material.itemreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 领料单项目编号字段类
 * 
 * @author zzx
 * @version [GlawayMro4.0, 2018-6-10]
 * @since [GlawayMro4.0/领料单]
 */
public class FldProjectnum extends JpoField {
	private static final long serialVersionUID = 1L;

	/**
	 * 根据类型过滤项目信息
	 * 
	 * @return
	 * @throws MroException
	 */
	public IJpoSet getList() throws MroException {

		String type = this.getJpo().getString("GDTYPE");
		String tasknum = this.getJpo().getString("TASKNUM");

		if (type.equals("服务") && tasknum != null) {
			IJpoSet workorderSet = MroServer.getMroServer().getJpoSet(
					"SERVPLAN", MroServer.getMroServer().getSystemUserServer());
			workorderSet
					.setUserWhere("SERVPLANNUM  in(select PLANNUM from workorder where TYPE='服务'");
			workorderSet.reset();

		} else if (type.equals("故障") && tasknum != null) {
			IJpoSet workorderSet = MroServer.getMroServer()
					.getJpoSet("WORKORDER",
							MroServer.getMroServer().getSystemUserServer());
			workorderSet
					.setUserWhere("SERVPLANNUM  in(select PLANNUM from workorder where TYPE='服务'");
			workorderSet.reset();
		}
		return super.getList();
	}

}
