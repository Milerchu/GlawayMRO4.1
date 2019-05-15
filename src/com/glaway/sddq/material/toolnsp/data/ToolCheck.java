package com.glaway.sddq.material.toolnsp.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <工具送检Jpo>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class ToolCheck extends Jpo {
	private static final long serialVersionUID = 59754576740756780L;

	/**
	 * 初始化页面控制
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {

		String createby = this.getParent().getString("CREATEBY");
		String status = this.getParent().getString("STATUS");
		String checkstatus = this.getString("STATUS");
		if (this.getParent().getString("STATUS").equals("待处理")
				&& this.getString("STATUS").equals("正常")
				&& createby.equalsIgnoreCase(getUserServer().getUserInfo()
						.getUserName())) {
			this.setFieldFlag("PLANCHECKDATE", GWConstant.S_READONLY, true);
			this.setFieldFlag("ACTCHECKDATE", GWConstant.S_READONLY, true);
			this.setFieldFlag("ISRETURN", GWConstant.S_READONLY, true);
			this.setFieldFlag("CHECKRESULT", GWConstant.S_READONLY, true);
			this.setFieldFlag("STATUS", GWConstant.S_READONLY, true);
		}

		super.init();

	}
}
