package com.glaway.sddq.material.itemreq.data;

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
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 领料单Jpo
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-8]
 * @since [GlawayMro4.0/领料单]
 */
public class MPR extends StatusJpo implements IStatusJpo, FixedLoggers {
	private static final long serialVersionUID = 1L;

	/**
	 * 根据状态设置页面控制
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		if (!this.isNew()) {
			String status = getString("status");
			if (this.getAppName() != null
					&& this.getAppName().equals("GZITEMREQ")) {// 服务专改造领料单
				String personid = this.getUserInfo().getLoginID();
				personid = personid.toUpperCase();
				String APPLICANTBY = this.getString("APPLICANTBY");
				if (status.equalsIgnoreCase("草稿")) {
					if (!personid.equalsIgnoreCase(APPLICANTBY)) {
						this.setFlag(GWConstant.S_READONLY, true);
					}
				}
				if (status.equalsIgnoreCase("售后计划经理审核")) {
					try {
						if (!WfControlUtil.isCurUser(this)) { /* 当前登陆人不是流程审批人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {
							this.setFlag(GWConstant.S_READONLY, true);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (status.equalsIgnoreCase("库管员审核")) {
					try {
						if (!WfControlUtil.isCurUser(this)) { /* 当前登陆人不是流程审批人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {
							String[] readlonystr = { "TYPE", "TRANSPLANNUM",
									"MPRSTOREROOM", "COSTCENTER", "MEMO" };
							this.setFieldFlag(readlonystr,
									GWConstant.S_READONLY, true);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (status.equalsIgnoreCase("申请人修改")) {
					try {
						if (!WfControlUtil.isCurUser(this)) { /* 当前登陆人不是流程审批人 */
							this.setFlag(GWConstant.S_READONLY, true);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (status.equalsIgnoreCase("关闭")) {
					this.setFlag(GWConstant.S_READONLY, true);
				}

			}
			if (this.getAppName() != null
					&& this.getAppName().equals("ITEMREQ")) {// 领料单
				String personid = this.getUserInfo().getLoginID();
				personid = personid.toUpperCase();
				String APPLICANTBY = this.getString("APPLICANTBY");
				if (status.equalsIgnoreCase("草稿")) {
					if (!personid.equalsIgnoreCase(APPLICANTBY)) {
						this.setFlag(GWConstant.S_READONLY, true);
					}
				}
				if (status.equalsIgnoreCase("办事处主任审批")) {
					try {
						if (!WfControlUtil.isCurUser(this)) { /* 当前登陆人不是流程审批人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {
							this.setFlag(GWConstant.S_READONLY, true);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (status.equalsIgnoreCase("库管员审核")) {
					try {
						if (!WfControlUtil.isCurUser(this)) { /* 当前登陆人不是流程审批人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {
							String[] readlonystr = { "WORKORDERNUM",
									"MPRSTOREROOM", "MEMO" };
							this.setFieldFlag(readlonystr,
									GWConstant.S_READONLY, true);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (status.equalsIgnoreCase("申请人修改")) {
					try {
						if (!WfControlUtil.isCurUser(this)) { /* 当前登陆人不是流程审批人 */
							this.setFlag(GWConstant.S_READONLY, true);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (status.equalsIgnoreCase("关闭")) {
					this.setFlag(GWConstant.S_READONLY, true);
				}
			}
			if (this.getAppName() != null && this.getAppName().equals("JXMPR")) {// 检修领料单
				String personid = this.getUserInfo().getLoginID();
				personid = personid.toUpperCase();
				String APPLICANTBY = this.getString("APPLICANTBY");
				if (status.equalsIgnoreCase("草稿")) {
					if (!personid.equalsIgnoreCase(APPLICANTBY)) {
						this.setFlag(GWConstant.S_READONLY, true);
					}
				}
				if (status.equalsIgnoreCase("库管员审核")) {
					try {
						if (!WfControlUtil.isCurUser(this)) { /* 当前登陆人不是流程审批人 */
							this.setFlag(GWConstant.S_READONLY, true);
						} else {
							String[] readlonystr = { "TYPE", "TASKNUM",
									"PROJECTNUM", "MPRSTOREROOM", "WHYPICK",
									"MEMO", "PLAN" };
							this.setFieldFlag(readlonystr,
									GWConstant.S_READONLY, true);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (status.equalsIgnoreCase("申请人接收")) {
					this.setFlag(GWConstant.S_READONLY, true);
				}
				if (status.equalsIgnoreCase("申请人修改")) {
					try {
						if (!WfControlUtil.isCurUser(this)) { /* 当前登陆人不是流程审批人 */
							this.setFlag(GWConstant.S_READONLY, true);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (status.equalsIgnoreCase("关闭")) {
					this.setFlag(GWConstant.S_READONLY, true);
				}
			}
			// if (status.equalsIgnoreCase("确认领料")) {
			// setFieldFlag("DESCRIPTION", GWConstant.S_READONLY, true);
			// setFieldFlag("WORKORDERNUM", GWConstant.S_READONLY, true);
			// setFieldFlag("TYPE", GWConstant.S_READONLY, true);
			// setFieldFlag("MPRSTOREROOM", GWConstant.S_READONLY, true);
			// setFieldFlag("MEMO", GWConstant.S_READONLY, true);
			// this.getJpoSet("mprline").setFlag(GWConstant.S_READONLY, true);
			// }

			// 入库单--关飞飞
			// if (getString("mprtype").equals("JS") && !isNew() &&
			// getString("status").equals("已接收")) {
			// String[] attribute = { "MEMO",
			// "MPRSTOREROOM","ENDTIME","RECIVEBY" };
			// this.setFieldFlag(attribute, 7L, true);
			// }
			// if (getString("mprtype").equals("JS") && !isNew() &&
			// getJpoSet("$mprline", "mprline",
			// "MPRNUM=:MPRNUM and status='已接收'").count() > 0) {
			// String[] attribute = { "MPRSTOREROOM" };
			// this.setFieldFlag(attribute, 7L, true);
			// }
			/* 入库单初始化控制 */
			if (this.getAppName() != null
					&& this.getAppName().equals("JSITEMREQ")) {
				String personid = this.getUserInfo().getLoginID();
				personid = personid.toUpperCase();
				String APPLICANTBY = this.getString("APPLICANTBY");/* 申请人 */
				String RECIVEBY = this.getString("RECIVEBY");/* 接收人 */
				if (status.equalsIgnoreCase("草稿")) {
					if (!personid.equalsIgnoreCase(APPLICANTBY)) {
						if (personid.equalsIgnoreCase(RECIVEBY)) {
							String[] readonlystr = { "MEMO", "MPRSTOREROOM",
									"PROJECTNUM", "RECIVEBY", "ENDTIME" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
						} else {
							this.setFlag(GWConstant.S_READONLY, true);
						}
					}
				}
				if (status.equalsIgnoreCase("部分接收")) {
					if (!personid.equalsIgnoreCase(APPLICANTBY)) {
						if (personid.equalsIgnoreCase(RECIVEBY)) {
							String[] readonlystr = { "MEMO", "MPRSTOREROOM",
									"PROJECTNUM", "RECIVEBY", "ENDTIME" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
						} else {
							this.setFlag(GWConstant.S_READONLY, true);
						}
					} else {
						this.setFlag(GWConstant.S_READONLY, true);
					}
				}
				if (status.equalsIgnoreCase("已接收")) {
					this.setFlag(GWConstant.S_READONLY, true);
				}
				if (status.equalsIgnoreCase("已关闭")) {
					this.setFlag(GWConstant.S_READONLY, true);
				}
			}
			
			if (this.getAppName() != null
					&& this.getAppName().equals("TKITEMREQ")) {
				String personid = this.getUserInfo().getLoginID();
				personid = personid.toUpperCase();
				String APPLICANTBY = this.getString("APPLICANTBY");/* 申请人 */
				String RECIVEBY = this.getString("RECIVEBY");/* 接收人 */
				if (status.equalsIgnoreCase("草稿")) {
					if (!personid.equalsIgnoreCase(APPLICANTBY)) {
						if (personid.equalsIgnoreCase(RECIVEBY)) {
							String[] readonlystr = { "MEMO", "MPRSTOREROOM",
									"PROJECTNUM", "RECIVEBY", "ENDTIME" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
						} else {
							this.setFlag(GWConstant.S_READONLY, true);
						}
					}
				}
				if (status.equalsIgnoreCase("部分退库")) {
					if (!personid.equalsIgnoreCase(APPLICANTBY)) {
						if (personid.equalsIgnoreCase(RECIVEBY)) {
							String[] readonlystr = { "MEMO", "MPRSTOREROOM",
									"PROJECTNUM", "RECIVEBY", "ENDTIME" };
							this.setFieldFlag(readonlystr,
									GWConstant.S_READONLY, true);
						} else {
							this.setFlag(GWConstant.S_READONLY, true);
						}
					} else {
						this.setFlag(GWConstant.S_READONLY, true);
					}
				}
				if (status.equalsIgnoreCase("已退库")) {
					this.setFlag(GWConstant.S_READONLY, true);
				}
			}
		}
	}

	/**
	 * 根据登录人办事处设置领料单编号
	 * 
	 * @throws MroException
	 */
	@Override
	public void add() throws MroException {
		super.add();
		if (toBeAdded()) {
			setValue("STATUS", "草稿");
			String loginid = getUserServer().getUserInfo().getLoginID();
			loginid = loginid.toUpperCase();
			if (this.getAppName() != null
					&& this.getAppName().equals("JSITEMREQ")) {//入库单
				String mprnum = this.getString("mprnum");
				IJpoSet deptset = MroServer.getMroServer().getJpoSet(
						"SYS_DEPT",
						MroServer.getMroServer().getSystemUserServer());
				deptset.setUserWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
						+ loginid + "')");
				deptset.reset();
				if (!deptset.isEmpty()) {
					String deptnum = deptset.getJpo(0).getString("deptnum");
					String retrun = WorkorderUtil
							.getAbbreviationByDeptnum(deptnum);
					if (retrun.equalsIgnoreCase("PJGL")) {
						mprnum = "ZX-" + "RK" + "-" + mprnum;
					} else {
						mprnum = retrun + "-" + "RK" + "-" + mprnum;
					}
				} else {
					mprnum = "ZX-" + "RK" + "-" + mprnum;
				}
				this.setValue("mprnum", mprnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			
			if (this.getAppName() != null
					&& this.getAppName().equals("TKITEMREQ")) {//退库单
				String mprnum = this.getString("mprnum");
				IJpoSet deptset = MroServer.getMroServer().getJpoSet(
						"SYS_DEPT",
						MroServer.getMroServer().getSystemUserServer());
				deptset.setUserWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
						+ loginid + "')");
				deptset.reset();
				if (!deptset.isEmpty()) {
					String deptnum = deptset.getJpo(0).getString("deptnum");
					String retrun = WorkorderUtil
							.getAbbreviationByDeptnum(deptnum);
					if (retrun.equalsIgnoreCase("PJGL")) {
						mprnum = "ZX-" + "TK" + "-" + mprnum;
					} else {
						mprnum = retrun + "-" + "TK" + "-" + mprnum;
					}
				} else {
					mprnum = "ZX-" + "TK" + "-" + mprnum;
				}
				this.setValue("mprnum", mprnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			if (this.getAppName() != null
					&& this.getAppName().equals("QTITEMREQ")) {//客户料退库领料单
				String mprnum = this.getString("mprnum");
				IJpoSet deptset = MroServer.getMroServer().getJpoSet(
						"SYS_DEPT",
						MroServer.getMroServer().getSystemUserServer());
				deptset.setUserWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
						+ loginid + "')");
				deptset.reset();
				if (!deptset.isEmpty()) {
					String deptnum = deptset.getJpo(0).getString("deptnum");
					String retrun = WorkorderUtil
							.getAbbreviationByDeptnum(deptnum);
					if (retrun.equalsIgnoreCase("PJGL")) {
						mprnum = "ZX-" + "QTLL" + "-" + mprnum;
					} else {
						mprnum = retrun + "-" + "QTLL" + "-" + mprnum;
					}
				} else {
					mprnum = "ZX-" + "QTLL" + "-" + mprnum;
				}
				this.setValue("mprnum", mprnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			if (this.getAppName() != null
					&& this.getAppName().equals("ITEMREQ")) {//领料单
				String mprnum = this.getString("mprnum");
				IJpoSet deptset = MroServer.getMroServer().getJpoSet(
						"SYS_DEPT",
						MroServer.getMroServer().getSystemUserServer());
				deptset.setUserWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
						+ loginid + "')");
				deptset.reset();
				if (!deptset.isEmpty()) {
					String deptnum = deptset.getJpo(0).getString("deptnum");
					String retrun = WorkorderUtil
							.getAbbreviationByDeptnum(deptnum);
					if (retrun.equalsIgnoreCase("PJGL")) {
						mprnum = "ZX-" + "LL" + "-" + mprnum;
					} else {
						mprnum = retrun + "-" + "LL" + "-" + mprnum;
					}
				} else {
					mprnum = "ZX-" + "LL" + "-" + mprnum;
				}
				this.setValue("mprnum", mprnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			if (this.getAppName() != null
					&& this.getAppName().equals("GZITEMREQ")) {
				String mprnum = this.getString("mprnum");
				IJpoSet deptset = MroServer.getMroServer().getJpoSet(
						"SYS_DEPT",
						MroServer.getMroServer().getSystemUserServer());
				deptset.setUserWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
						+ loginid + "')");
				deptset.reset();
				if (!deptset.isEmpty()) {
					String deptnum = deptset.getJpo(0).getString("deptnum");
					String retrun = WorkorderUtil
							.getAbbreviationByDeptnum(deptnum);
					if (retrun.equalsIgnoreCase("PJGL")) {
						mprnum = "ZX-" + "GZLL" + "-" + mprnum;
					} else {
						mprnum = retrun + "-" + "GZLL" + "-" + mprnum;
					}
				} else {
					mprnum = "ZX-" + "GZLL" + "-" + mprnum;
				}
				this.setValue("mprnum", mprnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			if (this.getAppName() != null && this.getAppName().equals("JXMPR")) {
				setValue("GDTYPE", "检修",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				String mprnum = this.getString("mprnum");
				IJpoSet deptset = MroServer.getMroServer().getJpoSet(
						"SYS_DEPT",
						MroServer.getMroServer().getSystemUserServer());
				deptset.setUserWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
						+ loginid + "')");
				deptset.reset();
				if (!deptset.isEmpty()) {
					String deptnum = deptset.getJpo(0).getString("deptnum");
					String retrun = WorkorderUtil
							.getAbbreviationByDeptnum(deptnum);
					if (retrun.equalsIgnoreCase("PJGL")) {
						mprnum = "ZX-" + "JXLL" + "-" + mprnum;
					} else {
						mprnum = retrun + "-" + "JXLL" + "-" + mprnum;
					}
				} else {
					mprnum = "ZX-" + "JXLL" + "-" + mprnum;
				}
				this.setValue("mprnum", mprnum,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
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
		IJpoSet ahstatusset = getJpoSet("MPRSTATUSHISTORY");
		IJpo ahstatusnew = ahstatusset.addJpo();
		ahstatusnew.setValue("MPRNUM", getString("MPRNUM"),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		ahstatusnew.setValue("STATUS", oldstatus,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		ahstatusnew.setValue("MEMO", memo, 11L);
		ahstatusnew.setFlag(7L, true);
	}

	/**
	 * 级联删除相关行信息
	 * 
	 * @param flag
	 * @throws MroException
	 */
	@Override
	public void delete(long flag) throws MroException {
		getJpoSet("MPRSTATUSHISTORY").deleteAll(
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		getJpoSet("MPRLINE").deleteAll(GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		super.delete(flag);
	}

}
