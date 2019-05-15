package com.glaway.sddq.material.locbin.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <仓位表JPo>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class Locbin extends Jpo {
	/**
	 * 初始化控制字段只读
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		if (!this.toBeAdded()) {
			if (this.getString("shelvestype").equalsIgnoreCase("高位货架")) {
				this.setFieldFlag("layer", GWConstant.S_READONLY, true);
			}
			if (this.getJpoSet("children").count() != 0) {
				this.setFieldFlag("shelvesnum", GWConstant.S_READONLY, true);
				this.setFieldFlag("shelvestype", GWConstant.S_READONLY, true);
				this.setFieldFlag("binrownum", GWConstant.S_READONLY, true);
				this.setFieldFlag("bincolnum", GWConstant.S_READONLY, true);
				this.setFieldFlag("layer", GWConstant.S_READONLY, true);
				this.setFieldFlag("description", GWConstant.S_READONLY, true);
				this.setFieldFlag("class", GWConstant.S_READONLY, true);
			}
		}
	}

	@Override
	public void delete() throws MroException {
		// 能删除
		canDelete();
		try {
			super.delete();
		} catch (MroException e) {
			// 不删除
			undelete();
			throw e;
		}
	}

	/**
	 * 判断能否删除仓位 <功能描述> [参数说明]
	 * 
	 * @throws MroException
	 */
	private void canDelete() throws MroException {
		IJpoSet childrenSet = this.getJpoSet("CHILDREN");
		IJpoSet locbinitem = this.getJpoSet("locbinitem");
		if (!childrenSet.isEmpty()) {
			String[] p = { this.getString("binnum") };
			throw new MroException("locbin", "nodelete", p);
		}
		if (!locbinitem.isEmpty()) {
			String[] p = { this.getString("binnum") };
			throw new MroException("locbin", "locbinitem", p);
		}

	}
}
