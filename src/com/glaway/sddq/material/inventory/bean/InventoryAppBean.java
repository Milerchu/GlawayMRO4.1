package com.glaway.sddq.material.inventory.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.page.control.Tab;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 库存管理DataBean
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-7]
 * @since [GlawayMro4.0/库存管理]
 */
public class InventoryAppBean extends AppBean {
	@Override
	public void setCurrentRow(int rowNum) throws MroException {
		super.setCurrentRow(rowNum);
		// 切换记录刷新界面
		refreshPage();
	}

	@Override
	public void afterTabChange(Tab currTab) throws IOException, MroException {
		// 标签页切换时刷新界面
		refreshPage();
		super.afterTabChange(currTab);
	}

	/**
	 * 
	 * 根据ROTATING字段的值和LOTTYPE字段的值刷新界面
	 * 
	 * @throws MroException
	 * 
	 */
	public void refreshPage() throws MroException {

		IJpo itemJpo = getJpo();
		if (itemJpo != null) {
			String itemnum = itemJpo.getString("itemnum");
			String type = ItemUtil.getItemInfo(itemnum);
			PageControl pcb = getPageControl().getControlByXmlId(
					"1508747248677");// 批次列表
			PageControl pczzj = getPageControl().getControlByXmlId(
					"1508747463306");// 有批次的周转件
			PageControl zzj = getPageControl().getControlByXmlId(
					"1510032445017");// 无批次的周转件
			try {
				if (ItemUtil.LOT_I_ITEM.equals(type)) {
					if (pcb != null && pczzj != null && zzj != null) {
						pcb.show();
						zzj.hide();
						if (ItemUtil.SQN_ITEM.equals(type)) {
							pczzj.show();

						} else {
							pczzj.hide();

						}
					}
				} else {
					if (pcb != null && pczzj != null && zzj != null) {
						pcb.hide();
						pczzj.hide();
						if (ItemUtil.SQN_ITEM.equals(type)) {
							zzj.show();

						} else {
							zzj.hide();

						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
