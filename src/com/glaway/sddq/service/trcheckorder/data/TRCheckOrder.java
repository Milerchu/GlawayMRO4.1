package com.glaway.sddq.service.trcheckorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * TR售后检查单 主JPO
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-6]
 * @since [产品/模块版本]
 */
public class TRCheckOrder extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 8289314570542027706L;

	@Override
	public void init() throws MroException {
		String status = this.getString("status");
		if ("关闭".equals(status)) {
			setFlag(GWConstant.S_READONLY, true);
		}
		super.init();

	}
}
