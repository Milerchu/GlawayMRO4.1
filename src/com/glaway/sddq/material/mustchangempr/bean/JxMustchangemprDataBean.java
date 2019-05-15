package com.glaway.sddq.material.mustchangempr.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <检修领料单序列号批次号按钮控制DATABEAN>
 * 
 * @author public2795
 * @version [版本号, 2018-8-6]
 * @since [产品/模块版本]
 */
public class JxMustchangemprDataBean extends DataBean {
	/**
	 * 
	 * <选择批次号按钮方法>
	 * 
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void selectlotnum() throws IOException, MroException {
		String itemnum = this.getParent().getJpo().getString("itemnum");
		String itemtype = ItemUtil.getItemInfo(itemnum);
		String ENDBY = this.page.getAppBean().getJpo().getString("ENDBY");
		String type = this.page.getAppBean().getJpo().getString("type");
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		if (type.equalsIgnoreCase("退料")) {
			throw new AppException("mprline", "tlselectlotnum");
		} else {
			if (personid.equalsIgnoreCase(ENDBY)) {
				if (ItemUtil.LOT_II_ITEM.equals(itemtype)) {
					throw new AppException("mprline", "enoselectlotnum");
				} else {
					if (ItemUtil.SQN_ITEM.equals(itemtype)) {
						throw new AppException("mprline", "znoselectlotnum");
					}
				}
			} else {
				throw new AppException("mprline", "ENDBY");
			}
		}

	}

	/**
	 * 
	 * <选择序列号按钮方法>
	 * 
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void selectsqn() throws IOException, MroException {
		String itemnum = this.getParent().getJpo().getString("itemnum");
		String itemtype = ItemUtil.getItemInfo(itemnum);
		String ENDBY = this.page.getAppBean().getJpo().getString("ENDBY");
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String type = this.page.getAppBean().getJpo().getString("type");
		if (type.equalsIgnoreCase("退料")) {
			throw new AppException("mprline", "tlselectsqn");
		} else {
			if (personid.equalsIgnoreCase(ENDBY)) {
				if (ItemUtil.LOT_I_ITEM.equals(itemtype)) {
					throw new AppException("mprline", "ynoselectsqn");
				} else {
					if (ItemUtil.LOT_II_ITEM.equals(itemtype)) {
						throw new AppException("mprline", "enoselectsqn");
					}
				}
			} else {
				throw new AppException("mprline", "ENDBY");
			}
		}

	}

	/**
	 * 
	 * <选择退回序列号按钮方法>
	 * 
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void selectmustsqn() throws IOException, MroException {
		String itemnum = this.getParent().getJpo().getString("itemnum");
		String itemtype = ItemUtil.getItemInfo(itemnum);
		String ENDBY = this.page.getAppBean().getJpo().getString("ENDBY");
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String type = this.page.getAppBean().getJpo().getString("type");
		if (type.equalsIgnoreCase("退料")) {
			if (personid.equalsIgnoreCase(ENDBY)) {
				if (ItemUtil.LOT_I_ITEM.equals(itemtype)) {
					throw new AppException("mprline", "ynoselectsqn");
				} else {
					if (ItemUtil.LOT_II_ITEM.equals(itemtype)) {
						throw new AppException("mprline", "enoselectsqn");
					}
				}
			} else {
				throw new AppException("mprline", "ENDBY");
			}
		} else {
			throw new AppException("mprline", "noselectmustsqn");
		}

	}

	/**
	 * 
	 * <选择退回批次号按钮方法>
	 * 
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void selectmustlotnum() throws IOException, MroException {
		String itemnum = this.getParent().getJpo().getString("itemnum");
		String itemtype = ItemUtil.getItemInfo(itemnum);
		String ENDBY = this.page.getAppBean().getJpo().getString("ENDBY");
		String type = this.page.getAppBean().getJpo().getString("type");
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		if (type.equalsIgnoreCase("退料")) {
			if (personid.equalsIgnoreCase(ENDBY)) {
				if (ItemUtil.LOT_II_ITEM.equals(itemtype)) {
					throw new AppException("mprline", "enoselectlotnum");
				} else {
					if (ItemUtil.SQN_ITEM.equals(itemtype)) {
						throw new AppException("mprline", "znoselectlotnum");
					}
				}
			} else {
				throw new AppException("mprline", "ENDBY");
			}
		} else {
			throw new AppException("mprline", "noselectmustlotnum");
		}

	}

	/**
	 * 删除行方法
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	public int toggledeleterow() throws MroException, IOException {
		// TODO Auto-generated method stub
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String ENDBY = this.page.getAppBean().getJpo().getString("ENDBY");
		if (!personid.equalsIgnoreCase(ENDBY)) {
			throw new MroException("mprline", "DELETEENDBY");
		}
		return super.toggledeleterow();
	}

	/**
	 * 编辑行信息方法
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	public int editrow() throws MroException, IOException {
		// TODO Auto-generated method stub
		super.editrow();
		String personid = this.page.getAppBean().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String ENDBY = this.page.getAppBean().getJpo().getString("ENDBY");
		if (!personid.equalsIgnoreCase(ENDBY)) {
			this.getJpo().setFieldFlag("amount", GWConstant.S_REQUIRED, false);
			this.getJpo().setFieldFlag("amount", GWConstant.S_READONLY, true);
		}
		return GWConstant.ACCESS_SAMEMETHOD;
	}
}
