package com.glaway.sddq.material.mrline.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <配件申请行计算现场库存字段类>
 * 
 * @author  public2795
 * @version  [版本号, 2018-8-28]
 * @since  [产品/模块版本]
 */
public class FldLocationqty extends JpoField {
	@Override
	public void init() throws MroException {
		super.init();
		if (getJpo() != null && !StringUtil.isNullOrEmpty(this.getJpo().getAppName())) {
			setValue(getSumCurbaltotal() + "",
					GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
		}
	}
	private double getSumCurbaltotal() throws MroException {
		double total = 0;
		if (getJpo() != null) {
			String appname=this.getJpo().getAppName();
			if(appname!=null){
				IJpoSet invenSet = getJpo().getJpoSet("INVENTORY");
				if(!invenSet.isEmpty()){
					total = invenSet.sum("CURBAL");
				}		
				}
			}
		return total;
	}
}
