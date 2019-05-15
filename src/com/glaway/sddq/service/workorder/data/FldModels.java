package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 服务工单-车型models字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-12]
 * @since [产品/模块版本]
 */
public class FldModels extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 6451374485398511726L;

	@Override
	public void action() throws MroException {
		// if (isValueChanged()) {
		/*
		 * 根据车型车号 设置项目编号的值 String models =
		 * this.getInputMroType().getStringValue(); String carnum =
		 * getJpo().getString("carnum");
		 * 
		 * IJpoSet assetSet = MroServer.getMroServer().getJpoSet("ASSET",
		 * getJpo().getUserServer()); assetSet.setUserWhere("carno='" + carnum +
		 * "' and cmodel='" + models + "'"); assetSet.reset(); if
		 * (assetSet.count() > 0) { String projectnum =
		 * assetSet.getJpo(0).getString("PROJECTNUM");
		 * getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_READONLY, false);
		 * getJpo().setValue("PROJECTNUM", projectnum); // if
		 * ("调试交车".equals(getJpo().getString("SERVORDERTYPE")) // ||
		 * "新车整备".equals(getJpo().getString("SERVORDERTYPE"))) { // // } else {
		 * // getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_READONLY, true);
		 * // }
		 * 
		 * } // }
		 */
		super.action();
	}
}
