package com.glaway.sddq.config.bzsq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * 车号字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-11-29]
 * @since  [产品/模块版本]
 */
public class FldCarno extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void init() throws MroException {
		this.setLookupMap(new String[] { "CARNO", "CMODEL","ANCESTOR"},
				new String[] { "CARNO", "CMODEL", "ASSETNUM"});
	}
	
}
