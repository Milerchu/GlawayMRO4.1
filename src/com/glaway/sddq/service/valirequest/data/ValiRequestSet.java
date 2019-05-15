package com.glaway.sddq.service.valirequest.data;

import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * <验证申请单Set>
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-5-27]
 * @since [MRO4.1/模块版本]
 */
public class ValiRequestSet extends JpoSet {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 184774881833374970L;

	@Override
	public Jpo getJpoInstance() {
		return new ValiRequest();
	}

}
