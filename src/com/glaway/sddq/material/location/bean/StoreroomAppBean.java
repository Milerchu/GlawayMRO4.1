/*
 * 文 件 名:  StoreroomAppBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yyang
 * 修改时间:  2016-5-16
 */
package com.glaway.sddq.material.location.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.page.control.Tab;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 库房应用程序AppBean
 * 
 * @author yyang
 * @version [版本号, 2016-5-16]
 * @since [产品/模块版本]
 */
public class StoreroomAppBean extends AppBean {
	/**
	 * 新增赋值type
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 */
	@Override
	public int INSERT() throws IOException, MroException {
		int re = super.INSERT();
		this.getJpo().setValue("type", "库房");
		return re;
	}

	/**
	 * 
	 * <判断是否可管理仓位>
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public int LOCBIN() throws MroException {
		IJpo locJpo = getJpo();
		boolean islocbin = locJpo.getBoolean("islocbin");
		if (!islocbin) {
			throw new MroException("该库房不做仓位管理");
		}

		return GWConstant.ACCESS_SAMEMETHOD;

	}

	@Override
	public void afterTabChange(Tab currTab) throws IOException, MroException {
		// 标签页切换时刷新界面
		refreshPage();

		super.afterTabChange(currTab);
	}

	public void refreshPage() throws MroException {

		IJpo locJpo = getJpo();
		if (locJpo != null) {
			PageControl selectwonum = getPageControl().getControlByXmlId(
					"1525674497471");// 选择工作令号按钮
			PageControl deletewonum = getPageControl().getControlByXmlId(
					"1525677198107");// 删除作令号按钮
			PageControl child = getPageControl().getControlByXmlId(
					"1519798248639");// 子库房
			PageControl childinv = getPageControl().getControlByXmlId(
					"1508738083999");// 子库房库存
			PageControl inv = getPageControl().getControlByXmlId(
					"1519808153648");// 库房库存
			String STOREROOMGRADE = locJpo.getString("STOREROOMGRADE");
			String ERPLOC = locJpo.getString("ERPLOC");
			if (ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1020)
					|| ERPLOC.equalsIgnoreCase(ItemUtil.ERPLOC_1030)) {
				if (STOREROOMGRADE.equalsIgnoreCase("一级")) {
					try {
						if (child != null) {
							child.show();
						}
						if (childinv != null) {
							childinv.show();
						}
						if (inv != null) {
							inv.hide();
						}
						if (selectwonum != null) {
							selectwonum.hide();
						}
						if (deletewonum != null) {
							deletewonum.hide();
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					try {
						if (child != null) {
							child.hide();
						}
						if (childinv != null) {
							childinv.hide();
						}
						if (inv != null) {
							inv.show();
						}
						if (selectwonum != null) {
							selectwonum.show();
						}
						if (deletewonum != null) {
							deletewonum.show();
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				try {
					if (child != null) {
						child.hide();
					}
					if (childinv != null) {
						childinv.hide();
					}
					if (inv != null) {
						inv.show();
					}
					if (selectwonum != null) {
						selectwonum.show();
					}
					if (deletewonum != null) {
						deletewonum.show();
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
