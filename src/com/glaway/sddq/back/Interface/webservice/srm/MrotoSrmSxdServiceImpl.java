package com.glaway.sddq.back.Interface.webservice.srm;

import io.netty.util.internal.StringUtil;

import java.rmi.RemoteException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.sddq.back.Interface.webservice.srm.client.CrrcMroRepairDocExecute_binding_serviceStub;
import com.glaway.sddq.back.Interface.webservice.srm.client.CrrcMroRepairDocExecute_binding_serviceStub.Requesttype;
import com.glaway.sddq.back.Interface.webservice.srm.client.CrrcMroRepairDocExecute_binding_serviceStub.SoapResponse;

/**
 * MRO送修单同步SRM <功能描述>
 * 
 * @author public2173
 * @version [版本号, 2019-1-25]
 * @since [产品/模块版本]
 */
public class MrotoSrmSxdServiceImpl {
	public static String toSrmSxd(IJpo transferJpo) throws RemoteException,
			MroException {
		
		String transfernum = transferJpo.getString("TRANSFERNUM");// 送修单号
		String sendorg = transferJpo.getString("SENDORG");// 送修单位
		String sendorgdesc = transferJpo.getString("SENDORG.DESCRIPTION");// 送修单位描述
		String senddate = transferJpo.getString("SENDDATE");// 送修日期
		String agentby = transferJpo.getString("AGENTBY.DISPLAYNAME");// 联系人取得（经办人）
		String phone = transferJpo.getString("AGENTBY.PRIMARYPHONE");// 联系人电话取得（经办人）
		String mrotosrmsxdmark = transferJpo.getString("MROTOSRMSXDMARK");// 取消标识
		String status = transferJpo.getString("STARUS");// 状态
		/*
		 * if(status.equals("在途")){ transferJpo.setValue("MROTOSRMSXDMARK",
		 * "Y"); }
		 */

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

		Element repaircodeElement = rootElement.addElement("repair_code");// 送修单号
		repaircodeElement.setText(transfernum);

		Element repairunitElement = rootElement.addElement("repair_unit");// 送修单位
		repairunitElement.setText(sendorg);

		Element repairunitnameElement = rootElement
				.addElement("repair_unit_name");// 送修单位名称
		repairunitnameElement.setText(sendorgdesc);

		Element repairdateElement = rootElement.addElement("repair_date");// 送修日期
		repairdateElement.setText(senddate);

		Element vendorcodeElement = rootElement.addElement("vendor_code");// 供应商
		// vendorcodeElement.setText(senddate);

		Element vendornameElement = rootElement.addElement("vendor_name");// 供应商名称

		Element contactnameElement = rootElement.addElement("contact_name");// 联系人（经办人）
		contactnameElement.setText(agentby);

		Element contacttelElement = rootElement.addElement("contact_tel");// 联系人电话（经办人）
		contacttelElement.setText(phone);

		Element cancelflagElement = rootElement.addElement("cancel_flag");// 取消标识
		cancelflagElement.setText(mrotosrmsxdmark);

		Element linesElement = rootElement.addElement("lines");// 取消标识
		
		// 取子表的jposet
		IJpoSet transferlineset = transferJpo.getJpoSet("transferline");

		if (transferlineset != null && transferlineset.count() > 0) {

			int count =transferlineset.count();
			for (int i = 0; i < transferlineset.count(); i++) {
				IJpo transferlineJpo = transferlineset.getJpo(i);
				String transferlinenum = transferlineJpo
						.getString("TRANSFERLINENUM");// 送修单行
				String itemnum = transferlineJpo.getString("ITEMNUM");// 物料编码
				String itemnumdesc = transferlineJpo
						.getString("ITEM.DESCRIPTION");// 物料编码描述
				String sqn = transferlineJpo.getString("SQN");// 序列号
				String model = transferlineJpo.getString("MODEL");// 车型
				String orderqty = transferlineJpo.getString("ORDERQTY");// 数量（调拨数量）

				Element itemElement = linesElement.addElement("item");
				Element repairlinenumElement = itemElement
						.addElement("repair_line_num");// 送修单行号
				repairlinenumElement.setText(transferlinenum);

				Element itemcodeElement = itemElement.addElement("item_code");// 物料编码
				itemcodeElement.setText(itemnum);

				Element itemnameElement = itemElement.addElement("item_name");// 物料编码描述
				itemnameElement.setText(itemnumdesc);

				Element xlnumElement = itemElement.addElement("xlnum");// 序列号
				xlnumElement.setText(sqn);

				Element pcnumElement = itemElement.addElement("pcnum");// 批次号

				Element cartypesElement = itemElement.addElement("car_types");// 车型
				if (model != null) {
					cartypesElement.setText(model);
				}

				Element quantityElement = itemElement.addElement("quantity");// 数量
				quantityElement.setText(orderqty);

			}
			xmlString = rootElement.asXML();
			System.out.println(xmlString);

			// 调用SRM的接口方法
			CrrcMroRepairDocExecute_binding_serviceStub service = new CrrcMroRepairDocExecute_binding_serviceStub();
			Requesttype data = new Requesttype();
			data.setInputXml(xmlString);
			SoapResponse message = service.execute(data);
			String messsages = message.getMessage();
			if (!StringUtil.isNullOrEmpty(messsages)
					&& messsages.equals("写入成功")) {
				throw new MroException("提示", "该数据成功传入SRM系统");
			} else {
				throw new MroException("提示", "该数据异常未同步SRM系统");
			}
		}

		return null;

	}

}
