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
 * <选择缴库单弹出框DataBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class selectjkdlineDataBean extends DataBean {
	/**
	 * 过滤缴库单信息
	 * 
	 * @throws MroException
	 */
	public void initialize() throws MroException {
		String ISSUESTOREROOM = this.getAppBean().getJpo()
				.getString("ISSUESTOREROOM");
		String mrnum = this.getAppBean().getJpo().getString("mrnum");
		IJpoSet transferlineset = this.getAppBean().getJpo()
				.getJpoSet("transferline");
		String RECEIVESTOREROOM = this.getAppBean().getJpo()
				.getString("RECEIVESTOREROOM");
		String LOCATIONTYPE = this.getAppBean().getJpo()
				.getJpoSet("RECEIVESTOREROOM").getJpo()
				.getString("LOCATIONTYPE");
		String STOREROOMLEVEL = this.getAppBean().getJpo()
				.getJpoSet("RECEIVESTOREROOM").getJpo()
				.getString("STOREROOMLEVEL");
		String ERPLOC = this.getAppBean().getJpo()
				.getJpoSet("RECEIVESTOREROOM").getJpo().getString("ERPLOC");
		String where = "";
		String Idstr = "";
		if (LOCATIONTYPE.equalsIgnoreCase("常规")
				&& STOREROOMLEVEL.equalsIgnoreCase("中心库")) {
			if (ERPLOC.equalsIgnoreCase("1020")) {
				if (transferlineset.isEmpty()) {
					where = " transferlineid in (select TRANSFERLINEID from transferline WHERE ISSUESTOREROOM='"
							+ ISSUESTOREROOM
							+ "' and status='已接收'"
							+ " and  exists (select transfernum from transfer where type='JKD' and transfer.transfernum=transferline.transfernum)"
							+ "and  not exists (select jkdline.jkdlineid from transferline jkdline where jkdline.jkdlineid is not null and jkdline.jkdlineid=transferline.transferlineid) "
							+ "and PASSQTY>0 and DEALTYPE='退库' and  exists (select projectnum from projectinfo where location='"
							+ RECEIVESTOREROOM
							+ "' and projectinfo.projectnum=transferline.projectnum))";
				}
				if (!transferlineset.isEmpty()) {
					for (int i = 0; i < transferlineset.count(); i++) {
						String jkdlineid = transferlineset.getJpo(i).getString(
								"jkdlineid");
						if (!"".equals(jkdlineid)) {
							if (StringUtil.isStrEmpty(Idstr))
								Idstr = "'"
										+ StringUtil.getSafeSqlStr(jkdlineid)
										+ "'";
							else
								Idstr = Idstr + ",'"
										+ StringUtil.getSafeSqlStr(jkdlineid)
										+ "'";

						}
					}
					if (Idstr != null) {
						String where1 = " transferlineid in (select TRANSFERLINEID from transferline WHERE ISSUESTOREROOM='"
								+ ISSUESTOREROOM
								+ "' and status='已接收'"
								+ " and  exists (select transfernum from transfer where type='JKD' and transfer.transfernum=transferline.transfernum)"
								+ "and  not exists (select jkdline.jkdlineid from transferline jkdline where jkdline.jkdlineid is not null and jkdline.jkdlineid=transferline.transferlineid) "
								+ "and PASSQTY>0 and DEALTYPE='退库' and  exists (select projectnum from projectinfo where location='"
								+ RECEIVESTOREROOM
								+ "' and projectinfo.projectnum=transferline.projectnum))";
						where = where1 + "and  transferlineid not in (" + Idstr
								+ ")";
					}
				}
			} else {
				if (transferlineset.isEmpty()) {
					where = " transferlineid in (select TRANSFERLINEID from transferline WHERE ISSUESTOREROOM='"
							+ ISSUESTOREROOM
							+ "' and status='已接收'"
							+ " and  exists (select transfernum from transfer where type='JKD' and transfer.transfernum=transferline.transfernum)"
							+ "and  not exists (select jkdline.jkdlineid from transferline jkdline where jkdline.jkdlineid is not null and jkdline.jkdlineid=transferline.transferlineid) "
							+ "and PASSQTY>0 and DEALTYPE='退库')";
				}
				if (!transferlineset.isEmpty()) {
					for (int i = 0; i < transferlineset.count(); i++) {
						String jkdlineid = transferlineset.getJpo(i).getString(
								"jkdlineid");
						if (!"".equals(jkdlineid)) {
							if (StringUtil.isStrEmpty(Idstr))
								Idstr = "'"
										+ StringUtil.getSafeSqlStr(jkdlineid)
										+ "'";
							else
								Idstr = Idstr + ",'"
										+ StringUtil.getSafeSqlStr(jkdlineid)
										+ "'";

						}
					}
					if (Idstr != null) {
						String where1 = " transferlineid in (select TRANSFERLINEID from transferline WHERE ISSUESTOREROOM='"
								+ ISSUESTOREROOM
								+ "' and status='已接收'"
								+ " and  exists (select transfernum from transfer where type='JKD' and transfer.transfernum=transferline.transfernum)"
								+ "and  not exists (select jkdline.jkdlineid from transferline jkdline where jkdline.jkdlineid is not null and jkdline.jkdlineid=transferline.transferlineid) "
								+ "and PASSQTY>0 and DEALTYPE='退库')";
						where = where1 + "and  transferlineid not in (" + Idstr
								+ ")";
					}
				}
			}
		}
		if (LOCATIONTYPE.equalsIgnoreCase("常规")
				&& STOREROOMLEVEL.equalsIgnoreCase("现场库")
				|| LOCATIONTYPE.equalsIgnoreCase("常规")
				&& STOREROOMLEVEL.equalsIgnoreCase("现场站点库")
				|| LOCATIONTYPE.equalsIgnoreCase("常规")
				&& STOREROOMLEVEL.equalsIgnoreCase("区域库")) {
			String wxlocation = "";
			if (ERPLOC.equalsIgnoreCase("其他待处理库")
					|| ERPLOC.equalsIgnoreCase("改造物料库")) {
				wxlocation = this.getAppBean().getJpo()
						.getString("RECEIVESTOREROOM");
			} else {
				wxlocation = this.getAppBean().getJpo()
						.getJpoSet("RECEIVESTOREROOM").getJpo()
						.getJpoSet("WXLOCATION").getJpo().getString("location");
			}
			if (transferlineset.isEmpty()) {
				if (mrnum.isEmpty()) {
					where = " transferlineid in (select TRANSFERLINEID from transferline WHERE ISSUESTOREROOM='"
							+ ISSUESTOREROOM
							+ "' and status='已接收'"
							+ " and  exists (select transfernum from transfer where type='JKD' and transfer.transfernum=transferline.transfernum)"
							+ "and  not exists (select jkdline.jkdlineid from transferline jkdline where jkdline.jkdlineid is not null and jkdline.jkdlineid=transferline.transferlineid) "
							+ "and PASSQTY>0 and DEALTYPE='返回' and exists (select transferlinetradeid from transferlinetrade where ISSUESTOREROOM in('"
							+ wxlocation
							+ "','"
							+ RECEIVESTOREROOM
							+ "') and transferlinetrade.transferlinetradeid=transferline.zxdlineid))";
				} else {
					where = " transferlineid in (select TRANSFERLINEID from transferline WHERE ISSUESTOREROOM='"
							+ ISSUESTOREROOM
							+ "' and status='已接收'"
							+ " and  exists (select transfernum from transfer where type='JKD' and transfer.transfernum=transferline.transfernum)"
							+ "and  not exists (select jkdline.jkdlineid from transferline jkdline where jkdline.jkdlineid is not null and jkdline.jkdlineid=transferline.transferlineid) "
							+ "and PASSQTY>0 and DEALTYPE!='返回' and exists (select itemnum from mrlinetransfer where mrnum='"
							+ mrnum
							+ "' and transtype='返修后发运' and mrlinetransfer.itemnum=transferline.itemnum))";
				}
			}
			if (!transferlineset.isEmpty()) {
				for (int i = 0; i < transferlineset.count(); i++) {
					String jkdlineid = transferlineset.getJpo(i).getString(
							"jkdlineid");
					if (!"".equals(jkdlineid)) {
						if (StringUtil.isStrEmpty(Idstr))
							Idstr = "'" + StringUtil.getSafeSqlStr(jkdlineid)
									+ "'";
						else
							Idstr = Idstr + ",'"
									+ StringUtil.getSafeSqlStr(jkdlineid) + "'";

					}
				}
				if (Idstr != null) {
					if (mrnum.isEmpty()) {
						String where1 = " transferlineid in (select TRANSFERLINEID from transferline WHERE ISSUESTOREROOM='"
								+ ISSUESTOREROOM
								+ "' and status='已接收'"
								+ " and  exists (select transfernum from transfer where type='JKD' and transfer.transfernum=transferline.transfernum)"
								+ "and  not exists (select jkdline.jkdlineid from transferline jkdline where jkdline.jkdlineid is not null and jkdline.jkdlineid=transferline.transferlineid) "
								+ "and PASSQTY>0 and DEALTYPE='返回' and exists (select transferlinetradeid from transferlinetrade where ISSUESTOREROOM in('"
								+ wxlocation
								+ "','"
								+ RECEIVESTOREROOM
								+ "') and transferlinetrade.transferlinetradeid=transferline.zxdlineid))";
						where = where1 + "and  transferlineid not in (" + Idstr
								+ ")";
					} else {
						String where1 = " transferlineid in (select TRANSFERLINEID from transferline WHERE ISSUESTOREROOM='"
								+ ISSUESTOREROOM
								+ "' and status='已接收'"
								+ " and  exists (select transfernum from transfer where type='JKD' and transfer.transfernum=transferline.transfernum)"
								+ "and  not exists (select jkdline.jkdlineid from transferline jkdline where jkdline.jkdlineid is not null and jkdline.jkdlineid=transferline.transferlineid) "
								+ "and PASSQTY>0 and DEALTYPE!='返回' and exists (select itemnum from mrlinetransfer where mrnum='"
								+ mrnum
								+ "' and transtype='返修后发运' and mrlinetransfer.itemnum=transferline.itemnum))";
						where = where1 + "and  transferlineid not in (" + Idstr
								+ ")";
					}
				}
			}
		}
		if (LOCATIONTYPE.equalsIgnoreCase("维修")
				&& STOREROOMLEVEL.equalsIgnoreCase("中心库")) {
			if (transferlineset.isEmpty()) {
				where = " transferlineid in (select TRANSFERLINEID from transferline WHERE ISSUESTOREROOM='"
						+ ISSUESTOREROOM
						+ "' and status='已接收'"
						+ " and  exists (select transfernum from transfer where type='JKD' and transfer.transfernum=transferline.transfernum)"
						+ "and  not exists (select jkdline.jkdlineid from transferline jkdline where jkdline.jkdlineid is not null and jkdline.jkdlineid=transferline.transferlineid) "
						+ "and FAILQTY>0 )";
			}
			if (!transferlineset.isEmpty()) {
				for (int i = 0; i < transferlineset.count(); i++) {
					String jkdlineid = transferlineset.getJpo(i).getString(
							"jkdlineid");
					if (!"".equals(jkdlineid)) {
						if (StringUtil.isStrEmpty(Idstr))
							Idstr = "'" + StringUtil.getSafeSqlStr(jkdlineid)
									+ "'";
						else
							Idstr = Idstr + ",'"
									+ StringUtil.getSafeSqlStr(jkdlineid) + "'";

					}
				}
				if (Idstr != null) {
					String where1 = " transferlineid in (select TRANSFERLINEID from transferline WHERE ISSUESTOREROOM='"
							+ ISSUESTOREROOM
							+ "' and status='已接收'"
							+ " and  exists (select transfernum from transfer where type='JKD' and transfer.transfernum=transferline.transfernum)"
							+ "and  not exists (select jkdline.jkdlineid from transferline jkdline where jkdline.jkdlineid is not null and jkdline.jkdlineid=transferline.transferlineid) "
							+ "and FAILQTY>0 )";
					where = where1 + "and  transferlineid not in (" + Idstr
							+ ")";
				}
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
				"1527057849697");
		IJpoSet ransferlineSet = transferlineBean.getJpoSet();
		IJpo transfer = this.getAppBean().getJpo();
		String transfernum = transfer.getString("transfernum");
		String mrnum = transfer.getString("mrnum");
		String orgid = transfer.getString("orgid");
		String siteid = transfer.getString("siteid");
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (IJpo iJpo : list) {
				String RECEIVESTOREROOM = transfer
						.getString("RECEIVESTOREROOM");/* 接收库房 */
				String ISSUESTOREROOM = transfer.getString("ISSUESTOREROOM");/* 发出库房 */
				String PROJECTNUM = iJpo.getString("PROJECTNUM");

				String jkdlineid = iJpo.getString("transferlineid");
				String itemnum = iJpo.getString("itemnum");
				String sqn = iJpo.getString("sqn");
				String lotnum = iJpo.getString("lotnum");
				String assetnum = iJpo.getString("assetnum");
				String newitemnum = iJpo.getString("newitemnum");
				String newsqn = iJpo.getString("newsqn");
				String newlotnum = iJpo.getString("newlotnum");
				String newassetnum = iJpo.getString("newassetnum");
				String MODEL = iJpo.getString("MODEL");
				String TRANSWORKORDERNUM = iJpo.getString("TRANSWORKORDERNUM");
				double PASSQTY = iJpo.getDouble("PASSQTY");
				double FAILQTY = iJpo.getDouble("FAILQTY");
				double ORDERQTY = PASSQTY + FAILQTY;
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
				if(newitemnum.isEmpty()){
					transferline.setValue("itemnum", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}else{
					transferline.setValue("itemnum", newitemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(newsqn.isEmpty()){
					transferline.setValue("sqn", sqn,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}else{
					transferline.setValue("sqn", newsqn,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(newlotnum.isEmpty()){
					transferline.setValue("lotnum", lotnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}else{
					transferline.setValue("lotnum", newlotnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(newassetnum.isEmpty()){
					transferline.setValue("assetnum", assetnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}else{
					transferline.setValue("assetnum", newassetnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				transferline.setValue("PROJECTNUM", PROJECTNUM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("ORDERQTY", ORDERQTY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("MODEL", MODEL,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("TRANSWORKORDERNUM", TRANSWORKORDERNUM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("PASSQTY", PASSQTY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("FAILQTY", FAILQTY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				transferline.setValue("jkdlineid", jkdlineid,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if (!mrnum.isEmpty()) {
					IJpoSet mrlinetransferset = this.getJpo().getJpoSet(
							"$mrlinetransferset",
							"mrlinetransfer",
							"mrnum='" + mrnum
									+ "' and transtype='返修后发运' and itemnum='"
									+ itemnum + "'");
					if (!mrlinetransferset.isEmpty()) {
						String mrlinetransferid = mrlinetransferset.getJpo(0)
								.getString("mrlinetransferid");
						transferline.setValue("mrlinetransid",
								mrlinetransferid,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}
				IJpoSet locaddressSet = this.getJpo().getJpoSet("$LOCADDRESS",
						"locaddress",
						"LOCATION='" + ISSUESTOREROOM + "' and isaddress='是'");
				if (!locaddressSet.isEmpty()) {
					IJpo locaddress = locaddressSet.getJpo(0);
					transferline.setValue("ISSUEADDRESS",
							locaddress.getString("address"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}

				IJpoSet relocaddressSet = this.getJpo()
						.getJpoSet(
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

		transferlineBean.reloadPage();
		this.page.getAppBean().SAVE();
		return super.dialogok();

	}
}
