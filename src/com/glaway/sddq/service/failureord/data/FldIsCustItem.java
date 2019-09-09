package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 上下车记录-是否使用客户料 字段类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月8日]
 * @since [产品/模块版本]
 */
public class FldIsCustItem extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		super.action();

		// 当前输入值
		int input = this.getInputMroType().asInt();
		// 检修or服务
		String jxorfw = "";
		if (SddqConstant.SXC_GZ.equalsIgnoreCase(getJpo().getString("tasktype"))) {
			jxorfw = ItemUtil.JXORFW_FW ;
		}else if(SddqConstant.SXC_OH.equalsIgnoreCase(getJpo().getString("tasktype"))){
			jxorfw = ItemUtil.JXORFW_JX ;
		}

		if (isValueChanged()) {
			// 清空上车件数据
			String[] upAssets = new String[] { "NEWSQN", "NEWASSETNUM",
					"NEWITEMNUM", "NEWLOC", "NEWBINNUM", "NEWLOTNUM" };
			for (String upAsset : upAssets) {

				getJpo().setValueNull(upAsset);

			}
		}
		// 选择是，则库房过滤改为其他待处理库
		if (input == 1) {

			String station = getJpo().getParent().getParent().getString("WHICHSTATION");
			// 其它待处理库
			String where = "erploc='" + ItemUtil.ERPLOC_QTDCL
					+ "' and STOREROOMLEVEL in('"
					+ ItemUtil.STOREROOMLEVEL_XCK + "','"
					+ ItemUtil.STOREROOMLEVEL_XCZDK
					+ "') and locationtype='" + ItemUtil.LOCATIONTYPE_CG
					+ "' and jxorfw='" + jxorfw
					+ "' and locsite ='" + station + "'";

			IJpoSet locations = MroServer.getMroServer().getSysJpoSet("LOCATIONS", where);
			if (locations.count() > 0) {
				String loc = locations.getJpo().getString("LOCATION");
				getJpo().setValue("LOCATION", loc, GWConstant.P_NOVALIDATION);
			}

		} else {
			getJpo().getField("SQN").action();
		}
	}
}
