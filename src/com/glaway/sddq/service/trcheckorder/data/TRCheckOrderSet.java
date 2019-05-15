package com.glaway.sddq.service.trcheckorder.data;

import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 售后TR检查单 Set
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-6]
 * @since [产品/模块版本]
 */
public class TRCheckOrderSet extends JpoSet {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -4462059468980876484L;

	@Override
	public Jpo getJpoInstance() {
		return new TRCheckOrder();
	}

}
