package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ProjectUtil;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造计划Jpo
 * 
 * @author bchen
 * @version [版本号, 2018-3-9]
 * @since [产品/模块版本]
 */
public class TransPlan extends Jpo {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		String status = getString("status");

		try {

			if (isNew()) {

			} else {

				/* 判断当前登录人是否在工单处理人中 */

				String planType = getString("plantype");
				if ("验证".equalsIgnoreCase(planType)) {// 验证计划

					if (WfControlUtil.isCurUser(this) || isDealPerson()) {// 根据工作流设置权限
						// 计划主表字段
						String[] valiAttr = { "TRANSPLANNAME", "PLANEDITOR",
								"TRANSPRJNUM", "USERCOMPANY", "PERIOD",
								"PERIODUNIT", "BVERSION", "AVERSION",
								"STANDARDWH", "REMARK", "PRJCOOR", "TECHSUP",
								"STARTDATE" };
						IJpoSet rangeSet = getJpoSet("VALIPRORANGE");
						setFieldFlag(valiAttr, GWConstant.S_READONLY, true);// 所有字段设只读
						rangeSet.setFlag(GWConstant.S_READONLY, true);// 验证范围子表只读
						if ("关闭".equals(status)) {

							setFlag(GWConstant.S_READONLY, true);

						} else if ("草稿".equals(status)) {

							setFieldFlag(valiAttr, GWConstant.S_READONLY, false);
							rangeSet.setFlag(GWConstant.S_READONLY, false);

						} else if ("执行中".equals(status)) {

							rangeSet.setFlag(GWConstant.S_READONLY, false);

						} else {// 待审核、已完成

							// setFieldFlag(valiAttr, GWConstant.S_READONLY,
							// true);

						}
					} else {

						setFlag(GWConstant.S_READONLY, true);

					}
				} else {// 改造计划

					String[] transAttrs = { "TRANSPLANNAME", "PRJCOOR",
							"PLANTYPE", "TECHSUP", "STANDARDWH" };

					IJpoSet transdistSet = getJpoSet("TRANSDIST");// 改造车辆分布
					IJpoSet materSet = getJpoSet("TRANSMATERIALPLAN");// 物料计划
					IJpoSet contentSet = getJpoSet("TRANSCONTENT");// 改造内容

					// 所有字段及子表只读
					setFieldFlag(transAttrs, GWConstant.S_READONLY, true);
					transdistSet.setFlag(GWConstant.S_READONLY, true);
					materSet.setFlag(GWConstant.S_READONLY, true);
					contentSet.setFlag(GWConstant.S_READONLY, true);

					if (WfControlUtil.isCurUser(this) ||
							WorkorderUtil.isInAdminGroup(getUserInfo().getLoginID())) {// 根据工作流设置权限

						if ("草稿".equals(status)) {

							setFieldFlag(transAttrs, GWConstant.S_READONLY,
									false);
							transdistSet.setFlag(GWConstant.S_READONLY, false);
							contentSet.setFlag(GWConstant.S_READONLY, false);

						} else if ("已完成".equals(status)) {

							materSet.setFlag(GWConstant.S_READONLY, false);

						} else if ("待审核".equals(status)) {

							// setFieldFlag(attr1, GWConstant.S_READONLY, true);

						} else if ("执行中".equals(status)) {

							transdistSet.setFlag(GWConstant.S_READONLY, false);

							String loginId = this.getUserInfo().getPersonId();
							for (int index = 0; index < transdistSet.count(); index++) {

								IJpo transDist = transdistSet.getJpo(index);
								String prjNum = transDist
										.getString("PROJECTNUM");
								if (ProjectUtil.isSomeRoleInTheProject(loginId,
										prjNum, SddqConstant.PRJ_ROLE_SHJHJL)) {
									// 判断项目售后计划经理才能编辑
									materSet.setFlag(GWConstant.S_READONLY,
											false);

								}

							}

						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void add() throws MroException {

		// 根据当前应用填入不同的值
		String appname = this.getAppName();

		if (StringUtil.isStrNotEmpty(appname)) {
			if (!"TRANSPLAN".equalsIgnoreCase(appname)) {// 验证计划
				this.setValue("plantype", "验证");
			}
		}

		super.add();
	}

	/**
	 * 
	 * 判断当前登录人是否是工单处理人
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	private boolean isDealPerson() throws MroException {
		String loginid = getUserInfo().getLoginID();
		IJpoSet rangeSet = this.getJpoSet("VALIPRORANGE");
		boolean flag = false;
		if (!rangeSet.isEmpty()) {

			for (int i = 0; i < rangeSet.count(); i++) {
				IJpo range = rangeSet.getJpo(i);
				IJpoSet orderSet = range.getJpoSet("valiorder");// 验证工单子表
				if (!orderSet.isEmpty()) {
					IJpo order = orderSet.getJpo(0);// 取第一条工单
					IJpoSet personSet = order.getJpoSet("JXTASKEXECPERSON");// 现场处理人子表
					if (!personSet.isEmpty()) {
						for (int j = 0; j < personSet.count(); j++) {
							IJpo person = personSet.getJpo(j);
							if (loginid.equalsIgnoreCase(person
									.getString("personnum"))) {
								flag = true;
								return flag;
							}
						}
					}
				}
			}
		}

		return flag;
	}
}
