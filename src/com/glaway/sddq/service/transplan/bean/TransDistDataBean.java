package com.glaway.sddq.service.transplan.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 改造车辆分布TRANSDIST <功能描述>
 * 
 * @author public2176
 * @version [版本号, 2018-6-14]
 * @since [产品/模块版本]
 */
public class TransDistDataBean extends DataBean {

	@Override
	public int addrow() throws MroException, IOException {

		if (!"草稿".equals(getParent().getJpo().getString("status"))) {

			throw new MroException("service", "cannotoperate");// 当前状态无法操作

		}
		return super.addrow();

	}

	@Override
	public synchronized void delete() throws MroException {

		if (!"草稿".equals(getParent().getJpo().getString("status"))) {

			throw new MroException("service", "cannotoperate");// 当前状态无法操作
		}
		String responble = getJpo().getString("RESPONSIBLE");
		if (StringUtil.isStrNotEmpty(responble)) {
			if (!responble.equalsIgnoreCase(getUserName())) {
				throw new MroException("非本办事处负责人不能删除工单！");
			}
		}

		super.delete();

	}

	/**
	 * 生成改造工单 <功能描述>
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public int toggleaddplan() throws MroException, IOException {
		String status = getParent().getJpo().getString("STATUS");// 状态
		if (!getJpo().getString("RESPONSIBLE").equalsIgnoreCase(getUserName())) {
			if(!WorkorderUtil.isInAdminGroup(getUserName())){//非管理员
				throw new MroException("非本办事处主任不能创建工单！");
			}
		}
		if (getJpo().getBoolean("ISCREATEWO")) {
			throw new MroException("已经创建过工单,请勿重复创建！");
		}
		if ("执行中".equals(status)) {
			// 改造车辆分布中要处理的办事处
			String office = getJpo().getString("WHICHOFFICE");
			String station = getJpo().getString("station");
			String transModel = getJpo().getString("TRANSMODELS");
			String owner = getJpo().getString("RESPONSIBLE");
			int transcount = getJpo().getInt("transcount");
			String transnoticenum = getJpo().getParent().getString(
					"TRANSNOTICENUM");
			String[] carnums = getJpo().getString("carnums").split(",");

			for (int index = 0; index < transcount; index++) {// 根据改造数量创建工单

				if (index < carnums.length) {// 根据已有车号创建工单
					addToWorkOrderSet(owner, station, office, transModel,
							transnoticenum, carnums[index]);
				} else {
					addToWorkOrderSet(owner, station, office, transModel,
							transnoticenum, "");
				}

			}
			getJpo().setValue("ISCREATEWO", 1);// 设置创建成功标志
			showMsgbox("提示", "工单创建成功！");
			this.page.getAppBean().SAVE();

		} else {

			throw new MroException("service", "cannotoperate");// 当前状态无法操作

		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	private void addToWorkOrderSet(String owner, String station, String office,
			String transModel, String noticeNum, String carnum)
			throws MroException {
		// 计划编号
		String transplannum = this.page.getAppBean().getJpo()
				.getString("TRANSPLANNUM");
		IJpoSet WorkOrderSet = MroServer.getMroServer().getJpoSet("WORKORDER",
				MroServer.getMroServer().getSystemUserServer());
		IJpo jpo = WorkOrderSet.addJpo();
		// jpo.setValue("ASSETNUM", TransCar.getString("ASSETNUM"));
		jpo.setValue("MODELS", transModel,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		if (StringUtil.isStrNotEmpty(carnum)) {
			jpo.setValue("CARNUM", carnum,
					GWConstant.P_NOVALIDATION_AND_NOACTION);
			// 给是否计划生成字段赋值为"1"
			jpo.setValue("ISPLANCRAET", "1");
			IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
					"ASSET",
					"carno='" + carnum + "' and cmodel='" + transModel
							+ "' and assetlevel='ASSET' and type='2'");
			if (!assetSet.isEmpty()) {
				IJpo asset = assetSet.getJpo(0);
				// 设置项目号
				jpo.setValue("projectnum", asset.getString("projectnum"));
				jpo.setValue("ASSETNUM", asset.getString("ASSETNUM"));// 设置assetnum
			}

		}
		jpo.setValue("PLANNUM", transplannum,
				GWConstant.P_NOVALIDATION_AND_NOACTION);
		jpo.setValue("NOTICENUM", noticeNum);
		// jpo.setValue("CHECKPERSON", owner);
		// jpo.setValue("REPORTER", userid);
		jpo.setValue("WHICHOFFICE", office, GWConstant.P_NOACTION);
		jpo.setValue("WHICHSTATION", station, GWConstant.P_NOVALIDATION);
		// jpo.setValue("SERVENGINEER", userid);
		jpo.setValue("TYPE", "改造");
		jpo.setValue("SITEID", "ELEC");
		jpo.setValue("ORGID", "CRRC");
		jpo.setValue("ORDERNUM",
				WorkorderUtil.generateOrdernum(office, "TRANSORDER", "改造"));
		WorkOrderSet.save();
	}

	public int distribute() throws MroException, IOException {

		String officer = this.getJpo().getString("RESPONSIBLE");

		if (!getJpo().getUserInfo().getPersonId().equalsIgnoreCase(officer)) {
			if(!WorkorderUtil.isInAdminGroup(getUserName())) {//非管理员
				throw new MroException("非本办事处主任无权操作！");
			}
		}

		String status = this.page.getAppBean().getJpo().getString("status");
		if (!("执行中".equals(status))) {

			throw new MroException("service", "cannotoperate");// 当前状态无法操作

		}

		if (!getJpo().getBoolean("iscreatewo")) {// 是否创建了工单
			throw new MroException("valiplan", "noorder");
		}
		// 显示分配人员对话框
		loadDialog("distribute");

		return 1;
	}
}
