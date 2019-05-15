package com.glaway.sddq.material.toolapplication.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <工具设备申请表Jpo>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class Toolapplication extends Jpo {
	private static final long serialVersionUID = 59754576740756780L;

	/**
	 * 初始化页面控制
	 * 
	 * @throws MroException
	 */
	public void init() throws MroException {
		String createby = this.getString("CREATEBY");

		if (getString("STATUS").equals("待评审")
				|| getString("STATUS").equals("已审批")
				|| getString("STATUS").equals("已评审")
				|| getString("STATUS").equals("待调拨")
				|| getString("STATUS").equals("已完成")) {
			// this.setFieldFlag("CREATEDATE", GWConstant.S_READONLY, true);

			this.setFlag(GWConstant.S_READONLY, true);

		} else {

			if (getString("STATUS").equals("待修改")
					&& (!createby.equalsIgnoreCase(getUserServer()
							.getUserInfo().getUserName()))) {
				this.setFlag(GWConstant.S_READONLY, true);

			}
			if (getString("STATUS").equals("待审批")
					&& createby.equalsIgnoreCase(getUserServer().getUserInfo()
							.getUserName())) {
				this.setFlag(GWConstant.S_READONLY, true);
				/*
				 * this.setFieldFlag("CREATEBY", GWConstant.S_READONLY, true);
				 * this.setFieldFlag("CREATEDATE", GWConstant.S_READONLY, true);
				 * this.setFieldFlag("RECEIVEADDRESS", GWConstant.S_READONLY,
				 * true); this.setFieldFlag("REQWHY", GWConstant.S_READONLY,
				 * true); this.setFieldFlag("RECEIVEBY", GWConstant.S_READONLY,
				 * true);
				 */
			}
			if (getString("STATUS").equals("待审批")) {

				this.setFieldFlag("CREATEBY", GWConstant.S_READONLY, true);
				this.setFieldFlag("CREATEDATE", GWConstant.S_READONLY, true);
				this.setFieldFlag("RECEIVEADDRESS", GWConstant.S_READONLY, true);
				this.setFieldFlag("REQWHY", GWConstant.S_READONLY, true);
				this.setFieldFlag("RECEIVEBY", GWConstant.S_READONLY, true);

			}

		}

	}

}
