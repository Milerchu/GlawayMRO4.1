package com.glaway.sddq.material.mprline.bean;

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
 * <退库单选择入库单明细类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-3-7]
 * @since  [产品/模块版本]
 */
public class SelectRklineDataBean extends DataBean {

	/**
	 * 过滤物料数据
	 * 
	 * @throws MroException
	 */
	public void initialize() throws MroException {
		String MPRSTOREROOM = this.page.getAppBean().getJpo()
				.getString("MPRSTOREROOM");
		String where = "";
		IJpoSet mprlineset = this.getAppBean().getJpo().getJpoSet("mprline");
		String rkmprlineid = null;

		for (int j = 0; j < mprlineset.count(); j++) {
			IJpo mprline = mprlineset.getJpo(j);

			if (rkmprlineid == null) {
				rkmprlineid = "'" + mprline.getString("rkmprlineid") + "'";
			} else {
				rkmprlineid = rkmprlineid + ",'" + mprline.getString("rkmprlineid") + "'";
			}
		}

		if (rkmprlineid != null) {
			where = " mprlineid not in(" + rkmprlineid + ")";
		}
		if (!StringUtil.isStrEmpty(MPRSTOREROOM)) {
			if (!StringUtil.isStrEmpty(where)) {
				where = where
						+ " and  ISSUESTOREROOM='"+MPRSTOREROOM+"' and status='已接收' and tkmprlineid is null and mprlineid not in(select rkmprlineid from mprline where status='未退库')";
			} else {
				where="ISSUESTOREROOM='"+MPRSTOREROOM+"' and status='已接收' and tkmprlineid is null and mprlineid not in(select rkmprlineid from mprline where status='未退库')";
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
				"15087392617582");
		IJpoSet mprlineSet = mprlinedataBean.getJpoSet();
		String mprmnum = this.page.getAppBean().getJpo().getString("MPRNUM");
		// 获取当前选择的库存信息
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IJpo rkline = list.get(i);
				IJpo mprline = mprlineSet.addJpo();
				mprline.setValue("rkmprlineid", rkline.getString("mprlineid"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("ISSUESTOREROOM", rkline.getString("ISSUESTOREROOM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("SITEID", this.page.getAppBean().getJpo()
						.getString("SITEID"));
				mprline.setValue("ORGID", this.page.getAppBean().getJpo()
						.getString("ORGID"));
				mprline.setValue("MPRNUM", mprmnum);
				mprline.setValue("qty", rkline.getDouble("qty"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("status", "未退库",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("PARENTLOCATION", rkline.getString("PARENTLOCATION"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("PARENTITEMNUM", rkline.getString("PARENTITEMNUM"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("PARENTSQN", rkline.getString("PARENTSQN"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("PARENTSQNASSETNUM", rkline.getString("PARENTSQNASSETNUM"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("PARENTASSETNUM", rkline.getString("PARENTASSETNUM"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}
		mprlinedataBean.reloadPage();
		return super.dialogok();
	}
}
