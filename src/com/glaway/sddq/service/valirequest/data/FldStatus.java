package com.glaway.sddq.service.valirequest.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 验证申请单 status字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月6日]
 * @since [产品/模块版本]
 */
public class FldStatus extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1012592820867006668L;

	@Override
	public void action() throws MroException {
		// 当前输入值
		// String status = this.getInputMroType().asString();
		// if ("已审核".equals(status)) {
		// // 审批完成创建验证计划
		// createPlan(getJpo());
		// }

		super.action();
	}

	/**
	 * 
	 * 创建验证计划
	 * 
	 * @param notice
	 *            验证申请单jpo
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void createPlan(IJpo notice) throws MroException {
		if (!notice.getJpoSet("VALIPLAN").isEmpty()) {// 已经生成过验证计划
			throw new MroException("valirebill", "valiplanexsits");
		}
		IJpoSet planSet = MroServer.getMroServer().getJpoSet("TRANSPLAN",
				getJpo().getUserServer());
		IJpo plan = planSet.addJpo();
		plan.setValue("TRANSTYPE", "软件验证");
		plan.setValue("TRANSPLANNAME", notice.getString("SNUMBER") + "验证计划");
		plan.setValue("PLANTYPE", "验证");
		plan.setValue("TRANSNOTICENUM", notice.getString("VALIREQUESTNUM"));
		plan.setValue("WORKORDERNUM", notice.getString("WORKORDERNUM"));
		planSet.save();
	}

}
