package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

public class JxProduct extends Jpo implements IJpo {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {

		super.init();
		IJpo JxProductSetJpo = this.getJpoSet().getJpo();
		String status = this.getParent().getString("STATUS");
		String dispatcher  =this.getParent().getString("dispatcher");
		IJpoSet jxproductset = this.getJpoSet();

		if (jxproductset != null && !jxproductset.isEmpty()) {
			if (isNew() || SddqConstant.JX_STATUS_CG.equals(status)) {
				IJpoSet jxtaskitemset = this.getJpoSet("JXTASKITEM");// 检修工序
				IJpoSet jxtasklosspartset = this.getJpoSet("JXTASKLOSSPART");// 耗损件
				IJpoSet exchangerecordset = this.getJpoSet("EXCHANGERECORD");// 周转件
				IJpoSet exchangerecord2set = this.getJpoSet("EXCHANGERECORD2");// 偶换件
				IJpoSet jxtasktoolsset = this.getJpoSet("JXTASKTOOLS");// 工具使用记录
				IJpoSet jxtaskexecpersonset = this
						.getJpoSet("JXTASKEXECPERSON");// 执行人工记录

				IJpoSet jxtaskrecordset = this.getJpoSet("JXTASKRECORD");// 检查单记录
				IJpoSet jxtestrecordset = this.getJpoSet("JXTESTRECORD");// 实验记录

				// this.getJpoSet("JXTASKSWAPHISTORY").setFlag(
				// GWConstant.S_READONLY, true);

				jxtaskitemset.setFlag(GWConstant.S_READONLY, true);// 检修工序
				jxtasklosspartset.setFlag(GWConstant.S_READONLY, true);// 耗损件
				exchangerecordset.setFlag(GWConstant.S_READONLY, true);// 周转件
				exchangerecord2set.setFlag(GWConstant.S_READONLY, true);// 偶换件
				jxtasktoolsset.setFlag(GWConstant.S_READONLY, true);// 工具使用记录
				jxtaskexecpersonset.setFlag(GWConstant.S_READONLY, true);// 执行人工记录
				jxtaskrecordset.setFlag(GWConstant.S_READONLY, true);// 检查单记录
				jxtestrecordset.setFlag(GWConstant.S_READONLY, true);// 实验记录

			} else if (isNew() || SddqConstant.JX_STATUS_WC.equals(status)) {
				IJpoSet jxtaskitemset = this.getJpoSet("JXTASKITEM");// 检修工序
				IJpoSet jxtasklosspartset = this.getJpoSet("JXTASKLOSSPART");// 耗损件
				IJpoSet exchangerecordset = this.getJpoSet("EXCHANGERECORD");// 周转件
				IJpoSet exchangerecord2set = this.getJpoSet("EXCHANGERECORD2");// 偶换件
				IJpoSet jxtasktoolsset = this.getJpoSet("JXTASKTOOLS");// 工具使用记录
				IJpoSet jxtaskexecpersonset = this
						.getJpoSet("JXTASKEXECPERSON");// 执行人工记录

				IJpoSet jxtaskrecordset = this.getJpoSet("JXTASKRECORD");// 检查单记录
				IJpoSet jxtestrecordset = this.getJpoSet("JXTESTRECORD");// 实验记录

				// this.getJpoSet("JXTASKSWAPHISTORY").setFlag(
				// GWConstant.S_READONLY, true);

				jxtaskitemset.setFlag(GWConstant.S_READONLY, true);// 检修工序
				jxtasklosspartset.setFlag(GWConstant.S_READONLY, true);// 耗损件
				exchangerecordset.setFlag(GWConstant.S_READONLY, true);// 周转件
				exchangerecord2set.setFlag(GWConstant.S_READONLY, true);// 偶换件
				jxtasktoolsset.setFlag(GWConstant.S_READONLY, true);// 工具使用记录
				jxtaskexecpersonset.setFlag(GWConstant.S_READONLY, true);// 执行人工记录
				jxtaskrecordset.setFlag(GWConstant.S_READONLY, true);// 检查单记录
				jxtestrecordset.setFlag(GWConstant.S_READONLY, true);// 实验记录

			}
		/*	//调度员是检修基地调度-青岛的判断
			if(SddqConstant.JX_DDY_QD.equals(dispatcher)){
				
				IJpoSet jxtasklosspartset = this.getJpoSet("JXTASKLOSSPART");// 耗损件
				IJpoSet exchangerecordset = this.getJpoSet("EXCHANGERECORD");// 周转件
				IJpoSet exchangerecord2set = this.getJpoSet("EXCHANGERECORD2");// 偶换件
				
				
				jxtasklosspartset.setFlag(GWConstant.S_READONLY, true);// 耗损件
				exchangerecordset.setFlag(GWConstant.S_READONLY, true);// 周转件
				exchangerecord2set.setFlag(GWConstant.S_READONLY, true);// 偶换件
			}*/
		}

	}

}