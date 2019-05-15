package com.glaway.sddq.service.transplan.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;

/**
 * 分发数量校验送 <功能描述>
 * 
 * @author 20167088
 * @version [版本号, 2018-7-24]
 * @since [产品/模块版本]
 */
public class TransplanLocDataBean extends DataBean {
	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {

		super.addEditRowCallBackOk();

		IJpo materialPlan = getJpo().getParent();
		double materialcount = materialPlan.getDouble("materialcount");
		IJpoSet data = getJpoSet();
		double sum = data.sum("qty");
		if (sum > materialcount) {
			getJpo().setValueNull("qty");
			throw new MroException("transplan", "outofmaterialcount");
		}
		double distqty = getJpo().getDouble("qty");
		// 已分发数量
		double distedQty = materialPlan.getDouble("DISTEDQTY");
		materialPlan.setValue("DISTEDQTY", distqty + distedQty);
		this.getParent().reloadPage();
	}

	@Override
	public int addrow() throws MroException, IOException {
		IJpo materPlan = getParent().getJpo();
		// double totalCount = materPlan.getDouble("MATERIALCOUNT");
		if (materPlan != null) {

			// 未分发数量
			double undistqty = materPlan.getDouble("UNDISTQTY");
			if (undistqty < 1) {
				throw new MroException("transplan", "outofundistqty");
			}
		}
		return super.addrow();
	}

	@Override
	public synchronized void delete() throws MroException {
		// 当前分发数量
		double qty = getJpo().getDouble("QTY");
		// 物料计划
		IJpo materPlan = getJpo().getParent();
		// 已分发数量
		double distedqty = materPlan.getDouble("DISTEDQTY");
		// 将该条目分批数量还原
		materPlan.setValue("DISTEDQTY", distedqty - qty);
		try {
			// 刷新父级页面
			this.getParent().reloadPage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.delete();
	}

	@Override
	public synchronized void undelete() throws MroException {
		// 当前分发数量
		double qty = getJpo().getDouble("QTY");
		// 物料计划
		IJpo materPlan = getJpo().getParent();
		// 已分发数量
		double distedqty = materPlan.getDouble("DISTEDQTY");
		// 将该条目分批数量还原
		materPlan.setValue("DISTEDQTY", distedqty + qty);
		try {
			// 刷新父级页面
			this.getParent().reloadPage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.undelete();
	}

	public int impItemDist() throws MroException, IOException {

		IJpo materPlan = getParent().getJpo();

		if (materPlan != null) {
			// 未分发数量
			double undistqty = materPlan.getDouble("UNDISTQTY");
			if (undistqty < 1) {
				throw new MroException("transplan", "outofundistqty");
			}

			loadDialog("impItemDistDialog");
		}

		return GWConstant.ACCESS_SAMEMETHOD;
	}

}
