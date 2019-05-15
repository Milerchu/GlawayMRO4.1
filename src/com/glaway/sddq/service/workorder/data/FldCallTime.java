package com.glaway.sddq.service.workorder.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 客户来电时间 字段类
 * 
 * @author zzx
 * @version [版本号, 2018-6-9]
 * @since [产品/模块版本]
 */
public class FldCallTime extends JpoField {

	/**
	 * 唯一序列
	 */
	private static final long serialVersionUID = 1L;

	public void validate() throws MroException {
		super.validate();

		Date newdate = MroServer.getMroServer().getDate();
		String calltime = getInputMroType().asString();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		try {
			Date date = simpleDateFormat.parse(calltime);
			if (date.getTime() > newdate.getTime()) {
				throw new MroException("WORKORDER", "CALLTIME");// 客户来电时间不能晚于当前时间
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
