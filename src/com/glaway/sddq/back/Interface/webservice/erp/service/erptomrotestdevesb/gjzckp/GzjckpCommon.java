package com.glaway.sddq.back.Interface.webservice.erp.service.erptomrotestdevesb.gjzckp;

import java.rmi.RemoteException;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient3.HttpTransportPropertiesImpl.Authenticator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.sddq.back.Interface.webservice.erp.service.erptomrotestdevesb.gjzckp.ComZzsErpZTFUN_FICO_ASSETS_WEBStub;
import com.glaway.sddq.back.Interface.webservice.erp.service.erptomrotestdevesb.gjzckp.ComZzsErpZTFUN_FICO_ASSETS_WEBStub.Char10;
import com.glaway.sddq.back.Interface.webservice.erp.service.erptomrotestdevesb.gjzckp.ComZzsErpZTFUN_FICO_ASSETS_WEBStub.Char12;
import com.glaway.sddq.back.Interface.webservice.erp.service.erptomrotestdevesb.gjzckp.ComZzsErpZTFUN_FICO_ASSETS_WEBStub.Char4;
import com.glaway.sddq.back.Interface.webservice.erp.service.erptomrotestdevesb.gjzckp.ComZzsErpZTFUN_FICO_ASSETS_WEBStub.TableOfZficoAssetsMasterdata;
import com.glaway.sddq.back.Interface.webservice.erp.service.erptomrotestdevesb.gjzckp.ComZzsErpZTFUN_FICO_ASSETS_WEBStub.ZficoAssetsMasterdata;
import com.glaway.sddq.back.Interface.webservice.erp.service.erptomrotestdevesb.gjzckp.ComZzsErpZTFUN_FICO_ASSETS_WEBStub.ZtfunFicoAssetsMasterdata;
import com.glaway.sddq.back.Interface.webservice.erp.service.erptomrotestdevesb.gjzckp.ComZzsErpZTFUN_FICO_ASSETS_WEBStub.ZtfunFicoAssetsMasterdataResponse;
import com.glaway.sddq.tools.IFUtil;

public class GzjckpCommon {
private static GzjckpCommon wfCtrl = null;;
    
    private GzjckpCommon()
    {
    }
    
    public synchronized static GzjckpCommon getInstance()
    {
        if (wfCtrl == null)
        {
            wfCtrl = new GzjckpCommon();
        }
        return wfCtrl;
    }
	   
    public String ErpToMro(String burks, String anln1, String kostl) throws RemoteException {
		ComZzsErpZTFUN_FICO_ASSETS_WEBStub service = new ComZzsErpZTFUN_FICO_ASSETS_WEBStub();
		// 认证代码 start
		Authenticator auth = new Authenticator();
		 String user = IFUtil.getIfServiceInfo("erp.user");
         String pwd = IFUtil.getIfServiceInfo("erp.pwd");
         auth.setUsername(user);
         auth.setPassword(pwd);
		service._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
		// 认证代码 end
		ZtfunFicoAssetsMasterdata param = new ZtfunFicoAssetsMasterdata();
		TableOfZficoAssetsMasterdata table = new TableOfZficoAssetsMasterdata();
		Char4 LBukrs = new Char4();
		Char12 LAnln1 = new Char12();
		Char10 LKostl = new Char10();

		LBukrs.setChar4(burks);// 公司代码
		LAnln1.setChar12(anln1);// 资产编号
		LKostl.setChar10(kostl);// 成本zhongxin

		param.setLBukrs(LBukrs);
		param.setLAnln1(LAnln1);
		param.setLKostl(LKostl);
		param.setTOutput(table);
		ZtfunFicoAssetsMasterdataResponse res = service.ztfunFicoAssetsMasterdata(param);
		TableOfZficoAssetsMasterdata retable = res.getTOutput();
		ZficoAssetsMasterdata[] zppz = retable.getItem();
		JSONArray array = new JSONArray();
		if (zppz != null && zppz.length > 0) {
			for (int index = 0; index < zppz.length; index++) {
				JSONObject obj = new JSONObject();
				String BUKRS = zppz[index].getBukrs().toString();// 公司代码
				String ANLN1 = zppz[index].getAnln1().toString();// 资产编码
				String ANLKL = zppz[index].getAnlkl().toString();// 资产分类
				String TXK50 = zppz[index].getTxk50().toString();// 资产分类描述
				String TXT50 = zppz[index].getTxt50().toString();// 资产描述
				String TXA50 = zppz[index].getTxa50().toString();// 资产附加描述
				String ANLHTXT = zppz[index].getAnlhtxt().toString();// 资产主号说明
				String SERNR = zppz[index].getSernr().toString();// 序列号
				String INVNR = zppz[index].getInvnr().toString();// 存货号
				String MENGE = zppz[index].getMenge().toString();// 数量
				String MEINS = zppz[index].getMeins().toString();// 计量单位
				String INVZU = zppz[index].getInvzu().toString();// 库存标记
				String KOSTL = zppz[index].getKostl().toString();// 成本中心
				String KOSTLV = zppz[index].getKostlv().toString();// 责任成本中心
				String CAUFN = zppz[index].getCaufn().toString();// 内部订单
				String RAUMN = zppz[index].getRaumn().toString();// 房间
				String ZPERNR = zppz[index].getZpernr().toString();// 保管人工号
				String ORD43 = zppz[index].getOrd43().toString();// 经济用途
				String ORD44 = zppz[index].getOrd44().toString();// 资产状态
				String IZWEK = zppz[index].getIzwek().toString();// 投资原因
				String ANLUE = zppz[index].getAnlue().toString();// 变动方式
				String LIFNR = zppz[index].getLifnr().toString();// 供应商代码
				String HERST = zppz[index].getHerst().toString();// 制造商
				String EAUFN = zppz[index].getEaufn().toString();// 投资工作令
				String ZZCYZ = zppz[index].getZzcyz().toString();// 资产原值
				String ZLJZJ = zppz[index].getZljzj().toString();// 累计折旧
				String ZJZZB = zppz[index].getZjzzb().toString();// 减值准备
				String ZZCJZ = zppz[index].getZzcjz().toString();// 资产净值
				String AKTIV = zppz[index].getAktiv().toString();// 资本化日期
				String DEAKT = zppz[index].getDeakt().toString();// 不活动日期
				obj.put("BUKRS", BUKRS);
				obj.put("ANLN1", ANLN1);
				obj.put("ANLKL", ANLKL);
				obj.put("TXK50", TXK50);
				obj.put("TXT50", TXT50);
				obj.put("TXA50", TXA50);
				obj.put("ANLHTXT", ANLHTXT);
				obj.put("SERNR", SERNR);
				obj.put("INVNR", INVNR);
				obj.put("MENGE", MENGE);
				obj.put("MEINS", MEINS);
				obj.put("INVZU", INVZU);
				obj.put("KOSTL", KOSTL);
				obj.put("KOSTLV", KOSTLV);
				obj.put("CAUFN", CAUFN);
				obj.put("RAUMN", RAUMN);
				obj.put("ZPERNR", ZPERNR);
				obj.put("ORD43", ORD43);
				obj.put("ORD44", ORD44);
				obj.put("IZWEK", IZWEK);
				obj.put("ANLUE", ANLUE);
				obj.put("LIFNR", LIFNR);
				obj.put("HERST", HERST);
				obj.put("EAUFN", EAUFN);
				obj.put("ZZCYZ", ZZCYZ);
				obj.put("ZLJZJ", ZLJZJ);
				obj.put("ZJZZB", ZJZZB);
				obj.put("ZZCJZ", ZZCJZ);
				obj.put("AKTIV", AKTIV);
				obj.put("DEAKT", DEAKT);
				array.add(obj);
			}
		}
		return array.toString();

	}
}
