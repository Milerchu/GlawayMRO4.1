package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 检修工单中的车号字段类
 * 
 * @author hyhe
 * @version [版本号, 2018-1-24]
 * @since [产品/模块版本]
 */
public class FldCarNo extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		this.setLookupMap(new String[] { "CARNO", "CMODEL", "PROJECTNUM" ,"ASSETNUM"},
				new String[] { "CARNO", "CMODEL", "PROJECTNUM" ,"ASSETNUM"});
	}

}
