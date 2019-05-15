package com.glaway.sddq.service.valiplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 验证计划-验证产品范围主Jpo类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月30日]
 * @since [产品/模块版本]
 */
public class ValiProRange extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();
		if (getParent() != null) {
			// 计划状态
			String planStatus = getParent().getString("status");
			String[] rangeAttr = { "PRODUCTCODENAMENUM", "PARTCODE", "CHIPLOC",
					"CHIPDESC", "PRODUCTCODE", "OWNERCUSTOMER", "TRANSMODELS",
					"VALICOUNT", "VALICOUNTUNIT", "PERIOD", "PERIODUNIT",
					"REMARK", "BVERSION", "AVERSION" };
			if ("草稿".equals(planStatus)) {// 只在草稿状态可编辑
				setFieldFlag(rangeAttr, GWConstant.S_READONLY, false);

			} else {
				setFieldFlag(rangeAttr, GWConstant.S_READONLY, true);

			}
		}
	}

	/**
	 * 
	 * 软件版本表中新增记录
	 * 
	 * @param version
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void addSoftversion(String version) throws MroException {

		String plannum = getString("PLANNUM");
		String ownercust = getString("OWNERCUSTOMER");
		String models = getString("TRANSMODELS");
		// 判断是否已经存在
		IJpoSet existSet = MroServer.getMroServer().getSysJpoSet(
				"VALIPLANSOFTVERSION",
				"plannum='" + plannum + "' and station='" + ownercust
						+ "' and version='" + version + "' and models='"
						+ models + "'");
		if (existSet.isEmpty()) {// 为空时才新增

			IJpoSet softVersionSet = MroServer.getMroServer().getSysJpoSet(
					"VALIPLANSOFTVERSION");
			IJpo softVersion = softVersionSet.addJpo();
			softVersion.setValue("PLANNUM", plannum);// 计划编号
			softVersion.setValue("STATION", ownercust);// 机务段
			softVersion.setValue("VERSION", version);// 软件版本
			softVersion.setValue("MODELS", models);// 车型
			softVersion.setValue("ORGID", "CRRC");// 车型
			softVersion.setValue("SITEID", "ELEC");// 车型

			softVersionSet.save();

		}

	}
}
