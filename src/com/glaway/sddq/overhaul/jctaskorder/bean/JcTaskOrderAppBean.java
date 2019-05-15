package com.glaway.sddq.overhaul.jctaskorder.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.material.invtrans.common.CommonInventory;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 交车工单AppBean
 * 
 * @author bchen
 * @version [版本号, 2018-5-18]
 * @since [产品/模块版本]
 */
public class JcTaskOrderAppBean extends AppBean {

	/**
	 * 交车派工
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 */
	public void JCDISPATCH() throws MroException, IOException {
		String liableperson = this.getString("LIABLEPERSON");
		String status = this.getString("STATUS");
		if((status != null && status.equals(SddqConstant.JC_STATUS_DJC))){
			throw new AppException("jctaskorder", "jcstatusdjc");
		}
		if((status != null && status.equals(SddqConstant.JC_STATUS_WC))){
			throw new AppException("jctaskorder", "jcstatuswc");			
		}if((status != null && status.equals(SddqConstant.JC_STATUS_PG))){
			throw new AppException("jctaskorder", "jcstatuspg");			
		}if (!liableperson.isEmpty()&&status.equals(SddqConstant.JC_STATUS_CG)) {
			this.getAppBean()
					.getJpo()
					.setValue("STATUS", SddqConstant.JC_STATUS_PG,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			Date arrivaldate = MroServer.getMroServer().getDate();
			getJpo().setValue("REALSTARTTIME", arrivaldate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getAppBean().SAVE();
		}
		else {
			throw new AppException("jctaskorder", "jcdispatch");
		}
	}

	/**
	 * 确认交车
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 */
	public void COMPLETED() throws MroException, IOException {
		String status = this.getString("STATUS");
		if (status != null && status.equals(SddqConstant.JC_STATUS_CG)) {
			throw new AppException("jxtaskorder", "completed");
		}
		if (status != null && status.equals(SddqConstant.JC_STATUS_WC)) {
			throw new AppException("jctaskorder", "jcstatuswc");
		}
		if (status != null && status.equals(SddqConstant.JC_STATUS_PG)) {
			// 确认交车前判断是否至少一个产品选择了车型车号
			IJpoSet jxproductJposet = this.getJpo().getJpoSet("JCPRODUCT");
			if (jxproductJposet != null && jxproductJposet.count() > 0) {
				boolean flag = false;
				for (int index = 0; index < jxproductJposet.count(); index++) {
					IJpo jpo = jxproductJposet.getJpo(index);
					if (StringUtil.isStrNotEmpty(jpo.getString("cmodel"))
							&& StringUtil.isStrNotEmpty(jpo.getString("carno"))
							&& StringUtil.isStrNotEmpty(jpo
									.getString("CARRIAGENUM"))) {
						flag = true;
						break;
					}
				}
				if (flag) {
					this.getAppBean()
							.getJpo()
							.setValue("STATUS", SddqConstant.JC_STATUS_DJC,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					this.getAppBean().SAVE();
				} else {
					throw new AppException("jctaskorder", "leastValidOne");
				}
			} else {
				throw new AppException("jctaskorder", "leastOne");
			}
		}
	}

	/**
	 * 完成交车
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 */
	public void JCCOMPLETED() throws MroException, IOException {
		String status = this.getString("STATUS");
		if (status != null && status.equals(SddqConstant.JC_STATUS_CG)) {
			throw new AppException("jxtaskorder", "completed");
		}
		if (status != null && status.equals(SddqConstant.JC_STATUS_PG)) {
			throw new AppException("jxtaskorder", "jccompleted");
		}
		if (status != null && status.equals(SddqConstant.JC_STATUS_WC)) {
			throw new AppException("jctaskorder", "hascomplete");
		}
		if (status != null && status.equals(SddqConstant.JC_STATUS_DJC)) {
			String jctasknum = this.getString("JCTASKNUM");
			String cmodel = this.getString("CMODEL");
			String carno = this.getString("CARNO");
			String repairprocess = this.getString("REPAIRPROCESS");
			// 上下车更新记录
			IJpoSet swapJpost = MroServer.getMroServer().getSysJpoSet(
					"EXCHANGERECORD",
					"TASKORDERNUM='" + jctasknum
							+ "' and ISDO='0' and TASKTYPE='"
							+ SddqConstant.SXC_OH_JC + "' and ISFAILURE !='是'");
			if (swapJpost != null && swapJpost.count() > 0) {
				swapHistory(swapJpost);
			}

			// 1、首先获取交车产品列表
			IJpoSet jxproductJposet = this.getJpo().getJpoSet("JCPRODUCT");
			if (jxproductJposet != null && jxproductJposet.count() > 0) {
				zcToAsset(jxproductJposet);
			}

			this.getAppBean()
					.getJpo()
					.setValue("STATUS", SddqConstant.JC_STATUS_WC,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			Date arrivaldate = MroServer.getMroServer().getDate();
			getJpo().setValue("REALENDTIME", arrivaldate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			IJpoSet assetset = MroServer.getMroServer().getSysJpoSet(
					"ASSET",
					"CMODEL='" + cmodel + "' and CARNO='" + carno
							+ "'and REPAIRPROCESS='" + repairprocess + "'");
			if (!assetset.isEmpty()) {
				assetset.getJpo().setValue("UPDATETIME", arrivaldate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				assetset.save();
			}
			this.getAppBean().SAVE();
		}
	}

	/**
	 * @Description 装车功能实现
	 * @param jposet
	 * @throws MroException
	 */
	public void zcToAsset(IJpoSet jposet) throws MroException {

		for (int i = 0; i < jposet.count(); i++) {
			IJpo jpo = jposet.getJpo(i);
			if (SddqConstant.JC_F_STATUS.equals(jpo.getString("STATUS"))) {
				// 根据选择的车型车号获取Ancestor
				String carno = jpo.getString("CARNO");
				String cmodel = jpo.getString("CMODEL");
				String carriagenum = jpo.getString("CARRIAGENUM");
				String sqn = jpo.getString("sqn");
				String jcassetnum = jpo.getString("assetnum");
				String parent = "";
				String loc = jpo.getString("ASSET.LOCATION");
				String ancestor = jpo.getString("CARASSETNUM");
				if (StringUtil.isStrEmpty(ancestor)) {
					IJpoSet assetSet = MroServer.getMroServer()
							.getSysJpoSet(
									"ASSET",
									"CARNO='" + carno + "' and CMODEL='"
											+ cmodel + "'");
					ancestor = assetSet.getJpo(0).getString("ANCESTOR");
				}
				if (!StringUtil.isStrEmpty(ancestor)) {
					IJpoSet carassetset = MroServer.getMroServer()
							.getSysJpoSet(
									"ASSET",
									"ANCESTOR='" + ancestor
											+ "' and CARRIAGENUM='"
											+ carriagenum + "'");
					if (carassetset != null && carassetset.count() > 0) {
						parent = carassetset.getJpo(0).getString("ASSETNUM");

						IJpoSet installMboset = MroServer.getMroServer()
								.getSysJpoSet("ASSET",
										"ancestor ='" + jcassetnum + "'");

						// 给从库存中取出的部件及子部件设置ANCESTOR和parent
						if (installMboset != null && installMboset.count() > 0) {
							for (int index = 0; index < installMboset.count(); index++) {
								if (installMboset.getJpo(index)
										.getString("ASSETNUM")
										.equals(jcassetnum)) {
									installMboset
											.getJpo(index)
											.setValue(
													"parent",
													parent,
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									installMboset
											.getJpo(index)
											.setValue(
													"ASSETLEVEL",
													"SYSTEM",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
								installMboset
										.getJpo(index)
										.setValue(
												"ANCESTOR",
												ancestor,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								installMboset
										.getJpo(index)
										.setValue(
												"LOCATION",
												"",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								installMboset
										.getJpo(index)
										.setValue(
												"BINNUM",
												"",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								installMboset
										.getJpo(index)
										.setValue(
												"TYPE",
												"2",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
							installMboset.save();

						}
					}
					if (StringUtil.isStrNotEmpty(loc)) {
						CommonInventory.OUTINVENTORY("", 1, loc,
								jpo.getString("itemnum"), jcassetnum,
								jpo.getString("JCTASKNUM"));// 自动出库（不需要传给ERP）
					}
					jpo.setValue("status", SddqConstant.JC_T_STATUS,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
		}
	}

	/**
	 * 上下车记录更新
	 * 
	 * @param jposet
	 *            [参数说明]
	 * @throws MroException
	 */
	public void swapHistory(IJpoSet jposet) throws MroException {

		for (int i = 0; i < jposet.count(); i++) {

			IJpo jpo = jposet.getJpo(i);
			// 下车件的信息
			String assetnum = jpo.getString("ASSETNUM");
			// String sqn = jpo.getString("sqn");
			// String itemnum = jpo.getString("itemnum");
			String ancestor = jpo.getString("ASSET.ANCESTOR");
			String parent = jpo.getString("ASSET.PARENT");
			String faultposition = jpo.getString("FAULTPOSITION");

			// 获取ASSET表中待拆卸的选中部件以及子部件
			IJpoSet removeMboset = MroServer.getMroServer().getSysJpoSet(
					"ASSET");
			removeMboset
					.setUserWhere("ANCESTOR='"
							+ ancestor
							+ "' and assetnum in (select assetnum from asset  start with assetnum ='"
							+ assetnum
							+ "' connect by parent = PRIOR assetnum)");
			removeMboset.reset();

			// 保留结构，更新Asset表的parent以及ancestor
			if (removeMboset != null && removeMboset.count() > 0) {
				for (int index = 0; index < removeMboset.count(); index++) {
					if (removeMboset.getJpo(index).getString("ASSETNUM")
							.equals(assetnum)) {
						removeMboset.getJpo(index).setValue("parent", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						removeMboset.getJpo(index).setValue("ASSETLEVEL",
								"ASSET",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					removeMboset.getJpo(index).setValue("ANCESTOR", assetnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					removeMboset.getJpo(index).setValue("LOCATION", "",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 检修的下车件默认要放到待处理物资库
					removeMboset.getJpo(index).setValue("TYPE", "3",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				removeMboset.save();
			}

			// 上车件的信息
			String newassetnum = jpo.getString("NEWASSETNUM");
			// String newsqn = jpo.getString("NEWSQN");
			// String newitemnum = jpo.getString("NEWITEMNUM");

			IJpoSet installMboset = MroServer.getMroServer().getSysJpoSet(
					"ASSET");
			installMboset
					.setUserWhere("assetnum in (select assetnum from asset  start with assetnum ='"
							+ newassetnum
							+ "' connect by parent = PRIOR assetnum)");
			installMboset.reset();

			// 给从库存中取出的部件及子部件设置ANCESTOR和parent
			if (!installMboset.isEmpty()) {
				for (int index = 0; index < installMboset.count(); index++) {

					if (installMboset.getJpo(index).getString("ASSETNUM")
							.equals(newassetnum)) {
						installMboset.getJpo(index).setValue("parent", parent,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						installMboset.getJpo(index).setValue("ASSETLEVEL",
								"SYSTEM",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					installMboset.getJpo(index).setValue("ANCESTOR", ancestor,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					installMboset.getJpo(index).setValue("LOCATION", "",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					installMboset.getJpo(index).setValue("BINNUM", "",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					installMboset.getJpo(index).setValue("TYPE", "2",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					installMboset.getJpo(index).setValue("RNUM", faultposition,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				installMboset.save();
			}
			// 出库
			CommonInventory.OUTINVENTORY("", 1, jpo.getString("newloc"),
					jpo.getString("newitemnum"), jpo.getString("newassetnum"),
					jpo.getString("TASKORDERNUM"));
			// 入库
			CommonInventory.ININVENTORY("", 1, jpo.getString("location"),
					jpo.getString("itemnum"), jpo.getString("assetnum"),
					jpo.getString("TASKORDERNUM"));

			jpo.setValue("ISDO", "1", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			jposet.save();
		}
	}
}
