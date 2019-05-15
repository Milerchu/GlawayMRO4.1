package com.glaway.sddq.service.valiplan.data;

import java.util.Date;
import java.util.GregorianCalendar;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 验证计划-启动日期startdate字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月28日]
 * @since [产品/模块版本]
 */
public class FldStartDate extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();
		if ("验证".equals(getJpo().getString("plantype"))) {

			/* 根据验证周期设置完成时间 */
			// 验证周期
			int period = getJpo().getInt("PERIOD");
			// 验证周期单位
			String periodUnit = getJpo().getString("PERIODUNIT");
			// 当前输入的启动时间
			// Date startDate = getInputMroType().asDate();
			Date startDate = getJpo().getDate("startdate");
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(startDate);
			if ("天".equals(periodUnit) || "日".equals(periodUnit)) {
				gc.add(5, period);
			} else if ("周".equals(periodUnit)) {
				gc.add(3, period);
			} else if ("月".equals(periodUnit)) {
				gc.add(2, period);
			} else if ("年".equals(periodUnit)) {
				gc.add(1, period);
			}
			// 设置完成时间
			getJpo().setValue("ENDDATE", gc.getTime());
		} else {// 改造

		}

	}

}
