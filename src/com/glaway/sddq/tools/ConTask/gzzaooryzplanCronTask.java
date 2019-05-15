package com.glaway.sddq.tools.ConTask;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.system.crontask.BaseStatefulJob;

/**
 * 
 * 改造/验证计划审核报警(改造/验证计划制定后2天未完成售后审批)（12）
 * 
 * 
 * @author zzx
 * @version [版本号, 2018-8-30]
 * @since [产品/模块版本]
 */
public class gzzaooryzplanCronTask extends BaseStatefulJob {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute() throws MroException {
		// TODO Auto-generated method stub
		super.execute();
		System.out.println("已经进入定时任务方法！！！");
		// EmailUtil.gzaooryzplanlate();
	}
}
