package com.glaway.sddq.service.trcheckorder.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * tr检查单选择模板DATAbean
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月9日]
 * @since [产品/模块版本]
 */
public class SelectCheckDetailsBean extends DataBean {

	/**
	 * 选择售后tr检查单模板并添加至售后tr检查单明细中
	 * 
	 * @author sjchen
	 */

	@Override
	public int dialogok() throws IOException, MroException {
		// TODO Auto-generated method stub
		DataBean databean = this.page.getAppBean().getDataBean(
				"tab_checkdetail");

		// 获得售后tr检查单明细表
		IJpoSet checkDetailSet = MroServer.getMroServer()
				.getJpoSet("TRCHECKDETAIL",
						MroServer.getMroServer().getSystemUserServer());
		String trcheckordernum = this.page.getAppBean().getJpo()
				.getString("TRCHECKORDERNUM");
		String orgid = this.page.getAppBean().getJpo().getString("ORGID");
		String siteid = this.page.getAppBean().getJpo().getString("SITEID");

		List<IJpo> list = getJpoSet().getSelections();
		for (int i = 0; i < list.size(); i++) {
			IJpo jpo = list.get(i);

			if (checkRepeat(checkDetailSet, list)) {
				throw new MroException("存在重复序号");
			} else {
				IJpo checkDetail = checkDetailSet.addJpo();
				checkDetail.setValue("SEQUENCE", jpo.getString("SEQUENCE"),
						GWConstant.S_READONLY);
				checkDetail.setValue("TRCHECKORDERNUM", trcheckordernum,
						GWConstant.P_NOVALIDATION);
				checkDetail.setValue("ORGID", orgid, GWConstant.P_NOVALIDATION);
				checkDetail.setValue("SITEID", siteid,
						GWConstant.P_NOVALIDATION);
				checkDetail.setValue("CHECKTYPE", jpo.getString("CHECKTYPE"),
						GWConstant.S_READONLY);
				checkDetail.setValue("CHECKCLASS", jpo.getString("CHECKCLASS"),
						GWConstant.S_READONLY);
				checkDetail.setValue("CHECCONTEXT",
						jpo.getString("CHECCONTEXT"), GWConstant.S_READONLY);
			}

		}
		checkDetailSet.save();
		databean.resetAndReload();
		checkDetailSet.destroy();
		return super.dialogok();
	}

	private boolean checkRepeat(IJpoSet table, List<IJpo> select)
			throws MroException {
		boolean flag = false;

		for (int i = 0; i < select.size(); i++) { // 复选框
			for (int j = 0; j < table.count(); j++) {// 明细表
				if (select.get(i).getString("SEQUENCE")
						.equals(table.getJpo(j).getString("SEQUENCE"))) {
					flag = true;
					break;
				}
			}

			if (flag) {
				break; // 一旦存在相同序号直接弹出循环，不再判断
			}
		}
		return flag;
	}

	/**
	 * 判断删除模板中的行，该行是否已在明细表中
	 */

	@Override
	public synchronized void delete() throws MroException {
		// TODO Auto-generated method stub

		IJpoSet checkDetailSet = MroServer.getMroServer()
				.getJpoSet("TRCHECKDETAIL",
						MroServer.getMroServer().getSystemUserServer());

		boolean flag = false;
		for (int i = 0; i < checkDetailSet.count(); i++) {
			IJpo jpo = checkDetailSet.getJpo(i);

			if (getString("SEQUENCE").equals(jpo.getString("SEQUENCE"))) {
				flag = true;
				break;
			}

		}
		if (flag) {
			super.undelete();
			throw new MroException("该模板在tr检查单明细中被使用，无法删除");
		} else {
			super.delete();
		}
	}
}
