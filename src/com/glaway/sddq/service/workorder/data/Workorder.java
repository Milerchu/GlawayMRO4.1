package com.glaway.sddq.service.workorder.data;

import java.util.Date;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 工单JPO类
 * 
 * @author hzhu
 * @version [MRO4.0, 2018-5-4]
 * @since [MRO4.0/模块版本]
 */
public class Workorder extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void add() throws MroException {

		super.add();
		String appname = this.getAppName();
		if (StringUtil.isStrNotEmpty(appname)) {
			if ("SERVORDER".equalsIgnoreCase(appname)) {
				this.setValue("type", "服务");
				if (isNew()) {
					setFieldFlag("CARNUM", GWConstant.S_READONLY, true);
				}
			} else if ("FAILUREORD".equalsIgnoreCase(appname)) {
				this.setValue("type", "故障");

				if (isNew()) {
					setFieldFlag("ISREADSAFEREQ", GWConstant.S_READONLY, true);
					setFieldFlag("CARNUM", GWConstant.S_READONLY, true);
				}
			} else if ("TRANSORDER".equalsIgnoreCase(appname)) {
				this.setValue("type", "改造");
			} else {
				this.setValue("type", "验证");
			}
		}

	}

	@Override
	public void init() throws MroException {

		super.init();
		String status = getString("status");
		String type = getString("type");// 工单类型

		try {

			if ("服务".equals(type)) {

				setServOrderAuth(status);

			} else if ("故障".equals(type)) {

				setFailOrderAuth(status);

			} else if ("改造".equals(type)) {

				setTransOrderAuth(status);

			} else if ("验证".equals(type)) {

				setValiOrderAuth(status);

			}
			// 关闭状态只读
			if (SddqConstant.WO_STATUS_GB.equals(status)) {
				setFlag(GWConstant.S_READONLY, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * 到达现场
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void arrival() throws MroException {
		if (getString("status").equals("处理中")) {

			if (getDate("ARRIVETIME") != null) {
				throw new MroException("", "已经到达现场!");
			}
			Date arrivaldate = MroServer.getMroServer().getDate();
			setValue("ARRIVETIME", arrivaldate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpoSet().save();
		} else {
			throw new MroException("", "当前状态无法操作！");
		}
	}

	/**
	 * 
	 * 开始作业
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void startwork() throws MroException {
		if (getString("status").equals("处理中")) {
			if (getDate("OPERATETIME") != null) {
				throw new MroException("", "已经开始作业!");
			}
			Date operadate = MroServer.getMroServer().getDate();
			setValue("OPERATETIME", operadate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpoSet().save();
		} else if ("挂起".equals(getString("status"))) {
			// 更新挂起记录表中挂起结束时间字段
			String ordernum = getString("ORDERNUM");
			IJpoSet holdRecordSet = MroServer.getMroServer().getJpoSet(
					"HOLDRECORD", getUserServer());
			holdRecordSet.setUserWhere("FAILUREORDERNUM='" + ordernum
					+ "' and endtime is null");
			holdRecordSet.reset();
			if (!holdRecordSet.isEmpty()) {
				IJpo holdrecord = holdRecordSet.getJpo(0);
				holdrecord.setValue("endtime", MroServer.getMroServer()
						.getDate(), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				holdRecordSet.save();
			}
			// 修改状态为处理中
			setValue("STATUS", "处理中", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpoSet().save();

		} else {
			throw new MroException("", "当前状态无法操作!");
		}
		// 是否阅读故障管理要求只在处理完成后必填
		setFieldFlag("ISREADSAFEREQ", GWConstant.S_READONLY, false);
		setFieldFlag("ISREADSAFEREQ", GWConstant.S_REQUIRED, true);
	}

	/**
	 * 
	 * 设置服务工单字段权限
	 * 
	 * @param status
	 *            状态
	 * @throws Exception
	 */
	private void setServOrderAuth(String status) throws Exception {

		String servordertype = getString("SERVORDERTYPE");// 服务工单类型
		// 服务工单所有可编辑字段
		String[] allAttrs = { "SERVORDERTYPE", "CALLTIME", "PROJECTNUM",
				"SERVCOMPANY", "WHICHOFFICE", "SERVCOMCONTACTOR",
				"SERVENGINEER", "MANAGEMENTREQ", "DISPATCHREASON", "REMARK",
				"CARNUM" };
		// 设置所有字段只读
		setFieldFlag(allAttrs, GWConstant.S_READONLY, true);
		getJpoSet("JXTASKEXECPERSON").setFlag(GWConstant.S_READONLY, true);

		if (toBeAdded() || "草稿".equals(status)) {// 新增或草稿状态

			// 草稿下可编辑字段
			String[] cgAttrs = { "SERVORDERTYPE", "CALLTIME", "PROJECTNUM",
					"SERVCOMPANY", "SERVCOMCONTACTOR", "SERVENGINEER",
					"MANAGEMENTREQ", "DISPATCHREASON", "WHICHOFFICE", "CARNUM" };
			setFieldFlag(cgAttrs, GWConstant.S_READONLY, false);// 只读取消
			getJpoSet("JXTASKEXECPERSON").setFlag(GWConstant.S_READONLY, true);
			setFieldFlag("remark", GWConstant.S_READONLY, true);// 工作汇报只读

			if (SddqConstant.SO_TYPE_TSJC.equals(servordertype)) {// 调试交车类型

				setFieldFlag("CARNUM", GWConstant.S_REQUIRED, true);// 车号必填

			} else if (SddqConstant.SO_TYPE_HWDC.equals(servordertype)) {// 货物点收类型

				setFieldFlag("CARNUM", GWConstant.S_READONLY, true);// 车号只读

			}

		} else {

			if (WfControlUtil.isCurUser(this)) {// 判断当前登录人是否是工作流执行人

				if (SddqConstant.WO_STATUS_CXPG.equals(status)) {// 重新派工

					String[] attrs = { "SERVORDERTYPE", "CALLTIME",
							"PROJECTNUM", "SERVCOMPANY", "SERVCOMCONTACTOR",
							"SERVENGINEER", "MANAGEMENTREQ", "DISPATCHREASON",
							"WHICHOFFICE" };
					setFieldFlag(attrs, GWConstant.S_READONLY, false);// 只读取消

				} else if (SddqConstant.WO_STATUS_CLZ.equals(status)) {// 处理中

					setFieldFlag("REMARK", GWConstant.S_READONLY, false);// 工作汇报只读取消
					setFieldFlag("CARNUM", GWConstant.S_READONLY, false);// 车号只读取消

					if (!(SddqConstant.SO_TYPE_HWDC.equals(servordertype) || SddqConstant.SO_TYPE_TSJC
							.equals(servordertype))) {// 非 调试交车、货物点收时

						setFieldFlag("CARNUM", GWConstant.S_REQUIRED, true);

					}

					// 工作汇报必填
					if (getDate("ARRIVETIME") != null
							&& getDate("OPERATETIME") != null) {// 到达现场和开始作业不为空时

						setFieldFlag("REMARK", GWConstant.S_REQUIRED, true);

					}
					// 执行人工子表
					getJpoSet("JXTASKEXECPERSON").setFlag(
							GWConstant.S_READONLY, false);

				} else if (SddqConstant.WO_STATUS_SHBH.equals(status)) {// 审核驳回

					setFieldFlag("REMARK", GWConstant.S_READONLY, false);// 工作汇报只读取消
					setFieldFlag("CARNUM", GWConstant.S_READONLY, false);// 车号只读取消
					// 执行人工子表
					getJpoSet("JXTASKEXECPERSON").setFlag(
							GWConstant.S_READONLY, false);

					if (getDate("ARRIVETIME") != null
							&& getDate("OPERATETIME") != null) {// 到达现场和开始作业不为空时

						setFieldFlag("REMARK", GWConstant.S_REQUIRED, true);// 工作汇报必填

					}
				}

			} else {

				setFieldFlag(allAttrs, GWConstant.S_READONLY, true);// 所有字段只读

			}

		}

	}

	/**
	 * 
	 * 设置故障工单字段权限
	 * 
	 * @param status
	 *            状态
	 */
	private void setFailOrderAuth(String status) throws Exception {

		// 工单所有可编辑字段
		String[] allAttrs = { "SERVORDERTYPE", "CALLTIME", "PROJECTNUM",
				"SERVCOMPANY", "SERVCOMCONTACTOR", "SERVENGINEER",
				"MANAGEMENTREQ", "DISPATCHREASON", "REMARK", "WHICHSTATION",
				"BACKTEL", "ISREADSAFEREQ" };
		String[] faultAttrs = { "FAULTCOMPONENTSQN", "FAULTCOMPLOTNUM",
				"FAULTCOMPDEALMODE", "ISMAINFAULTCOMP", "ISNOTICE",
				"ISTECHANALYZE" };
		// 处理方式
		String dealmethod = getString("FAILURELIB.DEALMETHOD");
		// 所有子表只读
		getJpoSet("CHECKOBJECT").setFlag(GWConstant.S_READONLY, true);// 现场故障排查子表
		getJpoSet("DOCLINKS2").setFlag(GWConstant.S_READONLY, true);// 故障数据包子表
		getJpoSet("FAULTDIAGNOSE").setFlag(GWConstant.S_READONLY, true);// 故障诊断子表
		getJpoSet("JXTASKLOSSPART").setFlag(GWConstant.S_READONLY, true);// 耗损件子表
		getJpoSet("JXTASKTOOLS").setFlag(GWConstant.S_READONLY, true);// 工具子表
		getJpoSet("JXTASKEXECPERSON").setFlag(GWConstant.S_READONLY, true);// 执行人工子表
		getJpoSet("CHASSET").setFlag(GWConstant.S_READONLY, true);// 串换记录子表

		// 设置所有字段只读
		setFieldFlag(allAttrs, GWConstant.S_READONLY, true);
		setFieldFlag(faultAttrs, GWConstant.S_READONLY, true);
		setFieldFlag("CARNUM", GWConstant.S_READONLY, true);// 设置车号只读
		if (SddqConstant.WO_STATUS_CG.equals(status)) {// 新增或草稿状态
			// 草稿下可编辑字段
			String[] cgAttrs = { "SERVORDERTYPE", "CALLTIME", "PROJECTNUM",
					"SERVCOMPANY", "SERVCOMCONTACTOR", "SERVENGINEER",
					"MANAGEMENTREQ", "DISPATCHREASON", "WHICHSTATION",
					"BACKTEL" };
			setFieldFlag(cgAttrs, GWConstant.S_READONLY, false);// 只读取消

		} else {

			/** 判断当前登录人是否在管理员权限组 */
			String loginId = this.getUserInfo().getLoginID();
			boolean hasAuth = WorkorderUtil.isInAdminGroup(loginId);

			if (WfControlUtil.isCurUser(this) || hasAuth) {// 判断当前登录人是否是工作流执行人

				if (SddqConstant.WO_STATUS_CXPG.equals(status)) {// 重新派工

					// 可编辑字段
					String[] attrs = { "CALLTIME", "PROJECTNUM",
							"SERVCOMCONTACTOR", "SERVENGINEER",
							"MANAGEMENTREQ", "DISPATCHREASON", "WHICHSTATION",
							"BACKTEL" };
					setFieldFlag(attrs, GWConstant.S_READONLY, false);// 只读取消

				} else if (SddqConstant.WO_STATUS_CLZ.equals(status)) {// 处理中

					// 是否阅读故障管理要求只在作业开始后必填
					if (getDate("OPERATETIME") != null) {
						setFieldFlag("ISREADSAFEREQ", GWConstant.S_READONLY,
								false);
						setFieldFlag("ISREADSAFEREQ", GWConstant.S_REQUIRED,
								true);
						setFieldFlag("WHICHSTATION", GWConstant.S_READONLY,
								false);// 故障处理站点只读取消
						setFieldFlag("WHICHSTATION", GWConstant.S_REQUIRED,
								true);// 设必填
					}
					setFieldFlag("REMARK", GWConstant.S_READONLY, false);// 工作汇报只读取消
					// 车号只读取消
					setFieldFlag("CARNUM", GWConstant.S_REQUIRED, true);// 设必填

					// 子表权限放开
					getJpoSet("CHASSET").setFlag(GWConstant.S_READONLY, false);// 串换子表
					getJpoSet("CHECKOBJECT").setFlag(GWConstant.S_READONLY,
							false);// 现场故障排查子表
					getJpoSet("DOCLINKS2")
							.setFlag(GWConstant.S_READONLY, false);// 故障数据包子表
					getJpoSet("FAULTDIAGNOSE").setFlag(GWConstant.S_READONLY,
							false);// 故障诊断子表
					// 故障件信息字段只读取消
					setFieldFlag(faultAttrs, GWConstant.S_READONLY, false);
					if (SddqConstant.FAIL_DEALMETHOD_HJCL.equals(dealmethod)
							|| SddqConstant.FAIL_DEALMETHOD_CHANDGH
									.equals(dealmethod)) {// 换件处理
						getJpoSet("JXTASKLOSSPART").setFlag(
								GWConstant.S_READONLY, false);// 耗损件子表
						setFieldFlag(faultAttrs, GWConstant.S_READONLY, true);
					} else {// 非换件处理
						if (WorkorderUtil
								.isImpFault(getString("FAILURELIB.FAULTCONSEQ"))) {// 是否高级故障后果
							// 将是否通报、是否技术分析勾选并只读
							setValue("ISNOTICE", 1,
									GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
							setValue("ISTECHANALYZE", 1,
									GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
							setFieldFlag("ISNOTICE", GWConstant.S_READONLY,
									true);
							setFieldFlag("ISTECHANALYZE",
									GWConstant.S_READONLY, true);
						}
					}
					getJpoSet("JXTASKTOOLS").setFlag(GWConstant.S_READONLY,
							false);// 工具子表
					getJpoSet("JXTASKEXECPERSON").setFlag(
							GWConstant.S_READONLY, false);// 执行人工子表

				} else if (SddqConstant.WO_STATUS_SHBH.equals(status)
						|| SddqConstant.WO_STATUS_JSZGBH.equals(status)
						|| SddqConstant.WO_STATUS_ZZKGYBH.equals(status)) {
					// 技术主管驳回、审核驳回、专职库管员驳回

					// 是否阅读故障管理要求只在作业开始后必填
					if (getDate("OPERATETIME") != null) {
						setFieldFlag("ISREADSAFEREQ", GWConstant.S_READONLY,
								false);
						setFieldFlag("ISREADSAFEREQ", GWConstant.S_REQUIRED,
								true);
						getJpoSet("CHASSET").setFlag(GWConstant.S_READONLY,
								false);// 串换子表
						getJpoSet("DOCLINKS2").setFlag(GWConstant.S_READONLY,
								false);// 故障数据包子表
						getJpoSet("FAULTDIAGNOSE").setFlag(
								GWConstant.S_READONLY, false);// 故障诊断子表
						// 故障件信息字段只读取消
						setFieldFlag(faultAttrs, GWConstant.S_READONLY, false);
						if (StringUtil.isStrEmpty(getString("ERPSTATUS"))
								&& (SddqConstant.FAIL_DEALMETHOD_HJCL
										.equals(dealmethod) || SddqConstant.FAIL_DEALMETHOD_CHANDGH
										.equals(dealmethod))) {// 未获取三包信息
							getJpoSet("JXTASKLOSSPART").setFlag(
									GWConstant.S_READONLY, false);// 耗损件子表
							setFieldFlag(faultAttrs, GWConstant.S_READONLY,
									true);
						}

						// 非换件处理
						if (!(SddqConstant.FAIL_DEALMETHOD_HJCL
								.equals(dealmethod) || SddqConstant.FAIL_DEALMETHOD_CHANDGH
								.equals(dealmethod))) {
							if (WorkorderUtil
									.isImpFault(getString("FAILURELIB.FAULTCONSEQ"))) {// 是否高级故障后果
								// 将是否通报、是否技术分析勾选并只读
								setValue("ISNOTICE", 1,
										GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
								setValue("ISTECHANALYZE", 1,
										GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
								setFieldFlag("ISNOTICE", GWConstant.S_READONLY,
										true);
								setFieldFlag("ISTECHANALYZE",
										GWConstant.S_READONLY, true);
							}
						}
					}

					IJpo failLib = getJpoSet("failurelib").getJpo();
					boolean exchangeFlag = false;
					if (failLib != null) {
						exchangeFlag = failLib.getJpoSet("EXCHANGERECORD") != null
								&& failLib.getJpoSet("EXCHANGERECORD").count() > 0;
					}
					boolean consumeFlag = getJpoSet("JXTASKLOSSPART") != null
							&& getJpoSet("JXTASKLOSSPART").count() > 0;
					if (consumeFlag || exchangeFlag) {// 上下车记录有数据
						setFieldFlag("WHICHSTATION", GWConstant.S_REQUIRED,
								false);
						setFieldFlag("WHICHSTATION", GWConstant.S_READONLY,
								true);
					} else {// 上下车记录无数据才可修改故障站点
						setFieldFlag("WHICHSTATION", GWConstant.S_READONLY,
								false);
						setFieldFlag("WHICHSTATION", GWConstant.S_REQUIRED,
								true);
					}

				} else if (SddqConstant.WO_STATUS_KGYBH.equals(status)) {// 库管员驳回

					// 是否阅读故障管理要求只在作业开始后必填
					if (getDate("OPERATETIME") != null) {
						setFieldFlag("ISREADSAFEREQ", GWConstant.S_READONLY,
								false);
						setFieldFlag("ISREADSAFEREQ", GWConstant.S_REQUIRED,
								true);

					}
					// 故障件信息字段只读取消
					setFieldFlag(faultAttrs, GWConstant.S_READONLY, false);
					if (SddqConstant.FAIL_DEALMETHOD_HJCL.equals(dealmethod)
							|| SddqConstant.FAIL_DEALMETHOD_CHANDGH
									.equals(dealmethod)) {
						getJpoSet("JXTASKLOSSPART").setFlag(
								GWConstant.S_READONLY, false);// 耗损件子表
						setFieldFlag(faultAttrs, GWConstant.S_READONLY, true);
					} else {// 非换件处理
						if (WorkorderUtil
								.isImpFault(getString("FAILURELIB.FAULTCONSEQ"))) {// 是否高级故障后果
							// 将是否通报、是否技术分析勾选并只读
							setValue("ISNOTICE", 1,
									GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
							setValue("ISTECHANALYZE", 1,
									GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
							setFieldFlag("ISNOTICE", GWConstant.S_READONLY,
									true);
							setFieldFlag("ISTECHANALYZE",
									GWConstant.S_READONLY, true);
						}
					}
					getJpoSet("CHASSET").setFlag(GWConstant.S_READONLY, false);// 串换子表
					getJpoSet("DOCLINKS2")
							.setFlag(GWConstant.S_READONLY, false);// 故障数据包子表
					getJpoSet("FAULTDIAGNOSE").setFlag(GWConstant.S_READONLY,
							false);// 故障诊断子表

				} else if (SddqConstant.WO_STATUS_JSZGSH.equals(status)) {

					getJpoSet("JXTASKLOSSPART").setFlag(GWConstant.S_READONLY,
							false);// 耗损件子表

				}

			} else {

				setFieldFlag(allAttrs, GWConstant.S_READONLY, true);// 所有字段只读

			}

		}

	}

	/**
	 * 
	 * 设置改造工单字段权限
	 * 
	 * @param status
	 *            状态
	 */
	private void setTransOrderAuth(String status) throws Exception {

		// 基数属性
		String[] basicAttr = { "CARNUM", "ISFIRST", "FIRSTCONDITION",
				"ENGINEERS", "CHECKPERSONS", "REMARK", "ACTSTARTDATE",
				"ACTCOMPLETEDATE" };
		String isplancraet = getString("ISPLANCRAET");

		setFieldFlag(basicAttr, GWConstant.S_READONLY, true);
		getJpoSet("JXTASKLOSSPART").setFlag(GWConstant.S_READONLY, true);
		getJpoSet("JXTASKTOOLS").setFlag(GWConstant.S_READONLY, true);
		getJpoSet("JXTASKEXECPERSON").setFlag(GWConstant.S_READONLY, true);
		getJpoSet("EXCHANGERECORD").setFlag(GWConstant.S_READONLY, true);

		if (WfControlUtil.isCurUser(this)) {

			if ("处理中".equals(status)) {

				setFieldFlag(basicAttr, GWConstant.S_READONLY, false);

				setFieldFlag(new String[] { "CARNUM" }, GWConstant.S_REQUIRED,
						true);
				// zzx add 9.19
				if (isplancraet.equals("1")) {
					setFieldFlag(new String[] { "CARNUM" },
							GWConstant.S_REQUIRED, false);
					setFieldFlag("CARNUM", GWConstant.S_READONLY, true);
				}
				// zzx add end
				// 首台改造
				IJpoSet distSet = this.getJpoSet("TRANSDIST");
				if (!distSet.isEmpty()) {
					IJpo dist = distSet.getJpo(0);
					if (dist.getBoolean("HASFIRSTTRAIN")) {// 已经存在首台车，则其他工单该字段只读

						setFieldFlag(
								new String[] { "ISFIRST", "FIRSTCONDITION" },
								GWConstant.S_READONLY, true);
					}

				}
				getJpoSet("EXCHANGERECORD").setFlag(GWConstant.S_READONLY,
						false);
				getJpoSet("JXTASKLOSSPART").setFlag(GWConstant.S_READONLY,
						false);
				getJpoSet("JXTASKTOOLS").setFlag(GWConstant.S_READONLY, false);
				getJpoSet("JXTASKEXECPERSON").setFlag(GWConstant.S_READONLY,
						false);

			} else if ("异常".equals(status)) {

				setFieldFlag("remark", GWConstant.S_READONLY, false);

			} else if ("关闭".equals(status)) {

				setFlag(GWConstant.S_READONLY, true);

			}
		} else {
			if ("草稿".equals(status)) {

				setFieldFlag(basicAttr, GWConstant.S_READONLY, false);
				// zzx add 9.19
				if (isplancraet.equals("1")) {

					setFieldFlag("CARNUM", GWConstant.S_READONLY, true);
				}

				// zzx add end
				// 草稿时不可编辑上下车子表
				String[] transAttr = { "ACTSTARTDATE", "ACTCOMPLETEDATE" };
				setFieldFlag(transAttr, GWConstant.S_READONLY, true);
				getJpoSet("JXTASKEXECPERSON").setFlag(GWConstant.S_READONLY,
						false);
			}
		}

	}

	/**
	 * 
	 * 设置验证工单字段权限
	 * 
	 * @param status
	 *            状态
	 */
	private void setValiOrderAuth(String status) throws Exception {

		// 工单所有可编辑字段
		String[] allAttrs = { "CARNUM", "ACTCOMPLETEDATE", "CHECKPERSON",
				"ISERROR", "REMARK" };
		IJpoSet exchangeSet = getJpoSet("EXCHANGERECORD");// 验证产品信息子表
		IJpoSet toolSet = getJpoSet("JXTASKTOOLS");// 工具子表
		IJpoSet personSet = getJpoSet("JXTASKEXECPERSON");// 处理人子表
		// 设置所有只读
		setFieldFlag(allAttrs, GWConstant.S_READONLY, true);
		exchangeSet.setFlag(GWConstant.S_READONLY, true);
		toolSet.setFlag(GWConstant.S_READONLY, true);
		personSet.setFlag(GWConstant.S_READONLY, true);
		if (SddqConstant.WO_STATUS_CG.equals(status)) {// 草稿状态

			setFieldFlag(allAttrs, GWConstant.S_READONLY, false);
			personSet.setFlag(GWConstant.S_READONLY, false);

		} else if (SddqConstant.WO_STATUS_CLZ.equals(status)) {// 处理中状态

			setFieldFlag(allAttrs, GWConstant.S_READONLY, false);// 解除只读限制
			setFieldFlag(new String[] { "carnum", "CHECKPERSON" },
					GWConstant.S_REQUIRED, true);// 设置必填字段
			exchangeSet.setFlag(GWConstant.S_READONLY, false);
			toolSet.setFlag(GWConstant.S_READONLY, false);
			personSet.setFlag(GWConstant.S_READONLY, false);

		}

	}

	/**
	 * 复制工单，仅复制派工信息中的部分字段
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpo duplicate() throws MroException {
		IJpo dupJpo = super.duplicate();

		// 生成新的工单编号
		dupJpo.setValue("servcompany", getString("servcompany"));
		dupJpo.setValue("STATUS", "草稿");
		dupJpo.setValue("REPORTER", getUserInfo().getPersonId());
		dupJpo.setValueNull("DISPATCHREASON");
		dupJpo.setValue("ISREADSAFEREQ", "否",
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		dupJpo.setValueNull("VBELNS");
		dupJpo.setValueNull("VBELNSSTATUS");
		dupJpo.setValueNull("VBELN");
		dupJpo.setValueNull("VBELNSTATUS");
		dupJpo.setValueNull("MESSAGE");
		dupJpo.setValueNull("MESSAGESTATUS");
		dupJpo.setValueNull("ERPSTATUS");
		dupJpo.setValueNull("SALES");
		dupJpo.setValueNull("OPERATETIME");
		dupJpo.setValueNull("CLOSETIME");
		dupJpo.setValueNull("ARRIVETIME");
		dupJpo.setValueNull("RESPONSETIME");
		dupJpo.setValueNull("QMS_NUM");
		dupJpo.setValueNull("REMARK");
		dupJpo.setValueNull("DISPATCHTIME");
		return dupJpo;
	}
}
