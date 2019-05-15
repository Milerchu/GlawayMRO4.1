package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 调拨单RECEIVESTOREROOM（接收）字段类
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-8]
 * @since [GlawayMro4.0/调拨单]
 */
public class FldReceivestoreroom extends JpoField {

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
		// String [] attr = {"RECEIVEBY","KEEPER"};

		super.init();
		String[] thisAttrs = { this.getFieldName(), "RECEIVEPHONE" };
		String[] srcAttrs = { "LOCATION", "PRIMARYPHONE" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 装箱单根据移动类型判断接收库房过滤
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("LOCATIONS");
		String listSql = "";
		/**
		 * 修改接收库房选择限制 1、1020 、1030、 改造、其他物资 库房之间不可以互相调用 2、改造装箱单中只可选择改造现场库
		 */
		String zxd_type = this.getJpo().getString("type");
		String TRANSFERMOVETYPE = this.getJpo().getString("TRANSFERMOVETYPE");// 装箱单移动类型
		if ("GZZXD".equals(zxd_type)) {
			String ISSUESTOREROOM = this.getJpo().getString("ISSUESTOREROOM");
			if (ISSUESTOREROOM.isEmpty()) {
				throw new MroException("transferline", "nostoreroom");
			} else {
				String erploc = this.getJpo().getJpoSet("ISSUESTOREROOM")
						.getJpo().getString("erploc");

				if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到现场")) {
					listSql = "ERPLOC='" + erploc + "' and STOREROOMLEVEL='"
							+ ItemUtil.STOREROOMLEVEL_XCK
							+ "' and LOCATIONTYPE='" + ItemUtil.LOCATIONTYPE_CG
							+ "' and ";
				}
				if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
					listSql = "ERPLOC='" + erploc + "' and STOREROOMLEVEL='"
							+ ItemUtil.STOREROOMLEVEL_ZXK + "' and ";
				}
				if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到中心")) {
					listSql = "ERPLOC='" + erploc + "' and STOREROOMLEVEL='"
							+ ItemUtil.STOREROOMLEVEL_ZXK + "' and ";
				}
			}

		} else if ("ZXD".equals(zxd_type)) {// 装箱单判断接收库房
			if (!this.getJpo().getString("ISSUESTOREROOM").isEmpty()) {
				String erploc = this.getJpo().getJpoSet("ISSUESTOREROOM")
						.getJpo().getString("erploc");

				if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到现场")) {
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_1020)) {
						listSql = "ERPLOC='"
								+ ItemUtil.ERPLOC_1020
								+ "' and STOREROOMLEVEL in('现场库','区域库','现场站点库') and LOCATIONTYPE='常规' and ";
					}
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_1030)) {
						listSql = "ERPLOC='"
								+ ItemUtil.ERPLOC_1030
								+ "' and STOREROOMLEVEL in('现场库','区域库','现场站点库') and ";
					}
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_QTGZ)) {
						listSql = "ERPLOC in ('"
								+ ItemUtil.ERPLOC_QTGZ
								+ "') and STOREROOMLEVEL in('现场库','区域库','现场站点库') and ";
					}
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_QTDCL)) {
						listSql = "ERPLOC in ('"
								+ ItemUtil.ERPLOC_QTDCL
								+ "') and STOREROOMLEVEL in('现场库','区域库','现场站点库') and ";
					}
				}
				if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到现场")) {
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_1020)) {
						listSql = "ERPLOC='"
								+ ItemUtil.ERPLOC_1020
								+ "' and STOREROOMLEVEL in('现场库','区域库','现场站点库') and LOCATIONTYPE='常规' and ";
					}
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_1030)) {
						listSql = "ERPLOC='"
								+ ItemUtil.ERPLOC_1030
								+ "' and STOREROOMLEVEL in('现场库','区域库','现场站点库') and LOCATIONTYPE='常规' and ";
					}
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_QTDCL)) {
						listSql = "ERPLOC in ('"
								+ ItemUtil.ERPLOC_QTDCL
								+ "') and STOREROOMLEVEL in('现场库','区域库','现场站点库') and LOCATIONTYPE='常规' and ";
					}
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_QTGZ)) {
						listSql = "ERPLOC in ('"
								+ ItemUtil.ERPLOC_QTGZ
								+ "') and STOREROOMLEVEL in('现场库','区域库','现场站点库') and LOCATIONTYPE='常规' and ";
					}
				}
				if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到中心")) {
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_1020)) {
						listSql = "location='Y1087' and ";
					}
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_QTDCL)) {
						listSql = "location='QT1080' and ";
					}
				}
				if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_1020)) {
						listSql = "ERPLOC='" + ItemUtil.ERPLOC_1020
								+ "' and STOREROOMLEVEL='中心库' and ";
					}
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_1030)) {
						listSql = "ERPLOC='" + ItemUtil.ERPLOC_1030
								+ "' and STOREROOMLEVEL='中心库' and ";
					}
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_QTDCL)) {
						listSql = "ERPLOC in ('" + ItemUtil.ERPLOC_QTDCL
								+ "') and STOREROOMLEVEL='中心库' and ";
					}
					if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_QTGZ)) {
						listSql = "ERPLOC in ('" + ItemUtil.ERPLOC_QTGZ
								+ "') and STOREROOMLEVEL='中心库' and ";
					}
				}
			} else {
				if (!TRANSFERMOVETYPE.equalsIgnoreCase("中心到现场")) {
					throw new MroException("transferline", "nostoreroom");
				} else {
					listSql = "STOREROOMLEVEL in('现场库','区域库','现场站点库') and LOCATIONTYPE='常规' and ";
				}
			}
		} else if ("JKD".equals(zxd_type)) {
			listSql = "location in ('Y1088','Y1089','Y1090') and ";
		} else {
			IJpoSet issuesLocSet = this.getJpo().getJpoSet("ISSUESTOREROOM");
			if (!issuesLocSet.isEmpty()) {
				listSql = "STOREROOMLEVEL='中心库' and ";
			}
		}
		listSql = listSql
				+ " location not in (select location from locations where ISSAPSTORE=1) ";
		if (!listSql.equals("")) {
			listSql=listSql+"and status='正常'";
			setListWhere(listSql);
		}
		return super.getList();
	}

	/**
	 * 触发字段只读必填控制，赋值接收地址，接收人信息
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String keeper = this.getJpo().getJpoSet("RECEIVESTOREROOM").getJpo()
				.getString("keeper");
		String ADDRESS = this.getJpo().getJpoSet("RECEIVESTOREROOM").getJpo()
				.getString("ADDRESS");
		String PRIMARYPHONE = this.getJpo().getJpoSet("RECEIVESTOREROOM")
				.getJpo().getJpoSet("keeper").getJpo()
				.getString("PRIMARYPHONE");
		this.getJpo().setValue("endby", keeper);
		this.getJpo().setValue("RECEIVEPHONE", PRIMARYPHONE);
		this.getJpo().setValue("RECEIVEBY", keeper,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("RECEIVEADDRESS", ADDRESS,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		if (!this.getJpo().getString("type").equalsIgnoreCase("ZXD")) {
			if (!this
					.getJpo()
					.getJpoSet(
							"$LOCATIONS",
							"LOCATIONS",
							"LOCATION='"
									+ this.getJpo().getString(
											this.getFieldName()) + "'")
					.isEmpty())
				this.getJpo().setValue(
						"OPENBY",
						this.getJpo()
								.getJpoSet(
										"$LOCATIONS",
										"LOCATIONS",
										"LOCATION='"
												+ this.getJpo().getString(
														this.getFieldName())
												+ "'").getJpo(0)
								.getString("keeper"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		} else {
			String issuestoreroom = this.getJpo().getString("issuestoreroom");
			String receivestoreroom = this.getValue();
			if (!issuestoreroom.isEmpty() && !receivestoreroom.isEmpty()) {
				String TRANSFERMOVETYPE = this.getJpo().getString(
						"TRANSFERMOVETYPE");
				String issLOCATIONTYPE = this.getJpo()
						.getJpoSet("ISSUESTOREROOM").getJpo()
						.getString("LOCATIONTYPE");
				String erploc = this.getJpo().getJpoSet("ISSUESTOREROOM")
						.getJpo().getString("erploc");
				if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
					if (issuestoreroom.equalsIgnoreCase("Y1087")
							|| issuestoreroom.equalsIgnoreCase("QT1080")) {
						if (receivestoreroom.equalsIgnoreCase("Y1090")
								|| receivestoreroom.equalsIgnoreCase("QT1083")) {
							this.getJpo().setFieldFlag("SENDBY",
									GWConstant.S_READONLY, false);
							this.getJpo().setValue("SENDBY", "",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.getJpo().setFieldFlag("SENDBY",
									GWConstant.S_REQUIRED, true);
						} else {
							this.getJpo().setFieldFlag("SENDBY",
									GWConstant.S_REQUIRED, false);
							this.getJpo().setValue("SENDBY", "",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.getJpo().setFieldFlag("SENDBY",
									GWConstant.S_READONLY, true);
						}
					}
					if (issLOCATIONTYPE.equalsIgnoreCase("常规")) {
						if (erploc.equalsIgnoreCase("1020")
								&& receivestoreroom.equalsIgnoreCase("Y1087")) {
							this.getJpo().setFieldFlag("sxtype",
									GWConstant.S_READONLY, false);
							this.getJpo().setValue("sxtype", "",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.getJpo().setFieldFlag("sxtype",
									GWConstant.S_REQUIRED, true);
						} else if (erploc.equalsIgnoreCase("其他待处理库")
								&& receivestoreroom.equalsIgnoreCase("QT1081")) {
							this.getJpo().setFieldFlag("sxtype",
									GWConstant.S_READONLY, false);
							this.getJpo().setValue("sxtype", "",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.getJpo().setFieldFlag("sxtype",
									GWConstant.S_REQUIRED, true);
						} else {
							this.getJpo().setFieldFlag("sxtype",
									GWConstant.S_REQUIRED, false);
							this.getJpo().setValue("sxtype", "",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.getJpo().setFieldFlag("sxtype",
									GWConstant.S_READONLY, true);
						}
					} else {
						this.getJpo().setFieldFlag("sxtype",
								GWConstant.S_REQUIRED, false);
						this.getJpo().setValue("sxtype", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setFieldFlag("sxtype",
								GWConstant.S_READONLY, true);
					}
				}

			}
			IJpoSet oldset = MroServer.getMroServer().getJpoSet("TRANSFER",
					MroServer.getMroServer().getSystemUserServer());
			oldset.setUserWhere("type='ZXD' and status!='未处理' and receiveby='"
					+ keeper + "'");
			oldset.reset();
			oldset.setOrderBy("transferid desc");
			if (!oldset.isEmpty()) {
				String OPENBY = oldset.getJpo(0).getString("OPENBY");
				String RECEIVECHECKBY = oldset.getJpo(0).getString(
						"RECEIVECHECKBY");
				this.getJpo().setValue("OPENBY", OPENBY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVECHECKBY", RECEIVECHECKBY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}
		/* 选择库房代入默认地址 */
		IJpoSet locaddressSet = this.getJpo().getJpoSet(
				"$LOCADDRESS",
				"locaddress",
				"LOCATION='" + this.getJpo().getString("RECEIVESTOREROOM")
						+ "' and isaddress='是'");
		if (!locaddressSet.isEmpty()) {
			IJpo locaddress = locaddressSet.getJpo(0);
			this.getJpo().setValue("RECEIVEADDRESS",
					locaddress.getString("address"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}

	}
}
