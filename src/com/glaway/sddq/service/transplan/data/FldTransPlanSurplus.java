package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 改造计划-列表改造数量
 * 
 * @author zzx
 * @version [版本号, 2018年9月11日]
 * @since [产品/模块版本]
 */
public class FldTransPlanSurplus extends JpoField {
	private static final long serialVersionUID = 2064297619076845120L;

	@Override
	public void init() throws MroException {
		super.init();
		IJpo transplanJpo = getJpo();
		if (!transplanJpo.isNew()) {
			float count1 = 0;
			IJpoSet transDistSet = transplanJpo.getJpoSet("TRANSDIST");
			if (!transDistSet.isEmpty()) {

				for (int i = 0; i < transDistSet.count(); i++) {
					float surpluscount = transDistSet.getJpo(i).getFloat(
							"SURPLUS");
					count1 += surpluscount;
				}
				transplanJpo.setValue("TRANSPLAYSURPLUS", count1,
						GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
			}
		}

	}
}
