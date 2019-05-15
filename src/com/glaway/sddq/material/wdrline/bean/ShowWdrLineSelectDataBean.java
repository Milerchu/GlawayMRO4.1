package com.glaway.sddq.material.wdrline.bean;

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
 * 
 * <处置管理-选择处置物料弹出框类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-3-19]
 * @since  [产品/模块版本]
 */
public class ShowWdrLineSelectDataBean extends DataBean {
	/**
	 * 过滤处置物料数据
	 * 
	 * @throws MroException
	 */
	public void initialize() throws MroException {
		String wdrnum = this.page.getAppBean().getJpo()
				.getString("wdrnum");
		String where = "";
		IJpoSet wdrlineset = this.getAppBean().getJpo().getJpoSet("wdrline");
		String wdrlineselectid= null;

		for (int j = 0; j < wdrlineset.count(); j++) {
			IJpo wdrline = wdrlineset.getJpo(j);

			if (wdrlineselectid == null) {
				wdrlineselectid = "'" + wdrline.getString("wdrlineselectid") + "'";
			} else {
				wdrlineselectid = wdrlineselectid + ",'" + wdrline.getString("wdrlineselectid") + "'";
			}
		}

		if (wdrlineselectid != null) {
			where = " wdrlineselectid not in(" + wdrlineselectid + ")";
		}
		if (!StringUtil.isStrEmpty(where)) {
				where = where
						+ " and  wdrnum='"+wdrnum+"'";
			} else {
				where="wdrnum='"+wdrnum+"'";
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
		DataBean wdrlinedatabean = this.page.getAppBean().getDataBean(
				"1552959998922");
		IJpoSet wdrlineset = wdrlinedatabean.getJpoSet();
		String wdrnum = this.page.getAppBean().getJpo().getString("wdrnum");
		String location=this.page.getAppBean().getJpo().getString("location");
		String DEALTYPE=this.page.getAppBean().getJpo().getString("DEALTYPE");
		// 获取当前选择的库存信息
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IJpo wdrlineselect = list.get(i);
				IJpo wdrline = wdrlineset.addJpo();
				wdrline.setValue("wdrnum", wdrnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				wdrline.setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				wdrline.setValue("DEALTYPE", DEALTYPE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				wdrline.setValue("itemnum", wdrlineselect.getString("itemnum"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				wdrline.setValue("sqn", wdrlineselect.getString("sqn"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				wdrline.setValue("assetnum", wdrlineselect.getString("assetnum"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				wdrline.setValue("lotnum", wdrlineselect.getString("lotnum"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				wdrline.setValue("SCRAPQTY", wdrlineselect.getDouble("qty"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				wdrline.setValue("description", wdrlineselect.getString("description"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				wdrline.setValue("memo", wdrlineselect.getString("memo"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				
				wdrline.setValue("wdrlineselectid", wdrlineselect.getString("wdrlineselectid"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				wdrline.setValue("SITEID", this.page.getAppBean().getJpo().getString("SITEID"));
				wdrline.setValue("ORGID", this.page.getAppBean().getJpo().getString("ORGID"));
			}
		}
		this.page.getAppBean().SAVE();
		wdrlinedatabean.reloadPage();
		return super.dialogok();
	}
}
