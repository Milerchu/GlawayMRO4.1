package com.glaway.sddq.service.chasset.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 串换件记录-原产品批次号bfebathunum字段类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月25日]
 * @since [产品/模块版本]
 */
public class FldBfeBatchnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {

		getJpo().setFieldFlag("BFEBATCHNUM", GWConstant.S_READONLY, false);
		setListObject("ASSETPART");
		setLookupMap(new String[] { "BEFOREITEMNUM", "BEFOREPARENT",
				"BFEBATCHNUM", "BEFOREASSETNUM" }, new String[] { "ITEMNUM",
				"ASSETNUM", "LOTNUM", "ASSETPARTNUM" });

		// 过滤原车所有批次号物料
		String where = "assetnum in (select assetnum from asset where ancestor ='"
				+ getJpo().getString("OLDTRAINASSETNUM")
				+ "') and (qty-(lockqty-1)) > 0 ";

		this.setListWhere(where);

		return super.getList();
	}

	@Override
	public void action() throws MroException {
		super.action();
		if (StringUtil.isStrNotEmpty(getJpo().getString("BEFORESQN"))) {// 清空序列号字段值
			String[] batchAttrs = { "BEFORESQN", "AFTERSQN", "AFTBATCHNUM",
					"AFTERITEMNUM" };
			for (String attr : batchAttrs) {
				getJpo().setValueNull(attr,
						GWConstant.P_NOVALIDATION_AND_NOACTION);
			}

		}
		// 设置批次件字段只读
		String[] readOnlyAtts = { "BEFORESQN", "AFTERSQN" };
		getJpo().setFieldFlag(readOnlyAtts, GWConstant.S_READONLY, true);
		// 取消序列号必填
		getJpo().setFieldFlag(readOnlyAtts, GWConstant.S_REQUIRED, false);
		getJpo().getField("BEFORESQN").clearError();
		getJpo().getField("AFTERSQN").clearError();
		// 批次号必填
		getJpo().setFieldFlag("AFTBATCHNUM", GWConstant.S_READONLY, false);
		getJpo().setFieldFlag("AFTBATCHNUM", GWConstant.S_REQUIRED, true);
		getJpo().setFieldFlag("BFEBATCHNUM", GWConstant.S_REQUIRED, true);

	}

}
