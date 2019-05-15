package com.glaway.sddq.service.transplan.bean;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 改造计划-改造车辆分布-选择改造车辆 table databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月7日]
 * @since [产品/模块版本]
 */
public class SelectTrainsTableBean extends DataBean {
	@Override
	public void initJpoSet() throws MroException {

		super.initJpoSet();

		IJpoSet jposet = getJpoSet();
		// 当前车辆分布jpo
		IJpo dist = this.getDataBean("1520318024916").getJpo();
		String station = dist.getString("STATION");// 配属用户
		String whichoffice = dist.getString("WHICHOFFICE");// 办事处
		String transmodels = dist.getString("TRANSMODELS");// 车型
		// String where = "assetlevel='ASSET' and type='2' and OWNERCUSTOMER ='"
		// + whichoffice + "' and CMODEL ='" + transmodels + "'";

		String where = "ownercustomer in(select custnum from custinfo where whichoffice='"
				+ whichoffice
				+ "') and assetlevel='ASSET' and type='2' and  CMODEL ='"
				+ transmodels + "'";
		// 排除已经选择过的车号
		if (StringUtil.isStrNotEmpty(dist.getString("CARNUMS"))) {

			String[] carnums = dist.getString("CARNUMS").split(",");
			String selected = "";
			for (String carnum : carnums) {
				selected += "'" + carnum + "',";
			}
			if (StringUtil.isStrNotEmpty(selected)) {
				selected = selected.substring(0, selected.length() - 1);
				where += " and carno not in(" + selected + ") ";
			}
		}

		jposet.setQueryWhere(where);
		jposet.reset();

	}
}
