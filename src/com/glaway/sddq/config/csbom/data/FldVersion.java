package com.glaway.sddq.config.csbom.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 版本字段类
 * 
 * @author zzx
 * @version [版本号, 2018-8-10]
 * @since [产品/模块版本]
 */
public class FldVersion extends JpoField {
	/**
	 * 唯一序列
	 */
	private static final long serialVersionUID = 1L;

	public void validate() throws MroException {
		super.validate();
		String cmodel = this.getJpo().getString("CMODEL");
		String version = getInputMroType().asString();
		// 1.取当前jposet
		IJpoSet assetcsSet = MroServer.getMroServer().getSysJpoSet("ASSETCS");

		assetcsSet.setUserWhere("CMODEL='" + cmodel + "' and VERSION='"
				+ version + "' ");
		assetcsSet.reset();
		if (assetcsSet.count() > 0) {
			throw new MroException("ASSETCS", "COMDEL");
		} else {
			this.getField("CMODEL").setErrorMsg("");
		}
	}
}
