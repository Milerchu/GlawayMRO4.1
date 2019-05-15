package com.glaway.sddq.service.transplan.data;

import java.util.Calendar;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造/验证计划 性质字段类transtype
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-5-16]
 * @since [MRO4.1/模块版本]
 */
public class FldTranstype extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -7573765730711398464L;

	@Override
	public IJpoSet getList() throws MroException {
		String appname = getJpo().getAppName();
		IJpoSet domainSet = null;
		if ("transplan".equalsIgnoreCase(appname)) {// 改造计划
			domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
					"domainid='TRANSPLANTYPE2'");
			domainSet.reset();
		} else {// 验证计划
			domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
					"domainid='VALITYPE'");
			domainSet.reset();
		}

		return domainSet;
	}

	@Override
	public void action() throws MroException {
		if (getJpo().isNew()) {

			// 当前输入的改造、验证性质的值
			String inputtype = this.getInputMroType().asString();
			// 当前年份
			int curYear = Calendar.getInstance().get(Calendar.YEAR);
			String transType = "";
			if ("硬件改造".equals(inputtype)) {
				transType = "YJGZ";
			} else if ("软件改造".equals(inputtype)) {
				transType = "RJGZ";
			} else if ("质量普查".equals(inputtype)) {
				transType = "ZLPC";
			} else if ("硬件验证".equals(inputtype)) {
				transType = "YJYZ";
			} else if ("软件验证".equals(inputtype)) {
				transType = "RJYZ";
			}
			/* 获取流水数 */
			int snum = WorkorderUtil.getSysAutoKey(transType + "PLANNUM",
					"CRRC", "ELEC");

			String plannum = "JH-" + transType + "-" + curYear + "-" + snum;
			// 设置计划编号
			getJpo().setValue("TRANSPLANNUM", plannum);
		}
		super.action();
	}
}
