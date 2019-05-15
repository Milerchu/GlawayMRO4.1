package com.glaway.sddq.tools.mobiletools;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 客户端小程序
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月10日]
 * @since [产品/模块版本]
 */
public class MobileCustom {

	/**
	 * 
	 * 客户提报故障
	 * 
	 * @param json
	 * @param customer
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public MobileResult REPORT(String json, String customer)
			throws MroException {
		Set<String> receiver = new HashSet<String>();
		MobileResult result = new MobileResult();
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("failureOrd");

		IJpoSet orderSet = MroServer.getMroServer().getSysJpoSet("WORKORDER");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		IJpo failureOrder = orderSet.addJpo();
		failureOrder.setValue("type", "故障");
		failureOrder.setValue("ISMOBILECREATE", 1);
		// 无登录ID，使用系统ID
		failureOrder.setValue("reporter", "MOBILECUSTOMER");
		failureOrder.setValue("creater", "MOBILECUSTOMER");

		// 车号
		String carnum = data.getString("carNum");
		// 车型
		String models = data.getString("cmodel");
		// 项目编号
		String projectnum = data.getString("projectNum");
		// 服务工程师
		String servEngineer = data.getString("servEngineer");
		receiver.add(servEngineer);
		// 故障处理站点
		String whichStation = data.getString("whichStation");
		// 安全管理要求
		String managementreq = data.getString("managementReq");
		// 派工理由
		String dispatchreason = data.getString("dispatchReason");
		// 客户来电时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String calltime = sdf.format(MroServer.getMroServer().getDate());
		// 服务单位
		IJpoSet contactorSet = MroServer.getMroServer().getSysJpoSet(
				"CUSTOMERCONTACTOR", "telephone='" + customer + "'");
		if (contactorSet.count() < 1) {
			throw new MroException("无此联系人：" + customer);
		}
		IJpo contactor = contactorSet.getJpo();
		String custnum = contactor.getString("custnum");
		String custcontactnum = contactor.getString("custcontactnum");
		// 服务单位联系人为当前登录客户

		IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet("asset",
				"carno='" + carnum + "' and cmodel='" + models + "'");
		if (!assetSet.isEmpty()) {
			failureOrder.setValue("carnum", assetSet.getJpo()
					.getString("carno"));
			failureOrder.setValue("models",
					assetSet.getJpo().getString("cmodel"));
			failureOrder.setValue("assetnum",
					assetSet.getJpo().getString("assetnum"),
					GWConstant.P_NOACTION);

			failureOrder.setValue("repairprocess",
					failureOrder.getString("ASSET.REPAIRPROCESS"));
			failureOrder.setValue("OWNERCUSTOMER",
					failureOrder.getString("ASSET.OWNERCUSTOMER"));
			failureOrder.setValue("REPAIRAFTERKILOMETER",
					failureOrder.getString("ASSET.REPAIRAFTERKILOMETER"));
			failureOrder.setValue("modelproject",
					failureOrder.getString("ASSET.MODELS.MODELDESC"));
			failureOrder.setValue("runKilometre",
					failureOrder.getString("ASSET.runKilometre"));
			failureOrder.setValue("runKilometre",
					failureOrder.getString("ASSET.runKilometre"));
		} else {
			throw new MroException("无法根据车型:" + failureOrder.getString("carnum")
					+ " 车号:" + failureOrder.getString("models")
					+ " 获取车辆信息，请检查！");
		}

		failureOrder.setValue("carnum", carnum);
		failureOrder.setValue("models", models);
		failureOrder.setValue("calltime", calltime);
		failureOrder.setValue("projectnum", projectnum);
		failureOrder.setValue("servcompany", custnum);
		failureOrder.setValue("servcomcontactor", custcontactnum);
		failureOrder.setValue("servengineer", servEngineer);
		failureOrder.setValue("whichStation", whichStation);
		failureOrder.setValue("managementreq", managementreq);
		failureOrder.setValue("dispatchreason", dispatchreason);
		failureOrder.setValue("remark", "由客户：" + customer + " 提报",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

		long id = failureOrder.getId();
		String ordernum = failureOrder.getString("ordernum");
		orderSet.save();
		// 通知服务工程师
		WorkorderUtil.sendMsg("FAILUREORD", id, receiver, "客户报修", "提报工单："
				+ ordernum + "，车型：" + models + "，车号：" + carnum + "。");

		return result;
	}

	/**
	 * 
	 * 评价工单
	 * 
	 * @param json
	 * @param customer
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public MobileResult APPRAISE(String json, String customer)
			throws MroException {
		Set<String> receiver = new HashSet<String>();
		receiver.add(customer);
		MobileResult result = new MobileResult();
		JSONObject obj = JSONObject.parseObject(json);
		JSONObject data = obj.getJSONObject("failureOrd");
		String orderNum = data.getString("orderNum");
		IJpoSet orderSet = MroServer.getMroServer().getSysJpoSet("WORKORDER",
				"orderNum='" + orderNum + "'");
		if (orderSet.count() < 1) {
			throw new MroException("无此工单：" + orderNum);
		}
		IJpo failureOrder = orderSet.getJpo();
		failureOrder.setValue("RANKDESC", data.getString("rankDesc"));
		failureOrder.setValue("RANK", data.getString("rank"));
		orderSet.save();

		return result;
	}

	/**
	 * 
	 * 客户登录
	 * 
	 * @param json
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public MobileResult LOGIN(String json, String customer) throws MroException {
		MobileResult result = new MobileResult();
		JSONObject obj = JSONObject.parseObject(json);
		String password = obj.getString("password");
		if (StringUtil.isStrEmpty(customer) || StringUtil.isStrEmpty(password)) {
			throw new MroException("用户名或密码为空，请检查");
		}
		IJpoSet contactorSet = MroServer.getMroServer().getSysJpoSet(
				"CUSTOMERCONTACTOR", "telephone='" + customer + "'");
		if (contactorSet.count() < 1) {
			throw new MroException("用户名或密码错误，请检查");
		}
		String existPW = contactorSet.getJpo().getString("MOBILEPASSWORD");
		if (!password.equals(existPW)) {
			throw new MroException("用户名或密码错误，请检查");
		}
		JSONObject customObj = new JSONObject();
		customObj.put("name", contactorSet.getJpo().getString("name"));
		customObj
				.put("telephone", contactorSet.getJpo().getString("TELEPHONE"));
		customObj.put("emailAddress",
				contactorSet.getJpo().getString("emailAddress"));
		customObj.put("gender", contactorSet.getJpo().getString("gender"));
		customObj.put("position", contactorSet.getJpo().getString("position"));
		customObj.put("custNum", contactorSet.getJpo().getString("custnum"));
		customObj.put("whichOffice",
				contactorSet.getJpo().getString("CUSTINFO.whichoffice"));
		result.setData(customObj);
		return result;
	}
}
