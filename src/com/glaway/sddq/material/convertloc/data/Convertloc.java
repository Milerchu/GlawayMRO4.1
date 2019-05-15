package com.glaway.sddq.material.convertloc.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨转库单JPO>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class Convertloc extends Jpo {

	@Override
	/**
	 * 初始化根据状态设置JPO只读
	 * @throws MroException
	 */
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		if (this.getString("status").equalsIgnoreCase("已关闭")) {
			this.setFlag(GWConstant.S_READONLY, true);
		}
	}

}
