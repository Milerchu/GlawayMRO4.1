/**
 * ComZzsErpZTFUN_SD_ZJHJP_WEBCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.8  Built on : May 19, 2018 (07:06:11 BST)
 */
package com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd;
/**
 * 三包交货单拣配接口
 */

/**
 *  ComZzsErpZTFUN_SD_ZJHJP_WEBCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class ComZzsErpZTFUN_SD_ZJHJP_WEBCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public ComZzsErpZTFUN_SD_ZJHJP_WEBCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public ComZzsErpZTFUN_SD_ZJHJP_WEBCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for ztfunSdZjhjp method
     * override this method for handling normal response from ztfunSdZjhjp operation
     */
    public void receiveResultztfunSdZjhjp(
        com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.sbywjpd.ComZzsErpZTFUN_SD_ZJHJP_WEBStub.ZtfunSdZjhjpResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from ztfunSdZjhjp operation
     */
    public void receiveErrorztfunSdZjhjp(java.lang.Exception e) {
    }
}
