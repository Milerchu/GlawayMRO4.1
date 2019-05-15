package com.glaway.sddq.overhaul.jobbook.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 检测项目DataBean
 * 
 * @author  chenbin
 * @version  [版本号, 2018-9-26]
 * @since  [产品/模块版本]
 */
public class JobTestRecordIngDataBean extends DataBean {

	public int uploadattachments5() throws MroException, IOException{
		String status = getParent().getParent().getString("STATUS");
		if (status.equals("已发布")) {
			 throw new AppException("jobbook", "notimport");
		}
		loadDialog("uploadattachments5");
		return GWConstant.NOACCESS_SAMEMETHOD;
	}
}
