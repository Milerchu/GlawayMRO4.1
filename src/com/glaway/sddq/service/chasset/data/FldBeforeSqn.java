package com.glaway.sddq.service.chasset.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 串换件原序列号字段类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月25日]
 * @since [产品/模块版本]
 */
public class FldBeforeSqn extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {
		getJpo().setFieldFlag("BEFORESQN", GWConstant.S_READONLY, false);
		setListObject("ASSET");
		setLookupMap(new String[] { "BEFORESQN", "BEFOREITEMNUM",
				"BEFOREASSETNUM", "BEFOREPARENT" }, new String[] { "SQN",
				"ITEMNUM", "ASSETNUM", "PARENT" });

		// 整车assetnum
		String assetnum = getJpo().getString("OLDTRAINASSETNUM");
		String listwhere = "assetnum in (select assetnum from asset start with assetnum='"
				+ assetnum
				+ "' connect by parent = PRIOR assetnum)  and type='2' and assetlevel='SYSTEM' and islocked<>1";

		setListWhere(listwhere);
		return super.getList();
	}

	@Override
	public void action() throws MroException {
		super.action();
		if (StringUtil.isStrNotEmpty(getJpo().getString("BFEBATCHNUM"))) {// 清空批次件字段值
			String[] batchAttrs = { "BFEBATCHNUM", "AFTERSQN", "AFTBATCHNUM",
					"AFTERITEMNUM" };
			for (String attr : batchAttrs) {
				getJpo().setValueNull(attr,
						GWConstant.P_NOVALIDATION_AND_NOACTION);
			}

		}
		// 设置批次件字段只读
		String[] readOnlyAtts = { "BFEBATCHNUM", "AFTBATCHNUM" };
		getJpo().setFieldFlag(readOnlyAtts, GWConstant.S_READONLY, true);
		// 取消批次号必填BFEBATCHNUM
		getJpo().setFieldFlag(readOnlyAtts, GWConstant.S_REQUIRED, false);
		getJpo().getField("BFEBATCHNUM").clearError();
		getJpo().getField("AFTBATCHNUM").clearError();
		// 序列号必填
		getJpo().setFieldFlag("AFTERSQN", GWConstant.S_READONLY, false);
		getJpo().setFieldFlag("AFTERSQN", GWConstant.S_REQUIRED, true);
		getJpo().setFieldFlag("BEFORESQN", GWConstant.S_REQUIRED, true);

	}

}
