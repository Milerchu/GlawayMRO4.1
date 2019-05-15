package com.glaway.sddq.service.servorder.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 服务工单-soclose操作类
 * 
 * @author zhuhao
 * @version [版本号, 2019年2月20日]
 * @since [产品/模块版本]
 */
public class SoCloseAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {

		if (jpo != null) {
			// 服务工单类型
			String orderType = jpo.getString("SERVORDERTYPE");
			// 新车整备类型工单
			if (SddqConstant.SO_TYPE_XCZB.equals(orderType)) {
				// 整车assetnum
				String assetnum = jpo.getString("ASSETNUM");
				if (StringUtil.isStrNotEmpty(assetnum)) {
					// 配置表
					IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
							"ASSET", "assetnum='" + assetnum + "'");
					if (assetSet != null && assetSet.count() > 0) {
						// 配置完整度
						double pzwzd = assetSet.getJpo(0).getDouble("PZWZD");
						// 配置完整度小于100不能关闭工单
						if (pzwzd < 100.00) {
							throw new MroException("servorder", "notcomplete");
						}
					}
				}

			}

			jpo.setValue("STATUS", "关闭", GWConstant.P_NOVALIDATION);
			jpo.getJpoSet().save();
		}

	}

}
