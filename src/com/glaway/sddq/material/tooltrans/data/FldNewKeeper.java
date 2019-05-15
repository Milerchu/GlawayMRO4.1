package com.glaway.sddq.material.tooltrans.data;

import java.io.IOException;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <工具交接表交接人字段类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldNewKeeper extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 7366618044871731505L;

	/**
	 * 赋值交接人
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		if (this.isValueChanged()) {
			String newkeeper = getInputMroType().asString();
			IJpoSet devicSet = this.getJpo().getJpoSet("DEVICETRANS");
			for (int j = 0; j < devicSet.count(); j++) {
				IJpo devic = devicSet.getJpo(j);
				devic.setValue("NEWKEEPER", newkeeper);
			}
		}
		super.action();
		try {
			this.getJpo().getJpoSet().getUserServer().getMroSession()
					.getCurrentPage().getAppBean().reloadPage();// getDataBean("1525341314605").resetAndReload();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
