package com.glaway.sddq.material.detectedmater.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 有效性检测AppBean
 * 
 * @author bchen
 * @version [版本号, 2018-6-2]
 * @since [产品/模块版本]
 */
public class DetectedAppBean extends AppBean {
	public void CHECK() throws MroException, IOException {
		IJpoSet detectedmaterset = MroServer.getMroServer()
				.getJpoSet("DETECTEDMATER",
						MroServer.getMroServer().getSystemUserServer());
		
		for (int i = 0; i < detectedmaterset.count(); i++) {
			IJpo detectedmater = detectedmaterset.getJpo(i);
			String status = detectedmater.getString("STATUS");
			if (status != null && status.equals("有效性检测")) {
				detectedmater.setValue("STATUS", "已完成",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			detectedmaterset.save();
			this.getDataBean("1527576696535").resetAndReload();
		}
	}
}
