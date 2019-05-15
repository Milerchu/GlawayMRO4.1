package com.glaway.sddq.overhaul.taskorder.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 偶换件DataBean
 * 
 * @author hyhe
 * @version [版本号, 2018-5-22]
 * @since [产品/模块版本]
 */
public class JxCoupleChangeDataBean extends DataBean {

	@Override
	public void buildJpoSet() throws MroException {
		super.buildJpoSet();
		this.jpoSet.setUserWhere("1=2");
		this.jpoSet.reset();
		IJpo jpo = this.jpoSet.addJpo();
		IJpo tyjpo = this.getAppBean().getJpo();
		String assetnum = tyjpo.getString("assetnum");
		String whichstation = tyjpo.getString("WHICHSTATION");

		IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet("ASSET",
				"ASSETNUM='" + assetnum + "'");
		assetSet.reset();
		if (!assetSet.isEmpty()) {
			IJpo asset = assetSet.getJpo();
			String carno = asset.getString("CARNO");
			String cmodel = asset.getString("CMODEL");
			IJpoSet modelsSet = MroServer.getMroServer().getSysJpoSet("MODELS",
					"MODELNUM='" + cmodel + "'");
			modelsSet.reset();
			if (!modelsSet.isEmpty()) {
				IJpo models = modelsSet.getJpo();
				String modeldesc = models.getString("MODELDESC");

				String repairprocess = asset.getString("REPAIRPROCESS");
				String updatetime = asset.getString("UPDATETIME");
				String runkilometre = asset.getString("RUNKILOMETRE");
				String repairafterkilometer = asset
						.getString("REPAIRAFTERKILOMETER");
				String overhauler = asset.getString("OVERHAULER");
				String ownercustomer = asset.getString("OWNERCUSTOMER");
				jpo.setValue("TYPE", "故障");
				jpo.setValue("CARNUM", carno);
				jpo.setValue("MODELS", cmodel);
				jpo.setValue("REPAIRPROCESS", repairprocess);
				jpo.setValue("updatetime", updatetime);
				jpo.setValue("RUNKILOMETRE", runkilometre);
				jpo.setValue("REPAIRAFTERKILOMETER", repairafterkilometer);
				jpo.setValue("OVERHAULER", overhauler);
				jpo.setValue("OWNERCUSTOMER", ownercustomer);
				jpo.setValue("ASSETNUM", assetnum);
				jpo.setValue("MODELPROJECT", modeldesc);
				jpo.setValue("WHICHSTATION", whichstation,
						GWConstant.P_NOVALIDATION);
			}
		}
	}

	@Override
	public int execute() throws MroException, IOException {
		this.getParent();
		// 生成故障工单
		IJpo exchangeJpo = this.getDialog().getCreator().getDataBean().getJpo();
		if(exchangeJpo!=null){
		String ordernum = this.getString("ORDERNUM");
		String failurenum = this.getJpo().getJpoSet("FAILURELIB").getJpo()
				.getString("FAILURENUM");
		exchangeJpo.setValue("failurenum", failurenum,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		exchangeJpo.setValue("ISFAILURE", "是",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		exchangeJpo.setValue("FAILUREORDERNUM", ordernum,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
		return super.execute();
	}
}
