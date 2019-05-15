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
 * <改造领料单选择序列号弹出框DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class GzSelectSqnDataBean extends DataBean {
	/**
	 * 过滤序列号数据
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
		if (!StringUtil.isStrEmpty(MPRSTOREROOM)) {
			if (!StringUtil.isStrEmpty(where)) {
				where = where + " and  location='" + MPRSTOREROOM
						+ "' and itemnum='" + itemnum + "'";
			} else {
				where = " location='" + MPRSTOREROOM + "' and itemnum='"
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
				IJpo assetjpo = list.get(i);
				IJpo mustchangempr = mustchangemprSet.addJpo();
				mustchangempr.setValue("ITEMNUM",
						assetjpo.getString("ITEMNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mustchangempr.setValue("SITEID", this.page.getAppBean()
						.getJpo().getString("SITEID"));
				mustchangempr.setValue("ORGID", this.page.getAppBean().getJpo()
						.getString("ORGID"));
				mustchangempr.setValue("MPRNUM", mprmnum);
				mustchangempr.setValue("mprlineid", mprlineid);
				mustchangempr.setValue("assetnum",
						assetjpo.getString("assetnum"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mustchangempr.setValue("sqn", assetjpo.getString("sqn"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mustchangempr.setValue("amount", 1,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}
		mustchangemprBean.reloadPage();
		return super.dialogok();
	}
}
