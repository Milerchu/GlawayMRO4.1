package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <服务单位字段类>
 * 
 * @author zzx
 * @version [MRO4.0, 2018-8-16]
 * @since [MRO4.0/模块版本]
 */
public class FldServcompany extends JpoField {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 6412918810073787753L;

	public void action() throws MroException {
		// 设置所属办事处
		if ("SERVORDER".equalsIgnoreCase(getJpo().getAppName())
				|| "FAILUREORD".equalsIgnoreCase(getJpo().getAppName())
				|| "JXTASKORDE".equalsIgnoreCase(getJpo().getAppName())) {
			// getJpo().setFieldFlag("WHICHOFFICE", GWConstant.S_READONLY,
			// true);
			getJpo().setValue("WHICHOFFICE",
					getJpo().getString("SERVCOMPANY.WHICHOFFICE"));

		}
		if (StringUtil.isStrEmpty(getJpo().getAppName())) {
			String type = getJpo().getString("type");
			if ("故障".equals(type) || "服务".equals(type)) {
				getJpo().setValue("WHICHOFFICE",
						getJpo().getString("SERVCOMPANY.WHICHOFFICE"));
			}
		}

		super.action();
	}
}
