package com.glaway.sddq.material.caritemlifecommom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <一物一档公共类调用方法类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-4-15]
 * @since  [产品/模块版本]
 */
public class CommomCarItemLifeMethod {
	private static CommomCarItemLifeMethod wfCtrl = null;;

	private CommomCarItemLifeMethod() {
	}

	public synchronized static CommomCarItemLifeMethod getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new CommomCarItemLifeMethod();
		}
		return wfCtrl;
	}
	/**
	 * 
	 * <//一物一档上车计算>
	 * @param asset [asset表当前jpo]
	 *
	 */
	public static void UPCAR(IJpo asset,String ancestor) {
		//firstupdate-首次上车时间
		//newupdate-最新上车时间
		//carupkm-上车时车辆走行公里数
		//status-状态
		try {
			System.out.println("ancestor:'"+ancestor+"'");
			Date mrodate = asset.getUserServer().getDate();//系统当前时间
			String firstupdate=asset.getString("firstupdate");
			if(firstupdate.isEmpty()){//判断首次上车时间是否为空
				asset.setValue("firstupdate", mrodate,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//上车赋值首次上车时间
			}
			if(!ancestor.isEmpty()){//判断上车的车辆ASSETNUM不是空
				IJpoSet carassetset = MroServer.getMroServer().getJpoSet("asset",MroServer.getMroServer().getSystemUserServer());
				carassetset.setUserWhere("assetnum='"+ancestor+"'");//上车的车辆SET
				if(!carassetset.isEmpty()){//判断上车的车辆SET不为空
					String RUNKILOMETRE=carassetset.getJpo(0).getString("RUNKILOMETRE");//上车的车辆累计走行公里数
					System.out.println("RUNKILOMETRE:'"+RUNKILOMETRE+"'");
					asset.setValue("carupkm", RUNKILOMETRE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//上车赋值上车时车辆走行公里数
				}
			}
			asset.setValue("newupdate", mrodate,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//上车赋值最新上车时间
			asset.setValue("status", "可用",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//上车赋值状态
			
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * <//一物一档下车计算>
	 * @param asset [asset表当前jpo]
	 * @throws ParseException 
	 *
	 */
	public static void DOWNCAR(IJpo asset,String ancestor){
		//itemlevel-层次
		//newdowndate-最新下车时间
		//usetime-本次使用时长（天）
		//historyallusetime-历史累计使用时长
		//newupcarbeforekm-本次上车前走行公里数
		//neupcarafterkm-本次上车后走行公里数
		//RUNKILOMETRE-累计走行公里数
		//status-状态
		try {
			System.out.println("ancestor2222:'"+ancestor+"'");
			Date mrodate = asset.getUserServer().getDate();//系统当前时间
			asset.setValue("itemlevel", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//下车清空层次信息
			asset.setValue("newdowndate", mrodate,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//下车赋值最新下车时间
			String strnewupdate=asset.getString("newupdate");
			Date newupdate=asset.getDate("newupdate");//最新上车时间
			if(!strnewupdate.isEmpty()){//判断最新上车时间不为空
				int usetime = daysBetween(mrodate, newupdate);//计算本次使用时长
				asset.setValue("usetime", usetime,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//下车赋值本次使用时长
			}
			double historyallusetime=asset.getDouble("historyallusetime");//历史累计使用时长
			double usetime=asset.getDouble("usetime");//本次使用时长
			double newhistoryallusetime=historyallusetime+usetime;//新历史累计使用时长
			asset.setValue("historyallusetime", newhistoryallusetime,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//下车赋值历史累计使用时长
			if(!ancestor.isEmpty()){//判断上车的车辆ASSETNUM不是空
				IJpoSet carassetset = MroServer.getMroServer().getJpoSet("asset",MroServer.getMroServer().getSystemUserServer());
				carassetset.setUserWhere("assetnum='"+ancestor+"'");//上车的车辆SET
				if(!carassetset.isEmpty()){//判断下车的车辆SET不为空
					String RUNKILOMETRE=carassetset.getJpo(0).getString("RUNKILOMETRE");//下车的车辆累计走行公里数
					System.out.println("RUNKILOMETRE2222:'"+RUNKILOMETRE+"'");
					asset.setValue("newupcarbeforekm", RUNKILOMETRE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//上车赋值本次上车前走行公里数
					asset.setValue("neupcarafterkm", RUNKILOMETRE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//上车赋值本次上车后走行公里数
					asset.setValue("RUNKILOMETRE", RUNKILOMETRE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//上车赋值累计走行公里数
				}
			}
//			asset.setValue("status", "故障",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//下车赋值状态
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * <//一物一档入库计算>
	 * @param asset [asset表当前jpo]
	 *
	 */
	public static void INLOCATION(IJpo asset) {
		//PRODUCTDATE-生产时间
		//firstinlocationdate-首次入库时间
		//newupcarbeforekm-本次上车前走行公里数
		//INSTOREDATE-本次入库时间
		try {
			Date mrodate = asset.getUserServer().getDate();//系统当前时间
			String itemnum=asset.getString("itemnum");//物料编码
			String firstinlocationdate=asset.getString("firstinlocationdate");//首次入库时间
			if(firstinlocationdate.isEmpty()){//判断首次入库时间为空
				IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item",MroServer.getMroServer().getSystemUserServer());
				itemset.setUserWhere("itemnum='"+itemnum+"'");
				if(!itemset.isEmpty()){
					String ITEMPOTYPE= itemset.getJpo(0).getString("ITEMPOTYPE");//产品类型
					if(!ITEMPOTYPE.equalsIgnoreCase("自制件")){
						asset.setValue("PRODUCTDATE", mrodate,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//入库赋值生产时间
					}
				}
				asset.setValue("newupcarbeforekm", 0,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//入库赋值本次上车前走行公里数
				asset.setValue("firstinlocationdate", mrodate,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//入库赋值首次入库时间
				asset.setValue("INSTOREDATE", mrodate,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//入库赋值本次入库时间
			}else{
				asset.setValue("INSTOREDATE", mrodate,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//入库赋值本次入库时间
			}
		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * <//一物一档出库计算>
	 * @param asset [asset表当前jpo]
	 *
	 */
	public static void OUTLOCATION(IJpo asset) {
		//
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
