package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <配件申请处置行选择发出库房字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-13]
 * @since [产品/模块版本]
 */
public class FldStoreroom extends JpoField {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "LOCATION" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤库房数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("locations");
		String receiveerploc = this.getJpo().getJpoSet("receivestoreroom")
				.getJpo().getString("erploc");
		String itemnum = this.getJpo().getString("itemnum");
		String TRANSTYPE = this.getJpo().getString("TRANSTYPE");/* 处置方式 */
		String RECEIVESTOREROOM = this.getJpo().getString("RECEIVESTOREROOM");/* 接收库房 */
		String mrnum = this.getJpo().getString("mrnum");
		String listSql = "";
		IJpoSet j = super.getList();
		if (TRANSTYPE.equalsIgnoreCase("内部协调")) {
			String locationstr = "";
			IJpoSet locinventoryset = MroServer.getMroServer().getJpoSet(
					"sys_inventory",
					MroServer.getMroServer().getSystemUserServer());
			locinventoryset
					.setUserWhere("itemnum='"
							+ itemnum
							+ "' and location!='"
							+ RECEIVESTOREROOM
							+ "' and location in (select location from locations where erploc='"
							+ receiveerploc
							+ "' AND STOREROOMGRADE='二级' and STOREROOMLEVEL in ('现场库','现场站点库','区域库') and LOCATIONTYPE!='维修' and status='正常')");
			locinventoryset.reset();
			if (!locinventoryset.isEmpty()) {
				for (int i = 0; i < locinventoryset.count(); i++) {
					IJpo locinventory = locinventoryset.getJpo(i);
					String location = locinventory.getString("location");
					// 调用计算可用数量方法
					double kyqty = invkyqty(itemnum, location);
					if (kyqty > 0) {
						if (StringUtil.isStrEmpty(locationstr))
							locationstr = "'"
									+ StringUtil.getSafeSqlStr(location) + "'";
						else
							locationstr = locationstr + ",'"
									+ StringUtil.getSafeSqlStr(location) + "'";
					}
				}
				IJpoSet MRLINETRANSFERset = MroServer.getMroServer().getJpoSet(
						"MRLINETRANSFER",
						MroServer.getMroServer().getSystemUserServer());
				MRLINETRANSFERset.setUserWhere("mrnum='" + mrnum
						+ "' and TRANSTYPE='内部协调' and itemnum='" + itemnum
						+ "'");
				MRLINETRANSFERset.reset();
				if (MRLINETRANSFERset.isEmpty()) {
					if (locationstr != null) {
						listSql = listSql + "location in (" + locationstr
								+ " )";
					}
				} else {
					if (locationstr != null) {
						listSql = listSql
								+ "location in ("
								+ locationstr
								+ " ) and location not in (select STOREROOM from MRLINETRANSFER where mrnum='"
								+ mrnum
								+ "' and TRANSTYPE='内部协调' and itemnum='"
								+ itemnum + "' and STOREROOM is not null)";
					}
				}
				if (!listSql.equals("")) {
					listSql=listSql+"and status='正常'";
					setListWhere(listSql);
				}
				j = super.getList();
				for (int i = 0; i < j.count(); i++) {
					String location = j.getJpo(i).getString("location");
					// 调用计算可用数量方法
					double kyqty = invkyqty(itemnum, location);
					j.getJpo(i).setValue("locqty", kyqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				j.save();
			} else {
				listSql = "location='XLB001'";
				setListWhere(listSql);
				j = super.getList();
			}

		}
		if (TRANSTYPE.equalsIgnoreCase("下达计划")) {
			IJpoSet MRLINETRANSFERset = MroServer.getMroServer().getJpoSet(
					"MRLINETRANSFER",
					MroServer.getMroServer().getSystemUserServer());
			MRLINETRANSFERset.setUserWhere("mrnum='" + mrnum
					+ "' and TRANSTYPE='下达计划' and itemnum='" + itemnum + "'");
			MRLINETRANSFERset.reset();
			if (MRLINETRANSFERset.isEmpty()) {
				listSql = listSql
						+ "erploc='"
						+ receiveerploc
						+ "' AND STOREROOMGRADE='二级' and STOREROOMLEVEL in ('中心库') and LOCATIONTYPE='常规' and status='正常'";
			} else {
				listSql = listSql
						+ "erploc='"
						+ receiveerploc
						+ "' AND STOREROOMGRADE='二级' and STOREROOMLEVEL in ('中心库') and LOCATIONTYPE='常规' and status='正常' "
						+ "and location not in (select STOREROOM from MRLINETRANSFER where mrnum='"
						+ mrnum + "' and TRANSTYPE='下达计划' and itemnum='"
						+ itemnum + "' and STOREROOM is not null)";
			}
			if (!listSql.equals("")) {
				listSql=listSql+"and status='正常'";
				setListWhere(listSql);
			}
			j = super.getList();
			for (int i = 0; i < j.count(); i++) {
				String location = j.getJpo(i).getString("location");
				// 调用计算可用数量方法
				double kyqty = invkyqty(itemnum, location);
				if (kyqty > 0) {
					j.getJpo(i).setValue("locqty", kyqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				} else {
					j.getJpo(i).setValue("locqty", 0,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
			j.save();
		}
		if (TRANSTYPE.equalsIgnoreCase("返修后发运")) {
			listSql = listSql + "location='Y1087'";
			if (!listSql.equals("")) {
				listSql=listSql+"and status='正常'";
				setListWhere(listSql);
			}
			j = super.getList();
			for (int i = 0; i < j.count(); i++) {
				String location = j.getJpo(i).getString("location");
				// 调用计算可用数量方法
				double kyqty = invkyqty(itemnum, location);
				if (kyqty > 0) {
					j.getJpo(i).setValue("locqty", kyqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				} else {
					j.getJpo(i).setValue("locqty", 0,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
			j.save();
		}
		if (TRANSTYPE.equalsIgnoreCase("现场调拨")) {
			String locationstr = "";
			IJpoSet locinventoryset = MroServer.getMroServer().getJpoSet(
					"sys_inventory",
					MroServer.getMroServer().getSystemUserServer());
			locinventoryset
					.setUserWhere("itemnum='"
							+ itemnum
							+ "' and location!='"
							+ RECEIVESTOREROOM
							+ "' and location in (select location from locations where erploc='"
							+ receiveerploc
							+ "' AND STOREROOMGRADE='二级' and STOREROOMLEVEL in ('现场库','现场站点库','区域库') and LOCATIONTYPE!='维修' and status='正常')");
			locinventoryset.reset();
			if (!locinventoryset.isEmpty()) {
				for (int i = 0; i < locinventoryset.count(); i++) {
					IJpo locinventory = locinventoryset.getJpo(i);
					String location = locinventory.getString("location");
					// 调用计算可用数量方法
					double kyqty = invkyqty(itemnum, location);
					if (kyqty > 0) {
						if (StringUtil.isStrEmpty(locationstr))
							locationstr = "'"
									+ StringUtil.getSafeSqlStr(location) + "'";
						else
							locationstr = locationstr + ",'"
									+ StringUtil.getSafeSqlStr(location) + "'";
					}
				}
				IJpoSet MRLINETRANSFERset = MroServer.getMroServer().getJpoSet(
						"MRLINETRANSFER",
						MroServer.getMroServer().getSystemUserServer());
				MRLINETRANSFERset.setUserWhere("mrnum='" + mrnum
						+ "' and TRANSTYPE='现场调拨' and itemnum='" + itemnum
						+ "'");
				MRLINETRANSFERset.reset();
				if (MRLINETRANSFERset.isEmpty()) {
					if (locationstr != null) {
						listSql = listSql + "location in (" + locationstr
								+ " )";
					}
				} else {
					if (locationstr != null) {
						listSql = listSql
								+ "location in ("
								+ locationstr
								+ " ) and location not in (select STOREROOM from MRLINETRANSFER where mrnum='"
								+ mrnum
								+ "' and TRANSTYPE='现场调拨' and itemnum='"
								+ itemnum + "' and STOREROOM is not null)";
					}
				}
				if (!listSql.equals("")) {
					listSql=listSql+"and status='正常'";
					setListWhere(listSql);
				}
				j = super.getList();
				for (int i = 0; i < j.count(); i++) {
					String location = j.getJpo(i).getString("location");
					// 调用计算可用数量方法
					double kyqty = invkyqty(itemnum, location);
					j.getJpo(i).setValue("locqty", kyqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				j.save();
			} else {
				listSql = "location='XLB001'";
				setListWhere(listSql);
				j = super.getList();
			}

		}
		if (TRANSTYPE.equalsIgnoreCase("中心库调拨")) {
			String locationstr = "";
			IJpoSet locinventoryset = MroServer.getMroServer().getJpoSet(
					"sys_inventory",
					MroServer.getMroServer().getSystemUserServer());
			locinventoryset
					.setUserWhere("itemnum='"
							+ itemnum
							+ "' and location!='"
							+ RECEIVESTOREROOM
							+ "' and location in (select location from locations where erploc='"
							+ receiveerploc
							+ "' AND STOREROOMGRADE='二级' and STOREROOMLEVEL in ('中心库') and LOCATIONTYPE='常规' and status='正常')");
			locinventoryset.reset();
			if (!locinventoryset.isEmpty()) {
				for (int i = 0; i < locinventoryset.count(); i++) {
					IJpo locinventory = locinventoryset.getJpo(i);
					String location = locinventory.getString("location");
					double kyqty = invkyqty(itemnum, location);
					if (kyqty > 0) {
						if (StringUtil.isStrEmpty(locationstr))
							locationstr = "'"
									+ StringUtil.getSafeSqlStr(location) + "'";
						else
							locationstr = locationstr + ",'"
									+ StringUtil.getSafeSqlStr(location) + "'";
					}
				}
				IJpoSet MRLINETRANSFERset = MroServer.getMroServer().getJpoSet(
						"MRLINETRANSFER",
						MroServer.getMroServer().getSystemUserServer());
				MRLINETRANSFERset.setUserWhere("mrnum='" + mrnum
						+ "' and TRANSTYPE='中心库调拨' and itemnum='" + itemnum
						+ "'");
				MRLINETRANSFERset.reset();
				if (MRLINETRANSFERset.isEmpty()) {
					if (locationstr != null) {
						listSql = listSql + "location in (" + locationstr
								+ " )";
					}
				} else {
					if (locationstr != null) {
						listSql = listSql
								+ "location in ("
								+ locationstr
								+ " ) and location not in (select STOREROOM from MRLINETRANSFER where mrnum='"
								+ mrnum
								+ "' and TRANSTYPE='中心库调拨' and itemnum='"
								+ itemnum + "' and STOREROOM is not null)";
					}
				}
				if (!listSql.equals("")) {
					listSql=listSql+"and status='正常'";
					setListWhere(listSql);
				}
				j = super.getList();
				for (int i = 0; i < j.count(); i++) {
					String location = j.getJpo(i).getString("location");
					// 调用计算可用数量方法
					double kyqty = invkyqty(itemnum, location);
					j.getJpo(i).setValue("locqty", kyqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				j.save();
			} else {
				listSql ="location='XLB001'";
				setListWhere(listSql);
				j = super.getList();

			}
		}
		if (TRANSTYPE.equalsIgnoreCase("中心库调拨后下达计划")) {
			String locationstr = "";
			IJpoSet locinventoryset = MroServer.getMroServer().getJpoSet(
					"sys_inventory",
					MroServer.getMroServer().getSystemUserServer());
			locinventoryset
					.setUserWhere("itemnum='"
							+ itemnum
							+ "' and location!='"
							+ RECEIVESTOREROOM
							+ "' and location in (select location from locations where erploc='"
							+ receiveerploc
							+ "' AND STOREROOMGRADE='二级' and STOREROOMLEVEL in ('中心库') and LOCATIONTYPE='常规' and status='正常')");
			locinventoryset.reset();
			if (!locinventoryset.isEmpty()) {
				for (int i = 0; i < locinventoryset.count(); i++) {
					IJpo locinventory = locinventoryset.getJpo(i);
					String location = locinventory.getString("location");
					// 调用计算可用数量方法
					double kyqty = invkyqty(itemnum, location);
					if (kyqty > 0) {
						if (StringUtil.isStrEmpty(locationstr))
							locationstr = "'"
									+ StringUtil.getSafeSqlStr(location) + "'";
						else
							locationstr = locationstr + ",'"
									+ StringUtil.getSafeSqlStr(location) + "'";
					}
				}
				IJpoSet MRLINETRANSFERset = MroServer.getMroServer().getJpoSet(
						"MRLINETRANSFER",
						MroServer.getMroServer().getSystemUserServer());
				MRLINETRANSFERset.setUserWhere("mrnum='" + mrnum
						+ "' and TRANSTYPE='中心库调拨后下达计划' and itemnum='"
						+ itemnum + "'");
				MRLINETRANSFERset.reset();
				if (MRLINETRANSFERset.isEmpty()) {
					if (locationstr != null) {
						listSql = listSql + "location in (" + locationstr
								+ " )";
					}
				} else {
					if (locationstr != null) {
						listSql = listSql
								+ "location in ("
								+ locationstr
								+ " ) and location not in (select STOREROOM from MRLINETRANSFER where mrnum='"
								+ mrnum
								+ "' and TRANSTYPE='中心库调拨后下达计划' and itemnum='"
								+ itemnum + "' and STOREROOM is not null)";
					}
				}
				if (!listSql.equals("")) {
					listSql=listSql+"and status='正常'";
					setListWhere(listSql);
				}
				j = super.getList();
				for (int i = 0; i < j.count(); i++) {
					String location = j.getJpo(i).getString("location");
					// 调用计算可用数量方法
					double kyqty = invkyqty(itemnum, location);
					j.getJpo(i).setValue("locqty", kyqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				j.save();
			} else {
				listSql = "location='XLB001'";
				setListWhere(listSql);
				j = super.getList();

			}
		}
		if (TRANSTYPE.equalsIgnoreCase("计划交付后发货")) {
			IJpoSet MRLINETRANSFERset = MroServer.getMroServer().getJpoSet(
					"MRLINETRANSFER",
					MroServer.getMroServer().getSystemUserServer());
			MRLINETRANSFERset.setUserWhere("mrnum='" + mrnum
					+ "' and TRANSTYPE='计划交付后发货' and itemnum='" + itemnum + "'");
			MRLINETRANSFERset.reset();
			if (MRLINETRANSFERset.isEmpty()) {
				listSql = listSql
						+ "erploc='"
						+ receiveerploc
						+ "' AND STOREROOMGRADE='二级' and STOREROOMLEVEL in ('中心库') and LOCATIONTYPE='常规' and status='正常'";
			} else {
				listSql = listSql
						+ "erploc='"
						+ receiveerploc
						+ "' AND STOREROOMGRADE='二级' and STOREROOMLEVEL in ('中心库') and LOCATIONTYPE='常规' and status='正常' "
						+ "and location not in (select STOREROOM from MRLINETRANSFER where mrnum='"
						+ mrnum + "' and TRANSTYPE='计划交付后发货' and itemnum='"
						+ itemnum + "' and STOREROOM is not null)";
			}
			if (!listSql.equals("")) {
				listSql=listSql+"and status='正常'";
				setListWhere(listSql);
			}
			j = super.getList();
			for (int i = 0; i < j.count(); i++) {
				String location = j.getJpo(i).getString("location");
				// 调用计算可用数量方法
				double kyqty = invkyqty(itemnum, location);
				if (kyqty > 0) {
					j.getJpo(i).setValue("locqty", kyqty,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				} else {
					j.getJpo(i).setValue("locqty", 0,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
			j.save();
		}
		return j;
	}

	/**
	 * 触发控制处置数量只读
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String transtype = this.getJpo().getString("TRANSTYPE");
		String keeper = this.getJpo().getJpoSet("STOREROOM").getJpo(0)
				.getString("keeper");
		this.getJpo().setValue("keeper", keeper,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		if (transtype.equalsIgnoreCase("下达计划")) {
			this.getJpo().setFieldFlag("TRANSFERQTY", GWConstant.S_REQUIRED,
					false);
			this.getJpo().setValue("TRANSFERQTY", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("TRANSFERQTY", GWConstant.S_READONLY,
					true);
		} else {
			this.getJpo().setFieldFlag("TRANSFERQTY", GWConstant.S_READONLY,
					false);
			this.getJpo().setValue("TRANSFERQTY", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("TRANSFERQTY", GWConstant.S_REQUIRED,
					true);
		}

	}

	/**
	 * 
	 * <计算库房可用数量方法>
	 * 
	 * @param itemnum
	 *            物料编码
	 * @param location
	 *            库房编码
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public double invkyqty(String itemnum, String location) throws MroException {
		double kyqty = 0;
		IJpoSet inventoryset = MroServer.getMroServer()
				.getJpoSet("sys_inventory",
						MroServer.getMroServer().getSystemUserServer());
		inventoryset.setUserWhere("itemnum='" + itemnum + "' and location='"
				+ location + "'");
		inventoryset.reset();
		if (!inventoryset.isEmpty()) {
			double curbal = inventoryset.getJpo(0).getDouble("curbal");// 库存总数量
			double DISPOSEQTY = inventoryset.getJpo(0).getDouble("DISPOSEQTY");// 待处理数量
			double FAULTQTY = inventoryset.getJpo(0).getDouble("FAULTQTY");// 故障数量
			double TESTINGQTY = inventoryset.getJpo(0).getDouble("TESTINGQTY");// 待检测数量
			double sqzyqty = 0;
			IJpoSet mrlinetransferset = MroServer.getMroServer().getSysJpoSet(
					"mrlinetransfer");
			mrlinetransferset.setUserWhere("STOREROOM='" + location
					+ "' and itemnum='" + itemnum
					+ "' and transtype not in ('计划经理协调','退回申请人','下达计划','返修后发运','计划交付后发货')");
			mrlinetransferset.reset();
			// IJpoSet
			// mrlineset=MroServer.getMroServer().getSysJpoSet("mrline");
			// mrlineset.setUserWhere("itemnum='"+itemnum+"' and mrnum in (select mrnum from mrlinetransfer where STOREROOM='"+location+"' and itemnum='"+itemnum+"' and transtype in ('"+ItemUtil.TRANSTYPE_NBXT+"','"+ItemUtil.TRANSTYPE_ZXKDB+"','"+ItemUtil.TRANSTYPE_XCDB+"'))");
			// mrlineset.reset();
			if (!mrlinetransferset.isEmpty()) {
				/* 配件申请中所有申请已发出的数量 */
				double sumsendqty = mrlinetransferset.sum("sendqty");
				/* 配件申请中所有申请的数量 */
				double sumsqqty = 0.00;
				for (int i = 0; i < mrlinetransferset.count(); i++) {
					IJpo mrlinetransfer = mrlinetransferset.getJpo(i);
					sumsqqty += mrlinetransfer.getDouble("transferqty");
				}
				double newsqzyqty = sumsqqty - sumsendqty;
				if(newsqzyqty<0){
					sqzyqty=0;
				}else{
					sqzyqty=newsqzyqty;
				}
			}
			double fcztqty = 0;
			IJpoSet transferlineset = MroServer.getMroServer().getJpoSet(
					"transferline",
					MroServer.getMroServer().getSystemUserServer());
			transferlineset
					.setUserWhere("itemnum='"
							+ itemnum
							+ "'  and ISSUESTOREROOM='"
							+ location
							+ "' and status='未接收' and transfernum in (select transfernum from transfer where status='在途' and type in ('SX','ZXD'))");
			transferlineset.reset();
			if (transferlineset.count() > 0) {
				fcztqty = transferlineset.sum("ORDERQTY");
			}
			kyqty = curbal - DISPOSEQTY - FAULTQTY - TESTINGQTY - sqzyqty
					- fcztqty;
		} else {
			kyqty = 0;
		}

		return kyqty;

	}
}
