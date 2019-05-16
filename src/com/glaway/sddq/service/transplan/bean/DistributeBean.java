package com.glaway.sddq.service.transplan.bean;

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

/**
 * 
 * 改造计划-改造车辆分布-分配人员Dialog databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月12日]
 * @since [产品/模块版本]
 */
public class DistributeBean extends DataBean {

	@Override
	public int dialogok() throws MroException, IOException {
		// int r = super.dialogok();
		// 获取选择的人员list
		List<IJpo> personList = getDataBean("1533716743069").getJpoSet()
				.getSelections();
		Set<String> personSet = new HashSet<String>();

		// 关联的改造工单jposet
		IJpoSet transorderSet = getDataBean("1520318024916").getJpo()
				.getJpoSet("TRANSORDER");
		if (transorderSet != null && transorderSet.count() > 0) {

			for (int i = 0; i < transorderSet.count(); i++) {
				// 工单jpo
				IJpo transorder = transorderSet.getJpo(i);
				String dealPersons = transorder.getString("DEALPERSON");
				// 工单编号
				String ordernum = transorder.getString("ORDERNUM");
				// 工单处理人子表
				IJpoSet orderPersonSet = transorder
						.getJpoSet("JXTASKEXECPERSON");
				// 遍历选择的人员
				for (int index = 0; index < personList.size(); index++) {

					IJpo person = personList.get(index);
					// 新增工单处理人
					IJpo orderPerson = orderPersonSet.addJpo();
					orderPerson.setValue("WORKORDERTYPE", "改造");
					orderPerson.setValue("JXTASKNUM", ordernum);
					orderPerson.setValue("PERSONNUM",
							person.getString("PERSONID"),
							GWConstant.P_NOVALIDATION_AND_NOACTION);
					orderPerson.setValue("CREATOR", getUserInfo()
							.getLoginUserName());
					orderPerson.setValue("SITEID", "ELEC");
					orderPerson.setValue("ORGID", "CRRC");

					personSet.add(person.getString("DISPLAYNAME"));
					if (StringUtil.isStrNotEmpty(dealPersons)) {
						dealPersons = dealPersons + ","
								+ person.getString("DISPLAYNAME");
					} else {
						dealPersons = person.getString("DISPLAYNAME");
					}

				}
				if (StringUtil.isStrNotEmpty(dealPersons)) {// 处理人不为空

					transorder.setValue("DEALPERSON", dealPersons);

				}
			}

			// 设置计划中的处理人字段的值
			String dealPersonSetStr = StringUtil.join(personSet.toArray());
			String planDealPersons = getAppBean().getJpo().getString("DEALPERSONS");
			if(StringUtil.isStrNotEmpty(planDealPersons)){
				planDealPersons += "," + dealPersonSetStr;
			}else{
				planDealPersons = dealPersonSetStr;
			}
			getAppBean().getJpo().setValue("DEALPERSONS", planDealPersons,
					GWConstant.P_NOVALIDATION);
		}

		// 关闭对话框
		this.dialogclose();
		this.page.getAppBean().SAVE();

		// 刷新表格
		// this.getDataBean("1536286533933").resetAndReload();

		return 1;
	}

}
