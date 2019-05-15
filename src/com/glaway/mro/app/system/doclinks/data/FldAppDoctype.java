package com.glaway.mro.app.system.doclinks.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

public class FldAppDoctype extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -4426034413930759077L;

	@Override
	public void init() throws MroException {
		super.init();
		if (getJpo().toBeAdded()) {
			setFirstValue();
		}
	}

	@Override
	public IJpoSet getList() throws MroException {

		IJpoSet fieldSet = this.getUserServer().getJpoSet("SYS_APPDOCTYPE");
		String where = "app='"
				+ StringUtil.getSafeSqlStr(getJpo().getAppName()) + "'";
		if ("FAILUREORD".equalsIgnoreCase(getJpo().getAppName())) {
			// where += " and doctype<>'FAILURE' ";
		}
		fieldSet.setUserWhere(where);
		fieldSet.reset();
		return fieldSet;
	}
}
