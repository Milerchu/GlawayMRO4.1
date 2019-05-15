package com.glaway.sddq.back.Interface.webservice.qms;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface QmsToMroService {
	
    /**
    * QMS现场事件信息传递数据到MRO系统
    */
    @WebMethod
    public String toMroFailureLib(@WebParam(name = "qmsXml") String qmsXml);
    

    /**
    * QMS维修配置信息传递数据到MRO系统
    */
    @WebMethod
    public String toMroRepairInfo(@WebParam(name = "qmsXml") String qmsXml);
}
