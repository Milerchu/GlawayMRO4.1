package com.glaway.sddq.overhaul.jctaskorder.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;
import com.glaway.sddq.overhaul.jobbook.data.JobTestRecord;
/**
 * 
 * 交车产品列表Jposet
 * 
 * @author  chenbin
 * @version  [版本号, 2018-8-8]
 * @since  [产品/模块版本]
 */
public class JcProductSet extends JpoSet implements IJpoSet {

	private static final long serialVersionUID = 1L;

	public JcProduct getJpoInstance() {
		return new JcProduct();
	}
}
