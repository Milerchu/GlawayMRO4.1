package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 产品列表计划数量字段类
 * 
 * @author  public2797
 * @version  [版本号, 2018-9-3]
 * @since  [产品/模块版本]
 */
public class FldProcPlanNum extends JpoField {

	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		
		int plannum =this.getJpo().getInt("PLANNUM");
		int amount =this.getJpo().getInt("AMOUNT");
		int palnamount=amount*plannum;		
		this.getJpo().setValue("PALNAMOUNT", palnamount, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		
		IJpoSet stockaruslistSet =this.getJpo().getJpoSet("STOCKARUSLIST");	
		if(!stockaruslistSet.isEmpty())
		{
			String[] field = {"PLANNUM"};
			String[] values = { String.valueOf(plannum)};
			stockaruslistSet.updateAll(field, values);
		}
	}	
}
