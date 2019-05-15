package com.glaway.sddq.service.chasset.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 串换车号aftercarno字段类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月25日]
 * @since [产品/模块版本]
 */
public class FldAfterCarno extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -7888922966576330513L;

	@Override
	public IJpoSet getList() throws MroException {

		// 设置字段映射
		setLookupMap(new String[] { "AFTERCARNO", "NEWTRAINASSETNUM" },
				new String[] { "CARNO", "ASSETNUM" });

		// 原整车assetnum
		String oldAssetNum = getJpo().getString("OLDTRAINASSETNUM");

		if (SddqConstant.SWAP_DIFFTRAIN.equals(getJpo().getString("CHTYPE"))) {// 两车互换

			// 排除原车
			setListWhere("assetnum <> '" + oldAssetNum + "'");
		}

		return super.getList();
	}

}
