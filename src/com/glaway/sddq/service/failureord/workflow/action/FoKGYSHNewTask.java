package com.glaway.sddq.service.failureord.workflow.action;

import java.util.Vector;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.EmailUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 库管员审核 操作类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月8日]
 * @since [产品/模块版本]
 */
public class FoKGYSHNewTask implements ActionCustomClass {

	@Override
	public void executeCustomAction(IJpo jpo, String arg1) throws MroException {
		// TODO Auto-generated method stub
		if (jpo != null) {
			String analysisrepneed = jpo
					.getString("FAILURELIB.ANALYSISREPNEED");// 获取客户是否需要分析报告
			jpo.setValue("status", "库管员审核",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			String ordernum = jpo.getString("ORDERNUM");// 工单号

			String displayname = jpo.getString("SERVENGINEER.DISPLAYNAME");// 现场处理人
			String servcompany = jpo.getString("SERVCOMPANY.CUSTNAME");// 服务单位
			String projectnum = jpo.getString("PROJECTNUM");// 项目编号

			if (analysisrepneed.equals("是")) {
				Vector<String> personall = new Vector();
				Vector<String> personVector = WorkorderUtil.getProjectRole(
						projectnum, "售后技术经理");
				for (int i = 0; i < personVector.size(); i++) {
					personall.add(personVector.get(i));
				}

				Vector<String> personxmVector = WorkorderUtil.getProjectRole(
						projectnum, "售后项目经理");
				for (int i = 0; i < personxmVector.size(); i++) {
					personall.add(personxmVector.get(i));
				}

				Vector<String> personzlVector = WorkorderUtil.getProjectRole(
						projectnum, "售后质量经理");
				for (int i = 0; i < personzlVector.size(); i++) {
					personall.add(personzlVector.get(i));
				}

				String[] object = personall
						.toArray(new String[personall.size()]);

				// 故障分析报告邮件提醒
				EmailUtil.gzreporttx(ordernum, displayname, personVector,
						object, servcompany);
			}
			jpo.getJpoSet().save();
		}
	}

}
