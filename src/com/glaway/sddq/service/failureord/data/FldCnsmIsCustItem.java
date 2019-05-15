package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 耗损件记录-是否使用客户料iscustitem字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年10月10日]
 * @since [产品/模块版本]
 */
public class FldCnsmIsCustItem extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {

		super.action();

		// 当前输入值
		int input = this.getInputMroType().asInt();
		if ("FAILUREORD".equalsIgnoreCase(getJpo().getAppName())) {// 故障工单
			if (isValueChanged()) {
				// 清空上车件数据
				String[] upAssets = new String[] { "ITEMNUM", "INVENTORY",
						"UPLOC", "LOTNUM" };
				for (String upAsset : upAssets) {

					getJpo().setValueNull(upAsset);

				}
			}
			if (input == 1) {

				String station = getJpo().getParent().getString("WHICHSTATION");// 故障所属站点
				// 其它待处理库
				String where = "erploc='" + ItemUtil.ERPLOC_QTDCL
						+ "' and STOREROOMLEVEL in('"
						+ ItemUtil.STOREROOMLEVEL_XCK + "','"
						+ ItemUtil.STOREROOMLEVEL_XCZDK
						+ "') and locationtype='" + ItemUtil.LOCATIONTYPE_CG
						+ "' and jxorfw='" + ItemUtil.JXORFW_FW
						+ "' and locsite ='" + station + "'";

				IJpoSet locations = MroServer.getMroServer().getSysJpoSet(
						"LOCATIONS", where);
				if (locations != null && locations.count() > 0) {
					String loc = locations.getJpo().getString("LOCATION");
					getJpo().setValue("UNDERLOC", loc,
							GWConstant.P_NOVALIDATION);
				}

			} else {
				getJpo().getField("DOWNITEMNUM").action();
			}
		}
	}

}
