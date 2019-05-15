package com.glaway.sddq.service.valiorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 验证工单-验证产品信息-验证前软件版本字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月14日]
 * @since [产品/模块版本]
 */
public class FldSoftVersion extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {

		super.init();
	}

	@Override
	public IJpoSet getList() throws MroException {

		// 验证软件版本虚拟表
		setLookupMap(new String[] { "SOFTVERSION" },
				new String[] { "SOFTVERSION" });

		IJpoSet softversionSet = MroServer.getMroServer().getSysJpoSet(
				"VALISOFTVERSION");
		if (getJpo().getParent() != null
				&& "验证".equals(getJpo().getParent().getString("type"))) {
			// 从验证范围中获取验证前后版本
			IJpo valiRange = getJpo().getParent().getParent();
			softversionSet.addJpo().setValue("SOFTVERSION",
					valiRange.getString("BVERSION"));
			softversionSet.addJpo().setValue("SOFTVERSION",
					valiRange.getString("AVERSION"));

		}

		return softversionSet;
	}

	@Override
	public void action() throws MroException {
		super.action();

		if (getJpo().getParent() != null
				&& "验证".equals(getJpo().getParent().getString("type"))) {
			String inputVal = getInputMroType().asString();// 当前选择值
			IJpoSet jposet = this.getList();
			String newv = "";
			if (jposet != null && jposet.count() > 0) {
				if (inputVal.equalsIgnoreCase(jposet.getJpo(0).getString(
						"SOFTVERSION"))) {
					newv = jposet.getJpo(1).getString("SOFTVERSION");
				} else {
					newv = jposet.getJpo(0).getString("SOFTVERSION");
				}
			}
			// 设置验证后软件版本
			getJpo().setValue("NEWSOFTVERSION", newv,
					GWConstant.P_NOVALIDATION_AND_NOACTION);
			jposet.getJpo(0).getString("SOFTVERSION");

		}
	}

	@Override
	protected void validateList() throws MroException {
		// 去除校验
		// super.validateList();
	}
}
