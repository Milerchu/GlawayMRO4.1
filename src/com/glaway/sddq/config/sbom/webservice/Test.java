package com.glaway.sddq.config.sbom.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import com.glaway.sddq.config.sbom.webservice.client.PlmToMroServiceImpService;
import com.glaway.sddq.config.sbom.webservice.client.PlmToMroService;
import com.ibm.json.java.JSONObject;

public class Test
{
    public static void main(String[] args)
    {
        test2();
    }
    
    public static void test1()
    {
        PlmToMroService plmToMroservice = new PlmToMroServiceImpService().getPlmToMroServiceImpPort();
        String msg = plmToMroservice.toMroEbomData("33331111", "22222");
        System.out.println(msg);
    }
    
    public static void test2()
    {
        Service service = new Service();
        Call call;
        try
        {
            call = (Call)service.createCall();
            
            String url = "http://192.168.112.41:8080/gwmro/mroservice/plmTomro?wsdl";
            call.setTargetEndpointAddress(url);// 远程调用路径
            call.setOperationName(new QName("http://webservice.sbom.config.sddq.glaway.com/",//命名空间targetNamespace
                "toMroEbomData")); //
            call.setTimeout(new Integer(360000));// 设置调用6分钟不返回即超时
            call.addParameter("itemnum", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("ebomXml", XMLType.XSD_STRING, ParameterMode.IN);
            call.setReturnType(XMLType.XSD_STRING);
            String dataXml = (String)call.invoke(new Object[] {"ITEMNUM001", "ebomXml001"});
            System.out.println("接口结束调用");
            System.out.println("dataXml:+'" + dataXml + "'");
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        catch (ServiceException e1)
        {
            e1.printStackTrace();
        }
    }
    
    public static void test3()
    {
        HttpURLConnection httpConnection = null;
        try
        {
            URL targetUrl = new URL("http://192.168.112.41:8080/gwmro/mroservice/plmTomro");
            httpConnection = (HttpURLConnection)targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            JSONObject parameter = new JSONObject();
            parameter.put("ebomXml", "11222");
            System.out.println("开始传递参数：111");
            OutputStream outputStream = httpConnection.getOutputStream();
            String msg = "2222222";
            outputStream.write(msg.getBytes());
            outputStream.flush();
            
            if (httpConnection.getResponseCode() != 200)
            {
                throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
            }
            
            BufferedReader responseBuffer =
                new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
            
            String output = null;
            System.out.println("Output from Server:\n");
            while ((output = responseBuffer.readLine()) != null)
            {
                System.out.println(output);
            }
            httpConnection.disconnect();
        }
        catch (MalformedURLException e1)
        {
            e1.printStackTrace();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        finally
        {
            if (httpConnection != null)
            {
                httpConnection.disconnect();
            }
        }
    }
}
