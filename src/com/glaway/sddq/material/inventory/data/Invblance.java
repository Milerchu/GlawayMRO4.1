package com.glaway.sddq.material.inventory.data;

import java.text.SimpleDateFormat;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 库存批次表Jpo
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-9]
 * @since [GlawayMro4.0/库存]
 */
public class Invblance extends StatusJpo implements IStatusJpo, FixedLoggers {
	private static final long serialVersionUID = 1L;

	/**
	 * 初始化判断不是新增设置字段自读
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		if (!this.toBeAdded()) {
			this.setFieldFlag("PHYSCNTQTY", GWConstant.S_READONLY, true);
			this.setFieldFlag("LOTNUM", GWConstant.S_READONLY, true);
			this.setFieldFlag("RECEIVEDATE", GWConstant.S_READONLY, true);
		}
	}

	/**
	 * 新建批次赋值父级关联字段以及数量
	 * 
	 * @throws MroException
	 */
	@Override
	public void add() throws MroException {
		super.add();
		if (getParent() != null
				&& StringUtil.isEqual(getParent().getName(), "SYS_INVENTORY")) {
			setValue("ITEMNUM", getParent().getString("ITEMNUM"));
			setValue("STOREROOM", getParent().getString("LOCATION"));
			double CURBAL = this.getParent().getDouble("CURBAL");
			IJpoSet thisjposet = this.getParent().getJpoSet("invblance");
			double sumPHYSCNTQTY = thisjposet.sum("PHYSCNTQTY");
			double newPHYSCNTQTY = CURBAL - sumPHYSCNTQTY;
			setValue("PHYSCNTQTY", newPHYSCNTQTY,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

		}
		// 调用生成批次号方法
		CreateLotnum();
	}

	/**
	 * 
	 * 生成批次号
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private void CreateLotnum() throws MroException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lotnum = sdf.format(getUserServer().getDate());
		int count = getJpoSet().count();
		int lot = 1;
		for (int i = 0; i < count; i++) {
			IJpo jpo = getJpoSet().getJpo(i);
			if (jpo.getString("LOTNUM").indexOf(lotnum) >= 0) {
				lot += 1;
			}
		}
		setValue("LOTNUM", lotnum + "-" + lot);
	}
}
