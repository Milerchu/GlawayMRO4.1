package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.jpo.type.MroType;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 服务计划编号绑定字段类
 * 
 * @author ygao
 * @version [版本号, 2017-10-18]
 * @since [产品/模块版本]
 */
public class FldExcProductNum extends JpoField {
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();
		MroType value = getInputMroType();
		IJpo exchangejpo = getJpo();
		IJpoSet assetset = MroServer.getMroServer().getJpoSet("ASSET",
				MroServer.getMroServer().getSystemUserServer());
		assetset.setQueryWhere("SQN = '" + value.asString() + "'");
		assetset.reset();
		if (!assetset.isEmpty()) {
			IJpo asset = assetset.getJpo();
			// servorderjpo.setValue("SERVPLANNAME",
			// servplan.getString("SERVPLANNAME"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			// servorderjpo.setValue("ORDERPROJECT",
			// servplan.getJpoSet("PROJECTINFO").getJpo().getString("PROJECTNUM"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			exchangejpo.setValue("PRODUCTCODEUP", asset.getString("ITEMNUM"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			exchangejpo.setValue("PRODUCTDESCUP", asset.getJpoSet("ITEM")
					.getJpo().getString("description"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			exchangejpo.setValue("SOFTVERSIONUP",
					asset.getString("softversion"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}

	@Override
	public IJpoSet getList() throws MroException {
		String carnum = getJpo().getParent().getString("CARNUM");
		String model = getJpo().getParent().getString("CARMODELS");
		String where = "itemnum in(SELECT ITEMNUM from asset t WHERE t.assetlevel = 'SYSTEM' AND T.TYPE = '2' AND t.carno = '"
				+ carnum + "' AND t.cmodel = '" + model + "') and type ='1'";
		setListWhere(where);
		return super.getList();
	}

}
