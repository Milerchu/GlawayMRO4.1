package com.glaway.sddq.back.Interface.webservice.srmtomro;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * 
 * SRM回传接收信息给MRO(2)
 * 
 * 
 * @author zzx
 * @version [版本号, 2019-1-21]
 * @since [产品/模块版本]
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface SrmToMroSxdService {
	/**
	 * SRM系统传递送修单单数据到MRO系统
	 */
	@WebMethod
	@WebResult(name = "strReturnMsg")
	public String toSrmToMroSxdData(

	@WebParam(name = "sxddata") String sxddata);


}
