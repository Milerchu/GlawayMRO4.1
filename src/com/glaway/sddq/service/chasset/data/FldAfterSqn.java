package com.glaway.sddq.service.chasset.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 串换记录-串换件序列号aftersqn字段类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月25日]
 * @since [产品/模块版本]
 */
public class FldAfterSqn extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -82485776070866580L;

	@Override
	public IJpoSet getList() throws MroException {

		getJpo().setFieldFlag("AFTERSQN", GWConstant.S_READONLY, false);
		setListObject("ASSET");
		setLookupMap(new String[] { "AFTERSQN", "AFTERITEMNUM", "CHASSETNUM",
				"CHPARENT" }, new String[] { "SQN", "ITEMNUM", "ASSETNUM",
				"PARENT" });

		// 原整车assetnum
		String oldAssetnum = getJpo().getString("OLDTRAINASSETNUM");
		// 串换整车assetnum
		String newAssetnum = getJpo().getString("NEWTRAINASSETNUM");
		// 原产品物料编码
		String oldItemnum = getJpo().getString("BEFOREITEMNUM");
		// 获取原产品可以替代关系
		String altItemnums = ItemUtil.getAltItemnums(oldItemnum);
		if (StringUtil.isStrNotEmpty(newAssetnum)) {
			// 拼接过滤语句
			String listwhere = "assetnum in (select assetnum from asset start with assetnum='"
					+ newAssetnum
					+ "' connect by parent = PRIOR assetnum)  and type='2' and assetlevel='SYSTEM' and islocked<>1 "
					+ " and itemnum in (" + altItemnums + ") ";
			// 如果是同车串换，需排除掉已选择的原物料
			if (newAssetnum.equalsIgnoreCase(oldAssetnum)) {
				listwhere += " and assetnum <>'"
						+ getJpo().getString("BEFOREASSETNUM") + "' ";
			}

			setListWhere(listwhere);
		} else {
			setListWhere("1=2");
		}
		return super.getList();
	}

}
