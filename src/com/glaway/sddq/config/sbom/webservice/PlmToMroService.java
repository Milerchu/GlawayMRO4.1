package com.glaway.sddq.config.sbom.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface PlmToMroService
{
    /**
    * PLM系统传递Ebom数据到MRO系统
    */
    @WebMethod
    public String toMroEbomData(@WebParam(name="itemnum")String itemnum,@WebParam(name="ebomXml")String ebomXml);
}
