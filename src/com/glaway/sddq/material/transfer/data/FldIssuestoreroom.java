package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 调拨单ISSUESTOREROOM（发出库房）字段类
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-8]
 * @since [GlawayMro4.0/调拨单]
 */
public class FldIssuestoreroom extends JpoField {

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
	 * 装箱单根据移动类型过滤库房
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		String type = this.getJpo().getString("TYPE");
		String TRANSFERMOVETYPE = this.getJpo().getString("TRANSFERMOVETYPE");// 装箱单移动类型
		String mrnum = this.getJpo().getString("mrnum");
		setListObject("LOCATIONS");
		if (type.equals("JKD")) {// 缴库单接收库房//---2018-08-07guan
			String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL ='现场站点库' and erploc='1020' and LOCATIONTYPE='常规' ";
			Sql=Sql+"and status='正常'";
			setListWhere(Sql);
		}
		if (type.equals("ZXD")) {// 接收库房//---2018-08-07guan
			if (TRANSFERMOVETYPE.isEmpty()) {
				throw new MroException("transfer", "notransfermovetype");
			} else {
				if (mrnum.isEmpty()) {
					if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到现场")) {
						String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL='中心库'";
						Sql=Sql+"and status='正常'";
						setListWhere(Sql);
					}
					if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到现场")) {
						String loginid = getUserServer().getUserInfo()
								.getLoginID();
						loginid = loginid.toUpperCase();
						IJpoSet PERSONDEPTMAPset = MroServer.getMroServer()
								.getJpoSet(
										"PERSONDEPTMAP",
										MroServer.getMroServer()
												.getSystemUserServer());
						PERSONDEPTMAPset.setQueryWhere("personid='" + loginid
								+ "'");
						if (!PERSONDEPTMAPset.isEmpty()) {
							String deptnum = PERSONDEPTMAPset.getJpo(0)
									.getString("deptnum");
							if (!deptnum.equalsIgnoreCase("")) {
								String Sql = "keeper='"
										+ loginid
										+ "' and location not in (select location from locations where ISSAPSTORE=1) "
										+ "and STOREROOMLEVEL in('现场库','区域库','现场站点库') and LOCATIONTYPE='常规' "
										+ "or LOCSITE in(select deptnum from PERSONDEPTMAP where personid='"
										+ loginid
										+ "') "
										+ "and location not in (select location from locations where ISSAPSTORE=1) and "
										+ "STOREROOMLEVEL in('现场库','区域库','现场站点库') and LOCATIONTYPE='常规' ";
								Sql=Sql+"and status='正常'";
								setListWhere(Sql);
							} else {
								throw new MroException("transfer", "site");
							}

						}
					}
					if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到中心")) {
						String Sxtype = this.getJpo().getString("sxtype");
						String loginid = getUserServer().getUserInfo()
								.getLoginID();
						loginid = loginid.toUpperCase();
						IJpoSet PERSONDEPTMAPset = MroServer.getMroServer()
								.getJpoSet(
										"PERSONDEPTMAP",
										MroServer.getMroServer()
												.getSystemUserServer());
						PERSONDEPTMAPset.setQueryWhere("personid='" + loginid
								+ "'");
						if (!PERSONDEPTMAPset.isEmpty()) {
							String deptnum = PERSONDEPTMAPset.getJpo(0)
									.getString("deptnum");
							if (Sxtype.equalsIgnoreCase("GZ")) {
								if (!deptnum.equalsIgnoreCase("")) {
									String Sql = "keeper='"
											+ loginid
											+ "' and location not in (select location from locations where ISSAPSTORE=1) "
											+ "and STOREROOMLEVEL in('现场库','区域库','现场站点库') and erploc='1020' and LOCATIONTYPE='维修' "
											+ "or LOCSITE in(select deptnum from PERSONDEPTMAP where personid='"
											+ loginid
											+ "') "
											+ "and location not in (select location from locations where ISSAPSTORE=1) and "
											+ "STOREROOMLEVEL in('现场库','区域库','现场站点库') and erploc='1020' and LOCATIONTYPE='维修' "
											+ "or keeper='"
											+ loginid
											+ "' and location not in (select location from locations where ISSAPSTORE=1) "
											+ "and STOREROOMLEVEL in('现场库','区域库','现场站点库') and erploc='其他待处理库' and LOCATIONTYPE='常规' "
											+ "or LOCSITE in(select deptnum from PERSONDEPTMAP where personid='"
											+ loginid
											+ "') "
											+ "and location not in (select location from locations where ISSAPSTORE=1) and "
											+ "STOREROOMLEVEL in('现场库','区域库','现场站点库') and erploc='其他待处理库' and LOCATIONTYPE='常规' ";
									Sql=Sql+"and status='正常'";
									setListWhere(Sql);
								} else {
									throw new MroException("transfer", "site");
								}
							}
							if (Sxtype.equalsIgnoreCase("YXX")) {
								if (!deptnum.equalsIgnoreCase("")) {
									String Sql = "keeper='"
											+ loginid
											+ "' and location not in (select location from locations where ISSAPSTORE=1) "
											+ "and STOREROOMLEVEL in('现场库','区域库','现场站点库') and erploc in ('1020','其他待处理库') and LOCATIONTYPE='常规' "
											+ "or LOCSITE in(select deptnum from PERSONDEPTMAP where personid='"
											+ loginid
											+ "') "
											+ "and location not in (select location from locations where ISSAPSTORE=1) and "
											+ "STOREROOMLEVEL in('现场库','区域库','现场站点库') and erploc in ('1020','其他待处理库') and LOCATIONTYPE='常规' ";
									Sql=Sql+"and status='正常'";
									setListWhere(Sql);
								} else {
									throw new MroException("transfer", "site");
								}
							}

						}
					}
					if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
						String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('中心库')";
						Sql=Sql+"and status='正常'";
						setListWhere(Sql);
					}
				} else {
					if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到现场")) {
						String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL='中心库' and location in (select storeroom from mrlinetransfer where mrnum='"
								+ mrnum + "')";
						Sql=Sql+"and status='正常'";
						setListWhere(Sql);
					}
					if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到现场")) {
						String loginid = getUserServer().getUserInfo()
								.getLoginID();
						loginid = loginid.toUpperCase();
						IJpoSet PERSONDEPTMAPset = MroServer.getMroServer()
								.getJpoSet(
										"PERSONDEPTMAP",
										MroServer.getMroServer()
												.getSystemUserServer());
						PERSONDEPTMAPset.setQueryWhere("personid='" + loginid
								+ "'");
						if (!PERSONDEPTMAPset.isEmpty()) {
							String deptnum = PERSONDEPTMAPset.getJpo(0)
									.getString("deptnum");
							if (!deptnum.equalsIgnoreCase("")) {
								String Sql = "LOCSITE in(select deptnum from PERSONDEPTMAP where personid='"
										+ loginid
										+ "') and location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('现场库','区域库','现场站点库') and location in (select storeroom from mrlinetransfer where mrnum='"
										+ mrnum + "')";
								Sql=Sql+"and status='正常'";
								setListWhere(Sql);
							} else {
								throw new MroException("transfer", "site");
							}

						}

					}
					if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到中心")) {
						String loginid = getUserServer().getUserInfo()
								.getLoginID();
						loginid = loginid.toUpperCase();
						IJpoSet PERSONDEPTMAPset = MroServer.getMroServer()
								.getJpoSet(
										"PERSONDEPTMAP",
										MroServer.getMroServer()
												.getSystemUserServer());
						PERSONDEPTMAPset.setQueryWhere("personid='" + loginid
								+ "'");
						if (!PERSONDEPTMAPset.isEmpty()) {
							String deptnum = PERSONDEPTMAPset.getJpo(0)
									.getString("deptnum");
							if (!deptnum.equalsIgnoreCase("")) {
								String Sql = "LOCSITE in (select deptnum from PERSONDEPTMAP where personid='"
										+ loginid
										+ "') and location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('现场库','区域库','现场站点库') and location in (select storeroom from mrlinetransfer where mrnum='"
										+ mrnum + "')";
								Sql=Sql+"and status='正常'";
								setListWhere(Sql);
							} else {
								throw new MroException("transfer", "site");
							}

						}
					}
					if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
						String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('中心库')";
						Sql=Sql+"and status='正常'";
						setListWhere(Sql);
					}
				}
			}
		}
		if (type.equalsIgnoreCase("GZZXD")) {
			if (TRANSFERMOVETYPE.isEmpty()) {
				throw new MroException("transfer", "notransfermovetype");
			} else {
				if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
					String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('中心库') and erploc in ('改造物料库','其他待处理库')";
					Sql=Sql+"and status='正常'";
					setListWhere(Sql);
				}
				if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到现场")) {
					String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('中心库') and erploc in ('改造物料库','其他待处理库')";
					Sql=Sql+"and status='正常'";
					setListWhere(Sql);
				}
				if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到中心")) {
					String loginid = getUserServer().getUserInfo().getLoginID();
					loginid = loginid.toUpperCase();
					IJpoSet PERSONDEPTMAPset = MroServer.getMroServer()
							.getJpoSet(
									"PERSONDEPTMAP",
									MroServer.getMroServer()
											.getSystemUserServer());
					PERSONDEPTMAPset
							.setQueryWhere("personid='" + loginid + "'");
					if (!PERSONDEPTMAPset.isEmpty()) {
						String deptnum = PERSONDEPTMAPset.getJpo(0).getString(
								"deptnum");
						if (!deptnum.equalsIgnoreCase("")) {
							String Sql = "keeper='"
									+ loginid
									+ "' and location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('现场库','区域库','现场站点库') and erploc in ('改造物料库','其他待处理库') or LOCSITE in (select deptnum from PERSONDEPTMAP where personid='"
									+ loginid
									+ "') and location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('现场库','区域库','现场站点库') and erploc in ('改造物料库','其他待处理库')";
							Sql=Sql+"and status='正常'";
							setListWhere(Sql);
						} else {
							String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('现场库','区域库','现场站点库') and erploc in ('改造物料库','其他待处理库')";
							Sql=Sql+"and status='正常'";
							setListWhere(Sql);
						}

					}
				}
			}

		}

		/**
		 * type=SX 是送修单
		 * 
		 */
		if (StringUtil.isEqualIgnoreCase(type, "SX")) {
			String listSql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in ('中心库','物流库') ";
			String sxtype = this.getJpo().getString("SXTYPE");
			if (StringUtil.isStrNotEmpty(sxtype)) {
				if (StringUtil.isEqualIgnoreCase(sxtype, "GAIZ")) {// 改造物料升级
					listSql = listSql + " and  erploc in ('改造物料库','1020') ";
				} else if (StringUtil.isEqualIgnoreCase(sxtype, "KH")) {// 客户产品维修
					listSql = listSql + " and erploc='其他待处理库' ";
				} else if (StringUtil.isEqualIgnoreCase(sxtype, "YXX")) {// 有效性检测
					listSql = "location in ('Y1087','QT1080')";
				} else if (StringUtil.isEqualIgnoreCase(sxtype, "DZX")) {// 大中修
					listSql = listSql;
				} else {
					listSql = "location in ('Y1087','QT1080')";
				}
			}
			listSql=listSql+"and status='正常'";
			setListWhere(listSql);
		}
		if (StringUtil.isEqualIgnoreCase(type, "JKD")) {
			String listSql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in ('中心库','现场库','现场站点库') and erploc='1020'";
			listSql=listSql+"and status='正常'";
			setListWhere(listSql);
		}

		return super.getList();
	}

	/**
	 * 根据选择的值控制接收库房字段只读必填以及默认值，并关联赋值库管员信息
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String type = this.getJpo().getString("TYPE");
		String MRNUM = this.getJpo().getString("MRNUM");
		if (!type.equalsIgnoreCase("JKD")) {
			if (MRNUM.isEmpty()) {
				this.getJpo().setFieldFlag("RECEIVESTOREROOM",
						GWConstant.S_READONLY, false);
				this.getJpo().setFieldFlag("RECEIVEADDRESS",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("RECEIVESTOREROOM",
						GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("RECEIVEADDRESS",
						GWConstant.S_REQUIRED, true);
			}
		}
		IJpoSet jpoSet = this.getJpo().getJpoSet("$LOCATIONS", "LOCATIONS",
				"LOCATION='" + this.getJpo().getString("ISSUESTOREROOM") + "'");
		if (!jpoSet.isEmpty()) {
			IJpo jpo = jpoSet.getJpo(0);
			this.getJpo().setValue("KEEPER", jpo.getString("KEEPER"));
		}
		/* 选择库房代入默认地址 */
		IJpoSet locaddressSet = this.getJpo().getJpoSet(
				"$LOCADDRESS",
				"locaddress",
				"LOCATION='" + this.getJpo().getString("ISSUESTOREROOM")
						+ "' and isaddress='是'");
		if (!locaddressSet.isEmpty()) {
			IJpo locaddress = locaddressSet.getJpo(0);
			this.getJpo().setValue("ISSUEADDRESS",
					locaddress.getString("address"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
		// String type = this.getJpo().getString("TYPE");
		if (type.equalsIgnoreCase("ZXD")) {
			String TRANSFERMOVETYPE = this.getJpo().getString(
					"TRANSFERMOVETYPE");// 装箱单移动类型
			if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到中心")) {
				String Erploc = this.getJpo().getJpoSet("ISSUESTOREROOM")
						.getJpo().getString("erploc");
				if (Erploc.equalsIgnoreCase("1020")) {
					this.getJpo().setValue("RECEIVESTOREROOM", "Y1087");
				}
				if (Erploc.equalsIgnoreCase("改造物料库")) {
					this.getJpo().setValue("RECEIVESTOREROOM", "GZ1080");
				}
				if (Erploc.equalsIgnoreCase("其他待处理库")) {
					this.getJpo().setValue("RECEIVESTOREROOM", "QT1080");
				}
			}
			if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
				if (!this.getJpo().getJpoSet("ISSUESTOREROOM").isEmpty()) {
					String createby = this.getJpo().getString("createby");
					String issuestoreroom = this.getJpo().getString(
							"issuestoreroom");
					IJpoSet oldset = MroServer.getMroServer().getJpoSet(
							"TRANSFER",
							MroServer.getMroServer().getSystemUserServer());
					oldset.setUserWhere("type='ZXD' and status!='未处理' and createby='"
							+ createby
							+ "' and issuestoreroom='"
							+ issuestoreroom + "'");
					oldset.reset();
					oldset.setOrderBy("transferid desc");
					if (!oldset.isEmpty()) {
						String PACKBY = oldset.getJpo(0).getString("PACKBY");
						String CHECKBY = oldset.getJpo(0).getString("CHECKBY");
						String SENDBY = oldset.getJpo(0).getString("SENDBY");
						this.getJpo().setValue("PACKBY", PACKBY,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setValue("CHECKBY", CHECKBY,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setValue("SENDBY", SENDBY,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}

					String LOCATIONTYPE = this.getJpo()
							.getJpoSet("ISSUESTOREROOM").getJpo()
							.getString("LOCATIONTYPE");
					if (LOCATIONTYPE.equalsIgnoreCase("维修")) {
						this.getJpo().setFieldFlag("sxtype",
								GWConstant.S_REQUIRED, false);
						this.getJpo().setValue("sxtype", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setFieldFlag("sxtype",
								GWConstant.S_READONLY, true);
						this.getJpo().setFieldFlag("RECEIVESTOREROOM",
								GWConstant.S_READONLY, false);
						this.getJpo().setValue("RECEIVESTOREROOM", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setFieldFlag("RECEIVEBY",
								GWConstant.S_READONLY, false);
						this.getJpo().setValue("RECEIVEBY", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setFieldFlag("RECEIVESTOREROOM",
								GWConstant.S_REQUIRED, true);
						this.getJpo().setFieldFlag("RECEIVEADDRESS",
								GWConstant.S_READONLY, false);
						this.getJpo().setValue("RECEIVEADDRESS", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setFieldFlag("RECEIVEADDRESS",
								GWConstant.S_REQUIRED, true);
					} else {
						this.getJpo().setFieldFlag("sxtype",
								GWConstant.S_REQUIRED, false);
						this.getJpo().setValue("sxtype", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setFieldFlag("sxtype",
								GWConstant.S_READONLY, true);
						this.getJpo().setFieldFlag("RECEIVESTOREROOM",
								GWConstant.S_READONLY, false);
						this.getJpo().setValue("RECEIVESTOREROOM", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setFieldFlag("RECEIVEBY",
								GWConstant.S_READONLY, false);
						this.getJpo().setValue("RECEIVEBY", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setFieldFlag("RECEIVESTOREROOM",
								GWConstant.S_REQUIRED, true);
						this.getJpo().setFieldFlag("RECEIVEADDRESS",
								GWConstant.S_READONLY, false);
						this.getJpo().setValue("RECEIVEADDRESS", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setFieldFlag("RECEIVEADDRESS",
								GWConstant.S_REQUIRED, true);

					}
				}
			}
			if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到现场")) {
				String createby = this.getJpo().getString("createby");
				String issuestoreroom = this.getJpo().getString(
						"issuestoreroom");
				IJpoSet oldset = MroServer.getMroServer().getJpoSet("TRANSFER",
						MroServer.getMroServer().getSystemUserServer());
				oldset.setUserWhere("type='ZXD' and status!='未处理' and createby='"
						+ createby
						+ "' and issuestoreroom='"
						+ issuestoreroom
						+ "'");
				oldset.reset();
				oldset.setOrderBy("transferid desc");
				if (!oldset.isEmpty()) {
					String PACKBY = oldset.getJpo(0).getString("PACKBY");
					String CHECKBY = oldset.getJpo(0).getString("CHECKBY");
					String SENDBY = oldset.getJpo(0).getString("SENDBY");
					this.getJpo().setValue("PACKBY", PACKBY,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					this.getJpo().setValue("CHECKBY", CHECKBY,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					this.getJpo().setValue("SENDBY", SENDBY,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
		}
		if (type.equalsIgnoreCase("SX")) {
			this.getJpo().setValue("REPAIRORG", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}

	}
}
