package com.glaway.sddq.service.failurelib.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.page.control.Section;
import com.glaway.mro.page.control.Table;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 故障记录-处理方式字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年12月5日]
 * @since [产品/模块版本]
 */
public class FldDealMethod extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {

		super.action();
		try {
			String[] attrs = { "FAULTCOMPONENTSQN", "FAULTCOMPLOTNUM",
					"FAULTCOMPDEALMODE", "ISMAINFAULTCOMP", "ISNOTICE",
					"ISTECHANALYZE", "FAULTCOMPITEMNUM", "FAULTCOMPASSETNUM" };// 故障件字段
			String[] requiredAttrs = { "FAULTCOMPONENTSQN", "FAULTCOMPLOTNUM",
					"FAULTCOMPDEALMODE" };
			// 处理方式
			String dealmethod = getInputMroType().asString();
			IJpo wo = getJpo().getParent();// 工单jpo
			if (wo != null) {
				Table exchangeTb = null;
				Table consumeTb = null;
				Table superiorTb = null;
				Table chAssetTb = null;
				IJpoSet exchangeSet = getJpo().getJpoSet("EXCHANGERECORD");
				IJpoSet jxtaskLossSet = wo.getJpoSet("JXTASKLOSSPART");
				IJpoSet chAssetSet = wo.getJpoSet("CHASSET");
				if (StringUtil.isStrNotEmpty(dealmethod)) {

					if (MroServer.getMroServer().getSystemUserServer()
							.getMroSession() != null) {

						// 序列号上下车子表
						exchangeTb = (Table) MroServer.getMroServer()
								.getSystemUserServer().getMroSession()
								.getCurrentPage()
								.getControlByXmlId("1534926020763");
						// 非序列号上下车子表
						consumeTb = (Table) MroServer.getMroServer()
								.getSystemUserServer().getMroSession()
								.getCurrentPage()
								.getControlByXmlId("1534925980151");
						// 故障件上级子表
						superiorTb = (Table) MroServer.getMroServer()
								.getSystemUserServer().getMroSession()
								.getCurrentPage()
								.getControlByXmlId("1543817262496");
						// 非换件处理时故障件字段区域
						Section faultSec = (Section) MroServer.getMroServer()
								.getSystemUserServer().getMroSession()
								.getCurrentPage()
								.getControlByXmlId("1543805689797");
						// 串换记录子表
						chAssetTb = (Table) MroServer.getMroServer()
								.getSystemUserServer().getMroSession()
								.getCurrentPage()
								.getControlByXmlId("chassetTable");

						if ((!SddqConstant.FAIL_DEALMETHOD_HJCL
								.equals(dealmethod))
								&& (!SddqConstant.FAIL_DEALMETHOD_CHANDGH
										.equals(dealmethod))) {// 非换件处理、非串换+换件处理
							// 显示故障件上级子表
							if (superiorTb != null) {

								superiorTb.show();
							}
							// 显示故障件字段
							if (faultSec != null) {

								faultSec.show();
							}
							// 隐藏上下车记录子表
							if (exchangeTb != null) {
								// 清空已有数据
								if (exchangeSet != null
										&& exchangeSet.count() > 0) {
									exchangeSet.deleteAll();
								}
								exchangeTb.hide();

							}
							// 隐藏上下车记录子表
							if (consumeTb != null) {
								// 清空已有数据
								if (jxtaskLossSet != null
										&& jxtaskLossSet.count() > 0) {
									jxtaskLossSet.deleteAll();
								}
								consumeTb.hide();

							}

							// 设置字段只读取消
							wo.setFieldFlag(attrs, GWConstant.S_READONLY, false);
							// 设置字段必填
							wo.setFieldFlag(requiredAttrs,
									GWConstant.S_REQUIRED, true);
							// 序列号不为空，取消批次号必填
							if (StringUtil.isStrNotEmpty(wo
									.getString("FAULTCOMPONENTSQN"))) {
								wo.setFieldFlag("FAULTCOMPLOTNUM",
										GWConstant.S_REQUIRED, false);
							}
							// 批次号不为空，取消序列号必填
							if (StringUtil.isStrNotEmpty(wo
									.getString("FAULTCOMPLOTNUM"))) {
								wo.setFieldFlag("FAULTCOMPONENTSQN",
										GWConstant.S_REQUIRED, false);
							}

							// 重大故障
							if (WorkorderUtil.isImpFault(getJpo().getString(
									"FAULTCONSEQ"))) {
								// 将是否通报、是否技术分析勾选并只读
								wo.setValue("ISNOTICE", 1);
								wo.setValue("ISTECHANALYZE", 1);
								wo.setFieldFlag("ISNOTICE",
										GWConstant.S_READONLY, true);
								wo.setFieldFlag("ISTECHANALYZE",
										GWConstant.S_READONLY, true);

							}

							// 设置子表只读
							jxtaskLossSet.setFlag(GWConstant.S_READONLY, true);
							exchangeSet.setFlag(GWConstant.S_READONLY, true);

						} else {// 换件处理、串换+换件处理

							// 隐藏故障件子表
							if (superiorTb != null) {
								superiorTb.hide();
							}
							// 隐藏故障件字段
							if (faultSec != null) {
								faultSec.hide();
							}
							// 显示上下车记录子表
							if (exchangeTb != null) {
								exchangeTb.show();
							}
							// 显示上下车记录子表
							if (consumeTb != null) {
								consumeTb.show();
							}

							// 清空已选择的故障件信息
							for (String attr : attrs) {

								wo.setValueNull(attr,
										GWConstant.P_NOVALIDATION_AND_NOACTION);

							}

							// 删除故障件上级子表
							wo.getJpoSet("SUPERIORASSET").deleteAll();
							wo.setFieldFlag(requiredAttrs,
									GWConstant.S_REQUIRED, false);
							wo.setFieldFlag(attrs, GWConstant.S_READONLY, true);
							jxtaskLossSet.setFlag(GWConstant.S_READONLY, false);
							exchangeSet.setFlag(GWConstant.S_READONLY, false);
						}

						// 串换处理
						if (SddqConstant.FAIL_DEALMETHOD_CHCL
								.equals(dealmethod)
								|| SddqConstant.FAIL_DEALMETHOD_CHANDGH
										.equals(dealmethod)) {

							// 显示串换子表
							if (chAssetTb != null) {
								chAssetTb.show();
							}
						} else {
							// 隐藏串换子表
							if (chAssetTb != null) {
								// 清空已有数据
								if (chAssetSet != null
										&& chAssetSet.count() > 0) {
									chAssetSet.deleteAll();
								}
								chAssetTb.hide();
							}
						}

						// 刷新表格状态
						List<PageControl> pageControls = new ArrayList<PageControl>();
						pageControls.add(exchangeTb);
						pageControls.add(consumeTb);
						pageControls.add(superiorTb);
						pageControls.add(chAssetTb);

						for (PageControl pageCtrl : pageControls) {
							if (pageCtrl != null) {
								pageCtrl.loadData();
							}
						}

					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
