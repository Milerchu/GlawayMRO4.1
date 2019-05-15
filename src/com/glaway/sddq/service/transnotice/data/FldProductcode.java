package com.glaway.sddq.service.transnotice.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 改造产品范围表物料编码字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-14]
 * @since [产品/模块版本]
 */
public class FldProductcode extends JpoField {

	private static final long serialVersionUID = -4852165666396224586L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		String[] thisAttrs = { "PRODUCTCODE", "PRODUCTDESC" };
		String[] srcAttrs = { "ITEMNUM", "DESCRIPTION" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	@Override
	public IJpoSet getList() throws MroException {
		// 从改造内容中选取
		IJpoSet transcontentSet = getJpo().getParent()
				.getJpoSet("TRANSCONTENT");
		if (!transcontentSet.isEmpty()) {
			String itemnums = "";
			for (int index = 0; index < transcontentSet.count(); index++) {
				IJpo transcontent = transcontentSet.getJpo(index);
				itemnums += "'" + transcontent.getString("PREVIOUSPRDNUM")
						+ "',";
			}
			itemnums = itemnums.substring(0, itemnums.length() - 1);

			setListWhere("itemnum in (" + itemnums + ")");

		}

		return super.getList();
	}
}
