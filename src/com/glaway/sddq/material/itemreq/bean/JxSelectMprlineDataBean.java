package com.glaway.sddq.material.itemreq.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <检修领料单选择物料DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class JxSelectMprlineDataBean extends DataBean {
	/**
	 * 过滤物料数据
	 * 
	 * @throws MroException
	 */
	public void initialize() throws MroException {
		String tasknum = this.page.getAppBean().getJpo().getString("tasknum");
		String where = "";
		IJpoSet mprlineset = this.getAppBean().getJpo().getJpoSet("mprline");
		String itemnum = null;

		for (int j = 0; j < mprlineset.count(); j++) {
			IJpo mprline = mprlineset.getJpo(j);

			if (itemnum == null) {
				itemnum = "'" + mprline.getString("ITEMNUM") + "'";
			} else {
				itemnum = itemnum + ",'" + mprline.getString("ITEMNUM") + "'";
			}
		}

		if (itemnum != null) {
			where = " itemnum not in(" + itemnum + ")";
		}
		if (!StringUtil.isStrEmpty(tasknum)) {
			if (!StringUtil.isStrEmpty(where)) {
				where = where
						+ " and mprnum in (select mprnum from mpr where type='领料' and tasknum='"
						+ tasknum + "')";
			} else {
				where = " mprnum in (select mprnum from mpr where type='领料' and tasknum='"
						+ tasknum + "')";
			}

		}
		if (!StringUtil.isStrEmpty(where)) {
			this.getJpoSet().setUserWhere(where);
		}
		super.initialize();
	}

	/**
	 * 确认按钮赋值选择的数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 */
	public int dialogok() throws IOException, MroException {
		// TODO Auto-generated method stub
		DataBean mprlinedataBean = this.page.getAppBean().getDataBean(
				"1531744611839");
		IJpoSet mprlineSet = mprlinedataBean.getJpoSet();
		String mprmnum = this.page.getAppBean().getJpo().getString("MPRNUM");
		String tasknum = this.page.getAppBean().getJpo().getString("MPRNUM");
		// 获取当前选择的库存信息
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IJpo inventoryjpo = list.get(i);
				IJpo mprline = mprlineSet.addJpo();
				mprline.setValue("ITEMNUM", inventoryjpo.getString("ITEMNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("SITEID", this.page.getAppBean().getJpo()
						.getString("SITEID"));
				mprline.setValue("ORGID", this.page.getAppBean().getJpo()
						.getString("ORGID"));
				mprline.setValue("MPRNUM", mprmnum);
				mprline.setValue("tasknum", tasknum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}
		mprlinedataBean.reloadPage();
		return super.dialogok();
	}
}
