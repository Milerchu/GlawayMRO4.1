package com.glaway.sddq.material.locbinitem.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;

/**
 * 
 * <序列号件分配仓位DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class XlocbinItemDataBean extends DataBean {

	@Override
	public void buildJpoSet() throws MroException {
		super.buildJpoSet();
		this.jpoSet = this.getParent().getJpo().getJpoSet("NOBINASSET");
		this.jpoSet.setUserWhere("binnum is null");
		this.jpoSet.reset();
	}

	/**
	 * 确认按钮赋值
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 */
	@Override
	public int dialogok() throws IOException, MroException {
		// TODO Auto-generated method stub
		DataBean locbin = this.getAppBean().getDataBean("nolotandroa");

		if (!locbin.isEmpty()) {
			IJpo locbinjpo = locbin.getJpo();
			String binnum = locbinjpo.getString("binnum");
			int qty = locbinjpo.getInt("qty");

			List<IJpo> list = this.getJpoSet().getSelections();
			int count = list.size();
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					IJpo asset = list.get(i);
					asset.setValue("binnum", binnum);

				}
			}
			int newqty = qty + count;
			locbinjpo.setValue("qty", newqty);
		}

		return super.dialogok();
	}

}
