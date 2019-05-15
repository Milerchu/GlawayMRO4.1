package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 工单 上下车记录 assetnum字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年7月5日]
 * @since [产品/模块版本]
 */
public class FldExchangeAssetnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 3907116022965124003L;

	@Override
	public void action() throws MroException {

		super.action();
		IJpo ex = getJpo();
		String assetnum = getInputMroType().asString();
		if ("验证".equals(ex.getString("tasktype"))) {// 验证工单

			String sqn = ex.getString("sqn");
			String itemnum = ex.getString("itemnum");
			String lotnum = ex.getString("lotnum");
			String confignum = ex.getString("ASSET.CONFIGNUM");
			String softversion = ex.getString("ASSET.SOFTVERSION");

			ex.setValue("NEWSQN", sqn, GWConstant.P_NOVALIDATION);
			ex.setValue("NEWASSETNUM", assetnum, GWConstant.P_NOVALIDATION);
			ex.setValue("NEWITEMNUM", itemnum, GWConstant.P_NOVALIDATION);
			ex.setValue("NEWLOTNUM", lotnum, GWConstant.P_NOVALIDATION);
			ex.setValue("NEWCONFIGNUM", confignum, GWConstant.P_NOVALIDATION);
			ex.setValue("NEWSOFTVERSION", softversion,
					GWConstant.P_NOVALIDATION);

		} else if ("故障".equals(ex.getString("tasktype"))
				|| "检修偶换件".equals(ex.getString("tasktype"))
				|| "交车工单".equals(ex.getString("tasktype"))) {
			if (!ex.getJpoSet("SUPERIORASSET").isEmpty()) {
				// 清空上级部件子表
				ex.getJpoSet("SUPERIORASSET").deleteAll();
			}

			// 获取故障件上级部件
			WorkorderUtil.getSuperiorAsset(ex, "SQN", ex.getString("itemnum"),
					assetnum, ex.getString("EXCHANGERECORDNUM"),
					ex.getString("SQN"));

		}

	}
}
