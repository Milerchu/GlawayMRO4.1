package com.glaway.sddq.service.valiplan.data;

import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 验证计划-验证产品范围 jpoSet
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月30日]
 * @since [产品/模块版本]
 */
public class ValiProRangeSet extends JpoSet {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Jpo getJpoInstance() {
		return new ValiProRange();
	}
}
