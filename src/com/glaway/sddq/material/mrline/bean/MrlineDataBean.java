package com.glaway.sddq.material.mrline.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <配件申请行DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class MrlineDataBean extends DataBean {
	/**
	 * 新建方法，判断是否能新建行以及新建数据初始化赋值
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int addrow() throws MroException, IOException {
		// TODO Auto-generated method stub

		String MRTYPE = this.page.getAppBean().getJpo().getString("MRTYPE");
		String status = this.page.getAppBean().getJpo().getString("status");
		String MODEL = this.page.getAppBean().getJpo().getString("model");
		String PROJECT = this.page.getAppBean().getJpo().getString("PROJECT");
		if (!status.equalsIgnoreCase("未处理")) {
			if (!status.equalsIgnoreCase("申请人修改")) {
				throw new MroException("materreq", "noaddrow");
			}
		}
		if (MODEL.isEmpty()) {
			throw new MroException("请先到‘配件申请’标签页选择车型和项目，并补全相应数据在进行新建行操作");
		} else if (PROJECT.isEmpty()) {
			throw new MroException("请先到‘配件申请’标签页选择车型和项目，并补全相应数据在进行新建行操作");
		}
		if (!this.getJpoSet().isEmpty()) {
			String datetype = this.getJpo(0).getString("datetype");
			if (datetype.equalsIgnoreCase("导入")) {
				throw new MroException("已通过导入数据进行添加数据，如需新建行，请删除导入的数据");
			}
		}
		int i = super.addrow();
		if (MRTYPE.equalsIgnoreCase("零星")) {
			this.getJpo().setFieldFlag("endqty", GWConstant.S_READONLY, true);
			this.getJpo().setFieldFlag("MODEL", GWConstant.S_READONLY, true);
			this.getJpo().setFieldFlag("PROJECT", GWConstant.S_REQUIRED, true);
			this.getJpo().setValue("datetype", "新建",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
		if (MRTYPE.equalsIgnoreCase("项目")) {
			this.getJpo().setFieldFlag("endqty", GWConstant.S_READONLY, true);
			this.getJpo().setFieldFlag("MODEL", GWConstant.S_REQUIRED, true);
			this.getJpo().setFieldFlag("PROJECT", GWConstant.S_READONLY, true);
			this.getJpo().setValue("datetype", "新建",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
		return i;
	}

	/**
	 * 编辑数据方法控制数据只读必填
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int editrow() throws MroException, IOException {
		// TODO Auto-generated method stub

		String MRTYPE = this.page.getAppBean().getJpo().getString("MRTYPE");
		String status = this.page.getAppBean().getJpo().getString("status");
		if (MRTYPE.equalsIgnoreCase("零星")) {
			this.getJpo().setFieldFlag("endqty", GWConstant.S_READONLY, true);
			this.getJpo().setFieldFlag("MODEL", GWConstant.S_READONLY, true);
			this.getJpo().setFieldFlag("PROJECT", GWConstant.S_REQUIRED, true);
		}
		if (MRTYPE.equalsIgnoreCase("项目")) {
			if (status.equalsIgnoreCase("未处理")
					|| status.equalsIgnoreCase(ItemUtil.STATUS_JHJLXG)) {
				this.getJpo().setFieldFlag("endqty", GWConstant.S_READONLY,
						true);
				this.getJpo()
						.setFieldFlag("MODEL", GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("PROJECT", GWConstant.S_READONLY,
						true);
			}
			if (status.equalsIgnoreCase(ItemUtil.STATUS_JHZGZZPS)) {
				this.getJpo().setFieldFlag("ITEMNUM", GWConstant.S_READONLY,
						true);
				this.getJpo().setFieldFlag("LINEQTY", GWConstant.S_READONLY,
						true);
				this.getJpo().setFieldFlag("PROJECT", GWConstant.S_READONLY,
						true);
				this.getJpo()
						.setFieldFlag("MODEL", GWConstant.S_READONLY, true);
				this.getJpo().setFieldFlag("endqty", GWConstant.S_REQUIRED,
						true);
			}

		}
		return super.editrow();

	}

	/**
	 * 
	 * <选择配件清单方法，判断是否可以选择配件清单>
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void selectmater() throws MroException, IOException {
		String MRTYPE = this.page.getAppBean().getJpo().getString("MRTYPE");
		String status = this.page.getAppBean().getJpo().getString("status");
		if (MRTYPE.equalsIgnoreCase("零星")) {
			throw new MroException("materreq", "noselect");
		}
		if (MRTYPE.equalsIgnoreCase("项目")) {
			if (!status.equalsIgnoreCase("未处理")) {
				throw new MroException("materreq", "nostatusselect");
			}
		}

	}

	/**
	 * 
	 * <上传数据方法判断是否可以上传数据>
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public int uploadattachments() throws MroException, IOException {
		String MODEL = this.page.getAppBean().getJpo().getString("model");
		String PROJECT = this.page.getAppBean().getJpo().getString("PROJECT");
		String RECEIVESTOREROOM = this.page.getAppBean().getJpo()
				.getString("RECEIVESTOREROOM");
		String DELIVERYDATE = this.page.getAppBean().getJpo()
				.getString("DELIVERYDATE");
		if (MODEL.isEmpty() || PROJECT.isEmpty() || RECEIVESTOREROOM.isEmpty()
				|| DELIVERYDATE.isEmpty()) {
			throw new MroException("请先到‘配件申请’标签页选择车型和项目，并补全相应数据在进行导入操作");
		}
		if (!this.getJpoSet().isEmpty()) {
			String datetype = this.getJpo(0).getString("datetype");
			if (datetype.equalsIgnoreCase("新建")) {
				throw new MroException("已通过新建行进行添加数据，如需导入，请删除新建的数据");
			}
		}
		loadDialog("impmrline");

		return GWConstant.ACCESS_SAMEMETHOD;
	}

	/**
	 * 删除方法，判断是否可以删除
	 * 
	 * @throws MroException
	 */
	@Override
	public synchronized void delete() throws MroException {
		// TODO Auto-generated method stub
		String mrlineid = this.getJpo().getString("mrlineid");
		String status = this.page.getAppBean().getJpo().getString("status");
		IJpoSet transferlineset = MroServer.getMroServer().getJpoSet(
				"transferline", MroServer.getMroServer().getSystemUserServer());
		transferlineset.setUserWhere("mrlineid='" + mrlineid + "'");
		if (!transferlineset.isEmpty()) {
			throw new MroException("该物料已经创建装箱单，不可删除");
		} else {
			if (!status.equalsIgnoreCase("未处理")) {
				throw new MroException("该状态下不可删除");
			}
		}
		super.delete();
	}
}
