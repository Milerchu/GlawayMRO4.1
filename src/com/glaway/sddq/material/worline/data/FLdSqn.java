package com.glaway.sddq.material.worline.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

public class FLdSqn extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		
		if (getJpo() != null && !StringUtil.isNullOrEmpty(this.getJpo().getAppName())) {
				String[] thisAttrs = { this.getFieldName(), "assetnum" };
				String[] srcAttrs = { "sqn", "assetnum" };
				setLookupMap(thisAttrs, srcAttrs);
		}
	}
	/**
	 * 获取可选择列表数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("asset");
		IJpo parent = this.getJpo().getParent();
		String location = parent.getString("location");
		String itemnum = this.getJpo().getString("itemnum");
		String listSql = "";
			listSql = "itemnum='"
					+ itemnum
					+ "' and location='"
					+ location
					+ "' and sqn not in (select sqn from asset where sqn like 'LS%') and iserp!='0' and assetlevel='ASSET'";
			setListWhere(listSql);
		
		return super.getList();
	}
}
