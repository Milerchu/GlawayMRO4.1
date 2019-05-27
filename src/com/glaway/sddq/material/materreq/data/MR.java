package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
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
 * 配件申请单Jpo
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-8]
 * @since [GlawayMro4.0/配件申请]
 */
public class MR extends StatusJpo implements IStatusJpo, FixedLoggers {
	private static final long serialVersionUID = 1L;

	/**
	 * 初始化页面只读控制
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		/* 计划经理处置列表 */
		IJpoSet mrlinetransferSet = this.getJpoSet("mrlinetransfer");
		/* 服务主管处置列表 */
		IJpoSet mrlinetranslineSet = this.getJpoSet("mrlinetransline");
		/* 配件申请行列表 */
		IJpoSet mrlineSet = this.getJpoSet("mrline");
		String[] readonly = { "MRTYPE", "RECEIVESTOREROOM", "RECEIVEADDRESS",
				"RECEIVEKEEPER", "RECEIVEPHONE", "DELIVERYDATE" };
		String status = this.getString("status");
		String CREATEBY = this.getString("CREATEBY");
		String mrnum = this.getString("mrnum");
		String MRTYPE = this.getString("MRTYPE");
		String loginid = this.getUserInfo().getLoginID();
		loginid = loginid.toUpperCase();
		if (this.isNew()) {
			this.setFieldFlag("PROJECT", GWConstant.S_REQUIRED, true);
			this.setFieldFlag("MODEL", GWConstant.S_REQUIRED, true);
			this.setFieldFlag("SAPPONUM", GWConstant.S_READONLY, true);
			this.setFieldFlag("TOTALCOST", GWConstant.S_READONLY, true);
			this.setFieldFlag("ELECTRONNUM", GWConstant.S_READONLY, true);
			mrlinetransferSet.setFlag(GWConstant.S_READONLY, true);
			mrlinetranslineSet.setFlag(GWConstant.S_READONLY, true);
		}
		if (!this.isNew()) {
			if (status.equalsIgnoreCase("关闭") || status.equalsIgnoreCase("结束")) {
				this.setFlag(GWConstant.S_READONLY, true);
			} else if (status.equalsIgnoreCase("副总经理审批")
					|| status.equalsIgnoreCase("总经理审批")) {
				String[] materreadonly = { "MRTYPE", "PROJECT", "MODEL",
						"RECEIVESTOREROOM", "RECEIVEADDRESS", "RECEIVEPHONE",
						"SAPPONUM", "ELECTRONNUM", "DELIVERYDATE",
						"DELIVERYWAY", "TOTALCOST", "ISIMPORT", "MEMO",
						"RECEIVEKEEPER" };
				this.setFieldFlag(materreadonly, GWConstant.S_READONLY, true);
				mrlineSet.setFlag(GWConstant.S_READONLY, true);
				mrlinetransferSet.setFlag(GWConstant.S_READONLY, true);
				mrlinetranslineSet.setFlag(GWConstant.S_READONLY, true);
			} else if (status.equalsIgnoreCase("未处理")) {
				if (loginid.equalsIgnoreCase(CREATEBY)) {
					if (MRTYPE.equalsIgnoreCase("零星")) {
						this.setFieldFlag("PROJECT", GWConstant.S_REQUIRED,
								true);
						this.setFieldFlag("DELIVERYWAY", GWConstant.S_REQUIRED,
								true);
						this.setFieldFlag("MODEL", GWConstant.S_REQUIRED, true);
					}
					if (MRTYPE.equalsIgnoreCase("项目")) {
						this.setFieldFlag("MODEL", GWConstant.S_REQUIRED, true);
						this.setFieldFlag("PROJECT", GWConstant.S_REQUIRED,
								true);
						this.setFieldFlag("DELIVERYWAY", GWConstant.S_READONLY,
								true);
					}
					this.setFieldFlag("SAPPONUM", GWConstant.S_READONLY, true);
					this.setFieldFlag("TOTALCOST", GWConstant.S_READONLY, true);
					this.setFieldFlag("ELECTRONNUM", GWConstant.S_READONLY,
							true);
					mrlinetransferSet.setFlag(GWConstant.S_READONLY, true);
					mrlinetranslineSet.setFlag(GWConstant.S_READONLY, true);
				} else {
					this.setFlag(GWConstant.S_READONLY, true);
				}
			} else {
				try {
					if (!WfControlUtil.isCurUser(this)) { /* 当前登陆人不是流程审批人 */
						this.setFlag(GWConstant.S_READONLY, true);
					} else { /* 当前登陆人是流程审批人 */
						if (MRTYPE.equalsIgnoreCase("零星")) {
							/* 状态为服务主管审批 */
							if (status.equalsIgnoreCase(ItemUtil.STATUS_FWZGSP)) {
								this.setFieldFlag(readonly,
										GWConstant.S_READONLY, true);
								this.setFieldFlag("MODEL",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("DELIVERYWAY",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("PROJECT",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("SAPPONUM",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("ELECTRONNUM",
										GWConstant.S_READONLY, true);
								mrlineSet.setFlag(GWConstant.S_READONLY, true);
								mrlinetransferSet.setFlag(
										GWConstant.S_READONLY, true);
							}
							/* 状态为计划经理审批 */
							if (status.equalsIgnoreCase(ItemUtil.STATUS_JHJLSP)) {
								this.setFieldFlag(readonly,
										GWConstant.S_READONLY, true);
								this.setFieldFlag("MODEL",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("DELIVERYWAY",
										GWConstant.S_READONLY, false);
								this.setFieldFlag("DELIVERYWAY",
										GWConstant.S_REQUIRED, true);
								this.setFieldFlag("PROJECT",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("SAPPONUM",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("ELECTRONNUM",
										GWConstant.S_READONLY, true);
								mrlineSet.setFlag(GWConstant.S_READONLY, true);
								mrlinetranslineSet.setFlag(
										GWConstant.S_READONLY, true);
							}
							/* 状态为产品线经理审批 */
							if (status
									.equalsIgnoreCase(ItemUtil.STATUS_CPXJLSP)) {
								this.setFieldFlag(readonly,
										GWConstant.S_READONLY, true);
								this.setFieldFlag("MODEL",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("DELIVERYWAY",
										GWConstant.S_REQUIRED, false);
								this.setFieldFlag("DELIVERYWAY",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_READONLY, false);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_REQUIRED, true);
								this.setFieldFlag("PROJECT",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("SAPPONUM",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("ELECTRONNUM",
										GWConstant.S_READONLY, true);
								mrlineSet.setFlag(GWConstant.S_READONLY, true);
								mrlinetransferSet.setFlag(
										GWConstant.S_READONLY, true);
								mrlinetranslineSet.setFlag(
										GWConstant.S_READONLY, true);
							}
							/* 状态为配件专员执行 */
							if (status.equalsIgnoreCase(ItemUtil.STATUS_PJZYZX)) {
								this.setFieldFlag(readonly,
										GWConstant.S_READONLY, true);
								this.setFieldFlag("MODEL",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("DELIVERYWAY",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_REQUIRED, false);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("PROJECT",
										GWConstant.S_READONLY, true);
								IJpoSet jhMRLINETRANSFERSet = MroServer
										.getMroServer().getJpoSet(
												"mrlinetransfer",
												MroServer.getMroServer()
														.getSystemUserServer());
								jhMRLINETRANSFERSet.setUserWhere("mrnum='"
										+ mrnum + "' and TRANSTYPE='下达计划'");
								jhMRLINETRANSFERSet.reset();
								if (jhMRLINETRANSFERSet.isEmpty()) {
									this.setFieldFlag("SAPPONUM",
											GWConstant.S_REQUIRED, false);
									this.setFieldFlag("SAPPONUM",
											GWConstant.S_READONLY, true);
									this.setFieldFlag("ELECTRONNUM",
											GWConstant.S_REQUIRED, false);
									this.setFieldFlag("ELECTRONNUM",
											GWConstant.S_READONLY, true);
								} else {
									this.setFieldFlag("SAPPONUM",
											GWConstant.S_READONLY, false);
									this.setFieldFlag("SAPPONUM",
											GWConstant.S_REQUIRED, true);
									this.setFieldFlag("ELECTRONNUM",
											GWConstant.S_READONLY, false);
									this.setFieldFlag("ELECTRONNUM",
											GWConstant.S_REQUIRED, true);
								}

								mrlineSet.setFlag(GWConstant.S_READONLY, true);
								mrlinetransferSet.setFlag(
										GWConstant.S_READONLY, true);
								mrlinetranslineSet.setFlag(
										GWConstant.S_READONLY, true);
							}
							/* 状态为计划经理修改 */
							if (status.equalsIgnoreCase(ItemUtil.STATUS_JHJLXG)) {
								this.setFieldFlag(readonly,
										GWConstant.S_READONLY, true);
								this.setFieldFlag("MODEL",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("DELIVERYWAY",
										GWConstant.S_READONLY, false);
								this.setFieldFlag("DELIVERYWAY",
										GWConstant.S_REQUIRED, true);
								this.setFieldFlag("SAPPONUM",
										GWConstant.S_REQUIRED, false);
								this.setFieldFlag("SAPPONUM",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("ELECTRONNUM",
										GWConstant.S_REQUIRED, false);
								this.setFieldFlag("ELECTRONNUM",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("PROJECT",
										GWConstant.S_READONLY, true);
								mrlineSet.setFlag(GWConstant.S_READONLY, true);
								mrlinetranslineSet.setFlag(
										GWConstant.S_READONLY, true);
							}
							/* 状态为申请人修改 */
							if (status.equalsIgnoreCase(ItemUtil.STATUS_SQRXG)) {
								this.setFieldFlag("PROJECT",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("SAPPONUM",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("ELECTRONNUM",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("MODEL",
										GWConstant.S_REQUIRED, true);
								mrlinetransferSet.setFlag(
										GWConstant.S_READONLY, true);
								mrlinetranslineSet.setFlag(
										GWConstant.S_READONLY, true);
							}
						}
						if (MRTYPE.equalsIgnoreCase("项目")) {
							mrlinetransferSet.setFlag(GWConstant.S_READONLY,
									true);
							mrlinetranslineSet.setFlag(GWConstant.S_READONLY,
									true);
							/* 状态为配件主管组织评审 */
							if (status
									.equalsIgnoreCase(ItemUtil.STATUS_JHZGZZPS)) {
								this.setFieldFlag(readonly,
										GWConstant.S_READONLY, true);
								this.setFieldFlag("PROJECT",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("DELIVERYWAY",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("MODEL",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("SAPPONUM",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("ELECTRONNUM",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_READONLY, true);
							}
							/* 状态为产品线经理审批 */
							if (status
									.equalsIgnoreCase(ItemUtil.STATUS_CPXJLSP)) {
								this.setFieldFlag(readonly,
										GWConstant.S_READONLY, true);
								this.setFieldFlag("PROJECT",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("DELIVERYWAY",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_READONLY, false);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_REQUIRED, true);
								this.setFieldFlag("MODEL",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("SAPPONUM",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("ELECTRONNUM",
										GWConstant.S_READONLY, true);
								mrlineSet.setFlag(GWConstant.S_READONLY, true);
							}
							/* 状态为配件专员执行 */
							if (status.equalsIgnoreCase(ItemUtil.STATUS_PJZYZX)) {
								this.setFieldFlag(readonly,
										GWConstant.S_READONLY, true);
								this.setFieldFlag("PROJECT",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("DELIVERYWAY",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("SAPPONUM",
										GWConstant.S_READONLY, false);
								this.setFieldFlag("SAPPONUM",
										GWConstant.S_REQUIRED, true);
								this.setFieldFlag("ELECTRONNUM",
										GWConstant.S_READONLY, false);
								this.setFieldFlag("ELECTRONNUM",
										GWConstant.S_REQUIRED, true);
								this.setFieldFlag("MODEL",
										GWConstant.S_READONLY, true);
								mrlineSet.setFlag(GWConstant.S_READONLY, true);
							}
							/* 状态为计划经理修改 */
							if (status.equalsIgnoreCase(ItemUtil.STATUS_JHJLXG)) {
								this.setFieldFlag("MODEL",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("SAPPONUM",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("ELECTRONNUM",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("DELIVERYWAY",
										GWConstant.S_READONLY, true);
								this.setFieldFlag("TOTALCOST",
										GWConstant.S_READONLY, true);
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据登陆人所属地点生成配件申请编号
	 * 
	 * @throws MroException
	 */
	@Override
	public void add() throws MroException {
		super.add();
		setValue("STATUS", "未处理");
		String loginid = getUserServer().getUserInfo().getLoginID();
		loginid = loginid.toUpperCase();
		String mrnum = this.getString("mrnum");
		IJpoSet deptset = MroServer.getMroServer().getJpoSet("SYS_DEPT",
				MroServer.getMroServer().getSystemUserServer());
		deptset.setUserWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
				+ loginid + "')");
		deptset.reset();
		if (!deptset.isEmpty()) {
			String deptnum = deptset.getJpo(0).getString("deptnum");
			String retrun = WorkorderUtil.getAbbreviationByDeptnum(deptnum);
			if (retrun.equalsIgnoreCase("PJGL")) {
				mrnum = "ZX-" + "PJSQ" + "-" + mrnum;
			} else {
				mrnum = retrun + "-" + "PJSQ" + "-" + mrnum;
			}
		} else {
			mrnum = "ZX-" + "PJSQ" + "-" + mrnum;
		}
		/* 20181205-xlb-判断登陆人是中心还是现场人员，中心默认为项目，现场默认为零星 */
		this.setValue("mrnum", mrnum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		IJpoSet siteset = MroServer.getMroServer().getJpoSet("PERSONDEPTMAP",
				MroServer.getMroServer().getSystemUserServer());
		siteset.setUserWhere("PERSONID='" + loginid + "'");
		siteset.reset();
		if (siteset.isEmpty()) {
			setValue("MRTYPE", "项目");
		} else {
			setValue("MRTYPE", "零星");
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
		IJpoSet ahstatusset = getJpoSet("MRSTATUSHISTORY");
		IJpo ahstatusnew = ahstatusset.addJpo();
		ahstatusnew.setValue("MRNUM", getString("MRNUM"),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		ahstatusnew.setValue("STATUS", oldstatus,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		ahstatusnew.setValue("MEMO", memo, 11L);
		ahstatusnew.setFlag(7L, true);
	}

	/**
	 * 级联删除相关数据
	 * 
	 * @param flag
	 * @throws MroException
	 */
	@Override
	public void delete(long flag) throws MroException {
		getJpoSet("MRSTATUSHISTORY").deleteAll(
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		getJpoSet("MRLINE").deleteAll();
		super.delete(flag);
	}
}
