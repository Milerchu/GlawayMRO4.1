package com.glaway.sddq.service.chasset.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 串换件aftbatchnum字段类
 * 
 * @author public2796
 * @version [版本号, 2019年1月25日]
 * @since [产品/模块版本]
 */
public class FldAftBatchnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -5103607794582217626L;

	@Override
	public IJpoSet getList() throws MroException {

		getJpo().setFieldFlag("AFTBATCHNUM", GWConstant.S_READONLY, false);
		setListObject("ASSETPART");
		setLookupMap(new String[] { "AFTERITEMNUM", "CHPARENT", "AFTBATCHNUM",
				"CHASSETNUM" }, new String[] { "ITEMNUM", "ASSETNUM", "LOTNUM",
				"ASSETPARTNUM" });

		String bfItemnum = getJpo().getString("BEFOREITEMNUM");
		String altItemnums = ItemUtil.getAltItemnums(bfItemnum);
		// 过滤串换车上所有可替换批次号物料
		String where = "assetnum in (select assetnum from asset where ancestor ='"
				+ getJpo().getString("NEWTRAINASSETNUM")
				+ "') and itemnum in("
				+ altItemnums + ") and (qty-(lockqty-1)) > 0 ";

		setListWhere(where);

		return super.getList();
	}

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
	}
}
