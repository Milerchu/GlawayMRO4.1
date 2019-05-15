package com.glaway.sddq.service.transnotice.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;

/**
 * 
 * 改造通知单-改造内容 Databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年10月31日]
 * @since [产品/模块版本]
 */
public class TransContentBean extends DataBean {

	@Override
	public int addrow() throws MroException, IOException {

		this.getAppBean().SAVE();// 新建子表前保存主表
		return super.addrow();
	}

}
