package com.glaway.sddq.service.failureord.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 故障工单-确认故障后更新asset表操作
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-14]
 * @since [产品/模块版本]
 */
public class UpdateAssetAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {
		// 故障记录
		IJpoSet failureSet = jpo.getJpoSet("FAILURELIB");
		if (!failureSet.isEmpty()) {
			// 上下车记录
			IJpoSet exchangeSet = failureSet.getJpo().getJpoSet(
					"EXCHANGERECORD");
			if (!exchangeSet.isEmpty()) {
				for (int index = 0; index < exchangeSet.count(); index++) {
					IJpo exJpo = exchangeSet.getJpo(index);
					// 下车件唯一序列号
					String assetnum = exJpo.getString("ASSETNUM");
					IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
							"ASSET", "ASSETNUM='" + assetnum + "'");
					// 将下车件状态设为故障
					assetSet.getJpo().setValue("status", "故障处理",
							GWConstant.P_NOVALIDATION_AND_NOACTION);
					assetSet.getJpo().setValue("exchangerecordnum",
							exJpo.getString("EXCHANGERECORDNUM"),
							GWConstant.P_NOVALIDATION_AND_NOACTION);
					assetSet.save();
				}
			}
		}

	}

}
