package com.glaway.sddq.tools.ConTask;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.sddq.tools.EmailUtil;

/**
 * 
 * 送修单打印后一个工作日承修单位未接收(送修品接收延误总清单) 定时任务
 * 
 * @author zzx
 * @version [版本号, 2018-8-30]
 * @since [产品/模块版本]
 */
public class sxdcxdwnotreceiveCronTask extends BaseStatefulJob {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute() throws MroException {
		// TODO Auto-generated method stub
		super.execute();
		System.out.println("已经进入定时任务方法！！！");
		EmailUtil.sxdcxcompanynotreceivelatetx();
	}
}
