package com.glaway.sddq.service.chasset.data;

import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 串换记录jposet
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月26日]
 * @since [产品/模块版本]
 */
public class ChAssetSet extends JpoSet {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1600571284777021319L;

	@Override
	public Jpo getJpoInstance() {
		return new ChAsset();
	}
}
