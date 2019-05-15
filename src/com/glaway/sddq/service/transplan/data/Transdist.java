package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 改造/验证计划 改造/验证分布 jpo
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月8日]
 * @since [产品/模块版本]
 */
public class Transdist extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -2297765695168911134L;

	@Override
	public void init() throws MroException {

		super.init();

		if ("VALIPLAN".equalsIgnoreCase(this.getAppName())) {// 验证计划

			String[] attr = { "WHICHOFFICE", "KINDLOC", "TRANSLOC",
					"TRANSCOUNT" };
			if (getParent() != null
					&& "执行中".equals(getParent().getString("status"))) {
				// 非当前办事处责任人登录时不可编辑
				if (!getString("RESPONSIBLE").equalsIgnoreCase(
						getUserInfo().getLoginID())) {
					this.setFlag(GWConstant.S_READONLY, true);
				}

				this.setFieldFlag(attr, GWConstant.S_READONLY, true);
			}

			if (!getJpoSet("VALIORDER").isEmpty()) {
				// 已经有生成的工单时，验证分布表不能修改
				String[] attr2 = { "WHICHOFFICE", "TRANSMODELS", "TRANSCOUNT" };
				setFieldFlag(attr2, GWConstant.S_READONLY, true);
			}
			if (getInt("SURPLUS") != getInt("TRANSCOUNT")
					&& !"草稿".equals(getParent().getString("status"))) {
				// 验证数量和剩余数量不符时不允许修改验证数量
				setFieldFlag("TRANSCOUNT", GWConstant.S_READONLY, true);
			}

		} else {// 改造计划

			String planStatus = getParent().getString("status");
			String[] attrs = { "STATION", "PROJECTNUM", "CARNUMS",
					"TRANSMODELS", "KINDLOC", "TRANSLOC", "TRANSCOUNT", "UNIT" };
			setFieldFlag(attrs, GWConstant.S_READONLY, true);

			if ("草稿".equals(planStatus)) {

				setFieldFlag(attrs, GWConstant.S_READONLY, false);

			} else if ("执行中".equals(planStatus)) {

				if (getString("RESPONSIBLE").equalsIgnoreCase(
						getUserInfo().getPersonId())) {// 非办事处负责人只读

					String[] zxzAttrs = { "CARNUMS", "KINDLOC", "TRANSLOC",
							"TRANSCOUNT", "UNIT" };
					setFieldFlag(zxzAttrs, GWConstant.S_READONLY, false);

				}

			}

		}

	}

}
