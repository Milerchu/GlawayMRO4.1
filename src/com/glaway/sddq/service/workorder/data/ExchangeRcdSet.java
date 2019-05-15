package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 上下车记录jposet
 * 
 * @author zhuhao
 * @version [版本号, 2019年3月20日]
 * @since [产品/模块版本]
 */
public class ExchangeRcdSet extends JpoSet {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -8363568177110052013L;

	@Override
	public Jpo getJpoInstance() {
		return new ExchangeRcd();
	}
}
