package com.glaway.sddq.overhaul.taskorder.bean;

import io.netty.util.internal.StringUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl.Authenticator;
import org.apache.commons.httpclient.util.DateUtil;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.MroServer;


/*正式接口调用*/
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.Char1;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.Char12;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.Char200;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.Char4;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.Char6;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.Date;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.Numeric8;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.Quantum133;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.Unit3;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.ZprodordconfOpt;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.ZtfunPpOrdConfirmOpt;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.jxgxbg.ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub.ZtfunPpOrdConfirmOptResponse;
/*正式接口调用*/
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * @ClassName JxTaskItemDataBean
 * @Description 检修工序DataBean
 * @author public2175
 * @Date 2018-7-31 下午8:02:13
 * @version 1.0.0
 */
public class JxTaskItemDataBean extends DataBean {
    
    /**
     * @Description 取消报工
     * @throws MroException
     */
    public void unreportWork() throws MroException {
        
        String reason = this.getJpo().getString("REASON");
        if(SddqConstant.JX_GX_STATUS_NO.equals(this.getString("STATUS"))){
            throw new AppException("jxtaskorder","noJxgxbg");
        }
        if(StringUtil.isNullOrEmpty(reason)){
            throw new AppException("jxtaskorder","unreportWork");
        }
        reportWorkToErp(SddqConstant.JX_GX_BG_NO,reason);
    }
    
    /**
     * @Description 报工
     * @throws MroException
     */
    public void reportWork() throws MroException {
        IJpo parent = this.getParent().getJpo();

        // IJpo parentJx = parent.getParent().getString("STATUS");
        String status = parent.getParent().getString("STATUS");
        if (SddqConstant.JX_STATUS_CG.equals(status)) {
            throw new MroException("jxtaskorder","dispatch");
        } else if (SddqConstant.JX_STATUS_WC.equals(status)) {
            throw new MroException("jxtaskorder","haswg");
        }else{
            reportWorkToErp(SddqConstant.JX_GX_BG_YES, "");
        }

    }
    
    /**
     * @throws MroException
     * @Description 向ERP报工
     */
    public void reportWorkToErp(String type,String reason) throws MroException {
        String num = "";
        try {
            JSONObject outData = new JSONObject();
            // 获取ERP接口所需字段
            String sn = this.getJpo().getString("SN");// 序列
            String seq = this.getJpo().getString("SEQ");// 序号
            String factory = this.getAppBean().getJpo().getString("FACTORY");// 工厂
            String productionordernum = this.getAppBean().getJpo().getString("productionordernum");// ERP生产订单编号
            String itemnum = this.getAppBean().getJpo().getString("JXCODE");
            String personId = this.getJpo().getString("NEWSPAPERWORKER");
            String personid=this.getUserInfo().getPersonId();
            String unit = "EA";
            String manualwork = this.getJpo().getString("manualwork");// 人工作业
            String machinework = this.getJpo().getString("machinework");// 机器动力作业
            String indirectcostwork = this.getJpo().getString("indirectcostwork");// 间接费用作业
            
            if(StringUtil.isNullOrEmpty(manualwork)){
                throw new AppException("jxtaskorder","manualworkisnull");
            }
            if(StringUtil.isNullOrEmpty(machinework)){
                throw new AppException("jxtaskorder","machineworkisnull");
            }
            if(StringUtil.isNullOrEmpty(indirectcostwork)){
                throw new AppException("jxtaskorder","indirectcostworkisnull");
            }
            if (SddqConstant.JX_GX_STATUS_YES.equals(this.getString("STATUS"))){
				throw new AppException("jxtaskorder", "statusyes");
			}
            outData.put("werks", factory);
            outData.put("sn", sn);
            outData.put("seq", seq);
            outData.put("productionordernum", productionordernum);
            outData.put("amount", 1);
            if(StringUtil.isNullOrEmpty(personId)){
            	outData.put("personId", personid);
            }else{
            	outData.put("personId", personId);
            }           
            String date = DateUtil.formatDate(MroServer.getMroServer().getDate(),"yyyy-MM-dd");
            outData.put("date", date);
            // 获取物料的单位
            IJpo item = ItemUtil.getItem(itemnum);
            if(item != null){
                unit = item.getString("ORDERUNIT");
            }
            outData.put("unit", unit);

            // 增加接口输出记录
            num = IFUtil.addIfHistory(IFUtil.MRO_ERP_GXBG, outData.toString(), IFUtil.TYPE_OUTPUT);// 增加输出记录
            
            // 调用ERP接口
            ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub service = new ComZzsErpZTFUN_PP_ORD_CONFIRM_OPTStub();
//            ZTFUN_PP_ORD_CONFIRM_OPTStub service = new ZTFUN_PP_ORD_CONFIRM_OPTStub();
            // 认证代码 start
            Authenticator auth = new Authenticator();
            String user = IFUtil.getIfServiceInfo("erp.user");
            String pwd = IFUtil.getIfServiceInfo("erp.pwd");
            auth.setUsername(user);
            auth.setPassword(pwd);
            service._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE,
            auth);
            // 认证代码 end
            ZtfunPpOrdConfirmOpt ztfunPpOrdConfirmOpt = new ZtfunPpOrdConfirmOpt();
            // 设置参数
            // 工厂
            Char4 werks = new Char4();
            werks.setChar4(factory);
            ztfunPpOrdConfirmOpt.setWerks(werks);
            // 报工or取消报工
            Char1 otype = new Char1();
            otype.setChar1(type);
            ztfunPpOrdConfirmOpt.setOtype(otype);
            
            Char200 ctext = new Char200();
            ctext.setChar200(reason);
            ztfunPpOrdConfirmOpt.setCtext(ctext);
            
            ZprodordconfOpt localProdordconfOpt = new ZprodordconfOpt();
            Char12 aufnr = new Char12();// ERP生产订单号
            aufnr.setChar12(productionordernum);
            localProdordconfOpt.setAufnr(aufnr);
            
            Char6 aplfl = new Char6();// 序列
            aplfl.setChar6(sn);
            localProdordconfOpt.setAplfl(aplfl);
            
            Char4 vornr = new Char4();// 序号
            vornr.setChar4(seq);
            localProdordconfOpt.setVornr(vornr);
            
            Quantum133 lmngr = new Quantum133();// 合格数量（经确认，不考虑报废的情况）
            BigDecimal amount = new BigDecimal(1);
            lmngr.setQuantum133(amount);
            localProdordconfOpt.setLmnga(lmngr);
            
            Unit3 meinh = new Unit3();// 物料单位
            meinh.setUnit3(unit);
            localProdordconfOpt.setMeinh(meinh);
            
            Numeric8 localPernr = new Numeric8();// 报工人
            if(StringUtil.isNullOrEmpty(personId)){
            	localPernr.setNumeric8(personid);
                }else{
            localPernr.setNumeric8(personId);
                }
            localProdordconfOpt.setPernr(localPernr);
            
            Date localBudat = new Date();// 报工日期
            localBudat.setDate(date);
            localProdordconfOpt.setBudat(localBudat);
            
            Quantum133 localIsm02 = new Quantum133();// 人工作业
            BigDecimal is = new BigDecimal(manualwork);
            localIsm02.setQuantum133(is);
            localProdordconfOpt.setIsm02(localIsm02);
            
            Quantum133 localIsm03 = new Quantum133();// 机器动力作业
            BigDecimal lsm03 = new BigDecimal(machinework);
            localIsm03.setQuantum133(lsm03);
            localProdordconfOpt.setIsm03(localIsm03);
            
            Quantum133 localIsm04 = new Quantum133();// 间接费用作业
            BigDecimal lsm04 = new BigDecimal(indirectcostwork);
            localIsm04.setQuantum133(lsm04);
            localProdordconfOpt.setIsm04(localIsm04);
            
            Char4 localUvorn = new Char4();
            localUvorn.setChar4("");
            localProdordconfOpt.setUvorn(localUvorn);
            
            Quantum133 localXmnga = new Quantum133();
            BigDecimal xmnga = new BigDecimal(0);
            localXmnga.setQuantum133(xmnga);
            localProdordconfOpt.setXmnga(localXmnga);
            
            Char4 localGrund = new Char4();
            localGrund.setChar4("");
            localProdordconfOpt.setGrund(localGrund);
            
            Quantum133 localIsm01 = new Quantum133();
            localIsm01.setQuantum133(new BigDecimal(0));
            localProdordconfOpt.setIsm01(localIsm01);
            
            Quantum133 localIsm05 = new Quantum133();
            localIsm05.setQuantum133(new BigDecimal(0));
            localProdordconfOpt.setIsm05(localIsm05);
            
            Quantum133 localIsm06 = new Quantum133();
            localIsm06.setQuantum133(new BigDecimal(0));
            localProdordconfOpt.setIsm06(localIsm06);
            
            Unit3 localTle01 = new Unit3();
            localTle01.setUnit3("");
            localProdordconfOpt.setTle01(localTle01);
            Unit3 localTle02 = new Unit3();
            localTle02.setUnit3("");
            localProdordconfOpt.setTle02(localTle02);
            Unit3 localTle03 = new Unit3();
            localTle03.setUnit3("");
            localProdordconfOpt.setTle03(localTle03);
            Unit3 localTle04 = new Unit3();
            localTle04.setUnit3("");
            localProdordconfOpt.setTle04(localTle04);
            Unit3 localTle05= new Unit3();
            localTle05.setUnit3("");
            localProdordconfOpt.setTle05(localTle05);
            
            Unit3 localTle06= new Unit3();
            localTle06.setUnit3("");
            localProdordconfOpt.setTle06(localTle06);
            
            ztfunPpOrdConfirmOpt.setProdordconfOpt(localProdordconfOpt);
            
            ZtfunPpOrdConfirmOptResponse returnMsg = service.ztfunPpOrdConfirmOpt(ztfunPpOrdConfirmOpt);
            if(StringUtil.isNullOrEmpty(personId)){
            this.getJpo().setValue("NEWSPAPERWORKER", personid);
            }else{
            	this.getJpo().setValue("NEWSPAPERWORKER", personId);
            }
            this.getJpo().setValue("NEWSPAPERWORKERDATE", MroServer.getMroServer().getDate());
            if(returnMsg != null){
                String code  = returnMsg.getRcode().toString();
                String rmesg = "";
                if("S".equals(code)){
                    IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "ERP返回信息：RCODE:" + code
                            + " rmesg:" + rmesg);
                    // 报工后，记录报工的时间以及报告人以及ERP返回的报工日期
                    if(SddqConstant.JX_GX_BG_YES.equals(type)){
                        this.getJpo().setValue("STATUS", SddqConstant.JX_GX_STATUS_YES);
                    }else{
                        this.getJpo().setValue("STATUS", SddqConstant.JX_GX_STATUS_QX);
                    }
                    this.getJpo().setValue("NEWSPAPERWORKERINFO", "");
                    this.getAppBean().reloadCurrTab();
                }else if("E".equals(code)){
                    rmesg = returnMsg.getRmesg()!=null?returnMsg.getRmesg().toString():"";
                    // 报工后，记录报工的时间以及报告人以及ERP返回的报工日期
                    this.getJpo().setValue("STATUS", SddqConstant.JX_GX_STATUS_ERROR);
                    this.getJpo().setValue("NEWSPAPERWORKERINFO", rmesg);
                    // 调用ERP接口成功，更新输出成功
                    IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "ERP返回信息：RCODE:" + code
                            + " rmesg:" + rmesg);
                    this.getAppBean().reloadCurrTab();
                    throw new AppException("jxtaskorder","gxbginfo",new String[]{rmesg});
                }else if("W".equals(code)){
                    // 调用ERP接口成功，更新输出成功
                    rmesg = returnMsg.getRmesg()!=null?returnMsg.getRmesg().toString():"";
                    if(SddqConstant.JX_GX_BG_YES.equals(type)){
                        this.getJpo().setValue("STATUS", SddqConstant.JX_GX_STATUS_YES);
                    }else{
                        this.getJpo().setValue("STATUS", SddqConstant.JX_GX_STATUS_QX);
                    }
                    this.getJpo().setValue("NEWSPAPERWORKERINFO", SddqConstant.JX_GX_MSG_WARN+":"+rmesg);
                    IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "ERP返回信息：RCODE:" + code
                            + " rmesg:" + rmesg);
                    this.getAppBean().reloadCurrTab();
                    throw new AppException("jxtaskorder", "gxbginfo",new String[]{rmesg});
                }else{
                    this.getJpo().setValue("STATUS", SddqConstant.JX_GX_STATUS_ERROR);
                    this.getJpo().setValue("NEWSPAPERWORKERINFO", "ERP返回信息为空");
                    this.getAppBean().reloadCurrTab();
                    throw new AppException("jxtaskorder", "gxbginfo", new String[] {"ERP返回信息为空" });
                }
            }else{
                this.getJpo().setValue("STATUS", SddqConstant.JX_GX_STATUS_ERROR);
                this.getJpo().setValue("NEWSPAPERWORKERINFO", "ERP返回信息为空");
                this.getAppBean().reloadCurrTab();
                throw new AppException("jxtaskorder", "gxbginfo", new String[] {"ERP返回信息为空" });
            }
        } catch (RemoteException e) {
            IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, e.getMessage());
            e.printStackTrace();
            throw new AppException("assettmp", "backerror");
        }catch (IOException e) {
            IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, e.getMessage());
            e.printStackTrace();
            throw new AppException("assettmp", "backerror");
        }catch (JSONException e) {
            IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, e.getMessage());
            e.printStackTrace();
            throw new AppException("assettmp", "backerror");
        }catch (RuntimeException e) {
            IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, e.getMessage());
            e.printStackTrace();
            throw new AppException("assettmp", "backerror");
        }
    }
    
}
