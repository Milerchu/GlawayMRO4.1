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
 * <改造领料单选择批次号弹出框DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class GzSelectLotnumDataBean extends DataBean {
	/**
	 * 过滤批次信息
	 * 
	 * @throws MroException
	 */
	public void initialize() throws MroException {
		String MPRSTOREROOM = this.page.getAppBean().getJpo()
				.getString("MPRSTOREROOM");
		DataBean mustchangemprBean = this.page.getAppBean().getDataBean(
				"1533537632186");
		IJpoSet mustchangemprSet = mustchangemprBean.getJpoSet();
		String itemnum = mustchangemprSet.getParent().getString("itemnum");
		String where = "";
		String lotnum = null;

		for (int j = 0; j < mustchangemprSet.count(); j++) {
			IJpo mustchangempr = mustchangemprSet.getJpo(j);

			if (lotnum == null) {
				lotnum = "'" + mustchangempr.getString("lotnum") + "'";
			} else {
				lotnum = lotnum + ",'" + mustchangempr.getString("lotnum")
						+ "'";
			}
		}

		if (lotnum != null) {
			where = " lotnum not in(" + lotnum + ")";
		}
		if (!StringUtil.isStrEmpty(MPRSTOREROOM)) {
			if (!StringUtil.isStrEmpty(where)) {
				where = where + " and  storeroom='" + MPRSTOREROOM
						+ "' and itemnum='" + itemnum + "'";
			} else {
				where = " storeroom='" + MPRSTOREROOM + "' and itemnum='"
						+ itemnum + "'";
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
		DataBean mustchangemprBean = this.page.getAppBean().getDataBean(
				"1533537632186");
		IJpoSet mustchangemprSet = mustchangemprBean.getJpoSet();
		String mprmnum = this.page.getAppBean().getJpo().getString("MPRNUM");
		String mprlineid = mustchangemprSet.getParent().getString("mprlineid");
		// 获取当前选择的库存信息
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IJpo lotnumjpo = list.get(i);
				IJpo mustchangempr = mustchangemprSet.addJpo();
				mustchangempr.setValue("ITEMNUM",
						lotnumjpo.getString("ITEMNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mustchangempr.setValue("lotnum", lotnumjpo.getString("lotnum"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mustchangempr.setValue("SITEID", this.page.getAppBean()
						.getJpo().getString("SITEID"));
				mustchangempr.setValue("ORGID", this.page.getAppBean().getJpo()
						.getString("ORGID"));
				mustchangempr.setValue("MPRNUM", mprmnum);
				mustchangempr.setValue("mprlineid", mprlineid);
			}
		}
		mustchangemprBean.reloadPage();
		return super.dialogok();
	}
}
