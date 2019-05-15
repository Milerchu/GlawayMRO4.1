package com.glaway.sddq.service.failurelib.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * <故障管理--故障后果字段类>
 * 
 * @author hzhu
 * @version [MRO4.0, 2018-4-24]
 * @since [MRO4.0/模块版本]
 */
public class FldFaultconseq extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {
		/* 根据车型分类（机动城）显示不同的下拉菜单 */
		IJpoSet domainSet = null;
		// 车型编号
		IJpo parent = getJpo().getParent();
		if (parent != null) {
			String cmodel = parent.getString("MODELS");
			if (!StringUtil.isStrEmpty(cmodel)) {
				// 机动城
				String productline = parent.getString("MODELS.PRODUCTLINE");
				domainSet = getUserServer().getJpoSet(
						"SYS_SYNDOMAIN",
						"domainid='FAULTCONSEQUENCE' and INNERVALUE = '"
								+ productline + "'");
				domainSet.reset();
				return domainSet;
			}
		}
		return domainSet;

	}

	@Override
	public void action() throws MroException {

		super.action();
		// 故障后果
		String faultconseq = getInputMroType().asString();
		if (WorkorderUtil.isImpFault(faultconseq)) {// 高等级故障
			this.getJpo().setFieldFlag("ANALYSISREPNEED",
					GWConstant.S_READONLY, false);
			this.getJpo().setValue("ANALYSISREPNEED", "是");
			this.getJpo().setFieldFlag("ANALYSISREPNEED",
					GWConstant.S_READONLY, true);
		} else {
			this.getJpo().setFieldFlag("ANALYSISREPNEED",
					GWConstant.S_READONLY, false);
		}

	}

}
