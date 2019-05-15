package com.glaway.sddq.service.transnotice.data;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 改造通知单jpo
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月9日]
 * @since [产品/模块版本]
 */
public class Transnotice extends Jpo {

	private static final long serialVersionUID = -833140152693701372L;

	@Override
	public void init() throws MroException {
		// 状态
		String status = getString("status");
		// 基本信息字段
		String[] fields = { "TRANSNOTICENUM", "APPPERSON", "APPDEPT",
				"APPDATE", "TRANSASSETTYPE", "TRANSDESC", "ISNEEDPRJTRANS",
				"PRJTRANSNUM", "BEFOREVERSION", "AFTERVERSION", "STARTDATE",
				"COMPLETEDATE", "TRANSTYPE", "TRANSKIND", "TRANSREASON",
				"TRANSPROGRAM", "TRANSPRJNUM", "TRANSWORKORDERNUM",
				"TOTALBUDGET", "ADDSPARECOUNT", "SPAREHANDLE",
				"PRJCHANGESTATUS", "DESIGNENGINEER", "ISCOMPLETED",
				"PRLINEASMNGER", "LEAVEDPROBLEM", "EXAMINERESULT",
				"VERIFYRESULT", "REMARK", "TRANSWORKORDERNUM",
				"COMPROJECTNAME", "TRANSREASONBRIEF", "IMPDEPT", "PRODLINEMGR",
				"QUALITYENG", "TECHMGR", "MANUFACTENGINEER", "TECHENGINEER",
				"TRANSFERENGINEER" };
		// 改造内容
		IJpoSet transcontent = this.getJpoSet("TRANSCONTENT");
		// 改造产品范围
		IJpoSet transrange = this.getJpoSet("TRANSRANGE");
		// 物料清单
		IJpoSet materiallist = this.getJpoSet("MATERIALLIST");
		// 改造计划
		IJpoSet transplan = this.getJpoSet("TRANSPLAN");

		// 非新建状态编号不能更改 add by sjchen 2018-05-02 11:12
		if (!isNew()) {
			setFieldFlag("TRANSNOTICENUM", GWConstant.S_READONLY, true);
		}

		if (!"草稿".equals(status)) {
			setFieldFlag(fields, GWConstant.S_READONLY, true);
			transcontent.setFlag(GWConstant.S_READONLY, true);
			transrange.setFlag(GWConstant.S_READONLY, true);
			materiallist.setFlag(GWConstant.S_READONLY, true);
			transplan.setFlag(GWConstant.S_READONLY, true);

			try {
				if (WfControlUtil.isCurUser(this)) {// 只有工作流中的角色才能操作
					if ("待审核".equals(status) || "审核中".equals(status)) {
						transcontent.setFlag(GWConstant.S_READONLY, false);
						transrange.setFlag(GWConstant.S_READONLY, false);
						materiallist.setFlag(GWConstant.S_READONLY, false);
						setFieldFlag("VERIFYRESULT", GWConstant.S_READONLY,
								false);
						setFieldFlag("EXAMINERESULT", GWConstant.S_READONLY,
								false);
						setFieldFlag("ISCOMPLETED", GWConstant.S_READONLY,
								false);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
