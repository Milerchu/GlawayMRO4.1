package com.glaway.sddq.material.item.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <物料管理是否批次件字段绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-8]
 * @since  [产品/模块版本]
 */
public class FldIslot extends JpoField {
/**
 * 根据选择是否批次件设置值和只读
 * @throws MroException
 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		 boolean islot = this.getJpo().getBoolean("islot");
		 if(islot){
			 this.getJpo().setValue("ISTURNOVER", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			 this.getJpo().setFieldFlag("ISTURNOVER", GWConstant.S_READONLY, true);
			 this.getJpo().setValue("LOTTYPE", "I类",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		 }else{
			 this.getJpo().setFieldFlag("ISTURNOVER", GWConstant.S_READONLY, false);
		 }
	}

}
