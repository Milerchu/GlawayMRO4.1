package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 故障工单-故障件唯一序号字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年12月4日]
 * @since [产品/模块版本]
 */
public class FldFaultCompAssetnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();

		String assetnum = getInputMroType().asString();// 当前输入值
		if (StringUtil.isStrNotEmpty(assetnum)) {

			// 获取故障件上级部件
			String sqn = getJpo().getString("FAULTCOMPONENTSQN");
			String lotnum = getJpo().getString("FAULTCOMPLOTNUM");

			String sqnorlotnum = StringUtil.isStrEmpty(sqn) ? lotnum : sqn;

			WorkorderUtil.getSuperiorAsset(getJpo(), "WO",
					getJpo().getString("FAULTCOMPITEMNUM"), assetnum, getJpo()
							.getString("ORDERNUM"), sqnorlotnum);

		}
	}

}
