package com.glaway.sddq.service.valiorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 验证后软件版本字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月15日]
 * @since [产品/模块版本]
 */
public class FldNewSoftVersion extends JpoField {

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
		setLookupMap(new String[] { "NEWSOFTVERSION" },
				new String[] { "SOFTVERSION" });

		if (StringUtil.isStrEmpty(getJpo().getString("SOFTVERSION"))) {
			throw new MroException("请先输入验证前软件版本！");
		}

		IJpoSet softversionSet = MroServer.getMroServer().getSysJpoSet(
				"VALISOFTVERSION", "1=2");

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
	protected void validateList() throws MroException {
		// 去除校验
		// super.validateList();
	}

	@Override
	public void action() throws MroException {

		super.action();

	}
}
