/**
 * ClassNotFoundExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.8  Built on : May 19, 2018 (07:06:11 BST)
 */
package com.glaway.sddq.back.Interface.webservice.qms.sclient;

public class ClassNotFoundExceptionException extends java.lang.Exception {
    private static final long serialVersionUID = 1540451080621L;
    private com.glaway.sddq.back.Interface.webservice.qms.sclient.ComZzsQmsDqXcgzMappingServiceStub.ClassNotFoundExceptionE faultMessage;

    public ClassNotFoundExceptionException() {
        super("ClassNotFoundExceptionException");
    }

    public ClassNotFoundExceptionException(java.lang.String s) {
        super(s);
    }

    public ClassNotFoundExceptionException(java.lang.String s,
        java.lang.Throwable ex) {
        super(s, ex);
    }

    public ClassNotFoundExceptionException(java.lang.Throwable cause) {
        super(cause);
    }

    public void setFaultMessage(
        com.glaway.sddq.back.Interface.webservice.qms.sclient.ComZzsQmsDqXcgzMappingServiceStub.ClassNotFoundExceptionE msg) {
        faultMessage = msg;
    }

    public com.glaway.sddq.back.Interface.webservice.qms.sclient.ComZzsQmsDqXcgzMappingServiceStub.ClassNotFoundExceptionE getFaultMessage() {
        return faultMessage;
    }
}
