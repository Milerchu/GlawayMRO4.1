package com.glaway.sddq.service.failurelib.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

public class FldFailurecode extends JpoField {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();
		if (getInputMroType().getStringValue() != null) {
			getJpo().setFieldFlag("FAULTCODE", GWConstant.S_READONLY, false);
		}
		String failurecode = getInputMroType().getStringValue();
		if (getJpo().getParent() != null) {

			String model = getJpo().getParent().getString("MODELS");
			String failname = "";
			// 车型、故障代码获取故障名称
			IJpoSet codeSet = MroServer.getMroServer().getSysJpoSet(
					"MODELFAILURECODE");
			codeSet.setUserWhere("FAILURECODE='" + failurecode
					+ "' and modelnum='" + model + "'");
			codeSet.reset();
			if (!codeSet.isEmpty()) {
				failname = codeSet.getJpo(0).getString("FAILUREDESC");
			}
			getJpo().setValue("FAILUREDESC", failname);
		}

	}

	@Override
	public IJpoSet getList() throws MroException {
		if (getJpo() != null) {

			String where = "";
			IJpo fl = getJpo();// 故障记录jpo

			if (fl.getParent() != null) {

				IJpo wo = fl.getParent();// 工单jpo

				String models = wo.getString("MODELS");

				where = "modelnum='" + models + "'";

			} else {

				where = "1=2";

			}
			setListWhere(where);
		}

		return super.getList();
	}
}
