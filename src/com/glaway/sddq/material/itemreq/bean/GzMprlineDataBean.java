package com.glaway.sddq.material.itemreq.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <服务转改造领料单领料单行DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class GzMprlineDataBean extends DataBean {
	/**
	 * 判断是否可以删除领料单行
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int toggledeleterow() throws MroException, IOException {
		// TODO Auto-generated method stub
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String APPLICANTBY = this.page.getAppBean().getJpo()
				.getString("APPLICANTBY");
		String status = this.page.getAppBean().getJpo().getString("status");

		if (status.equalsIgnoreCase("草稿") || status.equalsIgnoreCase("申请人修改")) {
			if (!personid.equalsIgnoreCase(APPLICANTBY)) {
				throw new MroException("mprline", "DELETEAPPLICANTBY");
			}
		} else {
			throw new MroException("mprline", "DELETEAPPLICANTBY");
		}

		return super.toggledeleterow();
	}

	/**
	 * 
	 * <判断是否可以点击选择物料按钮>
	 * 
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void selectinventory() throws IOException, MroException {
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String APPLICANTBY = this.page.getAppBean().getJpo()
				.getString("APPLICANTBY");
		if (!personid.equalsIgnoreCase(APPLICANTBY)) {
			throw new MroException("mprline", "SELECTAPPLICANTBY");
		} else {
			String status = this.page.getAppBean().getJpo().getString("status");
			if (status.equalsIgnoreCase("关闭")) {
				throw new AppException("mprline", "noselect");
			}
		}
	}

	/**
	 * 编辑数据时判断登录人是否申请人设置数量只读
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int editrow() throws MroException, IOException {
		// TODO Auto-generated method stub
		super.editrow();
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		// 申请人
		String APPLICANTBY = this.page.getAppBean().getJpo()
				.getString("APPLICANTBY");
		if (!personid.equalsIgnoreCase(APPLICANTBY)) {
			this.getJpo().setFieldFlag("qty", GWConstant.S_REQUIRED, false);
			this.getJpo().setFieldFlag("qty", GWConstant.S_READONLY, true);
		}
		return GWConstant.ACCESS_SAMEMETHOD;
	}

}
