package com.glaway.sddq.material.invtrans.common;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <装箱单获取ASSETNUM公共类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public final class CommAssetNum {

	private static CommAssetNum wfCtrl = null;;

	private CommAssetNum() {
	}

	public synchronized static CommAssetNum getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new CommAssetNum();
		}
		return wfCtrl;
	}

	/**
	 * 
	 * <获取当前jposet中ASSETNUM集合>
	 * 
	 * @param transferline
	 * @return [参数说明]
	 * 
	 */
	public static String GETASSETNUM(IJpoSet transferline) {
		String assetnums = null;
		try {
			if (!transferline.isEmpty()) {
				for (int i = 0; i < transferline.count(); i++) {
					if (transferline.getJpo(i) != null) {

						String assetnum = transferline.getJpo(i).getString(
								"assetnum");
						if (!"".equals(assetnum)) {
							if (assetnums == null)
								assetnums = "'"
										+ StringUtil.getSafeSqlStr(assetnum)
										+ "'";
							else
								assetnums = assetnums + ",'"
										+ StringUtil.getSafeSqlStr(assetnum)
										+ "'";
						}
					}
				}
			}
			return assetnums;
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
