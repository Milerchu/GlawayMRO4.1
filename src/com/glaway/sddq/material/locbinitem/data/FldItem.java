package com.glaway.sddq.material.locbinitem.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <仓位物料表物料编码字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldItem extends JpoField {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6714808953825812433L;

	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "itemnum" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 获取可选择列表数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("sys_item");
		IJpo parent = this.getJpo().getParent();
		String listSql = "";
		if (parent != null) {
			listSql = " itemnum in (select itemnum from sys_inventory where location='"
					+ parent.getString("location") + "') ";
		}
		IJpoSet thisSet = this.getJpo().getJpoSet();
		String itemnums = null;
		if (!thisSet.isEmpty()) {
			for (int i = 0; i < thisSet.count(); i++) {
				String itemnum = thisSet.getJpo(i).getString(
						this.getFieldName());
				if (!"".equals(itemnum)) {
					if (itemnums == null)
						itemnums = "'" + StringUtil.getSafeSqlStr(itemnum)
								+ "'";
					else
						itemnums = itemnums + ",'"
								+ StringUtil.getSafeSqlStr(itemnum) + "'";
				}
			}
		}
		if (itemnums != null) {
			listSql = listSql + "and  itemnum not in (" + itemnums + ")";
		}

		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}
}
