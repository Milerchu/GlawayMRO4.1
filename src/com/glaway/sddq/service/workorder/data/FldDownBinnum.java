package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * 耗损件仓库选择
 * 
 * @author  chenbin
 * @version  [版本号, 2018-6-21]
 * @since  [产品/模块版本]
 */
public class FldDownBinnum extends JpoField {
	
	private static final long serialVersionUID = 5469002096370653230L;
	
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("locbin");
		String listSql = "";
		listSql = " location='" + this.getJpo().getString("UNDERLOC") + "' ";
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}
		return super.getList();
	}
}
