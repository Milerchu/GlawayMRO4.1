package com.glaway.sddq.service.transplan.workflow.action;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 改造计划-启动工单工作流操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月19日]
 * @since [产品/模块版本]
 */
public class TpStartToWfAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {

		if (jpo != null) {

			IJpoSet transdistSet = jpo.getJpoSet("TRANSDIST");// 改造车辆分布表
			if (!transdistSet.isEmpty()) {

				for (int i = 0; i < transdistSet.count(); i++) {// 判断是否已经创建工单
					IJpo dist = transdistSet.getJpo(i);
					if (!dist.getBoolean("ISCREATEWO")) {
						throw new MroException("请先分配改造任务再发送工作流！");
					}
				}

				for (int index = 0; index < transdistSet.count(); index++) {

					IJpo transdist = transdistSet.getJpo(index);
					IJpoSet orderSet = transdist.getJpoSet("TRANSORDER");// 工单子表
					if (!orderSet.isEmpty()) {

						for (int i = 0; i < orderSet.count(); i++) {

							IJpo order = orderSet.getJpo(i);
							IJpoSet dealSet = order
									.getJpoSet("JXTASKEXECPERSON");// 处理人子表
							if (dealSet.isEmpty()) {
								throw new MroException("valiorder",
										"nodealperson");// 无现场处理人
							}

							// 启动工单工作流
							WfControlUtil.startwf(order, "TRANSORDER");

						}

					}

				}
			}
			jpo.setValue("STARTDATE", MroServer.getMroServer().getDate(),
					GWConstant.P_NOVALIDATION_AND_NOACTION);// 设置计划启动时间

			jpo.getJpoSet().save();

		}

	}

}
