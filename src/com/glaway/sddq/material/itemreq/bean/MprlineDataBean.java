package com.glaway.sddq.material.itemreq.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;

/**
 * 
 * <领料单行DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class MprlineDataBean extends DataBean {
	/**
	 * 判断非新增和修改状态非创建人不能删除
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
	 * <判断非创建人不能选择物料以及关闭状态不能选择物料>
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
		String MPRSTOREROOM = this.page.getAppBean().getJpo()
				.getString("MPRSTOREROOM");
		if(MPRSTOREROOM.isEmpty()){
			throw new MroException("请先选择领料库房");
		}else{
			if (!personid.equalsIgnoreCase(APPLICANTBY)) {
				throw new MroException("mprline", "SELECTAPPLICANTBY");
			} else {
				String status = this.page.getAppBean().getJpo().getString("status");
				if (status.equalsIgnoreCase("关闭")) {
					throw new AppException("mprline", "noselect");
				}
			}
		}

	}

}
