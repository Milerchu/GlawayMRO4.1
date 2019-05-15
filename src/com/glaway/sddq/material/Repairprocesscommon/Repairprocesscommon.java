package com.glaway.sddq.material.Repairprocesscommon;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <修程修次公共类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-4-18]
 * @since  [产品/模块版本]
 */
public class Repairprocesscommon {
	private static Repairprocesscommon wfCtrl = null;;

	private Repairprocesscommon() {
	}

	public synchronized static Repairprocesscommon getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new Repairprocesscommon();
		}
		return wfCtrl;
	}
	/**
	 * 
	 * <赋值修程修次方法>
	 * @param assetnum 车的资产编码
	 * @param type [类型]
	 *
	 */
	public static void CHANGESTATUS(String assetnum,String REPAIRPROCESS) {
		IJpoSet carassetset;
		try {
			carassetset = MroServer.getMroServer().getJpoSet("asset",MroServer.getMroServer().getSystemUserServer());
			carassetset.setUserWhere("assetnum='"+assetnum+"'");
			if(!carassetset.isEmpty()){
				IJpo carasset=carassetset.getJpo(0);
				carasset.setValue("REPAIRPROCESS", REPAIRPROCESS,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				carassetset.save();
			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
