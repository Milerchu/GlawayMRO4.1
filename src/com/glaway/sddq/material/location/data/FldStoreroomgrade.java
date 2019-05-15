package com.glaway.sddq.material.location.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <库房等级字段类>
 * 
 * @author  xlb
 * @version  [GlawayMro4.0, 2018-3-1]
 * @since  [GlawayMro4.0/库房管理]
 */
public class FldStoreroomgrade extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		if(this.getValue().equalsIgnoreCase("一级")){
          this.getJpo().setFieldFlag("STOREROOMPARENT", GWConstant.S_REQUIRED, false);
          this.getJpo().setValue("STOREROOMPARENT", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
          this.getJpo().setFieldFlag("STOREROOMPARENT", GWConstant.S_READONLY, true);
		}else{
        	this.getJpo().setFieldFlag("STOREROOMPARENT", GWConstant.S_READONLY, false);
        	this.getJpo().setFieldFlag("STOREROOMPARENT", GWConstant.S_REQUIRED, true);
		}
	}
    
}
