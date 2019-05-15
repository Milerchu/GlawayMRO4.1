package com.glaway.sddq.service.chasset.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 串换记录主jpo
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月26日]
 * @since [产品/模块版本]
 */
public class ChAsset extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 7294328784105122505L;

	@Override
	public void init() throws MroException {

		super.init();
		if (getBoolean("isdone")) {// 串换已经完成，记录只读
			setFlag(GWConstant.S_READONLY, true);
		} else {

			// 串换类型
			String chtype = getString("chtype");
			// 同车互换
			if (SddqConstant.SWAP_SAMETRAIN.equals(chtype)) {

				// 设置串换车号只读
				this.setFieldFlag("AFTERCARNO", GWConstant.S_READONLY, true);

			} else {
				// 两车互换
				// 设置串换车号只读取消
				this.setFieldFlag("AFTERCARNO", GWConstant.S_READONLY, false);

			}

			// 设置必填
			String[] requiredAttrs = { "BEFOREITEMNUM", "AFTERITEMNUM" };
			this.setFieldFlag(requiredAttrs, GWConstant.S_REQUIRED, true);
		}

	}
}
