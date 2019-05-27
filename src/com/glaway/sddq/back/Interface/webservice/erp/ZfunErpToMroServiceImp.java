package com.glaway.sddq.back.Interface.webservice.erp;

import io.netty.util.internal.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.back.Interface.webservice.erp.jxtask.InfoBack;
import com.glaway.sddq.back.Interface.webservice.erp.jxtask.JxTaskItem;
import com.glaway.sddq.back.Interface.webservice.erp.jxtask.Mustchangeinfo;
import com.glaway.sddq.back.Interface.webservice.erp.jxtask.SqnInfo;
import com.glaway.sddq.back.Interface.webservice.erp.jxtask.SqnInfoSet;
import com.glaway.sddq.back.Interface.webservice.erp.jxtask.TaskParameter;
import com.glaway.sddq.back.Interface.webservice.erp.sxtransfer.ZfunErptoMroDbzkLineSet;
import com.glaway.sddq.back.Interface.webservice.erp.sxtransfer.ZfunErptoMroDbzkResponses;
import com.glaway.sddq.back.Interface.webservice.erp.sxtransfer.ZfunErptoMroJkLineSet;
import com.glaway.sddq.back.Interface.webservice.erp.sxtransfer.ZfunErptoMroResponse;
import com.glaway.sddq.back.Interface.webservice.erp.sxtransfer.ZfunErptoMroResponses;
import com.glaway.sddq.material.invtrans.common.InventoryQtyCommon;
import com.glaway.sddq.material.invtrans.common.TransInvtranscommon;
import com.glaway.sddq.material.invtrans.common.TransStoroomCommon;
import com.glaway.sddq.material.transfer.data.Transfer;
import com.glaway.sddq.material.transfer.data.TransferSet;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.MsgUtil;
import com.glaway.sddq.tools.SddqConstant;

@WebService(endpointInterface = "com.glaway.sddq.back.Interface.webservice.erp.ZfunErpToMroService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class ZfunErpToMroServiceImp implements ZfunErpToMroService {

    @Override
    public InfoBack Ztfun_toMroCheckRepairOrder(TaskParameter taskParameter) {

        InfoBack infoback = new InfoBack();
        String num = "";
        try {
            String json = JSON.toJSONString(taskParameter);
            num = IFUtil.addIfHistory("ERP_MRO_JXWORKORDERIF", json, IFUtil.TYPE_INPUT);

            if (!StringUtil.isNullOrEmpty(json)) {
                IJpoSet jxtaskorderSet = MroServer.getMroServer().getJpoSet("JXTASKORDER",
                        MroServer.getMroServer().getSystemUserServer());
                MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultOrg("CRRC");
                MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultSite("ELEC");
                jxtaskorderSet.setUserWhere("productionordernum='" + taskParameter.getProductionordernum() + "'");
                jxtaskorderSet.reset();

                if (jxtaskorderSet == null || jxtaskorderSet.count() == 0) {
                    IJpo jxtaskorder = null;
					// 检修工单赋值
                    jxtaskorder = jxtaskorderSet.addJpo();
					// 判断车型，车号，修程不可为空
                    if (StringUtil.isNullOrEmpty(taskParameter.getCarno())) {
						infoback.setMsg("CARNO字段必填");
                        return infoback;
                    } else {
                        jxtaskorder.setValue("CARNO", taskParameter.getCarno(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                    }
                    if (taskParameter.getErpcmodel().isEmpty()) {
						infoback.setMsg("ERPCMODEL字段必填");
                        return infoback;
                    } else {
                        IJpoSet modelsSedt = MroServer.getMroServer().getSysJpoSet("MODELS",
                                "MODELDESC='" + taskParameter.getErpcmodel() + "'");
                        if (!modelsSedt.isEmpty() && modelsSedt.count() > 0) {
                            String modelnum = modelsSedt.getJpo().getString("MODELNUM");
							// 判断车号车型是否匹配，匹配自动关联出车型，不匹配，手动选择
                            IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET",
                                    "cmodel='" + modelnum + "' and carno='" + taskParameter.getCarno() + "'");
                            if (!jposet.isEmpty() && jposet.count() > 0) {
                                String cmodel = jposet.getJpo().getString("CMODEL");
                                String assetnum = jposet.getJpo().getString("ASSETNUM");
                                jxtaskorder.setValue("CMODEL", cmodel, GWConstant.P_NOVALIDATION_AND_NOACTION);
                                jxtaskorder.setValue("ERPCMODEL", taskParameter.getErpcmodel(),
                                        GWConstant.P_NOVALIDATION_AND_NOACTION);
                                jxtaskorder.setValue("ASSETNUM", assetnum, GWConstant.P_NOVALIDATION_AND_NOACTION);
                            } else {                           	
                           	 jxtaskorder.setValue("ERPCMODEL", taskParameter.getErpcmodel(),
                                     GWConstant.P_NOVALIDATION_AND_NOACTION);	
                        	 jxtaskorder.setValue("CMODEL", modelnum,
                                     GWConstant.P_NOVALIDATION_AND_NOACTION);	
                        }
                    }else{
                    	infoback.setMsg("输入的车型车号无法匹配车辆信息");
                        return infoback;
                    }
                    }
                    if (taskParameter.getRepairprocess().isEmpty()) {
						infoback.setMsg("REPAIRPROCESS字段必填");
                        return infoback;
                    } else {
                        jxtaskorder.setValue("REPAIRPROCESS", taskParameter.getRepairprocess());
                    }
                    if (taskParameter.getAmount() <= 0) {
						infoback.setMsg("amount字段需大于0");
                        return infoback;
                    } else {
                        jxtaskorder.setValue("AMOUNT", taskParameter.getAmount(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                    }
                    jxtaskorder.setValue("JXCODE", taskParameter.getJxcode(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                    jxtaskorder.setValue("ITEMNAME", taskParameter.getJxdesc(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                    jxtaskorder.setValue("FACTORY", taskParameter.getFactory(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                    jxtaskorder.setValue("DISPATCHER", taskParameter.getDispatcher(),
                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                    jxtaskorder.setValue("WHICHOFFICE", taskParameter.getWhichoffice(),
                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                    jxtaskorder.setValue("STOCK", taskParameter.getStock(), GWConstant.P_NOVALIDATION_AND_NOACTION);

                    jxtaskorder.setValue("PRODUCTIONORDERNUM", taskParameter.getProductionordernum(),
                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                    jxtaskorder.setValue("LIABLEPERSON", taskParameter.getLiableperson(),
                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                    jxtaskorder.setValue("PLANSTARTTIME", taskParameter.getPlanstarttime(),
                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                    jxtaskorder.setValue("PLANENDTIME", taskParameter.getPlanendtime(),
                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                    jxtaskorder.setValue("REMARK", taskParameter.getRemark(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                    String jxtasknum = jxtaskorder.getString("jxtasknum");
                    // List<Jxproduct> jxproducts =
                    // taskParameter.getJxproduct();

                    if (taskParameter.getAmount() > 0) {
                        IJpoSet jxproductSet = jxtaskorder.getJpoSet("JXPRODUCT");
                        for (int index = 0; index < taskParameter.getAmount(); index++) {
                            IJpo jxproduct = jxproductSet.addJpo();
                            jxproduct.setValue("jxtasknum", jxtasknum);
                            String autonum = jxproduct.getString("AUTONUM");
                            String jxtasknums = jxproduct.getString("JXTASKNUM");
                            List<JxTaskItem> jxtaskitem = taskParameter.getJxtaskitem();
                            for (JxTaskItem jx : jxtaskitem) {
                                IJpoSet jxtaskitemSet = jxtaskorder.getJpoSet("JXTASKITEM");
                                IJpo jxtaskitems = jxtaskitemSet.addJpo();
                                jxtaskitems.setValue("SEQ", jx.getSeq(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                                jxtaskitems.setValue("WORKCENTER", jx.getWorkcenter(),
                                        GWConstant.P_NOVALIDATION_AND_NOACTION);
                                jxtaskitems
                                        .setValue("FACTORY", jx.getFactory(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                                jxtaskitems.setValue("CONTROLCODE", jx.getControlcode(),
                                        GWConstant.P_NOVALIDATION_AND_NOACTION);
                                jxtaskitems.setValue("DESCRIPTION", jx.getDescription(),
                                        GWConstant.P_NOVALIDATION_AND_NOACTION);
                                jxtaskitems.setValue("AUTONUM", autonum, GWConstant.P_NOVALIDATION_AND_NOACTION);
                                jxtaskitems.setValue("JXTASKNUM", jxtasknums, GWConstant.P_NOVALIDATION_AND_NOACTION);
                                jxtaskitems.setValue("SN", jx.getSn(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                                jxtaskitems.setValue("MANUALWORK", jx.getManualwork() != null ? jx.getManualwork()
                                        .trim() : "", GWConstant.P_NOVALIDATION_AND_NOACTION);
                                jxtaskitems.setValue("MACHINEWORK", jx.getMachinework() != null ? jx.getMachinework()
                                        .trim() : "", GWConstant.P_NOVALIDATION_AND_NOACTION);
                                jxtaskitems.setValue("INDIRECTCOSTWORK", jx.getIndirectcostwork() != null ? jx
                                        .getIndirectcostwork().trim() : "", GWConstant.P_NOVALIDATION_AND_NOACTION);
                            }
                        }
                    }

                    List<Mustchangeinfo> mustchangeinfo = taskParameter.getMustchangeinfo();

                    for (Mustchangeinfo mu : mustchangeinfo) {
                        String productionordernum = jxtaskorderSet.getJpo().getString("PRODUCTIONORDERNUM");
                        IJpoSet mustchangeinfoSet = jxtaskorder.getJpoSet("MUSTCHANGEINFO");
                        IJpo mustchangeinfos = mustchangeinfoSet.addJpo();
						// 必换件信息赋值
                        mustchangeinfos.setValue("MOBILETYPE", mu.getMobiletype(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);

                        mustchangeinfos.setValue("PRODUCTIONORDERNUM", productionordernum,
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        mustchangeinfos.setValue("STOCKADDRESS", mu.getStockaddress(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        mustchangeinfos.setValue("ITEMNUM", mu.getItemnums(), GWConstant.P_NOVALIDATION_AND_NOACTION);

                        mustchangeinfos.setValue("AMOUNT", mu.getAmounts(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        mustchangeinfos.setValue("MEASUREMENTUNIT", mu.getMeasurementunit(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        mustchangeinfos.setValue("OBLIGATENUM", mu.getObligatenum(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        mustchangeinfos.setValue("OBLIGATELINENUM", mu.getObligatelinenum(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        mustchangeinfos.setValue("RECORDTYPE", mu.getRecordtype(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                    }
					// 保存生成的表单
                    jxtaskorderSet.save();
                } else if (jxtaskorderSet != null && jxtaskorderSet.count() > 0) {
                    String status = jxtaskorderSet.getJpo().getString("status");
					if (!status.equals("草稿")) {
						infoback.setMsg("已开工，不可添加数据！");
                        return infoback;
					} else if (jxtaskorderSet != null && status.equals("草稿")) {
                        IJpoSet jxproducta = jxtaskorderSet.getJpo(0).getJpoSet("JXPRODUCT");
                        jxproducta.deleteAll();
                        IJpoSet jxtaskitema = jxtaskorderSet.getJpo().getJpoSet("JXTASKITEM");
                        jxtaskitema.deleteAll();
                        IJpoSet mustchangeinfoa = jxtaskorderSet.getJpo().getJpoSet("MUSTCHANGEINFO");
                        mustchangeinfoa.deleteAll();
                        // jxtaskorderSet.setUserWhere(arg0)
						// 检修工单赋值
                        IJpo jxtaskorder = jxtaskorderSet.getJpo();
						// 判断车型，车号，修程不可为空
                        if (StringUtil.isNullOrEmpty(taskParameter.getCarno())) {
							infoback.setMsg("CARNO字段必填");
                            return infoback;
                        } else {
                            jxtaskorder.setValue("CARNO", taskParameter.getCarno(),
                                    GWConstant.P_NOVALIDATION_AND_NOACTION);
                        }
                        if (taskParameter.getErpcmodel().isEmpty()) {
							infoback.setMsg("CMODEL字段必填");
                            return infoback;
                        } else {

                            IJpoSet modelsSedt = MroServer.getMroServer().getSysJpoSet("MODELS",
                                    "MODELDESC='" + taskParameter.getErpcmodel() + "'");
                            if (!modelsSedt.isEmpty() && modelsSedt.count() > 0) {
                                String modelnum = modelsSedt.getJpo().getString("MODELNUM");
								// 判断车号车型是否匹配，匹配自动关联出车型，不匹配，手动选择
                                IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET",
                                        "cmodel='" + modelnum + "' and carno='" + taskParameter.getCarno() + "'");
                                if (!jposet.isEmpty() && jposet.count() > 0) {
                                    String cmodel = jposet.getJpo().getString("CMODEL");
                                    jxtaskorder.setValue("CMODEL", cmodel, GWConstant.P_NOVALIDATION_AND_NOACTION);
                                    jxtaskorder.setValue("ERPCMODEL", taskParameter.getErpcmodel(),
                                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                                } else {
                                    jxtaskorder.setValue("ERPCMODEL", taskParameter.getErpcmodel(),
                                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                                    jxtaskorder.setValue("CMODEL", modelnum,
                                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                                }
                            }else{
                            	 infoback.setMsg("输入的车型车号无法匹配车辆信息");
                                 return infoback;
                            }  
                        }
                        if (taskParameter.getRepairprocess().isEmpty()) {
							infoback.setMsg("REPAIRPROCESS字段必填");
                            return infoback;

                        } else {
                            jxtaskorder.setValue("REPAIRPROCESS", taskParameter.getRepairprocess(),
                                    GWConstant.P_NOVALIDATION_AND_NOACTION);
                        }
                        jxtaskorder.setValue("JXCODE", taskParameter.getJxcode(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jxtaskorder.setValue("FACTORY", taskParameter.getFactory(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jxtaskorder.setValue("DISPATCHER", taskParameter.getDispatcher(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jxtaskorder.setValue("WHICHOFFICE", taskParameter.getWhichoffice(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jxtaskorder.setValue("STOCK", taskParameter.getStock(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        if (taskParameter.getAmount() <= 0) {
							infoback.setMsg("amount字段需大于0");
                            return infoback;
                        } else {
                            jxtaskorder.setValue("AMOUNT", taskParameter.getAmount(),
                                    GWConstant.P_NOVALIDATION_AND_NOACTION);
                        }
                        jxtaskorder.setValue("PRODUCTIONORDERNUM", taskParameter.getProductionordernum(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jxtaskorder.setValue("LIABLEPERSON", taskParameter.getLiableperson(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jxtaskorder.setValue("PLANSTARTTIME", taskParameter.getPlanstarttime(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jxtaskorder.setValue("PLANENDTIME", taskParameter.getPlanendtime(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jxtaskorder.setValue("REMARK", taskParameter.getRemark(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        String jxtasknum = jxtaskorder.getString("jxtasknum");
                        // List<Jxproduct> jxproducts =
                        // taskParameter.getJxproduct();

                        if (taskParameter.getAmount() > 0) {
                            IJpoSet jxproductSet = jxtaskorder.getJpoSet("JXPRODUCT");
                            for (int index = 0; index < taskParameter.getAmount(); index++) {
                                IJpo jxproduct = jxproductSet.addJpo();
                                jxproduct.setValue("jxtasknum", jxtasknum);
                                String autonum = jxproduct.getString("AUTONUM");
                                String jxtasknums = jxproduct.getString("JXTASKNUM");
                                List<JxTaskItem> jxtaskitem = taskParameter.getJxtaskitem();
                                for (JxTaskItem jx : jxtaskitem) {
                                    IJpoSet jxtaskitemSet = jxtaskorder.getJpoSet("JXTASKITEM");
                                    IJpo jxtaskitems = jxtaskitemSet.addJpo();
                                    jxtaskitems.setValue("SEQ", jx.getSeq(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                                    jxtaskitems.setValue("WORKCENTER", jx.getWorkcenter(),
                                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                                    jxtaskitems.setValue("FACTORY", jx.getFactory(),
                                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                                    jxtaskitems.setValue("CONTROLCODE", jx.getControlcode(),
                                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                                    jxtaskitems.setValue("DESCRIPTION", jx.getDescription(),
                                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                                    jxtaskitems.setValue("AUTONUM", autonum, GWConstant.P_NOVALIDATION_AND_NOACTION);
                                    jxtaskitems.setValue("JXTASKNUM", jxtasknums,
                                            GWConstant.P_NOVALIDATION_AND_NOACTION);
                                    jxtaskitems.setValue("SN", jx.getSn(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                                    jxtaskitems.setValue("MANUALWORK", jx.getManualwork() != null ? jx.getManualwork()
                                            .trim() : "", GWConstant.P_NOVALIDATION_AND_NOACTION);
                                    jxtaskitems.setValue("MACHINEWORK", jx.getMachinework() != null ? jx
                                            .getMachinework().trim() : "", GWConstant.P_NOVALIDATION_AND_NOACTION);
                                    jxtaskitems.setValue("INDIRECTCOSTWORK", jx.getIndirectcostwork() != null ? jx
                                            .getIndirectcostwork().trim() : "", GWConstant.P_NOVALIDATION_AND_NOACTION);
                                }
                            }
                        }

                        List<Mustchangeinfo> mustchangeinfo = taskParameter.getMustchangeinfo();

                        for (Mustchangeinfo mu : mustchangeinfo) {
                            String productionordernum = jxtaskorderSet.getJpo().getString("PRODUCTIONORDERNUM");
                            IJpoSet mustchangeinfoSet = jxtaskorder.getJpoSet("MUSTCHANGEINFO");
                            IJpo mustchangeinfos = mustchangeinfoSet.addJpo();
							// 必换件信息赋值

                            mustchangeinfos.setValue("PRODUCTIONORDERNUM", productionordernum,
                                    GWConstant.P_NOVALIDATION_AND_NOACTION);
                            mustchangeinfos.setValue("MOBILETYPE", mu.getMobiletype(),
                                    GWConstant.P_NOVALIDATION_AND_NOACTION);

                            mustchangeinfos.setValue("STOCKADDRESS", mu.getStockaddress(),
                                    GWConstant.P_NOVALIDATION_AND_NOACTION);
                            mustchangeinfos.setValue("ITEMNUM", mu.getItemnums(),
                                    GWConstant.P_NOVALIDATION_AND_NOACTION);
                            mustchangeinfos.setValue("AMOUNT", mu.getAmounts(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                            mustchangeinfos.setValue("MEASUREMENTUNIT", mu.getMeasurementunit(),
                                    GWConstant.P_NOVALIDATION_AND_NOACTION);
                            mustchangeinfos.setValue("OBLIGATENUM", mu.getObligatenum(),
                                    GWConstant.P_NOVALIDATION_AND_NOACTION);
                            mustchangeinfos.setValue("OBLIGATELINENUM", mu.getObligatelinenum(),
                                    GWConstant.P_NOVALIDATION_AND_NOACTION);
                            mustchangeinfos.setValue("RECORDTYPE", mu.getRecordtype(),
                                    GWConstant.P_NOVALIDATION_AND_NOACTION);
                            /*
                             * mustchangeinfos.setValue("LOTNUM", mu.getLotnum(),
                             * GWConstant.P_NOVALIDATION_AND_NOACTION);
                             */
                        }
						// 保存生成的表单
                        jxtaskorderSet.save();
                    }
                }
                IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "");

            } else {
				infoback.setMsg("数据不可为空");
            }
        } catch (MroException e) {
			// 处理失败
            try {
                IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, e.getMessage());
                infoback.setMsg(e.getMessage());
            } catch (MroException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return infoback;
    }


    @Override
    public ZfunErptoMroResponse Ztfun_ErptoMroSxs(String data) {

        ZfunErptoMroResponse Response = new ZfunErptoMroResponse();
        String json = Ztfun_MrotoErpSx(data);
        @SuppressWarnings("static-access")
        JSONObject obj = new JSONObject().parseObject(json);
        if (obj.getString("MSG").equals("S")) {
            Response.setTRANSFERNUM(obj.getString("TRANSFERNUM"));
            Response.setSENDORG(obj.getString("SENDORG"));
            Response.setSXTYPE(obj.getString("SXTYPE"));
            Response.setCOURIERNUM(obj.getString("COURIERNUM"));
            Response.setCREATEBY(obj.getString("CREATEBY"));
            Response.setCREATEDATE(obj.getString("CREATEDATE"));
            Response.setLISTTYPE(obj.getString("LISTTYPE"));
            Response.setREPAIRORG(obj.getString("REPAIRORG"));
            Response.setRECEIVESTOREROOM(obj.getString("RECEIVESTOREROOM"));
            Response.setSENDSTOREROOM(obj.getString("SENDSTOREROOM"));
            Response.setSENDDATE(obj.getString("SENDDATE"));
            Response.setCOMPANY(obj.getString("COMPANY"));
            Response.setCONTACTBY(obj.getString("CONTACTBY"));
            Response.setCONTACTPHONE(obj.getString("CONTACTPHONE"));
            Response.setSTATUS(obj.getString("STATUS"));
            Response.setMSG(obj.getString("MSG"));
            Response.setREASON(obj.getString("REASON"));
			Response.setCREATEDATESTIME(obj.getString("CREATEDATESTIME"));// 2018-07-24新增
            Response.setCREATEBYDISPLAYNAME(obj.getString("CREATEBYDISPLAYNAME"));
            Response.setRECIVEBY(obj.getString("RECIVEBY"));
            Response.setRECIVEBYDISPLAYNAME(obj.getString("RECIVEBYDISPLAYNAME"));
            Response.setRECIVEDATE(obj.getString("RECIVEDATE"));
            Response.setRECIVETIME(obj.getString("RECIVETIME"));
            Response.setPLANREPAURDATE(obj.getString("PLANREPAURDATE"));
            List<ZfunErptoMroResponses> ls = new ArrayList<ZfunErptoMroResponses>();
            String LINES = obj.getString("LINES");
            @SuppressWarnings("static-access")
            JSONArray array = new JSONArray().parseArray(LINES);
            if (array.isEmpty())
                return Response;

            for (int i = 0; i < array.size(); i++) {
                JSONObject objs = array.getJSONObject(i);
                ZfunErptoMroResponses Responses = new ZfunErptoMroResponses();
                Responses.setTRANSFERNUMS(objs.getString("TRANSFERNUMS"));
                Responses.setTRANSFERLINENUM(objs.getString("TRANSFERLINENUM"));
                Responses.setWORKORDERNUM(objs.getString("WORKORDERNUM"));
                Responses.setSCALENUM(objs.getString("SCALENUM"));
                Responses.setSCALELINENUM(objs.getString("SCALELINENUM"));
                Responses.setCUSTNUM(objs.getString("CUSTNUM"));
                Responses.setCUSTNUMNAME(objs.getString("CUSTNUMNAME"));
                Responses.setITEMNUM(objs.getString("ITEMNUM"));
                Responses.setITEMNUMDES(objs.getString("ITEMNUMDES"));
                Responses.setSQN(objs.getString("SQN"));
                Responses.setMODEL(objs.getString("MODEL"));
                Responses.setORDERQTY(objs.getDouble("ORDERQTY"));
                Responses.setJLDW(objs.getString("JLDW"));
                Responses.setPRODUCTTYPE(objs.getString("PRODUCTTYPE"));
                Responses.setFAULTMNGDES(objs.getString("FAULTMNGDES"));
                Responses.setFAULTCONSEQ(objs.getString("FAULTCONSEQ"));
                Responses.setQMSNUM(objs.getString("QMSNUM"));
                Responses.setHARDWAREMEMO(objs.getString("HARDWAREMEMO"));
                Responses.setSOFTWAREMEMO(objs.getString("SOFTWAREMEMO"));
                Responses.setOTHERMEMO(objs.getString("OTHERMEMO"));
                Responses.setRECORDREQUIRED(objs.getString("RECORDREQUIRED"));
                Responses.setISREQUIREDCERT(objs.getString("ISREQUIREDCERT"));
                Responses.setPROJNUM(objs.getString("PROJNUM"));
                Responses.setSOFTVERSIONNUM(objs.getString("SOFTVERSIONNUM"));
                Responses.setSOFTNUM(objs.getString("SOFTNUM"));
                Responses.setIMPORTLEVEL(objs.getString("IMPORTLEVEL"));
                Responses.setPLANREPAURDATE(objs.getString("PLANREPAURDATE"));
                Responses.setISJSFX(objs.getString("ISJSFX"));
                Responses.setTRANSNOTICENUM(objs.getString("TRANSNOTICENUM"));
                Responses.setTASKNUM(objs.getString("TASKNUM"));
                Responses.setPASSQTY(objs.getDouble("PASSQTY"));
                Responses.setFAILQTY(objs.getDouble("FAILQTY"));
                // Responses.set
                ls.add(Responses);
            }
            Response.setLINE(ls);
            IJpoSet transferset;
			try {
				transferset = MroServer.getMroServer().getSysJpoSet("transfer");
				transferset.setUserWhere("transfernum='"+data+"'");
				if(!transferset.isEmpty()){
					if (transferset.getJpo(0).getString("status")
							.equalsIgnoreCase("在途")) {
						transferset.getJpo(0).setValue("status", "已接收",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						transferset.save();
						IJpoSet invtransferset= MroServer.getMroServer().getSysJpoSet("transfer");
						invtransferset.setUserWhere("transfernum='"+data+"'");
						if(!invtransferset.isEmpty()){
							IJpoSet invtransferlineset=invtransferset.getJpo(0).getJpoSet("transferline");
							if(!invtransferlineset.isEmpty()){
								TransStoroomCommon.out_storoom(invtransferlineset);// 调用公共方法物料出库
								TransInvtranscommon.out_invtrans(invtransferlineset);// 调用公共方法物料出库库存交易记录
								TransStoroomCommon.in_storoom(invtransferlineset);
								TransInvtranscommon.in_invtrans(invtransferlineset);
								for(int i=0;i<invtransferlineset.count();i++){
									IJpo invtransferline=invtransferlineset.getJpo(i);
									double djqty=invtransferline.getDouble("ORDERQTY");
									String itemnum=invtransferline.getString("itemnum");
									String issuestoreroom=transferset.getJpo(0).getString("issuestoreroom");
									String receivestoreroom=transferset.getJpo(0).getString("receivestoreroom");
									invtransferline.setValue("YJSQTY", djqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									invtransferline
											.setValue(
													"status",
													"已接收",
													GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									InventoryQtyCommon.SQZYQTY(itemnum, issuestoreroom);
									InventoryQtyCommon.FCZTQTY(itemnum, issuestoreroom);
									InventoryQtyCommon.JSZTQTY(itemnum, issuestoreroom);
									InventoryQtyCommon.KYQTY(itemnum, issuestoreroom);
									
									InventoryQtyCommon.SQZYQTY(itemnum, receivestoreroom);
									InventoryQtyCommon.FCZTQTY(itemnum, receivestoreroom);
									InventoryQtyCommon.JSZTQTY(itemnum, receivestoreroom);
									InventoryQtyCommon.KYQTY(itemnum, receivestoreroom);
								}
								invtransferlineset.save();
							}
						}
					}
					
				}
			} catch (MroException e) {
				e.printStackTrace();
			}
        } else {
            Response.setMSG(obj.getString("MSG"));
            Response.setREASON(obj.getString("REASON"));
        }
        return Response;
    }

    /**
	 * <功能描述>根据送修单号返回送修单明细给erp
	 * 
	 * @param data
	 * @return [参数说明]
	 */
    public String Ztfun_MrotoErpSx(String data) {
        String num = "";
        String nums = "";
        String reason = "";
        JSONObject obj = new JSONObject();
        try {
            num = IFUtil.addIfHistory("ERP_MRO_TOERPSXIF", data, IFUtil.TYPE_INPUT);
            if (data == null || data.isEmpty() || data.equals("")) {
                String msg = "E";
                obj.put("MSG", msg);
				obj.put("REASON", "送修单号不能为空");
                IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, obj.getString("REASON"));
                return obj.toString();

            }
            IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, reason);
        } catch (MroException e) {
            String msg = "E";
            obj.put("MSG", msg);
            reason = e.getMessage();
            obj.put("REASON", reason);
            return obj.toString();
        }
        try {
            IJpoSet transferSet = MroServer.getMroServer().getSysJpoSet("TRANSFER");
            MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultOrg("CRRC");
            MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultSite("ELEC");

            transferSet.setUserWhere("TRANSFERNUM='" + data + "'");
            transferSet.reset();
            if (transferSet != null && transferSet.count() > 0) {
                IJpo transfer = transferSet.getJpo(0);
				String TRANSFERNUM = transfer.getString("TRANSFERNUM");// 送修单号
                obj.put("TRANSFERNUM", TRANSFERNUM);
				String SENDORG = transfer.getString("SENDORG.DESCRIPTION");// 送修单位
                obj.put("SENDORG", SENDORG);
				String SXTYPE = transfer.getString("SXTYPE");// 修造类别
                if(SXTYPE.equalsIgnoreCase("GZ")){
					obj.put("SXTYPE", "故障修");
                }
                if(SXTYPE.equalsIgnoreCase("YXX")){
					obj.put("SXTYPE", "有效性检测");
                }
                if(SXTYPE.equalsIgnoreCase("DZX")){
					obj.put("SXTYPE", "大中修");
                }
                
				String DJH = transfer.getString("COURIERNUM");// 单据号
                obj.put("COURIERNUM", DJH);
				String CREATEBY = transfer.getString("CREATEBY");// 制单人//
																	// 2018-07-24修改
                obj.put("CREATEBY", CREATEBY);
				String CREATEBYDISPLAYNAME = transfer
						.getString("CREATEBY.DISPLAYNAME");// 制单人//
				// 2018-07-24修改
				obj.put("CREATEBYDISPLAYNAME", CREATEBYDISPLAYNAME);// 制单人姓名

				String reciveby = transfer.getString("reciveby");// 点收人
				String recivebydisplayname = transfer
						.getString("reciveby.displayname");// 点收人
                obj.put("RECIVEBY", reciveby);
                obj.put("RECIVEBYDISPLAYNAME", recivebydisplayname);
				Date CREATEDATE = transfer.getDate("CREATEDATE");// 制单日期
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// 2018-07-24修改
                String CREATEDATES = sdf.format(CREATEDATE);
				SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss");// 2018-07-24修改
                String CREATEDATESTIME = sdftime.format(CREATEDATE);
				obj.put("CREATEDATE", CREATEDATES);// 日期
				obj.put("CREATEDATESTIME", CREATEDATESTIME);// 时间

				Date recivedates = transfer.getDate("recivedate");// 制单日期
				/* 肖林宝修改判断空 */
                if(recivedates!=null){
                	String recivedate = sdf.format(recivedates);
                    String recivetime = sdftime.format(recivedates);
					obj.put("RECIVEDATE", recivedate);// 日期
					obj.put("RECIVETIME", recivetime);// 时间
                }else{
					obj.put("RECIVEDATE", "");// 日期
					obj.put("RECIVETIME", "");// 时间
                }
                
               

				String LISTTYPE = transfer.getString("LISTTYPE");// 订单类型
                obj.put("LISTTYPE", LISTTYPE);
				String REPAIRORG = transfer.getString("REPAIRORG.DESCRIPTION");// 承修单位
                obj.put("REPAIRORG", REPAIRORG);
				String RECEIVESTOREROOM = transfer
						.getString("RECEIVESTOREROOM");// 接收库房
                obj.put("RECEIVESTOREROOM", RECEIVESTOREROOM);
				// String AGENTBY = transfer.getString("AGENTBY");// 经办人
                // obj.put("AGENTBY", AGENTBY);
				String SENDSTOREROOM = transfer.getString("ISSUESTOREROOM");// 送修库房
                obj.put("SENDSTOREROOM", SENDSTOREROOM);
				Date SENDDATE = transfer.getDate("SENDDATE");// 送修日期
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");// 2018-07-24下午修改
                String SENDDATES;
                if (SENDDATE != null) {
                    SENDDATES = sdf1.format(SENDDATE);
                } else {
                    SENDDATES = "";
                }
                obj.put("SENDDATE", SENDDATES);
				String GYS = transfer.getString("COMPANY");// 供应商
                obj.put("COMPANY", GYS);
				String AGENTBY = transfer.getString("AGENTBY.displayname");// 联系人
                obj.put("CONTACTBY", AGENTBY);
				String CONTACTPHONE = transfer.getJpoSet("AGENTBY").getJpo()
						.getString("PRIMARYPHONE");// 联系电话
                obj.put("CONTACTPHONE", CONTACTPHONE);
				String STATUS = transfer.getString("STATUS");// 状态
                obj.put("STATUS", STATUS);
				Date TPLANREPAURDATES = transfer.getDate("PLANREPAURDATE");// 应修复日期
                if (TPLANREPAURDATES == null) {
                    obj.put("PLANREPAURDATE", "");
                } else {
                    obj.put("PLANREPAURDATE", sdf.format(TPLANREPAURDATES));
                }
                JSONArray array = new JSONArray();
                IJpoSet transferlineSet = transfer.getJpoSet("TRANSFERLINE");
                String lines;
                if (transferlineSet != null && transferlineSet.count() > 0) {
                    for (int i = 0; i < transferlineSet.count(); i++) {
                        JSONObject objs = new JSONObject();
                        IJpo transferline = transferlineSet.getJpo(i);
						String TRANSFERNUMS = transferline
								.getString("TRANSFERNUM");// 送修单号
                        objs.put("TRANSFERNUMS", TRANSFERNUMS);
						int TRANSFERLINENUM = transferline
								.getInt("TRANSFERLINENUM");// 送修单行号
                        objs.put("TRANSFERLINENUM", TRANSFERLINENUM);
						String WORKORDERNUM = transferline
								.getString("PROJECTINFO.WORKORDERNUM");// 项目工作令号
                        objs.put("WORKORDERNUM", WORKORDERNUM);
						String SCALENUM = transferline.getString("SCALENUM");// 销售订单号
                        objs.put("SCALENUM", SCALENUM);
						String SCALELINENUM = transferline
								.getString("SCALELINENUM");// 销售订单行
                        objs.put("SCALELINENUM", SCALELINENUM);
						String KHDM = transferline.getString("CUSTNUM");// 客户代码
                        objs.put("CUSTNUM", KHDM);
						String KHMC = transferline.getString("CUSTNUMNAME");// 客户名称
                        objs.put("CUSTNUMNAME", KHMC);
						String ITEMNUMDES = transferline
								.getString("ITEM.DESCRIPTION");// 物料描述
                        objs.put("ITEMNUMDES", ITEMNUMDES);
						String ITEMNUM = transferline.getString("ITEMNUM");// 物料编号
                        objs.put("ITEMNUM", ITEMNUM);
						String SQN = transferline.getString("SQN");// 序列号----
                        objs.put("SQN", SQN);
						String MODEL = transferline
								.getString("MODELS.MODELDESC");// 车型
                        objs.put("MODEL", MODEL);
						Double ORDERQTY = transferline.getDouble("ORDERQTY");// 数量//
                        objs.put("ORDERQTY", ORDERQTY);
						String JBDW = transferline
								.getString("ITEM.ORDERUNIT.DESCRIPTION");// 计算单位
                        objs.put("JLDW", JBDW);
						String PRODUCTTYPE = transferline
								.getString("PRODUCTTYPE");// 产品类型
                        objs.put("PRODUCTTYPE", PRODUCTTYPE);
						String FAULTMNGDES = transferline
								.getString("FAILUREDESC");// 故障描述
                        objs.put("FAULTMNGDES", FAULTMNGDES);
						String FAULTCONSEQ = transferline
								.getString("FAILURECONS");// 故障后果
                        objs.put("FAULTCONSEQ", FAULTCONSEQ);
						String QMSNUM = transferline.getString("QMSNUM");// QMS事件编号
                        objs.put("QMSNUM", QMSNUM);
						String HARDWAREMEMO = transferline
								.getString("HARDWAREMEMO");// 硬件要求--
                        objs.put("HARDWAREMEMO", HARDWAREMEMO);
						String SOFTWAREMEMO = transferline
								.getString("SOFTWAREMEMO");// 软件要求--
                        objs.put("SOFTWAREMEMO", SOFTWAREMEMO);
						String OTHERMEMO = transferline.getString("OTHERMEMO");// 其他特殊要求--
                        objs.put("OTHERMEMO", OTHERMEMO);
						String RECORDREQUIRED = transferline
								.getString("RECORDREQUIRED");// 记录要求
                        objs.put("RECORDREQUIRED", RECORDREQUIRED);
						String ISREQUIREDCERT = transferline
								.getString("ISREQUIREDCERT");// 合格证要求
                        objs.put("ISREQUIREDCERT", ISREQUIREDCERT);
						String PROJNUM = transferline.getString("PROJNUM");// 车型项目
                        objs.put("PROJNUM", PROJNUM);
						String softbb = transferline
								.getString("SOFTVERSIONNUM");// 软件版本号
                        objs.put("SOFTVERSIONNUM", softbb);
						String SOFTNUM = transferline.getString("SOFTNUM");// 软件编号
                        objs.put("SOFTNUM", SOFTNUM);
						String IMPORTLEVEL = transferline
								.getString("IMPORTLEVEL");// 紧急程度
                        objs.put("IMPORTLEVEL", IMPORTLEVEL);
						Date PLANREPAURDATES = transferline
								.getDate("PLANREPAURDATE");// 应修复日期
                        if (PLANREPAURDATES == null) {
                            objs.put("PLANREPAURDATE", "");
                        } else {
                            objs.put("PLANREPAURDATE", sdf.format(PLANREPAURDATES));
                        }
						String ISJSFX = transferline.getString("ISJSFX");// 是否需技术分析
                        objs.put("ISJSFX", ISJSFX);
						String TRANSNOTICENUM = transferline
								.getString("TRANSNOTICENUM");// 改造通知单号
                        objs.put("TRANSNOTICENUM", TRANSNOTICENUM);
						String TASKNUM = transferline.getString("TASKNUM");// 工单号
                        objs.put("TASKNUM", TASKNUM);
						Double RECEIVEQTY = transferline.getDouble("PASSQTY");// 合格数量
                        objs.put("PASSQTY", RECEIVEQTY);
						Double SCRAPQTY = transferline.getDouble("FAILQTY");// 报废数量
                        objs.put("FAILQTY", SCRAPQTY);
                        array.add(objs);
                    }
                    lines = array.toString();
                } else {
                    lines = "";
                }
                obj.put("LINES", lines);
                String msg = "S";
                obj.put("MSG", msg);
                reason = "";
                obj.put("REASON", reason);
                nums = IFUtil.addIfHistory("ERP_MRO_TOERPSXIF", obj.toString(), IFUtil.TYPE_OUTPUT);
                IFUtil.updateIfHistory(nums, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, reason);
                return obj.toString();
            } else {
                String msg = "E";
                obj.put("MSG", msg);
				reason = "送修单号不存在";
                obj.put("REASON", reason);
                IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, reason);
                return obj.toString();

            }

        } catch (Exception e) {
            try {
                nums = IFUtil.addIfHistory("ERP_MRO_TOERPSXIF", obj.toString(), IFUtil.TYPE_OUTPUT);
                IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, e.toString());
            } catch (MroException e1) {
                e1.printStackTrace();
            }
            String msg = "E";
            obj.put("MSG", msg);
            reason = e.toString();
            obj.put("REASON", reason);
            e.printStackTrace();
            return obj.toString();

        }
    }

    /**
	 * 送修单生成缴库单
	 * 
	 * @param zfunerptomrojklineset
	 * @return
	 */
    @Override
//    public InterfaceReponse Ztfun_ErptoMroJkd(ZfunErptoMroJkLineSet zfunerptomrojklineset) {
//        String num = "";
//        String nums = "";
//
//        String json = JSON.toJSONString(zfunerptomrojklineset);
//        try {
//            num = IFUtil.addIfHistory("ERP_MRO_WXXXIF", json, IFUtil.TYPE_INPUT);
//        } catch (MroException e1) {
//            e1.printStackTrace();
//        }
//        InterfaceReponse interfacereponse = new InterfaceReponse();
//        ZfunErptoMroJkLineSet objSet = zfunerptomrojklineset;
//        List<ZfunErptoMroResponses> ls = objSet.getLINE();
//        for (int i = 0; i < ls.size(); i++) {
//            ZfunErptoMroResponses obj = ls.get(i);
//            Double PASSQTY = obj.getPASSQTY();
//            Double FAILQTY = obj.getFAILQTY();
//            String TRANSFERNUM = obj.getTRANSFERNUMS();
//            String TRANSFERLINENUM = obj.getTRANSFERLINENUM();
//            String TRANSFERNUMS1 = "";
//            IJpoSet transferLineSet;
//            IJpoSet transferSet;
//            try {
//                transferSet = (TransferSet) MroServer.getMroServer().getSysJpoSet("TRANSFER");
//                transferSet.setUserWhere("1=2");
//                transferSet.reset();
//                
//                Transfer jpo = (Transfer) transferSet.addJpo(11L);
//                Calendar date = Calendar.getInstance();
//                String year = String.valueOf(date.get(Calendar.YEAR));
//                String type = "JKD";
//                IJpoSet locationsset = MroServer.getMroServer().getJpoSet("TRANSFER",
//                        MroServer.getMroServer().getSystemUserServer());
//                locationsset.setUserWhere("type='" + type + "'");
//                locationsset.reset();
//                
//                jpo.setValue("type", "JKD");
//                jpo.setValue("SENDNUM", TRANSFERNUM, 2L);
//                jpo.setValue("ORGID", "CRRC", 2L);
//                jpo.setValue("SITEID", "ELEC", 2L);
	// jpo.setValue("DESCRIPTION", TRANSFERNUM + " 缴库", 2L);
//                TRANSFERNUMS1 = jpo.getString("TRANSFERNUM");
//            
//                transferLineSet = MroServer.getMroServer().getSysJpoSet("TRANSFERLINE");
//                transferLineSet.setUserWhere("TRANSFERNUM='" + TRANSFERNUM + "' and TRANSFERLINENUM='"
//                        + TRANSFERLINENUM + "'");
//                transferLineSet.reset();
//                if (!transferLineSet.isEmpty()) {
//                    IJpo jpoline = transferLineSet.getJpo(0);
//                  
//                    IJpo jpos = jpoline.duplicate();
//                    jpos.setValue("TRANSFERNUM", TRANSFERNUMS1);
//                 
//                    jpos.setValue("YJSQTY", 0);
	// jpos.setValue("status", "未接收");
//                    jpos.setValue("PASSQTY", PASSQTY);
//                    jpos.setValue("FAILQTY", FAILQTY);
//                } else {
	// interfacereponse.setMESSAGE("送修单明细有误，请核查后输入");
//                    interfacereponse.setRETURN("E");
//                    IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
//                            JSON.toJSONString(interfacereponse));
//                    nums = IFUtil.addIfHistory("ERP_MRO_WXXXIF", JSON.toJSONString(interfacereponse),
//                            IFUtil.TYPE_OUTPUT);
//
//                    IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
//                            JSON.toJSONString(interfacereponse));
//                    return interfacereponse;
//
//                }
//                transferSet.save();
//                transferLineSet.save();
//                interfacereponse.setRETURN("S");
//                nums = IFUtil.addIfHistory("ERP_MRO_WXXXIF", JSON.toJSONString(interfacereponse), IFUtil.TYPE_OUTPUT);
//                IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "");
//                IFUtil.updateIfHistory(nums, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "");
//            } catch (MroException e) {
//                // e.printStackTrace();
//                interfacereponse.setMESSAGE(e.getMessage());
//                interfacereponse.setRETURN("E");
//                JSON.toJSONString(interfacereponse);
//                try {
//                    IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
//                            JSON.toJSONString(interfacereponse));
//                    nums = IFUtil.addIfHistory("ERP_MRO_WXXXIF", JSON.toJSONString(interfacereponse),
//                            IFUtil.TYPE_OUTPUT);
//                    IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
//                            JSON.toJSONString(interfacereponse));
//                } catch (MroException e1) {
//                    e1.printStackTrace();
//                }
//
//            }
//
//        }
//        return interfacereponse;
//    }
    public InterfaceReponse Ztfun_ErptoMroJkd(ZfunErptoMroJkLineSet zfunerptomrojklineset) {
        String num = "";
        String nums = "";
        Date date1 = MroServer.getMroServer().getDate();
        String json = JSON.toJSONString(zfunerptomrojklineset);
        try {
            num = IFUtil.addIfHistory("ERP_MRO_WXXXIF", json, IFUtil.TYPE_INPUT);
        } catch (MroException e1) {
            e1.printStackTrace();
        }
        InterfaceReponse interfacereponse = new InterfaceReponse();
        ZfunErptoMroJkLineSet objSet = zfunerptomrojklineset;
        List<ZfunErptoMroResponses> ls = objSet.getLINE();
        for (int i = 0; i < ls.size(); i++) {
            ZfunErptoMroResponses obj = ls.get(i);
            Double PASSQTY = obj.getPASSQTY();
            Double FAILQTY = obj.getFAILQTY();
            String ERPJKDNUM=obj.getERPJKDNUM();
            String NEWITEMNUM=obj.getNEWITEMNUM();
            if(NEWITEMNUM==null){
            	NEWITEMNUM="";
            }
            String NEWSQN=obj.getNEWSQN();
            if(NEWSQN==null){
            	NEWSQN="";
            }
            String TRANSFERNUM = obj.getTRANSFERNUMS();
            String TRANSFERLINENUM = obj.getTRANSFERLINENUM();
            String TRANSFERNUMS1 = "";
            IJpoSet transferLineSet;
            IJpoSet jkdtransferLineSet;
            IJpoSet transferSet;
            try {
                transferSet = (TransferSet) MroServer.getMroServer().getSysJpoSet("TRANSFER");
                transferSet.setUserWhere("SENDNUM='"+TRANSFERNUM+"'");
                transferSet.reset();
                if(transferSet.isEmpty()){
                	Transfer jpo = (Transfer) transferSet.addJpo(11L);
                    Calendar date = Calendar.getInstance();
                    String year = String.valueOf(date.get(Calendar.YEAR));
                    String type = "JKD";
                    IJpoSet locationsset = MroServer.getMroServer().getJpoSet("TRANSFER",
                            MroServer.getMroServer().getSystemUserServer());
                    locationsset.setUserWhere("type='" + type + "'");
                    locationsset.reset();
                    
                    jpo.setValue("type", "JKD");
                    jpo.setValue("SENDNUM", TRANSFERNUM, 2L);
                    jpo.setValue("ORGID", "CRRC", 2L);
                    jpo.setValue("SITEID", "ELEC", 2L);
					jpo.setValue("DESCRIPTION", TRANSFERNUM + " 缴库", 2L);
                    TRANSFERNUMS1 = jpo.getString("TRANSFERNUM");
                }else{
                	TRANSFERNUMS1=transferSet.getJpo(0).getString("TRANSFERNUM");
                }
//                Transfer jpo = (Transfer) transferSet.addJpo(11L);
//                Calendar date = Calendar.getInstance();
//                String year = String.valueOf(date.get(Calendar.YEAR));
//                String type = "JKD";
//                IJpoSet locationsset = MroServer.getMroServer().getJpoSet("TRANSFER",
//                        MroServer.getMroServer().getSystemUserServer());
//                locationsset.setUserWhere("type='" + type + "'");
//                locationsset.reset();
//                
//                jpo.setValue("type", "JKD");
//                jpo.setValue("SENDNUM", TRANSFERNUM, 2L);
//                jpo.setValue("ORGID", "CRRC", 2L);
//                jpo.setValue("SITEID", "ELEC", 2L);
				// jpo.setValue("DESCRIPTION", TRANSFERNUM + " 缴库", 2L);
//                TRANSFERNUMS1 = jpo.getString("TRANSFERNUM");
            
                transferLineSet = MroServer.getMroServer().getSysJpoSet("TRANSFERLINE");
                transferLineSet.setUserWhere("TRANSFERNUM='" + TRANSFERNUM + "' and TRANSFERLINENUM='"
                        + TRANSFERLINENUM + "'");
                transferLineSet.reset();
                if (!transferLineSet.isEmpty()) {
                	jkdtransferLineSet = MroServer.getMroServer().getSysJpoSet("TRANSFERLINE");
                	jkdtransferLineSet.setUserWhere("transfernum='"+TRANSFERNUMS1+"' and TRANSFERLINENUM='"+TRANSFERLINENUM+"'");
                	if(jkdtransferLineSet.isEmpty()){
                		IJpo jpoline = transferLineSet.getJpo(0);
                        String DEALTYPE=jpoline.getString("DEALTYPE");
                        IJpo jpos = jpoline.duplicate();
                        if(DEALTYPE.isEmpty()){
                        	jpos.setValue("DEALTYPE", "退库");
                        }
                        jpos.setValue("TRANSFERNUM", TRANSFERNUMS1);
                     
                        jpos.setValue("YJSQTY", 0);
    					jpos.setValue("status", "未接收");
                        jpos.setValue("ERPJKDNUM", ERPJKDNUM);
                        if(!NEWITEMNUM.isEmpty()){
                        	jpos.setValue("NEWITEMNUM", NEWITEMNUM);
                        	if(!NEWSQN.isEmpty()){
                        		 IJpoSet  itemset = MroServer.getMroServer().getSysJpoSet("sys_item");
                        		 itemset.setUserWhere("itemnum='"+NEWITEMNUM+"'");
                        		 if(itemset.isEmpty()){
//                        			 throw new MroException("返修图号在MRO系统不存在，请在MRO系统物料管理中补充：'"+NEWITEMNUM+"'信息");
                        			 interfacereponse.setMESSAGE("返修图号在MRO系统不存在，请在MRO系统物料管理中补充：'"+NEWITEMNUM+"'信息");
                                     interfacereponse.setRETURN("E");
                                     IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
                                             JSON.toJSONString(interfacereponse));
                                     nums = IFUtil.addIfHistory("ERP_MRO_WXXXIF", JSON.toJSONString(interfacereponse),
                                             IFUtil.TYPE_OUTPUT);

                                     IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
                                             JSON.toJSONString(interfacereponse));
                                     return interfacereponse;
                        		 }else{
                        			 String type = ItemUtil.getItemInfo(NEWITEMNUM);
                        			 if (ItemUtil.SQN_ITEM.equals(type)) {
                                     	 jpos.setValue("NEWSQN", NEWSQN);
                                     }else if (ItemUtil.LOT_I_ITEM.equals(type)) {
                                     	jpos.setValue("NEWLOTNUM", NEWSQN);
                                     }	 
                        		 }
                        	}else{
                        		 jpos.setValue("NEWSQN", NEWSQN);
                        		 jpos.setValue("NEWLOTNUM", NEWSQN);
                        	}
                           
                        }else{
                        	jpos.setValue("NEWITEMNUM", jpos.getString("itemnum"));
                        	if(!NEWSQN.isEmpty()){
                       		 String type = ItemUtil.getItemInfo(jpos.getString("itemnum"));
                                if (ItemUtil.SQN_ITEM.equals(type)) {
                                	 jpos.setValue("NEWSQN", NEWSQN);
                                }else if (ItemUtil.LOT_I_ITEM.equals(type)) {
                                	jpos.setValue("NEWLOTNUM", NEWSQN);
                                }		
                       	}else{
                       		 jpos.setValue("NEWSQN", jpos.getString("sqn"));
                       		 jpos.setValue("NEWLOTNUM", jpos.getString("lotnum"));
                       		}
                        }
                        jpos.setValue("PASSQTY", PASSQTY);
                        jpos.setValue("FAILQTY", FAILQTY);
                        jpos.setValue("transdate", date1);
                	}
                } else {
					interfacereponse.setMESSAGE("送修单明细有误，请核查后输入");
                    interfacereponse.setRETURN("E");
                    IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
                            JSON.toJSONString(interfacereponse));
                    nums = IFUtil.addIfHistory("ERP_MRO_WXXXIF", JSON.toJSONString(interfacereponse),
                            IFUtil.TYPE_OUTPUT);

                    IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
                            JSON.toJSONString(interfacereponse));
                    return interfacereponse;

                }
                transferSet.save();
                transferLineSet.save();
                interfacereponse.setRETURN("S");
                nums = IFUtil.addIfHistory("ERP_MRO_WXXXIF", JSON.toJSONString(interfacereponse), IFUtil.TYPE_OUTPUT);
                IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "");
                IFUtil.updateIfHistory(nums, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "");
            } catch (Exception e) {
                // e.printStackTrace();
                interfacereponse.setMESSAGE(e.getMessage());
                interfacereponse.setRETURN("E");
                JSON.toJSONString(interfacereponse);
                try {
                    IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
                            JSON.toJSONString(interfacereponse));
                    nums = IFUtil.addIfHistory("ERP_MRO_WXXXIF", JSON.toJSONString(interfacereponse),
                            IFUtil.TYPE_OUTPUT);
                    IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
                            JSON.toJSONString(interfacereponse));
                } catch (MroException e1) {
                    e1.printStackTrace();
                }

            }

        }
        return interfacereponse;
    }
    
	/**
	 * MES或ERP系统向MRO推送生产完工的产品序列号信息
	 */
    @SuppressWarnings("finally")
	@Override
    public InfoBack Ztfun_ErptoMroSqn(SqnInfoSet sqninfoSet) {

        InfoBack infoback = new InfoBack();
        String num = "";
        try {
            if (sqninfoSet != null && sqninfoSet.getSqnInfoList() != null && sqninfoSet.getSqnInfoList().size() > 0) {
                String json = JSON.toJSONString(sqninfoSet);
                num = IFUtil.addIfHistory(IFUtil.ERP_MRO_ERP_MRO_SQNIFNO, json, IFUtil.TYPE_INPUT);
				// 新增数据
                MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultOrg("CRRC");
                MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultSite("ELEC");
                IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET", "1=2");
                for (int index = 0; index < sqninfoSet.getSqnInfoList().size(); index++) {
                    SqnInfo sqninfo = sqninfoSet.getSqnInfoList().get(index);
                    
					// 判断产品序列号是否存在，如果已经存在，则更新信息
                    IJpoSet sqnJposet = MroServer.getMroServer().getSysJpoSet("ASSET", "SQN='"+sqninfo.getSqn()+"'");
                    if(sqnJposet != null && sqnJposet.count() > 0){
                        IJpo jpo = sqnJposet.getJpo(0);
                        
						MsgUtil.addMsg("SSCONFIG", jpo.getLong("ASSETID"),
								MsgUtil.SQNMSG,
								"MES推送的产品序列号:" + jpo.getString("SQN")
										+ " 在系统中已经存在", MsgUtil.PZROLE);

                        
                        jpo.setValue("factorydesc", sqninfo.getFactorydesc(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("factory", sqninfo.getFactory(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("description", sqninfo.getItemdesc(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("productionordernum", sqninfo.getProductionordernum(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("addDate", sqninfo.getAddDate(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("erploc", sqninfo.getLoc(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("addDate", sqninfo.getAddDate(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("MESDATE",  MroServer.getMroServer().getDate(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        sqnJposet.save();
                        
                    }else{
                        
                        IJpo jpo = jposet.addJpo();
                        jpo.setValue("type", 0);
                        jpo.setValue("factorydesc", sqninfo.getFactorydesc(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("factory", sqninfo.getFactory(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("description", sqninfo.getItemdesc(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("itemnum", sqninfo.getItemnum(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("productionordernum", sqninfo.getProductionordernum(),
                                GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("addDate", sqninfo.getAddDate(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("erploc", sqninfo.getLoc(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("sqn", sqninfo.getSqn(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("assetlevel", "ASSET", GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("ancestor", jpo.getString("assetnum"), GWConstant.P_NOVALIDATION_AND_NOACTION);
                        jpo.setValue("STATUS", SddqConstant.NO_UP_CAR_STATUS, GWConstant.P_NOVALIDATION_AND_NOACTION);
						jpo.setValue("FROMSOURCE", SddqConstant.FROMSOURCE_MES,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 在库存中-新品
                        jpo.setValue("MESDATE",  MroServer.getMroServer().getDate(), GWConstant.P_NOVALIDATION_AND_NOACTION);
                    }
                }
                jposet.save();
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "新增数据:"
								+ sqninfoSet.getSqnInfoList().size() + "条记录");
            } else {
                IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, SddqConstant.ISNULL);
                infoback.setMsg(SddqConstant.ISNULL);
            }
        } catch (Exception e) {
            try {
                IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, e.getMessage());
                infoback.setMsg(e.getMessage());
            } catch (MroException e1) {
                e1.printStackTrace();
            }
        }finally{
            return infoback;
        }
    }
    /*ERP推送调拨转库单*/
    public InterfaceReponse Ztfun_ErptoMroDbzkd(ZfunErptoMroDbzkLineSet zfunerptomrodbzklineset) {
        String num = "";
        String nums = "";
        Date date1 = MroServer.getMroServer().getDate();
        String json = JSON.toJSONString(zfunerptomrodbzklineset);
        try {
            num = IFUtil.addIfHistory("ERP_MRO_DBZKD", json, IFUtil.TYPE_INPUT);
        } catch (MroException e1) {
            e1.printStackTrace();
        }
        InterfaceReponse interfacereponse = new InterfaceReponse();
        ZfunErptoMroDbzkLineSet objSet = zfunerptomrodbzklineset;
        List<ZfunErptoMroDbzkResponses> ls = objSet.getLINE();
        boolean flg=false;
        for (int i = 0; i < ls.size(); i++) {
        	ZfunErptoMroDbzkResponses obj = ls.get(i);
        	String IHREZ=obj.getIHREZ();//工作令号
        	if(IHREZ==null){
        		IHREZ="";
        	}
        	String HOUSENUM1=obj.getHOUSENUM1();//门牌号（MRO库房代码）
        	if(HOUSENUM1==null){
        		HOUSENUM1="";
        	}else{
        		HOUSENUM1=HOUSENUM1.toUpperCase();
        	}
        	String VBELN=obj.getVBELN();// 交货单号
        	if(VBELN==null){
        		VBELN="";
        	}
        	String WERKS=obj.getWERKS();// 工厂
        	if(WERKS==null){
        		WERKS="";
        	}
            try {
            	 	if(WERKS.equalsIgnoreCase("1020")){
            	 		if (!IHREZ.isEmpty()) {
                        	IJpoSet proset = MroServer.getMroServer().getJpoSet("projectinfo", MroServer.getMroServer().getSystemUserServer());
        					proset.setUserWhere("WORKORDERNUM='"+IHREZ+"'");
        					proset.reset();
        					if(!proset.isEmpty()){
        						String location = proset.getJpo(0).getString("location");
        						if(location.isEmpty()){
								interfacereponse.setMESSAGE("工作令号:'" + IHREZ
										+ "',在MRO系统中未关联库房，请维护！");
        		                    interfacereponse.setRETURN("E");
        		                    IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
        		                            JSON.toJSONString(interfacereponse));
        		                    nums = IFUtil.addIfHistory("ERP_MRO_DBZKD", JSON.toJSONString(interfacereponse),
        		                            IFUtil.TYPE_OUTPUT);

        		                    IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
        		                            JSON.toJSONString(interfacereponse));
        		                    flg=false;
        		                    return interfacereponse;
        						}else{
        							flg=true;
        						}
        						
        					}else{
							interfacereponse.setMESSAGE("工作令号:'" + IHREZ
									+ "',在MRO系统中不存在，请维护！");
        	                    interfacereponse.setRETURN("E");
        	                    IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
        	                            JSON.toJSONString(interfacereponse));
        	                    nums = IFUtil.addIfHistory("ERP_MRO_DBZKD", JSON.toJSONString(interfacereponse),
        	                            IFUtil.TYPE_OUTPUT);

        	                    IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
        	                            JSON.toJSONString(interfacereponse));
        	                    flg=false;
        	                    return interfacereponse;
        					}
                        }else{
						interfacereponse.setMESSAGE("工作令号为空，请补充！");
    	                    interfacereponse.setRETURN("E");
    	                    IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
    	                            JSON.toJSONString(interfacereponse));
    	                    nums = IFUtil.addIfHistory("ERP_MRO_DBZKD", JSON.toJSONString(interfacereponse),
    	                            IFUtil.TYPE_OUTPUT);

    	                    IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
    	                            JSON.toJSONString(interfacereponse));
    	                    flg=false;
    	                    return interfacereponse;
                        }
            	 	}
            	 	else if(WERKS.equalsIgnoreCase("1030")){
            	 		if(HOUSENUM1.isEmpty()){
						interfacereponse.setMESSAGE("ERP门牌号为空，请检查！");
    	                    interfacereponse.setRETURN("E");
    	                    IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
    	                            JSON.toJSONString(interfacereponse));
    	                    nums = IFUtil.addIfHistory("ERP_MRO_DBZKD", JSON.toJSONString(interfacereponse),
    	                            IFUtil.TYPE_OUTPUT);

    	                    IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
    	                            JSON.toJSONString(interfacereponse));
    	                    flg=false;
    	                    return interfacereponse;
                    	}else{
                    		IJpoSet xlocationset = MroServer.getMroServer().getJpoSet("locations", MroServer.getMroServer().getSystemUserServer());
                    		xlocationset.setUserWhere("location='"+HOUSENUM1+"'");
                    		xlocationset.reset();
                    		if(HOUSENUM1.isEmpty()){
							interfacereponse
									.setMESSAGE("ERP门牌号所输入库房在MRO不存在，请检查！");
        	                    interfacereponse.setRETURN("E");
        	                    IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
        	                            JSON.toJSONString(interfacereponse));
        	                    nums = IFUtil.addIfHistory("ERP_MRO_DBZKD", JSON.toJSONString(interfacereponse),
        	                            IFUtil.TYPE_OUTPUT);

        	                    IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
        	                            JSON.toJSONString(interfacereponse));
        	                    flg=false;
        	                    return interfacereponse;
                    		}else{
                    			flg=true;
                    		}
                    		
                    	}
            	 	}else{
					interfacereponse.setMESSAGE("工厂请输入1020或1030");
	                    interfacereponse.setRETURN("E");
	                    IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
	                            JSON.toJSONString(interfacereponse));
	                    nums = IFUtil.addIfHistory("ERP_MRO_DBZKD", JSON.toJSONString(interfacereponse),
	                            IFUtil.TYPE_OUTPUT);

	                    IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
	                            JSON.toJSONString(interfacereponse));
	                    flg=false;
	                    return interfacereponse;
            	 	}
                interfacereponse.setRETURN("S");
                nums = IFUtil.addIfHistory("ERP_MRO_DBZKD", JSON.toJSONString(interfacereponse), IFUtil.TYPE_OUTPUT);
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "交货单号：'" + VBELN + "'");
				IFUtil.updateIfHistory(nums, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "交货单号：'" + VBELN + "'");
            } catch (MroException e) {
                // e.printStackTrace();
            	flg=false;
                interfacereponse.setMESSAGE(e.getMessage());
                interfacereponse.setRETURN("E");
                JSON.toJSONString(interfacereponse);
                try {
                    IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
                            JSON.toJSONString(interfacereponse));
                    nums = IFUtil.addIfHistory("ERP_MRO_DBZKD", JSON.toJSONString(interfacereponse),
                            IFUtil.TYPE_OUTPUT);
                    IFUtil.updateIfHistory(nums, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
                            JSON.toJSONString(interfacereponse));
                } catch (MroException e1) {
                	flg=false;
                    e1.printStackTrace();
                }

            }

        }
        if(flg){
        	for (int i = 0; i < ls.size(); i++) {
        		ZfunErptoMroDbzkResponses obj = ls.get(i);
				String ERPLFDAT = obj.getLFDAT();// 交货日期20181214
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// 
            	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");// 

        		String LFDAT = null;
				try {
					LFDAT = sdf2.format(sdf.parse(ERPLFDAT));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String VKORG = obj.getVKORG();// 销售组织
				String VBELN = obj.getVBELN();// 交货单号
				String POSNR = obj.getPOSNR();// 交货单行号
				String PSTYV = obj.getPSTYV();// 交货项目类别
				String MATNR=obj.getMATNR();// 物料号
            	if(MATNR==null){
            		MATNR="";
            	}
            	String MAKTX=obj.getMAKTX();// 物料描述
            	double LFIMG=obj.getLFIMG();// 数量
            	String VRKME=obj.getVRKME();// 单位
            	String WERKS=obj.getWERKS();// 工厂
            	if(WERKS==null){
            		WERKS="";
            	}
            	String LGORT=obj.getLGORT();// 库存地点
            	String BWART=obj.getBWART();// 移动类型
            	String VGBEL=obj.getVGBEL();// 工厂间转储单号
            	String VGPOS=obj.getVGPOS();// 转储单行
            	String NAME1=obj.getNAME1();// 收货单位
            	String IHREZ=obj.getIHREZ();//工作令号
            	if(IHREZ==null){
            		IHREZ="";
            	}
            	String HOUSENUM1=obj.getHOUSENUM1();//门牌号（MRO库房代码）
            	if(HOUSENUM1==null){
            		HOUSENUM1="";
            	}else{
            		HOUSENUM1=HOUSENUM1.toUpperCase();
            	}
            	 try {
            		IJpoSet SAVECONVERTLOCLINESet=MroServer.getMroServer().getSysJpoSet("CONVERTLOCLINE");
            		SAVECONVERTLOCLINESet.setUserWhere("CONVERTLOCNUM='"+VBELN+"' and POSNR='"+POSNR+"'");
            		if(SAVECONVERTLOCLINESet.isEmpty()){
            			String location="";
                		String CREATEBY="";
                		String description="";
                		if(WERKS.equalsIgnoreCase("1020")){
                			if(!IHREZ.isEmpty()){
        						IJpoSet proset = MroServer.getMroServer().getJpoSet("projectinfo", MroServer.getMroServer().getSystemUserServer());
        						proset.setUserWhere("WORKORDERNUM='"+IHREZ+"'");
        						proset.reset();
        						if(!proset.isEmpty()){
        							location = proset.getJpo(0).getString("location");
            						String desc = proset.getJpo(0).getString("PRJCATEGORY");
            						description=desc.substring(0, 4);
            						IJpoSet locationset = MroServer.getMroServer().getJpoSet("locations", MroServer.getMroServer().getSystemUserServer());
            						locationset.setUserWhere("location='"+location+"'");
            						locationset.reset();
            						if(!locationset.isEmpty()){
            							CREATEBY=locationset.getJpo(0).getString("keeper");
            						}
        						}
        					}
                		}
                		if(WERKS.equalsIgnoreCase("1030")){
                			if(!HOUSENUM1.isEmpty()){
        						location=HOUSENUM1;
        						IJpoSet locationset = MroServer.getMroServer().getJpoSet("locations", MroServer.getMroServer().getSystemUserServer());
        						locationset.setUserWhere("location='"+HOUSENUM1+"'");
        						locationset.reset();
        						if(!locationset.isEmpty()){
        							CREATEBY=locationset.getJpo(0).getString("keeper");
            						description=locationset.getJpo(0).getString("description");
        						}
        					}
                		}
    					IJpoSet CONVERTLOCSet=MroServer.getMroServer().getSysJpoSet("CONVERTLOC");
    					CONVERTLOCSet.setUserWhere("CONVERTLOCNUM='"+VBELN+"'");
    					CONVERTLOCSet.reset();
    					if(CONVERTLOCSet.isEmpty()){
    						IJpo CONVERTLOC=CONVERTLOCSet.addJpo();
    						String CONVERTLOCid=CONVERTLOC.getString("CONVERTLOCid");
    						CONVERTLOC.setValue("CONVERTLOCNUM", VBELN,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						CONVERTLOC.setValue("DESCRIPTION", description,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						CONVERTLOC.setValue("CREATEBY", CREATEBY,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						CONVERTLOC.setValue("CREATETIME", date1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						CONVERTLOC.setValue("STATUS", "未接收",
    								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						CONVERTLOC.setValue("isiface", "1",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						CONVERTLOCSet.save();
    						// 启动计划工作流
    						IJpoSet workCONVERTLOCset = MroServer.getMroServer().getSysJpoSet(
    								"CONVERTLOC", "CONVERTLOCid='" + CONVERTLOCid + "'");
    						if (workCONVERTLOCset != null && workCONVERTLOCset.count() > 0) {
    							IJpo workCONVERTLOC = workCONVERTLOCset.getJpo(0);
    							WfControlUtil.startwf(workCONVERTLOC, "CONVERTLOC");
    						}
    					}
    					IJpoSet CONVERTLOCLINESet=MroServer.getMroServer().getSysJpoSet("CONVERTLOCLINE");
    					String itemtype = ItemUtil.getItemInfo(MATNR);
    					if (ItemUtil.SQN_ITEM.equals(itemtype)) {
    						if (LFIMG > 0) {
    							int forcount = (int) LFIMG;
    							for (int j = 0; j < forcount; j++) {
    								IJpo jpo = CONVERTLOCLINESet.addJpo();
    								jpo.setValue("CONVERTLOCNUM", VBELN, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("ITEMNUM", MATNR, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("QTY", 1, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("ORDERUNIT", VRKME, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);								
    								jpo.setValue("LFDAT", LFDAT, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("VKORG", VKORG, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("description", VBELN, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("POSNR", POSNR, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("PSTYV", PSTYV, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("ITEMDESC", MAKTX, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("WERKS", WERKS, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("BWART", BWART, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("VGBEL", VGBEL, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("VGPOS", VGPOS, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("NAME1", NAME1, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("PROJNUM", IHREZ,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("LOCATION", location, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("YJSQTY", 0,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("STATUS", "未接收",
    										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    							}
    							CONVERTLOCLINESet.save();
    						}
    					} else if (ItemUtil.LOT_I_ITEM.equals(itemtype)) {
    						if (LFIMG > 0) {
    							int forcount = (int) LFIMG;
    							for (int j = 0; j < forcount; j++) {
    								IJpo jpo = CONVERTLOCLINESet.addJpo();
    								jpo.setValue("CONVERTLOCNUM", VBELN, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("ITEMNUM", MATNR, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("QTY", 1, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("ORDERUNIT", VRKME, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);								
    								jpo.setValue("LFDAT", LFDAT, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("VKORG", VKORG, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("description", VBELN, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("POSNR", POSNR, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("PSTYV", PSTYV, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("ITEMDESC", MAKTX, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("WERKS", WERKS, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("BWART", BWART, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("VGBEL", VGBEL, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("VGPOS", VGPOS, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("NAME1", NAME1, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("PROJNUM", IHREZ,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("LOCATION", location, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("YJSQTY", 0,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    								jpo.setValue("STATUS", "未接收", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    							}
    							CONVERTLOCLINESet.save();
    						}
    					}else {
    						IJpo jpo = CONVERTLOCLINESet.addJpo();
    						jpo.setValue("CONVERTLOCNUM", VBELN, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("ITEMNUM", MATNR, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("QTY", LFIMG, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("ORDERUNIT", VRKME, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("LFDAT", LFDAT, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("VKORG", VKORG, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("description", VBELN, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("POSNR", POSNR, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("PSTYV", PSTYV, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("ITEMDESC", MAKTX, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("WERKS", WERKS, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("BWART", BWART, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("VGBEL", VGBEL, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("VGPOS", VGPOS, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("NAME1", NAME1, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("LOCATION", location, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("PROJNUM", IHREZ,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("YJSQTY", 0,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						jpo.setValue("STATUS", "未接收",
    								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    						CONVERTLOCLINESet.save();
    					}
            		}
            		
				} catch (MroException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
        return interfacereponse;
    }
}
