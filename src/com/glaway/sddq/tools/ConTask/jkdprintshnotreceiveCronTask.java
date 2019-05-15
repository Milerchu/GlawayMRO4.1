package com.glaway.sddq.tools.ConTask;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.sddq.tools.EmailUtil;

public class jkdprintshnotreceiveCronTask extends BaseStatefulJob {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute() throws MroException {
		// TODO Auto-generated method stub
		super.execute();
		System.out.println("已经进入定时任务方法！！！");
		EmailUtil.jkdprintshnotreceivetx();
	}
}
