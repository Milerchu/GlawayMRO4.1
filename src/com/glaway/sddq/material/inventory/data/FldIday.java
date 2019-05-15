package com.glaway.sddq.material.inventory.data;

import io.netty.util.internal.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <ASSET表库存数据计算在库天数>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class FldIday extends JpoField {
	/**
	 * 计算在库天数
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		if (getJpo() != null
				&& !StringUtil.isNullOrEmpty(this.getJpo().getAppName())) {
			if (this.getJpo().getAppName().equalsIgnoreCase("GZINV")
					|| this.getJpo().getAppName().equalsIgnoreCase("INV1030")
					|| this.getJpo().getAppName().equalsIgnoreCase("INVENTORY")
					|| this.getJpo().getAppName().equalsIgnoreCase("QTINV")
					|| this.getJpo().getAppName().equalsIgnoreCase("STOREROOM")) {
				Date Stardate = this.getJpo().getDate("INSTOREDATE");
				Date Enddate = MroServer.getMroServer().getDate();
				if (Stardate != null) {
					try {
						// 调用天数计算方法
						int inday = daysBetween(Enddate, Stardate);
						setValue(inday, GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					setValue("0", GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
				}
			}

		}
	}

	/**
	 * 
	 * <天数计算方法>
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private int daysBetween(Date Enddate, Date Stardate) throws ParseException,
			MroException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Enddate = sdf.parse(sdf.format(Enddate));
		Stardate = sdf.parse(sdf.format(Stardate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(Enddate);
		long time1 = cal.getTimeInMillis();

		cal.setTime(Stardate);
		long time2 = cal.getTimeInMillis();
		// 两个日期相差的天数
		long daysbtw = (time1 - time2) / (1000 * 3600 * 24);
		// long j = cycle * 365;
		// long i = j - daysbtw;
		return Integer.parseInt(String.valueOf(daysbtw));
	}
}
