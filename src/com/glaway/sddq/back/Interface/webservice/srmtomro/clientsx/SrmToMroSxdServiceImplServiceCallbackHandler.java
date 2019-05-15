/**
 * SrmToMroSxdServiceImplServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.8  Built on : May 19, 2018 (07:06:11 BST)
 */
package com.glaway.sddq.back.Interface.webservice.srmtomro.clientsx;


/**
 *  SrmToMroSxdServiceImplServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class SrmToMroSxdServiceImplServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public SrmToMroSxdServiceImplServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public SrmToMroSxdServiceImplServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for toSrmToMroSxdData method
     * override this method for handling normal response from toSrmToMroSxdData operation
     */
    public void receiveResulttoSrmToMroSxdData(
			com.glaway.sddq.back.Interface.webservice.srmtomro.clientsx.SrmToMroSxdServiceImplServiceStub.ToSrmToMroSxdDataResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from toSrmToMroSxdData operation
     */
    public void receiveErrortoSrmToMroSxdData(java.lang.Exception e) {
    }
}
