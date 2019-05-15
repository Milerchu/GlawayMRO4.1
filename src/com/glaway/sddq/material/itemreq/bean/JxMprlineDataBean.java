package com.glaway.sddq.material.itemreq.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <检修领料单行DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class JxMprlineDataBean extends DataBean {
	/**
	 * 判断非制单人不能删除
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
		if (!personid.equalsIgnoreCase(APPLICANTBY)) {
			throw new MroException("mprline", "DELETEAPPLICANTBY");
		}
		return super.toggledeleterow();
	}

	/**
	 * 
	 * <判断计划外领料非制单人非编辑状态不能选择物料>
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
		String type = this.page.getAppBean().getJpo().getString("type");
		boolean plan = this.page.getAppBean().getJpo().getBoolean("plan");
		if (type.equalsIgnoreCase("退料")) {
			throw new MroException("mprline", "noselectitem");
		} else {
			if (plan) {
				if (!personid.equalsIgnoreCase(APPLICANTBY)) {
					throw new MroException("mprline", "SELECTAPPLICANTBY");
				} else {
					String status = this.page.getAppBean().getJpo()
							.getString("status");
					if (status.equalsIgnoreCase("确认领料")
							|| status.equalsIgnoreCase("确认接收")) {
						throw new AppException("mpr", "noselect");
					}
				}
			} else {
				throw new MroException("mprline", "selectitem");
			}
		}

	}

	/**
	 * 
	 * <判断非计划外领料非制单人非编辑状态不能选择必换件>
	 * 
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void selectbhjinfo() throws IOException, MroException {
		// TODO Auto-generated method stub
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String APPLICANTBY = this.page.getAppBean().getJpo()
				.getString("APPLICANTBY");
		boolean plan = this.page.getAppBean().getJpo().getBoolean("plan");
		if (plan) {
			if (!personid.equalsIgnoreCase(APPLICANTBY)) {
				throw new MroException("mprline", "SELECTAPPLICANTBY");
			} else {
				throw new MroException("mprline", "selectwo");
			}

		} else {
			if (!personid.equalsIgnoreCase(APPLICANTBY)) {
				throw new MroException("mprline", "SELECTAPPLICANTBY");
			} else {
				String status = this.page.getAppBean().getJpo()
						.getString("status");
				if (status.equalsIgnoreCase("确认领料")
						|| status.equalsIgnoreCase("确认接收")) {
					throw new AppException("mpr", "noselect");
				}
			}
		}
	}

	/**
	 * 
	 * <判断非退料类型不能选择退回物料>
	 * 
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void selectmprline() throws IOException, MroException {
		// TODO Auto-generated method stub
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String APPLICANTBY = this.page.getAppBean().getJpo()
				.getString("APPLICANTBY");
		String type = this.page.getAppBean().getJpo().getString("type");
		if (type.equalsIgnoreCase("退料")) {
			if (!personid.equalsIgnoreCase(APPLICANTBY)) {
				throw new MroException("mprline", "TLSELECTAPPLICANTBY");
			} else {
				String status = this.page.getAppBean().getJpo()
						.getString("status");
				if (status.equalsIgnoreCase("确认领料")
						|| status.equalsIgnoreCase("确认接收")) {
					throw new AppException("mpr", "noselect");
				}
			}
		} else {
			throw new MroException("mprline", "selectmprline");
		}
	}

	/**
	 * 编辑数据判断只读
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
		String APPLICANTBY = this.page.getAppBean().getJpo()
				.getString("APPLICANTBY");
		String type = this.page.getAppBean().getJpo().getString("type");
		boolean plan = this.page.getAppBean().getJpo().getBoolean("plan");
		if (!personid.equalsIgnoreCase(APPLICANTBY)) {
			this.getJpo().setFieldFlag("qty", GWConstant.S_REQUIRED, false);
			this.getJpo().setFieldFlag("qty", GWConstant.S_READONLY, true);
			this.getJpo().setFieldFlag("MOVEREASON", GWConstant.S_REQUIRED,
					false);
			this.getJpo().setFieldFlag("MOVEREASON", GWConstant.S_READONLY,
					true);
		} else {
			if (plan) {
				if (type.equalsIgnoreCase("领料")) {
					this.getJpo().setFieldFlag("MOVEREASON",
							GWConstant.S_READONLY, false);
					this.getJpo().setFieldFlag("MOVEREASON",
							GWConstant.S_REQUIRED, true);
				} else {
					this.getJpo().setFieldFlag("MOVEREASON",
							GWConstant.S_REQUIRED, false);
					this.getJpo().setFieldFlag("MOVEREASON",
							GWConstant.S_READONLY, true);
				}
			} else {
				this.getJpo().setFieldFlag("MOVEREASON", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setFieldFlag("MOVEREASON", GWConstant.S_READONLY,
						true);
			}
		}
		return GWConstant.ACCESS_SAMEMETHOD;
	}
}
