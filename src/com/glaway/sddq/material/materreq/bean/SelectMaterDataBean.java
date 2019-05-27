package com.glaway.sddq.material.materreq.bean;

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
 * <配件申请选择备件清单>
 * 
 * @author public2795
 * @version [版本号, 2018-8-13]
 * @since [产品/模块版本]
 */
public class SelectMaterDataBean extends DataBean {
	/**
	 * 过滤弹出框数据
	 * 
	 * @throws MroException
	 */
	public void initialize() throws MroException {
		String MODEL = this.page.getAppBean().getJpo().getString("MODEL");
		String PROJECT = this.page.getAppBean().getJpo().getString("PROJECT");
		String where = "";
		IJpoSet mrlineset = this.getAppBean().getJpo().getJpoSet("mrline");
		String itemnum = null;
		if (!MODEL.isEmpty()) {
			String assetcsnum = this.page.getAppBean().getJpo()
					.getJpoSet("model").getJpo().getString("assetcsnum");

			for (int j = 0; j < mrlineset.count(); j++) {
				IJpo mprline = mrlineset.getJpo(j);

				if (itemnum == null) {
					itemnum = "'" + mprline.getString("ITEMNUM") + "'";
				} else {
					itemnum = itemnum + ",'" + mprline.getString("ITEMNUM")
							+ "'";
				}
			}

			if (itemnum != null) {
				where = " itemnum not in(" + itemnum + ")";
			}
			if (!StringUtil.isStrEmpty(where)) {
				where = where
						+ " and ( itemnum in (select distinct itemnum from ASSETMODELPART where APPLY='1' and assetnum in (select assetcsnum from assetcs where ancestor ='"
						+ assetcsnum
						+ "' )) "
						+ "or itemnum in (select distinct itemnum from assetcs where ancestor ='"
						+ assetcsnum + "'))";
			} else {
				where = "itemnum in (select distinct itemnum from ASSETMODELPART where APPLY='1' and assetnum in (select assetcsnum from assetcs where ancestor ='"
						+ assetcsnum
						+ "' )) "
						+ "or itemnum in (select distinct itemnum from assetcs where ancestor ='"
						+ assetcsnum + "')";
			}

			if (!StringUtil.isStrEmpty(where)) {
				this.getJpoSet().setUserWhere(where);
			}
		} else {
			where = "itemnum in (select distinct itemnum from ASSETMODELPART where assetnum in (select assetcsnum from assetcs start with assetcsnum in (select assetcsnum from assetcs where cmodel in (select CMODEL from asset where PROJECTNUM='"
					+ PROJECT + "')) connect by prior assetcsnum=parent))";
			for (int j = 0; j < mrlineset.count(); j++) {
				IJpo mprline = mrlineset.getJpo(j);

				if (itemnum == null) {
					itemnum = "'" + mprline.getString("ITEMNUM") + "'";
				} else {
					itemnum = itemnum + ",'" + mprline.getString("ITEMNUM")
							+ "'";
				}
			}

			if (itemnum != null) {
				where = " itemnum not in(" + itemnum + ")";
			}
			if (!StringUtil.isStrEmpty(where)) {
				where = where
						+ " and  itemnum in (select distinct itemnum from ASSETMODELPART where assetnum in (select assetcsnum from assetcs start with assetcsnum in (select assetcsnum from assetcs where cmodel in (select CMODEL from asset where PROJECTNUM='"
						+ PROJECT + "')) connect by prior assetcsnum=parent))";
			} else {
				where = "itemnum in (select distinct itemnum from ASSETMODELPART where assetnum in (select assetcsnum from assetcs start with assetcsnum in (select assetcsnum from assetcs where cmodel in (select CMODEL from asset where PROJECTNUM='"
						+ PROJECT + "')) connect by prior assetcsnum=parent))";
			}

			if (!StringUtil.isStrEmpty(where)) {
				this.getJpoSet().setUserWhere(where);
			}
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
		DataBean mrlinedataBean = this.page.getAppBean().getDataBean(
				"15353750126952");
		IJpoSet mrlineSet = mrlinedataBean.getJpoSet();
		String mrnum = this.page.getAppBean().getJpo().getString("MRNUM");
		String MODEL = this.page.getAppBean().getJpo().getString("MODEL");
		String PROJECT = this.page.getAppBean().getJpo().getString("PROJECT");
		double PERCENTAGE = this.page.getAppBean().getJpo()
				.getDouble("PERCENTAGE");
		double SUMCAR = this.page.getAppBean().getJpo().getDouble("SUMCAR");
		// 获取当前选择的库存信息
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IJpo ASSETMODELPART = list.get(i);
				String itemnum = ASSETMODELPART.getString("itemnum");
				double sumqty = 0;
				IJpoSet OLDASSETMODELPARTSET = MroServer.getMroServer()
						.getJpoSet("ASSETMODELPART",
								MroServer.getMroServer().getSystemUserServer());
				OLDASSETMODELPARTSET
						.setUserWhere("itemnum='"
								+ itemnum
								+ "' and assetnum in (select assetcsnum from assetcs start with assetcsnum in (select assetcsnum from assetcs where cmodel in (select CMODEL from asset where PROJECTNUM='"
								+ PROJECT
								+ "')) connect by prior assetcsnum=parent)");
				if (!OLDASSETMODELPARTSET.isEmpty()) {
					sumqty = OLDASSETMODELPARTSET.sum("qty");
				}
				double QTY = sumqty;
				double LINEQTY = PERCENTAGE * SUMCAR * QTY * 0.01;

				IJpo mrline = mrlineSet.addJpo();
				mrline.setValue("ITEMNUM", ASSETMODELPART.getString("ITEMNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrline.setValue("mrnum", mrnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrline.setValue("MODEL", MODEL,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrline.setValue("PROJECT", PROJECT,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				mrline.setValue("LINEQTY", Math.round(LINEQTY),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

			}
		}
		mrlinedataBean.reloadPage();
		return super.dialogok();
	}
}
