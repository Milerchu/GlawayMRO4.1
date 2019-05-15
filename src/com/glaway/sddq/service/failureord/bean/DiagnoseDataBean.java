package com.glaway.sddq.service.failureord.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 故障诊断databean
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月7日]
 * @since [产品/模块版本]
 */
public class DiagnoseDataBean extends DataBean {

	/**
	 * 
	 * 修改诊断成功标识 当改变一个标识为是的时候，修改该工单下其余故障诊断标识为否
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public int changeFlag() throws MroException, IOException {
		String flag = getString("DIAGNOSEFLAG");
		String diagnoseID = getString("FAULTDIAGNOSEID");
		String sourceID = getString("SOURCEFILEORDERID");
		IJpoSet tmpSet = MroServer.getMroServer().getJpoSet("FAULTDIAGNOSE",
				getJpo().getUserServer());
		tmpSet.setQueryWhere("SOURCEFILEORDERID ='" + sourceID
				+ "' and FAULTDIAGNOSEID!='" + diagnoseID + "'");
		tmpSet.reset();
		for (int i = 0; i < tmpSet.count(); i++) {
			tmpSet.getJpo(i).setValue("DIAGNOSEFLAG", "否");
		}
		tmpSet.save();
		setValue("DIAGNOSEFLAG", flag.equals("是") ? "否" : "是");
		this.save();
		this.resetAndReload();
		return GWConstant.NOACCESS_SAMEMETHOD;
	}
}
