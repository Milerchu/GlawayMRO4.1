package com.glaway.sddq.service.milestonetemp.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 里程碑模板jposet
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月8日]
 * @since [产品/模块版本]
 */
public class MilestoneTempSet extends JpoSet {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	public MilestoneTemp getJpoInstance() {
		return new MilestoneTemp();
	}
}
