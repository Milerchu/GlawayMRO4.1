package com.glaway.sddq.material.convertlocline.bean;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨转库单行DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class ConvertloclineDatabean extends DataBean {
	/**
	 * 
	 * <接收按钮判断主信息是否完整以及是否能接收>
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public int status() throws MroException {
		String status = this.getJpo().getString("status");
		String DESCRIPTION = this.getJpo().getParent().getString("DESCRIPTION");
		if (DESCRIPTION.isEmpty()) {
			throw new MroException("请先选择描述");
		} else {
			if (status.equalsIgnoreCase("已接收")) {
				throw new MroException("transferline", "receive");
			}
		}

		return GWConstant.ACCESS_SAMEMETHOD;
	}
}
