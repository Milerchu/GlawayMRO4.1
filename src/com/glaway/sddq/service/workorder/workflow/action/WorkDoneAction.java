package com.glaway.sddq.service.workorder.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 服务工单-SOCLWC处理完成操作定制类
 * 
 * @author hzhu
 * @version [版本号, 2018-05-24]
 * @since [MRO4.1/模块版本]
 */
public class WorkDoneAction implements ActionCustomClass {

	/**
	 * 
	 * @param jpo
	 * @param parameter
	 * @throws MroException
	 */
	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		if (jpo != null) {
			jpo.setValue("status", "待审核",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			jpo.setValue("COMPLETETIME", MroServer.getMroServer().getDate(),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//			if ("服务".equals(jpo.getString("type"))) {
//				// 服务工单 类型为调试交车时，更新装车配置中出厂时间
//				if (SddqConstant.SO_TYPE_TSJC.equals(jpo
//						.getString("SERVORDERTYPE"))) {
//					// 获取车辆jposet
//					IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
//							"ASSET",
//							"ASSETNUM='" + jpo.getString("assetnum") + "'");
//					if (assetSet != null && assetSet.count() > 0) {
//						IJpo asset = assetSet.getJpo(0);
//						// 设置出厂时间
//						asset.setValue("RELEASEDATE", MroServer.getMroServer()
//								.getDate(),
//								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//						assetSet.save();
//					}
//				}
//			}

			jpo.getJpoSet().save();
		}
	}
}
