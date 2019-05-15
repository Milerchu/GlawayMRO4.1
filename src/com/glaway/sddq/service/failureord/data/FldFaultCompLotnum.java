package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 故障工单-故障件批次号字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年12月3日]
 * @since [产品/模块版本]
 */
public class FldFaultCompLotnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();
		setListObject("ASSETPART");
		setLookupMap(new String[] { "FAULTCOMPITEMNUM", "FAULTCOMPLOTNUM",
				"FAULTCOMPASSETNUM", "FAULTCOMPAPARTNUM" }, new String[] {
				"ITEMNUM", "LOTNUM", "ASSETNUM", "ASSETPARTNUM" });
	}

	@Override
	public IJpoSet getList() throws MroException {
		getJpo().setFieldFlag("FAULTCOMPLOTNUM", GWConstant.S_READONLY, false);
		IJpo wo = getJpo();// 工单jpo

		String where = "assetnum in (select assetnum from asset where ancestor ='"
				+ wo.getString("ASSETNUM") + "') ";

		this.setListWhere(where);

		return super.getList();
	}

	@Override
	public void action() throws MroException {
		super.action();

		// 清空序列号并设为只读
		getJpo().setValueNull("FAULTCOMPONENTSQN",
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		getJpo().setFieldFlag("FAULTCOMPLOTNUM", GWConstant.S_REQUIRED, true);
		getJpo().setFieldFlag("FAULTCOMPONENTSQN", GWConstant.S_REQUIRED, false);
		getJpo().setFieldFlag("FAULTCOMPONENTSQN", GWConstant.S_READONLY, true);

		if (isValueChanged()) {
			// 清空上级部件表
			getJpo().getJpoSet("SUPERIORASSET").deleteAll();
		}
	}

}
