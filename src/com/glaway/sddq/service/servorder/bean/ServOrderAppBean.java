package com.glaway.sddq.service.servorder.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 服务工单AppBean
 * 
 * @author ygao
 * @version [版本号, 2017-11-1]
 * @since [产品/模块版本]
 */
public class ServOrderAppBean extends AppBean {

	@Override
	public int ROUTEWF() throws Exception {
		String status = getJpo().getString("status");
		if ("关闭".equals(status)) {
			throw new MroException("servorder", "statusnowf");
		}

		if ("处理中".equals(status)) {
			if (getJpo().getDate("OPERATETIME") == null)// 作业开始时间为空
			{
				throw new MroException("", "开始作业后才能发送工作流！");
			}
		}
//		//------------xlb2019-4-18整车生命周期状态变更------------//
//				if("草稿".equals(status)){
//					String type="";
//					String SERVORDERTYPE = getJpo().getString("SERVORDERTYPE");
//					String assetnum = getJpo().getString("assetnum");
//					if(SERVORDERTYPE.equalsIgnoreCase("调试交车")){
//						type="调试交车";
//						Carlifestatuscommon.CHANGESTATUS(assetnum, type);
//					}
//					if(SERVORDERTYPE.equalsIgnoreCase("新车整备")){
//						type="新车整备/预验收";
//						Carlifestatuscommon.CHANGESTATUS(assetnum, type);
//					}
//				}
//				//------------xlb2019-4-18整车生命周期状态变更------------//
		return super.ROUTEWF();
	}

	/**
	 * 
	 * 到达现场按钮
	 * 
	 */
	public void ARRIVAL() throws Exception {

		// 判断当前登录人是否是工单执行人
		if (!WfControlUtil.isCurUser(getJpo())) {
			throw new MroException("workorder", "notwfcuruser");
		}

		if (getJpo().getString("status").equals("处理中")) {

			if (getJpo().getDate("ARRIVETIME") != null) {
				throw new MroException("", "已经到达现场!");
			}
			Date arrivaldate = MroServer.getMroServer().getDate();
			getJpo().setValue("ARRIVETIME", arrivaldate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.SAVE();
		} else {
			// 当前状态无法操作
			throw new MroException("servorder", "statusnoaction");
		}

	}

	/**
	 * 
	 * 开始作业按钮
	 * 
	 */
	public void STARTWORK() throws Exception {

		// 判断当前登录人是否是工单执行人
		if (!WfControlUtil.isCurUser(getJpo())) {
			throw new MroException("workorder", "notwfcuruser");
		}

		if (getJpo().getString("status").equals("处理中")) {
			if (getJpo().getDate("OPERATETIME") != null) {
				throw new MroException("", "已经开始作业!");
			}
			if (getJpo().getDate("ARRIVETIME") == null) {
				// 未进行到达现场操作
				throw new MroException("workorder", "arrivefirst");
			}
			Date operadate = MroServer.getMroServer().getDate();
			getJpo().setValue("OPERATETIME", operadate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.SAVE();
		} else {
			throw new MroException("servorder", "statusnoaction");
		}

	}

	/**
	 * 
	 * 复制工单
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public int DUPLICATEORDER() throws MroException {
		if (getJpo().isNew()) {
			throw new MroException("workorder", "cannotdump");
		}
		String office = getAppBean().getJpo().getString("whichoffice");// 旧工单编号
		String newOrderNum = WorkorderUtil.generateOrdernum(office,
				getAppBean().getAppName(),
				getAppBean().getJpo().getString("type"));// 新工单编号
		this.duplicate();

		IJpo dupJpo = getAppBean().getJpo();// 复制的jpo
		dupJpo.setValue("ordernum", newOrderNum);
		dupJpo.setValueNull("COMPLETETIME", GWConstant.P_NOVALIDATION);// 清空数据
		try {
			this.reloadPage();
			// this.reloadCurrTab();
			this.getAppBean().selectRow(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	@Override
	public void duplicate() throws MroException {
		super.duplicate();
	}
}
