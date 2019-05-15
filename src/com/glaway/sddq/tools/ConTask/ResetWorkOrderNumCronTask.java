package com.glaway.sddq.tools.ConTask;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;

/**
 * 
 * 工单编号重置定时任务处理类
 * 
 * @author zhuhao
 * @version [版本号, 2019年3月5日]
 * @since [产品/模块版本]
 */
public class ResetWorkOrderNumCronTask extends BaseStatefulJob {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -3300268562258662380L;

	@Override
	public void execute() throws MroException {

		super.execute();

		IJpoSet autokeySet = MroServer
				.getMroServer()
				.getSysJpoSet("sys_autokey",
						"autokeyname in ('SORDERNUM','FORDERNUM','VORDERNUM','TORDERNUM')");
		if (autokeySet != null && autokeySet.count() > 0) {
			for (int idx = 0; idx < autokeySet.count(); idx++) {
				IJpo autoKey = autokeySet.getJpo(idx);
				// 将所有编号都重置为1000
				autoKey.setValue("SEED", 1000);
			}
			autokeySet.save();
		}

	}

}
