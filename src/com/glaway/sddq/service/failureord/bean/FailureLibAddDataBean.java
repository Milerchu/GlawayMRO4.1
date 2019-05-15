package com.glaway.sddq.service.failureord.bean;

import java.io.IOException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.HttpRequestHelper;
import com.glaway.sddq.tools.IFUtil;

/**
 * 
 * 故障工单-故障记录table bean
 * 
 * @author ygao
 * @version [版本号, 2017-12-10]
 * @since [产品/模块版本]
 */
public class FailureLibAddDataBean extends DataBean {
	/**
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 */
	@Override
	public int addrow() throws MroException, IOException {
		String[] statuses = { "已审核", "处理中", "挂起", "审核驳回" };
		if (StringUtil.isHaveStr(statuses, this.getAppBean().getJpo()
				.getString("STATUS"))) {
			super.addrow();
			String carnum = this.getAppBean().getJpo().getString("CARNUM");
			String cmodel = this.getAppBean().getJpo().getString("MODELS");
			String modelprj = this.getAppBean().getJpo()
					.getString("MODELPROJECT");
			String modeltype = this.getAppBean().getJpo()
					.getString("MODELS.PRODUCTLINE");
			String repairprocess = this.getAppBean().getJpo()
					.getString("REPAIRPROCESS");
			// String assetnum =
			// this.getAppBean().getJpo().getString("ASSETNUM");
			IJpo failurelib = getJpo();
			IJpoSet assetset = MroServer.getMroServer().getJpoSet("ASSET",
					MroServer.getMroServer().getSystemUserServer());
			assetset.setQueryWhere("ASSETLEVEL = 'ASSET' AND CARNO = '"
					+ carnum + "' and cmodel = '" + cmodel + "'");
			failurelib.setValue("CARMODELS", cmodel);
			failurelib.setValue("CARNUM", carnum);
			failurelib.setValue("MODELPROJECT", modelprj);
			failurelib.setValue("MODELTYPE", modeltype);
			failurelib.setValue("REPAIRPROCESS", repairprocess);
			// failurelib.setValue("ASSETNUM", assetnum);
			if (!assetset.isEmpty()) {
				if (assetset.getJpo().getString("RUNKILOMETRE") != null) {
					failurelib.setValue("RUNMILEAGE", assetset.getJpo()
							.getString("RUNKILOMETRE"));
				}

			}

			return GWConstant.ACCESS_SAMEMETHOD;
		} else {
			throw new MroException("", "当前状态无法操作!");
		}

	}

	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {
		// 只能新建一条故障记录，新建后隐藏新建行按钮
		this.getAppBean().hideCtrl("15114297629311");
		super.addEditRowCallBackOk();
	}

	@Override
	public void initialize() throws MroException {
		try {
			if (this.getJpoSet().count() > 0) {
				// 隐藏新建行
				this.getAppBean().hideCtrl("15114297629311");

			} else {
				// 显示新建行按钮
				this.getAppBean().showCtrl("15114297629311");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.initialize();
	}

	@Override
	public synchronized void delete() throws MroException {
		// 标记删除子表 上下车记录关联数据
		if (!getJpo().getJpoSet("EXCHANGERECORD").isEmpty()) {
			getJpo().getJpoSet("EXCHANGERECORD").deleteAll();
		}
		super.delete();
	}

	@Override
	public synchronized void undelete() throws MroException {
		// 取消标记删除子表 上下车记录关联数据
		if (!getJpo().getJpoSet("EXCHANGERECORD").isEmpty()) {
			getJpo().getJpoSet("EXCHANGERECORD").undeleteAll();
		}
		super.undelete();
	}

	/**
	 * 
	 * 获取故障诊断信息
	 * 
	 * @return [参数说明]
	 * @throws MroException
	 * @throws IOException
	 * 
	 */
	public int GETDIAGNOSE() throws MroException, IOException {
		String diagnoseUrl = IFUtil.getIfServiceInfo("bigdata.diagnoseurl");
		String response = "";
		String num = "";

		// 故障来源文件工单
		String sourceOrderNum = getString("FAILUREORDERNUM");
		// 主故障件
		String failureItemCode = getString("PRODUCTCODE");

		// 下载地址
		String failureFileURL = getString("FAULTDATAURL");
		String[] failureArr = failureFileURL.split("=");
		// 文件名
		String failureFileName = failureArr[failureArr.length - 1];
		try {
			num = IFUtil.addIfHistory("BIGDATA_MRO_DIAGNOSE", "orderID="
					+ sourceOrderNum, IFUtil.TYPE_OUTPUT);
			response = HttpRequestHelper.sendGet(diagnoseUrl, "orderID="
					+ sourceOrderNum, "UTF-8");
		} catch (Exception e) {
			IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_NO,
					e.getMessage());
			e.printStackTrace();
			throw new MroException("", "无法获取故障信息：接口访问失败");
		}
		if (response.isEmpty()) {
			IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_NO,
					"返回结果为空，请联系端口负责人");
			throw new MroException("", "无法获取故障信息：返回结果为空");
		}
		JSONObject resObj = JSONObject.parseObject(response);
		if (!resObj.getString("stateCode").equals("200")) {
			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_NO,
					resObj.getString("message"));
			throw new MroException("", resObj.getString("message"));
		}
		IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
				response);
		num = IFUtil.addIfHistory("BIGDATA_MRO_DIAGNOSE",
				resObj.toJSONString(), IFUtil.TYPE_INPUT);

		// 故障结果名称
		String diagnoseResult = resObj.getString("diagnoseResult");
		// 故障原因数组
		JSONArray jArr = resObj.getJSONArray("diagnoseCauses");
		IJpoSet diagnoseSet = MroServer.getMroServer()
				.getJpoSet("FAULTDIAGNOSE",
						MroServer.getMroServer().getSystemUserServer());
		int inputcount = 0;
		for (int i = 0; i < jArr.size(); i++) {
			JSONObject jObj = jArr.getJSONObject(i);
			// 故障原因
			String cause = jObj.getString("cause");
			// 根据结果与原因查找是否有重复
			diagnoseSet.setQueryWhere("DIAGNOSECAUSE='" + cause
					+ "' and DIAGNOSERESULT='" + diagnoseResult
					+ "' and SOURCEFILEORDERID='" + sourceOrderNum + "'");
			diagnoseSet.reset();
			if (diagnoseSet.count() > 0) {
				continue;
			}

			IJpo diagnose = diagnoseSet.addJpo();
			diagnose.setValue("DIAGNOSECAUSE", cause);
			diagnose.setValue("SOURCEFILEORDERID", sourceOrderNum);
			diagnose.setValue("FAULTEQUIPCODE", failureItemCode);
			diagnose.setValue("DIAGNOSERESULT", diagnoseResult);
			diagnose.setValue("FAULTFILENAME", failureFileName);
			diagnose.setValue("SOURCEFILENAME", failureFileURL);
			diagnose.setValue("ORGID", "CRRC");
			inputcount++;
			diagnoseSet.save();
		}
		IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
				"操作成功，获取 " + inputcount + " 条诊断原因,另有 "
						+ (jArr.size() - inputcount) + " 条重复");

		// 刷新故障诊断单子表
		this.getDataBean("1533120802387").resetAndReload();
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

}
