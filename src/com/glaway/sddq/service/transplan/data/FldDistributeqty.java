package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <功能描述>改造物料清单，物料分发校验
 * 
 * @author 20167088
 * @version [版本号, 2018-7-23]
 * @since [产品/模块版本]
 */
public class FldDistributeqty extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {//

		IJpoSet jposet = this.getJpo().getJpoSet("TRANSPLANLISTLINE");
		double undistributeqty = this.getJpo().getDouble("qty");// 清单数量
		if (jposet.isEmpty()) {
			this.getJpo().setValue("undistributeqty", undistributeqty, 112L);// 默认
			// 未分发数量
			this.getJpo().setValue("DISTRIBUTEQTY", 0, 112L);// 默认 分发数量
		} else {
			double total = jposet.sum("DISTRIBUTEQTY");
			this.getJpo().setValue("DISTRIBUTEQTY", total, 112L);// 分发数量
			this.getJpo().setValue("undistributeqty", undistributeqty - total,
					112L);// 未分发数量
		}
		super.init();
	}
}
