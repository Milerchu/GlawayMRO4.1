package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 改造/验证计划 改造/验证分布 jposet
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月8日]
 * @since [产品/模块版本]
 */
public class TransdistSet extends JpoSet {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -6018530793222006161L;

	public Jpo getJpoInstance() {
		return new Transdist();
	}
}
