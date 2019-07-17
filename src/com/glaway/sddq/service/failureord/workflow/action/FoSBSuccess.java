package com.glaway.sddq.service.failureord.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 判断三包接口是否调用成功
 * 
 * @author zhuhao
 * @version [版本号, 2018年11月22日]
 * @since [产品/模块版本]
 */
public class FoSBSuccess implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {

		if (jpo != null) {
			String dealMethod = jpo.getJpoSet("FAILURELIB").getJpo()
					.getString("DEALMETHOD");
			if (SddqConstant.FAIL_DEALMETHOD_HJCL.equals(dealMethod)
					|| SddqConstant.FAIL_DEALMETHOD_CHANDGH.equals(dealMethod)) {// 换件处理
				String sbStatus = jpo.getString("ERPSTATUS");// 三包订单状态
				if ((!"成功".equals(sbStatus)) || StringUtil.isStrEmpty(sbStatus)) {

					// 判断是否有非客户料
					boolean flag = false;
					// 是否存在返回本部维修
					boolean flag2 = false;
					// 是否调用309接口
					boolean flag3 = false;

					IJpoSet exSet = jpo.getJpoSet("FAILURELIB").getJpo()
							.getJpoSet("EXCHANGERECORD");
					IJpoSet lossSet = jpo.getJpoSet("JXTASKLOSSPART");
					for (int idx = 0; idx < exSet.count(); idx++) {
						if (!exSet.getJpo(idx).getBoolean("ISCUSTITEM")) {
							flag = true;
							if (SddqConstant.FAIL_DEALMODE_BASEREPAIR.equals(exSet
									.getJpo(idx).getString("dealmode"))) {
								flag2 = true;
							}
						}
						// 不返修并且技术主管同意
						if (SddqConstant.FAIL_DEALMODE_RETENTION.equals(exSet
								.getJpo(idx).getString("dealmode"))
								&& ("同意".equals(exSet.getJpo(idx).getString(
										"ISAGREESTAY")))) {
							if(!exSet.getJpo(idx).getString("itemnum").equalsIgnoreCase(exSet
											.getJpo(idx).getString("newitemnum"))){
								flag3 = true;
							}
						}

					}
					for (int j = 0; j < lossSet.count(); j++) {
						if (!lossSet.getJpo(j).getBoolean("ISCUSTITEM")) {
							flag = true;
							if (SddqConstant.FAIL_DEALMODE_BASEREPAIR
									.equals(lossSet.getJpo(j).getString("dealmode"))) {
								flag2 = true;
							}
						}

						// 不返修并且技术主管同意
						if (SddqConstant.FAIL_DEALMODE_RETENTION.equals(lossSet
								.getJpo(j).getString("dealmode"))
								&& ("同意".equals(lossSet.getJpo(j).getString(
										"ISAGREESTAY")))) {
							if(!lossSet.getJpo(j).getString("downitemnum").equalsIgnoreCase(lossSet
									.getJpo(j).getString("itemnum"))){
								flag3 = true;
							}
						}

					}
					if (flag3 && (!jpo.getBoolean("IFACE309"))) {// 未触发309接口

						throw new MroException("请先触发309接口再发送工作流！");

					}
					if (!SddqConstant.WO_STATUS_ZZKGYSH.equals(jpo
							.getString("status"))) {// TODO 临时增加判断条件，旧数据过渡代码
						if (flag && flag2) {
							throw new MroException("请先完成三包业务订单再发送工作流！");
						}
					}
				}
			}
		}

	}

}
