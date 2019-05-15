package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 上下车记录 下车件库房字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-19]
 * @since [产品/模块版本]
 */
public class FldLocation extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -4635929287683405950L;

	@Override
	public IJpoSet getList() throws MroException {
		// 服务类工单下车件入1020库房，检修入1030库房过滤
		String appName = this.getJpo().getAppName();
		String where = "";
		if ("FAILUREORD".equalsIgnoreCase(appName)) {// 故障工单下车入现场维修库
			// 所属站点
			String station = getJpo().getString("WORKORDER.WHICHSTATION");
			where = "erploc='" + ItemUtil.ERPLOC_1020
					+ "' and  STOREROOMLEVEL='" + ItemUtil.STOREROOMLEVEL_XCZDK
					+ "' and locationtype='" + ItemUtil.LOCATIONTYPE_WX
					+ "' and jxorfw='" + ItemUtil.JXORFW_FW
					+ "' and locsite ='" + station + "'";

		} else if ("TRANSORDER".equalsIgnoreCase(appName)
				|| "VALIORDER".equalsIgnoreCase(appName)) {
			// 改造、验证工单
			// 当前工单处理人所在站点
			String station = this.getJpo().getString("WORKORDER.WHICHSTATION");
			where = "erploc='" + ItemUtil.ERPLOC_QTGZ
					+ "' and STOREROOMLEVEL='" + ItemUtil.STOREROOMLEVEL_XCK
					+ "' and locationtype='" + ItemUtil.LOCATIONTYPE_CG
					+ "' and jxorfw='" + ItemUtil.JXORFW_FW
					+ "' and locsite  ='" + station + "'";
		} else {// 检修工单
			where = "ERPLOC='" + ItemUtil.ERPLOC_1020 + "'and STOREROOMLEVEL='"
					+ ItemUtil.STOREROOMLEVEL_XCK + "' and locationtype='"
					+ ItemUtil.LOCATIONTYPE_CG + "'";

		}
		where=where+"and status='正常'";
		setListWhere(where);
		return super.getList();
	}
}
