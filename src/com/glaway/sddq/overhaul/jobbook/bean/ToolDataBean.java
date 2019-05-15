package com.glaway.sddq.overhaul.jobbook.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 工具需求DataBean
 * 
 * @author  chenbin
 * @version  [版本号, 2018-9-26]
 * @since  [产品/模块版本]
 */

public class ToolDataBean extends DataBean {

	public int uploadattachments1() throws MroException, IOException{
		String status = getParent().getString("STATUS");
		if (status.equals("已发布")) {
			 throw new AppException("jobbook", "notimport");
		}
		loadDialog("uploadattachments1");
		return GWConstant.NOACCESS_SAMEMETHOD;
	}
}
