package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <改造/验证计划 改造通知单/验证申请单编号 字段类>
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-5-17]
 * @since [MRO4.1/模块版本]
 */
public class FldTransnoticenum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 9113260631770963838L;

	@Override
	public void init() throws MroException {
		String appname = getJpo().getAppName();
		if ("transplan".equalsIgnoreCase(appname)) {// 改造计划
			setLookupMap(new String[] { "transnoticenum", "WORKORDERNUM" },
					new String[] { "transnoticenum", "TRANSWORKORDERNUM" });
		} else {// 验证计划
			setLookupMap(new String[] { "transnoticenum", "WORKORDERNUM" },
					new String[] { "VALIREQUESTNUM", "WORKORDERNUM" });
		}

		super.init();
	}

	@Override
	public IJpoSet getList() throws MroException {
		String appname = getJpo().getAppName();
		IJpoSet domainSet = null;
		if ("transplan".equalsIgnoreCase(appname)) {// 改造计划
			domainSet = getUserServer()
					.getJpoSet("TRANSNOTICE", "status='已审核'");
			domainSet.reset();
		} else {// 验证计划
			domainSet = getUserServer().getJpoSet("VALIREQUEST", "1=1");
			domainSet.reset();
		}
		return domainSet;
	}

}
