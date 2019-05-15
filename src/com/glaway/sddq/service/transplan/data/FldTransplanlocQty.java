package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <功能描述>改造物料计划，物料分发校验
 * 
 * @author 20167088
 * @version [版本号, 2018-7-23]
 * @since [产品/模块版本]
 */
public class FldTransplanlocQty extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		double qty = this.getInputMroType().asDouble();
		// double qtytotal = this.getJpo().getDouble(
		// "TRANSMATERIALPLAN.MATERIALCOUNT");// 总数量
		// IJpoSet jpoiset = this.getJpo().getJpoSet("$TRANSPLANLOC",
		// "TRANSPLANLOC", "ITEMNUM=:ITEMNUM and TRANSPLANNUM=:TRANSPLANNUM");
		// if (!jpoiset.isEmpty() && jpoiset.count() > 1) {
		// IJpoSet jposet = this.getJpo().getJpoSet("$TRANSPLANLOC",
		// "TRANSPLANLOC",
		// "ITEMNUM=:ITEMNUM and TRANSPLANLOCID!=:TRANSPLANLOCID and TRANSPLANNUM=:TRANSPLANNUM");
		// if (!jposet.isEmpty()) {
		// double total = jposet.sum("qty");
		// if (qtytotal < total + qty) {
		// throw new MroException("TRANSPLANLIST", "TRANSPLANLIST", new String[]
		// { "分发数量之和不能大于清单数量" });
		// }
		// }
		// } else {
		// if (qtytotal < qty) {
		// throw new MroException("TRANSPLANLIST", "TRANSPLANLIST", new String[]
		// { "分发数量之和不能大于清单数量" });
		// }
		// }
		IJpo parent = getJpo().getParent();
		double undistQty = 0.0;
		if ("TRANSMATERIALPLAN".equals(parent.getName())) {

			undistQty = parent.getDouble("UNDISTQTY");

		} else {

			undistQty = parent.getJpoSet("TRANSMATERIALPLAN").getJpo()
					.getDouble("UNDISTQTY");

		}

		if (qty > undistQty) {
			// 分发数量不能大于未分发数量
			throw new MroException("transplan", "outofundist");
		}
		super.action();

	}

}
