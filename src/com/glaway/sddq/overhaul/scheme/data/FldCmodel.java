package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * 检修方案车型字段类
 * 
 * @author  chenbin
 * @version  [版本号, 2018-8-6]
 * @since  [产品/模块版本]
 */
public class FldCmodel extends JpoField {

	  /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();
		if(this.isValueChanged()){
			this.getJpo().setValueNull("REPAIRPROCESS");
		}
	}    
}
