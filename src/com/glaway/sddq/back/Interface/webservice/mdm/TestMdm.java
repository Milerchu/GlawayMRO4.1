package com.glaway.sddq.back.Interface.webservice.mdm;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.glaway.sddq.back.Interface.webservice.mdm.client.MdmToMroService;
import com.glaway.sddq.back.Interface.webservice.mdm.client.MdmToMroServiceImpService;





public class TestMdm {
	
    public static void main(String[] args) 
    {
    	MdmToMroService mdmToMroService = new MdmToMroServiceImpService().getMdmToMroServiceImpPort();
  

    	SAXReader saxReader = new SAXReader();
    	try {
			// 读取文件
			//Document doc = saxReader.read(XmlUtil.class.getClassLoader().getResourceAsStream("src/com/glaway/sddq/back/Interface/webservice/mdm/mdmXml.xml"));
    		Document doc = saxReader.read(new File("src/com/glaway/sddq/back/Interface/webservice/mdm/mdmXml.xml"));
			// 转换成字符串
			String mdmdoc = doc.asXML();
			// 代入解析
			String msg = mdmToMroService.toMroMdmPersonData(mdmdoc);

		} catch (DocumentException e) {
			
			e.printStackTrace();
		}

    	
    	//return doc.asXML();
    }
    
    /*
	 * public static void test1(){ try { Service sv = new Service(); Call call =
	 * (Call) sv.createCall(); call.setTargetEndpointAddress(
	 * "http://127.0.0.1:58000/gwmro/mroservice/mdmTomro?wsdl"); //设置要调用的接口方法
	 * call.setOperationName(new QName(
	 * "http://mdm.webservice.Interface.back.sddq.glaway.com/"
	 * ,"toMroMdmCompanyData")); call.addParameter("mdmXml", XMLType.XSD_STRING,
	 * ParameterMode.IN); call.setReturnType(XMLType.XSD_STRING); try { String
	 * dataXml = (String) call.invoke(new Object[] {"mdmXml001"});
	 * System.out.println("dataXml:+'" + dataXml + "'"); } catch
	 * (RemoteException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } catch (ServiceException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

}


