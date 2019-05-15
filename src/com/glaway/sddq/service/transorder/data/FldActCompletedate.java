package com.glaway.sddq.service.transorder.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 改造工单-实际完成时间字段类
 * 
 * @author zzx
 * @version [版本号, 2018-8-10]
 * @since [产品/模块版本]
 */
public class FldActCompletedate extends JpoField {
	/**
	 * 唯一序列
	 */
	private static final long serialVersionUID = 1L;

	public void validate() throws MroException {
		super.validate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String actcompletedate = getInputMroType().asString();// 获取当前输入实际完成时间
		String actstartdate = this.getJpo().getString("ACTSTARTDATE");// 获取当前jpo实际开始时间
		try {
			if (StringUtil.isStrNotEmpty(actcompletedate)) {
				Date completedate = simpleDateFormat.parse(actcompletedate);
				if (StringUtil.isStrNotEmpty(actstartdate)) {
					Date startdate = simpleDateFormat.parse(actstartdate);
					if (startdate.getTime() > completedate.getTime()) {
						throw new MroException("TRANSORDER", "ACTCOMPLETEDATE");// 实际开始时间不能晚于实际完成时间
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
