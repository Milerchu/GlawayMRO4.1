/*
 * 文 件 名:  Asset.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  实时台帐Jpo
 * 修 改 人:  hyhe
 * 修改时间:  2016-5-6
 */
package com.glaway.sddq.config.zcconfig.data;

import io.netty.util.internal.StringUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.DBManager;
import com.glaway.mro.jpo.HierarchicalJpo;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 实时台帐Jpo
 * 
 * @author hyhe
 * @version [MRO4.0, 2016-5-6]
 * @since [MRO4.0/模块版本]
 */
public class Asset extends HierarchicalJpo implements IStatusJpo {

	/**
	 * 默认序列化ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();
		if (!this.isNew()) {
			// this.setValue("XASSETNUM", this.getString("ASSETNUM"),
			// GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			// this.setFieldFlag("XASSETNUM", GWConstant.S_READONLY, true);
			this.setFieldFlag("CARNO", GWConstant.S_READONLY, true);
			this.setFieldFlag("CMODEL", GWConstant.S_READONLY, true);
			this.setFieldFlag("OWNERCUSTOMER", GWConstant.S_READONLY, true);
			this.setFieldFlag("OVERHAULER", GWConstant.S_READONLY, true);
			if (!StringUtil.isNullOrEmpty(this.getAppName())
					&& ("SSCONFIG".equalsIgnoreCase(getAppName()))) {
				this.setFieldFlag("SQN", GWConstant.S_READONLY, true);
				this.setFieldFlag("ITEMNUM", GWConstant.S_READONLY, true);
			}
		}
		initTreeName();
		if (!StringUtil.isNullOrEmpty(this.getAppName())
				&& ("SSCONFIG".equalsIgnoreCase(getAppName())
						|| "ZCCONFIG".equalsIgnoreCase(getAppName()) || "CXCONFIG"
							.equalsIgnoreCase(getAppName()))) {
			// 初始化站点时,获取子级
			if (hasChild()) {
				setValue("HASCHILDREN", "1",
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			} else {
				setValue("HASCHILDREN", "0",
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			}
		}

		// getPzwzd();
	}

	public boolean hasChild() throws MroException {

		String assetnum = this.getString("ASSETNUM");
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET",
				"PARENT='" + assetnum + "'");
		if (jposet != null && jposet.count() > 0) {
			return true;
		}

		return false;

	}

	/**
	 * 初始化树结构显示信息
	 * 
	 * @throws MroException
	 *             [参数说明]
	 */
	public void initTreeName() throws MroException {

		if (!StringUtil.isNullOrEmpty(this.getAppName())
				&& "ZCCONFIG".equals(this.getAppName())) {
			if (StringUtils.isEmpty(this.getString("sqn"))) {
				this.getField("sqn").setReadonly(false);
			} else {
				this.getField("sqn").setReadonly(true);
			}
			if (this.getField("ASSETLEVEL").getValue().equals("ASSET")) {
				String modeldesc = this.getField("MODELS.MODELDESC").getValue();// 描述
				String carno = this.getField("CARNO").getValue();// 描述
				this.getField("SHOWTREENAME").setValue(
						modeldesc + "-" + carno + "号车",
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			} else if (this.getField("ASSETLEVEL").getValue().equals("CAR")) {
				String desc = this.getField("carriagenum").getValue();// 描述
				this.getField("SHOWTREENAME").setValue(desc,
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			} else {
				String locdesc = this.getField("locdesc").getValue();// 车号
				String desc = this.getField("DESCRIPTION").getValue();// 车号
				String loc = this.getField("RNUM").getValue();// 位号
				if (loc == null) {
					loc = "";
				}
				if (StringUtil.isNullOrEmpty(locdesc)) {
					if (StringUtil.isNullOrEmpty(desc)) {
						String itemdesc = this.getField("ITEM.DESCRIPTION")
								.getValue();
						this.getField("SHOWTREENAME").setValue(
								loc + "=" + itemdesc,
								GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
					} else {
						this.getField("SHOWTREENAME").setValue(
								loc + "=" + desc,
								GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
					}
				} else {
					this.getField("SHOWTREENAME").setValue(loc + "=" + locdesc,
							GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
				}
			}
		}
	}

	/**
	 * @Description 获取配置完整度
	 * @throws MroException
	 */
	public void getPzwzd() throws MroException {

		if (!StringUtil.isNullOrEmpty(this.getAppName())
				&& "ZCCONFIG".equals(this.getAppName())) {
			if (this.getField("ASSETLEVEL").getValue().equals("ASSET")) {
				String ancestor = this.getString("assetnum");
				// 获取所有子集（不包括车厢节点，assetlevel = 'SYSTEM'）
				IJpoSet allJpoSet = MroServer.getMroServer()
						.getSysJpoSet(
								"ASSET",
								"ANCESTOR='" + ancestor
										+ "' and assetlevel = 'SYSTEM'");
				if (allJpoSet != null && allJpoSet.count() > 0) {
					// 获取序列号为空的节点
					IJpoSet sqnIsNullJpoSet = MroServer
							.getMroServer()
							.getSysJpoSet(
									"ASSET",
									"ANCESTOR='"
											+ ancestor
											+ "' and assetlevel = 'SYSTEM' and sqn is null");
					if (sqnIsNullJpoSet != null && sqnIsNullJpoSet.count() > 0) {
						int xncount = 0;
						// 如果存在序列号不为空的数据，则判断不为空的数据中有多少是虚拟件,排除掉
						for (int index = 0; index < sqnIsNullJpoSet.count(); index++) {
							IJpo jpo = sqnIsNullJpoSet.getJpo(index);
							if (ItemUtil.getItem(jpo.getString("itemnum")) != null
									&& ItemUtil.getItem(
											jpo.getString("itemnum"))
											.getBoolean("ISIV")) {
								xncount = xncount + 1;
							}
						}
						NumberFormat numformat = NumberFormat.getInstance();
						numformat.setMaximumFractionDigits(2);
						if (xncount > 0) {
							int allcount = allJpoSet.count() - xncount;
							int sqnisnullCount = sqnIsNullJpoSet.count()
									- xncount;
							if (sqnisnullCount > 0) {
								String count = numformat
										.format(((float) (allcount - sqnisnullCount) / (float) allcount) * 100);
								this.getField("PZWZ").setValue(count + "%",
										GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
							} else {
								this.getField("PZWZ").setValue("100%",
										GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
							}
						} else {
							String count = numformat
									.format(((float) (allJpoSet.count() - sqnIsNullJpoSet
											.count()) / (float) allJpoSet
											.count()) * 100);
							this.getField("PZWZ").setValue(count + "%",
									GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
						}
					} else {
						this.getField("PZWZ").setValue("100%",
								GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
					}
				} else {
					this.getField("PZWZ").setValue("0%",
							GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
				}
			}
		}
	}

	@Override
	public void add() throws MroException {
		if ("SSCONFIG".equalsIgnoreCase(getAppName())
				|| "ZCCONFIG".equalsIgnoreCase(getAppName())
				|| "CXCONFIG".equalsIgnoreCase(getAppName())) {
			super.add();
			setValue("LANGCODE", this.getUserInfo().getDefaultLang());
			if (this.getParent() != null && this.getParent() instanceof Asset) {
				String assetnum = getChildAssetNum();
				setValue("ASSETNUM", assetnum);

				if (("ASSET").equals(this.getParent().getString("ASSETLEVEL"))
						&& ("ZCCONFIG").equalsIgnoreCase(getAppName())) {
					setValue("ASSETLEVEL", "CAR",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				} else {
					setValue("ASSETLEVEL", "SYSTEM",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}

				setValue("ANCESTOR", getParent().getString("ANCESTOR"));
				setValue("LOCSITE", getParent().getString("LOCSITE"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//
				setValue("PARENT", getParent().getString("ASSETNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				if ("SSCONFIG".equalsIgnoreCase(getAppName())) {
					setValue("TYPE", "0",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 在库存中
				} else if ("ZCCONFIG".equalsIgnoreCase(getAppName())) {
					setValue("TYPE", "2",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 在车上
				} else {
					setValue("TYPE", "3",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 在车下
				}
				// this.getParent().setValue("HASCHILDREN", "1",
				// GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				getParent().setValue("ISOPEN", true,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				getParent().setFieldFlag("ASSETNUM", GWConstant.S_READONLY,
						true);
			} else {
				setValue("ASSETLEVEL", "ASSET");
				setValue("ANCESTOR", getString("ASSETNUM"));
				if ("SSCONFIG".equalsIgnoreCase(getAppName())) {
					setValue("TYPE", "0",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 在库存中-新品
					setValue("STATUS", SddqConstant.NO_UP_CAR_STATUS,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 在库存中-新品
					setValue("FROMSOURCE", SddqConstant.FROMSOURCE_ZC,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 在库存中-新品
				} else if ("ZCCONFIG".equalsIgnoreCase(getAppName())) {
					setValue("TYPE", "2",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 在车上
				} else {
					setValue("TYPE", "3",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 在库存中-下车品
				}
			}
			setValue("NUM", getString("ASSETNUM"));
			// createAssetAncestor(this, getString("ASSETNUM"), 0,
			// getString("SITEID"), getString("ORGID"));
		}

	}

	/**
	 * 创建台帐结构树
	 * 
	 * @param asset
	 * @param assetNum
	 * @param level
	 * @param siteid
	 * @param orgid
	 * @throws MroException
	 *             [参数说明]
	 */
	public void createAssetAncestor(Asset asset, String assetNum, int level,
			String siteid, String orgid) throws MroException {
		Asset owner = (Asset) asset.getParent();
		IJpoSet ancestorset = getJpoSet("ASSETANCESTOR");
		IJpo ancestor = ancestorset.addJpo();
		ancestor.setValue("ASSETNUM", assetNum);
		ancestor.setValue("HIERARCHYLEVELS", level);
		ancestor.setValue("SITEID", siteid);
		ancestor.setValue("ORGID", orgid);
		ancestor.setValue("ANCESTOR", asset.getString("ASSETNUM"));
		if (!asset.isNull("PARENT")) {
			createAssetAncestor(owner, assetNum, level + 1, siteid, orgid);
		}
	}

	/**
	 * 获取最新的 装备序列号
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 */
	public String getChildAssetNum() throws MroException {
		String parantNum = getParent().getString("ASSETNUM");
		int maxNum = this.getJpoSet().count();
		if (!this.getJpoSet().isEmpty() && this.getJpoSet().count() > 1) {
			for (int index = 0; index < getJpoSet().count(); index++) {
				String assetNum = getJpoSet().getJpo(index).getString(
						"ASSETNUM");
				if (assetNum != null) {
					if (assetNum.lastIndexOf(".") != -1) {
						String num = assetNum.substring(assetNum
								.lastIndexOf(".") + 1);
						if (num != null && Integer.valueOf(num) >= maxNum) {
							maxNum = Integer.valueOf(num) + 1;
						}
					}
				}
			}
		}
		String assetnum = parantNum + "." + maxNum;
		while (true) {
			IJpoSet allAssetSet = getUserServer().getJpoSet("ASSET",
					"ASSETNUM='" + assetnum + "'");
			if (!allAssetSet.isEmpty()) {
				assetnum = parantNum + "." + (maxNum + 1);
			} else {
				break;
			}
		}
		return assetnum;
	}

	/**
	 * 删除判断
	 * @throws MroException
	 */
	@Override
	public void delete(long flag) throws MroException {
		// IJpoSet childrenJpoSet = getJpoSet("CHILDREN");
		// if (childrenJpoSet.isEmpty())
		// {
		// super.delete(flag);
		// }
		// else
		// {
		// throw new MroException("assetcs", "hasChildren",
		// new String[] {getString("MODEL"), getString("DESCRIPTION")});
		// }
		if(getBoolean("ISLOCKED")){
			throw new MroException("已锁定，无法删除！");
		}
		super.delete(flag);
	}

	/**
	 * 保存之前，判断是否删除状态，删除结构树关系
	 * 
	 * @throws MroException
	 */
	@Override
	public void beforeSave() throws MroException {
		if (this.toBeDeleted()) {
			if (!StringUtil.isNullOrEmpty(this.getString("ASSETLEVEL"))
					&& !this.getString("ASSETLEVEL").equals("ASSET")) {
				deleteAssetAncestor(this);
			}
		}
	}

	@Override
	public IJpo changeStatus(String status, String newStatus, String memo,
			long flag) throws MroException {
		IJpo jpo = super.changeStatus(status, newStatus, memo, flag);
		jpo.setValue("ASSETNUM", getString("ASSETNUM"));
		return jpo;
	}

	@Override
	public IJpoSet getStatusHistorySet() throws MroException {
		// 默认状态历史表的关系名称是表名后缀STATUS
		String relationName = getName().toUpperCase() + "STATUS";
		if (getJpoSet().getTableInfo().getRelation(relationName) == null) {
			return null;
		}
		return getJpoSet(relationName);
	}

	/**
	 * 站点变更
	 * 
	 * @param newLocation
	 * @param memo
	 * @throws MroException
	 *             [参数说明]
	 */
	public void changeLoc(String newLocation, String memo) throws MroException {
		IJpoSet jpoSet = getJpoSet("ASSETCHANGECUSTHIS");
		IJpo jpo = jpoSet.addJpo();
		jpo.setValue("LOCSITE", newLocation,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		jpo.setValue("OLDLOCATION", getString("LOCSITE"),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		jpo.setValue("ASSETNUM", getString("ASSETNUM"),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		jpo.setValue("MEMO", memo, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		jpo.setValue("CHANGEBY", getUserInfo().getPersonId(),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		jpo.setValue("CHANGEDATE", MroServer.getMroServer().getDate(),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		setValue("LOCSITE", newLocation,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
	}

	/**
	 * 删除ASSETCSTREE表的结构关系
	 * 
	 * @param asset
	 * @throws MroException
	 *             [参数说明]
	 */
	private void deleteAssetAncestor(Asset asset) throws MroException {

		String assetnum = asset.getString("ASSETNUM");
		Connection conn = getUserServer().getConnection();
		String delePartSql = "delete from ASSETPART where assetnum in (select assetnum from asset start with assetnum ='"
				+ assetnum + "' connect by parent = PRIOR assetnum)";
		String deleSql = "delete from ASSET where assetnum in (select assetnum from asset start with assetnum ='"
				+ assetnum + "' connect by parent = PRIOR assetnum)";
		try {
			DBManager.getDBManager().executeBatch(conn,
					new String[] { delePartSql, deleSql });
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 计算两个日期之间相隔多少年
	 * 
	 * @param from
	 *            起始日期
	 * @param to
	 *            终止日期
	 * @return
	 */
	@SuppressWarnings("unused")
	private int calculateLeftYear(Date from, Date to) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(from);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(to);
		if (c1.after(c2) || c1.equals(c2)) {
			return 0;
		}
		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			c1.add(Calendar.YEAR, i);
			if (c1.after(c2)) {
				return i - 1;
			}
			if (c1.equals(c2)) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * 配置动态变更
	 * 
	 * @param before
	 * @param after
	 * @param type
	 * @param memo
	 * @param flag
	 *            [参数说明]
	 */
	public void changeOwer(String before, String after, String type,
			String memo, long flag) {
		try {
			IJpoSet jposet = this.getJpoSet("$AssetOwerTemp", "ASSETOWNER",
					"1=2");
			IJpo jpo = jposet.addJpo();
			jpo.setValue("CHANGEDTYPE", type,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			jpo.setValue("BEFOROWNER", before,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			jpo.setValue("AFTEROWNER", after,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			jpo.setValue("REASON", memo,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			jpo.setValue("ASSETNUM", this.getString("ASSETNUM"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		} catch (MroException e) {
			e.printStackTrace();
		}
	}
}
