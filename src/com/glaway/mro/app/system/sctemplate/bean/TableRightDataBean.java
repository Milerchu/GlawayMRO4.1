package com.glaway.mro.app.system.sctemplate.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.page.control.MroPortlet;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 首页模板中的右侧列绑定的DataBean
 * 
 * @author  public2175
 * @version  [版本号, 2019-1-10]
 * @since  [产品/模块版本]
 */
public class TableRightDataBean extends DataBean {
	
	@Override
	public void toggleEditRow() throws MroException, IOException {
		String prtid = this.getJpo().getString("PORTLETID");
		if(!MroPortlet.INBOX_REPORT_CONFIG.equals(prtid)){
			this.getJpo().getField("DISPLAYAPP").setFlag(GWConstant.S_READONLY, true);
		}else{
			this.getJpo().getField("DISPLAYAPP").setFlag(GWConstant.S_READONLY, false);
		}
		super.toggleEditRow();
	}
	
}
