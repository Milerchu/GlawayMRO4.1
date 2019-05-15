package com.glaway.sddq.material.mustchangempr.bean;

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
 * <检修领料单选择退回序列号弹出框Databean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class JxSelectMustSqnDataBean extends DataBean {
	/**
	 * 过滤退回序列号数据
	 * 
	 * @throws MroException
	 */
	public void initialize() throws MroException {
		String tasknum = this.page.getAppBean().getJpo().getString("tasknum");
		DataBean mustchangemprBean = this.page.getAppBean().getDataBean(
				"1533629819367");
		IJpoSet mustchangemprSet = mustchangemprBean.getJpoSet();
		String itemnum = mustchangemprSet.getParent().getString("itemnum");
		String where = "";
		String assetnum = null;

		for (int j = 0; j < mustchangemprSet.count(); j++) {
			IJpo mustchangempr = mustchangemprSet.getJpo(j);

			if (assetnum == null) {
				assetnum = "'" + mustchangempr.getString("assetnum") + "'";
			} else {
				assetnum = assetnum + ",'"
						+ mustchangempr.getString("assetnum") + "'";
			}
		}

		if (assetnum != null) {
			where = " assetnum not in(" + assetnum + ")";
		}
		if (!StringUtil.isStrEmpty(tasknum)) {
			if (!StringUtil.isStrEmpty(where)) {
				where = where
						+ " and  tasknum='"
						+ tasknum
						+ "' and itemnum='"
						+ itemnum
						+ "' and mprnum in (select mprnum from mpr where type='领料') and assetnum is not null";
			} else {
				where = " tasknum='"
						+ tasknum
						+ "' and itemnum='"
						+ itemnum
						+ "' and mprnum in (select mprnum from mpr where type='领料') and assetnum is not null";
			}

		}
		if (!StringUtil.isStrEmpty(where)) {
			this.getJpoSet().setUserWhere(where);
		}
		super.initialize();
	}

	/**
	 * 确认赋值选择的数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 */
	public int dialogok() throws IOException, MroException {
		// TODO Auto-generated method stub
		String MPRSTOREROOM = this.page.getAppBean().getJpo()
				.getString("MPRSTOREROOM");
		DataBean mustchangemprBean = this.page.getAppBean().getDataBean(
				"1533629819367");
		IJpoSet mustchangemprSet = mustchangemprBean.getJpoSet();
		String mprmnum = this.page.getAppBean().getJpo().getString("MPRNUM");
		String mprlineid = mustchangemprSet.getParent().getString("mprlineid");
		boolean plan = this.page.getAppBean().getJpo().getBoolean("plan");
		String type = this.page.getAppBean().getJpo().getString("type");
		String OBLIGATELINENUM = "";
		String OBLIGATENUM = "";
		String MOVEREASON = "";
		if (!plan) {
			OBLIGATELINENUM = mustchangemprSet.getParent().getString(
					"OBLIGATELINENUM");
			OBLIGATENUM = mustchangemprSet.getParent().getString("OBLIGATENUM");
		} else {
			if (type.equalsIgnoreCase("领料")) {
				MOVEREASON = mustchangemprSet.getParent().getString(
						"MOVEREASON");
			}
		}
		// 获取当前选择的库存信息
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IJpo assetjpo = list.get(i);
				IJpo mustchangempr = mustchangemprSet.addJpo();
				mustchangempr.setValue("ITEMNUM",
						assetjpo.getString("ITEMNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mustchangempr.setValue("SITEID", this.page.getAppBean()
						.getJpo().getString("SITEID"));
				mustchangempr.setValue("ORGID", this.page.getAppBean().getJpo()
						.getString("ORGID"));
				mustchangempr.setValue("tasknum", this.page.getAppBean()
						.getJpo().getString("tasknum"));
				mustchangempr.setValue("MPRNUM", mprmnum);
				mustchangempr.setValue("mprlineid", mprlineid);
				mustchangempr.setValue("assetnum",
						assetjpo.getString("assetnum"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mustchangempr.setValue("sqn", assetjpo.getString("sqn"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mustchangempr.setValue("amount", 1,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mustchangempr.setValue("status", "2",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mustchangempr.setValue("STOCKADDRESS", MPRSTOREROOM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if (!plan) {
					mustchangempr.setValue("OBLIGATELINENUM", OBLIGATELINENUM);
					mustchangempr.setValue("OBLIGATENUM", OBLIGATENUM);
				} else {
					if (type.equalsIgnoreCase("领料")) {
						mustchangempr.setValue("MOVEREASON", MOVEREASON);
					}
				}
			}
		}
		mustchangemprBean.reloadPage();
		return super.dialogok();
	}
}
