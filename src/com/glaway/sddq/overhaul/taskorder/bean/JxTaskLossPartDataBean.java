package com.glaway.sddq.overhaul.taskorder.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
/**
 * 
 * 批次号上下车锁定
 * 
 * @author  chenbin
 * @version  [版本号, 2019-3-22]
 * @since  [产品/模块版本]
 */
public class JxTaskLossPartDataBean extends DataBean{
	
	
	

	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {
		// TODO Auto-generated method stub
		super.addEditRowCallBackOk();
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

	@Override
	public synchronized void delete() throws MroException {
		super.delete();
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

	@Override
	public synchronized void undelete() throws MroException {
		super.undelete();

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
