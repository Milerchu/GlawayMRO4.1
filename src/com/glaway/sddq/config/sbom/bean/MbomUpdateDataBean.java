package com.glaway.sddq.config.sbom.bean;

import com.glaway.mro.controller.ResultsBean;

/**
 * 
 * MBOM变更记录ResultsBean
 * 
 * @author  public2175
 * @version  [版本号, 2018-7-4]
 * @since  [产品/模块版本]
 */
public class MbomUpdateDataBean extends ResultsBean {
	/**
	 * 
	 * 打开链接地址
	 * @throws Exception [参数说明]
	 *
	 */
	public void openPlmlink() throws Exception{
		String link = this.getJpo().getString("link");
		if(link != null){
	        mroSession.returnReponse("", "openUrl", "<url>" + link + "</url>");
		}
	}
	
}
