package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <装箱单在途状态操作定制类>
 * 
 * @author zzx
 * @version [MRO4.1, 2019-3-13]
 * @since [MRO4.1/模块版本]
 */
public class ZxdZtStatusAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		// TODO Auto-generated method stub
		if (jpo != null) {
			jpo.setValue("status", "在途",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			String type = jpo.getString("type");
			IJpoSet transferlineset = jpo.getJpoSet("TRANSFERLINE");
			// 取是否发运的值
			String issue = jpo.getString("issue");
			if (transferlineset != null && transferlineset.count() > 0) {
				if (issue.equalsIgnoreCase("是")) {
					for (int i = 0; i < transferlineset.count(); i++) {
						String assetnum = transferlineset.getJpo(i).getString(
								"assetnum");
						if (StringUtil.isStrNotEmpty(assetnum)) {
							// 取asset
							IJpoSet assetjposet = transferlineset.getJpo(i)
									.getJpoSet("ASSET");
							int j = assetjposet.count();
							assetjposet.getJpo(0).setValue("STATUS", "在途",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					}
				}
			}
			jpo.getJpoSet().save();
		}
	}

}
