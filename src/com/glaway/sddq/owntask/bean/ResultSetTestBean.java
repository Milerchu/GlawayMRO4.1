package com.glaway.sddq.owntask.bean;

import org.apache.commons.lang3.StringUtils;

import com.glaway.mro.controller.ResultSetBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;

public class ResultSetTestBean extends ResultSetBean {

	public IJpoSet getCurJpoSet(String queryclause, IJpoSet set) {
		try {
			String username = this.getMroSession().getUserName();
			if (StringUtils.isNotEmpty(username)) {
				username = username.toUpperCase();
			}
			set.setUserWhere("proc_inst_ID_ in (select procinstid from act_hi_assign where MEMO = '流程启动' and assignee = '"
					+ username + "')");
			set.reset();
		} catch (MroException e) {
			e.printStackTrace();
		}
		return set;
	}
}

