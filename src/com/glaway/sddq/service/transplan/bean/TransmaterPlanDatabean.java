package com.glaway.sddq.service.transplan.bean;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;

/**
 * 
 * 改造计划-物料计划 databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年7月26日]
 * @since [产品/模块版本]
 */
public class TransmaterPlanDatabean extends DataBean {
	@Override
	public synchronized void delete() throws MroException {
		// 标记删除分发子表数据
		IJpoSet jpoSet = getJpo().getJpoSet("TRANSPLANLOC");
		if (!jpoSet.isEmpty()) {
			jpoSet.deleteAll();
		}
		super.delete();
	}

	@Override
	public synchronized void undelete() throws MroException {
		// 取消标记删除分发子表数据
		IJpoSet jpoSet = getJpo().getJpoSet("TRANSPLANLOC");
		if (!jpoSet.isEmpty()) {
			jpoSet.undeleteAll();
		}
		super.undelete();
	}
}
