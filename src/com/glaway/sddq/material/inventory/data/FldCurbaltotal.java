package com.glaway.sddq.material.inventory.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 库存的SYS_ITEM表的CURBALTOTAL字段类
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-9]
 * @since [GlawayMro4.0/库存]
 */
public class FldCurbaltotal extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 计算库存总数量
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		if (getJpo() != null
				&& !StringUtil.isNullOrEmpty(this.getJpo().getAppName())) {
			if (this.getJpo().getAppName().equalsIgnoreCase("ITEM")
					|| this.getJpo().getAppName().equalsIgnoreCase("GZINV")
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
			String appname = this.getJpo().getAppName();
			if (appname != null) {
				//
				String itemnum = getJpo().getString("itemnum");
				String type = ItemUtil.getItemInfo(itemnum);
				if (ItemUtil.SQN_ITEM.equals(type)) {// 按批次管理但是周转件需要统计所有在库存中的相同物资编码的部件的数量
					if (appname.equalsIgnoreCase("INVENTORY")) {// 1020库存
						IJpoSet assetSet = getJpo().getJpoSet("ASSET");
						if (assetSet.isEmpty()) {
							IJpoSet invbSet = getJpo().getJpoSet("INVENTORY");
							total = invbSet.sum("CURBAL");
						} else {
							total = assetSet.count();
						}
					}
					if (appname.equalsIgnoreCase("INV1030")) {// 1030库存
						IJpoSet assetSet = getJpo().getJpoSet("ASSET1030");
						if (assetSet.isEmpty()) {
							IJpoSet invbSet = getJpo().getJpoSet("INV1030");
							total = invbSet.sum("CURBAL");
						} else {
							total = assetSet.count();
						}
					}
					if (appname.equalsIgnoreCase("GZINV")) {// 改造库存
						IJpoSet assetSet = getJpo().getJpoSet("GZASSET");
						if (assetSet.isEmpty()) {
							IJpoSet invbSet = getJpo().getJpoSet("GZINV");
							total = invbSet.sum("CURBAL");
						} else {
							total = assetSet.count();
						}
					}
					if (appname.equalsIgnoreCase("QTINV")) {// 其他待处理库存
						IJpoSet assetSet = getJpo().getJpoSet("QTASSET");
						if (assetSet.isEmpty()) {
							IJpoSet invbSet = getJpo().getJpoSet("QTINV");
							total = invbSet.sum("CURBAL");
						} else {
							total = assetSet.count();
						}
					}

				} else {
					if (ItemUtil.LOT_I_ITEM.equals(type)) {
						// 按批次管理但是不是周转件只需要统计所有批次余量
						if (appname.equalsIgnoreCase("INVENTORY")) {// 1020库存
							IJpoSet invbSet = getJpo().getJpoSet("INVBLANCE");
							if (invbSet.isEmpty()) {
								IJpoSet invenSet = getJpo().getJpoSet(
										"INVENTORY");
								total = invenSet.sum("CURBAL");
							} else {
								total = invbSet.sum("PHYSCNTQTY");
							}
						}
						if (appname.equalsIgnoreCase("INV1030")) {// 1030库存
							IJpoSet invbSet = getJpo().getJpoSet(
									"INVBLANCE1030");
							if (invbSet.isEmpty()) {
								IJpoSet invenSet = getJpo()
										.getJpoSet("INV1030");
								total = invenSet.sum("CURBAL");
							} else {
								total = invbSet.sum("PHYSCNTQTY");
							}
						}
						if (appname.equalsIgnoreCase("GZINV")) {// 改造库存
							IJpoSet invbSet = getJpo().getJpoSet("GZINVBLANCE");
							if (invbSet.isEmpty()) {
								IJpoSet invenSet = getJpo().getJpoSet("GZINV");
								total = invenSet.sum("CURBAL");
							} else {
								total = invbSet.sum("PHYSCNTQTY");
							}
						}
						if (appname.equalsIgnoreCase("QTINV")) {// 其他待处理库存
							IJpoSet invbSet = getJpo().getJpoSet("QTINVBLANCE");
							if (invbSet.isEmpty()) {
								IJpoSet invenSet = getJpo().getJpoSet("QTINV");
								total = invenSet.sum("CURBAL");
							} else {
								total = invbSet.sum("PHYSCNTQTY");
							}
						}

					} else {
						if (appname.equalsIgnoreCase("INVENTORY")) {// 1020库存
							IJpoSet invbSet = getJpo().getJpoSet("INVENTORY");
							total = invbSet.sum("CURBAL");
						}
						if (appname.equalsIgnoreCase("INV1030")) {// 1030库存
							IJpoSet invbSet = getJpo().getJpoSet("INV1030");
							total = invbSet.sum("CURBAL");
						}
						if (appname.equalsIgnoreCase("GZINV")) {// 改造库存
							IJpoSet invbSet = getJpo().getJpoSet("GZINV");
							total = invbSet.sum("CURBAL");
						}
						if (appname.equalsIgnoreCase("QTINV")) {// 其他待处理库存
							IJpoSet invbSet = getJpo().getJpoSet("QTINV");
							total = invbSet.sum("CURBAL");
						}

					}
				}
			}

		}
		return total;
	}
}
