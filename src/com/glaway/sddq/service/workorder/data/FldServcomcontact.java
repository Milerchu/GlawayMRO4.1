package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 工单服务单位联系人字段类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月9日]
 * @since [产品/模块版本]
 */
public class FldServcomcontact extends JpoField {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 2726734032634625600L;

	@Override
	public IJpoSet getList() throws MroException {
		IJpoSet returnSet = super.getList();
		String servcompany = this.getJpo().getString("SERVCOMPANY");
		// 根据服务单位筛选数据
		if (StringUtil.isStrNotEmpty(servcompany)) {
			// 获取服务单位客户编号
			String custnum = getJpo().getString("SERVCOMPANY.CUSTNUM");
			returnSet.setUserWhere("custnum='" + custnum + "'");
			returnSet.reset();
		} else {

			returnSet.setUserWhere("1=2");
			returnSet.reset();

		}
		return returnSet;
	}
}
