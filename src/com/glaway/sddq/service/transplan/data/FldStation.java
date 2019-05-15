package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 改造计划-改造车辆分布-机务段字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月7日]
 * @since [产品/模块版本]
 */
public class FldStation extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();
	}

	@Override
	public void action() throws MroException {

		super.action();
		// 设置办事处及办事处负责人的值
		String parent = getJpo().getString("STATION.PARENT");
		// 青岛检修分公司特殊处理
		parent = "01062403".equals(parent) ? "01062400" : parent;
		String resp = getJpo().getString("STATION.ROOT.OWNER");
		getJpo().setValue("WHICHOFFICE", parent);
		getJpo().setValue("RESPONSIBLE", resp);

	}

	@Override
	public IJpoSet getList() throws MroException {
		return super.getList();
	}

}
