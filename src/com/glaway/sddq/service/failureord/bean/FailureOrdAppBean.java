package com.glaway.sddq.service.failureord.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.page.control.Tab;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.*;
import com.glaway.sddq.tools.*;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient3.HttpTransportPropertiesImpl.Authenticator;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 故障工单AppBean
 * 
 * @author ygao
 * @version [版本号, 2017-11-1]
 * @since [产品/模块版本]
 */
public class FailureOrdAppBean extends AppBean {

	@Override
	public void initialize() throws MroException {
		super.initialize();
	}

	@Override
	public void afterTabChange(Tab currTab) throws IOException, MroException {
		super.afterTabChange(currTab);

		try {
			// 故障件上级子表
			PageControl superiorTb = this.page
					.getControlByXmlId("1543817262496");
			// 串换记录子表
			PageControl chAssetTb = this.page.getControlById("chassetTable");
			// 非换件处理时故障件字段区域
			PageControl faultSec = this.page.getControlByXmlId("1543805689797");
			if (getJpo() != null) {
				// 处理方式
				String dealMethod = getJpo().getString("FAILURELIB.DEALMETHOD");
				if (StringUtil.isStrNotEmpty(dealMethod)
						&& (superiorTb != null && faultSec != null)) {
					// 换件处理
					if (SddqConstant.FAIL_DEALMETHOD_HJCL.equals(dealMethod)
							|| SddqConstant.FAIL_DEALMETHOD_CHANDGH
									.equals(dealMethod)) {
						// 隐藏故障件信息区域及子表
						superiorTb.hide();
						faultSec.hide();
					} else {// 非换件处理
						// 显示故障件信息区域及子表
						superiorTb.show();
						faultSec.show();
					}
					// 串换处理
					if (SddqConstant.FAIL_DEALMETHOD_CHCL.equals(dealMethod)
							|| SddqConstant.FAIL_DEALMETHOD_CHANDGH
									.equals(dealMethod)) {

						// 显示串换子表
						if (chAssetTb != null) {
							chAssetTb.show();
						}
					} else {
						// 隐藏串换子表
						if (chAssetTb != null) {
							chAssetTb.hide();
						}
					}
					superiorTb.loadData();
					chAssetTb.loadData();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * 工单挂起按钮
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public int HOLD() throws IOException, MroException {

		if ("处理中".equals(getJpo().getString("status"))) {
			if (getJpo().getDate("OPERATETIME") == null) {
				throw new MroException("", "开始作业后才能挂起工单！");
			}
			if (getJpo().getDate("COMPLETETIME") != null) {
				throw new MroException("工单已处理完成，无法挂起！");
			}
			this.page.loadDialog("SHOWHOLD", this.page.getCurrEventCtrl());
		} else if ("挂起".equals(getJpo().getString("status"))) {
			throw new MroException("", "当前工单已经挂起！");
		} else {
			throw new MroException("", "当前状态无法操作！");
		}
		return 0;

	}

	/**
	 * 
	 * 下载故障数据库包
	 * 
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public void downloaddata() throws Exception {
		String url = this.getJpo().getString("FAILURELIB.FAULTDATAURL");

		if (StringUtil.isStrNotEmpty(url)) {
			mroSession.returnReponse("", "openUrl", "<url>" + url + "</url>");
		} else {
			throw new AppException("servorder", "nodata");
		}
	}

	@Override
	public int ROUTEWF() throws Exception {

		String status = getJpo().getString("STATUS");
		String productline = getJpo().getString("MODELS.PRODUCTLINE");// 车型大类
		String faultconseq = getJpo().getString("FAILURELIB.FAULTCONSEQ");// 故障后果
		String dataStatus = getJpo().getString("FAILURELIB.FAULTDATAREC");// 故障数据包状态
		String nodataReason = getJpo().getString("FAILURELIB.NODATAREASON");// 无数据包原因

		IJpoSet losspartSet = getJpo().getJpoSet("JXTASKLOSSPART");
		IJpoSet exchangeSet = getJpo().getJpoSet("FAILURELIB").getJpo()
				.getJpoSet("EXCHANGERECORD");
		IJpoSet chAssetSet = getJpo().getJpoSet("CHASSET");

		if (StringUtil.isNull(status) || status.equals("关闭")) {
			throw new AppException("servorder", "statusnowf");
		}
		if (SddqConstant.WO_STATUS_CG.equals(status)) {// 录入工单时增加QMS必须字段判断

			if (StringUtil.isStrEmpty(getJpo().getString("REPAIRPROCESS"))) {
				if (StringUtil.isStrEmpty(getJpo().getString(
						"ASSET.REPAIRPROCESS"))) {
					throw new MroException("修程字段不可为空，请先去装车配置中维护后再发送！");
				} else {

					getJpo().setValue("REPAIRPROCESS",
							getJpo().getString("ASSET.REPAIRPROCESS"),
							GWConstant.P_NOVALIDATION);
				}
			}
			if (StringUtil.isStrEmpty(getJpo().getString("OWNERCUSTOMER"))) {
				if (StringUtil.isStrEmpty(getJpo().getString(
						"ASSET.OWNERCUSTOMER"))) {

					throw new MroException("配属用户不可为空，请先去装车配置中维护后再录入工单！");
				} else {

					getJpo().setValue("OWNERCUSTOMER",
							getJpo().getString("ASSET.OWNERCUSTOMER"),
							GWConstant.P_NOVALIDATION);
				}
			}
			if (StringUtil.isStrEmpty(getJpo().getString(
					"SERVENGINEER.PRIMARYPHONE"))) {
				throw new MroException("现场处理人联系方式不可为空，请先去人员信息中维护后在录入工单！");
			}

			// 设置当前任务执行人
			getJpo().setValue("ACTTASKPERSON",
					getJpo().getString("SERVENGINEER.DISPLAYNAME"),
					GWConstant.P_NOVALIDATION);

			// 处理中/库管员驳回
		} else if (SddqConstant.WO_STATUS_CLZ.equals(status)
				|| SddqConstant.WO_STATUS_KGYBH.equals(status)) {
			if (this.getJpo().getDate("COMPLETETIME") == null)// 处理完成时间为空
			{
				throw new MroException("", "处理完成后才能发送工作流！");
			}
			String dealMethod = getJpo().getJpoSet("FAILURELIB").getJpo()
					.getString("DEALMETHOD");
			if (SddqConstant.FAIL_DEALMETHOD_HJCL.equals(dealMethod)
					|| SddqConstant.FAIL_DEALMETHOD_CHANDGH.equals(dealMethod)) {// 故障处理方式为换件处理

				if (exchangeSet.count(GWConstant.P_COUNT_AFTERSAVE) < 1
						&& losspartSet.count(GWConstant.P_COUNT_AFTERSAVE) < 1) {

					throw new AppException("failureord", "noexchange");// 无上下车件无法提交

				}

			}

			// 串换处理
			if (SddqConstant.FAIL_DEALMETHOD_CHCL.equals(dealMethod)
					|| SddqConstant.FAIL_DEALMETHOD_CHANDGH.equals(dealMethod)) {

				if (chAssetSet.count(GWConstant.P_COUNT_AFTERSAVE) < 1) {

					throw new MroException("无串换记录无法提交！");// 无串换记录无法提交

				}
			}

			if ((!"上传成功".equals(dataStatus))
					&& (WorkorderUtil.isImpFault(faultconseq))) {// 高级故障需要有数据包才能发生工作流
				throw new MroException("WORKORDER", "DOCLINKS2");
			}

			if ("机车".equals(productline)) {// 机车类需判断是否有关键物料
				if (WorkorderUtil.hasImpItem(exchangeSet, "ITEM")
						|| WorkorderUtil.hasImpItem(losspartSet, "DOWNITEMNUM")) {
					if (!"上传成功".equals(dataStatus)
							&& StringUtil.isStrEmpty(nodataReason)) {
						throw new MroException("WORKORDER", "DOCLINKS2");
					}
				}
			}

			if ("否".equals(getJpo().getString("ISREADSAFEREQ"))) {// 未阅读安全管理要求不可发送工作流
				throw new AppException("failureord", "notreadsecreq");
			}

			// 库管员驳回/审核驳回/技术主管驳回/专职库管驳回
		} else if (SddqConstant.WO_STATUS_KGYBH.equals(status)
				|| SddqConstant.WO_STATUS_SHBH.equals(status)
				|| SddqConstant.WO_STATUS_JSZGBH.equals(status)
				|| SddqConstant.WO_STATUS_ZZKGYBH.equals(status)) {

			if ((!"上传成功".equals(dataStatus))
					&& (WorkorderUtil.isImpFault(faultconseq))) {// 高级故障需要有数据包才能发生工作流
				throw new MroException("WORKORDER", "DOCLINKS2");
			}

			if ("机车".equals(productline)) {// 机车类需判断是否有关键物料
				if (WorkorderUtil.hasImpItem(exchangeSet, "ITEM")
						|| WorkorderUtil.hasImpItem(losspartSet, "DOWNITEMNUM")) {
					if (!"上传成功".equals(dataStatus)
							&& StringUtil.isStrEmpty(nodataReason)) {
						throw new MroException("WORKORDER", "DOCLINKS2");
					}
				}
			}

			if ("否".equals(getJpo().getString("ISREADSAFEREQ"))) {// 未阅读安全管理要求不可发送工作流
				throw new AppException("failureord", "notreadsecreq");
			}

			String dealMethod = getJpo().getJpoSet("FAILURELIB").getJpo()
					.getString("DEALMETHOD");
			if (SddqConstant.FAIL_DEALMETHOD_HJCL.equals(dealMethod)
					|| SddqConstant.FAIL_DEALMETHOD_CHANDGH.equals(dealMethod)) {// 故障处理方式为换件处理

				if (exchangeSet.count(GWConstant.P_COUNT_AFTERSAVE) < 1
						&& losspartSet.count(GWConstant.P_COUNT_AFTERSAVE) < 1) {

					throw new AppException("failureord", "noexchange");// 无上下车件无法提交

				}

			}

			// 串换处理
			if (SddqConstant.FAIL_DEALMETHOD_CHCL.equals(dealMethod)
					|| SddqConstant.FAIL_DEALMETHOD_CHANDGH.equals(dealMethod)) {

				if (chAssetSet.count(GWConstant.P_COUNT_AFTERSAVE) < 1) {

					throw new MroException("无串换记录无法提交！");// 无串换记录无法提交

				}
			}
		} else if (SddqConstant.WO_STATUS_GQ.equals(status)) {// 挂起时无法发送工作流

			throw new MroException("当前状态无法发送工作流！");

		} else if (SddqConstant.WO_STATUS_JSZGSH.equals(status)) {// 技术主管审核

			boolean flag = false;
			if (exchangeSet != null && exchangeSet.count() > 0) {

				for (int idx = 0; idx < exchangeSet.count(); idx++) {
					IJpo ex = exchangeSet.getJpo(idx);
					// 现场留用观察
					if (SddqConstant.FAIL_DEALMODE_RETENTION.equals(ex
							.getString("dealmode"))) {
						// 未填写留用意见
						if (StringUtil.isStrEmpty(ex.getString("ISAGREESTAY"))) {
							flag = true;
							break;
						}
					}
				}

			}
			if (!flag) {

				if (losspartSet != null && losspartSet.count() > 0) {

					for (int idx = 0; idx < losspartSet.count(); idx++) {
						IJpo loss = losspartSet.getJpo(idx);
						// 现场留用观察
						if (SddqConstant.FAIL_DEALMODE_RETENTION.equals(loss
								.getString("dealmode"))) {
							// 未填写留用意见
							if (StringUtil.isStrEmpty(loss
									.getString("ISAGREESTAY"))) {
								flag = true;
								break;
							}
						}
					}

				}
			}

			if (flag) {
				throw new MroException("未填写现场留用观察意见，无法发送工作流！");
			}

		}
		return super.ROUTEWF();
	}

	/**
	 * 
	 * 到达现场按钮
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void ARRIVE() throws Exception {
		// 判断当前登录人是否是工单执行人
		if (!WfControlUtil.isCurUser(getJpo())) {
			throw new MroException("workorder", "notwfcuruser");
		}

		if (getJpo().getString("status").equals("处理中")) {

			if (getJpo().getDate("ARRIVETIME") != null) {
				throw new MroException("", "已经到达现场!");
			}
			Date arrivaldate = MroServer.getMroServer().getDate();
			getJpo().setValue("ARRIVETIME", arrivaldate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.SAVE();
			gotoTab(2);// 切换标签页
		} else {
			throw new MroException("", "当前状态无法操作!");
		}
	}

	/**
	 * 
	 * 开始作业按钮
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void STARTWORK() throws Exception {

		// 判断当前登录人是否是工单执行人
		if (!WfControlUtil.isCurUser(getJpo())) {
			throw new MroException("workorder", "notwfcuruser");
		}

		if (getJpo().getString("status").equals("处理中")) {

			// 车号为空
			if (StringUtil.isStrEmpty(getJpo().getString("CARNUM"))) {
				throw new MroException("请先选择车号！");
			}
			// 开始作业时间不为空
			if (getJpo().getDate("OPERATETIME") != null) {
				throw new MroException("", "已经开始作业!");
			}
			// 到达现场时间为空
			if (getJpo().getDate("ARRIVETIME") == null) {
				throw new MroException("", "还未到达现场!");
			}
			// 当前系统时间
			Date operadate = MroServer.getMroServer().getDate();
			getJpo().setValue("OPERATETIME", operadate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

		} else if ("挂起".equals(getJpo().getString("status"))) {
			// 获取挂起记录表
			IJpoSet hrSet = getJpo().getJpoSet("HOLDRECORD");
			// 取结束时间未填的
			hrSet.setUserWhere("endtime is null");
			hrSet.reset();

			IJpo hr = hrSet.getJpo(0);
			// 设置结束时间为当前时间
			hr.setValue("endtime", MroServer.getMroServer().getDate());

			// 设置工单状态为 处理中
			getJpo().setValue("status", "处理中");
			// this.reloadPage();
			showMsgbox("信息", "继续作业！");
		} else {
			throw new MroException("", "当前状态无法操作!");
		}
		this.SAVE();
	}

	/**
	 * 处理完成按钮 <功能描述>
	 * 
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public void FINISHPROCESS() throws Exception {

		// 判断当前登录人是否是工作流执行人
		if (!WfControlUtil.isCurUser(getJpo())) {
			throw new MroException("workorder", "notwfcuruser");
		}

		// 只在处理中状态下可以操作
		if (getString("status").equals("处理中")) {
			if (getJpo().getDate("OPERATETIME") == null) {
				throw new MroException("", "还未开始作业!");
			}
			if (getDate("COMPLETETIME") != null) {
				throw new MroException("", "已经处理完成!");
			}
			// 当前系统时间
			Date curdate = MroServer.getMroServer().getDate();
			getJpo().setValue("COMPLETETIME", curdate,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.SAVE();
			gotoTab(3);// 切换标签页
		} else {
			throw new MroException("当前状态无法操作！");
		}
	}

	/**
	 * 接收三包订单 <功能描述>
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public int GETZSYWJH() throws MroException, IOException {
		this.getAppBean().SAVE();
		// 工单状态
		String status = this.getJpo().getString("status");
		String[] statuses = { "技术主管审核", "审计审核" };
		// 只在以上3种状态下可以操作
		if (!StringUtil.isHaveStr(statuses, status)) {
			throw new MroException("servorder", "statusnoaction");
		}
		if (!WfControlUtil.isCurUser(this.getJpo())) {
			if (!WorkorderUtil.isInAdminGroup(getJpo().getUserInfo()
					.getLoginID())) {// 非管理员组
				throw new MroException("非工作流执行人无法操作！");
			}
		}
		IJpo jpo = this.getJpo();
		IJpoSet losspartSet = getJpo().getJpoSet("JXTASKLOSSPART");
		IJpoSet exchangeSet = getJpo().getJpoSet("FAILURELIB").getJpo()
				.getJpoSet("EXCHANGERECORD");
		// 工单编号
		String ordernum = jpo.getString("ORDERNUM");

		// 判断是否进行留用观察判断
		boolean flag = false;
		boolean flag2 = false;
		if (exchangeSet != null && exchangeSet.count() > 0) {

			for (int idx = 0; idx < exchangeSet.count(); idx++) {
				IJpo ex = exchangeSet.getJpo(idx);
				// 现场留用观察
				if (SddqConstant.FAIL_DEALMODE_RETENTION.equals(ex
						.getString("dealmode"))) {
					flag2 = true;
					// 未填写留用意见
					if (StringUtil.isStrEmpty(ex.getString("ISAGREESTAY"))) {
						flag = true;
						break;
					}
				}
			}

		}
		if (!flag) {

			if (losspartSet != null && losspartSet.count() > 0) {

				for (int idx = 0; idx < losspartSet.count(); idx++) {
					IJpo loss = losspartSet.getJpo(idx);
					// 现场留用观察
					if (SddqConstant.FAIL_DEALMODE_RETENTION.equals(loss
							.getString("dealmode"))) {
						// 未填写留用意见
						if (StringUtil
								.isStrEmpty(loss.getString("ISAGREESTAY"))) {
							flag = true;
							break;
						}
					}
				}

			}
		}

		if (!flag2) {
			if (losspartSet != null && losspartSet.count() > 0) {

				for (int idx = 0; idx < losspartSet.count(); idx++) {
					IJpo loss = losspartSet.getJpo(idx);
					// 现场留用观察
					if (SddqConstant.FAIL_DEALMODE_RETENTION.equals(loss
							.getString("dealmode"))) {
						flag2 = true;
					}
				}

			}
		}

		if (flag) {
			throw new MroException("请先填写留用观察意见！");
		}

		// 需要调用309接口
		if (flag2 && !getJpo().getBoolean("iface309")) {
			String result = iface309();
			if ("E".equals(result)) {
				throw new AppException("", "309接口出错：" + result);
			}else {// 接口访问成功

				/* 上下车操作 */
				// 耗损件记录子表
				IJpoSet consumeSet = MroServer.getMroServer().getSysJpoSet(
						"JXTASKLOSSPART", "JXTASKNUM='" + ordernum + "'");
				if (consumeSet != null && consumeSet.count() > 0) {
					WorkorderUtil.consumeUpDown(consumeSet, jpo);
				}
				// 序列号件上下车记录子表
				IJpoSet exchangeRcdSet = MroServer.getMroServer().getSysJpoSet(
						"EXCHANGERECORD", "FAILUREORDERNUM='" + ordernum + "'");
				if (exchangeRcdSet != null && exchangeRcdSet.count() > 0) {
					WorkorderUtil.swapHistory(exchangeRcdSet, jpo);
				}
			}
		}

		// 调用三包接口
		//if(!flag2){

			String ret = WorkorderUtil.tomsg(jpo);
			// 接口出错
			if (!"S".equals(ret)) {
				showMsgbox("错误", "三包订单接口访问出错:" + ret);
			} else {// 接口访问成功

				/* 上下车操作 */
				// 耗损件记录子表
				IJpoSet consumeSet = MroServer.getMroServer().getSysJpoSet(
						"JXTASKLOSSPART", "JXTASKNUM='" + ordernum + "'");
				if (consumeSet != null && consumeSet.count() > 0) {
					WorkorderUtil.consumeUpDown(consumeSet, jpo);
				}
				// 序列号件上下车记录子表
				IJpoSet exchangeRcdSet = MroServer.getMroServer().getSysJpoSet(
						"EXCHANGERECORD", "FAILUREORDERNUM='" + ordernum + "'");
				if (exchangeRcdSet != null && exchangeRcdSet.count() > 0) {
					WorkorderUtil.swapHistory(exchangeRcdSet, jpo);
				}
			}
		//}


		this.SAVE();
		this.reloadPage();
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	/**
	 * 
	 * 发送数据到QMS
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public int SENDTOQMS() throws MroException, IOException {
		// if (!("技术主管审核".equals(getString("status")) || "审计审核"
		// .equals(getString("status")))) {
		// throw new MroException("servorder", "statusnoaction");
		// }
		// 调用QMS接口
		WorkorderUtil.sendToQMS(getJpo());
		this.SAVE();

		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	/**
	 * 
	 * 获取TMS数据
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * @throws IOException
	 * 
	 */
	public int GETTMSDATA() throws MroException, IOException {
		String[] status = { "处理中", "技术主管审核", "审计审核", "审核驳回", "技术主管驳回", "待审核" };
		if ((!StringUtil.isHaveStr(status, getString("status")))
				&& !WorkorderUtil.isInAdminGroup(getUserName())) {

			throw new MroException("service", "cannotoperate");// 当前状态无法操作

		}
		WorkorderUtil.getTMSData(getJpo());
		this.SAVE();
		return GWConstant.ACCESS_SAMEMETHOD;
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
		String[] status = { "草稿", "已派工" };
		if (StringUtil.isHaveStr(status, getString("status"))) {
			throw new MroException("servorder", "statusnoaction");
		}
		String diagnoseUrl = IFUtil.getIfServiceInfo("bigdata.diagnoseurl");
		String response = "";
		String num = "";

		// 故障来源文件工单
		String sourceOrderNum = getString("ORDERNUM");
		// 主故障件
		String failureItemCode = getString("FAILURELIB.PRODUCTCODE");

		// 下载地址
		String failureFileURL = getString("FAILURELIB.FAULTDATAURL");
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
			IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_NO,
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

	/**
	 * 
	 * 复制工单
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public int DUPLICATEORDER() throws MroException {
		// 不可复制新建的工单
		if (getJpo().isNew()) {
			throw new MroException("workorder", "cannotdump");
		}
		// 旧工单编号
		String office = getAppBean().getJpo().getString("whichoffice");
		// 新工单编号
		String newOrderNum = WorkorderUtil.generateOrdernum(office,
				getAppBean().getAppName(),
				getAppBean().getJpo().getString("type"));
		// 复制工单
		this.duplicate();

		IJpo dupJpo = getAppBean().getJpo();// 复制的jpo
		// 设置新工单编号
		dupJpo.setValue("ordernum", newOrderNum);
		// 需要清除数据的字段
		String[] clearAttrs = { "COMPLETETIME", "ACTTASKPERSON",
				"AFTREPAIRRUNTIME", "ISREADSAFEREQ", "FAULTCOMPONENTSQN",
				"FAULTCOMPLOTNUM", "FAULTCOMPITEMNUM", "FAULTCOMPDEALMODE",
				"FAULTCOMPASSETNUM", "FAULTCOMPAPARTNUM" };
		for (String attr : clearAttrs) {
			dupJpo.setValueNull(attr, GWConstant.P_NOVALIDATION);// 清除数据
		}
		dupJpo.setFieldFlag("FAULTCOMPLOTNUM", GWConstant.S_REQUIRED, false);
		dupJpo.setValue("iface309", 0, GWConstant.P_NOVALIDATION);
		// 清空故障件上级子表
		IJpoSet superSet = dupJpo.getJpoSet("SUPERIORASSET");
		if (superSet != null && superSet.count() > 0) {
			superSet.deleteAll();
		}
		dupJpo.setValue("SALES", "Z010");// 设置三包销售部门
		// 新增故障记录
		IJpoSet failSet = dupJpo.getJpoSet("FAILURELIB");
		IJpo failure = null;
		if (failSet.isEmpty()) {
			failure = failSet.addJpo();
		} else {
			failure = failSet.getJpo();
		}
		failure.setValue("FAILUREORDERNUM", newOrderNum);

		try {
			// this.reloadCurrTab();
			this.reloadPage();
			this.getAppBean().selectRow(0);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

	@Override
	public void duplicate() throws MroException {
		super.duplicate();
	}

	/**
	 * 
	 * 更新配置信息
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * @throws IOException
	 * 
	 */
	public void GXQMSPZ() throws MroException, IOException {

		String repairnum = this.getJpo().getString("QMS_NUM");
		if (StringUtil.isStrNotEmpty(repairnum)) {

			IJpoSet repairconfigSet = MroServer.getMroServer().getJpoSet(
					"QMSREPAIRCONFIG",
					MroServer.getMroServer().getSystemUserServer());
			repairconfigSet.setQueryWhere("qmsrepairnum ='" + repairnum + "'");
			repairconfigSet.reset();
			for (int index = 0; index < repairconfigSet.count(); index++) {
				IJpo jpo = repairconfigSet.getJpo(index);
				String itemnum = jpo.getString("faultitemnum");// 故障子项图号/物料编码
				String sqn = jpo.getString("faultitemsn");// 故障子项序列号
				String newsqn = jpo.getString("NEWITEMSN");// 更换件序列号
				if (!SddqConstant.GZPZ_STATUS_YCL.equals(jpo
						.getString("STATUS"))) {
					if (StringUtil.isStrNotEmpty(sqn)) {

						if (StringUtil.isStrNotEmpty(itemnum)) {
							IJpoSet assetJpoSet = MroServer.getMroServer()
									.getSysJpoSet("ASSET");
							assetJpoSet.setQueryWhere("sqn='" + sqn
									+ "' and itemnum ='" + itemnum + "'");
							assetJpoSet.reset();
							if (assetJpoSet != null && assetJpoSet.count() > 0) {
								IJpo asset = assetJpoSet.getJpo(0);
								if (StringUtil.isStrNotEmpty(newsqn)) {
									if (!ItemUtil.getAssetInfo(newsqn, itemnum)) {
										// 校验新的产品序列号是否存在
										asset.setValue(
												"sqn",
												newsqn,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue(
												"status",
												SddqConstant.GZPZ_STATUS_YCL,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue("ERRMSG", "");
									} else {
										jpo.setValue(
												"status",
												SddqConstant.GZPZ_STATUS_CLSB,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue("ERRMSG", "系统中已存在产品序列号："
												+ newsqn);
									}
								}
							} else {
								jpo.setValue("status",
										SddqConstant.GZPZ_STATUS_CLSB,
										GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								jpo.setValue("ERRMSG", "系统中不存在产品序列号为：" + sqn
										+ " 物料编码为：" + itemnum + "的产品");
							}
							assetJpoSet.save();
						} else {
							IJpoSet assetJpoSet = MroServer.getMroServer()
									.getSysJpoSet("ASSET");
							assetJpoSet.setQueryWhere("sqn='" + sqn + "'");
							assetJpoSet.reset();
							if (assetJpoSet != null && assetJpoSet.count() > 0) {
								IJpo asset = assetJpoSet.getJpo(0);
								if (StringUtil.isStrNotEmpty(newsqn)) {
									if (!ItemUtil.getAssetInfo(newsqn, "")) {
										// 校验新的产品序列号是否存在
										asset.setValue(
												"sqn",
												newsqn,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue(
												"status",
												SddqConstant.GZPZ_STATUS_YCL,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue("ERRMSG", "");
									} else {
										// 处理信息
										jpo.setValue(
												"status",
												SddqConstant.GZPZ_STATUS_CLSB,
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										jpo.setValue("ERRMSG", "系统中已存在产品序列号："
												+ newsqn);
									}
								}
								assetJpoSet.save();
							}
						}
					}
				}
			}
			repairconfigSet.save();
			super.SAVE();
		}
	}

	/**
	 * 
	 * 上下车测试方法
	 * 
	 * @return
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public int TESTSWAP() throws MroException, IOException {

		IJpo workorder = getAppBean().getJpo();
		String ordernum = workorder.getString("ORDERNUM");
		IJpoSet consumeSet = MroServer.getMroServer().getSysJpoSet(
				"JXTASKLOSSPART", "jxtasknum='" + ordernum + "'");
		workorder.setValue("status", "关闭", GWConstant.P_NOVALIDATION);
		// 耗损件上下车
		WorkorderUtil.consumeUpDown(consumeSet, workorder);

		// 周转件上下车
		IJpoSet exSet = MroServer.getMroServer().getSysJpoSet("EXCHANGERECORD",
				"failureordernum='" + ordernum + "'");
		WorkorderUtil.swapHistory(exSet, workorder);

		getAppBean().SAVE();
		return GWConstant.ACCESS_SAMEMETHOD;
	}

	public String iface309() throws MroException {

		// 工单jpo
		IJpo order = getJpo();
		String num = "";
		String retu = "";
		try {

			String mroperson = getJpo().getUserServer().getUserInfo()
					.getLoginID().toUpperCase();
			java.util.Date mrodate = MroServer.getMroServer().getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String createDate = sdf.format(mrodate);

			String user = IFUtil.getIfServiceInfo("erp.user");
			String pwd = IFUtil.getIfServiceInfo("erp.pwd");
			ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub service = new ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub();
			// 认证代码 start
			Authenticator auth = new Authenticator();
			auth.setUsername(user);
			auth.setPassword(pwd);
			service._getServiceClient().getOptions()
					.setProperty(HTTPConstants.AUTHENTICATE, auth);
			// 认证代码 end
			ZtfunWmsBasisFunction param = new ZtfunWmsBasisFunction();
			Char3 movetype = new Char3();// 参数--移动类型
			Char10 person = new Char10();// 参数--点收人
			Char4 werks = new Char4();// 参数--工厂
			com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Date budat = new com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Date();// 参数--过账日期
			Char18 Material = new Char18();// 输入数据--物料编码
			Char4 StgeLoc = new Char4();// 输入数据--库存地点
			Char10 Batch = new Char10();// 输入数据--批次号
			Quantum133 EntryQnt = new Quantum133();// 输入数据--数量
			Unit3 EntryUom = new Unit3();// 输入数据--计量单位
			Char10 Costcenter = new Char10();// 输入数据--成本中心
			Char12 Orderid = new Char12();// 输入数据--生产订单号
			Numeric10 ReservNo = new Numeric10();// 输入数据--预留号
			Numeric4 ResItem = new Numeric4();// 输入数据--预留行号
			Char4 Plant = new Char4();
			Char3 MoveType = new Char3();
			Char1 StckType = new Char1();
			Char1 SpecStock = new Char1();
			Char10 Vendor = new Char10();
			Char10 Customer = new Char10();
			Char10 SalesOrd = new Char10();
			Numeric6 SOrdItem = new Numeric6();
			Numeric4 SchedLine = new Numeric4();
			Char10 ValType = new Char10();
			Char3 EntryUomIso = new Char3();
			Quantum133 PoPrQnt = new Quantum133();
			Unit3 OrderprUn = new Unit3();
			Char3 OrderprUnIso = new Char3();
			Char10 PoNumber = new Char10();
			Numeric5 PoItem = new Numeric5();
			Char2 Shipping = new Char2();
			Char2 CompShip = new Char2();
			Char1 NoMoreGr = new Char1();
			Char50 ItemText = new Char50();
			Char12 GrRcpt = new Char12();
			Char25 UnloadPt = new Char25();
			Numeric4 OrderItno = new Numeric4();
			Char2 CalcMotive = new Char2();
			Char12 AssetNo = new Char12();
			Char4 SubNumber = new Char4();
			Char1 ResType = new Char1();
			Char1 Withdrawn = new Char1();
			Char18 MoveMat = new Char18();
			Char4 MovePlant = new Char4();
			Char4 MoveStloc = new Char4();
			Char10 MoveBatch = new Char10();
			Char10 MoveValType = new Char10();
			Char1 MvtInd = new Char1();
			Numeric4 MoveReas = new Numeric4();
			Char8 RlEstKey = new Char8();
			com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Date RefDate = new com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Date();
			Char12 CostObj = new Char12();
			Numeric10 ProfitSegmNo = new Numeric10();
			Char10 ProfitCtr = new Char10();
			Char24 WbsElem = new Char24();
			Char12 Network = new Char12();
			Char4 Activity = new Char4();
			Char10 PartAcct = new Char10();
			Decimal234 AmountLc = new Decimal234();
			Decimal234 AmountSv = new Decimal234();
			Numeric4 RefDocYr = new Numeric4();
			Char10 RefDoc = new Char10();
			Numeric4 RefDocIt = new Numeric4();
			com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Date Expirydate = new com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Date();
			com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Date ProdDate = new com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.Date();
			Char10 Fund = new Char10();
			Char16 FundsCtr = new Char16();
			Char14 CmmtItem = new Char14();
			Char10 ValSalesOrd = new Char10();
			Numeric6 ValSOrdItem = new Numeric6();
			Char24 ValWbsElem = new Char24();
			Char10 GlAccount = new Char10();
			Char1 IndProposeQuanx = new Char1();
			Char1 Xstob = new Char1();
			Char18 EanUpc = new Char18();
			Char10 DelivNumbToSearch = new Char10();
			Numeric6 DelivItemToSearch = new Numeric6();
			Char1 SerialnoAutoNumberassignment = new Char1();
			Char15 Vendrbatch = new Char15();
			Char3 StgeType = new Char3();
			Char10 StgeBin = new Char10();
			Decimal30 SuPlStck1 = new Decimal30();
			Quantum133 StUnQtyy1 = new Quantum133();
			Char3 StUnQtyy1Iso = new Char3();
			Char3 Unittype1 = new Char3();
			Decimal30 SuPlStck2 = new Decimal30();
			Quantum133 StUnQtyy2 = new Quantum133();
			Char3 StUnQtyy2Iso = new Char3();
			Char3 Unittype2 = new Char3();
			Char3 StgeTypePc = new Char3();
			Char10 StgeBinPc = new Char10();
			Char1 NoPstChgnt = new Char1();
			Char10 GrNumber = new Char10();
			Char3 StgeTypeSt = new Char3();
			Char10 StgeBinSt = new Char10();
			Char10 MatdocTrCancel = new Char10();
			Numeric4 MatitemTrCancel = new Numeric4();
			Numeric4 MatyearTrCancel = new Numeric4();
			Char1 NoTransferReq = new Char1();
			Char12 CoBusproc = new Char12();
			Char6 Acttype = new Char6();
			Char10 SupplVend = new Char10();
			Char40 MaterialExternal = new Char40();
			Char32 MaterialGuid = new Char32();
			Char10 MaterialVersion = new Char10();
			Char40 MoveMatExternal = new Char40();
			Char32 MoveMatGuid = new Char32();
			Char10 MoveMatVersion = new Char10();
			Char4 FuncArea = new Char4();
			Char4 TrPartBa = new Char4();
			Char4 ParCompco = new Char4();
			Char10 DelivNumb = new Char10();
			Numeric6 DelivItem = new Numeric6();
			Numeric3 NbSlips = new Numeric3();
			Char1 NbSlipsx = new Char1();
			Char1 GrRcptx = new Char1();
			Char1 UnloadPtx = new Char1();
			Char1 SpecMvmt = new Char1();
			Char20 GrantNbr = new Char20();
			Char24 CmmtItemLong = new Char24();
			Char16 FuncAreaLong = new Char16();
			Numeric6 LineId = new Numeric6();
			Numeric6 ParentId = new Numeric6();
			Numeric2 LineDepth = new Numeric2();
			Quantum133 Quantity = new Quantum133();
			Unit3 BaseUom = new Unit3();
			Char40 Longnum = new Char40();

			movetype.setChar3("309");// 参数--移动类型311

			person.setChar10(mroperson);// 参数--点收入
			werks.setChar4("1020");// 参数--工厂
			budat.setDate(createDate);// 参数--过账日期
			TableOfBapi2017GmItemCreate table = new TableOfBapi2017GmItemCreate();
			IJpoSet exchangeSet = MroServer.getMroServer().getSysJpoSet(
					"EXCHANGERECORD",
					"FAILUREORDERNUM='" + order.getString("ordernum")
							+ "' and ISCUSTITEM=0 and dealmode='"
							+ SddqConstant.FAIL_DEALMODE_RETENTION
							+ "' and isagreestay='同意' and itemnum<>newitemnum");
			if (exchangeSet != null && exchangeSet.count() > 0) {

				for (int i = 0; i < exchangeSet.count(); i++) {
					IJpo exchange = exchangeSet.getJpo(i);
					String itemnum = exchange.getString("itemnum");// 物料编码
					String newitemnum = exchange.getString("newitemnum");// 物料编码
					String newlotnum = exchange.getString("newlotnum");// 批次号
					String unit = exchange.getString("item.ORDERUNIT");// 计量单位
					// String lotnum=jpo.getString("lotnum");
					String lotnum = "1";
					String inlocation = exchange
							.getString("location.STOREROOMPARENT");
					String outlocation = exchange
							.getString("newloc.STOREROOMPARENT");

					Bapi2017GmItemCreate tableobject = new Bapi2017GmItemCreate();

					Material.setChar18(newitemnum);// 输入数据--发出物料编码
					StgeLoc.setChar4(outlocation);// 输入数据--发出库存地点
					Batch.setChar10(newlotnum);// 输入数据--批次号
					EntryQnt.setQuantum133(new BigDecimal(1.00));
					// 输入数据--数量
					EntryUom.setUnit3(unit);// 输入数据--计量单位
					MoveMat.setChar18(itemnum);// 输入数据--接收物料编码
					MoveStloc.setChar4(inlocation);// 输入数据--接收库存地点
					MoveBatch.setChar10(lotnum);// 输入数据--接收批次号

					Plant.setChar4("1010");
					Costcenter.setChar10("");
					Orderid.setChar12("");
					ReservNo.setNumeric10("");
					ResItem.setNumeric4("");
					MoveReas.setNumeric4("");
					MoveType.setChar3("");
					StckType.setChar1("");
					SpecStock.setChar1("");
					Vendor.setChar10("");
					Customer.setChar10("");
					SalesOrd.setChar10("");
					SOrdItem.setNumeric6("");
					SchedLine.setNumeric4("");
					ValType.setChar10("");
					EntryUomIso.setChar3("");
					PoPrQnt.setQuantum133(new BigDecimal(0.000));
					OrderprUn.setUnit3("");
					OrderprUnIso.setChar3("");
					PoNumber.setChar10("");
					PoItem.setNumeric5("");
					Shipping.setChar2("");
					CompShip.setChar2("");
					NoMoreGr.setChar1("");
					ItemText.setChar50("");
					GrRcpt.setChar12("");
					UnloadPt.setChar25("");
					OrderItno.setNumeric4("");
					CalcMotive.setChar2("");
					AssetNo.setChar12("");
					SubNumber.setChar4("");
					ResType.setChar1("");
					Withdrawn.setChar1("");
					MovePlant.setChar4("");
					MoveValType.setChar10("");
					MvtInd.setChar1("");
					RlEstKey.setChar8("");
					RefDate.setDate("0000-00-00");
					CostObj.setChar12("");
					ProfitSegmNo.setNumeric10("");
					ProfitCtr.setChar10("");
					WbsElem.setChar24("");
					Network.setChar12("");
					Activity.setChar4("");
					PartAcct.setChar10("");
					AmountLc.setDecimal234(new BigDecimal(0.000));
					AmountSv.setDecimal234(new BigDecimal(0.000));
					RefDocYr.setNumeric4("");
					RefDoc.setChar10("");
					RefDocIt.setNumeric4("");
					Expirydate.setDate("0000-00-00");
					ProdDate.setDate("0000-00-00");
					Fund.setChar10("");
					FundsCtr.setChar16("");
					CmmtItem.setChar14("");
					ValSalesOrd.setChar10("");
					ValSOrdItem.setNumeric6("");
					ValWbsElem.setChar24("");
					GlAccount.setChar10("");
					IndProposeQuanx.setChar1("");
					Xstob.setChar1("");
					EanUpc.setChar18("");
					DelivNumbToSearch.setChar10("");
					DelivItemToSearch.setNumeric6("");
					SerialnoAutoNumberassignment.setChar1("");
					Vendrbatch.setChar15("");
					StgeType.setChar3("");
					StgeBin.setChar10("");
					SuPlStck1.setDecimal30(new BigDecimal(0.000));
					StUnQtyy1.setQuantum133(new BigDecimal(0.000));
					StUnQtyy1Iso.setChar3("");
					Unittype1.setChar3("");
					SuPlStck2.setDecimal30(new BigDecimal(0.000));
					StUnQtyy2.setQuantum133(new BigDecimal(0.000));
					StUnQtyy2Iso.setChar3("");
					Unittype2.setChar3("");
					StgeTypePc.setChar3("");
					StgeBinPc.setChar10("");
					NoPstChgnt.setChar1("");
					GrNumber.setChar10("");
					StgeTypeSt.setChar3("");
					StgeBinSt.setChar10("");
					MatdocTrCancel.setChar10("");
					MatitemTrCancel.setNumeric4("");
					MatyearTrCancel.setNumeric4("");
					NoTransferReq.setChar1("");
					CoBusproc.setChar12("");
					Acttype.setChar6("");
					SupplVend.setChar10("");
					MaterialExternal.setChar40("");
					MaterialGuid.setChar32("");
					MaterialVersion.setChar10("");
					MoveMatExternal.setChar40("");
					MoveMatGuid.setChar32("");
					MoveMatVersion.setChar10("");
					FuncArea.setChar4("");
					TrPartBa.setChar4("");
					ParCompco.setChar4("");
					DelivNumb.setChar10("");
					DelivItem.setNumeric6("");
					NbSlips.setNumeric3("");
					NbSlipsx.setChar1("");
					GrRcptx.setChar1("");
					UnloadPtx.setChar1("");
					SpecMvmt.setChar1("");
					GrantNbr.setChar20("");
					CmmtItemLong.setChar24("");
					FuncAreaLong.setChar16("");
					LineId.setNumeric6("");
					ParentId.setNumeric6("");
					LineDepth.setNumeric2("");
					Quantity.setQuantum133(new BigDecimal(0.000));
					BaseUom.setUnit3("");
					Longnum.setChar40("");

					tableobject.setMaterial(Material);
					tableobject.setPlant(Plant);
					tableobject.setStgeLoc(StgeLoc);
					tableobject.setBatch(Batch);
					tableobject.setMoveType(MoveType);
					tableobject.setStckType(StckType);
					tableobject.setSpecStock(SpecStock);
					tableobject.setVendor(Vendor);
					tableobject.setCustomer(Customer);
					tableobject.setSalesOrd(SalesOrd);
					tableobject.setSOrdItem(SOrdItem);
					tableobject.setSchedLine(SchedLine);
					tableobject.setValType(ValType);
					tableobject.setEntryQnt(EntryQnt);
					tableobject.setEntryUom(EntryUom);
					tableobject.setEntryUomIso(EntryUomIso);
					tableobject.setPoPrQnt(PoPrQnt);
					tableobject.setOrderprUn(OrderprUn);
					tableobject.setOrderprUnIso(OrderprUnIso);
					tableobject.setPoNumber(PoNumber);
					tableobject.setPoItem(PoItem);
					tableobject.setShipping(Shipping);
					tableobject.setCompShip(CompShip);
					tableobject.setNoMoreGr(NoMoreGr);
					tableobject.setItemText(ItemText);
					tableobject.setGrRcpt(GrRcpt);
					tableobject.setUnloadPt(UnloadPt);
					tableobject.setCostcenter(Costcenter);
					tableobject.setOrderid(Orderid);
					tableobject.setOrderItno(OrderItno);
					tableobject.setCalcMotive(CalcMotive);
					tableobject.setAssetNo(AssetNo);
					tableobject.setSubNumber(SubNumber);
					tableobject.setReservNo(ReservNo);
					tableobject.setResItem(ResItem);
					tableobject.setResType(ResType);
					tableobject.setWithdrawn(Withdrawn);
					tableobject.setMoveMat(MoveMat);
					tableobject.setMovePlant(MovePlant);
					tableobject.setMoveStloc(MoveStloc);
					tableobject.setMoveBatch(MoveBatch);
					tableobject.setMoveValType(MoveValType);
					tableobject.setMvtInd(MvtInd);
					tableobject.setMoveReas(MoveReas);
					tableobject.setRlEstKey(RlEstKey);
					tableobject.setRefDate(RefDate);
					tableobject.setCostObj(CostObj);
					tableobject.setProfitSegmNo(ProfitSegmNo);
					tableobject.setProfitCtr(ProfitCtr);
					tableobject.setWbsElem(WbsElem);
					tableobject.setNetwork(Network);
					tableobject.setActivity(Activity);
					tableobject.setPartAcct(PartAcct);
					tableobject.setAmountLc(AmountLc);
					tableobject.setAmountSv(AmountSv);
					tableobject.setRefDocYr(RefDocYr);
					tableobject.setRefDoc(RefDoc);
					tableobject.setRefDocIt(RefDocIt);
					tableobject.setExpirydate(Expirydate);
					tableobject.setProdDate(ProdDate);
					tableobject.setFund(Fund);
					tableobject.setFundsCtr(FundsCtr);
					tableobject.setCmmtItem(CmmtItem);
					tableobject.setValSalesOrd(ValSalesOrd);
					tableobject.setValSOrdItem(ValSOrdItem);
					tableobject.setValWbsElem(ValWbsElem);
					tableobject.setGlAccount(GlAccount);
					tableobject.setIndProposeQuanx(IndProposeQuanx);
					tableobject.setXstob(Xstob);
					tableobject.setEanUpc(EanUpc);
					tableobject.setDelivNumbToSearch(DelivNumbToSearch);
					tableobject.setDelivItemToSearch(DelivItemToSearch);
					tableobject
							.setSerialnoAutoNumberassignment(SerialnoAutoNumberassignment);
					tableobject.setVendrbatch(Vendrbatch);
					tableobject.setStgeType(StgeType);
					tableobject.setStgeBin(StgeBin);
					tableobject.setSuPlStck1(SuPlStck1);
					tableobject.setStUnQtyy1(StUnQtyy1);
					tableobject.setStUnQtyy1Iso(StUnQtyy1Iso);
					tableobject.setUnittype1(Unittype1);
					tableobject.setSuPlStck2(SuPlStck2);
					tableobject.setStUnQtyy2(StUnQtyy2);
					tableobject.setStUnQtyy2Iso(StUnQtyy2Iso);
					tableobject.setUnittype2(Unittype2);
					tableobject.setStgeTypePc(StgeTypePc);
					tableobject.setStgeBinPc(StgeBinPc);
					tableobject.setNoPstChgnt(NoPstChgnt);
					tableobject.setGrNumber(GrNumber);
					tableobject.setStgeTypeSt(StgeTypeSt);
					tableobject.setStgeBinSt(StgeBinSt);
					tableobject.setMatdocTrCancel(MatdocTrCancel);
					tableobject.setMatitemTrCancel(MatitemTrCancel);
					tableobject.setMatyearTrCancel(MatyearTrCancel);
					tableobject.setNoTransferReq(NoTransferReq);
					tableobject.setCoBusproc(CoBusproc);
					tableobject.setActtype(Acttype);
					tableobject.setSupplVend(SupplVend);
					tableobject.setMaterialExternal(MaterialExternal);
					tableobject.setMaterialGuid(MaterialGuid);
					tableobject.setMaterialVersion(MaterialVersion);
					tableobject.setMoveMatExternal(MoveMatExternal);
					tableobject.setMoveMatGuid(MoveMatGuid);
					tableobject.setMoveMatVersion(MoveMatVersion);
					tableobject.setFuncArea(FuncArea);
					tableobject.setTrPartBa(TrPartBa);
					tableobject.setParCompco(ParCompco);
					tableobject.setDelivNumb(DelivNumb);
					tableobject.setDelivItem(DelivItem);
					tableobject.setNbSlips(NbSlips);
					tableobject.setNbSlipsx(NbSlipsx);
					tableobject.setGrRcptx(GrRcptx);
					tableobject.setUnloadPtx(UnloadPtx);
					tableobject.setSpecMvmt(SpecMvmt);
					tableobject.setGrantNbr(GrantNbr);
					tableobject.setCmmtItemLong(CmmtItemLong);
					tableobject.setFuncAreaLong(FuncAreaLong);
					tableobject.setLineId(LineId);
					tableobject.setParentId(ParentId);
					tableobject.setLineDepth(LineDepth);
					tableobject.setQuantity(Quantity);
					tableobject.setBaseUom(BaseUom);
					tableobject.setLongnum(Longnum);
					table.addItem(tableobject);

				}
			}

			IJpoSet lossPartSet = MroServer
					.getMroServer()
					.getSysJpoSet(
							"JXTASKLOSSPART",
							"JXTASKNUM='"
									+ order.getString("ORDERNUM")
									+ "' and iscustitem = 0 and dealmode='"
									+ SddqConstant.FAIL_DEALMODE_RETENTION
									+ "' and isagreestay='同意' and downitemnum<>itemnum");

			if (lossPartSet != null && lossPartSet.count() > 0) {
				for (int i = 0; i < lossPartSet.count(); i++) {
					IJpo lossPart = lossPartSet.getJpo(i);
					String itemnum = lossPart.getString("downitemnum");// 物料编码
					String newitemnum = lossPart.getString("itemnum");// 物料编码
					String newlotnum = lossPart.getString("lotnum");// 批次号
					String unit = lossPart.getString("DOWNITEMNUM.ORDERUNIT");// 计量单位
					String lotnum = lossPart.getString("DOWNLOTNUM");
					String inlocation = lossPart
							.getString("UNDERLOC.STOREROOMPARENT");
					String outlocation = lossPart
							.getString("UPLOC.STOREROOMPARENT");

					Bapi2017GmItemCreate tableobject = new Bapi2017GmItemCreate();

					Material.setChar18(newitemnum);// 输入数据--发出物料编码
					StgeLoc.setChar4(outlocation);// 输入数据--发出库存地点
					Batch.setChar10(newlotnum);// 输入数据--批次号
					EntryQnt.setQuantum133(new BigDecimal(1.00));
					// 输入数据--数量
					EntryUom.setUnit3(unit);// 输入数据--计量单位
					MoveMat.setChar18(itemnum);// 输入数据--接收物料编码
					MoveStloc.setChar4(inlocation);// 输入数据--接收库存地点
					MoveBatch.setChar10(lotnum);// 输入数据--接收批次号

					Plant.setChar4("1010");
					Costcenter.setChar10("");
					Orderid.setChar12("");
					ReservNo.setNumeric10("");
					ResItem.setNumeric4("");
					MoveReas.setNumeric4("");
					MoveType.setChar3("");
					StckType.setChar1("");
					SpecStock.setChar1("");
					Vendor.setChar10("");
					Customer.setChar10("");
					SalesOrd.setChar10("");
					SOrdItem.setNumeric6("");
					SchedLine.setNumeric4("");
					ValType.setChar10("");
					EntryUomIso.setChar3("");
					PoPrQnt.setQuantum133(new BigDecimal(0.000));
					OrderprUn.setUnit3("");
					OrderprUnIso.setChar3("");
					PoNumber.setChar10("");
					PoItem.setNumeric5("");
					Shipping.setChar2("");
					CompShip.setChar2("");
					NoMoreGr.setChar1("");
					ItemText.setChar50("");
					GrRcpt.setChar12("");
					UnloadPt.setChar25("");
					OrderItno.setNumeric4("");
					CalcMotive.setChar2("");
					AssetNo.setChar12("");
					SubNumber.setChar4("");
					ResType.setChar1("");
					Withdrawn.setChar1("");
					MovePlant.setChar4("");
					MoveValType.setChar10("");
					MvtInd.setChar1("");
					RlEstKey.setChar8("");
					RefDate.setDate("0000-00-00");
					CostObj.setChar12("");
					ProfitSegmNo.setNumeric10("");
					ProfitCtr.setChar10("");
					WbsElem.setChar24("");
					Network.setChar12("");
					Activity.setChar4("");
					PartAcct.setChar10("");
					AmountLc.setDecimal234(new BigDecimal(0.000));
					AmountSv.setDecimal234(new BigDecimal(0.000));
					RefDocYr.setNumeric4("");
					RefDoc.setChar10("");
					RefDocIt.setNumeric4("");
					Expirydate.setDate("0000-00-00");
					ProdDate.setDate("0000-00-00");
					Fund.setChar10("");
					FundsCtr.setChar16("");
					CmmtItem.setChar14("");
					ValSalesOrd.setChar10("");
					ValSOrdItem.setNumeric6("");
					ValWbsElem.setChar24("");
					GlAccount.setChar10("");
					IndProposeQuanx.setChar1("");
					Xstob.setChar1("");
					EanUpc.setChar18("");
					DelivNumbToSearch.setChar10("");
					DelivItemToSearch.setNumeric6("");
					SerialnoAutoNumberassignment.setChar1("");
					Vendrbatch.setChar15("");
					StgeType.setChar3("");
					StgeBin.setChar10("");
					SuPlStck1.setDecimal30(new BigDecimal(0.000));
					StUnQtyy1.setQuantum133(new BigDecimal(0.000));
					StUnQtyy1Iso.setChar3("");
					Unittype1.setChar3("");
					SuPlStck2.setDecimal30(new BigDecimal(0.000));
					StUnQtyy2.setQuantum133(new BigDecimal(0.000));
					StUnQtyy2Iso.setChar3("");
					Unittype2.setChar3("");
					StgeTypePc.setChar3("");
					StgeBinPc.setChar10("");
					NoPstChgnt.setChar1("");
					GrNumber.setChar10("");
					StgeTypeSt.setChar3("");
					StgeBinSt.setChar10("");
					MatdocTrCancel.setChar10("");
					MatitemTrCancel.setNumeric4("");
					MatyearTrCancel.setNumeric4("");
					NoTransferReq.setChar1("");
					CoBusproc.setChar12("");
					Acttype.setChar6("");
					SupplVend.setChar10("");
					MaterialExternal.setChar40("");
					MaterialGuid.setChar32("");
					MaterialVersion.setChar10("");
					MoveMatExternal.setChar40("");
					MoveMatGuid.setChar32("");
					MoveMatVersion.setChar10("");
					FuncArea.setChar4("");
					TrPartBa.setChar4("");
					ParCompco.setChar4("");
					DelivNumb.setChar10("");
					DelivItem.setNumeric6("");
					NbSlips.setNumeric3("");
					NbSlipsx.setChar1("");
					GrRcptx.setChar1("");
					UnloadPtx.setChar1("");
					SpecMvmt.setChar1("");
					GrantNbr.setChar20("");
					CmmtItemLong.setChar24("");
					FuncAreaLong.setChar16("");
					LineId.setNumeric6("");
					ParentId.setNumeric6("");
					LineDepth.setNumeric2("");
					Quantity.setQuantum133(new BigDecimal(0.000));
					BaseUom.setUnit3("");
					Longnum.setChar40("");

					tableobject.setMaterial(Material);
					tableobject.setPlant(Plant);
					tableobject.setStgeLoc(StgeLoc);
					tableobject.setBatch(Batch);
					tableobject.setMoveType(MoveType);
					tableobject.setStckType(StckType);
					tableobject.setSpecStock(SpecStock);
					tableobject.setVendor(Vendor);
					tableobject.setCustomer(Customer);
					tableobject.setSalesOrd(SalesOrd);
					tableobject.setSOrdItem(SOrdItem);
					tableobject.setSchedLine(SchedLine);
					tableobject.setValType(ValType);
					tableobject.setEntryQnt(EntryQnt);
					tableobject.setEntryUom(EntryUom);
					tableobject.setEntryUomIso(EntryUomIso);
					tableobject.setPoPrQnt(PoPrQnt);
					tableobject.setOrderprUn(OrderprUn);
					tableobject.setOrderprUnIso(OrderprUnIso);
					tableobject.setPoNumber(PoNumber);
					tableobject.setPoItem(PoItem);
					tableobject.setShipping(Shipping);
					tableobject.setCompShip(CompShip);
					tableobject.setNoMoreGr(NoMoreGr);
					tableobject.setItemText(ItemText);
					tableobject.setGrRcpt(GrRcpt);
					tableobject.setUnloadPt(UnloadPt);
					tableobject.setCostcenter(Costcenter);
					tableobject.setOrderid(Orderid);
					tableobject.setOrderItno(OrderItno);
					tableobject.setCalcMotive(CalcMotive);
					tableobject.setAssetNo(AssetNo);
					tableobject.setSubNumber(SubNumber);
					tableobject.setReservNo(ReservNo);
					tableobject.setResItem(ResItem);
					tableobject.setResType(ResType);
					tableobject.setWithdrawn(Withdrawn);
					tableobject.setMoveMat(MoveMat);
					tableobject.setMovePlant(MovePlant);
					tableobject.setMoveStloc(MoveStloc);
					tableobject.setMoveBatch(MoveBatch);
					tableobject.setMoveValType(MoveValType);
					tableobject.setMvtInd(MvtInd);
					tableobject.setMoveReas(MoveReas);
					tableobject.setRlEstKey(RlEstKey);
					tableobject.setRefDate(RefDate);
					tableobject.setCostObj(CostObj);
					tableobject.setProfitSegmNo(ProfitSegmNo);
					tableobject.setProfitCtr(ProfitCtr);
					tableobject.setWbsElem(WbsElem);
					tableobject.setNetwork(Network);
					tableobject.setActivity(Activity);
					tableobject.setPartAcct(PartAcct);
					tableobject.setAmountLc(AmountLc);
					tableobject.setAmountSv(AmountSv);
					tableobject.setRefDocYr(RefDocYr);
					tableobject.setRefDoc(RefDoc);
					tableobject.setRefDocIt(RefDocIt);
					tableobject.setExpirydate(Expirydate);
					tableobject.setProdDate(ProdDate);
					tableobject.setFund(Fund);
					tableobject.setFundsCtr(FundsCtr);
					tableobject.setCmmtItem(CmmtItem);
					tableobject.setValSalesOrd(ValSalesOrd);
					tableobject.setValSOrdItem(ValSOrdItem);
					tableobject.setValWbsElem(ValWbsElem);
					tableobject.setGlAccount(GlAccount);
					tableobject.setIndProposeQuanx(IndProposeQuanx);
					tableobject.setXstob(Xstob);
					tableobject.setEanUpc(EanUpc);
					tableobject.setDelivNumbToSearch(DelivNumbToSearch);
					tableobject.setDelivItemToSearch(DelivItemToSearch);
					tableobject
							.setSerialnoAutoNumberassignment(SerialnoAutoNumberassignment);
					tableobject.setVendrbatch(Vendrbatch);
					tableobject.setStgeType(StgeType);
					tableobject.setStgeBin(StgeBin);
					tableobject.setSuPlStck1(SuPlStck1);
					tableobject.setStUnQtyy1(StUnQtyy1);
					tableobject.setStUnQtyy1Iso(StUnQtyy1Iso);
					tableobject.setUnittype1(Unittype1);
					tableobject.setSuPlStck2(SuPlStck2);
					tableobject.setStUnQtyy2(StUnQtyy2);
					tableobject.setStUnQtyy2Iso(StUnQtyy2Iso);
					tableobject.setUnittype2(Unittype2);
					tableobject.setStgeTypePc(StgeTypePc);
					tableobject.setStgeBinPc(StgeBinPc);
					tableobject.setNoPstChgnt(NoPstChgnt);
					tableobject.setGrNumber(GrNumber);
					tableobject.setStgeTypeSt(StgeTypeSt);
					tableobject.setStgeBinSt(StgeBinSt);
					tableobject.setMatdocTrCancel(MatdocTrCancel);
					tableobject.setMatitemTrCancel(MatitemTrCancel);
					tableobject.setMatyearTrCancel(MatyearTrCancel);
					tableobject.setNoTransferReq(NoTransferReq);
					tableobject.setCoBusproc(CoBusproc);
					tableobject.setActtype(Acttype);
					tableobject.setSupplVend(SupplVend);
					tableobject.setMaterialExternal(MaterialExternal);
					tableobject.setMaterialGuid(MaterialGuid);
					tableobject.setMaterialVersion(MaterialVersion);
					tableobject.setMoveMatExternal(MoveMatExternal);
					tableobject.setMoveMatGuid(MoveMatGuid);
					tableobject.setMoveMatVersion(MoveMatVersion);
					tableobject.setFuncArea(FuncArea);
					tableobject.setTrPartBa(TrPartBa);
					tableobject.setParCompco(ParCompco);
					tableobject.setDelivNumb(DelivNumb);
					tableobject.setDelivItem(DelivItem);
					tableobject.setNbSlips(NbSlips);
					tableobject.setNbSlipsx(NbSlipsx);
					tableobject.setGrRcptx(GrRcptx);
					tableobject.setUnloadPtx(UnloadPtx);
					tableobject.setSpecMvmt(SpecMvmt);
					tableobject.setGrantNbr(GrantNbr);
					tableobject.setCmmtItemLong(CmmtItemLong);
					tableobject.setFuncAreaLong(FuncAreaLong);
					tableobject.setLineId(LineId);
					tableobject.setParentId(ParentId);
					tableobject.setLineDepth(LineDepth);
					tableobject.setQuantity(Quantity);
					tableobject.setBaseUom(BaseUom);
					tableobject.setLongnum(Longnum);
					table.addItem(tableobject);

				}
			}

			param.setMoveType(movetype);
			param.setPerson(person);
			param.setWerks(werks);
			param.setWmBudat(budat);
			param.setTGvitem(table);

			// 存在数据才调用接口
			if (exchangeSet.count() + lossPartSet.count() > 0) {

				num = IFUtil.addIfHistory(IFUtil.MRO_ERP_JKD,
						"故障工单号：" + order.getString("ordernum") + ";传递ERP" + 1
								+ "条;传递数据为：" + JSON.toJSONString(param) + "。",
						IFUtil.TYPE_OUTPUT);// 增加输出记录
				ZtfunWmsBasisFunctionResponse res = service
						.ztfunWmsBasisFunction(param);
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "故障工单号：" + order.getString("ordernum")
								+ ";传递ERP" + 1 + "条;");
				// 设置成功调用接口标记
				getJpo().setValue("iface309", 1, GWConstant.P_NOVALIDATION);
				String message = res.getMessage().toString();
				retu = res.getReturn().toString();
				if (retu.equalsIgnoreCase("E")) {
					retu = message;
				}
				String MATERIALDOCUMENT = res.getMaterialdocument().toString();
				String MATDOCUMENTYEAR = res.getMatdocumentyear().toString();
				JSONArray returnjArray = new JSONArray();
				JSONObject returnrdata = new JSONObject();
				returnrdata.put("message", message);
				returnrdata.put("retu", retu);
				returnrdata.put("MATERIALDOCUMENT", MATERIALDOCUMENT);
				returnrdata.put("MATDOCUMENTYEAR", MATDOCUMENTYEAR);
				returnjArray.add(returnrdata);
				num = IFUtil.addIfHistory(IFUtil.MRO_ERP_JKD,
						returnjArray.toString(), IFUtil.TYPE_INPUT);// 增加输出记录

				if ("S".equals(res.getReturn().toString())) {// 回传成功
					IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS,
							IFUtil.FLAG_YES,
							"故障工单号：" + order.getString("ordernum")
									+ ";接收ERP回传:" + returnjArray.toString()
									+ "。");
				} else {// 回传失败
					IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO,
							"故障工单号：" + order.getString("ordernum")
									+ ";接收ERP回传失败，错误信息：" + message + "。");

				}
			}

		} catch (Exception e) {

			IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES,
					e.getMessage());
			e.printStackTrace();

		}
		return retu;
	}

}
