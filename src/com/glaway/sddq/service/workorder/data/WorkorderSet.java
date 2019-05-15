package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 工单workorder jposet
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月9日]
 * @since [产品/模块版本]
 */
public class WorkorderSet extends JpoSet {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Jpo getJpoInstance() {
		return new Workorder();
	}
}
