package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 工单-assetnum字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月5日]
 * @since [产品/模块版本]
 */
public class FldAssetnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {

		super.action();

		String assetnum = getInputMroType().asString();
		if (getJpo().isNew() || "草稿".equals(getJpo().getString("STATUS"))) {
			if (isValueChanged()) {

				getJpo().setValueNull("projectnum");// 清空项目编号

			}
			IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet("ASSET",
					"assetnum='" + assetnum + "'");
			if (assetSet != null && assetSet.count() > 0) {
				// 根据现场配置中的项目编号给工单项目编号设值
				getJpo().setValue("projectnum",
						assetSet.getJpo().getString("projectnum"));

			}
		}
	}

}
