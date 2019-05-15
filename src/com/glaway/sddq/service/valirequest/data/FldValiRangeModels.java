package com.glaway.sddq.service.valirequest.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 验证申请单-验证产品范围-涉及车型modesl字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月23日]
 * @since [产品/模块版本]
 */
public class FldValiRangeModels extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 3119098779565333699L;

	@Override
	public void action() throws MroException {
		// 输入值
		String input = getInputMroType().asString();
		if (StringUtil.isStrNotEmpty(input) && getJpo().getParent() != null) {
			IJpo valiRequest = getJpo().getParent();
			// 车型字段第一位
			String jdcnum = String.valueOf(input.trim().charAt(0));
			String jdc = valiRequest.getString("JDC");
			// 根据车型区分车型大类（机动城）
			if (StringUtil.isStrEmpty(jdc)) {
				if ("J".equalsIgnoreCase(jdcnum)) {
					jdc = "机车";
				} else if ("D".equalsIgnoreCase(jdcnum)) {
					jdc = "动车";
				} else {
					jdc = "城轨";
				}
				valiRequest.setValue("JDC", jdc);
			}

		}
		super.action();
	}

}
