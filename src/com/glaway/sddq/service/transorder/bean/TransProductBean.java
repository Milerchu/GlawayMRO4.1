package com.glaway.sddq.service.transorder.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 改造产品信息 databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年11月28日]
 * @since [产品/模块版本]
 */
public class TransProductBean extends DataBean {

	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {
		super.addEditRowCallBackOk();

		String status = getAppBean().getJpo().getString("status");

		if (SddqConstant.WO_STATUS_CLZ.equals(status)) {// 处理中

			IJpo exchangeRcd = getJpo();

			// 锁定下车件
			IJpoSet assetSet = exchangeRcd.getJpoSet("ASSET");
			if (assetSet != null && assetSet.count() > 0) {
				assetSet.getJpo(0).setValue("islocked", 1);
			}
			// 锁定上车件
			IJpoSet newAssetSet = exchangeRcd.getJpoSet("NEWASSET");
			if (newAssetSet != null && newAssetSet.count() > 0) {
				newAssetSet.getJpo(0).setValue("islocked", 1);
			}
		}
	}

	@Override
	public synchronized void delete() throws MroException {
		super.delete();

		String status = getAppBean().getJpo().getString("status");

		if (SddqConstant.WO_STATUS_CLZ.equals(status)) {// 处理中

			IJpo exchangeRcd = getJpo();

			// 释放锁定的下车件
			IJpoSet assetSet = exchangeRcd.getJpoSet("ASSET");
			if (assetSet != null && assetSet.count() > 0) {
				assetSet.getJpo(0).setValue("islocked", 0);
			}
			// 释放锁定的上车件
			IJpoSet newAssetSet = exchangeRcd.getJpoSet("NEWASSET");
			if (newAssetSet != null && newAssetSet.count() > 0) {
				newAssetSet.getJpo(0).setValue("islocked", 0);
			}
		}

	}

	@Override
	public synchronized void undelete() throws MroException {
		super.undelete();

		String status = getAppBean().getJpo().getString("status");

		if (SddqConstant.WO_STATUS_CLZ.equals(status)) {// 处理中

			IJpo exchangeRcd = getJpo();

			// 锁定下车件
			IJpoSet assetSet = exchangeRcd.getJpoSet("ASSET");
			if (assetSet != null && assetSet.count() > 0) {
				assetSet.getJpo(0).setValue("islocked", 1);
			}
			// 锁定上车件
			IJpoSet newAssetSet = exchangeRcd.getJpoSet("NEWASSET");
			if (newAssetSet != null && newAssetSet.count() > 0) {
				newAssetSet.getJpo(0).setValue("islocked", 1);
			}
		}
	}
}
