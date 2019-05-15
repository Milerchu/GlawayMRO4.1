package com.glaway.sddq.back.Interface.webservice.soo;

import java.rmi.RemoteException;

import javax.xml.datatype.DatatypeConfigurationException;

import com.glaway.sddq.back.Interface.webservice.soo.PortalSSOStub.CheckSSO;
import com.glaway.sddq.back.Interface.webservice.soo.PortalSSOStub.CheckSSOResponse;

public class Demo {
	public static void main(String[] args)
			throws DatatypeConfigurationException, RemoteException {
		PortalSSOStub service = new PortalSSOStub();
		CheckSSO csoo = new CheckSSO();
		csoo.setSSOValue("DF734E21-BB86-4A85-9D87-62A1C10A36C2");
		CheckSSOResponse ckso = service.checkSSO(csoo);
		System.out.print("result: " + ckso.getCheckSSOResult());
	}
}
