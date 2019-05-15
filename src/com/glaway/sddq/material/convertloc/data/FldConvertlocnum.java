package com.glaway.sddq.material.convertloc.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * <调拨转库单交货单号字段类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class FldConvertlocnum extends JpoField {
	/**
	 * 判断交货单号是否存在并提示
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String convertlocnum = this.getValue();
		IJpoSet CONVERTLOCSET = MroServer.getMroServer().getJpoSet(
				"CONVERTLOC", MroServer.getMroServer().getSystemUserServer());
		CONVERTLOCSET.setUserWhere("CONVERTLOCNUM='" + convertlocnum + "'");
		CONVERTLOCSET.reset();
		if (!CONVERTLOCSET.isEmpty()) {
			throw new MroException("CONVERTLOC", "CONVERTLOCNUM");
		}
	}

}
