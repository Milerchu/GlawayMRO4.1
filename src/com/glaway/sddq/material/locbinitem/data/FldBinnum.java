package com.glaway.sddq.material.locbinitem.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <仓位物料表仓位编码绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldBinnum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "binnum" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 获取仓位信息
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("locbin");
		String listSql = "";
		listSql = " location='" + this.getJpo().getString("location") + "' ";
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}
		return super.getList();
	}

	/**
	 * 校验相同仓位编号
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String xbinnum = this.getValue();
		String xlocbinitemid = this.getJpo().getString("locbinitemid");
		IJpoSet locbinitemset = this.getJpo().getParent()
				.getJpoSet("locbinitem");
		if (!locbinitemset.isEmpty()) {
			for (int i = 0; i < locbinitemset.count(); i++) {
				String binnum = locbinitemset.getJpo(i).getString("binnum");
				String locbinitemid = locbinitemset.getJpo(i).getString(
						"locbinitemid");
				if (binnum.equalsIgnoreCase(xbinnum)
						&& !locbinitemid.equalsIgnoreCase(xlocbinitemid)) {
					throw new MroException("locbinitem", "samebinnum");
				}
			}
		}
	}

}
