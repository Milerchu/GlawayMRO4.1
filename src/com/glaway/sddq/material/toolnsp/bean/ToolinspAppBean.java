package com.glaway.sddq.material.toolnsp.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.page.control.Button;
import com.glaway.mro.page.control.Tab;

/**
 * 
 * <工具送检AppBean>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class ToolinspAppBean extends AppBean {
	/**
	 * 切换页面方法
	 * 
	 * @param currTab
	 * @throws IOException
	 * @throws MroException
	 */
	@Override
	public void afterTabChange(Tab currTab) throws IOException, MroException {
		if (currTab == currTab.getTabGroup().getTabByNum(1)) {
			String createby = this.getString("CREATEBY");
			if (("送检中").equals(getString("STATUS"))
					&& createby.equalsIgnoreCase(this.getUserName())) {
				try {
					Button b1 = ((Button) this.getPage().getControlByXmlId(
							"1522304016848"));
					if (b1 != null) {
						this.getPage().getControlByXmlId("1522304016848")
								.hide();

					}
					//
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				if (("待处理").equals(getString("STATUS"))
						&& createby.equalsIgnoreCase(this.getUserName())) {
					try {
						Button b1 = ((Button) this.getPage().getControlByXmlId(
								"1522304016848"));
						if (b1 != null) {
							this.getPage().getControlByXmlId("1522304016848")
									.show();

						}
						//
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}