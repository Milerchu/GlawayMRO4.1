package com.glaway.sddq.material.transferline.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;

/**
 * 
 * <改造装箱单行DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class GzZxdTransferLineDataBean extends DataBean {
	/**
	 * 
	 * <接收按钮方法，判断是否能接收>
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void status() throws MroException, IOException {
		String status = this.getJpo().getString("status");
		if (status.equalsIgnoreCase("已接收")) {
			throw new MroException("transferline", "receive");
		}
		if (!this.getJpo().getParent().getString("status")
				.equalsIgnoreCase("在途")) {
			throw new MroException("transferline", "statusreceive");
		}

	}

	/**
	 * 删除校验
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int toggledeleterow() throws MroException, IOException {
		if (!this.getString("status").equals("未处理")) {
			throw new MroException("已发出不能删除行");
		} else {
			// TODO Auto-generated method stub
			return super.toggledeleterow();
		}
	}

}
