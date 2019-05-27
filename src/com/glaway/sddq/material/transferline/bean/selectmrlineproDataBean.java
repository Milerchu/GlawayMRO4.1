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
 * <选择配件申请行Databean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class selectmrlineproDataBean extends DataBean {
	/**
	 * 过滤配件申请行信息
	 * 
	 * @throws MroException
	 */
	public void initialize() throws MroException {

		String mrnum = this.getAppBean().getJpo().getString("mrnum");
		IJpoSet transferlineset = this.getAppBean().getJpo()
				.getJpoSet("transferline");
		String where = "";
		String Idstr = "";
		where = "MRNUM='" + mrnum + "' and ENDQTY-zxqty>0";
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
				String mrlineid = iJpo.getString("mrlineid");
				String mrnum = iJpo.getString("mrnum");
				String PROJECT = iJpo.getString("PROJECT");
				String MODEL = iJpo.getString("MODEL");
				String issuestoreroom = "";
				double LINEQTY = iJpo.getDouble("endqty");
				double zxqty = iJpo.getDouble("zxqty");
				IJpoSet projectset = MroServer.getMroServer().getJpoSet(
						"projectinfo",
						MroServer.getMroServer().getSystemUserServer());
				projectset.setUserWhere("projectnum='" + PROJECT + "'");
				if (!projectset.isEmpty()) {
					String location = projectset.getJpo(0)
							.getString("location");
					if (location.isEmpty()) {
						throw new MroException("工作令号:" + PROJECT
								+ " 未关联库房，请管理员维护！");
					} else {
						issuestoreroom = location;
					}
				}
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
						transferline.setValue("PROJECTNUM", PROJECT,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("MODEL", MODEL,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("ORDERQTY", 1,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("RECEIVESTOREROOM",
								RECEIVESTOREROOM,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("mrlineid", mrlineid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("ISSUESTOREROOM", issuestoreroom,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("orgid", orgid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferline.setValue("siteid", siteid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						IJpoSet locaddressSet = this.getJpo().getJpoSet(
								"$LOCADDRESS",
								"locaddress",
								"LOCATION='" + issuestoreroom
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
					transferline.setValue("PROJECTNUM", PROJECT,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("MODEL", MODEL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("ORDERQTY", LINEQTY - zxqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("RECEIVESTOREROOM", RECEIVESTOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("ISSUESTOREROOM", issuestoreroom,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("mrlineid", mrlineid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					IJpoSet locaddressSet = this.getJpo().getJpoSet(
							"$LOCADDRESS",
							"locaddress",
							"LOCATION='" + issuestoreroom
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

			}

		}

		transferlineBean.reloadPage();
		this.page.getAppBean().SAVE();
		return super.dialogok();
	}
}
