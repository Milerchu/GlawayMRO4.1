package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <调拨单行物料编码字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldItemnum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		if (getJpo() != null
				&& !StringUtil.isStrEmpty(this.getJpo().getAppName()) && !this.getJpo().getAppName().equalsIgnoreCase("ERPIFACE")&& !this.getJpo().getAppName().equalsIgnoreCase("QTITEMREQ")) {
			String type = this.getJpo().getParent().getString("type");
			if (type.equalsIgnoreCase("ZXD")) {
				String TRANSFERMOVETYPE = this.getJpo().getParent()
						.getString("TRANSFERMOVETYPE");
				String SXTYPE = this.getJpo().getParent().getString("SXTYPE");
				if (TRANSFERMOVETYPE
						.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)
						&& SXTYPE.equalsIgnoreCase("GZ")) {
					String[] thisAttrs = { this.getFieldName() };
					String[] srcAttrs = { "QMSREPAIRINFOID" };
					setLookupMap(thisAttrs, srcAttrs);
				} else {
					String[] thisAttrs = { this.getFieldName() };
					String[] srcAttrs = { "ITEMNUM" };
					setLookupMap(thisAttrs, srcAttrs);
				}
			} else {
				String[] thisAttrs = { this.getFieldName() };
				String[] srcAttrs = { "ITEMNUM" };
				setLookupMap(thisAttrs, srcAttrs);
			}
		}
	}

	/**
	 * 根据移动类型，修造类别判断物料的选择方式和过滤条件
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		String type = this.getJpo().getParent().getString("type");
		IJpoSet list = super.getList();
		if (type.equalsIgnoreCase("ZXD")) {
			String TRANSFERMOVETYPE = this.getJpo().getParent()
					.getString("TRANSFERMOVETYPE");
			String SXTYPE = this.getJpo().getParent().getString("SXTYPE");
			String tasknum = this.getJpo().getString("tasknum");
			if (TRANSFERMOVETYPE
					.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)
					&& SXTYPE.equalsIgnoreCase("GZ")) {

				if (tasknum.isEmpty()) {
					throw new MroException("zxdtransfer", "notasknum");
				} else {
					setListObject("QMSREPAIRINFO");
					String ISSUESTOREROOM = this.getJpo().getString(
							"ISSUESTOREROOM");
					IJpoSet EXCHANGERECORDset = MroServer.getMroServer()
							.getJpoSet(
									"EXCHANGERECORD",
									MroServer.getMroServer()
											.getSystemUserServer());
					EXCHANGERECORDset.setUserWhere("failureordernum='"
							+ tasknum + "' and LOCATION='" + ISSUESTOREROOM
							+ "'");
					EXCHANGERECORDset.reset();
					IJpoSet JXTASKLOSSPARTset = MroServer.getMroServer()
							.getJpoSet(
									"JXTASKLOSSPART",
									MroServer.getMroServer()
											.getSystemUserServer());
					JXTASKLOSSPARTset.setUserWhere("jxtasknum='" + tasknum
							+ "' and UNDERLOC='" + ISSUESTOREROOM + "'");
					JXTASKLOSSPARTset.reset();
					String listSql = "";
					if (EXCHANGERECORDset.isEmpty()) {
						if (!JXTASKLOSSPARTset.isEmpty()) {
							String jrownos = null;
							for (int i = 0; i < JXTASKLOSSPARTset.count(); i++) {
								if (JXTASKLOSSPARTset.getJpo(i) == null) {
									continue;
								}
								String jrowno = JXTASKLOSSPARTset.getJpo(i)
										.getString("rowno");
								if (!"".equals(jrowno)) {
									if (jrownos == null)
										jrownos = "'"
												+ StringUtil
														.getSafeSqlStr(jrowno)
												+ "'";
									else
										jrownos = jrownos
												+ ",'"
												+ StringUtil
														.getSafeSqlStr(jrowno)
												+ "'";
								}
							}
							listSql = "wordernum='"
									+ tasknum
									+ "' and QMSREPAIRNUM not in (select qmsnum from transferline where qmsnum is not null and tasknum='"
									+ tasknum + "')" + "and rowno in ("
									+ jrownos + ")";
						}
					} else {
						if (JXTASKLOSSPARTset.isEmpty()) {
							String erownos = null;
							for (int i = 0; i < EXCHANGERECORDset.count(); i++) {
								if (EXCHANGERECORDset.getJpo(i) == null) {
									continue;
								}
								String erowno = EXCHANGERECORDset.getJpo(i)
										.getString("rowno");
								if (!"".equals(erowno)) {
									if (erownos == null)
										erownos = "'"
												+ StringUtil
														.getSafeSqlStr(erowno)
												+ "'";
									else
										erownos = erownos
												+ ",'"
												+ StringUtil
														.getSafeSqlStr(erowno)
												+ "'";
								}
							}
							listSql = "wordernum='"
									+ tasknum
									+ "' and QMSREPAIRNUM not in (select qmsnum from transferline where qmsnum is not null and tasknum='"
									+ tasknum + "')" + "and rowno in ("
									+ erownos + ")";
						} else {
							String jrownos = null;
							String erownos = null;
							String srownos = null;
							for (int i = 0; i < JXTASKLOSSPARTset.count(); i++) {
								if (JXTASKLOSSPARTset.getJpo(i) == null) {
									continue;
								}
								String jrowno = JXTASKLOSSPARTset.getJpo(i)
										.getString("rowno");
								if (!"".equals(jrowno)) {
									if (jrownos == null)
										jrownos = "'"
												+ StringUtil
														.getSafeSqlStr(jrowno)
												+ "'";
									else
										jrownos = jrownos
												+ ",'"
												+ StringUtil
														.getSafeSqlStr(jrowno)
												+ "'";
								}
							}
							for (int i = 0; i < EXCHANGERECORDset.count(); i++) {
								if (EXCHANGERECORDset.getJpo(i) == null) {
									continue;
								}
								String erowno = EXCHANGERECORDset.getJpo(i)
										.getString("rowno");
								if (!"".equals(erowno)) {
									if (erownos == null)
										erownos = "'"
												+ StringUtil
														.getSafeSqlStr(erowno)
												+ "'";
									else
										erownos = erownos
												+ ",'"
												+ StringUtil
														.getSafeSqlStr(erowno)
												+ "'";
								}
							}
							srownos = jrownos + ", " + erownos;
							listSql = "wordernum='"
									+ tasknum
									+ "' and QMSREPAIRNUM not in (select qmsnum from transferline where qmsnum is not null and tasknum='"
									+ tasknum + "')" + "and rowno in ("
									+ srownos + ")";

						}
					}
					IJpoSet thisSet = this.getJpo().getJpoSet();
					String qmsnums = null;
					if (!thisSet.isEmpty()) {
						for (int i = 0; i < thisSet.count(); i++) {
							if (thisSet.getJpo(i) == null) {
								continue;
							}
							String qmsnum = thisSet.getJpo(i).getString(
									"qmsnum");
							if (!"".equals(qmsnum)) {
								if (qmsnums == null)
									qmsnums = "'"
											+ StringUtil.getSafeSqlStr(qmsnum)
											+ "'";
								else
									qmsnums = qmsnums + ",'"
											+ StringUtil.getSafeSqlStr(qmsnum)
											+ "'";
							}
						}
					}
					if (qmsnums != null) {
						listSql = listSql + "and  QMSREPAIRNUM not in ("
								+ qmsnums + ")";
					}

					if (!listSql.equals("")) {
						setListWhere(listSql);
					}
					list = super.getList();
				}

			}
			if (TRANSFERMOVETYPE
					.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)
					&& SXTYPE.equalsIgnoreCase("YXX")) {
				setListObject("SYS_INVENTORY");
				String ISSUESTOREROOM = this.getJpo().getString(
						"ISSUESTOREROOM");
				String listSql = "";
				IJpoSet rootwhereset = MroServer.getMroServer().getJpoSet(
						"SYS_INVENTORY",
						MroServer.getMroServer().getSystemUserServer());
				rootwhereset.setUserWhere("LOCATION='" + ISSUESTOREROOM
						+ "' and locqty>0");
				rootwhereset.reset();
				String stritemnum = null;
				for (int i = 0; i < rootwhereset.count(); i++) {
					if (rootwhereset.getJpo(i) == null) {
						continue;
					}
					String itemnum = rootwhereset.getJpo(i)
							.getString("itemnum");
					double locqty = rootwhereset.getJpo(i).getDouble("locqty");
					IJpoSet thisjposet = this.getJpo().getJpoSet();
					double thissumqty = 0.00;
					String thistransfelineid = null;
					for (int j = 0; j < thisjposet.count(); j++) {
						if (thisjposet.getJpo(j) == null) {
							continue;
						}
						String thisitemnum = thisjposet.getJpo(j).getString(
								"itemnum");
						String transferlineid = thisjposet.getJpo(j).getString(
								"transferlineid");
						if (thisitemnum.equalsIgnoreCase(itemnum)) {
							thissumqty += thisjposet.getJpo(j).getDouble(
									"orderqty");
							if (StringUtil.isStrEmpty(thistransfelineid))
								thistransfelineid = "'"
										+ StringUtil
												.getSafeSqlStr(transferlineid)
										+ "'";
							else
								thistransfelineid = thistransfelineid
										+ ",'"
										+ StringUtil
												.getSafeSqlStr(transferlineid)
										+ "'";
						}
					}
					if (thistransfelineid == null) {
						IJpoSet savewhereset = MroServer.getMroServer()
								.getJpoSet(
										"transferline",
										MroServer.getMroServer()
												.getSystemUserServer());
						savewhereset
								.setUserWhere("transfernum in (select transfernum from transfer where type='ZXD') and "
										+ "status not in ('已接收') and sxtype='YXX' and ISSUESTOREROOM='"
										+ ISSUESTOREROOM
										+ "' and itemnum='"
										+ itemnum + "'");
						savewhereset.reset();
						double sumsaveqty = 0.00;
						if (!savewhereset.isEmpty()) {
							sumsaveqty = savewhereset.sum("orderqty");
						} else {
							sumsaveqty = 0.00;
						}

						if (sumsaveqty + thissumqty >= locqty) {
							if (StringUtil.isStrEmpty(stritemnum))
								stritemnum = "'"
										+ StringUtil.getSafeSqlStr(itemnum)
										+ "'";
							else
								stritemnum = stritemnum + ",'"
										+ StringUtil.getSafeSqlStr(itemnum)
										+ "'";
						}
					} else {
						IJpoSet savewhereset = MroServer.getMroServer()
								.getJpoSet(
										"transferline",
										MroServer.getMroServer()
												.getSystemUserServer());
						savewhereset
								.setUserWhere("transfernum in (select transfernum from transfer where type='ZXD') and "
										+ "status not in ('已接收') and sxtype='YXX' and ISSUESTOREROOM='"
										+ ISSUESTOREROOM
										+ "' and itemnum='"
										+ itemnum
										+ "'"
										+ " and transferlineid not in ("
										+ thistransfelineid + ")");
						savewhereset.reset();
						double sumsaveqty = 0.00;
						if (!savewhereset.isEmpty()) {
							sumsaveqty = savewhereset.sum("orderqty");
						}

						if (sumsaveqty + thissumqty >= locqty) {
							if (StringUtil.isStrEmpty(stritemnum))
								stritemnum = "'"
										+ StringUtil.getSafeSqlStr(itemnum)
										+ "'";
							else
								stritemnum = stritemnum + ",'"
										+ StringUtil.getSafeSqlStr(itemnum)
										+ "'";
						}
					}

				}
				if (stritemnum == null) {
					listSql = " LOCATION='" + ISSUESTOREROOM + "' and locqty>0";
				} else {
					listSql = " LOCATION='" + ISSUESTOREROOM
							+ "' and locqty>0 and itemnum not in ("
							+ stritemnum + ")";
				}
				if (!listSql.equals("")) {
					setListWhere(listSql);
				}
				list = super.getList();
			} else {
				if (TRANSFERMOVETYPE
						.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)) {
					//-------------2019-4-22-------------------------//
					String erploc=this.getJpo().getJpoSet("ISSUESTOREROOM").getJpo().getString("erploc");
					if(erploc.equalsIgnoreCase("1030")){
						setListObject("SYS_INVENTORY");
						String ISSUESTOREROOM = this.getJpo().getString(
								"ISSUESTOREROOM");
						String listSql = " LOCATION='" + ISSUESTOREROOM
								+ "' and kyqty>0";
						if (!listSql.equals("")) {
							setListWhere(listSql);
						}
						list = super.getList();
					}else{
					setListObject("SYS_INVENTORY");
					String ISSUESTOREROOM = this.getJpo().getString(
							"ISSUESTOREROOM");
					String listSql = "";
					IJpoSet rootwhereset = MroServer.getMroServer().getJpoSet(
							"SYS_INVENTORY",
							MroServer.getMroServer().getSystemUserServer());
					rootwhereset.setUserWhere("LOCATION='" + ISSUESTOREROOM
							+ "' and locqty>0");
					rootwhereset.reset();
					String stritemnum = null;
					for (int i = 0; i < rootwhereset.count(); i++) {
						if (rootwhereset.getJpo(i) == null) {
							continue;
						}
						String itemnum = rootwhereset.getJpo(i).getString(
								"itemnum");
						double locqty = rootwhereset.getJpo(i).getDouble(
								"locqty");
						IJpoSet thisjposet = this.getJpo().getJpoSet();
						double thissumqty = 0.00;
						String thistransfelineid = null;
						for (int j = 0; j < thisjposet.count(); j++) {
							if (thisjposet.getJpo(j) == null) {
								continue;
							}
							String thisitemnum = thisjposet.getJpo(j)
									.getString("itemnum");
							String transferlineid = thisjposet.getJpo(j)
									.getString("transferlineid");
							if (thisitemnum.equalsIgnoreCase(itemnum)) {
								thissumqty += thisjposet.getJpo(j).getDouble(
										"orderqty");
								if (StringUtil.isStrEmpty(thistransfelineid))
									thistransfelineid = "'"
											+ StringUtil
													.getSafeSqlStr(transferlineid)
											+ "'";
								else
									thistransfelineid = thistransfelineid
											+ ",'"
											+ StringUtil
													.getSafeSqlStr(transferlineid)
											+ "'";
							}
						}
						if (thistransfelineid == null) {
							IJpoSet savewhereset = MroServer.getMroServer()
									.getJpoSet(
											"transferline",
											MroServer.getMroServer()
													.getSystemUserServer());
							savewhereset
									.setUserWhere("transfernum in (select transfernum from transfer where type='ZXD') and "
											+ "status not in ('已接收')  and ISSUESTOREROOM='"
											+ ISSUESTOREROOM
											+ "' and itemnum='" + itemnum + "'");
							savewhereset.reset();
							double sumsaveqty = 0.00;
							if (!savewhereset.isEmpty()) {
								sumsaveqty = savewhereset.sum("orderqty");
							} else {
								sumsaveqty = 0.00;
							}

							if (sumsaveqty + thissumqty >= locqty) {
								if (StringUtil.isStrEmpty(stritemnum))
									stritemnum = "'"
											+ StringUtil.getSafeSqlStr(itemnum)
											+ "'";
								else
									stritemnum = stritemnum + ",'"
											+ StringUtil.getSafeSqlStr(itemnum)
											+ "'";
							}
						} else {
							IJpoSet savewhereset = MroServer.getMroServer()
									.getJpoSet(
											"transferline",
											MroServer.getMroServer()
													.getSystemUserServer());
							savewhereset
									.setUserWhere("transfernum in (select transfernum from transfer where type='ZXD') and "
											+ "status not in ('已接收')  and ISSUESTOREROOM='"
											+ ISSUESTOREROOM
											+ "' and itemnum='"
											+ itemnum
											+ "'"
											+ " and transferlineid not in ("
											+ thistransfelineid + ")");
							savewhereset.reset();
							double sumsaveqty = 0.00;
							if (!savewhereset.isEmpty()) {
								sumsaveqty = savewhereset.sum("orderqty");
							} else {
								sumsaveqty = 0.00;
							}

							if (sumsaveqty + thissumqty >= locqty) {
								if (StringUtil.isStrEmpty(stritemnum))
									stritemnum = "'"
											+ StringUtil.getSafeSqlStr(itemnum)
											+ "'";
								else
									stritemnum = stritemnum + ",'"
											+ StringUtil.getSafeSqlStr(itemnum)
											+ "'";
							}
						}

					}
					if (stritemnum == null) {
						listSql = " LOCATION='" + ISSUESTOREROOM
								+ "' and locqty>0";
					} else {
						listSql = " LOCATION='" + ISSUESTOREROOM
								+ "' and locqty>0 and itemnum not in ("
								+ stritemnum + ")";
					}
					if (!listSql.equals("")) {
						setListWhere(listSql);
					}
					list = super.getList();
					}
				}
				if (TRANSFERMOVETYPE
						.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)
						|| TRANSFERMOVETYPE
								.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
//					//-------------2019-4-22-------------------------//
//					String erploc=this.getJpo().getJpoSet("ISSUESTOREROOM").getJpo().getString("erploc");
//					if(erploc.equalsIgnoreCase("1030")){
//						setListObject("SYS_INVENTORY");
//						String ISSUESTOREROOM = this.getJpo().getString(
//								"ISSUESTOREROOM");
//						String listSql = " LOCATION='" + ISSUESTOREROOM
//								+ "' and kyqty>0";
//						if (!listSql.equals("")) {
//							setListWhere(listSql);
//						}
//						list = super.getList();
//					}else{
//					setListObject("SYS_INVENTORY");
//					String ISSUESTOREROOM = this.getJpo().getString(
//							"ISSUESTOREROOM");
//					String listSql = "";
//					IJpoSet rootwhereset = MroServer.getMroServer().getJpoSet(
//							"SYS_INVENTORY",
//							MroServer.getMroServer().getSystemUserServer());
//					rootwhereset.setUserWhere("LOCATION='" + ISSUESTOREROOM
//							+ "' and locqty>0");
//					rootwhereset.reset();
//					String stritemnum = null;
//					for (int i = 0; i < rootwhereset.count(); i++) {
//						if (rootwhereset.getJpo(i) == null) {
//							continue;
//						}
//						String itemnum = rootwhereset.getJpo(i).getString(
//								"itemnum");
//						double locqty = rootwhereset.getJpo(i).getDouble(
//								"locqty");
//						IJpoSet thisjposet = this.getJpo().getJpoSet();
//						double thissumqty = 0.00;
//						String thistransfelineid = null;
//						for (int j = 0; j < thisjposet.count(); j++) {
//							if (thisjposet.getJpo(j) == null) {
//								continue;
//							}
//							String thisitemnum = thisjposet.getJpo(j)
//									.getString("itemnum");
//							String transferlineid = thisjposet.getJpo(j)
//									.getString("transferlineid");
//							if (thisitemnum.equalsIgnoreCase(itemnum)) {
//								thissumqty += thisjposet.getJpo(j).getDouble(
//										"orderqty");
//								if (StringUtil.isStrEmpty(thistransfelineid))
//									thistransfelineid = "'"
//											+ StringUtil
//													.getSafeSqlStr(transferlineid)
//											+ "'";
//								else
//									thistransfelineid = thistransfelineid
//											+ ",'"
//											+ StringUtil
//													.getSafeSqlStr(transferlineid)
//											+ "'";
//							}
//						}
//						if (thistransfelineid == null) {
//							IJpoSet savewhereset = MroServer.getMroServer()
//									.getJpoSet(
//											"transferline",
//											MroServer.getMroServer()
//													.getSystemUserServer());
//							savewhereset
//									.setUserWhere("transfernum in (select transfernum from transfer where type='ZXD') and "
//											+ "status not in ('已接收') and ISSUESTOREROOM='"
//											+ ISSUESTOREROOM
//											+ "' and itemnum='" + itemnum + "'");
//							savewhereset.reset();
//							double sumsaveqty = 0.00;
//							if (!savewhereset.isEmpty()) {
//								sumsaveqty = savewhereset.sum("orderqty");
//							} else {
//								sumsaveqty = 0.00;
//							}
//
//							IJpoSet savejkdlineset = MroServer.getMroServer()
//									.getJpoSet(
//											"transferline",
//											MroServer.getMroServer()
//													.getSystemUserServer());
//							savejkdlineset
//									.setUserWhere("transfernum in (select transfernum from transfer where type='JKD') and "
//											+ "status in ('已接收') and ISSUESTOREROOM='"
//											+ ISSUESTOREROOM
//											+ "' and itemnum='"
//											+ itemnum
//											+ "' "
//											+ "and transferlineid not in (select jkdlineid from transferline where jkdlineid is not null)");
//							savejkdlineset.reset();
//							double sumsavejkdqty = 0.00;
//							if (!savejkdlineset.isEmpty()) {
//								sumsavejkdqty = savejkdlineset.sum("orderqty");
//							} else {
//								sumsavejkdqty = 0.00;
//							}
//
//							if (sumsaveqty + thissumqty + sumsavejkdqty >= locqty) {
//								if (StringUtil.isStrEmpty(stritemnum))
//									stritemnum = "'"
//											+ StringUtil.getSafeSqlStr(itemnum)
//											+ "'";
//								else
//									stritemnum = stritemnum + ",'"
//											+ StringUtil.getSafeSqlStr(itemnum)
//											+ "'";
//							}
//						} else {
//							IJpoSet savewhereset = MroServer.getMroServer()
//									.getJpoSet(
//											"transferline",
//											MroServer.getMroServer()
//													.getSystemUserServer());
//							savewhereset
//									.setUserWhere("transfernum in (select transfernum from transfer where type='ZXD') and "
//											+ "status not in ('已接收') and ISSUESTOREROOM='"
//											+ ISSUESTOREROOM
//											+ "' and itemnum='"
//											+ itemnum
//											+ "'"
//											+ " and transferlineid not in ("
//											+ thistransfelineid + ")");
//							savewhereset.reset();
//							double sumsaveqty = 0.00;
//							if (!savewhereset.isEmpty()) {
//								sumsaveqty = savewhereset.sum("orderqty");
//							} else {
//								sumsaveqty = 0.00;
//							}
//							IJpoSet savejkdlineset = MroServer.getMroServer()
//									.getJpoSet(
//											"transferline",
//											MroServer.getMroServer()
//													.getSystemUserServer());
//							savejkdlineset
//									.setUserWhere("transfernum in (select transfernum from transfer where type='JKD') and "
//											+ "status in ('已接收') and ISSUESTOREROOM='"
//											+ ISSUESTOREROOM
//											+ "' and itemnum='"
//											+ itemnum
//											+ "' "
//											+ "and transferlineid not in (select jkdlineid from transferline where jkdlineid is not null)");
//							savejkdlineset.reset();
//							double sumsavejkdqty = 0.00;
//							if (!savejkdlineset.isEmpty()) {
//								sumsavejkdqty = savejkdlineset.sum("orderqty");
//							} else {
//								sumsavejkdqty = 0.00;
//							}
//
//							if (sumsaveqty + thissumqty + sumsavejkdqty >= locqty) {
//								if (StringUtil.isStrEmpty(stritemnum))
//									stritemnum = "'"
//											+ StringUtil.getSafeSqlStr(itemnum)
//											+ "'";
//								else
//									stritemnum = stritemnum + ",'"
//											+ StringUtil.getSafeSqlStr(itemnum)
//											+ "'";
//							}
//						}
//
//					}
//					if (stritemnum == null) {
//						listSql = " LOCATION='" + ISSUESTOREROOM
//								+ "' and locqty>0";
//					} else {
//						listSql = " LOCATION='" + ISSUESTOREROOM
//								+ "' and locqty>0 and itemnum not in ("
//								+ stritemnum + ")";
//					}
//					if (!listSql.equals("")) {
//						setListWhere(listSql);
//					}
//					list = super.getList();
//					}
					setListObject("SYS_INVENTORY");
					String ISSUESTOREROOM = this.getJpo().getString(
							"ISSUESTOREROOM");
					String listSql = " LOCATION='" + ISSUESTOREROOM
							+ "' and kyqty>0";
					if (!listSql.equals("")) {
						setListWhere(listSql);
					}
					list = super.getList();
				}
			}
		} else if (type.equalsIgnoreCase("JKD")) {
			setListObject("SYS_INVENTORY");
			String RECEIVESTOREROOM = this.getJpo().getString(
					"RECEIVESTOREROOM");
			String listSql = "";
			listSql = " LOCATION='" + RECEIVESTOREROOM + "' and kyqty>0";
			if (!listSql.equals("")) {
				setListWhere(listSql);
			}
			list = super.getList();
		} else {
			setListObject("SYS_INVENTORY");
			String ISSUESTOREROOM = this.getJpo().getString("ISSUESTOREROOM");
			String listSql = "";
			listSql = " LOCATION='" + ISSUESTOREROOM + "' and kyqty>0";
			if (!listSql.equals("")) {
				setListWhere(listSql);
			}
			list = super.getList();
		}
		return list;
	}

	/**
	 * 触发赋值相关数据信息
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String TRANSFERTYPE = this.getJpo().getParent()
				.getString("TRANSFERTYPE");
		String sxtype = this.getJpo().getParent().getString("sxtype");
		String TRANSFERMOVETYPE = this.getJpo().getParent()
				.getString("TRANSFERMOVETYPE");
		String itemnum = this.getValue();

		if (!TRANSFERTYPE.equalsIgnoreCase("工具")) {
			if (!this.getJpo().getParent().getString("type")
					.equalsIgnoreCase("JKD")) {
				/** 肖林宝修改送修单自动赋值QMS单号以及故障描述，故障后果 **/
				if (!this.getJpo().getParent().getString("type")
						.equalsIgnoreCase("SX")) {
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)
							&& sxtype.equalsIgnoreCase("GZ")) {
						String qmsrepairinfoid = this.getValue();

						IJpoSet qmsInfoSet = MroServer.getMroServer()
								.getJpoSet(
										"QMSREPAIRINFO",
										MroServer.getMroServer()
												.getSystemUserServer());
						qmsInfoSet.setQueryWhere("qmsrepairinfoid='"
								+ qmsrepairinfoid + "' ");
						qmsInfoSet.reset();
						IJpo qmsInfo = qmsInfoSet.getJpo(0);
						if (qmsInfo == null) {
							return;
						}
						String itemnums = qmsInfo.getString("ITEMNUM");
						this.setValue(itemnums,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

						String tasknum = this.getJpo().getString("tasknum");

						String QMSNUM = qmsInfo.getString("QMSREPAIRNUM");
						String SCALELINENUM = qmsInfo.getString("ROWNO");
						this.getJpo().setValue("qmsnum", QMSNUM,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setValue("SCALELINENUM", SCALELINENUM,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

						String REPAIRMENTSN = qmsInfo.getString("REPAIRMENTSN");
						if (!REPAIRMENTSN.isEmpty()) {
							String type = ItemUtil.getItemInfo(itemnums);// --关联取物料是否批次号管理属性
							if (ItemUtil.SQN_ITEM.equals(type)) {
								this.getJpo()
										.setValue(
												"sqn",
												REPAIRMENTSN,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								IJpoSet EXCHANGERECORDSet = MroServer
										.getMroServer().getJpoSet(
												"EXCHANGERECORD",
												MroServer.getMroServer()
														.getSystemUserServer());
								EXCHANGERECORDSet
										.setQueryWhere("FAILUREORDERNUM='"
												+ tasknum + "' and sqn='"
												+ REPAIRMENTSN
												+ "' and ROWNO='"
												+ SCALELINENUM + "'");
								EXCHANGERECORDSet.reset();
								if (EXCHANGERECORDSet.getJpo(0).getBoolean(
										"ISAPPNOTICE")) {
									this.getJpo()
											.setValue(
													"ISAPPNOTICE",
													EXCHANGERECORDSet
															.getJpo(0)
															.getBoolean(
																	"ISAPPNOTICE"),
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
								this.getJpo()
										.setValue(
												"assetnum",
												EXCHANGERECORDSet.getJpo(0)
														.getString("assetnum"),
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								if (EXCHANGERECORDSet.getJpo(0).getBoolean(
										"ISTECHAANALYZE")) {
									this.getJpo()
											.setValue(
													"ISJSFX",
													"是",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									this.getJpo()
											.setValue(
													"IMPORTLEVEL",
													"紧急",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								} else {
									this.getJpo()
											.setValue(
													"ISJSFX",
													"否",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									this.getJpo()
											.setValue(
													"IMPORTLEVEL",
													"普通",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							} else {
								this.getJpo()
										.setValue(
												"LOTNUM",
												REPAIRMENTSN,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								IJpoSet JXTASKLOSSPARTSet = MroServer
										.getMroServer().getJpoSet(
												"JXTASKLOSSPART",
												MroServer.getMroServer()
														.getSystemUserServer());
								JXTASKLOSSPARTSet.setQueryWhere("jxtasknum='"
										+ tasknum + "' and DOWNLOTNUM='"
										+ REPAIRMENTSN + "' and ROWNO='"
										+ SCALELINENUM + "'");
								JXTASKLOSSPARTSet.reset();
								this.getJpo()
										.setValue(
												"ISAPPNOTICE",
												JXTASKLOSSPARTSet.getJpo(0)
														.getString("ISNOTICE"),
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								if (JXTASKLOSSPARTSet.getJpo(0).getBoolean(
										"ISTECHAANALYZE")) {
									this.getJpo()
											.setValue(
													"ISJSFX",
													"是",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									this.getJpo()
											.setValue(
													"IMPORTLEVEL",
													"紧急",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								} else {
									this.getJpo()
											.setValue(
													"ISJSFX",
													"否",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									this.getJpo()
											.setValue(
													"IMPORTLEVEL",
													"普通",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							}
						} else {
							IJpoSet JXTASKLOSSPARTSet = MroServer
									.getMroServer().getJpoSet(
											"JXTASKLOSSPART",
											MroServer.getMroServer()
													.getSystemUserServer());
							JXTASKLOSSPARTSet.setQueryWhere("jxtasknum='"
									+ tasknum + "' and ROWNO='" + SCALELINENUM
									+ "'");
							JXTASKLOSSPARTSet.reset();
							if (JXTASKLOSSPARTSet.getJpo(0).getBoolean(
									"ISNOTICE")) {
								this.getJpo()
										.setValue(
												"ISAPPNOTICE",
												JXTASKLOSSPARTSet.getJpo(0)
														.getBoolean("ISNOTICE"),
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}

							if (JXTASKLOSSPARTSet.getJpo(0).getBoolean(
									"ISTECHAANALYZE")) {
								this.getJpo()
										.setValue(
												"ISJSFX",
												"是",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								this.getJpo()
										.setValue(
												"IMPORTLEVEL",
												"紧急",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							} else {
								this.getJpo()
										.setValue(
												"ISJSFX",
												"否",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								this.getJpo()
										.setValue(
												"IMPORTLEVEL",
												"普通",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}

						IJpoSet FAILURELIBSet = MroServer.getMroServer()
								.getJpoSet(
										"FAILURELIB",
										MroServer.getMroServer()
												.getSystemUserServer());
						FAILURELIBSet.setQueryWhere("FAILUREORDERNUM='"
								+ tasknum + "'");
						this.getJpo().setValue("FAILUREDESC",
								FAILURELIBSet.getJpo(0).getString("FAULTDESC"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setValue(
								"FAILURECONS",
								FAILURELIBSet.getJpo(0)
										.getString("FAULTCONSEQ"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setValue("orderqty", 1,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.getJpo().setFieldFlag("orderqty",
								GWConstant.S_READONLY, true);

					}

					IJpoSet itemSet = MroServer.getMroServer().getJpoSet(
							"sys_item",
							MroServer.getMroServer().getSystemUserServer());
					itemSet.setQueryWhere("itemnum='" + itemnum + "'");
					String SXTYPE = this.getJpo().getParent()
							.getString("SXTYPE");
					if (!itemSet.isEmpty()) {
						String type = ItemUtil.getItemInfo(itemnum);
						if (ItemUtil.SQN_ITEM.equals(type)) {
							if (TRANSFERMOVETYPE
									.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)
									|| TRANSFERMOVETYPE
											.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)) {
								this.getJpo().setFieldFlag("sqn",
										GWConstant.S_READONLY, false);
								this.getJpo()
										.setValue(
												"sqn",
												"",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								this.getJpo().setFieldFlag("sqn",
										GWConstant.S_REQUIRED, true);
							} else {
								this.getJpo().setFieldFlag("sqn",
										GWConstant.S_READONLY, false);
								this.getJpo()
										.setValue(
												"sqn",
												"",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								this.getJpo().setFieldFlag("sqn",
										GWConstant.S_REQUIRED, false);
								this.getJpo()
										.setValue(
												"ORDERQTY",
												1,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								this.getJpo().setFieldFlag("ORDERQTY",
										GWConstant.S_REQUIRED, false);
								this.getJpo().setFieldFlag("ORDERQTY",
										GWConstant.S_READONLY, true);
							}

							this.getJpo().setFieldFlag("lotnum",
									GWConstant.S_REQUIRED, false);
							this.getJpo().setValue("lotnum", "",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.getJpo().setFieldFlag("lotnum",
									GWConstant.S_READONLY, true);
						} else {
							if (ItemUtil.LOT_I_ITEM.equals(type)) {
								if (TRANSFERMOVETYPE
										.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
									this.getJpo().setFieldFlag("lotnum",
											GWConstant.S_READONLY, false);
									this.getJpo()
											.setValue(
													"lotnum",
													"",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									this.getJpo().setFieldFlag("lotnum",
											GWConstant.S_REQUIRED, true);
								}
								if (TRANSFERMOVETYPE
										.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)) {
									this.getJpo().setFieldFlag("lotnum",
											GWConstant.S_READONLY, false);
									this.getJpo()
											.setValue(
													"lotnum",
													"",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									this.getJpo().setFieldFlag("lotnum",
											GWConstant.S_REQUIRED, true);
									this.getJpo().setFieldFlag("ORDERQTY",
											GWConstant.S_READONLY, false);
									this.getJpo().setFieldFlag("ORDERQTY",
											GWConstant.S_REQUIRED, true);
								}
								if (TRANSFERMOVETYPE
										.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
									this.getJpo().setFieldFlag("lotnum",
											GWConstant.S_READONLY, false);
									this.getJpo()
											.setValue(
													"lotnum",
													"",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									this.getJpo().setFieldFlag("lotnum",
											GWConstant.S_REQUIRED, false);
									this.getJpo().setFieldFlag("ORDERQTY",
											GWConstant.S_READONLY, false);
									this.getJpo().setFieldFlag("ORDERQTY",
											GWConstant.S_REQUIRED, true);
								} else {
									this.getJpo().setFieldFlag("lotnum",
											GWConstant.S_READONLY, false);
									this.getJpo()
											.setValue(
													"lotnum",
													"",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									this.getJpo().setFieldFlag("lotnum",
											GWConstant.S_REQUIRED, false);
									this.getJpo().setFieldFlag("ORDERQTY",
											GWConstant.S_READONLY, false);
									this.getJpo().setFieldFlag("ORDERQTY",
											GWConstant.S_REQUIRED, true);
								}
								this.getJpo().setFieldFlag("sqn",
										GWConstant.S_REQUIRED, false);
								this.getJpo()
										.setValue(
												"sqn",
												"",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								this.getJpo().setFieldFlag("sqn",
										GWConstant.S_READONLY, true);
							} else {

								this.getJpo().setFieldFlag("sqn",
										GWConstant.S_REQUIRED, false);
								this.getJpo()
										.setValue(
												"sqn",
												"",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								this.getJpo().setFieldFlag("sqn",
										GWConstant.S_READONLY, true);
								this.getJpo().setFieldFlag("lotnum",
										GWConstant.S_REQUIRED, false);
								this.getJpo()
										.setValue(
												"lotnum",
												"",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								this.getJpo().setFieldFlag("lotnum",
										GWConstant.S_READONLY, true);
								if (TRANSFERMOVETYPE
										.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)) {
									this.getJpo().setFieldFlag("ORDERQTY",
											GWConstant.S_READONLY, false);
									this.getJpo().setFieldFlag("ORDERQTY",
											GWConstant.S_REQUIRED, true);
								}
								if (TRANSFERMOVETYPE
										.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
									this.getJpo().setFieldFlag("ORDERQTY",
											GWConstant.S_READONLY, false);
									this.getJpo().setFieldFlag("ORDERQTY",
											GWConstant.S_REQUIRED, true);
								}
								if (TRANSFERMOVETYPE
										.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
									this.getJpo().setFieldFlag("ORDERQTY",
											GWConstant.S_READONLY, false);
									this.getJpo()
									.setValue(
											"ORDERQTY",
											"",
											GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									this.getJpo().setFieldFlag("ORDERQTY",
											GWConstant.S_REQUIRED, true);
								}

							}
						}
					}
				} else {
					if (this.getJpo().getParent().getString("type")
							.equalsIgnoreCase("SX")) {
						String type = ItemUtil.getItemInfo(itemnum);
						if (ItemUtil.SQN_ITEM.equals(type)) {
							this.getJpo().setFieldFlag("sqn",
									GWConstant.S_READONLY, false);
							this.getJpo().setValue("sqn", "",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.getJpo().setFieldFlag("sqn",
									GWConstant.S_REQUIRED, true);
							this.getJpo().setValue("ORDERQTY", 1,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.getJpo().setFieldFlag("ORDERQTY",
									GWConstant.S_REQUIRED, false);
							this.getJpo().setFieldFlag("ORDERQTY",
									GWConstant.S_READONLY, true);
						} else {
							this.getJpo().setFieldFlag("sqn",
									GWConstant.S_REQUIRED, false);
							this.getJpo().setValue("sqn", "",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.getJpo().setFieldFlag("sqn",
									GWConstant.S_READONLY, true);
							this.getJpo().setValue("ORDERQTY", 1,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.getJpo().setFieldFlag("ORDERQTY",
									GWConstant.S_REQUIRED, false);
							this.getJpo().setFieldFlag("ORDERQTY",
									GWConstant.S_READONLY, true);
						}
					}
				}
			} else {
				String type = ItemUtil.getItemInfo(itemnum);
				if (ItemUtil.SQN_ITEM.equals(type)) {
					//-------------------
					double orderqty=1;
					String issuestoreroom=this.getJpo().getString("receivestoreroom");
					IJpoSet inventoryset = MroServer.getMroServer().getJpoSet("sys_inventory", MroServer.getMroServer().getSystemUserServer());
					inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+issuestoreroom+"'");
					double kyqty = inventoryset.getJpo(0).getDouble("kyqty");// 可用数量
					if(kyqty==0){
						throw new MroException("库存可用数量为0");
					}else if(kyqty>0){
						IJpoSet jkdtransferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
						jkdtransferlineset.setUserWhere("itemnum='"+ itemnum+ "' and receivestoreroom='"+ issuestoreroom+ "' and status='未接收' and transfernum in (select transfernum from transfer where status='未处理' and type='JKD')");
						jkdtransferlineset.reset();
						
						IJpoSet transferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
						transferlineset.setUserWhere("itemnum='"+ itemnum+ "' and ISSUESTOREROOM='"+ issuestoreroom+ "' and status='未接收' and transfernum in (select transfernum from transfer where status='未处理' and type in ('SX','ZXD'))");
						transferlineset.reset();
						if(jkdtransferlineset.isEmpty()){
							if(transferlineset.isEmpty()){
								double newqty=kyqty-orderqty;
								if(newqty<0){
									throw new MroException("数量大于库存可用数量");
								}
							}else{
								double sumtransferorderqty=transferlineset.sum("orderqty");
								double newqty=kyqty-(orderqty+sumtransferorderqty);
								if(newqty<0){
									throw new MroException("累计发货数量大于库存可用数量");
								}
							}
						}else{
							double sumjkdtransferorderqty=jkdtransferlineset.sum("orderqty");
							if(transferlineset.isEmpty()){
								double newqty=kyqty-(orderqty+sumjkdtransferorderqty);
								if(newqty<0){
									throw new MroException("累计发货数量大于库存可用数量");
								}
							}else{
								double sumtransferorderqty=transferlineset.sum("orderqty");
								double newqty=kyqty-(orderqty+sumtransferorderqty+sumjkdtransferorderqty);
								if(newqty<0){
									throw new MroException("累计发货数量大于库存可用数量");
								}
							}
						}
					}
					//-------------------
					this.getJpo().setFieldFlag("sqn", GWConstant.S_READONLY,
							false);
					this.getJpo().setValue("sqn", "",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					this.getJpo().setFieldFlag("sqn", GWConstant.S_REQUIRED,
							true);
					this.getJpo().setValue("ORDERQTY", 1,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					this.getJpo().setFieldFlag("ORDERQTY",
							GWConstant.S_REQUIRED, false);
					this.getJpo().setFieldFlag("ORDERQTY",
							GWConstant.S_READONLY, true);
				}
			}
		}
	}

}
