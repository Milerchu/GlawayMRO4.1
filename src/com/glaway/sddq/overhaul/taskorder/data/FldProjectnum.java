package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 检修工单--项目编号projectnum字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-12]
 * @since [产品/模块版本]
 */
public class FldProjectnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -7309636098748454949L;

	@Override
	public void action() throws MroException {
		if (isValueChanged()) {
			String inputPrjnum = getInputMroType().getStringValue();
			if (StringUtil.isStrNotEmpty(inputPrjnum)) {
				IJpoSet assetSet = getJpo().getJpoSet("ASSET");
				if (!assetSet.isEmpty()) {
					IJpo asset = assetSet.getJpo();
					// 获取装车配置中存储的项目编号
					String projectnum = asset.getString("PROJECTNUM");
					// 项目编号发生变更，回填asset表
					if (!inputPrjnum.equalsIgnoreCase(projectnum)) {
						asset.setValue("projectnum", inputPrjnum);
					}
				}
			}
		}
		super.action();
	}

}
