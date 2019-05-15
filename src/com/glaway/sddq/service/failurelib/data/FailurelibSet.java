package com.glaway.sddq.service.failurelib.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 故障件记录 failurelib jposet
 * 
 * @author zhuho
 * @version [版本号, 2019年1月7日]
 * @since [产品/模块版本]
 */
public class FailurelibSet extends JpoSet {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取故障记录
	 * 
	 * @return
	 */
	public Failurelib getJpoInstance() {
		return new Failurelib();
	}
}
