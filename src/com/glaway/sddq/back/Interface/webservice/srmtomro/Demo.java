package com.glaway.sddq.back.Interface.webservice.srmtomro;

import java.net.UnknownHostException;
import java.rmi.RemoteException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient3.HttpTransportPropertiesImpl.Authenticator;
import org.codehaus.jettison.json.JSONException;

import com.glaway.sddq.back.Interface.webservice.srmtomro.client.SrmToMroServiceImplServiceStub;
import com.glaway.sddq.back.Interface.webservice.srmtomro.client.SrmToMroServiceImplServiceStub.ToSrmToMroData;

public class Demo {

	/**
	 * SRM同步送货单到MRO\ <功能描述>
	 * 
	 * @param args
	 *            [参数说明]
	 * 
	 */
	public static void main(String[] args) throws RemoteException,
			JSONException, UnknownHostException {
		SrmToMroServiceImplServiceStub service = new SrmToMroServiceImplServiceStub();
		Authenticator auth = new Authenticator();
		auth.setUsername("glawaymro");
		auth.setPassword("glawaymro");
		service._getServiceClient().getOptions()
				.setProperty(HTTPConstants.AUTHENTICATE, auth);
		ToSrmToMroData srmdata = new ToSrmToMroData();

		JSONObject item = new JSONObject();
		item.put("DELIVER_ORDER_CODE", "DB-JKD-2019-8892");

		item.put("REPAIR_UNIT", "XX公司");
		item.put("CONTACTS", "张某");
		item.put("CONTACTS_PHONE", "15623358665");
		JSONArray childrens = new JSONArray();
		for (int index = 0; index < 2; index++) {
			JSONObject s = new JSONObject();
			s.put("LINE_NUMBER", "1");
			s.put("ITEM_CODE", "TEM001");
			s.put("DELIVER_COUNT", "12");
			s.put("CANCEL_FLAG", "N");
			childrens.add(s);
		}
		item.put("children", childrens);
		srmdata.setSrmdata(item.toString());
		service.toSrmToMroData(srmdata);
	}

}
