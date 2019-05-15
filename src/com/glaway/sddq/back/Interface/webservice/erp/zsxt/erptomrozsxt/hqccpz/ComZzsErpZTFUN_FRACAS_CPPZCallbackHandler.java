/**
 * ComZzsErpZTFUN_FRACAS_CPPZCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.8  Built on : May 19, 2018 (07:06:11 BST)
 */
package com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz;
/**
 * ERP获取出厂配置接口
 */

/**
 *  ComZzsErpZTFUN_FRACAS_CPPZCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class ComZzsErpZTFUN_FRACAS_CPPZCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public ComZzsErpZTFUN_FRACAS_CPPZCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public ComZzsErpZTFUN_FRACAS_CPPZCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for ztfunFracasCppz method
     * override this method for handling normal response from ztfunFracasCppz operation
     */
    public void receiveResultztfunFracasCppz(
        com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub.ZtfunFracasCppzResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from ztfunFracasCppz operation
     */
    public void receiveErrorztfunFracasCppz(java.lang.Exception e) {
    }
}
