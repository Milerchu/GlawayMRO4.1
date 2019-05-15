package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 改造计划-改造分布-改造数量字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-14]
 * @since [产品/模块版本]
 */
public class FldTranscount extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -5338116449225406444L;

	@Override
	public void action() throws MroException {
		// 新增时，设置剩余改造数量等于计划改造数量
		if ("草稿".equals(getJpo().getParent().getString("STATUS"))) {
			int input = this.getInputMroType().asInt();
			getJpo().setValue("SURPLUS", input);
		}

		// 取数量的值
		// String carnums = getInputMroType().asString();
		float transcount = this.getJpo().getFloat("TRANSCOUNT");
		String carnums = this.getJpo().getString("CARNUMS");
		if (!carnums.isEmpty()) {
			String[] carnumsList = carnums.split(",");
			int countnew = carnumsList.length;

			if (transcount >= 0) {

				if (countnew > transcount) {
					throw new MroException("TRANSDIST", "TRANSCOUNT");
				}
			}
		}

		super.action();
	}

}
