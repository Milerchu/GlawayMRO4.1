package com.glaway.sddq.material.assetlifecommon;

/**
 * 
 * <一车一档公共类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-4-15]
 * @since  [产品/模块版本]
 */
public class CommomCarLife {
	private static CommomCarLife wfCtrl = null;;

	private CommomCarLife() {
	}

	public synchronized static CommomCarLife getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new CommomCarLife();
		}
		return wfCtrl;
	}
}
