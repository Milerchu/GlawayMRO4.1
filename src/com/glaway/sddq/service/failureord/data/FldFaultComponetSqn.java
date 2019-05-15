package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 故障工单-故障件序列号字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年12月3日]
 * @since [产品/模块版本]
 */
public class FldFaultComponetSqn extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();
	}

	@Override
	public IJpoSet getList() throws MroException {
		getJpo().setFieldFlag("FAULTCOMPONENTSQN", GWConstant.S_READONLY, false);
		setListObject("ASSET");
		setLookupMap(new String[] { "FAULTCOMPONENTSQN", "FAULTCOMPITEMNUM",
				"FAULTCOMPASSETNUM" }, new String[] { "SQN", "ITEMNUM",
				"ASSETNUM" });

		// 整车assetnum
		String assetnum = getJpo().getString("assetnum");
		String listwhere = "assetnum in (select assetnum from asset start with assetnum='"
				+ assetnum
				+ "' connect by parent = PRIOR assetnum)  and type='2' and assetlevel='SYSTEM'";

		setListWhere(listwhere);

		return super.getList();
	}

	@Override
	public void action() throws MroException {

		super.action();

		// 清空批次号并设为只读
		getJpo().setValueNull("FAULTCOMPLOTNUM",
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		getJpo().setValueNull("FAULTCOMPAPARTNUM",
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		getJpo().setFieldFlag("FAULTCOMPONENTSQN", GWConstant.S_REQUIRED, true);
		getJpo().setFieldFlag("FAULTCOMPLOTNUM", GWConstant.S_REQUIRED, false);
		getJpo().setFieldFlag("FAULTCOMPLOTNUM", GWConstant.S_READONLY, true);

		if (isValueChanged()) {
			// 清空上级部件表
			getJpo().getJpoSet("SUPERIORASSET").deleteAll();
		}

	}

}
