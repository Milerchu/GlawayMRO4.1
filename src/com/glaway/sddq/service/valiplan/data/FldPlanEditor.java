package com.glaway.sddq.service.valiplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 验证计划-计划编制人字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月23日]
 * @since [产品/模块版本]
 */
public class FldPlanEditor extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -4138684194212780898L;

	@Override
	public IJpoSet getList() throws MroException {

		if ("VALIPLAN".equalsIgnoreCase(getJpo().getAppName())) {// 验证计划

			String persons = "";
			// 人员组
			String group = "";
			group = "100210";
			persons = WorkorderUtil.getPersonsFromPersonGroup(group);
			if (StringUtil.isStrEmpty(persons)) {
				throw new MroException("", "人员组中无角色！");
			}
			setListWhere("personid in (" + persons + ")");

		}
		return super.getList();

	}
}
