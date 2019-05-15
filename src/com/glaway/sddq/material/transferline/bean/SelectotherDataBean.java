package com.glaway.sddq.material.transferline.bean;

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
 * <送修单选择耗损件功能类>
 * 
 * @author public2795
 * @version [版本号, 2018-9-4]
 * @since [产品/模块版本]
 */
public class SelectotherDataBean extends DataBean {
	/**
	 * 过滤耗损件记录
	 * 
	 * @throws MroException
	 */
	public void initialize() throws MroException {
		String ISSUESTOREROOM = this.getAppBean().getJpo()
				.getString("ISSUESTOREROOM");
		String sxtype = this.getAppBean().getJpo().getString("sxtype");
		String where = "";
		if (sxtype.equalsIgnoreCase("GZ")) {
			where = "location='"
					+ ISSUESTOREROOM
					+ "' and faultqty>0 and itemnum in (select itemnum from sys_item where ISTURNOVERERP!=1 and ISLOTERP!=1)";
		} else if (sxtype.equalsIgnoreCase("GAIZ")) {
			where = "location='"
					+ ISSUESTOREROOM
					+ "' and remouldqty>0 and itemnum in (select itemnum from sys_item where ISTURNOVERERP!=1 and ISLOTERP!=1)";
		} else if (sxtype.equalsIgnoreCase("YXX")) {
			where = "location='"
					+ ISSUESTOREROOM
					+ "' and testingqty>0 and itemnum in (select itemnum from sys_item where ISTURNOVERERP!=1 and ISLOTERP!=1)";
		} else {
			where = "location='"
					+ ISSUESTOREROOM
					+ "' and itemnum in (select itemnum from sys_item where ISTURNOVERERP!=1 and ISLOTERP!=1)";
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
	@Override
	public int dialogok() throws IOException, MroException {
		DataBean transferlineBean = this.page.getAppBean().getDataBean(
				"othertable");
		IJpoSet ransferlineSet = transferlineBean.getJpoSet();
		IJpo transfer = this.getAppBean().getJpo();
		String transfernum = transfer.getString("transfernum");
		String orgid = transfer.getString("orgid");
		String siteid = transfer.getString("siteid");
		String RECEIVESTOREROOM = transfer.getString("RECEIVESTOREROOM");
		String PROJECTNUM = transfer.getString("PROJECTNUM");
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (IJpo iJpo : list) {
				String itemnum = iJpo.getString("itemnum");
				String sxtype = transfer.getString("sxtype");

				IJpo transferline = ransferlineSet.addJpo();
				transferline.setValue("transfernum", transfernum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("orgid", orgid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("siteid", siteid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("RECEIVESTOREROOM", RECEIVESTOREROOM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("itemnum", itemnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("sxtype", sxtype,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("PROJECTNUM", PROJECTNUM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if (!sxtype.equalsIgnoreCase("")) {
					if (sxtype.equalsIgnoreCase("GZ")) {
						transferline.setValue("ORDERQTY",
								iJpo.getDouble("faultqty"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					} else if (sxtype.equalsIgnoreCase("GAIZ")) {
						transferline.setValue("ORDERQTY",
								iJpo.getDate("remouldqty"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					} else if (sxtype.equalsIgnoreCase("YXX")) {
						transferline.setValue("ORDERQTY",
								iJpo.getDate("testingqty"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}
			}

		}

		transferlineBean.reloadPage();
		return super.dialogok();
	}
}
