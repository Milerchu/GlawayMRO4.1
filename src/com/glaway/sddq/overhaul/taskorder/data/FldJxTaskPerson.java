package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 故障工单人工执行记录人员编号字段类
 * 
 * @author zzx
 * @version [版本号, 2018-8-20]
 * @since [产品/模块版本]
 */
public class FldJxTaskPerson extends JpoField {
	public IJpoSet getList() throws MroException {
		String appName = getJpo().getAppName();
		if (!appName.equals("JXTASKORDE")) {
			if (StringUtil.isStrNotEmpty(appName)) {
				// 所属办事处为空，返回空列表
				String whichOffice = getJpo().getParent().getString(
						"whichoffice");
				if (whichOffice.isEmpty()) {
					this.setListWhere("1=2");
					return super.getList();
				}
				String personids = "";
				IJpoSet jxtaskexecpersonset = getJpo().getJpoSet();
				if (jxtaskexecpersonset != null
						&& jxtaskexecpersonset.count() > 0) {
					for (int i = 0; i < jxtaskexecpersonset.count(); i++) {
						if (jxtaskexecpersonset.getJpo(i) != this.getJpo()) {
							String personnum = jxtaskexecpersonset.getJpo(i)
									.getString("PERSONNUM");
							personids += "'" + personnum + "',";
						}

					}
					if (StringUtil.isStrNotEmpty(personids)) {
						personids = personids.substring(0,
								personids.length() - 1);
					}
					// 需要根据办事处过滤人员
					// String listwhere = " department='" + whichOffice + "'";
					String listwhere = " department in(select deptnum from sys_dept where parent in('01060000','01062400'))  ";// 过滤售后人员
					// 过滤已选择
					if (StringUtil.isStrNotEmpty(personids)) {
						listwhere += "  and personid not in(" + personids + ")";
					}
					this.setListWhere(listwhere);
				}
			}
		}
		return super.getList();
	}
}
