package com.glaway.sddq.material.carlinfecommon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * <一物一档计算累计使用时长>
 * 
 * @author  public2795
 * @version  [版本号, 2019-4-17]
 * @since  [产品/模块版本]
 */
public class FLdAllusertime extends JpoField {
	/**
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		if (getJpo() != null
				&& !StringUtil.isNullOrEmpty(this.getJpo().getAppName())) {
			if (this.getJpo().getAppName().equalsIgnoreCase("ASSETFILE")) {
				Date mrodate = this.getJpo().getUserServer().getDate();//系统当前时间
				String type=this.getJpo().getString("type");
				if(type.equalsIgnoreCase("2")){//车上件
					Date newupdate=this.getJpo().getDate("newupdate");
					if(newupdate!=null){//最新上车时间不为空
						try {
							int usetime = daysBetween(mrodate, newupdate);
							if(this.getJpo().getString("historyallusetime")!=null){//历史累计使用时长不为空
								int newallusetime=usetime+this.getJpo().getInt("historyallusetime");
								this.setValue(newallusetime,GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
							}else{
								this.setValue(usetime,GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//计算本次使用时长
					}
				}else{//车下件
					if(!this.getJpo().getString("historyallusetime").isEmpty()){//历史累计使用时长不为空
						this.setValue(this.getJpo().getString("historyallusetime"),GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
					}
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
	private static int daysBetween(Date Enddate, Date Stardate) throws ParseException,
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
