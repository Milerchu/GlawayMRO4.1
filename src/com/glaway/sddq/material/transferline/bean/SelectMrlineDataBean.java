package com.glaway.sddq.material.transferline.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <装箱单选择配件申请行功能类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-21]
 * @since [产品/模块版本]
 */
public class SelectMrlineDataBean extends DataBean {
	/**
	 * 过滤配件申请行信息
	 * 
	 * @throws MroException
	 */
	public void initialize() throws MroException {

		String mrnum = this.getAppBean().getJpo().getString("mrnum");
		String transfernum = this.getAppBean().getJpo()
				.getString("transfernum");
		String SAPPONUM = this.getAppBean().getJpo().getJpoSet("mr").getJpo()
				.getString("SAPPONUM");
		IJpoSet transferlineset = this.getAppBean().getJpo()
				.getJpoSet("transferline");
		String ISSUESTOREROOM = this.getAppBean().getJpo()
				.getString("ISSUESTOREROOM");
		String TRANSFERMOVETYPE = this.page.getAppBean().getJpo()
				.getString("TRANSFERMOVETYPE");
		String where = "";
		String Idstr = "";
		if (TRANSFERMOVETYPE.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {

			where = "mrlinetransferid in(select mrlinetransferid from mrlinetransfer where mrnum='"
					+ mrnum
					+ "' "
					+ "and exists (select location from locations where STOREROOMLEVEL='中心库' and locations.location=mrlinetransfer.storeroom) "
					+ "and exists(select VGBEL from CONVERTLOCLINE where status='已接收' and CONVERTLOCLINE.VGBEL=mrlinetransfer.SAPPONUM) "
					+ "and exists(select itemnum from CONVERTLOCLINE where status='已接收'and CONVERTLOCLINE.itemnum=mrlinetransfer.itemnum)"
					+ "and transferqty>zxqty or mrnum='"
					+ mrnum
					+ "' and exists (select location from locations where STOREROOMLEVEL='中心库' "
					+ "and locations.location=mrlinetransfer.storeroom) and exists(select itemnum from CONVERTLOCLINE where VGBEL='"
					+ SAPPONUM
					+ "' and status='已接收' "
					+ "and CONVERTLOCLINE.itemnum=mrlinetransfer.itemnum) and jhqty>zxqty or  mrnum='"
					+ mrnum
					+ "' and exists "
					+ "(select location from locations where STOREROOMLEVEL='中心库'  and locations.location=mrlinetransfer.storeroom) and transferqty>zxqty and SAPPONUM is null)";
		} else {
			where = "mrlinetransferid in(select mrlinetransferid from mrlinetransfer where mrnum='"
					+ mrnum
					+ "' "
					+ "and exists (select location from locations where STOREROOMLEVEL in('区域库','现场库','现场站点库') "
					+ "and locations.location=mrlinetransfer.storeroom) and exists(select VGBEL from CONVERTLOCLINE where status='已接收' "
					+ "and CONVERTLOCLINE.VGBEL=mrlinetransfer.SAPPONUM) and exists(select itemnum from CONVERTLOCLINE where status='已接收'"
					+ "and CONVERTLOCLINE.itemnum=mrlinetransfer.itemnum)and transferqty>zxqty or mrnum='"
					+ mrnum
					+ "' "
					+ "and exists (select location from locations where STOREROOMLEVEL in('区域库','现场库','现场站点库') "
					+ "and locations.location=mrlinetransfer.storeroom) and exists(select itemnum from CONVERTLOCLINE where VGBEL='"
					+ SAPPONUM
					+ "' "
					+ "and status='已接收' and CONVERTLOCLINE.itemnum=mrlinetransfer.itemnum)and jhqty>zxqty or  mrnum='"
					+ mrnum
					+ "' "
					+ "and exists (select location from locations where STOREROOMLEVEL in('区域库','现场库','现场站点库')  and locations.location=mrlinetransfer.storeroom) "
					+ "and transferqty>zxqty) and transfernum='"
					+ transfernum
					+ "' and storeroom='" + ISSUESTOREROOM + "'";
		}
		if (!StringUtil.isStrEmpty(where)) {
			this.getJpoSet().setUserWhere(where);
		}
		super.initialize();
	}

	/**
	 * 赋值选择的信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 */
	@Override
	public int dialogok() throws IOException, MroException {
		DataBean transferlineBean = this.page.getAppBean().getDataBean(
				"1527057849697");
		IJpoSet ransferlineSet = transferlineBean.getJpoSet();
		IJpo transfer = this.getAppBean().getJpo();
		String transfernum = transfer.getString("transfernum");
		String orgid = transfer.getString("orgid");
		String siteid = transfer.getString("siteid");
		String RECEIVESTOREROOM = transfer.getString("RECEIVESTOREROOM");
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (IJpo iJpo : list) {
				String itemnum = iJpo.getString("itemnum");
				String mrnum = iJpo.getString("mrnum");
				String mrlinetransferid = iJpo.getString("mrlinetransferid");
				String STOREROOM = iJpo.getString("STOREROOM");
				String PROJECT = iJpo.getString("PROJECT");
				String MODEL = iJpo.getString("MODEL");
				String transtype = iJpo.getString("transtype");
				double LINEQTY = 0.00;
				if (transtype.equalsIgnoreCase("下达计划")) {
					LINEQTY = iJpo.getDouble("jhqty");
				} else {
					LINEQTY = iJpo.getDouble("transferqty");
				}
				double zxqty = iJpo.getDouble("zxqty");
				IJpoSet itemset = MroServer.getMroServer().getJpoSet(
						"sys_item",
						MroServer.getMroServer().getSystemUserServer());
				itemset.setUserWhere("itemnum='" + itemnum + "'");
				String type = ItemUtil.getItemInfo(itemnum);

				if (ItemUtil.SQN_ITEM.equals(type)
						|| ItemUtil.LOT_I_ITEM.equals(type)) {
					for (int i = 0; i < LINEQTY - zxqty; i++) {
						IJpo transferline = ransferlineSet.addJpo();
						transferline.setValue("transfernum", transfernum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("itemnum", itemnum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

						transferline.setValue("mrnum", mrnum,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("mrlinetransid",
								mrlinetransferid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("PROJECTNUM", PROJECT,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("MODEL", MODEL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("ORDERQTY", 1,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("RECEIVESTOREROOM",
								RECEIVESTOREROOM,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("ISSUESTOREROOM", STOREROOM,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

						transferline.setValue("orgid", orgid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("siteid", siteid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						IJpoSet locaddressSet = this.getJpo().getJpoSet(
								"$LOCADDRESS",
								"locaddress",
								"LOCATION='" + STOREROOM
										+ "' and isaddress='是'");
						if (!locaddressSet.isEmpty()) {
							IJpo locaddress = locaddressSet.getJpo(0);
							transferline.setValue("ISSUEADDRESS",
									locaddress.getString("address"),
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}

						IJpoSet relocaddressSet = this.getJpo().getJpoSet(
								"$LOCADDRESS",
								"locaddress",
								"LOCATION='" + RECEIVESTOREROOM
										+ "' and isaddress='是'");
						if (!relocaddressSet.isEmpty()) {
							IJpo locaddress = relocaddressSet.getJpo(0);
							transferline.setValue("RECEIVEADDRESS",
									locaddress.getString("address"),
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					}
				} else {

					IJpo transferline = ransferlineSet.addJpo();
					transferline.setValue("transfernum", transfernum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					transferline.setValue("mrnum", mrnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("mrlinetransid", mrlinetransferid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("PROJECTNUM", PROJECT,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("MODEL", MODEL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("ORDERQTY", LINEQTY - zxqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("RECEIVESTOREROOM", RECEIVESTOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("ISSUESTOREROOM", STOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					transferline.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					IJpoSet locaddressSet = this.getJpo().getJpoSet(
							"$LOCADDRESS", "locaddress",
							"LOCATION='" + STOREROOM + "' and isaddress='是'");
					if (!locaddressSet.isEmpty()) {
						IJpo locaddress = locaddressSet.getJpo(0);
						transferline.setValue("ISSUEADDRESS",
								locaddress.getString("address"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}

					IJpoSet relocaddressSet = this.getJpo().getJpoSet(
							"$LOCADDRESS",
							"locaddress",
							"LOCATION='" + RECEIVESTOREROOM
									+ "' and isaddress='是'");
					if (!relocaddressSet.isEmpty()) {
						IJpo locaddress = relocaddressSet.getJpo(0);
						transferline.setValue("RECEIVEADDRESS",
								locaddress.getString("address"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}

			}

		}

		transferlineBean.reloadPage();
		this.page.getAppBean().SAVE();
		return super.dialogok();
	}
}
