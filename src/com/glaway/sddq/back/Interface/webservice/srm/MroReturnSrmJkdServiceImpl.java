package com.glaway.sddq.back.Interface.webservice.srm;

import io.netty.util.internal.StringUtil;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.glaway.mro.exception.MroException;
import com.glaway.sddq.back.Interface.webservice.srm.clientjk.CrrcMroInvAsnBackExecute_binding_serviceStub;
import com.glaway.sddq.back.Interface.webservice.srm.clientjk.CrrcMroInvAsnBackExecute_binding_serviceStub.Requesttype;
import com.glaway.sddq.back.Interface.webservice.srm.clientjk.CrrcMroInvAsnBackExecute_binding_serviceStub.SoapResponse;


/**
 * MRO回传接收信息给SRM <功能描述>
 * 
 * @author public2173
 * @version [版本号, 2019-1-24]
 * @since [产品/模块版本]
 */

public class MroReturnSrmJkdServiceImpl {

	public static String toReturnSrmJkd(String transfernum,
			String transferlinenum, Double JSQTY, String rctime)
			throws RemoteException, MroException {
		String xmlString = "";
		String num = "";
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("soapRequest");// 根节点
		Element keyElement = rootElement.addElement("key");// 用于srm接口验证
		keyElement.setText("MRO");

		Element valueElement = rootElement.addElement("value");// 用于srm接口验证
		valueElement.setText("C4E222C00ADFREDE");

		Element businessgroupElement = rootElement.addElement("business_group");// 用于srm接口验证
		businessgroupElement.setText("BG00000441");

		Element deliverordercodeElement = rootElement
				.addElement("deliver_order_code");// 缴库单号
		deliverordercodeElement.setText(transfernum);
		// deliverordercodeElement.setText("0000441");

		Element linenumberElement = rootElement.addElement("line_number");// 行号
		linenumberElement.setText(transferlinenum);
		// linenumberElement.setText("111");
		
		Element demanderreceivecountElement = rootElement
				.addElement("demander_receive_count");// 接收数量
		// demanderreceivecountElement.setText("2");
		String str = String.valueOf(JSQTY);
		if (!str.isEmpty()) {
			demanderreceivecountElement.setText(str);
		}

		Element demanderreceivedateElement = rootElement
				.addElement("demander_receive_date");// 接收日期

		demanderreceivedateElement.setText(rctime);
		// demanderreceivedateElement.setText("2018/12/24 14:53:45");
		xmlString = rootElement.asXML();
		System.out.println(xmlString);
		// 调用SRM的接口方法
		try {
			CrrcMroInvAsnBackExecute_binding_serviceStub service = new CrrcMroInvAsnBackExecute_binding_serviceStub();
			Requesttype data = new Requesttype();
			data.setInputXml(xmlString);
			// service.execute(data);
			SoapResponse message = service.execute(data);
			String messsages = message.getMessage();
			if (!StringUtil.isNullOrEmpty(messsages)
					&& messsages.equals("写入成功")) {
				throw new MroException("提示", "该数据成功同步SRM系统");
			} else {
				throw new MroException("提示", "该数据异常未传入SRM系统");
			}

		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// toReturnSrmJkd(null, null, null, null);
	}
}
