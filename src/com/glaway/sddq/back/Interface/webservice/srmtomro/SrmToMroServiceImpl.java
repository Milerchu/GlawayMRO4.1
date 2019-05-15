package com.glaway.sddq.back.Interface.webservice.srmtomro;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.IFUtil;

@WebService(endpointInterface = "com.glaway.sddq.back.Interface.webservice.srmtomro.SrmToMroService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class SrmToMroServiceImpl implements SrmToMroService {
	String returntf = "";
	@Override
	public String toSrmToMroData(String srmdata) {
		// TODO Auto-generated method stub
		/**
		 * 
		 * SRM系统调用MRO系统的接口（3）
		 * 
		 * @author zzx
		 * @version [版本号, 2019-1-21]
		 * @since [产品/模块版本]
		 */
		String num = "";
		String msg = "success";
		String msgnew = "此数据已传输过MRO系统";
		try {
			num = IFUtil.addIfHistory("SRM_MRO_SOFT", srmdata,
					IFUtil.TYPE_INPUT);
			JSONObject data = JSONObject.fromObject(srmdata);
			// JSONObject childrens = new JSONObject();
			String deliverordercode = data.get("DELIVER_ORDER_CODE").toString();// 送货单号（mro中缴库单号）

			String vendorcode = data.get("VENDOR_CODE") != null ? data.get(
					"VENDOR_CODE").toString() : "";// 供应商
			String outaddress = data.get("OUT_ADDRESS") != null ? data.get(
					"OUT_ADDRESS").toString() : "";// 发货地点
			String outdate = data.get("OUT_DATE") != null ? data
					.get("OUT_DATE").toString() : "";// 发货日期
			String repairunit = data.get("REPAIR_UNIT") != null ? data.get(
					"REPAIR_UNIT").toString() : "";// 接收单位
			String waybillnumber = data.get("WAYBILL_NUMBER") != null ? data
					.get("REPAIR_UNIT").toString() : "";// 运单号
			String transporttype = data.get("TRANSPORT_TYPE") != null ? data
					.get("TRANSPORT_TYPE").toString() : "";// 运输方式
			String contacts = data.get("CONTACTS") != null ? data.get(
					"CONTACTS").toString() : "";// 联系人
			String contactsphone = data.get("CONTACTS_PHONE") != null ? data
					.get("CONTACTS_PHONE").toString() : "";// 联系电话
			String cancelflag = data.get("CANCEL_FLAG") != null ? data.get(
					"CANCEL_FLAG").toString() : "";// 取消标识
			String repairordercode = data.get("REPAIR_ORDER_CODE") != null ? data
					.get("REPAIR_ORDER_CODE").toString() : "";// 关联送修单号

			// 取缴库单的Jposet
			IJpoSet transferset = MroServer.getMroServer().getSysJpoSet(
					"TRANSFER");
			transferset.setUserWhere("TYPE='JKD' and TRANSFERNUM='"
					+ deliverordercode + "'");
			transferset.reset();
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultOrg("CRRC");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultSite("ELEC");
			// 如果srm传输有重复的缴库单号数据，就把此条的标识改成Y
			if (transferset != null && transferset.count() > 0) {
				IJpo transfersetJpo = transferset.getJpo(0);
				transfersetJpo.setValue("SRMTOMROMARK", "Y");
				return msgnew;
			} else {
				IJpo transferaddJpo = transferset.addJpo();
				transferaddJpo.setValue("TRANSFERNUM", deliverordercode,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 缴库单号赋值
				transferaddJpo.setValue("SENDDATE", outdate,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 送修日期赋值
				// transferaddJpo.setValue("REPAIRORG", repairunit);//承修单位赋值
				transferaddJpo.setValue("CONTACTBY", contacts,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 联系人赋值
				transferaddJpo.setValue("CONTACTPHONE", contactsphone,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 联系人电话赋值
				transferaddJpo.setValue("SENDNUM", repairordercode,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 送修单号赋值
				transferaddJpo.setValue("TYPE", "JKD",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 类型赋值

				// 给childrens节点赋值
				JSONArray childrenArray = data.getJSONArray("children");

				IJpoSet transferlineset = transferaddJpo.getJpoSet(
						"$TRANSFERLINES", "TRANSFERLINE", "1=2");
				for (int i = 0; i < childrenArray.size(); i++) {

					JSONObject dataCh = childrenArray.getJSONObject(i);
					String linenumber = dataCh.containsKey("LINE_NUMBER") ? dataCh
							.getString("LINE_NUMBER").toString() : "";// 行号
					String itemcode = dataCh.containsKey("ITEM_CODE") ? dataCh
							.getString("ITEM_CODE").toString() : "";// 物料编码
					String itemunitdesc = dataCh.containsKey("ITEM_UNIT_DESC") ? dataCh
							.getString("ITEM_UNIT_DESC").toString() : "";// 单位
					String itemdesc = dataCh.containsKey("ITEM_DESC") ? dataCh
							.getString("ITEM_DESC").toString() : "";// 物料描述

					String sequencenumber = dataCh
							.containsKey("SEQUENCE_NUMBER") ? dataCh.getString(
							"SEQUENCE_NUMBER").toString() : "";// 送货序列号

					String batchnumber = dataCh.containsKey("BATCH_NUMBER") ? dataCh
							.getString("BATCH_NUMBER").toString() : "";// 送货批次号

					String delivercount = dataCh.containsKey("DELIVER_COUNT") ? dataCh
							.getString("DELIVER_COUNT").toString() : "";// //
																		// 发货数量

					String repairorderlinenum = dataCh
							.containsKey("REPAIR_ORDER_LINE_NUM") ? dataCh
							.getString("REPAIR_ORDER_LINE_NUM").toString() : "";// 关联送修单行号


					IJpo transferlinesetaddJpo = transferlineset.addJpo();
					transferlinesetaddJpo.setValue("TRANSFERNUM",
							deliverordercode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 缴库单号

					transferlinesetaddJpo
							.setValue("TRANSFERLINENUM", linenumber,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 缴库单行号

					transferlinesetaddJpo.setValue("ITEMNUM", itemcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 物料编码



					transferlinesetaddJpo.setValue("SQN", sequencenumber,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 序列号

					transferlinesetaddJpo.setValue("LOTNUM", batchnumber,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 批次号

					transferlinesetaddJpo.setValue("ORDERQTY", delivercount,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 订单数量（发货数量）

				}
				transferset.save();
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "");
				return msg;
			}


		} catch (MroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 处理失败
			try {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, e.getMessage());
				String retruntmp = e.getMessage();
				msg = retruntmp;
			} catch (MroException e1) {
				e1.printStackTrace();

			}
		}

		return msg;
	}

}
