package com.glaway.sddq.service.troublemg.data;

import java.util.Date;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 项目问题跟踪jpo
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月9日]
 * @since [产品/模块版本]
 */
public class Troublemg extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -5165546562707947541L;

	@Override
	public void init() throws MroException {

		String status = this.getString("dealstatus");
		if ("关闭".equals(status)) {
			this.setFlag(GWConstant.S_READONLY, true);
		}
		/*
		 * if (!"挂起".equals(status)) {//不是挂起状态，编号只读 setFieldFlag("troublenum",
		 * GWConstant.S_READONLY, true); }
		 */
		super.init();
	}

	@Override
	public void add() throws MroException {
		IJpo wo = this.getParent();
		// 项目编号
		String prjnum = wo.getString("PROJECTNUM");
		// 流水号
		int snum = 1000;
		IJpoSet troublemgSet = MroServer.getMroServer().getJpoSet("TROUBLEMG",
				getUserServer());
		snum = snum + troublemgSet.count() + 1;
		// 问题编号
		String toublenum = prjnum + "-" + snum;

		setValue("TROUBLENUM", toublenum);

		super.add();
	}

	/**
	 * 
	 * 状态变更，记录到状态历史表
	 * 
	 * @param newstatus
	 *            新状态
	 * @param date
	 *            日期
	 * @param memo
	 *            备忘录
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void changestatus(String newstatus, Date date, String memo)
			throws MroException {
		if ("关闭".equals(getString("dealstatus"))) {
			throw new MroException("troublemg", "cannotchangestatus");
		}
		if (!newstatus.equals("")) {
			String oldstatus = getString("dealstatus");
			// 给当前jpo赋值
			setValue("DEALSTATUS", newstatus);
			// setValue("statusdate", date, 11L);

			IJpoSet statusset = getJpoSet("TROUBLEMGSTATUSHISTORY");
			IJpo statusnew = statusset.addJpo();
			statusnew.setValue("troublenum", getString("troublenum"), 11L);
			statusnew.setValue("siteid", getString("siteid"), 11L);
			statusnew.setValue("orgid", getString("orgid"), 11L);
			statusnew.setValue("changedate", date, 11L);
			statusnew.setValue("status", oldstatus, 11L);
			statusnew.setValue("newstatus", newstatus, 11L);
			statusnew.setValue("memo", memo, 11L);
			statusnew.setValue("changeby", getUserInfo().getPersonId(), 11L);
			statusnew.setFlag(7L, true);
		} else {
			throw new AppException("custinfo", "cannotnull");
		}
	}
}
