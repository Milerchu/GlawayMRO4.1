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

/**
 * 
 * <选择入库单弹出框DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class selectmprlineDataBean extends DataBean {
	/**
	 * 过滤入库单信息
	 * 
	 * @throws MroException
	 */
	public void initialize() throws MroException {
		String ISSUESTOREROOM = this.getAppBean().getJpo()
				.getString("ISSUESTOREROOM");
		String sxtype = this.getAppBean().getJpo().getString("sxtype");
		IJpoSet transferlineset = this.getAppBean().getJpo()
				.getJpoSet("transferline");
		String where = "";
		String Idstr = "";
		if (transferlineset.isEmpty()) {

			where = "ISSUESTOREROOM='"
					+ ISSUESTOREROOM
					+ "' and status='已接收' and sxtype='"
					+ sxtype
					+ "' and mprlineid not in (select mprlineid from transferline where mprlineid is not null and transfernum in (select transfernum from transfer where type='SX'))";
		}
		if (!transferlineset.isEmpty()) {
			for (int i = 0; i < transferlineset.count(); i++) {
				String mprlineid = transferlineset.getJpo(i).getString(
						"mprlineid");
				if (!"".equals(mprlineid)) {
					if (StringUtil.isStrEmpty(Idstr))
						Idstr = "'" + StringUtil.getSafeSqlStr(mprlineid) + "'";
					else
						Idstr = Idstr + ",'"
								+ StringUtil.getSafeSqlStr(mprlineid) + "'";

				}
			}
			if (Idstr != null) {
				String where1 = "ISSUESTOREROOM='"
						+ ISSUESTOREROOM
						+ "' and status='已接收' and sxtype='"
						+ sxtype
						+ "' and mprlineid not in (select mprlineid from transferline where mprlineid is not null and transfernum in (select transfernum from transfer where type='SX'))";
				where = where1 + "and  mprlineid not in (" + Idstr + ")";
			}
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
				"othertable");
		IJpoSet ransferlineSet = transferlineBean.getJpoSet();
		IJpo transfer = this.getAppBean().getJpo();
		String transfernum = transfer.getString("transfernum");
		String orgid = transfer.getString("orgid");
		String siteid = transfer.getString("siteid");
		String RECEIVESTOREROOM = transfer.getString("RECEIVESTOREROOM");
		String ISSUESTOREROOM = transfer.getString("ISSUESTOREROOM");
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (IJpo iJpo : list) {
				String mprnum = iJpo.getString("mprnum");
				String memo = iJpo.getString("memo");
				IJpoSet mprset = MroServer.getMroServer().getJpoSet("mpr",
						MroServer.getMroServer().getSystemUserServer());
				mprset.setUserWhere("mprnum='" + mprnum + "'");
				mprset.reset();
				String PROJECTNUM = "";
				if (!mprset.isEmpty()) {
					PROJECTNUM = mprset.getJpo(0).getString("PROJECTNUM");
				}
				String mprlineid = iJpo.getString("mprlineid");
				String itemnum = iJpo.getString("itemnum");
				String sqn = iJpo.getString("sqn");

				String sxtype = iJpo.getString("sxtype");
				String lotnum = iJpo.getString("lotnum");
				double ORDERQTY = iJpo.getDouble("qty");

				String assetnum = iJpo.getString("assetnum");
				for (int i = 0; i < ORDERQTY; i++) {
					IJpo transferline = ransferlineSet.addJpo();
					transferline.setValue("transfernum", transfernum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("orgid", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("siteid", siteid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("RECEIVESTOREROOM", RECEIVESTOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("ISSUESTOREROOM", ISSUESTOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("sqn", sqn,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("lotnum", lotnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("sxtype", sxtype,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("PROJECTNUM", PROJECTNUM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("ORDERQTY", 1,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("mprlineid", mprlineid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("assetnum", assetnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					transferline.setValue("FAILUREDESC", memo,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}

			}
		}

		transferlineBean.reloadPage();
		this.page.getAppBean().SAVE();
		return super.dialogok();
	}
}
