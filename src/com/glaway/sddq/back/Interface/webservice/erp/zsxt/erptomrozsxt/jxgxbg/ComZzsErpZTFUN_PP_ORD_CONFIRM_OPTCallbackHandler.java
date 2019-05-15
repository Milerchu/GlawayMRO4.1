/**
 * ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.8  Built on : May 19, 2018 (07:06:11 BST)
 */
package com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg;
/**
 * 获取ERP检修工序报工接口
 */

/**
 *  ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for ztfunPpOrdConfirmOpt method
     * override this method for handling normal response from ztfunPpOrdConfirmOpt operation
     */
    public void receiveResultztfunPpOrdConfirmOpt(
        com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.ZtfunPpOrdConfirmOptResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from ztfunPpOrdConfirmOpt operation
     */
    public void receiveErrorztfunPpOrdConfirmOpt(java.lang.Exception e) {
    }
}
