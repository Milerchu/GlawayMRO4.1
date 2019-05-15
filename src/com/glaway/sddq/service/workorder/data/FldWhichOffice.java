package com.glaway.sddq.service.workorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 所属办事处whichoffice字段类
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-6-2]
 * @since [MRO4.1/模块版本]
 */
public class FldWhichOffice extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -2671951574792754210L;

	@Override
	public IJpoSet getList() throws MroException {
		String appName = getJpo().getAppName();
		if ("TRANSORDER".equalsIgnoreCase(appName)
				|| "VALIORDER".equalsIgnoreCase(appName)) {// 改造工单\验证工单
			if (StringUtil.isStrEmpty(getJpo().getString("plannum"))) {
				// 先选择计划
				throw new MroException("workorder", "noplannum");
			}
			IJpoSet transdistSet = getJpo().getJpoSet("TRANSPLAN").getJpo()
					.getJpoSet("TRANSDIST");
			String deptnums = "";
			for (int i = 0; i < transdistSet.count(); i++) {
				IJpo transdist = transdistSet.getJpo(i);
				deptnums += "'" + transdist.getString("whichoffice") + "',";
			}
			if (StringUtil.isStrNotEmpty(deptnums)) {
				// 去除末尾，
				deptnums = deptnums.substring(0, deptnums.length() - 1);
				this.setListWhere("deptnum in(" + deptnums + ")");
			}

		}

		return super.getList();
	}

	@Override
	public void action() throws MroException {

		super.action();
		if (isValueChanged()) {
			if (StringUtil.isStrNotEmpty(getJpo().getString("whichstation"))) {// 清空已经选择的站点
				getJpo().setValueNull("whichstation", GWConstant.P_NOVALIDATION);
			}
			// 设置工单编号
			if (getJpo().isNew()
					|| SddqConstant.WO_STATUS_CG.equals(getJpo().getString(
							"status"))) {
				// 当前输入的值
				String whichoffice = getInputMroType().asString();
				// 生成工单编号
				String ordernum = WorkorderUtil.generateOrdernum(whichoffice,
						getJpo().getAppName(), getJpo().getString("type"));
				getJpo().setValue("ordernum", ordernum);

				if ("FAILUREORD".equalsIgnoreCase(getJpo().getAppName())
						|| "故障".equals(getJpo().getString("TYPE"))) {// 故障工单

					// 新增故障记录
					IJpoSet failSet = getJpo().getJpoSet("FAILURELIB");
					IJpo failure = null;
					if (failSet.isEmpty()) {
						failure = failSet.addJpo();
					} else {
						failure = failSet.getJpo();
					}
					failure.setValue("FAILUREORDERNUM", ordernum);
				}
			}
		}
	}

}
