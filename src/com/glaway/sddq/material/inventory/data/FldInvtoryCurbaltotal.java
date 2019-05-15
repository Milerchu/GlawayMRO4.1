package com.glaway.sddq.material.inventory.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 库存的SYS_INVENTORY表的CURBALTOTAL字段类
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-9]
 * @since [GlawayMro4.0/库存]
 */
public class FldInvtoryCurbaltotal extends JpoField {

	/**
	 * 计算库房库存总数量
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();
		if (getJpo() != null
				&& !StringUtil.isStrEmpty(this.getJpo().getAppName())) {
			if (this.getJpo().getAppName().equalsIgnoreCase("GZINV")
					|| this.getJpo().getAppName().equalsIgnoreCase("INV1030")
					|| this.getJpo().getAppName().equalsIgnoreCase("INVENTORY")
					|| this.getJpo().getAppName().equalsIgnoreCase("QTINV")) {
				// 调用计算数量方法
				setValue(getSumCurbaltotal() + "",
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			}

		}
	}

	/**
	 * 
	 * <计算数量方法>
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private double getSumCurbaltotal() throws MroException {
		double total = 0;
		if (getJpo() != null) {
			boolean isturnover = getJpo().getBoolean("ITEM.ISTURNOVER");
			boolean islot = getJpo().getBoolean("ITEM.ISLOT");
			if (isturnover) {// 周转件需要统计所有在库存中的相同物资编码的部件的数量
				IJpoSet assetSet = getJpo().getJpoSet("ASSET");
				if (assetSet.isEmpty()) {
					total = getField("CURBAL").getDoubleValue();
				} else {
					total = assetSet.count();
					// getJpo().setValue("CURBAL",
					// total,GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
				}
			} else {
				if (islot) {
					// 按批次管理但是不是周转件只需要统计所有批次余量
					IJpoSet invbSet = getJpo().getJpoSet("INVBLANCE");
					if (invbSet.isEmpty()) {
						total = getField("CURBAL").getDoubleValue();
					} else {
						total = invbSet.sum("PHYSCNTQTY");
						// getJpo().setValue("CURBAL",
						// total,GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
					}
				} else {
					total = getField("CURBAL").getDoubleValue();
				}
			}
		}
		return total;
	}
}
