package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 工单 项目编号 字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-12]
 * @since [产品/模块版本]
 */
public class FldProjectnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 7731784591597913490L;

	@Override
	public void action() throws MroException {
//		if (isValueChanged()) {
//			String inputPrjnum = getInputMroType().getStringValue();
//			IJpoSet assetSet = getJpo().getJpoSet("ASSET");
//			if (!assetSet.isEmpty()) {
//				IJpo asset = assetSet.getJpo();
//				String projectnum = asset.getString("PROJECTNUM");
//				// 项目编号发生变更，回填asset表
//				if (!inputPrjnum.equalsIgnoreCase(projectnum)) {
//					asset.setValue("projectnum", inputPrjnum);
//				}
//			}
//		}
		super.action();
	}
}
