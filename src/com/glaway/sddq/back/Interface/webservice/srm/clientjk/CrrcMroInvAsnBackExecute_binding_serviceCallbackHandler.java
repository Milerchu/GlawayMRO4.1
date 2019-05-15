/**
 * CrrcMroInvAsnBackExecute_binding_serviceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.8  Built on : May 19, 2018 (07:06:11 BST)
 */
package com.glaway.sddq.back.Interface.webservice.srm.clientjk;


/**
 *  CrrcMroInvAsnBackExecute_binding_serviceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class CrrcMroInvAsnBackExecute_binding_serviceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public CrrcMroInvAsnBackExecute_binding_serviceCallbackHandler(
        Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public CrrcMroInvAsnBackExecute_binding_serviceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for execute method
     * override this method for handling normal response from execute operation
     */
    public void receiveResultexecute(
			com.glaway.sddq.back.Interface.webservice.srm.clientjk.CrrcMroInvAsnBackExecute_binding_serviceStub.SoapResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from execute operation
     */
    public void receiveErrorexecute(java.lang.Exception e) {
    }
}
