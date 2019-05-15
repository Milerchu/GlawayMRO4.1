package com.glaway.sddq.service.failurelib.data;

import java.util.Date;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.AppException;
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
 * 故障记录failurelib 主jpo类
 * 
 * @author zhuhao
 * @version [1.0, 2019年1月7日]
 * @since [产品/模块版本]
 */
public class Failurelib extends Jpo {
	/**
	 * 唯一序列
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();

		// 应用名称
		String appName = "";
		// 父级jpo
		IJpo parent = getParent();
		if (parent != null) {
			appName = getParent().getAppName();
		}

		try {
			// 故障工单应用
			if ("FAILUREORD".equalsIgnoreCase(appName)) {

				// 工单状态
				String status = getParent().getString("status");
				// 车型大类
				String productline = getParent()
						.getString("MODELS.PRODUCTLINE");
				// 处理方式
				String dealMethod = getString("dealmethod");
				// 数据包状态
				String dataStatus = getString("FAULTDATAREC");
				// 所有字段
				String[] allAttrs = { "CARSECTIONNUM", "PRODUCTNICKNAME",
						"FAILURECODE", "FAULTDESC", "PREREASONALYS",
						"DEALMEASURE", "FAULTTIME", "FINDPROCESS",
						"RUNNINGMODE", "DEALMETHOD", "FAULTQUALIT",
						"FAULTCONSEQ", "ANALYSISREPNEED", "FAULTLOCATION",
						"GDQJ", "ROADTYPE", "FAILWEATHER", "QYFZDW",
						"NODATAREASON" };
				// 上下车子表
				IJpoSet exchangeSet = getJpoSet("EXCHANGERECORD");
				// 耗损件子表
				IJpoSet losspartSet = getParent().getJpoSet("JXTASKLOSSPART");
				exchangeSet.setFlag(GWConstant.S_READONLY, true);
				// 将所有字段只读
				setFieldFlag(allAttrs, GWConstant.S_READONLY, true);
				// 必填字段
				String[] requiredAttrs = { "CARSECTIONNUM", "FAILURECODE",
						"FAULTTIME", "FINDPROCESS", "DEALMETHOD",
						"RUNNINGMODE", "FAULTQUALIT", "FAULTCONSEQ",
						"ANALYSISREPNEED", "QYFZDW", "ROADTYPE", "FAILWEATHER",
						"FAULTDESC", "PREREASONALYS", "DEALMEASURE" };

				/** 判断当前登录人是否在管理员权限组 */
				String loginId = this.getUserInfo().getLoginID();
				boolean hasAuth = WorkorderUtil.isInAdminGroup(loginId);

				if (WfControlUtil.isCurUser(parent) || hasAuth) {// 判断当前工作流执行人

					if (parent.getDate("OPERATETIME") != null) {// 开始作业后才可以填写

						// 处理中
						if (SddqConstant.WO_STATUS_CLZ.equals(status)) {

							setFieldFlag(allAttrs, GWConstant.S_READONLY, false);// 字段只读取消

							setFieldFlag(new String[] { "FAULTTIME",
									"PRODUCTNICKNAME", "FAULTCONSEQ",
									"CARSECTIONNUM", "FAILURECODE" },
									GWConstant.S_REQUIRED, true);// 必填字段
							if (parent.getDate("COMPLETETIME") != null) {// 处理完成后必填

								setFieldFlag(requiredAttrs,
										GWConstant.S_REQUIRED, true);// 必填字段

								if (WorkorderUtil.hasImpItem(exchangeSet,
										"ITEM")
										|| WorkorderUtil.hasImpItem(
												losspartSet, "DOWNITEMNUM")) {// 故障件中有重要物料
									if (StringUtil.isStrEmpty(dataStatus)
											|| "待下载".equals(dataStatus)) {// 数据包上传状态为空或待下载

										setFieldFlag("NODATAREASON",
												GWConstant.S_REQUIRED, true);// 设置无故障数据原因必填
									}
								}
								if ("上传成功".equals(dataStatus)) {
									setFieldFlag("NODATAREASON",
											GWConstant.S_REQUIRED, false);// 设置无故障数据原因必填取消
									setFieldFlag("NODATAREASON",
											GWConstant.S_READONLY, true);// 设置无故障数据原因只读
								}

							}

							if (SddqConstant.FAIL_DEALMETHOD_HJCL
									.equals(dealMethod)
									|| SddqConstant.FAIL_DEALMETHOD_CHANDGH
											.equals(dealMethod)) {// 换件处理
								exchangeSet.setFlag(GWConstant.S_READONLY,
										false);

								// 检修生成的工单不可编辑
								if (exchangeSet != null
										&& exchangeSet.count() > 0) {
									boolean isFromJx = false;
									for (int i = 0; i < exchangeSet.count(); i++) {
										IJpo jpo = exchangeSet.getJpo(i);
										if ("检修偶换件".equals(jpo
												.getString("tasktype"))
												|| "交车工单".equals(jpo
														.getString("tasktype"))) {
											isFromJx = true;
											break;
										}
									}
									// 来自检修工单
									if (isFromJx) {
										exchangeSet.setFlag(
												GWConstant.S_READONLY, true);
										getParent().getJpoSet("JXTASKLOSSPART")
												.setFlag(GWConstant.S_READONLY,
														true);
									}
								}

							}

							if ("城轨".equals(productline)
									|| "动车".equals(productline)) {// 城轨动车牵引吨位只读
								// 取消必填
								setFieldFlag("QYFZDW", GWConstant.S_REQUIRED,
										false);
								// 设置只读
								setFieldFlag("QYFZDW", GWConstant.S_READONLY,
										true);
							}

							// 重要故障设置是否需要分析报告为是，并且不可修改
							if (WorkorderUtil
									.isImpFault(getString("FAULTCONSEQ"))) {
								if (!"是".equals(getString("ANALYSISREPNEED"))) {
									setValue(
											"ANALYSISREPNEED",
											"是",
											GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
								}
								setFieldFlag("ANALYSISREPNEED",
										GWConstant.S_READONLY, true);
							}

						} else if (SddqConstant.WO_STATUS_KGYBH.equals(status)
								|| SddqConstant.WO_STATUS_SHBH.equals(status)
								|| SddqConstant.WO_STATUS_JSZGBH.equals(status)
								|| SddqConstant.WO_STATUS_ZZKGYBH
										.equals(status)) {
							// 库管员驳回 技术主管驳回 审计驳回 专职库管员驳回 四种状态可编辑字段一致
							setFieldFlag(allAttrs, GWConstant.S_READONLY, false);// 字段只读取消

							// 开始作业后必填字段
							if (parent.getDate("OPERATETIME") != null) {
								setFieldFlag(new String[] { "CARSECTIONNUM",
										"FAILURECODE" }, GWConstant.S_REQUIRED,
										true);// 必填字段
							}

							setFieldFlag(requiredAttrs, GWConstant.S_REQUIRED,
									true);// 必填字段

							// 三包接口状态字段为空
							if (StringUtil.isStrEmpty(getParent().getString(
									"ERPSTATUS"))
									&& (SddqConstant.FAIL_DEALMETHOD_HJCL
											.equals(dealMethod) || SddqConstant.FAIL_DEALMETHOD_CHANDGH
											.equals(dealMethod))) {// 未获取三包信息
								exchangeSet.setFlag(GWConstant.S_READONLY,
										false);// 周转件上下车
								// 检修生成的工单不可编辑
								if (exchangeSet != null
										&& exchangeSet.count() > 0) {
									boolean isFromJx = false;
									for (int i = 0; i < exchangeSet.count(); i++) {
										IJpo jpo = exchangeSet.getJpo(i);
										if ("检修偶换件".equals(jpo
												.getString("tasktype"))
												|| "交车工单".equals(jpo
														.getString("tasktype"))) {
											isFromJx = true;
											break;
										}
									}
									if (isFromJx) {
										exchangeSet.setFlag(
												GWConstant.S_READONLY, true);
										getParent().getJpoSet("JXTASKLOSSPART")
												.setFlag(GWConstant.S_READONLY,
														true);
									}
								}
							}

							if ("城轨".equals(productline)
									|| "动车".equals(productline)) {// 城轨动车牵引吨位只读
								setFieldFlag("QYFZDW", GWConstant.S_REQUIRED,
										false);
								setFieldFlag("QYFZDW", GWConstant.S_READONLY,
										true);
							}

							// 重要故障
							if (WorkorderUtil
									.isImpFault(getString("FAULTCONSEQ"))) {
								if (!"是".equals(getString("ANALYSISREPNEED"))) {
									setValue(
											"ANALYSISREPNEED",
											"是",
											GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
								}
								setFieldFlag("ANALYSISREPNEED",
										GWConstant.S_READONLY, true);
							}
						} else if (SddqConstant.WO_STATUS_JSZGSH.equals(status)) {
							exchangeSet.setFlag(GWConstant.S_READONLY, false);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 变更状态处理类
	 * 
	 * @param curstatus
	 *            当前状态
	 * @param newstatus
	 *            新状态
	 * @param date
	 *            变更日期
	 * @param memo
	 *            备忘录
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void changeStatus(String curstatus, String newstatus, Date date,
			String memo) throws MroException {
		validate(curstatus, newstatus);
		if (date == null) {
			date = MroServer.getMroServer().getDate();
		}
		if (("草稿".equals(curstatus)) && ("已解决".equals(newstatus))) {
			changeStatus(curstatus, "确认故障", date, memo);
		}
		setValue("STATUS", newstatus);
		setValue("statusdate", date);
		// 将状态变更记录记录到状态变更历史表中
		IJpoSet flstatus = getJpoSet("FAILURELIBSTATUS");
		IJpo flstatusnew = flstatus.addJpo();
		flstatusnew.setValue("FAILURENUM", getString("FAILURENUM"),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		flstatusnew.setValue("STATUS", newstatus,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		flstatusnew.setValue("OLDSTATUS", curstatus,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		flstatusnew.setValue("SITEID", getString("SITEID"),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		flstatusnew.setValue("ORGID", getString("ORGID"),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		flstatusnew.setValue("CHANGEDATE", date,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		flstatusnew.setValue("CHANGEBY", getUserInfo().getPersonId(),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
	}

	/**
	 * 
	 * 对新状态进行校验
	 * 
	 * @param curstatus
	 *            当前状态
	 * @param newstatus
	 *            新状态
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void validate(String curstatus, String newstatus)
			throws MroException {
		if (curstatus.equals(newstatus)) {
			// 状态未变更
			throw new AppException("failurelib", "statusnochange");
		}
		if (newstatus.equals("")) {
			// 状态为空
			throw new AppException("failurelib", "statuscannotnull");
		}
		IJpoSet values = getList("STATUS");
		int i = 0;
		for (; i < values.count(); i++) {
			if (values.getJpo(i).getString("VALUE").equals(newstatus)) {
				break;
			}
			if (i > values.count() - 1) {
				// 状态异常
				throw new AppException("failurelib", "statusexception");
			}
		}
		if ((curstatus.equals("确认故障")) && (newstatus.equals("草稿"))) {
			String[] p = { "故障记录状态不能从'确认故障'变为'草稿'。" };
			throw new AppException("failurelib", "vstatus", p);
		}
		if ((curstatus.equals("处理中")) && (newstatus.equals("草稿"))) {
			String[] p = { "故障记录状态不能从'处理中'变为'草稿'。" };
			throw new AppException("failurelib", "vstatus", p);
		}
		if (curstatus.equals("已解决")) {
			String[] p = { "当前故障记录状态为'已解决'，不能进行状态变更。" };
			throw new AppException("failurelib", "vstatus", p);
		}
		if ((!curstatus.equals("已解决")) && (newstatus.equals("已解决"))) {
			if (isNull("CAUSECODE")) {
				String[] p = { "请填写故障原因。" };
				throw new AppException("failurelib", "vstatus", p);
			}
			if (isNull("REMEDYCODE")) {
				String[] p = { "请填写解决措施。" };
				throw new AppException("failurelib", "vstatus", p);
			}
		}
	}

	@Override
	public void add() throws MroException {
		super.add();
		IJpo parent = this.getParent();
		if (parent != null) {
			// 根据工单编号生成故障编号
			String ordernum = parent.getString("ORDERNUM");
			if (StringUtil.isStrNotEmpty(ordernum)) {
				// 获取自动生成的故障编号
				String failurenum = this.getString("FAILURENUM");
				this.setValue("FAILURENUM", ordernum + "-" + failurenum);

				// FailureLibAddDataBean中初始化赋值逻辑
				String carnum = parent.getString("CARNUM");
				String cmodel = parent.getString("MODELS");
				String modelprj = parent.getString("MODELPROJECT");
				String modeltype = parent.getString("MODELS.PRODUCTLINE");
				String repairprocess = parent.getString("REPAIRPROCESS");
				this.setValue("CARMODELS", cmodel);
				this.setValue("CARNUM", carnum);
				this.setValue("MODELPROJECT", modelprj);
				this.setValue("MODELTYPE", modeltype);
				this.setValue("REPAIRPROCESS", repairprocess);
				this.setValue("RUNMILEAGE",
						parent.getString("ASSET.RUNKILOMETRE"));
				// 初始化赋值END
			}
		}
	}
}
