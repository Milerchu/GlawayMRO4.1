package com.glaway.sddq.material.devtools.bean;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.back.Interface.webservice.erp.service.erptomrotestdevesb.gjzckp.GzjckpCommon;
import com.glaway.sddq.back.Interface.webservice.erp.service.newgjzckp.test2;

/**
 * 
 * <功能描述> 获取ERP资产卡片
 * 
 * @author Guan
 * @version [版本号, 2018-7-31]
 * @since [产品/模块版本]
 */
public class DeviceInfoAppBean extends AppBean {

	public int GETASSTE() throws MroException, IOException, ParseException {
//		test2 demo = new test2();
//		String str = demo.demo("1000", "", "V101050100");
		String str =GzjckpCommon.getInstance().ErpToMro("1000", "", "V101050100");
		JSONArray array = JSON.parseArray(str);
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				String assetnum = obj.getString("ANLN1");
				IJpoSet DeviceInfoset = MroServer.getMroServer().getJpoSet("DEVICEINFO", MroServer.getMroServer().getSystemUserServer());
				DeviceInfoset.setQueryWhere("ASSETNUM='" + assetnum + "'");
				// System.out.println("ASSETNUM='" + assetnum + "'");
				DeviceInfoset.reset();
				IJpo ms = null;
				if (!DeviceInfoset.isEmpty()) {
					ms = DeviceInfoset.getJpo(0);
					ms.setValue("BUKRS", obj.getString("BUKRS"));// 公司代码
					ms.setValue("CLASSNUM", obj.getString("ANLKL"));// 资产分类
					ms.setValue("CLASSDESC", obj.getString("TXK50"));// 资产分类描述
					ms.setValue("ASSETDESC", obj.getString("TXT50"));// 资产描述
					ms.setValue("ASSETDESCS", obj.getString("TXA50"));// 资产附加描述
					ms.setValue("ASSETMAIN", obj.getString("ANLHTXT"));// 资产主号说明
					ms.setValue("TOOLNUM", obj.getString("SERNR"));// 序列号
					ms.setValue("INVNR", obj.getString("INVNR"));// 存货号
					ms.setValue("QTY", Double.parseDouble(obj.getString("MENGE")));// 数量
					ms.setValue("UNIT", obj.getString("MEINS"));// 计量单位
					ms.setValue("INVZU", obj.getString("INVZU"));// 库存标记
					ms.setValue("KOSTL", obj.getString("KOSTL"));// 成本中心
					ms.setValue("KOSTLV", obj.getString("KOSTLV"));// 责任成本中心
					ms.setValue("CAUFN", obj.getString("CAUFN"));// 内部订单
					ms.setValue("RAUMN", obj.getString("RAUMN"));// 房间
					ms.setValue("KEEPER", obj.getString("ZPERNR"), 112L);// 保管人工号
					ms.setValue("ORD43", obj.getString("ORD43"));// 经济用途
					ms.setValue("STATUSDESC", obj.getString("ORD44"));// 资产状态
					ms.setValue("IZWEK", obj.getString("IZWEK"));// 投资原因
					ms.setValue("ANLUE", obj.getString("ANLUE"));// 变动方式
					try {
						ms.setValue("VENDOR", "" + Integer.parseInt(obj.getString("LIFNR")));// 供应商代码

					} catch (Exception e) {
						ms.setValue("VENDOR", obj.getString("LIFNR"));// 供应商代码

					}
					ms.setValue("EAUFN", obj.getString("EAUFN"));// 投资工作令
					ms.setValue("ORIGINALCOST", Double.parseDouble(obj.getString("ZZCYZ")));// 资产原值
					ms.setValue("ZLJZJ", Double.parseDouble(obj.getString("ZLJZJ")));// 累计折旧
					ms.setValue("ZJZZB", Double.parseDouble(obj.getString("ZJZZB")));// 减值准备
					ms.setValue("WORTH", Double.parseDouble(obj.getString("ZZCJZ")));// 资产净值
					ms.setValue("ASSETDATE", fmt.parse(obj.getString("AKTIV")));// 资本化日期
					ms.setValue("DEAKT", fmt.parse(obj.getString("DEAKT")));// 不活动日期
				} else {
					ms = DeviceInfoset.addJpo();
					ms.setValue("BUKRS", obj.getString("BUKRS"));// 公司代码
					ms.setValue("ASSETNUM", obj.getString("ANLN1"));// 资产编码
					ms.setValue("CLASSNUM", obj.getString("ANLKL"));// 资产分类
					ms.setValue("CLASSDESC", obj.getString("TXK50"));// 资产分类描述
					ms.setValue("ASSETDESC", obj.getString("TXT50"));// 资产描述
					ms.setValue("ASSETDESCS", obj.getString("TXA50"));// 资产附加描述
					ms.setValue("ASSETMAIN", obj.getString("ANLHTXT"));// 资产主号说明
					ms.setValue("TOOLNUM", obj.getString("SERNR"));// 序列号
					ms.setValue("INVNR", obj.getString("INVNR"));// 存货号
					ms.setValue("QTY", Double.parseDouble(obj.getString("MENGE")));// 数量
					ms.setValue("UNIT", obj.getString("MEINS"));// 计量单位
					ms.setValue("INVZU", obj.getString("INVZU"));// 库存标记
					ms.setValue("KOSTL", obj.getString("KOSTL"));// 成本中心
					ms.setValue("KOSTLV", obj.getString("KOSTLV"));// 责任成本中心
					ms.setValue("CAUFN", obj.getString("CAUFN"));// 内部订单
					ms.setValue("RAUMN", obj.getString("RAUMN"));// 房间
					ms.setValue("KEEPER", obj.getString("ZPERNR"), 112L);// 保管人工号
					ms.setValue("ORD43", obj.getString("ORD43"));// 经济用途
					ms.setValue("STATUSDESC", obj.getString("ORD44"));// 资产状态
					ms.setValue("IZWEK", obj.getString("IZWEK"));// 投资原因
					ms.setValue("ANLUE", obj.getString("ANLUE"));// 变动方式
					try {
						ms.setValue("VENDOR", "" + Integer.parseInt(obj.getString("LIFNR")));// 供应商代码

					} catch (Exception e) {
						ms.setValue("VENDOR", obj.getString("LIFNR"));// 供应商代码
					}
					ms.setValue("EAUFN", obj.getString("EAUFN"));// 投资工作令
					ms.setValue("ORIGINALCOST", Double.parseDouble(obj.getString("ZZCYZ")));// 资产原值
					ms.setValue("ZLJZJ", Double.parseDouble(obj.getString("ZLJZJ")));// 累计折旧
					ms.setValue("ZJZZB", Double.parseDouble(obj.getString("ZJZZB")));// 减值准备
					ms.setValue("WORTH", Double.parseDouble(obj.getString("ZZCJZ")));// 资产净值
					ms.setValue("ASSETDATE", fmt.parse(obj.getString("AKTIV")));// 资本化日期
					ms.setValue("DEAKT", fmt.parse(obj.getString("DEAKT")));// 不活动日期
				}
				ms.getThisJpoSet().save();
			}
		}
		showMsgbox("提示：", "获取成功，请刷新界面");
		return 1;
	}

}
