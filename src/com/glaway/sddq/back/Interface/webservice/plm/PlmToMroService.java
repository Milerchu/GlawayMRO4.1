package com.glaway.sddq.back.Interface.webservice.plm;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * PLM系统调用MRO系统的接口
 * 
 * @author hyhe
 * @version [版本号, 2018-3-15]
 * @since [产品/模块版本]
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface PlmToMroService
{
    
    /**
     * PLM系统传递Ebom结构变更数据到MRO系统(包括物料替代变更数据)
     */
    @WebMethod
    public String toMroMbomUpdateData(@WebParam(name = "jsonData")
    String jsonData);
    
    /**
     * PLM系统传递软件验证申请单数据到MRO系统
     */
    @WebMethod
    public String toMroSoftValirebill(@WebParam(name = "softXml")

 String softXml);
    
    /**
     * PLM系统传递软件配置库信息到MRO系统
     * 
     * @param itemnum
     * @param ebomXml
     * @return [参数说明]
     */
    @WebMethod
    public String toMroSoft(@WebParam(name = "itemnum")
    String itemnum, @WebParam(name = "softinfoxml")
    String softinfoxml);
    
}
