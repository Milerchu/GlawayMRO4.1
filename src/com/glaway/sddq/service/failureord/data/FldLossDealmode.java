package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 故障工单-上下车记录耗损件 故障品处置方式字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年12月14日]
 * @since [产品/模块版本]
 */
public class FldLossDealmode extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {

		return super.getList();

	}

	@Override
	public void action() throws MroException {
		super.action();
		String dealmode = this.getInputMroType().asString();
		if (!getJpo().getBoolean("ISCUSTITEM")) {
			if (StringUtil.isStrNotEmpty(dealmode) && isValueChanged()) {
				// 出库库房
				String newloc = getJpo().getString("UPLOC");
				// 入库库房
				String oldloc = "";
				String station = getJpo().getParent().getString("WHICHSTATION");
				String where = "erploc='" + ItemUtil.ERPLOC_1020
						+ "' and  STOREROOMLEVEL in('"
						+ ItemUtil.STOREROOMLEVEL_XCZDK + "','"
						+ ItemUtil.STOREROOMLEVEL_XCK + "') and locationtype='"
						+ ItemUtil.LOCATIONTYPE_WX + "' and jxorfw='"
						+ ItemUtil.JXORFW_FW + "' and locsite ='" + station
						+ "'";
				IJpoSet locationSet = MroServer.getMroServer().getSysJpoSet(
						"LOCATIONS", where);
				if (!locationSet.isEmpty()) {
					oldloc = locationSet.getJpo(0).getString("LOCATION");
				}
				// 现场留用观察
				if (SddqConstant.FAIL_DEALMODE_RETENTION.equals(dealmode)
						&& StringUtil.isStrNotEmpty(newloc)) {

					getJpo().setValue("UNDERLOC", newloc,
							GWConstant.P_NOVALIDATION);

				} else {
					// 返修
					if (StringUtil.isStrNotEmpty(oldloc)) {

						getJpo().setValue("UNDERLOC", oldloc,
								GWConstant.P_NOVALIDATION);

					}
				}
			}
		}
	}

}
