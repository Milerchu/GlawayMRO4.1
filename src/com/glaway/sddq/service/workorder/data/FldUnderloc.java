package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 耗损件记录 下车件库房 underloc字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018-6-10]
 * @since [产品/模块版本]
 */
public class FldUnderloc extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 2463108405986716108L;

	@Override
	public void init() throws MroException {
		super.init();
	}

	@Override
	public IJpoSet getList() throws MroException {
		String listwhere = "";
		IJpo parent = getJpo().getParent();
		String orderType = parent.getString("type");
		String erploc = "";
		String storeroomLevel = "";
		String locationType = "";
		if ("改造".equals(orderType) || "验证".equals(orderType)) {
			erploc = ItemUtil.ERPLOC_QTGZ;
			storeroomLevel = ItemUtil.STOREROOMLEVEL_XCK;
			locationType = ItemUtil.LOCATIONTYPE_CG;
		} else if ("故障".equals(orderType)) {
			erploc = ItemUtil.ERPLOC_1020;
			storeroomLevel = ItemUtil.STOREROOMLEVEL_XCZDK;
			locationType = ItemUtil.LOCATIONTYPE_WX;
		}
		if ("WORKORDER".equalsIgnoreCase(parent.getName())) {
			// 故障、改造、验证工单
			if (parent != null
					&& StringUtil.isStrNotEmpty(parent
							.getString("WHICHSTATION"))) {

				listwhere = "erploc='" + erploc + "' and STOREROOMLEVEL='"
						+ storeroomLevel + "' and LOCATIONTYPE='"
						+ locationType + "' and status='正常'  and locsite='"
						+ parent.getString("WHICHSTATION") + "'";
			} else {
				listwhere = "1=2";
			}
			listwhere=listwhere+"and status='正常'";
			this.setListWhere(listwhere);
		}
		if ("JXPRODUCT".equalsIgnoreCase(parent.getName())) {

			IJpo jxwo = this.getJpo().getParent().getParent();
			if ("JXTASKORDER".equalsIgnoreCase(jxwo.getName())) {
				listwhere = "erploc='其他待处理库' and STOREROOMLEVEL='现场库'and LOCATIONTYPE='常规'";
			}
			listwhere=listwhere+"and status='正常'";
			this.setListWhere(listwhere);
		}
		return super.getList();
	}
}
