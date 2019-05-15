package com.glaway.sddq.material.carlifestatuscommon;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <整车生命周期状态公共类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-4-18]
 * @since  [产品/模块版本]
 */
public class Carlifestatuscommon {
	private static Carlifestatuscommon wfCtrl = null;;

	private Carlifestatuscommon() {
	}

	public synchronized static Carlifestatuscommon getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new Carlifestatuscommon();
		}
		return wfCtrl;
	}
	/**
	 * 
	 * <赋值整车生命周期状态方法>
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
				if(type.equalsIgnoreCase("调试交车")){
					carasset.setValue("carlifestatus", "S-S-100",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("现场试验")){
					carasset.setValue("carlifestatus", "S-S-200",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("新车整备/预验收")){
					carasset.setValue("carlifestatus", "S-S-300",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("新造后质保")){
					carasset.setValue("carlifestatus", "S-S-500",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("检修后质保")){
					carasset.setValue("carlifestatus", "S-S-510",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("超期质保")){
					carasset.setValue("carlifestatus", "S-S-580",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("过保")){
					carasset.setValue("carlifestatus", "S-S-590",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				if(type.equalsIgnoreCase("延保服务")){
					carasset.setValue("carlifestatus", "S-S-600",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				carassetset.save();
			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
