package com.glaway.sddq.back.Interface.webservice.erp;

/**
 * 
 * <功能描述> erp返回消息实例类
 * 
 * @author 20167088
 * @version [版本号, 2018-7-18]
 * @since [产品/模块版本]
 */
public class InterfaceReponse {
	private String RETURN;

	public String getRETURN() {
		return RETURN;
	}

	public void setRETURN(String rETURN) {
		RETURN = rETURN;
	}

	public String getMESSAGE() {
		return MESSAGE;
	}

	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}

	private String MESSAGE;
}
