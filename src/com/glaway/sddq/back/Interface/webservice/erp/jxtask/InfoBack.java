package com.glaway.sddq.back.Interface.webservice.erp.jxtask;

import java.io.Serializable;


/**
 * 
 * erp返回信息类
 * 
 * @author  chenbin
 * @version  [版本号, 2018-7-24]
 * @since  [产品/模块版本]
 */
public class InfoBack implements Serializable{
	
	/**
     * @Field @serialVersionUID : 默认序列化ID
     */
    private static final long serialVersionUID = 1L;

    private String flag = "S";
	
	private String msg;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
	    this.flag = "E";
		this.msg = msg;
	}
	
}
