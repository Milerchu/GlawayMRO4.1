package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 非序列号件上下车记录jpo
 * 
 * @author zhuhao
 * @version [版本号, 2019年3月20日]
 * @since [产品/模块版本]
 */
public class LossPart extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 8305185983689176365L;

	@Override
	public void init() throws MroException {
		super.init();
		if (this.getAppName() != null) {
			if ("FAILUREORD".equalsIgnoreCase(getAppName())) {// 故障工单

				if (getParent() != null) {

					// 工单jpo
					IJpo workOrder = getParent();
					// 工单状态
					String status = workOrder.getString("status");
					// 是否调用309接口
					boolean iface309 = workOrder.getBoolean("IFACE309");
					// 三包接口状态
					String erpStatus = workOrder.getString("ERPSTATUS");
					// 所有可编辑字段
					String[] attrs = { "DOWNITEMNUM", "ISAGREESTAY", "REMARK",
							"DEALMODE", "ISNOTICE", "ISTECHAANALYZE",
							"ISCUSTITEM", "ITEMNUM" };
					setFieldFlag("ISAGREESTAY", GWConstant.S_READONLY, true);
					setFieldFlag("REMARK", GWConstant.S_READONLY, true);
					// 技术主管审核
					if (SddqConstant.WO_STATUS_JSZGSH.equals(status)) {
						this.setFieldFlag(attrs, GWConstant.S_READONLY, true);

						if (WfControlUtil.isCurUser(workOrder)) {// 当前工作流执行人

							// 现场留用观察
							if (SddqConstant.FAIL_DEALMODE_RETENTION
									.equals(getString("dealmode"))) {

								setFieldFlag("ISAGREESTAY",
										GWConstant.S_READONLY, false);
								setFieldFlag("ISAGREESTAY",
										GWConstant.S_REQUIRED, true);
								setFieldFlag("REMARK", GWConstant.S_READONLY,
										false);
							}

							// 已经触发接口时不可编辑
							if (iface309) {
								setFieldFlag("ISAGREESTAY",
										GWConstant.S_REQUIRED, false);
								setFieldFlag("ISAGREESTAY",
										GWConstant.S_READONLY, true);
								setFieldFlag("REMARK", GWConstant.S_READONLY,
										true);
							}
						}
					}
				}
			}
		}

	}

}
