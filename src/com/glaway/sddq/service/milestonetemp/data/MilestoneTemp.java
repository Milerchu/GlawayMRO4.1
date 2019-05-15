package com.glaway.sddq.service.milestonetemp.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;

/**
 * 
 * 里程碑模板 jpo
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月8日]
 * @since [产品/模块版本]
 */
public class MilestoneTemp extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void unselect() {
		String appname = getAppName();
		if (!"MSTONETMP".equalsIgnoreCase(appname)) {

			try {
				boolean isedited = getBoolean("ISEDITED");
				if (!isedited) {

					// throw new MroException("", "当前里程碑不可裁剪！");
				}

			} catch (MroException e) {
				e.printStackTrace();
			}

		}
		super.unselect();
	}
}
