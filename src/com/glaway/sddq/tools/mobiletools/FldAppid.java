package com.glaway.sddq.tools.mobiletools;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 移动端appid字典 APPID字段类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月10日]
 * @since [产品/模块版本]
 */
public class FldAppid extends JpoField {
	private static final long serialVersionUID = 6714808953825812433L;

	@Override
	public void action() throws MroException {
		super.action();
		String appid = this.getJpo().getString("appid");
		IJpoSet mobiletools = this.getJpo().getJpoSet("$mobiletools",
				"mobiletools", "appid='" + appid + "'");
		if (!mobiletools.isEmpty()) {
			throw new MroException("configure", "BlankMsg",
					new String[] { "appid,重复" });
		}
	}

}
