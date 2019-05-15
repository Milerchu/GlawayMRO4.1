package com.glaway.sddq.back.Interface.webservice.erp.sxtransfer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.IFUtil;

@WebService(endpointInterface = "com.glaway.sddq.back.Interface.webservice.erp.sxtransfer.ZfunMrotoErpSxService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class ZfunMrotoErpSxServiceImp implements ZfunMrotoErpSxService {

	// public String Ztfun_MrotoErpSx(String data) {
	// String num = "";
	// String nums = "";
	// String reason = "";
	// JSONObject obj = new JSONObject();
	// try {
	// num = IFUtil.addIfHistory("ERP_MRO_TOERPSXIF", data, IFUtil.TYPE_INPUT);
	// if (data == null || data.isEmpty() || data.equals("")) {
	// String msg = "E";
	// obj.put("MSG", msg);
	// obj.put("REASON", "送修单号不能为空");
	// IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
	// obj.getString("REASON"));
	// String a = toXmlString(obj.toString());
	// // return a.substring(45, a.length()).replace("</root>",
	// // "").replaceAll("><", ">\n<");
	// return obj.toString();
	//
	// }
	// IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
	// reason);
	// } catch (MroException e) {
	// String msg = "E";
	// obj.put("MSG", msg);
	// reason = e.getMessage();
	// obj.put("REASON", reason);
	// String a = toXmlString(obj.toString());
	// // return a.substring(45, a.length()).replace("</root>",
	// // "").replaceAll("><", ">\n<");
	// return obj.toString();
	// }
	// try {
	// IJpoSet transferSet = MroServer.getMroServer().getSysJpoSet("TRANSFER");
	// MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultOrg("CRRC");
	// MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultSite("ELEC");
	// transferSet.setQueryWhere("TRANSFERNUM='" + data + "'");
	// transferSet.reset();
	// if (transferSet != null && transferSet.count() > 0) {
	// IJpo transfer = transferSet.getJpo(0);
	// String TRANSFERNUM = transfer.getString("TRANSFERNUM");// 送修单号
	// obj.put("TRANSFERNUM", TRANSFERNUM);
	// String SENDORG = transfer.getString("SENDORG");// 送修单位
	// obj.put("SENDORG", SENDORG);
	// String SXTYPE = transfer.getString("SXTYPE");// 修造类别
	// obj.put("SXTYPE", SXTYPE);
	// String DJH = transfer.getString("COURIERNUM");// 单据号
	// obj.put("COURIERNUM", DJH);
	// String CREATEBY = transfer.getString("CREATEBY");// 制单人
	// obj.put("CREATEBY", CREATEBY);
	// Date CREATEDATE = transfer.getDate("CREATEDATE");// 制单日期
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// String CREATEDATES = sdf.format(CREATEDATE);
	// obj.put("CREATEDATE", CREATEDATES);
	// String LISTTYPE = transfer.getString("LISTTYPE");// 订单类型
	// obj.put("LISTTYPE", LISTTYPE);
	// String REPAIRORG = transfer.getString("REPAIRORG");// 承修单位
	// obj.put("REPAIRORG", REPAIRORG);
	// String RECEIVESTOREROOM = transfer.getString("RECEIVESTOREROOM");// 接收库房
	// obj.put("RECEIVESTOREROOM", RECEIVESTOREROOM);
	// // String AGENTBY = transfer.getString("AGENTBY");// 经办人
	// // obj.put("AGENTBY", AGENTBY);
	// String SENDSTOREROOM = transfer.getString("ISSUESTOREROOM");// 送修库房
	// obj.put("SENDSTOREROOM", SENDSTOREROOM);
	// Date SENDDATE = transfer.getDate("SENDDATE");// 送修日期
	// SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	// String SENDDATES;
	// if (SENDDATE != null) {
	// SENDDATES = sdf1.format(SENDDATE);
	// } else {
	// SENDDATES = "";
	// }
	// obj.put("SENDDATE", SENDDATES);
	// String GYS = transfer.getString("COMPANY");// 供应商
	// obj.put("COMPANY", GYS);
	// String CONTACTBY = transfer.getString("CONTACTBY");// 联系人
	// obj.put("CONTACTBY", CONTACTBY);
	// String CONTACTPHONE = transfer.getString("CONTACTPHONE");// 联系电话
	// obj.put("CONTACTPHONE", CONTACTPHONE);
	// String STATUS = transfer.getString("STATUS");// 状态
	// obj.put("STATUS", STATUS);
	// JSONArray array = new JSONArray();
	// IJpoSet transferlineSet = transfer.getJpoSet("TRANSFERLINE");
	// String lines;
	// if (transferlineSet != null && transferlineSet.count() > 0) {
	// for (int i = 0; i < transferlineSet.count(); i++) {
	// JSONObject objs = new JSONObject();
	// IJpo transferline = transferlineSet.getJpo(i);
	// String TRANSFERNUMS = transferline.getString("TRANSFERNUM");// 送修单号
	// objs.put("TRANSFERNUMS", TRANSFERNUMS);
	// int TRANSFERLINENUM = transferline.getInt("TRANSFERLINENUM");// 送修单行号
	// objs.put("TRANSFERLINENUM", TRANSFERLINENUM);
	// String WORKORDERNUM =
	// transferline.getString("PROJECTINFO.WORKORDERNUM");// 项目工作令号
	// objs.put("WORKORDERNUM", WORKORDERNUM);
	// String SCALENUM = transferline.getString("SCALENUM");// 销售订单号
	// objs.put("SCALENUM", SCALENUM);
	// String SCALELINENUM = transferline.getString("SCALELINENUM");// 销售订单行
	// objs.put("SCALELINENUM", SCALELINENUM);
	// String KHDM = transferline.getString("CUSTNUM");// 客户代码
	// objs.put("CUSTNUM", KHDM);
	// String KHMC = transferline.getString("CUSTNUMNAME");// 客户名称
	// objs.put("CUSTNUMNAME", KHMC);
	// String ITEMNUMDES = transferline.getString("ITEM.DESCRIPTION");// 物料描述
	// objs.put("ITEMNUMDES", ITEMNUMDES);
	// String ITEMNUM = transferline.getString("ITEMNUM");// 物料编号
	// objs.put("ITEMNUM", ITEMNUM);
	// String SQN = transferline.getString("SQN");// 序列号----
	// objs.put("SQN", SQN);
	// String MODEL = transferline.getString("MODEL");// 车型
	// objs.put("MODEL", MODEL);
	// Double ORDERQTY = transferline.getDouble("ORDERQTY");// 数量
	// objs.put("ORDERQTY", ORDERQTY);
	// String JBDW = transferline.getString("ITEM.ORDERUNIT.DESCRIPTION");//
	// 计算单位
	// objs.put("JLDW", JBDW);
	// String PRODUCTTYPE = transferline.getString("PRODUCTTYPE");// 产品类型
	// objs.put("PRODUCTTYPE", PRODUCTTYPE);
	// String FAULTMNGDES = transferline.getString("FAILUREDESC");// 故障描述
	// objs.put("FAULTMNGDES", FAULTMNGDES);
	// String FAULTCONSEQ = transferline.getString("FAILURECONS");// 故障后果
	// objs.put("FAULTCONSEQ", FAULTCONSEQ);
	// String QMSNUM = transferline.getString("QMSNUM");// QMS事件编号
	// objs.put("QMSNUM", QMSNUM);
	// String HARDWAREMEMO = transferline.getString("HARDWAREMEMO");// 硬件要求--
	// objs.put("HARDWAREMEMO", HARDWAREMEMO);
	// String SOFTWAREMEMO = transferline.getString("SOFTWAREMEMO");// 软件要求--
	// objs.put("SOFTWAREMEMO", SOFTWAREMEMO);
	// String OTHERMEMO = transferline.getString("OTHERMEMO");// 其他特殊要求--
	// objs.put("OTHERMEMO", OTHERMEMO);
	// String RECORDREQUIRED = transferline.getString("RECORDREQUIRED");// 记录要求
	// objs.put("RECORDREQUIRED", RECORDREQUIRED);
	// String ISREQUIREDCERT = transferline.getString("ISREQUIREDCERT");// 合格证要求
	// objs.put("ISREQUIREDCERT", ISREQUIREDCERT);
	// String PROJNUM = transferline.getString("PROJNUM");// 车型项目
	// objs.put("PROJNUM", PROJNUM);
	// String softbb = transferline.getString("SOFTVERSIONNUM");// 软件版本号
	// objs.put("SOFTVERSIONNUM", softbb);
	// String SOFTNUM = transferline.getString("SOFTNUM");// 软件编号
	// objs.put("SOFTNUM", SOFTNUM);
	// String IMPORTLEVEL = transferline.getString("IMPORTLEVEL");// 紧急程度
	// objs.put("IMPORTLEVEL", IMPORTLEVEL);
	// Date PLANREPAURDATES = transferline.getDate("PLANREPAURDATE");// 应修复日期
	// if (PLANREPAURDATES == null) {
	// objs.put("PLANREPAURDATE", "");
	// } else {
	// objs.put("PLANREPAURDATE", sdf.format(PLANREPAURDATES));
	//
	// }
	// String ISJSFX = transferline.getString("ISJSFX");// 是否需技术分析
	// objs.put("ISJSFX", ISJSFX);
	// String TRANSNOTICENUM = transferline.getString("TRANSNOTICENUM");//
	// 改造通知单号
	// objs.put("TRANSNOTICENUM", TRANSNOTICENUM);
	// String TASKNUM = transferline.getString("TASKNUM");// 工单号
	// objs.put("TASKNUM", TASKNUM);
	// Double RECEIVEQTY = transferline.getDouble("PASSQTY");// 合格数量
	// objs.put("PASSQTY", RECEIVEQTY);
	// Double SCRAPQTY = transferline.getDouble("FAILQTY");// 报废数量
	// objs.put("FAILQTY", SCRAPQTY);
	// array.add(objs);
	// }
	// lines = array.toString();
	// } else {
	// lines = "";
	// }
	// obj.put("LINES", lines);
	// String msg = "S";
	// obj.put("MSG", msg);
	// reason = "";
	// obj.put("REASON", reason);
	// nums = IFUtil.addIfHistory("ERP_MRO_TOERPSXIF", obj.toString(),
	// IFUtil.TYPE_OUTPUT);
	// IFUtil.updateIfHistory(nums, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
	// reason);
	// String a = toXmlString(obj.toString());
	// // return toXmlString(obj.toString());
	// return obj.toString();
	// } else {
	// String msg = "E";
	// obj.put("MSG", msg);
	// reason = "送修单号不存在";
	// obj.put("REASON", reason);
	// IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
	// reason);
	// String a = toXmlString(obj.toString());
	// // return a.substring(45, a.length()).replace("</root>",
	// // "").replaceAll("><", ">\n<");
	// return obj.toString();
	//
	// }
	//
	// } catch (Exception e) {
	// try {
	// nums = IFUtil.addIfHistory("ERP_MRO_TOERPSXIF", obj.toString(),
	// IFUtil.TYPE_OUTPUT);
	// IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
	// e.toString());
	// } catch (MroException e1) {
	// e1.printStackTrace();
	// }
	// String msg = "E";
	// obj.put("MSG", msg);
	// reason = e.toString();
	// obj.put("REASON", reason);
	// e.printStackTrace();
	// String a = toXmlString(obj.toString());
	// // return a.substring(45, a.length()).replace("</root>", "");
	// return obj.toString();
	//
	// }
	// }
	// @Override
	// public ZfunMrotoErpResponse Ztfun_MrotoErpSxs(String data) {
	// ZfunMrotoErpResponse Response = new ZfunMrotoErpResponse();
	// String json = Ztfun_MrotoErpSx(data);
	// @SuppressWarnings("static-access")
	// JSONObject obj = new JSONObject().parseObject(json);
	// if (obj.getString("MSG").equals("S")) {
	// Response.setTRANSFERNUM(obj.getString("TRANSFERNUM"));
	// Response.setSENDORG(obj.getString("SENDORG"));
	// Response.setSXTYPE(obj.getString("SXTYPE"));
	// Response.setCOURIERNUM(obj.getString("COURIERNUM"));
	// Response.setCREATEBY(obj.getString("CREATEBY"));
	// Response.setCREATEDATE(obj.getString("CREATEDATE"));
	// Response.setLISTTYPE(obj.getString("LISTTYPE"));
	// Response.setREPAIRORG(obj.getString("REPAIRORG"));
	// Response.setRECEIVESTOREROOM(obj.getString("RECEIVESTOREROOM"));
	// Response.setSENDSTOREROOM(obj.getString("SENDSTOREROOM"));
	// Response.setSENDDATE(obj.getString("SENDDATE"));
	// Response.setCOMPANY(obj.getString("COMPANY"));
	// Response.setCONTACTBY(obj.getString("CONTACTBY"));
	// Response.setCONTACTPHONE(obj.getString("CONTACTPHONE"));
	// Response.setSTATUS(obj.getString("STATUS"));
	// Response.setMSG(obj.getString("MSG"));
	// Response.setREASON(obj.getString("REASON"));
	// List<ZfunMrotoErpResponses> ls = new ArrayList<ZfunMrotoErpResponses>();
	// String LINES = obj.getString("LINES");
	// @SuppressWarnings("static-access")
	// JSONArray array = new JSONArray().parseArray(LINES);
	// if (array.isEmpty())
	// return Response;
	//
	// for (int i = 0; i < array.size(); i++) {
	// JSONObject objs = array.getJSONObject(i);
	// ZfunMrotoErpResponses Responses = new ZfunMrotoErpResponses();
	// Responses.setTRANSFERNUMS(objs.getString("TRANSFERNUMS"));
	// Responses.setTRANSFERLINENUM(objs.getString("TRANSFERLINENUM"));
	// Responses.setWORKORDERNUM(objs.getString("WORKORDERNUM"));
	// Responses.setSCALENUM(objs.getString("SCALENUM"));
	// Responses.setSCALELINENUM(objs.getString("SCALELINENUM"));
	// Responses.setCUSTNUM(objs.getString("CUSTNUM"));
	// Responses.setCUSTNUMNAME(objs.getString("CUSTNUMNAME"));
	// Responses.setITEMNUM(objs.getString("ITEMNUM"));
	// Responses.setITEMNUMDES(objs.getString("ITEMNUMDES"));
	// Responses.setSQN(objs.getString("SQN"));
	// Responses.setMODEL(objs.getString("MODEL"));
	// Responses.setORDERQTY(objs.getDouble("ORDERQTY"));
	// Responses.setJLDW(objs.getString("JLDW"));
	// Responses.setPRODUCTTYPE(objs.getString("PRODUCTTYPE"));
	// Responses.setFAULTMNGDES(objs.getString("FAULTMNGDES"));
	// Responses.setFAULTCONSEQ(objs.getString("FAULTCONSEQ"));
	// Responses.setQMSNUM(objs.getString("QMSNUM"));
	// Responses.setHARDWAREMEMO(objs.getString("HARDWAREMEMO"));
	// Responses.setSOFTWAREMEMO(objs.getString("SOFTWAREMEMO"));
	// Responses.setOTHERMEMO(objs.getString("OTHERMEMO"));
	// Responses.setRECORDREQUIRED(objs.getString("RECORDREQUIRED"));
	// Responses.setISREQUIREDCERT(objs.getString("ISREQUIREDCERT"));
	// Responses.setPROJNUM(objs.getString("PROJNUM"));
	// Responses.setSOFTVERSIONNUM(objs.getString("SOFTVERSIONNUM"));
	// Responses.setSOFTNUM(objs.getString("SOFTNUM"));
	// Responses.setIMPORTLEVEL(objs.getString("IMPORTLEVEL"));
	// Responses.setPLANREPAURDATE(objs.getString("PLANREPAURDATE"));
	// Responses.setISJSFX(objs.getString("ISJSFX"));
	// Responses.setTRANSNOTICENUM(objs.getString("TRANSNOTICENUM"));
	// Responses.setTASKNUM(objs.getString("TASKNUM"));
	// Responses.setPASSQTY(objs.getDouble("PASSQTY"));
	// Responses.setFAILQTY(objs.getDouble("FAILQTY"));
	// ls.add(Responses);
	// }
	// Response.setLINE(ls);
	//
	// } else {
	// Response.setMSG(obj.getString("MSG"));
	// Response.setREASON(obj.getString("REASON"));
	// }
	// return Response;
	//
	// }
	//
	// public static String toXmlString(String json) {
	// JSONObject jObj = JSONObject.parseObject(json);
	// Document doc = DocumentHelper.createDocument();
	// Element root = doc.addElement("root");
	// jsonToXml(root, jObj);
	// return doc.asXML();
	// }
	// private static void jsonToXml(Element node, JSONObject jObj) {
	// for (Map.Entry<String, Object> entry : jObj.entrySet()) {
	// Element e = node.addElement(entry.getKey());
	// if (entry.getValue().toString().startsWith("[") &&
	// entry.getValue().toString().endsWith("]")) {
	// JSONArray jArr = JSONArray.parseArray(entry.getValue().toString());
	// for (int i = 0; i < jArr.size(); i++) {
	// Element sub = e.addElement("LINE");
	// JSONObject subObj = JSONObject.parseObject(jArr.get(i).toString());
	// jsonToXml(sub, subObj);
	// }
	// } else {
	// e.addText(entry.getValue().toString());
	// }
	// }
	// }

}
