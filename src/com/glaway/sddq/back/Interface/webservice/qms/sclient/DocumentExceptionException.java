/**
 * DocumentExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.8  Built on : May 19, 2018 (07:06:11 BST)
 */
package com.glaway.sddq.back.Interface.webservice.qms.sclient;

public class DocumentExceptionException extends java.lang.Exception {
    private static final long serialVersionUID = 1540451080632L;
    private com.glaway.sddq.back.Interface.webservice.qms.sclient.ComZzsQmsDqXcgzMappingServiceStub.DocumentExceptionE faultMessage;

    public DocumentExceptionException() {
        super("DocumentExceptionException");
    }

    public DocumentExceptionException(java.lang.String s) {
        super(s);
    }

    public DocumentExceptionException(java.lang.String s, java.lang.Throwable ex) {
        super(s, ex);
    }

    public DocumentExceptionException(java.lang.Throwable cause) {
        super(cause);
    }

    public void setFaultMessage(
        com.glaway.sddq.back.Interface.webservice.qms.sclient.ComZzsQmsDqXcgzMappingServiceStub.DocumentExceptionE msg) {
        faultMessage = msg;
    }

    public com.glaway.sddq.back.Interface.webservice.qms.sclient.ComZzsQmsDqXcgzMappingServiceStub.DocumentExceptionE getFaultMessage() {
        return faultMessage;
    }
}
