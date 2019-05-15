package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 验证计划-验证范围 验证周期
 * 
 * @author zzx
 * @version [版本号, 2018年9月12日]
 * @since [产品/模块版本]
 */
public class FldPeriod extends JpoField {
	private static final long serialVersionUID = 2064297619076845120L;

	public void init() throws MroException {
		super.init();
		// IJpo transplanJpo = getJpo().getParent();
		// String transnoticenum = transplanJpo.getString("TRANSNOTICENUM");
		IJpo valiprorangeJpo = getJpo();
		IJpo transplanJpo = valiprorangeJpo.getParent();// 改造计划jpo
		IJpoSet valirequestset = transplanJpo.getJpoSet("VALIREQUEST");
		IJpo valirequestJpo = valirequestset.getJpo();// 获取改造通知单JPO
		IJpoSet plmvaliprorangeset = valirequestJpo
				.getJpoSet("PLMVALIPRORANGE");// plm验证范围jposet
		if (!transplanJpo.isNew()) {
			if (!plmvaliprorangeset.isEmpty()) {
				for (int i = 0; i < plmvaliprorangeset.count(); i++) {
					String period = plmvaliprorangeset.getJpo(i).getString(
							"PERIOD");
					valiprorangeJpo.setValue("PERIOD", period);
				}

			}
		}


	}
}
