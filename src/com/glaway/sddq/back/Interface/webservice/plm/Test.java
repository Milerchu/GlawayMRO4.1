package com.glaway.sddq.back.Interface.webservice.plm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.glaway.sddq.back.Interface.webservice.plm.client.PlmToMroService;
import com.glaway.sddq.back.Interface.webservice.plm.client.PlmToMroServiceImpService;
import com.glaway.sddq.tools.IFUtil;
import com.ibm.json.java.JSONObject;

public class Test {
	public static void main(String[] args) {
		test7();
	}

	public static void test5() {
		try {
			String user = IFUtil.getIfServiceInfo("plm.user");
			String pwd = IFUtil.getIfServiceInfo("plm.pwd");
			String url2 = "http://plm.teg.cn/Windchill/servlet/RPC?CLASS=com.infoengine.soap";
			String methodName = "getBomInfoByMro";
			String partNubmer = "TE6229000000";
			String factory = IFUtil.getIfServiceInfo("plm.factory");
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			org.apache.axis.client.Call call;
			call = (org.apache.axis.client.Call) service.createCall();
			call.setUsername(user);
			call.setPassword(pwd);
			call.setTargetEndpointAddress(new java.net.URL(url2));
			call.addParameter("arg1", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("arg2", XMLType.XSD_STRING, ParameterMode.IN);
			call.setOperationName(methodName);
			call.setReturnType(XMLType.XSD_STRING);
			String returnValue = (String) call.invoke(new Object[] {
					partNubmer, factory });
			System.out.println(methodName + " -> webservice 返回结果: \n "
					+ returnValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test1() {
		PlmToMroService plmToMroservice = new PlmToMroServiceImpService()
				.getPlmToMroServiceImpPort();
		String msg = plmToMroservice.toMroEbomSrUpdateData("111", "22222");
		System.out.println(msg);
		String msg1 = plmToMroservice.toMroEbomRsUpdateData("333", "44444");
		System.out.println(msg1);
	}

	public static void test2() {
		Service service = new Service();
		Call call;
		try {
			call = (Call) service.createCall();
			String url = "http://mrotst.teg.cn:58000/gwmro/mroservice/plmTomro?wsdl";
			call.setTargetEndpointAddress(url);// 远程调用路径
			call.setOperationName(new QName(
					"http://plm.webservice.Interface.back.sddq.glaway.com/",// 命名空间targetNamespace
					"toMroMbomUpdateData")); //
			call.setTimeout(new Integer(360000));// 设置调用6分钟不返回即超时
			call.addParameter("jsonData", XMLType.XSD_STRING, ParameterMode.IN);
			call.setUsername("glawaymro");
			call.setPassword("glawaymro");
			call.setReturnType(XMLType.XSD_STRING);
			JSONObject object = new JSONObject();
			object.put("subject", "物料替代关系变更");
			object.put("content", "A物料增加B替代关系");
			object.put("changeNumber", "CA0000000385");
			object.put("link",
					"http://mrotst.teg.cn:58000/gwmro/mroservice/plmTomro?wsdl");
			System.out.println(object.toString());
			String dataXml = (String) call.invoke(new Object[] { object
					.toString() });
			System.out.println("接口结束调用");
			System.out.println("dataXml:+'" + dataXml + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test3() {
		HttpURLConnection httpConnection = null;
		try {
			URL targetUrl = new URL(
					"http://192.168.112.41:8080/gwmro/mroservice/plmTomro");
			httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type",
					"text/xml;charset=UTF-8");
			JSONObject parameter = new JSONObject();
			parameter.put("ebomXml", "11222");
			System.out.println("开始传递参数：111");
			OutputStream outputStream = httpConnection.getOutputStream();
			String msg = "2222222";
			outputStream.write(msg.getBytes());
			outputStream.flush();

			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ httpConnection.getResponseCode());
			}

			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));

			String output = null;
			System.out.println("Output from Server:\n");
			while ((output = responseBuffer.readLine()) != null) {
				System.out.println(output);
			}
			httpConnection.disconnect();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
		}
	}

	public void test4() {
		try {
			String user = "";
			String pwd = "";
			String url2 = "http://127.0.0.1:58000/gwmro/mroservice/plmTomro?wsdl";
			String methodName = "getBomInfoByMro";
			String partNubmer = "TE6229000000";
			String factory = IFUtil.getIfServiceInfo("plm.factory");
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			org.apache.axis.client.Call call;
			call = (org.apache.axis.client.Call) service.createCall();
			call.setUsername(user);
			call.setPassword(pwd);
			call.setTargetEndpointAddress(new java.net.URL(url2));
			call.addParameter("arg1", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("arg2", XMLType.XSD_STRING, ParameterMode.IN);
			call.setOperationName(methodName);
			call.setReturnType(XMLType.XSD_STRING);
			String returnValue = (String) call.invoke(new Object[] {
					partNubmer, factory });
			System.out.println(methodName + " -> webservice 返回结果: \n "
					+ returnValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test6() {
		// CallWebServiceUtil call = new CallWebServiceUtil();
		// try{
		// Map<String,String> sapParam = new HashMap<String,String>();
		// sapParam.put("mdmXml", xmlData);
		// Object[] sapreqparam = call.setParameter(sapParam);
		// OMElement omElemnt =
		// call.getMroInvokeServiceMethod(ServiceConstants.MRO_WL_URL,
		// sapaction, sapmethod,
		// ServiceConstants.MRO_WL_TNS,ServiceConstants.MRO_WL_USERNAME,ServiceConstants.MRO_WL_PASSWORD,
		// sapreqparam);

		try {
			String user = "glawaymro";
			String pwd = "glawaymro";
			String url2 = "http://mrotst.teg.cn:58000/gwmro/mroservice/mdmTomro?wsdl";

			// String methodName="toMroMdmItemData";
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			org.apache.axis.client.Call call;
			call = (org.apache.axis.client.Call) service.createCall();
			call.setUsername(user);
			call.setPassword(pwd);
			call.setTargetEndpointAddress(new java.net.URL(url2));
			call.addParameter("mdmXml", XMLType.XSD_STRING, ParameterMode.IN);
			// call.setOperationName(methodName);
			call.setOperationName(new QName(
					"http://mdm.webservice.Interface.back.sddq.glaway.com/",// 命名空间targetNamespace
					"toMroMdmItemData")); //
			call.setReturnType(XMLType.XSD_STRING);
			String returnValue = (String) call.invoke(new Object[] { "12345" });
			System.out.println(" -> webservice 返回结果: \n " + returnValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// String wsurl
		// ="http://mrotst.teg.cn:58000/gwmro/mroservice/mdmTomro?wsdl";
		//
		// String sapaction =
		// "_http://mdm.webservice.Interface.back.sddq.glaway.com/toMroMdmItemData";
		// String sapmethod = "toMroMdmItemData";
		// Options options = new Options();
		// // 指定调用WebService的URL
		// EndpointReference targetEPR = new EndpointReference(wsurl);
		// options.setTo(targetEPR);
		// options.setAction(sapaction);
		//
		// ServiceClient sender;
		// try {
		// sender = new ServiceClient();
		// sender.setOptions(options);
		//
		// //用户名密码认证
		// // HttpTransportProperties.Authenticator auth = new
		// HttpTransportProperties.Authenticator();
		// // auth.setUsername(username);
		// // auth.setPassword(password);
		// // options.setProperty(HTTPConstants.AUTHENTICATE,auth);
		// options.setTimeOutInMilliSeconds(180000);
		// OMFactory fac = OMAbstractFactory.getOMFactory();
		// String wstns =
		// "http://mdm.webservice.Interface.back.sddq.glaway.com/";
		// OMNamespace omNs = fac.createOMNamespace(wstns, sapmethod);
		// OMElement wsmethod = fac.createOMElement(sapmethod, omNs);
		// OMElement symbol = fac.createOMElement("mdmXml", omNs);
		// wsmethod.addChild(symbol);
		// wsmethod.build();
		// // LogUtil.logInfo("调用MRO接口请求地址{0}, 发送的WebService请求消息体： {1}", null,
		// wsurl, wsmethod.toString());
		// try {
		// OMElement result = sender.sendReceive(wsmethod);
		// } catch (AxisFault e) {
		// e.printStackTrace();
		// }
		// } catch (AxisFault e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//

	}

	public static void test7() {

		try {
			String user = "glawaymro";
			String pwd = "glawaymro";
			String url2 = "http://127.0.0.1:58000/gwmro/mroservice/plmTomro?wsdl";
			// String url2 =
			// "http://mrotst.teg.cn:58000/gwmro/mroservice/plmTomro?wsdl";

			String methodName = "toMroSoftValirebill";
			// String partNubmer = "TE6229000000";
			// String factory = IFUtil.getIfServiceInfo("plm.factory");
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			org.apache.axis.client.Call call;
			call = (org.apache.axis.client.Call) service.createCall();
			call.setUsername(user);
			call.setPassword(pwd);
			call.setTargetEndpointAddress(new java.net.URL(url2));
			call.addParameter("softXml", XMLType.XSD_STRING, ParameterMode.IN);
			// call.setOperationName(methodName);
			call.setOperationName(new QName(
					"http://plm.webservice.Interface.back.sddq.glaway.com/",// 命名空间targetNamespace
					"toMroSoftValirebill")); //
			call.setReturnType(XMLType.XSD_STRING);
			SAXReader saxReader = new SAXReader();

			Document doc = saxReader
					.read(new File(
							"src/com/glaway/sddq/back/Interface/webservice/plm/softxml.xml"));
			// 转换成字符串
			String softdoc = doc.asXML();
			// 代入解析
			String returnValue = (String) call.invoke(new Object[] { softdoc });
			System.out.println(returnValue);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
