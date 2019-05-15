package com.glaway.sddq.back.Interface.webservice.bigdata;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.Result;

@Path("/bigdata")
public class BigdataToMroService {

	@GET
	@Path("/faultConfirm")
	@Produces(MediaType.APPLICATION_JSON)
	public String faultConfirm(@QueryParam("orderID") String orderID,
			@QueryParam("fileName") String fileName) throws MroException {
		Result result = new Result();
		String num = "";
		String resnum = "";
		num = IFUtil.addIfHistory("BIGDATA_CONFIRM", "orderID=" + orderID
				+ ", fileName=" + fileName, IFUtil.TYPE_INPUT);

		if (orderID == null || orderID.isEmpty()) {
			result.setStateCode("401");
			result.setMessage("请求参数错误:缺少工单号");
			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_NO,
					result.getMessage());
			return result.toString();
		}
		IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
				"orderID=" + orderID + ", fileName=" + fileName);
		resnum = IFUtil.addIfHistory("BIGDATA_CONFIRM", "orderID=" + orderID
				+ ", fileName=" + fileName, IFUtil.TYPE_OUTPUT);
		try {
			IJpoSet diagnoseSet = MroServer.getMroServer().getJpoSet(
					"FAULTDIAGNOSE",
					MroServer.getMroServer().getSystemUserServer());
			String where = "SOURCEFILEORDERID='" + orderID + "'";
			// 验证工单是否存在诊断记录
			diagnoseSet.setQueryWhere(where);
			diagnoseSet.reset();
			if (!(diagnoseSet.count() > 0)) {
				result.setStateCode("404");
				result.setMessage("无该工单诊断结果");
			} else {
				// 根据文件是否有诊断记录
				if (fileName != null && !fileName.isEmpty()) {
					where += " and FAULTFILENAME='" + fileName + "'";
					diagnoseSet.setQueryWhere(where);
					diagnoseSet.reset();
					if (!(diagnoseSet.count() > 0)) {
						result.setStateCode("403");
						result.setMessage("无该文件诊断结果");
						IFUtil.updateIfHistory(resnum, IFUtil.STATUS_SUCCESS,
								IFUtil.FLAG_NO, result.getMessage());
						return result.toString();
					}
				}
				// 是否确认故障原因
				diagnoseSet.setQueryWhere("DIAGNOSEFLAG='是' and " + where);
				diagnoseSet.reset();
				if (diagnoseSet.count() > 0) {
					IJpo diagnose = diagnoseSet.getJpo();
					String sourceFileName = diagnose.getString("FAULTFILENAME");
					String sourceFileOrderID = diagnose
							.getString("SOURCEFILEORDERID");
					String confirmResult = diagnose.getString("DIAGNOSERESULT");
					String confirmCause = diagnose.getString("DIAGNOSECAUSE");
					JSONObject jObj = new JSONObject();
					jObj.put("SOURCEFILENAME", sourceFileName);
					jObj.put("SOURCEFILEORDERID", sourceFileOrderID);
					jObj.put("CONFIRMRESULT", confirmResult);
					jObj.put("CONFIRMCAUSE", confirmCause);
					result.setData(jObj);
					IFUtil.updateIfHistory(resnum, IFUtil.STATUS_SUCCESS,
							IFUtil.FLAG_YES, jObj.toString());
				} else {
					result.setStateCode("402");
					result.setMessage("诊断确认结果为空，尚未确认故障原因");
					IFUtil.updateIfHistory(resnum, IFUtil.STATUS_SUCCESS,
							IFUtil.FLAG_NO, result.getMessage());
				}
			}
		} catch (MroException e) {
			result.setStateCode("409");
			result.setMessage("其他错误");
			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_NO,
					e.getMessage());
			e.printStackTrace();
		} finally {
			if (!result.getStateCode().equals("200")) {
				IFUtil.updateIfHistory(resnum, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_NO, result.getMessage());
			} else {
				result.setActionResult(true);
				IFUtil.updateIfHistory(resnum, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, result.getMessage());
			}
		}
		return result.toString();
	}

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test(@QueryParam("name") String name) throws MroException {
		Result r = new Result();
		r.setMessage("Welcome," + name);

		return r.toString();
	}

}