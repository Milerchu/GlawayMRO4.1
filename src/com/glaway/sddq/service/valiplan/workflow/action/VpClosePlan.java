package com.glaway.sddq.service.valiplan.workflow.action;

import java.util.Date;
import java.util.GregorianCalendar;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;

/**
 * 
 * 验证计划-计划关闭操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月13日]
 * @since [产品/模块版本]
 */
public class VpClosePlan implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {

		Date startDate = jpo.getDate("startdate");
		int period = jpo.getInt("period");// 验证周期
		String unit = jpo.getString("periodunit");// 周期单位
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(startDate);
		if ("天".equals(unit) || "日".equals(unit)) {
			gc.add(5, period);
		} else if ("周".equals(unit)) {
			gc.add(3, period);
		} else if ("月".equals(unit)) {
			gc.add(2, period);
		} else if ("年".equals(unit)) {
			gc.add(1, period);
		}
		Date endDate = gc.getTime();// 根据周期算出的结束日期
		Date curDate = MroServer.getMroServer().getDate();// 当前日期
		if (endDate.after(curDate)) {// 未到验证周期则无法关闭
			throw new MroException("valiplan", "untime");
		}
	}

}
