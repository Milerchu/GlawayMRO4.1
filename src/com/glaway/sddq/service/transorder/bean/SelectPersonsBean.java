package com.glaway.sddq.service.transorder.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 改造工单-选择人员databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月26日]
 * @since [产品/模块版本]
 */
public class SelectPersonsBean extends DataBean {

	@Override
	public int dialogok() throws IOException, MroException {
		// 获取选择的人员
		List<IJpo> personSet = getDataBean("sps_1536289520596").getJpoSet()
				.getSelections();

		if (personSet.size() > 0) {

			String value = this.getDialog().getCreator().getValue();// 获取当前字段
			String persons = "";
			if (StringUtil.isStrEmpty(value)) {// 当前字段没值

				for (IJpo person : personSet) {

					persons += person.getString("DISPLAYNAME") + ",";

				}
				if (StringUtil.isStrNotEmpty(persons)) {
					// 去除末尾逗号
					persons = persons.substring(0, persons.length() - 1);
				}

			} else {// 当前字段有值

				persons += value;

				for (IJpo person : personSet) {

					persons += "," + person.getString("DISPLAYNAME");

				}

			}

			this.getDialog().getCreator().setValue(persons);
		}

		// 关闭对话框
		this.dialogclose();
		return GWConstant.NOACCESS_SAMEMETHOD;
	}

}
