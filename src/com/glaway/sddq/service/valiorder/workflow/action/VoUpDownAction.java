package com.glaway.sddq.service.valiorder.workflow.action;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 验证工单-上下车操作类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-15]
 * @since [产品/模块版本]
 */
public class VoUpDownAction implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {

		if (jpo != null) {
			IJpoSet rangeSet = jpo.getJpoSet("VALIPRORANGE");// 验证产品范围
			if (rangeSet != null && rangeSet.count() > 0) {
				for (int index = 0; index < rangeSet.count(); index++) {
					IJpo range = rangeSet.getJpo(index);
					// 工单jposet
					IJpoSet orderSet = range.getJpoSet("VALIORDER");
					if (orderSet != null && orderSet.count() > 0) {
						for (int j = 0; j < orderSet.count(); j++) {
							IJpo valiorder = orderSet.getJpo(j);
							if (!"关闭".equals(valiorder.getString("status"))) {
								throw new MroException("还有工单未处理完成！");
							}
							// 上下车操作
							String ordernum = valiorder.getString("ORDERNUM");
							IJpoSet exchangeSet = MroServer.getMroServer()
									.getSysJpoSet("EXCHANGERECORD",
											"TASKORDERNUM='" + ordernum + "'");
							if (!exchangeSet.isEmpty()) {
								WorkorderUtil.swapHistory(exchangeSet,
										valiorder);
							}
						}

					}
				}
			}
		}

	}

}
