package com.glaway.sddq.service.valiplan.bean;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 验证计划-人员分配dialog-databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月8日]
 * @since [产品/模块版本]
 */
public class DistributeBean extends DataBean {

	@Override
	public int dialogok() throws MroException, IOException {
		// int r = super.dialogok();
		// 获取选择的人员list
		List<IJpo> personList = getDataBean("1533716743069").getJpoSet()
				.getSelections();

		// 关联的验证工单jposet
		IJpoSet valiorderSet = getDataBean("main_tab2").getJpo().getJpoSet(
				"VALIORDER");
		Set<String> personSet = new HashSet<String>();// 处理人personId set
		Set<String> personNameSet = new HashSet<String>();// 处理人name set

		if (valiorderSet != null && valiorderSet.count() > 0) {
			for (int i = 0; i < valiorderSet.count(); i++) {
				// 工单jpo
				IJpo valiorder = valiorderSet.getJpo(i);
				String dealPersons = valiorder.getString("DEALPERSON");
				// 工单编号
				String ordernum = valiorder.getString("ORDERNUM");
				// 工单处理人子表
				IJpoSet orderPersonSet = valiorder
						.getJpoSet("JXTASKEXECPERSON");
				// 遍历选择的人员
				for (int index = 0; index < personList.size(); index++) {
					IJpo person = personList.get(index);
					// 新增工单处理人
					IJpo orderPerson = orderPersonSet.addJpo();
					orderPerson.setValue("WORKORDERTYPE", "验证");
					orderPerson.setValue("JXTASKNUM", ordernum);
					orderPerson.setValue("PERSONNUM",
							person.getString("PERSONID"),
							GWConstant.P_NOVALIDATION_AND_NOACTION);
					orderPerson.setValue("CREATOR", getUserInfo()
							.getLoginUserName());
					orderPerson.setValue("SITEID", "ELEC");
					orderPerson.setValue("ORGID", "CRRC");
					personSet.add(person.getString("PERSONID"));
					personNameSet.add(person.getString("DISPLAYNAME"));
					if (StringUtil.isStrNotEmpty(dealPersons)) {
						dealPersons = dealPersons + ","
								+ person.getString("DISPLAYNAME");
					} else {
						dealPersons = person.getString("DISPLAYNAME");
					}
				}
				// orderPersonSet.save();
				valiorder.setValue("status", "处理中");
				if (StringUtil.isStrNotEmpty(dealPersons)) {// 处理人不为空

					valiorder.setValue("DEALPERSON", dealPersons);

				}
			}

			// 设置计划中的处理人字段的值
			String dealPersonSetStr = StringUtil.join(personNameSet.toArray());
			String planDealPersons = getAppBean().getJpo().getString(
					"DEALPERSONS");
			if (StringUtil.isStrNotEmpty(planDealPersons)) {
				planDealPersons += "," + dealPersonSetStr;
			} else {
				planDealPersons = dealPersonSetStr;
			}
			getAppBean().getJpo().setValue("DEALPERSONS", planDealPersons,
					GWConstant.P_NOVALIDATION);
		}

		// 关闭对话框
		this.dialogclose();
		this.page.getAppBean().SAVE();

		if (personSet.size() > 0) {

			// 发送系统消息通知处理人
			String[] receivers = new String[personSet.size()];
			personSet.toArray(receivers);
			WorkorderUtil.sendMsg("VALIPLAN", this.page.getAppBean().getJpo()
					.getId(), receivers, "您有新的验证任务，请查收！", "您有新的验证计划（"
					+ this.page.getAppBean().getJpo().getString("TRANSPLANNUM")
					+ "）正在执行，请查收！");

		}
		// 刷新表格
		// this.getDataBean("1533607363636").resetAndReload();

		return 1;
	}
}
