package com.glaway.sddq.back.Interface.webservice.srmtomro;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import net.sf.json.JSONObject;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.IFUtil;

@WebService(endpointInterface = "com.glaway.sddq.back.Interface.webservice.srmtomro.SrmToMroSxdService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
/**
 * 
 * SRM回传接收信息给MRO(2)
 * 
 * 
 * @author zzx
 * @version [版本号, 2019-1-21]
 * @since [产品/模块版本]
 */
public class SrmToMroSxdServiceImpl implements SrmToMroSxdService {

	@Override
	public String toSrmToMroSxdData(String sxddata) {
		// TODO Auto-generated method stub
		String num = "";
		String msg = "success";
		try {
			num = IFUtil.addIfHistory("SRM_MROXSD_SOFT", sxddata,
					IFUtil.TYPE_INPUT);
			JSONObject data = JSONObject.fromObject(sxddata);
			String repairordercode = data.get("REPAIR_ORDER_CODE") != null ? data
					.get("REPAIR_ORDER_CODE").toString() : "";// 送修单号

			String repairorderline_num = data.get("REPAIR_ORDER_LINE_NUM") != null ? data
					.get("REPAIR_ORDER_LINE_NUM").toString() : "";// 行号

			String vendorreceivecount = data.get("VENDOR_RECEIVE_COUNT") != null ? data
					.get("VENDOR_RECEIVE_COUNT").toString() : "";// 接收数量

			String vendorreceivedate = data.get("VENDOR_RECEIVE_DATE") != null ? data
					.get("VENDOR_RECEIVE_DATE").toString() : "";// 接收日期

			// 取送修单行的Jposet
			IJpoSet transferlineset = MroServer.getMroServer().getSysJpoSet(
					"TRANSFERLINE");
			transferlineset.setUserWhere(" TRANSFERNUM='" + repairordercode
					+ "'and TRANSFERLINENUM='" + repairorderline_num + "'");
			transferlineset.reset();
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultOrg("CRRC");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultSite("ELEC");
			if (transferlineset != null && transferlineset.count() > 0) {
				IJpo transferlineJpo = transferlineset.getJpo();
				transferlineJpo.setValue("YJSQTY", vendorreceivecount,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

			}

			transferlineset.save();
			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"");
			return msg;
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

		return null;
	}

}
