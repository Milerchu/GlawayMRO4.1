package com.glaway.sddq.material.toolapplication.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <工具设备申请行Jpo>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class Trline extends Jpo {
	private static final long serialVersionUID = 59754576740756780L;

	/**
	 * 初始化页面控制
	 * 
	 * @throws MroException
	 */
	public void init() throws MroException {

		String status = this.getParent().getString("STATUS");
		String createby = this.getParent().getString("CREATEBY");
		if ((status.equals("草稿") && createby.equalsIgnoreCase(getUserServer()
				.getUserInfo().getUserName()))
				|| (status.equals("待修改") && createby
						.equalsIgnoreCase(getUserServer().getUserInfo()
								.getUserName()))) {
			IJpoSet trlineTmp1 = this.getJpoSet("TRLINETRANSFER");
			trlineTmp1.setFlag(GWConstant.S_READONLY, true);
		}

		if (status.equals("待审批")
				&& (!createby.equalsIgnoreCase(getUserServer().getUserInfo()
						.getUserName()))) {

			// this.setFlag(GWConstant.S_READONLY, true);

			String trlinenum = getString("TRLINENUM");
			IJpoSet trlinetransferSet = this.getJpoSet("TRLINETRANSFER");
			int sum = 0;
			for (int i = 0; i < trlinetransferSet.count(); i++) {
				int val = trlinetransferSet.getJpo(i).getInt("TRANSFERQTY");
				sum = sum + val;
			}
			setValue("PLANTRANSFERQTY", sum);

		}
		super.init();
	}
}