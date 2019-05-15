package com.glaway.sddq.material.locaddress.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <库房地址主类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-23]
 * @since [产品/模块版本]
 */
public class Locaddress extends Jpo {
	/**
	 * 非新建状态下设置库房地址字段只读控制
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		if (!this.isNew()) {
			if (this.getParent() != null) {
				String locaddressid = this.getString("locaddressid");
				IJpoSet locaddressSet = MroServer.getMroServer().getJpoSet(
						"locaddress",
						MroServer.getMroServer().getSystemUserServer());
				locaddressSet.setUserWhere("location='"
						+ this.getParent().getString("location")
						+ "' and isaddress='是' and locaddressid not in ('"
						+ locaddressid + "')");
				if (!locaddressSet.isEmpty()) {
					this.setFieldFlag("isaddress", GWConstant.S_READONLY, true);
				} else {
					this.setFieldFlag("isaddress", GWConstant.S_READONLY, false);
				}
			}

		}

	}

	@Override
	public void add() throws MroException {
		// TODO Auto-generated method stub
		super.add();
		String locaddressid = this.getString("locaddressid");
		IJpoSet locaddressSet = MroServer.getMroServer().getJpoSet(
				"locaddress", MroServer.getMroServer().getSystemUserServer());
		locaddressSet.setUserWhere("location='"
				+ this.getParent().getString("location")
				+ "' and isaddress='是' and locaddressid not in ('"
				+ locaddressid + "')");
		if (!locaddressSet.isEmpty()) {
			this.setFieldFlag("isaddress", GWConstant.S_READONLY, true);
		} else {
			this.setFieldFlag("isaddress", GWConstant.S_READONLY, false);
		}
	}

	@Override
	public void delete() throws MroException {
		// TODO Auto-generated method stub
		if (this.getString("isaddress").equalsIgnoreCase("是")) {
			throw new MroException("locaddress", "contdelete");
		} else {
			super.delete();
		}

	}

}
