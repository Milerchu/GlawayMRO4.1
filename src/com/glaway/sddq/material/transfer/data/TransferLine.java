package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 调拨单行Jpo
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-8]
 * @since [GlawayMro4.0/调拨单]
 */
public class TransferLine extends Jpo implements IJpo, FixedLoggers {
	private static final long serialVersionUID = 1L;

	/**
	 * 页面控制
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();

		if ("ZXTRANSFER".equalsIgnoreCase(getAppName())) {

			String personid = getParent().getUserInfo().getLoginID();
			personid = personid.toUpperCase();
			String CREATEBY = getParent().getString("CREATEBY");
			String TRANSFERMOVETYPE = getParent().getString("TRANSFERMOVETYPE");
			String SXTYPE = getParent().getString("SXTYPE");
			String ISSUESTOREROOM = getParent().getString("ISSUESTOREROOM");
			String RECEIVESTOREROOM = getParent().getString("RECEIVESTOREROOM");
			String status = getParent().getString("status");
			String ISMR = getParent().getString("ISMR");
			if (isNew()) {
				this.setValue("transfernum",
						getParent().getString("transfernum"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.setValue("PROJECTNUM",
						getParent().getString("PROJECTNUM"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				/* 20181010肖林宝修改 */
				if (TRANSFERMOVETYPE
						.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)) {/* 移动类型是现场到中心 */
					if (SXTYPE.equalsIgnoreCase("GZ")) {/* 修造类别是故障 */
						this.setFieldFlag("TASKNUM", GWConstant.S_REQUIRED,
								true);
					}
					if (SXTYPE.equalsIgnoreCase("YXX")) {/* 修造类别是故障 */
						this.setFieldFlag("TASKNUM", GWConstant.S_READONLY,
								true);
					}
					this.setFieldFlag("FAILUREDESC", GWConstant.S_REQUIRED,
							true);
					this.setFieldFlag("ISJSFX", GWConstant.S_REQUIRED, true);
					this.setFieldFlag("MODEL", GWConstant.S_REQUIRED, true);
					this.setFieldFlag("PROJECTNUM", GWConstant.S_REQUIRED, true);
					this.setValue("DEALTYPE", "退库",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					this.setFieldFlag("DEALTYPE", GWConstant.S_REQUIRED, true);
					this.setValue("ORDERQTY", 1,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					this.setFieldFlag("ORDERQTY", GWConstant.S_READONLY, true);
				}
				if (TRANSFERMOVETYPE
						.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {/* 移动类型是中心到中心 */
					if (!SXTYPE.isEmpty()) {
						this.setFieldFlag("TASKNUM", GWConstant.S_READONLY,
								true);
						this.setFieldFlag("FAILUREDESC", GWConstant.S_REQUIRED,
								true);
						this.setFieldFlag("ISJSFX", GWConstant.S_REQUIRED, true);
						this.setFieldFlag("MODEL", GWConstant.S_REQUIRED, true);
						this.setFieldFlag("PROJECTNUM", GWConstant.S_REQUIRED,
								true);
						this.setValue("DEALTYPE", "退库",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						this.setFieldFlag("DEALTYPE", GWConstant.S_REQUIRED,
								true);
					}
					this.setValue("ORDERQTY", 1,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					this.setFieldFlag("ORDERQTY", GWConstant.S_READONLY, true);
				}
				/* 20181010肖林宝修改 */
				/* 装箱单状态为未处理*************************** */
				if (status.equalsIgnoreCase("未处理")) {
					/* 移动类型为现场到现场*********************** */
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {/* 当前登陆人不是申请人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {/* 当前登陆人是申请人*********************** */
							String[] readonlystr = { "ISSUESTOREROOM",
									"ISSUEADDRESS", "SQN", "TASKNUM",
									"INBINNUM", "RETURNTYPE", "FAILURECONS" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag("ORDERQTY",
									GWConstant.S_REQUIRED, true);
						}
					}
					/* 移动类型为现场到中心*********************** */
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {/* 当前登陆人不是申请人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {/* 当前登陆人是申请人*********************** */
							String[] readonlystr = { "ISSUESTOREROOM",
									"ISSUEADDRESS", "SQN", "INBINNUM",
									"RETURNTYPE", "FAILURECONS" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setValue("ORDERQTY", 1,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.setFieldFlag("ORDERQTY",
									GWConstant.S_READONLY, true);
						}
					}
					/* 移动类型为中心到现场*********************** */
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {/* 当前登陆人不是申请人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {/* 当前登陆人是申请人*********************** */
							String[] readonlystr = { "TASKNUM", "INBINNUM",
									"RETURNTYPE", "FAILURECONS" };
							String[] requierdstr = { "ISSUESTOREROOM",
									"ISSUEADDRESS" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag(requierdstr,
									GWConstant.S_READONLY, false);
							this.setFieldFlag(requierdstr,
									GWConstant.S_REQUIRED, true);
							this.setFieldFlag("ORDERQTY",
									GWConstant.S_REQUIRED, true);
							String itemnum = this.getString("itemnum");
							if (!itemnum.isEmpty()) {
								String type = ItemUtil.getItemInfo(itemnum);
								if (ItemUtil.SQN_ITEM.equals(type)) {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, false);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, true);
								} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, false);
								} else {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, true);
								}
							} else {
								this.setFieldFlag("sqn", GWConstant.S_READONLY,
										true);
								this.setFieldFlag("lotnum",
										GWConstant.S_READONLY, true);
							}
						}
					}
					/* 移动类型为中心到中心*********************** */
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {/* 当前登陆人不是申请人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {/* 当前登陆人是申请人*********************** */
							String[] readonlystr = { "ISSUESTOREROOM",
									"ISSUEADDRESS", "SQN", "TASKNUM",
									"INBINNUM", "RETURNTYPE", "FAILURECONS" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							if (!SXTYPE.isEmpty()) {
								this.setFieldFlag("FAILUREDESC",
										GWConstant.S_REQUIRED, true);
							}
							this.setValue("ORDERQTY", 1,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.setFieldFlag("ORDERQTY",
									GWConstant.S_READONLY, true);
							String itemnum = this.getString("itemnum");
							if (!itemnum.isEmpty()) {
								String type = ItemUtil.getItemInfo(itemnum);
								if (ItemUtil.SQN_ITEM.equals(type)) {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, false);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, true);
								} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, false);
								} else {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, true);
								}
							} else {
								this.setFieldFlag("sqn", GWConstant.S_READONLY,
										true);
								this.setFieldFlag("lotnum",
										GWConstant.S_READONLY, true);
							}
						}
					}
				}
				/* 装箱单状态为申请人修改*************************** */
				if (status.equalsIgnoreCase("申请人修改")) {
					/* 移动类型为现场到现场*********************** */
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {/* 当前登陆人不是申请人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {/* 当前登陆人是申请人*********************** */
							String[] readonlystr = { "ISSUESTOREROOM",
									"ISSUEADDRESS", "SQN", "TASKNUM",
									"INBINNUM", "RETURNTYPE", "FAILURECONS" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag("ORDERQTY",
									GWConstant.S_REQUIRED, true);
						}
					}
					/* 移动类型为现场到中心*********************** */
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {/* 当前登陆人不是申请人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {/* 当前登陆人是申请人*********************** */
							String[] readonlystr = { "ISSUESTOREROOM",
									"ISSUEADDRESS", "SQN", "INBINNUM",
									"RETURNTYPE", "FAILURECONS" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setValue("ORDERQTY", 1,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.setFieldFlag("ORDERQTY",
									GWConstant.S_READONLY, true);
						}
					}
					/* 移动类型为中心到现场*********************** */
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {/* 当前登陆人不是申请人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {/* 当前登陆人是申请人*********************** */
							String[] readonlystr = { "SQN", "TASKNUM",
									"INBINNUM", "RETURNTYPE", "FAILURECONS" };
							String[] requierdstr = { "ISSUESTOREROOM",
									"ISSUEADDRESS" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag(requierdstr,
									GWConstant.S_READONLY, false);
							this.setFieldFlag(requierdstr,
									GWConstant.S_REQUIRED, true);
							this.setFieldFlag("ORDERQTY",
									GWConstant.S_REQUIRED, true);
							String itemnum = this.getString("itemnum");
							if (!itemnum.isEmpty()) {
								String type = ItemUtil.getItemInfo(itemnum);
								if (ItemUtil.SQN_ITEM.equals(type)) {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, false);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, true);
								} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, false);
								} else {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, true);
								}
							} else {
								this.setFieldFlag("sqn", GWConstant.S_READONLY,
										true);
								this.setFieldFlag("lotnum",
										GWConstant.S_READONLY, true);
							}
						}
					}
					/* 移动类型为中心到中心*********************** */
					if (TRANSFERMOVETYPE
							.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {/* 当前登陆人不是申请人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {/* 当前登陆人是申请人*********************** */
							String[] readonlystr = { "ISSUESTOREROOM",
									"ISSUEADDRESS", "SQN", "TASKNUM",
									"INBINNUM", "RETURNTYPE", "FAILURECONS" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setValue("ORDERQTY", 1,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.setFieldFlag("ORDERQTY",
									GWConstant.S_READONLY, true);
							String itemnum = this.getString("itemnum");
							if (!itemnum.isEmpty()) {
								String type = ItemUtil.getItemInfo(itemnum);
								if (ItemUtil.SQN_ITEM.equals(type)) {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, false);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, true);
								} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, false);
								} else {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, true);
								}
							} else {
								this.setFieldFlag("sqn", GWConstant.S_READONLY,
										true);
								this.setFieldFlag("lotnum",
										GWConstant.S_READONLY, true);
							}
						}
					}
				}
			} else {

				String linestatus = this.getString("status");
				if (StringUtil.isStrNotEmpty(linestatus)
						&& (!linestatus.equalsIgnoreCase("未接收"))) {
					String[] readonlystrtrue = { "ISSUESTOREROOM",
							"ISSUEADDRESS", "TASKNUM", "ITEMNUM", "SQN",
							"OUTBINNUM", "INBINNUM", "ORDERQTY", "FAILUREDESC",
							"ISJSFX", "SXTYPE", "MODEL", "TRANSWORKORDERNUM",
							"PROJECTNUM", "REPAIRPROCESS", "DEALTYPE",
							"FAILURECONS", "RETURNTYPE", "MEMO" };
					this.setFieldFlag(readonlystrtrue, GWConstant.S_READONLY,
							true);
				} else {
					if (status.equalsIgnoreCase("未处理")) {
						if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到中心")) {
							if (this.getString("sxtype")
									.equalsIgnoreCase("YXX")) {
								String[] readonlystrtrue = { "ISSUESTOREROOM",
										"ISSUEADDRESS", "OUTBINNUM", "TASKNUM",
										"itemnum", "sqn", "INBINNUM",
										"ORDERQTY", "FAILURECONS", "RETURNTYPE" };
								this.setFieldFlag(readonlystrtrue,
										GWConstant.S_READONLY, true);
							} else {
								String[] readonlystrtrue = { "ISSUESTOREROOM",
										"ISSUEADDRESS", "OUTBINNUM", "TASKNUM",
										"itemnum", "sqn", "INBINNUM",
										"ORDERQTY", "FAILURECONS", "RETURNTYPE" };
								this.setFieldFlag(readonlystrtrue,
										GWConstant.S_READONLY, true);
							}

						}
						if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到现场")) {
							String[] readonlystrtrue = { "ISSUESTOREROOM",
									"ISSUEADDRESS", "TASKNUM", "ITEMNUM",
									"OUTBINNUM", "INBINNUM", "ORDERQTY",
									"FAILUREDESC", "ISJSFX", "MODEL",
									"PROJECTNUM", "REPAIRPROCESS",
									"FAILURECONS", "DEALTYPE" };
							this.setFieldFlag(readonlystrtrue,
									GWConstant.S_READONLY, true);
							if (ISMR.equalsIgnoreCase("否")) {
								String[] readonlystr = { "ITEMNUM", "ORDERQTY",
										"FAILUREDESC", "ISJSFX", "MODEL",
										"PROJECTNUM", "REPAIRPROCESS",
										"DEALTYPE", "MEMO" };
								this.setFieldFlag(readonlystr,
										GWConstant.S_READONLY, false);
							} else {
								String[] readonlystr = { "FAILUREDESC",
										"ISJSFX", "MODEL", "PROJECTNUM",
										"REPAIRPROCESS", "DEALTYPE", "MEMO" };
								this.setFieldFlag(readonlystr,
										GWConstant.S_READONLY, false);
							}
						}
						if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
							if (this.getString("sxtype").equalsIgnoreCase("")) {
								String[] readonlystrtrue = { "ISSUESTOREROOM",
										"ISSUEADDRESS", "TASKNUM", "ITEMNUM",
										"SQN", "OUTBINNUM", "INBINNUM",
										"ORDERQTY", "FAILUREDESC",
										"RECEIVESTOREROOM", "RECEIVEADDRESS",
										"ISJSFX", "SXTYPE", "MODEL",
										"TRANSWORKORDERNUM", "PROJECTNUM",
										"REPAIRPROCESS", "DEALTYPE",
										"FAILURECONS", "RETURNTYPE", "MEMO" };
								this.setFieldFlag(readonlystrtrue,
										GWConstant.S_READONLY, true);
							} else {
								String[] readonlystrtrue = { "ISSUESTOREROOM",
										"ISSUEADDRESS", "TASKNUM", "ITEMNUM",
										"OUTBINNUM", "INBINNUM", "ORDERQTY",
										"FAILUREDESC", "ISJSFX", "MODEL",
										"PROJECTNUM", "REPAIRPROCESS",
										"FAILURECONS", "DEALTYPE" };
								this.setFieldFlag(readonlystrtrue,
										GWConstant.S_READONLY, true);
								String[] readonlystr = { "ITEMNUM",
										"FAILUREDESC", "ISJSFX", "MODEL",
										"PROJECTNUM", "REPAIRPROCESS",
										"DEALTYPE", "MEMO" };
								this.setFieldFlag(readonlystr,
										GWConstant.S_READONLY, false);
							}
							String itemnum = this.getString("itemnum");
							if (!itemnum.isEmpty()) {
								String type = ItemUtil.getItemInfo(itemnum);
								if (ItemUtil.SQN_ITEM.equals(type)) {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, false);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, true);
								} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, false);
								} else {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, true);
								}
							} else {
								this.setFieldFlag("sqn", GWConstant.S_READONLY,
										true);
								this.setFieldFlag("lotnum",
										GWConstant.S_READONLY, true);
							}
						}
						if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到现场")) {
							if (ISMR.equalsIgnoreCase("否")) {
								String[] readonlystrtrue = { "ISSUESTOREROOM",
										"ISSUEADDRESS", "TASKNUM", "ITEMNUM",
										"OUTBINNUM", "INBINNUM", "ORDERQTY",
										"FAILUREDESC", "ISJSFX", "MODEL",
										"PROJECTNUM", "REPAIRPROCESS",
										"FAILURECONS", "DEALTYPE" };
								this.setFieldFlag(readonlystrtrue,
										GWConstant.S_READONLY, true);
								String[] readonlystr = { "ITEMNUM", "ORDERQTY",
										"FAILUREDESC", "ISJSFX", "MODEL",
										"PROJECTNUM", "REPAIRPROCESS",
										"DEALTYPE", "MEMO" };
								this.setFieldFlag(readonlystr,
										GWConstant.S_READONLY, false);
							} else {
								String[] readonlystrtrue = { "ISSUESTOREROOM",
										"ISSUEADDRESS", "TASKNUM", "ITEMNUM",
										"OUTBINNUM", "INBINNUM",
										"FAILUREDESC", "ISJSFX", "MODEL",
										"PROJECTNUM", "REPAIRPROCESS",
										"FAILURECONS", "DEALTYPE" };
								this.setFieldFlag(readonlystrtrue,
										GWConstant.S_READONLY, true);
								String[] readonlystr = { "FAILUREDESC",
										"ISJSFX", "MODEL", "PROJECTNUM",
										"REPAIRPROCESS", "DEALTYPE", "MEMO" };
								this.setFieldFlag(readonlystr,
										GWConstant.S_READONLY, false);
							}
							String itemnum = this.getString("itemnum");
							if (!itemnum.isEmpty()) {
								String type = ItemUtil.getItemInfo(itemnum);
								if (ItemUtil.SQN_ITEM.equals(type)) {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, false);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("orderqty",
											GWConstant.S_READONLY, true);
								} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, false);
									this.setFieldFlag("orderqty",
											GWConstant.S_READONLY, false);
								} else {
									this.setFieldFlag("sqn",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("lotnum",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("orderqty",
											GWConstant.S_READONLY, false);
								}
							} else {
								this.setFieldFlag("sqn", GWConstant.S_READONLY,
										true);
								this.setFieldFlag("lotnum",
										GWConstant.S_READONLY, true);
							}
						}
					} else if (status.equalsIgnoreCase("申请人修改")
							|| status.equalsIgnoreCase("驳回")) {
						if (null != getParent()
								&& !WfControlUtil.isCurUser(getParent())) { /* 当前登陆人不是流程审批人 */
							String[] readonlystrtrue = { "ISSUESTOREROOM",
									"ISSUEADDRESS", "TASKNUM", "ITEMNUM",
									"SQN", "OUTBINNUM", "INBINNUM", "ORDERQTY",
									"FAILUREDESC", "RECEIVESTOREROOM",
									"RECEIVEADDRESS", "ISJSFX", "SXTYPE",
									"MODEL", "TRANSWORKORDERNUM", "PROJECTNUM",
									"REPAIRPROCESS", "DEALTYPE", "FAILURECONS",
									"RETURNTYPE", "MEMO" };
							this.setFieldFlag(readonlystrtrue,
									GWConstant.S_READONLY, true);
						} else {
							if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到中心")) {
								String[] readonlystrtrue = { "ISSUESTOREROOM",
										"ISSUEADDRESS", "TASKNUM", "ITEMNUM",
										"OUTBINNUM", "INBINNUM", "ORDERQTY",
										"FAILUREDESC", "ISJSFX", "MODEL",
										"PROJECTNUM", "REPAIRPROCESS",
										"FAILURECONS", "DEALTYPE", "RETURNTYPE" };
								this.setFieldFlag(readonlystrtrue,
										GWConstant.S_READONLY, true);
								String[] readonlystr = { "ITEMNUM", "TASKNUM",
										"FAILUREDESC", "ISJSFX", "MODEL",
										"PROJECTNUM", "REPAIRPROCESS",
										"DEALTYPE", "MEMO" };
								this.setFieldFlag(readonlystr,
										GWConstant.S_READONLY, false);
							}
							if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到现场")) {
								String[] readonlystrtrue = { "ISSUESTOREROOM",
										"ISSUEADDRESS", "TASKNUM", "ITEMNUM",
										"OUTBINNUM", "INBINNUM",
										"FAILUREDESC", "ISJSFX", "MODEL",
										"PROJECTNUM", "REPAIRPROCESS",
										"FAILURECONS", "DEALTYPE", "RETURNTYPE" };
								this.setFieldFlag(readonlystrtrue,
										GWConstant.S_READONLY, true);
								if (ISMR.equalsIgnoreCase("否")||ISMR.equalsIgnoreCase("")) {
									String[] readonlystr = { "ITEMNUM",
											"FAILUREDESC",
											"ISJSFX", "MODEL", "PROJECTNUM",
											"REPAIRPROCESS", "DEALTYPE","ORDERQTY",
											"MEMO" };
									this.setFieldFlag(readonlystr,
											GWConstant.S_READONLY, false);
									String itemnum = this.getString("itemnum");
									if (!itemnum.equalsIgnoreCase("")) {
										String type = ItemUtil.getItemInfo(itemnum);
										if (ItemUtil.SQN_ITEM.equals(type)) {
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, false);
											this.setFieldFlag("sqn",
													GWConstant.S_REQUIRED, true);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_REQUIRED, false);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_READONLY, true);
										} else {
											this.setFieldFlag("sqn",
													GWConstant.S_REQUIRED, false);
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_READONLY, false);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_REQUIRED, true);
										}
									}
								} else {
									String[] readonlystr = { "FAILUREDESC",
											"ISJSFX", "MODEL", "PROJECTNUM",
											"REPAIRPROCESS", "DEALTYPE","ORDERQTY",
											"MEMO"};
									this.setFieldFlag(readonlystr,
											GWConstant.S_READONLY, false);
									String itemnum = this.getString("itemnum");
									if (!itemnum.equalsIgnoreCase("")) {
										String type = ItemUtil.getItemInfo(itemnum);
										if (ItemUtil.SQN_ITEM.equals(type)) {
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, false);
											this.setFieldFlag("sqn",
													GWConstant.S_REQUIRED, true);
										} else {
											this.setFieldFlag("sqn",
													GWConstant.S_REQUIRED, false);
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_READONLY, false);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_REQUIRED, true);
										}
									}
								}

								
							}
							if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
								if (this.getString("sxtype").equalsIgnoreCase(
										"")) {
									String[] readonlystrtrue = {
											// "ISSUESTOREROOM", "ISSUEADDRESS",
											// "TASKNUM",
											"ITEMNUM",
											"SQN",
											"OUTBINNUM", // "INBINNUM",
											// "ORDERQTY",
											"FAILUREDESC",
											"RECEIVESTOREROOM",
											"RECEIVEADDRESS", "ISJSFX",
											"SXTYPE", "MODEL",
											"TRANSWORKORDERNUM", "PROJECTNUM",
											"REPAIRPROCESS", "DEALTYPE", "ORDERQTY",
											// "FAILURECONS",
											 "MEMO" };
									String[] readonlystrfalse = {
											"ISSUESTOREROOM", "ISSUEADDRESS",
											"TASKNUM", "INBINNUM","RETURNTYPE",
											"FAILURECONS", };
									this.setFieldFlag(readonlystrfalse,
											GWConstant.S_READONLY, true);
									this.setFieldFlag(readonlystrtrue,
											GWConstant.S_READONLY, false);
									String zxdlineid=this.getString("zxdlineid");
									String jkdlineid=this.getString("zxdlineid");
									if(zxdlineid.equalsIgnoreCase("")&&jkdlineid.equalsIgnoreCase("")){
										String itemnum = this.getString("itemnum");
										if (!itemnum.isEmpty()) {
											String type = ItemUtil.getItemInfo(itemnum);
											if (ItemUtil.SQN_ITEM.equals(type)) {
												this.setFieldFlag("sqn",
														GWConstant.S_READONLY, false);
												this.setFieldFlag("lotnum",
														GWConstant.S_READONLY, true);
											} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
												this.setFieldFlag("sqn",
														GWConstant.S_READONLY, true);
												this.setFieldFlag("lotnum",
														GWConstant.S_READONLY, false);
											} else {
												this.setFieldFlag("sqn",
														GWConstant.S_READONLY, true);
												this.setFieldFlag("lotnum",
														GWConstant.S_READONLY, true);
											}
										} else {
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("lotnum",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_READONLY, false);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_REQUIRED, true);
										}
									}
									if(!zxdlineid.equalsIgnoreCase("")&&!jkdlineid.equalsIgnoreCase("")){
										String itemnum = this.getString("itemnum");
										if (!itemnum.isEmpty()) {
											String type = ItemUtil.getItemInfo(itemnum);
											if (ItemUtil.SQN_ITEM.equals(type)) {
												this.setFieldFlag("sqn",
														GWConstant.S_READONLY, false);
												this.setFieldFlag("lotnum",
														GWConstant.S_READONLY, true);
											} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
												this.setFieldFlag("sqn",
														GWConstant.S_READONLY, true);
												this.setFieldFlag("lotnum",
														GWConstant.S_READONLY, false);
											} else {
												this.setFieldFlag("sqn",
														GWConstant.S_READONLY, true);
												this.setFieldFlag("lotnum",
														GWConstant.S_READONLY, true);
											}
										} else {
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("lotnum",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_REQUIRED, false);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_READONLY, true);
										}
									}
								} else {
									String[] readonlystrtrue = {
											"ISSUESTOREROOM", "ISSUEADDRESS",
											"TASKNUM", "ITEMNUM", "SQN",
											"OUTBINNUM", "INBINNUM",
											"FAILUREDESC",
											"ISJSFX", "MODEL", "PROJECTNUM",
											"REPAIRPROCESS", "FAILURECONS",
											"DEALTYPE" };
									this.setFieldFlag(readonlystrtrue,
											GWConstant.S_READONLY, true);
									String[] readonlystr = { "ITEMNUM",
											"ORDERQTY", "FAILUREDESC",
											"ISJSFX", "MODEL", "PROJECTNUM",
											"ORDERQTY", "REPAIRPROCESS", "DEALTYPE",
											"MEMO", "RETURNTYPE" };
									this.setFieldFlag(readonlystr,
											GWConstant.S_READONLY, false);
									String zxdlineid=this.getString("zxdlineid");
									String jkdlineid=this.getString("zxdlineid");
									if(zxdlineid.equalsIgnoreCase("")&&jkdlineid.equalsIgnoreCase("")){
										String itemnum = this.getString("itemnum");
										if (!itemnum.isEmpty()) {
											String type = ItemUtil.getItemInfo(itemnum);
											if (ItemUtil.SQN_ITEM.equals(type)) {
												this.setFieldFlag("sqn",
														GWConstant.S_READONLY, false);
												this.setFieldFlag("lotnum",
														GWConstant.S_READONLY, true);
											} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
												this.setFieldFlag("sqn",
														GWConstant.S_READONLY, true);
												this.setFieldFlag("lotnum",
														GWConstant.S_READONLY, false);
											} else {
												this.setFieldFlag("sqn",
														GWConstant.S_READONLY, true);
												this.setFieldFlag("lotnum",
														GWConstant.S_READONLY, true);
											}
										} else {
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("lotnum",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_READONLY, false);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_REQUIRED, true);
										}
									}
									if(!zxdlineid.equalsIgnoreCase("")&&!jkdlineid.equalsIgnoreCase("")){
										String itemnum = this.getString("itemnum");
										if (!itemnum.isEmpty()) {
											String type = ItemUtil.getItemInfo(itemnum);
											if (ItemUtil.SQN_ITEM.equals(type)) {
												this.setFieldFlag("sqn",
														GWConstant.S_READONLY, false);
												this.setFieldFlag("lotnum",
														GWConstant.S_READONLY, true);
											} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
												this.setFieldFlag("sqn",
														GWConstant.S_READONLY, true);
												this.setFieldFlag("lotnum",
														GWConstant.S_READONLY, false);
											} else {
												this.setFieldFlag("sqn",
														GWConstant.S_READONLY, true);
												this.setFieldFlag("lotnum",
														GWConstant.S_READONLY, true);
											}
										} else {
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("lotnum",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_REQUIRED, false);
											this.setFieldFlag("ORDERQTY",
													GWConstant.S_READONLY, true);
										}
									}
								}
								
							}
							if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到现场")) {
								if (ISMR.equalsIgnoreCase("否")) {
									String[] readonlystrtrue = {
											"ISSUESTOREROOM", "ISSUEADDRESS",
											"TASKNUM", "ITEMNUM", "OUTBINNUM",
											"INBINNUM","ORDERQTY",
											"FAILUREDESC", "ISJSFX", "MODEL",
											"PROJECTNUM", "REPAIRPROCESS", "RETURNTYPE",
											"FAILURECONS", "DEALTYPE" };
									this.setFieldFlag(readonlystrtrue,
											GWConstant.S_READONLY, true);
									String[] readonlystr = { "ITEMNUM",
											 "FAILUREDESC",
											"ISJSFX", "MODEL", "PROJECTNUM",
											"REPAIRPROCESS", "DEALTYPE",
											"MEMO" };
									this.setFieldFlag(readonlystr,
											GWConstant.S_READONLY, false);
									String itemnum = this.getString("itemnum");
									if (!itemnum.isEmpty()) {
										String type = ItemUtil.getItemInfo(itemnum);
										if (ItemUtil.SQN_ITEM.equals(type)) {
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, false);
											this.setFieldFlag("lotnum",
													GWConstant.S_READONLY, true);
										} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("lotnum",
													GWConstant.S_READONLY, false);
										} else {
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("lotnum",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("orderqty",
													GWConstant.S_READONLY, false);
											this.setFieldFlag("orderqty",
													GWConstant.S_REQUIRED, true);
										}
									} else {
										this.setFieldFlag("sqn", GWConstant.S_READONLY,
												true);
										this.setFieldFlag("lotnum",
												GWConstant.S_READONLY, true);
									}
								} else {
									String[] readonlystr = { "ISSUESTOREROOM",
											"ISSUEADDRESS", "TASKNUM",
											"ITEMNUM", "OUTBINNUM", "INBINNUM",
											"FAILUREDESC",
											"ISJSFX", "MODEL", "PROJECTNUM",
											"REPAIRPROCESS", "FAILURECONS", "RETURNTYPE",
											"DEALTYPE" };
									this.setFieldFlag(readonlystr,
											GWConstant.S_READONLY, true);
									String[] readonlystrfalse = {
											"FAILUREDESC", "ISJSFX", "MODEL",
											"PROJECTNUM", "REPAIRPROCESS",
											"DEALTYPE", "MEMO" };
									this.setFieldFlag(readonlystrfalse,
											GWConstant.S_READONLY, false);
									
									String itemnum = this.getString("itemnum");
									if (!itemnum.isEmpty()) {
										String type = ItemUtil.getItemInfo(itemnum);
										if (ItemUtil.SQN_ITEM.equals(type)) {
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, false);
											this.setFieldFlag("lotnum",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("orderqty",
													GWConstant.S_READONLY, true);
										} else if (ItemUtil.LOT_I_ITEM.equals(type)) {
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("lotnum",
													GWConstant.S_READONLY, false);
											this.setFieldFlag("orderqty",
													GWConstant.S_READONLY, false);
											this.setFieldFlag("orderqty",
													GWConstant.S_REQUIRED, true);
										} else {
											this.setFieldFlag("sqn",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("lotnum",
													GWConstant.S_READONLY, true);
											this.setFieldFlag("orderqty",
													GWConstant.S_READONLY, false);
											this.setFieldFlag("orderqty",
													GWConstant.S_REQUIRED, true);
										}
									} else {
										this.setFieldFlag("sqn", GWConstant.S_READONLY,
												true);
										this.setFieldFlag("lotnum",
												GWConstant.S_READONLY, true);
									}
								}
							}
							
						}
					} else if (status.equalsIgnoreCase("在途")
							|| status.equalsIgnoreCase("发运人修改")
							|| status.equalsIgnoreCase("发运人审核")) {
						try {
							if (null != getParent()
									&& !WfControlUtil.isCurUser(getParent())) { /* 当前登陆人不是流程审批人 */
								String[] readonlystrtrue = { "ISSUESTOREROOM",
										"ISSUEADDRESS", "TASKNUM", "ITEMNUM",
										"SQN", "lotnum", "OUTBINNUM",
										"INBINNUM", "ORDERQTY", "FAILUREDESC",
										"RECEIVESTOREROOM", "RECEIVEADDRESS",
										"ISJSFX", "SXTYPE", "MODEL",
										"TRANSWORKORDERNUM", "PROJECTNUM",
										"REPAIRPROCESS", "DEALTYPE",
										"FAILURECONS", "RETURNTYPE", "MEMO" };
								this.setFieldFlag(readonlystrtrue,
										GWConstant.S_READONLY, true);
							} else {
								String[] readonlystr = { "ISSUESTOREROOM",
										"ISSUEADDRESS", "TASKNUM", "ITEMNUM",
										"OUTBINNUM", "SQN", "lotnum",
										"INBINNUM", "ORDERQTY", "FAILUREDESC",
										"ISJSFX", "MODEL", "PROJECTNUM",
										"REPAIRPROCESS", "FAILURECONS",
										"DEALTYPE" };
								this.setFieldFlag(readonlystr,
										GWConstant.S_READONLY, true);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						// this.getJpoSet().setFlag(GWConstant.S_READONLY,
						// true);
					}
				}
			}
		}
		if ("SXTRANSFER".equalsIgnoreCase(getAppName())) {
			String zxdlineid = this.getString("zxdlineid");
			String status = this.getParent().getString("status");
			String[] readonlystr = { "TASKNUM", "PROJNUM", "PROJECTNUM",
					"CUSTNUM", "CUSTNUMNAME", "ITEMNUM", "SQN", "MODEL",
					"SCALENUM", "SCALELINENUM", "FAILUREDESC", "FAILURECONS",
					"QMSNUM" };
			if (zxdlineid.isEmpty()) {
				if (!status.equalsIgnoreCase("未处理")) {
					this.setFieldFlag(readonlystr, GWConstant.S_READONLY, true);
				}
			} else {
				this.setFieldFlag(readonlystr, GWConstant.S_READONLY, true);
			}
		}
		if ("JKTRANSFER".equalsIgnoreCase(getAppName())) {
			if (this.getString("status").equalsIgnoreCase("已接收")) {
				String[] readonlystr = { "ITEMNUM", "SQN", "LOTNUM", "ORDERQTY" };
				this.setFieldFlag(readonlystr, GWConstant.S_READONLY, true);
			}
		}
	}

	@Override
	public void add() throws MroException {
		super.add();
		// 设置行号
		IJpoSet thisSet = getJpoSet();
		setValue("TRANSFERLINENUM", (int) thisSet.max("TRANSFERLINENUM") + 10,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		// 设置申请单号
		if (getParent() != null) {
			setValue("TRANSFERNUM", getParent().getString("TRANSFERNUM"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}
}
