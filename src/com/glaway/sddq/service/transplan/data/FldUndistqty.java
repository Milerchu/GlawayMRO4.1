package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 改造计划 物料计划 未分发数量undistqty字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年7月25日]
 * @since [产品/模块版本]
 */
public class FldUndistqty extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -4251044383657866417L;

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		if (getJpo() != null) {
			float allQty = getJpo().getFloat("MATERIALCOUNT");
			float distedQty = getJpo().getFloat("DISTEDQTY");
			float undistQty = allQty - distedQty;
			setValue(undistQty + "", GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
		}

	}

}
