package com.glaway.sddq.service.failurelib.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 故障时间 字段类
 * 
 * @author zzx
 * @version [版本号, 2018-6-9]
 * @since [产品/模块版本]
 */
public class FldFaultime extends JpoField {
	/**
	 * 唯一序列
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void validate() throws MroException {
		super.validate();

		// 校验故障发生时间和客户来电时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		IJpo parentJpo = getJpo().getParent();
		// 来电时间
		String calltime = parentJpo.getString("CALLTIME");
		// 故障发生时间
		String faultime = getInputMroType().asString();
		try {
			Date date = simpleDateFormat.parse(calltime);
			Date dateFaultime = simpleDateFormat.parse(faultime);
			if (dateFaultime.getTime() > date.getTime()) {
				throw new MroException("failurelib", "faultime");// 故障发生时间不能晚于客户来电时间
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void action() throws MroException {

		super.action();
		if (getJpo().getParent() != null) {

			// 计算修后运行时间
			IJpo wo = getJpo().getParent();// 工单jpo
			Date updateTime = wo.getDate("UPDATETIME");// 检修日期
			Date faultTime = getInputMroType().asDate();// 故障发生时间
			if (faultTime != null && updateTime != null) {

				long runtime = (faultTime.getTime() - updateTime.getTime())
						/ (1000 * 60 * 60 * 24);// 修后运行时间
				if (runtime < 0) {
					runtime = 0;
				}
				// 设置修后运行时间值
				wo.setValue("AFTREPAIRRUNTIME", runtime);

			}
		}
	}
}
