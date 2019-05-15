package com.glaway.sddq.service.transplan.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 改造计划-改造车辆分布-选择改造车辆 databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月7日]
 * @since [产品/模块版本]
 */
public class SelectTrainsDataBean extends DataBean {
	@Override
	public int dialogok() throws IOException, MroException {
		// 选择的车辆
		List<IJpo> assetList = getDataBean("1536289520596").getJpoSet()
				.getSelections();

		int transcount = this.getJpo().getInt("TRANSCOUNT");

		String carnumsnew = this.getJpo().getString("CARNUMS");
		if (StringUtil.isStrNotEmpty(carnumsnew)) {// 已经有数据

			for (IJpo asset : assetList) {
				carnumsnew += "," + asset.getString("carno");
			}

			String[] carnumsnewList = carnumsnew.split(",");

			if (carnumsnewList.length > transcount) {
				throw new AppException("TRANSDIST", "TRANSCOUNT");
			}
			this.getDialog().getCreator().setValue(carnumsnew);

		} else {

			if (assetList.size() > transcount) {
				throw new AppException("TRANSDIST", "TRANSCOUNT");
			}
			String carnums = "";
			for (IJpo asset : assetList) {
				carnums += asset.getString("carno") + ",";
			}
			if (StringUtil.isStrNotEmpty(carnums)) {
				carnums = carnums.substring(0, carnums.length() - 1);
			}
			this.getDialog().getCreator().setValue(carnums);
		}

		// 关闭对话框
		this.dialogclose();
		return 1;
	}
}
