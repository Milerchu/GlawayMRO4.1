package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;
/**
 * 
 * 交车工单库房字段类
 * 
 * @author  chenbin
 * @version  [版本号, 2018-7-25]
 * @since  [产品/模块版本]
 */
public class FldStock extends JpoField {

	@Override
	public IJpoSet getList() throws MroException {
//		// String appName = this.getJpo().getAppName();
//		String erploc = "1030";
//		String where = "";
//		where = "ERPLOC='" + erploc + "'";
//
//		if (StringUtil.isStrNotEmpty(erploc)) {
//			setListWhere(where);
//		}
		return super.getList();
	}
}
