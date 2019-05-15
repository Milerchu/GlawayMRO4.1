package com.glaway.sddq.material.jxmpr.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 检修领料单中子表选择弹出框dataBean
 * 
 * @author zzx
 * @version [版本号, 2018-7-30]
 * @since [物资/检修领料单]
 */
public class JxMPrBhDataBean extends DataBean {

	/**
	 * 弹出框物料数据过滤
	 * 
	 * @throws MroException
	 */

	@Override
	public void initialize() throws MroException {
		// TODO Auto-generated method stub
		String PRODUCTIONORDERNUM = this.page.getAppBean().getJpo()
				.getString("PRODUCTIONORDERNUM");
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
		if (!StringUtil.isStrEmpty(PRODUCTIONORDERNUM)) {
			if (!StringUtil.isStrEmpty(where)) {
				where = where + " and  PRODUCTIONORDERNUM='"
						+ PRODUCTIONORDERNUM + "'";
			} else {
				where = " PRODUCTIONORDERNUM='" + PRODUCTIONORDERNUM + "'";
			}

		}
		if (!StringUtil.isStrEmpty(where)) {
			this.getJpoSet().setUserWhere(where);
		}
		super.initialize();
	}

	/**
	 * 赋值选择的数据
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
		String mprnum = this.page.getAppBean().getJpo().getString("MPRNUM");
		String MPRSTOREROOM = this.page.getAppBean().getJpo()
				.getString("MPRSTOREROOM");
		// 获取当前选择的库存信息
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IJpo mustcinfo = list.get(i);
				double curbal = 0;
				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet(
						"sys_inventory",
						MroServer.getMroServer().getSystemUserServer());
				inventoryset.setQueryWhere("itemnum='"
						+ mustcinfo.getString("itemnum") + "' and location='"
						+ MPRSTOREROOM + "'");
				if (!inventoryset.isEmpty()) {
					curbal = inventoryset.getJpo(0).getDouble("curbal");
				}
				IJpo mprline = mprlineSet.addJpo();
				mprline.setValue("MOBILETYPE",
						mustcinfo.getString("MOBILETYPE"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("COLLECTTIONDATE",
						mustcinfo.getString("COLLECTTIONDATE"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("FACTORY", mustcinfo.getString("FACTORY"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("DISTRIBUTOR",
						mustcinfo.getString("DISTRIBUTOR"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("PRODUCTIONORDERNUM",
						mustcinfo.getString("PRODUCTIONORDERNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("STOCKADDRESS",
						mustcinfo.getString("STOCKADDRESS"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("ITEMNUM", mustcinfo.getString("ITEMNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("qty", mustcinfo.getFloat("AMOUNT"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("MEASUREMENTUNIT",
						mustcinfo.getString("MEASUREMENTUNIT"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("OBLIGATENUM",
						mustcinfo.getString("OBLIGATENUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("OBLIGATELINENUM",
						mustcinfo.getString("OBLIGATELINENUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("RECORDTYPE",
						mustcinfo.getString("RECORDTYPE"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("curbal", curbal,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mprline.setValue("MPRNUM", mprnum);
			}
		}
		mprlinedataBean.reloadPage();
		return super.dialogok();
	}

}
