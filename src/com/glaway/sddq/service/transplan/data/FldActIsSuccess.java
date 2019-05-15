package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 验证计划-列表 是否成功
 * 
 * @author zzx
 * @version [版本号, 2018年9月12日]
 * @since [产品/模块版本]
 */
public class FldActIsSuccess extends JpoField {
	private static final long serialVersionUID = 2064297619076845120L;

	public void init() throws MroException {
		super.init();
		IJpo transplanJpo = getJpo();
		IJpoSet valiprorangeset = transplanJpo.getJpoSet("VALIPRORANGE");
		if (!valiprorangeset.isEmpty()) {
			for (int i = 0; i < valiprorangeset.count(); i++) {
				// 获取验证工单的jposet
				IJpoSet valiorderset = valiprorangeset.getJpo(i).getJpoSet(
						"VALIORDER");
				for (int j = 0; j < valiorderset.count(); j++) {
					// 只根据关闭状态的工单判断
					if (!SddqConstant.WO_STATUS_GB.equals(valiorderset
							.getJpo(j).getString("status"))) {
						continue;
					}
					boolean issuccess = valiorderset.getJpo(j).getBoolean(
							"ISSUCCESS");
					if (issuccess) {
						transplanJpo.setValue("ACTISSUCCESS", "1",
								GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
					} else {
						transplanJpo.setValue("ACTISSUCCESS", "0",
								GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
					}
				}
			}
		}

	}
}
