package com.glaway.sddq.material.locbin.bean;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
/**
 * 
 * <库房管理管理仓位弹出框DataBean>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-8]
 * @since  [产品/模块版本]
 */
public class LocbinDataBean extends DataBean {

	@Override
	public void setValue(String attribute, String value, long accessModifier)
			throws MroException {
		// TODO Auto-generated method stub
		if (attribute.equalsIgnoreCase("SHELVESTYPE")
				|| attribute.equalsIgnoreCase("SHELVESNUM")
				|| attribute.equalsIgnoreCase("BINROWNUM")
				|| attribute.equalsIgnoreCase("BINCOLNUM")|| attribute.equalsIgnoreCase("layer")) {
			try {
				super.setValue(attribute, value, accessModifier);
			} catch (MroException e) {
				if ("binnum".equals(e.getKey()) && "locbin".equals(e.getGroup())) {
					showErrorMsgbox(e);
				} else {
				}
				throw e;
			}
			
		} else {
			super.setValue(attribute, value, accessModifier);
			
		}
		
	}
	

}
