package com.glaway.sddq.back.Interface.webservice.srmtomro;

import java.net.UnknownHostException;
import java.rmi.RemoteException;

import net.sf.json.JSONObject;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient3.HttpTransportPropertiesImpl.Authenticator;
import org.codehaus.jettison.json.JSONException;

import com.glaway.sddq.back.Interface.webservice.srmtomro.clientsx.SrmToMroSxdServiceImplServiceStub;
import com.glaway.sddq.back.Interface.webservice.srmtomro.clientsx.SrmToMroSxdServiceImplServiceStub.ToSrmToMroSxdData;

public class DemoSx {

	/**
	 * SRM回传接收信息给MRO <功能描述>
	 * 
	 * @param args
	 *            [参数说明]
	 * 
	 */
	public static void main(String[] args) throws RemoteException,
			JSONException, UnknownHostException {
		SrmToMroSxdServiceImplServiceStub service = new SrmToMroSxdServiceImplServiceStub();
		Authenticator auth = new Authenticator();
		auth.setUsername("glawaymro");
		auth.setPassword("glawaymro");
		service._getServiceClient().getOptions()
				.setProperty(HTTPConstants.AUTHENTICATE, auth);
		ToSrmToMroSxdData srmdata = new ToSrmToMroSxdData();

		JSONObject item = new JSONObject();
		item.put("REPAIR_ORDER_CODE", "SX-GZ-2018-5704");

		item.put("REPAIR_ORDER_LINE_NUM", "10");
		item.put("VENDOR_RECEIVE_COUNT", "3");
		item.put("VENDOR_RECEIVE_DATE", "2018/12/31");
		System.out.println(item.toString());
		srmdata.setSxddata(item.toString());
		service.toSrmToMroSxdData(srmdata);
	}
}
