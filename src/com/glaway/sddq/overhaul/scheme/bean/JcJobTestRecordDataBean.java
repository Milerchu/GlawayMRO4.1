package com.glaway.sddq.overhaul.scheme.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 交车检查项DataBean
 * 
 * @author  chenbin
 * @version  [版本号, 2018-9-26]
 * @since  [产品/模块版本]
 */
public class JcJobTestRecordDataBean extends DataBean {

	public int uploadattachments() throws MroException, IOException{
		String status = getParent().getString("STATUS");
		if (status.equals("已发布")) {
			 throw new AppException("repairscheme", "notimport");
		}
		loadDialog("uploadattachments");
		return GWConstant.NOACCESS_SAMEMETHOD;
	}
}
