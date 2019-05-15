package com.glaway.sddq.back.Interface.webservice.srmtomro;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * 
 * SRM系统调用MRO系统的接口
 * 
 * @author zzx
 * @version [版本号, 2019-1-21]
 * @since [产品/模块版本]
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface SrmToMroService {
	/**
	 * SRM系统传递缴库单数据到MRO系统
	 */
	@WebMethod
	@WebResult(name = "strReturnMsg")
	public String toSrmToMroData(

	@WebParam(name = "srmdata") String srmdata);
}
