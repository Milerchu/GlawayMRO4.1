package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 工单所属站段 whichstation字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月1日]
 * @since [产品/模块版本]
 */
public class FldWhichStation extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -8177858340209659724L;

	@Override
	public IJpoSet getList() throws MroException {
		// 根据办事处过滤站点
		String whichoffice = getJpo().getString("whichoffice");
		String where = "";
		if (StringUtil.isStrNotEmpty(whichoffice)) {
			if ("01062400".equals(whichoffice)) {// 青岛检修分公司特殊处理
				where = " parent in ('01062400','01062401','01062403','01062402') ";
			} else {
				where = " parent='" + whichoffice + "' ";
			}
		} else {
			where = "1=2";
		}
		this.setListWhere(where);
		return super.getList();
	}
}
