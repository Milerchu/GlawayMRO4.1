package com.glaway.sddq.material.cartechnologystatuscommon;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <整车配置技术状态公共类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-4-18]
 * @since  [产品/模块版本]
 */
public class Cartechnologystatuscommon {
	private static Cartechnologystatuscommon wfCtrl = null;;

	private Cartechnologystatuscommon() {
	}

	public synchronized static Cartechnologystatuscommon getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new Cartechnologystatuscommon();
		}
		return wfCtrl;
	}
	/**
	 * 
	 * <赋值整车配置技术状方法>
	 * @param assetnum 车的资产编码
	 * @param type [类型]
	 *
	 */
	public static void CHANGESTATUS(String assetnum,String type) {
		IJpoSet carassetset;
		try {
			carassetset = MroServer.getMroServer().getJpoSet("asset",MroServer.getMroServer().getSystemUserServer());
			carassetset.setUserWhere("assetnum='"+assetnum+"'");
			if(!carassetset.isEmpty()){
				IJpo carasset=carassetset.getJpo(0);
				if(type.equalsIgnoreCase("新造")){
					carasset.setValue("cartechnologystatus", "S-S-010",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("正常")){
					carasset.setValue("cartechnologystatus", "S-S-020",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("故障处理")){
					carasset.setValue("cartechnologystatus", "S-S-050",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("现场验证")){
					carasset.setValue("cartechnologystatus", "S-S-540",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("现场改造")){
					carasset.setValue("cartechnologystatus", "S-S-550",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("检修")){
					carasset.setValue("cartechnologystatus", "S-S-800",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("报废")){
					carasset.setValue("cartechnologystatus", "S-S-900",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				carassetset.save();
			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
