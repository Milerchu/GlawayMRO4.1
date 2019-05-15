package com.glaway.sddq.material.transfer.data;

import java.util.Calendar;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.jpo.StatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 调拨单Jpo
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-8]
 * @since [GlawayMro4.0/调拨单]
 */
public class Transfer extends StatusJpo implements IStatusJpo, FixedLoggers {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();
		// 非新建的记录如下字段只读
		String[] add_readonly_attrs = { "TRANSFERTYPE" };
		if (toBeAdded()) {
			setFieldFlag(add_readonly_attrs, GWConstant.S_READONLY, false);
		} else {
			setFieldFlag(add_readonly_attrs, GWConstant.S_READONLY, true);
		}
		// 根据TRANSFERTYPE字段的值设置界面字段的必填
		setAttrsRequired();
		if (this.getString("SENDORG").equalsIgnoreCase("75180552008")) {
			this.setFieldFlag("COURIERNUM", GWConstant.S_REQUIRED, true);
		}
		// 改造装箱单只读控制 2018/08/04 关飞飞
		if (!this.isNew()) {
			//
			String[] gzjkd_readonly_attrs = { "PROJECTNUM", "RECEIVESTOREROOM" };// 项目编号，接收库房
			String[] gzjkd_readonly_attr = { "PACKBY", "CHECKBY", "PACKDATE",
					"ISSUEADDRESS", "AGENTBY", "AGENTPHONE", "PACKQTY",
					"RECEIVEBY", "RECEIVEPHONE", "COURIERNUM",
					"COURIERCOMPANY", "RECEIVEQTY" };

			if (this.getString("type").equals("GZZXD")) {
				if (this.getString("status").equals("在途")) {
					this.setFieldFlag(gzjkd_readonly_attrs, 7L, true);
					this.setFieldFlag(gzjkd_readonly_attr, 7L, true);

				} else if (this.getString("status").equals("已接收")) {
					this.setFlag(7L, true);
				}
			}

			if (this.getString("type").equals("JKD")) {
				if (this.getString("status").equals("已接收")) {
					this.setFlag(7L, true);
				}
				IJpoSet transferlineset = getJpoSet("transferline");
				transferlineset.setQueryWhere("status='已接收'");
				transferlineset.reset();
				if (transferlineset.count() > 0) {
					this.setFieldFlag("ISSUESTOREROOM", 7L, true);

				}
			}

		}

		/* 送修单界面控制 */
		if (this.getString("type").equals("SX")) {// 判断为送修单
			if (!this.isNew()) {
				String personid = getUserServer().getUserInfo().getLoginID();
				personid = personid.toUpperCase();
				String sendby = this.getString("sendby");
				String CREATEBY = this.getString("CREATEBY");
				if (this.getString("status").equalsIgnoreCase("已接收")
						|| this.getString("status").equalsIgnoreCase("部分缴库")
						|| this.getString("status").equalsIgnoreCase("全部缴库")) {
					this.setFlag(GWConstant.S_READONLY, true);
				}
				if (this.getString("status").equalsIgnoreCase("未处理")) {
					if (this.getJpoSet("transferline").count() > 0) {
						String[] streadonly = { "SXTYPE", "AGENTBY",
								"SENDDATE", "LISTTYPE", "ISSUESTOREROOM",
								"ISSUEADDRESS", "REPAIRORG", "COURIERNUM",
								"GOTODATE", "GOTONUM", "PROJECTNUM", "MEMO",
								"SENDBY", "RECEIVEADDRESS", "PLANREPAURDATE" };
						this.setFieldFlag(streadonly, GWConstant.S_READONLY,
								true);
					} else {
						String[] streadonlyfalse = { "SXTYPE", "AGENTBY",
								"SENDDATE", "LISTTYPE", "ISSUESTOREROOM",
								"ISSUEADDRESS", "REPAIRORG", "COURIERNUM",
								"GOTODATE", "GOTONUM", "MEMO", "SENDBY",
								"RECEIVEADDRESS", "PLANREPAURDATE" };
						this.setFieldFlag(streadonlyfalse,
								GWConstant.S_READONLY, false);
					}
					if (!personid.equalsIgnoreCase(CREATEBY)) {
						if (personid.equalsIgnoreCase(sendby)) {
							String[] readonlystr = { "SXTYPE", "PROJECTNUM",
									"AGENTBY", "SENDDATE", "MEMO", "LISTTYPE",
									"ISSUESTOREROOM", "ISSUEADDRESS",
									"REPAIRORG", "COURIERNUM", "SENDBY",
									"RECEIVEADDRESS", "PLANREPAURDATE" };
							this.setFieldFlag("gotodate",
									GWConstant.S_READONLY, false);
							this.setFieldFlag("gotodate",
									GWConstant.S_REQUIRED, true);
							this.setFieldFlag("gotonum", GWConstant.S_READONLY,
									false);
							this.setFieldFlag("gotonum", GWConstant.S_REQUIRED,
									true);
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.getJpoSet("transferline").setFlag(
									GWConstant.S_READONLY, true);
						} else {
							this.setFlag(GWConstant.S_READONLY, true);
						}
					}
				}
				if (this.getString("status").equalsIgnoreCase("在途")) {
					if (personid.equalsIgnoreCase(sendby)) {
						this.setFieldFlag("gotodate", GWConstant.S_READONLY,
								false);
						this.setFieldFlag("gotodate", GWConstant.S_REQUIRED,
								true);
						this.setFieldFlag("gotonum", GWConstant.S_READONLY,
								false);
						this.setFieldFlag("gotonum", GWConstant.S_REQUIRED,
								true);
					} else {
						this.setFlag(GWConstant.S_READONLY, true);
					}
				}

			}

		}
		/* 装箱单界面控制 */
		if (this.getString("type").equals("ZXD")
				|| "ZXTRANSFER".equalsIgnoreCase(getAppName())) {// 判断为装箱单
			String personid = getUserServer().getUserInfo().getLoginID();
			personid = personid.toUpperCase();
			String CREATEBY = this.getString("CREATEBY");
			String RECEIVEBY = this.getString("RECEIVEBY");
			String sendby = this.getString("sendby");
			/* 新建下状态****************************** */
			if (this.isNew()) {
				String[] newstr = { "COURIERNUM", "COURIERCOMPANY", "OPENBY",
						"RECEIVEDATE", "RECEIVECHECKBY", "SXTYPE",
						"TRANSWORKORDERNUM", "PROJECTNUM", "ISMR", "MRNUM",
						"ISFXFY" };
				this.setFieldFlag(newstr, GWConstant.S_READONLY, true);
				/* 移动类型为中心到中心****************************** */
				if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
						ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
					this.setFieldFlag(newstr, GWConstant.S_READONLY, true);
					this.setFieldFlag("sendby", GWConstant.S_READONLY, true);
				}
				/* 移动类型为中心到现场****************************** */
				if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
						ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
					this.setFieldFlag(newstr, GWConstant.S_READONLY, true);
					this.setFieldFlag("ISSUESTOREROOM", GWConstant.S_READONLY,
							true);
					this.setFieldFlag("ISSUEADDRESS", GWConstant.S_READONLY,
							true);
				}
			} else {
				/* 非新建下状态为未处理****************************** */
				if (this.getString("status").equalsIgnoreCase("未处理")) {
					if (this.getJpoSet("transferline").count() > 0) {
						String[] streadonly = { "TRANSFERMOVETYPE", "SXTYPE",
								"ISMR", "MRNUM", "TRANSWORKORDERNUM",
								"PROJECTNUM", "MEMO", "ISSUESTOREROOM",
								"PACKBY", "SENDBY", "CHECKBY", "PACKDATE",
								"ISSUEADDRESS", "RECEIVESTOREROOM",
								"RECEIVEADDRESS" };
						this.setFieldFlag(streadonly, GWConstant.S_READONLY,
								true);
					}
					/* 移动类型为现场到中心****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {// 登陆人不是申请人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为申请人
							String[] readonlystr = { "ISMR", "ISFXFY", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"COURIERCOMPANY", "COURIERNUM",
									"RECEIVEBY", "OPENBY", "RECEIVEDATE",
									"RECEIVECHECKBY" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
						}
					}
					/* 移动类型为现场到现场****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_XTOX)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {// 登陆人不是申请人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为申请人
							String[] readonlystr = { "ISMR", "ISFXFY", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"COURIERCOMPANY", "COURIERNUM",
									"RECEIVEBY", "OPENBY", "RECEIVEDATE",
									"RECEIVECHECKBY" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
						}
					}
					/* 移动类型为中心到中心****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {// 登陆人不是申请人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为申请人
							String[] readonlystr = { "ISMR", "ISFXFY", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"COURIERCOMPANY", "COURIERNUM",
									"RECEIVEBY", "OPENBY", "RECEIVEDATE",
									"RECEIVECHECKBY" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag("sendby", GWConstant.S_READONLY,
									true);
						}
					}
					/* 移动类型为中心到现场****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {// 登陆人不是申请人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为申请人
							String[] readonlystr = { "TRANSWORKORDERNUM",
									"PROJECTNUM", "COURIERCOMPANY",
									"COURIERNUM", "RECEIVEBY", "OPENBY",
									"RECEIVEDATE", "RECEIVECHECKBY" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag("ISSUESTOREROOM",
									GWConstant.S_READONLY, true);
							this.setFieldFlag("ISSUEADDRESS",
									GWConstant.S_READONLY, true);
						}
					}
				}
				/* 非新建下状态为发运人审核****************************** */
				if (this.getString("status").equalsIgnoreCase("发运人审核")) {
					/* 移动类型为现场到中心****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
						if (!personid.equalsIgnoreCase(sendby)) {// 登陆人不是发运人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为发运人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY" };
							String[] requierdstr = { "COURIERCOMPANY",
									"COURIERNUM" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag(requierdstr,
									GWConstant.S_READONLY, false);
							this.setFieldFlag(requierdstr,
									GWConstant.S_REQUIRED, true);
							// this.getJpoSet("transferline").setFlag(GWConstant.S_READONLY,
							// true);
						}
					}
					/* 移动类型为现场到现场****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_XTOX)) {
						if (!personid.equalsIgnoreCase(sendby)) {// 登陆人不是发运人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为发运人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY" };
							String[] requierdstr = { "COURIERCOMPANY",
									"COURIERNUM" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag(requierdstr,
									GWConstant.S_READONLY, false);
							this.setFieldFlag(requierdstr,
									GWConstant.S_REQUIRED, true);
							// this.getJpoSet("transferline").setFlag(GWConstant.S_READONLY,
							// true);
						}
					}
					/* 移动类型为中心到中心****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
						if (!personid.equalsIgnoreCase(sendby)) {// 登陆人不是发运人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为发运人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY" };
							String[] requierdstr = { "COURIERCOMPANY",
									"COURIERNUM" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag(requierdstr,
									GWConstant.S_READONLY, false);
							this.setFieldFlag(requierdstr,
									GWConstant.S_REQUIRED, true);
							// this.getJpoSet("transferline").setFlag(GWConstant.S_READONLY,
							// true);
						}
					}
					/* 移动类型为中心到现场****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
						if (!personid.equalsIgnoreCase(sendby)) {// 登陆人不是发运人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为发运人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY" };
							String[] requierdstr = { "COURIERCOMPANY",
									"COURIERNUM" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag(requierdstr,
									GWConstant.S_READONLY, false);
							this.setFieldFlag(requierdstr,
									GWConstant.S_REQUIRED, true);
							// this.getJpoSet("transferline").setFlag(GWConstant.S_READONLY,
							// true);
						}
					}
				}
				/* 非新建下状态为申请人修改****************************** */
				if (this.getString("status").equalsIgnoreCase("申请人修改")) {
					/* 移动类型为现场到中心****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {// 登陆人不是申请人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为申请人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY",
									"PACKDATE" };
							String[] requierdstr = { "COURIERCOMPANY",
									"COURIERNUM" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag(requierdstr,
									GWConstant.S_READONLY, false);
							this.setFieldFlag(requierdstr,
									GWConstant.S_REQUIRED, true);
						}
					}
					/* 移动类型为现场到现场****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_XTOX)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {// 登陆人不是申请人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为申请人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY",
									"PACKDATE" };
							String[] requierdstr = { "COURIERCOMPANY",
									"COURIERNUM" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag(requierdstr,
									GWConstant.S_READONLY, false);
							this.setFieldFlag(requierdstr,
									GWConstant.S_REQUIRED, true);
						}
					}
					/* 移动类型为中心到中心****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {// 登陆人不是申请人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为申请人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY",
									"COURIERCOMPANY", "COURIERNUM", "PACKDATE" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
						}
					}
					/* 移动类型为中心到现场****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {// 登陆人不是申请人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为申请人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY",
									"COURIERCOMPANY", "COURIERNUM", "PACKDATE" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
						}
					}
				}
				/* 非新建下状态为驳回****************************** */
				if (this.getString("status").equalsIgnoreCase("驳回")) {
					/* 移动类型为现场到中心****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {// 登陆人不是申请人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为申请人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY",
									"COURIERCOMPANY", "COURIERNUM", "PACKDATE" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							// this.getJpoSet("transferline").setFlag(GWConstant.S_READONLY,
							// false);
						}
					}
					/* 移动类型为现场到现场****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_XTOX)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {// 登陆人不是申请人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为申请人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY",
									"COURIERCOMPANY", "COURIERNUM", "PACKDATE" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							// this.getJpoSet("transferline").setFlag(GWConstant.S_READONLY,
							// false);
						}
					}
					/* 移动类型为中心到中心****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {// 登陆人不是申请人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为申请人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY",
									"COURIERCOMPANY", "COURIERNUM", "PACKDATE" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							// this.getJpoSet("transferline").setFlag(GWConstant.S_READONLY,
							// false);
						}
					}
					/* 移动类型为中心到现场****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
						if (!personid.equalsIgnoreCase(CREATEBY)) {// 登陆人不是申请人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为申请人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY",
									"COURIERCOMPANY", "COURIERNUM", "PACKDATE" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							IJpoSet transferlineset=this.getJpoSet("transferline");
							if(transferlineset.isEmpty()){
								this.setFieldFlag("mrnum", GWConstant.S_READONLY, false);
								this.setFieldFlag("mrnum", GWConstant.S_REQUIRED, true);
							}else{
								this.setFieldFlag("mrnum", GWConstant.S_REQUIRED, false);
								this.setFieldFlag("mrnum", GWConstant.S_READONLY, true);
							}
							// this.getJpoSet("transferline").setFlag(GWConstant.S_READONLY,
							// false);
						}
					}
				}
				/* 非新建下状态为在途****************************** */
				if (this.getString("status").equalsIgnoreCase("在途")) {
					/* 移动类型为现场到中心****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_XTOZ)) {
						if (!personid.equalsIgnoreCase(RECEIVEBY)) {// 登陆人不是接收人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为接收人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"COURIERCOMPANY", "COURIERNUM", "PACKDATE" };
//							String[] requierdstr = { "OPENBY", "RECEIVEDATE",
//									"RECEIVECHECKBY" };
							String[] requierdstr = { "OPENBY",
							"RECEIVECHECKBY" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag(requierdstr,
									GWConstant.S_READONLY, false);
							this.setFieldFlag(requierdstr,
									GWConstant.S_REQUIRED, true);
						}
					}
					/* 移动类型为现场到现场****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_XTOX)) {
						if (!personid.equalsIgnoreCase(RECEIVEBY)) {// 登陆人不是接收人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为接收人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"COURIERCOMPANY", "COURIERNUM", "PACKDATE" };
//							String[] requierdstr = { "OPENBY", "RECEIVEDATE",
//									"RECEIVECHECKBY" };
							String[] requierdstr = { "OPENBY",
							"RECEIVECHECKBY" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag(requierdstr,
									GWConstant.S_READONLY, false);
							this.setFieldFlag(requierdstr,
									GWConstant.S_REQUIRED, true);
						}
					}
					/* 移动类型为中心到中心****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_ZTOZ)) {
						if (!personid.equalsIgnoreCase(RECEIVEBY)) {// 登陆人不是接收人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为接收人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"COURIERCOMPANY", "COURIERNUM", "PACKDATE" };
//							String[] requierdstr = { "OPENBY", "RECEIVEDATE",
//									"RECEIVECHECKBY" };
							String[] requierdstr = { "OPENBY", 
							"RECEIVECHECKBY" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag(requierdstr,
									GWConstant.S_READONLY, false);
							this.setFieldFlag(requierdstr,
									GWConstant.S_REQUIRED, true);
						}
					}
					/* 移动类型为中心到现场****************************** */
					if (this.getString("TRANSFERMOVETYPE").equalsIgnoreCase(
							ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
						if (!personid.equalsIgnoreCase(RECEIVEBY)) {// 登陆人不是接收人
							this.setFlag(GWConstant.S_READONLY, true);
						} else {// 登陆人为接收人
							String[] readonlystr = { "TRANSFERMOVETYPE",
									"ISFXFY", "ISMR", "MRNUM",
									"TRANSWORKORDERNUM", "PROJECTNUM",
									"SXTYPE", "ISSUESTOREROOM", "PACKBY",
									"CHECKBY", "SENDBY", "ISSUEADDRESS",
									"RECEIVESTOREROOM", "RECEIVEADDRESS",
									"COURIERCOMPANY", "COURIERNUM", "PACKDATE" };
//							String[] requierdstr = { "OPENBY", "RECEIVEDATE",
//									"RECEIVECHECKBY" };
							String[] requierdstr = { "OPENBY",
							"RECEIVECHECKBY" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
							this.setFieldFlag(requierdstr,
									GWConstant.S_READONLY, false);
							this.setFieldFlag(requierdstr,
									GWConstant.S_REQUIRED, true);
						}
					}
				}
				/* 非新建下状态为结束****************************** */
				if (this.getString("status").equalsIgnoreCase("结束")) {
					this.setFlag(GWConstant.S_READONLY, true);
				}
				/* 非新建下状态为已接收****************************** */
				if (this.getString("status").equalsIgnoreCase("已接收")) {
					this.setFlag(GWConstant.S_READONLY, true);
				}
				/* 非新建下状态为发运人修改****************************** */
				if (this.getString("status").equalsIgnoreCase("发运人修改")) {
					if (!personid.equalsIgnoreCase(sendby)) {// 登陆人不是接收人
						this.setFlag(GWConstant.S_READONLY, true);
					} else {// 登陆人为接收人
						String[] readonlystr = { "TRANSFERMOVETYPE", "ISFXFY",
								"ISMR", "MRNUM", "TRANSWORKORDERNUM",
								"PROJECTNUM", "SXTYPE", "ISSUESTOREROOM",
								"PACKBY", "CHECKBY", "SENDBY", "ISSUEADDRESS",
								"RECEIVESTOREROOM", "RECEIVEADDRESS",
								"COURIERCOMPANY", "COURIERNUM", "PACKDATE",
								"OPENBY", "RECEIVEDATE", "RECEIVECHECKBY" };
						String[] requierdstr = { "COURIERCOMPANY", "COURIERNUM" };
						this.setFieldFlag(readonlystr, GWConstant.S_READONLY,
								true);
						this.setFieldFlag(requierdstr, GWConstant.S_READONLY,
								false);
						this.setFieldFlag(requierdstr, GWConstant.S_REQUIRED,
								true);
					}

				}
			}
		}
	}

	public void setAttrsRequired() throws MroException {
		String[] zxd_required_attrs = { "PACKBY", "CHECKBY", "PACKDATE",
				"ISSUESTOREROOM", "ISSUEADDRESS", "PACKQTY", "RECEIVEBY",
				"RECEIVEPHONE", "RECEIVEADDRESS", "RECEIVESTOREROOM", "OPENBY",
				"RECEIVECHECKBY", "RECEIVEDATE", "RECEIVEQTY" };
		String[] sxd_required_attrs = { "SENDORG", "SENDSTOREROOM", "SENDDATE",
				"REPAIRORG", "PLANREPAURDATE", "RECEIVEDATE", "RECEIVECHECKBY",
				"RECEIVEBY" };
		String[] jkd_required_attrs = { "SENDORG", "SENDNUM", "SENDDATE",
				"CONTACTBY", "CONTACTPHONE", "REPAIRORG", "RECEIVEBY",
				"RECEIVEDATE", "RECEIVECHECKBY" };

		String transfertype = getString("TRANSFERTYPE");
		if (StringUtil.isEqual(transfertype, "装箱单")) {
			setFieldFlag(sxd_required_attrs, GWConstant.S_REQUIRED, false);
			setFieldFlag(jkd_required_attrs, GWConstant.S_REQUIRED, false);
			setFieldFlag(zxd_required_attrs, GWConstant.S_REQUIRED, true);
		} else if (StringUtil.isEqual(transfertype, "送修单")) {
			setFieldFlag(zxd_required_attrs, GWConstant.S_REQUIRED, false);
			setFieldFlag(jkd_required_attrs, GWConstant.S_REQUIRED, false);
			setFieldFlag(sxd_required_attrs, GWConstant.S_REQUIRED, true);
		} else if (StringUtil.isEqual(transfertype, "缴库单")) {
			setFieldFlag(zxd_required_attrs, GWConstant.S_REQUIRED, false);
			setFieldFlag(sxd_required_attrs, GWConstant.S_REQUIRED, false);
			setFieldFlag(jkd_required_attrs, GWConstant.S_REQUIRED, true);
		}
	}

	/**
	 * 新建设置装箱单编号
	 * 
	 * @throws MroException
	 */
	@Override
	public void add() throws MroException {
		super.add();
		String type = this.getString("type");
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		setValue("STATUS", "未处理");
		String loginid = getUserServer().getUserInfo().getLoginID();
		loginid = loginid.toUpperCase();
		if (type.equalsIgnoreCase("SX")) {
			setValue("SENDDATE", MroServer.getMroServer().getDate(),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			// setValue("ISSUESTOREROOM", ItemUtil.ISSUESTOREROOM_ZXWXK);
			IJpoSet deptset = MroServer.getMroServer().getJpoSet("SYS_DEPT",
					MroServer.getMroServer().getSystemUserServer());
			deptset.setQueryWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
					+ loginid + "')");
			deptset.reset();
			if (!deptset.isEmpty()) {
				String PARENTdept = deptset.getJpo(0).getString("PARENT");
				setValue("SENDORG", PARENTdept, GWConstant.P_NOACTION);
				if (PARENTdept.equalsIgnoreCase("75180552008")) {
					this.setFieldFlag("COURIERNUM", GWConstant.S_REQUIRED, true);
				}
			}
			IJpoSet zxtransferset = MroServer.getMroServer().getSysJpoSet(
					"transfer");
			zxtransferset.setUserWhere("type='" + type + "'");
			zxtransferset.setOrderBy("CREATEDATE desc");
			zxtransferset.reset();
			if (!zxtransferset.isEmpty()) {
				String AGENTBY = zxtransferset.getJpo(0).getString("AGENTBY");
				this.setValue("AGENTBY", AGENTBY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}
		// 杨毅 修改
		if (!type.equalsIgnoreCase("SX")) {
			this.setValue("PACKDATE", MroServer.getMroServer().getDate());
			String transfernum = this.getString("TRANSFERNUM");

			if ("ZXTRANSFER".equals(getAppName())) {
				IJpoSet deptset = MroServer.getMroServer().getJpoSet(
						"SYS_DEPT",
						MroServer.getMroServer().getSystemUserServer());
				deptset.setQueryWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
						+ loginid + "')");
				deptset.reset();
				if (!deptset.isEmpty()) {
					String deptnum = deptset.getJpo(0).getString("deptnum");
					String retrun = WorkorderUtil
							.getAbbreviationByDeptnum(deptnum);
					if (retrun.equalsIgnoreCase("PJGL")) {
						transfernum = "ZX-" + "ZX" + "-" + transfernum;
					} else {
						transfernum = retrun + "-" + "ZX" + "-" + transfernum;
					}
				} else {
					transfernum = "ZX-" + "ZX" + "-" + transfernum;
				}
			} else if (type.equalsIgnoreCase("")) {
				String Ztype = "JKD";
				transfernum = "DB-" + Ztype + "-" + transfernum;
			} else {
				transfernum = "DB-" + type + "-" + transfernum;
			}
			this.setValue("TRANSFERNUM", transfernum, GWConstant.P_NOACTION);
		}

	}

	/**
	 * 
	 * 变更状态
	 * 
	 * @param newstatus
	 * @param memo
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void changestatus(String newstatus, String memo) throws MroException {
		if (StringUtil.isStrNotEmpty(newstatus)) {
			String oldstatus = getString("STATUS");
			setValue("STATUS", newstatus);
			addStatusHistory(memo, oldstatus);
		} else {
			throw new AppException("custinfo", "cannotnull");
		}

	}

	/**
	 * 
	 * 添加状态历史
	 * 
	 * @param memo
	 * @param oldstatus
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private void addStatusHistory(String memo, String oldstatus)
			throws MroException {
		IJpoSet ahstatusset = getJpoSet("TRANSFERSTATUSHISTORY");
		IJpo ahstatusnew = ahstatusset.addJpo();
		ahstatusnew.setValue("TRANSFERNUM", getString("TRANSFERNUM"),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		ahstatusnew.setValue("STATUS", oldstatus,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		ahstatusnew.setValue("MEMO", memo, 11L);
		ahstatusnew.setFlag(7L, true);
	}

	@Override
	public void delete(long flag) throws MroException {
		getJpoSet("TRANSFERSTATUSHISTORY").deleteAll(
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		getJpoSet("TRANSFERLINE").deleteAll(
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		super.delete(flag);
	}
}
