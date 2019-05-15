package com.glaway.sddq.service.transnotice.data;

import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 改造通知单jposet
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月9日]
 * @since [产品/模块版本]
 */
public class TransnoticeSet extends JpoSet {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -3258350238751970675L;

	@Override
	public Jpo getJpoInstance() {
		return new Transnotice();
	}
}
