package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 非序列号件上下车记录主jposet
 * 
 * @author zhuhao
 * @version [版本号, 2019年3月20日]
 * @since [产品/模块版本]
 */
public class LossPartSet extends JpoSet {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -54287326659892648L;

	@Override
	public Jpo getJpoInstance() {
		return new LossPart();
	}

}
