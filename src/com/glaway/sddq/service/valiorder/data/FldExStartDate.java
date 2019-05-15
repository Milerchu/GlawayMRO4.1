package com.glaway.sddq.service.valiorder.data;

import java.util.Date;
import java.util.GregorianCalendar;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 验证产品信息-开始时间字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月15日]
 * @since [产品/模块版本]
 */
public class FldExStartDate extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {

		super.action();

		boolean dqsfzy = getJpo().getParent().getBoolean(
				"TRANSPLAN.VALIREQUEST.PLANTOUSE");// 到期是否装用
		if (dqsfzy) {// 到期装用

			int period = getJpo().getParent().getInt("TRANSPLAN.PERIOD");// 验证周期
			String periodUnit = getJpo().getParent().getString(
					"TRANSPLAN.PERIODUNIT");// 周期单位

			Date startDate = getInputMroType().asDate();
			if(startDate!=null){
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
			}
		}

	}

}
