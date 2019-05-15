package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <调拨单行故障工单编号字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldTasknum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "ORDERNUM" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 根据放出库房过滤故障工单编号
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		// TODO Auto-generated method stub
		String SXTYPE = this.getJpo().getParent().getString("SXTYPE");
		String itemnum = this.getJpo().getString("itemnum");
		String ISSUESTOREROOM = this.getJpo().getString("ISSUESTOREROOM");
		setListObject("workorder");
		String listSql = "";
		if (SXTYPE.equalsIgnoreCase("GZ")) {
			if (this.getJpo().getParent().getString("type")
					.equalsIgnoreCase("ZXD")) {
				IJpoSet thisjposet = this.getJpo().getJpoSet();
				String qmsnum = null;
				for (int j = 0; j < thisjposet.count(); j++) {
					if (thisjposet.getJpo(j) == null) {
						continue;
					}
					String thisqmsnum = thisjposet.getJpo(j)
							.getString("qmsnum");
					if (StringUtil.isStrNotEmpty(thisqmsnum)) {
						if (StringUtil.isStrEmpty(qmsnum))
							qmsnum = "'" + StringUtil.getSafeSqlStr(thisqmsnum)
									+ "'";
						else
							qmsnum = qmsnum + ",'"
									+ StringUtil.getSafeSqlStr(thisqmsnum)
									+ "'";
					}

				}
				if (qmsnum == null) {
					listSql = " type='故障' and ORDERNUM in　"
							+ "(select wordernum from QMSREPAIRINFO where wordernum in "
							+ "(select JXTASKNUM from JXTASKLOSSPART where UNDERLOC='"
							+ ISSUESTOREROOM
							+ "') and  QMSREPAIRNUM not in (select qmsnum from transferline where qmsnum is not null)"
							+ "and zxdtransfernum is null)and status='关闭' or type='故障' and ORDERNUM in　"
							+ "(select wordernum from QMSREPAIRINFO where wordernum in(select Failureordernum from EXCHANGERECORD where LOCATION='"
							+ ISSUESTOREROOM
							+ "') and "
							+ "QMSREPAIRNUM not in (select qmsnum from transferline where qmsnum is not null) and zxdtransfernum is null)and status='关闭'";
				} else {
					listSql = " type='故障' and ORDERNUM in　"
							+ "(select wordernum from QMSREPAIRINFO where wordernum in "
							+ "(select JXTASKNUM from JXTASKLOSSPART where UNDERLOC='"
							+ ISSUESTOREROOM
							+ "') and QMSREPAIRNUM not in (select qmsnum from transferline where qmsnum is not null) and QMSREPAIRNUM not in ("
							+ qmsnum
							+ ")"
							+ "and zxdtransfernum is null)and status='关闭' or type='故障' and ORDERNUM in　"
							+ "(select wordernum from QMSREPAIRINFO where wordernum in(select Failureordernum from EXCHANGERECORD where LOCATION='"
							+ ISSUESTOREROOM
							+ "') and "
							+ "QMSREPAIRNUM not in (select qmsnum from transferline where qmsnum is not null) and QMSREPAIRNUM not in ("
							+ qmsnum
							+ ") and zxdtransfernum is null)and status='关闭'";
				}

			} else {
				listSql = " type='故障' and status='关闭'";
			}

		}
		if (SXTYPE.equalsIgnoreCase("GAIZ")) {
			listSql = " type='改造' and status='关闭'";
		}
		setListWhere(listSql);
		return super.getList();
	}

	/**
	 * 触发赋值项目，车型信息
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String ORDERNUM = this.getValue();
		IJpoSet workorderSet = MroServer.getMroServer().getJpoSet("workorder",
				MroServer.getMroServer().getSystemUserServer());
		workorderSet.setQueryWhere("ORDERNUM='" + ORDERNUM + "'");
		workorderSet.reset();
		if (!workorderSet.isEmpty()) {
			String PROJECTNUM = workorderSet.getJpo().getString("PROJECTNUM");
			;
			if (this.getJpo().getParent().getString("sxtype")
					.equalsIgnoreCase("GAIZ")) {
				String TRANSNOTICENUM = workorderSet.getJpo()
						.getJpoSet("TRANSPLAN").getJpo()
						.getString("TRANSNOTICENUM");
				this.getJpo().setValue("PROJECTNUM", PROJECTNUM,
						GWConstant.P_NOACTION);// 项目
				this.getJpo().setValue("TRANSNOTICENUM", TRANSNOTICENUM,
						GWConstant.P_NOACTION);// 项目
			} else {
				String models = workorderSet.getJpo().getString("models");
				String MODELPROJECT = workorderSet.getJpo().getString(
						"MODELPROJECT");
				String VBELN = workorderSet.getJpo().getString("VBELN");
				String SERVCOMPANY = workorderSet.getJpo().getString(
						"SERVCOMPANY");
				String CUSTNAME = workorderSet.getJpo()
						.getJpoSet("SERVCOMPANY").getJpo()
						.getString("CUSTNAME");

				this.getJpo().setValue("PROJECTNUM", PROJECTNUM,
						GWConstant.P_NOACTION);// 项目
				this.getJpo().setValue("MODEL", models, GWConstant.P_NOACTION);// 车型
				this.getJpo().setValue("PROJNUM", MODELPROJECT,
						GWConstant.P_NOACTION);// 车型项目
				this.getJpo().setValue("CUSTNUM", SERVCOMPANY,
						GWConstant.P_NOACTION);// 车型项目
				this.getJpo().setValue("CUSTNUMNAME", CUSTNAME,
						GWConstant.P_NOACTION);// 车型项目
				this.getJpo()
						.setValue("SCALENUM", VBELN, GWConstant.P_NOACTION);// 销售订单号
			}
		}
		if (!ORDERNUM.isEmpty()) {
			this.getField("sqn").setUserLookup("WOITEM");
		} else if (ORDERNUM.isEmpty()) {
			this.getField("sqn").setUserLookup("sqn");
		}
	}

}
