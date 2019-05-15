package com.glaway.sddq.back.Interface.webservice.mdm;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * 
 * MDM系统调用MRO系统的接口
 * 
 * @author  zzx
 * @version  [版本号, 2018-6-13]
 * @since  [产品/模块版本]
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface MdmToMroService {
    /**
    * MDM系统传递人员数据到MRO系统
    */
    @WebMethod
    @WebResult(name = "strReturnMsg")
    public String toMroMdmPersonData(
    
    @WebParam(name = "mdmXml")  String mdmXml);
   
    /**
     * MDM系统传递组织架构数据到MRO系统
     */
     @WebMethod
     @WebResult(name = "strReturnMsg")
     public String toMroMdmDeptData(
    		    
    @WebParam(name = "mdmXml")  String mdmXml);
     
     /**
      * MDM系统传递岗位数据到MRO系统
      */
      @WebMethod
      @WebResult(name = "strReturnMsg")
     public String toMroMdmPostData(
     		    
     @WebParam(name = "mdmXml")  String mdmXml);
      
      /**
       * MDM系统传递客户数据到MRO系统
       */
       @WebMethod
       @WebResult(name = "strReturnMsg")
      public String toMroMdmCustinfoData(
      		    
      @WebParam(name = "mdmXml")  String mdmXml);
       
       /**
        * MDM系统传递供应商数据到MRO系统
        */
        @WebMethod
        @WebResult(name = "strReturnMsg")
       public String toMroMdmCompanyData(
       		    
       @WebParam(name = "mdmXml")  String mdmXml);
        
        /**
         * MDM系统传递物料数据到MRO系统
         */
         @WebMethod
         @WebResult(name = "strReturnMsg")
        public String toMroMdmItemData(
        		    
        @WebParam(name = "mdmXml")  String mdmXml);
      
        
        
      
}
