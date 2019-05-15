package com.glaway.sddq.config.zcconfig.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 通过产品序列号去获取Erp中该产品的BBOM结构信息
 * 
 * @author  public2175
 * @version  [版本号, 2018-6-11]
 * @since  [产品/模块版本]
 */
public class GetErpBbomBySqnDataBean extends DataBean {
	
	 @Override
     public int execute()
        throws MroException, IOException
    {
		 //调用ERP系统接口,获取XML,并保留到中间表中
		 
		 
		 return GWConstant.NOACCESS_SAMEMETHOD;
    }
	
}
