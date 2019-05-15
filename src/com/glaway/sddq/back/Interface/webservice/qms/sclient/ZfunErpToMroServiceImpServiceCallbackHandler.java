/**
 * ZfunErpToMroServiceImpServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.8  Built on : May 19, 2018 (07:06:11 BST)
 */
package com.glaway.sddq.back.Interface.webservice.qms.sclient;


/**
 *  ZfunErpToMroServiceImpServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class ZfunErpToMroServiceImpServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public ZfunErpToMroServiceImpServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public ZfunErpToMroServiceImpServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for ztfun_ErptoMroSxs method
     * override this method for handling normal response from ztfun_ErptoMroSxs operation
     */
    public void receiveResultztfun_ErptoMroSxs(
        com.glaway.sddq.back.Interface.webservice.qms.sclient.ZfunErpToMroServiceImpServiceStub.Ztfun_ErptoMroSxsResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from ztfun_ErptoMroSxs operation
     */
    public void receiveErrorztfun_ErptoMroSxs(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for ztfun_toMroCheckRepairOrder method
     * override this method for handling normal response from ztfun_toMroCheckRepairOrder operation
     */
    public void receiveResultztfun_toMroCheckRepairOrder(
        com.glaway.sddq.back.Interface.webservice.qms.sclient.ZfunErpToMroServiceImpServiceStub.Ztfun_toMroCheckRepairOrderResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from ztfun_toMroCheckRepairOrder operation
     */
    public void receiveErrorztfun_toMroCheckRepairOrder(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for ztfun_ErptoMroJkd method
     * override this method for handling normal response from ztfun_ErptoMroJkd operation
     */
    public void receiveResultztfun_ErptoMroJkd(
        com.glaway.sddq.back.Interface.webservice.qms.sclient.ZfunErpToMroServiceImpServiceStub.Ztfun_ErptoMroJkdResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from ztfun_ErptoMroJkd operation
     */
    public void receiveErrorztfun_ErptoMroJkd(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for ztfun_toMroRepairInfoOrScarpInfo method
     * override this method for handling normal response from ztfun_toMroRepairInfoOrScarpInfo operation
     */
    public void receiveResultztfun_toMroRepairInfoOrScarpInfo(
        com.glaway.sddq.back.Interface.webservice.qms.sclient.ZfunErpToMroServiceImpServiceStub.Ztfun_toMroRepairInfoOrScarpInfoResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from ztfun_toMroRepairInfoOrScarpInfo operation
     */
    public void receiveErrorztfun_toMroRepairInfoOrScarpInfo(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for ztfun_ErptoMroSqn method
     * override this method for handling normal response from ztfun_ErptoMroSqn operation
     */
    public void receiveResultztfun_ErptoMroSqn(
        com.glaway.sddq.back.Interface.webservice.qms.sclient.ZfunErpToMroServiceImpServiceStub.Ztfun_ErptoMroSqnResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from ztfun_ErptoMroSqn operation
     */
    public void receiveErrorztfun_ErptoMroSqn(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for ztfun_toMroSendRepairInfo method
     * override this method for handling normal response from ztfun_toMroSendRepairInfo operation
     */
    public void receiveResultztfun_toMroSendRepairInfo(
        com.glaway.sddq.back.Interface.webservice.qms.sclient.ZfunErpToMroServiceImpServiceStub.Ztfun_toMroSendRepairInfoResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from ztfun_toMroSendRepairInfo operation
     */
    public void receiveErrorztfun_toMroSendRepairInfo(java.lang.Exception e) {
    }
}
