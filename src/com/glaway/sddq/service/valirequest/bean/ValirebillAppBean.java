package com.glaway.sddq.service.valirequest.bean;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 验证申请单 appbean
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月8日]
 * @since [产品/模块版本]
 */
public class ValirebillAppBean extends AppBean {

	/**
	 * 
	 * 生成验证计划按钮
	 * 
	 * @author zhuhao
	 * @throws Exception
	 * 
	 */
	public int CREATEPRJ() throws Exception {

		IJpo valirequestJpo = getJpo();
		WorkorderUtil.createValiPlan(valirequestJpo);
		showMsgbox("提示", "成功生成验证计划！");
		this.SAVE();
		return GWConstant.NOACCESS_SAMEMETHOD;
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
