package com.glaway.sddq.back.Interface.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.back.Interface.data.UserInterface;

/**
 * 
 * 接口管理状态变更类
 * 
 * @author public2175
 * @version [版本号, 2018-6-6]
 * @since [产品/模块版本]
 */
public class UserInterfaceStatusBean extends DataBean {
	@Override
	public int execute() throws MroException {


		IJpo mr = getAppBean().getJpo();
		if (mr == null) {
			return GWConstant.NOACCESS_SAMEMETHOD;
		}
		checkSave();
		UserInterface ct = (UserInterface) mr;
		String oldStatus = mr.getString("status");
		String newStatus = this.getString("status");
		if (oldStatus.equals(newStatus)) {
			throw new AppException("userinterface", "isStatus");
		}

		ct.changeStatus(getString("status"), getString("memo"));
		try {
			getAppBean().SAVE();
			this.reloadPage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}
}
