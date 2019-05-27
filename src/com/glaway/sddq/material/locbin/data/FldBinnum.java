package com.glaway.sddq.material.locbin.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <仓位表仓位编码字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldBinnum extends JpoField {
	/**
	 * 校验仓位编码
	 * 
	 * @throws MroException
	 */
	@Override
	public void validate() throws MroException {
		// TODO Auto-generated method stub
		super.validate();
		IJpoSet locbinset = this.getJpo().getParent().getJpoSet("locbin");
		locbinset.setUserWhere("locbinid!='"
				+ this.getJpo().getString("locbinid") + "'");
		if (locbinset.count() != 0) {
			for (int i = 0; i < locbinset.count(); i++) {
				String binnum = locbinset.getJpo(i).getString("binnum");
				if (binnum.equalsIgnoreCase(getInputMroType().asString())
						&& !"".equalsIgnoreCase(getInputMroType().asString())) {
					throw new MroException("locbin", "binnum",
							new String[] { getInputMroType().asString() });
				}
			}
		}

	}

}
