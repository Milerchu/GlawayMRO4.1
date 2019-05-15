/**
 * PortalSSOCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.8  Built on : May 19, 2018 (07:06:11 BST)
 */
package com.glaway.sddq.back.Interface.webservice.soo;


/**
 *  PortalSSOCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class PortalSSOCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public PortalSSOCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public PortalSSOCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for checkSSO method
     * override this method for handling normal response from checkSSO operation
     */
    public void receiveResultcheckSSO(
			com.glaway.sddq.back.Interface.webservice.soo.PortalSSOStub.CheckSSOResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from checkSSO operation
     */
    public void receiveErrorcheckSSO(java.lang.Exception e) {
    }
}
